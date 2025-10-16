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
 * 
 * <pre>
 * ParallelBranch ::= 'branch' '->' IDENTIFIER
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 5: Cláusulas de Control de Flujo
 *
 * <h3>Ejemplos:</h3>
 * 
 * <pre>
 * branch -> VerificarCredito
 * branch -> VerificarAntecedentes
 * branch -> NotificarSupervisor
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * <ol>
 * <li>Consumir 'branch' o 'rama'</li>
 * <li>Consumir '->'</li>
 * <li>Consumir IDENTIFIER (nodo destino)</li>
 * <li>Crear y retornar ParallelBranchNode</li>
 * </ol>
 *
 * @see ParallelBranchNode
 */
public class ParallelBranchParser implements IParser<ParallelBranchNode> {

    @Override
    public ParallelBranchNode parse(ParserContext context) throws Parser.ParseException {
        if (!context.hasMoreTokens()) {
            throw new Parser.ParseException(
                    "Se esperaba una cláusula 'branch', pero se encontró el final del archivo");
        }


        Token branchToken = context.getCurrentToken();

        if (branchToken.getType() != TokenType.BRANCH) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba un branch",
                            branchToken.getValue(),
                            branchToken.getLine(),
                            branchToken.getColumn()));
        }

        context.consume(TokenType.BRANCH);

        if (!context.hasMoreTokens()) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba un branch",
                            branchToken.getLine(),
                            branchToken.getColumn()));
        }

        Token currentToken = context.getCurrentToken();
        if (currentToken.getType() != TokenType.ARROW) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba un branch",
                            currentToken.getValue(),
                            currentToken.getLine(),
                            currentToken.getColumn()));
        }

        context.consume(TokenType.ARROW);

        if (!context.hasMoreTokens()) {
            throw new Parser.ParseException(
                    "Se esperaba un branch");
        }

        Token targetToken = context.getCurrentToken();

        if (targetToken.getType() != TokenType.IDENTIFIER) {
            throw new Parser.ParseException(
                    String.format(
                            "se esperaba un branch",
                            targetToken.getValue(),
                            targetToken.getLine(),
                            targetToken.getColumn()));
        }

        String targetTask = targetToken.getValue();

        if (targetTask == null || targetTask.trim().isEmpty()) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba un branch, no puede estar vacio",
                            targetToken.getLine(),
                            targetToken.getColumn()));
        }


        context.consume(TokenType.IDENTIFIER);

        ParallelBranchNode parallelBranchNode = new ParallelBranchNode(branchToken, targetTask);

        return parallelBranchNode;
    }
}