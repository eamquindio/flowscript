package com.flowscript.sintactic.parsers.process.estructura_principal;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.process.estructura_principal.ProcessDeclarationNode;

import java.util.List;

/**
 * Parser para declaraciones de proceso.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ProcessDeclaration ::= 'process' IDENTIFIER '{' ProcessBody '}'
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)
 * Nivel 1: Estructura Principal del Proceso
 *
 * <h3>Ejemplos:</h3>
 * <pre>
 * // Proceso simple
 * process RegistroUsuario {
 *     start -> ValidarDatos
 *
 *     task ValidarDatos {
 *         action:
 *             if validar_email(entrada.email) {
 *                 go_to GuardarDB
 *             } else {
 *                 go_to FinError
 *             }
 *     }
 *
 *     task GuardarDB {
 *         action:
 *             db.insert("usuarios", entrada)
 *             go_to FinExitoso
 *     }
 *
 *     end FinExitoso
 *     end FinError
 * }
 *
 * // Proceso con gateway exclusivo
 * process AprobacionFactura {
 *     start -> ClasificarMonto
 *
 *     task ClasificarMonto {
 *         action:
 *             gateway DecisionMonto {
 *                 when entrada.monto > 10000 -> RequiereAprobacionGerente
 *                 when entrada.monto > 1000 -> RequiereAprobacionSupervisor
 *                 else -> AprobacionAutomatica
 *             }
 *     }
 *
 *     task AprobacionAutomatica {
 *         action: aprobar_automaticamente()
 *         go_to FinOK
 *     }
 *
 *     end FinOK
 * }
 *
 * // Proceso con gateway paralelo
 * process VerificacionAntecedentes {
 *     start -> Iniciar
 *
 *     gateway Iniciar parallel {
 *         branch -> VerificarCredito
 *         branch -> VerificarPenal
 *         join -> Consolidar
 *     }
 *
 *     task VerificarCredito {
 *         action: reporte_credito = http.get("api/credito/" + entrada.cedula)
 *         go_to FinCredito
 *     }
 *
 *     end FinCredito
 *
 *     task VerificarPenal {
 *         action: reporte_penal = http.get("api/penal/" + entrada.cedula)
 *         go_to FinPenal
 *     }
 *
 *     end FinPenal
 *
 *     task Consolidar {
 *         action:
 *             if reporte_credito.aprobado and reporte_penal.limpio {
 *                 go_to Exito
 *             } else {
 *                 go_to Falla
 *             }
 *     }
 *
 *     end Exito
 *     end Falla
 * }
 * </pre>
 *
 * <h3>Uso:</h3>
 * <pre>
 * ParserContext context = new ParserContext(tokens);
 * ProcessDeclarationParser parser = new ProcessDeclarationParser();
 * ProcessDeclarationNode node = parser.parse(context);
 * </pre>
 *
 * @see ProcessDeclarationNode
 * @see ProcessBodyParser
 */
public class ProcessDeclarationParser implements IParser<ProcessDeclarationNode> {

    private final ProcessBodyParser bodyParser;

    public ProcessDeclarationParser() {
        this.bodyParser = new ProcessBodyParser();
    }

    @Override
    public ProcessDeclarationNode parse(ParserContext context) throws Parser.ParseException {
        Token processToken = parseProcessKeyword(context);
        String processName = parseProcessName(context);
        parseOpenBrace(context);
        List<ASTNode> processElements = bodyParser.parse(context);
        parseCloseBrace(context);

        return new ProcessDeclarationNode(processToken, processName, processElements);
    }

    private Token parseProcessKeyword(ParserContext context) throws Parser.ParseException {
        Token token = context.getCurrentToken();

        if (!context.check(TokenType.PROCESS)) {
            throw new Parser.ParseException("Se esperaba 'process' o 'proceso'");
        }
        context.advance();

        return token;
    }

    private String parseProcessName(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.IDENTIFIER)) {
            throw new Parser.ParseException("Se esperaba un identificador después de 'process'");
        }

        String name = context.getCurrentToken().getValue();
        context.advance();

        return name;
    }

    private void parseOpenBrace(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.LEFT_BRACE)) {
            throw new Parser.ParseException("Se esperaba '{' después del nombre del proceso");
        }
        context.advance();
    }

    private void parseCloseBrace(ParserContext context) throws Parser.ParseException {
        if (!context.check(TokenType.RIGHT_BRACE)) {
            throw new Parser.ParseException("Se esperaba '}' al final del proceso");
        }
        context.advance();
    }
}
