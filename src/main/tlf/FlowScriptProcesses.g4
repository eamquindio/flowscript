grammar FlowScriptProcesses;

@header { package edu.eam.ingesoft.tlf; }

/* ============================================================================
   FlowScriptProcesses (versión comentada y legible)
   ----------------------------------------------------------------------------
   Qué cubre:
   - Programa con imports, variables globales, funciones y procesos
   - Proceso: start único, tareas (task), gateways (exclusive y parallel), ends
   - Acciones dentro de task: if/try/for-each/gateway interno/assign/goto/expr
   - Expresiones con precedencia, llamadas a funciones, acceso por punto/índice
   - Literales de lista y objeto (clave: valor)
   Puntos clave de validación:
   - “Proceso sin end” es inválido (se exige al menos un end al final)
   - Tipos de función limitados (rechaza tipos inventados)
   - Gateway paralelo requiere branches y un join
   ============================================================================ */


/* =========================
 *  LEXER: Palabras clave
 * =========================
 * Nota: Las keywords van primero para evitar choques con ID.
 */
NOT         : 'not';
AND         : 'and';
OR          : 'or';

IMPORT      : 'import';
IMPORT_JAR  : 'import_jar';
AS          : 'as';

PROCESS     : 'process';
START       : 'start';
TASK        : 'task';
ACTION      : 'action';
GOTO        : 'go_to';
END         : 'end';

GATEWAY     : 'gateway';
PARALLEL    : 'parallel';
BRANCH      : 'branch';
JOIN        : 'join';
WHEN        : 'when';
ELSE        : 'else';

FUNCTION    : 'function';
RETURN      : 'return';

FOR         : 'for';
EACH        : 'each';
IN          : 'in';
TRY         : 'try';
CATCH       : 'catch';
IF          : 'if';

TRUE        : 'true';
FALSE       : 'false';
NULL        : 'null';

/* =========================
 *  LEXER: Tipos y símbolos
 * ========================= */
TYPE_INTEGER : 'integer';
TYPE_DECIMAL : 'decimal';
TYPE_BOOLEAN : 'boolean';
TYPE_TEXT    : 'text';
TYPE_OBJECT  : 'object';
TYPE_VOID    : 'void';

ARROW       : '->';
COLON       : ':';
COMMA       : ',';
LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
LBRACK      : '[';
RBRACK      : ']';
EQ          : '=';
GT          : '>';
LT          : '<';
GE          : '>=';
LE          : '<=';
NE          : '!=';
PLUS        : '+';
MINUS       : '-';
MUL         : '*';
DIV         : '/';
DOT         : '.';

/* =========================
 *  LEXER: Literales e ID
 * ========================= */
INTEGER     : [0-9]+;
STRING      : '"' ( ~["\\] | '\\' . )* '"' ;
ID          : [a-zA-Z_] [a-zA-Z_0-9]* ;

/* =========================
 *  LEXER: Espacios/Comentarios
 * ========================= */
LINE_COMMENT : '#' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
WS           : [ \t\r\n]+ -> skip;


/* =========================
 *  PARSER: Programa
 * =========================
 * Un archivo puede traer imports, variables globales, funciones
 * y uno o varios procesos en cualquier orden.
 */
program
  : (importDecl | importJarDecl | globalVarDecl | functionDecl | processDecl)* EOF
  ;


/* -------------------------
 * Imports y globales
 * ------------------------- */
importDecl     : IMPORT STRING (AS ID)? ;
importJarDecl  : IMPORT_JAR STRING (AS ID)? ;

/* Variable global simple: NOMBRE = expr */
globalVarDecl  : ID EQ expression ;


/* -------------------------
 * Funciones auxiliares
 * -------------------------
 * Se incluyen con tipado básico y retorno opcional; los tipos
 * válidos son los definidos (integer, decimal, boolean, text, object, void).
 */
functionDecl
  : FUNCTION ID LPAREN paramList? RPAREN returnType? block
  ;

paramList : param (COMMA param)* ;
param     : ID (COLON typeRef)? ;     // El proyecto permite parámetros sin tipo, opcional

returnType : ARROW typeRef ;

typeRef
  : TYPE_INTEGER
  | TYPE_DECIMAL
  | TYPE_BOOLEAN
  | TYPE_TEXT
  | TYPE_OBJECT
  | TYPE_VOID
  ;


/* =========================
 *  PARSER: Procesos
 * =========================
 * Estructura: process Nombre { start -> X ... end Y ... }
 * Regla clave: al final del cuerpo debe existir al menos un end.
 */
processDecl
  : PROCESS ID LBRACE processBody RBRACE
  ;

/* start va primero, luego cualquier cantidad de items (task/gateway),
 * y el/los end van al final. De esta forma “proceso sin end” falla.
 */
processBody
  : startDecl (processItemNoEnd)* endSection
  ;

/* start -> NombreTareaInicial */
startDecl      : START ARROW ID ;

/* Ítems intermedios del proceso (no incluyen end) */
processItemNoEnd
  : taskDecl
  | gatewayDecl
  ;

/* Uno o más end al final del proceso */
endSection     : (endDecl)+ ;
endDecl        : END ID ;


/* -------------------------
 * Tareas
 * -------------------------
 * Las acciones admiten múltiples sentencias.
 */
taskDecl
  : TASK ID LBRACE ACTION COLON statementBlock RBRACE
  ;


/* -------------------------
 * Gateways
 * -------------------------
 * - Exclusivo: when ... -> Nodo, else -> Nodo
 * - Paralelo: branches + un join obligatorio
 */
gatewayDecl
  : GATEWAY ID (PARALLEL gatewayParallelBody | gatewayExclusiveBody)
  ;

gatewayExclusiveBody
  : LBRACE (whenClause)+ (elseClause)? RBRACE
  ;

gatewayParallelBody
  : LBRACE (branchClause)+ joinClause RBRACE
  ;

whenClause   : WHEN expression ARROW ID ;
elseClause   : ELSE ARROW ID ;
branchClause : BRANCH ARROW ID ;
joinClause   : JOIN ARROW ID ;


/* =========================
 *  PARSER: Acciones
 * =========================
 * Conjunto de sentencias permisivo pero con las estructuras necesarias
 * para los tests (if, try/catch, for-each, gateway anidado, etc.).
 */
statementBlock : (statement)* ;

statement
  : gotoStmt
  | assignStmt
  | ifStmt
  | tryCatchStmt
  | forEachStmt
  | gatewayStmt          // gateway dentro de action
  | returnStmt
  | exprStmt
  | block
  ;

/* Saltos a nodos del proceso */
gotoStmt       : GOTO ID ;

/* Asignaciones con lvalue extendido (a.b[expr].c) */
assignStmt     : lvalue EQ expression ;
lvalue         : ID ( (DOT ID) | (LBRACK expression RBRACK) )* ;

/* Gateway interno dentro de una acción */
gatewayStmt
  : GATEWAY ID (PARALLEL gatewayParallelBody | gatewayExclusiveBody)
  ;

/* return exige expresión en este DSL de procesos */
returnStmt     : RETURN expression ;

/* if con else opcional (bloques obligatorios) */
ifStmt
  : IF expression block (ELSE block)?
  ;

/* try-catch básico con una sola cláusula catch */
tryCatchStmt
  : TRY block CATCH LPAREN ID RPAREN block
  ;

/* for-each: for each x in expr { ... } */
forEachStmt
  : FOR EACH ID IN expression block
  ;

/* Expresión suelta como sentencia, y bloque general */
exprStmt       : expression ;
block          : LBRACE statementBlock RBRACE ;


/* =========================
 *  PARSER: Expresiones
 * =========================
 * Precedencia usual: or < and < ==/!= < rel < +,- < *,/
 * Soporta llamadas, acceso por punto e índice.
 */
expression     : logicalOrExpr ;

logicalOrExpr  : logicalAndExpr ( OR  logicalAndExpr )* ;
logicalAndExpr : equalityExpr   ( AND equalityExpr   )* ;

equalityExpr
  : relationalExpr ( (EQ EQ | NE) relationalExpr )*
  ;

relationalExpr
  : additiveExpr ( (LT | LE | GT | GE) additiveExpr )*
  ;

additiveExpr
  : multiplicativeExpr ( (PLUS | MINUS) multiplicativeExpr )*
  ;

multiplicativeExpr
  : unaryExpr ( (MUL | DIV) unaryExpr )*
  ;

unaryExpr
  : NOT unaryExpr
  | MINUS unaryExpr
  | postfixExpr
  ;

/* Postfijo: a.b, a[b], a() */
postfixExpr
  : primary ( (DOT ID) | (LBRACK expression RBRACK) | funcCall )*
  ;

funcCall : LPAREN (argList)? RPAREN ;
argList  : expression (COMMA expression)* ;

primary
  : INTEGER
  | STRING
  | TRUE
  | FALSE
  | NULL
  | ID
  | listLiteral
  | objectLiteral
  | LPAREN expression RPAREN
  ;


/* =========================
 *  Literales compuestos
 * ========================= */
listLiteral    : LBRACK (expression (COMMA expression)*)? RBRACK ;
objectLiteral  : LBRACE (objPair (COMMA objPair)*)? RBRACE ;
objPair        : ID COLON expression ;
