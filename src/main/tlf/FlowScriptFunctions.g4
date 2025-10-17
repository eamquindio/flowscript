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

/*--------------------------------------------
 * REGLA PRINCIPAL
 *-------------------------------------------*/
program
    : functionDeclaration* EOF
    ;

/*--------------------------------------------
 * DECLARACIÓN DE FUNCIONES
 *-------------------------------------------*/
functionDeclaration
    : FUNCTION IDENTIFIER LEFT_PAREN parameterList? RIGHT_PAREN returnType? block
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

/*--------------------------------------------
 * TIPOS DE DATOS
 *-------------------------------------------*/
type
    : INTEGER_TYPE
    | DECIMAL_TYPE
    | BOOLEAN_TYPE
    | TEXT_TYPE
    | LIST_TYPE
    | OBJECT_TYPE
    | VOID
    ;

/*--------------------------------------------
 * BLOQUES Y SENTENCIAS
 *-------------------------------------------*/
block
    : LEFT_BRACE statement* RIGHT_BRACE
    ;

statement
    : variableDeclaration
    | assignment
    | ifStatement
    | whileStatement
    | forEachStatement
    | forRangeStatement
    | tryCatchStatement
    | returnStatement
    | throwStatement
    | expressionStatement
    ;
    
expressionStatement
    : expression
    ;

/*--------------------------------------------
 * DECLARACIÓN Y ASIGNACIÓN
 *-------------------------------------------*/
variableDeclaration
    : IDENTIFIER ASSIGN expression
    ;

assignment
    : IDENTIFIER ASSIGN expression
    ;

/*--------------------------------------------
 * SENTENCIAS DE CONTROL
 *-------------------------------------------*/
ifStatement
    : IF expression block (ELSE_IF expression block)* (ELSE block)?
    ;

whileStatement
    : WHILE expression block
    ;

forEachStatement
    : FOR EACH IDENTIFIER IN expression block
    ;

forRangeStatement
    : FOR IDENTIFIER FROM expression TO expression (STEP expression)? block
    ;

tryCatchStatement
    : TRY block (CATCH LEFT_PAREN IDENTIFIER RIGHT_PAREN block)+
    ;

returnStatement
    : RETURN expression?
    ;

throwStatement
    : THROW expression
    ;

/*--------------------------------------------
 * EXPRESIONES
 *-------------------------------------------*/
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
    : relationalExpression ((EQUAL | NOT_EQUAL) relationalExpression)*
    ;

relationalExpression
    : additiveExpression ((LESS_THAN | LESS_EQUAL | GREATER_THAN | GREATER_EQUAL) additiveExpression)*
    ;

additiveExpression
    : multiplicativeExpression ((PLUS | MINUS) multiplicativeExpression)*
    ;

multiplicativeExpression
    : unaryExpression ((MULTIPLY | DIVIDE | MODULO) unaryExpression)*
    ;

unaryExpression
    : (NOT | MINUS)? primaryExpression
    ;

primaryExpression
    : literal
    | IDENTIFIER
    | listLiteral
    | objectLiteral
    | functionCall
    | LEFT_PAREN expression RIGHT_PAREN
    ;

/*--------------------------------------------
 * LLAMADAS DE FUNCIÓN Y ESTRUCTURAS
 *-------------------------------------------*/
functionCall
    : IDENTIFIER LEFT_PAREN argumentList? RIGHT_PAREN
    ;

argumentList
    : expression (COMMA expression)*
    ;

listLiteral
    : LEFT_BRACKET (expression (COMMA expression)*)? RIGHT_BRACKET
    ;

objectLiteral
    : LEFT_BRACE (objectEntry (COMMA objectEntry)*)? RIGHT_BRACE
    ;

objectEntry
    : IDENTIFIER COLON expression
    ;

/*--------------------------------------------
 * LITERALES
 *-------------------------------------------*/
literal
    : INTEGER_LITERAL
    | DECIMAL_LITERAL
    | STRING_LITERAL
    | TRUE
    | FALSE
    | NULL
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