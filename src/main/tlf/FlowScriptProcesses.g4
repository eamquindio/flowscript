grammar FlowScriptProcesses;

@header {
    package edu.eam.ingesoft.tlf;
}

program
    : (importStmt
      | importJarStmt
      | globalVarDecl
      | functionDecl
      | processDecl
      )* EOF
    ;

importStmt
    : IMPORT STRING_LITERAL (AS IDENTIFIER)?
    ;

importJarStmt
    : IMPORT_JAR STRING_LITERAL AS IDENTIFIER
    ;

globalVarDecl
    : IDENTIFIER ASSIGN expression
    ;

functionDecl
    : FUNCTION IDENTIFIER LPAREN parameterList? RPAREN (ARROW typeName)? block
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : IDENTIFIER COLON typeName
    ;

typeName
    : INTEGER_T
    | DECIMAL_T
    | BOOLEAN_T
    | TEXT_T
    | LIST_T
    | OBJECT_T
    | VOID_T
    ;

processDecl
    : PROCESS IDENTIFIER LBRACE startDecl processBody RBRACE
    ;

processBody
    : (taskDecl | gatewayDecl)* endDecl (taskDecl | gatewayDecl | endDecl)*
    ;

startDecl
    : START ARROW IDENTIFIER
    ;

endDecl
    : END IDENTIFIER
    ;

taskDecl
    : TASK IDENTIFIER LBRACE ACTION COLON statementList RBRACE
    ;

gatewayDecl
    : GATEWAY IDENTIFIER gatewayBody
    ;

gatewayBody
    : LBRACE exclusiveGatewayBody RBRACE
    | PARALLEL LBRACE parallelGatewayBody RBRACE
    ;

exclusiveGatewayBody
    : whenClause+ elseClause?
    ;

whenClause
    : WHEN expression ARROW IDENTIFIER
    ;

elseClause
    : ELSE ARROW IDENTIFIER
    ;

parallelGatewayBody
    : branchClause+ JOIN ARROW IDENTIFIER
    ;

branchClause
    : BRANCH ARROW IDENTIFIER
    ;

statementList
    : (statement)*
    ;

statement
    : gotoStmt
    | ifStmt
    | tryCatchStmt
    | whileStmt
    | forEachStmt
    | forRangeStmt
    | returnStmt
    | assignment
    | exprStmt
    | gatewayStmt
    ;

gotoStmt
    : GO_TO IDENTIFIER
    ;

assignment
    : assignable ASSIGN expression
    ;

assignable
    : IDENTIFIER (memberAccess | indexer)*
    ;

memberAccess
    : DOT IDENTIFIER (indexer)*
    ;

indexer
    : LBRACK expression RBRACK
    ;

exprStmt
    : expression
    ;

ifStmt
    : IF expression block (ELSE_IF expression block)* (ELSE block)?
    ;

tryCatchStmt
    : TRY block CATCH LPAREN IDENTIFIER RPAREN block
    ;

whileStmt
    : WHILE expression block
    ;

forEachStmt
    : FOR EACH IDENTIFIER IN expression block
    ;

forRangeStmt
    : FOR IDENTIFIER FROM expression TO expression (STEP expression)? block
    ;

returnStmt
    : RETURN expression?
    ;

gatewayStmt
    : GATEWAY IDENTIFIER LBRACE exclusiveGatewayBody RBRACE
    ;

block
    : LBRACE statementList RBRACE
    ;

expression
    : logicOrExpr
    ;

logicOrExpr
    : logicAndExpr (OR logicAndExpr)*
    ;

logicAndExpr
    : equalityExpr (AND equalityExpr)*
    ;

equalityExpr
    : relationalExpr ((EQ | NEQ) relationalExpr)*
    ;

relationalExpr
    : additiveExpr ((LT | LTE | GT | GTE) additiveExpr)*
    ;

additiveExpr
    : multiplicativeExpr ((PLUS | MINUS) multiplicativeExpr)*
    ;

multiplicativeExpr
    : unaryExpr ((MUL | DIV | MOD) unaryExpr)*
    ;

unaryExpr
    : (NOT | MINUS) unaryExpr
    | callMemberIndexExpr
    ;

callMemberIndexExpr
    : primaryExpr (callSuffix | memberSuffix | indexSuffix)*
    ;

callSuffix
    : LPAREN argumentList? RPAREN
    ;

memberSuffix
    : DOT IDENTIFIER
    ;

indexSuffix
    : LBRACK expression RBRACK
    ;

argumentList
    : expression (COMMA expression)*
    ;

primaryExpr
    : literal
    | INPUT
    | IDENTIFIER
    | LPAREN expression RPAREN
    | listLiteral
    | objectLiteral
    ;

literal
    : TRUE
    | FALSE
    | NULL
    | DECIMAL
    | INTEGER
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

ACTION      : 'action';
AND         : 'and';
ARROW       : '->';
AS          : 'as';
ASSIGN      : '=';
BOOLEAN_T   : 'boolean';
BRANCH      : 'branch';
BREAK       : 'break';
CATCH       : 'catch';
COLON       : ':';
COMMA       : ',';
CONTINUE    : 'continue';
DECIMAL_T   : 'decimal';
DIV         : '/';
DOT         : '.';
EACH        : 'each';
ELSE        : 'else';
ELSE_IF     : 'else_if';
END         : 'end';
EQ          : '==';
FALSE       : 'false';
FOR         : 'for';
FROM        : 'from';
FUNCTION    : 'function';
GATEWAY     : 'gateway';
GO_TO       : 'go_to';
GT          : '>';
GTE         : '>=';
IF          : 'if';
IMPORT      : 'import';
IMPORT_JAR  : 'import_jar';
IN          : 'in';
INPUT       : 'input';
INTEGER_T   : 'integer';
JOIN        : 'join';
LBRACE      : '{';
LBRACK      : '[';
LPAREN      : '(';
LIST_T      : 'list';
LT          : '<';
LTE         : '<=';
MINUS       : '-';
MOD         : '%';
MUL         : '*';
NEQ         : '!=';
NOT         : 'not';
NULL        : 'null';
OBJECT_T    : 'object';
OR          : 'or';
PARALLEL    : 'parallel';
PLUS        : '+';
PROCESS     : 'process';
RBRACE      : '}';
RBRACK      : ']';
RETURN      : 'return';
RPAREN      : ')';
START       : 'start';
STEP        : 'step';
TASK        : 'task';
TEXT_T      : 'text';
THROW       : 'throw';
TO          : 'to';
TRUE        : 'true';
TRY         : 'try';
VOID_T      : 'void';
WHEN        : 'when';
WHILE       : 'while';


DECIMAL     : [0-9]+ '.' [0-9]+ ( [eE] [+-]? [0-9]+ )?;
INTEGER     : [0-9]+;
STRING_LITERAL
    : '"' ( '\\' [btnr"\\] | ~["\\\r\n] )* '"'
    ;

IDENTIFIER
    : [a-zA-Z_] [a-zA-Z0-9_]*
    ;

LINE_COMMENT
    : '#' ~[\r\n]* -> skip
    ;

BLOCK_COMMENT
    : '/*' .*? '*/' -> skip
    ;

WS
    : [ \t\r\n]+ -> skip
    ;
