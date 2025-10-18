package com.flowscript.sintactic.ast.process.estructura_principal;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.process.clausulas_control.WhenClauseNode;
import com.flowscript.sintactic.ast.process.clausulas_control.ElseClauseNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Nodo AST que representa un gateway exclusivo (XOR) en un proceso.
 *
 * <p>Ejemplo:</p>
 * <pre>
 * gateway DecisionMonto {
 *     when entrada.monto > 10000 -> RequiereAprobacionGerente
 *     when entrada.monto > 1000 -> RequiereAprobacionSupervisor
 *     else -> AprobacionAutomatica
 * }
 * </pre>
 */
public class ExclusiveGatewayNode extends ASTNode {
    private final String gatewayName;
    private final List<WhenClauseNode> whenClauses;
    private final ElseClauseNode elseClause; // puede ser null

    public ExclusiveGatewayNode(Token gatewayToken, String gatewayName,
                                List<WhenClauseNode> whenClauses, ElseClauseNode elseClause) {
        super(gatewayToken.getLine(), gatewayToken.getColumn(), gatewayToken.getPosition());
        this.gatewayName = gatewayName;
        this.whenClauses = new ArrayList<>(whenClauses);
        this.elseClause = elseClause;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public List<WhenClauseNode> getWhenClauses() {
        return new ArrayList<>(whenClauses);
    }

    public ElseClauseNode getElseClause() {
        return elseClause;
    }

    @Override
    public String getNodeType() {
        return "ExclusiveGateway";
    }

    @Override
    public String toString() {
        return "ExclusiveGateway(" + gatewayName + " with " + whenClauses.size() +
               " when clauses" + (elseClause != null ? " and else clause" : "") + ")";
    }
}
