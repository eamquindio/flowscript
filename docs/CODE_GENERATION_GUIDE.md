# GUÍA COMPLETA DE GENERACIÓN DE CÓDIGO JAVA

## ÍNDICE

1. [Introducción](#introducción)
2. [Arquitectura del Code Generator](#arquitectura)
3. [Estrategia General](#estrategia-general)
4. [Generación por Tipo de Nodo](#generación-por-nodo)
5. [Generación de Procesos BPMN](#procesos-bpmn)
6. [Manejo de Tipos Dinámicos](#tipos-dinámicos)
7. [Runtime Support Library](#runtime-library)
8. [Casos Especiales](#casos-especiales)

---

## 1. INTRODUCCIÓN

### 1.1 Objetivo

Este documento describe **en detalle exhaustivo** cómo traducir cada nodo del AST de FlowScript a código Java equivalente utilizando el patrón **Visitor**.

### 1.2 Filosofía de Diseño

**FlowScript → Java**: Traducción directa manteniendo semántica
- **Funciones** → Métodos estáticos de Java
- **Procesos** → Clases que implementan state machine pattern
- **Variables dinámicas** → Object + casting explícito o tipos genéricos
- **Expresiones** → Expresiones Java equivalentes

### 1.3 Estructura del Generador

```
CodeGenerator (implements ASTVisitor)
├── StringBuilder output           // Código generado acumulado
├── int indentLevel               // Nivel de indentación actual
├── SymbolTable symbolTable       // Información de tipos (del análisis semántico)
├── FunctionTable functionTable   // Información de funciones
├── ProcessTable processTable     // Información de procesos
└── CodeGenContext context        // Contexto de generación actual
```

---

## 2. ARQUITECTURA DEL CODE GENERATOR

### 2.1 Clase Principal: CodeGenerator

```java
public class CodeGenerator implements ASTVisitor {
    private StringBuilder output;
    private int indentLevel;
    private SymbolTable symbolTable;
    private FunctionTable functionTable;
    private ProcessTable processTable;
    private CodeGenContext context;

    public String generate(ProgramNode program,
                          SymbolTable symbolTable,
                          FunctionTable functionTable,
                          ProcessTable processTable) {
        this.output = new StringBuilder();
        this.indentLevel = 0;
        this.symbolTable = symbolTable;
        this.functionTable = functionTable;
        this.processTable = processTable;
        this.context = new CodeGenContext();

        program.accept(this);

        return output.toString();
    }
}
```

### 2.2 Clase de Contexto

```java
public class CodeGenContext {
    private String currentClassName;      // Clase actual siendo generada
    private String currentMethodName;     // Método actual
    private boolean inProcess;            // ¿Estamos en un proceso?
    private String currentProcessName;    // Nombre del proceso actual
    private Set<String> usedVariables;    // Variables ya declaradas en scope actual
    private Stack<String> loopLabels;     // Labels para break/continue

    // ... getters y setters
}
```

### 2.3 Helpers de Generación

```java
// Emitir código con indentación
private void emit(String code) {
    output.append("    ".repeat(indentLevel)).append(code);
}

private void emitLine(String code) {
    emit(code);
    output.append("\n");
}

// Control de indentación
private void indent() { indentLevel++; }
private void dedent() { indentLevel--; }

// Conversión de tipos FlowScript → Java
private String mapType(TypeNode type) {
    if (type == null) return "void";
    return switch (type.getKind()) {
        case ENTERO -> "int";
        case DECIMAL -> "double";
        case BOOLEANO -> "boolean";
        case TEXTO -> "String";
        case LISTA -> "List<Object>";
        case OBJETO -> "Map<String, Object>";
        case VACIO -> "void";
    };
}
```

---

## 3. ESTRATEGIA GENERAL

### 3.1 Estructura del Código Java Generado

**IMPORTANTE:** El código generado es **completamente autocontenido** y no depende de librerías externas (excepto `java.util.*` y `java.net.http.*` que son parte del JDK).

```java
// Para un programa FlowScript típico:

// ========== IMPORTS ==========
import java.util.*;
import java.sql.*;
import java.net.http.*;
import java.net.*;

// ========== CLASE PRINCIPAL ==========
public class GeneratedProgram {

    // ========== MÉTODOS HELPER INLINE ==========
    // Todos los helpers necesarios se generan inline aquí
    private static int toInt(Object obj) { ... }
    private static double toDouble(Object obj) { ... }
    private static String toString(Object obj) { ... }
    private static Object getMember(Object obj, String member) { ... }
    private static Object getIndex(Object array, Object index) { ... }
    // ... más helpers según se necesiten

    // ========== FUNCIONES GLOBALES ==========
    public static ReturnType functionName(ParamType param1, ...) {
        // Cuerpo de la función
    }

    // ========== CLASES DE PROCESOS ==========
    public static class ProcessName {
        // Variables de contexto del proceso
        private Map<String, Object> context;
        private String currentState;
        private boolean running;

        // Constructor
        public ProcessName(Map<String, Object> initialContext) { ... }

        // Método principal de ejecución
        public Map<String, Object> execute() { ... }

        // Métodos para cada tarea
        private void task_TaskName() { ... }

        // Métodos helper
        private void goTo(String taskName) { ... }
    }
}
```

### 3.2 Flujo de Generación

1. **Generar imports y cabecera de clase**
2. **Generar funciones globales** (cada FunctionDeclarationNode)
3. **Generar clases de procesos** (cada ProcessDeclarationNode)
4. **Cerrar clase principal**

---

## 4. GENERACIÓN POR TIPO DE NODO

### 4.1 ProgramNode

**QUÉ ES:** Nodo raíz que contiene todas las declaraciones del programa.

**ESTRUCTURA:**
```java
public class ProgramNode {
    List<DeclarationNode> declarations;  // Funciones y Procesos
}
```

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(ProgramNode node) {
    // PASO 1: Generar imports (solo JDK, sin dependencias externas)
    emitLine("import java.util.*;");
    emitLine("import java.sql.*;");
    emitLine("import java.net.http.*;");
    emitLine("import java.net.*;");
    emitLine("");

    // PASO 2: Generar clase principal
    emitLine("public class GeneratedProgram {");
    indent();

    // PASO 3: Generar métodos helper inline
    generateInlineHelpers();

    // PASO 4: Generar cada declaración (funciones y procesos)
    for (DeclarationNode declaration : node.getDeclarations()) {
        emitLine("");
        declaration.accept(this);
    }

    // PASO 5: Generar método main si es necesario
    generateMainMethod();

    // PASO 6: Cerrar clase
    dedent();
    emitLine("}");
}

private void generateInlineHelpers() {
    emitLine("");
    emitLine("// ========== MÉTODOS HELPER INLINE ==========");
    emitLine("");

    // Helper: Conversión a int
    emitLine("private static int toInt(Object obj) {");
    indent();
    emitLine("if (obj instanceof Integer) return (Integer) obj;");
    emitLine("if (obj instanceof Double) return ((Double) obj).intValue();");
    emitLine("if (obj instanceof String) return Integer.parseInt((String) obj);");
    emitLine("throw new RuntimeException(\"Cannot convert to int: \" + obj);");
    dedent();
    emitLine("}");
    emitLine("");

    // Helper: Conversión a double
    emitLine("private static double toDouble(Object obj) {");
    indent();
    emitLine("if (obj instanceof Double) return (Double) obj;");
    emitLine("if (obj instanceof Integer) return ((Integer) obj).doubleValue();");
    emitLine("if (obj instanceof String) return Double.parseDouble((String) obj);");
    emitLine("throw new RuntimeException(\"Cannot convert to double: \" + obj);");
    dedent();
    emitLine("}");
    emitLine("");

    // Helper: Conversión a String
    emitLine("private static String toString(Object obj) {");
    indent();
    emitLine("if (obj == null) return \"null\";");
    emitLine("return obj.toString();");
    dedent();
    emitLine("}");
    emitLine("");

    // Helper: Conversión a boolean
    emitLine("private static boolean toBoolean(Object obj) {");
    indent();
    emitLine("if (obj instanceof Boolean) return (Boolean) obj;");
    emitLine("if (obj instanceof String) return Boolean.parseBoolean((String) obj);");
    emitLine("if (obj instanceof Number) return ((Number) obj).doubleValue() != 0;");
    emitLine("return obj != null;");
    dedent();
    emitLine("}");
    emitLine("");

    // Helper: Acceso a miembro de objeto
    emitLine("private static Object getMember(Object obj, String memberName) {");
    indent();
    emitLine("if (obj instanceof Map) {");
    indent();
    emitLine("return ((Map<?, ?>) obj).get(memberName);");
    dedent();
    emitLine("}");
    emitLine("throw new RuntimeException(\"Cannot access member on non-object\");");
    dedent();
    emitLine("}");
    emitLine("");

    // Helper: Acceso a índice de lista
    emitLine("private static Object getIndex(Object array, Object index) {");
    indent();
    emitLine("if (array instanceof List) {");
    indent();
    emitLine("return ((List<?>) array).get(toInt(index));");
    dedent();
    emitLine("}");
    emitLine("throw new RuntimeException(\"Cannot index non-list\");");
    dedent();
    emitLine("}");
    emitLine("");

    // Helper: Print
    emitLine("private static void print(Object obj) {");
    indent();
    emitLine("System.out.print(toString(obj));");
    dedent();
    emitLine("}");
    emitLine("");

    // Helper: Println
    emitLine("private static void println(Object obj) {");
    indent();
    emitLine("System.out.println(toString(obj));");
    dedent();
    emitLine("}");
    emitLine("");
}

private void generateMainMethod() {
    emitLine("");
    emitLine("public static void main(String[] args) {");
    indent();
    emitLine("// Entry point - ejecutar función main() si existe");
    if (functionTable.isDeclared("main")) {
        emitLine("main();");
    } else {
        emitLine("System.out.println(\"No main function defined\");");
    }
    dedent();
    emitLine("}");
}
```

**POR QUÉ:**
- Cada programa FlowScript se convierte en una clase Java con métodos estáticos
- Los imports proveen las utilidades necesarias para el código generado
- El método main permite ejecutar el programa compilado

---

### 4.2 FunctionDeclarationNode

**QUÉ ES:** Declaración de una función con nombre, parámetros, tipo de retorno y cuerpo.

**ESTRUCTURA:**
```java
public class FunctionDeclarationNode {
    String name;
    List<ParameterNode> parameters;
    TypeNode returnType;
    BlockNode body;
}
```

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(FunctionDeclarationNode node) {
    // PASO 1: Guardar contexto
    String previousMethod = context.getCurrentMethodName();
    context.setCurrentMethodName(node.getName());
    context.setInProcess(false);

    // PASO 2: Generar firma de método
    String returnTypeStr = mapType(node.getReturnType());
    emit("public static " + returnTypeStr + " " + node.getName() + "(");

    // PASO 3: Generar parámetros
    List<ParameterNode> params = node.getParameters();
    for (int i = 0; i < params.size(); i++) {
        ParameterNode param = params.get(i);
        String paramType = mapType(param.getType());
        emit(paramType + " " + param.getName());
        if (i < params.size() - 1) {
            emit(", ");
        }
    }
    emitLine(") {");

    // PASO 4: Generar cuerpo
    indent();
    node.getBody().accept(this);
    dedent();

    // PASO 5: Cerrar método
    emitLine("}");

    // PASO 6: Restaurar contexto
    context.setCurrentMethodName(previousMethod);
}
```

**EJEMPLO:**
```flowscript
// FlowScript:
funcion suma(a: entero, b: entero) -> entero {
    resultado = a + b
    retornar resultado
}

// Java generado:
public static int suma(int a, int b) {
    int resultado = a + b;
    return resultado;
}
```

**POR QUÉ:**
- Las funciones de FlowScript son métodos estáticos en Java (no hay estado compartido)
- Los tipos se mapean directamente
- El cuerpo se traduce statement por statement

---

### 4.3 BlockNode

**QUÉ ES:** Bloque de statements (cuerpo de función, cuerpo de if, etc.)

**ESTRUCTURA:**
```java
public class BlockNode {
    List<StatementNode> statements;
}
```

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(BlockNode node) {
    // PASO 1: Crear nuevo scope para variables locales
    Set<String> previousUsedVars = context.getUsedVariables();
    context.setUsedVariables(new HashSet<>(previousUsedVars));

    // PASO 2: Generar cada statement
    for (StatementNode statement : node.getStatements()) {
        statement.accept(this);
    }

    // PASO 3: Restaurar scope
    context.setUsedVariables(previousUsedVars);
}
```

**POR QUÉ:**
- Los bloques crean nuevos scopes de variables
- Cada statement se genera secuencialmente
- El tracking de variables usadas permite declarar variables implícitas

---

### 4.4 VariableDeclarationStatementNode

**QUÉ ES:** Declaración explícita de variable (si existe en FlowScript).

**ESTRUCTURA:**
```java
public class VariableDeclarationStatementNode {
    String name;
    TypeNode type;      // Puede ser null (inferir)
    ExpressionNode value;
}
```

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(VariableDeclarationStatementNode node) {
    // PASO 1: Determinar tipo
    String javaType;
    if (node.getType() != null) {
        javaType = mapType(node.getType());
    } else {
        // Inferir tipo del valor inicial
        javaType = inferType(node.getValue());
    }

    // PASO 2: Generar declaración
    emit(javaType + " " + node.getName());

    // PASO 3: Generar inicialización si existe
    if (node.getValue() != null) {
        emit(" = ");
        node.getValue().accept(this);
    }

    emitLine(";");

    // PASO 4: Marcar variable como declarada
    context.addUsedVariable(node.getName());
}

private String inferType(ExpressionNode expr) {
    // Inferir tipo basado en la expresión
    if (expr instanceof IntegerLiteralNode) return "int";
    if (expr instanceof DecimalLiteralNode) return "double";
    if (expr instanceof BooleanLiteralNode) return "boolean";
    if (expr instanceof StringLiteralNode) return "String";
    if (expr instanceof ListLiteralNode) return "List<Object>";
    if (expr instanceof ObjectLiteralNode) return "Map<String, Object>";
    return "Object";  // Tipo dinámico
}
```

**EJEMPLO:**
```flowscript
// FlowScript:
variable x: entero = 10
variable y: texto = "hola"
variable z = 3.14  // Inferencia de tipo

// Java:
int x = 10;
String y = "hola";
double z = 3.14;
```

---

### 4.5 AssignmentStatementNode

**QUÉ ES:** Asignación a una variable (puede ser declaración implícita en FlowScript).

**ESTRUCTURA:**
```java
public class AssignmentStatementNode {
    ExpressionNode target;  // Identificador o acceso (a.b, a[i])
    ExpressionNode value;
}
```

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(AssignmentStatementNode node) {
    // PASO 1: Verificar si es declaración implícita
    if (node.getTarget() instanceof IdentifierNode) {
        IdentifierNode target = (IdentifierNode) node.getTarget();
        String varName = target.getName();

        // Si la variable NO ha sido declarada, declararla
        if (!context.isVariableDeclared(varName)) {
            // Inferir tipo del valor
            String inferredType = inferType(node.getValue());
            emit(inferredType + " ");
            context.addUsedVariable(varName);
        }
    }

    // PASO 2: Generar target
    node.getTarget().accept(this);

    // PASO 3: Generar asignación
    emit(" = ");

    // PASO 4: Generar valor
    node.getValue().accept(this);

    emitLine(";");
}
```

**EJEMPLO:**
```flowscript
// FlowScript (declaración implícita):
x = 10
y = x + 5
list[0] = 42

// Java:
int x = 10;          // Declaración implícita
int y = x + 5;       // Declaración implícita
list.set(0, 42);     // Asignación a elemento existente
```

**POR QUÉ:**
- FlowScript permite declaraciones implícitas de variables
- El generador debe detectar cuándo una variable se usa por primera vez
- Usa información del SemanticAnalyzer para saber los tipos

---

### 4.6 ReturnStatementNode

**QUÉ ES:** Statement de retorno de función.

**ESTRUCTURA:**
```java
public class ReturnStatementNode {
    ExpressionNode value;  // Puede ser null para funciones void
}
```

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(ReturnStatementNode node) {
    emit("return");

    if (node.getValue() != null) {
        emit(" ");
        node.getValue().accept(this);
    }

    emitLine(";");
}
```

**EJEMPLO:**
```flowscript
// FlowScript:
retornar x + y
retornar

// Java:
return x + y;
return;
```

---

### 4.7 IfStatementNode

**QUÉ ES:** Condicional if-else if-else.

**ESTRUCTURA:**
```java
public class IfStatementNode {
    ExpressionNode condition;
    BlockNode thenBlock;
    List<ElseIfClause> elseIfClauses;  // Optional
    BlockNode elseBlock;                // Optional
}
```

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(IfStatementNode node) {
    // PASO 1: Generar if principal
    emit("if (");
    node.getCondition().accept(this);
    emitLine(") {");

    // PASO 2: Generar bloque then
    indent();
    node.getThenBlock().accept(this);
    dedent();
    emit("}");

    // PASO 3: Generar else if clauses
    if (node.getElseIfClauses() != null) {
        for (ElseIfClause elseIf : node.getElseIfClauses()) {
            emit(" else if (");
            elseIf.getCondition().accept(this);
            emitLine(") {");
            indent();
            elseIf.getBlock().accept(this);
            dedent();
            emit("}");
        }
    }

    // PASO 4: Generar else block
    if (node.getElseBlock() != null) {
        emitLine(" else {");
        indent();
        node.getElseBlock().accept(this);
        dedent();
        emitLine("}");
    } else {
        emitLine("");  // Nueva línea
    }
}
```

**EJEMPLO:**
```flowscript
// FlowScript:
si (x > 0) {
    print("positivo")
} sino_si (x < 0) {
    print("negativo")
} sino {
    print("cero")
}

// Java:
if (x > 0) {
    print("positivo");
} else if (x < 0) {
    print("negativo");
} else {
    print("cero");
}
```

---

### 4.8 ForStatementNode

**QUÉ ES:** Loop que itera sobre una colección.

**ESTRUCTURA:**
```java
public class ForStatementNode {
    String iteratorVariable;
    ExpressionNode iterable;
    BlockNode body;
}
```

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(ForStatementNode node) {
    // PASO 1: Generar for-each de Java
    emit("for (Object " + node.getIteratorVariable() + " : ");

    // PASO 2: Generar expresión iterable
    // Las listas ya son Iterable en Java
    node.getIterable().accept(this);

    emitLine(") {");

    // PASO 3: Generar cuerpo
    indent();
    node.getBody().accept(this);
    dedent();

    emitLine("}");
}
```

**EJEMPLO:**
```flowscript
// FlowScript:
para item en [1, 2, 3] {
    print(item)
}

// Java:
for (Object item : List.of(1, 2, 3)) {
    print(item);
}
```

**POR QUÉ:**
- FlowScript permite iterar sobre listas
- List<> en Java ya implementa Iterable<>
- No necesitamos conversión adicional

---

### 4.9 ExpressionStatementNode

**QUÉ ES:** Una expresión usada como statement (ej: llamada a función).

**ESTRUCTURA:**
```java
public class ExpressionStatementNode {
    ExpressionNode expression;
}
```

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(ExpressionStatementNode node) {
    node.getExpression().accept(this);
    emitLine(";");
}
```

---

### 4.10 BinaryExpressionNode

**QUÉ ES:** Expresión binaria (operadores aritméticos, lógicos, comparación).

**ESTRUCTURA:**
```java
public class BinaryExpressionNode {
    ExpressionNode left;
    Operator operator;
    ExpressionNode right;
}

enum Operator {
    ADD, SUB, MUL, DIV, MOD,        // Aritméticos
    EQ, NEQ, LT, GT, LTE, GTE,      // Comparación
    AND, OR                          // Lógicos
}
```

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(BinaryExpressionNode node) {
    // CASO ESPECIAL: Operadores de FlowScript que no mapean directo
    if (node.getOperator() == Operator.AND) {
        // FlowScript: x y y → Java: x && y
        emit("(");
        node.getLeft().accept(this);
        emit(" && ");
        node.getRight().accept(this);
        emit(")");
        return;
    }

    if (node.getOperator() == Operator.OR) {
        // FlowScript: x o y → Java: x || y
        emit("(");
        node.getLeft().accept(this);
        emit(" || ");
        node.getRight().accept(this);
        emit(")");
        return;
    }

    // CASO GENERAL: Mapeo directo
    emit("(");
    node.getLeft().accept(this);
    emit(" " + mapOperator(node.getOperator()) + " ");
    node.getRight().accept(this);
    emit(")");
}

private String mapOperator(Operator op) {
    return switch (op) {
        case ADD -> "+";
        case SUB -> "-";
        case MUL -> "*";
        case DIV -> "/";
        case MOD -> "%";
        case EQ -> "==";
        case NEQ -> "!=";
        case LT -> "<";
        case GT -> ">";
        case LTE -> "<=";
        case GTE -> ">=";
        case AND -> "&&";
        case OR -> "||";
    };
}
```

**EJEMPLO:**
```flowscript
// FlowScript:
x + y * 2
a > 0 y b < 10
nombre == "Juan" o edad >= 18

// Java:
(x + (y * 2))
((a > 0) && (b < 10))
((nombre == "Juan") || (edad >= 18))
```

**POR QUÉ:**
- La mayoría de operadores mapean directo
- Los operadores lógicos en español ("y", "o") se convierten a "&&", "||"
- Paréntesis aseguran precedencia correcta

---

### 4.11 UnaryExpressionNode

**QUÉ ES:** Expresión unaria (negación, menos unario).

**ESTRUCTURA:**
```java
public class UnaryExpressionNode {
    UnaryOperator operator;
    ExpressionNode operand;
}

enum UnaryOperator {
    NOT,    // no x
    MINUS   // -x
}
```

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(UnaryExpressionNode node) {
    String op = switch (node.getOperator()) {
        case NOT -> "!";
        case MINUS -> "-";
    };

    emit("(" + op);
    node.getOperand().accept(this);
    emit(")");
}
```

**EJEMPLO:**
```flowscript
// FlowScript:
no activo
-valor

// Java:
(!activo)
(-valor)
```

---

### 4.12 FunctionCallNode

**QUÉ ES:** Llamada a función con argumentos.

**ESTRUCTURA:**
```java
public class FunctionCallNode {
    String functionName;
    List<ExpressionNode> arguments;
}
```

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(FunctionCallNode node) {
    // CASO ESPECIAL: Funciones built-in de FlowScript
    if (isBuiltInFunction(node.getFunctionName())) {
        generateBuiltInCall(node);
        return;
    }

    // CASO NORMAL: Llamada a función definida por usuario
    emit(node.getFunctionName() + "(");

    List<ExpressionNode> args = node.getArguments();
    for (int i = 0; i < args.size(); i++) {
        args.get(i).accept(this);
        if (i < args.size() - 1) {
            emit(", ");
        }
    }

    emit(")");
}

private boolean isBuiltInFunction(String name) {
    return Set.of("print", "println", "len", "range", "str", "int", "float")
              .contains(name);
}

private void generateBuiltInCall(FunctionCallNode node) {
    String name = node.getFunctionName();

    // Mapear a métodos helper inline
    switch (name) {
        case "print" -> {
            emit("print(");
            node.getArguments().get(0).accept(this);
            emit(")");
        }
        case "println" -> {
            emit("println(");
            node.getArguments().get(0).accept(this);
            emit(")");
        }
        case "len" -> {
            // len se expande inline
            emit("(");
            ExpressionNode arg = node.getArguments().get(0);
            arg.accept(this);
            emit(" instanceof List ? ((List<?>)");
            arg.accept(this);
            emit(").size() : ((Map<?,?>)");
            arg.accept(this);
            emit(").size())");
        }
        // ... otros built-ins
    }
}
```

**EJEMPLO:**
```flowscript
// FlowScript:
suma(10, 20)
print("Hola")
len([1, 2, 3])

// Java:
suma(10, 20)
print("Hola")
(List.of(1, 2, 3) instanceof List ? ((List<?>)List.of(1, 2, 3)).size() : ((Map<?,?>)List.of(1, 2, 3)).size())
```

---

### 4.13 IdentifierNode

**QUÉ ES:** Referencia a una variable.

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(IdentifierNode node) {
    // CASO ESPECIAL: Contexto de proceso
    if (context.isInProcess()) {
        // En procesos, variables están en un Map<String, Object>
        emit("context.get(\"" + node.getName() + "\")");
    } else {
        // En funciones, acceso directo
        emit(node.getName());
    }
}
```

**POR QUÉ:**
- En funciones normales, las variables son locales de Java
- En procesos BPMN, las variables se almacenan en un contexto compartido

---

### 4.14 MemberAccessNode

**QUÉ ES:** Acceso a miembro de objeto (a.b).

**ESTRUCTURA:**
```java
public class MemberAccessNode {
    ExpressionNode object;
    String memberName;
}
```

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(MemberAccessNode node) {
    // FlowScript usa objetos dinámicos (Map)
    emit("getMember(");
    node.getObject().accept(this);
    emit(", \"" + node.getMemberName() + "\")");
}
```

**EJEMPLO:**
```flowscript
// FlowScript:
usuario.nombre
resultado.exitoso

// Java:
getMember(usuario, "nombre")
getMember(resultado, "exitoso")
```

**POR QUÉ:**
- Los objetos en FlowScript son Map<String, Object>
- El acceso a miembros requiere get() del Map
- El helper inline maneja casting automático

---

### 4.15 IndexAccessNode

**QUÉ ES:** Acceso a elemento de lista por índice (a[i]).

**ESTRUCTURA:**
```java
public class IndexAccessNode {
    ExpressionNode array;
    ExpressionNode index;
}
```

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(IndexAccessNode node) {
    emit("getIndex(");
    node.getArray().accept(this);
    emit(", ");
    node.getIndex().accept(this);
    emit(")");
}
```

**EJEMPLO:**
```flowscript
// FlowScript:
lista[0]
matriz[i][j]

// Java:
getIndex(lista, 0)
getIndex(getIndex(matriz, i), j)
```

---

### 4.16 Literales

**IntegerLiteralNode:**
```java
@Override
public void visit(IntegerLiteralNode node) {
    emit(String.valueOf(node.getValue()));
}
```

**DecimalLiteralNode:**
```java
@Override
public void visit(DecimalLiteralNode node) {
    emit(String.valueOf(node.getValue()));
}
```

**BooleanLiteralNode:**
```java
@Override
public void visit(BooleanLiteralNode node) {
    // FlowScript: verdadero/falso → Java: true/false
    emit(node.getValue() ? "true" : "false");
}
```

**StringLiteralNode:**
```java
@Override
public void visit(StringLiteralNode node) {
    // Escapar caracteres especiales
    String escaped = node.getValue()
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\t", "\\t");
    emit("\"" + escaped + "\"");
}
```

**NullLiteralNode:**
```java
@Override
public void visit(NullLiteralNode node) {
    emit("null");
}
```

**ListLiteralNode:**
```java
@Override
public void visit(ListLiteralNode node) {
    emit("List.of(");

    List<ExpressionNode> elements = node.getElements();
    for (int i = 0; i < elements.size(); i++) {
        elements.get(i).accept(this);
        if (i < elements.size() - 1) {
            emit(", ");
        }
    }

    emit(")");
}
```

**ObjectLiteralNode:**
```java
@Override
public void visit(ObjectLiteralNode node) {
    emit("Map.of(");

    List<ObjectMember> members = node.getMembers();
    for (int i = 0; i < members.size(); i++) {
        ObjectMember member = members.get(i);
        emit("\"" + member.getKey() + "\", ");
        member.getValue().accept(this);

        if (i < members.size() - 1) {
            emit(", ");
        }
    }

    emit(")");
}
```

**EJEMPLO:**
```flowscript
// FlowScript:
[1, 2, 3]
{nombre: "Juan", edad: 30}

// Java:
List.of(1, 2, 3)
Map.of("nombre", "Juan", "edad", 30)
```

---

## 5. GENERACIÓN DE PROCESOS BPMN

### 5.1 Arquitectura de un Proceso

Un proceso BPMN en FlowScript se traduce a una **clase Java** que implementa una **máquina de estados**.

**COMPONENTES:**
1. **Context Map**: Almacena variables del proceso
2. **State Machine**: Ejecuta tareas según el estado actual
3. **Task Methods**: Cada tarea es un método privado
4. **goTo Method**: Cambia el estado actual

### 5.2 ProcessDeclarationNode

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(ProcessDeclarationNode node) {
    String processName = node.getName();
    context.setCurrentProcessName(processName);
    context.setInProcess(true);

    // PASO 1: Generar clase del proceso
    emitLine("public static class " + processName + " {");
    indent();

    // PASO 2: Generar variables de instancia
    emitLine("private Map<String, Object> context;");
    emitLine("private String currentState;");
    emitLine("private boolean running;");
    emitLine("");

    // PASO 3: Generar constructor
    generateProcessConstructor(node);

    // PASO 4: Generar método execute()
    generateProcessExecuteMethod(node);

    // PASO 5: Generar método goTo()
    generateProcessGoToMethod();

    // PASO 6: Generar métodos para cada tarea
    for (ProcessElementNode element : node.getElements()) {
        if (element instanceof TaskElementNode) {
            emitLine("");
            element.accept(this);
        }
    }

    // PASO 7: Cerrar clase
    dedent();
    emitLine("}");

    context.setInProcess(false);
}
```

### 5.3 Generación del Constructor

```java
private void generateProcessConstructor(ProcessDeclarationNode node) {
    String processName = node.getName();

    emitLine("public " + processName + "() {");
    indent();
    emitLine("this.context = new HashMap<>();");
    emitLine("this.running = true;");

    // Inicializar con el elemento de inicio
    StartElementNode startNode = findStartElement(node);
    if (startNode != null) {
        emitLine("this.currentState = \"" + startNode.getTargetTask() + "\";");
    }

    dedent();
    emitLine("}");
    emitLine("");

    // Constructor con contexto inicial
    emitLine("public " + processName + "(Map<String, Object> initialContext) {");
    indent();
    emitLine("this.context = new HashMap<>(initialContext);");
    emitLine("this.running = true;");
    if (startNode != null) {
        emitLine("this.currentState = \"" + startNode.getTargetTask() + "\";");
    }
    dedent();
    emitLine("}");
    emitLine("");
}
```

### 5.4 Generación del Método Execute

```java
private void generateProcessExecuteMethod(ProcessDeclarationNode node) {
    emitLine("public Map<String, Object> execute() {");
    indent();

    emitLine("while (running) {");
    indent();

    // Switch sobre el estado actual
    emitLine("switch (currentState) {");
    indent();

    // Generar case para cada tarea
    for (ProcessElementNode element : node.getElements()) {
        if (element instanceof TaskElementNode) {
            TaskElementNode task = (TaskElementNode) element;
            emitLine("case \"" + task.getName() + "\":");
            indent();
            emitLine("task_" + task.getName() + "();");
            emitLine("break;");
            dedent();
        } else if (element instanceof EndElementNode) {
            EndElementNode endNode = (EndElementNode) element;
            emitLine("case \"" + endNode.getName() + "\":");
            indent();
            emitLine("running = false;");
            emitLine("break;");
            dedent();
        }
    }

    // Default case
    emitLine("default:");
    indent();
    emitLine("throw new RuntimeException(\"Unknown state: \" + currentState);");
    dedent();

    dedent();
    emitLine("}");  // Cierre switch

    dedent();
    emitLine("}");  // Cierre while

    emitLine("return context;");
    dedent();
    emitLine("}");
    emitLine("");
}
```

### 5.5 Generación del Método goTo

```java
private void generateProcessGoToMethod() {
    emitLine("private void goTo(String taskName) {");
    indent();
    emitLine("this.currentState = taskName;");
    dedent();
    emitLine("}");
    emitLine("");
}
```

### 5.6 TaskElementNode

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(TaskElementNode node) {
    String taskName = node.getName();

    // Generar método para la tarea
    emitLine("private void task_" + taskName + "() {");
    indent();

    // Generar acciones de la tarea
    for (StatementNode action : node.getActions()) {
        action.accept(this);
    }

    dedent();
    emitLine("}");
}
```

### 5.7 GotoStatementNode

**ESTRATEGIA DE GENERACIÓN:**

```java
@Override
public void visit(GotoStatementNode node) {
    emitLine("goTo(\"" + node.getTargetName() + "\");");
    emitLine("return;  // Salir de tarea actual");
}
```

**EJEMPLO COMPLETO:**

```flowscript
// FlowScript:
proceso ValidarPedido {
    inicio -> verificar

    tarea verificar {
        accion:
            valido = pedido.monto > 0
            si (valido) {
                ir_a aprobar
            } sino {
                ir_a rechazar
            }
    }

    tarea aprobar {
        accion:
            estado = "aprobado"
            ir_a fin_exito
    }

    tarea rechazar {
        accion:
            estado = "rechazado"
            ir_a fin_fallo
    }

    fin fin_exito
    fin fin_fallo
}

// Java generado:
public static class ValidarPedido {
    private Map<String, Object> context;
    private String currentState;
    private boolean running;

    public ValidarPedido() {
        this.context = new HashMap<>();
        this.running = true;
        this.currentState = "verificar";
    }

    public ValidarPedido(Map<String, Object> initialContext) {
        this.context = new HashMap<>(initialContext);
        this.running = true;
        this.currentState = "verificar";
    }

    public Map<String, Object> execute() {
        while (running) {
            switch (currentState) {
                case "verificar":
                    task_verificar();
                    break;
                case "aprobar":
                    task_aprobar();
                    break;
                case "rechazar":
                    task_rechazar();
                    break;
                case "fin_exito":
                    running = false;
                    break;
                case "fin_fallo":
                    running = false;
                    break;
                default:
                    throw new RuntimeException("Unknown state: " + currentState);
            }
        }
        return context;
    }

    private void goTo(String taskName) {
        this.currentState = taskName;
    }

    private void task_verificar() {
        boolean valido = (boolean) FlowScriptRuntime.getMember(
            context.get("pedido"), "monto") > 0;
        context.put("valido", valido);

        if (valido) {
            goTo("aprobar");
            return;
        } else {
            goTo("rechazar");
            return;
        }
    }

    private void task_aprobar() {
        context.put("estado", "aprobado");
        goTo("fin_exito");
        return;
    }

    private void task_rechazar() {
        context.put("estado", "rechazado");
        goTo("fin_fallo");
        return;
    }
}
```

---

## 6. MANEJO DE TIPOS DINÁMICOS

### 6.1 Problema

FlowScript permite tipos dinámicos (variables sin declaración de tipo explícito), pero Java es fuertemente tipado.

### 6.2 Solución: Object + Casting

**ESTRATEGIA:**
- Variables sin tipo explícito → `Object` en Java
- Runtime helpers para conversiones automáticas
- Casting explícito cuando se conoce el tipo

**EJEMPLO:**

```flowscript
// FlowScript:
x = 10
y = "hola"
z = x + 5

// Java:
Object x = 10;
Object y = "hola";
Object z = ((Integer) x) + 5;
```

### 6.3 Runtime Helpers

```java
public class FlowScriptRuntime {
    // Conversión automática de tipos
    public static int toInt(Object obj) {
        if (obj instanceof Integer) return (Integer) obj;
        if (obj instanceof Double) return ((Double) obj).intValue();
        if (obj instanceof String) return Integer.parseInt((String) obj);
        throw new RuntimeException("Cannot convert to int: " + obj);
    }

    public static double toDouble(Object obj) { ... }
    public static boolean toBoolean(Object obj) { ... }
    public static String toString(Object obj) { ... }

    // Operaciones dinámicas
    public static Object add(Object a, Object b) {
        if (a instanceof Integer && b instanceof Integer) {
            return (Integer) a + (Integer) b;
        }
        if (a instanceof String || b instanceof String) {
            return toString(a) + toString(b);
        }
        // ... más casos
    }
}
```

---

## 7. CÓDIGO AUTOCONTENIDO - INLINE HELPERS

### 7.1 Filosofía: Sin Dependencias Externas

**IMPORTANTE:** El código generado NO depende de librerías externas. Todos los métodos helper se generan **inline** dentro de la clase `GeneratedProgram`.

### 7.2 Helpers Generados Automáticamente

El generador crea estos métodos helper al inicio de la clase generada:

#### Conversión de Tipos
```java
private static int toInt(Object obj) {
    if (obj instanceof Integer) return (Integer) obj;
    if (obj instanceof Double) return ((Double) obj).intValue();
    if (obj instanceof String) return Integer.parseInt((String) obj);
    throw new RuntimeException("Cannot convert to int: " + obj);
}

private static double toDouble(Object obj) {
    if (obj instanceof Double) return (Double) obj;
    if (obj instanceof Integer) return ((Integer) obj).doubleValue();
    if (obj instanceof String) return Double.parseDouble((String) obj);
    throw new RuntimeException("Cannot convert to double: " + obj);
}

private static boolean toBoolean(Object obj) {
    if (obj instanceof Boolean) return (Boolean) obj;
    if (obj instanceof String) return Boolean.parseBoolean((String) obj);
    if (obj instanceof Number) return ((Number) obj).doubleValue() != 0;
    return obj != null;
}

private static String toString(Object obj) {
    if (obj == null) return "null";
    return obj.toString();
}
```

#### Acceso a Miembros y Elementos
```java
private static Object getMember(Object obj, String memberName) {
    if (obj instanceof Map) {
        return ((Map<?, ?>) obj).get(memberName);
    }
    throw new RuntimeException("Cannot access member on non-object");
}

private static Object getIndex(Object array, Object index) {
    if (array instanceof List) {
        return ((List<?>) array).get(toInt(index));
    }
    throw new RuntimeException("Cannot index non-list");
}
```

#### Built-in Functions
```java
private static void print(Object obj) {
    System.out.print(toString(obj));
}

private static void println(Object obj) {
    System.out.println(toString(obj));
}
```

### 7.3 Ventajas del Enfoque Inline

1. **Autocontenido**: Un solo archivo `.java` compilable
2. **Sin dependencias**: No requiere JAR adicionales
3. **Portable**: Fácil de distribuir y ejecutar
4. **Simple**: El usuario solo ve código Java estándar

---

## 8. CASOS ESPECIALES

### 8.1 Gateways Exclusivos

Los gateways exclusivos en BPMN son simplemente condicionales if-else dentro de tareas.

**NO se generan como nodos separados**, sino que el flujo condicional se maneja con `ir_a` dentro de tareas.

### 8.2 Parallel Gateway - Ejecución con Threads

#### 8.2.1 Arquitectura del Parallel Gateway

Un Parallel Gateway ejecuta múltiples tareas en paralelo y espera a que todas terminen antes de continuar (JOIN).

**COMPONENTES:**
1. **Split**: Punto donde se dividen las ramas paralelas
2. **Branches**: Lista de tareas a ejecutar en paralelo
3. **Join/Merge**: Punto donde se sincronizan todas las ramas

#### 8.2.2 Estrategia de Generación con Thread

**IMPORTANTE:** Usamos la clase `Thread` de Java (NO ExecutorService) para mantener el código autocontenido.

```java
@Override
public void visit(ParallelGatewayNode node) {
    String gatewayName = node.getName();
    List<String> branches = node.getBranches(); // Lista de nombres de tareas
    String mergeTarget = node.getMergeTargetName();

    // PASO 1: Crear array para almacenar threads
    emitLine("// Parallel Gateway: " + gatewayName);
    emitLine("Thread[] threads_" + gatewayName + " = new Thread[" + branches.size() + "];");
    emitLine("");

    // PASO 2: Crear y lanzar un thread por cada rama
    for (int i = 0; i < branches.size(); i++) {
        String branchTask = branches.get(i);
        emitLine("threads_" + gatewayName + "[" + i + "] = new Thread(() -> {");
        indent();
        emitLine("// Ejecutar tarea en paralelo: " + branchTask);
        emitLine("task_" + branchTask + "();");
        dedent();
        emitLine("});");
        emitLine("threads_" + gatewayName + "[" + i + "].start();");
        emitLine("");
    }

    // PASO 3: Esperar a que todos los threads terminen (JOIN)
    emitLine("// JOIN: Esperar a que todas las ramas terminen");
    emitLine("for (int i = 0; i < threads_" + gatewayName + ".length; i++) {");
    indent();
    emitLine("try {");
    indent();
    emitLine("threads_" + gatewayName + "[i].join();");
    dedent();
    emitLine("} catch (InterruptedException e) {");
    indent();
    emitLine("throw new RuntimeException(\"Thread interrupted in parallel gateway\", e);");
    dedent();
    emitLine("}");
    dedent();
    emitLine("}");
    emitLine("");

    // PASO 4: Continuar con el merge target
    emitLine("// Continuar después del JOIN");
    emitLine("goTo(\"" + mergeTarget + "\");");
}
```

#### 8.2.3 Ejemplo Completo de Parallel Gateway

```flowscript
// FlowScript:
proceso ProcesarPedido {
    inicio -> ValidarDatos

    tarea ValidarDatos {
        accion:
            si (pedido.valido) {
                ir_a ParallelGateway1
            } sino {
                ir_a Rechazar
            }
    }

    gateway_paralelo ParallelGateway1 {
        rama: VerificarStock
        rama: CalcularEnvio
        rama: VerificarPago
        unir: Confirmar
    }

    tarea VerificarStock {
        accion:
            stock_ok = verificar_inventario(pedido.items)
            context.put("stock_ok", stock_ok)
    }

    tarea CalcularEnvio {
        accion:
            costo_envio = calcular_costo(pedido.direccion)
            context.put("costo_envio", costo_envio)
    }

    tarea VerificarPago {
        accion:
            pago_ok = procesar_pago(pedido.pago)
            context.put("pago_ok", pago_ok)
    }

    tarea Confirmar {
        accion:
            stock_ok = context.get("stock_ok")
            pago_ok = context.get("pago_ok")

            si (stock_ok y pago_ok) {
                ir_a Fin_Exito
            } sino {
                ir_a Fin_Error
            }
    }

    tarea Rechazar {
        accion:
            ir_a Fin_Error
    }

    fin Fin_Exito
    fin Fin_Error
}

// Java generado:
public static class ProcesarPedido {
    private Map<String, Object> context;
    private String currentState;
    private boolean running;

    // ... constructores ...

    public Map<String, Object> execute() {
        while (running) {
            switch (currentState) {
                case "ValidarDatos":
                    task_ValidarDatos();
                    break;
                case "ParallelGateway1":
                    parallelGateway_ParallelGateway1();
                    break;
                case "Confirmar":
                    task_Confirmar();
                    break;
                case "Rechazar":
                    task_Rechazar();
                    break;
                case "Fin_Exito":
                    running = false;
                    break;
                case "Fin_Error":
                    running = false;
                    break;
                default:
                    throw new RuntimeException("Unknown state: " + currentState);
            }
        }
        return context;
    }

    private void task_ValidarDatos() {
        Object pedido = context.get("pedido");
        boolean valido = toBoolean(getMember(pedido, "valido"));

        if (valido) {
            goTo("ParallelGateway1");
            return;
        } else {
            goTo("Rechazar");
            return;
        }
    }

    private void parallelGateway_ParallelGateway1() {
        // Parallel Gateway: ParallelGateway1
        Thread[] threads_ParallelGateway1 = new Thread[3];

        threads_ParallelGateway1[0] = new Thread(() -> {
            // Ejecutar tarea en paralelo: VerificarStock
            task_VerificarStock();
        });
        threads_ParallelGateway1[0].start();

        threads_ParallelGateway1[1] = new Thread(() -> {
            // Ejecutar tarea en paralelo: CalcularEnvio
            task_CalcularEnvio();
        });
        threads_ParallelGateway1[1].start();

        threads_ParallelGateway1[2] = new Thread(() -> {
            // Ejecutar tarea en paralelo: VerificarPago
            task_VerificarPago();
        });
        threads_ParallelGateway1[2].start();

        // JOIN: Esperar a que todas las ramas terminen
        for (int i = 0; i < threads_ParallelGateway1.length; i++) {
            try {
                threads_ParallelGateway1[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException("Thread interrupted in parallel gateway", e);
            }
        }

        // Continuar después del JOIN
        goTo("Confirmar");
        return;
    }

    private void task_VerificarStock() {
        Object pedido = context.get("pedido");
        Object items = getMember(pedido, "items");
        boolean stock_ok = verificar_inventario(items);
        context.put("stock_ok", stock_ok);
    }

    private void task_CalcularEnvio() {
        Object pedido = context.get("pedido");
        Object direccion = getMember(pedido, "direccion");
        Object costo_envio = calcular_costo(direccion);
        context.put("costo_envio", costo_envio);
    }

    private void task_VerificarPago() {
        Object pedido = context.get("pedido");
        Object pago = getMember(pedido, "pago");
        boolean pago_ok = procesar_pago(pago);
        context.put("pago_ok", pago_ok);
    }

    private void task_Confirmar() {
        boolean stock_ok = toBoolean(context.get("stock_ok"));
        boolean pago_ok = toBoolean(context.get("pago_ok"));

        if (stock_ok && pago_ok) {
            goTo("Fin_Exito");
            return;
        } else {
            goTo("Fin_Error");
            return;
        }
    }

    private void task_Rechazar() {
        goTo("Fin_Error");
        return;
    }

    private void goTo(String taskName) {
        this.currentState = taskName;
    }
}
```

#### 8.2.4 Consideraciones Importantes

1. **Contexto Compartido**: Las tareas paralelas comparten el mismo `context` Map
2. **Thread Safety**: Si múltiples threads modifican el contexto simultáneamente, considerar `ConcurrentHashMap`
3. **Excepciones**: Las excepciones en threads se propagan al hilo principal
4. **Sin ExecutorService**: Usamos `Thread` directamente para evitar dependencias

### 8.3 Operaciones de Base de Datos

#### 8.3.1 Funciones DB en FlowScript

FlowScript proporciona dos operaciones básicas de base de datos:
- `db.ejecutar(sql, params)`: Ejecuta INSERT/UPDATE/DELETE, retorna número de filas afectadas
- `db.consultar(sql, params)`: Ejecuta SELECT, retorna lista de mapas (cada fila es un Map)

#### 8.3.2 Estrategia de Generación

**IMPORTANTE:** Usamos `java.sql.*` del JDK (JDBC estándar), sin dependencias externas.

**Helper Methods Inline:**

```java
// Agregar estos helpers al inicio de GeneratedProgram
private static Connection dbConnection = null;

private static Connection getDbConnection() throws SQLException {
    if (dbConnection == null || dbConnection.isClosed()) {
        // Leer configuración de variables de entorno
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (url == null) {
            throw new RuntimeException("DB_URL environment variable not set");
        }

        dbConnection = DriverManager.getConnection(url, user, password);
    }
    return dbConnection;
}

private static int db_ejecutar(String sql, List<Object> params) {
    try {
        Connection conn = getDbConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Setear parámetros
        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }

        int rowsAffected = stmt.executeUpdate();
        stmt.close();

        return rowsAffected;
    } catch (SQLException e) {
        throw new RuntimeException("Database error: " + e.getMessage(), e);
    }
}

private static List<Map<String, Object>> db_consultar(String sql, List<Object> params) {
    try {
        Connection conn = getDbConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        // Setear parámetros
        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }

        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        List<Map<String, Object>> results = new ArrayList<>();

        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = meta.getColumnName(i);
                Object value = rs.getObject(i);
                row.put(columnName, value);
            }
            results.add(row);
        }

        rs.close();
        stmt.close();

        return results;
    } catch (SQLException e) {
        throw new RuntimeException("Database error: " + e.getMessage(), e);
    }
}
```

#### 8.3.3 Generación de Llamadas DB

```java
@Override
public void visit(FunctionCallNode node) {
    String functionName = node.getFunctionName();

    // Detectar llamadas a db.ejecutar
    if (functionName.equals("db.ejecutar")) {
        emit("db_ejecutar(");

        // Primer argumento: SQL string
        node.getArguments().get(0).accept(this);
        emit(", ");

        // Segundo argumento: parámetros (lista)
        node.getArguments().get(1).accept(this);
        emit(")");
        return;
    }

    // Detectar llamadas a db.consultar
    if (functionName.equals("db.consultar")) {
        emit("db_consultar(");

        // Primer argumento: SQL string
        node.getArguments().get(0).accept(this);
        emit(", ");

        // Segundo argumento: parámetros (lista)
        node.getArguments().get(1).accept(this);
        emit(")");
        return;
    }

    // ... resto de generación de llamadas ...
}
```

#### 8.3.4 Ejemplo Completo de Operaciones DB

```flowscript
// FlowScript:
funcion guardar_usuario(nombre: texto, edad: entero) -> entero {
    sql = "INSERT INTO usuarios (nombre, edad) VALUES (?, ?)"
    params = [nombre, edad]
    filas = db.ejecutar(sql, params)
    retornar filas
}

funcion buscar_usuarios(edad_minima: entero) -> lista {
    sql = "SELECT * FROM usuarios WHERE edad >= ?"
    params = [edad_minima]
    resultados = db.consultar(sql, params)
    retornar resultados
}

funcion main() -> vacio {
    // Insertar usuario
    filas_insertadas = guardar_usuario("Juan", 25)
    print("Insertadas: ")
    println(filas_insertadas)

    // Consultar usuarios
    usuarios = buscar_usuarios(18)
    para usuario en usuarios {
        print("Usuario: ")
        print(usuario.nombre)
        print(" - Edad: ")
        println(usuario.edad)
    }
}

// Java generado:
import java.util.*;
import java.sql.*;
import java.net.http.*;
import java.net.*;

public class GeneratedProgram {

    // ========== DB HELPERS INLINE ==========

    private static Connection dbConnection = null;

    private static Connection getDbConnection() throws SQLException {
        if (dbConnection == null || dbConnection.isClosed()) {
            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");

            if (url == null) {
                throw new RuntimeException("DB_URL environment variable not set");
            }

            dbConnection = DriverManager.getConnection(url, user, password);
        }
        return dbConnection;
    }

    private static int db_ejecutar(String sql, List<Object> params) {
        try {
            Connection conn = getDbConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            int rowsAffected = stmt.executeUpdate();
            stmt.close();

            return rowsAffected;
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    private static List<Map<String, Object>> db_consultar(String sql, List<Object> params) {
        try {
            Connection conn = getDbConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            List<Map<String, Object>> results = new ArrayList<>();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = meta.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                results.add(row);
            }

            rs.close();
            stmt.close();

            return results;
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    // ... otros helpers (toInt, toString, print, etc.) ...

    // ========== FUNCIONES ==========

    public static int guardar_usuario(String nombre, int edad) {
        String sql = "INSERT INTO usuarios (nombre, edad) VALUES (?, ?)";
        List<Object> params = List.of(nombre, edad);
        int filas = db_ejecutar(sql, params);
        return filas;
    }

    public static List<Map<String, Object>> buscar_usuarios(int edad_minima) {
        String sql = "SELECT * FROM usuarios WHERE edad >= ?";
        List<Object> params = List.of(edad_minima);
        List<Map<String, Object>> resultados = db_consultar(sql, params);
        return resultados;
    }

    public static void main() {
        int filas_insertadas = guardar_usuario("Juan", 25);
        print("Insertadas: ");
        println(filas_insertadas);

        List<Map<String, Object>> usuarios = buscar_usuarios(18);
        for (Object usuario : usuarios) {
            print("Usuario: ");
            print(getMember(usuario, "nombre"));
            print(" - Edad: ");
            println(getMember(usuario, "edad"));
        }
    }

    public static void main(String[] args) {
        main();
    }
}
```

#### 8.3.5 Configuración de Base de Datos

El código generado espera tres variables de entorno:
- `DB_URL`: JDBC URL (ej: `jdbc:postgresql://localhost:5432/mydb`)
- `DB_USER`: Usuario de la base de datos
- `DB_PASSWORD`: Contraseña

**Ejemplo de ejecución:**
```bash
export DB_URL="jdbc:postgresql://localhost:5432/mydb"
export DB_USER="postgres"
export DB_PASSWORD="secret"
java GeneratedProgram
```

### 8.4 Operaciones HTTP

#### 8.4.1 Funciones HTTP en FlowScript

FlowScript proporciona cuatro operaciones HTTP:
- `http.get(url, headers)`: Realiza GET, retorna objeto respuesta
- `http.post(url, body, headers)`: Realiza POST, retorna objeto respuesta
- `http.put(url, body, headers)`: Realiza PUT, retorna objeto respuesta
- `http.delete(url, headers)`: Realiza DELETE, retorna objeto respuesta

**Objeto Respuesta:**
```
{
    status: 200,
    body: "...",
    headers: {...}
}
```

#### 8.4.2 Estrategia de Generación

**IMPORTANTE:** Usamos `java.net.http.HttpClient` del JDK 11+ (sin dependencias externas).

**Helper Methods Inline:**

```java
// Agregar estos helpers al inicio de GeneratedProgram
private static HttpClient httpClient = null;

private static HttpClient getHttpClient() {
    if (httpClient == null) {
        httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    }
    return httpClient;
}

private static Map<String, Object> http_get(String url, Map<String, Object> headers) {
    try {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
            .uri(new URI(url))
            .GET();

        // Agregar headers
        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                requestBuilder.header(entry.getKey(), toString(entry.getValue()));
            }
        }

        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = getHttpClient().send(request,
            HttpResponse.BodyHandlers.ofString());

        // Construir objeto respuesta
        Map<String, Object> result = new HashMap<>();
        result.put("status", response.statusCode());
        result.put("body", response.body());

        Map<String, Object> responseHeaders = new HashMap<>();
        response.headers().map().forEach((k, v) ->
            responseHeaders.put(k, v.size() == 1 ? v.get(0) : v)
        );
        result.put("headers", responseHeaders);

        return result;
    } catch (Exception e) {
        throw new RuntimeException("HTTP GET error: " + e.getMessage(), e);
    }
}

private static Map<String, Object> http_post(String url, Object body, Map<String, Object> headers) {
    try {
        // Convertir body a JSON string
        String bodyJson = toJsonString(body);

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
            .uri(new URI(url))
            .POST(HttpRequest.BodyPublishers.ofString(bodyJson));

        // Agregar Content-Type por defecto
        requestBuilder.header("Content-Type", "application/json");

        // Agregar headers adicionales
        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                requestBuilder.header(entry.getKey(), toString(entry.getValue()));
            }
        }

        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = getHttpClient().send(request,
            HttpResponse.BodyHandlers.ofString());

        // Construir objeto respuesta
        Map<String, Object> result = new HashMap<>();
        result.put("status", response.statusCode());
        result.put("body", response.body());

        Map<String, Object> responseHeaders = new HashMap<>();
        response.headers().map().forEach((k, v) ->
            responseHeaders.put(k, v.size() == 1 ? v.get(0) : v)
        );
        result.put("headers", responseHeaders);

        return result;
    } catch (Exception e) {
        throw new RuntimeException("HTTP POST error: " + e.getMessage(), e);
    }
}

private static Map<String, Object> http_put(String url, Object body, Map<String, Object> headers) {
    try {
        String bodyJson = toJsonString(body);

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
            .uri(new URI(url))
            .PUT(HttpRequest.BodyPublishers.ofString(bodyJson));

        requestBuilder.header("Content-Type", "application/json");

        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                requestBuilder.header(entry.getKey(), toString(entry.getValue()));
            }
        }

        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = getHttpClient().send(request,
            HttpResponse.BodyHandlers.ofString());

        Map<String, Object> result = new HashMap<>();
        result.put("status", response.statusCode());
        result.put("body", response.body());

        Map<String, Object> responseHeaders = new HashMap<>();
        response.headers().map().forEach((k, v) ->
            responseHeaders.put(k, v.size() == 1 ? v.get(0) : v)
        );
        result.put("headers", responseHeaders);

        return result;
    } catch (Exception e) {
        throw new RuntimeException("HTTP PUT error: " + e.getMessage(), e);
    }
}

private static Map<String, Object> http_delete(String url, Map<String, Object> headers) {
    try {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
            .uri(new URI(url))
            .DELETE();

        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                requestBuilder.header(entry.getKey(), toString(entry.getValue()));
            }
        }

        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = getHttpClient().send(request,
            HttpResponse.BodyHandlers.ofString());

        Map<String, Object> result = new HashMap<>();
        result.put("status", response.statusCode());
        result.put("body", response.body());

        Map<String, Object> responseHeaders = new HashMap<>();
        response.headers().map().forEach((k, v) ->
            responseHeaders.put(k, v.size() == 1 ? v.get(0) : v)
        );
        result.put("headers", responseHeaders);

        return result;
    } catch (Exception e) {
        throw new RuntimeException("HTTP DELETE error: " + e.getMessage(), e);
    }
}

// Helper para convertir objetos a JSON string
private static String toJsonString(Object obj) {
    if (obj == null) return "null";
    if (obj instanceof String) return "\"" + ((String) obj).replace("\"", "\\\"") + "\"";
    if (obj instanceof Number || obj instanceof Boolean) return obj.toString();

    if (obj instanceof Map) {
        Map<?, ?> map = (Map<?, ?>) obj;
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!first) json.append(",");
            json.append("\"").append(entry.getKey()).append("\":");
            json.append(toJsonString(entry.getValue()));
            first = false;
        }
        json.append("}");
        return json.toString();
    }

    if (obj instanceof List) {
        List<?> list = (List<?>) obj;
        StringBuilder json = new StringBuilder("[");
        boolean first = true;
        for (Object item : list) {
            if (!first) json.append(",");
            json.append(toJsonString(item));
            first = false;
        }
        json.append("]");
        return json.toString();
    }

    return "\"" + obj.toString() + "\"";
}
```

#### 8.4.3 Generación de Llamadas HTTP

```java
@Override
public void visit(FunctionCallNode node) {
    String functionName = node.getFunctionName();

    // Detectar http.get
    if (functionName.equals("http.get")) {
        emit("http_get(");
        node.getArguments().get(0).accept(this); // url
        emit(", ");
        node.getArguments().get(1).accept(this); // headers
        emit(")");
        return;
    }

    // Detectar http.post
    if (functionName.equals("http.post")) {
        emit("http_post(");
        node.getArguments().get(0).accept(this); // url
        emit(", ");
        node.getArguments().get(1).accept(this); // body
        emit(", ");
        node.getArguments().get(2).accept(this); // headers
        emit(")");
        return;
    }

    // Detectar http.put
    if (functionName.equals("http.put")) {
        emit("http_put(");
        node.getArguments().get(0).accept(this); // url
        emit(", ");
        node.getArguments().get(1).accept(this); // body
        emit(", ");
        node.getArguments().get(2).accept(this); // headers
        emit(")");
        return;
    }

    // Detectar http.delete
    if (functionName.equals("http.delete")) {
        emit("http_delete(");
        node.getArguments().get(0).accept(this); // url
        emit(", ");
        node.getArguments().get(1).accept(this); // headers
        emit(")");
        return;
    }

    // ... resto de generación de llamadas ...
}
```

#### 8.4.4 Ejemplo Completo de Operaciones HTTP

```flowscript
// FlowScript:
funcion obtener_usuarios() -> lista {
    headers = {Authorization: "Bearer token123"}
    respuesta = http.get("https://api.ejemplo.com/users", headers)

    si (respuesta.status == 200) {
        retornar respuesta.body
    } sino {
        retornar []
    }
}

funcion crear_usuario(nombre: texto, email: texto) -> objeto {
    body = {nombre: nombre, email: email}
    headers = {Authorization: "Bearer token123"}

    respuesta = http.post("https://api.ejemplo.com/users", body, headers)
    retornar respuesta
}

funcion main() -> vacio {
    // Obtener usuarios
    usuarios = obtener_usuarios()
    print("Usuarios: ")
    println(usuarios)

    // Crear nuevo usuario
    resultado = crear_usuario("Juan", "juan@email.com")
    print("Status: ")
    println(resultado.status)
}

// Java generado (parcial, mostrando helpers HTTP):
import java.util.*;
import java.sql.*;
import java.net.http.*;
import java.net.*;

public class GeneratedProgram {

    // ========== HTTP HELPERS INLINE ==========

    private static HttpClient httpClient = null;

    private static HttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        }
        return httpClient;
    }

    // ... http_get, http_post, http_put, http_delete methods ...
    // ... toJsonString helper ...

    // ========== FUNCIONES ==========

    public static Object obtener_usuarios() {
        Map<String, Object> headers = Map.of("Authorization", "Bearer token123");
        Map<String, Object> respuesta = http_get("https://api.ejemplo.com/users", headers);

        if (toInt(respuesta.get("status")) == 200) {
            return respuesta.get("body");
        } else {
            return List.of();
        }
    }

    public static Map<String, Object> crear_usuario(String nombre, String email) {
        Map<String, Object> body = Map.of("nombre", nombre, "email", email);
        Map<String, Object> headers = Map.of("Authorization", "Bearer token123");

        Map<String, Object> respuesta = http_post("https://api.ejemplo.com/users", body, headers);
        return respuesta;
    }

    public static void main() {
        Object usuarios = obtener_usuarios();
        print("Usuarios: ");
        println(usuarios);

        Map<String, Object> resultado = crear_usuario("Juan", "juan@email.com");
        print("Status: ");
        println(getMember(resultado, "status"));
    }

    public static void main(String[] args) {
        main();
    }
}
```

### 8.5 Manejo de Nulos

FlowScript usa `nulo`, Java usa `null`.

**ESTRATEGIA:** Mapeo directo en NullLiteralNode.

---

## 9. RESUMEN Y PRÓXIMOS PASOS

### 9.1 Resumen

Este documento describe la estrategia completa de generación de código Java desde el AST de FlowScript:

1. **Visitor Pattern**: Cada nodo implementa `accept(ASTVisitor)`
2. **StringBuilder**: Acumula código generado con indentación
3. **Mapeo de Tipos**: FlowScript → Java
4. **Procesos como State Machines**: Cada proceso es una clase Java
5. **Runtime Library**: Provee soporte para tipos dinámicos

### 9.2 Próximos Pasos de Implementación

1. **Crear CodeGenerator.java** con estructura base
2. **Implementar visitor methods** uno por uno (empezar con los simples)
3. **Crear FlowScriptRuntime.java** con helpers necesarios
4. **Implementar generación de procesos** (lo más complejo)
5. **Crear tests** para verificar código generado
6. **Optimizaciones**: Eliminar castings innecesarios, inline de constantes

### 9.3 Testing

Para cada tipo de nodo:
1. Crear ejemplo de código FlowScript
2. Generar código Java
3. Compilar código Java
4. Ejecutar y verificar resultado correcto

---

## APÉNDICE A: EJEMPLO COMPLETO

### Código FlowScript:

```flowscript
funcion calcular(x: entero) -> entero {
    retornar x * 2
}

proceso ValidarDatos {
    inicio -> validar

    tarea validar {
        accion:
            resultado = calcular(valor)
            si (resultado > 10) {
                ir_a fin_exito
            } sino {
                ir_a fin_fallo
            }
    }

    fin fin_exito
    fin fin_fallo
}
```

### Código Java Generado:

```java
import java.util.*;
import java.sql.*;
import java.net.http.*;
import java.net.*;

public class GeneratedProgram {

    // ========== MÉTODOS HELPER INLINE ==========

    private static int toInt(Object obj) {
        if (obj instanceof Integer) return (Integer) obj;
        if (obj instanceof Double) return ((Double) obj).intValue();
        if (obj instanceof String) return Integer.parseInt((String) obj);
        throw new RuntimeException("Cannot convert to int: " + obj);
    }

    private static String toString(Object obj) {
        if (obj == null) return "null";
        return obj.toString();
    }

    private static Object getMember(Object obj, String memberName) {
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).get(memberName);
        }
        throw new RuntimeException("Cannot access member on non-object");
    }

    private static Object getIndex(Object array, Object index) {
        if (array instanceof List) {
            return ((List<?>) array).get(toInt(index));
        }
        throw new RuntimeException("Cannot index non-list");
    }

    private static void print(Object obj) {
        System.out.print(toString(obj));
    }

    private static void println(Object obj) {
        System.out.println(toString(obj));
    }

    // ========== FUNCIONES GLOBALES ==========

    public static int calcular(int x) {
        return (x * 2);
    }

    // ========== CLASES DE PROCESOS ==========

    public static class ValidarDatos {
        private Map<String, Object> context;
        private String currentState;
        private boolean running;

        public ValidarDatos() {
            this.context = new HashMap<>();
            this.running = true;
            this.currentState = "validar";
        }

        public ValidarDatos(Map<String, Object> initialContext) {
            this.context = new HashMap<>(initialContext);
            this.running = true;
            this.currentState = "validar";
        }

        public Map<String, Object> execute() {
            while (running) {
                switch (currentState) {
                    case "validar":
                        task_validar();
                        break;
                    case "fin_exito":
                        running = false;
                        break;
                    case "fin_fallo":
                        running = false;
                        break;
                    default:
                        throw new RuntimeException("Unknown state: " + currentState);
                }
            }
            return context;
        }

        private void goTo(String taskName) {
            this.currentState = taskName;
        }

        private void task_validar() {
            int resultado = calcular(toInt(context.get("valor")));
            context.put("resultado", resultado);

            if (resultado > 10) {
                goTo("fin_exito");
                return;
            } else {
                goTo("fin_fallo");
                return;
            }
        }
    }

    // ========== MAIN ==========

    public static void main(String[] args) {
        // Entry point
        System.out.println("No main function defined");
    }
}
```

---

**FIN DEL DOCUMENTO**

Este documento debe servir como guía completa para implementar el generador de código. Cada decisión de diseño está justificada y cada nodo está explicado en detalle.
