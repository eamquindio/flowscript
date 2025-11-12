package edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations;

import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import java.util.*;

public class ProgramNode extends ASTNode {
    private final List<DeclarationNode> declarations;

    public ProgramNode(List<DeclarationNode> declarations) {
        this.declarations = declarations != null ? declarations : new ArrayList<>();
    }

    public List<DeclarationNode> getDeclarations() {
        return declarations;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
