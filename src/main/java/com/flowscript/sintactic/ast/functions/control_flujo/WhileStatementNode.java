package com.flowscript.sintactic.ast.functions.control_flujo;

import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;
import com.flowscript.semantic.visitor.ASTVisitor;

/**
 * Represents while loop statements.
 *
 * <h3>Grammar:</h3>
 * <pre>
 * WhileStatement ::= 'while' Expression Statement
 * </pre>
 *
 * <h3>Examples:</h3>
 * <pre>
 * while x < 10 {
 *     x = x + 1
 * }
 *
 * while not finished {
 *     process_next_item()
 * }
 * </pre>
 */
public class WhileStatementNode extends StatementNode {
    private final ExpressionNode condition;
    private final StatementNode body;

    public WhileStatementNode(Token whileToken, ExpressionNode condition, StatementNode body) {
        super(whileToken);
        this.condition = condition;
        this.body = body;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public StatementNode getBody() {
        return body;
    }

    @Override
    public String getNodeType() {
        return "WhileStatement";
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "WhileStatement(while " + condition.getNodeType() + ")";
    }
}
