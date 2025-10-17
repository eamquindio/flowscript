package com.flowscript.sintactic.parsers.functions.literales;

import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;
import com.flowscript.sintactic.IParser;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ParserContext;
import com.flowscript.sintactic.ast.functions.expresiones.ObjectMemberNode;
import com.flowscript.sintactic.ast.functions.listas_argumentos.ObjectMemberListNode;
import com.flowscript.sintactic.ast.functions.literales.ObjectLiteralNode;
import com.flowscript.sintactic.parsers.functions.listas_argumentos.ObjectMemberListParser;
import com.flowscript.sintactic.parsers.functions.listas_argumentos.ObjectMemberParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser para literales de objeto.
 *
 * <h3>Gramática BNF:</h3>
 * <pre>
 * ObjectLiteral ::= '{' ObjectMemberList? '}'
 * </pre>
 *
 * <h3>Categoría:</h3>
 * 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES
 * Nivel 18: Literales - Objetos
 *
 * <h3>Tarea del Estudiante:</h3>
 * Implementar el método {@code parse()} siguiendo la gramática BNF.
 * Debe reconocer literales de objeto como: { nombre: "Ana", edad: 25 }
 * Un objeto vacío {} también es válido.
 *
 * @see ObjectLiteralNode
 * @see com.flowscript.sintactic.parsers.functions.listas_argumentos.ObjectMemberListParser
 */
public class ObjectLiteralParser implements IParser<ObjectLiteralNode> {

    private ObjectMemberParser memberParser;

    private ObjectMemberParser getMemberParser() {
        if (memberParser == null) {
            memberParser = new ObjectMemberParser();
        }
        return memberParser;
    }

    @Override
    public ObjectLiteralNode parse(ParserContext context) throws Parser.ParseException {
        Token lb = context.consume(TokenType.LEFT_BRACE);

        if (context.check(TokenType.RIGHT_BRACE)) {
            context.consume(TokenType.RIGHT_BRACE);
            return new ObjectLiteralNode(lb);
        }
        List<ObjectMemberListNode.ObjectMemberNode> lst = new ArrayList<>();

        ObjectMemberNode f = getMemberParser().parse(context);
        ObjectMemberListNode.ObjectMemberNode tmp = new ObjectMemberListNode.ObjectMemberNode(lb, f.getKey(), f.getValue());
        lst.add(tmp);

        while (context.checkValue(",")) {
            context.consume(TokenType.COMMA);
            ObjectMemberNode m = getMemberParser().parse(context);
            ObjectMemberListNode.ObjectMemberNode n = new ObjectMemberListNode.ObjectMemberNode(lb, m.getKey(), m.getValue());
            lst.add(n);
        }
        context.consume(TokenType.RIGHT_BRACE);

        ObjectMemberListNode ml = new ObjectMemberListNode(lb, lst);
        ObjectLiteralNode out = new ObjectLiteralNode(lb, ml);
        return out;
    }
}
