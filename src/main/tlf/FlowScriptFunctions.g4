grammar FlowScriptFunctions;

// Package declaration for generated code
@header {
    package edu.eam.ingesoft.tlf;
}

/*
 * GRAMÁTICA DE FUNCIONES PARA FLOWSCRIPT
 * 
 * Este archivo define la gramática completa para el sistema de funciones
 * del lenguaje FlowScript, incluyendo:
 * - Declaración de funciones
 * - Parámetros tipados
 * - Tipos de retorno
 * - Cuerpo de funciones con statements
 * - Expresiones y operadores
 * - Control de flujo dentro de funciones
 */

// ============================
// LEXER RULES (TOKENS)
// ============================

// Palabras clave para funciones
FUNCTION    : 'function';
RETURN      : 'return';
IMPORT      : 'import';
AS          : 'as';

// Palabras clave de control de flujo
IF          : 'if';
ELSE        : 'else';
ELSE_IF     : 'else_if';
FOR         : 'for';
WHILE       : 'while';
BREAK       : 'break';
CONTINUE    : 'continue';
TRY         : 'try';
CATCH       : 'catch';
THROW       : 'throw';
EACH        : 'each';
IN          : 'in';
FROM        : 'from';
TO          : 'to';
STEP        : 'step';

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
TRUE        : 'true' | 'verdadero';
FALSE       : 'false' | 'falso';

// Operadores
PLUS        : '+';
MINUS       : '-';
MULTIPLY    : '*';
DIVIDE      : '/';
MODULO      : '%';
ASSIGN      : '=';
EQ          : '==';
NEQ         : '!=';
LT          : '<';
GT          : '>';
LTE         : '<=';
GTE         : '>=';
AND         : 'and' | '&&';
OR          : 'or' | '||';
NOT         : 'not' | '!';

// Símbolos
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
ARROW       : '->';
UNDERSCORE  : '_';

// Literales
INTEGER     : [0-9]+ ('_' [0-9]+)*;
DECIMAL     : [0-9]+ '.' [0-9]+ ([eE] [+-]? [0-9]+)?
            | [0-9]+ [eE] [+-]? [0-9]+;
STRING      : '"' (~["\\\r\n] | '\\' .)* '"';
IDENTIFIER  : [a-zA-Z_][a-zA-Z0-9_]*;

// Comentarios y espacios en blanco
COMMENT     : '//' ~[\r\n]* -> skip;
HASH_COMMENT : '#' ~[\r\n]* -> skip;
BLOCK_COMMENT : '/*' .*? '*/' -> skip;
WS          : [ \t\r\n]+ -> skip;

// ============================
// PARSER RULES
// ============================

// Punto de entrada de la gramática
functionProgram
    : functionDeclaration+ EOF
    ;

// Declaración de funciones
functionDeclaration
    : FUNCTION IDENTIFIER LPAREN parameterList? RPAREN (ARROW type_)? block
    ;

// Lista de parámetros
parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : IDENTIFIER COLON type_
    ;

// Tipos
type_
    : INTEGER_TYPE
    | DECIMAL_TYPE
    | BOOLEAN_TYPE
    | TEXT_TYPE
    | LIST_TYPE
    | OBJECT_TYPE
    | VOID_TYPE
    ;

// Bloques de código
block
    : LBRACE statement* RBRACE
    ;

// Statements
statement
    : returnStatement
    | ifStatement
    | forStatement
    | whileStatement
    | tryStatement
    | throwStatement
    | expressionStatement
    | variableDeclaration
    | assignmentStatement
    | BREAK SEMICOLON?
    | CONTINUE SEMICOLON?
    ;

returnStatement
    : RETURN expression? SEMICOLON?
    ;

ifStatement
    : IF expression block elseIfClause* elseClause?
    ;

elseIfClause
    : ELSE_IF expression block
    | ELSE IF expression block
    ;

elseClause
    : ELSE block
    ;

forStatement
    : FOR IDENTIFIER FROM expression TO expression (STEP expression)? block  # ForRangeStatement
    | FOR EACH IDENTIFIER IN expression block                                 # ForEachStatement
    ;

whileStatement
    : WHILE expression block
    ;

tryStatement
    : TRY block catchClause+
    ;

catchClause
    : CATCH LPAREN IDENTIFIER RPAREN block
    ;

throwStatement
    : THROW expression SEMICOLON?
    ;

expressionStatement
    : expression SEMICOLON?
    ;

variableDeclaration
    : IDENTIFIER ASSIGN expression SEMICOLON?
    ;

assignmentStatement
    : leftHandSide ASSIGN expression SEMICOLON?
    ;

leftHandSide
    : IDENTIFIER                                                                 # SimpleLeftHandSide
    | IDENTIFIER LBRACKET expression RBRACKET                                   # IndexLeftHandSide
    | IDENTIFIER DOT IDENTIFIER                                                  # MemberLeftHandSide
    | IDENTIFIER DOT IDENTIFIER LBRACKET expression RBRACKET                    # MemberIndexLeftHandSide
    | IDENTIFIER LBRACKET expression RBRACKET DOT IDENTIFIER                    # IndexMemberLeftHandSide
    | IDENTIFIER DOT IDENTIFIER DOT IDENTIFIER                                   # NestedMemberLeftHandSide
    ;

// Expresiones
expression
    : primary                                                       # PrimaryExpression
    | MINUS expression                                              # UnaryMinusExpression
    | PLUS expression                                               # UnaryPlusExpression
    | NOT expression                                                # LogicalNotExpression
    | expression (MULTIPLY | DIVIDE | MODULO) expression          # MultiplicativeExpression
    | expression (PLUS | MINUS) expression                         # AdditiveExpression
    | expression (LT | GT | LTE | GTE) expression                 # RelationalExpression
    | expression (EQ | NEQ) expression                             # EqualityExpression
    | expression AND expression                                     # LogicalAndExpression
    | expression OR expression                                      # LogicalOrExpression
    | expression LPAREN expressionList? RPAREN                     # FunctionCallExpression
    | expression DOT IDENTIFIER                                     # MemberAccessExpression
    | expression LBRACKET expression RBRACKET                      # IndexAccessExpression
    ;

primary
    : INTEGER                                                       # IntegerLiteral
    | DECIMAL                                                       # DecimalLiteral
    | STRING                                                        # StringLiteral
    | TRUE                                                          # BooleanLiteral
    | FALSE                                                         # BooleanLiteral
    | NULL                                                          # NullLiteral
    | IDENTIFIER                                                    # IdentifierExpression
    | LPAREN expression RPAREN                                      # ParenthesizedExpression
    | LBRACKET expressionList? RBRACKET                            # ListLiteral
    | LBRACE objectMemberList? RBRACE                              # ObjectLiteral
    ;

expressionList
    : expression (COMMA expression)*
    ;

objectMemberList
    : objectMember (COMMA objectMember)*
    ;

objectMember
    : (IDENTIFIER | STRING) COLON expression
    ;
// ============================
// EJEMPLOS DE USO
// ============================

/*
 * EJEMPLOS VÁLIDOS:
 * 
 * 1. Función simple:
 * function greet() -> void {
 *     print("Hello World")
 * }
 * 
 * 2. Función con parámetros y retorno:
 * function add(a: integer, b: integer) -> integer {
 *     return a + b
 * }
 * 
 * 3. Función con lógica compleja:
 * function factorial(n: integer) -> integer {
 *     if n <= 1 {
 *         return 1
 *     }
 *     return n * factorial(n - 1)
 * }
 * 
 * 4. Función con manejo de errores:
 * function safe_divide(a: decimal, b: decimal) -> decimal {
 *     try {
 *         if b == 0 {
 *             throw { type: "DivisionError", message: "Division by zero" }
 *         }
 *         return a / b
 *     } catch (error) {
 *         print("Error: " + error.message)
 *         return 0.0
 *     }
 * }
 * 
 * 5. Función con estructuras de datos:
 * function process_items(items: list, threshold: decimal) -> object {
 *     result = { count: 0, sum: 0.0 }
 *     
 *     for each item in items {
 *         if item.value > threshold {
 *             result.count = result.count + 1
 *             result.sum = result.sum + item.value
 *         }
 *     }
 *     
 *     return result
 * }
 * 
 * 6. Función con bucles:
 * function find_max(numbers: list) -> decimal {
 *     if numbers.length() == 0 {
 *         return null
 *     }
 *     
 *     max = numbers[0]
 *     for i from 1 to numbers.length() - 1 {
 *         if numbers[i] > max {
 *             max = numbers[i]
 *         }
 *     }
 *     
 *     return max
 * }
 */