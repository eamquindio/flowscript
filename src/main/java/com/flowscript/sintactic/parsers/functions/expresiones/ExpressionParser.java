package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.sintactic.ast.functions.expresiones.*;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ArgumentListNode;
import com.flowscript.sintactic.ast.functions.literales.*;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsBaseVisitor;
import edu.eam.ingesoft.tlf.FlowScriptFunctionsParser;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {

    /** Visitor de expresiones (usado por ProgramParser.FunctionsAstVisitor) */
    public static class FunctionsExpressionVisitor extends FlowScriptFunctionsBaseVisitor<Object> {

        // --- Primary / literales / id / paréntesis ---
        @Override
        public Object visitPrimaryExpression(FlowScriptFunctionsParser.PrimaryExpressionContext ctx) {
            if (ctx.IntegerLiteral() != null) return new IntegerLiteralNode(Integer.parseInt(ctx.IntegerLiteral().getText()));
            if (ctx.DecimalLiteral() != null) return new DecimalLiteralNode(Double.parseDouble(ctx.DecimalLiteral().getText()));
            if (ctx.BooleanLiteral() != null) return new BooleanLiteralNode(Boolean.parseBoolean(ctx.BooleanLiteral().getText()));
            if (ctx.NullLiteral() != null)    return new NullLiteralNode();
            if (ctx.StringLiteral() != null) {
                String raw = ctx.StringLiteral().getText();
                String unq = raw.length() >= 2 ? raw.substring(1, raw.length() - 1) : raw; // unquote simple
                return new StringLiteralNode(unq);
            }
            if (ctx.Identifier() != null)     return new IdentifierNode(ctx.Identifier().getText());
            if (ctx.expression() != null)     return ctx.expression().accept(this);
            throw new UnsupportedOperationException("Primary no soportado: " + ctx.getText());
        }

        // --- Unarios ---
        @Override
        public Object visitUnaryExpression(FlowScriptFunctionsParser.UnaryExpressionContext ctx) {
            if (ctx.op != null) {
                ExpressionNode rhs = (ExpressionNode) ctx.unaryExpression().accept(this);
                return new UnaryExpressionNode(ctx.op.getText(), rhs);
            }
            return ctx.postfixExpression().accept(this);
        }

        // --- Postfix: llamadas, propiedad, indexación ---
        @Override
        public Object visitPostfixExpression(FlowScriptFunctionsParser.PostfixExpressionContext ctx) {
            ExpressionNode base = (ExpressionNode) ctx.primaryExpression().accept(this);
            for (FlowScriptFunctionsParser.PostfixPartContext p : ctx.postfixPart()) {
                if (p.arguments() != null) {
                    ArgumentListNode args = (ArgumentListNode) p.arguments().accept(this);
                    base = new FunctionCallNode(base, args);
                } else if (p.propertyAccess() != null) {
                    String prop = p.propertyAccess().Identifier().getText();
                    base = new PropertyAccessNode(base, prop);
                } else if (p.indexAccess() != null) {
                    ExpressionNode idx = (ExpressionNode) p.indexAccess().expression().accept(this);
                    base = new IndexAccessNode(base, idx);
                }
            }
            return base;
        }

        @Override
        public Object visitArguments(FlowScriptFunctionsParser.ArgumentsContext ctx) {
            List<ExpressionNode> list = new ArrayList<>();
            if (ctx.argumentList() != null) {
                list.addAll(((ArgumentListNode) ctx.argumentList().accept(this)).getArguments());
            }
            return new ArgumentListNode(list);
        }

        @Override
        public Object visitArgumentList(FlowScriptFunctionsParser.ArgumentListContext ctx) {
            List<ExpressionNode> args = new ArrayList<>();
            for (FlowScriptFunctionsParser.ExpressionContext e : ctx.expression()) {
                args.add((ExpressionNode) e.accept(this));
            }
            return new ArgumentListNode(args);
        }

        // --- Binarios por precedencia (fold-left) ---
        @Override
        public Object visitMultiplicativeExpression(FlowScriptFunctionsParser.MultiplicativeExpressionContext ctx) {
            return foldLeft(ctx.unaryExpression(), ctx.op);
        }

        @Override
        public Object visitAdditiveExpression(FlowScriptFunctionsParser.AdditiveExpressionContext ctx) {
            return foldLeft(ctx.multiplicativeExpression(), ctx.op);
        }

        @Override
        public Object visitRelationalExpression(FlowScriptFunctionsParser.RelationalExpressionContext ctx) {
            return foldLeft(ctx.additiveExpression(), ctx.op);
        }

        @Override
        public Object visitEqualityExpression(FlowScriptFunctionsParser.EqualityExpressionContext ctx) {
            return foldLeft(ctx.relationalExpression(), ctx.op);
        }

        @Override
        public Object visitLogicalAndExpression(FlowScriptFunctionsParser.LogicalAndExpressionContext ctx) {
            return foldLeft(ctx.equalityExpression(), ctx.op);
        }

        @Override
        public Object visitLogicalOrExpression(FlowScriptFunctionsParser.LogicalOrExpressionContext ctx) {
            return foldLeft(ctx.logicalAndExpression(), ctx.op);
        }

        @Override
        public Object visitTernaryExpression(FlowScriptFunctionsParser.TernaryExpressionContext ctx) {
            if (ctx.QMARK() == null) return ctx.logicalOrExpression().accept(this);
            ExpressionNode cond  = (ExpressionNode) ctx.logicalOrExpression().accept(this);
            ExpressionNode thenE = (ExpressionNode) ctx.expression(0).accept(this);
            ExpressionNode elseE = (ExpressionNode) ctx.expression(1).accept(this);
            return new TernaryExpressionNode(cond, thenE, elseE);
        }

        @Override
        public Object visitExpression(FlowScriptFunctionsParser.ExpressionContext ctx) {
            return ctx.ternaryExpression().accept(this);
        }

        // util: pliega secuencias a izq (a op b op c ...)
        private ExpressionNode foldLeft(List<? extends org.antlr.v4.runtime.tree.ParseTree> terms,
                                        List<? extends Token> ops) {
            ExpressionNode acc = (ExpressionNode) terms.get(0).accept(this);
            for (int i = 1; i < terms.size(); i++) {
                String op = ops.get(i - 1).getText();
                ExpressionNode right = (ExpressionNode) terms.get(i).accept(this);
                acc = new BinaryExpressionNode(acc, op, right);
            }
            return acc;
        }
    }
}
