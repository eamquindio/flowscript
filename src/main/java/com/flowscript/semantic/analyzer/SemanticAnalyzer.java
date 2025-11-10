package com.flowscript.semantic.analyzer;

import com.flowscript.semantic.checker.Operator;
import com.flowscript.semantic.checker.TypeChecker;
import com.flowscript.semantic.checker.TypeInferenceVisitor;
import com.flowscript.semantic.errors.*;
import com.flowscript.semantic.functions.*;
import com.flowscript.semantic.processes.*;
import com.flowscript.semantic.symbols.*;
import com.flowscript.semantic.types.Type;
import com.flowscript.semantic.visitor.ASTVisitor;
import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.*;
import com.flowscript.sintactic.ast.functions.control_flujo.*;
import com.flowscript.sintactic.ast.functions.expresiones.*;
import com.flowscript.sintactic.ast.functions.listas_argumentos.*;
import com.flowscript.sintactic.ast.functions.literales.*;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.*;
import com.flowscript.sintactic.ast.functions.statements_basicos.*;
import com.flowscript.sintactic.ast.functions.tipos_parametros.*;
import com.flowscript.sintactic.ast.process.clausulas_control.*;
import com.flowscript.sintactic.ast.process.estructura_principal.*;
import com.flowscript.sintactic.ast.process.navegacion.*;

import java.util.*;

/**
 * Main semantic analyzer for FlowScript.
 * Implements a 4-phase analysis:
 * 1. Collect declarations (functions, processes, imports, globals)
 * 2. Analyze function bodies
 * 3. Analyze process structures (BPMN validation)
 * 4. Dead code detection
 */
public class SemanticAnalyzer implements ASTVisitor<Void> {
    private final SymbolTable symbolTable;
    private final FunctionTable functionTable;
    private final ProcessTable processTable;
    private final TypeChecker typeChecker;
    private final TypeInferenceVisitor typeInference;

    private final List<SemanticError> errors;
    private final List<SemanticWarning> warnings;

    private AnalysisContext currentContext;
    private FunctionSymbol currentFunction;
    private ProcessSymbol currentProcess;
    private boolean hasReturnInPath;
    private int loopDepth;  // Track nesting depth for break/continue validation

    public SemanticAnalyzer() {
        this.symbolTable = new SymbolTable();
        this.functionTable = new FunctionTable();
        this.processTable = new ProcessTable();
        this.typeChecker = new TypeChecker(symbolTable, functionTable);
        this.typeInference = new TypeInferenceVisitor(symbolTable, functionTable, typeChecker);

        this.errors = new ArrayList<>();
        this.warnings = new ArrayList<>();

        this.currentContext = AnalysisContext.GLOBAL;
        this.currentFunction = null;
        this.currentProcess = null;
        this.hasReturnInPath = false;
        this.loopDepth = 0;
    }

    /**
     * Main entry point for semantic analysis.
     * @param program The root AST node
     * @return Analysis result with errors and warnings
     */
    public SemanticAnalysisResult analyze(ProgramNode program) {
        // Phase 1: Collect all declarations
        collectDeclarations(program);

        // Phase 2: Analyze function bodies
        analyzeFunctions(program);

        // Phase 3: Analyze process structures
        analyzeProcesses(program);

        // Phase 4: Dead code detection (done during analysis)

        // Check for unused symbols
        checkUnusedSymbols();

        return new SemanticAnalysisResult(errors, warnings);
    }

    // ========== PHASE 1: COLLECT DECLARATIONS ==========

    private void collectDeclarations(ProgramNode program) {
        currentContext = AnalysisContext.GLOBAL;

        for (DeclarationNode decl : program.getDeclarations()) {
            if (decl instanceof FunctionDeclarationNode) {
                collectFunctionDeclaration((FunctionDeclarationNode) decl);
            } else if (decl instanceof ProcessDeclarationNode) {
                collectProcessDeclaration((ProcessDeclarationNode) decl);
            } else if (decl instanceof ImportDeclarationNode) {
                collectImportDeclaration((ImportDeclarationNode) decl);
            } else if (decl instanceof VariableDeclarationNode) {
                collectGlobalVariable((VariableDeclarationNode) decl);
            }
        }
    }

    private void collectFunctionDeclaration(FunctionDeclarationNode node) {
        String name = node.getName();

        // Check if already declared
        if (functionTable.isFunctionDeclared(name)) {
            errors.add(new SemanticError(
                ErrorCodes.FUNCTION_ALREADY_DECLARED,
                "Function '" + name + "' is already declared",
                node
            ));
            return;
        }

        // Collect parameters
        List<Parameter> params = new ArrayList<>();
        for (ParameterNode paramNode : node.getParameters()) {
            Type paramType = parseType(paramNode.getType());
            params.add(new Parameter(paramNode.getName(), paramType));
        }

        // Get return type
        Type returnType = node.getReturnType() != null ?
            parseType(node.getReturnType()) : Type.VOID;

        // Add to function table
        functionTable.declareFunction(name, params, returnType, node);
    }

    private void collectProcessDeclaration(ProcessDeclarationNode node) {
        String name = node.getName();

        // Check if already declared
        if (processTable.isProcessDeclared(name)) {
            errors.add(new SemanticError(
                ErrorCodes.FUNCTION_ALREADY_DECLARED, // Reusing code for process
                "Process '" + name + "' is already declared",
                node
            ));
            return;
        }

        // Add to process table
        processTable.declareProcess(name, node);
    }

    private void collectImportDeclaration(ImportDeclarationNode node) {
        // For now, we create a placeholder module
        String moduleName = node.getAlias() != null ? node.getAlias() : extractModuleName(node.getModulePath());
        ModuleSymbol module = new ModuleSymbol(moduleName, node.getModulePath());

        // Add standard library functions based on module name
        addStandardLibraryFunctions(module, node.getModulePath());

        functionTable.addModule(module);
    }

    private void collectGlobalVariable(VariableDeclarationNode node) {
        String name = node.getVariableName();

        // Check if already declared in current scope
        if (symbolTable.isVariableDeclaredInCurrentScope(name)) {
            errors.add(new SemanticError(
                ErrorCodes.VARIABLE_ALREADY_DECLARED,
                "Variable '" + name + "' is already declared",
                node
            ));
            return;
        }

        // Infer type from initializer
        Type type = Type.ANY;
        if (node.getInitializer() != null) {
            try {
                type = node.getInitializer().accept(typeInference);
            } catch (Exception e) {
                // Type inference failed, use ANY
            }
        }

        // VariableDeclarationNode doesn't have isConstant method, assume not constant
        symbolTable.declareVariable(name, type, node, false, false);
    }

    // ========== PHASE 2: ANALYZE FUNCTIONS ==========

    private void analyzeFunctions(ProgramNode program) {
        for (DeclarationNode decl : program.getDeclarations()) {
            if (decl instanceof FunctionDeclarationNode) {
                analyzeFunctionBody((FunctionDeclarationNode) decl);
            }
        }
    }

    private void analyzeFunctionBody(FunctionDeclarationNode node) {
        currentContext = AnalysisContext.FUNCTION;
        currentFunction = functionTable.lookupFunction(node.getName());
        hasReturnInPath = false;

        // Enter function scope
        symbolTable.enterScope(ScopeType.FUNCTION);

        // Add parameters to symbol table
        for (ParameterNode paramNode : node.getParameters()) {
            String paramName = paramNode.getName();
            Type paramType = parseType(paramNode.getType());
            symbolTable.declareVariable(paramName, paramType, paramNode, false, true);
        }

        // Analyze function body
        if (node.getBody() != null) {
            node.getBody().accept(this);
        }

        // Check if all paths return (for non-void functions)
        if (currentFunction != null &&
            !currentFunction.getReturnType().equals(Type.VOID) &&
            !hasReturnInPath) {
            errors.add(new SemanticError(
                ErrorCodes.MISSING_RETURN,
                "Function '" + node.getName() + "' must return a value of type " +
                    currentFunction.getReturnType(),
                node
            ));
        }

        // Check for unused parameters
        checkUnusedParameters();

        // Exit function scope
        symbolTable.exitScope();

        currentContext = AnalysisContext.GLOBAL;
        currentFunction = null;
    }

    // ========== PHASE 3: ANALYZE PROCESSES ==========

    private void analyzeProcesses(ProgramNode program) {
        for (DeclarationNode decl : program.getDeclarations()) {
            if (decl instanceof ProcessDeclarationNode) {
                analyzeProcessStructure((ProcessDeclarationNode) decl);
            }
        }
    }

    private void analyzeProcessStructure(ProcessDeclarationNode node) {
        currentContext = AnalysisContext.PROCESS;
        currentProcess = processTable.lookupProcess(node.getName());

        // Enter process scope (process context)
        symbolTable.enterScope(ScopeType.PROCESS);

        // Add special 'entrada' object
        symbolTable.declareVariable("entrada", Type.OBJECT, node, true, false);

        // Validate BPMN structure
        validateBPMNStructure(node);

        // Analyze each task
        for (ASTNode element : node.getProcessElements()) {
            if (element instanceof TaskElementNode) {
                analyzeTask((TaskElementNode) element);
            } else if (element instanceof ExclusiveGatewayNode) {
                analyzeExclusiveGateway((ExclusiveGatewayNode) element);
            } else if (element instanceof ParallelGatewayNode) {
                analyzeParallelGateway((ParallelGatewayNode) element);
            }
        }

        // Exit process scope
        symbolTable.exitScope();

        currentContext = AnalysisContext.GLOBAL;
        currentProcess = null;
    }

    private void validateBPMNStructure(ProcessDeclarationNode node) {
        List<StartElementNode> starts = new ArrayList<>();
        List<EndElementNode> ends = new ArrayList<>();
        Set<String> nodeNames = new HashSet<>();

        // Collect all elements
        for (ASTNode element : node.getProcessElements()) {
            if (element instanceof StartElementNode) {
                starts.add((StartElementNode) element);
            } else if (element instanceof EndElementNode) {
                EndElementNode end = (EndElementNode) element;
                ends.add(end);
                nodeNames.add(end.getEndName());
            } else if (element instanceof TaskElementNode) {
                TaskElementNode task = (TaskElementNode) element;
                nodeNames.add(task.getTaskName());
            } else if (element instanceof ExclusiveGatewayNode) {
                ExclusiveGatewayNode gw = (ExclusiveGatewayNode) element;
                nodeNames.add(gw.getGatewayName());
            } else if (element instanceof ParallelGatewayNode) {
                ParallelGatewayNode gw = (ParallelGatewayNode) element;
                nodeNames.add(gw.getGatewayName());
            }
        }

        // Check exactly one 'inicio'
        if (starts.isEmpty()) {
            errors.add(new SemanticError(
                ErrorCodes.PROCESS_NO_START,
                "Process '" + node.getName() + "' must have exactly one 'inicio' element",
                node
            ));
            return;
        }

        if (starts.size() > 1) {
            errors.add(new SemanticError(
                ErrorCodes.MULTIPLE_START_ELEMENTS,
                "Process '" + node.getName() + "' has multiple 'inicio' elements",
                starts.get(1)
            ));
        }

        // Check at least one 'fin'
        if (ends.isEmpty()) {
            errors.add(new SemanticError(
                ErrorCodes.PROCESS_NO_END,
                "Process '" + node.getName() + "' must have at least one 'fin' element",
                node
            ));
        }

        // Validate start points to existing node
        StartElementNode start = starts.get(0);
        String startTarget = start.getTargetNodeName();
        if (!nodeNames.contains(startTarget)) {
            errors.add(new SemanticError(
                ErrorCodes.PROCESS_NODE_NOT_FOUND,
                "Start element points to non-existent node '" + startTarget + "'",
                start
            ));
        }

        // Validate all go_to references
        validateGoToReferences(node, nodeNames);

        // Check reachability
        if (!starts.isEmpty() && !nodeNames.isEmpty()) {
            checkReachability(node, start, nodeNames);
        }
    }

    private void validateGoToReferences(ProcessDeclarationNode node, Set<String> nodeNames) {
        for (ASTNode element : node.getProcessElements()) {
            if (element instanceof TaskElementNode) {
                TaskElementNode task = (TaskElementNode) element;
                for (StatementNode stmt : task.getActionStatements()) {
                    collectGoToStatements(stmt, nodeNames);
                }
            }
        }
    }

    private void collectGoToStatements(StatementNode stmt, Set<String> nodeNames) {
        if (stmt instanceof GotoStatementNode) {
            GotoStatementNode gotoStmt = (GotoStatementNode) stmt;
            String target = gotoStmt.getTargetLabel();
            if (!nodeNames.contains(target)) {
                errors.add(new SemanticError(
                    ErrorCodes.PROCESS_NODE_NOT_FOUND,
                    "go_to references non-existent node '" + target + "'",
                    gotoStmt
                ));
            }
        } else if (stmt instanceof IfStatementNode) {
            IfStatementNode ifStmt = (IfStatementNode) stmt;
            StatementNode thenStmt = ifStmt.getThenStatement();
            if (thenStmt != null) {
                if (thenStmt instanceof BlockNode) {
                    for (StatementNode s : ((BlockNode) thenStmt).getStatements()) {
                        collectGoToStatements(s, nodeNames);
                    }
                } else {
                    collectGoToStatements(thenStmt, nodeNames);
                }
            }
            for (ElseIfClauseNode elseIf : ifStmt.getElseIfClauses()) {
                StatementNode elseIfStmt = elseIf.getStatement();
                if (elseIfStmt != null) {
                    if (elseIfStmt instanceof BlockNode) {
                        for (StatementNode s : ((BlockNode) elseIfStmt).getStatements()) {
                            collectGoToStatements(s, nodeNames);
                        }
                    } else {
                        collectGoToStatements(elseIfStmt, nodeNames);
                    }
                }
            }
            StatementNode elseStmt = ifStmt.getElseStatement();
            if (elseStmt != null) {
                if (elseStmt instanceof BlockNode) {
                    for (StatementNode s : ((BlockNode) elseStmt).getStatements()) {
                        collectGoToStatements(s, nodeNames);
                    }
                } else {
                    collectGoToStatements(elseStmt, nodeNames);
                }
            }
        }
    }

    private void checkReachability(ProcessDeclarationNode node, StartElementNode start, Set<String> nodeNames) {
        Set<String> reachable = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        // Start BFS from inicio
        String startTarget = start.getTargetNodeName();
        queue.add(startTarget);
        reachable.add(startTarget);

        // BFS to find reachable nodes
        while (!queue.isEmpty()) {
            String current = queue.poll();
            Set<String> nexts = findNextNodes(node, current);

            for (String next : nexts) {
                if (!reachable.contains(next)) {
                    reachable.add(next);
                    queue.add(next);
                }
            }
        }

        // Check for unreachable nodes
        for (String nodeName : nodeNames) {
            if (!reachable.contains(nodeName)) {
                warnings.add(new SemanticWarning(
                    ErrorCodes.UNREACHABLE_NODE,
                    "Node '" + nodeName + "' in process '" + node.getName() +
                        "' is unreachable from inicio",
                    node
                ));
            }
        }
    }

    private Set<String> findNextNodes(ProcessDeclarationNode node, String currentNode) {
        Set<String> nexts = new HashSet<>();

        for (ASTNode element : node.getProcessElements()) {
            if (element instanceof TaskElementNode) {
                TaskElementNode task = (TaskElementNode) element;
                if (task.getTaskName().equals(currentNode)) {
                    // Find go_to statements in this task
                    collectNextNodesFromStatements(task.getActionStatements(), nexts);
                }
            }
        }

        return nexts;
    }

    private void collectNextNodesFromStatements(List<StatementNode> statements, Set<String> nexts) {
        for (StatementNode stmt : statements) {
            if (stmt instanceof GotoStatementNode) {
                nexts.add(((GotoStatementNode) stmt).getTargetLabel());
            } else if (stmt instanceof IfStatementNode) {
                IfStatementNode ifStmt = (IfStatementNode) stmt;
                StatementNode thenStmt = ifStmt.getThenStatement();
                if (thenStmt instanceof BlockNode) {
                    collectNextNodesFromStatements(((BlockNode) thenStmt).getStatements(), nexts);
                }
                for (ElseIfClauseNode elseIf : ifStmt.getElseIfClauses()) {
                    StatementNode elseIfStmt = elseIf.getStatement();
                    if (elseIfStmt instanceof BlockNode) {
                        collectNextNodesFromStatements(((BlockNode) elseIfStmt).getStatements(), nexts);
                    }
                }
                StatementNode elseStmt = ifStmt.getElseStatement();
                if (elseStmt instanceof BlockNode) {
                    collectNextNodesFromStatements(((BlockNode) elseStmt).getStatements(), nexts);
                }
            }
        }
    }

    private void analyzeTask(TaskElementNode node) {
        // Analyze each statement in the task
        for (StatementNode stmt : node.getActionStatements()) {
            stmt.accept(this);
        }
    }

    private void analyzeExclusiveGateway(ExclusiveGatewayNode node) {
        // Exclusive gateways are typically represented as if/else statements
        // They should already be validated through normal statement analysis
    }

    private void analyzeParallelGateway(ParallelGatewayNode node) {
        // Validate parallel gateway structure
        if (node.getBranches().size() < 2) {
            errors.add(new SemanticError(
                ErrorCodes.INSUFFICIENT_BRANCHES,
                "Parallel gateway must have at least 2 branches",
                node
            ));
        }

        if (node.getJoinClause() == null) {
            errors.add(new SemanticError(
                ErrorCodes.MISSING_JOIN,
                "Parallel gateway must have a 'unir' clause",
                node
            ));
        }
    }

    // ========== HELPER METHODS ==========

    private Type parseType(TypeNode typeNode) {
        if (typeNode == null) {
            return Type.ANY;
        }

        String typeName = typeNode.getTypeName();
        switch (typeName) {
            case "entero": return Type.INTEGER;
            case "decimal": return Type.DECIMAL;
            case "booleano": return Type.BOOLEAN;
            case "texto": return Type.TEXT;
            case "lista": return Type.LIST;
            case "objeto": return Type.OBJECT;
            case "nulo": return Type.NULL;
            case "vacio": return Type.VOID;
            default: return Type.ANY;
        }
    }

    private String extractModuleName(String path) {
        // Extract module name from path (e.g., "std/http" -> "http")
        int lastSlash = path.lastIndexOf('/');
        return lastSlash >= 0 ? path.substring(lastSlash + 1) : path;
    }

    private void addStandardLibraryFunctions(ModuleSymbol module, String path) {
        // Add standard library functions based on module path
        if (path.contains("http")) {
            module.addFunction(new FunctionSymbol("get",
                Arrays.asList(new Parameter("url", Type.TEXT)), Type.OBJECT, null));
            module.addFunction(new FunctionSymbol("post",
                Arrays.asList(new Parameter("url", Type.TEXT), new Parameter("body", Type.OBJECT)),
                Type.OBJECT, null));
        } else if (path.contains("json")) {
            module.addFunction(new FunctionSymbol("parse",
                Arrays.asList(new Parameter("text", Type.TEXT)), Type.OBJECT, null));
            module.addFunction(new FunctionSymbol("stringify",
                Arrays.asList(new Parameter("obj", Type.OBJECT)), Type.TEXT, null));
        } else if (path.contains("io")) {
            module.addFunction(new FunctionSymbol("imprimir",
                Arrays.asList(new Parameter("value", Type.ANY)), Type.VOID, null));
            module.addFunction(new FunctionSymbol("leer_linea",
                Arrays.asList(new Parameter("prompt", Type.TEXT)), Type.TEXT, null));
        }
    }

    private void checkUnusedSymbols() {
        List<Symbol> unused = symbolTable.getUnusedSymbols();
        for (Symbol symbol : unused) {
            if (symbol instanceof VariableSymbol) {
                VariableSymbol varSym = (VariableSymbol) symbol;
                if (!varSym.isParameter()) {
                    warnings.add(new SemanticWarning(
                        ErrorCodes.VARIABLE_NEVER_USED,
                        "Variable '" + symbol.getName() + "' is declared but never used",
                        symbol.getDeclarationNode()
                    ));
                }
            }
        }
    }

    private void checkUnusedParameters() {
        Scope currentScope = symbolTable.getCurrentScope();
        for (Symbol symbol : currentScope.getAllSymbols()) {
            if (symbol instanceof VariableSymbol) {
                VariableSymbol varSym = (VariableSymbol) symbol;
                if (varSym.isParameter() && !varSym.isUsed()) {
                    warnings.add(new SemanticWarning(
                        ErrorCodes.UNUSED_PARAMETER,
                        "Parameter '" + symbol.getName() + "' is never used",
                        symbol.getDeclarationNode()
                    ));
                }
            }
        }
    }

    // ========== VISITOR IMPLEMENTATIONS ==========

    @Override
    public Void visit(ProgramNode node) {
        // Handled by analyze() method
        return null;
    }

    @Override
    public Void visit(FunctionDeclarationNode node) {
        // Handled by analyzeFunctionBody()
        return null;
    }

    @Override
    public Void visit(ProcessDeclarationNode node) {
        // Handled by analyzeProcessStructure()
        return null;
    }

    @Override
    public Void visit(ImportDeclarationNode node) {
        // Handled by collectImportDeclaration()
        return null;
    }

    @Override
    public Void visit(VariableDeclarationNode node) {
        // Handled by collectGlobalVariable()
        return null;
    }

    @Override
    public Void visit(BlockNode node) {
        symbolTable.enterScope(ScopeType.BLOCK);

        for (StatementNode stmt : node.getStatements()) {
            stmt.accept(this);
        }

        symbolTable.exitScope();
        return null;
    }

    @Override
    public Void visit(IfStatementNode node) {
        // Check condition is boolean
        Type condType = node.getCondition().accept(typeInference);
        if (!condType.equals(Type.BOOLEAN) && !condType.equals(Type.ANY)) {
            errors.add(new SemanticError(
                ErrorCodes.NON_BOOLEAN_CONDITION,
                "Condition must be boolean type, got " + condType,
                node
            ));
        }

        // Analyze then statement
        if (node.getThenStatement() != null) {
            node.getThenStatement().accept(this);
        }

        // Analyze else-if clauses
        for (ElseIfClauseNode elseIf : node.getElseIfClauses()) {
            elseIf.accept(this);
        }

        // Analyze else statement
        if (node.getElseStatement() != null) {
            node.getElseStatement().accept(this);
        }

        return null;
    }

    @Override
    public Void visit(WhileStatementNode node) {
        // Check condition is boolean
        Type condType = node.getCondition().accept(typeInference);
        if (!condType.equals(Type.BOOLEAN) && !condType.equals(Type.ANY)) {
            errors.add(new SemanticError(
                ErrorCodes.NON_BOOLEAN_CONDITION,
                "While condition must be boolean type, got " + condType,
                node
            ));
        }

        // Enter loop context
        symbolTable.enterScope(ScopeType.BLOCK);
        loopDepth++;

        // Analyze body
        if (node.getBody() != null) {
            node.getBody().accept(this);
        }

        // Exit loop context
        loopDepth--;
        symbolTable.exitScope();
        return null;
    }

    @Override
    public Void visit(ForStatementNode node) {
        symbolTable.enterScope(ScopeType.BLOCK);
        loopDepth++;

        // Declare iterator variable
        String iteratorVar = node.getIteratorVariable();
        symbolTable.declareVariable(iteratorVar, Type.ANY, node, false, false);

        // Check iterable expression
        Type iterableType = node.getIterable().accept(typeInference);
        if (!iterableType.equals(Type.LIST) && !iterableType.equals(Type.TEXT) && !iterableType.equals(Type.ANY)) {
            errors.add(new SemanticError(
                ErrorCodes.TYPE_MISMATCH,
                "for-each requires list or text type, got " + iterableType,
                node
            ));
        }

        // Analyze body
        if (node.getBody() != null) {
            node.getBody().accept(this);
        }

        loopDepth--;
        symbolTable.exitScope();
        return null;
    }

    @Override
    public Void visit(ForRangeStatementNode node) {
        symbolTable.enterScope(ScopeType.BLOCK);
        loopDepth++;

        // Declare loop variable
        String loopVar = node.getLoopVariable();
        symbolTable.declareVariable(loopVar, Type.INTEGER, node, false, false);

        // Check from/to/step expressions are numeric
        Type fromType = node.getFromExpression().accept(typeInference);
        Type toType = node.getToExpression().accept(typeInference);

        if (!typeChecker.isNumeric(fromType)) {
            errors.add(new SemanticError(
                ErrorCodes.TYPE_MISMATCH,
                "for-range 'from' expression must be numeric, got " + fromType,
                node
            ));
        }

        if (!typeChecker.isNumeric(toType)) {
            errors.add(new SemanticError(
                ErrorCodes.TYPE_MISMATCH,
                "for-range 'to' expression must be numeric, got " + toType,
                node
            ));
        }

        if (node.hasStepExpression()) {
            Type stepType = node.getStepExpression().accept(typeInference);
            if (!typeChecker.isNumeric(stepType)) {
                errors.add(new SemanticError(
                    ErrorCodes.TYPE_MISMATCH,
                    "for-range 'step' expression must be numeric, got " + stepType,
                    node
                ));
            }
        }

        // Analyze body
        if (node.getBody() != null) {
            node.getBody().accept(this);
        }

        loopDepth--;
        symbolTable.exitScope();
        return null;
    }

    @Override
    public Void visit(BreakStatementNode node) {
        // Check we're inside a loop
        if (loopDepth == 0) {
            errors.add(new SemanticError(
                ErrorCodes.BREAK_OUTSIDE_LOOP,
                "break statement can only be used inside while or for loops",
                node
            ));
        }

        // Check we're not in a process (use goto instead)
        if (currentContext == AnalysisContext.PROCESS) {
            errors.add(new SemanticError(
                ErrorCodes.BREAK_IN_PROCESS,
                "break cannot be used in process tasks, use go_to instead",
                node
            ));
        }

        return null;
    }

    @Override
    public Void visit(ContinueStatementNode node) {
        // Check we're inside a loop
        if (loopDepth == 0) {
            errors.add(new SemanticError(
                ErrorCodes.CONTINUE_OUTSIDE_LOOP,
                "continue statement can only be used inside while or for loops",
                node
            ));
        }

        // Check we're not in a process (use goto instead)
        if (currentContext == AnalysisContext.PROCESS) {
            errors.add(new SemanticError(
                ErrorCodes.CONTINUE_IN_PROCESS,
                "continue cannot be used in process tasks, use go_to instead",
                node
            ));
        }

        return null;
    }

    @Override
    public Void visit(ReturnStatementNode node) {
        // Check we're in a function
        if (currentContext != AnalysisContext.FUNCTION) {
            errors.add(new SemanticError(
                ErrorCodes.MISSING_RETURN, // Reuse for wrong context
                "return statement can only be used inside functions",
                node
            ));
            return null;
        }

        hasReturnInPath = true;

        // Check return type matches function signature
        if (currentFunction != null) {
            Type expectedType = currentFunction.getReturnType();
            Type actualType = Type.VOID;

            if (node.getExpression() != null) {
                actualType = node.getExpression().accept(typeInference);
            }

            if (!typeChecker.isCompatible(expectedType, actualType)) {
                errors.add(new SemanticError(
                    ErrorCodes.INVALID_RETURN_TYPE,
                    "Function returns " + actualType + " but declared return type is " + expectedType,
                    node
                ));
            }
        }

        return null;
    }

    @Override
    public Void visit(ThrowStatementNode node) {
        // Validate throw expression is an object
        if (node.getExpression() != null) {
            Type exprType = node.getExpression().accept(typeInference);
            if (!exprType.equals(Type.OBJECT) && !exprType.equals(Type.ANY)) {
                errors.add(new SemanticError(
                    ErrorCodes.TYPE_MISMATCH,
                    "throw expression must be an object, got " + exprType,
                    node
                ));
            }
        }

        return null;
    }

    @Override
    public Void visit(TryStatementNode node) {
        // Analyze try block
        if (node.getTryBlock() != null) {
            node.getTryBlock().accept(this);
        }

        // Analyze catch block with exception variable
        if (node.getCatchBlock() != null) {
            symbolTable.enterScope(ScopeType.BLOCK);

            // Add exception variable to scope
            if (node.getCatchVariable() != null) {
                symbolTable.declareVariable(
                    node.getCatchVariable(),
                    Type.OBJECT,
                    node,
                    false,
                    false
                );
            }

            node.getCatchBlock().accept(this);
            symbolTable.exitScope();
        }

        return null;
    }

    @Override
    public Void visit(GotoStatementNode node) {
        // Check we're in a process
        if (currentContext != AnalysisContext.PROCESS) {
            errors.add(new SemanticError(
                ErrorCodes.GOTO_IN_FUNCTION,
                "go_to can only be used inside processes",
                node
            ));
        }

        return null;
    }

    @Override
    public Void visit(IdentifierNode node) {
        String name = node.getName();

        // Check for 'entrada' in function
        if (name.equals("entrada") && currentContext == AnalysisContext.FUNCTION) {
            errors.add(new SemanticError(
                ErrorCodes.ENTRADA_IN_FUNCTION,
                "'entrada' can only be used inside processes",
                node
            ));
            return null;
        }

        // Check if variable is declared
        Symbol symbol = symbolTable.lookupVariable(name);
        if (symbol == null && !name.equals("entrada")) {
            errors.add(new SemanticError(
                ErrorCodes.VARIABLE_NOT_DECLARED,
                "Variable '" + name + "' is not declared",
                node
            ));
        }

        return null;
    }

    @Override
    public Void visit(VariableDeclarationStatementNode node) {
        String name = node.getVariableName();

        // Check if already declared in current scope
        if (symbolTable.isVariableDeclaredInCurrentScope(name)) {
            errors.add(new SemanticError(
                ErrorCodes.VARIABLE_ALREADY_DECLARED,
                "Variable '" + name + "' is already declared in this scope",
                node
            ));
            return null;
        }

        // Infer type from initializer
        Type type = Type.ANY;
        if (node.getInitializer() != null) {
            try {
                type = node.getInitializer().accept(typeInference);
            } catch (Exception e) {
                // Type inference failed
            }
        }

        symbolTable.declareVariable(name, type, node, false, false);
        return null;
    }

    @Override
    public Void visit(FunctionCallNode node) {
        // Get function name
        ExpressionNode funcExpr = node.getFunction();

        if (funcExpr instanceof IdentifierNode) {
            String funcName = ((IdentifierNode) funcExpr).getName();
            FunctionSymbol func = functionTable.lookupFunction(funcName);

            if (func == null) {
                errors.add(new SemanticError(
                    ErrorCodes.FUNCTION_NOT_DECLARED,
                    "Function '" + funcName + "' is not declared",
                    node
                ));
                return null;
            }

            // Check argument count
            List<ExpressionNode> args = node.getArguments();

            if (args.size() != func.getParameters().size()) {
                errors.add(new SemanticError(
                    ErrorCodes.WRONG_ARGUMENT_COUNT,
                    "Function '" + funcName + "' expects " + func.getParameters().size() +
                        " arguments but got " + args.size(),
                    node
                ));
            }
        }

        return null;
    }

    @Override
    public Void visit(PropertyAccessNode node) {
        Type objectType = node.getObject().accept(typeInference);

        if (!objectType.equals(Type.OBJECT) && !objectType.equals(Type.ANY)) {
            errors.add(new SemanticError(
                ErrorCodes.PROPERTY_ACCESS_NON_OBJECT,
                "Cannot access property on non-object type " + objectType,
                node
            ));
        }

        return null;
    }

    @Override
    public Void visit(IndexAccessNode node) {
        Type listType = node.getObject().accept(typeInference);
        Type indexType = node.getIndex().accept(typeInference);

        if (!listType.equals(Type.LIST) && !listType.equals(Type.ANY)) {
            errors.add(new SemanticError(
                ErrorCodes.INDEX_ACCESS_NON_LIST,
                "Cannot access index on non-list type " + listType,
                node
            ));
        }

        if (!indexType.equals(Type.INTEGER) && !indexType.equals(Type.ANY)) {
            errors.add(new SemanticError(
                ErrorCodes.NON_INTEGER_INDEX,
                "Index must be integer type, got " + indexType,
                node
            ));
        }

        return null;
    }

    // ========== STUB IMPLEMENTATIONS FOR REMAINING VISITORS ==========

    // These are minimal implementations - they can be expanded as needed

    @Override
    public Void visit(AdditiveExpressionNode node) {
        // Type checking is done by TypeInferenceVisitor
        return null;
    }

    @Override
    public Void visit(ArgumentListNode node) {
        return null;
    }

    @Override
    public Void visit(EqualityExpressionNode node) {
        return null;
    }

    @Override
    public Void visit(LogicalAndExpressionNode node) {
        return null;
    }

    @Override
    public Void visit(LogicalOrExpressionNode node) {
        return null;
    }

    @Override
    public Void visit(MultiplicativeExpressionNode node) {
        return null;
    }

    @Override
    public Void visit(ObjectMemberNode node) {
        return null;
    }

    @Override
    public Void visit(PostfixExpressionNode node) {
        return null;
    }

    @Override
    public Void visit(PostfixOperatorNode node) {
        return null;
    }

    @Override
    public Void visit(PrimaryExpressionNode node) {
        return null;
    }

    @Override
    public Void visit(RelationalExpressionNode node) {
        return null;
    }

    @Override
    public Void visit(TernaryExpressionNode node) {
        Type condType = node.getCondition().accept(typeInference);
        if (!condType.equals(Type.BOOLEAN) && !condType.equals(Type.ANY)) {
            errors.add(new SemanticError(
                ErrorCodes.NON_BOOLEAN_CONDITION,
                "Ternary condition must be boolean, got " + condType,
                node
            ));
        }
        return null;
    }

    @Override
    public Void visit(UnaryExpressionNode node) {
        return null;
    }

    @Override
    public Void visit(ExpressionListNode node) {
        return null;
    }

    @Override
    public Void visit(ObjectMemberListNode node) {
        return null;
    }

    @Override
    public Void visit(BooleanLiteralNode node) {
        return null;
    }

    @Override
    public Void visit(DecimalLiteralNode node) {
        return null;
    }

    @Override
    public Void visit(IntegerLiteralNode node) {
        return null;
    }

    @Override
    public Void visit(ListLiteralNode node) {
        return null;
    }

    @Override
    public Void visit(NullLiteralNode node) {
        return null;
    }

    @Override
    public Void visit(ObjectLiteralNode node) {
        return null;
    }

    @Override
    public Void visit(StringLiteralNode node) {
        return null;
    }

    @Override
    public Void visit(ElseIfClauseNode node) {
        Type condType = node.getCondition().accept(typeInference);
        if (!condType.equals(Type.BOOLEAN) && !condType.equals(Type.ANY)) {
            errors.add(new SemanticError(
                ErrorCodes.NON_BOOLEAN_CONDITION,
                "else_if condition must be boolean, got " + condType,
                node
            ));
        }

        if (node.getStatement() != null) {
            node.getStatement().accept(this);
        }

        return null;
    }

    @Override
    public Void visit(ExpressionStatementNode node) {
        // Just analyze the expression
        if (node.getExpression() != null) {
            node.getExpression().accept(typeInference);
        }
        return null;
    }

    @Override
    public Void visit(ParameterNode node) {
        return null;
    }

    @Override
    public Void visit(TypeNode node) {
        return null;
    }

    @Override
    public Void visit(ElseClauseNode node) {
        return null;
    }

    @Override
    public Void visit(JoinClauseNode node) {
        return null;
    }

    @Override
    public Void visit(ParallelBranchNode node) {
        return null;
    }

    @Override
    public Void visit(WhenClauseNode node) {
        return null;
    }

    @Override
    public Void visit(EndElementNode node) {
        return null;
    }

    @Override
    public Void visit(ExclusiveGatewayNode node) {
        return null;
    }

    @Override
    public Void visit(ParallelGatewayNode node) {
        return null;
    }

    @Override
    public Void visit(StartElementNode node) {
        return null;
    }

    @Override
    public Void visit(TaskElementNode node) {
        return null;
    }
}
