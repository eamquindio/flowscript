package com.flowscript.sintactic.ast.process.navegacion;

import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.lexer.Token;

/**
 * Represents go_to statements for navigation between process tasks.
 * Example: go_to ValidateOrder
 */
public class GotoStatementNode extends StatementNode {
    private final String targetLabel;

    public GotoStatementNode(Token gotoToken, String targetLabel) {
        super(gotoToken);
        this.targetLabel = targetLabel;
    }

    public String getTargetLabel() {
        return targetLabel;
    }

    /** Alias de conveniencia: algunos tests usan getTargetName(). */
    public String getTargetName() {
        return targetLabel;
    }

    @Override
    public String getNodeType() {
        return "GotoStatement";
    }

    @Override
    public String toString() {
        return "GotoStatement -> " + targetLabel;
    }
}
