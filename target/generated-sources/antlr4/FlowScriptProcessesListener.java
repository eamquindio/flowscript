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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#importsPr}.
	 * @param ctx the parse tree
	 */
	void enterImportsPr(FlowScriptProcessesParser.ImportsPrContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#importsPr}.
	 * @param ctx the parse tree
	 */
	void exitImportsPr(FlowScriptProcessesParser.ImportsPrContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#declarationsPr}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationsPr(FlowScriptProcessesParser.DeclarationsPrContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#declarationsPr}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationsPr(FlowScriptProcessesParser.DeclarationsPrContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#imports}.
	 * @param ctx the parse tree
	 */
	void enterImports(FlowScriptProcessesParser.ImportsContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#imports}.
	 * @param ctx the parse tree
	 */
	void exitImports(FlowScriptProcessesParser.ImportsContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#importModulos}.
	 * @param ctx the parse tree
	 */
	void enterImportModulos(FlowScriptProcessesParser.ImportModulosContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#importModulos}.
	 * @param ctx the parse tree
	 */
	void exitImportModulos(FlowScriptProcessesParser.ImportModulosContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#nombreModulo}.
	 * @param ctx the parse tree
	 */
	void enterNombreModulo(FlowScriptProcessesParser.NombreModuloContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#nombreModulo}.
	 * @param ctx the parse tree
	 */
	void exitNombreModulo(FlowScriptProcessesParser.NombreModuloContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#alias}.
	 * @param ctx the parse tree
	 */
	void enterAlias(FlowScriptProcessesParser.AliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#alias}.
	 * @param ctx the parse tree
	 */
	void exitAlias(FlowScriptProcessesParser.AliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#declarations}.
	 * @param ctx the parse tree
	 */
	void enterDeclarations(FlowScriptProcessesParser.DeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#declarations}.
	 * @param ctx the parse tree
	 */
	void exitDeclarations(FlowScriptProcessesParser.DeclarationsContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#variablesGlDc}.
	 * @param ctx the parse tree
	 */
	void enterVariablesGlDc(FlowScriptProcessesParser.VariablesGlDcContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#variablesGlDc}.
	 * @param ctx the parse tree
	 */
	void exitVariablesGlDc(FlowScriptProcessesParser.VariablesGlDcContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#funcionDc}.
	 * @param ctx the parse tree
	 */
	void enterFuncionDc(FlowScriptProcessesParser.FuncionDcContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#funcionDc}.
	 * @param ctx the parse tree
	 */
	void exitFuncionDc(FlowScriptProcessesParser.FuncionDcContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#paramListaOpt}.
	 * @param ctx the parse tree
	 */
	void enterParamListaOpt(FlowScriptProcessesParser.ParamListaOptContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#paramListaOpt}.
	 * @param ctx the parse tree
	 */
	void exitParamListaOpt(FlowScriptProcessesParser.ParamListaOptContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#returnTipoOpt}.
	 * @param ctx the parse tree
	 */
	void enterReturnTipoOpt(FlowScriptProcessesParser.ReturnTipoOptContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#returnTipoOpt}.
	 * @param ctx the parse tree
	 */
	void exitReturnTipoOpt(FlowScriptProcessesParser.ReturnTipoOptContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#tipoRef}.
	 * @param ctx the parse tree
	 */
	void enterTipoRef(FlowScriptProcessesParser.TipoRefContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#tipoRef}.
	 * @param ctx the parse tree
	 */
	void exitTipoRef(FlowScriptProcessesParser.TipoRefContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#startNodo}.
	 * @param ctx the parse tree
	 */
	void enterStartNodo(FlowScriptProcessesParser.StartNodoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#startNodo}.
	 * @param ctx the parse tree
	 */
	void exitStartNodo(FlowScriptProcessesParser.StartNodoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#endNodo}.
	 * @param ctx the parse tree
	 */
	void enterEndNodo(FlowScriptProcessesParser.EndNodoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#endNodo}.
	 * @param ctx the parse tree
	 */
	void exitEndNodo(FlowScriptProcessesParser.EndNodoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#taskNodo}.
	 * @param ctx the parse tree
	 */
	void enterTaskNodo(FlowScriptProcessesParser.TaskNodoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#taskNodo}.
	 * @param ctx the parse tree
	 */
	void exitTaskNodo(FlowScriptProcessesParser.TaskNodoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayNodo}.
	 * @param ctx the parse tree
	 */
	void enterGatewayNodo(FlowScriptProcessesParser.GatewayNodoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayNodo}.
	 * @param ctx the parse tree
	 */
	void exitGatewayNodo(FlowScriptProcessesParser.GatewayNodoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#getawayExcNodo}.
	 * @param ctx the parse tree
	 */
	void enterGetawayExcNodo(FlowScriptProcessesParser.GetawayExcNodoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#getawayExcNodo}.
	 * @param ctx the parse tree
	 */
	void exitGetawayExcNodo(FlowScriptProcessesParser.GetawayExcNodoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayParNodo}.
	 * @param ctx the parse tree
	 */
	void enterGatewayParNodo(FlowScriptProcessesParser.GatewayParNodoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayParNodo}.
	 * @param ctx the parse tree
	 */
	void exitGatewayParNodo(FlowScriptProcessesParser.GatewayParNodoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayWhen}.
	 * @param ctx the parse tree
	 */
	void enterGatewayWhen(FlowScriptProcessesParser.GatewayWhenContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayWhen}.
	 * @param ctx the parse tree
	 */
	void exitGatewayWhen(FlowScriptProcessesParser.GatewayWhenContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayElse}.
	 * @param ctx the parse tree
	 */
	void enterGatewayElse(FlowScriptProcessesParser.GatewayElseContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayElse}.
	 * @param ctx the parse tree
	 */
	void exitGatewayElse(FlowScriptProcessesParser.GatewayElseContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayBranch}.
	 * @param ctx the parse tree
	 */
	void enterGatewayBranch(FlowScriptProcessesParser.GatewayBranchContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayBranch}.
	 * @param ctx the parse tree
	 */
	void exitGatewayBranch(FlowScriptProcessesParser.GatewayBranchContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayJoin}.
	 * @param ctx the parse tree
	 */
	void enterGatewayJoin(FlowScriptProcessesParser.GatewayJoinContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayJoin}.
	 * @param ctx the parse tree
	 */
	void exitGatewayJoin(FlowScriptProcessesParser.GatewayJoinContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#ref}.
	 * @param ctx the parse tree
	 */
	void enterRef(FlowScriptProcessesParser.RefContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#ref}.
	 * @param ctx the parse tree
	 */
	void exitRef(FlowScriptProcessesParser.RefContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#goToSent}.
	 * @param ctx the parse tree
	 */
	void enterGoToSent(FlowScriptProcessesParser.GoToSentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#goToSent}.
	 * @param ctx the parse tree
	 */
	void exitGoToSent(FlowScriptProcessesParser.GoToSentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#ifSent}.
	 * @param ctx the parse tree
	 */
	void enterIfSent(FlowScriptProcessesParser.IfSentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#ifSent}.
	 * @param ctx the parse tree
	 */
	void exitIfSent(FlowScriptProcessesParser.IfSentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#whileSent}.
	 * @param ctx the parse tree
	 */
	void enterWhileSent(FlowScriptProcessesParser.WhileSentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#whileSent}.
	 * @param ctx the parse tree
	 */
	void exitWhileSent(FlowScriptProcessesParser.WhileSentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#foreachSent}.
	 * @param ctx the parse tree
	 */
	void enterForeachSent(FlowScriptProcessesParser.ForeachSentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#foreachSent}.
	 * @param ctx the parse tree
	 */
	void exitForeachSent(FlowScriptProcessesParser.ForeachSentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#forRangeSent}.
	 * @param ctx the parse tree
	 */
	void enterForRangeSent(FlowScriptProcessesParser.ForRangeSentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#forRangeSent}.
	 * @param ctx the parse tree
	 */
	void exitForRangeSent(FlowScriptProcessesParser.ForRangeSentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#tryCatchSent}.
	 * @param ctx the parse tree
	 */
	void enterTryCatchSent(FlowScriptProcessesParser.TryCatchSentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#tryCatchSent}.
	 * @param ctx the parse tree
	 */
	void exitTryCatchSent(FlowScriptProcessesParser.TryCatchSentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#throwSent}.
	 * @param ctx the parse tree
	 */
	void enterThrowSent(FlowScriptProcessesParser.ThrowSentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#throwSent}.
	 * @param ctx the parse tree
	 */
	void exitThrowSent(FlowScriptProcessesParser.ThrowSentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#returnSent}.
	 * @param ctx the parse tree
	 */
	void enterReturnSent(FlowScriptProcessesParser.ReturnSentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#returnSent}.
	 * @param ctx the parse tree
	 */
	void exitReturnSent(FlowScriptProcessesParser.ReturnSentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewaySent}.
	 * @param ctx the parse tree
	 */
	void enterGatewaySent(FlowScriptProcessesParser.GatewaySentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewaySent}.
	 * @param ctx the parse tree
	 */
	void exitGatewaySent(FlowScriptProcessesParser.GatewaySentContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayExclusivo}.
	 * @param ctx the parse tree
	 */
	void enterGatewayExclusivo(FlowScriptProcessesParser.GatewayExclusivoContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayExclusivo}.
	 * @param ctx the parse tree
	 */
	void exitGatewayExclusivo(FlowScriptProcessesParser.GatewayExclusivoContext ctx);
	/**
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#gatewayParalelo}.
	 * @param ctx the parse tree
	 */
	void enterGatewayParalelo(FlowScriptProcessesParser.GatewayParaleloContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#gatewayParalelo}.
	 * @param ctx the parse tree
	 */
	void exitGatewayParalelo(FlowScriptProcessesParser.GatewayParaleloContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#listaLiteral}.
	 * @param ctx the parse tree
	 */
	void enterListaLiteral(FlowScriptProcessesParser.ListaLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#listaLiteral}.
	 * @param ctx the parse tree
	 */
	void exitListaLiteral(FlowScriptProcessesParser.ListaLiteralContext ctx);
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
	 * Enter a parse tree produced by {@link FlowScriptProcessesParser#objectPair}.
	 * @param ctx the parse tree
	 */
	void enterObjectPair(FlowScriptProcessesParser.ObjectPairContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlowScriptProcessesParser#objectPair}.
	 * @param ctx the parse tree
	 */
	void exitObjectPair(FlowScriptProcessesParser.ObjectPairContext ctx);
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
}