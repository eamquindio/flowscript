package edu.eam.ingesoft.tlf.flowscript.parser.ast.statements;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import java.util.*;

public class BlockNode extends StatementNode {
    private final List<StatementNode> statements;
    
    public BlockNode(List<StatementNode> statements) {
        this.statements = statements != null ? statements : new ArrayList<>();
    }
    
    public List<StatementNode> getStatements() { return statements; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
