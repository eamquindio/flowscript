// Generated from FlowScriptFunctions.g4 by ANTLR 4.13.1

package edu.eam.ingesoft.tlf;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FlowScriptFunctionsParser}.
 */
public interface FlowScriptFunctionsListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#functionProgram}.
	 * @param ctx the parse tree
	 */
	void enterFunctionProgram(FlowScriptFunctionsParser.FunctionProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#functionProgram}.
	 * @param ctx the parse tree
	 */
	void exitFunctionProgram(FlowScriptFunctionsParser.FunctionProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(FlowScriptFunctionsParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(FlowScriptFunctionsParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#functionName}.
	 * @param ctx the parse tree
	 */
	void enterFunctionName(FlowScriptFunctionsParser.FunctionNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#functionName}.
	 * @param ctx the parse tree
	 */
	void exitFunctionName(FlowScriptFunctionsParser.FunctionNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(FlowScriptFunctionsParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(FlowScriptFunctionsParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(FlowScriptFunctionsParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(FlowScriptFunctionsParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(FlowScriptFunctionsParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(FlowScriptFunctionsParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(FlowScriptFunctionsParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(FlowScriptFunctionsParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(FlowScriptFunctionsParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(FlowScriptFunctionsParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#variableAssignment}.
	 * @param ctx the parse tree
	 */
	void enterVariableAssignment(FlowScriptFunctionsParser.VariableAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#variableAssignment}.
	 * @param ctx the parse tree
	 */
	void exitVariableAssignment(FlowScriptFunctionsParser.VariableAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#leftHandSide}.
	 * @param ctx the parse tree
	 */
	void enterLeftHandSide(FlowScriptFunctionsParser.LeftHandSideContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#leftHandSide}.
	 * @param ctx the parse tree
	 */
	void exitLeftHandSide(FlowScriptFunctionsParser.LeftHandSideContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#memberAccess}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccess(FlowScriptFunctionsParser.MemberAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#memberAccess}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccess(FlowScriptFunctionsParser.MemberAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#indexAccess}.
	 * @param ctx the parse tree
	 */
	void enterIndexAccess(FlowScriptFunctionsParser.IndexAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#indexAccess}.
	 * @param ctx the parse tree
	 */
	void exitIndexAccess(FlowScriptFunctionsParser.IndexAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(FlowScriptFunctionsParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(FlowScriptFunctionsParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#elseIfClause}.
	 * @param ctx the parse tree
	 */
	void enterElseIfClause(FlowScriptFunctionsParser.ElseIfClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#elseIfClause}.
	 * @param ctx the parse tree
	 */
	void exitElseIfClause(FlowScriptFunctionsParser.ElseIfClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(FlowScriptFunctionsParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(FlowScriptFunctionsParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#forEachStatement}.
	 * @param ctx the parse tree
	 */
	void enterForEachStatement(FlowScriptFunctionsParser.ForEachStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#forEachStatement}.
	 * @param ctx the parse tree
	 */
	void exitForEachStatement(FlowScriptFunctionsParser.ForEachStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#forRangeStatement}.
	 * @param ctx the parse tree
	 */
	void enterForRangeStatement(FlowScriptFunctionsParser.ForRangeStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#forRangeStatement}.
	 * @param ctx the parse tree
	 */
	void exitForRangeStatement(FlowScriptFunctionsParser.ForRangeStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(FlowScriptFunctionsParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(FlowScriptFunctionsParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(FlowScriptFunctionsParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(FlowScriptFunctionsParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(FlowScriptFunctionsParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(FlowScriptFunctionsParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#tryCatchStatement}.
	 * @param ctx the parse tree
	 */
	void enterTryCatchStatement(FlowScriptFunctionsParser.TryCatchStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#tryCatchStatement}.
	 * @param ctx the parse tree
	 */
	void exitTryCatchStatement(FlowScriptFunctionsParser.TryCatchStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#catchClause}.
	 * @param ctx the parse tree
	 */
	void enterCatchClause(FlowScriptFunctionsParser.CatchClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#catchClause}.
	 * @param ctx the parse tree
	 */
	void exitCatchClause(FlowScriptFunctionsParser.CatchClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#throwStatement}.
	 * @param ctx the parse tree
	 */
	void enterThrowStatement(FlowScriptFunctionsParser.ThrowStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#throwStatement}.
	 * @param ctx the parse tree
	 */
	void exitThrowStatement(FlowScriptFunctionsParser.ThrowStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(FlowScriptFunctionsParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(FlowScriptFunctionsParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(FlowScriptFunctionsParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(FlowScriptFunctionsParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#orExpression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpression(FlowScriptFunctionsParser.OrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#orExpression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpression(FlowScriptFunctionsParser.OrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(FlowScriptFunctionsParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(FlowScriptFunctionsParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(FlowScriptFunctionsParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(FlowScriptFunctionsParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(FlowScriptFunctionsParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(FlowScriptFunctionsParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(FlowScriptFunctionsParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(FlowScriptFunctionsParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(FlowScriptFunctionsParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(FlowScriptFunctionsParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(FlowScriptFunctionsParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(FlowScriptFunctionsParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpression(FlowScriptFunctionsParser.PostfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpression(FlowScriptFunctionsParser.PostfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#postfixOp}.
	 * @param ctx the parse tree
	 */
	void enterPostfixOp(FlowScriptFunctionsParser.PostfixOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#postfixOp}.
	 * @param ctx the parse tree
	 */
	void exitPostfixOp(FlowScriptFunctionsParser.PostfixOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpression(FlowScriptFunctionsParser.PrimaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpression(FlowScriptFunctionsParser.PrimaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(FlowScriptFunctionsParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(FlowScriptFunctionsParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(FlowScriptFunctionsParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(FlowScriptFunctionsParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(FlowScriptFunctionsParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(FlowScriptFunctionsParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#decimalLiteral}.
	 * @param ctx the parse tree
	 */
	void enterDecimalLiteral(FlowScriptFunctionsParser.DecimalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#decimalLiteral}.
	 * @param ctx the parse tree
	 */
	void exitDecimalLiteral(FlowScriptFunctionsParser.DecimalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(FlowScriptFunctionsParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(FlowScriptFunctionsParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#nullLiteral}.
	 * @param ctx the parse tree
	 */
	void enterNullLiteral(FlowScriptFunctionsParser.NullLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#nullLiteral}.
	 * @param ctx the parse tree
	 */
	void exitNullLiteral(FlowScriptFunctionsParser.NullLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(FlowScriptFunctionsParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(FlowScriptFunctionsParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void enterListLiteral(FlowScriptFunctionsParser.ListLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void exitListLiteral(FlowScriptFunctionsParser.ListLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#objectLiteral}.
	 * @param ctx the parse tree
	 */
	void enterObjectLiteral(FlowScriptFunctionsParser.ObjectLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#objectLiteral}.
	 * @param ctx the parse tree
	 */
	void exitObjectLiteral(FlowScriptFunctionsParser.ObjectLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#objectPair}.
	 * @param ctx the parse tree
	 */
	void enterObjectPair(FlowScriptFunctionsParser.ObjectPairContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#objectPair}.
	 * @param ctx the parse tree
	 */
	void exitObjectPair(FlowScriptFunctionsParser.ObjectPairContext ctx);
}