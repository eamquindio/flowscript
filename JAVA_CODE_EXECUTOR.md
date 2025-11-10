# JavaCodeExecutor

Componente para compilar y ejecutar código Java dinámicamente en tiempo de ejecución.

## Características

- ✅ **Compilación en memoria**: Compila código Java sin crear archivos .class en disco
- ✅ **Ejecución dinámica**: Ejecuta métodos estáticos de clases compiladas dinámicamente
- ✅ **Soporte para primitivos y wrappers**: Maneja autoboxing/unboxing automáticamente
- ✅ **Medición de rendimiento**: Reporta tiempo de compilación y ejecución
- ✅ **Mensajes de error claros**: Reporta errores de compilación con número de línea
- ✅ **Integración con FlowScript**: Compatible con código generado desde FlowScript

## Uso Básico

### 1. Compilar código Java

```java
JavaCodeExecutor executor = new JavaCodeExecutor();

String javaCode = """
    package com.example;

    public class Calculator {
        public static int add(int a, int b) {
            return a + b;
        }
    }
    """;

// Solo compilar (sin ejecutar)
CompilationResult result = executor.compile(javaCode);

if (result.isSuccess()) {
    System.out.println("Compilado exitosamente!");
    System.out.println("Tamaño: " + result.getBytecodeSize() + " bytes");
} else {
    System.out.println("Error: " + result.getErrorMessage());
}
```

### 2. Compilar y ejecutar

```java
JavaCodeExecutor executor = new JavaCodeExecutor();

String javaCode = """
    package com.example;

    public class Math {
        public static int multiply(int a, int b) {
            return a * b;
        }
    }
    """;

// Compilar y ejecutar método 'multiply' con argumentos (5, 3)
ExecutionResult result = executor.execute(javaCode, "multiply", 5, 3);

if (result.isSuccess()) {
    System.out.println("Resultado: " + result.getReturnValue()); // 15
    System.out.println("Tiempo: " + result.getExecutionTimeMs() + "ms");
}
```

### 3. Integración con FlowScript

```java
// 1. Código FlowScript
String flowScriptCode = """
    funcion suma(a: entero, b: entero) -> entero {
        retornar a + b
    }
    """;

// 2. Generar código Java
Lexer lexer = new Lexer(flowScriptCode);
List<Token> tokens = lexer.tokenize();
Parser parser = new Parser();
ProgramNode program = parser.parse(tokens);
CodeGenerator generator = new CodeGenerator();
String javaCode = generator.generate(program);

// 3. Compilar y ejecutar
JavaCodeExecutor executor = new JavaCodeExecutor();
CompilationResult result = executor.compile(javaCode);

if (result.isSuccess()) {
    System.out.println("FlowScript compilado exitosamente!");
}
```

## API

### JavaCodeExecutor

#### Métodos principales

```java
// Compilar código sin ejecutar
CompilationResult compile(String javaCode)

// Compilar y ejecutar método main()
ExecutionResult execute(String javaCode)

// Compilar y ejecutar método específico con argumentos
ExecutionResult execute(String javaCode, String methodName, Object... args)
```

### CompilationResult

```java
boolean isSuccess()           // ¿Compiló exitosamente?
String getErrorMessage()      // Mensaje de error (si falló)
int getBytecodeSize()         // Tamaño del bytecode en bytes
```

### ExecutionResult

```java
boolean isSuccess()           // ¿Ejecutó exitosamente?
Object getReturnValue()       // Valor retornado por el método
String getErrorMessage()      // Mensaje de error (si falló)
long getExecutionTimeMs()     // Tiempo de ejecución en milisegundos
```

## Ejemplos Avanzados

### Ejecutar código con tipos primitivos

```java
String code = """
    package com.test;

    public class Calc {
        public static double square(double x) {
            return x * x;
        }
    }
    """;

ExecutionResult result = executor.execute(code, "square", 5.0);
System.out.println(result.getReturnValue()); // 25.0
```

### Manejar errores de compilación

```java
String code = """
    package com.test;

    public class Broken {
        public static void test() {
            int x = "string"; // Error de tipo
        }
    }
    """;

CompilationResult result = executor.compile(code);

if (!result.isSuccess()) {
    System.out.println("Errores de compilación:");
    System.out.println(result.getErrorMessage());
}
```

Salida:
```
Errores de compilación:
Línea 5: incompatible types: java.lang.String cannot be converted to int
```

### Pipeline completo FlowScript → Java → Bytecode

```java
// Pipeline completo
String flowScript = """
    funcion factorial(n: entero) -> entero {
        resultado = 1
        para i desde 1 hasta n {
            resultado = resultado * i
        }
        retornar resultado
    }
    """;

// Lexer → Parser → CodeGen → Compile
Lexer lexer = new Lexer(flowScript);
Parser parser = new Parser();
CodeGenerator codegen = new CodeGenerator();
JavaCodeExecutor executor = new JavaCodeExecutor();

List<Token> tokens = lexer.tokenize();
ProgramNode ast = parser.parse(tokens);
String javaCode = codegen.generate(ast);
CompilationResult result = executor.compile(javaCode);

System.out.println("Pipeline: FlowScript → Java → Bytecode");
System.out.println("Estado: " + (result.isSuccess() ? "✓ Éxito" : "✗ Error"));
```

## Requisitos

- **JDK** (no JRE): Se requiere el compilador de Java (`javac`)
- **Java 17+**: Para soporte de text blocks y características modernas
- **Maven**: Para gestión de dependencias

## Limitaciones Conocidas

1. **Solo métodos estáticos**: Actualmente solo soporta ejecución de métodos `static`
2. **Tipos primitivos**: Maneja autoboxing pero puede haber casos edge
3. **Generador de código**: El `CodeGenerator` de FlowScript tiene limitaciones con:
   - Inferencia de tipos en variables locales
   - Redeclaración de variables en bloques anidados
   - Algunas estructuras de control complejas

## Arquitectura Interna

### Flujo de compilación

```
┌─────────────────┐
│  Código Java    │ (String)
│  (source code)  │
└────────┬────────┘
         │
         ▼
┌─────────────────────┐
│ JavaCompiler        │ (javax.tools)
│ - Parse             │
│ - Type Check        │
│ - Generate Bytecode │
└────────┬────────────┘
         │
         ▼
┌─────────────────┐
│  Bytecode       │ (byte[])
│  (.class data)  │
└────────┬────────┘
         │
         ▼
┌─────────────────────┐
│ InMemoryClassLoader │
│ - Load class        │
│ - Link              │
└────────┬────────────┘
         │
         ▼
┌─────────────────┐
│ Class<?> object │
│ (ready to use)  │
└─────────────────┘
```

### Componentes clave

- **InMemoryJavaFileObject**: Representa código fuente Java en memoria
- **InMemoryClassFileManager**: Captura bytecode compilado sin escribir a disco
- **InMemoryClassLoader**: Carga clases desde bytecode en memoria
- **ForwardingJavaFileManager**: Wrapper para delegar operaciones del file manager

## Testing

Ejecutar tests:

```bash
mvn test -Dtest=JavaCodeExecutorTest
```

Tests incluidos:
- ✅ Compilación de código simple
- ✅ Ejecución con argumentos
- ✅ Manejo de errores de compilación
- ✅ Integración con FlowScript
- ✅ Autoboxing primitivos/wrappers
- ✅ Medición de rendimiento

## Ejemplo Completo

Ver `com.flowscript.examples.JavaCodeExecutorExample` para ejemplos completos y ejecutables.

Ejecutar:
```bash
mvn compile
java -cp target/classes:$(mvn dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout) \
  com.flowscript.examples.JavaCodeExecutorExample
```

## Recursos

- [Java Compiler API](https://docs.oracle.com/en/java/javase/17/docs/api/java.compiler/javax/tools/JavaCompiler.html)
- [Dynamic Class Loading](https://docs.oracle.com/javase/tutorial/ext/basics/load.html)
- [FlowScript Documentation](README.md)

## Autor

Proyecto FlowScript - Compilador y Runtime
