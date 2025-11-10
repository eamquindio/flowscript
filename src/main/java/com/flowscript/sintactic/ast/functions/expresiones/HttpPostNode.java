package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.semantic.visitor.ASTVisitor;

/**
 * Represents HTTP POST operations.
 * Syntax: http.post(url, body) or http.post(url, body, headers)
 * Returns: objeto (object/map with response)
 *
 * Examples:
 *   respuesta = http.post("https://api.ejemplo.com/usuarios", { "nombre": "Juan" })
 *   respuesta = http.post("https://api.ejemplo.com/usuarios", datos, { "Authorization": "Bearer token" })
 */
public class HttpPostNode extends ExpressionNode {
    private final ExpressionNode url;     // URL string
    private final ExpressionNode body;    // Request body object
    private final ExpressionNode headers; // Optional headers object (can be null)

    public HttpPostNode(Token httpToken, ExpressionNode url, ExpressionNode body, ExpressionNode headers) {
        super(httpToken);
        this.url = url;
        this.body = body;
        this.headers = headers;
    }

    public ExpressionNode getUrl() {
        return url;
    }

    public ExpressionNode getBody() {
        return body;
    }

    public ExpressionNode getHeaders() {
        return headers;
    }

    public boolean hasHeaders() {
        return headers != null;
    }

    @Override
    public String getNodeType() {
        return "HttpPost";
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
        return "HttpPost(url, body" + (hasHeaders() ? ", headers" : "") + ")";
    }
}
