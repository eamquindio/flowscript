package com.flowscript.sintactic.ast.functions.control_flujo;

import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;

/**
 * Represents for-each statements for iteration.
 * Example: for each item in items_list { process_item(item) }
 */
public class ForStatementNode extends StatementNode {
    private final String iteratorVariable;
    private final ExpressionNode iterable;
    private final StatementNode body;

    public ForStatementNode(Token forToken, String iteratorVariable, ExpressionNode iterable, StatementNode body) {
        super(forToken);
        this.iteratorVariable = iteratorVariable;
        this.iterable = iterable;
        this.body = body;
    }

    public String getIteratorVariable() {
        return iteratorVariable;
    }

    public ExpressionNode getIterable() {
        return iterable;
    }

    public StatementNode getBody() {
        return body;
    }

    @Override
    public String getNodeType() {
        return "ForStatement";
    }

    @Override
    public String toString() {
        return "ForStatement(for each " + iteratorVariable + " in " + iterable.getNodeType() + ")";
    }
}