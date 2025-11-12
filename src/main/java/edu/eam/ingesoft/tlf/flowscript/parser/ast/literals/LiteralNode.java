package edu.eam.ingesoft.tlf.flowscript.parser.ast.literals;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.ExpressionNode;

public abstract class LiteralNode extends ExpressionNode {
    public abstract Object getValue();
}
