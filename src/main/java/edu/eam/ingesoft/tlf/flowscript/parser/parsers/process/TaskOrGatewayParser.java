package edu.eam.ingesoft.tlf.flowscript.parser.parsers.process;

import edu.eam.ingesoft.tlf.flowscript.lexer.Token;
import edu.eam.ingesoft.tlf.flowscript.lexer.TokenType;
import edu.eam.ingesoft.tlf.flowscript.parser.IParser;
import edu.eam.ingesoft.tlf.flowscript.parser.ParserContext;
import edu.eam.ingesoft.tlf.flowscript.parser.ParserException;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.ExpressionNode;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.process.ExclusiveGatewayNode;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.process.ProcessElementNode;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.process.TaskElementNode;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.GotoStatementNode;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.StatementNode;
import edu.eam.ingesoft.tlf.flowscript.parser.parsers.expressions.ExpressionParser;
import edu.eam.ingesoft.tlf.flowscript.parser.parsers.statements.StatementParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser que distingue entre TaskElement y ExclusiveGateway.
 * Ambos empiezan con 'tarea', pero se diferencian por su contenido:
 * - TaskElement contiene statements regulares
 * - ExclusiveGateway contiene ramas condicionales (si/sino_si/sino)
 *
 * Gramática:
 * TaskElement ::= 'tarea' IDENTIFIER '{' 'accion' ':' Statement* '}'
 * ExclusiveGateway ::= 'tarea' IDENTIFIER '{' 'accion' ':' ConditionalBranch+ '}'
 * ConditionalBranch ::= ('si' | 'sino_si') '(' Expression ')' '{' 'ir_a' IDENTIFIER '}'
 *                     | 'sino' '{' 'ir_a' IDENTIFIER '}'
 */
public class TaskOrGatewayParser implements IParser<ProcessElementNode> {
    private final StatementParser statementParser;
    private final ExpressionParser expressionParser;

    public TaskOrGatewayParser() {
        this.statementParser = new StatementParser();
        this.expressionParser = new ExpressionParser();
    }

    @Override
    public ProcessElementNode parse(ParserContext context) throws ParserException {
        int startLine = context.current().getLine();
        int startColumn = context.current().getColumn();

        // Parse 'tarea'
        context.expect(TokenType.TAREA);

        // Parse task/gateway name
        Token nameToken = context.expect(TokenType.IDENTIFIER);
        String name = nameToken.getLexeme();

        // Parse '{'
        context.expect(TokenType.LEFT_BRACE);

        // Parse 'accion'
        context.expect(TokenType.ACCION);

        // Parse ':'
        context.expect(TokenType.COLON);

        // Look ahead to determine if this is a TaskElement or ExclusiveGateway
        TokenType nextType = context.current().getType();

        if (nextType == TokenType.SI || nextType == TokenType.SINO_SI || nextType == TokenType.SINO) {
            // This is an ExclusiveGateway
            return parseExclusiveGateway(context, name, startLine, startColumn);
        } else {
            // This is a TaskElement
            return parseTaskElement(context, name, startLine, startColumn);
        }
    }

    /**
     * Parsea un TaskElement regular con statements.
     */
    private TaskElementNode parseTaskElement(ParserContext context, String name, int startLine, int startColumn)
            throws ParserException {
        List<StatementNode> actions = new ArrayList<>();

        // Parse statements until '}'
        while (!context.check(TokenType.RIGHT_BRACE) && !context.isAtEnd()) {
            StatementNode statement = statementParser.parse(context);
            actions.add(statement);
        }

        // Parse '}'
        context.expect(TokenType.RIGHT_BRACE);

        TaskElementNode node = new TaskElementNode(name, actions);
        return node;
    }

    /**
     * Parsea un ExclusiveGateway con ramas condicionales.
     */
    private ExclusiveGatewayNode parseExclusiveGateway(ParserContext context, String name, int startLine, int startColumn)
            throws ParserException {
        List<ExclusiveGatewayNode.ConditionalBranch> branches = new ArrayList<>();

        // Parse conditional branches until '}'
        while (!context.check(TokenType.RIGHT_BRACE) && !context.isAtEnd()) {
            ExclusiveGatewayNode.ConditionalBranch branch = parseConditionalBranch(context);
            branches.add(branch);
        }

        // Parse '}'
        context.expect(TokenType.RIGHT_BRACE);

        // Validate at least one branch
        if (branches.isEmpty()) {
            throw new ParserException(
                "Gateway exclusivo '" + name + "' debe contener al menos una rama condicional",
                context.current()
            );
        }

        ExclusiveGatewayNode node = new ExclusiveGatewayNode(name, branches);
        return node;
    }

    /**
     * Parsea una rama condicional del gateway exclusivo.
     * ConditionalBranch ::= ('si' | 'sino_si') '(' Expression ')' '{' 'ir_a' IDENTIFIER '}'
     *                     | 'sino' '{' 'ir_a' IDENTIFIER '}'
     */
    private ExclusiveGatewayNode.ConditionalBranch parseConditionalBranch(ParserContext context)
            throws ParserException {
        TokenType branchType = context.current().getType();
        ExpressionNode condition = null;

        if (branchType == TokenType.SI || branchType == TokenType.SINO_SI) {
            // Parse 'si' or 'sino_si'
            context.consume();

            // Parse '(' Expression ')'
            context.expect(TokenType.LEFT_PAREN);
            condition = expressionParser.parse(context);
            context.expect(TokenType.RIGHT_PAREN);
        } else if (branchType == TokenType.SINO) {
            // Parse 'sino' (else branch, condition is null)
            context.consume();
        } else {
            throw new ParserException(
                "Se esperaba 'si', 'sino_si' o 'sino', pero se encontró: " + branchType,
                context.current()
            );
        }

        // Parse '{' 'ir_a' IDENTIFIER '}'
        context.expect(TokenType.LEFT_BRACE);
        context.expect(TokenType.IR_A);
        Token targetToken = context.expect(TokenType.IDENTIFIER);
        context.expect(TokenType.RIGHT_BRACE);

        String targetTaskName = targetToken.getLexeme();
        return new ExclusiveGatewayNode.ConditionalBranch(condition, targetTaskName);
    }
}
