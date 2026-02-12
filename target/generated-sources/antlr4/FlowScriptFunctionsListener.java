// Generated from FlowScriptFunctions.g4 by ANTLR 4.13.1

    package edu.eam.ingesoft.tlf;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FlowScriptFunctionsParser}.
 */
public interface FlowScriptFunctionsListener extends ParseTreeListener {
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
}