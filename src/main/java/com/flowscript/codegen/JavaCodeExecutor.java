package com.flowscript.codegen;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Componente para compilar y ejecutar código Java generado dinámicamente.
 *
 * Toma una cadena con código Java, lo compila en memoria y ejecuta métodos específicos.
 */
public class JavaCodeExecutor {

    private final JavaCompiler compiler;
    private final StandardJavaFileManager fileManager;

    public JavaCodeExecutor() {
        this.compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("No se encontró el compilador de Java. " +
                    "Asegúrate de estar usando un JDK, no un JRE.");
        }
        this.fileManager = compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8);
    }

    /**
     * Compila y ejecuta código Java.
     *
     * @param javaCode Código Java completo (debe incluir package, imports y clase)
     * @return Resultado de la ejecución
     * @throws ExecutionException Si hay errores de compilación o ejecución
     */
    public ExecutionResult execute(String javaCode) throws ExecutionException {
        return execute(javaCode, "main", new String[0]);
    }

    /**
     * Compila y ejecuta un método específico del código Java.
     *
     * @param javaCode Código Java completo
     * @param methodName Nombre del método a ejecutar
     * @param args Argumentos para el método
     * @return Resultado de la ejecución
     * @throws ExecutionException Si hay errores de compilación o ejecución
     */
    public ExecutionResult execute(String javaCode, String methodName, Object... args)
            throws ExecutionException {

        long startTime = System.currentTimeMillis();

        // 1. Extraer nombre de clase y paquete del código
        ClassInfo classInfo = extractClassInfo(javaCode);

        // 2. Compilar el código
        CompiledClass compiledClass = compile(javaCode, classInfo);

        // 3. Cargar la clase compilada
        Class<?> loadedClass = loadClass(compiledClass, classInfo);

        // 4. Ejecutar el método
        Object result = invokeMethod(loadedClass, methodName, args);

        long executionTime = System.currentTimeMillis() - startTime;

        return new ExecutionResult(true, result, null, executionTime);
    }

    /**
     * Compila el código Java sin ejecutarlo.
     * Útil para validar sintaxis.
     */
    public CompilationResult compile(String javaCode) {
        try {
            ClassInfo classInfo = extractClassInfo(javaCode);
            CompiledClass compiled = compile(javaCode, classInfo);
            return new CompilationResult(true, null, compiled.bytecode.length);
        } catch (ExecutionException e) {
            return new CompilationResult(false, e.getMessage(), 0);
        }
    }

    /**
     * Extrae información de la clase del código fuente.
     */
    private ClassInfo extractClassInfo(String javaCode) throws ExecutionException {
        // Extraer nombre de paquete
        String packageName = "";
        String[] lines = javaCode.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("package ")) {
                packageName = line.substring(8, line.indexOf(';')).trim();
                break;
            }
        }

        // Extraer nombre de clase
        String className = null;
        for (String line : lines) {
            line = line.trim();
            if (line.contains("class ") && (line.startsWith("public") || line.startsWith("class"))) {
                int classIndex = line.indexOf("class ") + 6;
                int endIndex = line.indexOf('{', classIndex);
                if (endIndex == -1) {
                    endIndex = line.indexOf(' ', classIndex);
                }
                if (endIndex == -1) {
                    endIndex = line.length();
                }
                className = line.substring(classIndex, endIndex).trim();
                break;
            }
        }

        if (className == null) {
            throw new ExecutionException("No se pudo encontrar una clase en el código fuente");
        }

        String fullClassName = packageName.isEmpty() ? className : packageName + "." + className;

        return new ClassInfo(packageName, className, fullClassName);
    }

    /**
     * Compila el código Java en bytecode.
     */
    private CompiledClass compile(String javaCode, ClassInfo classInfo) throws ExecutionException {
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        // Crear un JavaFileObject en memoria
        JavaFileObject sourceFile = new InMemoryJavaFileObject(classInfo.className, javaCode);

        // Preparar el file manager para capturar bytecode
        InMemoryClassFileManager classFileManager = new InMemoryClassFileManager(fileManager);

        // Opciones de compilación
        List<String> options = Arrays.asList(
            "-classpath", System.getProperty("java.class.path")
        );

        // Compilar
        JavaCompiler.CompilationTask task = compiler.getTask(
            null,
            classFileManager,
            diagnostics,
            options,
            null,
            Collections.singletonList(sourceFile)
        );

        boolean success = task.call();

        if (!success) {
            StringBuilder errorMsg = new StringBuilder("Errores de compilación:\n");
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                errorMsg.append(String.format("Línea %d: %s\n",
                    diagnostic.getLineNumber(),
                    diagnostic.getMessage(Locale.getDefault())));
            }
            throw new ExecutionException(errorMsg.toString());
        }

        // Obtener el bytecode compilado
        byte[] bytecode = classFileManager.getCompiledClass(classInfo.fullClassName);

        if (bytecode == null) {
            throw new ExecutionException("No se generó bytecode para la clase");
        }

        return new CompiledClass(classInfo.fullClassName, bytecode);
    }

    /**
     * Carga la clase compilada usando un ClassLoader personalizado.
     */
    private Class<?> loadClass(CompiledClass compiledClass, ClassInfo classInfo)
            throws ExecutionException {
        try {
            InMemoryClassLoader classLoader = new InMemoryClassLoader(
                Thread.currentThread().getContextClassLoader(),
                compiledClass
            );
            return classLoader.loadClass(classInfo.fullClassName);
        } catch (ClassNotFoundException e) {
            throw new ExecutionException("No se pudo cargar la clase compilada: " + e.getMessage());
        }
    }

    /**
     * Invoca un método estático de la clase cargada.
     */
    private Object invokeMethod(Class<?> clazz, String methodName, Object... args)
            throws ExecutionException {
        try {
            Method method = null;

            if (args.length == 0) {
                // Buscar método sin parámetros
                try {
                    method = clazz.getMethod(methodName);
                } catch (NoSuchMethodException e) {
                    // Intentar con String[] args para main
                    method = clazz.getMethod(methodName, String[].class);
                    args = new Object[] { new String[0] };
                }
            } else {
                // Buscar método con parámetros - necesitamos manejar autoboxing
                method = findMatchingMethod(clazz, methodName, args);
            }

            if (method == null) {
                throw new NoSuchMethodException(methodName);
            }

            // Invocar el método (asumimos que es estático)
            return method.invoke(null, args);

        } catch (NoSuchMethodException e) {
            throw new ExecutionException("No se encontró el método '" + methodName + "': " + e.getMessage());
        } catch (Exception e) {
            throw new ExecutionException("Error al ejecutar el método: " + e.getMessage(), e);
        }
    }

    /**
     * Busca un método que coincida con los argumentos dados,
     * manejando conversiones primitivo/wrapper.
     */
    private Method findMatchingMethod(Class<?> clazz, String methodName, Object... args)
            throws NoSuchMethodException {

        // Obtener todos los métodos públicos de la clase
        for (Method method : clazz.getMethods()) {
            if (!method.getName().equals(methodName)) {
                continue;
            }

            Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes.length != args.length) {
                continue;
            }

            // Verificar si los argumentos son compatibles
            boolean compatible = true;
            for (int i = 0; i < args.length; i++) {
                if (!isCompatible(paramTypes[i], args[i])) {
                    compatible = false;
                    break;
                }
            }

            if (compatible) {
                return method;
            }
        }

        throw new NoSuchMethodException(clazz.getName() + "." + methodName);
    }

    /**
     * Verifica si un argumento es compatible con un tipo de parámetro.
     * Maneja conversiones primitivo/wrapper.
     */
    private boolean isCompatible(Class<?> paramType, Object arg) {
        if (arg == null) {
            return !paramType.isPrimitive();
        }

        Class<?> argType = arg.getClass();

        // Coincidencia exacta
        if (paramType.isAssignableFrom(argType)) {
            return true;
        }

        // Manejar autoboxing/unboxing
        if (paramType.isPrimitive()) {
            if (paramType == int.class && argType == Integer.class) return true;
            if (paramType == long.class && argType == Long.class) return true;
            if (paramType == double.class && argType == Double.class) return true;
            if (paramType == float.class && argType == Float.class) return true;
            if (paramType == boolean.class && argType == Boolean.class) return true;
            if (paramType == byte.class && argType == Byte.class) return true;
            if (paramType == short.class && argType == Short.class) return true;
            if (paramType == char.class && argType == Character.class) return true;
        } else {
            // Wrapper a primitivo
            if (argType == Integer.class && (paramType == int.class || paramType == Integer.class)) return true;
            if (argType == Long.class && (paramType == long.class || paramType == Long.class)) return true;
            if (argType == Double.class && (paramType == double.class || paramType == Double.class)) return true;
            if (argType == Float.class && (paramType == float.class || paramType == Float.class)) return true;
            if (argType == Boolean.class && (paramType == boolean.class || paramType == Boolean.class)) return true;
            if (argType == Byte.class && (paramType == byte.class || paramType == Byte.class)) return true;
            if (argType == Short.class && (paramType == short.class || paramType == Short.class)) return true;
            if (argType == Character.class && (paramType == char.class || paramType == Character.class)) return true;
        }

        return false;
    }

    // ==================== Clases de soporte ====================

    /**
     * Información de una clase Java.
     */
    private static class ClassInfo {
        final String packageName;
        final String className;
        final String fullClassName;

        ClassInfo(String packageName, String className, String fullClassName) {
            this.packageName = packageName;
            this.className = className;
            this.fullClassName = fullClassName;
        }
    }

    /**
     * Clase compilada con su bytecode.
     */
    private static class CompiledClass {
        final String className;
        final byte[] bytecode;

        CompiledClass(String className, byte[] bytecode) {
            this.className = className;
            this.bytecode = bytecode;
        }
    }

    /**
     * JavaFileObject en memoria para compilación dinámica.
     */
    private static class InMemoryJavaFileObject extends SimpleJavaFileObject {
        private final String code;

        InMemoryJavaFileObject(String className, String code) {
            super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension),
                  Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }

    /**
     * ClassLoader personalizado para cargar clases desde bytecode en memoria.
     */
    private static class InMemoryClassLoader extends ClassLoader {
        private final CompiledClass compiledClass;

        InMemoryClassLoader(ClassLoader parent, CompiledClass compiledClass) {
            super(parent);
            this.compiledClass = compiledClass;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            if (name.equals(compiledClass.className)) {
                return defineClass(name, compiledClass.bytecode, 0, compiledClass.bytecode.length);
            }
            return super.findClass(name);
        }
    }

    /**
     * File manager personalizado para capturar bytecode compilado.
     */
    private static class InMemoryClassFileManager extends ForwardingJavaFileManager<JavaFileManager> {
        private final Map<String, ByteArrayOutputStream> compiledClasses = new HashMap<>();

        InMemoryClassFileManager(JavaFileManager fileManager) {
            super(fileManager);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className,
                JavaFileObject.Kind kind, FileObject sibling) throws IOException {

            if (kind == JavaFileObject.Kind.CLASS) {
                return new InMemoryClassFileObject(className, compiledClasses);
            }
            return super.getJavaFileForOutput(location, className, kind, sibling);
        }

        byte[] getCompiledClass(String className) {
            ByteArrayOutputStream baos = compiledClasses.get(className);
            return baos != null ? baos.toByteArray() : null;
        }
    }

    /**
     * JavaFileObject para capturar bytecode compilado.
     */
    private static class InMemoryClassFileObject extends SimpleJavaFileObject {
        private final Map<String, ByteArrayOutputStream> compiledClasses;
        private final String className;

        InMemoryClassFileObject(String className, Map<String, ByteArrayOutputStream> compiledClasses) {
            super(URI.create("mem:///" + className.replace('.', '/') + Kind.CLASS.extension),
                  Kind.CLASS);
            this.className = className;
            this.compiledClasses = compiledClasses;
        }

        @Override
        public OutputStream openOutputStream() {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            compiledClasses.put(className, baos);
            return baos;
        }
    }

    /**
     * Wrapper para JavaFileManager.
     */
    private static class ForwardingJavaFileManager<M extends JavaFileManager>
            implements JavaFileManager {

        protected final M fileManager;

        ForwardingJavaFileManager(M fileManager) {
            this.fileManager = fileManager;
        }

        @Override
        public ClassLoader getClassLoader(Location location) {
            return fileManager.getClassLoader(location);
        }

        @Override
        public Iterable<JavaFileObject> list(Location location, String packageName,
                Set<JavaFileObject.Kind> kinds, boolean recurse) throws IOException {
            return fileManager.list(location, packageName, kinds, recurse);
        }

        @Override
        public String inferBinaryName(Location location, JavaFileObject file) {
            return fileManager.inferBinaryName(location, file);
        }

        @Override
        public boolean isSameFile(FileObject a, FileObject b) {
            return fileManager.isSameFile(a, b);
        }

        @Override
        public boolean handleOption(String current, Iterator<String> remaining) {
            return fileManager.handleOption(current, remaining);
        }

        @Override
        public boolean hasLocation(Location location) {
            return fileManager.hasLocation(location);
        }

        @Override
        public JavaFileObject getJavaFileForInput(Location location, String className,
                JavaFileObject.Kind kind) throws IOException {
            return fileManager.getJavaFileForInput(location, className, kind);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className,
                JavaFileObject.Kind kind, FileObject sibling) throws IOException {
            return fileManager.getJavaFileForOutput(location, className, kind, sibling);
        }

        @Override
        public FileObject getFileForInput(Location location, String packageName,
                String relativeName) throws IOException {
            return fileManager.getFileForInput(location, packageName, relativeName);
        }

        @Override
        public FileObject getFileForOutput(Location location, String packageName,
                String relativeName, FileObject sibling) throws IOException {
            return fileManager.getFileForOutput(location, packageName, relativeName, sibling);
        }

        @Override
        public void flush() throws IOException {
            fileManager.flush();
        }

        @Override
        public void close() throws IOException {
            fileManager.close();
        }

        @Override
        public int isSupportedOption(String option) {
            return fileManager.isSupportedOption(option);
        }

        @Override
        public Location getLocationForModule(Location location, String moduleName) throws IOException {
            return fileManager.getLocationForModule(location, moduleName);
        }

        @Override
        public Location getLocationForModule(Location location, JavaFileObject fo) throws IOException {
            return fileManager.getLocationForModule(location, fo);
        }

        @Override
        public <S> ServiceLoader<S> getServiceLoader(Location location, Class<S> service) throws IOException {
            return fileManager.getServiceLoader(location, service);
        }

        @Override
        public String inferModuleName(Location location) throws IOException {
            return fileManager.inferModuleName(location);
        }

        @Override
        public Iterable<Set<Location>> listLocationsForModules(Location location) throws IOException {
            return fileManager.listLocationsForModules(location);
        }

        @Override
        public boolean contains(Location location, FileObject fo) throws IOException {
            return fileManager.contains(location, fo);
        }
    }

    // ==================== Clases de resultado ====================

    /**
     * Resultado de la ejecución.
     */
    public static class ExecutionResult {
        private final boolean success;
        private final Object returnValue;
        private final String errorMessage;
        private final long executionTimeMs;

        public ExecutionResult(boolean success, Object returnValue, String errorMessage,
                             long executionTimeMs) {
            this.success = success;
            this.returnValue = returnValue;
            this.errorMessage = errorMessage;
            this.executionTimeMs = executionTimeMs;
        }

        public boolean isSuccess() {
            return success;
        }

        public Object getReturnValue() {
            return returnValue;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public long getExecutionTimeMs() {
            return executionTimeMs;
        }

        @Override
        public String toString() {
            if (success) {
                return String.format("Éxito (tiempo: %dms): %s", executionTimeMs, returnValue);
            } else {
                return String.format("Error: %s", errorMessage);
            }
        }
    }

    /**
     * Resultado de la compilación.
     */
    public static class CompilationResult {
        private final boolean success;
        private final String errorMessage;
        private final int bytecodeSize;

        public CompilationResult(boolean success, String errorMessage, int bytecodeSize) {
            this.success = success;
            this.errorMessage = errorMessage;
            this.bytecodeSize = bytecodeSize;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public int getBytecodeSize() {
            return bytecodeSize;
        }

        @Override
        public String toString() {
            if (success) {
                return String.format("Compilación exitosa (%d bytes)", bytecodeSize);
            } else {
                return String.format("Error de compilación: %s", errorMessage);
            }
        }
    }

    /**
     * Excepción para errores de ejecución.
     */
    public static class ExecutionException extends Exception {
        public ExecutionException(String message) {
            super(message);
        }

        public ExecutionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
