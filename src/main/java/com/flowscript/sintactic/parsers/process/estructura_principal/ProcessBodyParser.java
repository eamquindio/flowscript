package com.flowscript.sintactic.parsers.process.estructura_principal;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.parsers.process.elementos_core.*;
import com.flowscript.sintactic.parsers.process.elementos_trabajo.*;
import com.flowscript.sintactic.parsers.process.control_flujo.*;
import com.flowscript.sintactic.parsers.process.elementos_core.StartElementParser;
import com.flowscript.sintactic.parsers.process.elementos_trabajo.TaskElementParser;
import com.flowscript.sintactic.parsers.process.control_flujo.ParallelGatewayParser;
import com.flowscript.sintactic.parsers.process.elementos_core.EndElementParser;

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
        List<ASTNode> elements = new ArrayList<>();

        while (context.hasMoreTokens() && !context.check(TokenType.RIGHT_BRACE)) {
            ASTNode element = parseProcessElement(context);
            if (element != null) {
                elements.add(element);
            } else {
                break;
            }
        }

        return elements;
    }

    private ASTNode parseProcessElement(ParserContext context) throws Parser.ParseException {
        Token current = context.getCurrentToken();

        if (current == null) {
            return null;
        }

        switch (current.getType()) {
            case START:
                return startParser.parse(context);

            case TASK:
                return taskParser.parse(context);

            case END:
                return endParser.parse(context);

            case GATEWAY:
                Token typeCheckToken = context.peek(2);

                if (typeCheckToken != null && typeCheckToken.getType() == TokenType.PARALLEL) {
                    return parallelGatewayParser.parse(context);
                } else {
                    return exclusiveGatewayParser.parse(context);
                }

            default:
                throw new Parser.ParseException(
                        "Unexpected token '" + current.getValue() +
                                "' in process body at line " + current.getLine() +
                                ", column " + current.getColumn() +
                                ". Expected START, TASK, GATEWAY, or END keyword."
                );
        }
    }
}
