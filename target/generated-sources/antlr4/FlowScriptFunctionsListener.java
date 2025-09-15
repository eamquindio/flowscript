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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#parametros}.
	 * @param ctx the parse tree
	 */
	void enterParametros(FlowScriptFunctionsParser.ParametrosContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#parametros}.
	 * @param ctx the parse tree
	 */
	void exitParametros(FlowScriptFunctionsParser.ParametrosContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#tipo}.
	 * @param ctx the parse tree
	 */
	void enterTipo(FlowScriptFunctionsParser.TipoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#tipo}.
	 * @param ctx the parse tree
	 */
	void exitTipo(FlowScriptFunctionsParser.TipoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#retorno}.
	 * @param ctx the parse tree
	 */
	void enterRetorno(FlowScriptFunctionsParser.RetornoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#retorno}.
	 * @param ctx the parse tree
	 */
	void exitRetorno(FlowScriptFunctionsParser.RetornoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#resultado}.
	 * @param ctx the parse tree
	 */
	void enterResultado(FlowScriptFunctionsParser.ResultadoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#resultado}.
	 * @param ctx the parse tree
	 */
	void exitResultado(FlowScriptFunctionsParser.ResultadoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#return}.
	 * @param ctx the parse tree
	 */
	void enterReturn(FlowScriptFunctionsParser.ReturnContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#return}.
	 * @param ctx the parse tree
	 */
	void exitReturn(FlowScriptFunctionsParser.ReturnContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(FlowScriptFunctionsParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(FlowScriptFunctionsParser.PrintContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#if}.
	 * @param ctx the parse tree
	 */
	void enterIf(FlowScriptFunctionsParser.IfContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#if}.
	 * @param ctx the parse tree
	 */
	void exitIf(FlowScriptFunctionsParser.IfContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#while}.
	 * @param ctx the parse tree
	 */
	void enterWhile(FlowScriptFunctionsParser.WhileContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#while}.
	 * @param ctx the parse tree
	 */
	void exitWhile(FlowScriptFunctionsParser.WhileContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#for}.
	 * @param ctx the parse tree
	 */
	void enterFor(FlowScriptFunctionsParser.ForContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#for}.
	 * @param ctx the parse tree
	 */
	void exitFor(FlowScriptFunctionsParser.ForContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#tryCatch}.
	 * @param ctx the parse tree
	 */
	void enterTryCatch(FlowScriptFunctionsParser.TryCatchContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#tryCatch}.
	 * @param ctx the parse tree
	 */
	void exitTryCatch(FlowScriptFunctionsParser.TryCatchContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#error}.
	 * @param ctx the parse tree
	 */
	void enterError(FlowScriptFunctionsParser.ErrorContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#error}.
	 * @param ctx the parse tree
	 */
	void exitError(FlowScriptFunctionsParser.ErrorContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#llamadafunction}.
	 * @param ctx the parse tree
	 */
	void enterLlamadafunction(FlowScriptFunctionsParser.LlamadafunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#llamadafunction}.
	 * @param ctx the parse tree
	 */
	void exitLlamadafunction(FlowScriptFunctionsParser.LlamadafunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#argumentos}.
	 * @param ctx the parse tree
	 */
	void enterArgumentos(FlowScriptFunctionsParser.ArgumentosContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#argumentos}.
	 * @param ctx the parse tree
	 */
	void exitArgumentos(FlowScriptFunctionsParser.ArgumentosContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#lista}.
	 * @param ctx the parse tree
	 */
	void enterLista(FlowScriptFunctionsParser.ListaContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#lista}.
	 * @param ctx the parse tree
	 */
	void exitLista(FlowScriptFunctionsParser.ListaContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptFunctionsParser#objeto}.
	 * @param ctx the parse tree
	 */
	void enterObjeto(FlowScriptFunctionsParser.ObjetoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptFunctionsParser#objeto}.
	 * @param ctx the parse tree
	 */
	void exitObjeto(FlowScriptFunctionsParser.ObjetoContext ctx);
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
}