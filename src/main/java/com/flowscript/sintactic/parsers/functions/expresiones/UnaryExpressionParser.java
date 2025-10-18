package com.flowscript.sintactic.parsers.functions.expresiones;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ExpressionNode;
// Ajusta el nombre si tu nodo unario se llama distinto:
import com.flowscript.sintactic.ast.functions.expresiones.UnaryExpressionNode;

/**
 * Unarios prefix (!, -, +). Si no hay unario, delega a Primary.
 */
public class UnaryExpressionParser implements IParser<ExpressionNode> {

    private final PrimaryExpressionParser primary;

    public UnaryExpressionParser(IParser<ExpressionNode> root) {
        this.primary = new PrimaryExpressionParser(root);
    }

    @Override
    public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
        Token t = context.getCurrentToken();
        if (t == null) throw new Parser.ParseException("Se esperaba expresión");

        if (t.getType() == TokenType.NOT || t.getType() == TokenType.MINUS || t.getType() == TokenType.PLUS) {
            Token op = t;
            context.consume();
            ExpressionNode right = this.parse(context); // asociatividad a derecha
            return new UnaryExpressionNode(op, right);  // <-- cambia si tu AST usa otro nombre
        }

        return primary.parse(context);
    }
}
