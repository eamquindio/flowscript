package edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.*;
import java.util.*;

public class FunctionCallNode extends ExpressionNode {
    private final ExpressionNode function;
    private final List<ExpressionNode> arguments;
    
    public FunctionCallNode(ExpressionNode function, List<ExpressionNode> arguments) {
        this.function = function;
        this.arguments = arguments != null ? arguments : new ArrayList<>();
    }
    
    public ExpressionNode getFunction() { return function; }
    public List<ExpressionNode> getArguments() { return arguments; }
    
    @Override
    public void accept(ASTVisitor visitor) { visitor.visit(this); }
}
