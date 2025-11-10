package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.semantic.visitor.ASTVisitor;

/**
 * Represents database execute operations.
 * Syntax: db.ejecutar(query, params)
 * Returns: integer (number of affected rows)
 *
 * Example:
 *   filasAfectadas = db.ejecutar("INSERT INTO usuarios VALUES (?, ?)", [nombre, email])
 */
public class DbExecuteNode extends ExpressionNode {
    private final ExpressionNode query;      // SQL query string
    private final ExpressionNode parameters; // List of parameters

    public DbExecuteNode(Token dbToken, ExpressionNode query, ExpressionNode parameters) {
        super(dbToken);
        this.query = query;
        this.parameters = parameters;
    }

    public ExpressionNode getQuery() {
        return query;
    }

    public ExpressionNode getParameters() {
        return parameters;
    }

    @Override
    public String getNodeType() {
        return "DbExecute";
    }

    @Override
    public String getExpressionType() {
        return "entero"; // Returns integer (affected rows)
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "DbExecute(query, params)";
    }
}
