package edu.eam.ingesoft.tlf.flowscript.parser.parsers.declarations;

import edu.eam.ingesoft.tlf.flowscript.lexer.Token;
import edu.eam.ingesoft.tlf.flowscript.lexer.TokenType;
import edu.eam.ingesoft.tlf.flowscript.parser.IParser;
import edu.eam.ingesoft.tlf.flowscript.parser.ParserContext;
import edu.eam.ingesoft.tlf.flowscript.parser.ParserException;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations.ProcessDeclarationNode;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.process.ProcessElementNode;
import edu.eam.ingesoft.tlf.flowscript.parser.parsers.process.*;
import java.util.*;

/**
 * Parser para declaraciones de procesos.
 *
 * Gramática:
 * ProcessDeclaration ::= 'proceso' IDENTIFIER '{' ProcessBody '}'
 * ProcessBody ::= ProcessElement+
 *
 * Ejemplo:
 * proceso ProcesarPedido {
 *     inicio -> ValidarPedido
 *     tarea ValidarPedido {
 *         accion:
 *             validar_datos()
 *     }
 *     fin CompletarPedido
 * }
 */
public class ProcessDeclarationParser implements IParser<ProcessDeclarationNode> {
    private final StartElementParser startElementParser;
    private final TaskOrGatewayParser taskOrGatewayParser;
    private final ParallelGatewayParser parallelGatewayParser;
    private final EndElementParser endElementParser;

    public ProcessDeclarationParser() {
        this.startElementParser = new StartElementParser();
        this.taskOrGatewayParser = new TaskOrGatewayParser();
        this.parallelGatewayParser = new ParallelGatewayParser();
        this.endElementParser = new EndElementParser();
    }

    @Override
    public ProcessDeclarationNode parse(ParserContext context) throws ParserException {
        int startLine = context.current().getLine();
        int startColumn = context.current().getColumn();

        // Parse 'proceso'
        context.expect(TokenType.PROCESO);

        // Parse process name
        Token nameToken = context.expect(TokenType.IDENTIFIER);
        String processName = nameToken.getLexeme();

        // Parse '{'
        context.expect(TokenType.LEFT_BRACE);

        // Parse process body (ProcessElement+)
        List<ProcessElementNode> elements = new ArrayList<>();

        while (!context.check(TokenType.RIGHT_BRACE) && !context.isAtEnd()) {
            ProcessElementNode element = parseProcessElement(context);
            elements.add(element);
        }

        // Parse '}'
        context.expect(TokenType.RIGHT_BRACE);

        // Validate that at least one element was parsed
        if (elements.isEmpty()) {
            throw new ParserException(
                "Proceso '" + processName + "' debe contener al menos un elemento",
                context.current()
            );
        }

        ProcessDeclarationNode processNode = new ProcessDeclarationNode(processName, elements);
        return processNode;
    }

    /**
     * Parsea un elemento de proceso según el tipo.
     */
    private ProcessElementNode parseProcessElement(ParserContext context) throws ParserException {
        TokenType currentType = context.current().getType();

        return switch (currentType) {
            case INICIO -> startElementParser.parse(context);
            case TAREA -> taskOrGatewayParser.parse(context);
            case GATEWAY -> parallelGatewayParser.parse(context);
            case FIN -> endElementParser.parse(context);
            default -> throw new ParserException(
                "Se esperaba un elemento de proceso (inicio, tarea, gateway, fin), pero se encontró: " + currentType,
                context.current()
            );
        };
    }
}
