// Generated from FlowScriptProcesses.g4 by ANTLR 4.13.1

    package edu.eam.ingesoft.tlf;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FlowScriptProcessesParser}.
 */
public interface FlowScriptProcessesListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#nl}.
	 * @param ctx the parse tree
	 */
	void enterNl(FlowScriptProcessesParser.NlContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#nl}.
	 * @param ctx the parse tree
	 */
	void exitNl(FlowScriptProcessesParser.NlContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#processMidElement}.
	 * @param ctx the parse tree
	 */
	void enterProcessMidElement(FlowScriptProcessesParser.ProcessMidElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#processMidElement}.
	 * @param ctx the parse tree
	 */
	void exitProcessMidElement(FlowScriptProcessesParser.ProcessMidElementContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayParallelNode}.
	 * @param ctx the parse tree
	 */
	void enterGatewayParallelNode(FlowScriptProcessesParser.GatewayParallelNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayParallelNode}.
	 * @param ctx the parse tree
	 */
	void exitGatewayParallelNode(FlowScriptProcessesParser.GatewayParallelNodeContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#parallelBranch}.
	 * @param ctx the parse tree
	 */
	void enterParallelBranch(FlowScriptProcessesParser.ParallelBranchContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#parallelBranch}.
	 * @param ctx the parse tree
	 */
	void exitParallelBranch(FlowScriptProcessesParser.ParallelBranchContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#joinClause}.
	 * @param ctx the parse tree
	 */
	void enterJoinClause(FlowScriptProcessesParser.JoinClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#joinClause}.
	 * @param ctx the parse tree
	 */
	void exitJoinClause(FlowScriptProcessesParser.JoinClauseContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(FlowScriptProcessesParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(FlowScriptProcessesParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(FlowScriptProcessesParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(FlowScriptProcessesParser.ContinueStatementContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#exprStatement}.
	 * @param ctx the parse tree
	 */
	void enterExprStatement(FlowScriptProcessesParser.ExprStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#exprStatement}.
	 * @param ctx the parse tree
	 */
	void exitExprStatement(FlowScriptProcessesParser.ExprStatementContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#logicOr}.
	 * @param ctx the parse tree
	 */
	void enterLogicOr(FlowScriptProcessesParser.LogicOrContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#logicOr}.
	 * @param ctx the parse tree
	 */
	void exitLogicOr(FlowScriptProcessesParser.LogicOrContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#logicAnd}.
	 * @param ctx the parse tree
	 */
	void enterLogicAnd(FlowScriptProcessesParser.LogicAndContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#logicAnd}.
	 * @param ctx the parse tree
	 */
	void exitLogicAnd(FlowScriptProcessesParser.LogicAndContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#equality}.
	 * @param ctx the parse tree
	 */
	void enterEquality(FlowScriptProcessesParser.EqualityContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#equality}.
	 * @param ctx the parse tree
	 */
	void exitEquality(FlowScriptProcessesParser.EqualityContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(FlowScriptProcessesParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(FlowScriptProcessesParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#addition}.
	 * @param ctx the parse tree
	 */
	void enterAddition(FlowScriptProcessesParser.AdditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#addition}.
	 * @param ctx the parse tree
	 */
	void exitAddition(FlowScriptProcessesParser.AdditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#multiplication}.
	 * @param ctx the parse tree
	 */
	void enterMultiplication(FlowScriptProcessesParser.MultiplicationContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#multiplication}.
	 * @param ctx the parse tree
	 */
	void exitMultiplication(FlowScriptProcessesParser.MultiplicationContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#unary}.
	 * @param ctx the parse tree
	 */
	void enterUnary(FlowScriptProcessesParser.UnaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#unary}.
	 * @param ctx the parse tree
	 */
	void exitUnary(FlowScriptProcessesParser.UnaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#postfix}.
	 * @param ctx the parse tree
	 */
	void enterPostfix(FlowScriptProcessesParser.PostfixContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#postfix}.
	 * @param ctx the parse tree
	 */
	void exitPostfix(FlowScriptProcessesParser.PostfixContext ctx);
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