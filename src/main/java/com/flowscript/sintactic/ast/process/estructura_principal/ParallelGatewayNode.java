package com.flowscript.sintactic.ast.process.estructura_principal;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.process.clausulas_control.ParallelBranchNode;
import com.flowscript.sintactic.ast.process.clausulas_control.JoinClauseNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Nodo AST que representa un gateway paralelo (AND) en un proceso.
 *
 * <p>Ejemplo:</p>
 * <pre>
 * gateway Iniciar parallel {
 *     branch -> VerificarCredito
 *     branch -> VerificarPenal
 *     join -> Consolidar
 * }
 * </pre>
 */
public class ParallelGatewayNode extends ASTNode {
    private final String gatewayName;
    private final List<ParallelBranchNode> branches;
    private final JoinClauseNode joinClause;

    public ParallelGatewayNode(Token gatewayToken, String gatewayName,
                               List<ParallelBranchNode> branches, JoinClauseNode joinClause) {
        super(gatewayToken.getLine(), gatewayToken.getColumn(), gatewayToken.getPosition());
        this.gatewayName = gatewayName;
        this.branches = new ArrayList<>(branches);
        this.joinClause = joinClause;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public List<ParallelBranchNode> getBranches() {
        return new ArrayList<>(branches);
    }

    public JoinClauseNode getJoinClause() {
        return joinClause;
    }

    @Override
    public String getNodeType() {
        return "ParallelGateway";
    }

    @Override
    public String toString() {
        return "ParallelGateway(" + gatewayName + " with " + branches.size() +
               " branches joining at " + joinClause.getTargetTask() + ")";
    }
}
