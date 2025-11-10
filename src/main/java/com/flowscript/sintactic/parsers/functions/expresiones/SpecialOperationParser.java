package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.*;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;

import java.util.List;

/**
 * Parser para operaciones especiales del lenguaje:
 * - Database: db.ejecutar(...), db.consultar(...)
 * - HTTP: http.get(...), http.post(...), http.delete(...)
 *
 * Estas son construcciones especiales integradas en el lenguaje.
 *
 * Note: This parser does not implement IParser because it uses a special
 * tryParse() method that returns null when the pattern doesn't match.
 */
public class SpecialOperationParser {

    private final ExpressionParser expressionParser;

    public SpecialOperationParser() {
        this.expressionParser = new ExpressionParser();
    }

    /**
     * Attempts to parse a special operation.
     * Returns null if this is not a special operation.
     *
     * Expected patterns:
     * - db.ejecutar(query, params)
     * - db.consultar(query, params)
     * - http.get(url) or http.get(url, headers)
     * - http.post(url, body) or http.post(url, body, headers)
     * - http.delete(url) or http.delete(url, headers)
     */
    public ExpressionNode tryParse(ParserContext context, IdentifierNode baseIdentifier) throws Parser.ParseException {
        String baseName = baseIdentifier.getName();

        // Check if base is "db" or "http"
        if (!baseName.equals("db") && !baseName.equals("http")) {
            return null; // Not a special operation
        }

        // Save position in case we need to backtrack
        int savedPosition = context.getCurrentIndex();

        try {
            // Expect DOT
            if (context.getCurrentToken() == null ||
                context.getCurrentToken().getType() != TokenType.DOT) {
                return null; // Not a special operation
            }

            Token dotToken = context.consume(); // consume '.'

            // Expect operation name (identifier)
            if (context.getCurrentToken() == null ||
                context.getCurrentToken().getType() != TokenType.IDENTIFIER) {
                // Backtrack - this will be handled as regular property access
                context.setCurrentIndex(savedPosition);
                return null;
            }

            Token operationToken = context.consume();
            String operation = operationToken.getValue();

            // Expect LEFT_PAREN
            if (context.getCurrentToken() == null ||
                context.getCurrentToken().getType() != TokenType.LEFT_PAREN) {
                // Backtrack - not a function call
                context.setCurrentIndex(savedPosition);
                return null;
            }

            context.consume(); // consume '('

            // Now check if it's a valid special operation and parse arguments
            if (baseName.equals("db")) {
                return parseDbOperation(baseIdentifier.getToken(), operation, context);
            } else if (baseName.equals("http")) {
                return parseHttpOperation(baseIdentifier.getToken(), operation, context);
            }

            // Should never reach here
            context.setCurrentIndex(savedPosition);
            return null;

        } catch (Exception e) {
            // If any error occurs, backtrack
            context.setCurrentIndex(savedPosition);
            return null;
        }
    }

    /**
     * Parses database operations: ejecutar, consultar
     */
    private ExpressionNode parseDbOperation(Token dbToken, String operation, ParserContext context)
            throws Parser.ParseException {

        if (operation.equals("ejecutar")) {
            // db.ejecutar(query, params)
            ExpressionNode query = expressionParser.parse(context);
            context.consume(TokenType.COMMA);
            ExpressionNode params = expressionParser.parse(context);
            context.consume(TokenType.RIGHT_PAREN);
            return new DbExecuteNode(dbToken, query, params);

        } else if (operation.equals("consultar")) {
            // db.consultar(query, params)
            ExpressionNode query = expressionParser.parse(context);
            context.consume(TokenType.COMMA);
            ExpressionNode params = expressionParser.parse(context);
            context.consume(TokenType.RIGHT_PAREN);
            return new DbQueryNode(dbToken, query, params);

        } else {
            throw new Parser.ParseException(
                "Unknown database operation: db." + operation +
                " (expected 'ejecutar' or 'consultar') at line " + dbToken.getLine()
            );
        }
    }

    /**
     * Parses HTTP operations: get, post, delete
     */
    private ExpressionNode parseHttpOperation(Token httpToken, String operation, ParserContext context)
            throws Parser.ParseException {

        if (operation.equals("get")) {
            // http.get(url) or http.get(url, headers)
            ExpressionNode url = expressionParser.parse(context);

            // Check for optional headers
            ExpressionNode headers = null;
            if (context.getCurrentToken() != null &&
                context.getCurrentToken().getType() == TokenType.COMMA) {
                context.consume(TokenType.COMMA);
                headers = expressionParser.parse(context);
            }

            context.consume(TokenType.RIGHT_PAREN);
            return new HttpGetNode(httpToken, url, headers);

        } else if (operation.equals("post")) {
            // http.post(url, body) or http.post(url, body, headers)
            ExpressionNode url = expressionParser.parse(context);
            context.consume(TokenType.COMMA);
            ExpressionNode body = expressionParser.parse(context);

            // Check for optional headers
            ExpressionNode headers = null;
            if (context.getCurrentToken() != null &&
                context.getCurrentToken().getType() == TokenType.COMMA) {
                context.consume(TokenType.COMMA);
                headers = expressionParser.parse(context);
            }

            context.consume(TokenType.RIGHT_PAREN);
            return new HttpPostNode(httpToken, url, body, headers);

        } else if (operation.equals("delete")) {
            // http.delete(url) or http.delete(url, headers)
            ExpressionNode url = expressionParser.parse(context);

            // Check for optional headers
            ExpressionNode headers = null;
            if (context.getCurrentToken() != null &&
                context.getCurrentToken().getType() == TokenType.COMMA) {
                context.consume(TokenType.COMMA);
                headers = expressionParser.parse(context);
            }

            context.consume(TokenType.RIGHT_PAREN);
            return new HttpDeleteNode(httpToken, url, headers);

        } else {
            throw new Parser.ParseException(
                "Unknown HTTP operation: http." + operation +
                " (expected 'get', 'post', or 'delete') at line " + httpToken.getLine()
            );
        }
    }
}
