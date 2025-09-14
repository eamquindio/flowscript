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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#variableDecl}.
	 * @param ctx the parse tree
	 */
	void enterVariableDecl(FlowScriptFunctionsParser.VariableDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#variableDecl}.
	 * @param ctx the parse tree
	 */
	void exitVariableDecl(FlowScriptFunctionsParser.VariableDeclContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(FlowScriptFunctionsParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(FlowScriptFunctionsParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(FlowScriptFunctionsParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(FlowScriptFunctionsParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#breakStmt}.
	 * @param ctx the parse tree
	 */
	void enterBreakStmt(FlowScriptFunctionsParser.BreakStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#breakStmt}.
	 * @param ctx the parse tree
	 */
	void exitBreakStmt(FlowScriptFunctionsParser.BreakStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#continueStmt}.
	 * @param ctx the parse tree
	 */
	void enterContinueStmt(FlowScriptFunctionsParser.ContinueStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#continueStmt}.
	 * @param ctx the parse tree
	 */
	void exitContinueStmt(FlowScriptFunctionsParser.ContinueStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(FlowScriptFunctionsParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(FlowScriptFunctionsParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#elseIfPart}.
	 * @param ctx the parse tree
	 */
	void enterElseIfPart(FlowScriptFunctionsParser.ElseIfPartContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#elseIfPart}.
	 * @param ctx the parse tree
	 */
	void exitElseIfPart(FlowScriptFunctionsParser.ElseIfPartContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#elsePart}.
	 * @param ctx the parse tree
	 */
	void enterElsePart(FlowScriptFunctionsParser.ElsePartContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#elsePart}.
	 * @param ctx the parse tree
	 */
	void exitElsePart(FlowScriptFunctionsParser.ElsePartContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(FlowScriptFunctionsParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(FlowScriptFunctionsParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#forEachStmt}.
	 * @param ctx the parse tree
	 */
	void enterForEachStmt(FlowScriptFunctionsParser.ForEachStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#forEachStmt}.
	 * @param ctx the parse tree
	 */
	void exitForEachStmt(FlowScriptFunctionsParser.ForEachStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#forRangeStmt}.
	 * @param ctx the parse tree
	 */
	void enterForRangeStmt(FlowScriptFunctionsParser.ForRangeStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#forRangeStmt}.
	 * @param ctx the parse tree
	 */
	void exitForRangeStmt(FlowScriptFunctionsParser.ForRangeStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#tryCatchStmt}.
	 * @param ctx the parse tree
	 */
	void enterTryCatchStmt(FlowScriptFunctionsParser.TryCatchStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#tryCatchStmt}.
	 * @param ctx the parse tree
	 */
	void exitTryCatchStmt(FlowScriptFunctionsParser.TryCatchStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#catchBlock}.
	 * @param ctx the parse tree
	 */
	void enterCatchBlock(FlowScriptFunctionsParser.CatchBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#catchBlock}.
	 * @param ctx the parse tree
	 */
	void exitCatchBlock(FlowScriptFunctionsParser.CatchBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#throwStmt}.
	 * @param ctx the parse tree
	 */
	void enterThrowStmt(FlowScriptFunctionsParser.ThrowStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#throwStmt}.
	 * @param ctx the parse tree
	 */
	void exitThrowStmt(FlowScriptFunctionsParser.ThrowStmtContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void enterAddExpr(FlowScriptFunctionsParser.AddExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void exitAddExpr(FlowScriptFunctionsParser.AddExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#mulExpr}.
	 * @param ctx the parse tree
	 */
	void enterMulExpr(FlowScriptFunctionsParser.MulExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#mulExpr}.
	 * @param ctx the parse tree
	 */
	void exitMulExpr(FlowScriptFunctionsParser.MulExprContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(FlowScriptFunctionsParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(FlowScriptFunctionsParser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpr(FlowScriptFunctionsParser.PrimaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpr(FlowScriptFunctionsParser.PrimaryExprContext ctx);
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
}