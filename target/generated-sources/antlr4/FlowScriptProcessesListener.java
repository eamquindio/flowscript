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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#topLevelElement}.
	 * @param ctx the parse tree
	 */
	void enterTopLevelElement(FlowScriptProcessesParser.TopLevelElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#topLevelElement}.
	 * @param ctx the parse tree
	 */
	void exitTopLevelElement(FlowScriptProcessesParser.TopLevelElementContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#functionName}.
	 * @param ctx the parse tree
	 */
	void enterFunctionName(FlowScriptProcessesParser.FunctionNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#functionName}.
	 * @param ctx the parse tree
	 */
	void exitFunctionName(FlowScriptProcessesParser.FunctionNameContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#processMembers}.
	 * @param ctx the parse tree
	 */
	void enterProcessMembers(FlowScriptProcessesParser.ProcessMembersContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#processMembers}.
	 * @param ctx the parse tree
	 */
	void exitProcessMembers(FlowScriptProcessesParser.ProcessMembersContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#startClause}.
	 * @param ctx the parse tree
	 */
	void enterStartClause(FlowScriptProcessesParser.StartClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#startClause}.
	 * @param ctx the parse tree
	 */
	void exitStartClause(FlowScriptProcessesParser.StartClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#processMember}.
	 * @param ctx the parse tree
	 */
	void enterProcessMember(FlowScriptProcessesParser.ProcessMemberContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#processMember}.
	 * @param ctx the parse tree
	 */
	void exitProcessMember(FlowScriptProcessesParser.ProcessMemberContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#endDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterEndDeclaration(FlowScriptProcessesParser.EndDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#endDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitEndDeclaration(FlowScriptProcessesParser.EndDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#taskDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTaskDeclaration(FlowScriptProcessesParser.TaskDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#taskDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTaskDeclaration(FlowScriptProcessesParser.TaskDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGatewayDeclaration(FlowScriptProcessesParser.GatewayDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGatewayDeclaration(FlowScriptProcessesParser.GatewayDeclarationContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayExclusiveBody}.
	 * @param ctx the parse tree
	 */
	void enterGatewayExclusiveBody(FlowScriptProcessesParser.GatewayExclusiveBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayExclusiveBody}.
	 * @param ctx the parse tree
	 */
	void exitGatewayExclusiveBody(FlowScriptProcessesParser.GatewayExclusiveBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayExclusiveRule}.
	 * @param ctx the parse tree
	 */
	void enterGatewayExclusiveRule(FlowScriptProcessesParser.GatewayExclusiveRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayExclusiveRule}.
	 * @param ctx the parse tree
	 */
	void exitGatewayExclusiveRule(FlowScriptProcessesParser.GatewayExclusiveRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayParallelBody}.
	 * @param ctx the parse tree
	 */
	void enterGatewayParallelBody(FlowScriptProcessesParser.GatewayParallelBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayParallelBody}.
	 * @param ctx the parse tree
	 */
	void exitGatewayParallelBody(FlowScriptProcessesParser.GatewayParallelBodyContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#postfixPart}.
	 * @param ctx the parse tree
	 */
	void enterPostfixPart(FlowScriptProcessesParser.PostfixPartContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#postfixPart}.
	 * @param ctx the parse tree
	 */
	void exitPostfixPart(FlowScriptProcessesParser.PostfixPartContext ctx);
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