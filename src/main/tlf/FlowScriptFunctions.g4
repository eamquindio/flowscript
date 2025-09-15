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
 * - Expresiones y operadores
 * - Control de flujo dentro de funciones
 */

// ============================
// LEXER RULES (TOKENS)
// ============================

// Palabras clave para funciones
FUNCTION    : 'function';
RETURN      : 'return';
IF          : 'if';
ELSE_IF     : 'else_if';
ELSE        : 'else';
WHILE       : 'while';
FOR         : 'for';
EACH        : 'each';
IN          : 'in';
FROM        : 'from';
TO          : 'to';
STEP        : 'step';
BREAK       : 'break';
CONTINUE    : 'continue';
TRY         : 'try';
CATCH       : 'catch';
THROW       : 'throw';
LET         : 'let';
CONST       : 'const';

// Tipos
TYPE_INTEGER: 'integer';
TYPE_DECIMAL: 'decimal';
TYPE_BOOLEAN: 'boolean';
TYPE_TEXT   : 'text';
TYPE_LIST   : 'list';
TYPE_OBJECT : 'object';
TYPE_VOID   : 'void';

// Literales
TRUE        : 'true';
FALSE       : 'false';
NULL        : 'null';

// Operadores
ARROW       : '->';
ASSIGN      : '=';
PLUS        : '+';
MINUS       : '-';
MUL         : '*';
DIV         : '/';
MOD         : '%';
EQ          : '==';
NEQ         : '!=';
LT          : '<';
GT          : '>';
LE          : '<=';
GE          : '>=';
AND         : 'and';
OR          : 'or';
NOT         : 'not';


// Símbolos
LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
LBRACK      : '[';
RBRACK      : ']';
COMMA       : ',';
COLON       : ':';
DOT         : '.';


// Identificadores
IDENTIFIER  : [a-zA-Z_] [a-zA-Z_0-9]* ;
// Números
INTEGER_LITERAL
    : '0' | [1-9] [0-9]*
    ;

DECIMAL_LITERAL
    : [0-9]+ '.' [0-9]+ ([eE] [+\-]? [0-9]+)?
    | [0-9]+ [eE] [+\-]? [0-9]+
    ;

// Cadenas
STRING_LITERAL
    : '"' ( '\\' . | ~["\\\r\n] )* '"'
    ;

// Comentarios
LINE_COMMENT
    : '#' ~[\r\n]* -> skip
    ;

BLOCK_COMMENT
    : '/*' .*? '*/' -> skip
    ;

// Espacios en blanco
WS
    : [ \t\r\n]+ -> skip
    ;

// ============================
// DECLARACIÓN DE FUNCIONES
// ============================

functionProgram
    : functionDeclaration* EOF
    ;

functionDeclaration
    : FUNCTION IDENTIFIER LPAREN parameterList? RPAREN returnType? block
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : IDENTIFIER COLON type
    ;

type
    : TYPE_INTEGER
    | TYPE_DECIMAL
    | TYPE_BOOLEAN
    | TYPE_TEXT
    | TYPE_LIST
    | TYPE_OBJECT
    | TYPE_VOID
    ;

returnType
    : ARROW type
    ;

block
    : LBRACE statement* RBRACE
    ;

statement
    : variableDeclaration
    | assignment
    | ifStatement
    | whileLoop
    | forEachLoop
    | forRangeLoop
    | returnStatement
    | breakStatement
    | continueStatement
    | tryCatchStatement
    | throwStatement
    | expressionStatement
    ;

variableDeclaration
    : (LET | CONST) IDENTIFIER (ASSIGN expression)?
    ;

assignment
    : (IDENTIFIER
      | postfixExpr   
      ) ASSIGN expression
    ;

expressionStatement
    : expression
    ;

ifStatement
    : IF expression block (ELSE_IF expression block)* (ELSE block)?
    ;

whileLoop
    : WHILE expression block
    ;

forEachLoop
    : FOR EACH IDENTIFIER IN expression block
    ;

forRangeLoop
    : FOR IDENTIFIER FROM expression TO expression (STEP expression)? block
    ;

returnStatement
    : RETURN expression?
    ;

breakStatement
    : BREAK
    ;

continueStatement
    : CONTINUE
    ;

tryCatchStatement
    : TRY block (CATCH LPAREN IDENTIFIER RPAREN block)+
    ;

throwStatement
    : THROW expression
    ;

// ============================
// EXPRESIONES CON PRECEDENCIA
// ============================
expression
    : orExpr
    ;

orExpr
    : andExpr (OR andExpr)*
    ;

andExpr
    : equalityExpr (AND equalityExpr)*
    ;

equalityExpr
    : relationalExpr ((EQ | NEQ) relationalExpr)*
    ;

relationalExpr
    : additiveExpr ((LT | GT | LE | GE) additiveExpr)*
    ;

additiveExpr
    : multiplicativeExpr ((PLUS | MINUS) multiplicativeExpr)*
    ;

multiplicativeExpr
    : unaryExpr ((MUL | DIV | MOD) unaryExpr)*
    ;

unaryExpr
    : (MINUS | NOT) unaryExpr
    | postfixExpr
    ;

// llamadas, acceso a miembros, indexación
postfixExpr
    : primary (LPAREN argumentList? RPAREN | DOT IDENTIFIER | LBRACK expression RBRACK)*
    ;

argumentList
    : expression (COMMA expression)*
    ;

primary
    : IDENTIFIER
    | literal
    | LPAREN expression RPAREN
    ;

// ============================
// LITERALES
// ============================

literal
    : numberLiteral
    | stringLiteral
    | booleanLiteral
    | NULL
    | listLiteral
    | objectLiteral
    ;

numberLiteral
    : DECIMAL_LITERAL
    | INTEGER_LITERAL
    ;

stringLiteral
    : STRING_LITERAL
    ;

booleanLiteral
    : TRUE
    | FALSE
    ;

listLiteral
    : LBRACK (expression (COMMA expression)*)? RBRACK
    ;

objectLiteral
    : LBRACE (IDENTIFIER COLON expression (COMMA IDENTIFIER COLON expression)*)? RBRACE
    ;

// ============================
// EJEMPLOS DE USO
// ============================

/*
 * EJEMPLOS VÁLIDOS:
 * 
 * 1. Función simple:
 * function greet() -> void {
 *     print("Hello World")
 * }
 * 
 * 2. Función con parámetros y retorno:
 * function add(a: integer, b: integer) -> integer {
 *     return a + b
 * }
 * 
 * 3. Función con lógica compleja:
 * function factorial(n: integer) -> integer {
 *     if n <= 1 {
 *         return 1
 *     }
 *     return n * factorial(n - 1)
 * }
 * 
 * 4. Función con manejo de errores:
 * function safe_divide(a: decimal, b: decimal) -> decimal {
 *     try {
 *         if b == 0 {
 *             throw { type: "DivisionError", message: "Division by zero" }
 *         }
 *         return a / b
 *     } catch (error) {
 *         print("Error: " + error.message)
 *         return 0.0
 *     }
 * }
 * 
 * 5. Función con estructuras de datos:
 * function process_items(items: list, threshold: decimal) -> object {
 *     result = { count: 0, sum: 0.0 }
 *     
 *     for each item in items {
 *         if item.value > threshold {
 *             result.count = result.count + 1
 *             result.sum = result.sum + item.value
 *         }
 *     }
 *     
 *     return result
 * }
 * 
 * 6. Función con bucles:
 * function find_max(numbers: list) -> decimal {
 *     if numbers.length() == 0 {
 *         return null
 *     }
 *     
 *     max = numbers[0]
 *     for i from 1 to numbers.length() - 1 {
 *         if numbers[i] > max {
 *             max = numbers[i]
 *         }
 *     }
 *     
 *     return max
 * }
 */