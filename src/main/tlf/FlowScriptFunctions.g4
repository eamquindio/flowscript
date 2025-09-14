grammar FlowScriptFunctions;

@header {
    package edu.eam.ingesoft.tlf;
}

// ============================
// PARSER RULES
// ============================

functionProgram
    : functionDeclaration* EOF
    ;

functionDeclaration
    : FUNCTION functionName LPAREN parameterList? RPAREN (ARROW dataType)? block
    ;

functionName
    : IDENTIFIER
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : IDENTIFIER COLON dataType
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
    | breakStatement
    | continueStatement
    | returnStatement
    | expressionStatement
    ;

ifStatement
    : IF expression block (ELSE_IF expression block)* (ELSE block)?
    ;

whileStatement
    : WHILE expression block
    ;

forEachStatement
    : FOR EACH IDENTIFIER IN expression block
    ;

forRangeStatement
    : FOR IDENTIFIER FROM expression TO expression (STEP expression)? block
    ;

tryCatchStatement
    : TRY block (CATCH LPAREN IDENTIFIER RPAREN block)+
    ;

throwStatement
    : THROW expression
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

expressionStatement
    : expression
    ;

// ============================
// EXPRESSIONS (precedence and associativity)
// ============================

expression
    : assignmentExpression
    ;

assignmentExpression
    : leftHandSide ASSIGN assignmentExpression    #AssignmentExpr
    | logicalOrExpression                         #ExprNoAssign
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
    : (NOT | MINUS) unaryExpression
    | postfixExpression
    ;

postfixExpression
    : primaryExpression postfixPart*
    ;

postfixPart
    : DOT IDENTIFIER                 #MemberAccess
    | LBRACK expression RBRACK       #IndexAccess
    | LPAREN argumentList? RPAREN    #FunctionCall
    ;

leftHandSide
    : IDENTIFIER (DOT IDENTIFIER | LBRACK expression RBRACK)*
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
    : NULL_T
    | TRUE_T
    | FALSE_T
    | DECIMAL_LITERAL
    | INTEGER_LITERAL
    | STRING_LITERAL
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

// ============================
// LEXER RULES (TOKENS)
// ============================

// Keywords
FUNCTION : 'function';
RETURN   : 'return';
IF       : 'if';
ELSE_IF  : 'else_if';
ELSE     : 'else';
WHILE    : 'while';
FOR      : 'for';
EACH     : 'each';
IN       : 'in';
FROM     : 'from';
TO       : 'to';
STEP     : 'step';
BREAK    : 'break';
CONTINUE : 'continue';
TRY      : 'try';
CATCH    : 'catch';
THROW    : 'throw';

// Types and literals
INTEGER_T : 'integer';
DECIMAL_T : 'decimal';
BOOLEAN_T : 'boolean';
TEXT_T    : 'text';
LIST_T    : 'list';
OBJECT_T  : 'object';
VOID_T    : 'void';
TRUE_T    : 'true';
FALSE_T   : 'false';
NULL_T    : 'null';

// Operators
AND : 'and';
OR  : 'or';
NOT : 'not';

ASSIGN : '=';
PLUS   : '+';
MINUS  : '-';
MUL    : '*';
DIV    : '/';
MOD    : '%';
LT     : '<';
GT     : '>';
LTE    : '<=';
GTE    : '>=';
EQ     : '==';
NEQ    : '!=';

// Delimiters & punctuation
LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';
LBRACK : '[';
RBRACK : ']';
COMMA  : ',';
COLON  : ':';
DOT    : '.';
ARROW  : '->';

// Identifiers
IDENTIFIER: [a-zA-Z_] [a-zA-Z0-9_]*;

// Numbers
DECIMAL_LITERAL : DIGITS '.' DIGITS (EXPONENT)? | DIGITS EXPONENT;
INTEGER_LITERAL : DIGITS;

fragment DIGITS   : [0-9] ([0-9_]* [0-9])?;
fragment EXPONENT : [eE] [+-]? DIGITS;

// Strings with escapes
STRING_LITERAL: '"' ( ESC_SEQ | ~["\\\r\n] )* '"';

fragment ESC_SEQ     : '\\' [\\/"bfnrt] | UNICODE_ESC;
fragment UNICODE_ESC : '\\' 'u' HEX HEX HEX HEX;
fragment HEX         : [0-9a-fA-F];

// Comments and whitespace
LINE_COMMENT  : '#' ~[\r\n]* -> skip;
BLOCK_COMMENT : '/*' .*? '*/' -> skip;
WS            : [ \t\r\n]+ -> skip;
