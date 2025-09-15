grammar FlowScriptProcesses;

@header {
    package edu.eam.ingesoft.tlf;
}

// ============================
// PARSER RULES
// ============================

program
    : (importStatement | globalVar | functionDecl | processDecl)* EOF
    ;

importStatement
    : IMPORT STRING (AS ID)? (SEMICOLON)?
    | IMPORT_JAR STRING (AS ID)? (SEMICOLON)?
    ;

globalVar
    : ID ASSIGN expression (SEMICOLON)?
    ;

functionDecl
    : FUNCTION ID LPAREN paramList? RPAREN (ARROW type)? LBRACE statementList RBRACE (SEMICOLON)?
    ;

paramList
    : param (COMMA param)*
    ;

param
    : ID COLON type
    ;

type
    : BOOLEAN
    | TEXT
    | INTEGER
    | VOID
    | OBJECT
    ;

processDecl
    : PROCESS ID LBRACE processBody RBRACE (SEMICOLON)?
    ;

processBody
    : startNode processElement* endNode+
    ;

processElement
    : taskNode
    | gatewayNode
    ;

startNode
    : START ARROW ID (SEMICOLON)?
    ;

taskNode
    : TASK ID LBRACE actionBlock RBRACE (SEMICOLON)?
    ;

actionBlock
    : ACTION COLON statementList
    ;

gatewayNode
    : GATEWAY ID (PARALLEL)? LBRACE gatewayBody RBRACE (SEMICOLON)?
    ;

gatewayBody
    : parallelGateway
    | exclusiveGateway
    ;

parallelGateway
    : PARALLEL? branchStmt+ joinStmt
    ;

exclusiveGateway
    : whenStmt+ (ELSE ARROW ID (SEMICOLON)?)?
    ;

branchStmt
    : BRANCH ARROW ID (SEMICOLON)?
    ;

whenStmt
    : WHEN condition ARROW ID (SEMICOLON)?
    ;

joinStmt
    : JOIN ARROW ID (SEMICOLON)?
    ;

endNode
    : END ID (SEMICOLON)?
    ;

statementList
    : statement*
    ;

statement
    : assignment
    | arrayAssignment
    | ifStatement
    | forStatement
    | tryCatch
    | gotoStatement
    | gatewayStmt
    | returnStatement
    | expressionStatement
    ;

assignment
    : ID ASSIGN expression (SEMICOLON)?
    ;

arrayAssignment
    : expression LBRACKET expression RBRACKET ASSIGN expression (SEMICOLON)?
    ;

ifStatement
    : IF condition LBRACE statementList RBRACE (ELSE LBRACE statementList RBRACE)? (SEMICOLON)?
    ;

forStatement
    : FOR EACH ID IN expression LBRACE statementList RBRACE (SEMICOLON)?
    ;

tryCatch
    : TRY LBRACE statementList RBRACE CATCH LPAREN ID RPAREN LBRACE statementList RBRACE (SEMICOLON)?
    ;

gotoStatement
    : GOTO ID (SEMICOLON)?
    ;

gatewayStmt
    : GATEWAY ID LBRACE (whenStmt+ (ELSE ARROW ID (SEMICOLON)?)?) RBRACE (SEMICOLON)?
    ;

expressionStatement
    : expression (SEMICOLON)?
    ;

returnStatement
    : RETURN expression (SEMICOLON)?
    ;

expression
    : primary                                                               
    | expression DOT ID
    | expression LPAREN expressionList? RPAREN
    | expression LBRACKET expression RBRACKET
    | NOT expression
    | expression op=(MULT | DIV) expression
    | expression op=(PLUS | MINUS) expression
    | expression op=(EQUALS | NOT_EQUALS | GT | LT | GTE | LTE) expression
    | expression op=(AND | OR) expression
    ;

primary
    : literal
    | ID
    | LPAREN expression RPAREN
    | objectLiteral
    | arrayLiteral
    ;

expressionList
    : expression (COMMA expression)*
    ;

literal
    : STRING
    | NUMBER
    | TRUE
    | FALSE
    | NULL
    ;

objectLiteral
    : LBRACE (objectField (COMMA objectField)*)? RBRACE
    ;

objectField
    : ID COLON expression
    | STRING COLON expression
    ;

arrayLiteral
    : LBRACKET (expressionList)? RBRACKET
    ;

condition
    : expression
    ;

// ============================
// LEXER RULES (TOKENS)
// ============================

IMPORT      : 'import';
IMPORT_JAR  : 'import_jar';
AS          : 'as';
FUNCTION    : 'function';
PROCESS     : 'process';
START       : 'start';
TASK        : 'task';
ACTION      : 'action';
GATEWAY     : 'gateway';
END         : 'end';
PARALLEL    : 'parallel';
BRANCH      : 'branch';
JOIN        : 'join';
WHEN        : 'when';
ELSE        : 'else';
IF          : 'if';
FOR         : 'for';
EACH        : 'each';
IN          : 'in';
TRY         : 'try';
CATCH       : 'catch';
GOTO        : 'goto' | 'go_to';
RETURN      : 'return';

GET         : 'get';
INSERT      : 'insert';
EXECUTE     : 'execute';
LOG_ERROR   : 'log_error';

BOOLEAN     : 'boolean';
TEXT        : 'text';
INTEGER     : 'integer';
VOID        : 'void';
OBJECT      : 'object';

ARROW       : '->';
EQUALS      : '==';
NOT_EQUALS  : '!=';
AND         : 'and';
OR          : 'or';
NOT         : 'not';
GT          : '>';
LT          : '<';
GTE         : '>=';
LTE         : '<=';
ASSIGN      : '=';
PLUS        : '+';
MINUS       : '-';
MULT        : '*';
DIV         : '/';

LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
LBRACKET    : '[';
RBRACKET    : ']';
COMMA       : ',';
DOT         : '.';
COLON       : ':';
SEMICOLON   : ';';

STRING      : '"' (~["\\] | '\\' .)* '"';
NUMBER      : [0-9]+ ('.' [0-9]+)?;
TRUE        : 'true';
FALSE       : 'false';
NULL        : 'null';

ID          : [a-zA-Z_][a-zA-Z0-9_]*;

WS          : [ \t\r\n]+ -> skip;
COMMENT     : '#' ~[\r\n]* -> skip;
MULTI_COMMENT : '/*' .*? '*/' -> skip;
