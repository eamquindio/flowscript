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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#declaracionImportar}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracionImportar(FlowScriptProcessesParser.DeclaracionImportarContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#declaracionImportar}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracionImportar(FlowScriptProcessesParser.DeclaracionImportarContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#declaracionImportarJar}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracionImportarJar(FlowScriptProcessesParser.DeclaracionImportarJarContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#declaracionImportarJar}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracionImportarJar(FlowScriptProcessesParser.DeclaracionImportarJarContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#variableGlobal}.
	 * @param ctx the parse tree
	 */
	void enterVariableGlobal(FlowScriptProcessesParser.VariableGlobalContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#variableGlobal}.
	 * @param ctx the parse tree
	 */
	void exitVariableGlobal(FlowScriptProcessesParser.VariableGlobalContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#valorGlobal}.
	 * @param ctx the parse tree
	 */
	void enterValorGlobal(FlowScriptProcessesParser.ValorGlobalContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#valorGlobal}.
	 * @param ctx the parse tree
	 */
	void exitValorGlobal(FlowScriptProcessesParser.ValorGlobalContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#declaracionFuncion}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracionFuncion(FlowScriptProcessesParser.DeclaracionFuncionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#declaracionFuncion}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracionFuncion(FlowScriptProcessesParser.DeclaracionFuncionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#listaParametros}.
	 * @param ctx the parse tree
	 */
	void enterListaParametros(FlowScriptProcessesParser.ListaParametrosContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#listaParametros}.
	 * @param ctx the parse tree
	 */
	void exitListaParametros(FlowScriptProcessesParser.ListaParametrosContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#parametro}.
	 * @param ctx the parse tree
	 */
	void enterParametro(FlowScriptProcessesParser.ParametroContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#parametro}.
	 * @param ctx the parse tree
	 */
	void exitParametro(FlowScriptProcessesParser.ParametroContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#tipo}.
	 * @param ctx the parse tree
	 */
	void enterTipo(FlowScriptProcessesParser.TipoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#tipo}.
	 * @param ctx the parse tree
	 */
	void exitTipo(FlowScriptProcessesParser.TipoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#cuerpoFuncion}.
	 * @param ctx the parse tree
	 */
	void enterCuerpoFuncion(FlowScriptProcessesParser.CuerpoFuncionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#cuerpoFuncion}.
	 * @param ctx the parse tree
	 */
	void exitCuerpoFuncion(FlowScriptProcessesParser.CuerpoFuncionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#declaracionProceso}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracionProceso(FlowScriptProcessesParser.DeclaracionProcesoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#declaracionProceso}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracionProceso(FlowScriptProcessesParser.DeclaracionProcesoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#cuerpoProceso}.
	 * @param ctx the parse tree
	 */
	void enterCuerpoProceso(FlowScriptProcessesParser.CuerpoProcesoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#cuerpoProceso}.
	 * @param ctx the parse tree
	 */
	void exitCuerpoProceso(FlowScriptProcessesParser.CuerpoProcesoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#elementoProceso}.
	 * @param ctx the parse tree
	 */
	void enterElementoProceso(FlowScriptProcessesParser.ElementoProcesoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#elementoProceso}.
	 * @param ctx the parse tree
	 */
	void exitElementoProceso(FlowScriptProcessesParser.ElementoProcesoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#elementoInicio}.
	 * @param ctx the parse tree
	 */
	void enterElementoInicio(FlowScriptProcessesParser.ElementoInicioContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#elementoInicio}.
	 * @param ctx the parse tree
	 */
	void exitElementoInicio(FlowScriptProcessesParser.ElementoInicioContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#elementoFin}.
	 * @param ctx the parse tree
	 */
	void enterElementoFin(FlowScriptProcessesParser.ElementoFinContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#elementoFin}.
	 * @param ctx the parse tree
	 */
	void exitElementoFin(FlowScriptProcessesParser.ElementoFinContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#elementoTarea}.
	 * @param ctx the parse tree
	 */
	void enterElementoTarea(FlowScriptProcessesParser.ElementoTareaContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#elementoTarea}.
	 * @param ctx the parse tree
	 */
	void exitElementoTarea(FlowScriptProcessesParser.ElementoTareaContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#itemsAccion}.
	 * @param ctx the parse tree
	 */
	void enterItemsAccion(FlowScriptProcessesParser.ItemsAccionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#itemsAccion}.
	 * @param ctx the parse tree
	 */
	void exitItemsAccion(FlowScriptProcessesParser.ItemsAccionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#sentencia}.
	 * @param ctx the parse tree
	 */
	void enterSentencia(FlowScriptProcessesParser.SentenciaContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#sentencia}.
	 * @param ctx the parse tree
	 */
	void exitSentencia(FlowScriptProcessesParser.SentenciaContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#irA}.
	 * @param ctx the parse tree
	 */
	void enterIrA(FlowScriptProcessesParser.IrAContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#irA}.
	 * @param ctx the parse tree
	 */
	void exitIrA(FlowScriptProcessesParser.IrAContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#asignacion}.
	 * @param ctx the parse tree
	 */
	void enterAsignacion(FlowScriptProcessesParser.AsignacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#asignacion}.
	 * @param ctx the parse tree
	 */
	void exitAsignacion(FlowScriptProcessesParser.AsignacionContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#expresionStmt}.
	 * @param ctx the parse tree
	 */
	void enterExpresionStmt(FlowScriptProcessesParser.ExpresionStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#expresionStmt}.
	 * @param ctx the parse tree
	 */
	void exitExpresionStmt(FlowScriptProcessesParser.ExpresionStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#siSino}.
	 * @param ctx the parse tree
	 */
	void enterSiSino(FlowScriptProcessesParser.SiSinoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#siSino}.
	 * @param ctx the parse tree
	 */
	void exitSiSino(FlowScriptProcessesParser.SiSinoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#intentarCapturar}.
	 * @param ctx the parse tree
	 */
	void enterIntentarCapturar(FlowScriptProcessesParser.IntentarCapturarContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#intentarCapturar}.
	 * @param ctx the parse tree
	 */
	void exitIntentarCapturar(FlowScriptProcessesParser.IntentarCapturarContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#paraCada}.
	 * @param ctx the parse tree
	 */
	void enterParaCada(FlowScriptProcessesParser.ParaCadaContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#paraCada}.
	 * @param ctx the parse tree
	 */
	void exitParaCada(FlowScriptProcessesParser.ParaCadaContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#bloque}.
	 * @param ctx the parse tree
	 */
	void enterBloque(FlowScriptProcessesParser.BloqueContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#bloque}.
	 * @param ctx the parse tree
	 */
	void exitBloque(FlowScriptProcessesParser.BloqueContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#pasarelaExclusiva}.
	 * @param ctx the parse tree
	 */
	void enterPasarelaExclusiva(FlowScriptProcessesParser.PasarelaExclusivaContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#pasarelaExclusiva}.
	 * @param ctx the parse tree
	 */
	void exitPasarelaExclusiva(FlowScriptProcessesParser.PasarelaExclusivaContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#cuando}.
	 * @param ctx the parse tree
	 */
	void enterCuando(FlowScriptProcessesParser.CuandoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#cuando}.
	 * @param ctx the parse tree
	 */
	void exitCuando(FlowScriptProcessesParser.CuandoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#sino}.
	 * @param ctx the parse tree
	 */
	void enterSino(FlowScriptProcessesParser.SinoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#sino}.
	 * @param ctx the parse tree
	 */
	void exitSino(FlowScriptProcessesParser.SinoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#pasarelaParalela}.
	 * @param ctx the parse tree
	 */
	void enterPasarelaParalela(FlowScriptProcessesParser.PasarelaParalelaContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#pasarelaParalela}.
	 * @param ctx the parse tree
	 */
	void exitPasarelaParalela(FlowScriptProcessesParser.PasarelaParalelaContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#ramaParalela}.
	 * @param ctx the parse tree
	 */
	void enterRamaParalela(FlowScriptProcessesParser.RamaParalelaContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#ramaParalela}.
	 * @param ctx the parse tree
	 */
	void exitRamaParalela(FlowScriptProcessesParser.RamaParalelaContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#unirRama}.
	 * @param ctx the parse tree
	 */
	void enterUnirRama(FlowScriptProcessesParser.UnirRamaContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#unirRama}.
	 * @param ctx the parse tree
	 */
	void exitUnirRama(FlowScriptProcessesParser.UnirRamaContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExpresion(FlowScriptProcessesParser.ExpresionContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExpresion(FlowScriptProcessesParser.ExpresionContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpr(FlowScriptProcessesParser.PostfixExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpr(FlowScriptProcessesParser.PostfixExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#primario}.
	 * @param ctx the parse tree
	 */
	void enterPrimario(FlowScriptProcessesParser.PrimarioContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#primario}.
	 * @param ctx the parse tree
	 */
	void exitPrimario(FlowScriptProcessesParser.PrimarioContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#listaArgumentos}.
	 * @param ctx the parse tree
	 */
	void enterListaArgumentos(FlowScriptProcessesParser.ListaArgumentosContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#listaArgumentos}.
	 * @param ctx the parse tree
	 */
	void exitListaArgumentos(FlowScriptProcessesParser.ListaArgumentosContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#objetoLiteral}.
	 * @param ctx the parse tree
	 */
	void enterObjetoLiteral(FlowScriptProcessesParser.ObjetoLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#objetoLiteral}.
	 * @param ctx the parse tree
	 */
	void exitObjetoLiteral(FlowScriptProcessesParser.ObjetoLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#campoObjeto}.
	 * @param ctx the parse tree
	 */
	void enterCampoObjeto(FlowScriptProcessesParser.CampoObjetoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#campoObjeto}.
	 * @param ctx the parse tree
	 */
	void exitCampoObjeto(FlowScriptProcessesParser.CampoObjetoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#listaLiteral}.
	 * @param ctx the parse tree
	 */
	void enterListaLiteral(FlowScriptProcessesParser.ListaLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#listaLiteral}.
	 * @param ctx the parse tree
	 */
	void exitListaLiteral(FlowScriptProcessesParser.ListaLiteralContext ctx);
}