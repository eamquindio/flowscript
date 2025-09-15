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
NOMBRE : [a-zA-Z_] [a-zA-Z0-9_]* ;
NUMERO  : [0-9]+ ('.' [0-9]+)? ;
CADENA  : '"' (~["\r\n])* '"' ;
BOOLEANO: 'verdadero' | 'falso' ;
WS: [ \t\r\n]+ -> skip ;
LINEA: ('//' | '#') ~[\r\n]* -> skip ;
COMENTARIO: '/*' .*? '*/' -> skip ;

// ============================
// DECLARACIÓN DE FUNCIONES
// ============================

functionProgram: functionDeclaration* EOF ;

functionDeclaration: 'function' NOMBRE '(' parametros? ')' ('->' retorno)?  bloque ;

parametros: parametro (',' parametro)* ;
parametro: NOMBRE ':' tipo ;
tipo: 'integer' | 'decimal' | 'text' | 'boolean' | 'list' | 'object' | 'void' ;
retorno: tipo ;

resultado:  return
    | print
    | asignacion
    | if
    | while
    | for
    | tryCatch
    | error
    | llamadafunction
    | 'break'
    | 'continue'
    ;

return: 'return' expresion? ;

print: 'print' '(' expresion ')' ;

asignacion
    : NOMBRE '=' expresion
    | NOMBRE '[' expresion ']' '=' expresion;

if
    : 'if' expresion bloque ( 'else_if' expresion bloque )* ('else' bloque)?;

while: 'while' expresion bloque ;

for: 'for' NOMBRE 'from' expresion 'to' expresion ('step' expresion)? bloque| 'for' 'each' NOMBRE 'in' expresion bloque;

tryCatch: 'try' bloque ('catch' '(' NOMBRE ')' bloque)+ ;

error: 'throw' '{' (NOMBRE ':' expresion (',' NOMBRE ':' expresion)*)? '}' ;

llamadafunction: NOMBRE '(' argumentos? ')' ;

argumentos: expresion (',' expresion)* ;

expresion: expresion ('+' | '-' | '*' | '/' | '%' | '==' | '!=' | '<' | '<=' | '>' | '>=' | 'and' | 'or') expresion| expresion '.' NOMBRE ('(' argumentos? ')')?| expresion '[' expresion ']'
    | '(' expresion ')'| llamadafunction| NOMBRE| NUMERO| CADENA| BOOLEANO| lista| objeto;

lista: '[' (expresion (',' expresion)*)? ']' ;

objeto: '{' (NOMBRE ':' expresion (',' NOMBRE ':' expresion)*)? '}' ;

bloque: '{' resultado* '}' ;

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
