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
        Token branchKeyword = parseBranchKeyword(context);
        parseArrow(context);
        String targetNode = parseTargetNode(context);

        return new ParallelBranchNode(branchKeyword, targetNode);
    }

    private Token parseBranchKeyword(ParserContext context) throws Parser.ParseException {
        Token token = context.getCurrentToken();

        if (!context.check(TokenType.BRANCH)) {
            throw new Parser.ParseException("Se esperaba 'branch' o 'rama'");
        }

        context.advance();
        return token;
    }

    private void parseArrow(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.ARROW)) {
            throw new Parser.ParseException("Se esperaba '->' después de 'branch'");
        }

        context.advance();
    }

    private String parseTargetNode(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.IDENTIFIER)) {
            throw new Parser.ParseException("Se esperaba un identificador después de '->'");
        }

        String nodeName = context.getCurrentToken().getValue();
        context.advance();

        return nodeName;
    }
}
