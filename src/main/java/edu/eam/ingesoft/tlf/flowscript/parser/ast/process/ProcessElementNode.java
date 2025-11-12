package edu.eam.ingesoft.tlf.flowscript.parser.ast.process;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.ASTNode;

public abstract class ProcessElementNode extends ASTNode {
    public ProcessElementNode() {
        super();
    }

    public ProcessElementNode(int line, int column) {
        super(line, column);
    }
}
