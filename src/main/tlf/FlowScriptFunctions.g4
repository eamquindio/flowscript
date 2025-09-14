grammar FlowScriptFunctions;

@header {
    package edu.eam.ingesoft.tlf;
}

/*
 * FlowScript Functions Grammar
 * Equipo A
 * Compatible con los casos de prueba entregados
 */

// ============================
// PARSER RULES
// ============================

functionProgram
    : functionDeclaration* EOF
    ;

// Declaración de función
functionDeclaration
    : FUNCTION IDENTIFIER LPAREN parameterList? RPAREN (returnType)? block
    ;


parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : IDENTIFIER COLON type
    ;

returnType
    : ARROW type
    ;

// Tipos permitidos
type
    : INTEGER
    | DECIMAL
    | BOOLEAN
    | TEXT
    | LIST
    | OBJECT
    | VOID
    ;

// ----------------------------
// STATEMENTS
// ----------------------------

block
    : LBRACE statement* RBRACE
    ;

statement
    : variableDecl (SEMI)?
    | assignment (SEMI)?
    | ifStmt
    | whileStmt
    | forEachStmt
    | forRangeStmt
    | tryCatchStmt
    | throwStmt (SEMI)?
    | returnStmt (SEMI)?
    | breakStmt (SEMI)?
    | continueStmt (SEMI)?
    | exprStmt (SEMI)?
    ;

variableDecl
    : IDENTIFIER ASSIGN expression
    ;

assignment
    : postfixExpr ASSIGN expression
    ;


exprStmt
    : expression
    ;

returnStmt
    : RETURN expression?
    ;

breakStmt
    : BREAK
    ;

continueStmt
    : CONTINUE
    ;

// ----------------------------
// CONTROL STRUCTURES
// ----------------------------

ifStmt
    : IF expression block elseIfPart* elsePart?
    ;

elseIfPart
    : ELSE_IF expression block
    ;

elsePart
    : ELSE block
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

// ----------------------------
// ERROR HANDLING
// ----------------------------

tryCatchStmt
    : TRY block catchBlock+
    ;

catchBlock
    : CATCH LPAREN IDENTIFIER RPAREN block
    ;

throwStmt
    : THROW expression
    ;

// ----------------------------
// EXPRESSIONS
// ----------------------------

expression
    : orExpr
    ;

orExpr
    : andExpr (OR andExpr)*
    ;

andExpr
    : equalityExpr (AND equalityExpr)*
    ;

equalityExpr
    : relationalExpr ((EQ | NEQ) relationalExpr)*
    ;

relationalExpr
    : addExpr ((LT | LTEQ | GT | GTEQ) addExpr)*
    ;

addExpr
    : mulExpr ((PLUS | MINUS) mulExpr)*
    ;

mulExpr
    : unaryExpr ((MUL | DIV | MOD) unaryExpr)*
    ;

unaryExpr
    : (NOT | MINUS) unaryExpr
    | postfixExpr
    ;

postfixExpr
    : primaryExpr (postfixOp)*
    ;

postfixOp
    : DOT IDENTIFIER
    | LBRACK expression RBRACK
    | LPAREN argList? RPAREN
    ;

argList
    : expression (COMMA expression)*
    ;

primaryExpr
    : literal
    | IDENTIFIER
    | LPAREN expression RPAREN
    | listLiteral
    | objectLiteral
    ;

// ----------------------------
// LITERALS
// ----------------------------

listLiteral
    : LBRACK (expression (COMMA expression)*)? RBRACK
    ;

objectLiteral
    : LBRACE (objectPair (COMMA objectPair)*)? RBRACE
    ;

objectPair
    : IDENTIFIER COLON expression
    ;

literal
    : INTEGER_LITERAL
    | DECIMAL_LITERAL
    | STRING_LITERAL
    | TRUE
    | FALSE
    | NULL
    ;

// ============================
// LEXER RULES
// ============================

// Palabras clave
FUNCTION    : 'function';
RETURN      : 'return';
IF          : 'if';
ELSE_IF     : 'else_if';
ELSE        : 'else';
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
BREAK       : 'break';
CONTINUE    : 'continue';

// Tipos
INTEGER     : 'integer';
DECIMAL     : 'decimal';
BOOLEAN     : 'boolean';
TEXT        : 'text';
LIST        : 'list';
OBJECT      : 'object';
VOID        : 'void';

// Literales boolean y null
TRUE        : 'true';
FALSE       : 'false';
NULL        : 'null';

// Operadores
PLUS        : '+';
MINUS       : '-';
MUL         : '*';
DIV         : '/';
MOD         : '%';
LT          : '<';
GT          : '>';
LTEQ        : '<=';
GTEQ        : '>=';
EQ          : '==';
NEQ         : '!=';
AND         : 'and';
OR          : 'or';
NOT         : 'not';
ASSIGN      : '=';

// Puntuación
LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
LBRACK      : '[';
RBRACK      : ']';
COMMA       : ',';
SEMI        : ';';
COLON       : ':';
DOT         : '.';
ARROW       : '->';

// Identificadores y literales
IDENTIFIER
    : [a-zA-Z_] [a-zA-Z0-9_]*
    ;

INTEGER_LITERAL
    : [0-9]+
    ;

DECIMAL_LITERAL
    : [0-9]+ '.' [0-9]+ ([eE] [+-]? [0-9]+)?
    ;

STRING_LITERAL
    : '"' (~["\\] | '\\' .)* '"'
    ;

// Comentarios y espacios
COMMENT
    : '#' ~[\r\n]* -> skip
    ;

MULTILINE_COMMENT
    : '/*' .*? '*/' -> skip
    ;

WS
    : [ \t\r\n]+ -> skip
    ;
