grammar FlowScriptFunctions;

@header { package edu.eam.ingesoft.tlf; }

/* ===================== PARSER ===================== */

functionProgram
  : (functionDecl)* EOF
  ;

functionDecl
  : FUNCTION ID LPAREN paramListOpt? RPAREN returnTypeOpt block
  ;

paramListOpt
  : param (COMMA param)*
  ;

param
  : ID COLON typeRef
  ;

returnTypeOpt
  : (ARROW typeRef)?      // -> type  (o void omitible)
  ;

typeRef
  : INTEGER_T | DECIMAL_T | BOOLEAN_T | TEXT_T | LIST_T | OBJECT_T | VOID_T
  ;

/* -------- Statements -------- */

block
  : LBRACE statement* RBRACE
  ;

statement
  : ifStatement
  | whileStatement
  | forEachStatement
  | forRangeStatement
  | tryCatchStatement
  | throwStatement
  | returnStatement
  | BREAK SEMI?
  | CONTINUE SEMI?
  | SEMI                                       // empty
  | expression SEMI?                           // incluye asignación como expr
  ;

ifStatement
  : IF expression block (ELSE_IF expression block)* (ELSE block)?
  ;

whileStatement
  : WHILE expression block
  ;

forEachStatement
  : FOR EACH ID IN expression block
  ;

forRangeStatement
  : FOR ID FROM expression TO expression (STEP expression)? block
  ;

tryCatchStatement
  : TRY block (CATCH LPAREN catchVar RPAREN block)+
  ;

catchVar
  : ID (COLON typeRef)?
  ;
throwStatement
  : THROW expression SEMI?
  ;

returnStatement
  : RETURN expression? SEMI?
  ;

/* -------- Expressions (con asignación nivel 1, derecha-asociativa) -------- */

expression
  : assignment
  ;

assignment
  : lvalue ASSIGN assignment           #assignExpr      // derecha-asoc
  | orExpr                             #nonAssignExpr
  ;

lvalue
  : ID (DOT ID | LBRACK expression RBRACK)*
  ;

orExpr
  : andExpr (OR andExpr)*
  ;

andExpr
  : eqExpr (AND eqExpr)*
  ;

eqExpr
  : relExpr ((EQ | NEQ) relExpr)*
  ;

relExpr
  : addExpr ((LT | GT | LE | GE) addExpr)*
  ;

addExpr
  : mulExpr ((ADD | SUB) mulExpr)*
  ;

mulExpr
  : unaryExpr ((MUL | DIV | MOD) unaryExpr)*
  ;

unaryExpr
  : NOT unaryExpr
  | SUB unaryExpr
  | postfixExpr
  ;

postfixExpr
  : primary
    ( DOT ID
    | LBRACK expression RBRACK
    | LPAREN argListOpt? RPAREN
    )*
  ;

argListOpt
  : expression (COMMA expression)*
  ;

primary
  : LPAREN expression RPAREN
  | listLiteral
  | objectLiteral
  | literal
  | ID
  ;

listLiteral
  : LBRACK (expression (COMMA expression)*)? RBRACK
  ;

objectLiteral
  : LBRACE (objectPair (COMMA objectPair)*)? RBRACE
  ;

objectPair
  : ID COLON expression
  ;

literal
  : INT | DECIMAL | STRING | BOOL | NULL_T
  ;

/* ===================== LEXER ===================== */
/* Palabras clave */
FUNCTION  : 'function';
RETURN    : 'return';
IF        : 'if';
ELSE_IF   : 'else_if';
ELSE      : 'else';
WHILE     : 'while';
FOR       : 'for';
EACH      : 'each';
IN        : 'in';
FROM      : 'from';
TO        : 'to';
STEP      : 'step';
TRY       : 'try';
CATCH     : 'catch';
THROW     : 'throw';
BREAK     : 'break';
CONTINUE  : 'continue';

/* Tipos */
INTEGER_T : 'integer';
DECIMAL_T : 'decimal';
BOOLEAN_T : 'boolean';
TEXT_T    : 'text';
LIST_T    : 'list';
OBJECT_T  : 'object';
VOID_T    : 'void';

/* Lógicos palabra */
AND       : 'and';
OR        : 'or';
NOT       : 'not';

/* Operadores y signos */
ADD: '+'; SUB: '-'; MUL: '*'; DIV: '/'; MOD: '%';
LT: '<'; GT: '>'; LE: '<='; GE: '>=';
EQ: '=='; NEQ: '!=';
ASSIGN: '=';
LPAREN:'(' ; RPAREN:')';
LBRACE:'{' ; RBRACE:'}';
LBRACK:'[' ; RBRACK:']';
DOT:'.' ; COMMA:',' ; COLON:':' ; SEMI:';';
ARROW: '->';

/* Literales */
BOOL   : 'true' | 'false';
NULL_T : 'null';

DECIMAL
  : [0-9] [0-9_]* '.' [0-9] [0-9_]* ([eE] [+-]? [0-9] [0-9_]*)?
  | '.' [0-9] [0-9_]* ([eE] [+-]? [0-9] [0-9_]*)?
  | [0-9] [0-9_]* '.' ([eE] [+-]? [0-9] [0-9_]*)?
  ;

INT
  : [0-9] [0-9_]*
  ;

STRING
  : '"' ( '\\' ESC | ~["\\] )* '"'
  ;
fragment ESC : [btnr"\\/] | 'u' HEX HEX HEX HEX ;
fragment HEX : [0-9a-fA-F] ;

/* Identificadores */
ID : [a-zA-Z_][a-zA-Z0-9_]* ;

/* Comentarios/WS */
WS : [ \t\r\n\f]+ -> skip ;
LINE_COMMENT_HASH  : '#' ~[\r\n]* -> skip ;
LINE_COMMENT_SLASH : '//' ~[\r\n]* -> skip ;
BLOCK_COMMENT
  : '/*' .*? '*/' -> skip
  ;

/* Reservadas futuras (no usadas) */
ASYNC  : 'async'   -> skip ;
AWAIT  : 'await'   -> skip ;
EVENT  : 'event'   -> skip ;
CLASS  : 'class'   -> skip ;