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
 * 
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
    return null;
  }

  private ASTNode parseProcessElement(ParserContext context) throws Parser.ParseException {
    return null;
  }
}
