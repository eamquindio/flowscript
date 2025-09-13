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
// PARSER RULES
// ============================

// Programa de funciones: una o más funciones
functionProgram
    : (functionDeclaration)+ EOF
    ;

// ============================
// DECLARACIÓN DE FUNCIONES
// ============================

functionDeclaration
    : FUNCTION identifier LPAREN parameterList? RPAREN (ARROW returnType)? block
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : identifier COLON type
    ;

type
    : primitiveType
    ;

primitiveType
    : INTEGER_T
    | DECIMAL_T
    | BOOLEAN_T
    | TEXT_T
    | LIST_T
    | OBJECT_T
    | VOID_T
    ;

returnType
    : primitiveType
    ;

// ============================
// BLOQUES Y SENTENCIAS
// ============================

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
    | breakStatement
    | continueStatement
    | assignmentStatement
    | expressionStatement
    ;

ifStatement
    : IF expression block (ELSE_IF expression block)* (ELSE block)?
    ;

whileStatement
    : WHILE expression block
    ;

forEachStatement
    : FOR EACH identifier IN expression block
    ;

forRangeStatement
    : FOR identifier FROM expression TO expression (STEP expression)? block
    ;

tryCatchStatement
    : TRY block (CATCH LPAREN identifier RPAREN block)+
    ;

throwStatement
    : THROW expression
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

assignmentStatement
    : leftHandSide ASSIGN expression
    ;

expressionStatement
    : expression
    ;

// ============================
// EXPRESIONES Y PRECEDENCIA
// ============================

expression
    : assignmentExpression
    ;

assignmentExpression
    : logicalOrExpression (ASSIGN assignmentExpression)?
    ;

logicalOrExpression
    : logicalAndExpression (OR logicalAndExpression)*
    ;

logicalAndExpression
    : equalityExpression (AND equalityExpression)*
    ;

equalityExpression
    : relationalExpression ((EQ | NEQ) relationalExpression)*
    ;

relationalExpression
    : additiveExpression ((LT | GT | LE | GE) additiveExpression)*
    ;

additiveExpression
    : multiplicativeExpression ((PLUS | MINUS) multiplicativeExpression)*
    ;

multiplicativeExpression
    : unaryExpression ((MUL | DIV | MOD) unaryExpression)*
    ;

unaryExpression
    : (NOT | MINUS) unaryExpression
    | postfixExpression
    ;

postfixExpression
    : primaryExpression postfixPart*
    ;

postfixPart
    : DOT identifier
    | LBRACK expression RBRACK
    | LPAREN argumentList? RPAREN
    ;

argumentList
    : expression (COMMA expression)*
    ;

leftHandSide
    : primaryExpression postfixPart+
    | identifier
    ;

primaryExpression
    : literal
    | identifier
    | LPAREN expression RPAREN
    | listLiteral
    | objectLiteral
    ;

literal
    : INTEGER_LITERAL
    | DECIMAL_LITERAL
    | STRING_LITERAL
    | TRUE
    | FALSE
    | NULL
    ;

listLiteral
    : LBRACK (expression (COMMA expression)*)? RBRACK
    ;

objectLiteral
    : LBRACE (objectProperty (COMMA objectProperty)*)? RBRACE
    ;

objectProperty
    : identifier COLON expression
    ;

identifier
    : IDENTIFIER
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

// ============================
// LEXER RULES (TOKENS)
// ============================

// Palabras clave
FUNCTION: 'function';
RETURN: 'return';
IF: 'if';
ELSE_IF: 'else_if';
ELSE: 'else';
WHILE: 'while';
FOR: 'for';
EACH: 'each';
IN: 'in';
FROM: 'from';
TO: 'to';
STEP: 'step';
TRY: 'try';
CATCH: 'catch';
THROW: 'throw';
BREAK: 'break';
CONTINUE: 'continue';

// Tipos
INTEGER_T: 'integer';
DECIMAL_T: 'decimal';
BOOLEAN_T: 'boolean';
TEXT_T: 'text';
LIST_T: 'list';
OBJECT_T: 'object';
VOID_T: 'void';

// Booleanos y null
TRUE: 'true';
FALSE: 'false';
NULL: 'null';

// Operadores lógicos
AND: 'and';
OR: 'or';
NOT: 'not';

// Operadores y signos de puntuación
ARROW: '->';
EQ: '==';
NEQ: '!=';
LE: '<=';
GE: '>=';
LT: '<';
GT: '>';
ASSIGN: '=';
PLUS: '+';
MINUS: '-';
MUL: '*';
DIV: '/';
MOD: '%';
DOT: '.';
COMMA: ',';
COLON: ':';
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
LBRACK: '[';
RBRACK: ']';

// Literales
// Números decimales: 1.23, 1e10, 1.2e-3
DECIMAL_LITERAL
    : DIGIT+ '.' DIGIT+ ([eE] [+-]? DIGIT+)?
    | DIGIT+ [eE] [+-]? DIGIT+
    ;

// Enteros: 0, 123
INTEGER_LITERAL
    : DIGIT+
    ;

fragment DIGIT: [0-9];

// Cadenas de texto con escapes comunes
STRING_LITERAL
    : '"' ( '\\' [btnr"\\] | '\\u' HEX HEX HEX HEX | ~["\\\r\n] )* '"'
    ;

fragment HEX: [0-9a-fA-F];

// Identificadores
IDENTIFIER
    : [a-zA-Z_] [a-zA-Z0-9_]*
    ;

// Comentarios y espacios en blanco
LINE_COMMENT
    : '#' ~[\r\n]* -> skip
    ;

BLOCK_COMMENT
    : '/*' .*? '*/' -> skip
    ;

WS
    : [ \t\r\n\f]+ -> skip
    ;
