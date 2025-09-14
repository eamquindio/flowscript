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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#importDecl}.
	 * @param ctx the parse tree
	 */
	void enterImportDecl(FlowScriptProcessesParser.ImportDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#importDecl}.
	 * @param ctx the parse tree
	 */
	void exitImportDecl(FlowScriptProcessesParser.ImportDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#importJarDecl}.
	 * @param ctx the parse tree
	 */
	void enterImportJarDecl(FlowScriptProcessesParser.ImportJarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#importJarDecl}.
	 * @param ctx the parse tree
	 */
	void exitImportJarDecl(FlowScriptProcessesParser.ImportJarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#globalVarDecl}.
	 * @param ctx the parse tree
	 */
	void enterGlobalVarDecl(FlowScriptProcessesParser.GlobalVarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#globalVarDecl}.
	 * @param ctx the parse tree
	 */
	void exitGlobalVarDecl(FlowScriptProcessesParser.GlobalVarDeclContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#paramListOpt}.
	 * @param ctx the parse tree
	 */
	void enterParamListOpt(FlowScriptProcessesParser.ParamListOptContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#paramListOpt}.
	 * @param ctx the parse tree
	 */
	void exitParamListOpt(FlowScriptProcessesParser.ParamListOptContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#returnTypeOpt}.
	 * @param ctx the parse tree
	 */
	void enterReturnTypeOpt(FlowScriptProcessesParser.ReturnTypeOptContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#returnTypeOpt}.
	 * @param ctx the parse tree
	 */
	void exitReturnTypeOpt(FlowScriptProcessesParser.ReturnTypeOptContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#typeRef}.
	 * @param ctx the parse tree
	 */
	void enterTypeRef(FlowScriptProcessesParser.TypeRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#typeRef}.
	 * @param ctx the parse tree
	 */
	void exitTypeRef(FlowScriptProcessesParser.TypeRefContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#processInner}.
	 * @param ctx the parse tree
	 */
	void enterProcessInner(FlowScriptProcessesParser.ProcessInnerContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#processInner}.
	 * @param ctx the parse tree
	 */
	void exitProcessInner(FlowScriptProcessesParser.ProcessInnerContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#startNode}.
	 * @param ctx the parse tree
	 */
	void enterStartNode(FlowScriptProcessesParser.StartNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#startNode}.
	 * @param ctx the parse tree
	 */
	void exitStartNode(FlowScriptProcessesParser.StartNodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#endNode}.
	 * @param ctx the parse tree
	 */
	void enterEndNode(FlowScriptProcessesParser.EndNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#endNode}.
	 * @param ctx the parse tree
	 */
	void exitEndNode(FlowScriptProcessesParser.EndNodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#taskNode}.
	 * @param ctx the parse tree
	 */
	void enterTaskNode(FlowScriptProcessesParser.TaskNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#taskNode}.
	 * @param ctx the parse tree
	 */
	void exitTaskNode(FlowScriptProcessesParser.TaskNodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayNode}.
	 * @param ctx the parse tree
	 */
	void enterGatewayNode(FlowScriptProcessesParser.GatewayNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayNode}.
	 * @param ctx the parse tree
	 */
	void exitGatewayNode(FlowScriptProcessesParser.GatewayNodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#exclusiveGatewayNode}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveGatewayNode(FlowScriptProcessesParser.ExclusiveGatewayNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#exclusiveGatewayNode}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveGatewayNode(FlowScriptProcessesParser.ExclusiveGatewayNodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#parallelGatewayNode}.
	 * @param ctx the parse tree
	 */
	void enterParallelGatewayNode(FlowScriptProcessesParser.ParallelGatewayNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#parallelGatewayNode}.
	 * @param ctx the parse tree
	 */
	void exitParallelGatewayNode(FlowScriptProcessesParser.ParallelGatewayNodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayWhen}.
	 * @param ctx the parse tree
	 */
	void enterGatewayWhen(FlowScriptProcessesParser.GatewayWhenContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayWhen}.
	 * @param ctx the parse tree
	 */
	void exitGatewayWhen(FlowScriptProcessesParser.GatewayWhenContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayElse}.
	 * @param ctx the parse tree
	 */
	void enterGatewayElse(FlowScriptProcessesParser.GatewayElseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayElse}.
	 * @param ctx the parse tree
	 */
	void exitGatewayElse(FlowScriptProcessesParser.GatewayElseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayBranch}.
	 * @param ctx the parse tree
	 */
	void enterGatewayBranch(FlowScriptProcessesParser.GatewayBranchContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayBranch}.
	 * @param ctx the parse tree
	 */
	void exitGatewayBranch(FlowScriptProcessesParser.GatewayBranchContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayJoin}.
	 * @param ctx the parse tree
	 */
	void enterGatewayJoin(FlowScriptProcessesParser.GatewayJoinContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayJoin}.
	 * @param ctx the parse tree
	 */
	void exitGatewayJoin(FlowScriptProcessesParser.GatewayJoinContext ctx);
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
	/**
	 * Enter a parse tree produced by the {@code assignStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(FlowScriptProcessesParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(FlowScriptProcessesParser.AssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code gotoStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterGotoStmt(FlowScriptProcessesParser.GotoStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gotoStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitGotoStmt(FlowScriptProcessesParser.GotoStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(FlowScriptProcessesParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(FlowScriptProcessesParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(FlowScriptProcessesParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(FlowScriptProcessesParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forEachStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterForEachStmt(FlowScriptProcessesParser.ForEachStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forEachStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitForEachStmt(FlowScriptProcessesParser.ForEachStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forRangeStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterForRangeStmt(FlowScriptProcessesParser.ForRangeStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forRangeStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitForRangeStmt(FlowScriptProcessesParser.ForRangeStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tryCatchStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterTryCatchStmt(FlowScriptProcessesParser.TryCatchStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tryCatchStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitTryCatchStmt(FlowScriptProcessesParser.TryCatchStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code throwStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterThrowStmt(FlowScriptProcessesParser.ThrowStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code throwStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitThrowStmt(FlowScriptProcessesParser.ThrowStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(FlowScriptProcessesParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(FlowScriptProcessesParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code gatewayStmtInAction}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterGatewayStmtInAction(FlowScriptProcessesParser.GatewayStmtInActionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code gatewayStmtInAction}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitGatewayStmtInAction(FlowScriptProcessesParser.GatewayStmtInActionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStmt(FlowScriptProcessesParser.BlockStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStmt(FlowScriptProcessesParser.BlockStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(FlowScriptProcessesParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link FlowScriptProcessesParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(FlowScriptProcessesParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void enterLvalue(FlowScriptProcessesParser.LvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void exitLvalue(FlowScriptProcessesParser.LvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#goToStatement}.
	 * @param ctx the parse tree
	 */
	void enterGoToStatement(FlowScriptProcessesParser.GoToStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#goToStatement}.
	 * @param ctx the parse tree
	 */
	void exitGoToStatement(FlowScriptProcessesParser.GoToStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(FlowScriptProcessesParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(FlowScriptProcessesParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(FlowScriptProcessesParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(FlowScriptProcessesParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#forEachStatement}.
	 * @param ctx the parse tree
	 */
	void enterForEachStatement(FlowScriptProcessesParser.ForEachStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#forEachStatement}.
	 * @param ctx the parse tree
	 */
	void exitForEachStatement(FlowScriptProcessesParser.ForEachStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#forRangeStatement}.
	 * @param ctx the parse tree
	 */
	void enterForRangeStatement(FlowScriptProcessesParser.ForRangeStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#forRangeStatement}.
	 * @param ctx the parse tree
	 */
	void exitForRangeStatement(FlowScriptProcessesParser.ForRangeStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#tryCatchStatement}.
	 * @param ctx the parse tree
	 */
	void enterTryCatchStatement(FlowScriptProcessesParser.TryCatchStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#tryCatchStatement}.
	 * @param ctx the parse tree
	 */
	void exitTryCatchStatement(FlowScriptProcessesParser.TryCatchStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#throwStatement}.
	 * @param ctx the parse tree
	 */
	void enterThrowStatement(FlowScriptProcessesParser.ThrowStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#throwStatement}.
	 * @param ctx the parse tree
	 */
	void exitThrowStatement(FlowScriptProcessesParser.ThrowStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(FlowScriptProcessesParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(FlowScriptProcessesParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayStatement}.
	 * @param ctx the parse tree
	 */
	void enterGatewayStatement(FlowScriptProcessesParser.GatewayStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayStatement}.
	 * @param ctx the parse tree
	 */
	void exitGatewayStatement(FlowScriptProcessesParser.GatewayStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayStmtExclusive}.
	 * @param ctx the parse tree
	 */
	void enterGatewayStmtExclusive(FlowScriptProcessesParser.GatewayStmtExclusiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayStmtExclusive}.
	 * @param ctx the parse tree
	 */
	void exitGatewayStmtExclusive(FlowScriptProcessesParser.GatewayStmtExclusiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayStmtParallel}.
	 * @param ctx the parse tree
	 */
	void enterGatewayStmtParallel(FlowScriptProcessesParser.GatewayStmtParallelContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayStmtParallel}.
	 * @param ctx the parse tree
	 */
	void exitGatewayStmtParallel(FlowScriptProcessesParser.GatewayStmtParallelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eqExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqExpr(FlowScriptProcessesParser.EqExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eqExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqExpr(FlowScriptProcessesParser.EqExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinusExpr(FlowScriptProcessesParser.UnaryMinusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinusExpr(FlowScriptProcessesParser.UnaryMinusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primaryExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpr(FlowScriptProcessesParser.PrimaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primaryExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpr(FlowScriptProcessesParser.PrimaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddExpr(FlowScriptProcessesParser.AddExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddExpr(FlowScriptProcessesParser.AddExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code indexAccessExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIndexAccessExpr(FlowScriptProcessesParser.IndexAccessExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code indexAccessExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIndexAccessExpr(FlowScriptProcessesParser.IndexAccessExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mulExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMulExpr(FlowScriptProcessesParser.MulExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mulExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMulExpr(FlowScriptProcessesParser.MulExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(FlowScriptProcessesParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(FlowScriptProcessesParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberAccessExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccessExpr(FlowScriptProcessesParser.MemberAccessExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberAccessExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccessExpr(FlowScriptProcessesParser.MemberAccessExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterRelExpr(FlowScriptProcessesParser.RelExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitRelExpr(FlowScriptProcessesParser.RelExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCallExpr(FlowScriptProcessesParser.CallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCallExpr(FlowScriptProcessesParser.CallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryNotExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryNotExpr(FlowScriptProcessesParser.UnaryNotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryNotExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryNotExpr(FlowScriptProcessesParser.UnaryNotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(FlowScriptProcessesParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(FlowScriptProcessesParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#argListOpt}.
	 * @param ctx the parse tree
	 */
	void enterArgListOpt(FlowScriptProcessesParser.ArgListOptContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#argListOpt}.
	 * @param ctx the parse tree
	 */
	void exitArgListOpt(FlowScriptProcessesParser.ArgListOptContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#objectPair}.
	 * @param ctx the parse tree
	 */
	void enterObjectPair(FlowScriptProcessesParser.ObjectPairContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#objectPair}.
	 * @param ctx the parse tree
	 */
	void exitObjectPair(FlowScriptProcessesParser.ObjectPairContext ctx);
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
}