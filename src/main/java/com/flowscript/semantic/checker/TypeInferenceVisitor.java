package com.flowscript.semantic.checker;

import com.flowscript.semantic.types.Type;
import com.flowscript.semantic.symbols.SymbolTable;
import com.flowscript.semantic.symbols.Symbol;
import com.flowscript.semantic.functions.FunctionTable;
import com.flowscript.semantic.functions.FunctionSymbol;
import com.flowscript.semantic.visitor.ASTVisitor;
import com.flowscript.sintactic.ast.*;
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
import com.flowscript.lexer.Token;

import java.util.List;

/**
 * Visitor that infers the type of expressions in the AST.
 * Used by TypeChecker to validate type compatibility.
 */
public class TypeInferenceVisitor implements ASTVisitor<Type> {
    private final SymbolTable symbolTable;
    private final FunctionTable functionTable;
    private final TypeChecker typeChecker;

    public TypeInferenceVisitor(SymbolTable symbolTable, FunctionTable functionTable, TypeChecker typeChecker) {
        this.symbolTable = symbolTable;
        this.functionTable = functionTable;
        this.typeChecker = typeChecker;
    }

    // ========== Literals ==========

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

    // ========== Identifiers ==========

    @Override
    public Type visit(IdentifierNode node) {
        Symbol symbol = symbolTable.lookupVariable(node.getName());
        if (symbol == null) {
            // Variable not declared - error will be reported elsewhere
            return Type.ANY;
        }
        return symbol.getType();
    }

    // ========== Binary Expressions ==========

    @Override
    public Type visit(AdditiveExpressionNode node) {
        if (node.isSingleOperand()) {
            return node.getSingleOperand().accept(this);
        }

        List<ExpressionNode> operands = node.getOperands();
        List<Token> operators = node.getOperators();

        // Start with first operand type
        Type resultType = operands.get(0).accept(this);

        // Process each operation
        for (int i = 0; i < operators.size(); i++) {
            Type rightType = operands.get(i + 1).accept(this);
            String op = operators.get(i).getValue();

            Operator operator = op.equals("+") ? Operator.PLUS : Operator.MINUS;
            resultType = typeChecker.getOperatorResultType(operator, resultType, rightType);
        }

        return resultType;
    }

    @Override
    public Type visit(MultiplicativeExpressionNode node) {
        if (node.isSingleOperand()) {
            return node.getSingleOperand().accept(this);
        }

        List<ExpressionNode> operands = node.getOperands();
        List<Token> operators = node.getOperators();

        Type resultType = operands.get(0).accept(this);

        for (int i = 0; i < operators.size(); i++) {
            Type rightType = operands.get(i + 1).accept(this);
            String op = operators.get(i).getValue();

            Operator operator;
            switch (op) {
                case "*": operator = Operator.MULTIPLY; break;
                case "/": operator = Operator.DIVIDE; break;
                case "%": operator = Operator.MODULO; break;
                default: throw new IllegalArgumentException("Unknown operator: " + op);
            }

            resultType = typeChecker.getOperatorResultType(operator, resultType, rightType);
        }

        return resultType;
    }

    @Override
    public Type visit(RelationalExpressionNode node) {
        if (node.isSingleOperand()) {
            return node.getSingleOperand().accept(this);
        }

        // Relational operators always return boolean
        List<ExpressionNode> operands = node.getOperands();
        List<Token> operators = node.getOperators();

        Type leftType = operands.get(0).accept(this);

        for (int i = 0; i < operators.size(); i++) {
            Type rightType = operands.get(i + 1).accept(this);
            String op = operators.get(i).getValue();

            Operator operator;
            switch (op) {
                case "<": operator = Operator.LESS_THAN; break;
                case ">": operator = Operator.GREATER_THAN; break;
                case "<=": operator = Operator.LESS_EQUAL; break;
                case ">=": operator = Operator.GREATER_EQUAL; break;
                default: throw new IllegalArgumentException("Unknown operator: " + op);
            }

            // Validate but we know result is boolean
            typeChecker.getOperatorResultType(operator, leftType, rightType);
            leftType = Type.BOOLEAN;
        }

        return Type.BOOLEAN;
    }

    @Override
    public Type visit(EqualityExpressionNode node) {
        if (node.isSingleOperand()) {
            return node.getSingleOperand().accept(this);
        }

        // Equality operators always return boolean
        return Type.BOOLEAN;
    }

    @Override
    public Type visit(LogicalAndExpressionNode node) {
        if (node.isSingleOperand()) {
            return node.getSingleOperand().accept(this);
        }

        // Logical AND always returns boolean
        List<ExpressionNode> operands = node.getOperands();
        for (ExpressionNode operand : operands) {
            Type type = operand.accept(this);
            typeChecker.getOperatorResultType(Operator.AND, Type.BOOLEAN, type);
        }

        return Type.BOOLEAN;
    }

    @Override
    public Type visit(LogicalOrExpressionNode node) {
        if (node.isSingleOperand()) {
            return node.getSingleOperand().accept(this);
        }

        // Logical OR always returns boolean
        List<ExpressionNode> operands = node.getOperands();
        for (ExpressionNode operand : operands) {
            Type type = operand.accept(this);
            typeChecker.getOperatorResultType(Operator.OR, Type.BOOLEAN, type);
        }

        return Type.BOOLEAN;
    }

    // ========== Unary Expressions ==========

    @Override
    public Type visit(UnaryExpressionNode node) {
        Type operandType = node.getOperand().accept(this);
        String op = node.getOperator();

        Operator operator;
        switch (op) {
            case "no": operator = Operator.NOT; break;
            case "-": operator = Operator.MINUS; break;
            default: throw new IllegalArgumentException("Unknown unary operator: " + op);
        }

        return typeChecker.getUnaryOperatorResultType(operator, operandType);
    }

    // ========== Property and Index Access ==========

    @Override
    public Type visit(PropertyAccessNode node) {
        Type objectType = node.getObject().accept(this);

        // Property access on non-object is an error (reported elsewhere)
        if (!objectType.equals(Type.OBJECT) && !objectType.equals(Type.ANY)) {
            return Type.ANY;
        }

        // Property access returns unknown type (dynamic)
        return Type.ANY;
    }

    @Override
    public Type visit(IndexAccessNode node) {
        Type listType = node.getObject().accept(this);
        Type indexType = node.getIndex().accept(this);

        // Index access on non-list is an error (reported elsewhere)
        if (!listType.equals(Type.LIST) && !listType.equals(Type.ANY)) {
            return Type.ANY;
        }

        // Index must be integer (validated elsewhere)
        if (!indexType.equals(Type.INTEGER) && !indexType.equals(Type.ANY)) {
            // Error reported elsewhere
        }

        // Index access returns unknown type (dynamic)
        return Type.ANY;
    }

    // ========== Function Calls ==========

    @Override
    public Type visit(FunctionCallNode node) {
        // FunctionCallNode has an ExpressionNode for function, which may be an identifier or property access
        // For now, we'll handle simple identifier cases
        ExpressionNode funcExpr = node.getFunction();

        if (funcExpr instanceof IdentifierNode) {
            String funcName = ((IdentifierNode) funcExpr).getName();
            FunctionSymbol func = functionTable.lookupFunction(funcName);
            if (func == null) {
                // Function not declared - error reported elsewhere
                return Type.ANY;
            }
            return func.getReturnType();
        }

        // For complex function expressions (like module.function), return ANY for now
        return Type.ANY;
    }

    // ========== Ternary Expression ==========

    @Override
    public Type visit(TernaryExpressionNode node) {
        Type condType = node.getCondition().accept(this);

        // Condition must be boolean (validated elsewhere)
        Type trueType = node.getTrueExpression().accept(this);
        Type falseType = node.getFalseExpression().accept(this);

        // If both branches same type, that's the result
        if (trueType.equals(falseType)) {
            return trueType;
        }

        // Numeric promotion
        if (typeChecker.isNumeric(trueType) && typeChecker.isNumeric(falseType)) {
            return Type.DECIMAL;
        }

        // Otherwise, types differ - return ANY
        return Type.ANY;
    }

    // ========== Postfix Expressions ==========

    @Override
    public Type visit(PostfixExpressionNode node) {
        // Postfix expressions wrap a primary expression with operators
        // For type inference, we start with the primary type
        Type baseType = node.getPrimary().accept(this);

        // If there are postfix operators (like function calls, property/index access),
        // they may change the type. For now, we return ANY if there are operators.
        if (node.hasOperators()) {
            return Type.ANY;
        }

        return baseType;
    }

    @Override
    public Type visit(PrimaryExpressionNode node) {
        // PrimaryExpressionNode is abstract, but we handle the ParenthesizedExpressionNode case
        if (node instanceof PrimaryExpressionNode.ParenthesizedExpressionNode) {
            PrimaryExpressionNode.ParenthesizedExpressionNode paren =
                (PrimaryExpressionNode.ParenthesizedExpressionNode) node;
            return paren.getInnerExpression().accept(this);
        }

        // Other primary expressions (literals, identifiers) are handled by their own visit methods
        return Type.ANY;
    }

    // ========== Nodes that don't directly return types ==========

    @Override
    public Type visit(ProgramNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(FunctionDeclarationNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(ProcessDeclarationNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(ImportDeclarationNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(VariableDeclarationNode node) {
        if (node.getInitializer() != null) {
            return node.getInitializer().accept(this);
        }
        return Type.ANY;
    }

    @Override
    public Type visit(BlockNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(IfStatementNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(WhileStatementNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(ForStatementNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(ForRangeStatementNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(BreakStatementNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(ContinueStatementNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(ReturnStatementNode node) {
        if (node.getExpression() != null) {
            return node.getExpression().accept(this);
        }
        return Type.VOID;
    }

    @Override
    public Type visit(ThrowStatementNode node) {
        if (node.getExpression() != null) {
            return node.getExpression().accept(this);
        }
        return Type.OBJECT;
    }

    @Override
    public Type visit(TryStatementNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(ArgumentListNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(ObjectMemberNode node) {
        if (node.getValue() != null) {
            return node.getValue().accept(this);
        }
        return Type.ANY;
    }

    @Override
    public Type visit(PostfixOperatorNode node) {
        return Type.ANY;
    }

    @Override
    public Type visit(ExpressionListNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(ObjectMemberListNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(ElseIfClauseNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(ExpressionStatementNode node) {
        if (node.getExpression() != null) {
            return node.getExpression().accept(this);
        }
        return Type.VOID;
    }

    @Override
    public Type visit(VariableDeclarationStatementNode node) {
        if (node.getInitializer() != null) {
            return node.getInitializer().accept(this);
        }
        return Type.VOID;
    }

    @Override
    public Type visit(ParameterNode node) {
        return Type.ANY;
    }

    @Override
    public Type visit(TypeNode node) {
        return Type.ANY;
    }

    @Override
    public Type visit(ElseClauseNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(JoinClauseNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(ParallelBranchNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(WhenClauseNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(EndElementNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(ExclusiveGatewayNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(ParallelGatewayNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(StartElementNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(TaskElementNode node) {
        return Type.VOID;
    }

    @Override
    public Type visit(GotoStatementNode node) {
        return Type.VOID;
    }
}
