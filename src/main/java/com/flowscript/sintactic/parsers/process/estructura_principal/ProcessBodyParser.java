package com.flowscript.sintactic.parsers.process.estructura_principal;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.parsers.process.elementos_core.*;
import com.flowscript.sintactic.parsers.process.elementos_trabajo.*;
import com.flowscript.sintactic.parsers.process.control_flujo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser para el cuerpo de un proceso.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ProcessBody ::= ProcessElement*
 * ProcessElement ::= StartElement | TaskElement | GatewayElement | EndElement
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 1: Estructura Principal del Proceso
 *
 * <h3>Responsabilidad:</h3>
 * Coordina el parseo de todos los elementos que componen un proceso BPMN.
 *
 * <h3>Nota:</h3>
 * Este parser no implementa IParser porque retorna una lista, no un nodo AST.
 *
 * @see ProcessDeclarationParser
 */
public class ProcessBodyParser {

    private final StartElementParser startParser;
    private final TaskElementParser taskParser;
    private final EndElementParser endParser;
    private final ExclusiveGatewayParser exclusiveGatewayParser;
    private final ParallelGatewayParser parallelGatewayParser;

    public ProcessBodyParser() {
        this.startParser = new StartElementParser();
        this.taskParser = new TaskElementParser();
        this.endParser = new EndElementParser();
        this.exclusiveGatewayParser = new ExclusiveGatewayParser();
        this.parallelGatewayParser = new ParallelGatewayParser();
    }

    public List<ASTNode> parse(ParserContext context) throws Parser.ParseException {
        List<ASTNode> processElements = new ArrayList<>();

        // Parsear elementos del proceso hasta encontrar '}'
        while (!context.check(TokenType.RIGHT_BRACE) && context.hasMoreTokens()) {
            ASTNode element = parseProcessElement(context);
            processElements.add(element);
        }

        return processElements;
    }

    private ASTNode parseProcessElement(ParserContext context) throws Parser.ParseException {
        TokenType currentType = context.getCurrentToken().getType();

        // Determinar qué tipo de elemento parsear según el token actual
        if (currentType == TokenType.START) {
            return startParser.parse(context);
        } else if (currentType == TokenType.TASK) {
            return taskParser.parse(context);
        } else if (currentType == TokenType.END) {
            return endParser.parse(context);
        } else if (currentType == TokenType.GATEWAY) {
            // Determinar si es gateway exclusivo o paralelo
            // Necesitamos mirar hacia adelante para ver si hay 'parallel'
            int savedPosition = context.getCurrentIndex();
            context.advance(); // saltar 'gateway'

            if (context.check(TokenType.IDENTIFIER)) {
                context.advance(); // saltar IDENTIFIER

                if (context.check(TokenType.PARALLEL)) {
                    // Es un gateway paralelo, restaurar posición y parsear
                    context.setCurrentIndex(savedPosition);
                    return parallelGatewayParser.parse(context);
                } else {
                    // Es un gateway exclusivo, restaurar posición y parsear
                    context.setCurrentIndex(savedPosition);
                    return exclusiveGatewayParser.parse(context);
                }
            } else {
                throw new Parser.ParseException("Se esperaba un identificador después de 'gateway'");
            }
        } else {
            throw new Parser.ParseException("Token inesperado en el cuerpo del proceso. Se esperaba 'start', 'task', 'gateway' o 'end'");
        }
    }
}
