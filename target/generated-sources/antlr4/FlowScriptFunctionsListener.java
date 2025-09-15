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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#simpleType}.
	 * @param ctx the parse tree
	 */
	void enterSimpleType(FlowScriptFunctionsParser.SimpleTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#simpleType}.
	 * @param ctx the parse tree
	 */
	void exitSimpleType(FlowScriptFunctionsParser.SimpleTypeContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void enterLoopStatement(FlowScriptFunctionsParser.LoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void exitLoopStatement(FlowScriptFunctionsParser.LoopStatementContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void enterLvalue(FlowScriptFunctionsParser.LvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void exitLvalue(FlowScriptFunctionsParser.LvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(FlowScriptFunctionsParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(FlowScriptFunctionsParser.AssignmentStatementContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#logicOr}.
	 * @param ctx the parse tree
	 */
	void enterLogicOr(FlowScriptFunctionsParser.LogicOrContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#logicOr}.
	 * @param ctx the parse tree
	 */
	void exitLogicOr(FlowScriptFunctionsParser.LogicOrContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#logicAnd}.
	 * @param ctx the parse tree
	 */
	void enterLogicAnd(FlowScriptFunctionsParser.LogicAndContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#logicAnd}.
	 * @param ctx the parse tree
	 */
	void exitLogicAnd(FlowScriptFunctionsParser.LogicAndContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#equality}.
	 * @param ctx the parse tree
	 */
	void enterEquality(FlowScriptFunctionsParser.EqualityContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#equality}.
	 * @param ctx the parse tree
	 */
	void exitEquality(FlowScriptFunctionsParser.EqualityContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(FlowScriptFunctionsParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(FlowScriptFunctionsParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#addition}.
	 * @param ctx the parse tree
	 */
	void enterAddition(FlowScriptFunctionsParser.AdditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#addition}.
	 * @param ctx the parse tree
	 */
	void exitAddition(FlowScriptFunctionsParser.AdditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#multiplication}.
	 * @param ctx the parse tree
	 */
	void enterMultiplication(FlowScriptFunctionsParser.MultiplicationContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#multiplication}.
	 * @param ctx the parse tree
	 */
	void exitMultiplication(FlowScriptFunctionsParser.MultiplicationContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#unary}.
	 * @param ctx the parse tree
	 */
	void enterUnary(FlowScriptFunctionsParser.UnaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#unary}.
	 * @param ctx the parse tree
	 */
	void exitUnary(FlowScriptFunctionsParser.UnaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#memberExpr}.
	 * @param ctx the parse tree
	 */
	void enterMemberExpr(FlowScriptFunctionsParser.MemberExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#memberExpr}.
	 * @param ctx the parse tree
	 */
	void exitMemberExpr(FlowScriptFunctionsParser.MemberExprContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(FlowScriptFunctionsParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(FlowScriptFunctionsParser.FunctionCallContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#objectEntry}.
	 * @param ctx the parse tree
	 */
	void enterObjectEntry(FlowScriptFunctionsParser.ObjectEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#objectEntry}.
	 * @param ctx the parse tree
	 */
	void exitObjectEntry(FlowScriptFunctionsParser.ObjectEntryContext ctx);
}