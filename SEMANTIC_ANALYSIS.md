# FlowScript - Especificación del Análisis Semántico

## 1. INTRODUCCIÓN

El análisis semántico de FlowScript debe validar la **corrección semántica** del código fuente, más allá de la corrección sintáctica. Esto incluye:

- **Resolución de nombres**: Todas las variables y funciones usadas deben estar declaradas
- **Verificación de tipos**: Las operaciones deben aplicarse a tipos compatibles
- **Validación de flujo de control**: Los procesos deben seguir las reglas BPMN
- **Detección de código inalcanzable**: Advertir sobre código que nunca se ejecutará
- **Validación de imports**: Los módulos importados deben existir

## 2. ELEMENTOS SEMÁNTICOS DEL LENGUAJE

### 2.1 FUNCIONES

#### Características semánticas:
```flowscript
funcion calcular_impuesto(monto: decimal, tasa: decimal) -> decimal {
    si monto <= 0 { retornar 0.0 }
    retornar monto * tasa
}
```

**Validaciones requeridas:**
1. ✅ **Nombre único**: No puede haber dos funciones con el mismo nombre en el mismo scope
2. ✅ **Parámetros únicos**: Dentro de una función, cada parámetro debe tener nombre único
3. ✅ **Tipo de retorno**:
   - Si tipo != `vacio`, TODAS las rutas deben terminar en `retornar`
   - El tipo del valor retornado debe ser compatible con el tipo declarado
4. ✅ **Recursión**: Permitida, pero se debe detectar recursión infinita obvia
5. ✅ **Variables locales**:
   - Declaradas antes de uso
   - No pueden redeclararse en el mismo scope
   - Shadowing permitido en bloques anidados
6. ✅ **Prohibiciones**:
   - NO puede usar `entrada` (exclusivo de procesos)
   - NO puede usar `go_to` (exclusivo de procesos)
   - NO puede declarar tareas, gateways, etc.

#### Scopes en funciones:
```
Función
├── Scope de parámetros (nivel 1)
├── Scope del cuerpo de función (nivel 2)
│   ├── Scope de bloque if (nivel 3)
│   ├── Scope de bloque for (nivel 3)
│   │   └── Variable de iteración
│   ├── Scope de bloque try (nivel 3)
│   └── Scope de bloque catch (nivel 3)
│       └── Variable de excepción
└── Variables locales por nivel
```

### 2.2 PROCESOS

#### Características semánticas:
```flowscript
proceso AprobacionFactura {
    inicio -> ClasificarMonto

    tarea ClasificarMonto {
        accion:
            monto_total = entrada.monto
            si monto_total > 10000 {
                go_to RequiereAprobacionGerente
            } sino {
                go_to AprobacionAutomatica
            }
    }

    tarea AprobacionAutomatica {
        accion:
            # monto_total DEBE estar disponible aquí (contexto de proceso)
            imprimir("Monto: " + monto_total)
            go_to FinOK
    }

    fin FinOK
}
```

**Validaciones requeridas:**

#### 2.2.1 Estructura BPMN obligatoria:
1. ✅ **Exactamente UN elemento `inicio`**
   - Error si hay 0 o más de 1
   - Debe apuntar a un nodo válido: `inicio -> NombreNodo`

2. ✅ **Al menos UN elemento `fin`**
   - Puede haber múltiples fins (diferentes flujos terminan en diferentes estados)
   - Cada fin debe tener nombre único

3. ✅ **Todos los nodos en `go_to` deben existir**
   ```flowscript
   go_to NodoInexistente  # ❌ ERROR: NodoInexistente no declarado
   ```

4. ✅ **No debe haber nodos inalcanzables**
   - Todo nodo (tarea/gateway/fin) debe ser alcanzable desde `inicio`
   - Advertencia (warning) si un nodo nunca es referenciado

5. ✅ **No debe haber ciclos infinitos obvios**
   ```flowscript
   tarea TareaA {
       accion: go_to TareaB
   }
   tarea TareaB {
       accion: go_to TareaA  # ⚠️ WARNING: Posible ciclo infinito
   }
   # Sin condiciones de salida ni go_to a fin
   ```

#### 2.2.2 Contexto de proceso:
```flowscript
proceso Ejemplo {
    inicio -> Tarea1

    tarea Tarea1 {
        accion:
            # Variables declaradas aquí se guardan en CONTEXTO
            nombre = "Juan"
            edad = 30
            go_to Tarea2
    }

    tarea Tarea2 {
        accion:
            # DEBE poder acceder a 'nombre' y 'edad'
            imprimir(nombre + " tiene " + edad + " años")
            go_to Fin
    }

    fin Fin
}
```

**Reglas del contexto:**
- Variables declaradas en cualquier tarea están disponibles en TODAS las tareas subsiguientes
- El contexto persiste durante toda la ejecución del proceso
- `entrada` es un objeto especial disponible en TODO el proceso
- Scope es GLOBAL dentro del proceso (no hay scopes locales en tareas)

#### 2.2.3 Objeto `entrada`:
```flowscript
proceso ProcesarOrden {
    inicio -> Validar

    tarea Validar {
        accion:
            # 'entrada' está implícitamente disponible
            cliente_id = entrada.cliente_id
            monto = entrada.monto
            items = entrada.items

            si monto > 1000 {
                go_to ValidacionEspecial
            } sino {
                go_to ProcesarNormal
            }
    }

    fin ProcesarNormal
    fin ValidacionEspecial
}
```

**Validaciones:**
- `entrada` solo puede usarse dentro de procesos
- `entrada` se trata como tipo `objeto` (dinámico)
- Acceso a propiedades: `entrada.prop` es válido
- Error si se usa `entrada` en una función

### 2.3 GATEWAYS

#### 2.3.1 Gateway Exclusivo (XOR):
```flowscript
proceso EjemploGateway {
    inicio -> Clasificar

    tarea Clasificar {
        accion:
            si entrada.monto > 10000 {
                go_to AprobacionGerente
            } sino_si entrada.monto > 1000 {
                go_to AprobacionSupervisor
            } sino {
                go_to AprobacionAutomatica
            }
    }

    fin AprobacionGerente
    fin AprobacionSupervisor
    fin AprobacionAutomatica
}
```

**Validaciones:**
1. ✅ Todas las condiciones deben ser tipo `booleano`
2. ✅ Debe haber al menos una rama (cuando/sino)
3. ✅ Se recomienda tener un `sino` final (exhaustividad)
4. ✅ Todos los nodos destino deben existir

#### 2.3.2 Gateway Paralelo (AND):
```flowscript
proceso VerificacionParalela {
    inicio -> IniciarVerificaciones

    gateway IniciarVerificaciones paralelo {
        rama -> VerificarCredito
        rama -> VerificarPenal
        rama -> VerificarLaboral
        unir -> Consolidar
    }

    tarea VerificarCredito {
        accion:
            reporte_credito = obtener_credito()
            go_to FinCredito
    }
    fin FinCredito

    tarea VerificarPenal {
        accion:
            reporte_penal = obtener_penal()
            go_to FinPenal
    }
    fin FinPenal

    tarea VerificarLaboral {
        accion:
            reporte_laboral = obtener_laboral()
            go_to FinLaboral
    }
    fin FinLaboral

    tarea Consolidar {
        accion:
            # DEBE tener acceso a todas las variables de las ramas
            si reporte_credito.ok y reporte_penal.ok y reporte_laboral.ok {
                go_to Aprobado
            } sino {
                go_to Rechazado
            }
    }

    fin Aprobado
    fin Rechazado
}
```

**Validaciones:**
1. ✅ **Mínimo 2 ramas**: `rama ->` debe aparecer al menos 2 veces
2. ✅ **Obligatorio `unir`**: Debe especificar punto de sincronización
3. ✅ **Nodo de unión existe**: El nodo después de `unir` debe estar declarado
4. ✅ **Todas las ramas convergen**: Cada rama debe eventualmente llegar a un `fin` o antes del `unir`
5. ✅ **Contexto compartido**: Variables de ramas paralelas están disponibles en nodo `unir`

### 2.4 VARIABLES Y CONSTANTES

#### Declaración implícita:
```flowscript
funcion ejemplo() {
    x = 42          # Declaración implícita de x: entero
    nombre = "Ana"  # Declaración implícita de nombre: texto
    activo = verdadero  # Declaración implícita de activo: booleano
}
```

**Reglas de scope:**
```flowscript
funcion scope_ejemplo() {
    x = 10  # Scope: función

    si x > 5 {
        y = 20  # Scope: bloque if (accesible solo dentro del if)
        x = 15  # Modifica x del scope exterior
    }

    # y no está disponible aquí ❌
    imprimir(x)  # x = 15 ✅
}
```

**Validaciones:**
1. ✅ Variable usada antes de ser declarada (error)
2. ✅ Variable redeclarada en mismo scope (error)
3. ✅ Variable nunca usada (warning)
4. ✅ Variable declarada pero nunca leída (warning)
5. ✅ Shadowing en bloques anidados (permitido, warning)

### 2.5 TIPOS DE DATOS

#### Tipos primitivos:
- `entero`: Números enteros de 64 bits
- `decimal`: Números de punto flotante de doble precisión
- `booleano`: `verdadero` o `falso`
- `texto`: Cadenas Unicode
- `nulo`: Valor especial que representa ausencia

#### Tipos compuestos:
- `lista`: Colección ordenada heterogénea
  ```flowscript
  items = [1, "texto", verdadero, 3.14]
  ```
- `objeto`: Mapa clave-valor dinámico
  ```flowscript
  persona = { nombre: "Ana", edad: 30, activo: verdadero }
  ```

#### Tipo especial:
- `vacio`: Indica que una función no retorna valor

#### Sistema de tipos:

**FlowScript es un lenguaje con TIPADO DINÁMICO y VERIFICACIÓN ESTÁTICA PARCIAL:**

1. **Inferencia de tipos**: El tipo se deduce del valor asignado
   ```flowscript
   x = 42        # Tipo inferido: entero
   y = x + 10    # Tipo inferido: entero (porque x es entero)
   ```

2. **Promoción de tipos**:
   ```flowscript
   a = 10        # entero
   b = 3.14      # decimal
   c = a + b     # decimal (entero se promociona a decimal)
   ```

3. **Compatibilidad de tipos**:

   | Operador | Tipo Izq. | Tipo Der. | Resultado | ¿Válido? |
   |----------|-----------|-----------|-----------|----------|
   | `+`      | entero    | entero    | entero    | ✅ |
   | `+`      | entero    | decimal   | decimal   | ✅ |
   | `+`      | texto     | texto     | texto     | ✅ |
   | `+`      | texto     | cualquier | texto     | ✅ (conversión implícita) |
   | `-`, `*`, `/`, `%` | entero | entero | entero | ✅ |
   | `-`, `*`, `/`, `%` | decimal | decimal | decimal | ✅ |
   | `-`, `*`, `/` | entero | decimal | decimal | ✅ |
   | `y`, `o` | booleano | booleano | booleano | ✅ |
   | `y`, `o` | otro | otro | - | ❌ Error |
   | `<`, `>`, `<=`, `>=` | entero/decimal | entero/decimal | booleano | ✅ |
   | `<`, `>`, `<=`, `>=` | texto | texto | booleano | ✅ (comparación lexicográfica) |
   | `==`, `!=` | cualquier | cualquier | booleano | ✅ |
   | `no`     | booleano | - | booleano | ✅ |
   | `no`     | otro | - | - | ❌ Error |

4. **Acceso a propiedades y elementos**:
   ```flowscript
   obj = { nombre: "Ana" }
   nombre_valor = obj.nombre       # ✅ Tipo: texto (si existe)
   nombre_valor = obj.inexistente  # ⚠️ Warning: propiedad puede no existir

   lista = [10, 20, 30]
   primer = lista[0]  # ✅ Tipo: entero
   fuera = lista[100] # ⚠️ Warning: índice puede estar fuera de rango
   ```

**Verificaciones de tipos:**
1. ✅ Operador aplicado a tipos incompatibles
2. ✅ Asignación de tipo incompatible a parámetro tipado
3. ✅ Retorno de tipo incompatible con firma de función
4. ✅ Condiciones no booleanas en `si`, `mientras`, `para`
5. ✅ Operador unario aplicado a tipo incorrecto
6. ✅ Acceso a propiedad en no-objeto (error)
7. ✅ Acceso a índice en no-lista (error)
8. ✅ Ternario con ramas de tipos diferentes (warning)

### 2.6 EXPRESIONES

#### Operadores aritméticos:
```flowscript
suma = a + b
resta = a - b
producto = a * b
division = a / b
modulo = a % b
```

**Validaciones:**
- Operandos deben ser `entero` o `decimal`
- División por cero literal genera warning
- Módulo `%` solo con enteros

#### Operadores lógicos:
```flowscript
resultado = condicion1 y condicion2
resultado = condicion1 o condicion2
negado = no condicion
```

**Validaciones:**
- Operandos deben ser `booleano`
- Cortocircuito: `falso y x` no evalúa `x`

#### Operadores relacionales:
```flowscript
mayor = a > b
menor_igual = a <= b
igual = a == b
diferente = a != b
```

**Validaciones:**
- `<`, `>`, `<=`, `>=`: Solo para `entero`, `decimal`, `texto`
- `==`, `!=`: Cualquier tipo (comparación por valor)

#### Operador ternario:
```flowscript
resultado = condicion ? valor_si_cierto : valor_si_falso
```

**Validaciones:**
- Condición debe ser `booleano`
- Tipos de ramas pueden diferir (warning si muy diferentes)

#### Acceso a miembros:
```flowscript
valor = objeto.propiedad
valor = objeto.prop1.prop2.prop3  # Acceso encadenado
```

**Validaciones:**
- Expresión izquierda debe ser tipo `objeto`
- Warning si propiedad puede no existir

#### Acceso a elementos:
```flowscript
elemento = lista[indice]
elemento = lista[0]
```

**Validaciones:**
- Expresión izquierda debe ser tipo `lista`
- Índice debe ser tipo `entero`
- Warning si índice literal fuera de rango conocido

#### Llamadas a función:
```flowscript
resultado = funcion_nombre(arg1, arg2, arg3)
```

**Validaciones:**
1. ✅ Función debe estar declarada (o importada)
2. ✅ Número de argumentos debe coincidir con parámetros
3. ✅ Tipos de argumentos deben ser compatibles (si parámetros están tipados)
4. ✅ Función llamada antes de su declaración (permitido si está en el mismo archivo)

### 2.7 STATEMENTS DE CONTROL DE FLUJO

#### `si` / `sino_si` / `sino`:
```flowscript
si condicion1 {
    # bloque 1
} sino_si condicion2 {
    # bloque 2
} sino {
    # bloque 3
}
```

**Validaciones:**
- `condicion1`, `condicion2` deben ser `booleano`
- Warning si condición es constante: `si verdadero` o `si falso`
- Warning si ramas son idénticas

#### `mientras`:
```flowscript
mientras condicion {
    # bloque
}
```

**Validaciones:**
- Condición debe ser `booleano`
- Warning si condición es constante `verdadero` (bucle infinito)
- Warning si condición es constante `falso` (código inalcanzable)

#### `para cada`:
```flowscript
para cada item en lista {
    imprimir(item)
}
```

**Validaciones:**
- Expresión iterable debe ser tipo `lista`
- Variable de iteración (`item`) tiene scope del bloque `para`
- Variable de iteración es readonly dentro del bucle

#### `para` con rango:
```flowscript
para i desde 1 hasta 10 {
    imprimir(i)
}

para i desde inicio hasta fin paso 2 {
    imprimir(i)
}
```

**Validaciones:**
- `inicio`, `fin`, `paso` deben ser tipo `entero`
- Variable de iteración tiene scope del bloque
- Warning si rango es vacío: `desde 10 hasta 1` sin paso negativo

#### `intentar` / `capturar`:
```flowscript
intentar {
    resultado = operacion_riesgosa()
} capturar (e) {
    imprimir("Error: " + e.mensaje)
}
```

**Validaciones:**
- Variable de excepción (`e`) tiene scope del bloque `capturar`
- Variable de excepción es tipo `objeto`
- Bloque `intentar` no puede estar vacío

#### `lanzar`:
```flowscript
lanzar { tipo: "ErrorValidacion", mensaje: "Dato inválido" }
```

**Validaciones:**
- Expresión lanzada debe ser tipo `objeto`
- Se recomienda que tenga propiedades `tipo` y `mensaje`

#### `retornar`:
```flowscript
retornar expresion
retornar  # Sin valor (solo en funciones void)
```

**Validaciones:**
1. ✅ Solo puede usarse dentro de funciones
2. ✅ Si función tiene tipo retorno != `vacio`, DEBE retornar un valor
3. ✅ Tipo del valor retornado debe ser compatible con tipo declarado
4. ✅ Warning: código después de `retornar` es inalcanzable
5. ✅ Error: función con tipo != `vacio` sin `retornar` en todas las rutas

### 2.8 IMPORTS Y MÓDULOS

#### Import simple:
```flowscript
importar "std/http"
importar "std/json"
importar "utilidades"

# Uso:
datos = http.get("https://api.com")
obj = json.parse(datos)
```

**Validaciones:**
- Módulo debe existir en el sistema
- Ruta del módulo es relativa o absoluta
- Símbolos del módulo se acceden con nombre de módulo como prefijo

#### Import con alias:
```flowscript
importar "std/http" como Http
importar "std/json" como JSON

# Uso:
datos = Http.get("https://api.com")
obj = JSON.parse(datos)
```

**Validaciones:**
- Alias debe ser único (no conflictos con otros imports)
- Alias no puede ser palabra reservada
- Alias no puede coincidir con función/variable declarada

#### Import de JAR:
```flowscript
importar_jar "libs/bouncycastle.jar" as crypto
importar_jar "libs/guava.jar" as guava

# Uso:
hasher = crypto.MessageDigest.getInstance("SHA-256")
```

**Validaciones:**
- Archivo JAR debe existir en el sistema
- Clases Java accesibles con alias

### 2.9 LITERALES

#### Enteros:
```flowscript
x = 42
y = 1_000_000  # Con separadores
z = -99
```

#### Decimales:
```flowscript
pi = 3.14159
cientifico = 1.23e-5
negativo = -0.001
```

#### Textos:
```flowscript
simple = "Hola mundo"
con_escapes = "Línea 1\nLínea 2\tTabulado"
vacio = ""
```

#### Booleanos:
```flowscript
activo = verdadero
deshabilitado = falso
```

#### Nulo:
```flowscript
valor = nulo
```

#### Listas:
```flowscript
vacia = []
enteros = [1, 2, 3, 4, 5]
mixta = [1, "texto", verdadero, 3.14, nulo]
anidada = [[1, 2], [3, 4], [5, 6]]
```

#### Objetos:
```flowscript
vacio = {}
persona = {
    nombre: "Ana",
    edad: 30,
    activo: verdadero,
    direccion: {
        calle: "Main St",
        numero: 123
    }
}
```

## 3. ARQUITECTURA DEL ANÁLISIS SEMÁNTICO

### 3.1 VISITOR PATTERN

Implementaremos el patrón Visitor para recorrer el AST:

```java
public interface ASTVisitor<T> {
    T visit(ProgramNode node);
    T visit(FunctionDeclarationNode node);
    T visit(ProcessDeclarationNode node);
    T visit(IfStatementNode node);
    T visit(BinaryExpressionNode node);
    // ... un método visit() por cada tipo de nodo AST
}
```

### 3.2 COMPONENTES PRINCIPALES

#### SemanticAnalyzer (Visitor Principal)
```java
public class SemanticAnalyzer implements ASTVisitor<Void> {
    private SymbolTable symbolTable;
    private FunctionTable functionTable;
    private ProcessTable processTable;
    private TypeChecker typeChecker;
    private List<SemanticError> errors;
    private List<SemanticWarning> warnings;

    private AnalysisContext currentContext;  // FUNCION, PROCESO, GLOBAL

    public SemanticAnalysisResult analyze(ProgramNode program) {
        // 1. First pass: Collect all declarations
        collectDeclarations(program);

        // 2. Second pass: Analyze function bodies
        analyzeFunctions(program);

        // 3. Third pass: Analyze process structures
        analyzeProcesses(program);

        // 4. Return results
        return new SemanticAnalysisResult(errors, warnings);
    }
}
```

#### SymbolTable (Tabla de símbolos con scopes)
```java
public class SymbolTable {
    private Deque<Scope> scopeStack;
    private Map<String, Symbol> globals;

    public void enterScope(ScopeType type) {
        scopeStack.push(new Scope(type));
    }

    public void exitScope() {
        scopeStack.pop();
    }

    public void declareVariable(String name, Type type, ASTNode location) {
        Scope current = scopeStack.peek();
        if (current.hasSymbol(name)) {
            throw new VariableAlreadyDeclaredException(name, location);
        }
        current.addSymbol(new VariableSymbol(name, type, location));
    }

    public Symbol lookupVariable(String name) {
        // Search from innermost to outermost scope
        for (Scope scope : scopeStack) {
            if (scope.hasSymbol(name)) {
                return scope.getSymbol(name);
            }
        }
        // Check globals
        return globals.get(name);
    }
}

public class Scope {
    private ScopeType type;  // GLOBAL, FUNCTION, BLOCK, PROCESS
    private Map<String, Symbol> symbols;

    public boolean hasSymbol(String name) {
        return symbols.containsKey(name);
    }
}

public enum ScopeType {
    GLOBAL,           // Variables/constantes globales
    FUNCTION,         // Scope de función (parámetros + cuerpo)
    BLOCK,            // Scope de bloque (if, while, for, try)
    PROCESS,          // Scope de proceso (contexto compartido)
    PARALLEL_BRANCH   // Rama paralela de gateway
}
```

#### FunctionTable
```java
public class FunctionTable {
    private Map<String, FunctionSymbol> functions;
    private Map<String, ModuleSymbol> importedModules;

    public void declareFunction(
        String name,
        List<Parameter> params,
        Type returnType,
        ASTNode location
    ) {
        if (functions.containsKey(name)) {
            throw new FunctionAlreadyDeclaredException(name, location);
        }
        functions.put(name, new FunctionSymbol(name, params, returnType, location));
    }

    public FunctionSymbol lookupFunction(String name) {
        // Check local functions
        if (functions.containsKey(name)) {
            return functions.get(name);
        }
        // Check imported modules
        for (ModuleSymbol module : importedModules.values()) {
            if (module.hasFunction(name)) {
                return module.getFunction(name);
            }
        }
        return null;
    }

    public void validateCall(
        String functionName,
        List<ExpressionNode> args,
        ASTNode location
    ) {
        FunctionSymbol func = lookupFunction(functionName);
        if (func == null) {
            throw new FunctionNotDeclaredException(functionName, location);
        }

        // Check argument count
        if (args.size() != func.getParameters().size()) {
            throw new WrongArgumentCountException(
                functionName,
                func.getParameters().size(),
                args.size(),
                location
            );
        }

        // Check argument types
        for (int i = 0; i < args.size(); i++) {
            Type expectedType = func.getParameters().get(i).getType();
            Type actualType = typeChecker.inferType(args.get(i));

            if (!typeChecker.isCompatible(expectedType, actualType)) {
                throw new TypeMismatchException(
                    expectedType,
                    actualType,
                    location
                );
            }
        }
    }
}
```

#### ProcessTable
```java
public class ProcessTable {
    private Map<String, ProcessSymbol> processes;

    public void declareProcess(String name, ProcessDeclarationNode node) {
        if (processes.containsKey(name)) {
            throw new ProcessAlreadyDeclaredException(name, node);
        }

        ProcessSymbol symbol = new ProcessSymbol(name, node);
        processes.put(name, symbol);

        // Validate BPMN structure
        validateProcessStructure(symbol);
    }

    private void validateProcessStructure(ProcessSymbol process) {
        ProcessDeclarationNode node = process.getNode();

        // 1. Check exactly one 'inicio'
        List<StartElementNode> starts = findStartElements(node);
        if (starts.isEmpty()) {
            throw new MissingStartElementException(process.getName(), node);
        }
        if (starts.size() > 1) {
            throw new MultipleStartElementsException(process.getName(), node);
        }

        // 2. Check at least one 'fin'
        List<EndElementNode> ends = findEndElements(node);
        if (ends.isEmpty()) {
            throw new MissingEndElementException(process.getName(), node);
        }

        // 3. Validate all go_to references
        Set<String> declaredNodes = collectAllNodeNames(node);
        List<GotoStatementNode> gotos = findAllGotos(node);

        for (GotoStatementNode goto : gotos) {
            String targetNode = goto.getTargetNode();
            if (!declaredNodes.contains(targetNode)) {
                throw new NodeNotFoundException(
                    targetNode,
                    process.getName(),
                    goto
                );
            }
        }

        // 4. Check reachability from 'inicio'
        checkReachability(process, starts.get(0), declaredNodes);

        // 5. Validate gateways
        validateGateways(node);
    }

    private void checkReachability(
        ProcessSymbol process,
        StartElementNode start,
        Set<String> allNodes
    ) {
        Set<String> reachable = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        // Start from inicio
        queue.add(start.getTargetNode());
        reachable.add(start.getTargetNode());

        // BFS to find all reachable nodes
        while (!queue.isEmpty()) {
            String current = queue.poll();
            List<String> nexts = findNextNodes(process.getNode(), current);

            for (String next : nexts) {
                if (!reachable.contains(next)) {
                    reachable.add(next);
                    queue.add(next);
                }
            }
        }

        // Check for unreachable nodes
        for (String nodeName : allNodes) {
            if (!reachable.contains(nodeName)) {
                warnings.add(new UnreachableNodeWarning(
                    nodeName,
                    process.getName()
                ));
            }
        }
    }

    private void validateGateways(ProcessDeclarationNode process) {
        // Validate exclusive gateways (if/else)
        List<IfStatementNode> conditionals = findAllConditionals(process);
        for (IfStatementNode ifStmt : conditionals) {
            // Check all conditions are boolean
            Type condType = typeChecker.inferType(ifStmt.getCondition());
            if (!condType.equals(Type.BOOLEAN)) {
                throw new NonBooleanConditionException(ifStmt);
            }
        }

        // Validate parallel gateways
        List<ParallelGatewayNode> parallelGateways = findParallelGateways(process);
        for (ParallelGatewayNode gateway : parallelGateways) {
            // Check at least 2 branches
            if (gateway.getBranches().size() < 2) {
                throw new InsufficientBranchesException(gateway);
            }

            // Check 'unir' is specified
            if (gateway.getJoinNode() == null) {
                throw new MissingJoinException(gateway);
            }

            // Check join node exists
            String joinNodeName = gateway.getJoinNode().getTargetNode();
            if (!findNodeByName(process, joinNodeName)) {
                throw new NodeNotFoundException(joinNodeName, gateway);
            }
        }
    }
}
```

#### TypeChecker
```java
public class TypeChecker {
    private SymbolTable symbolTable;

    public Type inferType(ExpressionNode expr) {
        return expr.accept(new TypeInferenceVisitor());
    }

    public boolean isCompatible(Type expected, Type actual) {
        // Exact match
        if (expected.equals(actual)) {
            return true;
        }

        // Numeric promotion: entero -> decimal
        if (expected.equals(Type.DECIMAL) && actual.equals(Type.INTEGER)) {
            return true;
        }

        // Any type converts to texto in concatenation context
        if (expected.equals(Type.TEXT)) {
            return true;
        }

        // null is compatible with any object/list type
        if (actual.equals(Type.NULL) &&
            (expected.equals(Type.OBJECT) || expected.equals(Type.LIST))) {
            return true;
        }

        return false;
    }

    public Type getOperatorResultType(
        Operator op,
        Type left,
        Type right
    ) {
        switch (op) {
            case PLUS:
                // texto + any -> texto
                if (left.equals(Type.TEXT) || right.equals(Type.TEXT)) {
                    return Type.TEXT;
                }
                // decimal + any_numeric -> decimal
                if (left.equals(Type.DECIMAL) || right.equals(Type.DECIMAL)) {
                    return Type.DECIMAL;
                }
                // entero + entero -> entero
                if (left.equals(Type.INTEGER) && right.equals(Type.INTEGER)) {
                    return Type.INTEGER;
                }
                throw new TypeMismatchException(op, left, right);

            case MINUS:
            case MULTIPLY:
            case DIVIDE:
                if (!isNumeric(left) || !isNumeric(right)) {
                    throw new TypeMismatchException(op, left, right);
                }
                // If either is decimal, result is decimal
                if (left.equals(Type.DECIMAL) || right.equals(Type.DECIMAL)) {
                    return Type.DECIMAL;
                }
                return Type.INTEGER;

            case MODULO:
                if (!left.equals(Type.INTEGER) || !right.equals(Type.INTEGER)) {
                    throw new TypeMismatchException(op, left, right);
                }
                return Type.INTEGER;

            case AND:
            case OR:
                if (!left.equals(Type.BOOLEAN) || !right.equals(Type.BOOLEAN)) {
                    throw new TypeMismatchException(op, left, right);
                }
                return Type.BOOLEAN;

            case LESS_THAN:
            case GREATER_THAN:
            case LESS_EQUAL:
            case GREATER_EQUAL:
                if (!isComparable(left, right)) {
                    throw new TypeMismatchException(op, left, right);
                }
                return Type.BOOLEAN;

            case EQUAL:
            case NOT_EQUAL:
                return Type.BOOLEAN;

            default:
                throw new UnsupportedOperatorException(op);
        }
    }

    private boolean isNumeric(Type type) {
        return type.equals(Type.INTEGER) || type.equals(Type.DECIMAL);
    }

    private boolean isComparable(Type left, Type right) {
        // Numbers are comparable with numbers
        if (isNumeric(left) && isNumeric(right)) {
            return true;
        }
        // Texts are comparable with texts
        if (left.equals(Type.TEXT) && right.equals(Type.TEXT)) {
            return true;
        }
        return false;
    }
}

class TypeInferenceVisitor implements ASTVisitor<Type> {
    @Override
    public Type visit(IntegerLiteralNode node) {
        return Type.INTEGER;
    }

    @Override
    public Type visit(DecimalLiteralNode node) {
        return Type.DECIMAL;
    }

    @Override
    public Type visit(StringLiteralNode node) {
        return Type.TEXT;
    }

    @Override
    public Type visit(BooleanLiteralNode node) {
        return Type.BOOLEAN;
    }

    @Override
    public Type visit(NullLiteralNode node) {
        return Type.NULL;
    }

    @Override
    public Type visit(ListLiteralNode node) {
        return Type.LIST;
    }

    @Override
    public Type visit(ObjectLiteralNode node) {
        return Type.OBJECT;
    }

    @Override
    public Type visit(IdentifierNode node) {
        Symbol symbol = symbolTable.lookupVariable(node.getName());
        if (symbol == null) {
            throw new VariableNotDeclaredException(node.getName(), node);
        }
        return symbol.getType();
    }

    @Override
    public Type visit(BinaryExpressionNode node) {
        Type leftType = node.getLeft().accept(this);
        Type rightType = node.getRight().accept(this);
        return typeChecker.getOperatorResultType(
            node.getOperator(),
            leftType,
            rightType
        );
    }

    @Override
    public Type visit(PropertyAccessNode node) {
        Type objectType = node.getObject().accept(this);
        if (!objectType.equals(Type.OBJECT)) {
            throw new PropertyAccessOnNonObjectException(node);
        }
        // Property access returns unknown type (dynamic)
        return Type.ANY;
    }

    @Override
    public Type visit(IndexAccessNode node) {
        Type listType = node.getList().accept(this);
        Type indexType = node.getIndex().accept(this);

        if (!listType.equals(Type.LIST)) {
            throw new IndexAccessOnNonListException(node);
        }
        if (!indexType.equals(Type.INTEGER)) {
            throw new NonIntegerIndexException(node);
        }

        // Index access returns unknown type (dynamic)
        return Type.ANY;
    }

    @Override
    public Type visit(FunctionCallNode node) {
        FunctionSymbol func = functionTable.lookupFunction(node.getName());
        if (func == null) {
            throw new FunctionNotDeclaredException(node.getName(), node);
        }
        return func.getReturnType();
    }

    @Override
    public Type visit(TernaryExpressionNode node) {
        Type condType = node.getCondition().accept(this);
        if (!condType.equals(Type.BOOLEAN)) {
            throw new NonBooleanConditionException(node);
        }

        Type trueType = node.getTrueExpression().accept(this);
        Type falseType = node.getFalseExpression().accept(this);

        // If both branches same type, that's the result
        if (trueType.equals(falseType)) {
            return trueType;
        }

        // Numeric promotion
        if (isNumeric(trueType) && isNumeric(falseType)) {
            return Type.DECIMAL;
        }

        // Otherwise, types differ (warning)
        warnings.add(new TernaryTypeMismatchWarning(
            node,
            trueType,
            falseType
        ));
        return Type.ANY;
    }
}
```

## 4. ERRORES Y ADVERTENCIAS

### 4.1 ERRORES SEMÁNTICOS (Compilation fails)

| Código | Nombre | Descripción | Ejemplo |
|--------|--------|-------------|---------|
| E001 | VARIABLE_NOT_DECLARED | Variable usada sin declaración | `x = y + 1` donde `y` no existe |
| E002 | VARIABLE_ALREADY_DECLARED | Redeclaración en mismo scope | `x = 1` seguido de `x = 2` (mismo scope) |
| E003 | FUNCTION_NOT_DECLARED | Función llamada sin definición | `resultado = foo()` donde `foo` no existe |
| E004 | FUNCTION_ALREADY_DECLARED | Función redeclarada | Dos funciones con nombre `calcular` |
| E005 | WRONG_ARGUMENT_COUNT | Número incorrecto de args | `add(1)` cuando `add` requiere 2 params |
| E006 | TYPE_MISMATCH | Tipos incompatibles | `x: entero = "texto"` |
| E007 | INVALID_OPERATION | Operación no válida para tipo | `verdadero + 10` |
| E008 | MISSING_RETURN | Falta retorno en función | Función con tipo != void sin retornar |
| E009 | PROCESS_NODE_NOT_FOUND | Nodo en go_to no existe | `go_to NodoInexistente` |
| E010 | PROCESS_NO_START | Proceso sin inicio | Proceso sin elemento `inicio` |
| E011 | PROCESS_NO_END | Proceso sin fin | Proceso sin ningún `fin` |
| E012 | MULTIPLE_START_ELEMENTS | Más de un inicio | Proceso con 2+ `inicio` |
| E013 | INVALID_GATEWAY_STRUCTURE | Gateway mal formado | Gateway paralelo sin `unir` |
| E014 | ENTRADA_IN_FUNCTION | `entrada` fuera de proceso | Usar `entrada` en función |
| E015 | GOTO_IN_FUNCTION | `go_to` fuera de proceso | Usar `go_to` en función |
| E016 | PROPERTY_ACCESS_NON_OBJECT | Acceso `.prop` en no-objeto | `42.propiedad` |
| E017 | INDEX_ACCESS_NON_LIST | Acceso `[i]` en no-lista | `"texto"[0]` |
| E018 | NON_BOOLEAN_CONDITION | Condición no booleana | `si 42 { ... }` |
| E019 | MODULE_NOT_FOUND | Módulo no encontrado | `importar "inexistente"` |
| E020 | INSUFFICIENT_BRANCHES | Gateway con < 2 ramas | Gateway paralelo con 1 rama |
| E021 | MISSING_JOIN | Gateway sin unir | Gateway paralelo sin `unir` |
| E022 | NON_INTEGER_INDEX | Índice no entero | `lista[3.14]` |
| E023 | INVALID_RETURN_TYPE | Tipo retorno incorrecto | Retornar entero en función -> texto |
| E024 | BREAK_OUTSIDE_LOOP | `break` fuera de bucle | `break` en if sin bucle padre |
| E025 | CONTINUE_OUTSIDE_LOOP | `continue` fuera de bucle | `continue` en función |

### 4.2 ADVERTENCIAS (Warnings, compilation succeeds)

| Código | Nombre | Descripción | Ejemplo |
|--------|--------|-------------|---------|
| W001 | VARIABLE_NEVER_USED | Variable declarada no usada | `x = 10` pero nunca se lee `x` |
| W002 | UNREACHABLE_CODE | Código inalcanzable | Código después de `retornar` |
| W003 | UNREACHABLE_NODE | Nodo proceso inalcanzable | Nodo que no se alcanza desde inicio |
| W004 | CONSTANT_CONDITION | Condición constante | `si verdadero` o `mientras falso` |
| W005 | DIVISION_BY_ZERO | División por cero literal | `x = 10 / 0` |
| W006 | INFINITE_LOOP | Posible bucle infinito | `mientras verdadero` sin break |
| W007 | SHADOWING_VARIABLE | Variable oculta otra | Variable local con nombre de global |
| W008 | PROPERTY_MAY_NOT_EXIST | Propiedad puede no existir | `obj.prop_desconocida` |
| W009 | INDEX_OUT_OF_RANGE | Índice puede estar fuera | `lista[100]` cuando lista tiene 3 items |
| W010 | TERNARY_TYPE_MISMATCH | Ramas ternario diferentes | `x ? 42 : "texto"` |
| W011 | IMPLICIT_TYPE_CONVERSION | Conversión implícita | `texto + entero` |
| W012 | UNUSED_PARAMETER | Parámetro no usado | Parámetro de función nunca leído |
| W013 | DUPLICATE_BRANCH | Ramas idénticas | if/else con código igual |
| W014 | MISSING_ELSE | Falta else exhaustivo | Gateway sin `sino` final |

## 5. FASES DEL ANÁLISIS SEMÁNTICO

### Fase 1: Recolección de Declaraciones
```
Input: ProgramNode (AST)
Output: SymbolTable, FunctionTable, ProcessTable pobladas

Algoritmo:
  1. Recorrer todas las declaraciones top-level
  2. Para cada FunctionDeclarationNode:
     - Agregar a FunctionTable
     - NO analizar cuerpo aún
  3. Para cada ProcessDeclarationNode:
     - Agregar a ProcessTable
     - Recolectar nombres de nodos (tareas, fins)
  4. Para cada ImportDeclarationNode:
     - Resolver módulo
     - Agregar símbolos importados
  5. Para cada variable global:
     - Agregar a SymbolTable global
```

### Fase 2: Análisis de Funciones
```
Input: FunctionTable poblada, SymbolTable con globales
Output: Errores/warnings de análisis de funciones

Algoritmo:
  Para cada función en FunctionTable:
    1. Crear nuevo scope de función
    2. Agregar parámetros al scope
    3. Analizar cuerpo:
       a. Verificar uso de variables
       b. Verificar tipos de expresiones
       c. Verificar llamadas a función
       d. Verificar returns
    4. Verificar todas las rutas retornan (si tipo != void)
    5. Salir del scope
```

### Fase 3: Análisis de Procesos
```
Input: ProcessTable poblada, FunctionTable
Output: Errores/warnings de estructura BPMN

Algoritmo:
  Para cada proceso en ProcessTable:
    1. Validar estructura BPMN:
       - Exactamente 1 inicio
       - Al menos 1 fin
       - Todos go_to apuntan a nodos existentes
    2. Análisis de alcanzabilidad desde inicio
    3. Crear scope de proceso (contexto)
    4. Para cada tarea:
       a. Analizar bloque accion
       b. Variables van al contexto del proceso
       c. Verificar que termina en go_to o retornar
    5. Validar gateways:
       - Exclusivos: condiciones booleanas
       - Paralelos: >= 2 ramas, unir especificado
    6. Verificar acceso a `entrada`
```

### Fase 4: Detección de Código Muerto
```
Algoritmo:
  1. Para cada función:
     - Detectar código después de retornar
     - Detectar bloques if/else inalcanzables
  2. Para cada proceso:
     - Nodos nunca alcanzados desde inicio
     - Tareas sin referencia en go_to
```

## 6. EJEMPLO COMPLETO DE ANÁLISIS

### Código de entrada:
```flowscript
importar "std/http" como http

PI = 3.14159  # Constante global

funcion calcular_area(radio: decimal) -> decimal {
    si radio < 0 {
        lanzar { tipo: "ErrorValidacion", mensaje: "Radio negativo" }
    }
    area = PI * radio * radio
    retornar area
}

proceso ProcesarDatos {
    inicio -> ObtenerDatos

    tarea ObtenerDatos {
        accion:
            url = entrada.url
            datos = http.get(url)
            si datos.ok {
                go_to ProcesarRespuesta
            } sino {
                go_to Error
            }
    }

    tarea ProcesarRespuesta {
        accion:
            area = calcular_area(datos.radio)
            imprimir("Área: " + area)
            go_to Fin
    }

    fin Fin
    fin Error
}
```

### Análisis semántico paso a paso:

#### Fase 1: Recolección
```
SymbolTable (Global):
  - PI: decimal (constante)

FunctionTable:
  - calcular_area(radio: decimal) -> decimal

ProcessTable:
  - ProcesarDatos
    Nodos: [ObtenerDatos, ProcesarRespuesta, Fin, Error]

ModuleTable:
  - http (alias para "std/http")
    Funciones: [get, post, put, delete]
```

#### Fase 2: Análisis de función `calcular_area`
```
Scope función:
  Parámetros: [radio: decimal]
  Variables locales: [area: decimal]

Verificaciones:
  ✅ Parámetro `radio` usado (línea 6, 9)
  ✅ Variable `area` declarada antes de uso
  ✅ Constante global `PI` accesible
  ✅ Todas las rutas retornan valor
  ✅ Tipo retornado (decimal) coincide con firma
  ✅ Condición `radio < 0` es booleana
  ✅ Expresión lanzada es objeto
```

#### Fase 3: Análisis de proceso `ProcesarDatos`
```
Validación estructura:
  ✅ Exactamente 1 inicio (-> ObtenerDatos)
  ✅ 2 fins (Fin, Error)
  ✅ go_to ProcesarRespuesta: nodo existe ✅
  ✅ go_to Error: nodo existe ✅
  ✅ go_to Fin: nodo existe ✅

Alcanzabilidad desde inicio:
  inicio -> ObtenerDatos
           ObtenerDatos -> ProcesarRespuesta (rama si)
           ObtenerDatos -> Error (rama sino)
           ProcesarRespuesta -> Fin

  Alcanzables: [ObtenerDatos, ProcesarRespuesta, Fin, Error] ✅
  Todos los nodos son alcanzables.

Análisis de tareas:

  Tarea: ObtenerDatos
    Contexto proceso:
      Variables: [url: texto, datos: objeto]
    Verificaciones:
      ✅ `entrada.url`: entrada es objeto ✅, acceso .url válido
      ✅ `http.get(url)`: módulo http importado ✅, función get existe ✅
      ✅ `datos.ok`: datos es objeto ✅, acceso .ok válido
      ✅ Condición si es booleana (datos.ok) ✅
      ✅ Todas las ramas terminan en go_to ✅

  Tarea: ProcesarRespuesta
    Contexto proceso (heredado):
      Variables disponibles: [url: texto, datos: objeto, area: decimal]
    Verificaciones:
      ✅ `datos.radio`: datos es objeto ✅, acceso .radio válido
      ✅ `calcular_area(datos.radio)`: función existe ✅
         Argumento 1: esperado decimal, actual ANY (object.property) ⚠️
         → Warning W011: Conversión implícita object->decimal
      ✅ Variable `area` agregada al contexto
      ✅ Tarea termina en go_to ✅
```

### Resultado final:
```
✅ Análisis completado
❌ Errores: 0
⚠️ Advertencias: 1
  W011: Conversión implícita de tipo en línea 29 (object property -> decimal)
```

## 7. IMPLEMENTACIÓN: ORDEN DE DESARROLLO

### Paso 1: Estructuras de datos básicas
1. `Symbol`, `SymbolTable`, `Scope`
2. `Type`, `TypeSystem`
3. `FunctionSymbol`, `FunctionTable`
4. `ProcessSymbol`, `ProcessTable`

### Paso 2: Visitor base
1. Interfaz `ASTVisitor<T>`
2. Implementar `accept()` en TODOS los nodos AST

### Paso 3: Type Checker
1. `TypeChecker` class
2. `TypeInferenceVisitor`
3. Reglas de compatibilidad
4. Inferencia de tipos de expresiones

### Paso 4: Semantic Analyzer
1. Fase 1: Declaration collector
2. Fase 2: Function analyzer
3. Fase 3: Process analyzer
4. Fase 4: Dead code detector

### Paso 5: Error Reporting
1. `SemanticError` class hierarchy
2. `SemanticWarning` class hierarchy
3. `ErrorReporter` con mensajes localizados

### Paso 6: Testing
1. Tests unitarios por categoría de error
2. Tests de integración con programas completos
3. Tests de casos edge

---

**Este documento define la especificación completa del análisis semántico de FlowScript.**
**Todos los casos de error, tipos de validación y algoritmos están documentados aquí.**
