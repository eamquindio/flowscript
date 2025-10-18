package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.lexer.Token;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents function call expressions.
 * Examples: calculate_tax(amount, rate), http.get(url), obj.method(a,b)
 */
public class FunctionCallNode extends ExpressionNode {
    private final ExpressionNode function;
    private final List<ExpressionNode> arguments;

    /**
     * Canonical constructor. Use the '(' token for source location.
     */
    public FunctionCallNode(ExpressionNode function, Token leftParenToken, List<ExpressionNode> arguments) {
        super(leftParenToken);
        this.function = function;
        this.arguments = new ArrayList<>(arguments != null ? arguments : List.of());
    }

    public ExpressionNode getFunction() {
        return function;
    }

    public List<ExpressionNode> getArguments() {
        return new ArrayList<>(arguments);
    }

    public int getArgumentCount() {
        return arguments.size();
    }

    @Override
    public String getNodeType() {
        return "FunctionCall";
    }

    @Override
    public String getExpressionType() {
        return "";
    }
}
