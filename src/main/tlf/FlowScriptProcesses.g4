grammar FlowScriptProcesses;

// Package declaration for generated code
@header {
    package edu.eam.ingesoft.tlf;
}

/*
 * GRAMÁTICA DE PROCESOS PARA FLOWSCRIPT

 * Este archivo define la gramática completa para el sistema de procesos
 * y estructura del programa principal de FlowScript, incluyendo:
 * - Estructura del programa (imports, declaraciones)
 * - Declaración de procesos
 * - Nodos del proceso (start, task, end, gateway)
 * - Gateways exclusivos y paralelos
 * - Control de flujo con goto
 * - Variables globales y contexto del proceso
 */

// ============================
// LEXER RULES (TOKENS)
// ============================

// Palabras clave de estructura
PROCESS: 'process';
START: 'start';
END: 'end';
TASK: 'task';
GATEWAY: 'gateway';
GO_TO: 'go_to';
WHEN: 'when';
BRANCH: 'branch';
JOIN: 'join';
PARALLEL: 'parallel';
ELSE: 'else';

// Palabras clave de funciones
FUNCTION: 'function';
RETURN: 'return';

// Palabras clave de control
IF: 'if';
ELSE_IF: 'else_if';
FOR: 'for';
EACH: 'each';
IN: 'in';
TRY: 'try';
CATCH: 'catch';
THROW: 'throw';

// Tipos de datos
INTEGER_TYPE: 'integer';
DECIMAL_TYPE: 'decimal';
BOOLEAN_TYPE: 'boolean';
TEXT_TYPE: 'text';
LIST_TYPE: 'list';
OBJECT_TYPE: 'object';
VOID_TYPE: 'void';

// Valores
TRUE: 'true';
FALSE: 'false';
NULL: 'null';

// Importaciones
IMPORT: 'import';
IMPORT_JAR: 'import_jar';
AS: 'as';

// Operadores lógicos
AND: 'and';
OR: 'or';
NOT: 'not';

// Operadores
ASSIGN: '=';
ARROW: '->';
PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';
MOD: '%';
LT: '<';
GT: '>';
LE: '<=';
GE: '>=';
EQ: '==';
NE: '!=';

// Delimitadores
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
LBRACKET: '[';
RBRACKET: ']';
COMMA: ',';
COLON: ':';
SEMICOLON: ';';
DOT: '.';

// Literales
INTEGER_LITERAL: [0-9]+ ('_' [0-9]+)*;
DECIMAL_LITERAL: [0-9]+ '.' [0-9]+ ([eE] [+-]? [0-9]+)?;
STRING_LITERAL: '"' (~["\\\r\n] | '\\' ["\\/bfnrt] | '\\u' [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F])* '"';

// Identificadores
IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;

// Comentarios y espacios
LINE_COMMENT: '#' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
WS: [ \t\r\n]+ -> skip;


// ============================
// PARSER RULES
// ============================

// iimportaciones y globlaes

importStatement
    : IMPORT STRING_LITERAL (AS IDENTIFIER)? SEMICOLON?
    | IMPORT_JAR STRING_LITERAL AS IDENTIFIER SEMICOLON?
    ;

globalVariable
    : IDENTIFIER ASSIGN expression SEMICOLON?
    ;


// Funcciones

functionDeclaration
    : FUNCTION IDENTIFIER LPAREN parameterList? RPAREN (ARROW type)? block
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : IDENTIFIER COLON type
    ;

type
    : INTEGER_TYPE | DECIMAL_TYPE | BOOLEAN_TYPE | TEXT_TYPE
    | LIST_TYPE | OBJECT_TYPE | VOID_TYPE
    ;

block
    : LBRACE statement* RBRACE
    ;



// Procesos
processDeclaration
    : PROCESS IDENTIFIER LBRACE startNode processElement* RBRACE
    ;

processElement
    : taskNode
    | endNode
    | gatewayNode
    ;

startNode
    : START ARROW IDENTIFIER
    ;

taskNode
    : TASK IDENTIFIER LBRACE actionBlock RBRACE
    ;

endNode
    : END IDENTIFIER
    ;



// los Gtaeways y los flujos

gatewayNode
    : exclusiveGateway
    | parallelGateway
    ;

exclusiveGateway
    : GATEWAY IDENTIFIER LBRACE exclusiveGatewayBody RBRACE
    ;

parallelGateway
    : GATEWAY IDENTIFIER PARALLEL LBRACE parallelGatewayBody RBRACE
    ;

exclusiveGatewayBody
    : (whenBranch | elseBranch)+
    ;

parallelGatewayBody
    : branchStatement+ joinStatement
    ;

whenBranch
    : WHEN expression ARROW IDENTIFIER
    ;

elseBranch
    : ELSE ARROW IDENTIFIER
    ;

branchStatement
    : BRANCH ARROW IDENTIFIER
    ;

joinStatement
    : JOIN ARROW IDENTIFIER
    ;

actionBlock
    : 'action:' (statement | gatewayNode)+
    ;

gotoStatement
    : GO_TO IDENTIFIER SEMICOLON?
    ;


// Elas expresiiones y los staments


statement
    : variableDeclaration
    | assignment
    | ifStatement
    | forEachStatement
    | tryCatchStatement
    | gotoStatement
    | expression SEMICOLON?
    | returnStatement
    | block
    ;

variableDeclaration
    : IDENTIFIER ASSIGN expression SEMICOLON?
    ;

assignment
    : postfixExpression ASSIGN expression SEMICOLON?
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
    : relationalExpression ((EQ | NE) relationalExpression)*
    ;

relationalExpression
    : additiveExpression ((LT | GT | LE | GE) additiveExpression)*
    ;

additiveExpression
    : multiplicativeExpression ((PLUS | MINUS) multiplicativeExpression)*
    ;

multiplicativeExpression
    : unaryExpression ((MULT | DIV | MOD) unaryExpression)*
    ;

unaryExpression
    : (NOT | MINUS)? postfixExpression
    ;

postfixExpression
    : primaryExpression (postfixOp)*
    ;
postfixOp
    : memberAccess
    | functionCall
    | indexAccess
    ;

memberAccess
    : DOT IDENTIFIER
    ;

functionCall
    : LPAREN (expression (COMMA expression)*)? RPAREN
    ;

indexAccess
    : LBRACKET expression RBRACKET
    ;

primaryExpression
    : literal
    | IDENTIFIER
    | LPAREN expression RPAREN
    | inputAccess
    ;

literal
    : INTEGER_LITERAL
    | DECIMAL_LITERAL
    | STRING_LITERAL
    | TRUE
    | FALSE
    | NULL
    | listLiteral
    | objectLiteral
    ;

listLiteral
    : LBRACKET (expression (COMMA expression)*)? RBRACKET
    ;

objectLiteral
    : LBRACE (objectProperty (COMMA objectProperty)*)? RBRACE
    ;

objectProperty
    : IDENTIFIER COLON expression
    ;


inputAccess
    : 'input' (DOT IDENTIFIER | LBRACKET expression RBRACKET)*
    ;

ifStatement
    : IF expression block (ELSE_IF expression block)* (ELSE block)?
    ;

forEachStatement
    : FOR EACH IDENTIFIER IN expression block
    ;

tryCatchStatement
    : TRY block CATCH LPAREN IDENTIFIER RPAREN block
    ;

returnStatement
    : RETURN expression? SEMICOLON?
    ;

// ============================
// ESTRUCTURA DEL PROGRAMA
// ============================

program
    : (importStatement | globalVariable | functionDeclaration | processDeclaration)* EOF
    ;


