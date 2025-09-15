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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#functionDecl}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDecl(FlowScriptFunctionsParser.FunctionDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#functionDecl}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDecl(FlowScriptFunctionsParser.FunctionDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#paramListOpt}.
	 * @param ctx the parse tree
	 */
	void enterParamListOpt(FlowScriptFunctionsParser.ParamListOptContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#paramListOpt}.
	 * @param ctx the parse tree
	 */
	void exitParamListOpt(FlowScriptFunctionsParser.ParamListOptContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(FlowScriptFunctionsParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(FlowScriptFunctionsParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#returnTypeOpt}.
	 * @param ctx the parse tree
	 */
	void enterReturnTypeOpt(FlowScriptFunctionsParser.ReturnTypeOptContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#returnTypeOpt}.
	 * @param ctx the parse tree
	 */
	void exitReturnTypeOpt(FlowScriptFunctionsParser.ReturnTypeOptContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#typeRef}.
	 * @param ctx the parse tree
	 */
	void enterTypeRef(FlowScriptFunctionsParser.TypeRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#typeRef}.
	 * @param ctx the parse tree
	 */
	void exitTypeRef(FlowScriptFunctionsParser.TypeRefContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#catchVar}.
	 * @param ctx the parse tree
	 */
	void enterCatchVar(FlowScriptFunctionsParser.CatchVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#catchVar}.
	 * @param ctx the parse tree
	 */
	void exitCatchVar(FlowScriptFunctionsParser.CatchVarContext ctx);
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
	 * Enter a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link FlowScriptFunctionsParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpr(FlowScriptFunctionsParser.AssignExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link FlowScriptFunctionsParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpr(FlowScriptFunctionsParser.AssignExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nonAssignExpr}
	 * labeled alternative in {@link FlowScriptFunctionsParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterNonAssignExpr(FlowScriptFunctionsParser.NonAssignExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nonAssignExpr}
	 * labeled alternative in {@link FlowScriptFunctionsParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitNonAssignExpr(FlowScriptFunctionsParser.NonAssignExprContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#eqExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqExpr(FlowScriptFunctionsParser.EqExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#eqExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqExpr(FlowScriptFunctionsParser.EqExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#relExpr}.
	 * @param ctx the parse tree
	 */
	void enterRelExpr(FlowScriptFunctionsParser.RelExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#relExpr}.
	 * @param ctx the parse tree
	 */
	void exitRelExpr(FlowScriptFunctionsParser.RelExprContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#argListOpt}.
	 * @param ctx the parse tree
	 */
	void enterArgListOpt(FlowScriptFunctionsParser.ArgListOptContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#argListOpt}.
	 * @param ctx the parse tree
	 */
	void exitArgListOpt(FlowScriptFunctionsParser.ArgListOptContext ctx);
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