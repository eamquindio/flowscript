package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.semantic.visitor.ASTVisitor;

/**
 * Represents database query operations.
 * Syntax: db.consultar(query, params)
 * Returns: lista (list of objects/maps)
 *
 * Example:
 *   usuarios = db.consultar("SELECT * FROM usuarios WHERE edad > ?", [18])
 */
public class DbQueryNode extends ExpressionNode {
    private final ExpressionNode query;      // SQL query string
    private final ExpressionNode parameters; // List of parameters

    public DbQueryNode(Token dbToken, ExpressionNode query, ExpressionNode parameters) {
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
        return "DbQuery";
    }

    @Override
    public String getExpressionType() {
        return "lista"; // Returns list of objects
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "DbQuery(query, params)";
    }
}
