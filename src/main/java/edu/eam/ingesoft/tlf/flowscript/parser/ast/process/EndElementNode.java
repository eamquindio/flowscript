package edu.eam.ingesoft.tlf.flowscript.parser.ast.process;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;

public class EndElementNode extends ProcessElementNode {
    private final String name;

    public EndElementNode(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
