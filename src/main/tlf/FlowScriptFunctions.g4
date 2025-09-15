grammar FlowScriptFunctions;

@header {
    package edu.eam.ingesoft.tlf;
}

functionProgram
    : (functionDeclaration | statement)* EOF
    ;

statement
    : assignmentStatement
    | loopStatement
    | ifStatement
    | tryCatchStatement
    | returnStatement
    | breakStatement
    | continueStatement
    | throwStatement
    | expressionStatement
    | block
    ;

functionDeclaration
    : 'function' Identifier '(' parameterList? ')' ('->' type)? block
    ;

parameterList
    : parameter (',' parameter)*
    ;

parameter
    : Identifier ':' type
    ;

type
    : simpleType ('<' type (',' type)* '>')?
    ;

simpleType
    : 'integer'
    | 'decimal'
    | 'boolean'
    | 'string'
    | 'text'
    | 'list'
    | 'object'
    | 'void'
    ;

block
    : '{' statement* '}'
    ;

loopStatement
    : 'for' 'each' Identifier 'in' expression block
    | 'for' Identifier 'from' expression 'to' expression ('step' expression)? block
    | 'while' expression block
    ;

ifStatement
    : 'if' expression block (('else' 'if' | 'else_if') expression block)* ('else' block)?
    ;

tryCatchStatement
    : 'try' block ('catch' '(' Identifier ')' block)+
    ;

returnStatement
    : 'return' expression? ';'?
    ;

breakStatement
    : 'break' ';'?
    ;

continueStatement
    : 'continue' ';'?
    ;

throwStatement
    : 'throw' expression ';'?
    ;


lvalue
    : Identifier ('.' Identifier | '[' expression ']')*
    ;

assignmentStatement
    : lvalue '=' expression ';'?
    ;

expressionStatement
    : expression ';'?
    ;

expression
    : logicOr
    ;

logicOr
    : logicAnd (('||' | 'or') logicAnd)*
    ;

logicAnd
    : equality (('&&' | 'and') equality)*
    ;

equality
    : comparison (('==' | '!=') comparison)*
    ;

comparison
    : addition (('>' | '>=' | '<' | '<=') addition)*
    ;

addition
    : multiplication (('+' | '-') multiplication)*
    ;

multiplication
    : unary (('*' | '/' | '%') unary)*
    ;

unary
    : ('!' | 'not' | '-') unary
    | memberExpr
    ;

memberExpr
    : primary ('.' Identifier | '.' functionCall | '[' expression ']')*
    ;

primary
    : literal
    | Identifier
    | functionCall
    | listLiteral
    | objectLiteral
    | '(' expression ')'
    ;

functionCall
    : Identifier '(' argumentList? ')'
    ;

argumentList
    : expression (',' expression)*
    ;

literal
    : DecimalLiteral
    | IntegerLiteral
    | BooleanLiteral
    | StringLiteral
    | 'null'
    ;

listLiteral
    : '[' (expression (',' expression)*)? ']'
    ;

objectLiteral
    : '{' (objectEntry (',' objectEntry)*)? '}'
    ;

objectEntry
    : (Identifier | StringLiteral) ':' expression
    ;

DecimalLiteral : [0-9]+ '.' [0-9]+ ;
IntegerLiteral : [0-9]+ ;
BooleanLiteral : 'true' | 'false' ;
StringLiteral  : '"' (~["\\] | '\\' .)* '"' ;

Identifier     : [a-zA-Z_][a-zA-Z0-9_]* ;

WS             : [ \t\r\n]+ -> skip ;
LINE_COMMENT   : '//' ~[\r\n]* -> skip ;
HASH_COMMENT   : '#' ~[\r\n]* -> skip ;
BLOCK_COMMENT  : '/*' .*? '*/' -> skip ;
