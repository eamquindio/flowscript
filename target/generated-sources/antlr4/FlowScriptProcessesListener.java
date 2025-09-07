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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#importFlowScript}.
	 * @param ctx the parse tree
	 */
	void enterImportFlowScript(FlowScriptProcessesParser.ImportFlowScriptContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#importFlowScript}.
	 * @param ctx the parse tree
	 */
	void exitImportFlowScript(FlowScriptProcessesParser.ImportFlowScriptContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#importJar}.
	 * @param ctx the parse tree
	 */
	void enterImportJar(FlowScriptProcessesParser.ImportJarContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#importJar}.
	 * @param ctx the parse tree
	 */
	void exitImportJar(FlowScriptProcessesParser.ImportJarContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#topLevelDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTopLevelDeclaration(FlowScriptProcessesParser.TopLevelDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#topLevelDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTopLevelDeclaration(FlowScriptProcessesParser.TopLevelDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#globalVariable}.
	 * @param ctx the parse tree
	 */
	void enterGlobalVariable(FlowScriptProcessesParser.GlobalVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#globalVariable}.
	 * @param ctx the parse tree
	 */
	void exitGlobalVariable(FlowScriptProcessesParser.GlobalVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(FlowScriptProcessesParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(FlowScriptProcessesParser.FunctionDeclarationContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#returnType}.
	 * @param ctx the parse tree
	 */
	void enterReturnType(FlowScriptProcessesParser.ReturnTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#returnType}.
	 * @param ctx the parse tree
	 */
	void exitReturnType(FlowScriptProcessesParser.ReturnTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterDataType(FlowScriptProcessesParser.DataTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitDataType(FlowScriptProcessesParser.DataTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(FlowScriptProcessesParser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(FlowScriptProcessesParser.FunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#processDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterProcessDeclaration(FlowScriptProcessesParser.ProcessDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#processDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitProcessDeclaration(FlowScriptProcessesParser.ProcessDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#processName}.
	 * @param ctx the parse tree
	 */
	void enterProcessName(FlowScriptProcessesParser.ProcessNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#processName}.
	 * @param ctx the parse tree
	 */
	void exitProcessName(FlowScriptProcessesParser.ProcessNameContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#taskContent}.
	 * @param ctx the parse tree
	 */
	void enterTaskContent(FlowScriptProcessesParser.TaskContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#taskContent}.
	 * @param ctx the parse tree
	 */
	void exitTaskContent(FlowScriptProcessesParser.TaskContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#taskStatements}.
	 * @param ctx the parse tree
	 */
	void enterTaskStatements(FlowScriptProcessesParser.TaskStatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#taskStatements}.
	 * @param ctx the parse tree
	 */
	void exitTaskStatements(FlowScriptProcessesParser.TaskStatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#taskStatement}.
	 * @param ctx the parse tree
	 */
	void enterTaskStatement(FlowScriptProcessesParser.TaskStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#taskStatement}.
	 * @param ctx the parse tree
	 */
	void exitTaskStatement(FlowScriptProcessesParser.TaskStatementContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#exclusiveBranches}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveBranches(FlowScriptProcessesParser.ExclusiveBranchesContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#exclusiveBranches}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveBranches(FlowScriptProcessesParser.ExclusiveBranchesContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#whenBranch}.
	 * @param ctx the parse tree
	 */
	void enterWhenBranch(FlowScriptProcessesParser.WhenBranchContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#whenBranch}.
	 * @param ctx the parse tree
	 */
	void exitWhenBranch(FlowScriptProcessesParser.WhenBranchContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#elseBranch}.
	 * @param ctx the parse tree
	 */
	void enterElseBranch(FlowScriptProcessesParser.ElseBranchContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#elseBranch}.
	 * @param ctx the parse tree
	 */
	void exitElseBranch(FlowScriptProcessesParser.ElseBranchContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#parallelBranches}.
	 * @param ctx the parse tree
	 */
	void enterParallelBranches(FlowScriptProcessesParser.ParallelBranchesContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#parallelBranches}.
	 * @param ctx the parse tree
	 */
	void exitParallelBranches(FlowScriptProcessesParser.ParallelBranchesContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#branchStatement}.
	 * @param ctx the parse tree
	 */
	void enterBranchStatement(FlowScriptProcessesParser.BranchStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#branchStatement}.
	 * @param ctx the parse tree
	 */
	void exitBranchStatement(FlowScriptProcessesParser.BranchStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#joinStatement}.
	 * @param ctx the parse tree
	 */
	void enterJoinStatement(FlowScriptProcessesParser.JoinStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#joinStatement}.
	 * @param ctx the parse tree
	 */
	void exitJoinStatement(FlowScriptProcessesParser.JoinStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#nodeName}.
	 * @param ctx the parse tree
	 */
	void enterNodeName(FlowScriptProcessesParser.NodeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#nodeName}.
	 * @param ctx the parse tree
	 */
	void exitNodeName(FlowScriptProcessesParser.NodeNameContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(FlowScriptProcessesParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(FlowScriptProcessesParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#leftHandSide}.
	 * @param ctx the parse tree
	 */
	void enterLeftHandSide(FlowScriptProcessesParser.LeftHandSideContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#leftHandSide}.
	 * @param ctx the parse tree
	 */
	void exitLeftHandSide(FlowScriptProcessesParser.LeftHandSideContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#tryStatement}.
	 * @param ctx the parse tree
	 */
	void enterTryStatement(FlowScriptProcessesParser.TryStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#tryStatement}.
	 * @param ctx the parse tree
	 */
	void exitTryStatement(FlowScriptProcessesParser.TryStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#catchClause}.
	 * @param ctx the parse tree
	 */
	void enterCatchClause(FlowScriptProcessesParser.CatchClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#catchClause}.
	 * @param ctx the parse tree
	 */
	void exitCatchClause(FlowScriptProcessesParser.CatchClauseContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(FlowScriptProcessesParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(FlowScriptProcessesParser.ConditionContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpression(FlowScriptProcessesParser.AssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpression(FlowScriptProcessesParser.AssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpression(FlowScriptProcessesParser.LogicalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpression(FlowScriptProcessesParser.LogicalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpression(FlowScriptProcessesParser.LogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpression(FlowScriptProcessesParser.LogicalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(FlowScriptProcessesParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(FlowScriptProcessesParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(FlowScriptProcessesParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(FlowScriptProcessesParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(FlowScriptProcessesParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(FlowScriptProcessesParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(FlowScriptProcessesParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(FlowScriptProcessesParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(FlowScriptProcessesParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(FlowScriptProcessesParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpression(FlowScriptProcessesParser.PostfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpression(FlowScriptProcessesParser.PostfixExpressionContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpression(FlowScriptProcessesParser.PrimaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpression(FlowScriptProcessesParser.PrimaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#inputReference}.
	 * @param ctx the parse tree
	 */
	void enterInputReference(FlowScriptProcessesParser.InputReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#inputReference}.
	 * @param ctx the parse tree
	 */
	void exitInputReference(FlowScriptProcessesParser.InputReferenceContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#objectProperty}.
	 * @param ctx the parse tree
	 */
	void enterObjectProperty(FlowScriptProcessesParser.ObjectPropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#objectProperty}.
	 * @param ctx the parse tree
	 */
	void exitObjectProperty(FlowScriptProcessesParser.ObjectPropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#propertyKey}.
	 * @param ctx the parse tree
	 */
	void enterPropertyKey(FlowScriptProcessesParser.PropertyKeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#propertyKey}.
	 * @param ctx the parse tree
	 */
	void exitPropertyKey(FlowScriptProcessesParser.PropertyKeyContext ctx);
}