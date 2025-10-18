package com.flowscript.sintactic.ast.process.estructura_principal;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.ASTNode;

/**
 * Nodo AST que representa el elemento de inicio de un proceso.
 *
 * <p>Ejemplo: start -> PrimeraTarea</p>
 */
public class StartElementNode extends ASTNode {
    private final String targetNodeName;

    public StartElementNode(Token startToken, String targetNodeName) {
        super(startToken.getLine(), startToken.getColumn(), startToken.getPosition());
        this.targetNodeName = targetNodeName;
    }

    public String getTargetNodeName() {
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
