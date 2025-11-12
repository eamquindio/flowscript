package edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.ASTNode;

public abstract class DeclarationNode extends ASTNode {
    public DeclarationNode() {
        super();
    }

    public DeclarationNode(int line, int column) {
        super(line, column);
    }
}
