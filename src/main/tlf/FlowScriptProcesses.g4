grammar FlowScriptProcesses;

// Package declaration for generated code
@header {
    package edu.eam.ingesoft.tlf;
}

/*
 * GRAMÁTICA DE PROCESOS PARA FLOWSCRIPT - VERSIÓN FINAL CORREGIDA
 *
 * Correcciones críticas basadas en los errores de test:
 * 1. 'action' seguido de ':' puede tener saltos de línea
 * 2. Objetos literales corregidos completamente
 * 3. Mejor manejo de saltos de línea en todas las estructuras
 * 4. Corrección de la precedencia de expresiones
 */

// ============================
// PARSER RULES
// ============================

// 1. Programa principal
program: programElement* EOF;

programElement:
    importStatement
    | declaration
    | processDeclaration
    | NL
    ;

// 2. Imports
importStatement:
    (IMPORT | IMPORT_JAR) STRING (AS IDENTIFIER)? (NL | SEMI)*
    ;

// 3. Declaraciones
declaration:
    ( functionDeclaration | globalVariableDeclaration ) (NL | SEMI)*
    ;

globalVariableDeclaration:
    IDENTIFIER ASSIGN expression
    ;

// 4. Funciones
functionDeclaration:
    FUNCTION IDENTIFIER LPAREN parameterList? RPAREN ('->' type)?
    LBRACE blockContent RBRACE
    ;

parameterList: parameter (COMMA parameter)*;
parameter: IDENTIFIER COLON type;

// 5. Procesos
processDeclaration:
    PROCESS IDENTIFIER LBRACE processContent RBRACE
    ;

processContent: processElement*;

processElement:
    ( startNode
    | taskNode
    | endNode
    | gatewayNode
    | NL
    )
    ;

// 6. Nodos de proceso
startNode: START '->' IDENTIFIER (NL | SEMI)*;
endNode: END IDENTIFIER (NL | SEMI)*;

// CORRECCIÓN CRÍTICA: permitir NL después de 'action' y ':'
taskNode:
    TASK IDENTIFIER LBRACE (NL)*
        'action' (NL)* COLON (NL)* actionBlock (NL)*
    RBRACE (NL | SEMI)*
    ;

actionBlock:
    LBRACE blockContent RBRACE
    | blockContent
    ;

blockContent: blockElement*;

blockElement:
    statement (NL | SEMI)*
    | NL
    ;

// 7. Gateways
gatewayNode:
    GATEWAY IDENTIFIER PARALLEL? (NL)* LBRACE (NL)*
        gatewayContent (NL)*
    RBRACE (NL | SEMI)*
    ;

gatewayContent:
    ( exclusiveGatewayBody | parallelGatewayBody )
    ;

exclusiveGatewayBody:
    (WHEN expression '->' IDENTIFIER (NL | SEMI)*)+
    (ELSE '->' IDENTIFIER (NL | SEMI)*)?
    ;

parallelGatewayBody:
    (BRANCH '->' IDENTIFIER (NL | SEMI)*)+
    (JOIN '->' IDENTIFIER (NL | SEMI)*)
    ;

// 8. Statements
statement:
    assignmentStatement
    | gotoStatement
    | ifStatement
    | tryCatchStatement
    | throwStatement
    | returnStatement
    | forStatement
    | whileStatement
    | gatewayStatement
    | expressionStatement
    ;

gatewayStatement:
    GATEWAY IDENTIFIER PARALLEL? (NL)* LBRACE (NL)*
        gatewayContent (NL)*
    RBRACE
    ;

assignmentStatement: expression ASSIGN expression;
gotoStatement: GOTO IDENTIFIER;
returnStatement: RETURN expression?;
throwStatement: THROW expression;
expressionStatement: expression;

ifStatement:
    IF expression (NL)* statementBlock (NL)*
    (ELSE_IF expression (NL)* statementBlock (NL)*)*
    (ELSE (NL)* statementBlock (NL)*)?
    ;

tryCatchStatement:
    TRY (NL)* statementBlock (NL)*
    CATCH LPAREN IDENTIFIER RPAREN (NL)* statementBlock
    ;

forStatement:
    FOR (EACH IDENTIFIER IN expression | IDENTIFIER FROM expression TO expression (STEP expression)?) (NL)*
    statementBlock
    ;

whileStatement:
    WHILE expression (NL)* statementBlock
    ;

statementBlock:
    LBRACE blockContent RBRACE
    | statement
    ;

// 9. Expresiones con precedencia corregida
expression:
      <assoc=right> expression ASSIGN expression      # assignmentExpression
    | <assoc=left>  expression OR expression         # logicalOrExpression
    | <assoc=left>  expression AND expression        # logicalAndExpression
    | <assoc=left>  expression (EQ | NE) expression  # equalityExpression
    | <assoc=left>  expression (GT | LT | GE | LE) expression # relationalExpression
    | <assoc=left>  expression (ADD | SUB) expression  # additiveExpression
    | <assoc=left>  expression (MUL | DIV | MOD) expression # multiplicativeExpression
    |               (NOT | SUB) expression           # unaryExpression
    | <assoc=left>  expression DOT IDENTIFIER        # memberAccessExpression
    | <assoc=left>  expression LBRACK expression RBRACK # arrayAccessExpression
    | <assoc=left>  expression LPAREN argumentList? RPAREN # functionCallExpression
    |               atom                             # atomExpression
    ;

atom:
      literal
    | IDENTIFIER
    | LPAREN expression RPAREN
    ;

argumentList: expression (COMMA expression)*;

// 10. Literales - CORRECCIÓN CRÍTICA DE OBJETOS
literal:
      INTEGER_LITERAL
    | DECIMAL_LITERAL
    | STRING
    | BOOLEAN_LITERAL
    | NULL_LITERAL
    | listLiteral
    | objectLiteral
    ;

listLiteral: LBRACK (NL)* (argumentList (NL)*)? RBRACK;

// CORRECCIÓN CRÍTICA: objeto literal permite saltos de línea
objectLiteral: 
    LBRACE (NL)* 
    (objectEntry ((COMMA | NL)+ objectEntry)* (COMMA | NL)*)? 
    (NL)* RBRACE
    ;

objectEntry: IDENTIFIER COLON expression;

type:
      'integer' | 'decimal' | 'boolean' | 'text' | 'list' | 'object' | 'void'
    ;

// ============================
// LEXER RULES
// ============================

// Palabras clave de estructura
PROCESS: 'process';
FUNCTION: 'function';
IMPORT: 'import';
IMPORT_JAR: 'import_jar';
AS: 'as';
RETURN: 'return';

// Palabras clave de proceso
START: 'start';
END: 'end';
TASK: 'task';
GATEWAY: 'gateway';
GOTO: 'go_to';
WHEN: 'when';
BRANCH: 'branch';
JOIN: 'join';
PARALLEL: 'parallel';

// Palabras clave de control
IF: 'if';
ELSE_IF: 'else_if';
ELSE: 'else';
TRY: 'try';
CATCH: 'catch';
THROW: 'throw';
WHILE: 'while';
FOR: 'for';
EACH: 'each';
IN: 'in';
FROM: 'from';
TO: 'to';
STEP: 'step';
BREAK: 'break';
CONTINUE: 'continue';

// Operadores lógicos
AND: 'and';
OR: 'or';
NOT: 'not';

// Literales
BOOLEAN_LITERAL: 'true' | 'false';
NULL_LITERAL: 'null';
INTEGER_LITERAL: [0-9]+;
DECIMAL_LITERAL: [0-9]+ '.' [0-9]+;
STRING: '"' (~["\\] | '\\' .)*? '"';

// Identificadores
IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;

// Puntuación y operadores
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
LBRACK: '[';
RBRACK: ']';
SEMI: ';';
COMMA: ',';
DOT: '.';
COLON: ':';
ASSIGN: '=';
GT: '>';
LT: '<';
EQ: '==';
LE: '<=';
GE: '>=';
NE: '!=';
ADD: '+';
SUB: '-';
MUL: '*';
DIV: '/';
MOD: '%';

// Espacios en blanco y comentarios
WS: [ \t\r]+ -> skip;
NL: '\n' ;
LINE_COMMENT: '#' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;