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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaTope}.
	 * @param ctx the parse tree
	 */
	void enterSentenciaTope(FlowScriptFunctionsParser.SentenciaTopeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaTope}.
	 * @param ctx the parse tree
	 */
	void exitSentenciaTope(FlowScriptFunctionsParser.SentenciaTopeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#declaracionFuncion}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracionFuncion(FlowScriptFunctionsParser.DeclaracionFuncionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#declaracionFuncion}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracionFuncion(FlowScriptFunctionsParser.DeclaracionFuncionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#listaParametros}.
	 * @param ctx the parse tree
	 */
	void enterListaParametros(FlowScriptFunctionsParser.ListaParametrosContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#listaParametros}.
	 * @param ctx the parse tree
	 */
	void exitListaParametros(FlowScriptFunctionsParser.ListaParametrosContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#parametro}.
	 * @param ctx the parse tree
	 */
	void enterParametro(FlowScriptFunctionsParser.ParametroContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#parametro}.
	 * @param ctx the parse tree
	 */
	void exitParametro(FlowScriptFunctionsParser.ParametroContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#tipoRetorno}.
	 * @param ctx the parse tree
	 */
	void enterTipoRetorno(FlowScriptFunctionsParser.TipoRetornoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#tipoRetorno}.
	 * @param ctx the parse tree
	 */
	void exitTipoRetorno(FlowScriptFunctionsParser.TipoRetornoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#bloque}.
	 * @param ctx the parse tree
	 */
	void enterBloque(FlowScriptFunctionsParser.BloqueContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#bloque}.
	 * @param ctx the parse tree
	 */
	void exitBloque(FlowScriptFunctionsParser.BloqueContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#sentencia}.
	 * @param ctx the parse tree
	 */
	void enterSentencia(FlowScriptFunctionsParser.SentenciaContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#sentencia}.
	 * @param ctx the parse tree
	 */
	void exitSentencia(FlowScriptFunctionsParser.SentenciaContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#asignacion}.
	 * @param ctx the parse tree
	 */
	void enterAsignacion(FlowScriptFunctionsParser.AsignacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#asignacion}.
	 * @param ctx the parse tree
	 */
	void exitAsignacion(FlowScriptFunctionsParser.AsignacionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void enterLvalue(FlowScriptFunctionsParser.LvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#lvalue}.
	 * @param ctx the parse tree
	 */
	void exitLvalue(FlowScriptFunctionsParser.LvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#expresionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpresionStatement(FlowScriptFunctionsParser.ExpresionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#expresionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpresionStatement(FlowScriptFunctionsParser.ExpresionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaReturn}.
	 * @param ctx the parse tree
	 */
	void enterSentenciaReturn(FlowScriptFunctionsParser.SentenciaReturnContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaReturn}.
	 * @param ctx the parse tree
	 */
	void exitSentenciaReturn(FlowScriptFunctionsParser.SentenciaReturnContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaIf}.
	 * @param ctx the parse tree
	 */
	void enterSentenciaIf(FlowScriptFunctionsParser.SentenciaIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaIf}.
	 * @param ctx the parse tree
	 */
	void exitSentenciaIf(FlowScriptFunctionsParser.SentenciaIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#parteElseIf}.
	 * @param ctx the parse tree
	 */
	void enterParteElseIf(FlowScriptFunctionsParser.ParteElseIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#parteElseIf}.
	 * @param ctx the parse tree
	 */
	void exitParteElseIf(FlowScriptFunctionsParser.ParteElseIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaWhile}.
	 * @param ctx the parse tree
	 */
	void enterSentenciaWhile(FlowScriptFunctionsParser.SentenciaWhileContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaWhile}.
	 * @param ctx the parse tree
	 */
	void exitSentenciaWhile(FlowScriptFunctionsParser.SentenciaWhileContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaForEach}.
	 * @param ctx the parse tree
	 */
	void enterSentenciaForEach(FlowScriptFunctionsParser.SentenciaForEachContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaForEach}.
	 * @param ctx the parse tree
	 */
	void exitSentenciaForEach(FlowScriptFunctionsParser.SentenciaForEachContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaForRange}.
	 * @param ctx the parse tree
	 */
	void enterSentenciaForRange(FlowScriptFunctionsParser.SentenciaForRangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaForRange}.
	 * @param ctx the parse tree
	 */
	void exitSentenciaForRange(FlowScriptFunctionsParser.SentenciaForRangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaTryCatch}.
	 * @param ctx the parse tree
	 */
	void enterSentenciaTryCatch(FlowScriptFunctionsParser.SentenciaTryCatchContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaTryCatch}.
	 * @param ctx the parse tree
	 */
	void exitSentenciaTryCatch(FlowScriptFunctionsParser.SentenciaTryCatchContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaThrow}.
	 * @param ctx the parse tree
	 */
	void enterSentenciaThrow(FlowScriptFunctionsParser.SentenciaThrowContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaThrow}.
	 * @param ctx the parse tree
	 */
	void exitSentenciaThrow(FlowScriptFunctionsParser.SentenciaThrowContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaBreak}.
	 * @param ctx the parse tree
	 */
	void enterSentenciaBreak(FlowScriptFunctionsParser.SentenciaBreakContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaBreak}.
	 * @param ctx the parse tree
	 */
	void exitSentenciaBreak(FlowScriptFunctionsParser.SentenciaBreakContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaContinue}.
	 * @param ctx the parse tree
	 */
	void enterSentenciaContinue(FlowScriptFunctionsParser.SentenciaContinueContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#sentenciaContinue}.
	 * @param ctx the parse tree
	 */
	void exitSentenciaContinue(FlowScriptFunctionsParser.SentenciaContinueContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExpresion(FlowScriptFunctionsParser.ExpresionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExpresion(FlowScriptFunctionsParser.ExpresionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#expresionOr}.
	 * @param ctx the parse tree
	 */
	void enterExpresionOr(FlowScriptFunctionsParser.ExpresionOrContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#expresionOr}.
	 * @param ctx the parse tree
	 */
	void exitExpresionOr(FlowScriptFunctionsParser.ExpresionOrContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#expresionAnd}.
	 * @param ctx the parse tree
	 */
	void enterExpresionAnd(FlowScriptFunctionsParser.ExpresionAndContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#expresionAnd}.
	 * @param ctx the parse tree
	 */
	void exitExpresionAnd(FlowScriptFunctionsParser.ExpresionAndContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#expresionEq}.
	 * @param ctx the parse tree
	 */
	void enterExpresionEq(FlowScriptFunctionsParser.ExpresionEqContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#expresionEq}.
	 * @param ctx the parse tree
	 */
	void exitExpresionEq(FlowScriptFunctionsParser.ExpresionEqContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#expresionRel}.
	 * @param ctx the parse tree
	 */
	void enterExpresionRel(FlowScriptFunctionsParser.ExpresionRelContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#expresionRel}.
	 * @param ctx the parse tree
	 */
	void exitExpresionRel(FlowScriptFunctionsParser.ExpresionRelContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#expresionAdd}.
	 * @param ctx the parse tree
	 */
	void enterExpresionAdd(FlowScriptFunctionsParser.ExpresionAddContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#expresionAdd}.
	 * @param ctx the parse tree
	 */
	void exitExpresionAdd(FlowScriptFunctionsParser.ExpresionAddContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#expresionMul}.
	 * @param ctx the parse tree
	 */
	void enterExpresionMul(FlowScriptFunctionsParser.ExpresionMulContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#expresionMul}.
	 * @param ctx the parse tree
	 */
	void exitExpresionMul(FlowScriptFunctionsParser.ExpresionMulContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#expresionUnary}.
	 * @param ctx the parse tree
	 */
	void enterExpresionUnary(FlowScriptFunctionsParser.ExpresionUnaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#expresionUnary}.
	 * @param ctx the parse tree
	 */
	void exitExpresionUnary(FlowScriptFunctionsParser.ExpresionUnaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#expresionPostfijo}.
	 * @param ctx the parse tree
	 */
	void enterExpresionPostfijo(FlowScriptFunctionsParser.ExpresionPostfijoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#expresionPostfijo}.
	 * @param ctx the parse tree
	 */
	void exitExpresionPostfijo(FlowScriptFunctionsParser.ExpresionPostfijoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#expresionPrimario}.
	 * @param ctx the parse tree
	 */
	void enterExpresionPrimario(FlowScriptFunctionsParser.ExpresionPrimarioContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#expresionPrimario}.
	 * @param ctx the parse tree
	 */
	void exitExpresionPrimario(FlowScriptFunctionsParser.ExpresionPrimarioContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#listaArgumentos}.
	 * @param ctx the parse tree
	 */
	void enterListaArgumentos(FlowScriptFunctionsParser.ListaArgumentosContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#listaArgumentos}.
	 * @param ctx the parse tree
	 */
	void exitListaArgumentos(FlowScriptFunctionsParser.ListaArgumentosContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#listaLiteral}.
	 * @param ctx the parse tree
	 */
	void enterListaLiteral(FlowScriptFunctionsParser.ListaLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#listaLiteral}.
	 * @param ctx the parse tree
	 */
	void exitListaLiteral(FlowScriptFunctionsParser.ListaLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#objetoLiteral}.
	 * @param ctx the parse tree
	 */
	void enterObjetoLiteral(FlowScriptFunctionsParser.ObjetoLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#objetoLiteral}.
	 * @param ctx the parse tree
	 */
	void exitObjetoLiteral(FlowScriptFunctionsParser.ObjetoLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#entradaObjeto}.
	 * @param ctx the parse tree
	 */
	void enterEntradaObjeto(FlowScriptFunctionsParser.EntradaObjetoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#entradaObjeto}.
	 * @param ctx the parse tree
	 */
	void exitEntradaObjeto(FlowScriptFunctionsParser.EntradaObjetoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#tipoNombre}.
	 * @param ctx the parse tree
	 */
	void enterTipoNombre(FlowScriptFunctionsParser.TipoNombreContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#tipoNombre}.
	 * @param ctx the parse tree
	 */
	void exitTipoNombre(FlowScriptFunctionsParser.TipoNombreContext ctx);
}