package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.process.clausulas_control.WhenClauseNode;

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

    @Override
    public WhenClauseNode parse(ParserContext context) throws Parser.ParseException {
        Token whenToken = context.getCurrentToken();
        if (whenToken == null || whenToken.getType() != TokenType.WHEN) {
            throw new Parser.ParseException("Expected 'when'");
        }
        context.consume();

        Token current = context.getCurrentToken();
        StringBuilder expr = new StringBuilder();

        while (current != null && current.getType() != TokenType.ARROW && current.getType() != TokenType.EOF) {
            expr.append(current.getValue()).append(" ");
            context.consume();
            current = context.getCurrentToken();
        }

        if (expr.toString().trim().isEmpty()) {
            throw new Parser.ParseException("Expected condition after 'when'");
        }

        if (current == null || current.getType() != TokenType.ARROW) {
            throw new Parser.ParseException("Expected '->' after condition");
        }
        context.consume();

        Token target = context.getCurrentToken();
        if (target == null || target.getType() != TokenType.IDENTIFIER) {
            throw new Parser.ParseException("Expected identifier after '->'");
        }
        context.consume();

        ExpressionNode dummyCondition = new ExpressionNode(
                whenToken.getLine(),
                whenToken.getColumn(),
                whenToken.getPosition()) {
            @Override
            public String getNodeType() {
                return "Expression";
            }

            @Override
            public String getExpressionType() {
                return "Generic";
            }
        };

        return new WhenClauseNode(dummyCondition, target.getValue());
    }
}