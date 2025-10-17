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
 * - Control de flujo con goto
 * - Variables globales y contexto del proceso
 */

// ============================
// LEXER RULES (TOKENS)
// ============================

// Palabras clave de procesos
PROCESS     : 'process';
START       : 'start';
END         : 'end';
TASK        : 'task';
GATEWAY     : 'gateway';
WHEN        : 'when';
ELSE        : 'else';
PARALLEL    : 'parallel';
BRANCH      : 'branch';
JOIN        : 'join';
GO_TO       : 'go_to' | 'goto';
ACTION      : 'action';

// Importaciones y alias
IMPORT      : 'import';
IMPORT_JAR  : 'import_jar';
AS          : 'as';

// Control, funciones y otros (comparten con funciones)
FUNCTION    : 'function';
TRY         : 'try';
CATCH       : 'catch';
RETURN      : 'return';
AND         : 'and';
OR          : 'or';
NOT         : 'not';
IN          : 'in';
EACH        : 'each';
IF          : 'if';
FOR         : 'for';
WHILE       : 'while';

// Tipos primitivos (para funciones dentro del archivo de procesos)
INTEGER_T   : 'integer';
DECIMAL_T   : 'decimal';
BOOLEAN_T   : 'boolean';
TEXT_T      : 'text';
LIST_T      : 'list';
OBJECT_T    : 'object';
VOID_T      : 'void';

// Literales booleanos / nulo
TRUE        : 'true';
FALSE       : 'false';
NULL        : 'null';

// Operadores y puntuación
ARROW       : '->';
ASSIGN      : '=';
EQ          : '==';
NEQ         : '!=';
LTE         : '<=';
GTE         : '>=';
LT          : '<';
GT          : '>';
PLUS        : '+';
MINUS       : '-';
MUL         : '*';
DIV         : '/';
MOD         : '%';
LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
LBRACK      : '[';
RBRACK      : ']';
COMMA       : ',';
COLON       : ':';
DOT         : '.';

// Literales
IntegerLiteral
    : '0'
    | NonZeroDigit ('_'? Digit)*
    ;

DecimalLiteral
    : DigitSequence '.' DigitSequence
    ;

StringLiteral
    : '"' (ESC_SEQ | ~["\\\r\n])* '"'
    ;

fragment ESC_SEQ
    : '\\' ["\\/bfnrt]
    | '\\' 'u' HEX HEX HEX HEX
    ;

fragment HEX: [0-9a-fA-F];

fragment Digit : [0-9];

fragment NonZeroDigit : [1-9];

fragment DigitSequence
    : Digit ('_'? Digit)*
    ;

Identifier
    : [a-zA-Z_] [a-zA-Z_0-9]*
    ;

// Espacios en blanco y comentarios
WS                  : [ \t\r\n]+ -> skip ;
LINE_COMMENT_HASH   : '#' ~[\r\n]* -> skip ;
LINE_COMMENT_SLASH  : '//' ~[\r\n]* -> skip ;
BLOCK_COMMENT       : '/*' .*? '*/' -> skip ;


// ============================
// PARSER RULES
// ============================

// Programa: imports, variables, funciones y procesos
program
    : topLevelElement* EOF
    ;

topLevelElement
    : importStmt
    | importJarStmt
    | globalVarDecl
    | functionDeclaration
    | processDeclaration
    ;

// Imports
importStmt
    : IMPORT StringLiteral (AS Identifier)?
    ;

importJarStmt
    : IMPORT_JAR StringLiteral AS Identifier
    ;

// Variable global: asignación simple
globalVarDecl
    : Identifier ASSIGN expression
    ;

// Funciones (reutiliza estructura de Functions)
functionDeclaration
    : FUNCTION functionName LPAREN parameterList? RPAREN (ARROW returnType)? functionBody
    ;

functionName
    : Identifier
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : Identifier COLON dataType
    ;

dataType
    : INTEGER_T
    | DECIMAL_T
    | BOOLEAN_T
    | TEXT_T
    | LIST_T
    | OBJECT_T
    | VOID_T
    ;

returnType
    : dataType
    ;

functionBody
    : LBRACE statement* RBRACE
    ;

// Procesos
processDeclaration
    : PROCESS Identifier LBRACE startClause processBody RBRACE
    ;

// Requiere al menos un 'end' en el cuerpo del proceso
processBody
    : processElement* endDeclaration+
    ;

startClause
    : START ARROW Identifier
    ;

processElement
    : taskDeclaration
    | gatewayDeclaration
    ;

endDeclaration
    : END Identifier
    ;

taskDeclaration
    : TASK Identifier LBRACE ACTION COLON statement* RBRACE
    ;

gatewayDeclaration
    : GATEWAY Identifier (PARALLEL gatewayParallelBody | gatewayExclusiveBody)
    ;

// Gateway como statement dentro de acciones
gatewayStatement
    : GATEWAY Identifier (PARALLEL gatewayParallelBody | gatewayExclusiveBody)
    ;

gatewayExclusiveBody
    : LBRACE gatewayExclusiveWhen+ gatewayExclusiveElse? RBRACE
    ;

gatewayExclusiveWhen
    : WHEN expression ARROW Identifier
    ;

gatewayExclusiveElse
    : ELSE ARROW Identifier
    ;

gatewayParallelBody
    : LBRACE (BRANCH ARROW Identifier)+ JOIN ARROW Identifier RBRACE
    ;

// Statements utilizados en acciones y funciones
statement
    : gatewayStatement
    | ifStatement
    | forEachStatement
    | tryStatement
    | returnStatement
    | goToStatement
    | assignmentStatement
    | expressionStatement
    ;

block
    : LBRACE statement* RBRACE
    ;

ifStatement
    : IF expression block (ELSE block)?
    ;

forEachStatement
    : FOR EACH Identifier IN expression block
    ;

tryStatement
    : TRY block CATCH LPAREN Identifier RPAREN block
    ;

returnStatement
    : RETURN expression?
    ;

goToStatement
    : GO_TO Identifier
    ;

assignmentStatement
    : leftHandSide ASSIGN expression
    ;

leftHandSide
    : primaryExpression postfixPart*
    ;

expressionStatement
    : expression
    ;

// Expresiones (precedencia)
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
    : relationalExpression ((EQ | NEQ) relationalExpression)*
    ;

relationalExpression
    : additiveExpression ((LT | LTE | GT | GTE) additiveExpression)*
    ;

additiveExpression
    : multiplicativeExpression ((PLUS | MINUS) multiplicativeExpression)*
    ;

multiplicativeExpression
    : unaryExpression ((MUL | DIV | MOD) unaryExpression)*
    ;

unaryExpression
    : (NOT | MINUS) unaryExpression
    | postfixExpression
    ;

postfixExpression
    : primaryExpression postfixPart*
    ;

postfixPart
    : DOT Identifier
    | LBRACK expression RBRACK
    | LPAREN argumentList? RPAREN
    | DOT Identifier LPAREN argumentList? RPAREN
    ;

primaryExpression
    : literal
    | LPAREN expression RPAREN
    | Identifier
    ;

argumentList
    : expression (COMMA expression)*
    ;

// Literales y estructuras
literal
    : IntegerLiteral
    | DecimalLiteral
    | StringLiteral
    | TRUE
    | FALSE
    | NULL
    | listLiteral
    | objectLiteral
    ;

listLiteral
    : LBRACK (expression (COMMA expression)*)? RBRACK
    ;

objectLiteral
    : LBRACE (objectProperty (COMMA objectProperty)*)? RBRACE
    ;

objectProperty
    : propertyKey COLON expression
    ;

propertyKey
    : Identifier
    | StringLiteral
    ;


// ============================
// EJEMPLOS DE USO
// ============================

/*
 * PROGRAMA COMPLETO CON PROCESOS:
 * 
 * import "std/http" as http
 * import "std/db" as db
 * import_jar "libs/utils.jar" as utils
 * 
 * # Variable global
 * MAX_RETRIES = 3
 * 
 * # Función auxiliar
 * function validate_email(email: text) -> boolean {
 *     return email.contains("@") and email.length() > 5
 * }
 * 
 * # Proceso principal
 * process OrderValidation {
 *     start -> CheckInput
 *     
 *     task CheckInput {
 *         action:
 *             if input.customer_id == null {
 *                 error_message = "Customer ID required"
 *                 goto ValidationError
 *             }
 *             
 *             if not validate_email(input.email) {
 *                 error_message = "Invalid email"
 *                 goto ValidationError
 *             }
 *             
 *             goto CheckInventory
 *     }
 *     
 *     task CheckInventory {
 *         action:
 *             items_available = true
 *             
 *             for each item in input.items {
 *                 stock = db.get("inventory", item.id)
 *                 if stock.quantity < item.quantity {
 *                     items_available = false
 *                 }
 *             }
 *             
 *             gateway InventoryDecision {
 *                 when items_available -> ProcessPayment
 *                 else -> InsufficientStock
 *             }
 *     }
 *     
 *     task ProcessPayment {
 *         action:
 *             try {
 *                 payment_result = http.post("https://api.payment.com/charge", {
 *                     amount: input.total,
 *                     card: input.payment_info
 *                 })
 *                 
 *                 if payment_result.success {
 *                     order_id = db.insert("orders", {
 *                         customer_id: input.customer_id,
 *                         items: input.items,
 *                         total: input.total,
 *                         status: "paid"
 *                     })
 *                     goto PrepareShipping
 *                 } else {
 *                     goto PaymentFailed
 *                 }
 *             } catch (error) {
 *                 log_error(error)
 *                 goto PaymentFailed
 *             }
 *     }
 *     
 *     # Gateway paralelo para preparar envío y notificar
 *     gateway PrepareShipping parallel {
 *         branch -> UpdateInventory
 *         branch -> NotifyCustomer
 *         branch -> CreateShipment
 *         join -> FinalizeOrder
 *     }
 *     
 *     task UpdateInventory {
 *         action:
 *             for each item in input.items {
 *                 db.execute(
 *                     "UPDATE inventory SET quantity = quantity - ? WHERE id = ?",
 *                     [item.quantity, item.id]
 *                 )
 *             }
 *             goto InventoryUpdated
 *     }
 *     
 *     task NotifyCustomer {
 *         action:
 *             utils.EmailService.send(
 *                 input.email,
 *                 "Order Confirmation",
 *                 "Your order has been confirmed and is being prepared."
 *             )
 *             goto CustomerNotified
 *     }
 *     
 *     task CreateShipment {
 *         action:
 *             shipment = http.post("https://api.shipping.com/create", {
 *                 address: input.shipping_address,
 *                 items: input.items
 *             })
 *             tracking_number = shipment.tracking_number
 *             goto ShipmentCreated
 *     }
 *     
 *     task FinalizeOrder {
 *         action:
 *             db.execute(
 *                 "UPDATE orders SET tracking_number = ?, status = ? WHERE id = ?",
 *                 [tracking_number, "shipped", order_id]
 *             )
 *             goto Success
 *     }
 *     
 *     task ValidationError {
 *         action:
 *             response = { success: false, message: error_message }
 *             goto Error
 *     }
 *     
 *     task InsufficientStock {
 *         action:
 *             response = { success: false, message: "Insufficient stock" }
 *             goto Error
 *     }
 *     
 *     task PaymentFailed {
 *         action:
 *             response = { success: false, message: "Payment failed" }
 *             goto Error
 *     }
 *     
 *     end InventoryUpdated
 *     end CustomerNotified
 *     end ShipmentCreated
 *     end Success
 *     end Error
 * }
 * 
 * # Proceso con gateway exclusivo
 * process ApprovalWorkflow {
 *     start -> EvaluateAmount
 *     
 *     task EvaluateAmount {
 *         action:
 *             gateway ApprovalLevel {
 *                 when input.amount > 10000 -> RequireCEOApproval
 *                 when input.amount > 5000 -> RequireManagerApproval
 *                 when input.amount > 1000 -> RequireSupervisorApproval
 *                 else -> AutoApprove
 *             }
 *     }
 *     
 *     task RequireCEOApproval {
 *         action:
 *             send_approval_request("ceo@company.com", input)
 *             goto WaitingApproval
 *     }
 *     
 *     task RequireManagerApproval {
 *         action:
 *             send_approval_request("manager@company.com", input)
 *             goto WaitingApproval
 *     }
 *     
 *     task RequireSupervisorApproval {
 *         action:
 *             send_approval_request("supervisor@company.com", input)
 *             goto WaitingApproval
 *     }
 *     
 *     task AutoApprove {
 *         action:
 *             update_status(input.id, "approved")
 *             goto Approved
 *     }
 *     
 *     task WaitingApproval {
 *         action:
 *             # En un caso real, esto esperaría una respuesta asíncrona
 *             approval_result = wait_for_approval(input.id)
 *             if approval_result.approved {
 *                 goto Approved
 *             } else {
 *                 goto Rejected
 *             }
 *     }
 *     
 *     end Approved
 *     end Rejected
 * }
 */
