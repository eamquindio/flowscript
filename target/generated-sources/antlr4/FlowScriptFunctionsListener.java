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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#importDecl}.
	 * @param ctx the parse tree
	 */
	void enterImportDecl(FlowScriptFunctionsParser.ImportDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#importDecl}.
	 * @param ctx the parse tree
	 */
	void exitImportDecl(FlowScriptFunctionsParser.ImportDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#importJarDecl}.
	 * @param ctx the parse tree
	 */
	void enterImportJarDecl(FlowScriptFunctionsParser.ImportJarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#importJarDecl}.
	 * @param ctx the parse tree
	 */
	void exitImportJarDecl(FlowScriptFunctionsParser.ImportJarDeclContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(FlowScriptFunctionsParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(FlowScriptFunctionsParser.ParamListContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(FlowScriptFunctionsParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(FlowScriptFunctionsParser.AssignStmtContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpr(FlowScriptFunctionsParser.LogicalOrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpr(FlowScriptFunctionsParser.LogicalOrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpr(FlowScriptFunctionsParser.LogicalAndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpr(FlowScriptFunctionsParser.LogicalAndExprContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#funcCall}.
	 * @param ctx the parse tree
	 */
	void enterFuncCall(FlowScriptFunctionsParser.FuncCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#funcCall}.
	 * @param ctx the parse tree
	 */
	void exitFuncCall(FlowScriptFunctionsParser.FuncCallContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#objPair}.
	 * @param ctx the parse tree
	 */
	void enterObjPair(FlowScriptFunctionsParser.ObjPairContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#objPair}.
	 * @param ctx the parse tree
	 */
	void exitObjPair(FlowScriptFunctionsParser.ObjPairContext ctx);
}