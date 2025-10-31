package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.ast.functions.expresiones.*;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ArgumentListNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;
import com.flowscript.sintactic.ast.functions.literales.*;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsBaseVisitor;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsParser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Visitor de expresiones para FlowScriptFunctions.g4 (versión B reforzada).
 * Se alinea con las reglas: expression -> logicalOrExpr -> ... y postfix/primary.
 * Requiere que en la gramática los operadores estén etiquetados como op+=... y
 * los términos como lhs y rhs (ver g4).
 */
public class ExpressionParser {

    /** Visitor de expresiones (usado por parsers de funciones). */
    public static class FunctionsExpressionVisitor extends FlowScriptFunctionsBaseVisitor<Object> {

        // =========================
        // ENTRADA
        // =========================
        @Override
        public Object visitExpression(FlowScriptFunctionsParser.ExpressionContext ctx) {
            // Regla tope: expression : logicalOrExpr ;
            return ctx.logicalOrExpr().accept(this);
        }

        // =========================
        // PRECEDENCIA BINARIOS
        // =========================
        @Override
        public Object visitLogicalOrExpr(FlowScriptFunctionsParser.LogicalOrExprContext ctx) {
            return foldLeft(ctx.lhs, ctx.rhs, ctx.op);
        }

        @Override
        public Object visitLogicalAndExpr(FlowScriptFunctionsParser.LogicalAndExprContext ctx) {
            return foldLeft(ctx.lhs, ctx.rhs, ctx.op);
        }

        @Override
        public Object visitEqualityExpr(FlowScriptFunctionsParser.EqualityExprContext ctx) {
            return foldLeft(ctx.lhs, ctx.rhs, ctx.op);
        }

        @Override
        public Object visitRelationalExpr(FlowScriptFunctionsParser.RelationalExprContext ctx) {
            return foldLeft(ctx.lhs, ctx.rhs, ctx.op);
        }

        @Override
        public Object visitAdditiveExpr(FlowScriptFunctionsParser.AdditiveExprContext ctx) {
            return foldLeft(ctx.lhs, ctx.rhs, ctx.op);
        }

        @Override
        public Object visitMultiplicativeExpr(FlowScriptFunctionsParser.MultiplicativeExprContext ctx) {
            return foldLeft(ctx.lhs, ctx.rhs, ctx.op);
        }

        // =========================
        // UNARIOS / POSTFIX
        // =========================
        @Override
        public Object visitUnaryExpr(FlowScriptFunctionsParser.UnaryExprContext ctx) {
            if (ctx.op != null) { // NOT o MINUS
                ExpressionNode rhs = (ExpressionNode) ctx.unaryExpr().accept(this);
                return new UnaryExpressionNode(ctx.op.getText(), rhs);
            }
            return ctx.postfixExpr().accept(this);
        }

        @Override
        public Object visitPostfixExpr(FlowScriptFunctionsParser.PostfixExprContext ctx) {
            // primary ( funcCall | DOT ID | LBRACK expression RBRACK )*
            ExpressionNode base = (ExpressionNode) ctx.primary().accept(this);
            for (FlowScriptFunctionsParser.PostfixExprContext.PostfixPartContext p : ctx.postfixPart()) {
                if (p.funcCall() != null) {
                    ArgumentListNode args = (ArgumentListNode) p.funcCall().accept(this);
                    base = new FunctionCallNode(base, args);
                } else if (p.DOT() != null) {
                    String prop = p.ID().getText();
                    base = new PropertyAccessNode(base, prop);
                } else if (p.LBRACK() != null) {
                    ExpressionNode idx = (ExpressionNode) p.expression().accept(this);
                    base = new IndexAccessNode(base, idx);
                }
            }
            return base;
        }

        @Override
        public Object visitFuncCall(FlowScriptFunctionsParser.FuncCallContext ctx) {
            // LPAREN argList? RPAREN
            List<ExpressionNode> args = new ArrayList<>();
            if (ctx.argList() != null) {
                ArgumentListNode list = (ArgumentListNode) ctx.argList().accept(this);
                args.addAll(list.getArguments());
            }
            return new ArgumentListNode(args);
        }

        @Override
        public Object visitArgList(FlowScriptFunctionsParser.ArgListContext ctx) {
            List<ExpressionNode> args = new ArrayList<>();
            for (FlowScriptFunctionsParser.ExpressionContext e : ctx.expression()) {
                args.add((ExpressionNode) e.accept(this));
            }
            return new ArgumentListNode(args);
        }

        // =========================
        // PRIMARY / LITERALES
        // =========================
        @Override
        public Object visitPrimary(FlowScriptFunctionsParser.PrimaryContext ctx) {
            if (ctx.DECIMAL() != null)   return new DecimalLiteralNode(Double.parseDouble(ctx.DECIMAL().getText()));
            if (ctx.INTEGER() != null)   return new IntegerLiteralNode(Integer.parseInt(ctx.INTEGER().getText()));
            if (ctx.TRUE() != null)      return new BooleanLiteralNode(true);
            if (ctx.FALSE() != null)     return new BooleanLiteralNode(false);
            if (ctx.NULL() != null)      return new NullLiteralNode();
            if (ctx.STRING() != null) {
                String raw = ctx.STRING().getText();
                String unq = raw.length() >= 2 ? raw.substring(1, raw.length() - 1) : raw;
                return new StringLiteralNode(unq);
            }
            if (ctx.ID() != null)        return new IdentifierNode(ctx.ID().getText());
            if (ctx.listLiteral() != null)   return ctx.listLiteral().accept(this);
            if (ctx.objectLiteral() != null) return ctx.objectLiteral().accept(this);
            if (ctx.expression() != null)    return ctx.expression().accept(this); // (expr)
            throw new UnsupportedOperationException("Primary no soportado: " + ctx.getText());
        }

        // --- list/object literals ---
        @Override
        public Object visitListLiteral(FlowScriptFunctionsParser.ListLiteralContext ctx) {
            List<ExpressionNode> elems = new ArrayList<>();
            if (ctx.expression() != null) {
                for (FlowScriptFunctionsParser.ExpressionContext e : ctx.expression()) {
                    elems.add((ExpressionNode) e.accept(this));
                }
            }
            return new ListLiteralNode(elems);
        }

        @Override
        public Object visitObjectLiteral(FlowScriptFunctionsParser.ObjectLiteralContext ctx) {
            List<ObjectMemberNode> members = new ArrayList<>();
            if (ctx.objPair() != null) {
                for (FlowScriptFunctionsParser.ObjPairContext p : ctx.objPair()) {
                    members.add((ObjectMemberNode) p.accept(this));
                }
            }
            return new ObjectLiteralNode(new ObjectMemberListNode(members));
        }

        @Override
        public Object visitObjPair(FlowScriptFunctionsParser.ObjPairContext ctx) {
            String key = ctx.ID().getText();
            ExpressionNode value = (ExpressionNode) ctx.expression().accept(this);
            return new ObjectMemberNode(key, value);
        }

        // =========================
        // UTIL
        // =========================
        /**
         * Plegado izquierda para secuencias binarias etiquetadas:
         *   lhs (op rhs)*  => (((lhs op rhs1) op rhs2) ...)
         */
        private ExpressionNode foldLeft(
                ParseTree lhs,
                List<? extends ParseTree> rhs,
                List<? extends Token> ops) {

            ExpressionNode acc = (ExpressionNode) lhs.accept(this);
            for (int i = 0; i < rhs.size(); i++) {
                String op = ops.get(i).getText();
                ExpressionNode right = (ExpressionNode) rhs.get(i).accept(this);
                acc = new BinaryExpressionNode(acc, op, right);
            }
            return acc;
        }
    }
}
