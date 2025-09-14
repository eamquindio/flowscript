grammar FlowScriptProcesses;

@header {
    package edu.eam.ingesoft.tlf;
}

program
    : (globalVar | functionDecl | processDecl | importStmt | importJarStmt)* EOF
    ;

importStmt
    : 'import' STRING ('as' IDENTIFIER)?
    ;

importJarStmt
    : 'import_jar' STRING ('as' IDENTIFIER)?
    ;

globalVar
    : IDENTIFIER '=' expression
    ;

functionDecl
    : 'function' IDENTIFIER '(' params? ')' '->' type block
    ;

params
    : param (',' param)*
    ;

param
    : IDENTIFIER ':' type
    ;

type
    : 'integer'
    | 'float'
    | 'boolean'
    | 'string'
    | 'object'
    | 'void'
    ;

processDecl
    : 'process' IDENTIFIER '{'
        startDecl
        (processElementNoStartNoEnd)*
        endDecl+
      '}'
    ;

processElementNoStartNoEnd
    : taskDecl
    | gatewayDecl
    | importStmt
    | importJarStmt
    | functionDecl
    | globalVar
    ;

startDecl
    : 'start' '->' IDENTIFIER
    ;

endDecl
    : 'end' IDENTIFIER
    ;

taskDecl
    : 'task' IDENTIFIER '{' actionDecl '}'
    ;

actionDecl
    : 'action:' statement+
    ;

gatewayDecl
    : 'gateway' IDENTIFIER 'parallel' '{' branchDecl+ joinDecl+ '}'
    | 'gateway' IDENTIFIER '{' whenClause+ elseClause? '}'
    ;

branchDecl
    : 'branch' '->' IDENTIFIER
    ;

joinDecl
    : 'join' '->' IDENTIFIER
    ;

whenClause
    : 'when' expression '->' IDENTIFIER
    ;

elseClause
    : 'else' '->' IDENTIFIER
    ;

statement
    : assignment
    | goToStmt
    | ifStmt
    | forStmt
    | tryCatchStmt
    | gatewayStmt
    | exprStmt
    | block
    ;

assignment
    : (memberAccessExpr | IDENTIFIER) '=' expression
    ;

goToStmt
    : 'go_to' IDENTIFIER
    ;

ifStmt
    : 'if' expression block ('else' block)?
    ;

forStmt
    : 'for' 'each' IDENTIFIER 'in' expression block
    ;

tryCatchStmt
    : 'try' block 'catch' '(' IDENTIFIER ')' block
    ;

gatewayStmt
    : 'gateway' IDENTIFIER 'parallel' '{' branchDecl+ joinDecl+ '}'
    | 'gateway' IDENTIFIER '{' whenClause+ elseClause? '}'
    ;

exprStmt
    : expression
    ;

expression
    : expression 'and' expression
    | expression 'or' expression
    | 'not' expression
    | expression ('==' | '!=') expression
    | expression ('<' | '<=' | '>' | '>=') expression
    | expression ('+' | '-') expression
    | expression ('*' | '/' | '%') expression
    | primary
    ;

primary
    : literal
    | memberAccessExpr
    | '(' expression ')'
    ;

memberAccessExpr
    : IDENTIFIER ('.' IDENTIFIER | '[' expression ']' | '(' args? ')')*
    ;

args
    : expression (',' expression)*
    ;

literal
    : STRING
    | NUMBER
    | 'true'
    | 'false'
    | 'null'
    | listLiteral
    | objectLiteral
    ;

listLiteral
    : '[' (expression (',' expression)*)? ']'
    ;

objectLiteral
    : '{' (pair (',' pair)*)? '}'
    ;

pair
    : IDENTIFIER ':' expression
    ;

block
    : '{' statement* '}'
    ;

IDENTIFIER
    : [a-zA-Z_][a-zA-Z0-9_]*
    ;

NUMBER
    : [0-9]+ ('.' [0-9]+)?
    ;

STRING
    : '"' (~["\\] | '\\' .)* '"'
    ;

LINE_COMMENT
    : '#' ~[\r\n]* -> skip
    ;

MULTILINE_COMMENT
    : '/*' .*? '*/' -> skip
    ;

WS
    : [ \t\r\n]+ -> skip
    ;
