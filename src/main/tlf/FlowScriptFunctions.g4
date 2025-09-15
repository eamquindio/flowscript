grammar FlowScriptFunctions;

// Package declaration for generated code
@header {
    package edu.eam.ingesoft.tlf;
}

/*
 * GRAMÁTICA DE FUNCIONES PARA FLOWSCRIPT
 *
 * Este archivo define la gramática completa para el sistema de funciones
 * del lenguaje FlowScript, incluyendo:
 * - Declaración de funciones
 * - Parámetros tipados
 * - Tipos de retorno
 * - Cuerpo de funciones con statements
 * - Expresiones y operadores (con precedencia y asignación derecha-asoc)
 * - Control de flujo dentro de funciones
 *
 * Nota: Esta gramática es autónoma (solo funciones).
 *       Si la integras con otras, cuida colisiones de tokens.
 */

// ============================
// LEXER RULES (TOKENS)
// ============================

// Palabras clave para funciones
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

// Tipos
INTEGER_T : 'integer';
DECIMAL_T : 'decimal';
BOOLEAN_T : 'boolean';
TEXT_T    : 'text';
LIST_T    : 'list';
OBJECT_T  : 'object';
VOID_T    : 'void';

// Lógicos palabra
AND       : 'and';
OR        : 'or';
NOT       : 'not';

// Operadores y signos
ADD: '+'; SUB: '-'; MUL: '*'; DIV: '/'; MOD: '%';
LT: '<'; GT: '>'; LE: '<='; GE: '>=';
EQ: '=='; NEQ: '!=';
ASSIGN: '=';
LPAREN:'(' ; RPAREN:')';
LBRACE:'{' ; RBRACE:'}';
LBRACK:'[' ; RBRACK:']';
DOT:'.' ; COMMA:',' ; COLON:':' ; SEMI:';';
ARROW: '->';

// Literales
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

// Identificadores
ID : [a-zA-Z_][a-zA-Z0-9_]* ;

// Comentarios/WS
WS : [ \t\r\n\f]+ -> skip ;
LINE_COMMENT_HASH  : '#' ~[\r\n]* -> skip ;
LINE_COMMENT_SLASH : '//' ~[\r\n]* -> skip ;
BLOCK_COMMENT      : '/*' .*? '*/' -> skip ;

// Reservadas futuras (silenciadas para evitar conflictos si aparecen)
ASYNC  : 'async'   -> skip ;
AWAIT  : 'await'   -> skip ;
EVENT  : 'event'   -> skip ;
CLASS  : 'class'   -> skip ;

// ============================
// DECLARACIÓN DE FUNCIONES
// ============================

/*
 * Entrada del archivo de funciones:
 * - Cero o más declaraciones de función, seguido de EOF.
 */
functionProgram
  : (functionDecl)* EOF
  ;

/*
 * function foo(a: integer, b: text) -> boolean { ... }
 * El tipo de retorno es opcional (si no está, puede asumirse void).
 */
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
  : (ARROW typeRef)?
  ;

typeRef
  : INTEGER_T | DECIMAL_T | BOOLEAN_T | TEXT_T | LIST_T | OBJECT_T | VOID_T
  ;

// ============================
// STATEMENTS
// ============================

block
  : LBRACE statement* RBRACE
  ;

/*
 * Orden pensado para minimizar conflictos y permitir:
 * - if/while/for/try/throw/return
 * - break/continue
 * - expresión (incluye asignación como expresión)
 * - ; vacío (statement vacío)
 */
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
  | SEMI
  | expression SEMI?
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

/*
 * try { ... } catch (e[: Tipo]) { ... } [catch (...) { ...} ...]
 * Se permite 1..N bloques catch.
 */
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

// ============================
// EXPRESIONES
// ============================

/*
 * Asignación es expresión y es derecha-asociativa:
 * a = b = c    ==> a = (b = c)
 */
expression
  : assignment
  ;

assignment
  : lvalue ASSIGN assignment           #assignExpr
  | orExpr                             #nonAssignExpr
  ;

/* lvalues compuestos: id(.id|[expr])* */
lvalue
  : ID (DOT ID | LBRACK expression RBRACK)*
  ;

/* Precedencia (baja -> alta): or < and < == != < rel < + - < * / % < unarios < postfix */
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

/* Unarios: not x, -x */
unaryExpr
  : NOT unaryExpr
  | SUB unaryExpr
  | postfixExpr
  ;

/* Postfijos: acceso .miembro, [índice], llamada f(args) */
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

/* Primitivos y agrupación */
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

/* Objeto estilo { a: 1, b: x + 2 } con claves ID */
objectLiteral
  : LBRACE (objectPair (COMMA objectPair)*)? RBRACE
  ;

objectPair
  : ID COLON expression
  ;

literal
  : INT | DECIMAL | STRING | BOOL | NULL_T
  ;

// ============================
// EJEMPLOS DE USO (comentarios)
// ============================

/*
 * EJEMPLOS VÁLIDOS:
 *
 * 1) Función simple:
 * function greet() -> void {
 *   print("Hello World")
 * }
 *
 * 2) Con parámetros y retorno:
 * function add(a: integer, b: integer) -> integer {
 *   return a + b
 * }
 *
 * 3) Recursiva:
 * function factorial(n: integer) -> integer {
 *   if n <= 1 { return 1 }
 *   return n * factorial(n - 1)
 * }
 *
 * 4) Manejo de errores:
 * function safe_divide(a: decimal, b: decimal) -> decimal {
 *   try {
 *     if b == 0 {
 *       throw { type: "DivisionError", message: "Division by zero" }
 *     }
 *     return a / b
 *   } catch (error: object) {
 *     print("Error: " + error.message)
 *     return 0.0
 *   }
 * }
 *
 * 5) Estructuras de datos:
 * function process_items(items: list, threshold: decimal) -> object {
 *   result = { count: 0, sum: 0.0 }
 *   for each item in items {
 *     if item.value > threshold {
 *       result.count = result.count + 1
 *       result.sum = result.sum + item.value
 *     }
 *   }
 *   return result
 * }
 *
 * 6) Bucles:
 * function find_max(numbers: list) -> decimal {
 *   if numbers.length() == 0 { return null }
 *   max = numbers[0]
 *   for i from 1 to numbers.length() - 1 {
 *     if numbers[i] > max { max = numbers[i] }
 *   }
 *   return max
 * }
 */
