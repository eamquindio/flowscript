package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.ParallelBranchNode;

/**
 * Parser para ramas paralelas en gateways paralelos.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ParallelBranch ::= 'branch' '->' IDENTIFIER
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 5: Cláusulas de Control de Flujo
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * branch -> VerificarCredito
 * branch -> VerificarAntecedentes
 * branch -> NotificarSupervisor
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * <ol>
 *   <li>Consumir 'branch' o 'rama'</li>
 *   <li>Consumir '->'</li>
 *   <li>Consumir IDENTIFIER (nodo destino)</li>
 *   <li>Crear y retornar ParallelBranchNode</li>
 * </ol>
 *
 * @see ParallelBranchNode
 */
public class ParallelBranchParser implements IParser<ParallelBranchNode> {

    @Override
    public ParallelBranchNode parse(ParserContext context) throws Parser.ParseException {
        // TODO: Implementar este método
        Token branchToken = context.getCurrentToken();
        if (branchToken == null || branchToken.getType() != TokenType.BRANCH) {
            throw new Parser.ParseException("Se esperaba _branch_ al inicio del elemento.");
        }
        if(branchToken.getType() == TokenType.BRANCH){
            context.consume(TokenType.BRANCH);
        }else if(branchToken.getType() == TokenType.IDENTIFIER && "rama".equals(branchToken.getValue())){
            context.consume();
        }else {
            throw new Parser.ParseException("Se espera la palabra _branch_ o _rama_ al inicio del elemento. ");
        }
        context.consume(TokenType.ARROW);
        Token tarName = context.consume(TokenType.IDENTIFIER);
        return new ParallelBranchNode(branchToken, tarName.getValue());
    }
}
