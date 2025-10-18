package com.flowscript.sintactic.ast.functions.control_ejecucion;

import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.lexer.Token;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a block of statements enclosed in braces.
 * Example: { statement1; statement2; statement3; }
 */
public class BlockNode extends StatementNode {
    private final List<StatementNode> statements;

    public BlockNode(Token openBraceToken) {
        super(openBraceToken);
        this.statements = new ArrayList<>();
    }

    public void addStatement(StatementNode statement) {
        statements.add(statement);
    }

    public List<StatementNode> getStatements() {
        return new ArrayList<>(statements);
    }

    @Override
    public String getNodeType() {
        return "Block";
    }

    @Override
    public String toString() {
        return "Block with " + statements.size() + " statements";
    }
}