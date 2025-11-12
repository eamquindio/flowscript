package edu.eam.ingesoft.tlf.flowscript.parser.parsers.types;

import edu.eam.ingesoft.tlf.flowscript.lexer.TokenType;
import edu.eam.ingesoft.tlf.flowscript.parser.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.types.TypeNode;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.types.TypeNode.TypeKind;

/**
 * Parser para tipos de datos.
 * Type ::= 'entero' | 'decimal' | 'booleano' | 'texto' | 'lista' | 'objeto' | 'vacio'
 */
public class TypeParser implements IParser<TypeNode> {

    @Override
    public TypeNode parse(ParserContext context) {
        TypeKind kind;

        if (context.match(TokenType.ENTERO)) {
            kind = TypeKind.ENTERO;
        } else if (context.match(TokenType.DECIMAL)) {
            kind = TypeKind.DECIMAL;
        } else if (context.match(TokenType.BOOLEANO)) {
            kind = TypeKind.BOOLEANO;
        } else if (context.match(TokenType.TEXTO)) {
            kind = TypeKind.TEXTO;
        } else if (context.match(TokenType.LISTA)) {
            kind = TypeKind.LISTA;
        } else if (context.match(TokenType.OBJETO)) {
            kind = TypeKind.OBJETO;
        } else if (context.match(TokenType.VACIO)) {
            kind = TypeKind.VACIO;
        } else {
            throw new ParserException(
                    "Expected type",
                    context.current(),
                    "entero, decimal, booleano, texto, lista, objeto, or vacio"
            );
        }

        return new TypeNode(kind);
    }
}
