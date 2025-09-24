package com.flowscript.sintactic.ast.functions.control_ejecucion;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.ASTNode;

/**
 * Base class for all statement nodes in the FlowScript AST.
 * Statements are executable units that perform actions but don't return values.
 */
public abstract class StatementNode extends ASTNode {

    public StatementNode(int line, int column, int position) {
        super(line, column, position);
    }

    public StatementNode(Token token) {
        super(token);
    }

    @Override
    public String getNodeType() {
        return "Statement";
    }
}