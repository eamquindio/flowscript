package com.flowscript.sintactic.ast.process.estructura_principal;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.ASTNode;

/**
 * Nodo AST que representa el elemento de inicio de un proceso.
 *
 * Ejemplo: start -> PrimeraTarea
 */
public class StartElementNode extends ASTNode {

    /** Nombre del primer nodo al que salta el start. */
    private final String targetNodeName;

    public StartElementNode(Token startToken, String targetNodeName) {
        super(startToken.getLine(), startToken.getColumn(), startToken.getPosition());
        this.targetNodeName = targetNodeName;
    }

    /** Getter original usado internamente. */
    public String getTargetNodeName() {
        return targetNodeName;
    }

    /**
     * Alias para compatibilidad con los tests y otros consumidores.
     * Los tests invocan getTargetName(), así que delegamos al campo real.
     */
    public String getTargetName() {
        return targetNodeName;
    }

    @Override
    public String getNodeType() {
        return "StartElement";
    }

    @Override
    public String toString() {
        return "StartElement -> " + targetNodeName;
    }
}
