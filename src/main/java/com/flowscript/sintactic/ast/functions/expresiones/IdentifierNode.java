package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;
import com.flowscript.semantic.visitor.ASTVisitor;

/**
 * Represents an identifier (variable name, function name, etc.).
 * Example: variable_name, function_name
 */
public class IdentifierNode extends ExpressionNode {
    private final String name;

    public IdentifierNode(Token identifierToken) {
        super(identifierToken);
        this.name = identifierToken.getValue();
    }

    public String getName() {
        return name;
    }

    @Override
    public String getNodeType() {
        return "Identifier";
    }

    @Override
    public String getExpressionType() {
        // Type would be determined by symbol table lookup during semantic analysis
        return "identifier";
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Identifier(" + name + ")";
    }
}