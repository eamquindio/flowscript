package com.flowscript.sintactic.ast.functions.control_flujo;

import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;
import com.flowscript.lexer.Token;

/**
 * Represents try/catch statements for error handling.
 * Example: try { risky_operation() } catch (error) { handle_error(error) }
 */
public class TryStatementNode extends StatementNode {
    private final BlockNode tryBlock;
    private final String catchVariable;
    private final BlockNode catchBlock;

    public TryStatementNode(Token tryToken, BlockNode tryBlock, String catchVariable, BlockNode catchBlock) {
        super(tryToken);
        this.tryBlock = tryBlock;
        this.catchVariable = catchVariable;
        this.catchBlock = catchBlock;
    }

    public BlockNode getTryBlock() {
        return tryBlock;
    }

    public String getCatchVariable() {
        return catchVariable;
    }

    public BlockNode getCatchBlock() {
        return catchBlock;
    }

    @Override
    public String getNodeType() {
        return "TryStatement";
    }

    @Override
    public String toString() {
        return "TryStatement with catch variable: " + catchVariable;
    }
}