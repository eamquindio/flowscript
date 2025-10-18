package com.flowscript.sintactic.parsers.functions.programa_declaraciones;

import com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;
import com.flowscript.sintactic.ast.functions.control_flujo.ForStatementNode;
import com.flowscript.sintactic.ast.functions.control_flujo.IfStatementNode;
import com.flowscript.sintactic.ast.functions.control_flujo.ReturnStatementNode;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ParameterListNode;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.*;
import com.flowscript.sintactic.ast.functions.statements_basicos.ExpressionStatementNode;
import com.flowscript.sintactic.ast.functions.statements_basicos.StatementListNode;
import com.flowscript.sintactic.ast.functions.statements_basicos.VariableDeclarationStatementNode;
import com.flowscript.sintactic.ast.functions.tipos_parametros.ParameterNode;
import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;
import com.flowscript.sintactic.parsers.functions.expresiones.ExpressionParser;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsBaseVisitor;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsLexer;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsParser;
import org.antlr.v4.runtime.*;

import java.util.ArrayList;
import java.util.List;

public class ProgramParser {

    // API principal: agarra el código fuente y lo convierte en un AST del programa
    public static ProgramNode parse(String source) {
        FlowScriptFunctionsParser parser = buildParser(source);
        var tree = parser.program(); // punto de entrada en tu gramática FlowScriptFunctions.g4
        return (ProgramNode) new FunctionsAstVisitor().visit(tree);
    }

    // Constructor común del parser ANTLR (útil para otros parsers también)
    public static FlowScriptFunctionsParser buildParser(String source) {
        CharStream input = CharStreams.fromString(source);
        FlowScriptFunctionsLexer lexer = new FlowScriptFunctionsLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FlowScriptFunctionsParser parser = new FlowScriptFunctionsParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new DiagnosticErrorListener(true));
        parser.addErrorListener(ConsoleErrorListener.INSTANCE);
        return parser;
    }

    // Visitor principal: transforma el árbol de parseo en AST (para funciones)
    public static class FunctionsAstVisitor extends FlowScriptFunctionsBaseVisitor<Object> {

        private final ExpressionParser.FunctionsExpressionVisitor expr = new ExpressionParser.FunctionsExpressionVisitor();

        // ------- Programa / Declaraciones -------
        @Override
        public Object visitProgram(FlowScriptFunctionsParser.ProgramContext ctx) {
            List<DeclarationNode> decls = new ArrayList<>();
            for (FlowScriptFunctionsParser.DeclarationContext d : ctx.declaration()) {
                decls.add((DeclarationNode) d.accept(this));
            }
            return new ProgramNode(decls);
        }

        @Override
        public Object visitDeclaration(FlowScriptFunctionsParser.DeclarationContext ctx) {
            if (ctx.functionDeclaration() != null) return ctx.functionDeclaration().accept(this);
            if (ctx.importDeclaration() != null)  return new ImportDeclarationNode(/* TODO si los tests lo piden */);
            if (ctx.variableDeclaration() != null) return ctx.variableDeclaration().accept(this);
            throw new UnsupportedOperationException("Esta declaración no la manejo: " + ctx.getText());
        }

        @Override
        public Object visitFunctionDeclaration(FlowScriptFunctionsParser.FunctionDeclarationContext ctx) {
            String name = ctx.Identifier().getText();
            ParameterListNode params = ctx.parameterList() != null
                    ? (ParameterListNode) ctx.parameterList().accept(this)
                    : new ParameterListNode(List.of());
            BlockNode body = (BlockNode) ctx.block().accept(this);
            return new FunctionDeclarationNode(name, params, body);
        }

        // ------- Parámetros / Tipos -------
        @Override
        public Object visitParameterList(FlowScriptFunctionsParser.ParameterListContext ctx) {
            List<ParameterNode> list = new ArrayList<>();
            for (FlowScriptFunctionsParser.ParameterContext p : ctx.parameter()) {
                list.add((ParameterNode) p.accept(this));
            }
            return new ParameterListNode(list);
        }

        @Override
        public Object visitParameter(FlowScriptFunctionsParser.ParameterContext ctx) {
            String name = ctx.Identifier().getText();
            TypeNode type = null;
            if (ctx.typeAnnotation() != null) type = (TypeNode) ctx.typeAnnotation().accept(this);
            return new ParameterNode(name, type);
        }

        @Override
        public Object visitTypeAnnotation(FlowScriptFunctionsParser.TypeAnnotationContext ctx) {
            String typeName = ctx.getText().replace(":", "").trim();
            return new TypeNode(typeName);
        }

        // ------- Bloques / Sentencias -------
        @Override
        public Object visitBlock(FlowScriptFunctionsParser.BlockContext ctx) {
            List<StatementNode> stmts = new ArrayList<>();
            if (ctx.statementList() != null) {
                StatementListNode list = (StatementListNode) ctx.statementList().accept(this);
                stmts.addAll(list.getStatements());
            }
            return new BlockNode(stmts);
        }

        @Override
        public Object visitStatementList(FlowScriptFunctionsParser.StatementListContext ctx) {
            List<StatementNode> list = new ArrayList<>();
            for (FlowScriptFunctionsParser.StatementContext s : ctx.statement()) {
                list.add((StatementNode) s.accept(this));
            }
            return new StatementListNode(list);
        }

        @Override
        public Object visitStatement(FlowScriptFunctionsParser.StatementContext ctx) {
            if (ctx.variableDeclarationStatement() != null) return ctx.variableDeclarationStatement().accept(this);
            if (ctx.expressionStatement() != null)           return ctx.expressionStatement().accept(this);
            if (ctx.returnStatement() != null)               return ctx.returnStatement().accept(this);
            if (ctx.ifStatement() != null)                   return ctx.ifStatement().accept(this);
            if (ctx.forStatement() != null)                  return ctx.forStatement().accept(this);
            if (ctx.block() != null)                         return new StatementNode((BlockNode) ctx.block().accept(this));
            throw new UnsupportedOperationException("Esta sentencia no la soporto: " + ctx.getText());
        }

        @Override
        public Object visitVariableDeclarationStatement(FlowScriptFunctionsParser.VariableDeclarationStatementContext ctx) {
            String id = ctx.Identifier().getText();
            ExpressionNode init = ctx.expression() != null ? (ExpressionNode) ctx.expression().accept(expr) : null;
            return new VariableDeclarationStatementNode(id, init);
        }

        @Override
        public Object visitExpressionStatement(FlowScriptFunctionsParser.ExpressionStatementContext ctx) {
            ExpressionNode e = (ExpressionNode) ctx.expression().accept(expr);
            return new ExpressionStatementNode(e);
        }

        @Override
        public Object visitReturnStatement(FlowScriptFunctionsParser.ReturnStatementContext ctx) {
            ExpressionNode e = ctx.expression() != null ? (ExpressionNode) ctx.expression().accept(expr) : null;
            return new ReturnStatementNode(e);
        }

        @Override
        public Object visitIfStatement(FlowScriptFunctionsParser.IfStatementContext ctx) {
            ExpressionNode cond = (ExpressionNode) ctx.expression().accept(expr);
            BlockNode thenB = (BlockNode) ctx.block(0).accept(this);
            BlockNode elseB = null;
            if (ctx.elseClause() != null && ctx.elseClause().block() != null) {
                elseB = (BlockNode) ctx.elseClause().block().accept(this);
            }
            return new IfStatementNode(cond, thenB, elseB);
        }

        @Override
        public Object visitForStatement(FlowScriptFunctionsParser.ForStatementContext ctx) {
            StatementNode init = null;
            ExpressionNode cond = null;
            ExpressionNode update = null;

            if (ctx.forInit() != null) {
                if (ctx.forInit().variableDeclarationStatement() != null) {
                    init = (StatementNode) ctx.forInit().variableDeclarationStatement().accept(this);
                } else if (ctx.forInit().expressionStatement() != null) {
                    init = (StatementNode) ctx.forInit().expressionStatement().accept(this);
                }
            }
            if (ctx.expression(0) != null) cond = (ExpressionNode) ctx.expression(0).accept(expr);
            if (ctx.expression(1) != null) update = (ExpressionNode) ctx.expression(1).accept(expr);

            BlockNode body = (BlockNode) ctx.block().accept(this);
            return new ForStatementNode(init, cond, update, body);
        }
    }
}
