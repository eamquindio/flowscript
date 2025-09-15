grammar FlowScriptFunctions;

// ===================================================================
// Package for generated classes (requerido por los tests en Java)
// ===================================================================
@header {
package edu.eam.ingesoft.tlf;
}

// ===================================================================
// Parser Rules
// ===================================================================

functionProgram
    : (functionDeclaration)* EOF
    ;

functionDeclaration
    : FUNCTION functionName LPAREN parameterList? RPAREN (ARROW type)? block
    ;

functionName
    : IDENTIFIER
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : IDENTIFIER COLON type
    ;

type
    : INTEGER_T
    | DECIMAL_T
    | BOOLEAN_T
    | TEXT_T
    | LIST_T
    | OBJECT_T
    | VOID_T
    ;

// ---------------- Statements ----------------

block
    : LBRACE statement* RBRACE
    ;

statement
    : variableAssignment (SEMI)?
    | ifStatement
    | whileStatement
    | forEachStatement
    | forRangeStatement
    | breakStatement (SEMI)?
    | continueStatement (SEMI)?
    | returnStatement (SEMI)?
    | tryCatchStatement
    | throwStatement (SEMI)?
    | expressionStatement (SEMI)?
    ;

variableAssignment
    : leftHandSide ASSIGN expression
    ;

leftHandSide
    : IDENTIFIER (memberAccess | indexAccess)*
    ;

memberAccess
    : DOT IDENTIFIER
    ;

indexAccess
    : LBRACK expression RBRACK
    ;

ifStatement
    : IF expression block elseIfClause* (ELSE block)?
    ;

elseIfClause
    : ELSE_IF expression block
    ;

whileStatement
    : WHILE expression block
    ;

// for each element in collection { ... }
forEachStatement
    : FOR EACH IDENTIFIER IN expression block
    ;

// for i from a to b step c { ... }
forRangeStatement
    : FOR IDENTIFIER FROM expression TO expression (STEP expression)? block
    ;

breakStatement
    : BREAK
    ;

continueStatement
    : CONTINUE
    ;

returnStatement
    : RETURN expression?
    ;

// ✅ Soporte para múltiples catch y finally opcional
tryCatchStatement
    : TRY block catchClause+ (FINALLY block)?
    ;

catchClause
    : CATCH LPAREN IDENTIFIER RPAREN block
    ;

throwStatement
    : THROW expression
    ;

expressionStatement
    : expression
    ;

// ---------------- Expressions (precedence & associativity) ----------------

expression
    : orExpression
    ;

orExpression
    : andExpression (OR andExpression)*
    ;

andExpression
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
    : primaryExpression ( postfixOp )*
    ;

postfixOp
    : LPAREN argumentList? RPAREN
    | memberAccess
    | indexAccess
    ;

primaryExpression
    : literal
    | IDENTIFIER
    | LPAREN expression RPAREN
    ;

argumentList
    : expression (COMMA expression)*
    ;

// ---------------- Literals ----------------

literal
    : integerLiteral
    | decimalLiteral
    | booleanLiteral
    | nullLiteral
    | stringLiteral
    | listLiteral
    | objectLiteral
    ;

integerLiteral
    : INTEGER_LITERAL
    ;

decimalLiteral
    : DECIMAL_LITERAL
    ;

booleanLiteral
    : TRUE
    | FALSE
    ;

nullLiteral
    : NULL
    ;

stringLiteral
    : STRING_LITERAL
    ;

listLiteral
    : LBRACK (expression (COMMA expression)*)? RBRACK
    ;

objectLiteral
    : LBRACE (objectPair (COMMA objectPair)*)? RBRACE
    ;

objectPair
    : IDENTIFIER COLON expression
    ;

// ===================================================================
// Lexer Rules
// ===================================================================

// Palabras clave (solo funciones/control; SIN keywords de procesos)
FUNCTION    : 'function';
RETURN      : 'return';
IF          : 'if';
ELSE_IF     : 'else_if';
ELSE        : 'else';
TRY         : 'try';
CATCH       : 'catch';
FINALLY     : 'finally';
THROW       : 'throw';
WHILE       : 'while';
FOR         : 'for';
EACH        : 'each';
IN          : 'in';
FROM        : 'from';
TO          : 'to';
STEP        : 'step';
BREAK       : 'break';
CONTINUE    : 'continue';

// Tipos
INTEGER_T   : 'integer';
DECIMAL_T   : 'decimal';
BOOLEAN_T   : 'boolean';
TEXT_T      : 'text';
LIST_T      : 'list';
OBJECT_T    : 'object';
VOID_T      : 'void';
NULL        : 'null';

// Literales booleanos
TRUE        : 'true';
FALSE       : 'false';

// Operadores y puntuación
ARROW       : '->';
ASSIGN      : '=';
DOT         : '.';
COMMA       : ',';
COLON       : ':';
SEMI        : ';';

LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
LBRACK      : '[';
RBRACK      : ']';

PLUS        : '+';
MINUS       : '-';
MUL         : '*';
DIV         : '/';
MOD         : '%';

LT          : '<';
GT          : '>';
LE          : '<=';
GE          : '>=';
EQ          : '==';
NEQ         : '!=';

AND         : 'and';
OR          : 'or';
NOT         : 'not';

// Identificadores
IDENTIFIER
    : [a-zA-Z_][a-zA-Z0-9_]*
    ;

// Números
fragment DIGIT : [0-9];
fragment NDIGIT: [0-9_];
INTEGER_LITERAL
    : '-'? DIGIT (NDIGIT)*
    ;

DECIMAL_LITERAL
    : '-'? DIGIT+ ('.' DIGIT+)? ([eE] [+\-]? DIGIT+)?
    ;

// Strings con escapes
STRING_LITERAL
    : '"' ( '\\' [btnr"\\] | '\\u' HEX HEX HEX HEX | ~["\\\r\n] )* '"'
    ;

fragment HEX : [0-9a-fA-F];

// Comentarios
LINE_COMMENT
    : '#' ~[\r\n]* -> skip
    ;

BLOCK_COMMENT
    : '/*' .*? '*/' -> skip
    ;

// Espacios
WS
    : [ \t\r\n]+ -> skip
    ;
