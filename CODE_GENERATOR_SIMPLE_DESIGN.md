# FlowScript → Java: Diseño Minimalista (Semántica Preservada)

## PRINCIPIOS FUNDAMENTALES

1. **Un proceso = Un método** (no clases complejas)
2. **Flujo secuencial = Código secuencial** (legible de arriba a abajo)
3. **Variables del proceso = Variables locales** (no wrappers complejos)
4. **Comentarios estructurados = Nodos BPMN** (autoexplicativo)
5. **Zero boilerplate innecesario**

---

## ¿QUÉ ES SEMÁNTICAMENTE UN PROCESO BPMN?

Un proceso BPMN expresa:

```
1. Secuencia de pasos (tareas)
2. Decisiones condicionales (exclusive gateway)
3. Ejecución paralela + sincronización (parallel gateway + join)
4. Contexto compartido (variables)
5. Navegación explícita (go_to) [casos especiales]
```

En Java, esto se mapea naturalmente a:

```java
1. Bloques de código secuenciales
2. if/else
3. CompletableFuture.allOf(...).join()
4. Variables locales
5. State machine (solo si hay go_to)
```

---

## TRADUCCIÓN DE FUNCTIONS

### Regla: Una función → Un método static

**FlowScript:**
```javascript
funcion calcular(a: entero, b: entero) -> entero {
    var resultado = a + b
    retornar resultado
}
```

**Java:**
```java
public static int calcular(int a, int b) {
    int resultado = a + b;
    return resultado;
}
```

**Sin complejidad. Directo. Idiomático.**

---

## TRADUCCIÓN DE PROCESSES

### CASO 1: Proceso Lineal Simple (SIN go_to)

**FlowScript:**
```javascript
proceso ValidarUsuario {
    inicio -> buscarUsuario

    tarea buscarUsuario {
        var usuario = consultar("SELECT * FROM usuarios WHERE id = ?", userId)
        si (usuario.activo) {
            context.usuarioValido = verdadero
        } sino {
            context.usuarioValido = falso
        }
    }

    gateway decision {
        cuando (context.usuarioValido) -> permitirAcceso
        sino -> rechazarAcceso
    }

    tarea permitirAcceso {
        imprimir("Acceso permitido")
    }

    tarea rechazarAcceso {
        imprimir("Acceso denegado")
    }

    fin
}
```

**Java Generado (SIMPLE):**
```java
public static void validarUsuario(int userId) {
    // === INICIO ===

    // === TAREA: buscarUsuario ===
    List<Map<String, Object>> usuario = FlowScriptBuiltins.consultar(
        "SELECT * FROM usuarios WHERE id = ?",
        userId
    );
    boolean usuarioValido;
    if ((Boolean) usuario.get(0).get("activo")) {
        usuarioValido = true;
    } else {
        usuarioValido = false;
    }

    // === GATEWAY: decision ===
    if (usuarioValido) {
        // === TAREA: permitirAcceso ===
        FlowScriptBuiltins.imprimir("Acceso permitido");
    } else {
        // === TAREA: rechazarAcceso ===
        FlowScriptBuiltins.imprimir("Acceso denegado");
    }

    // === FIN ===
}
```

**Análisis:**
- ✅ **Un método, un proceso**: Toda la lógica en un solo método
- ✅ **Flujo evidente**: Se lee de arriba a abajo
- ✅ **Variables locales**: No hay ProcessContext, solo `usuarioValido`
- ✅ **Comentarios estructurados**: Cada nodo BPMN es explícito
- ✅ **Zero boilerplate**: No hay clases, no hay métodos privados, no hay estado oculto

---

### CASO 2: Proceso con Paralelismo (Parallel Gateway)

**FlowScript:**
```javascript
proceso ProcesarOrden {
    inicio -> validarStock

    tarea validarStock {
        var productos = consultar("SELECT stock FROM productos WHERE id = ?", productoId)
        context.stockDisponible = productos[0].stock > 0
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
        httpPost("https://api.pagos.com/procesar",
                 toJson({ordenId: context.ordenId, monto: context.monto}))
    }

    tarea actualizarInventario {
        ejecutar("UPDATE productos SET stock = stock - 1 WHERE id = ?", productoId)
    }

    tarea notificarCliente {
        httpPost("https://api.notif.com/enviar",
                 toJson({email: context.email, mensaje: "Orden procesada"}))
    }

    tarea finalizarOrden {
        ejecutar("UPDATE ordenes SET estado = 'completada' WHERE id = ?", ordenId)
    }

    tarea notificarSinStock {
        httpPost("https://api.notif.com/enviar",
                 toJson({email: context.email, mensaje: "Sin stock"}))
    }

    fin
}
```

**Java Generado (SIMPLE):**
```java
public static void procesarOrden(int ordenId, int productoId, String email, double monto) {
    // === INICIO ===

    // === TAREA: validarStock ===
    List<Map<String, Object>> productos = FlowScriptBuiltins.consultar(
        "SELECT stock FROM productos WHERE id = ?",
        productoId
    );
    boolean stockDisponible = (Integer) productos.get(0).get("stock") > 0;

    // === GATEWAY: decision ===
    if (stockDisponible) {

        // === PARALLEL GATEWAY: procesamientoParalelo ===
        CompletableFuture<Void> procesarPago = CompletableFuture.runAsync(() -> {
            // === TAREA: procesarPago ===
            try {
                FlowScriptBuiltins.httpPost(
                    "https://api.pagos.com/procesar",
                    FlowScriptBuiltins.toJson(Map.of(
                        "ordenId", ordenId,
                        "monto", monto
                    ))
                );
            } catch (Exception e) {
                throw new RuntimeException("Error en procesarPago", e);
            }
        });

        CompletableFuture<Void> actualizarInventario = CompletableFuture.runAsync(() -> {
            // === TAREA: actualizarInventario ===
            try {
                FlowScriptBuiltins.ejecutar(
                    "UPDATE productos SET stock = stock - 1 WHERE id = ?",
                    productoId
                );
            } catch (Exception e) {
                throw new RuntimeException("Error en actualizarInventario", e);
            }
        });

        CompletableFuture<Void> notificarCliente = CompletableFuture.runAsync(() -> {
            // === TAREA: notificarCliente ===
            try {
                FlowScriptBuiltins.httpPost(
                    "https://api.notif.com/enviar",
                    FlowScriptBuiltins.toJson(Map.of(
                        "email", email,
                        "mensaje", "Orden procesada"
                    ))
                );
            } catch (Exception e) {
                throw new RuntimeException("Error en notificarCliente", e);
            }
        });

        // === JOIN: Esperar todas las ramas paralelas ===
        CompletableFuture.allOf(procesarPago, actualizarInventario, notificarCliente).join();

        // === TAREA: finalizarOrden ===
        FlowScriptBuiltins.ejecutar(
            "UPDATE ordenes SET estado = 'completada' WHERE id = ?",
            ordenId
        );

    } else {
        // === TAREA: notificarSinStock ===
        FlowScriptBuiltins.httpPost(
            "https://api.notif.com/enviar",
            FlowScriptBuiltins.toJson(Map.of(
                "email", email,
                "mensaje", "Sin stock"
            ))
        );
    }

    // === FIN ===
}
```

**Análisis:**
- ✅ **Paralelismo evidente**: Se ven las 3 ramas paralelas
- ✅ **Join explícito**: `allOf(...).join()` es claro
- ✅ **Parámetros directos**: No hay Map genérico, parámetros tipados
- ✅ **Manejo de errores simple**: Try-catch en cada rama
- ✅ **Semántica BPMN preservada**: "Fork → 3 ramas → Join" está explícito en el código

---

### CASO 3: Proceso con go_to (State Machine)

**FlowScript:**
```javascript
proceso ProcesoConCiclo {
    inicio -> verificar

    tarea verificar {
        var intentos = context.intentos || 0
        si (intentos < 3) {
            go_to reintentar
        }
        go_to fallar
    }

    tarea reintentar {
        context.intentos = (context.intentos || 0) + 1
        var exito = intentarOperacion()
        si (exito) {
            go_to finalizar
        }
        go_to verificar
    }

    tarea fallar {
        imprimir("Falló después de 3 intentos")
    }

    tarea finalizar {
        imprimir("Operación exitosa")
    }

    fin
}
```

**Java Generado (State Machine):**
```java
public static void procesoConCiclo() {
    // Variables del contexto
    Map<String, Object> ctx = new HashMap<>();
    String estado = "inicio";

    // === State Machine ===
    while (!estado.equals("fin")) {
        switch (estado) {

            case "inicio":
                // === INICIO ===
                estado = "verificar";
                break;

            case "verificar":
                // === TAREA: verificar ===
                int intentos = (Integer) ctx.getOrDefault("intentos", 0);
                if (intentos < 3) {
                    estado = "reintentar";  // go_to
                } else {
                    estado = "fallar";  // go_to
                }
                break;

            case "reintentar":
                // === TAREA: reintentar ===
                ctx.put("intentos", (Integer) ctx.getOrDefault("intentos", 0) + 1);
                boolean exito = intentarOperacion();
                if (exito) {
                    estado = "finalizar";  // go_to
                } else {
                    estado = "verificar";  // go_to (ciclo)
                }
                break;

            case "fallar":
                // === TAREA: fallar ===
                FlowScriptBuiltins.imprimir("Falló después de 3 intentos");
                estado = "fin";
                break;

            case "finalizar":
                // === TAREA: finalizar ===
                FlowScriptBuiltins.imprimir("Operación exitosa");
                estado = "fin";
                break;
        }
    }
    // === FIN ===
}
```

**Análisis:**
- ✅ **go_to = cambio de estado**: Semántica preservada
- ✅ **Ciclos explícitos**: El while + switch hace evidente el ciclo
- ✅ **Contexto explícito**: Map solo cuando se necesita compartir estado entre iteraciones
- ⚠️ Más verboso, pero SOLO cuando hay go_to (caso raro)

---

## ESTRATEGIA DE GENERACIÓN

### Algoritmo de Decisión:

```
1. ¿Es una FUNCIÓN?
   → Generar método static directo

2. ¿Es un PROCESO?

   2.1. ¿Tiene go_to?
        SÍ → Generar State Machine (switch)
        NO → Continuar

   2.2. ¿Tiene Parallel Gateway?
        SÍ → Generar con CompletableFuture.allOf()
        NO → Continuar

   2.3. Proceso simple:
        → Generar método secuencial con if/else
```

### Generador Simplificado:

```java
public class SimpleCodeGenerator {

    public String generate(ASTNode node) {
        if (node instanceof FunctionDeclarationNode) {
            return generateFunction((FunctionDeclarationNode) node);
        }

        if (node instanceof ProcessDeclarationNode) {
            ProcessDeclarationNode process = (ProcessDeclarationNode) node;

            if (hasGoTo(process)) {
                return generateStateMachine(process);
            } else {
                return generateSequentialProcess(process);
            }
        }

        throw new IllegalArgumentException("Unknown node type");
    }

    private String generateFunction(FunctionDeclarationNode func) {
        StringBuilder sb = new StringBuilder();

        // Signature
        sb.append("public static ")
          .append(mapType(func.getReturnType()))
          .append(" ")
          .append(func.getName())
          .append("(")
          .append(generateParameters(func.getParameters()))
          .append(") {\n");

        // Body
        for (StatementNode stmt : func.getBody()) {
            sb.append("    ").append(generateStatement(stmt)).append("\n");
        }

        sb.append("}\n");
        return sb.toString();
    }

    private String generateSequentialProcess(ProcessDeclarationNode process) {
        StringBuilder sb = new StringBuilder();

        // Signature
        sb.append("public static void ")
          .append(process.getName())
          .append("(")
          .append(extractParameters(process))
          .append(") {\n");

        // INICIO
        sb.append("    // === INICIO ===\n\n");

        // Generar cada nodo en orden de flujo
        for (ProcessElementNode element : process.getElements()) {
            sb.append(generateProcessElement(element));
        }

        // FIN
        sb.append("    // === FIN ===\n");
        sb.append("}\n");

        return sb.toString();
    }

    private String generateProcessElement(ProcessElementNode element) {
        if (element instanceof TaskElementNode) {
            return generateTask((TaskElementNode) element);
        }

        if (element instanceof ExclusiveGatewayNode) {
            return generateExclusiveGateway((ExclusiveGatewayNode) element);
        }

        if (element instanceof ParallelGatewayNode) {
            return generateParallelGateway((ParallelGatewayNode) element);
        }

        return "";
    }

    private String generateTask(TaskElementNode task) {
        StringBuilder sb = new StringBuilder();
        sb.append("    // === TAREA: ").append(task.getName()).append(" ===\n");

        for (StatementNode stmt : task.getStatements()) {
            sb.append("    ").append(generateStatement(stmt)).append("\n");
        }

        sb.append("\n");
        return sb.toString();
    }

    private String generateExclusiveGateway(ExclusiveGatewayNode gateway) {
        StringBuilder sb = new StringBuilder();
        sb.append("    // === GATEWAY: ").append(gateway.getName()).append(" ===\n");

        List<WhenClauseNode> whens = gateway.getWhenClauses();

        for (int i = 0; i < whens.size(); i++) {
            WhenClauseNode when = whens.get(i);

            if (i == 0) {
                sb.append("    if (").append(generateExpression(when.getCondition())).append(") {\n");
            } else {
                sb.append("    } else if (").append(generateExpression(when.getCondition())).append(") {\n");
            }

            // Generar siguiente nodo
            sb.append(generateProcessElement(when.getNextElement()));
        }

        // Else clause
        if (gateway.getElseClause() != null) {
            sb.append("    } else {\n");
            sb.append(generateProcessElement(gateway.getElseClause().getNextElement()));
        }

        sb.append("    }\n\n");
        return sb.toString();
    }

    private String generateParallelGateway(ParallelGatewayNode gateway) {
        StringBuilder sb = new StringBuilder();
        sb.append("    // === PARALLEL GATEWAY: ").append(gateway.getName()).append(" ===\n");

        List<ParallelBranchNode> branches = gateway.getBranches();

        // Crear CompletableFutures
        for (int i = 0; i < branches.size(); i++) {
            ParallelBranchNode branch = branches.get(i);
            sb.append("    CompletableFuture<Void> rama").append(i).append(" = ");
            sb.append("CompletableFuture.runAsync(() -> {\n");

            // Código de la rama
            for (ProcessElementNode element : branch.getElements()) {
                sb.append("        try {\n");
                String elementCode = generateProcessElement(element);
                // Indentar el código
                sb.append(indent(elementCode, 3));
                sb.append("        } catch (Exception e) {\n");
                sb.append("            throw new RuntimeException(\"Error en rama paralela\", e);\n");
                sb.append("        }\n");
            }

            sb.append("    });\n\n");
        }

        // JOIN
        sb.append("    // === JOIN: Esperar todas las ramas ===\n");
        sb.append("    CompletableFuture.allOf(");
        for (int i = 0; i < branches.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append("rama").append(i);
        }
        sb.append(").join();\n\n");

        // Siguiente elemento después del join
        if (gateway.getJoinClause() != null) {
            sb.append(generateProcessElement(gateway.getJoinClause().getNextElement()));
        }

        return sb.toString();
    }
}
```

---

## RUNTIME LIBRARY (Simplificado)

### FlowScriptBuiltins.java

```java
package com.flowscript.runtime;

import com.google.gson.Gson;
import com.zaxxer.hikari.*;
import java.net.http.*;
import java.util.*;

public class FlowScriptBuiltins {
    private static HttpClient httpClient = HttpClient.newHttpClient();
    private static HikariDataSource dataSource;
    private static Gson gson = new Gson();

    // === I/O ===
    public static void imprimir(Object value) {
        System.out.println(value);
    }

    // === HTTP ===
    public static String httpGet(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public static String httpPost(String url, String body) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    // === Database ===
    public static void configurarBD(String url, String user, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        dataSource = new HikariDataSource(config);
    }

    public static List<Map<String, Object>> consultar(String sql, Object... params)
            throws Exception {
        List<Map<String, Object>> results = new ArrayList<>();
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            try (var rs = stmt.executeQuery()) {
                var meta = rs.getMetaData();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        row.put(meta.getColumnName(i), rs.getObject(i));
                    }
                    results.add(row);
                }
            }
        }
        return results;
    }

    public static int ejecutar(String sql, Object... params) throws Exception {
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeUpdate();
        }
    }

    // === JSON ===
    public static Map<String, Object> parseJson(String json) {
        return gson.fromJson(json, Map.class);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
}
```

**Total: ~80 líneas. Simple. Directo. Funcional.**

---

## COMPARACIÓN: Diseño Anterior vs Simple

| Aspecto | Diseño Anterior | Diseño Simple |
|---------|----------------|---------------|
| **Proceso → Código** | Clase completa | Un método |
| **Variables contexto** | ProcessContext class | Variables locales / Map |
| **Tareas** | Métodos privados | Bloques inline |
| **Parallel gateway** | CompletableFuture | CompletableFuture (igual) |
| **Exclusive gateway** | Métodos con if/else | If/else inline |
| **go_to** | Métodos llamándose | State machine (solo si necesario) |
| **Legibilidad** | Saltos entre métodos | Flujo lineal |
| **LOC para proceso simple** | ~150 líneas | ~40 líneas |
| **LOC para proceso complejo** | ~200 líneas | ~80-100 líneas |
| **Complejidad cognitiva** | Alta (muchas piezas) | Baja (todo junto) |

---

## VENTAJAS DEL DISEÑO SIMPLE

1. **Código autoexplicativo**: Los comentarios `// === TAREA: ... ===` hacen obvio qué nodo BPMN se está ejecutando
2. **Debugging fácil**: Stack trace más simple, menos métodos
3. **Modificación directa**: Se puede editar el código generado fácilmente
4. **Zero overhead**: No hay abstracciones innecesarias
5. **Idiomático**: Es código Java normal, sin patrones raros
6. **Semántica preservada**: El flujo del proceso se lee directamente en el código

---

## EJEMPLO COMPLETO: FlowScript → Java

### FlowScript
```javascript
proceso ComprarProducto {
    inicio -> verificarStock

    tarea verificarStock {
        var producto = consultar("SELECT * FROM productos WHERE id = ?", productoId)[0]
        context.stockDisponible = producto.stock > cantidad
    }

    gateway decision {
        cuando (context.stockDisponible) -> procesarCompra
        sino -> notificarSinStock
    }

    tarea procesarCompra {
        ejecutar("UPDATE productos SET stock = stock - ? WHERE id = ?",
                 cantidad, productoId)
        imprimir("Compra procesada")
    }

    tarea notificarSinStock {
        imprimir("Stock insuficiente")
    }

    fin
}
```

### Java Generado
```java
import com.flowscript.runtime.*;
import java.util.*;
import java.util.concurrent.*;

public class GeneratedCode {

    public static void comprarProducto(int productoId, int cantidad) {
        // === INICIO ===

        // === TAREA: verificarStock ===
        Map<String, Object> producto = FlowScriptBuiltins.consultar(
            "SELECT * FROM productos WHERE id = ?",
            productoId
        ).get(0);
        boolean stockDisponible = (Integer) producto.get("stock") > cantidad;

        // === GATEWAY: decision ===
        if (stockDisponible) {
            // === TAREA: procesarCompra ===
            FlowScriptBuiltins.ejecutar(
                "UPDATE productos SET stock = stock - ? WHERE id = ?",
                cantidad,
                productoId
            );
            FlowScriptBuiltins.imprimir("Compra procesada");
        } else {
            // === TAREA: notificarSinStock ===
            FlowScriptBuiltins.imprimir("Stock insuficiente");
        }

        // === FIN ===
    }
}
```

**20 líneas de FlowScript → 30 líneas de Java**

Claro, conciso, semánticamente equivalente.

---

## CONCLUSIÓN

Este diseño minimalista:

- ✅ **Genera menos código** (40-60% menos líneas)
- ✅ **Es más legible** (flujo lineal vs saltos entre métodos)
- ✅ **Preserva semántica BPMN** (comentarios estructurados + estructura de código)
- ✅ **Es idiomático Java** (métodos static, variables locales, CompletableFuture)
- ✅ **Fácil de debugear** (menos abstracciones)
- ✅ **Fácil de extender** (menos componentes)

**La simplicidad NO sacrifica expresividad. Al contrario, la MEJORA.**
