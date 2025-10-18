package com.flowscript.sintactic.parsers.process.estructura_principal;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.parsers.process.elementos_core.EndElementParser;
import com.flowscript.sintactic.parsers.process.elementos_core.StartElementParser;
import com.flowscript.sintactic.parsers.process.elementos_trabajo.TaskElementParser;
import com.flowscript.sintactic.parsers.process.control_flujo.ExclusiveGatewayParser;
import com.flowscript.sintactic.parsers.process.control_flujo.ParallelGatewayParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser para el cuerpo de un proceso.
 *
 * <pre>
 * ProcessBody      ::= ProcessElement*
 * ProcessElement   ::= StartElement | TaskElement | GatewayElement | EndElement
 * StartElement     ::= 'start' '->' IDENTIFIER
 * TaskElement      ::= 'task' IDENTIFIER '{' 'action:' Statement* '}'
 * EndElement       ::= 'end' IDENTIFIER
 * GatewayElement   ::= ExclusiveGateway | ParallelGateway
 * ExclusiveGateway ::= 'gateway' IDENTIFIER '{' WhenClause+ ElseClause? '}'
 * ParallelGateway  ::= 'gateway' IDENTIFIER 'parallel' '{' Branch* Join '}'
 * </pre>
 *
 * Se detiene cuando encuentra la llave de cierre '}' del bloque del proceso
 * (esa llave la consume el parser que invoca a este cuerpo, típicamente ProcessDeclarationParser).
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

    /**
     * Parsea una lista de elementos del proceso hasta ver '}' o EOF.
     * No consume la '}' final; solo se detiene frente a ella.
     */
    public List<ASTNode> parse(ParserContext context) throws Parser.ParseException {
        List<ASTNode> elements = new ArrayList<>();

        while (context.hasMoreTokens()) {
            Token current = context.getCurrentToken();
            if (current == null) break;

            // Si llegamos a la '}' que cierra el bloque del proceso, devolvemos lo acumulado.
            if (isRBrace(current)) {
                break;
            }

            // Parsear un elemento de proceso según el token actual.
            ASTNode element = parseProcessElement(context);
            elements.add(element);
        }

        return elements;
    }

    /**
     * Decide qué tipo de elemento de proceso viene y delega al parser correspondiente.
     */
    private ASTNode parseProcessElement(ParserContext context) throws Parser.ParseException {
        Token t = context.getCurrentToken();
        if (t == null) {
            throw new Parser.ParseException("Se esperaba un elemento de proceso, pero no hay más tokens.");
        }

        TokenType type = t.getType();
        switch (type) {
            case START:
                // start -> Label
                return startParser.parse(context);

            case TASK:
                // task Nombre { action: ... }
                return taskParser.parse(context);

            case END:
                // end Nombre
                return endParser.parse(context);

            case GATEWAY:
                // gateway Nombre [parallel] { ... }
                // Decidimos si es paralelo mirando hacia adelante:
                // pos 0: 'gateway', pos 1: IDENTIFIER, pos 2: 'parallel' (si es paralelo)
                Token afterName = context.peek(2);
                boolean isParallel = afterName != null &&
                        (afterName.getType() == TokenType.PARALLEL || "parallel".equals(afterName.getValue()));

                if (isParallel) {
                    return parallelGatewayParser.parse(context);
                } else {
                    return exclusiveGatewayParser.parse(context);
                }

            default:
                // Mensaje claro si vino algo inesperado dentro del proceso.
                throw new Parser.ParseException(
                        "Se esperaba 'start', 'task', 'gateway' o 'end', pero se encontró '" +
                                t.getValue() + "' en línea " + t.getLine() + ", columna " + t.getColumn());
        }
    }

    // -------------------------
    // Utilidad local
    // -------------------------

    private static boolean isRBrace(Token t) {
        if (t == null) return false;
        // Soporta tanto por tipo como por lexema literal, por si el lexer no seteó el tipo exacto
        return t.getType() == TokenType.RIGHT_BRACE || "}".equals(t.getValue());
    }
}
