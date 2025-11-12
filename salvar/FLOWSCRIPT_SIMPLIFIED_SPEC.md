# FlowScript - Especificación Simplificada

## Tabla de Contenidos
1. [BNF del Lenguaje](#1-bnf-del-lenguaje)
2. [Autómatas Finitos del Analizador Léxico](#2-autómatas-finitos-del-analizador-léxico)
3. [Estructura de Nodos AST](#3-estructura-de-nodos-ast)

---

## 1. BNF del Lenguaje

### 1.1. Programa y Declaraciones

```bnf
Program ::= Declaration*

Declaration ::= FunctionDeclaration
              | ProcessDeclaration

FunctionDeclaration ::= 'funcion' IDENTIFIER '(' ParameterList? ')' '->' Type Block

ProcessDeclaration ::= 'proceso' IDENTIFIER '{' ProcessBody '}'

ParameterList ::= Parameter (',' Parameter)*

Parameter ::= IDENTIFIER ':' Type

Type ::= 'entero'
       | 'decimal'
       | 'booleano'
       | 'texto'
       | 'lista'
       | 'objeto'
       | 'vacio'
```

### 1.2. Cuerpo de Funciones

```bnf
Block ::= '{' Statement* '}'

Statement ::= VariableDeclarationStatement
            | AssignmentStatement
            | IfStatement
            | ForStatement
            | ReturnStatement
            | ExpressionStatement

VariableDeclarationStatement ::= IDENTIFIER '=' Expression

AssignmentStatement ::= LeftHandSide '=' Expression

LeftHandSide ::= IDENTIFIER
               | MemberAccess

MemberAccess ::= Expression '.' IDENTIFIER
               | Expression '[' Expression ']'

IfStatement ::= 'si' '(' Expression ')' Block ('sino_si' '(' Expression ')' Block)* ('sino' Block)?

ForStatement ::= 'para' IDENTIFIER 'en' Expression Block

ReturnStatement ::= 'retornar' Expression?

ExpressionStatement ::= Expression
```

### 1.3. Expresiones

```bnf
Expression ::= LogicalOrExpression

LogicalOrExpression ::= LogicalAndExpression ('o' LogicalAndExpression)*

LogicalAndExpression ::= EqualityExpression ('y' EqualityExpression)*

EqualityExpression ::= RelationalExpression (('==' | '!=') RelationalExpression)*

RelationalExpression ::= AdditiveExpression (('<' | '>' | '<=' | '>=') AdditiveExpression)*

AdditiveExpression ::= MultiplicativeExpression (('+' | '-') MultiplicativeExpression)*

MultiplicativeExpression ::= UnaryExpression (('*' | '/' | '%') UnaryExpression)*

UnaryExpression ::= ('no' | '-') UnaryExpression
                  | PostfixExpression

PostfixExpression ::= PrimaryExpression (PostfixOperator)*

PostfixOperator ::= '.' IDENTIFIER
                  | '[' Expression ']'
                  | '(' ArgumentList? ')'

PrimaryExpression ::= Literal
                    | IDENTIFIER
                    | '(' Expression ')'

ArgumentList ::= Expression (',' Expression)*
```

### 1.4. Literales

```bnf
Literal ::= INTEGER_LITERAL
          | DECIMAL_LITERAL
          | BOOLEAN_LITERAL
          | STRING_LITERAL
          | ListLiteral
          | ObjectLiteral
          | 'nulo'

ListLiteral ::= '[' (Expression (',' Expression)*)? ']'

ObjectLiteral ::= '{' (ObjectMember (',' ObjectMember)*)? '}'

ObjectMember ::= IDENTIFIER ':' Expression
```

### 1.5. Cuerpo de Procesos

```bnf
ProcessBody ::= ProcessElement+

ProcessElement ::= StartElement
                 | TaskElement
                 | GatewayElement
                 | EndElement

StartElement ::= 'inicio' '->' IDENTIFIER

TaskElement ::= 'tarea' IDENTIFIER '{' 'accion' ':' Statement* '}'

GatewayElement ::= ExclusiveGateway
                 | ParallelGateway

ExclusiveGateway ::= 'tarea' IDENTIFIER '{' 'accion' ':' ConditionalBranch+ '}'

ConditionalBranch ::= ('si' | 'sino_si') '(' Expression ')' '{' 'ir_a' IDENTIFIER '}'
                    | 'sino' '{' 'ir_a' IDENTIFIER '}'

ParallelGateway ::= 'gateway' IDENTIFIER 'paralelo' '{' ParallelBranch+ ParallelMerge '}'

ParallelBranch ::= 'rama' '->' IDENTIFIER

ParallelMerge ::= 'unir' '->' IDENTIFIER

EndElement ::= 'fin' IDENTIFIER
```

---

## 2. Autómatas Finitos del Analizador Léxico

### 2.1. Palabras Reservadas (Keywords)

**Autómata:** Reconocedor de cadenas exactas

**Estados:**
- q0: Estado inicial
- qf: Estado final (aceptación)

**Transiciones:** Cada keyword se reconoce con un AFD específico que valida la secuencia exacta de caracteres.

**Keywords del lenguaje:**

| Keyword | Categoría | AFD |
|---------|-----------|-----|
| `funcion` | Declaración | q0 -f-> q1 -u-> q2 -n-> q3 -c-> q4 -i-> q5 -o-> q6 -n-> qf |
| `proceso` | Declaración | q0 -p-> q1 -r-> q2 -o-> q3 -c-> q4 -e-> q5 -s-> q6 -o-> qf |
| `retornar` | Control | q0 -r-> q1 -e-> q2 -t-> q3 -o-> q4 -r-> q5 -n-> q6 -a-> q7 -r-> qf |
| `si` | Control | q0 -s-> q1 -i-> qf |
| `sino_si` | Control | q0 -s-> q1 -i-> q2 -n-> q3 -o-> q4 -_-> q5 -s-> q6 -i-> qf |
| `sino` | Control | q0 -s-> q1 -i-> q2 -n-> q3 -o-> qf |
| `para` | Control | q0 -p-> q1 -a-> q2 -r-> q3 -a-> qf |
| `en` | Control | q0 -e-> q1 -n-> qf |
| `entero` | Tipo | q0 -e-> q1 -n-> q2 -t-> q3 -e-> q4 -r-> q5 -o-> qf |
| `decimal` | Tipo | q0 -d-> q1 -e-> q2 -c-> q3 -i-> q4 -m-> q5 -a-> q6 -l-> qf |
| `booleano` | Tipo | q0 -b-> q1 -o-> q2 -o-> q3 -l-> q4 -e-> q5 -a-> q6 -n-> q7 -o-> qf |
| `texto` | Tipo | q0 -t-> q1 -e-> q2 -x-> q3 -t-> q4 -o-> qf |
| `lista` | Tipo | q0 -l-> q1 -i-> q2 -s-> q3 -t-> q4 -a-> qf |
| `objeto` | Tipo | q0 -o-> q1 -b-> q2 -j-> q3 -e-> q4 -t-> q5 -o-> qf |
| `vacio` | Tipo | q0 -v-> q1 -a-> q2 -c-> q3 -i-> q4 -o-> qf |
| `verdadero` | Literal | q0 -v-> q1 -e-> q2 -r-> q3 -d-> q4 -a-> q5 -d-> q6 -e-> q7 -r-> q8 -o-> qf |
| `falso` | Literal | q0 -f-> q1 -a-> q2 -l-> q3 -s-> q4 -o-> qf |
| `nulo` | Literal | q0 -n-> q1 -u-> q2 -l-> q3 -o-> qf |
| `y` | Operador | q0 -y-> qf |
| `o` | Operador | q0 -o-> qf |
| `no` | Operador | q0 -n-> q1 -o-> qf |
| `inicio` | Proceso | q0 -i-> q1 -n-> q2 -i-> q3 -c-> q4 -i-> q5 -o-> qf |
| `fin` | Proceso | q0 -f-> q1 -i-> q2 -n-> qf |
| `tarea` | Proceso | q0 -t-> q1 -a-> q2 -r-> q3 -e-> q4 -a-> qf |
| `gateway` | Proceso | q0 -g-> q1 -a-> q2 -t-> q3 -e-> q4 -w-> q5 -a-> q6 -y-> qf |
| `paralelo` | Proceso | q0 -p-> q1 -a-> q2 -r-> q3 -a-> q4 -l-> q5 -e-> q6 -l-> q7 -o-> qf |
| `rama` | Proceso | q0 -r-> q1 -a-> q2 -m-> q3 -a-> qf |
| `unir` | Proceso | q0 -u-> q1 -n-> q2 -i-> q3 -r-> qf |
| `accion` | Proceso | q0 -a-> q1 -c-> q2 -c-> q3 -i-> q4 -o-> q5 -n-> qf |
| `ir_a` | Proceso | q0 -i-> q1 -r-> q2 -_-> q3 -a-> qf |
| `db` | Operación | q0 -d-> q1 -b-> qf |
| `http` | Operación | q0 -h-> q1 -t-> q2 -t-> q3 -p-> qf |

### 2.2. Identificadores (IDENTIFIER)

**Definición:** `[a-zA-Z_][a-zA-Z0-9_]*`

**Autómata:**

```
Estados: {q0, q1, qf}

q0: Estado inicial
q1: Primer carácter válido leído
qf: Estado final (aceptación)

Transiciones:
  δ(q0, [a-zA-Z_]) = q1
  δ(q1, [a-zA-Z0-9_]) = q1
  δ(q1, EOF) = qf

Alfabeto: [a-zA-Z0-9_]
Estado inicial: q0
Estados finales: {qf}
```

**Diagrama:**
```
    [a-zA-Z_]        [a-zA-Z0-9_]*
q0 ───────────> q1 ──────────────> qf
                 └──────┐
                   [a-zA-Z0-9_]
```

### 2.3. Literales Numéricos

#### 2.3.1. INTEGER_LITERAL

**Definición:** `[0-9]+`

**Autómata:**

```
Estados: {q0, q1, qf}

q0: Estado inicial
q1: Dígitos leídos
qf: Estado final (aceptación)

Transiciones:
  δ(q0, [0-9]) = q1
  δ(q1, [0-9]) = q1
  δ(q1, EOF) = qf

Alfabeto: [0-9]
Estado inicial: q0
Estados finales: {qf}
```

**Diagrama:**
```
    [0-9]        [0-9]*
q0 ────────> q1 ─────────> qf
              └───┐
               [0-9]
```

#### 2.3.2. DECIMAL_LITERAL

**Definición:** `[0-9]+ '.' [0-9]+`

**Autómata:**

```
Estados: {q0, q1, q2, q3, qf}

q0: Estado inicial
q1: Parte entera leída
q2: Punto decimal leído
q3: Parte decimal leída
qf: Estado final (aceptación)

Transiciones:
  δ(q0, [0-9]) = q1
  δ(q1, [0-9]) = q1
  δ(q1, '.') = q2
  δ(q2, [0-9]) = q3
  δ(q3, [0-9]) = q3
  δ(q3, EOF) = qf

Alfabeto: [0-9.]
Estado inicial: q0
Estados finales: {qf}
```

**Diagrama:**
```
    [0-9]        [0-9]*      '.'        [0-9]        [0-9]*
q0 ────────> q1 ─────────> q2 ─────> q3 ─────────> qf
              └───┐                     └────┐
               [0-9]                      [0-9]
```

### 2.4. STRING_LITERAL

**Definición:** `" [^"]* "`

**Autómata:**

```
Estados: {q0, q1, q2, qf}

q0: Estado inicial
q1: Comilla abierta leída
q2: Contenido de string leído
qf: Estado final (aceptación)

Transiciones:
  δ(q0, '"') = q1
  δ(q1, [^"]) = q2
  δ(q1, '"') = qf
  δ(q2, [^"]) = q2
  δ(q2, '"') = qf

Alfabeto: Todos los caracteres Unicode
Estado inicial: q0
Estados finales: {qf}
```

**Diagrama:**
```
    "          [^"]*         "
q0 ────> q1 ──────────> q2 ────> qf
          └─────────────────────> qf
                  "
              [^"]
          └────┘
```

### 2.5. BOOLEAN_LITERAL

**Definición:** `verdadero | falso`

**Autómata:** Dos AFDs específicos para cada valor (ver keywords arriba)

### 2.6. Operadores

#### 2.6.1. Operadores de Un Carácter

**Símbolos:** `+ - * / % < > = !`

**Autómata:**

```
    símbolo
q0 ────────> qf
```

#### 2.6.2. Operadores de Dos Caracteres

**Símbolos:** `== != <= >= ->`

**Autómata para '==':**

```
    '='        '='
q0 ────> q1 ────> qf
```

**Autómata para '!=':**

```
    '!'        '='
q0 ────> q1 ────> qf
```

**Autómata para '<=':**

```
    '<'        '='
q0 ────> q1 ────> qf
```

**Autómata para '>=':**

```
    '>'        '='
q0 ────> q1 ────> qf
```

**Autómata para '->':**

```
    '-'        '>'
q0 ────> q1 ────> qf
```

### 2.7. Delimitadores

**Símbolos:** `( ) { } [ ] , : .`

**Autómata:**

```
    símbolo
q0 ────────> qf
```

### 2.8. Espacios en Blanco y Comentarios

#### 2.8.1. Espacios en Blanco (Ignorados)

**Definición:** `[ \t\n\r]+`

**Autómata:**

```
    [ \t\n\r]      [ \t\n\r]*
q0 ─────────> q1 ──────────> qf
               └──────┐
                [ \t\n\r]
```

#### 2.8.2. Comentarios de Línea

**Definición:** `# [^\n]*`

**Autómata:**

```
    '#'        [^\n]*      '\n'
q0 ────> q1 ──────────> q2 ────> qf
              └────┘
              [^\n]
```

#### 2.8.3. Comentarios Multilínea

**Definición:** `/* .* */`

**Autómata:**

```
    '/'        '*'        .*          '*'        '/'
q0 ────> q1 ────> q2 ──────────> q3 ────> q4 ────> qf
                   └──────┘
                    [^*]
                   └─────> q5 ────> q2
                      '*'[^/]
```

---

## 3. Estructura de Nodos AST

### 3.1. Nodos Raíz y Declaraciones

#### 3.1.1. ProgramNode

**Descripción:** Nodo raíz del AST que contiene todas las declaraciones del programa.

**Estructura:**

```java
class ProgramNode extends ASTNode {
    // Atributos
    List<DeclarationNode> declarations;

    // Constructor
    ProgramNode(List<DeclarationNode> declarations);

    // Métodos
    void addDeclaration(DeclarationNode declaration);
    List<DeclarationNode> getDeclarations();
    void accept(ASTVisitor visitor);
}
```

#### 3.1.2. DeclarationNode

**Descripción:** Nodo abstracto que representa cualquier tipo de declaración.

**Estructura:**

```java
abstract class DeclarationNode extends ASTNode {
    // Métodos
    abstract void accept(ASTVisitor visitor);
}
```

#### 3.1.3. FunctionDeclarationNode

**Descripción:** Nodo que representa la declaración de una función.

**Estructura:**

```java
class FunctionDeclarationNode extends DeclarationNode {
    // Atributos
    String name;                      // Nombre de la función
    List<ParameterNode> parameters;   // Lista de parámetros
    TypeNode returnType;              // Tipo de retorno
    BlockNode body;                   // Cuerpo de la función

    // Constructor
    FunctionDeclarationNode(String name, List<ParameterNode> parameters,
                           TypeNode returnType, BlockNode body);

    // Métodos
    String getName();
    List<ParameterNode> getParameters();
    TypeNode getReturnType();
    BlockNode getBody();
    void accept(ASTVisitor visitor);
}
```

#### 3.1.4. ProcessDeclarationNode

**Descripción:** Nodo que representa la declaración de un proceso.

**Estructura:**

```java
class ProcessDeclarationNode extends DeclarationNode {
    // Atributos
    String name;                           // Nombre del proceso
    List<ProcessElementNode> elements;     // Elementos del proceso

    // Constructor
    ProcessDeclarationNode(String name, List<ProcessElementNode> elements);

    // Métodos
    String getName();
    List<ProcessElementNode> getElements();
    void addElement(ProcessElementNode element);
    void accept(ASTVisitor visitor);
}
```

### 3.2. Nodos de Tipos y Parámetros

#### 3.2.1. TypeNode

**Descripción:** Nodo que representa un tipo de dato.

**Estructura:**

```java
class TypeNode extends ASTNode {
    // Enum para tipos
    enum TypeKind {
        ENTERO,
        DECIMAL,
        BOOLEANO,
        TEXTO,
        LISTA,
        OBJETO,
        VACIO
    }

    // Atributos
    TypeKind kind;    // Tipo básico

    // Constructor
    TypeNode(TypeKind kind);

    // Métodos
    TypeKind getKind();
    boolean isEntero();
    boolean isDecimal();
    boolean isBooleano();
    boolean isTexto();
    boolean isLista();
    boolean isObjeto();
    boolean isVacio();
    void accept(ASTVisitor visitor);
}
```

#### 3.2.2. ParameterNode

**Descripción:** Nodo que representa un parámetro de función.

**Estructura:**

```java
class ParameterNode extends ASTNode {
    // Atributos
    String name;      // Nombre del parámetro
    TypeNode type;    // Tipo del parámetro

    // Constructor
    ParameterNode(String name, TypeNode type);

    // Métodos
    String getName();
    TypeNode getType();
    void accept(ASTVisitor visitor);
}
```

### 3.3. Nodos de Sentencias (Statements)

#### 3.3.1. StatementNode

**Descripción:** Nodo abstracto para todas las sentencias.

```java
abstract class StatementNode extends ASTNode {
    abstract void accept(ASTVisitor visitor);
}
```

#### 3.3.2. BlockNode

**Descripción:** Nodo que representa un bloque de código (secuencia de sentencias).

```java
class BlockNode extends StatementNode {
    // Atributos
    List<StatementNode> statements;

    // Constructor
    BlockNode(List<StatementNode> statements);

    // Métodos
    List<StatementNode> getStatements();
    void addStatement(StatementNode statement);
    void accept(ASTVisitor visitor);
}
```

#### 3.3.3. VariableDeclarationStatementNode

**Descripción:** Nodo para declaración/inicialización de variable.

```java
class VariableDeclarationStatementNode extends StatementNode {
    // Atributos
    String name;              // Nombre de la variable
    ExpressionNode value;     // Valor inicial

    // Constructor
    VariableDeclarationStatementNode(String name, ExpressionNode value);

    // Métodos
    String getName();
    ExpressionNode getValue();
    void accept(ASTVisitor visitor);
}
```

#### 3.3.4. AssignmentStatementNode

**Descripción:** Nodo para asignación a variable o propiedad.

```java
class AssignmentStatementNode extends StatementNode {
    // Atributos
    ExpressionNode target;    // Objetivo de la asignación (IDENTIFIER o MemberAccess)
    ExpressionNode value;     // Valor a asignar

    // Constructor
    AssignmentStatementNode(ExpressionNode target, ExpressionNode value);

    // Métodos
    ExpressionNode getTarget();
    ExpressionNode getValue();
    void accept(ASTVisitor visitor);
}
```

#### 3.3.5. IfStatementNode

**Descripción:** Nodo para estructura condicional if/sino_si/sino.

```java
class IfStatementNode extends StatementNode {
    // Clase interna para representar cada rama
    static class ConditionalBranch {
        ExpressionNode condition;  // null para 'sino'
        BlockNode thenBlock;

        ConditionalBranch(ExpressionNode condition, BlockNode thenBlock);
    }

    // Atributos
    List<ConditionalBranch> branches;  // Lista de ramas (if, sino_si, sino)

    // Constructor
    IfStatementNode(List<ConditionalBranch> branches);

    // Métodos
    List<ConditionalBranch> getBranches();
    void addBranch(ConditionalBranch branch);
    void accept(ASTVisitor visitor);
}
```

#### 3.3.6. ForStatementNode

**Descripción:** Nodo para bucle for-each.

```java
class ForStatementNode extends StatementNode {
    // Atributos
    String iteratorVariable;     // Variable iteradora
    ExpressionNode iterable;     // Expresión iterable
    BlockNode body;              // Cuerpo del bucle

    // Constructor
    ForStatementNode(String iteratorVariable, ExpressionNode iterable, BlockNode body);

    // Métodos
    String getIteratorVariable();
    ExpressionNode getIterable();
    BlockNode getBody();
    void accept(ASTVisitor visitor);
}
```

#### 3.3.7. ReturnStatementNode

**Descripción:** Nodo para sentencia de retorno.

```java
class ReturnStatementNode extends StatementNode {
    // Atributos
    ExpressionNode value;     // Valor de retorno (puede ser null para 'retornar' sin valor)

    // Constructor
    ReturnStatementNode(ExpressionNode value);

    // Métodos
    ExpressionNode getValue();
    boolean hasValue();
    void accept(ASTVisitor visitor);
}
```

#### 3.3.8. ExpressionStatementNode

**Descripción:** Nodo para sentencia que es una expresión (ej: llamada a función).

```java
class ExpressionStatementNode extends StatementNode {
    // Atributos
    ExpressionNode expression;

    // Constructor
    ExpressionStatementNode(ExpressionNode expression);

    // Métodos
    ExpressionNode getExpression();
    void accept(ASTVisitor visitor);
}
```

### 3.4. Nodos de Expresiones

#### 3.4.1. ExpressionNode

**Descripción:** Nodo abstracto para todas las expresiones.

```java
abstract class ExpressionNode extends ASTNode {
    abstract void accept(ASTVisitor visitor);
}
```

#### 3.4.2. BinaryExpressionNode

**Descripción:** Nodo para expresiones binarias (operadores con dos operandos).

```java
class BinaryExpressionNode extends ExpressionNode {
    // Enum para operadores binarios
    enum Operator {
        // Aritméticos
        ADD("+"), SUBTRACT("-"), MULTIPLY("*"), DIVIDE("/"), MODULO("%"),

        // Relacionales
        LESS_THAN("<"), GREATER_THAN(">"), LESS_EQUAL("<="), GREATER_EQUAL(">="),

        // Igualdad
        EQUAL("=="), NOT_EQUAL("!="),

        // Lógicos
        AND("y"), OR("o");

        private final String symbol;
        Operator(String symbol) { this.symbol = symbol; }
        public String getSymbol() { return symbol; }
    }

    // Atributos
    Operator operator;
    ExpressionNode left;
    ExpressionNode right;

    // Constructor
    BinaryExpressionNode(Operator operator, ExpressionNode left, ExpressionNode right);

    // Métodos
    Operator getOperator();
    ExpressionNode getLeft();
    ExpressionNode getRight();
    void accept(ASTVisitor visitor);
}
```

#### 3.4.3. UnaryExpressionNode

**Descripción:** Nodo para expresiones unarias (operadores con un operando).

```java
class UnaryExpressionNode extends ExpressionNode {
    // Enum para operadores unarios
    enum Operator {
        LOGICAL_NOT("no"),
        NEGATE("-");

        private final String symbol;
        Operator(String symbol) { this.symbol = symbol; }
        public String getSymbol() { return symbol; }
    }

    // Atributos
    Operator operator;
    ExpressionNode operand;

    // Constructor
    UnaryExpressionNode(Operator operator, ExpressionNode operand);

    // Métodos
    Operator getOperator();
    ExpressionNode getOperand();
    void accept(ASTVisitor visitor);
}
```

#### 3.4.4. MemberAccessNode

**Descripción:** Nodo para acceso a miembros (propiedades de objetos).

```java
class MemberAccessNode extends ExpressionNode {
    // Atributos
    ExpressionNode object;    // Objeto al que se accede
    String memberName;        // Nombre del miembro

    // Constructor
    MemberAccessNode(ExpressionNode object, String memberName);

    // Métodos
    ExpressionNode getObject();
    String getMemberName();
    void accept(ASTVisitor visitor);
}
```

#### 3.4.5. IndexAccessNode

**Descripción:** Nodo para acceso por índice (listas).

```java
class IndexAccessNode extends ExpressionNode {
    // Atributos
    ExpressionNode array;     // Lista a la que se accede
    ExpressionNode index;     // Índice de acceso

    // Constructor
    IndexAccessNode(ExpressionNode array, ExpressionNode index);

    // Métodos
    ExpressionNode getArray();
    ExpressionNode getIndex();
    void accept(ASTVisitor visitor);
}
```

#### 3.4.6. FunctionCallNode

**Descripción:** Nodo para llamada a función.

```java
class FunctionCallNode extends ExpressionNode {
    // Atributos
    ExpressionNode function;           // Expresión que evalúa a la función
    List<ExpressionNode> arguments;    // Argumentos de la llamada

    // Constructor
    FunctionCallNode(ExpressionNode function, List<ExpressionNode> arguments);

    // Métodos
    ExpressionNode getFunction();
    List<ExpressionNode> getArguments();
    void addArgument(ExpressionNode argument);
    void accept(ASTVisitor visitor);
}
```

#### 3.4.7. IdentifierNode

**Descripción:** Nodo para identificadores (referencias a variables).

```java
class IdentifierNode extends ExpressionNode {
    // Atributos
    String name;

    // Constructor
    IdentifierNode(String name);

    // Métodos
    String getName();
    void accept(ASTVisitor visitor);
}
```

### 3.5. Nodos de Literales

#### 3.5.1. LiteralNode

**Descripción:** Nodo abstracto para todos los literales.

```java
abstract class LiteralNode extends ExpressionNode {
    abstract Object getValue();
}
```

#### 3.5.2. IntegerLiteralNode

**Descripción:** Nodo para literales enteros.

```java
class IntegerLiteralNode extends LiteralNode {
    // Atributos
    long value;

    // Constructor
    IntegerLiteralNode(long value);

    // Métodos
    Long getValue();
    void accept(ASTVisitor visitor);
}
```

#### 3.5.3. DecimalLiteralNode

**Descripción:** Nodo para literales decimales.

```java
class DecimalLiteralNode extends LiteralNode {
    // Atributos
    double value;

    // Constructor
    DecimalLiteralNode(double value);

    // Métodos
    Double getValue();
    void accept(ASTVisitor visitor);
}
```

#### 3.5.4. BooleanLiteralNode

**Descripción:** Nodo para literales booleanos.

```java
class BooleanLiteralNode extends LiteralNode {
    // Atributos
    boolean value;

    // Constructor
    BooleanLiteralNode(boolean value);

    // Métodos
    Boolean getValue();
    void accept(ASTVisitor visitor);
}
```

#### 3.5.5. StringLiteralNode

**Descripción:** Nodo para literales de texto.

```java
class StringLiteralNode extends LiteralNode {
    // Atributos
    String value;

    // Constructor
    StringLiteralNode(String value);

    // Métodos
    String getValue();
    void accept(ASTVisitor visitor);
}
```

#### 3.5.6. NullLiteralNode

**Descripción:** Nodo para el literal nulo.

```java
class NullLiteralNode extends LiteralNode {
    // Constructor
    NullLiteralNode();

    // Métodos
    Object getValue();  // Retorna null
    void accept(ASTVisitor visitor);
}
```

#### 3.5.7. ListLiteralNode

**Descripción:** Nodo para literales de lista.

```java
class ListLiteralNode extends LiteralNode {
    // Atributos
    List<ExpressionNode> elements;

    // Constructor
    ListLiteralNode(List<ExpressionNode> elements);

    // Métodos
    List<ExpressionNode> getElements();
    List<Object> getValue();  // Retorna lista con valores evaluados
    void addElement(ExpressionNode element);
    void accept(ASTVisitor visitor);
}
```

#### 3.5.8. ObjectLiteralNode

**Descripción:** Nodo para literales de objeto.

```java
class ObjectLiteralNode extends LiteralNode {
    // Clase interna para miembros
    static class ObjectMember {
        String key;
        ExpressionNode value;

        ObjectMember(String key, ExpressionNode value);

        String getKey();
        ExpressionNode getValue();
    }

    // Atributos
    List<ObjectMember> members;

    // Constructor
    ObjectLiteralNode(List<ObjectMember> members);

    // Métodos
    List<ObjectMember> getMembers();
    Map<String, Object> getValue();  // Retorna mapa con valores evaluados
    void addMember(String key, ExpressionNode value);
    void accept(ASTVisitor visitor);
}
```

### 3.6. Nodos de Elementos de Proceso

#### 3.6.1. ProcessElementNode

**Descripción:** Nodo abstracto para todos los elementos de un proceso.

```java
abstract class ProcessElementNode extends ASTNode {
    abstract void accept(ASTVisitor visitor);
}
```

#### 3.6.2. StartElementNode

**Descripción:** Nodo para el elemento de inicio del proceso.

```java
class StartElementNode extends ProcessElementNode {
    // Atributos
    String targetTaskName;    // Nombre de la tarea destino

    // Constructor
    StartElementNode(String targetTaskName);

    // Métodos
    String getTargetTaskName();
    void accept(ASTVisitor visitor);
}
```

#### 3.6.3. TaskElementNode

**Descripción:** Nodo para una tarea del proceso.

```java
class TaskElementNode extends ProcessElementNode {
    // Atributos
    String name;                       // Nombre de la tarea
    List<StatementNode> actions;       // Acciones de la tarea

    // Constructor
    TaskElementNode(String name, List<StatementNode> actions);

    // Métodos
    String getName();
    List<StatementNode> getActions();
    void addAction(StatementNode action);
    void accept(ASTVisitor visitor);
}
```

#### 3.6.4. GotoStatementNode

**Descripción:** Nodo para la instrucción ir_a (solo válida en procesos).

```java
class GotoStatementNode extends StatementNode {
    // Atributos
    String targetName;    // Nombre del nodo destino

    // Constructor
    GotoStatementNode(String targetName);

    // Métodos
    String getTargetName();
    void accept(ASTVisitor visitor);
}
```

#### 3.6.5. ExclusiveGatewayNode

**Descripción:** Nodo para gateway exclusivo (XOR).

```java
class ExclusiveGatewayNode extends ProcessElementNode {
    // Clase interna para ramas condicionales
    static class ConditionalBranch {
        ExpressionNode condition;  // null para 'sino'
        String targetTaskName;

        ConditionalBranch(ExpressionNode condition, String targetTaskName);

        ExpressionNode getCondition();
        String getTargetTaskName();
        boolean isElseBranch();
    }

    // Atributos
    String name;                           // Nombre del gateway
    List<ConditionalBranch> branches;      // Ramas del gateway

    // Constructor
    ExclusiveGatewayNode(String name, List<ConditionalBranch> branches);

    // Métodos
    String getName();
    List<ConditionalBranch> getBranches();
    void addBranch(ConditionalBranch branch);
    void accept(ASTVisitor visitor);
}
```

#### 3.6.6. ParallelGatewayNode

**Descripción:** Nodo para gateway paralelo (AND).

```java
class ParallelGatewayNode extends ProcessElementNode {
    // Clase interna para ramas paralelas
    static class ParallelBranch {
        String targetTaskName;

        ParallelBranch(String targetTaskName);

        String getTargetTaskName();
    }

    // Atributos
    String name;                       // Nombre del gateway
    List<ParallelBranch> branches;     // Ramas paralelas
    String mergeTargetName;            // Nodo de unión

    // Constructor
    ParallelGatewayNode(String name, List<ParallelBranch> branches, String mergeTargetName);

    // Métodos
    String getName();
    List<ParallelBranch> getBranches();
    String getMergeTargetName();
    void addBranch(ParallelBranch branch);
    void accept(ASTVisitor visitor);
}
```

#### 3.6.7. EndElementNode

**Descripción:** Nodo para elemento de fin del proceso.

```java
class EndElementNode extends ProcessElementNode {
    // Atributos
    String name;    // Nombre del elemento de fin

    // Constructor
    EndElementNode(String name);

    // Métodos
    String getName();
    void accept(ASTVisitor visitor);
}
```

### 3.7. Nodo Base y Visitor

#### 3.7.1. ASTNode

**Descripción:** Clase base abstracta para todos los nodos del AST.

```java
abstract class ASTNode {
    // Atributos comunes
    protected int lineNumber;      // Línea en el código fuente
    protected int columnNumber;    // Columna en el código fuente

    // Constructor
    ASTNode();
    ASTNode(int lineNumber, int columnNumber);

    // Métodos comunes
    int getLineNumber();
    void setLineNumber(int lineNumber);
    int getColumnNumber();
    void setColumnNumber(int columnNumber);

    // Método abstracto para el patrón Visitor
    abstract void accept(ASTVisitor visitor);
}
```

#### 3.7.2. ASTVisitor (Patrón Visitor)

**Descripción:** Interfaz para implementar el patrón Visitor sobre el AST.

```java
interface ASTVisitor {
    // Declaraciones
    void visit(ProgramNode node);
    void visit(FunctionDeclarationNode node);
    void visit(ProcessDeclarationNode node);

    // Tipos y parámetros
    void visit(TypeNode node);
    void visit(ParameterNode node);

    // Sentencias
    void visit(BlockNode node);
    void visit(VariableDeclarationStatementNode node);
    void visit(AssignmentStatementNode node);
    void visit(IfStatementNode node);
    void visit(ForStatementNode node);
    void visit(ReturnStatementNode node);
    void visit(ExpressionStatementNode node);
    void visit(GotoStatementNode node);

    // Expresiones
    void visit(BinaryExpressionNode node);
    void visit(UnaryExpressionNode node);
    void visit(MemberAccessNode node);
    void visit(IndexAccessNode node);
    void visit(FunctionCallNode node);
    void visit(IdentifierNode node);

    // Literales
    void visit(IntegerLiteralNode node);
    void visit(DecimalLiteralNode node);
    void visit(BooleanLiteralNode node);
    void visit(StringLiteralNode node);
    void visit(NullLiteralNode node);
    void visit(ListLiteralNode node);
    void visit(ObjectLiteralNode node);

    // Elementos de proceso
    void visit(StartElementNode node);
    void visit(TaskElementNode node);
    void visit(ExclusiveGatewayNode node);
    void visit(ParallelGatewayNode node);
    void visit(EndElementNode node);
}
```

---

## 4. Operaciones Especiales: DB y HTTP

### 4.1. Operaciones de Base de Datos

Las operaciones de base de datos se representan como llamadas a funciones especiales del objeto `db`:

#### 4.1.1. db.ejecutar

**Sintaxis:** `db.ejecutar(sql: texto, params: lista) -> entero`

**Representación AST:**
```java
FunctionCallNode {
    function: MemberAccessNode {
        object: IdentifierNode("db"),
        memberName: "ejecutar"
    },
    arguments: [
        StringLiteralNode(sql),
        ListLiteralNode(params)
    ]
}
```

#### 4.1.2. db.consultar

**Sintaxis:** `db.consultar(sql: texto, params: lista) -> lista`

**Representación AST:**
```java
FunctionCallNode {
    function: MemberAccessNode {
        object: IdentifierNode("db"),
        memberName: "consultar"
    },
    arguments: [
        StringLiteralNode(sql),
        ListLiteralNode(params)
    ]
}
```

### 4.2. Operaciones HTTP

Las operaciones HTTP se representan como llamadas a funciones especiales del objeto `http`:

#### 4.2.1. http.get

**Sintaxis:** `http.get(url: texto, headers: objeto) -> objeto`

**Representación AST:**
```java
FunctionCallNode {
    function: MemberAccessNode {
        object: IdentifierNode("http"),
        memberName: "get"
    },
    arguments: [
        StringLiteralNode(url),
        ObjectLiteralNode(headers)  // Opcional
    ]
}
```

#### 4.2.2. http.post

**Sintaxis:** `http.post(url: texto, body: objeto, headers: objeto) -> objeto`

**Representación AST:**
```java
FunctionCallNode {
    function: MemberAccessNode {
        object: IdentifierNode("http"),
        memberName: "post"
    },
    arguments: [
        StringLiteralNode(url),
        ObjectLiteralNode(body),
        ObjectLiteralNode(headers)  // Opcional
    ]
}
```

#### 4.2.3. http.put

**Sintaxis:** `http.put(url: texto, body: objeto, headers: objeto) -> objeto`

**Representación AST:** Similar a http.post

#### 4.2.4. http.delete

**Sintaxis:** `http.delete(url: texto, headers: objeto) -> objeto`

**Representación AST:** Similar a http.get

---

## 5. Ejemplo Completo

### 5.1. Código FlowScript

```flowscript
# Función para verificar capacidad de crédito
funcion verificar_credito(monto: decimal, cliente_id: entero) -> booleano {
    resultado = db.consultar(
        "SELECT credito_disponible FROM clientes WHERE id = ?",
        [cliente_id]
    )

    si (resultado != nulo y resultado.length() > 0) {
        disponible = resultado[0].credito_disponible
        retornar disponible >= monto
    }

    retornar falso
}

# Proceso simplificado
proceso ProcesarPago {
    inicio -> validar

    tarea validar {
        accion:
            monto = entrada.monto
            cliente = entrada.cliente_id

            tiene_credito = verificar_credito(monto, cliente)

            si (tiene_credito) {
                ir_a procesar
            } sino {
                ir_a rechazar
            }
    }

    tarea procesar {
        accion:
            respuesta = http.post(
                "http://api.pagos.com/procesar",
                { monto: monto, cliente: cliente },
                { Authorization: "Bearer token123" }
            )

            si (respuesta.exitoso) {
                ir_a FinExito
            } sino {
                ir_a FinError
            }
    }

    tarea rechazar {
        accion:
            ir_a FinError
    }

    fin FinExito
    fin FinError
}
```

### 5.2. AST Resultante (Simplificado)

```
ProgramNode
├── FunctionDeclarationNode (verificar_credito)
│   ├── parameters: [
│   │   ParameterNode(monto, TypeNode(DECIMAL)),
│   │   ParameterNode(cliente_id, TypeNode(ENTERO))
│   │   ]
│   ├── returnType: TypeNode(BOOLEANO)
│   └── body: BlockNode
│       ├── VariableDeclarationStatementNode (resultado)
│       │   └── FunctionCallNode (db.consultar)
│       ├── IfStatementNode
│       │   └── branches: [...]
│       └── ReturnStatementNode
│           └── BooleanLiteralNode(false)
└── ProcessDeclarationNode (ProcesarPago)
    ├── StartElementNode -> "validar"
    ├── TaskElementNode (validar)
    │   └── actions: [
    │       VariableDeclarationStatementNode (monto),
    │       VariableDeclarationStatementNode (cliente),
    │       VariableDeclarationStatementNode (tiene_credito),
    │       IfStatementNode with GotoStatementNode
    │       ]
    ├── TaskElementNode (procesar)
    │   └── actions: [...]
    ├── TaskElementNode (rechazar)
    │   └── actions: [GotoStatementNode("FinError")]
    ├── EndElementNode (FinExito)
    └── EndElementNode (FinError)
```

---

## 6. Resumen de Simplificaciones

### 6.1. Eliminadas del Lenguaje Original

1. **Importaciones:** `importar`, `importar_jar`
2. **Bucles:** `mientras`
3. **Manejo de errores:** `intentar`, `capturar`, `lanzar`
4. **Operador ternario:** `? :`
5. **Funciones con múltiples returns en medio**

### 6.2. Mantenidas

1. **Funciones:** Con parámetros tipados y tipo de retorno
2. **Procesos:** Con gateways exclusivos y paralelos
3. **Estructuras de control:** `si`, `sino_si`, `sino`, `para`
4. **Operaciones especiales:** `db.ejecutar`, `db.consultar`, `http.get`, `http.post`, `http.put`, `http.delete`
5. **Tipos:** `entero`, `decimal`, `booleano`, `texto`, `lista`, `objeto`, `vacio`
6. **Expresiones:** Aritméticas, lógicas y relacionales completas
7. **Literales:** Números, texto, booleanos, listas, objetos, nulo

---

## Fin del Documento
