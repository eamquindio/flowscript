grammar FlowScriptFunctions;

// Package declaration for generated code
@header {
    package edu.eam.ingesoft.tlf;
}

functionProgram
    : declaracionFuncion* EOF
    ;

declaracionFuncion
    : FUNCTION IDENTIFICADOR LPAREN listaParametros? RPAREN (ARROW tipo)? LBRACE estado* RBRACE
    ;

listaParametros
    : parametro (COMMA parametro)*
    ;
parametro
    : IDENTIFICADOR COLON tipo
    ;

tipo
    : INTEGER
    | DECIMAL
    | BOOLEAN
    | TEXT
    | LIST
    | OBJECT
    | VOID
    ;

estado
    : ( asignacion
      | estadoSi
      | estadoMientras
      | estadoParaCada
      | estadoParaRango
      | estadoIntentarCapturar
      | estadoRetornar
      | estadoRomper
      | estadoContinuar
      | estadoLanzar
      | expresion
      ) SEMICOLON?   // ';' opcional
    ;

asignacion
    : IDENTIFICADOR ASSIGN expresion
    ;

estadoSi
    : IF expresion LBRACE estado* RBRACE (ELSE_IF expresion LBRACE estado* RBRACE)* (ELSE LBRACE estado* RBRACE)?
    ;

estadoMientras
    : WHILE expresion LBRACE estado* RBRACE
    ;

estadoParaCada
    : FOR EACH IDENTIFICADOR IN expresion LBRACE estado* RBRACE
    ;

estadoParaRango
    : FOR IDENTIFICADOR FROM expresion TO expresion (STEP expresion)? LBRACE estado* RBRACE
    ;

estadoIntentarCapturar
    : TRY LBRACE estado* RBRACE (CATCH LPAREN IDENTIFICADOR RPAREN LBRACE estado* RBRACE)+
    ;

estadoRetornar
    : RETURN expresion?
    ;

estadoRomper
    : BREAK
    ;
estadoContinuar
    : CONTINUE
    ;

estadoLanzar
    : THROW expresion
    ;

expresion
    : expresionAsignacion
    ;

expresionAsignacion
    : expresionOLogica (ASSIGN expresionAsignacion)?
    ;

expresionOLogica
    : expresionYLogica (OR_OP expresionYLogica)*
    ;

expresionYLogica
    : expresionIgualdad (AND_OP expresionIgualdad)*
    ;

expresionIgualdad
    : expresionRelacional ((EQ | NE) expresionRelacional)*
    ;

expresionRelacional
    : expresionAditiva ((LT | GT | LE | GE) expresionAditiva)*
    ;

expresionAditiva
    : expresionMultiplicativa ((PLUS | MINUS) expresionMultiplicativa)*
    ;

expresionMultiplicativa
    : expresionUnaria ((MULT | DIV | MOD) expresionUnaria)*
    ;

expresionUnaria
    : (MINUS | NOT) expresionUnaria
    | expresionPostfija
    ;

expresionPostfija
    : expresionPrimaria accesoPostfijo*
    ;

accesoPostfijo
    : DOT IDENTIFICADOR
    | LBRACKET expresion RBRACKET
    | LPAREN (expresion (COMMA expresion)*)? RPAREN
    ;

expresionPrimaria
    : literal
    | IDENTIFICADOR
    | LPAREN expresion RPAREN
    | literalLista
    | literalObjeto
    ;

literalLista
    : LBRACKET (expresion (COMMA expresion)*)? RBRACKET
    ;

literalObjeto
    : LBRACE (propiedad (COMMA propiedad)*)? RBRACE
    ;
propiedad
    : IDENTIFICADOR COLON expresion
    ;

literal
    : LITERAL_ENTERO
    | LITERAL_DECIMAL
    | LITERAL_BOOLEANO
    | LITERAL_TEXTO
    | NULL
    ;

LITERAL_ENTERO
    : [0-9]+ ('_' [0-9]+)*
    ;
LITERAL_DECIMAL
    : [0-9]+ '.' [0-9]+ (('e' | 'E') ('+' | '-')? [0-9]+)?
    ;
LITERAL_BOOLEANO
    : 'true' | 'false'
    ;
LITERAL_TEXTO
    : '"' (~["\\\r\n] | '\\' [nrt"\\])* '"'
    ;

FUNCTION : 'function';
IF       : 'if';
ELSE_IF  : 'else_if';
ELSE     : 'else';
WHILE    : 'while';
FOR      : 'for';
EACH     : 'each';
IN       : 'in';
FROM     : 'from';
TO       : 'to';
STEP     : 'step';
TRY      : 'try';
CATCH    : 'catch';
RETURN   : 'return';
BREAK    : 'break';
CONTINUE : 'continue';
THROW    : 'throw';

OR_OP    : 'or';
AND_OP   : 'and';
NOT      : 'not';
NULL     : 'null';

INTEGER  : 'integer';
DECIMAL  : 'decimal';
BOOLEAN  : 'boolean';
TEXT     : 'text';
LIST     : 'list';
OBJECT   : 'object';
VOID     : 'void';

EQ       : '==';
NE       : '!=';
LE       : '<=';
GE       : '>=';
LT       : '<';
GT       : '>';
ASSIGN   : '=';
PLUS     : '+';
MINUS    : '-';
MULT     : '*';
DIV      : '/';
MOD      : '%';
LPAREN   : '(';
RPAREN   : ')';
LBRACE   : '{';
RBRACE   : '}';
LBRACKET : '[';
RBRACKET : ']';
DOT      : '.';
COMMA    : ',';
COLON    : ':';
SEMICOLON: ';';
ARROW    : '->';

IDENTIFICADOR
    : [a-zA-Z_] [a-zA-Z0-9_]*
    ;

WS
    : [ \t\r\n]+ -> skip
    ;

COMENTARIO_LINEA
    : '#' ~[\r\n]* -> skip
    ;

COMENTARIO_BLOQUE
    : '/*' .*? '*/' -> skip
    ;