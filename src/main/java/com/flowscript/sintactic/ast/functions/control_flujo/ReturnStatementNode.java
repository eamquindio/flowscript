package com.flowscript.sintactic.ast.functions.control_flujo;

import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;

/**
 * Represents return statements in functions.
 * Examples: return 42; return calculate_tax(amount, rate); return;
 */
public class ReturnStatementNode extends StatementNode {
    private final ExpressionNode expression; // Can be null for void returns

    public ReturnStatementNode(Token returnToken, ExpressionNode expression) {
        super(returnToken);
        this.expression = expression;
    }

    public ReturnStatementNode(Token returnToken) {
        this(returnToken, null);
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    public boolean hasReturnValue() {
        return expression != null;
    }

    @Override
    public String getNodeType() {
        return "ReturnStatement";
    }

    @Override
    public String toString() {
        return "ReturnStatement" + (hasReturnValue() ? "(" + expression.getNodeType() + ")" : "(void)");
    }
}