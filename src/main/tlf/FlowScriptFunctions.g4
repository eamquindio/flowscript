grammar FlowScriptFunctions;

@header {
    package edu.eam.ingesoft.tlf;
}

programa
    : (funcion)* EOF
    ;

functionProgram
    : (funcion)* EOF
    ;

funcion
    : FUNCTION ID LPAREN parametros? RPAREN (FLECHA tipo)? bloque
    ;

parametros
    : parametro (COMA parametro)*
    ;

parametro
    : ID DOSPUNTOS tipo
    ;

tipo
    : INTEGER_TYPE
    | DECIMAL_TYPE
    | BOOLEAN_TYPE
    | TEXT_TYPE
    | LIST_TYPE
    | OBJECT_TYPE
    | VOID_TYPE
    ;

bloque
    : LLLAVE sentencias? RLLAVE
    ;

sentencias
    : sentencia*
    ;

sentencia
    : si
    | mientras
    | paraCada
    | paraDesde
    | intentarCapturar
    | lanzar
    | retornar
    | romper
    | continuar
    | expr
    | bloque
    ;

si
    : IF expr bloque (ELSE_IF expr bloque)* (ELSE bloque)?
    ;

mientras
    : WHILE expr bloque
    ;

paraCada
    : FOR EACH ID IN expr bloque
    ;

paraDesde
    : FOR ID FROM expr TO expr (STEP expr)? bloque
    ;

intentarCapturar
    : TRY bloque (CATCH LPAREN ID RPAREN bloque)+
    ;

lanzar
    : THROW expr
    ;

retornar
    : RETURN expr?
    ;

romper
    : BREAK
    ;

continuar
    : CONTINUE
    ;

expr
    : expr ASIGN expr
    | expr OR expr
    | expr AND expr
    | expr PREGUNTA expr DOSPUNTOS expr
    | expr (IGUAL | DIFERENTE) expr
    | expr (MENOR | MAYOR | MENORIGUAL | MAYORIGUAL) expr
    | expr (MAS | MENOS) expr
    | expr (POR | DIVIDIR | MODULO) expr
    | NOT expr
    | MENOS expr
    | expr PUNTO ID
    | expr LBRACK expr RBRACK
    | expr llamada
    | primario
    ;


argumentos
    : expr (COMA expr)*
    ;

primario
    : ID
    | literal
    | objeto
    | lista
    | LPAREN expr RPAREN
    ;

literal
    : ENTERO
    | DECIMAL
    | TRUE
    | FALSE
    | NULL
    | CADENA
    ;

objeto
    : LLLAVE pares? RLLAVE
    ;

pares
    : par (COMA par)*
    ;

par
    : (ID | CADENA) DOSPUNTOS expr
    ;

lista
    : LBRACK elementos? RBRACK
    ;

elementos
    : expr (COMA expr)*
    ;

FUNCTION      : 'function';
RETURN        : 'return';
IF            : 'if';
ELSE_IF       : 'else_if';
ELSE          : 'else';
TRY           : 'try';
CATCH         : 'catch';
THROW         : 'throw';
WHILE         : 'while';
FOR           : 'for';
EACH          : 'each';
IN            : 'in';
FROM          : 'from';
TO            : 'to';
STEP          : 'step';
BREAK         : 'break';
CONTINUE      : 'continue';
AND           : 'and';
OR            : 'or';
NOT           : 'not';

INTEGER_TYPE  : 'integer';
DECIMAL_TYPE  : 'decimal';
BOOLEAN_TYPE  : 'boolean';
TEXT_TYPE     : 'text';
LIST_TYPE     : 'list';
OBJECT_TYPE   : 'object';
VOID_TYPE     : 'void';

TRUE          : 'true';
FALSE         : 'false';
NULL          : 'null';

FLECHA        : '->';
LLLAVE        : '{';
RLLAVE        : '}';
LPAREN        : '(';
RPAREN        : ')';
LBRACK        : '[';
RBRACK        : ']';
COMA          : ',';
DOSPUNTOS     : ':';
PUNTO         : '.';
ASIGN         : '=';
MAS           : '+';
MENOS         : '-';
POR           : '*';
DIVIDIR       : '/';
MODULO        : '%';
MENOR         : '<';
MAYOR         : '>';
MENORIGUAL    : '<=';
MAYORIGUAL    : '>=';
IGUAL         : '==';
DIFERENTE     : '!=';

PREGUNTA      : '?';

CADENA
    : '"' ( '\\' ('"' | '\\' | 'n' | 'r' | 't' | 'u' HEX HEX HEX HEX)
           | ~('"'|'\\')
           )* '"'
    ;

DECIMAL
    : DIGITO+ '.' DIGITO+ (('e'|'E') ('+'|'-')? DIGITO+)?
    | DIGITO+ (('e'|'E') ('+'|'-')? DIGITO+)
    ;

ENTERO
    : DIGITO+ ('_' DIGITO+)*
    ;

ID
    : LETRA (LETRA | DIGITO | '_')*
    ;

fragment LETRA
    : [a-zA-Z]
    ;

fragment DIGITO
    : [0-9]
    ;

fragment HEX
    : [0-9a-fA-F]
    ;

COMENTARIO_LINEA
    : '#' ~[\r\n]* -> skip
    ;

COMENTARIO_BLOQUE
    : '/*' .*? '*/' -> skip
    ;

ESPACIO
    : [ \t\r\n]+ -> skip
    ;



llamada: LPAREN argumentos? RPAREN ;
