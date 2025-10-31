grammar FlowScriptProcesses;

@header {
    package edu.eam.ingesoft.tlf;
}

program
    : imports? globalDecl* (functionDecl | processDecl)* EOF
    ;

imports
    : importStmt (importStmt)*
    ;

importStmt
    : 'import' STRING ( 'as' ID )?
    | 'import_jar' STRING 'as' ID
    ;

globalDecl
    : ID '=' expr ';'? 
    ;

functionDecl
    : 'function' ID '(' paramList? ')' ( '->' type )? '{' stmt* '}'
    ;

paramList
    : param (',' param)*
    ;

param
    : ID ':' type
    ;

type
    : 'integer'
    | 'decimal'
    | 'boolean'
    | 'text'
    | 'list'
    | 'object'
    | 'void'
    ;

processDecl
    : 'process' ID '{' processBody '}'
    ;

processBody
    : startDecl nodeDecl* endDecl+
    ;

startDecl
    : 'start' '->' ID
    ;

nodeDecl
    : taskDecl
    | gatewayDecl
    ;

taskDecl
    : 'task' ID '{' 'action' ':' stmt* '}'
    ;

gatewayDecl
    : 'gateway' ID 'parallel' '{' parallelGatewayBody '}'
    | 'gateway' ID '{' exclusiveGatewayBody '}'
    ;

parallelGatewayBody
    : ( 'branch' '->' ID )+ ( 'join' '->' ID )?
    ;

exclusiveGatewayBody
    : ( 'when' expr '->' ID )+ ( 'else' '->' ID )?
    ;

endDecl
    : 'end' ID
    ;

stmt
    : assignStmt
    | ifStmt
    | gotoStmt
    | tryCatchStmt
    | forEachStmt
    | gatewayStmt
    | returnStmt
    | exprStmt
    ;

assignStmt
    : target '=' expr ';'?
    ;

target
    : ID ( callOrAccess )*
    ;

ifStmt
    : 'if' expr '{' stmt* '}' ( 'else_if' expr '{' stmt* '}' )* ( 'else' '{' stmt* '}' )?
    ;

gotoStmt
    : GO_TO ID ';'?
    ;

tryCatchStmt
    : 'try' '{' stmt* '}' ( 'catch' '(' ID ')' '{' stmt* '}' )?
    ;

forEachStmt
    : 'for' 'each' ID 'in' expr '{' stmt* '}'
    ;

gatewayStmt
    : 'gateway' ID 'parallel' '{' parallelGatewayBody '}'
    | 'gateway' ID '{' exclusiveGatewayBody '}'
    ;

exprStmt
    : expr ';'?
    ;

returnStmt
    : 'return' expr? ';'?
    ;

expr
    : orExpr
    ;

orExpr
    : andExpr ( 'or' andExpr )*
    ;

andExpr
    : eqExpr ( 'and' eqExpr )*
    ;

eqExpr
    : relExpr ( ('==' | '!=') relExpr )*
    ;

relExpr
    : addExpr ( ('<' | '>' | '<=' | '>=') addExpr )*
    ;

addExpr
    : mulExpr ( ('+' | '-') mulExpr )*
    ;

mulExpr
    : unaryExpr ( ('*' | '/' | '%') unaryExpr )*
    ;

unaryExpr
    : '-' unaryExpr
    | 'not' unaryExpr
    | primary
    ;

primary
    : literal
    | ID ( callOrAccess )*
    | '(' expr ')'
    ;

callOrAccess
    : '(' argList? ')'
    | '.' ID
    | '[' expr ']'
    ;

argList
    : expr (',' expr)*
    ;

literal
    : STRING
    | NUMBER
    | 'null'
    | 'true'
    | 'false'
    | listLiteral
    | objectLiteral
    ;

listLiteral
    : '[' (expr (',' expr)*)? ']'
    ;

objectLiteral
    : '{' (ID ':' expr (',' ID ':' expr)*)? '}'
    ;

GO_TO
    : 'go_to'
    ;
// Keywords as explicit tokens to avoid ambiguity with ID
GATEWAY: 'gateway';
BRANCH: 'branch';
JOIN: 'join';
WHEN: 'when';
ELSE: 'else';
FOR: 'for';
EACH: 'each';
FUNCTION: 'function';
PROCESS: 'process';
TASK: 'task';
END: 'end';
START: 'start';
IMPORT: 'import';
IMPORT_JAR: 'import_jar';
TRY: 'try';
CATCH: 'catch';
RETURN: 'return';
NOT: 'not';
AND: 'and';
OR: 'or';
ACTION: 'action';

ID
    : [a-zA-Z_] [a-zA-Z0-9_]*
    ;

NUMBER
    : [0-9]+ ('.' [0-9]+)?
    ;

STRING
    : '"' (~["\\] | '\\' . )* '"'
    ;

WS
    : [ \t\r\n]+ -> skip
    ;

COMMENT
    : '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    : '//' ~[\r\n]* -> skip
    ;

POUND_COMMENT
    : '#' ~[\r\n]* -> skip
    ;