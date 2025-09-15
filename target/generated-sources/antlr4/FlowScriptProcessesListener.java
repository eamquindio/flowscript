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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#programElement}.
	 * @param ctx the parse tree
	 */
	void enterProgramElement(FlowScriptProcessesParser.ProgramElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#programElement}.
	 * @param ctx the parse tree
	 */
	void exitProgramElement(FlowScriptProcessesParser.ProgramElementContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(FlowScriptProcessesParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(FlowScriptProcessesParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#globalVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGlobalVariableDeclaration(FlowScriptProcessesParser.GlobalVariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#globalVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGlobalVariableDeclaration(FlowScriptProcessesParser.GlobalVariableDeclarationContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#processContent}.
	 * @param ctx the parse tree
	 */
	void enterProcessContent(FlowScriptProcessesParser.ProcessContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#processContent}.
	 * @param ctx the parse tree
	 */
	void exitProcessContent(FlowScriptProcessesParser.ProcessContentContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#blockContent}.
	 * @param ctx the parse tree
	 */
	void enterBlockContent(FlowScriptProcessesParser.BlockContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#blockContent}.
	 * @param ctx the parse tree
	 */
	void exitBlockContent(FlowScriptProcessesParser.BlockContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#blockElement}.
	 * @param ctx the parse tree
	 */
	void enterBlockElement(FlowScriptProcessesParser.BlockElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#blockElement}.
	 * @param ctx the parse tree
	 */
	void exitBlockElement(FlowScriptProcessesParser.BlockElementContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayContent}.
	 * @param ctx the parse tree
	 */
	void enterGatewayContent(FlowScriptProcessesParser.GatewayContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayContent}.
	 * @param ctx the parse tree
	 */
	void exitGatewayContent(FlowScriptProcessesParser.GatewayContentContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#statementBlock}.
	 * @param ctx the parse tree
	 */
	void enterStatementBlock(FlowScriptProcessesParser.StatementBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#statementBlock}.
	 * @param ctx the parse tree
	 */
	void exitStatementBlock(FlowScriptProcessesParser.StatementBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayAccessExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccessExpression(FlowScriptProcessesParser.ArrayAccessExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayAccessExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccessExpression(FlowScriptProcessesParser.ArrayAccessExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpression(FlowScriptProcessesParser.AtomExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpression(FlowScriptProcessesParser.AtomExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code additiveExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(FlowScriptProcessesParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code additiveExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(FlowScriptProcessesParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationalExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(FlowScriptProcessesParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationalExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(FlowScriptProcessesParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalAndExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpression(FlowScriptProcessesParser.LogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalAndExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpression(FlowScriptProcessesParser.LogicalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalityExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(FlowScriptProcessesParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalityExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(FlowScriptProcessesParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignmentExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpression(FlowScriptProcessesParser.AssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignmentExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpression(FlowScriptProcessesParser.AssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberAccessExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccessExpression(FlowScriptProcessesParser.MemberAccessExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberAccessExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccessExpression(FlowScriptProcessesParser.MemberAccessExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpression(FlowScriptProcessesParser.FunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpression(FlowScriptProcessesParser.FunctionCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalOrExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpression(FlowScriptProcessesParser.LogicalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalOrExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpression(FlowScriptProcessesParser.LogicalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiplicativeExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(FlowScriptProcessesParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiplicativeExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(FlowScriptProcessesParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(FlowScriptProcessesParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryExpression}
	 * labeled alternative in {@link FlowScriptProcessesParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(FlowScriptProcessesParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(FlowScriptProcessesParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(FlowScriptProcessesParser.AtomContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#objectEntry}.
	 * @param ctx the parse tree
	 */
	void enterObjectEntry(FlowScriptProcessesParser.ObjectEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#objectEntry}.
	 * @param ctx the parse tree
	 */
	void exitObjectEntry(FlowScriptProcessesParser.ObjectEntryContext ctx);
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
}