package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;

import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.WhenClauseNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;

/**
 * Parser para cláusulas when en gateways exclusivos.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * WhenClause ::= 'when' Expression '->' IDENTIFIER
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 5: Cláusulas de Control de Flujo
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * when entrada.monto > 10000 -> RequiereAprobacionGerente
 * when usuario.edad >= 18 -> ProcesoAdulto
 * when stock < 10 -> AlertaBajoStock
 * </pre>
 *
 * <h3>Tarea del Estudiante:</h3>
 * <ol>
 *   <li>Consumir 'when' o 'cuando'</li>
 *   <li>Parsear la expresión de condición usando ExpressionParser</li>
 *   <li>Consumir '->'</li>
 *   <li>Consumir IDENTIFIER (nodo destino)</li>
 *   <li>Crear y retornar WhenClauseNode</li>
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
        parseWhenKeyword(context);
        ExpressionNode condition = parseCondition(context);
        parseArrow(context);
        String targetNode = parseTargetNode(context);

        return new WhenClauseNode(condition, targetNode);
    }

    private void parseWhenKeyword(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.WHEN)) {
            throw new Parser.ParseException("Se esperaba 'when' o 'cuando'");
        }
        context.advance();
    }

    private ExpressionNode parseCondition(ParserContext context) throws Parser.ParseException {
        return expressionParser.parse(context);
    }

    private void parseArrow(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.ARROW)) {
            throw new Parser.ParseException("Se esperaba '->' después de la condición");
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
