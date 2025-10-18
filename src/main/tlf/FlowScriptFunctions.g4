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

// Funciones y retorno
RETURN      : 'return';
FUNCTION    : 'function';
AS          : 'as';
IMPORT      : 'import';

// Control de flujo
WHILE       : 'while';
BREAK       : 'break';
CONTINUE    : 'continue';
IF          : 'if';
ELSE        : 'else';
ELIF        : 'else_if';
TRY         : 'try';
THROW       : 'throw';
CATCH       : 'catch';
EACH        : 'each';
IN          : 'in';
STEP        : 'step';
FOR         : 'for';
TO          : 'to';
FROM        : 'from';

// Tipos de datos
DECIMAL_TYPE : 'decimal';
TEXT_TYPE    : 'text';
INT_TYPE    : 'integer';
OBJECT_TYPE : 'object';
LIST_TYPE   : 'list';
VOID_TYPE   : 'void';
BOOL_TYPE   : 'boolean';
NULL        : 'null';

// Booleanos
TRUE        : 'true' | 'verdadero';
FALSE       : 'false' | 'falso';

// Operadores
SUB         : '-';
ADD         : '+';
MULT        : '*';
MOD         : '%';
DIV         : '/';

NOT         : 'not' | '!';
AND         : 'and' | '&&';
OR          : 'or' | '||';

EQ          : '==';
NEQ         : '!=';
GTE         : '>=';
GT          : '>';
LTE         : '<=';
LT          : '<';

ASSIGN      : '=';

// Símbolos
ARROW       : '->';
COLON       : ':';
DOT         : '.';
COMMA       : ',';
SEMICOLON   : ';';
UNDERSCORE  : '_';

// Estructura
LEFT_P      : '(';
RIGHT_P     : ')';
LEFT_B      : '{';
RIGHT_B     : '}';
LEFT_SB     : '[';
RIGHT_SB    : ']';

// Literales y nombres
INTEGER     : [0-9]+ ('_' [0-9]+)*;
DECIMAL     : [0-9]+ '.' [0-9]+ ([eE] [+-]? [0-9]+)?
            | [0-9]+ [eE] [+-]? [0-9]+;
STRING      : '"' (~["\\\r\n] | '\\' .)* '"';
IDENTIFIER  : [a-zA-Z_][a-zA-Z0-9_]*;

// Comentarios y espacios
COMMENT     : '//' ~[\r\n]* -> skip;
HASH_COMMENT: '#' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
WS          : [ \t\r\n]+ -> skip;

// ============================
// PARSER RULES
// ============================

// Entry point
functionProgram
    : functionDeclaration+ EOF
    ;

// Function definition
functionDeclaration
    : FUNCTION IDENTIFIER LEFT_P params? RIGHT_P (ARROW type_)? block
    ;

// Function parameters
params
    : param (COMMA param)*
    ;

param
    : IDENTIFIER COLON type_
    ;

// Data types
type_
    : INT_TYPE
    | DECIMAL_TYPE
    | BOOL_TYPE
    | TEXT_TYPE
    | LIST_TYPE
    | OBJECT_TYPE
    | VOID_TYPE
    ;

// Code block
block
    : LEFT_B statement* RIGHT_B
    ;

// Statements
statement
    : returnStatement
    | ifStatement
    | forStatement
    | whileStatement
    | tryStatement
    | throwStatement
    | exprStatement
    | varAssign
    | assignmentStatement
    | BREAK SEMICOLON?
    | CONTINUE SEMICOLON?
    ;

// Return
returnStatement
    : RETURN expr? SEMICOLON?
    ;

// If / Else
ifStatement
    : IF expr block elifClause* elseClause?
    ;

elifClause
    : ELIF expr block
    ;

elseClause
    : ELSE block
    ;

// For loops
forStatement
    : FOR IDENTIFIER FROM expr TO expr (STEP expr)? block         # ForRangeStatement
    | FOR EACH IDENTIFIER IN expr block                           # ForEachStatement
    ;

// While loop
whileStatement
    : WHILE expr block
    ;

// Try / Catch
tryStatement
    : TRY block catchClause+
    ;

catchClause
    : CATCH LEFT_P IDENTIFIER RIGHT_P block
    ;

// Throw
throwStatement
    : THROW expr SEMICOLON?
    ;

// Standalone expression
exprStatement
    : expr SEMICOLON?
    ;

// Variable declaration and assignment
varAssign
    : IDENTIFIER ASSIGN expr SEMICOLON?
    ;

// Assignment to an existing variable or structure
assignmentStatement
    : leftSide ASSIGN expr SEMICOLON?
    ;

// Left side of an assignment
leftSide
    : IDENTIFIER                                                  # SimpleLeftSide
    | IDENTIFIER LEFT_SB expr RIGHT_SB                            # IndexLeftSide
    | IDENTIFIER DOT IDENTIFIER                                   # MemberLeftSide
    | IDENTIFIER DOT IDENTIFIER LEFT_SB expr RIGHT_SB             # MemberIndexLeftSide
    | IDENTIFIER LEFT_SB expr RIGHT_SB DOT IDENTIFIER             # IndexMemberLeftSide
    | IDENTIFIER DOT IDENTIFIER DOT IDENTIFIER                    # NestedMemberLeftSide
    ;

// Expressions
expr
    : primary                                                     # ExprPrimary
    | SUB expr                                                    # ExprUnaryMinus
    | ADD expr                                                    # ExprUnaryPlus
    | NOT expr                                                    # ExprLogicalNot
    | expr (MULT | DIV | MOD) expr                                # ExprMultiplicative
    | expr (ADD | SUB) expr                                       # ExprAdditive
    | expr (LT | GT | LTE | GTE) expr                             # ExprRelational
    | expr (EQ | NEQ) expr                                        # ExprEquality
    | expr AND expr                                               # ExprLogicalAnd
    | expr OR expr                                                # ExprLogicalOr
    | expr LEFT_P exprList? RIGHT_P                               # ExprFunctionCall
    | expr DOT IDENTIFIER                                         # ExprMemberAccess
    | expr LEFT_SB expr RIGHT_SB                                  # ExprIndexAccess
    ;

// Literal values and groupings
primary
    : INTEGER                                                     # IntegerLiteral
    | DECIMAL                                                     # DecimalLiteral
    | STRING                                                      # StringLiteral
    | TRUE                                                        # BooleanLiteral
    | FALSE                                                       # BooleanLiteral
    | NULL                                                        # NullLiteral
    | IDENTIFIER                                                  # IdentifierExpression
    | LEFT_P expr RIGHT_P                                         # ParenthesizedExpression
    | LEFT_SB exprList? RIGHT_SB                                  # ListLiteral
    | LEFT_B objectMemberList? RIGHT_B                            # ObjectLiteral
    ;

// List of expressions
exprList
    : expr (COMMA expr)*
    ;

// Object key-value list
objectMemberList
    : objectMember (COMMA objectMember)*
    ;

objectMember
    : (IDENTIFIER | STRING) COLON expr
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