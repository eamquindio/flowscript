grammar FlowScriptFunctions;

// Package declaration for generated code
@header {
    package edu.eam.ingesoft.tlf;
}

// ============================
// LEXER RULES (TOKENS)
// ============================

// Palabras clave para funciones
FUNCTION        : 'function';
RETURN          : 'return';
IF              : 'if';
ELSE_IF         : 'else_if';
ELSE            : 'else';
WHILE           : 'while';
FOR             : 'for';
EACH            : 'each';
IN              : 'in';
FROM            : 'from';
TO              : 'to';
STEP            : 'step';
BREAK           : 'break';
CONTINUE        : 'continue';
TRY             : 'try';
CATCH           : 'catch';
THROW           : 'throw';

// Tipos de datos
INTEGER_TYPE    : 'integer';
DECIMAL_TYPE    : 'decimal';
BOOLEAN_TYPE    : 'boolean';
TEXT_TYPE       : 'text';
LIST_TYPE       : 'list';
OBJECT_TYPE     : 'object';
VOID_TYPE       : 'void';

// Literales booleanos
TRUE            : 'true';
FALSE           : 'false';
NULL            : 'null';

// Operadores lógicos
AND             : 'and';
OR              : 'or';
NOT             : 'not';

// Operadores aritméticos
PLUS            : '+';
MINUS           : '-';
MULTIPLY        : '*';
DIVIDE          : '/';
MODULO          : '%';

// Operadores de comparación
LT              : '<';
GT              : '>';
LE              : '<=';
GE              : '>=';
EQ              : '==';
NE              : '!=';

// Operadores de asignación
ASSIGN          : '=';

// Delimitadores
LPAREN          : '(';
RPAREN          : ')';
LBRACE          : '{';
RBRACE          : '}';
LBRACKET        : '[';
RBRACKET        : ']';
COMMA           : ',';
SEMICOLON       : ';';
COLON           : ':';
DOT             : '.';
ARROW           : '->';

// Literales numéricos
INTEGER_LITERAL : [0-9]+ ('_' [0-9]+)*;
DECIMAL_LITERAL : [0-9]+ '.' [0-9]+ ([eE] [+-]? [0-9]+)?
                | [0-9]+ [eE] [+-]? [0-9]+;

// Literales de texto
TEXT_LITERAL    : '"' (~["\\\r\n] | '\\' .)* '"';

// Identificadores
IDENTIFIER      : [a-zA-Z_][a-zA-Z0-9_]*;

// Comentarios
SINGLE_COMMENT  : '#' ~[\r\n]* -> skip;
MULTI_COMMENT   : '/*' .*? '*/' -> skip;

// Espacios en blanco
WS              : [ \t\r\n]+ -> skip;

// ============================
// PARSER RULES
// ============================

functionProgram
    : functionDeclaration* EOF
    ;

functionDeclaration
    : FUNCTION IDENTIFIER LPAREN parameterList? RPAREN (ARROW returnType)? LBRACE statementList RBRACE
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : IDENTIFIER COLON dataType
    ;

returnType
    : dataType
    ;

dataType
    : INTEGER_TYPE
    | DECIMAL_TYPE
    | BOOLEAN_TYPE
    | TEXT_TYPE
    | LIST_TYPE
    | OBJECT_TYPE
    | VOID_TYPE
    ;

statementList
    : statement*
    ;

statement
    : ifStatement
    | whileStatement
    | forEachStatement
    | forRangeStatement
    | breakStatement
    | continueStatement
    | returnStatement
    | tryStatement
    | throwStatement
    | assignmentOrExpressionStatement
    ;

assignmentOrExpressionStatement
    : IDENTIFIER ASSIGN expression      # assignmentStatement
    | expression                        # expressionStatement
    ;

ifStatement
    : IF expression LBRACE statementList RBRACE elseIfClause* elseClause?
    ;

elseIfClause
    : ELSE_IF expression LBRACE statementList RBRACE
    ;

elseClause
    : ELSE LBRACE statementList RBRACE
    ;

whileStatement
    : WHILE expression LBRACE statementList RBRACE
    ;

forEachStatement
    : FOR EACH IDENTIFIER IN expression LBRACE statementList RBRACE
    ;

forRangeStatement
    : FOR IDENTIFIER FROM expression TO expression (STEP expression)? LBRACE statementList RBRACE
    ;

breakStatement
    : BREAK
    ;

continueStatement
    : CONTINUE
    ;

returnStatement
    : RETURN expression?
    ;

tryStatement
    : TRY LBRACE statementList RBRACE catchClause+
    ;

catchClause
    : CATCH LPAREN IDENTIFIER RPAREN LBRACE statementList RBRACE
    ;

throwStatement
    : THROW expression
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
    : unaryExpression ((MULTIPLY | DIVIDE | MODULO) unaryExpression)*
    ;

unaryExpression
    : (NOT | MINUS) unaryExpression
    | postfixExpression
    ;

postfixExpression
    : primaryExpression (postfixOperator)*
    ;

postfixOperator
    : LPAREN argumentList? RPAREN          # functionCall
    | DOT IDENTIFIER                       # memberAccess
    | LBRACKET expression RBRACKET         # indexAccess
    ;

argumentList
    : expression (COMMA expression)*
    ;

primaryExpression
    : IDENTIFIER                           # identifierExpression
    | INTEGER_LITERAL                      # integerLiteral
    | DECIMAL_LITERAL                      # decimalLiteral
    | TEXT_LITERAL                         # textLiteral
    | TRUE                                 # trueLiteral
    | FALSE                                # falseLiteral
    | NULL                                 # nullLiteral
    | listLiteral                          # listExpression
    | objectLiteral                        # objectExpression
    | LPAREN expression RPAREN             # parenthesizedExpression
    ;

listLiteral
    : LBRACKET (expression (COMMA expression)*)? RBRACKET
    ;

objectLiteral
    : LBRACE (objectProperty (COMMA objectProperty)*)? RBRACE
    ;

objectProperty
    : IDENTIFIER COLON expression
    | TEXT_LITERAL COLON expression
    ;