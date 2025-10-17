grammar FlowScriptFunctions;

// Package declaration for generated code
@header {
    package edu.eam.ingesoft.tlf;
}

/*
 * Gramática de funciones para FlowScript.
 * Cubre declaraciones de funciones, parámetros, tipos,
 * statements de control y expresiones con precedencia.
 */

// ============================
// PARSER RULES
// ============================

functionProgram
    : functionDeclaration* EOF
    ;

functionDeclaration
    : FUNCTION IDENTIFIER '(' parameterList? ')' returnSignature? block
    ;

returnSignature
    : ARROW type
    ;

parameterList
    : parameter (',' parameter)*
    ;

parameter
    : IDENTIFIER ':' type
    ;

type
    : INTEGER_TYPE
    | DECIMAL_TYPE
    | BOOLEAN_TYPE
    | TEXT_TYPE
    | LIST_TYPE
    | OBJECT_TYPE
    | VOID_TYPE
    | NULL
    ;

block
    : '{' statement* '}'
    ;

statement
    : variableAssignment
    | ifStatement
    | whileStatement
    | forStatement
    | tryCatchStatement
    | returnStatement
    | breakStatement
    | continueStatement
    | throwStatement
    | expressionStatement
    ;

variableAssignment
    : assignable ASSIGN expression
    ;

assignable
    : IDENTIFIER ('.' IDENTIFIER | '[' expression ']')*
    ;

ifStatement
    : IF expression block (ELSE_IF expression block)* (ELSE block)?
    ;

whileStatement
    : WHILE expression block
    ;

forStatement
    : FOR EACH IDENTIFIER IN expression block
    | FOR IDENTIFIER FROM expression TO expression (STEP expression)? block
    ;

tryCatchStatement
    : TRY block catchClause+
    ;

catchClause
    : CATCH '(' IDENTIFIER ')' block
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

throwStatement
    : THROW expression
    ;

expressionStatement
    : expression
    ;

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
    : comparisonExpression ((EQUAL | NOT_EQUAL) comparisonExpression)*
    ;

comparisonExpression
    : additiveExpression ((LT | LTE | GT | GTE) additiveExpression)*
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
    : primaryExpression postfixOperator*
    ;

postfixOperator
    : '(' argumentList? ')'
    | '[' expression ']'
    | '.' IDENTIFIER
    ;

argumentList
    : expression (',' expression)*
    ;

primaryExpression
    : literal
    | IDENTIFIER
    | '(' expression ')'
    | objectLiteral
    | listLiteral
    ;

literal
    : STRING_LITERAL
    | DECIMAL_LITERAL
    | INTEGER_LITERAL
    | TRUE
    | FALSE
    | NULL
    ;

objectLiteral
    : '{' (objectProperty (',' objectProperty)*)? '}'
    ;

objectProperty
    : (IDENTIFIER | STRING_LITERAL) ':' expression
    ;

listLiteral
    : '[' (expression (',' expression)*)? ']'
    ;

// ============================
// LEXER RULES
// ============================

FUNCTION: 'function';
RETURN: 'return';
IF: 'if';
ELSE_IF: 'else_if';
ELSE: 'else';
TRY: 'try';
CATCH: 'catch';
THROW: 'throw';
WHILE: 'while';
FOR: 'for';
EACH: 'each';
IN: 'in';
FROM: 'from';
TO: 'to';
STEP: 'step';
BREAK: 'break';
CONTINUE: 'continue';
TRUE: 'true';
FALSE: 'false';
NULL: 'null';
AND: 'and';
OR: 'or';
NOT: 'not';
INTEGER_TYPE: 'integer';
DECIMAL_TYPE: 'decimal';
BOOLEAN_TYPE: 'boolean';
TEXT_TYPE: 'text';
LIST_TYPE: 'list';
OBJECT_TYPE: 'object';
VOID_TYPE: 'void';

ARROW: '->';
EQUAL: '==';
NOT_EQUAL: '!=';
LTE: '<=';
GTE: '>=';
LT: '<';
GT: '>';
ASSIGN: '=';
PLUS: '+';
MINUS: '-';
MULTIPLY: '*';
DIVIDE: '/';
MODULO: '%';

DECIMAL_LITERAL
    : DIGIT_SEQUENCE '.' DIGIT_SEQUENCE EXPONENT_PART?
    | DIGIT_SEQUENCE EXPONENT_PART
    | '.' DIGIT_SEQUENCE EXPONENT_PART?
    ;

INTEGER_LITERAL
    : DIGIT_SEQUENCE
    ;

STRING_LITERAL
    : '"' ( '\\' . | ~["\\] )* '"'
    ;

IDENTIFIER
    : [a-zA-Z_] [a-zA-Z_0-9]*
    ;

WS
    : [ \t\r\n]+ -> skip
    ;

LINE_COMMENT
    : '#' ~[\r\n]* -> skip
    ;

BLOCK_COMMENT
    : '/*' .*? '*/' -> skip
    ;

fragment DIGIT_SEQUENCE
    : DIGIT ( '_'? DIGIT )*
    ;

fragment DIGIT
    : [0-9]
    ;

fragment EXPONENT_PART
    : [eE] [+\-]? DIGIT_SEQUENCE
    ;
