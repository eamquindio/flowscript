package com.flowscript.sintactic.ast.functions.expresiones;

import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents postfix operators: '.', '[', '('
 * Grammar: PostfixOperator ::= '.' IDENTIFIER | '[' Expression ']' | '(' ArgumentList? ')'
 * These transform the base expression
 */
public abstract class PostfixOperatorNode extends ASTNode {

    protected PostfixOperatorNode(Token operatorToken) {
        super(operatorToken);
    }

    /**
     * Property access operator: expr.property
     */
    public static class PropertyAccessOperatorNode extends PostfixOperatorNode {
        private final String propertyName;

        public PropertyAccessOperatorNode(Token dotToken, String propertyName) {
            super(dotToken);
            this.propertyName = propertyName;
        }

        public String getPropertyName() {
            return propertyName;
        }

        @Override
        public String getNodeType() {
            return "PropertyAccessOperator";
        }

        @Override
        public String toString() {
            return "." + propertyName;
        }
    }

    /**
     * Index access operator: expr[index]
     */
    public static class IndexAccessOperatorNode extends PostfixOperatorNode {
        private final ExpressionNode index;

        public IndexAccessOperatorNode(Token leftBracketToken, ExpressionNode index) {
            super(leftBracketToken);
            this.index = index;
        }

        public ExpressionNode getIndex() {
            return index;
        }

        @Override
        public String getNodeType() {
            return "IndexAccessOperator";
        }

        @Override
        public String toString() {
            return "[" + index.getNodeType() + "]";
        }
    }

    /**
     * Function call operator: expr(args)
     */
    public static class FunctionCallOperatorNode extends PostfixOperatorNode {
        private final List<ExpressionNode> arguments;

        public FunctionCallOperatorNode(Token leftParenToken, List<ExpressionNode> arguments) {
            super(leftParenToken);
            this.arguments = new ArrayList<>(arguments);
        }

        public List<ExpressionNode> getArguments() {
            return new ArrayList<>(arguments);
        }

        @Override
        public String getNodeType() {
            return "FunctionCallOperator";
        }

        @Override
        public String toString() {
            return "(" + arguments.size() + " args)";
        }
    }
}