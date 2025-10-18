grammar FlowScriptFunctions;

/*
 * Gramática de funciones de FlowScript, ajustada a los tests:
 * - functionProgram como regla inicial
 * - Parámetros tipados y tipo de retorno opcional
 * - if / else_if / else con bloques (evita dangling else)
 * - while, for-each, for-range (con step)
 * - try con múltiples catch
 * - break / continue
 * - return con o sin expresión (como exigen los tests)
 * - Expresiones con precedencia, llamadas, acceso por punto e índice
 * - Literales lista y objeto (permitiendo clave: valor y clave = valor)
 */

@header { package edu.eam.ingesoft.tlf; }

// ===== Palabras clave =====
IMPORT      : 'import';
IMPORT_JAR  : 'import_jar';
AS          : 'as';
FUNCTION    : 'function';
RETURN      : 'return';
IF          : 'if';
ELSE        : 'else';
ELSE_IF     : 'else_if';   // <- añadido para los tests
WHILE       : 'while';
FOR         : 'for';
EACH        : 'each';
IN          : 'in';
FROM        : 'from';
TO          : 'to';
STEP        : 'step';
TRY         : 'try';
CATCH       : 'catch';
BREAK       : 'break';
CONTINUE    : 'continue';

TRUE        : 'true';
FALSE       : 'false';
NULL        : 'null';
NOT         : 'not';
AND         : 'and';
OR          : 'or';

// ===== Símbolos / Operadores =====
ARROW       : '->';
COLON       : ':';
COMMA       : ',';
LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
LBRACK      : '[';
RBRACK      : ']';
DOT         : '.';

GE          : '>=';
LE          : '<=';
EQEQ        : '==';
NE          : '!=';
GT          : '>';
LT          : '<';
EQ          : '=';
PLUS        : '+';
MINUS       : '-';
MUL         : '*';
DIV         : '/';
MOD         : '%';

// ===== Literales / Identificadores =====
DECIMAL     : [0-9]+ '.' [0-9]+;
INTEGER     : [0-9]+;
STRING      : '"' ( ~["\\] | '\\' . )* '"' ;
ID          : [a-zA-Z_] [a-zA-Z_0-9]* ;

// ===== Espacios y comentarios =====
LINE_COMMENT  : '#' ~[\r\n]*        -> skip;
BLOCK_COMMENT : '/*' .*? '*/'       -> skip;
WS            : [ \t\r\n]+          -> skip;

// ============================
// PARSER RULES
// ============================

functionProgram
  : (importDecl | importJarDecl)* functionDecl+ EOF
  ;

// --- imports ---
importDecl     : IMPORT STRING (AS ID)? ;
importJarDecl  : IMPORT_JAR STRING (AS ID)? ;

// --- funciones ---
functionDecl
  : FUNCTION ID LPAREN paramList? RPAREN returnType? block
  ;

paramList : param (COMMA param)* ;
param     : ID COLON typeRef ; // tipo obligatorio en parámetros

returnType : ARROW typeRef ;

typeRef
  : 'integer'
  | 'decimal'
  | 'boolean'
  | 'text'
  | 'object'
  | 'list'
  | 'void'
  ;

// --- bloques y sentencias ---
block : LBRACE statement* RBRACE ;

statement
  : ifStmt
  | whileStmt
  | forEachStmt
  | forRangeStmt
  | tryCatchStmt
  | returnStmt
  | breakStmt
  | continueStmt
  | assignStmt
  | exprStmt
  ;

// if con else_if encadenables y else final opcional
ifStmt
  : IF expression block (ELSE_IF expression block)* (ELSE block)?
  ;

whileStmt
  : WHILE expression block
  ;

forEachStmt
  : FOR EACH ID IN expression block
  ;

forRangeStmt
  : FOR ID FROM expression TO expression (STEP expression)? block
  ;

tryCatchStmt
  : TRY block (CATCH LPAREN ID RPAREN block)+
  ;

// return con o sin valor (los tests incluyen "return" solo)
returnStmt : RETURN expression? ;

breakStmt    : BREAK ;
continueStmt : CONTINUE ;

// asignación a lvalue (propiedades/índices encadenados)
assignStmt : lvalue EQ expression ;
lvalue     : ID ( (DOT ID) | (LBRACK expression RBRACK) )* ;

// expresión como sentencia
exprStmt : expression ;

// --- expresiones con precedencia ---
expression       : logicalOrExpr ;

logicalOrExpr    : logicalAndExpr ( OR  logicalAndExpr )* ;
logicalAndExpr   : equalityExpr   ( AND equalityExpr   )* ;
equalityExpr     : relationalExpr ( (EQEQ | NE) relationalExpr )* ;
relationalExpr   : additiveExpr   ( (LT | LE | GT | GE) additiveExpr )* ;
additiveExpr     : multiplicativeExpr ( (PLUS | MINUS) multiplicativeExpr )* ;
multiplicativeExpr : unaryExpr ( (MUL | DIV | MOD) unaryExpr )* ;

unaryExpr
  : NOT unaryExpr
  | MINUS unaryExpr
  | postfixExpr
  ;

postfixExpr
  : primary ( funcCall | DOT ID | LBRACK expression RBRACK )*
  ;

funcCall : LPAREN argList? RPAREN ;
argList  : expression (COMMA expression)* ;

primary
  : DECIMAL
  | INTEGER
  | STRING
  | TRUE
  | FALSE
  | NULL
  | ID
  | listLiteral
  | objectLiteral
  | LPAREN expression RPAREN
  ;

// --- literales compuestos ---
listLiteral : LBRACK (expression (COMMA expression)*)? RBRACK ;

objectLiteral : LBRACE (objPair (COMMA objPair)*)? RBRACE ;
objPair       : ID (COLON | EQ) expression ;
