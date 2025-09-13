grammar FlowScriptFunctions;

// Package declaration for generated code
@header {
    package edu.eam.ingesoft.tlf;
}

/*
 * GRAMÁTICA DE FUNCIONES PARA FLOWSCRIPT
 *
 * - Declaración de funciones
 * - Parámetros tipados
 * - Tipos de retorno
 * - Cuerpo de funciones con statements
 * - Expresiones y operadores
 * - Control de flujo dentro de funciones
 */

// ============================
// PARSER RULES (ENTRY POINT)
// ============================

functionProgram
    : (functionDeclaration)* EOF
    ;

// ============================
// DECLARACIÓN DE FUNCIONES
// ============================

functionDeclaration
    : FUNCTION functionName LPAREN parameterList? RPAREN (ARROW returnType)? functionBody
    ;

functionName
    : Identifier
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : Identifier COLON dataType
    ;

dataType
    : INTEGER_T
    | DECIMAL_T
    | BOOLEAN_T
    | TEXT_T
    | LIST_T
    | OBJECT_T
    | VOID_T
    ;

returnType
    : dataType
    ;

functionBody
    : LBRACE statement* RBRACE
    ;

// ============================
// STATEMENTS
// ============================

statement
    : ifStatement
    | whileStatement
    | forEachStatement
    | forRangeStatement
    | tryStatement
    | throwStatement
    | returnStatement
    | breakStatement
    | continueStatement
    | assignmentStatement
    | expressionStatement
    ;

block
    : LBRACE statement* RBRACE
    ;

ifStatement
    : IF expression block (ELSE_IF expression block)* (ELSE block)?
    ;

whileStatement
    : WHILE expression block
    ;

forEachStatement
    : FOR EACH Identifier IN expression block
    ;

forRangeStatement
    : FOR Identifier FROM expression TO expression (STEP expression)? block
    ;

tryStatement
    : TRY block catchClause+
    ;

catchClause
    : CATCH LPAREN Identifier RPAREN block
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

leftHandSide
    : primaryExpression postfixPart*
    ;

expressionStatement
    : expression
    ;

// ============================
// EXPRESSIONS (precedence)
// ============================

expression
    : logicalOrExpression
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
    : additiveExpression ((LT | LTE | GT | GTE) additiveExpression)*
    ;

additiveExpression
    : multiplicativeExpression ((PLUS | MINUS) multiplicativeExpression)*
    ;

multiplicativeExpression
    : unaryExpression ((MUL | DIV | MOD) unaryExpression)*
    ;

unaryExpression
    : (NOT | PLUS | MINUS) unaryExpression
    | postfixExpression
    ;

postfixExpression
    : primaryExpression postfixPart*
    ;

postfixPart
    : DOT Identifier                               // property access
    | LBRACK expression RBRACK                     // index access
    | LPAREN argumentList? RPAREN                  // function call
    | DOT Identifier LPAREN argumentList? RPAREN   // method call
    ;

primaryExpression
    : literal
    | LPAREN expression RPAREN
    | Identifier
    ;

argumentList
    : expression (COMMA expression)*
    ;

// ============================
// LITERALS
// ============================

literal
    : IntegerLiteral
    | DecimalLiteral
    | StringLiteral
    | TRUE
    | FALSE
    | NULL
    | listLiteral
    | objectLiteral
    ;

listLiteral
    : LBRACK (expression (COMMA expression)*)? RBRACK
    ;

objectLiteral
    : LBRACE (objectProperty (COMMA objectProperty)*)? RBRACE
    ;

objectProperty
    : propertyKey COLON expression
    ;

propertyKey
    : Identifier
    | StringLiteral
    ;

// ============================
// LEXER RULES (TOKENS)
// ============================

// Keywords
FUNCTION: 'function';
RETURN: 'return';
IF: 'if';
ELSE: 'else';
ELSE_IF: 'else_if';
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
AND: 'and';
OR: 'or';
NOT: 'not';

// Types
INTEGER_T: 'integer';
DECIMAL_T: 'decimal';
BOOLEAN_T: 'boolean';
TEXT_T: 'text';
LIST_T: 'list';
OBJECT_T: 'object';
VOID_T: 'void';

// Booleans / Null
TRUE: 'true';
FALSE: 'false';
NULL: 'null';

// Operators and punctuation
ARROW: '->';
ASSIGN: '=';
EQ: '==';
NEQ: '!=';
LTE: '<=';
GTE: '>=';
LT: '<';
GT: '>';
PLUS: '+';
MINUS: '-';
MUL: '*';
DIV: '/';
MOD: '%';
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
LBRACK: '[';
RBRACK: ']';
COMMA: ',';
COLON: ':';
DOT: '.';

// Literals
IntegerLiteral
    : '0' | [1-9] [0-9]*
    ;

DecimalLiteral
    : [0-9]+ '.' [0-9]+
    ;

StringLiteral
    : '"' (ESC_SEQ | ~["\\\r\n])* '"'
    ;

fragment ESC_SEQ
    : '\\' ["\\/bfnrt]
    | '\\' 'u' HEX HEX HEX HEX
    ;

fragment HEX: [0-9a-fA-F];

Identifier
    : [a-zA-Z_] [a-zA-Z_0-9]*
    ;

// Whitespace & Comments
WS
    : [ \t\r\n]+ -> skip
    ;

LINE_COMMENT_HASH
    : '#' ~[\r\n]* -> skip
    ;

LINE_COMMENT_SLASH
    : '//' ~[\r\n]* -> skip
    ;

BLOCK_COMMENT
    : '/*' .*? '*/' -> skip
    ;

