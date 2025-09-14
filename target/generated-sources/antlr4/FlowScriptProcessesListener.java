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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(FlowScriptProcessesParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(FlowScriptProcessesParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(FlowScriptProcessesParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(FlowScriptProcessesParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#typeName}.
	 * @param ctx the parse tree
	 */
	void enterTypeName(FlowScriptProcessesParser.TypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#typeName}.
	 * @param ctx the parse tree
	 */
	void exitTypeName(FlowScriptProcessesParser.TypeNameContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#processBody}.
	 * @param ctx the parse tree
	 */
	void enterProcessBody(FlowScriptProcessesParser.ProcessBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#processBody}.
	 * @param ctx the parse tree
	 */
	void exitProcessBody(FlowScriptProcessesParser.ProcessBodyContext ctx);
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
	 * Enter a parse tree produced by the {@code exclusiveGateway}
	 * labeled alternative in {@link FlowScriptProcessesParser#gatewayBody}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveGateway(FlowScriptProcessesParser.ExclusiveGatewayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exclusiveGateway}
	 * labeled alternative in {@link FlowScriptProcessesParser#gatewayBody}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveGateway(FlowScriptProcessesParser.ExclusiveGatewayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parallelGateway}
	 * labeled alternative in {@link FlowScriptProcessesParser#gatewayBody}.
	 * @param ctx the parse tree
	 */
	void enterParallelGateway(FlowScriptProcessesParser.ParallelGatewayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parallelGateway}
	 * labeled alternative in {@link FlowScriptProcessesParser#gatewayBody}.
	 * @param ctx the parse tree
	 */
	void exitParallelGateway(FlowScriptProcessesParser.ParallelGatewayContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#exclusiveGatewayBody}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveGatewayBody(FlowScriptProcessesParser.ExclusiveGatewayBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#exclusiveGatewayBody}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveGatewayBody(FlowScriptProcessesParser.ExclusiveGatewayBodyContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#parallelGatewayBody}.
	 * @param ctx the parse tree
	 */
	void enterParallelGatewayBody(FlowScriptProcessesParser.ParallelGatewayBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#parallelGatewayBody}.
	 * @param ctx the parse tree
	 */
	void exitParallelGatewayBody(FlowScriptProcessesParser.ParallelGatewayBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#branchClause}.
	 * @param ctx the parse tree
	 */
	void enterBranchClause(FlowScriptProcessesParser.BranchClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#branchClause}.
	 * @param ctx the parse tree
	 */
	void exitBranchClause(FlowScriptProcessesParser.BranchClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#statementList}.
	 * @param ctx the parse tree
	 */
	void enterStatementList(FlowScriptProcessesParser.StatementListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#statementList}.
	 * @param ctx the parse tree
	 */
	void exitStatementList(FlowScriptProcessesParser.StatementListContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gotoStmt}.
	 * @param ctx the parse tree
	 */
	void enterGotoStmt(FlowScriptProcessesParser.GotoStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gotoStmt}.
	 * @param ctx the parse tree
	 */
	void exitGotoStmt(FlowScriptProcessesParser.GotoStmtContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#assignable}.
	 * @param ctx the parse tree
	 */
	void enterAssignable(FlowScriptProcessesParser.AssignableContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#assignable}.
	 * @param ctx the parse tree
	 */
	void exitAssignable(FlowScriptProcessesParser.AssignableContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#memberAccess}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccess(FlowScriptProcessesParser.MemberAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#memberAccess}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccess(FlowScriptProcessesParser.MemberAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#indexer}.
	 * @param ctx the parse tree
	 */
	void enterIndexer(FlowScriptProcessesParser.IndexerContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#indexer}.
	 * @param ctx the parse tree
	 */
	void exitIndexer(FlowScriptProcessesParser.IndexerContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(FlowScriptProcessesParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(FlowScriptProcessesParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#forEachStmt}.
	 * @param ctx the parse tree
	 */
	void enterForEachStmt(FlowScriptProcessesParser.ForEachStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#forEachStmt}.
	 * @param ctx the parse tree
	 */
	void exitForEachStmt(FlowScriptProcessesParser.ForEachStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#forRangeStmt}.
	 * @param ctx the parse tree
	 */
	void enterForRangeStmt(FlowScriptProcessesParser.ForRangeStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#forRangeStmt}.
	 * @param ctx the parse tree
	 */
	void exitForRangeStmt(FlowScriptProcessesParser.ForRangeStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(FlowScriptProcessesParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(FlowScriptProcessesParser.ReturnStmtContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#logicOrExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicOrExpr(FlowScriptProcessesParser.LogicOrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#logicOrExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicOrExpr(FlowScriptProcessesParser.LogicOrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#logicAndExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicAndExpr(FlowScriptProcessesParser.LogicAndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#logicAndExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicAndExpr(FlowScriptProcessesParser.LogicAndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(FlowScriptProcessesParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(FlowScriptProcessesParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(FlowScriptProcessesParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(FlowScriptProcessesParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(FlowScriptProcessesParser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(FlowScriptProcessesParser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpr(FlowScriptProcessesParser.MultiplicativeExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpr(FlowScriptProcessesParser.MultiplicativeExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(FlowScriptProcessesParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(FlowScriptProcessesParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#callMemberIndexExpr}.
	 * @param ctx the parse tree
	 */
	void enterCallMemberIndexExpr(FlowScriptProcessesParser.CallMemberIndexExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#callMemberIndexExpr}.
	 * @param ctx the parse tree
	 */
	void exitCallMemberIndexExpr(FlowScriptProcessesParser.CallMemberIndexExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#callSuffix}.
	 * @param ctx the parse tree
	 */
	void enterCallSuffix(FlowScriptProcessesParser.CallSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#callSuffix}.
	 * @param ctx the parse tree
	 */
	void exitCallSuffix(FlowScriptProcessesParser.CallSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#memberSuffix}.
	 * @param ctx the parse tree
	 */
	void enterMemberSuffix(FlowScriptProcessesParser.MemberSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#memberSuffix}.
	 * @param ctx the parse tree
	 */
	void exitMemberSuffix(FlowScriptProcessesParser.MemberSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#indexSuffix}.
	 * @param ctx the parse tree
	 */
	void enterIndexSuffix(FlowScriptProcessesParser.IndexSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#indexSuffix}.
	 * @param ctx the parse tree
	 */
	void exitIndexSuffix(FlowScriptProcessesParser.IndexSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(FlowScriptProcessesParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(FlowScriptProcessesParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpr(FlowScriptProcessesParser.PrimaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpr(FlowScriptProcessesParser.PrimaryExprContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#objectPair}.
	 * @param ctx the parse tree
	 */
	void enterObjectPair(FlowScriptProcessesParser.ObjectPairContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#objectPair}.
	 * @param ctx the parse tree
	 */
	void exitObjectPair(FlowScriptProcessesParser.ObjectPairContext ctx);
}