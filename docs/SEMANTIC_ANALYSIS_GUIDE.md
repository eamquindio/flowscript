# Guía Didáctica del Análisis Semántico de FlowScript

## Introducción

El análisis semántico es la tercera fase del compilador, después del análisis léxico y sintáctico. Su propósito es **verificar que el programa tenga sentido desde el punto de vista del lenguaje**, más allá de su estructura gramatical correcta.

### ¿Qué valida el análisis semántico?

1. **Declaraciones duplicadas**: No puedes declarar dos funciones con el mismo nombre
2. **Referencias válidas**: Las variables y funciones que usas deben estar declaradas
3. **Tipos compatibles**: Las operaciones deben usar tipos adecuados
4. **Flujo de control correcto**: Reglas específicas del lenguaje (ej: `ir_a` solo en procesos)
5. **Estructura de procesos**: Los procesos deben tener inicio y fin

---

## Arquitectura del Análisis Semántico

### Componentes Principales

```
SemanticAnalyzer
├── SymbolTable (tabla de símbolos/variables)
├── FunctionTable (tabla de funciones)
├── ProcessTable (tabla de procesos)
│   └── TaskTable (tareas dentro de cada proceso)
└── List<SemanticError> (errores encontrados)
```

### Flujo del Análisis

```
1. Primera Pasada: Recolectar Declaraciones
   └── Registrar todas las funciones y procesos

2. Segunda Pasada: Visitor Pattern
   └── Recorrer el AST validando cada nodo

3. Tercera Pasada: Validaciones Finales
   └── Verificar restricciones globales
```

---

## Análisis Nodo por Nodo

### 1. ProgramNode

**QUÉ ES:** El nodo raíz del programa que contiene todas las declaraciones.

**QUÉ SE HACE:**
1. Recorrer todas las declaraciones del programa
2. Visitar cada una para analizarla individualmente

**POR QUÉ:** Necesitamos procesar todas las declaraciones de top-level (funciones y procesos) para validar el programa completo.

**CÓDIGO:**
```java
@Override
public void visit(ProgramNode node) {
    for (DeclarationNode declaration : node.getDeclarations()) {
        declaration.accept(this);
    }
}
```

---

### 2. FunctionDeclarationNode

**QUÉ ES:** Una declaración de función con nombre, parámetros, tipo de retorno y cuerpo.

**QUÉ SE HACE:**
1. **Buscar la función en la tabla**: Ya fue registrada en la primera pasada
2. **Crear un nuevo scope**: Las variables dentro de la función son locales
3. **Declarar los parámetros**: Los parámetros son variables inicializadas
4. **Marcar parámetros como inicializados**: Los parámetros siempre tienen valor
5. **Analizar el cuerpo de la función**: Visitar el bloque de código
6. **Salir del scope**: Eliminar las variables locales
7. **Limpiar el contexto**: Ya no estamos dentro de una función

**POR QUÉ:**
- Cada función tiene su propio espacio de nombres (scope)
- Los parámetros deben estar disponibles dentro de la función
- Necesitamos validar que el cuerpo de la función sea correcto

**CÓDIGO:**
```java
@Override
public void visit(FunctionDeclarationNode node) {
    currentFunction = functionTable.lookup(node.getName());
    enterScope("function_" + node.getName());

    // Declarar parámetros
    for (ParameterNode param : node.getParameters()) {
        Symbol paramSymbol = new Symbol(
            param.getName(),
            typeToString(param.getType()),
            0, 0,
            currentSymbolTable.getScopeName()
        );
        paramSymbol.setInitialized(true);  // Los parámetros siempre están inicializados
        currentSymbolTable.declare(paramSymbol);
    }

    node.getBody().accept(this);  // Analizar el cuerpo
    exitScope();
    currentFunction = null;
}
```

**EJEMPLO:**
```flowscript
funcion suma(a: entero, b: entero) -> entero {
    // Aquí 'a' y 'b' están disponibles como variables
    variable resultado: entero = a + b
    retornar resultado
}
```

---

### 3. ProcessDeclarationNode

**QUÉ ES:** Una declaración de proceso BPMN con elementos (tareas, gateways, etc.).

**QUÉ SE HACE:**
1. **Buscar el proceso en la tabla**: Ya fue registrado en la primera pasada
2. **Establecer contexto**: Recordar que estamos dentro de un proceso
3. **Analizar cada elemento**: Visitar tareas, gateways, etc.
4. **Limpiar contexto**: Ya no estamos dentro del proceso

**POR QUÉ:**
- Los procesos tienen reglas especiales (ej: `ir_a` solo funciona aquí)
- Necesitamos validar que los elementos del proceso sean correctos
- Debemos verificar referencias entre tareas

**CÓDIGO:**
```java
@Override
public void visit(ProcessDeclarationNode node) {
    currentProcess = processTable.lookup(node.getName());

    for (ProcessElementNode element : node.getElements()) {
        element.accept(this);
    }

    currentProcess = null;
}
```

**EJEMPLO:**
```flowscript
proceso ValidarPedido {
    inicio -> Validar

    tarea Validar {
        accion:
        validar_datos()
        ir_a FinExito
    }

    fin FinExito
}
```

---

### 4. BlockNode

**QUÉ ES:** Un bloque de código delimitado por llaves `{ }`.

**QUÉ SE HACE:**
1. **Crear un nuevo scope**: Las variables del bloque son locales
2. **Analizar cada statement**: Visitar todas las sentencias del bloque
3. **Salir del scope**: Las variables del bloque dejan de existir

**POR QUÉ:**
- Los bloques crean alcance (scope) para variables
- Las variables declaradas en un bloque no son visibles fuera de él

**CÓDIGO:**
```java
@Override
public void visit(BlockNode node) {
    enterScope("block");

    for (StatementNode statement : node.getStatements()) {
        statement.accept(this);
    }

    exitScope();
}
```

**EJEMPLO:**
```flowscript
{
    variable x: entero = 10
    // 'x' existe aquí
}
// 'x' NO existe aquí - error semántico si intentas usarla
```

---

### 5. VariableDeclarationStatementNode

**QUÉ ES:** Declaración de una variable con nombre, tipo opcional e inicialización opcional.

**QUÉ SE HACE:**
1. **Verificar duplicados**: La variable NO debe estar ya declarada en este scope
2. **Analizar inicializador**: Si tiene valor inicial, validarlo
3. **Crear símbolo**: Registrar la variable en la tabla de símbolos
4. **Marcar estado**: Indicar si está inicializada o no

**POR QUÉ:**
- No podemos tener dos variables con el mismo nombre en el mismo scope
- Debemos validar que el valor inicial sea correcto
- Necesitamos saber si una variable tiene valor antes de usarla

**CÓDIGO:**
```java
@Override
public void visit(VariableDeclarationStatementNode node) {
    if (currentSymbolTable.isDeclaredLocally(node.getName())) {
        addError(SemanticError.ErrorType.DUPLICATE_DECLARATION,
            "Variable '" + node.getName() + "' ya declarada", 0, 0);
        return;
    }

    if (node.getValue() != null) {
        node.getValue().accept(this);  // Validar el inicializador
    }

    Symbol symbol = new Symbol(
        node.getName(),
        "unknown",  // En versión completa: obtener tipo
        0, 0,
        currentSymbolTable.getScopeName()
    );
    symbol.setInitialized(node.getValue() != null);
    currentSymbolTable.declare(symbol);
}
```

**EJEMPLOS:**
```flowscript
variable x: entero = 10        // Inicializada
variable y: texto               // No inicializada
variable x: decimal = 3.14     // ERROR: 'x' ya declarada
```

---

### 6. AssignmentStatementNode

**QUÉ ES:** Asignación de un valor a una variable existente.

**QUÉ SE HACE:**
1. **Verificar que la variable existe**: Debe estar declarada previamente
2. **Analizar el valor**: Validar la expresión del lado derecho
3. **Marcar como inicializada**: La variable ahora tiene valor
4. **Marcar como usada**: Registrar que se está usando

**POR QUÉ:**
- No podemos asignar a una variable que no existe
- El valor asignado debe ser válido
- Nos ayuda a detectar variables no usadas

**CÓDIGO:**
```java
@Override
public void visit(AssignmentStatementNode node) {
    // En versión completa: extraer nombre del target
    // Por ahora asumimos que target es IdentifierNode

    Symbol symbol = currentSymbolTable.lookup(targetName);
    if (symbol == null) {
        addError(SemanticError.ErrorType.UNDECLARED_VARIABLE,
            "Variable '" + targetName + "' no declarada", 0, 0);
    } else {
        symbol.setInitialized(true);
        symbol.setUsed(true);
    }

    node.getValue().accept(this);  // Validar el valor
}
```

**EJEMPLOS:**
```flowscript
variable x: entero
x = 10           // OK: asignación válida
y = 20           // ERROR: 'y' no declarada
```

---

### 7. ReturnStatementNode

**QUÉ ES:** Sentencia de retorno en una función.

**QUÉ SE HACE:**
1. **Verificar contexto**: Debe estar dentro de una función
2. **Analizar el valor**: Si retorna algo, validar la expresión
3. **Verificar tipo**: El valor debe coincidir con el tipo de retorno de la función

**POR QUÉ:**
- `return` solo tiene sentido dentro de funciones
- El tipo retornado debe coincidir con la declaración de la función

**CÓDIGO:**
```java
@Override
public void visit(ReturnStatementNode node) {
    if (currentFunction == null) {
        addError(SemanticError.ErrorType.INVALID_RETURN,
            "return fuera de función", 0, 0);
        return;
    }

    if (node.getValue() != null) {
        node.getValue().accept(this);
        // En versión completa: verificar tipo
    }
}
```

**EJEMPLOS:**
```flowscript
funcion suma(a: entero, b: entero) -> entero {
    retornar a + b    // OK
}

retornar 42          // ERROR: return fuera de función
```

---

### 8. IfStatementNode

**QUÉ ES:** Sentencia condicional (if-else).

**QUÉ SE HACE:**
1. **Analizar condiciones**: Validar cada expresión condicional
2. **Analizar bloques**: Validar el código de cada rama
3. **Verificar tipos**: Las condiciones deben ser booleanas

**POR QUÉ:**
- Las condiciones deben ser expresiones válidas
- El código de cada rama debe ser correcto

**CÓDIGO:**
```java
@Override
public void visit(IfStatementNode node) {
    for (IfStatementNode.ConditionalBranch branch : node.getBranches()) {
        if (branch.getCondition() != null) {
            branch.getCondition().accept(this);
            // En versión completa: verificar que sea booleano
        }
        branch.getThenBlock().accept(this);
    }
}
```

**EJEMPLO:**
```flowscript
si (x > 0) {
    // rama verdadera
} sino {
    // rama falsa
}
```

---

### 9. ForStatementNode

**QUÉ ES:** Bucle for-each para iterar sobre colecciones.

**QUÉ SE HACE:**
1. **Crear scope**: El iterador es local al loop
2. **Declarar iterador**: Variable que toma cada valor
3. **Marcar como inicializada**: El iterador siempre tiene valor
4. **Analizar iterable**: Validar la colección a iterar
5. **Analizar cuerpo**: Validar el código del loop
6. **Salir del scope**: El iterador deja de existir

**POR QUÉ:**
- El iterador solo existe dentro del loop
- La colección a iterar debe ser válida

**CÓDIGO:**
```java
@Override
public void visit(ForStatementNode node) {
    enterScope("for_loop");

    Symbol iteratorSymbol = new Symbol(
        node.getIteratorVariable(),
        "unknown",  // Tipo inferido del iterable
        0, 0,
        currentSymbolTable.getScopeName()
    );
    iteratorSymbol.setInitialized(true);
    currentSymbolTable.declare(iteratorSymbol);

    node.getIterable().accept(this);
    node.getBody().accept(this);

    exitScope();
}
```

**EJEMPLO:**
```flowscript
para item en lista {
    // 'item' existe aquí
    imprimir(item)
}
// 'item' NO existe aquí
```

---

### 10. GotoStatementNode

**QUÉ ES:** Sentencia `ir_a` para navegar entre tareas en un proceso.

**QUÉ SE HACE:**
1. **Verificar contexto**: Solo válido dentro de un proceso
2. **Verificar destino**: La tarea destino debe existir
3. **Marcar referencia**: Registrar que la tarea es usada

**POR QUÉ:**
- `ir_a` es específico de procesos BPMN
- El destino debe ser una tarea válida del proceso
- Ayuda a detectar tareas inalcanzables

**CÓDIGO:**
```java
@Override
public void visit(GotoStatementNode node) {
    if (currentProcess == null) {
        addError(SemanticError.ErrorType.GOTO_OUTSIDE_PROCESS,
            "ir_a solo puede usarse dentro de un proceso", 0, 0);
        return;
    }

    TaskSymbol targetTask = currentProcess.getTaskTable().lookup(node.getTargetName());
    if (targetTask == null) {
        addError(SemanticError.ErrorType.INVALID_GOTO_TARGET,
            "Tarea '" + node.getTargetName() + "' no existe", 0, 0);
    } else {
        currentProcess.getTaskTable().markAsReferenced(node.getTargetName());
    }
}
```

**EJEMPLOS:**
```flowscript
proceso MiProceso {
    tarea A {
        accion:
        ir_a B      // OK: B existe
        ir_a C      // ERROR: C no existe
    }

    tarea B {
        accion:
        ir_a fin
    }

    fin
}

funcion foo() {
    ir_a algo      // ERROR: ir_a fuera de proceso
}
```

---

### 11. FunctionCallNode

**QUÉ ES:** Llamada a una función con argumentos.

**QUÉ SE HACE:**
1. **Analizar la función**: Puede ser un identificador o expresión
2. **Verificar declaración**: La función debe existir
3. **Verificar argumentos**: El número debe coincidir
4. **Analizar cada argumento**: Validar todas las expresiones

**POR QUÉ:**
- Solo podemos llamar funciones que existen
- El número de argumentos debe coincidir con la declaración
- Los argumentos deben ser expresiones válidas

**CÓDIGO:**
```java
@Override
public void visit(FunctionCallNode node) {
    node.getFunction().accept(this);

    if (node.getFunction() instanceof IdentifierNode) {
        String functionName = ((IdentifierNode) node.getFunction()).getName();
        FunctionSymbol function = functionTable.lookup(functionName);

        if (function == null) {
            addError(SemanticError.ErrorType.UNDECLARED_FUNCTION,
                "Función '" + functionName + "' no declarada", 0, 0);
        } else if (function.getParameterCount() != node.getArguments().size()) {
            addError(SemanticError.ErrorType.INVALID_ARGUMENT_COUNT,
                "Función '" + functionName + "' espera " + function.getParameterCount() +
                " argumentos, pero se pasaron " + node.getArguments().size(), 0, 0);
        }
    }

    for (ExpressionNode arg : node.getArguments()) {
        arg.accept(this);
    }
}
```

**EJEMPLOS:**
```flowscript
funcion suma(a: entero, b: entero) -> entero {
    retornar a + b
}

suma(1, 2)      // OK: 2 argumentos
suma(1)         // ERROR: faltan argumentos
suma(1, 2, 3)   // ERROR: demasiados argumentos
resta(1, 2)     // ERROR: función no declarada
```

---

### 12. IdentifierNode

**QUÉ ES:** Referencia a una variable por su nombre.

**QUÉ SE HACE:**
1. **Buscar la variable**: Debe estar declarada en algún scope
2. **Reportar error**: Si no existe, es un error semántico
3. **Marcar como usada**: Registrar que se está usando

**POR QUÉ:**
- No podemos usar variables que no existen
- Nos ayuda a detectar variables declaradas pero nunca usadas
- Validamos el alcance (scope) correcto

**CÓDIGO:**
```java
@Override
public void visit(IdentifierNode node) {
    Symbol symbol = currentSymbolTable.lookup(node.getName());

    if (symbol == null) {
        addError(SemanticError.ErrorType.UNDECLARED_VARIABLE,
            "Variable '" + node.getName() + "' no declarada", 0, 0);
    } else {
        symbol.setUsed(true);
    }
}
```

**EJEMPLOS:**
```flowscript
variable x: entero = 10
variable y: entero = x    // OK: 'x' está declarada
variable z: entero = w    // ERROR: 'w' no declarada
```

---

### 13. Expresiones y Literales

**QUÉ SON:** Nodos que representan valores y operaciones.

**QUÉ SE HACE:**
- **BinaryExpressionNode**: Analizar ambos operandos
- **UnaryExpressionNode**: Analizar el operando
- **Literales (Integer, String, etc.)**: No requieren validación

**POR QUÉ:**
- Las expresiones anidadas deben ser válidas
- Los operandos deben ser correctos
- En versión completa: verificar tipos compatibles

**CÓDIGO:**
```java
@Override
public void visit(BinaryExpressionNode node) {
    node.getLeft().accept(this);   // Validar lado izquierdo
    node.getRight().accept(this);  // Validar lado derecho
    // En versión completa: verificar tipos compatibles
}

@Override
public void visit(UnaryExpressionNode node) {
    node.getOperand().accept(this);
    // En versión completa: verificar tipo compatible
}

// Los literales no necesitan validación
@Override public void visit(IntegerLiteralNode node) {}
@Override public void visit(StringLiteralNode node) {}
@Override public void visit(BooleanLiteralNode node) {}
```

**EJEMPLOS:**
```flowscript
variable x: entero = 10 + 20           // BinaryExpression: 10 + 20
variable y: booleano = no verdadero    // UnaryExpression: no verdadero
variable z: entero = 42                // Literal: 42
```

---

### 14. Elementos de Proceso

#### StartElementNode

**QUÉ ES:** Elemento de inicio del proceso que apunta a la primera tarea.

**QUÉ SE HACE:**
1. **Verificar destino**: La tarea destino debe existir
2. **Marcar referencia**: La tarea destino se marca como alcanzable

**POR QUÉ:** El proceso debe comenzar en una tarea válida.

```java
@Override
public void visit(StartElementNode node) {
    if (currentProcess != null) {
        TaskSymbol task = currentProcess.getTaskTable().lookup(node.getTargetTaskName());
        if (task == null) {
            addError(SemanticError.ErrorType.UNDECLARED_TASK,
                "Tarea '" + node.getTargetTaskName() + "' no declarada", 0, 0);
        } else {
            currentProcess.getTaskTable().markAsReferenced(node.getTargetTaskName());
        }
    }
}
```

#### TaskElementNode

**QUÉ ES:** Una tarea del proceso con acciones.

**QUÉ SE HACE:**
1. **Analizar acciones**: Validar cada statement de la tarea

**POR QUÉ:** Las acciones de la tarea deben ser código válido.

```java
@Override
public void visit(TaskElementNode node) {
    for (StatementNode action : node.getActions()) {
        action.accept(this);
    }
}
```

#### EndElementNode

**QUÉ ES:** Elemento de fin del proceso.

**QUÉ SE HACE:** Nada - es un nodo terminal.

**POR QUÉ:** Solo marca el fin del proceso.

```java
@Override
public void visit(EndElementNode node) {
    // Nodo terminal - no requiere validación
}
```

---

## Validaciones Finales

Después de recorrer todo el AST, se ejecutan validaciones globales:

### 1. Procesos sin Inicio

```java
for (ProcessSymbol process : processTable.getProcesses()) {
    if (!process.hasStart()) {
        addError(SemanticError.ErrorType.PROCESS_NO_START,
            "Proceso '" + process.getName() + "' no tiene elemento 'inicio'",
            process.getLine(), process.getColumn());
    }
}
```

**POR QUÉ:** Todo proceso debe tener un punto de entrada.

### 2. Procesos sin Fin

```java
if (!process.hasEnd()) {
    addError(SemanticError.ErrorType.PROCESS_NO_END,
        "Proceso '" + process.getName() + "' no tiene elemento 'fin'",
        process.getLine(), process.getColumn());
}
```

**POR QUÉ:** Todo proceso debe tener al menos un punto de salida.

### 3. Tareas Inalcanzables

```java
List<TaskSymbol> unreachable = process.getTaskTable().getUnreferencedTasks();
for (TaskSymbol task : unreachable) {
    addError(SemanticError.ErrorType.TASK_UNREACHABLE,
        "Tarea '" + task.getName() + "' es posiblemente inalcanzable",
        task.getLine(), task.getColumn());
}
```

**POR QUÉ:** Si ningún `ir_a` apunta a una tarea, nunca se ejecutará.

---

## Ejemplo Completo de Análisis

```flowscript
funcion calcular(x: entero) -> entero {
    variable resultado: entero = x * 2
    retornar resultado
}

proceso Validar {
    inicio -> Verificar

    tarea Verificar {
        accion:
        variable valor: entero = calcular(10)
        si (valor > 0) {
            ir_a FinExito
        } sino {
            ir_a FinError
        }
    }

    fin FinExito
    fin FinError
}
```

### Análisis Paso a Paso

1. **Primera Pasada - Recolectar Declaraciones:**
   - Registrar función `calcular(entero) -> entero`
   - Registrar proceso `Validar`
   - Registrar tareas: `Verificar`, `FinExito`, `FinError`

2. **Segunda Pasada - Visitor:**
   - **Función calcular:**
     - Crear scope `function_calcular`
     - Declarar parámetro `x: entero`
     - Declarar variable `resultado: entero`
     - Validar expresión `x * 2`
     - Validar `return resultado`

   - **Proceso Validar:**
     - Verificar inicio apunta a `Verificar` ✓
     - Tarea Verificar:
       - Declarar variable `valor`
       - Validar llamada `calcular(10)` ✓
       - Validar if condition `valor > 0` ✓
       - Validar `ir_a FinExito` ✓
       - Validar `ir_a FinError` ✓

3. **Tercera Pasada - Validaciones Finales:**
   - Proceso tiene inicio ✓
   - Proceso tiene fin ✓
   - Todas las tareas son referenciadas ✓

**Resultado:** Programa semánticamente correcto ✓

---

## Errores Comunes y Cómo Detectarlos

### 1. Variable No Declarada

```flowscript
funcion foo() {
    x = 10  // ERROR: 'x' no declarada
}
```

**Detección:** En `IdentifierNode.visit()`, buscar en `currentSymbolTable`. Si no existe, error.

### 2. Función No Declarada

```flowscript
calcular(10)  // ERROR: si 'calcular' no existe
```

**Detección:** En `FunctionCallNode.visit()`, buscar en `functionTable`. Si no existe, error.

### 3. Ir_a Fuera de Proceso

```flowscript
funcion foo() {
    ir_a alguna_tarea  // ERROR: ir_a solo en procesos
}
```

**Detección:** En `GotoStatementNode.visit()`, verificar `currentProcess != null`.

### 4. Proceso Sin Inicio

```flowscript
proceso MiProceso {
    // ERROR: falta 'inicio -> ...'
    tarea A { accion: ... }
    fin
}
```

**Detección:** En validaciones finales, verificar `process.hasStart()`.

### 5. Argumentos Incorrectos

```flowscript
funcion suma(a: entero, b: entero) -> entero { ... }
suma(1)  // ERROR: faltan argumentos
```

**Detección:** En `FunctionCallNode.visit()`, comparar `function.getParameterCount()` con `node.getArguments().size()`.

---

## Conclusión

El análisis semántico es esencial para garantizar que el programa sea correcto más allá de su sintaxis. Utiliza el patrón Visitor para recorrer el AST y mantiene tablas de símbolos para rastrear declaraciones y usos.

**Puntos Clave:**
1. **Tres pasadas**: Recolección, Visitor, Validaciones finales
2. **Tablas de símbolos**: Mantener información sobre declaraciones
3. **Scoping**: Gestionar alcance de variables con scopes anidados
4. **Contexto**: Saber dónde estamos (función, proceso, loop, etc.)
5. **Validaciones específicas**: Cada tipo de nodo tiene sus reglas

Esta documentación sirve como recurso didáctico para comprender qué hace cada parte del análisis semántico y por qué es necesario.
