package com.flowscript.sintactic.ast.functions.control_flujo;

import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.lexer.Token;
import com.flowscript.semantic.visitor.ASTVisitor;

/**
 * Represents continue statements for skipping to next loop iteration.
 *
 * <h3>Grammar:</h3>
 * <pre>
 * ContinueStatement ::= 'continue'
 * </pre>
 *
 * <h3>Examples:</h3>
 * <pre>
 * for each item in items {
 *     if item.skip {
 *         continue
 *     }
 *     process(item)
 * }
 *
 * while processing {
 *     if not valid {
 *         continue
 *     }
 *     execute()
 * }
 * </pre>
 *
 * <h3>Semantic Constraints:</h3>
 * - Can only be used inside while or for loops
 * - Cannot be used in function root scope
 * - Cannot be used in process tasks
 */
public class ContinueStatementNode extends StatementNode {

    public ContinueStatementNode(Token continueToken) {
        super(continueToken);
    }

    @Override
    public String getNodeType() {
        return "ContinueStatement";
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ContinueStatement";
    }
}
