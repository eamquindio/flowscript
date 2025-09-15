grammar FlowScriptFunctions;


@header {
package edu.eam.ingesoft.tlf;
}

/*
* GRAMATICA DE FUNCIONES PARA FLOWSCRIPT
*
* Este archivo define la gramatica completa para el sistema de funciones
* del lenguaje FlowScript, incluyendo:
* -- Declaración de funciones
* - Parámetros tipados
* - Tipos de retorno
* - Cuerpo de funciones con statements
* - Expresiones y operadores
* - Control de flujo dentro de funciones
    */

// =======================
// LEXER RULES (TOKENS)
// ============================

FUNCION    : 'function';
RETURN      : 'return';
IF          : 'if';
ELSE        : 'else';
ELSE_IF     : 'else_if';
WHILE       : 'while';
FOR         : 'for';
EACH        : 'each';
IN          : 'in';
FROM        : 'from';
TO          : 'to';
STEP        : 'step';
TRY         : 'try';
CATCH       : 'catch';
THROW       : 'throw';
BREAK       : 'break';
CONTINUE    : 'continue';
NULL        : 'null';


AND         : 'and';
OR          : 'or';
NOT         : 'not';


ENTERO_TYPE : 'integer';
DECIMAL_TYPE : 'decimal';
BOOLEAN_TYPE : 'boolean';
TEXT_TYPE    : 'text';
LIST_TYPE    : 'list';
OBJECT_TYPE  : 'object';
VOID_TYPE    : 'void';


LPAREN  : '(' ;
RPAREN  : ')' ;
LBRACE  : '{' ;
RBRACE  : '}' ;
LBRACK  : '[' ;
RBRACK  : ']' ;
COMA   : ',' ;
DOSPUNTOS   : ':' ;
FLECHA   : '->' ;
ASSIGN  : '=' ;
PUNTOYCOMA : ';' ;
PUNTO     : '.' ;

/* Operadores */
IGUAL      : '==' ;
DIFERENTE  : '!=' ;
MENORIGUAL     : '<=' ;
MAYORIGUAL     : '>=' ;
MENOR   : '<' ;
MAYOR   : '>' ;
MAS     : '+' ;
MENOS   : '-' ;
MULT    : '*' ;
DIV     : '/' ;
MOD     : '%' ;


DECIMAL
: [0-9]+ '.' [0-9]+
;
NUMERO
: [0-9]+
;
STRING
: '"' ( ~["\\] | '\\' . )* '"'
;

BOOLEAN
: 'true' | 'false'
;

/* Identificdor  */
IDENTIFICADOR
: [a-zA-Z_][a-zA-Z_0-9]*
;

/* Comentarios y espacios */
HASH_COMMENT
: '#' ~[\r\n]* -> skip
;
LINE_COMMENT
: '//' ~[\r\n]* -> skip
;
BLOCK_COMMENT
: '/*' .*? '*/' -> skip
;
WS
: [ \t\r\n]+ -> skip
;


/* ----------------------------
REGLA DE ENTRADA
---------------------------- */
functionProgram
: (declaracionFuncion | sentenciaTope)* EOF
;

/* Sentencias a nivel top  */
sentenciaTope
: sentencia
;

/* ----------------------------
DECLARCION DE FUNCIONES
--------------------------- */
declaracionFuncion
: FUNCION IDENTIFICADOR LPAREN listaParametros? RPAREN tipoRetorno? bloque
;

/* Lista de params */
listaParametros
: parametro (COMA parametro)*
;

parametro
: IDENTIFICADOR DOSPUNTOS tipoNombre
;

/* Tipo de retorn */
tipoRetorno
: FLECHA tipoNombre
;


bloque
: LBRACE (sentencia (PUNTOYCOMA)?)* RBRACE
;

/* Sentencas */
sentencia
: asignacion
| expresionStatement
| sentenciaIf
| sentenciaWhile
| sentenciaForEach
| sentenciaForRange
| sentenciaReturn
| sentenciaTryCatch
| sentenciaThrow
| sentenciaBreak
| sentenciaContinue
;

/* Asignación */
asignacion
: lvalue ASSIGN expresion
;

/* lvalue: permite acceso a campos y a índices anidados */
lvalue
: IDENTIFICADOR
| lvalue PUNTO IDENTIFICADOR
| lvalue LBRACK expresion RBRACK
;

/* Expresión como sentencia */
expresionStatement
: expresion PUNTOYCOMA?
;

/* Return */
sentenciaReturn
: RETURN expresion? PUNTOYCOMA?
;

/* If / else_if / else  */
sentenciaIf
: IF expresion bloque (parteElseIf)* (ELSE bloque)?
;

parteElseIf
: ELSE_IF expresion bloque
;

/* While */
sentenciaWhile
: WHILE expresion bloque
;

/* For-each: */
sentenciaForEach
: FOR EACH IDENTIFICADOR IN expresion bloque
;

/* For-range*/
sentenciaForRange
: FOR IDENTIFICADOR FROM expresion TO expresion (STEP expresion)? bloque
;

/* Try / Catch */
sentenciaTryCatch
: TRY bloque (CATCH LPAREN IDENTIFICADOR RPAREN bloque)+
;

/* Throw */
sentenciaThrow
: THROW expresion PUNTOYCOMA?
;

/* Break / Continue */
sentenciaBreak
: BREAK PUNTOYCOMA?
;

sentenciaContinue
: CONTINUE PUNTOYCOMA?
;


expresion
: expresionOr
;

expresionOr
: expresionAnd ( OR expresionAnd )*
;

expresionAnd
: expresionEq ( AND expresionEq )*
;

expresionEq
: expresionRel ( (IGUAL | DIFERENTE) expresionRel )*
;

expresionRel
: expresionAdd ( (MENOR | MENORIGUAL | MAYOR | MAYORIGUAL) expresionAdd )*
;

expresionAdd
: expresionMul ( (MAS | MENOS) expresionMul )*
;

expresionMul
: expresionUnary ( (MULT | DIV | MOD) expresionUnary )*
;

expresionUnary
: (NOT | MENOS) expresionUnary
| expresionPostfijo
;

/* Postfijos encadenables  */
expresionPostfijo
: expresionPrimario ( (PUNTO IDENTIFICADOR)
| (PUNTO IDENTIFICADOR LPAREN listaArgumentos? RPAREN)
| (LPAREN listaArgumentos? RPAREN)
| (LBRACK expresion RBRACK)
)*
;

/* Primarioss */
expresionPrimario
: literal
| IDENTIFICADOR
| LPAREN expresion RPAREN
| listaLiteral
| objetoLiteral
;

/* Argumentos en llamadas */
listaArgumentos
: expresion (COMA expresion)*
;

/* ----------------------------
LITERALES
---------------------------- */

literal
: NULL
| BOOLEAN
| DECIMAL
| NUMERO
| STRING
;

/* Lista literal */
listaLiteral
: LBRACK (expresion (COMA expresion)*)? RBRACK
;

/* Objeto literal (clave: valor)*/
objetoLiteral
: LBRACE (entradaObjeto (COMA entradaObjeto)*)? RBRACE
;

entradaObjeto
: (IDENTIFICADOR | STRING) (DOSPUNTOS | ASSIGN) expresion
;


/* ----------------------------
TIPOS---------------------------- */

tipoNombre
: ENTERO_TYPE
| DECIMAL_TYPE
| BOOLEAN_TYPE
| TEXT_TYPE
| LIST_TYPE
| OBJECT_TYPE
| VOID_TYPE
;

