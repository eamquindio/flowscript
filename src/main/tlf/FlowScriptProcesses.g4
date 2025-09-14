grammar FlowScriptProcesses;

@header {
       package edu.eam.ingesoft.tlf;
}

program
    : (importDecl | importJarDecl)* (globalVarDecl | functionDecl | processDecl)* EOF
    ;

// ---------- Imports ----------
importDecl
    : IMPORT STRING (AS ID)? SEMI?
    ;

importJarDecl
    : IMPORT_JAR STRING (AS ID)? SEMI?
    ;

// ---------- Variables globales ----------
globalVarDecl
    : lvalue ASSIGN expression SEMI?
    ;

// ---------- Funciones ----------
functionDecl
    : FUNCTION ID LPAREN paramListOpt? RPAREN returnTypeOpt block
    ;

paramListOpt
    : param (COMMA param)*
    ;

param
    : ID COLON typeRef
    ;

returnTypeOpt
    : (ARROW typeRef)?
    ;

typeRef
    : INTEGER_T | DECIMAL_T | BOOLEAN_T | TEXT_T | LIST_T | OBJECT_T | VOID_T
    ;

// ---------- Procesos (estructura de flujo principal) ----------
processDecl
    : PROCESS ID LBRACE
          startNode
          processInner*
          endNode+
      RBRACE
    ;

processInner
    : taskNode
    | gatewayNode
    ;

// ---------- Nodos ----------
startNode
    : START ARROW ID SEMI?
    ;

endNode
    : END ID SEMI?
    ;

// Tarea con bloque de acciones (statements)
taskNode
    : TASK ID LBRACE
          ACTION COLON statement*
      RBRACE
    ;

// ---------- Gateways como nodo ----------
gatewayNode
    : exclusiveGatewayNode
    | parallelGatewayNode
    ;

exclusiveGatewayNode
    : GATEWAY ID LBRACE
          gatewayWhen+ gatewayElse?
      RBRACE
    ;

parallelGatewayNode
    : GATEWAY ID PARALLEL LBRACE
          gatewayBranch+ gatewayJoin
      RBRACE
    ;

gatewayWhen
    : WHEN expression ARROW ID SEMI?
    ;

gatewayElse
    : ELSE ARROW ID SEMI?
    ;

gatewayBranch
    : BRANCH ARROW ID SEMI?
    ;

gatewayJoin
    : JOIN ARROW ID SEMI?
    ;

// ---------- Sentencias ----------
block
    : LBRACE statement* RBRACE
    ;

statement
    : lvalue ASSIGN expression SEMI?                   #assignStmt
    | goToStatement                                    #gotoStmt
    | ifStatement                                      #ifStmt
    | whileStatement                                   #whileStmt
    | forEachStatement                                 #forEachStmt
    | forRangeStatement                                #forRangeStmt
    | tryCatchStatement                                #tryCatchStmt
    | throwStatement                                   #throwStmt
    | returnStatement                                  #returnStmt
    | gatewayStatement                                 #gatewayStmtInAction
    | block                                            #blockStmt
    | expression SEMI?                                 #exprStmt
    ;


lvalue
    : ID (DOT ID | LBRACK expression RBRACK)*
    ;


goToStatement
    : GO_TO ID SEMI?
    ;


ifStatement
    : IF expression block (ELSE_IF expression block)* (ELSE block)?
    ;

whileStatement
    : WHILE expression block
    ;


forEachStatement
    : FOR EACH ID IN expression block
    ;


forRangeStatement
    : FOR ID FROM expression TO expression (STEP expression)? block
    ;


tryCatchStatement
    : TRY block CATCH LPAREN ID RPAREN block
    ;


throwStatement
    : THROW expression SEMI?
    ;


returnStatement
    : RETURN expression? SEMI?
    ;


gatewayStatement
    : gatewayStmtExclusive
    | gatewayStmtParallel
    ;

gatewayStmtExclusive
    : GATEWAY ID LBRACE
          gatewayWhen+ gatewayElse?
      RBRACE
    ;

gatewayStmtParallel
    : GATEWAY ID PARALLEL LBRACE
          gatewayBranch+ gatewayJoin
      RBRACE
    ;


expression
    : expression OR expression                           #orExpr
    | expression AND expression                          #andExpr
    | expression (EQ | NEQ) expression                   #eqExpr
    | expression (LT | GT | LE | GE) expression          #relExpr
    | expression (ADD | SUB) expression                  #addExpr
    | expression (MUL | DIV | MOD) expression            #mulExpr
    | NOT expression                                     #unaryNotExpr
    | SUB expression                                     #unaryMinusExpr
    | expression DOT ID                                  #memberAccessExpr
    | expression LBRACK expression RBRACK                #indexAccessExpr
    | expression LPAREN argListOpt? RPAREN               #callExpr
    | primary                                            #primaryExpr
    ;

argListOpt
    : expression (COMMA expression)*
    ;

primary
    : LPAREN expression RPAREN
    | listLiteral
    | objectLiteral
    | literal
    | INPUT
    | ID
    ;

listLiteral
    : LBRACK (expression (COMMA expression)*)? RBRACK
    ;

objectLiteral
    : LBRACE (objectPair (COMMA objectPair)*)? RBRACE
    ;

objectPair
    : ID COLON expression
    ;

literal
    : INT
    | DECIMAL
    | STRING
    | BOOL
    | NULL_T
    ;

/*
   =========================================================
   LEXER
   ---------------------------------------------------------
   Nota: el orden evita que ID se coma palabras clave.
   =========================================================
*/

/* Estructura */
PROCESS     : 'process';
FUNCTION    : 'function';
IMPORT      : 'import';
IMPORT_JAR  : 'import_jar';
AS          : 'as';

/* Nodos de proceso */
START       : 'start';
END         : 'end';
TASK        : 'task';
GATEWAY     : 'gateway';
PARALLEL    : 'parallel';
WHEN        : 'when';
ELSE        : 'else';
BRANCH      : 'branch';
JOIN        : 'join';

/* Action */
ACTION      : 'action';

/* Flow */
GO_TO       : 'go_to';
ARROW       : '->';

/* Control */
IF          : 'if';
ELSE_IF     : 'else_if';
WHILE       : 'while';
FOR         : 'for';
EACH        : 'each';
IN          : 'in';
FROM        : 'from';
TO          : 'to';
STEP        : 'step';
TRY         : 'try';
CATCH       : 'catch';
THROW       : 'throw';
RETURN      : 'return';

/* Tipos para firmas */
INTEGER_T   : 'integer';
DECIMAL_T   : 'decimal';
BOOLEAN_T   : 'boolean';
TEXT_T      : 'text';
LIST_T      : 'list';
OBJECT_T    : 'object';
VOID_T      : 'void';

/* Lógicos */
AND         : 'and';
OR          : 'or';
NOT         : 'not';

/* Identificador especial */
INPUT       : 'input';

/* Operadores y símbolos */
ADD         : '+';
SUB         : '-';
MUL         : '*';
DIV         : '/';
MOD         : '%';
LT          : '<';
GT          : '>';
LE          : '<=';
GE          : '>=';
EQ          : '==';
NEQ         : '!=';
ASSIGN      : '=';
LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
LBRACK      : '[';
RBRACK      : ']';
DOT         : '.';
COMMA       : ',';
COLON       : ':';
SEMI        : ';';

/* Literales */
BOOL        : 'true' | 'false';
NULL_T      : 'null';

DECIMAL
    : [0-9]+ '.' [0-9]+ ([eE] [+-]? [0-9]+)?
    | '.' [0-9]+ ([eE] [+-]? [0-9]+)?
    | [0-9]+ '.' ([eE] [+-]? [0-9]+)?
    ;

INT
    : [0-9]+
    ;

STRING
    : '"' ( '\\' [btnr"\\/] | '\\u' HEX HEX HEX HEX | ~["\\] )* '"'
    ;

fragment HEX : [0-9a-fA-F] ;

/* Identificadores */
ID
    : [a-zA-Z_][a-zA-Z0-9_]*
    ;

/* Espacios y comentarios (se ignoran) */
WS  : [ \t\r\n\f]+ -> skip ;
LINE_COMMENT_SLASH : '//' ~[\r\n]* -> skip ;
LINE_COMMENT_HASH  : '#' ~[\r\n]*  -> skip ;
BLOCK_COMMENT      : '/*' .*? '*/' -> skip ;