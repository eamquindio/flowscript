package edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.process.ProcessElementNode;
import java.util.*;

public class ProcessDeclarationNode extends DeclarationNode {
    private final String name;
    private final List<ProcessElementNode> elements;

    public ProcessDeclarationNode(String name, List<ProcessElementNode> elements) {
        this.name = name;
        this.elements = elements != null ? elements : new ArrayList<>();
    }

    public String getName() { return name; }
    public List<ProcessElementNode> getElements() { return elements; }
    public void addElement(ProcessElementNode element) { elements.add(element); }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
