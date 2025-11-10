# FlowScript Code Generator - Arquitectura de Traducción a Java

## 1. VISIÓN GENERAL

### 1.1 Pipeline de Compilación
```
FlowScript Source
    ↓
[Lexer] → Tokens
    ↓
[Parser] → AST
    ↓
[Semantic Analyzer] → Validated AST + Symbol Tables
    ↓
[Code Generator] → Java Source Code
    ↓
[Java Compiler (javac)] → Bytecode ejecutable
```

### 1.2 Arquitectura del Generador
```
CodeGenerator
    ├── TypeMapper          # Mapeo de tipos FlowScript → Java
    ├── ExpressionGenerator # Traduce expresiones
    ├── FunctionGenerator   # Traduce funciones
    ├── ProcessGenerator    # Traduce procesos BPMN
    ├── RuntimeEmitter      # Genera código de runtime library
    └── JavaEmitter         # Utilidades para emitir código Java
```

---

## 2. MAPEO DE TIPOS FlowScript → Java

### 2.1 Tipos Primitivos
```
FlowScript          Java                    Wrapper
─────────────────────────────────────────────────────
entero              int                     Integer
decimal             double                  Double
booleano            boolean                 Boolean
texto               String                  String
nulo                null                    null
```

### 2.2 Tipos Compuestos
```java
// FlowScript: lista<entero>
// Java:
List<Integer>

// FlowScript: objeto
// Java:
Map<String, Object>

// FlowScript: objeto con campos específicos
// Java (generado):
class GeneratedObject {
    private String campo1;
    private Integer campo2;
    // getters/setters
}
```

### 2.3 Tipos de Función
```java
// FlowScript: funcion(entero, texto) -> booleano
// Java:
BiFunction<Integer, String, Boolean>

// Para funciones sin retorno:
Consumer<Integer>

// Para funciones sin parámetros:
Supplier<String>
```

---

## 3. TRADUCCIÓN DE FUNCTIONS

### 3.1 Declaración de Función

**FlowScript:**
```javascript
funcion calcular(a: entero, b: entero) -> entero {
    retornar a + b
}
```

**Java Generado:**
```java
public static int calcular(int a, int b) {
    return a + b;
}
```

### 3.2 Expresiones

#### 3.2.1 Operadores Binarios
```
FlowScript      Java
─────────────────────
+               +
-               -
*               *
/               /
%               %
==              ==
!=              !=
<               <
>               >
<=              <=
>=              >=
y               &&
o               ||
```

#### 3.2.2 Operadores Unarios
```java
// FlowScript: -x
// Java:
-x

// FlowScript: no x
// Java:
!x

// FlowScript: x++
// Java:
x++
```

#### 3.2.3 Operador Ternario
```java
// FlowScript: condicion ? valor1 : valor2
// Java:
condicion ? valor1 : valor2
```

#### 3.2.4 Acceso a Propiedades
```java
// FlowScript: objeto.propiedad
// Java (si objeto es Map):
((Map<String, Object>) objeto).get("propiedad")

// Java (si objeto es clase generada):
objeto.getPropiedad()
```

#### 3.2.5 Acceso a Índices
```java
// FlowScript: lista[indice]
// Java:
lista.get(indice)
```

#### 3.2.6 Llamadas a Función
```java
// FlowScript: funcion(arg1, arg2)
// Java:
funcion(arg1, arg2)
```

### 3.3 Literales

#### 3.3.1 Literales Simples
```java
// FlowScript: 42
// Java:
42

// FlowScript: 3.14
// Java:
3.14

// FlowScript: "texto"
// Java:
"texto"

// FlowScript: verdadero
// Java:
true

// FlowScript: nulo
// Java:
null
```

#### 3.3.2 Literales de Lista
```java
// FlowScript: [1, 2, 3]
// Java:
List.of(1, 2, 3)

// Para listas mutables:
new ArrayList<>(List.of(1, 2, 3))
```

#### 3.3.3 Literales de Objeto
```java
// FlowScript:
{
    nombre: "Juan",
    edad: 30,
    activo: verdadero
}

// Java:
new HashMap<String, Object>() {{
    put("nombre", "Juan");
    put("edad", 30);
    put("activo", true);
}}

// O usando Map.of (inmutable):
Map.of(
    "nombre", "Juan",
    "edad", 30,
    "activo", true
)
```

### 3.4 Control de Flujo

#### 3.4.1 If-Else
```java
// FlowScript:
si (x > 10) {
    y = 1
} sino_si (x > 5) {
    y = 2
} sino {
    y = 3
}

// Java:
if (x > 10) {
    y = 1;
} else if (x > 5) {
    y = 2;
} else {
    y = 3;
}
```

#### 3.4.2 For Loop
```java
// FlowScript:
para (i en 0..10) {
    imprimir(i)
}

// Java:
for (int i = 0; i <= 10; i++) {
    System.out.println(i);
}

// FlowScript: para elemento en lista
para (elemento en lista) {
    imprimir(elemento)
}

// Java:
for (var elemento : lista) {
    System.out.println(elemento);
}
```

#### 3.4.3 While Loop
```java
// FlowScript:
mientras (x < 10) {
    x = x + 1
}

// Java:
while (x < 10) {
    x = x + 1;
}
```

#### 3.4.4 Try-Catch
```java
// FlowScript:
intentar {
    resultado = operacionPeligrosa()
} capturar (e: Error) {
    manejarError(e)
}

// Java:
try {
    resultado = operacionPeligrosa();
} catch (Exception e) {
    manejarError(e);
}
```

### 3.5 Declaración de Variables
```java
// FlowScript: var x: entero = 10
// Java:
int x = 10;

// FlowScript: var x = 10 (inferencia)
// Java:
int x = 10;  // tipo inferido del análisis semántico

// FlowScript: const PI = 3.14159
// Java:
final double PI = 3.14159;
```

---

## 4. TRADUCCIÓN DE PROCESSES (BPMN)

### 4.1 Estructura General de un Proceso

**FlowScript:**
```javascript
proceso OrdenCompra {
    inicio -> validarOrden

    tarea validarOrden {
        // lógica
    }

    gateway decision {
        cuando (ordenValida) -> procesarPago
        sino -> rechazarOrden
    }

    tarea procesarPago {
        // lógica
    }

    tarea rechazarOrden {
        // lógica
    }

    fin
}
```

**Java Generado:**
```java
public class OrdenCompraProcess extends FlowScriptProcess {
    // Context holder
    private ProcessContext context;

    public OrdenCompraProcess() {
        this.context = new ProcessContext();
    }

    // Método principal de ejecución
    public void execute(Map<String, Object> initialVariables) {
        context.setVariables(initialVariables);
        context.setCurrentState("inicio");

        // Ejecutar desde el inicio
        inicio();
    }

    // Nodo inicio
    private void inicio() {
        context.setCurrentState("validarOrden");
        validarOrden();
    }

    // Tarea validarOrden
    private void validarOrden() {
        try {
            // Código de la tarea
            // ...

            // Siguiente nodo
            context.setCurrentState("decision");
            decision();
        } catch (Exception e) {
            handleError("validarOrden", e);
        }
    }

    // Gateway de decisión
    private void decision() {
        boolean ordenValida = (Boolean) context.getVariable("ordenValida");

        if (ordenValida) {
            context.setCurrentState("procesarPago");
            procesarPago();
        } else {
            context.setCurrentState("rechazarOrden");
            rechazarOrden();
        }
    }

    // Otras tareas...
    private void procesarPago() { /* ... */ }
    private void rechazarOrden() { /* ... */ }

    // Nodo fin
    private void fin() {
        context.setCurrentState("fin");
        context.setCompleted(true);
    }
}
```

### 4.2 ProcessContext - Contexto Compartido

```java
public class ProcessContext {
    private Map<String, Object> variables;
    private String currentState;
    private boolean completed;
    private Deque<String> stateHistory;

    public ProcessContext() {
        this.variables = new ConcurrentHashMap<>();
        this.stateHistory = new ArrayDeque<>();
        this.completed = false;
    }

    // Gestión de variables
    public Object getVariable(String name) {
        return variables.get(name);
    }

    public void setVariable(String name, Object value) {
        variables.put(name, value);
    }

    // Gestión de estado
    public void setCurrentState(String state) {
        stateHistory.push(currentState);
        this.currentState = state;
    }

    public String getCurrentState() {
        return currentState;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
```

### 4.3 Parallel Gateway - Paralelismo con Hilos

**FlowScript:**
```javascript
proceso ProcesoParalelo {
    inicio -> gateway_paralelo

    gateway paralelo {
        rama -> tarea1
        rama -> tarea2
        rama -> tarea3

        unir -> continuacion
    }

    tarea tarea1 { /* ... */ }
    tarea tarea2 { /* ... */ }
    tarea tarea3 { /* ... */ }

    tarea continuacion { /* ... */ }
}
```

**Java Generado:**
```java
public class ProcesoParaleloProcess extends FlowScriptProcess {
    private ProcessContext context;
    private ExecutorService executorService;

    public ProcesoParaleloProcess() {
        this.context = new ProcessContext();
        this.executorService = Executors.newCachedThreadPool();
    }

    private void gateway_paralelo() {
        // Crear CompletableFutures para cada rama paralela
        CompletableFuture<Void> rama1 = CompletableFuture.runAsync(
            () -> tarea1(),
            executorService
        );

        CompletableFuture<Void> rama2 = CompletableFuture.runAsync(
            () -> tarea2(),
            executorService
        );

        CompletableFuture<Void> rama3 = CompletableFuture.runAsync(
            () -> tarea3(),
            executorService
        );

        // Esperar a que todas las ramas completen (JOIN)
        CompletableFuture.allOf(rama1, rama2, rama3)
            .thenRun(() -> {
                context.setCurrentState("continuacion");
                continuacion();
            })
            .exceptionally(ex -> {
                handleError("gateway_paralelo", ex);
                return null;
            })
            .join();
    }

    private void tarea1() {
        // Código de la tarea 1
        // Cada tarea tiene su propio scope pero comparte el context
    }

    private void tarea2() { /* ... */ }
    private void tarea3() { /* ... */ }
}
```

### 4.4 Exclusive Gateway - Decisiones

**FlowScript:**
```javascript
gateway decision {
    cuando (monto > 1000) -> aprobarSupervisor
    cuando (monto > 500) -> aprobarManager
    sino -> aprobarAutomatico
}
```

**Java Generado:**
```java
private void decision() {
    double monto = (Double) context.getVariable("monto");

    if (monto > 1000) {
        context.setCurrentState("aprobarSupervisor");
        aprobarSupervisor();
    } else if (monto > 500) {
        context.setCurrentState("aprobarManager");
        aprobarManager();
    } else {
        context.setCurrentState("aprobarAutomatico");
        aprobarAutomatico();
    }
}
```

### 4.5 Navegación con go_to

**FlowScript:**
```javascript
tarea verificarStock {
    si (stock < minimo) {
        go_to reordenar
    }
    go_to continuar
}
```

**Java Generado:**
```java
private void verificarStock() {
    int stock = (Integer) context.getVariable("stock");
    int minimo = (Integer) context.getVariable("minimo");

    if (stock < minimo) {
        context.setCurrentState("reordenar");
        reordenar();
        return;
    }

    context.setCurrentState("continuar");
    continuar();
}
```

---

## 5. RUNTIME LIBRARY

### 5.1 Clase Base para Procesos

```java
package com.flowscript.runtime;

public abstract class FlowScriptProcess {
    protected ProcessContext context;
    protected FlowScriptRuntime runtime;

    public FlowScriptProcess() {
        this.context = new ProcessContext();
        this.runtime = FlowScriptRuntime.getInstance();
    }

    // Método abstracto para iniciar el proceso
    public abstract void execute(Map<String, Object> initialVariables);

    // Manejo de errores
    protected void handleError(String taskName, Throwable error) {
        runtime.logError(taskName, error);
        context.setVariable("__error__", error);
        context.setVariable("__errorTask__", taskName);
    }

    // Utilidades
    protected void log(String message) {
        runtime.log(message);
    }

    protected void cleanup() {
        runtime.cleanup();
    }
}
```

### 5.2 FlowScriptRuntime - Singleton para Recursos

```java
package com.flowscript.runtime;

public class FlowScriptRuntime {
    private static FlowScriptRuntime instance;

    private HttpClient httpClient;
    private DataSource dataSource;
    private ExecutorService executorService;

    private FlowScriptRuntime() {
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .build();
        this.executorService = Executors.newCachedThreadPool();
    }

    public static synchronized FlowScriptRuntime getInstance() {
        if (instance == null) {
            instance = new FlowScriptRuntime();
        }
        return instance;
    }

    // HTTP Methods
    public String httpGet(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        HttpResponse<String> response = httpClient.send(
            request,
            HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }

    public String httpPost(String url, String body) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        HttpResponse<String> response = httpClient.send(
            request,
            HttpResponse.BodyHandlers.ofString()
        );

        return response.body();
    }

    // Database Methods
    public void configureDataSource(String url, String user, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(10);

        this.dataSource = new HikariDataSource(config);
    }

    public List<Map<String, Object>> executeQuery(String sql, Object... params)
            throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            // Execute query
            try (ResultSet rs = stmt.executeQuery()) {
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(meta.getColumnName(i), rs.getObject(i));
                    }
                    results.add(row);
                }
            }
        }

        return results;
    }

    public int executeUpdate(String sql, Object... params) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            return stmt.executeUpdate();
        }
    }

    // Executor Service
    public ExecutorService getExecutorService() {
        return executorService;
    }

    // Logging
    public void log(String message) {
        System.out.println("[FlowScript] " + message);
    }

    public void logError(String context, Throwable error) {
        System.err.println("[FlowScript ERROR] " + context + ": " + error.getMessage());
        error.printStackTrace();
    }

    // Cleanup
    public void cleanup() {
        if (dataSource != null && dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
        }
        executorService.shutdown();
    }
}
```

### 5.3 Funciones Built-in

```java
package com.flowscript.runtime;

public class FlowScriptBuiltins {
    // I/O
    public static void imprimir(Object value) {
        System.out.println(value);
    }

    public static String leer() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    // String operations
    public static int longitud(String str) {
        return str.length();
    }

    public static String subcadena(String str, int inicio, int fin) {
        return str.substring(inicio, fin);
    }

    public static boolean contiene(String str, String sub) {
        return str.contains(sub);
    }

    // List operations
    public static <T> void agregar(List<T> lista, T elemento) {
        lista.add(elemento);
    }

    public static <T> void remover(List<T> lista, int indice) {
        lista.remove(indice);
    }

    public static <T> int tamano(List<T> lista) {
        return lista.size();
    }

    // HTTP shortcuts
    public static String httpGet(String url) throws Exception {
        return FlowScriptRuntime.getInstance().httpGet(url);
    }

    public static String httpPost(String url, String body) throws Exception {
        return FlowScriptRuntime.getInstance().httpPost(url, body);
    }

    // Database shortcuts
    public static List<Map<String, Object>> consultar(String sql, Object... params)
            throws SQLException {
        return FlowScriptRuntime.getInstance().executeQuery(sql, params);
    }

    public static int ejecutar(String sql, Object... params) throws SQLException {
        return FlowScriptRuntime.getInstance().executeUpdate(sql, params);
    }

    // JSON parsing
    public static Map<String, Object> parseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Map.class);
    }

    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
```

---

## 6. EJEMPLOS COMPLETOS DE TRADUCCIÓN

### 6.1 Función Simple con HTTP

**FlowScript:**
```javascript
funcion obtenerUsuario(id: entero) -> objeto {
    var url = "https://api.ejemplo.com/usuarios/" + id
    var respuesta = httpGet(url)
    var usuario = parseJson(respuesta)
    retornar usuario
}
```

**Java Generado:**
```java
import com.flowscript.runtime.*;
import java.util.*;

public class GeneratedCode {
    public static Map<String, Object> obtenerUsuario(int id) {
        String url = "https://api.ejemplo.com/usuarios/" + id;
        String respuesta = FlowScriptBuiltins.httpGet(url);
        Map<String, Object> usuario = FlowScriptBuiltins.parseJson(respuesta);
        return usuario;
    }
}
```

### 6.2 Función con Consulta a BD

**FlowScript:**
```javascript
funcion buscarProductos(categoria: texto) -> lista<objeto> {
    var sql = "SELECT * FROM productos WHERE categoria = ?"
    var resultados = consultar(sql, categoria)
    retornar resultados
}
```

**Java Generado:**
```java
public static List<Map<String, Object>> buscarProductos(String categoria)
        throws SQLException {
    String sql = "SELECT * FROM productos WHERE categoria = ?";
    List<Map<String, Object>> resultados = FlowScriptBuiltins.consultar(sql, categoria);
    return resultados;
}
```

### 6.3 Proceso Completo con HTTP, BD y Paralelismo

**FlowScript:**
```javascript
proceso ProcesarOrden {
    inicio -> validarStock

    tarea validarStock {
        var productos = consultar("SELECT * FROM productos WHERE id = ?", ordenId)
        var stock = productos[0].stock

        si (stock > 0) {
            context.stockDisponible = verdadero
        } sino {
            context.stockDisponible = falso
        }
    }

    gateway decision {
        cuando (context.stockDisponible) -> procesamientoParalelo
        sino -> notificarSinStock
    }

    gateway procesamientoParalelo {
        rama -> procesarPago
        rama -> actualizarInventario
        rama -> notificarCliente

        unir -> finalizarOrden
    }

    tarea procesarPago {
        var respuesta = httpPost(
            "https://pagos.ejemplo.com/procesar",
            toJson({
                ordenId: context.ordenId,
                monto: context.monto
            })
        )
        context.pagoExitoso = parseJson(respuesta).success
    }

    tarea actualizarInventario {
        ejecutar(
            "UPDATE productos SET stock = stock - 1 WHERE id = ?",
            context.productoId
        )
    }

    tarea notificarCliente {
        httpPost(
            "https://notificaciones.ejemplo.com/enviar",
            toJson({
                email: context.email,
                mensaje: "Su orden está siendo procesada"
            })
        )
    }

    tarea finalizarOrden {
        ejecutar(
            "UPDATE ordenes SET estado = 'completada' WHERE id = ?",
            context.ordenId
        )
    }

    tarea notificarSinStock {
        httpPost(
            "https://notificaciones.ejemplo.com/enviar",
            toJson({
                email: context.email,
                mensaje: "Producto sin stock"
            })
        )
    }

    fin
}
```

**Java Generado:**
```java
import com.flowscript.runtime.*;
import java.util.*;
import java.util.concurrent.*;

public class ProcesarOrdenProcess extends FlowScriptProcess {

    public ProcesarOrdenProcess() {
        super();
    }

    @Override
    public void execute(Map<String, Object> initialVariables) {
        context.setVariables(initialVariables);
        context.setCurrentState("inicio");
        inicio();
    }

    private void inicio() {
        context.setCurrentState("validarStock");
        validarStock();
    }

    private void validarStock() {
        try {
            int ordenId = (Integer) context.getVariable("ordenId");
            List<Map<String, Object>> productos = FlowScriptBuiltins.consultar(
                "SELECT * FROM productos WHERE id = ?",
                ordenId
            );
            int stock = (Integer) productos.get(0).get("stock");

            if (stock > 0) {
                context.setVariable("stockDisponible", true);
            } else {
                context.setVariable("stockDisponible", false);
            }

            context.setCurrentState("decision");
            decision();
        } catch (Exception e) {
            handleError("validarStock", e);
        }
    }

    private void decision() {
        boolean stockDisponible = (Boolean) context.getVariable("stockDisponible");

        if (stockDisponible) {
            context.setCurrentState("procesamientoParalelo");
            procesamientoParalelo();
        } else {
            context.setCurrentState("notificarSinStock");
            notificarSinStock();
        }
    }

    private void procesamientoParalelo() {
        ExecutorService executor = runtime.getExecutorService();

        CompletableFuture<Void> rama1 = CompletableFuture.runAsync(
            () -> procesarPago(),
            executor
        );

        CompletableFuture<Void> rama2 = CompletableFuture.runAsync(
            () -> actualizarInventario(),
            executor
        );

        CompletableFuture<Void> rama3 = CompletableFuture.runAsync(
            () -> notificarCliente(),
            executor
        );

        CompletableFuture.allOf(rama1, rama2, rama3)
            .thenRun(() -> {
                context.setCurrentState("finalizarOrden");
                finalizarOrden();
            })
            .exceptionally(ex -> {
                handleError("procesamientoParalelo", ex);
                return null;
            })
            .join();
    }

    private void procesarPago() {
        try {
            int ordenId = (Integer) context.getVariable("ordenId");
            double monto = (Double) context.getVariable("monto");

            Map<String, Object> payload = Map.of(
                "ordenId", ordenId,
                "monto", monto
            );

            String respuesta = FlowScriptBuiltins.httpPost(
                "https://pagos.ejemplo.com/procesar",
                FlowScriptBuiltins.toJson(payload)
            );

            Map<String, Object> resultado = FlowScriptBuiltins.parseJson(respuesta);
            context.setVariable("pagoExitoso", resultado.get("success"));
        } catch (Exception e) {
            handleError("procesarPago", e);
        }
    }

    private void actualizarInventario() {
        try {
            int productoId = (Integer) context.getVariable("productoId");
            FlowScriptBuiltins.ejecutar(
                "UPDATE productos SET stock = stock - 1 WHERE id = ?",
                productoId
            );
        } catch (Exception e) {
            handleError("actualizarInventario", e);
        }
    }

    private void notificarCliente() {
        try {
            String email = (String) context.getVariable("email");

            Map<String, Object> payload = Map.of(
                "email", email,
                "mensaje", "Su orden está siendo procesada"
            );

            FlowScriptBuiltins.httpPost(
                "https://notificaciones.ejemplo.com/enviar",
                FlowScriptBuiltins.toJson(payload)
            );
        } catch (Exception e) {
            handleError("notificarCliente", e);
        }
    }

    private void finalizarOrden() {
        try {
            int ordenId = (Integer) context.getVariable("ordenId");
            FlowScriptBuiltins.ejecutar(
                "UPDATE ordenes SET estado = 'completada' WHERE id = ?",
                ordenId
            );

            context.setCurrentState("fin");
            fin();
        } catch (Exception e) {
            handleError("finalizarOrden", e);
        }
    }

    private void notificarSinStock() {
        try {
            String email = (String) context.getVariable("email");

            Map<String, Object> payload = Map.of(
                "email", email,
                "mensaje", "Producto sin stock"
            );

            FlowScriptBuiltins.httpPost(
                "https://notificaciones.ejemplo.com/enviar",
                FlowScriptBuiltins.toJson(payload)
            );

            context.setCurrentState("fin");
            fin();
        } catch (Exception e) {
            handleError("notificarSinStock", e);
        }
    }

    private void fin() {
        context.setCurrentState("fin");
        context.setCompleted(true);
    }
}
```

---

## 7. ARQUITECTURA DE IMPLEMENTACIÓN

### 7.1 Estructura de Paquetes

```
com.flowscript.codegen
├── CodeGenerator.java          # Orquestador principal
├── JavaEmitter.java            # Generación de código Java
├── types/
│   ├── TypeMapper.java         # Mapeo de tipos
│   └── TypeResolver.java       # Resolución de tipos
├── generators/
│   ├── FunctionGenerator.java  # Genera métodos Java
│   ├── ProcessGenerator.java   # Genera clases de proceso
│   ├── ExpressionGenerator.java # Genera expresiones
│   └── StatementGenerator.java  # Genera statements
├── visitors/
│   ├── CodeGenVisitor.java     # Visitor del AST
│   └── TypeVisitor.java        # Extrae información de tipos
└── templates/
    ├── ProcessTemplate.java    # Template para procesos
    └── RuntimeTemplate.java    # Template para runtime
```

### 7.2 Flujo de Generación

```
1. AST + Symbol Tables
   ↓
2. CodeGenVisitor recorre el AST
   ↓
3. Para cada nodo:
   - FunctionDeclaration → FunctionGenerator
   - ProcessDeclaration → ProcessGenerator
   - Expression → ExpressionGenerator
   - Statement → StatementGenerator
   ↓
4. Cada generator:
   - Consulta TypeMapper para tipos
   - Usa JavaEmitter para construir código
   - Maneja imports y dependencias
   ↓
5. JavaEmitter acumula:
   - Imports
   - Declaraciones de clase
   - Métodos
   - Código auxiliar
   ↓
6. Output: Archivo .java completo
```

### 7.3 CodeGenerator - Clase Principal

```java
package com.flowscript.codegen;

public class CodeGenerator {
    private TypeMapper typeMapper;
    private FunctionGenerator functionGenerator;
    private ProcessGenerator processGenerator;
    private JavaEmitter emitter;

    public CodeGenerator() {
        this.typeMapper = new TypeMapper();
        this.emitter = new JavaEmitter();
        this.functionGenerator = new FunctionGenerator(typeMapper, emitter);
        this.processGenerator = new ProcessGenerator(typeMapper, emitter);
    }

    public String generateJavaCode(ProgramNode ast, SymbolTable symbolTable) {
        emitter.reset();

        // Add package and imports
        emitter.addPackage("com.flowscript.generated");
        emitter.addImport("com.flowscript.runtime.*");
        emitter.addImport("java.util.*");
        emitter.addImport("java.util.concurrent.*");

        // Generate class
        emitter.beginClass("GeneratedProgram", null);

        // Process imports
        for (ImportDeclarationNode importNode : ast.getImports()) {
            processImport(importNode);
        }

        // Generate functions
        for (FunctionDeclarationNode funcNode : ast.getFunctions()) {
            String methodCode = functionGenerator.generate(funcNode);
            emitter.addMethod(methodCode);
        }

        // Generate processes
        for (ProcessDeclarationNode procNode : ast.getProcesses()) {
            String classCode = processGenerator.generate(procNode);
            emitter.addInnerClass(classCode);
        }

        emitter.endClass();

        return emitter.getGeneratedCode();
    }

    private void processImport(ImportDeclarationNode importNode) {
        // Add Java imports based on FlowScript imports
        emitter.addImport(importNode.getModulePath());
    }
}
```

### 7.4 JavaEmitter - Constructor de Código

```java
public class JavaEmitter {
    private StringBuilder code;
    private int indentLevel;
    private Set<String> imports;
    private String packageName;

    public JavaEmitter() {
        this.code = new StringBuilder();
        this.imports = new LinkedHashSet<>();
        this.indentLevel = 0;
    }

    public void reset() {
        code.setLength(0);
        imports.clear();
        indentLevel = 0;
    }

    public void addPackage(String pkg) {
        this.packageName = pkg;
    }

    public void addImport(String imp) {
        imports.add(imp);
    }

    public void beginClass(String name, String superClass) {
        String extendsClause = superClass != null ? " extends " + superClass : "";
        emit("public class " + name + extendsClause + " {");
        indent();
    }

    public void endClass() {
        dedent();
        emit("}");
    }

    public void addMethod(String methodCode) {
        emit(methodCode);
    }

    public void emit(String line) {
        code.append("    ".repeat(indentLevel)).append(line).append("\n");
    }

    public void indent() {
        indentLevel++;
    }

    public void dedent() {
        indentLevel--;
    }

    public String getGeneratedCode() {
        StringBuilder result = new StringBuilder();

        // Package
        if (packageName != null) {
            result.append("package ").append(packageName).append(";\n\n");
        }

        // Imports
        for (String imp : imports) {
            result.append("import ").append(imp).append(";\n");
        }
        result.append("\n");

        // Code
        result.append(code);

        return result.toString();
    }
}
```

---

## 8. CONSIDERACIONES ESPECIALES

### 8.1 Manejo de Errores

```java
// Los procesos deben tener manejo robusto de errores
protected void handleError(String taskName, Throwable error) {
    runtime.logError(taskName, error);

    // Guardar error en contexto
    context.setVariable("__error__", error);
    context.setVariable("__errorTask__", taskName);

    // Si hay un handler definido, llamarlo
    if (hasErrorHandler(taskName)) {
        invokeErrorHandler(taskName, error);
    } else {
        // Propagar la excepción
        throw new FlowScriptRuntimeException(
            "Error en tarea: " + taskName,
            error
        );
    }
}
```

### 8.2 Thread Safety en Contexto Compartido

```java
// El ProcessContext debe ser thread-safe para parallel gateways
public class ProcessContext {
    private ConcurrentHashMap<String, Object> variables;
    private AtomicReference<String> currentState;

    // Operaciones atómicas
    public void setVariable(String name, Object value) {
        variables.put(name, value);
    }

    public Object computeIfAbsent(String key, Function<String, Object> supplier) {
        return variables.computeIfAbsent(key, supplier);
    }
}
```

### 8.3 Pool de Conexiones a BD

```java
// Usar HikariCP para manejo eficiente de conexiones
private void configureDataSource(String url, String user, String password) {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(url);
    config.setUsername(user);
    config.setPassword(password);
    config.setMaximumPoolSize(10);
    config.setMinimumIdle(2);
    config.setConnectionTimeout(30000);
    config.setIdleTimeout(600000);

    this.dataSource = new HikariDataSource(config);
}
```

### 8.4 Timeout para HTTP y Tareas Paralelas

```java
// Configurar timeouts para evitar bloqueos
private HttpClient createHttpClient() {
    return HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(30))
        .build();
}

// En parallel gateways, timeout para join
CompletableFuture.allOf(rama1, rama2, rama3)
    .orTimeout(60, TimeUnit.SECONDS)  // Timeout de 60 segundos
    .thenRun(() -> continuacion())
    .exceptionally(ex -> {
        if (ex instanceof TimeoutException) {
            handleTimeout("gateway_paralelo");
        }
        return null;
    });
```

### 8.5 Logging y Observabilidad

```java
// Cada tarea debe loggear su ejecución
private void tarea1() {
    long startTime = System.currentTimeMillis();
    runtime.log("Iniciando tarea: tarea1");

    try {
        // Código de la tarea
        runtime.log("Tarea tarea1 completada en " +
                   (System.currentTimeMillis() - startTime) + "ms");
    } catch (Exception e) {
        runtime.logError("tarea1", e);
        throw e;
    }
}
```

---

## 9. DEPENDENCIAS MAVEN

```xml
<dependencies>
    <!-- Runtime core -->
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>

    <!-- HTTP client (Java 11+) -->
    <!-- java.net.http incluido en JDK -->

    <!-- Database connection pool -->
    <dependency>
        <groupId>com.zaxxer</groupId>
        <artifactId>HikariCP</artifactId>
        <version>5.0.1</version>
    </dependency>

    <!-- JDBC driver (ejemplo: PostgreSQL) -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.6.0</version>
    </dependency>

    <!-- Logging -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>2.0.9</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.4.11</version>
    </dependency>
</dependencies>
```

---

## 10. ROADMAP DE IMPLEMENTACIÓN

### Fase 1: Fundamentos (2-3 semanas)
- [ ] Implementar TypeMapper
- [ ] Implementar JavaEmitter
- [ ] Implementar ExpressionGenerator (expresiones simples)
- [ ] Pruebas básicas de generación

### Fase 2: Functions (2 semanas)
- [ ] Implementar FunctionGenerator
- [ ] Generar funciones con parámetros y retornos
- [ ] Traducir control de flujo (if, loops)
- [ ] Manejo de variables locales

### Fase 3: Runtime Library (2 semanas)
- [ ] Implementar FlowScriptRuntime
- [ ] Implementar FlowScriptBuiltins
- [ ] Integración HTTP con HttpClient
- [ ] Integración JDBC con HikariCP

### Fase 4: Processes Básicos (3 semanas)
- [ ] Implementar ProcessGenerator
- [ ] Generar ProcessContext
- [ ] Traducir tareas simples
- [ ] Traducir exclusive gateways
- [ ] Navegación con go_to

### Fase 5: Paralelismo (2-3 semanas)
- [ ] Implementar parallel gateways
- [ ] CompletableFuture para ramas paralelas
- [ ] Join de ramas paralelas
- [ ] Thread safety en contexto compartido
- [ ] Manejo de timeouts

### Fase 6: Integración y Testing (2 semanas)
- [ ] Tests end-to-end
- [ ] Ejemplos completos
- [ ] Optimizaciones
- [ ] Documentación

**Total estimado: 13-16 semanas**

---

## 11. PRÓXIMOS PASOS

1. **Revisar y validar este diseño**
2. **Crear estructura de paquetes**: `com.flowscript.codegen`
3. **Implementar TypeMapper** (fundamental para todo)
4. **Implementar JavaEmitter** (generación de código)
5. **Comenzar con ExpressionGenerator** (más simple)
6. **Extender a FunctionGenerator**
7. **Implementar Runtime Library**
8. **Finalmente ProcessGenerator** (más complejo)

---

## CONCLUSIÓN

Este diseño proporciona una arquitectura completa y escalable para traducir FlowScript a Java ejecutable. Los puntos clave son:

1. **Mapeo claro de tipos**: FlowScript → Java
2. **Generación modular**: Generators especializados por tipo de nodo
3. **Runtime robusto**: HTTP, JDBC, paralelismo
4. **Paralelismo real**: CompletableFuture + ExecutorService
5. **Thread safety**: ConcurrentHashMap para contexto compartido
6. **Manejo de errores**: Try-catch en cada tarea
7. **Observabilidad**: Logging de ejecución

El código generado es Java idiomático, eficiente y listo para producción.
