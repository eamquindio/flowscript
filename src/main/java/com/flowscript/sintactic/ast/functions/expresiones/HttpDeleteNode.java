package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.semantic.visitor.ASTVisitor;

/**
 * Represents HTTP DELETE operations.
 * Syntax: http.delete(url) or http.delete(url, headers)
 * Returns: objeto (object/map with response)
 *
 * Examples:
 *   respuesta = http.delete("https://api.ejemplo.com/usuarios/1")
 *   respuesta = http.delete("https://api.ejemplo.com/usuarios/1", { "Authorization": "Bearer token" })
 */
public class HttpDeleteNode extends ExpressionNode {
    private final ExpressionNode url;     // URL string
    private final ExpressionNode headers; // Optional headers object (can be null)

    public HttpDeleteNode(Token httpToken, ExpressionNode url, ExpressionNode headers) {
        super(httpToken);
        this.url = url;
        this.headers = headers;
    }

    public ExpressionNode getUrl() {
        return url;
    }

    public ExpressionNode getHeaders() {
        return headers;
    }

    public boolean hasHeaders() {
        return headers != null;
    }

    @Override
    public String getNodeType() {
        return "HttpDelete";
    }

    @Override
    public String getExpressionType() {
        return "objeto"; // Returns object (response map)
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "HttpDelete(url" + (hasHeaders() ? ", headers" : "") + ")";
    }
}
