# FlowScript AST y Parsers - Documentación Completa

## Arquitectura General

FlowScript utiliza un diseño de parser modular que separa claramente las dos partes fundamentales del lenguaje:

1. **Process (Orquestación)**: Modelado de procesos de negocio estilo BPMN
2. **Functions (Implementación)**: Lógica de negocio tradicional

Esta separación se refleja en la estructura de paquetes y clases.

## Estructura de Paquetes

```
com.flowscript.sintactic/
├── Parser.java                 # Clase base para todos los parsers
├── ProgramParser.java           # Parser principal del programa
├── DeclarationParser.java       # Parser para declaraciones globales
├── ExpressionParser.java        # Parser para expresiones
├── StatementParser.java         # Parser para statements
│
├── process/                     # Parsers específicos de procesos
│   ├── ProcessDeclarationParser.java
│   ├── ProcessElementParser.java
│   ├── TaskElementParser.java
│   └── GatewayParser.java
│
└── functions/                   # Parsers específicos de funciones
    ├── FunctionDeclarationParser.java
    └── ParameterListParser.java

com.flowscript.sintactic.ast/
├── ASTNode.java                # Nodo base del AST
├── DeclarationNode.java         # Base para declaraciones
├── ExpressionNode.java          # Base para expresiones
├── StatementNode.java           # Base para statements
├── ProgramNode.java             # Nodo raíz del programa
├── TypeNode.java                # Nodo para tipos de datos
│
├── process/                     # AST de elementos de proceso
│   ├── ProcessDeclarationNode.java
│   ├── ProcessElementNode.java
│   ├── StartElementNode.java
│   ├── EndElementNode.java
│   ├── TaskElementNode.java
│   ├── TaskBodyNode.java
│   ├── ExclusiveGatewayNode.java
│   ├── ParallelGatewayNode.java
│   ├── WhenClauseNode.java
│   ├── ElseClauseNode.java
│   ├── ParallelBranchNode.java
│   └── JoinClauseNode.java
│
├── functions/                   # AST de funciones
│   ├── FunctionDeclarationNode.java
│   └── ParameterNode.java
│
├── statements/                  # AST de statements
│   ├── BlockNode.java
│   ├── IfStatementNode.java
│   ├── ElseIfClauseNode.java
│   ├── ExpressionStatementNode.java
│   ├── TryStatementNode.java
│   ├── ThrowStatementNode.java
│   ├── ReturnStatementNode.java
│   ├── GotoStatementNode.java
│   ├── ForStatementNode.java
│   └── VariableDeclarationStatementNode.java
│
├── expressions/                 # AST de expresiones
│   ├── BinaryExpressionNode.java
│   ├── UnaryExpressionNode.java
│   ├── TernaryExpressionNode.java
│   ├── IdentifierNode.java
│   ├── LiteralNode.java
│   ├── PropertyAccessNode.java
│   ├── IndexAccessNode.java
│   ├── FunctionCallNode.java
│   ├── ObjectLiteralNode.java
│   ├── ObjectMemberNode.java
│   └── ListLiteralNode.java
│
└── declarations/                # AST de declaraciones globales
    └── ImportDeclarationNode.java
```

## Clases AST (Abstract Syntax Tree)

### Nodos Base

#### ASTNode
**Responsabilidad**: Nodo base abstracto para todos los nodos del AST.
- Mantiene información de posición (línea, columna)
- Define método abstracto `getNodeType()`

#### ProgramNode
**Responsabilidad**: Nodo raíz que representa el programa completo.
- Contiene lista de declaraciones globales (imports, funciones, procesos)

#### DeclarationNode
**Responsabilidad**: Base abstracta para declaraciones globales.
- Define método abstracto `getDeclaredName()`

#### ExpressionNode
**Responsabilidad**: Base abstracta para todas las expresiones.
- Define método abstracto `getExpressionType()`

#### StatementNode
**Responsabilidad**: Base abstracta para todos los statements.

### Nodos de Proceso (BPMN)

#### ProcessDeclarationNode
**Responsabilidad**: Representa una declaración de proceso completa.
- Contiene el nombre del proceso
- Lista de elementos del proceso (start, task, gateway, end)

#### ProcessElementNode
**Responsabilidad**: Base abstracta para elementos dentro de un proceso.
- Define tipo de elemento y nombre

#### StartElementNode
**Responsabilidad**: Punto de entrada del proceso.
- Define tarea destino inicial
- Sintaxis: `start -> TaskName`

#### EndElementNode
**Responsabilidad**: Punto de terminación del proceso.
- Define nombre del punto final
- Sintaxis: `end EndName`

#### TaskElementNode
**Responsabilidad**: Tarea ejecutable dentro del proceso.
- Contiene nombre y cuerpo (TaskBodyNode)
- Sintaxis: `task TaskName { action: ... }`

#### TaskBodyNode
**Responsabilidad**: Cuerpo de una tarea con sus statements.
- Lista de statements a ejecutar

#### ExclusiveGatewayNode
**Responsabilidad**: Gateway exclusivo (XOR) para decisiones.
- Múltiples condiciones when
- Cláusula else opcional
- Sintaxis: `gateway Name { when cond -> Task; else -> Task }`

#### ParallelGatewayNode
**Responsabilidad**: Gateway paralelo (AND) para concurrencia.
- Múltiples ramas paralelas
- Punto de sincronización join
- Sintaxis: `gateway Name parallel { branch -> Task; join -> Task }`

#### WhenClauseNode
**Responsabilidad**: Condición en gateway exclusivo.
- Expresión de condición
- Tarea destino

#### ElseClauseNode
**Responsabilidad**: Rama por defecto en gateway exclusivo.
- Tarea destino cuando no se cumple ningún when

#### ParallelBranchNode
**Responsabilidad**: Rama en gateway paralelo.
- Tarea destino de la rama

#### JoinClauseNode
**Responsabilidad**: Punto de sincronización en gateway paralelo.
- Tarea destino después de la sincronización

### Nodos de Función

#### FunctionDeclarationNode
**Responsabilidad**: Declaración completa de función.
- Nombre, parámetros, tipo de retorno, cuerpo
- Sintaxis: `function name(params) -> type { ... }`

#### ParameterNode
**Responsabilidad**: Parámetro de función.
- Nombre y tipo del parámetro
- Sintaxis: `paramName: type`

### Nodos de Statement

#### BlockNode
**Responsabilidad**: Bloque de statements entre llaves.
- Lista de statements
- Sintaxis: `{ statement1; statement2; }`

#### IfStatementNode
**Responsabilidad**: Statement condicional if/else_if/else.
- Condición, then-statement, else-if clauses, else-statement
- Sintaxis: `if cond { ... } else_if cond2 { ... } else { ... }`

#### ElseIfClauseNode
**Responsabilidad**: Cláusula else_if en if statement.
- Condición y statement asociado

#### TryStatementNode
**Responsabilidad**: Manejo de excepciones.
- Bloque try, variable de excepción, bloque catch
- Sintaxis: `try { ... } catch (e) { ... }`

#### ThrowStatementNode
**Responsabilidad**: Lanzamiento de excepciones.
- Expresión a lanzar
- Sintaxis: `throw expression`

#### ReturnStatementNode
**Responsabilidad**: Retorno de función.
- Expresión opcional a retornar
- Sintaxis: `return expression`

#### GotoStatementNode
**Responsabilidad**: Transferencia de control en procesos.
- Nombre de tarea destino
- Sintaxis: `go_to TaskName`

#### ForStatementNode
**Responsabilidad**: Iteración sobre colecciones.
- Variable, expresión iterable, cuerpo
- Sintaxis: `for each item in list { ... }`

#### VariableDeclarationStatementNode
**Responsabilidad**: Declaración y asignación de variable.
- Nombre y valor inicial
- Sintaxis: `varName = expression`

#### ExpressionStatementNode
**Responsabilidad**: Statement que es solo una expresión.
- Envuelve una expresión como statement

### Nodos de Expresión

#### BinaryExpressionNode
**Responsabilidad**: Operaciones binarias.
- Operandos izquierdo y derecho, operador
- Ejemplos: `a + b`, `x == y`, `p and q`

#### UnaryExpressionNode
**Responsabilidad**: Operaciones unarias.
- Operando y operador
- Ejemplos: `not x`, `-value`

#### TernaryExpressionNode
**Responsabilidad**: Operador ternario condicional.
- Condición, expresión verdadera, expresión falsa
- Sintaxis: `cond ? trueExpr : falseExpr`

#### IdentifierNode
**Responsabilidad**: Referencia a variable o función.
- Nombre del identificador

#### LiteralNode
**Responsabilidad**: Valores literales.
- Valor literal (número, string, booleano, null)

#### PropertyAccessNode
**Responsabilidad**: Acceso a propiedad de objeto.
- Objeto y nombre de propiedad
- Sintaxis: `object.property`

#### IndexAccessNode
**Responsabilidad**: Acceso indexado a lista.
- Lista e índice
- Sintaxis: `list[index]`

#### FunctionCallNode
**Responsabilidad**: Llamada a función.
- Función y argumentos
- Sintaxis: `function(arg1, arg2)`

#### ObjectLiteralNode
**Responsabilidad**: Literal de objeto.
- Lista de miembros (ObjectMemberNode)
- Sintaxis: `{ key1: value1, key2: value2 }`

#### ObjectMemberNode
**Responsabilidad**: Par clave-valor en objeto.
- Clave (string o identificador) y valor

#### ListLiteralNode
**Responsabilidad**: Literal de lista.
- Lista de elementos
- Sintaxis: `[item1, item2, item3]`

### Otros Nodos

#### TypeNode
**Responsabilidad**: Tipo de dato.
- Nombre del tipo (integer, decimal, boolean, text, list, object, void)

#### ImportDeclarationNode
**Responsabilidad**: Declaración de import.
- Ruta del módulo, alias opcional, flag de JAR
- Sintaxis: `import "module" as alias`

## Clases Parser

### Parser Base

#### Parser
**Responsabilidad**: Clase base con funcionalidad común para todos los parsers.
- Manejo de tokens
- Métodos utilitarios (consume, check, peekToken)
- Sincronización entre parsers

### Parsers Principales

#### ProgramParser
**Responsabilidad**: Parser principal que coordina el parsing completo.
- Parsea declaraciones globales
- Maneja recuperación de errores
- Construye ProgramNode

#### DeclarationParser
**Responsabilidad**: Parsea declaraciones globales.
- Imports (normales y JAR)
- Delega funciones a FunctionDeclarationParser
- Delega procesos a ProcessDeclarationParser

#### ExpressionParser
**Responsabilidad**: Parsea expresiones con precedencia de operadores.
- 15 niveles de precedencia
- Manejo de operadores ternarios, binarios y unarios
- Expresiones complejas (objetos, listas, llamadas)

#### StatementParser
**Responsabilidad**: Parsea todos los tipos de statements.
- Statements de control (if, try, throw, return)
- Statements de flujo (goto, for)
- Bloques y declaraciones de variables

### Parsers de Proceso

#### ProcessDeclarationParser
**Responsabilidad**: Parsea declaraciones completas de proceso.
- Estructura: `process Name { elements }`
- Delega elementos a ProcessElementParser

#### ProcessElementParser
**Responsabilidad**: Parsea elementos individuales del proceso.
- Distribuye a parsers especializados según tipo
- Maneja start y end directamente

#### TaskElementParser
**Responsabilidad**: Parsea elementos de tarea.
- Estructura: `task Name { action: statements }`
- Parsea cuerpo de tarea con statements

#### GatewayParser
**Responsabilidad**: Parsea gateways exclusivos y paralelos.
- Gateway exclusivo: condiciones when/else
- Gateway paralelo: ramas branch/join

### Parsers de Función

#### FunctionDeclarationParser
**Responsabilidad**: Parsea declaraciones de función.
- Estructura: `function name(params) -> type { body }`
- Delega parámetros a ParameterListParser

#### ParameterListParser
**Responsabilidad**: Parsea lista de parámetros de función.
- Estructura: `param1: type1, param2: type2`
- Crea ParameterNode para cada parámetro

## Flujo de Parsing

1. **ProgramParser** inicia el proceso
2. **DeclarationParser** identifica tipo de declaración global
3. Para **procesos**:
   - ProcessDeclarationParser parsea la estructura
   - ProcessElementParser identifica cada elemento
   - TaskElementParser, GatewayParser manejan elementos específicos
4. Para **funciones**:
   - FunctionDeclarationParser parsea la signatura
   - ParameterListParser maneja parámetros
   - StatementParser parsea el cuerpo
5. **ExpressionParser** maneja todas las expresiones con precedencia correcta
6. **StatementParser** maneja todos los statements dentro de funciones y tareas

## Principios de Diseño

1. **Separación Clara**: Process vs Functions reflejan la filosofía del lenguaje
2. **Sin Clases Internas**: Cada nodo AST es una clase independiente
3. **Parsers como Clases**: Cada parser es una clase, no métodos
4. **Modularidad**: Cada parser tiene una responsabilidad específica
5. **Sincronización**: Los parsers pueden sincronizar posiciones para delegación

## Ejemplo de Uso

```java
// Tokenización
Lexer lexer = new Lexer(sourceCode);
List<Token> tokens = lexer.tokenize();

// Parsing
ProgramParser parser = new ProgramParser(tokens);
ProgramNode ast = parser.parse();

// El AST resultante contiene la estructura completa del programa
// con clara separación entre procesos (orquestación) y funciones (lógica)
```

## Conclusión

Esta arquitectura proporciona una separación limpia entre las dos partes fundamentales de FlowScript:
- **Procesos**: Orquestación del flujo de trabajo (BPMN)
- **Funciones**: Implementación de la lógica de negocio

Cada parser y nodo AST tiene una responsabilidad específica y bien definida, facilitando el mantenimiento y extensión del compilador.