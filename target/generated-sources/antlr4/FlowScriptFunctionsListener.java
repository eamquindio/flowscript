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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(FlowScriptFunctionsParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(FlowScriptFunctionsParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#functionName}.
	 * @param ctx the parse tree
	 */
	void enterFunctionName(FlowScriptFunctionsParser.FunctionNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#functionName}.
	 * @param ctx the parse tree
	 */
	void exitFunctionName(FlowScriptFunctionsParser.FunctionNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(FlowScriptFunctionsParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(FlowScriptFunctionsParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(FlowScriptFunctionsParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(FlowScriptFunctionsParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#parameterName}.
	 * @param ctx the parse tree
	 */
	void enterParameterName(FlowScriptFunctionsParser.ParameterNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#parameterName}.
	 * @param ctx the parse tree
	 */
	void exitParameterName(FlowScriptFunctionsParser.ParameterNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#parameterType}.
	 * @param ctx the parse tree
	 */
	void enterParameterType(FlowScriptFunctionsParser.ParameterTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#parameterType}.
	 * @param ctx the parse tree
	 */
	void exitParameterType(FlowScriptFunctionsParser.ParameterTypeContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterDataType(FlowScriptFunctionsParser.DataTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitDataType(FlowScriptFunctionsParser.DataTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(FlowScriptFunctionsParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(FlowScriptFunctionsParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#compositeType}.
	 * @param ctx the parse tree
	 */
	void enterCompositeType(FlowScriptFunctionsParser.CompositeTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#compositeType}.
	 * @param ctx the parse tree
	 */
	void exitCompositeType(FlowScriptFunctionsParser.CompositeTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(FlowScriptFunctionsParser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(FlowScriptFunctionsParser.FunctionBodyContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(FlowScriptFunctionsParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(FlowScriptFunctionsParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(FlowScriptFunctionsParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#assignmentStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(FlowScriptFunctionsParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#leftHandSide}.
	 * @param ctx the parse tree
	 */
	void enterLeftHandSide(FlowScriptFunctionsParser.LeftHandSideContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#leftHandSide}.
	 * @param ctx the parse tree
	 */
	void exitLeftHandSide(FlowScriptFunctionsParser.LeftHandSideContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#elseIfClause}.
	 * @param ctx the parse tree
	 */
	void enterElseIfClause(FlowScriptFunctionsParser.ElseIfClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#elseIfClause}.
	 * @param ctx the parse tree
	 */
	void exitElseIfClause(FlowScriptFunctionsParser.ElseIfClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void enterElseClause(FlowScriptFunctionsParser.ElseClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void exitElseClause(FlowScriptFunctionsParser.ElseClauseContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(FlowScriptFunctionsParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(FlowScriptFunctionsParser.ForStatementContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#tryStatement}.
	 * @param ctx the parse tree
	 */
	void enterTryStatement(FlowScriptFunctionsParser.TryStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#tryStatement}.
	 * @param ctx the parse tree
	 */
	void exitTryStatement(FlowScriptFunctionsParser.TryStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#catchClause}.
	 * @param ctx the parse tree
	 */
	void enterCatchClause(FlowScriptFunctionsParser.CatchClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#catchClause}.
	 * @param ctx the parse tree
	 */
	void exitCatchClause(FlowScriptFunctionsParser.CatchClauseContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(FlowScriptFunctionsParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(FlowScriptFunctionsParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(FlowScriptFunctionsParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(FlowScriptFunctionsParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(FlowScriptFunctionsParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(FlowScriptFunctionsParser.ExpressionStatementContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(FlowScriptFunctionsParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(FlowScriptFunctionsParser.ConditionContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpression(FlowScriptFunctionsParser.AssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#assignmentExpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpression(FlowScriptFunctionsParser.AssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpression(FlowScriptFunctionsParser.LogicalOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#logicalOrExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpression(FlowScriptFunctionsParser.LogicalOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpression(FlowScriptFunctionsParser.LogicalAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpression(FlowScriptFunctionsParser.LogicalAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(FlowScriptFunctionsParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(FlowScriptFunctionsParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpression(FlowScriptFunctionsParser.RelationalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#relationalExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpression(FlowScriptFunctionsParser.RelationalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(FlowScriptFunctionsParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(FlowScriptFunctionsParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(FlowScriptFunctionsParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(FlowScriptFunctionsParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(FlowScriptFunctionsParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(FlowScriptFunctionsParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpression(FlowScriptFunctionsParser.PostfixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#postfixExpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpression(FlowScriptFunctionsParser.PostfixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(FlowScriptFunctionsParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(FlowScriptFunctionsParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpression(FlowScriptFunctionsParser.PrimaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpression(FlowScriptFunctionsParser.PrimaryExpressionContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#objectProperty}.
	 * @param ctx the parse tree
	 */
	void enterObjectProperty(FlowScriptFunctionsParser.ObjectPropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#objectProperty}.
	 * @param ctx the parse tree
	 */
	void exitObjectProperty(FlowScriptFunctionsParser.ObjectPropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#propertyKey}.
	 * @param ctx the parse tree
	 */
	void enterPropertyKey(FlowScriptFunctionsParser.PropertyKeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#propertyKey}.
	 * @param ctx the parse tree
	 */
	void exitPropertyKey(FlowScriptFunctionsParser.PropertyKeyContext ctx);
}