package com.flowscript.sintactic.ast.functions.control_flujo;

import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.lexer.Token;
import com.flowscript.semantic.visitor.ASTVisitor;

/**
 * Represents break statements for exiting loops.
 *
 * <h3>Grammar:</h3>
 * <pre>
 * BreakStatement ::= 'break'
 * </pre>
 *
 * <h3>Examples:</h3>
 * <pre>
 * while true {
 *     if found {
 *         break
 *     }
 *     search_next()
 * }
 *
 * for each item in items {
 *     if item == target {
 *         break
 *     }
 * }
 * </pre>
 *
 * <h3>Semantic Constraints:</h3>
 * - Can only be used inside while or for loops
 * - Cannot be used in function root scope
 * - Cannot be used in process tasks (use go_to instead)
 */
public class BreakStatementNode extends StatementNode {

    public BreakStatementNode(Token breakToken) {
        super(breakToken);
    }

    @Override
    public String getNodeType() {
        return "BreakStatement";
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "BreakStatement";
    }
}
