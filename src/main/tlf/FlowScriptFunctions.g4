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
FUNCTION: 'function';
RETURN: 'return';
VOID: 'void';

// Tipos de datos
INTEGER_TYPE: 'integer';
DECIMAL_TYPE: 'decimal';
BOOLEAN_TYPE: 'boolean';
TEXT_TYPE: 'text';
LIST_TYPE: 'list';
OBJECT_TYPE: 'object';

// Control de flujo
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
BREAK: 'break';
CONTINUE: 'continue';
TRY: 'try';
CATCH: 'catch';
THROW: 'throw';

// Operadores lógicos
AND: 'and';
OR: 'or';
NOT: 'not';

// Literales booleanos y null
TRUE: 'true';
FALSE: 'false';
NULL: 'null';

// Operadores aritméticos
PLUS: '+';
MINUS: '-';
MULTIPLY: '*';
DIVIDE: '/';
MODULO: '%';

// Operadores de comparación
LESS_THAN: '<';
GREATER_THAN: '>';
LESS_EQUAL: '<=';
GREATER_EQUAL: '>=';
EQUAL: '==';
NOT_EQUAL: '!=';

// Operadores de asignación y acceso
ASSIGN: '=';
DOT: '.';
ARROW: '->';

// Delimitadores
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
LBRACKET: '[';
RBRACKET: ']';
COMMA: ',';
SEMICOLON: ';';
COLON: ':';

// Literales numéricos
INTEGER_LITERAL
    : DIGIT+ ('_' DIGIT+)*
    | '0'
    ;

DECIMAL_LITERAL
    : DIGIT+ '.' DIGIT+ EXPONENT?
    | DIGIT+ EXPONENT
    | '.' DIGIT+ EXPONENT?
    ;

// Literal de texto
STRING_LITERAL
    : '"' STRING_CONTENT* '"'
    ;

// Identificadores
IDENTIFIER
    : LETTER (LETTER | DIGIT | '_')*
    | '_' (LETTER | DIGIT | '_')+
    ;

// Comentarios
SINGLE_LINE_COMMENT: '#' ~[\r\n]* -> skip;
MULTI_LINE_COMMENT: '/*' .*? '*/' -> skip;

// Espacios en blanco
WS: [ \t\r\n]+ -> skip;

// Fragmentos
fragment DIGIT: [0-9];
fragment LETTER: [a-zA-Z];
fragment EXPONENT: [eE] [+-]? DIGIT+;
fragment STRING_CONTENT: ~["\\\r\n] | '\\' ESCAPE_SEQUENCE;
fragment ESCAPE_SEQUENCE: [btnfr"\\] | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT;
fragment HEX_DIGIT: [0-9a-fA-F];

// ============================
// PARSER RULES
// ============================

// Punto de entrada para testing de funciones
functionProgram
    : functionDeclaration+ EOF
    ;

// ============================
// DECLARACIÓN DE FUNCIONES
// ============================

functionDeclaration
    : FUNCTION functionName LPAREN parameterList? RPAREN returnType? functionBody
    ;

functionName
    : IDENTIFIER
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : parameterName COLON parameterType
    ;

parameterName
    : IDENTIFIER
    ;

parameterType
    : dataType
    ;

returnType
    : ARROW dataType
    ;

dataType
    : primitiveType
    | compositeType
    | VOID
    ;

primitiveType
    : INTEGER_TYPE
    | DECIMAL_TYPE
    | BOOLEAN_TYPE
    | TEXT_TYPE
    ;

compositeType
    : LIST_TYPE
    | OBJECT_TYPE
    ;

functionBody
    : LBRACE statement* RBRACE
    ;

// ============================
// STATEMENTS
// ============================

statement
    : variableDeclaration
    | assignmentStatement
    | ifStatement
    | whileStatement
    | forStatement
    | tryStatement
    | returnStatement
    | throwStatement
    | breakStatement
    | continueStatement
    | expressionStatement
    | block
    | SEMICOLON  // Empty statement
    ;

variableDeclaration
    : IDENTIFIER ASSIGN expression SEMICOLON?
    ;

assignmentStatement
    : leftHandSide ASSIGN expression SEMICOLON?
    ;

leftHandSide
    : IDENTIFIER
    | postfixExpression
    ;

ifStatement
    : IF condition block elseIfClause* elseClause?
    ;

elseIfClause
    : ELSE_IF condition block
    ;

elseClause
    : ELSE block
    ;

whileStatement
    : WHILE condition block
    ;

forStatement
    : forEachStatement
    | forRangeStatement
    ;

forEachStatement
    : FOR EACH IDENTIFIER IN expression block
    ;

forRangeStatement
    : FOR IDENTIFIER FROM expression TO expression (STEP expression)? block
    ;

tryStatement
    : TRY block catchClause+
    ;

catchClause
    : CATCH LPAREN IDENTIFIER RPAREN block
    ;

returnStatement
    : RETURN expression? SEMICOLON?
    ;

throwStatement
    : THROW expression SEMICOLON?
    ;

breakStatement
    : BREAK SEMICOLON?
    ;

continueStatement
    : CONTINUE SEMICOLON?
    ;

expressionStatement
    : expression SEMICOLON?
    ;

block
    : LBRACE statement* RBRACE
    ;

condition
    : expression
    | LPAREN expression RPAREN
    ;

// ============================
// EXPRESIONES
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
    : relationalExpression ((EQUAL | NOT_EQUAL) relationalExpression)*
    ;

relationalExpression
    : additiveExpression ((LESS_THAN | GREATER_THAN | LESS_EQUAL | GREATER_EQUAL) additiveExpression)*
    ;

additiveExpression
    : multiplicativeExpression ((PLUS | MINUS) multiplicativeExpression)*
    ;

multiplicativeExpression
    : unaryExpression ((MULTIPLY | DIVIDE | MODULO) unaryExpression)*
    ;

unaryExpression
    : (NOT | MINUS) unaryExpression
    | postfixExpression
    ;

postfixExpression
    : primaryExpression (
        DOT IDENTIFIER                     // Member access
        | LBRACKET expression RBRACKET     // Array indexing
        | LPAREN argumentList? RPAREN      // Function call
      )*
    ;

argumentList
    : expression (COMMA expression)*
    ;

primaryExpression
    : literal
    | IDENTIFIER
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
    : LBRACKET (expression (COMMA expression)*)? RBRACKET
    ;

objectLiteral
    : LBRACE (objectProperty (COMMA objectProperty)*)? RBRACE
    ;

objectProperty
    : propertyKey COLON expression
    ;

propertyKey
    : IDENTIFIER
    | STRING_LITERAL
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