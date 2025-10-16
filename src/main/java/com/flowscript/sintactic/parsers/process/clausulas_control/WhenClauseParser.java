package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
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
        Token startToken = context.getCurrentToken();
        if (startToken == null ||
            !(startToken.getValue().equalsIgnoreCase("when") )) {
            throw new Parser.ParseException("No hay un WHEN");
        }
        context.consume(); 

        ExpressionNode expresionCondicional = (ExpressionNode) expressionParser.parse(context);
        if (expresionCondicional == null) {
            throw new Parser.ParseException("No es una condicion valida");
        }

        Token arrowToken = context.getCurrentToken();
        if (arrowToken == null || !arrowToken.getValue().equals("->")) {
            throw new Parser.ParseException("No hay una -> después de la condicion");
        }
        context.consume(); 

        Token identifierToken = context.getCurrentToken();
        if (identifierToken == null || identifierToken.getType() != TokenType.IDENTIFIER) {
            throw new Parser.ParseException("No es una expesion valida");
        }
        String targetTask = identifierToken.getValue();
        context.consume(); 

        return new WhenClauseNode(expresionCondicional, targetTask);
    }
}