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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#returnType}.
	 * @param ctx the parse tree
	 */
	void enterReturnType(FlowScriptFunctionsParser.ReturnTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#returnType}.
	 * @param ctx the parse tree
	 */
	void exitReturnType(FlowScriptFunctionsParser.ReturnTypeContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(FlowScriptFunctionsParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(FlowScriptFunctionsParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(FlowScriptFunctionsParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(FlowScriptFunctionsParser.AssignmentContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#whileLoop}.
	 * @param ctx the parse tree
	 */
	void enterWhileLoop(FlowScriptFunctionsParser.WhileLoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#whileLoop}.
	 * @param ctx the parse tree
	 */
	void exitWhileLoop(FlowScriptFunctionsParser.WhileLoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#forEachLoop}.
	 * @param ctx the parse tree
	 */
	void enterForEachLoop(FlowScriptFunctionsParser.ForEachLoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#forEachLoop}.
	 * @param ctx the parse tree
	 */
	void exitForEachLoop(FlowScriptFunctionsParser.ForEachLoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#forRangeLoop}.
	 * @param ctx the parse tree
	 */
	void enterForRangeLoop(FlowScriptFunctionsParser.ForRangeLoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#forRangeLoop}.
	 * @param ctx the parse tree
	 */
	void exitForRangeLoop(FlowScriptFunctionsParser.ForRangeLoopContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(FlowScriptFunctionsParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(FlowScriptFunctionsParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(FlowScriptFunctionsParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(FlowScriptFunctionsParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(FlowScriptFunctionsParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(FlowScriptFunctionsParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(FlowScriptFunctionsParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(FlowScriptFunctionsParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(FlowScriptFunctionsParser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(FlowScriptFunctionsParser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpr(FlowScriptFunctionsParser.MultiplicativeExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpr(FlowScriptFunctionsParser.MultiplicativeExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(FlowScriptFunctionsParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(FlowScriptFunctionsParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpr(FlowScriptFunctionsParser.PostfixExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpr(FlowScriptFunctionsParser.PostfixExprContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(FlowScriptFunctionsParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(FlowScriptFunctionsParser.PrimaryContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#numberLiteral}.
	 * @param ctx the parse tree
	 */
	void enterNumberLiteral(FlowScriptFunctionsParser.NumberLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#numberLiteral}.
	 * @param ctx the parse tree
	 */
	void exitNumberLiteral(FlowScriptFunctionsParser.NumberLiteralContext ctx);
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
}