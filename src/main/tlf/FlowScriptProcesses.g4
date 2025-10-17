grammar FlowScriptProcesses;

// Package declaration for generated code
@header {
    package edu.eam.ingesoft.tlf;
}

/*
 * GRAMÁTICA DE PROCESOS PARA FLOWSCRIPT
 * 
 * Este archivo define la gramática completa para el sistema de procesos
 * y estructura del programa principal de FlowScript, incluyendo:
 * - Estructura del programa (imports, declaraciones)
 * - Declaración de procesos
 * - Nodos del proceso (start, task, end, gateway)
 * - Gateways exclusivos y paralelos
 * - Control de flujo con go_to
 * - Variables globales y contexto del proceso
 */

// ============================
// LEXER RULES (TOKENS)
// ============================

// Palabras clave de estructura
IMPORT      : 'import';
IMPORT_JAR  : 'import_jar';
AS          : 'as';
FUNCTION    : 'function';
PROCESS     : 'process';
RETURN      : 'return';

// Palabras clave de nodos de proceso
START       : 'start';
END         : 'end';
TASK        : 'task';
GATEWAY     : 'gateway';
ACTION      : 'action';
GO_TO       : 'go_to';

// Palabras clave de gateway
WHEN        : 'when';
ELSE        : 'else';
BRANCH      : 'branch';
JOIN        : 'join';
PARALLEL    : 'parallel';

// Palabras clave de control de flujo
IF          : 'if';
ELSE_IF     : 'else_if';
TRY         : 'try';
CATCH       : 'catch';
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

// Tipos de datos
INTEGER_TYPE : 'integer';
DECIMAL_TYPE : 'decimal';
BOOLEAN_TYPE : 'boolean';
TEXT_TYPE    : 'text';
LIST_TYPE    : 'list';
OBJECT_TYPE  : 'object';
VOID_TYPE    : 'void';
NULL         : 'null';

// Valores booleanos
TRUE        : 'true';
FALSE       : 'false';

// Operadores lógicos
AND         : 'and';
OR          : 'or';
NOT         : 'not';

// Operadores de comparación
EQ          : '==';
NE          : '!=';
LE          : '<=';
GE          : '>=';
LT          : '<';
GT          : '>';

// Operadores aritméticos
PLUS        : '+';
MINUS       : '-';
MULTIPLY    : '*';
DIVIDE      : '/';
MODULO      : '%';

// Operadores de asignación
ASSIGN      : '=';

// Delimitadores
LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
LBRACKET    : '[';
RBRACKET    : ']';
COMMA       : ',';
SEMICOLON   : ';';
COLON       : ':';
DOT         : '.';
ARROW       : '->';

// Literales
INTEGER     : [0-9]+('_'[0-9]+)*;
DECIMAL     : [0-9]+('_'[0-9]+)*'.'[0-9]+([eE][+-]?[0-9]+)?;
STRING      : '"' (~["\\\r\n] | '\\' .)* '"';

// Identificadores
IDENTIFIER  : [a-zA-Z_][a-zA-Z0-9_]*;

// Comentarios
COMMENT     : '#' ~[\r\n]* -> skip;
BLOCK_COMMENT : '/*' .*? '*/' -> skip;

// Espacios en blanco
WS          : [ \t\r\n]+ -> skip;

// ============================
// PARSER RULES
// ============================

// ============================
// ESTRUCTURA DEL PROGRAMA
// ============================

program
    : (importStatement | globalVariable | functionDeclaration | processDeclaration)* EOF
    ;

// Declaraciones de importación
importStatement
    : IMPORT STRING (AS IDENTIFIER)?
    | IMPORT_JAR STRING AS IDENTIFIER
    ;

// Variables globales
globalVariable
    : IDENTIFIER ASSIGN expression
    ;

// ============================
// FUNCIONES (BÁSICAS)
// ============================

functionDeclaration
    : FUNCTION IDENTIFIER LPAREN parameterList? RPAREN (ARROW type)? LBRACE statement* RBRACE
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : IDENTIFIER COLON type
    ;

type
    : INTEGER_TYPE
    | DECIMAL_TYPE
    | BOOLEAN_TYPE
    | TEXT_TYPE
    | LIST_TYPE
    | OBJECT_TYPE
    | VOID_TYPE
    ;

// ============================
// DECLARACIONES DE PROCESO
// ============================

processDeclaration
    : PROCESS IDENTIFIER LBRACE startNode processElement* endNode+ RBRACE
    ;

processElement
    : taskNode
    | gatewayNodeDeclaration
    ;

// Nodo de inicio
startNode
    : START ARROW IDENTIFIER
    ;

// Nodo de tarea
taskNode
    : TASK IDENTIFIER LBRACE ACTION COLON statement* RBRACE
    ;

// Nodo de fin
endNode
    : END IDENTIFIER
    ;

// Nodo de gateway
gatewayNodeDeclaration
    : gatewayExclusiveDeclaration
    | gatewayParallelDeclaration
    ;

// Gateway exclusivo como declaración de nodo
gatewayExclusiveDeclaration
    : GATEWAY IDENTIFIER LBRACE whenClause+ elseClause? RBRACE
    ;

// Gateway paralelo como declaración de nodo
gatewayParallelDeclaration
    : GATEWAY IDENTIFIER PARALLEL LBRACE branchClause branchClause+ joinClause? RBRACE
    ;

// Gateway exclusivo (XOR)
gatewayExclusive
    : GATEWAY IDENTIFIER LBRACE whenClause+ elseClause? RBRACE
    ;

// Gateway paralelo (AND)
gatewayParallel
    : GATEWAY IDENTIFIER PARALLEL LBRACE branchClause+ joinClause? RBRACE
    ;

// Cláusulas de gateway
whenClause
    : WHEN expression ARROW IDENTIFIER
    ;

elseClause
    : ELSE ARROW IDENTIFIER
    ;

branchClause
    : BRANCH ARROW IDENTIFIER
    ;

joinClause
    : JOIN ARROW IDENTIFIER
    ;

// ============================
// DECLARACIONES Y CONTROL DE FLUJO
// ============================

statement
    : expressionStatement
    | ifStatement
    | whileStatement
    | forEachStatement
    | forRangeStatement
    | tryStatement
    | throwStatement
    | returnStatement
    | breakStatement
    | continueStatement
    | goToStatement
    | gatewayStatement
    ;

expressionStatement
    : expression
    ;

ifStatement
    : IF expression LBRACE statement* RBRACE (ELSE_IF expression LBRACE statement* RBRACE)* (ELSE LBRACE statement* RBRACE)?
    ;

whileStatement
    : WHILE expression LBRACE statement* RBRACE
    ;

forEachStatement
    : FOR EACH IDENTIFIER IN expression LBRACE statement* RBRACE
    ;

forRangeStatement
    : FOR IDENTIFIER FROM expression TO expression (STEP expression)? LBRACE statement* RBRACE
    ;

tryStatement
    : TRY LBRACE statement* RBRACE CATCH LPAREN IDENTIFIER RPAREN LBRACE statement* RBRACE
    ;

throwStatement
    : THROW expression
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

goToStatement
    : GO_TO IDENTIFIER
    ;

gatewayStatement
    : gatewayExclusive
    | gatewayParallel
    ;

// ============================
// EXPRESIONES
// ============================

expression
    : assignmentExpression
    ;

assignmentExpression
    : logicalOrExpression (ASSIGN assignmentExpression)?
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
    : unaryExpression ((MULTIPLY | DIVIDE | MODULO) unaryExpression)*
    ;

unaryExpression
    : (NOT | MINUS) unaryExpression
    | postfixExpression
    ;

postfixExpression
    : primaryExpression (memberAccess | indexAccess | functionCall)*
    ;

memberAccess
    : DOT IDENTIFIER
    ;

indexAccess
    : LBRACKET expression RBRACKET
    ;

functionCall
    : LPAREN argumentList? RPAREN
    ;

argumentList
    : expression (COMMA expression)*
    ;

primaryExpression
    : IDENTIFIER
    | literal
    | LPAREN expression RPAREN
    ;

// ============================
// LITERALES
// ============================

literal
    : INTEGER
    | DECIMAL
    | STRING
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