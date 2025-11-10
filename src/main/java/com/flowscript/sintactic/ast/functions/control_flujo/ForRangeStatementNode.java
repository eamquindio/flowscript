package com.flowscript.sintactic.ast.functions.control_flujo;

import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.lexer.Token;
import com.flowscript.semantic.visitor.ASTVisitor;

/**
 * Represents range-based for loops with from/to/step syntax.
 *
 * <h3>Grammar:</h3>
 * <pre>
 * ForRangeStatement ::= 'for' IDENTIFIER 'from' Expression 'to' Expression ('step' Expression)? Statement
 * </pre>
 *
 * <h3>Examples:</h3>
 * <pre>
 * // Basic range
 * for i from 1 to 10 {
 *     imprimir(i)
 * }
 *
 * // With step
 * for x from 0 to 100 step 5 {
 *     imprimir(x)  // 0, 5, 10, 15, ..., 100
 * }
 *
 * // Descending
 * for i from 10 to 1 step -1 {
 *     imprimir(i)
 * }
 *
 * // With expressions
 * for n from start_value to end_value step increment {
 *     process(n)
 * }
 * </pre>
 *
 * <h3>Semantic Notes:</h3>
 * - Loop variable is block-scoped to the for loop
 * - from and to expressions are evaluated once at loop entry
 * - step is optional, defaults to 1
 * - step can be negative for descending loops
 */
public class ForRangeStatementNode extends StatementNode {
    private final String loopVariable;
    private final ExpressionNode fromExpression;
    private final ExpressionNode toExpression;
    private final ExpressionNode stepExpression;  // Can be null (defaults to 1)
    private final StatementNode body;

    public ForRangeStatementNode(Token forToken, String loopVariable,
                                  ExpressionNode fromExpression,
                                  ExpressionNode toExpression,
                                  ExpressionNode stepExpression,
                                  StatementNode body) {
        super(forToken);
        this.loopVariable = loopVariable;
        this.fromExpression = fromExpression;
        this.toExpression = toExpression;
        this.stepExpression = stepExpression;
        this.body = body;
    }

    public String getLoopVariable() {
        return loopVariable;
    }

    public ExpressionNode getFromExpression() {
        return fromExpression;
    }

    public ExpressionNode getToExpression() {
        return toExpression;
    }

    public ExpressionNode getStepExpression() {
        return stepExpression;
    }

    public boolean hasStepExpression() {
        return stepExpression != null;
    }

    public StatementNode getBody() {
        return body;
    }

    @Override
    public String getNodeType() {
        return "ForRangeStatement";
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        String stepPart = hasStepExpression() ? " step " + stepExpression.getNodeType() : "";
        return "ForRangeStatement(for " + loopVariable +
               " from " + fromExpression.getNodeType() +
               " to " + toExpression.getNodeType() +
               stepPart + ")";
    }
}
