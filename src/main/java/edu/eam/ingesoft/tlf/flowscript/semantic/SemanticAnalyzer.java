package edu.eam.ingesoft.tlf.flowscript.semantic;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.literals.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.process.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.types.*;

import java.util.*;

/**
 * Analizador semántico que implementa el patrón Visitor para recorrer el AST.
 * Valida: declaraciones duplicadas, referencias no declaradas, tipos, y reglas del lenguaje.
 */
public class SemanticAnalyzer implements ASTVisitor {
    // Tablas de símbolos
    private final SymbolTable globalSymbolTable;
    private SymbolTable currentSymbolTable;
    private final FunctionTable functionTable;
    private final ProcessTable processTable;

    // Estado del análisis
    private final List<SemanticError> errors;
    private FunctionSymbol currentFunction;
    private ProcessSymbol currentProcess;
    private String sourceCode;

    public SemanticAnalyzer() {
        this.globalSymbolTable = new SymbolTable("global");
        this.currentSymbolTable = globalSymbolTable;
        this.functionTable = new FunctionTable();
        this.processTable = new ProcessTable();
        this.errors = new ArrayList<>();
        this.currentFunction = null;
        this.currentProcess = null;
        this.sourceCode = "";
    }

    /**
     * Inicia el análisis semántico del programa.
     */
    public void analyze(ProgramNode program, String sourceCode) {
        this.sourceCode = sourceCode;
        this.errors.clear();

        // Primera pasada: recolectar declaraciones
        collectDeclarations(program);

        // Segunda pasada: analizar con visitor
        program.accept(this);

        // Tercera pasada: validaciones finales
        performFinalValidations();
    }

    /**
     * Primera pasada: recolecta declaraciones de funciones y procesos.
     */
    private void collectDeclarations(ProgramNode program) {
        for (DeclarationNode declaration : program.getDeclarations()) {
            if (declaration instanceof FunctionDeclarationNode) {
                collectFunctionDeclaration((FunctionDeclarationNode) declaration);
            } else if (declaration instanceof ProcessDeclarationNode) {
                collectProcessDeclaration((ProcessDeclarationNode) declaration);
            }
        }
    }

    private void collectFunctionDeclaration(FunctionDeclarationNode node) {
        String functionName = node.getName();

        if (functionTable.isDeclared(functionName)) {
            addError(SemanticError.ErrorType.DUPLICATE_DECLARATION,
                "Función '" + functionName + "' ya declarada", 0, 0);
            return;
        }

        List<FunctionSymbol.Parameter> parameters = new ArrayList<>();
        for (ParameterNode param : node.getParameters()) {
            parameters.add(new FunctionSymbol.Parameter(
                param.getName(),
                typeToString(param.getType())
            ));
        }

        FunctionSymbol functionSymbol = new FunctionSymbol(
            functionName,
            typeToString(node.getReturnType()),
            parameters,
            0, 0
        );

        functionTable.declare(functionSymbol);
    }

    private void collectProcessDeclaration(ProcessDeclarationNode node) {
        String processName = node.getName();

        if (processTable.isDeclared(processName)) {
            addError(SemanticError.ErrorType.DUPLICATE_DECLARATION,
                "Proceso '" + processName + "' ya declarado", 0, 0);
            return;
        }

        ProcessSymbol processSymbol = new ProcessSymbol(processName, 0, 0);
        processTable.declare(processSymbol);

        // Recolectar tareas del proceso
        for (ProcessElementNode element : node.getElements()) {
            collectProcessElement(element, processSymbol);
        }
    }

    private void collectProcessElement(ProcessElementNode element, ProcessSymbol process) {
        TaskTable taskTable = process.getTaskTable();

        if (element instanceof StartElementNode) {
            process.setHasStart(true);
            taskTable.declare(new TaskSymbol("inicio", process.getName(),
                TaskSymbol.TaskType.START, 0, 0));

        } else if (element instanceof EndElementNode) {
            EndElementNode end = (EndElementNode) element;
            process.setHasEnd(true);
            taskTable.declare(new TaskSymbol(end.getName(), process.getName(),
                TaskSymbol.TaskType.END, 0, 0));

        } else if (element instanceof TaskElementNode) {
            TaskElementNode task = (TaskElementNode) element;
            if (!taskTable.declare(new TaskSymbol(task.getName(), process.getName(),
                TaskSymbol.TaskType.TASK, 0, 0))) {
                addError(SemanticError.ErrorType.DUPLICATE_DECLARATION,
                    "Tarea '" + task.getName() + "' ya declarada en proceso '" + process.getName() + "'", 0, 0);
            }

        } else if (element instanceof ExclusiveGatewayNode) {
            ExclusiveGatewayNode gateway = (ExclusiveGatewayNode) element;
            if (!taskTable.declare(new TaskSymbol(gateway.getName(), process.getName(),
                TaskSymbol.TaskType.GATEWAY, 0, 0))) {
                addError(SemanticError.ErrorType.DUPLICATE_DECLARATION,
                    "Gateway '" + gateway.getName() + "' ya declarado en proceso '" + process.getName() + "'", 0, 0);
            }

        } else if (element instanceof ParallelGatewayNode) {
            ParallelGatewayNode parallel = (ParallelGatewayNode) element;
            if (!taskTable.declare(new TaskSymbol(parallel.getName(), process.getName(),
                TaskSymbol.TaskType.PARALLEL_GATEWAY, 0, 0))) {
                addError(SemanticError.ErrorType.DUPLICATE_DECLARATION,
                    "Gateway paralelo '" + parallel.getName() + "' ya declarado en proceso '" + process.getName() + "'", 0, 0);
            }
        }
    }

    /**
     * Validaciones finales después del recorrido.
     */
    private void performFinalValidations() {
        for (ProcessSymbol process : processTable.getProcesses()) {
            if (!process.hasStart()) {
                addError(SemanticError.ErrorType.PROCESS_NO_START,
                    "Proceso '" + process.getName() + "' no tiene elemento 'inicio'",
                    process.getLine(), process.getColumn());
            }
            if (!process.hasEnd()) {
                addError(SemanticError.ErrorType.PROCESS_NO_END,
                    "Proceso '" + process.getName() + "' no tiene elemento 'fin'",
                    process.getLine(), process.getColumn());
            }
        }
    }

    // Métodos auxiliares

    private void enterScope(String scopeName) {
        currentSymbolTable = new SymbolTable(scopeName, currentSymbolTable);
    }

    private void exitScope() {
        if (currentSymbolTable.getParent() != null) {
            currentSymbolTable = currentSymbolTable.getParent();
        }
    }

    private void addError(SemanticError.ErrorType type, String message, int line, int column) {
        String snippet = extractSourceLine(line);
        errors.add(new SemanticError(type, message, line, column, snippet));
    }

    private String extractSourceLine(int lineNumber) {
        if (sourceCode == null || sourceCode.isEmpty() || lineNumber < 1) {
            return null;
        }
        String[] lines = sourceCode.split("\n");
        if (lineNumber <= lines.length) {
            return lines[lineNumber - 1];
        }
        return null;
    }

    private String typeToString(TypeNode typeNode) {
        if (typeNode == null) return "unknown";
        switch (typeNode.getKind()) {
            case ENTERO: return "entero";
            case DECIMAL: return "decimal";
            case BOOLEANO: return "booleano";
            case TEXTO: return "texto";
            case LISTA: return "lista";
            case OBJETO: return "objeto";
            case VACIO: return "nulo";
            default: return "unknown";
        }
    }

    // Getters
    public List<SemanticError> getErrors() {
        return new ArrayList<>(errors);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public SymbolTable getGlobalSymbolTable() {
        return globalSymbolTable;
    }

    public FunctionTable getFunctionTable() {
        return functionTable;
    }

    public ProcessTable getProcessTable() {
        return processTable;
    }

    // ========== Implementación de ASTVisitor ==========

    @Override
    public void visit(ProgramNode node) {
        for (DeclarationNode declaration : node.getDeclarations()) {
            declaration.accept(this);
        }
    }

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
            paramSymbol.setInitialized(true);

            if (!currentSymbolTable.declare(paramSymbol)) {
                addError(SemanticError.ErrorType.DUPLICATE_DECLARATION,
                    "Parámetro '" + param.getName() + "' duplicado", 0, 0);
            }
        }

        // Analizar cuerpo
        node.getBody().accept(this);

        exitScope();
        currentFunction = null;
    }

    @Override
    public void visit(ProcessDeclarationNode node) {
        currentProcess = processTable.lookup(node.getName());

        for (ProcessElementNode element : node.getElements()) {
            element.accept(this);
        }

        currentProcess = null;
    }

    @Override
    public void visit(BlockNode node) {
        enterScope("block");

        for (StatementNode statement : node.getStatements()) {
            statement.accept(this);
        }

        exitScope();
    }

    @Override
    public void visit(VariableDeclarationStatementNode node) {
        String varName = node.getName();

        if (currentSymbolTable.isDeclaredLocally(varName)) {
            addError(SemanticError.ErrorType.DUPLICATE_DECLARATION,
                "Variable '" + varName + "' ya declarada en este scope", 0, 0);
            return;
        }

        // Analizar valor inicial si existe
        if (node.getValue() != null) {
            node.getValue().accept(this);
        }

        // Declarar variable
        Symbol symbol = new Symbol(
            varName,
            "unknown", // En versión completa se inferiría el tipo
            0, 0,
            currentSymbolTable.getScopeName()
        );
        symbol.setInitialized(node.getValue() != null);
        currentSymbolTable.declare(symbol);
    }

    @Override
    public void visit(AssignmentStatementNode node) {
        // Analizar el target (puede ser identificador, acceso a miembro, etc.)
        node.getTarget().accept(this);

        // Analizar el valor
        node.getValue().accept(this);

        // Si el target es un identificador simple, marcarlo como inicializado
        if (node.getTarget() instanceof IdentifierNode) {
            String varName = ((IdentifierNode) node.getTarget()).getName();
            Symbol symbol = currentSymbolTable.lookup(varName);
            if (symbol != null) {
                symbol.setInitialized(true);
                symbol.setUsed(true);
            }
        }
    }

    @Override
    public void visit(ReturnStatementNode node) {
        if (currentFunction == null) {
            addError(SemanticError.ErrorType.INVALID_RETURN,
                "return fuera de función", 0, 0);
            return;
        }

        if (node.getValue() != null) {
            node.getValue().accept(this);
        }
    }

    @Override
    public void visit(ExpressionStatementNode node) {
        node.getExpression().accept(this);
    }

    @Override
    public void visit(IfStatementNode node) {
        for (IfStatementNode.ConditionalBranch branch : node.getBranches()) {
            if (branch.getCondition() != null) {
                branch.getCondition().accept(this);
            }
            branch.getThenBlock().accept(this);
        }
    }

    @Override
    public void visit(ForStatementNode node) {
        enterScope("for_loop");

        // Declarar variable iteradora
        Symbol iteratorSymbol = new Symbol(
            node.getIteratorVariable(),
            "unknown", // Tipo inferido del iterable
            0, 0,
            currentSymbolTable.getScopeName()
        );
        iteratorSymbol.setInitialized(true);
        currentSymbolTable.declare(iteratorSymbol);

        // Analizar iterable
        node.getIterable().accept(this);

        // Analizar cuerpo
        node.getBody().accept(this);

        exitScope();
    }

    @Override
    public void visit(GotoStatementNode node) {
        if (currentProcess == null) {
            addError(SemanticError.ErrorType.GOTO_OUTSIDE_PROCESS,
                "ir_a solo puede usarse dentro de un proceso", 0, 0);
            return;
        }

        String targetName = node.getTargetName();
        TaskSymbol targetTask = currentProcess.getTaskTable().lookup(targetName);

        if (targetTask == null) {
            addError(SemanticError.ErrorType.INVALID_GOTO_TARGET,
                "Tarea '" + targetName + "' no existe en proceso '" + currentProcess.getName() + "'", 0, 0);
        } else {
            currentProcess.getTaskTable().markAsReferenced(targetName);
        }
    }

    @Override
    public void visit(BinaryExpressionNode node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
    }

    @Override
    public void visit(UnaryExpressionNode node) {
        node.getOperand().accept(this);
    }

    @Override
    public void visit(FunctionCallNode node) {
        node.getFunction().accept(this);

        // Validar operaciones especiales: db.* y http.*
        if (node.getFunction() instanceof MemberAccessNode) {
            MemberAccessNode memberAccess = (MemberAccessNode) node.getFunction();
            if (memberAccess.getObject() instanceof IdentifierNode) {
                String objectName = ((IdentifierNode) memberAccess.getObject()).getName();
                String methodName = memberAccess.getMemberName();

                if ("db".equals(objectName)) {
                    validateDbOperation(methodName, node.getArguments().size());
                } else if ("http".equals(objectName)) {
                    validateHttpOperation(methodName, node.getArguments().size());
                }
            }
        }
        // Si es llamada directa a función, validar
        else if (node.getFunction() instanceof IdentifierNode) {
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

        // Analizar argumentos
        for (ExpressionNode arg : node.getArguments()) {
            arg.accept(this);
        }
    }

    /**
     * Valida operaciones de base de datos (db.ejecutar, db.consultar).
     */
    private void validateDbOperation(String methodName, int argCount) {
        switch (methodName) {
            case "ejecutar":
            case "consultar":
                if (argCount != 2) {
                    addError(SemanticError.ErrorType.INVALID_ARGUMENT_COUNT,
                        "db." + methodName + " espera 2 argumentos (sql: texto, params: lista), " +
                        "pero se pasaron " + argCount, 0, 0);
                }
                break;
            default:
                addError(SemanticError.ErrorType.INVALID_DB_OPERATION,
                    "Operación de base de datos 'db." + methodName + "' no válida. " +
                    "Operaciones válidas: db.ejecutar, db.consultar", 0, 0);
        }
    }

    /**
     * Valida operaciones HTTP (http.get, http.post, http.put, http.delete).
     */
    private void validateHttpOperation(String methodName, int argCount) {
        switch (methodName) {
            case "get":
            case "delete":
                if (argCount != 2) {
                    addError(SemanticError.ErrorType.INVALID_ARGUMENT_COUNT,
                        "http." + methodName + " espera 2 argumentos (url: texto, headers: objeto), " +
                        "pero se pasaron " + argCount, 0, 0);
                }
                break;
            case "post":
            case "put":
                if (argCount != 3) {
                    addError(SemanticError.ErrorType.INVALID_ARGUMENT_COUNT,
                        "http." + methodName + " espera 3 argumentos (url: texto, body: objeto, headers: objeto), " +
                        "pero se pasaron " + argCount, 0, 0);
                }
                break;
            default:
                addError(SemanticError.ErrorType.INVALID_HTTP_OPERATION,
                    "Operación HTTP 'http." + methodName + "' no válida. " +
                    "Operaciones válidas: http.get, http.post, http.put, http.delete", 0, 0);
        }
    }

    @Override
    public void visit(MemberAccessNode node) {
        node.getObject().accept(this);
    }

    @Override
    public void visit(IndexAccessNode node) {
        node.getArray().accept(this);
        node.getIndex().accept(this);
    }

    @Override
    public void visit(IdentifierNode node) {
        String name = node.getName();
        Symbol symbol = currentSymbolTable.lookup(name);

        if (symbol == null) {
            addError(SemanticError.ErrorType.UNDECLARED_VARIABLE,
                "Variable '" + name + "' no declarada", 0, 0);
        } else {
            symbol.setUsed(true);
        }
    }

    // Literales - no requieren validación
    @Override
    public void visit(IntegerLiteralNode node) {}

    @Override
    public void visit(DecimalLiteralNode node) {}

    @Override
    public void visit(BooleanLiteralNode node) {}

    @Override
    public void visit(StringLiteralNode node) {}

    @Override
    public void visit(NullLiteralNode node) {}

    @Override
    public void visit(ListLiteralNode node) {
        for (ExpressionNode element : node.getElements()) {
            element.accept(this);
        }
    }

    @Override
    public void visit(ObjectLiteralNode node) {
        for (ObjectLiteralNode.ObjectMember member : node.getMembers()) {
            member.getValue().accept(this);
        }
    }

    // Elementos de proceso

    @Override
    public void visit(StartElementNode node) {
        if (currentProcess != null) {
            String targetName = node.getTargetTaskName();
            TaskSymbol task = currentProcess.getTaskTable().lookup(targetName);

            if (task == null) {
                addError(SemanticError.ErrorType.UNDECLARED_TASK,
                    "Tarea '" + targetName + "' no declarada en proceso '" + currentProcess.getName() + "'", 0, 0);
            } else {
                currentProcess.getTaskTable().markAsReferenced(targetName);
            }
        }
    }

    @Override
    public void visit(TaskElementNode node) {
        for (StatementNode action : node.getActions()) {
            action.accept(this);
        }
    }

    @Override
    public void visit(ExclusiveGatewayNode node) {
        for (ExclusiveGatewayNode.ConditionalBranch branch : node.getBranches()) {
            if (branch.getCondition() != null) {
                branch.getCondition().accept(this);
            }

            if (currentProcess != null) {
                String targetName = branch.getTargetTaskName();
                TaskSymbol task = currentProcess.getTaskTable().lookup(targetName);

                if (task == null) {
                    addError(SemanticError.ErrorType.UNDECLARED_TASK,
                        "Tarea '" + targetName + "' no declarada en proceso '" + currentProcess.getName() + "'", 0, 0);
                } else {
                    currentProcess.getTaskTable().markAsReferenced(targetName);
                }
            }
        }
    }

    @Override
    public void visit(ParallelGatewayNode node) {
        if (currentProcess != null) {
            // Validar ramas
            for (ParallelGatewayNode.ParallelBranch branch : node.getBranches()) {
                String branchTarget = branch.getTargetTaskName();
                TaskSymbol task = currentProcess.getTaskTable().lookup(branchTarget);
                if (task == null) {
                    addError(SemanticError.ErrorType.UNDECLARED_TASK,
                        "Tarea '" + branchTarget + "' no declarada en proceso '" + currentProcess.getName() + "'", 0, 0);
                } else {
                    currentProcess.getTaskTable().markAsReferenced(branchTarget);
                }
            }

            // Validar merge
            String mergeTarget = node.getMergeTargetName();
            TaskSymbol mergeTask = currentProcess.getTaskTable().lookup(mergeTarget);
            if (mergeTask == null) {
                addError(SemanticError.ErrorType.UNDECLARED_TASK,
                    "Tarea '" + mergeTarget + "' no declarada en proceso '" + currentProcess.getName() + "'", 0, 0);
            } else {
                currentProcess.getTaskTable().markAsReferenced(mergeTarget);
            }
        }
    }

    @Override
    public void visit(EndElementNode node) {
        // Nodo terminal - no requiere validación
    }

    @Override
    public void visit(TypeNode node) {
        // Los tipos no requieren validación en este contexto
    }

    @Override
    public void visit(ParameterNode node) {
        // Los parámetros se procesan en FunctionDeclarationNode
    }
}
