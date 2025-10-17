package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import java.util.ArrayList;
import java.util.List;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser.ParseException;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.FunctionDeclarationNode;
import com.flowscript.sintactic.ast.functions.tipos_parametros.ParameterNode;
import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;
import com.flowscript.sintactic.parsers.functions.control_ejecucion.BlockParser;
import com.flowscript.sintactic.parsers.functions.tipos_parametros.ParameterListParser;
import com.flowscript.sintactic.parsers.functions.tipos_parametros.TypeParser;

/**
 * Parser para declaraciones de función en FlowScript.
 *
 * <h3>Gramática BNF:</h3>
 * 
 * <pre>
 * FunctionDeclaration ::= 'function' IDENTIFIER '(' ParameterList? ')' ( '->' Type )? Block
 * </pre>
 *
 * <h3>Ejemplos:</h3>
 * 
 * <pre>
 * // Función sin parámetros ni retorno
 * function saludar() {
 *     imprimir("Hola mundo")
 * }
 *
 * // Función con parámetros y retorno
 * function sumar(a: entero, b: entero) -> entero {
 *     return a + b
 * }
 *
 * // Función recursiva
 * function factorial(n: entero) -> entero {
 *     if n <= 1 {
 *         return 1
 *     }
 *     return n * factorial(n - 1)
 * }
 * </pre>
 *
 * @see FunctionDeclarationNode
 */
public class FunctionDeclarationParser implements IParser<FunctionDeclarationNode> {
  private static final ParameterListParser PARAMETER_LIST_PARSER = new ParameterListParser();
  private static final TypeParser TYPE_PARSER = new TypeParser();
  private static final BlockParser BLOCK_PARSER = new BlockParser();

  @Override
  public FunctionDeclarationNode parse(ParserContext context) throws ParseException {
    Token functionToken = context.consume(TokenType.FUNCTION);
    String name = context.consume(TokenType.IDENTIFIER).getValue();

    context.consume(TokenType.LEFT_PAREN);
    List<ParameterNode> parameters = context.check(TokenType.RIGHT_PAREN)
        ? new ArrayList<>()
        : PARAMETER_LIST_PARSER.parse(context);
    context.consume(TokenType.RIGHT_PAREN);

    TypeNode returnType = null;
    if (context.check(TokenType.ARROW)) {
      context.consume(TokenType.ARROW);
      returnType = TYPE_PARSER.parse(context);
    }

    BlockNode body = BLOCK_PARSER.parse(context);

    return new FunctionDeclarationNode(functionToken, name, parameters, returnType, body);
  }
}