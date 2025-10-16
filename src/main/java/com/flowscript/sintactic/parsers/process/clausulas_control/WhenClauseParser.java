package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.WhenClauseNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

/**
 * Parser para cláusulas when en gateways exclusivos.
 *
 * <h3>Gramática BNF:</h3>
 * 
 * <pre>
 * WhenClause ::= 'when' Expression '->' IDENTIFIER
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 5: Cláusulas de Control de Flujo
 *
 * <h3>Ejemplos:</h3>
 * 
 * <pre>
 * when entrada.monto > 10000 -> RequiereAprobacionGerente
 * when usuario.edad >= 18 -> ProcesoAdulto
 * when stock < 10 -> AlertaBajoStock
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * <ol>
 * <li>Consumir 'when' o 'cuando'</li>
 * <li>Parsear la expresión de condición usando ExpressionParser</li>
 * <li>Consumir '->'</li>
 * <li>Consumir IDENTIFIER (nodo destino)</li>
 * <li>Crear y retornar WhenClauseNode</li>
 * </ol>
 *
 * @see WhenClauseNode
 */
public class WhenClauseParser implements IParser<WhenClauseNode> {

    private final ExpressionParser expressionParser;

    public WhenClauseParser() {
        this.expressionParser = new ExpressionParser();
    }

    @Override
    public WhenClauseNode parse(ParserContext context) throws Parser.ParseException {

        if (!context.hasMoreTokens()) {
            throw new Parser.ParseException(
                    "Se esperaba una cláusula when");
        }

        Token whenToken = context.getCurrentToken();

        if (whenToken.getType() != TokenType.WHEN) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba when",
                            whenToken.getValue(),
                            whenToken.getLine(),
                            whenToken.getColumn()));
        }


        context.consume(TokenType.WHEN);

        if (!context.hasMoreTokens()) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba un when",
                            whenToken.getLine(),
                            whenToken.getColumn()));
        }


        Token nextToken = context.getCurrentToken();
        if (nextToken.getType() == TokenType.ARROW) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba un when",
                            nextToken.getLine(),
                            nextToken.getColumn()));
        }

        ExpressionNode condition = null;
        try {
            condition = expressionParser.parse(context);
        } catch (Parser.ParseException e) {
            throw new Parser.ParseException(
                    String.format(
                            "Error al parsear la expresión when",
                            e.getMessage()),
                    e);
        }


        if (condition == null) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba un when",
                            whenToken.getLine(),
                            whenToken.getColumn()));
        }

       
        if (!context.hasMoreTokens()) {
            throw new Parser.ParseException(
                    "Se esperaba un when");
        }

        Token arrowToken = context.getCurrentToken();
        if (arrowToken.getType() != TokenType.ARROW) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba un when",
                            arrowToken.getValue(),
                            arrowToken.getLine(),
                            arrowToken.getColumn()));
        }


        context.consume(TokenType.ARROW);


        if (!context.hasMoreTokens()) {
            throw new Parser.ParseException(
                    "Se esperaba un when");
        }

        Token targetToken = context.getCurrentToken();

        if (targetToken.getType() != TokenType.IDENTIFIER) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba un when",
                            targetToken.getValue(),
                            targetToken.getLine(),
                            targetToken.getColumn()));
        }

        String targetTask = targetToken.getValue();

        if (targetTask == null || targetTask.trim().isEmpty()) {
            throw new Parser.ParseException(
                    String.format(
                            "Se esperaba un when",
                            targetToken.getLine(),
                            targetToken.getColumn()));
        }

        context.consume(TokenType.IDENTIFIER);


        WhenClauseNode whenClauseNode = new WhenClauseNode(condition, targetTask);


        return whenClauseNode;
    }
}