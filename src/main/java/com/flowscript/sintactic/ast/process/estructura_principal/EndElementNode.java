package com.flowscript.sintactic.ast.process.estructura_principal;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.ASTNode;

/**
 * Nodo AST que representa el elemento de fin de un proceso.
 *
 * <p>Ejemplo: end FinExitoso</p>
 */
public class EndElementNode extends ASTNode {
    private final String endName;

    public EndElementNode(Token endToken, String endName) {
        super(endToken.getLine(), endToken.getColumn(), endToken.getPosition());
        this.endName = endName;
    }

    public String getEndName() {
        return endName;
    }

    @Override
    public String getNodeType() {
        return "EndElement";
    }

    @Override
    public String toString() {
        return "EndElement(" + endName + ")";
    }
}
