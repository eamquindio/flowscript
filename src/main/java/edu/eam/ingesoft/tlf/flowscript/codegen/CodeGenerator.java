package edu.eam.ingesoft.tlf.flowscript.codegen;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.literals.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.process.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.types.*;
import edu.eam.ingesoft.tlf.flowscript.semantic.*;

/**
 * Generador de código Java a partir del AST de FlowScript.
 * Implementa el patrón Visitor para recorrer el AST y generar código autocontenido.
 */
public class CodeGenerator implements ASTVisitor {
    private StringBuilder output;
    private int indentLevel;
    private SymbolTable symbolTable;
    private FunctionTable functionTable;
    private ProcessTable processTable;
    private CodeGenContext context;

    public CodeGenerator() {
        this.output = new StringBuilder();
        this.indentLevel = 0;
        this.context = new CodeGenContext();
    }

    /**
     * Genera código Java a partir de un programa FlowScript.
     */
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

    /**
     * Obtiene el código generado hasta el momento (útil para testing).
     */
    public String getGeneratedCode() {
        return output.toString();
    }

    // ========== Helpers de Emisión ==========

    /**
     * Emite código sin nueva línea.
     */
    private void emit(String code) {
        output.append(code);
    }

    /**
     * Emite código con indentación y nueva línea.
     */
    private void emitLine(String code) {
        output.append("    ".repeat(indentLevel)).append(code).append("\n");
    }

    /**
     * Emite solo indentación.
     */
    private void emitIndent() {
        output.append("    ".repeat(indentLevel));
    }

    /**
     * Incrementa nivel de indentación.
     */
    private void indent() {
        indentLevel++;
    }

    /**
     * Decrementa nivel de indentación.
     */
    private void dedent() {
        if (indentLevel > 0) {
            indentLevel--;
        }
    }

    // ========== Mapeo de Tipos ==========

    /**
     * Mapea un tipo de FlowScript a su equivalente en Java.
     */
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

    // ========== Generación de Helpers Inline ==========

    /**
     * Genera métodos helper inline para soporte runtime.
     */
    private void generateInlineHelpers() {
        emitLine("");
        emitLine("// ========== MÉTODOS HELPER INLINE ==========");
        emitLine("");

        // Helper: getMember - Acceso a miembro de objeto
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

        // Helper: getIndex - Acceso a índice de lista
        emitLine("private static Object getIndex(Object array, Object index) {");
        indent();
        emitLine("if (array instanceof List) {");
        indent();
        emitLine("int idx = (index instanceof Integer) ? (Integer) index : ((Number) index).intValue();");
        emitLine("return ((List<?>) array).get(idx);");
        dedent();
        emitLine("}");
        emitLine("throw new RuntimeException(\"Cannot index non-list\");");
        dedent();
        emitLine("}");
        emitLine("");

        // Helper: print
        emitLine("private static void print(Object obj) {");
        indent();
        emitLine("System.out.print(obj == null ? \"null\" : obj.toString());");
        dedent();
        emitLine("}");
        emitLine("");

        // Helper: println
        emitLine("private static void println(Object obj) {");
        indent();
        emitLine("System.out.println(obj == null ? \"null\" : obj.toString());");
        dedent();
        emitLine("}");
        emitLine("");
    }

    // ========== Implementación de ASTVisitor (stubs) ==========

    @Override
    public void visit(ProgramNode node) {
        // PASO 1: Generar imports
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

        // PASO 5: Cerrar clase
        dedent();
        emitLine("}");
    }

    @Override
    public void visit(FunctionDeclarationNode node) {
        // PASO 1: Guardar contexto
        String previousMethod = context.getCurrentMethodName();
        context.setCurrentMethodName(node.getName());
        context.setInProcess(false);

        // PASO 2: Generar firma de método
        String returnTypeStr = mapType(node.getReturnType());
        emitIndent();
        emit("public static " + returnTypeStr + " " + node.getName() + "(");

        // PASO 3: Generar parámetros
        java.util.List<ParameterNode> params = node.getParameters();
        for (int i = 0; i < params.size(); i++) {
            ParameterNode param = params.get(i);
            String paramType = mapType(param.getType());
            emit(paramType + " " + param.getName());
            if (i < params.size() - 1) {
                emit(", ");
            }
        }
        emit(") {\n");

        // PASO 4: Generar cuerpo
        indent();
        node.getBody().accept(this);
        dedent();

        // PASO 5: Cerrar método
        emitLine("}");

        // PASO 6: Restaurar contexto
        context.setCurrentMethodName(previousMethod);
    }

    @Override
    public void visit(ProcessDeclarationNode node) {
        // PASO 1: Guardar contexto
        String previousMethod = context.getCurrentMethodName();
        boolean previousInProcess = context.isInProcess();
        context.setCurrentProcessName(node.getName());
        context.setInProcess(true);

        // PASO 2: Generar clase estática para el proceso
        emitLine("public static class " + node.getName() + " {");
        indent();

        // PASO 3: Generar clase Context interna
        emitLine("");
        emitLine("private static class Context {");
        indent();
        emitLine("// Process variables stored in context");
        emitLine("java.util.Map<String, Object> variables = new java.util.HashMap<>();");
        dedent();
        emitLine("}");
        emitLine("");

        // PASO 4: Campo context
        emitLine("private Context context = new Context();");
        emitLine("");

        // PASO 5: Generar métodos para cada elemento (tareas y gateways)
        for (ProcessElementNode element : node.getElements()) {
            if (element instanceof TaskElementNode) {
                generateTaskMethod((TaskElementNode) element);
            } else if (element instanceof ExclusiveGatewayNode) {
                generateExclusiveGatewayMethod((ExclusiveGatewayNode) element);
            } else if (element instanceof ParallelGatewayNode) {
                generateParallelGatewayMethod((ParallelGatewayNode) element);
            }
            // Start and End elements don't generate methods
        }

        // PASO 6: Generar método run()
        generateProcessRunMethod(node);

        // PASO 7: Cerrar clase
        dedent();
        emitLine("}");

        // PASO 8: Restaurar contexto
        context.setCurrentMethodName(previousMethod);
        context.setInProcess(previousInProcess);
    }

    /**
     * Genera un método para una tarea del proceso.
     */
    private void generateTaskMethod(TaskElementNode task) {
        emitLine("private void " + task.getName() + "() {");
        indent();

        // Generar el cuerpo de la tarea
        for (StatementNode statement : task.getActions()) {
            statement.accept(this);
        }

        dedent();
        emitLine("}");
        emitLine("");
    }

    /**
     * Genera un método para un gateway exclusivo.
     */
    private void generateExclusiveGatewayMethod(ExclusiveGatewayNode gateway) {
        emitLine("private void " + gateway.getName() + "() {");
        indent();

        java.util.List<ExclusiveGatewayNode.ConditionalBranch> branches = gateway.getBranches();
        for (int i = 0; i < branches.size(); i++) {
            ExclusiveGatewayNode.ConditionalBranch branch = branches.get(i);

            if (branch.isElseBranch()) {
                // Rama else
                emitIndent();
                emit("} else {\n");
                indent();
                emitLine(branch.getTargetTaskName() + "();");
                dedent();
            } else {
                // Rama if o else-if
                if (i == 0) {
                    emitIndent();
                    emit("if (");
                    branch.getCondition().accept(this);
                    emit(") {\n");
                } else {
                    emitIndent();
                    emit("} else if (");
                    branch.getCondition().accept(this);
                    emit(") {\n");
                }

                indent();
                emitLine(branch.getTargetTaskName() + "();");
                dedent();
            }
        }

        // Cerrar el último if
        emitLine("}");

        dedent();
        emitLine("}");
        emitLine("");
    }

    /**
     * Genera un método para un gateway paralelo (usa threads).
     */
    private void generateParallelGatewayMethod(ParallelGatewayNode gateway) {
        emitLine("private void " + gateway.getName() + "() {");
        indent();

        emitLine("// Parallel execution with threads");
        emitLine("java.util.List<Thread> threads = new java.util.ArrayList<>();");
        emitLine("");

        // Crear un thread por cada rama
        for (ParallelGatewayNode.ParallelBranch branch : gateway.getBranches()) {
            emitLine("threads.add(new Thread(() -> " + branch.getTargetTaskName() + "()));");
        }

        emitLine("");
        emitLine("// Start all threads");
        emitLine("for (Thread t : threads) t.start();");
        emitLine("");
        emitLine("// Wait for all threads to complete");
        emitLine("try {");
        indent();
        emitLine("for (Thread t : threads) t.join();");
        dedent();
        emitLine("} catch (InterruptedException e) {");
        indent();
        emitLine("throw new RuntimeException(e);");
        dedent();
        emitLine("}");

        // After all parallel tasks complete, call merge target
        if (gateway.getMergeTargetName() != null && !gateway.getMergeTargetName().isEmpty()) {
            emitLine("");
            emitLine("// Continue to merge point");
            emitLine(gateway.getMergeTargetName() + "();");
        }

        dedent();
        emitLine("}");
        emitLine("");
    }

    /**
     * Genera el método run() que ejecuta el flujo del proceso.
     * Para procesos simples (sin gateways), ejecuta tareas en secuencia.
     * Para procesos complejos, sigue el flujo definido por start y gateways.
     */
    private void generateProcessRunMethod(ProcessDeclarationNode process) {
        emitLine("public void run() {");
        indent();

        // Buscar el elemento Start
        StartElementNode startElement = null;
        boolean hasGateways = false;

        for (ProcessElementNode element : process.getElements()) {
            if (element instanceof StartElementNode) {
                startElement = (StartElementNode) element;
            }
            if (element instanceof ExclusiveGatewayNode || element instanceof ParallelGatewayNode) {
                hasGateways = true;
            }
        }

        if (hasGateways) {
            // Proceso complejo: seguir el flujo definido por start
            if (startElement != null) {
                emitLine(startElement.getTargetTaskName() + "();");
            }
        } else {
            // Proceso simple lineal: llamar tareas en secuencia
            for (ProcessElementNode element : process.getElements()) {
                if (element instanceof TaskElementNode) {
                    emitLine(((TaskElementNode) element).getName() + "();");
                }
            }
        }

        dedent();
        emitLine("}");
        emitLine("");
    }

    @Override
    public void visit(BlockNode node) {
        // Generar cada statement
        for (StatementNode statement : node.getStatements()) {
            statement.accept(this);
        }
    }

    @Override
    public void visit(VariableDeclarationStatementNode node) {
        // Generar declaración de variable (FlowScript usa inferencia de tipos)
        emitIndent();
        // Por ahora, usar Object para variables con inferencia de tipos
        emit("Object " + node.getName());

        if (node.getValue() != null) {
            emit(" = ");
            node.getValue().accept(this);
        }

        emit(";\n");
        context.addUsedVariable(node.getName());
    }

    @Override
    public void visit(AssignmentStatementNode node) {
        emitIndent();

        // En procesos, usar context.variables.put()
        if (context.isInProcess() && node.getTarget() instanceof IdentifierNode) {
            String varName = ((IdentifierNode) node.getTarget()).getName();
            emit("context.variables.put(\"" + varName + "\", ");
            node.getValue().accept(this);
            emit(");\n");
            return;
        }

        // En funciones: declaración e inicialización normal
        if (node.getTarget() instanceof IdentifierNode) {
            String varName = ((IdentifierNode) node.getTarget()).getName();

            // Si la variable NO ha sido declarada, declararla (FlowScript permite declaración implícita)
            if (!context.isVariableDeclared(varName)) {
                // Inferir tipo como Object por ahora
                emit("Object ");
                context.addUsedVariable(varName);
            }
        }

        // Generar target = value;
        node.getTarget().accept(this);
        emit(" = ");
        node.getValue().accept(this);
        emit(";\n");
    }

    @Override
    public void visit(ReturnStatementNode node) {
        emitIndent();
        emit("return");

        if (node.getValue() != null) {
            emit(" ");
            node.getValue().accept(this);
        }

        emit(";\n");
    }

    @Override
    public void visit(ExpressionStatementNode node) {
        emitIndent();
        node.getExpression().accept(this);
        emit(";\n");
    }

    @Override
    public void visit(IfStatementNode node) {
        // Generate if-else-if-else chain
        java.util.List<IfStatementNode.ConditionalBranch> branches = node.getBranches();

        for (int i = 0; i < branches.size(); i++) {
            IfStatementNode.ConditionalBranch branch = branches.get(i);

            if (branch.isElseBranch()) {
                // This is the else branch (condition is null)
                emitIndent();
                emit("} else {\n");
                indent();
                branch.getThenBlock().accept(this);
                dedent();
            } else {
                // This is an if or else-if branch
                if (i == 0) {
                    // First branch: if
                    emitIndent();
                    emit("if (");
                    branch.getCondition().accept(this);
                    emit(") {\n");
                } else {
                    // Subsequent branches: else if
                    emitIndent();
                    emit("} else if (");
                    branch.getCondition().accept(this);
                    emit(") {\n");
                }

                indent();
                branch.getThenBlock().accept(this);
                dedent();
            }
        }

        // Close the final brace
        emitLine("}");
    }

    @Override
    public void visit(ForStatementNode node) {
        // Generate Java for-each loop: for (Object var : iterable) { ... }
        emitIndent();
        emit("for (Object " + node.getIteratorVariable() + " : ");

        // Cast the iterable to List<Object> for type safety
        emit("(List<Object>) ");
        node.getIterable().accept(this);
        emit(") {\n");

        // Generate loop body
        indent();
        node.getBody().accept(this);
        dedent();

        // Close loop
        emitLine("}");
    }

    @Override
    public void visit(GotoStatementNode node) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void visit(BinaryExpressionNode node) {
        emit("(");
        node.getLeft().accept(this);
        emit(" " + mapOperator(node.getOperator()) + " ");
        node.getRight().accept(this);
        emit(")");
    }

    @Override
    public void visit(UnaryExpressionNode node) {
        UnaryExpressionNode.Operator operator = node.getOperator();
        String op = switch (operator) {
            case LOGICAL_NOT -> "!";
            case NEGATE -> "-";
        };

        emit("(" + op);
        node.getOperand().accept(this);
        emit(")");
    }

    /**
     * Mapea un operador de FlowScript a su equivalente en Java.
     */
    private String mapOperator(BinaryExpressionNode.Operator operator) {
        return switch (operator) {
            case ADD -> "+";
            case SUBTRACT -> "-";
            case MULTIPLY -> "*";
            case DIVIDE -> "/";
            case MODULO -> "%";
            case EQUAL -> "==";
            case NOT_EQUAL -> "!=";
            case LESS_THAN -> "<";
            case GREATER_THAN -> ">";
            case LESS_EQUAL -> "<=";
            case GREATER_EQUAL -> ">=";
            case AND -> "&&";  // "y" → "&&"
            case OR -> "||";   // "o" → "||"
        };
    }

    @Override
    public void visit(FunctionCallNode node) {
        // Caso especial: built-in functions (print, println)
        if (node.getFunction() instanceof IdentifierNode) {
            String functionName = ((IdentifierNode) node.getFunction()).getName();

            // Built-in functions se llaman directamente, no desde context
            if (functionName.equals("print") || functionName.equals("println")) {
                emit(functionName + "(");

                java.util.List<?> args = node.getArguments();
                for (int i = 0; i < args.size(); i++) {
                    ((ExpressionNode) args.get(i)).accept(this);
                    if (i < args.size() - 1) {
                        emit(", ");
                    }
                }

                emit(")");
                return;
            }
        }

        // Caso general: llamada a función definida por usuario o miembro de objeto
        node.getFunction().accept(this);
        emit("(");

        java.util.List<?> args = node.getArguments();
        for (int i = 0; i < args.size(); i++) {
            ((ExpressionNode) args.get(i)).accept(this);
            if (i < args.size() - 1) {
                emit(", ");
            }
        }

        emit(")");
    }

    @Override
    public void visit(MemberAccessNode node) {
        // Acceso a miembro de objeto: usa helper getMember
        emit("getMember(");
        node.getObject().accept(this);
        emit(", \"" + node.getMemberName() + "\")");
    }

    @Override
    public void visit(IndexAccessNode node) {
        // Acceso a índice de lista: usa helper getIndex
        emit("getIndex(");
        node.getArray().accept(this);
        emit(", ");
        node.getIndex().accept(this);
        emit(")");
    }

    @Override
    public void visit(IdentifierNode node) {
        // En procesos: acceder desde context.variables
        if (context.isInProcess()) {
            emit("context.variables.get(\"" + node.getName() + "\")");
        } else {
            // En funciones: acceso directo a variable
            emit(node.getName());
        }
    }

    @Override
    public void visit(IntegerLiteralNode node) {
        emit(String.valueOf(node.getValue()));
    }

    @Override
    public void visit(DecimalLiteralNode node) {
        emit(String.valueOf(node.getValue()));
    }

    @Override
    public void visit(BooleanLiteralNode node) {
        emit(node.getValue() ? "true" : "false");
    }

    @Override
    public void visit(StringLiteralNode node) {
        // Escapar caracteres especiales
        String escaped = node.getValue()
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\t", "\\t")
            .replace("\r", "\\r");
        emit("\"" + escaped + "\"");
    }

    @Override
    public void visit(NullLiteralNode node) {
        emit("null");
    }

    @Override
    public void visit(ListLiteralNode node) {
        emit("List.of(");

        java.util.List<ExpressionNode> elements = node.getElements();
        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).accept(this);
            if (i < elements.size() - 1) {
                emit(", ");
            }
        }

        emit(")");
    }

    @Override
    public void visit(ObjectLiteralNode node) {
        emit("Map.of(");

        java.util.List<ObjectLiteralNode.ObjectMember> members = node.getMembers();
        for (int i = 0; i < members.size(); i++) {
            ObjectLiteralNode.ObjectMember member = members.get(i);
            emit("\"" + member.getKey() + "\", ");
            member.getValue().accept(this);

            if (i < members.size() - 1) {
                emit(", ");
            }
        }

        emit(")");
    }

    @Override
    public void visit(StartElementNode node) {
        // Process elements are handled inside ProcessDeclarationNode
        // These visitor methods are not called directly
    }

    @Override
    public void visit(TaskElementNode node) {
        // Process elements are handled inside ProcessDeclarationNode
        // These visitor methods are not called directly
    }

    @Override
    public void visit(ExclusiveGatewayNode node) {
        // Process elements are handled inside ProcessDeclarationNode
        // These visitor methods are not called directly
    }

    @Override
    public void visit(ParallelGatewayNode node) {
        // Process elements are handled inside ProcessDeclarationNode
        // These visitor methods are not called directly
    }

    @Override
    public void visit(EndElementNode node) {
        // Process elements are handled inside ProcessDeclarationNode
        // These visitor methods are not called directly
    }

    @Override
    public void visit(TypeNode node) {
        // Los tipos no generan código por sí mismos
    }

    @Override
    public void visit(ParameterNode node) {
        // Los parámetros se procesan en FunctionDeclarationNode
    }
}
