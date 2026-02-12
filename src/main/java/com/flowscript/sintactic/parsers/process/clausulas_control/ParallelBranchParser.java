package com.flowscript.sintactic.parsers.process.clausulas_control;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.process.clausulas_control.ParallelBranchNode;

/**
 * Parser para ramas paralelas en gateways paralelos.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ParallelBranch ::= 'branch' '->' IDENTIFIER
 * </pre>
 */
public class ParallelBranchParser implements IParser<ParallelBranchNode> {

    @Override
    public ParallelBranchNode parse(ParserContext context) throws Parser.ParseException {

        Token branchToken = context.consume(TokenType.BRANCH);

        context.consume(TokenType.ARROW);

        Token targetToken = context.consume(TokenType.IDENTIFIER);

        return new ParallelBranchNode(branchToken, targetToken.getValue());
    }
}
