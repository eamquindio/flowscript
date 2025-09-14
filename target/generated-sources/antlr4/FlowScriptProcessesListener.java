// Generated from FlowScriptProcesses.g4 by ANTLR 4.13.1

    package edu.eam.ingesoft.tlf;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FlowScriptProcessesParser}.
 */
public interface FlowScriptProcessesListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(FlowScriptProcessesParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(FlowScriptProcessesParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#importStmt}.
	 * @param ctx the parse tree
	 */
	void enterImportStmt(FlowScriptProcessesParser.ImportStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#importStmt}.
	 * @param ctx the parse tree
	 */
	void exitImportStmt(FlowScriptProcessesParser.ImportStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#importJarStmt}.
	 * @param ctx the parse tree
	 */
	void enterImportJarStmt(FlowScriptProcessesParser.ImportJarStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#importJarStmt}.
	 * @param ctx the parse tree
	 */
	void exitImportJarStmt(FlowScriptProcessesParser.ImportJarStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#globalVar}.
	 * @param ctx the parse tree
	 */
	void enterGlobalVar(FlowScriptProcessesParser.GlobalVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#globalVar}.
	 * @param ctx the parse tree
	 */
	void exitGlobalVar(FlowScriptProcessesParser.GlobalVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#functionDecl}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDecl(FlowScriptProcessesParser.FunctionDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#functionDecl}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDecl(FlowScriptProcessesParser.FunctionDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(FlowScriptProcessesParser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(FlowScriptProcessesParser.ParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(FlowScriptProcessesParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(FlowScriptProcessesParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(FlowScriptProcessesParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(FlowScriptProcessesParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#processDecl}.
	 * @param ctx the parse tree
	 */
	void enterProcessDecl(FlowScriptProcessesParser.ProcessDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#processDecl}.
	 * @param ctx the parse tree
	 */
	void exitProcessDecl(FlowScriptProcessesParser.ProcessDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#processElementNoStartNoEnd}.
	 * @param ctx the parse tree
	 */
	void enterProcessElementNoStartNoEnd(FlowScriptProcessesParser.ProcessElementNoStartNoEndContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#processElementNoStartNoEnd}.
	 * @param ctx the parse tree
	 */
	void exitProcessElementNoStartNoEnd(FlowScriptProcessesParser.ProcessElementNoStartNoEndContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#startDecl}.
	 * @param ctx the parse tree
	 */
	void enterStartDecl(FlowScriptProcessesParser.StartDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#startDecl}.
	 * @param ctx the parse tree
	 */
	void exitStartDecl(FlowScriptProcessesParser.StartDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#endDecl}.
	 * @param ctx the parse tree
	 */
	void enterEndDecl(FlowScriptProcessesParser.EndDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#endDecl}.
	 * @param ctx the parse tree
	 */
	void exitEndDecl(FlowScriptProcessesParser.EndDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#taskDecl}.
	 * @param ctx the parse tree
	 */
	void enterTaskDecl(FlowScriptProcessesParser.TaskDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#taskDecl}.
	 * @param ctx the parse tree
	 */
	void exitTaskDecl(FlowScriptProcessesParser.TaskDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#actionDecl}.
	 * @param ctx the parse tree
	 */
	void enterActionDecl(FlowScriptProcessesParser.ActionDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#actionDecl}.
	 * @param ctx the parse tree
	 */
	void exitActionDecl(FlowScriptProcessesParser.ActionDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayDecl}.
	 * @param ctx the parse tree
	 */
	void enterGatewayDecl(FlowScriptProcessesParser.GatewayDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayDecl}.
	 * @param ctx the parse tree
	 */
	void exitGatewayDecl(FlowScriptProcessesParser.GatewayDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#branchDecl}.
	 * @param ctx the parse tree
	 */
	void enterBranchDecl(FlowScriptProcessesParser.BranchDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#branchDecl}.
	 * @param ctx the parse tree
	 */
	void exitBranchDecl(FlowScriptProcessesParser.BranchDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#joinDecl}.
	 * @param ctx the parse tree
	 */
	void enterJoinDecl(FlowScriptProcessesParser.JoinDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#joinDecl}.
	 * @param ctx the parse tree
	 */
	void exitJoinDecl(FlowScriptProcessesParser.JoinDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#whenClause}.
	 * @param ctx the parse tree
	 */
	void enterWhenClause(FlowScriptProcessesParser.WhenClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#whenClause}.
	 * @param ctx the parse tree
	 */
	void exitWhenClause(FlowScriptProcessesParser.WhenClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void enterElseClause(FlowScriptProcessesParser.ElseClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void exitElseClause(FlowScriptProcessesParser.ElseClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(FlowScriptProcessesParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(FlowScriptProcessesParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(FlowScriptProcessesParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(FlowScriptProcessesParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#goToStmt}.
	 * @param ctx the parse tree
	 */
	void enterGoToStmt(FlowScriptProcessesParser.GoToStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#goToStmt}.
	 * @param ctx the parse tree
	 */
	void exitGoToStmt(FlowScriptProcessesParser.GoToStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(FlowScriptProcessesParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(FlowScriptProcessesParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(FlowScriptProcessesParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(FlowScriptProcessesParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#tryCatchStmt}.
	 * @param ctx the parse tree
	 */
	void enterTryCatchStmt(FlowScriptProcessesParser.TryCatchStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#tryCatchStmt}.
	 * @param ctx the parse tree
	 */
	void exitTryCatchStmt(FlowScriptProcessesParser.TryCatchStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayStmt}.
	 * @param ctx the parse tree
	 */
	void enterGatewayStmt(FlowScriptProcessesParser.GatewayStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayStmt}.
	 * @param ctx the parse tree
	 */
	void exitGatewayStmt(FlowScriptProcessesParser.GatewayStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(FlowScriptProcessesParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(FlowScriptProcessesParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(FlowScriptProcessesParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(FlowScriptProcessesParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(FlowScriptProcessesParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(FlowScriptProcessesParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#memberAccessExpr}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccessExpr(FlowScriptProcessesParser.MemberAccessExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#memberAccessExpr}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccessExpr(FlowScriptProcessesParser.MemberAccessExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(FlowScriptProcessesParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(FlowScriptProcessesParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(FlowScriptProcessesParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(FlowScriptProcessesParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void enterListLiteral(FlowScriptProcessesParser.ListLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void exitListLiteral(FlowScriptProcessesParser.ListLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#objectLiteral}.
	 * @param ctx the parse tree
	 */
	void enterObjectLiteral(FlowScriptProcessesParser.ObjectLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#objectLiteral}.
	 * @param ctx the parse tree
	 */
	void exitObjectLiteral(FlowScriptProcessesParser.ObjectLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#pair}.
	 * @param ctx the parse tree
	 */
	void enterPair(FlowScriptProcessesParser.PairContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#pair}.
	 * @param ctx the parse tree
	 */
	void exitPair(FlowScriptProcessesParser.PairContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(FlowScriptProcessesParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(FlowScriptProcessesParser.BlockContext ctx);
}