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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void enterImportStatement(FlowScriptProcessesParser.ImportStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void exitImportStatement(FlowScriptProcessesParser.ImportStatementContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(FlowScriptProcessesParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(FlowScriptProcessesParser.ParamListContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#processElement}.
	 * @param ctx the parse tree
	 */
	void enterProcessElement(FlowScriptProcessesParser.ProcessElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#processElement}.
	 * @param ctx the parse tree
	 */
	void exitProcessElement(FlowScriptProcessesParser.ProcessElementContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#actionBlock}.
	 * @param ctx the parse tree
	 */
	void enterActionBlock(FlowScriptProcessesParser.ActionBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#actionBlock}.
	 * @param ctx the parse tree
	 */
	void exitActionBlock(FlowScriptProcessesParser.ActionBlockContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayBody}.
	 * @param ctx the parse tree
	 */
	void enterGatewayBody(FlowScriptProcessesParser.GatewayBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayBody}.
	 * @param ctx the parse tree
	 */
	void exitGatewayBody(FlowScriptProcessesParser.GatewayBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#parallelGateway}.
	 * @param ctx the parse tree
	 */
	void enterParallelGateway(FlowScriptProcessesParser.ParallelGatewayContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#parallelGateway}.
	 * @param ctx the parse tree
	 */
	void exitParallelGateway(FlowScriptProcessesParser.ParallelGatewayContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#exclusiveGateway}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveGateway(FlowScriptProcessesParser.ExclusiveGatewayContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#exclusiveGateway}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveGateway(FlowScriptProcessesParser.ExclusiveGatewayContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#branchStmt}.
	 * @param ctx the parse tree
	 */
	void enterBranchStmt(FlowScriptProcessesParser.BranchStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#branchStmt}.
	 * @param ctx the parse tree
	 */
	void exitBranchStmt(FlowScriptProcessesParser.BranchStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#whenStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhenStmt(FlowScriptProcessesParser.WhenStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#whenStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhenStmt(FlowScriptProcessesParser.WhenStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#joinStmt}.
	 * @param ctx the parse tree
	 */
	void enterJoinStmt(FlowScriptProcessesParser.JoinStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#joinStmt}.
	 * @param ctx the parse tree
	 */
	void exitJoinStmt(FlowScriptProcessesParser.JoinStmtContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#arrayAssignment}.
	 * @param ctx the parse tree
	 */
	void enterArrayAssignment(FlowScriptProcessesParser.ArrayAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#arrayAssignment}.
	 * @param ctx the parse tree
	 */
	void exitArrayAssignment(FlowScriptProcessesParser.ArrayAssignmentContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(FlowScriptProcessesParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(FlowScriptProcessesParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#tryCatch}.
	 * @param ctx the parse tree
	 */
	void enterTryCatch(FlowScriptProcessesParser.TryCatchContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#tryCatch}.
	 * @param ctx the parse tree
	 */
	void exitTryCatch(FlowScriptProcessesParser.TryCatchContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gotoStatement}.
	 * @param ctx the parse tree
	 */
	void enterGotoStatement(FlowScriptProcessesParser.GotoStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gotoStatement}.
	 * @param ctx the parse tree
	 */
	void exitGotoStatement(FlowScriptProcessesParser.GotoStatementContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(FlowScriptProcessesParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(FlowScriptProcessesParser.ExpressionStatementContext ctx);
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
	 * Enter a parse tree produced by the {@code MulDivExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMulDivExpr(FlowScriptProcessesParser.MulDivExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDivExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMulDivExpr(FlowScriptProcessesParser.MulDivExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionCallExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpr(FlowScriptProcessesParser.FunctionCallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionCallExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpr(FlowScriptProcessesParser.FunctionCallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ComparisonExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterComparisonExpr(FlowScriptProcessesParser.ComparisonExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ComparisonExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitComparisonExpr(FlowScriptProcessesParser.ComparisonExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayAccessExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccessExpr(FlowScriptProcessesParser.ArrayAccessExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayAccessExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccessExpr(FlowScriptProcessesParser.ArrayAccessExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrimaryExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpr(FlowScriptProcessesParser.PrimaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrimaryExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpr(FlowScriptProcessesParser.PrimaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(FlowScriptProcessesParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(FlowScriptProcessesParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSubExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddSubExpr(FlowScriptProcessesParser.AddSubExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSubExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddSubExpr(FlowScriptProcessesParser.AddSubExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LogicalExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalExpr(FlowScriptProcessesParser.LogicalExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LogicalExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalExpr(FlowScriptProcessesParser.LogicalExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PropertyAccessExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPropertyAccessExpr(FlowScriptProcessesParser.PropertyAccessExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PropertyAccessExpr}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPropertyAccessExpr(FlowScriptProcessesParser.PropertyAccessExprContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(FlowScriptProcessesParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(FlowScriptProcessesParser.ExpressionListContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#objectField}.
	 * @param ctx the parse tree
	 */
	void enterObjectField(FlowScriptProcessesParser.ObjectFieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#objectField}.
	 * @param ctx the parse tree
	 */
	void exitObjectField(FlowScriptProcessesParser.ObjectFieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#arrayLiteral}.
	 * @param ctx the parse tree
	 */
	void enterArrayLiteral(FlowScriptProcessesParser.ArrayLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#arrayLiteral}.
	 * @param ctx the parse tree
	 */
	void exitArrayLiteral(FlowScriptProcessesParser.ArrayLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(FlowScriptProcessesParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(FlowScriptProcessesParser.ConditionContext ctx);
}