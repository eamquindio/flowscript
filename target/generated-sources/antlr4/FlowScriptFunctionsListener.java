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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#statementList}.
	 * @param ctx the parse tree
	 */
	void enterStatementList(FlowScriptFunctionsParser.StatementListContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#statementList}.
	 * @param ctx the parse tree
	 */
	void exitStatementList(FlowScriptFunctionsParser.StatementListContext ctx);
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
	 * Enter a parse tree produced by the {@code assignmentStatement}
	 * labeled alternative in {@link FlowScriptFunctionsParser#assignmentOrExpressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(FlowScriptFunctionsParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignmentStatement}
	 * labeled alternative in {@link FlowScriptFunctionsParser#assignmentOrExpressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(FlowScriptFunctionsParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionStatement}
	 * labeled alternative in {@link FlowScriptFunctionsParser#assignmentOrExpressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(FlowScriptFunctionsParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionStatement}
	 * labeled alternative in {@link FlowScriptFunctionsParser#assignmentOrExpressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(FlowScriptFunctionsParser.ExpressionStatementContext ctx);
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
	 * Enter a parse tree produced by the {@code functionCall}
	 * labeled alternative in {@link FlowScriptFunctionsParser#postfixOperator}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(FlowScriptFunctionsParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionCall}
	 * labeled alternative in {@link FlowScriptFunctionsParser#postfixOperator}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(FlowScriptFunctionsParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberAccess}
	 * labeled alternative in {@link FlowScriptFunctionsParser#postfixOperator}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccess(FlowScriptFunctionsParser.MemberAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberAccess}
	 * labeled alternative in {@link FlowScriptFunctionsParser#postfixOperator}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccess(FlowScriptFunctionsParser.MemberAccessContext ctx);
	/**
	 * Enter a parse tree produced by the {@code indexAccess}
	 * labeled alternative in {@link FlowScriptFunctionsParser#postfixOperator}.
	 * @param ctx the parse tree
	 */
	void enterIndexAccess(FlowScriptFunctionsParser.IndexAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code indexAccess}
	 * labeled alternative in {@link FlowScriptFunctionsParser#postfixOperator}.
	 * @param ctx the parse tree
	 */
	void exitIndexAccess(FlowScriptFunctionsParser.IndexAccessContext ctx);
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
	 * Enter a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpression(FlowScriptFunctionsParser.IdentifierExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpression(FlowScriptFunctionsParser.IdentifierExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code integerLiteral}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(FlowScriptFunctionsParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code integerLiteral}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(FlowScriptFunctionsParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code decimalLiteral}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterDecimalLiteral(FlowScriptFunctionsParser.DecimalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code decimalLiteral}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitDecimalLiteral(FlowScriptFunctionsParser.DecimalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code textLiteral}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterTextLiteral(FlowScriptFunctionsParser.TextLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code textLiteral}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitTextLiteral(FlowScriptFunctionsParser.TextLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trueLiteral}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterTrueLiteral(FlowScriptFunctionsParser.TrueLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trueLiteral}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitTrueLiteral(FlowScriptFunctionsParser.TrueLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code falseLiteral}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterFalseLiteral(FlowScriptFunctionsParser.FalseLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code falseLiteral}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitFalseLiteral(FlowScriptFunctionsParser.FalseLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullLiteral}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterNullLiteral(FlowScriptFunctionsParser.NullLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullLiteral}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitNullLiteral(FlowScriptFunctionsParser.NullLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code listExpression}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterListExpression(FlowScriptFunctionsParser.ListExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code listExpression}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitListExpression(FlowScriptFunctionsParser.ListExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code objectExpression}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterObjectExpression(FlowScriptFunctionsParser.ObjectExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code objectExpression}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitObjectExpression(FlowScriptFunctionsParser.ObjectExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenthesizedExpression}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedExpression(FlowScriptFunctionsParser.ParenthesizedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenthesizedExpression}
	 * labeled alternative in {@link FlowScriptFunctionsParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedExpression(FlowScriptFunctionsParser.ParenthesizedExpressionContext ctx);
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
}