package edu.eam.ingesoft.tlf.flowscript.parser.ast.statements;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.ASTNode;

public abstract class StatementNode extends ASTNode {
    public StatementNode() { super(); }
    public StatementNode(int line, int column) { super(line, column); }
}
