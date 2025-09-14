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

// Palabras clave
IMPORT      : 'import';
IMPORT_JAR  : 'import_jar';
AS          : 'as';
PROCESS     : 'process';
TASK        : 'task';
START       : 'start';
END         : 'end';
GATEWAY     : 'gateway';
PARALLEL    : 'parallel';
WHEN        : 'when';
ELSE        : 'else';
BRANCH      : 'branch';
JOIN        : 'join';
GO_TO       : 'go_to';
FUNCTION    : 'function';
RETURN      : 'return';
TRY         : 'try';
CATCH       : 'catch';
IF          : 'if';
ELSE_IF     : 'else_if';
FOR         : 'for';
EACH        : 'each';
IN          : 'in';
ACTION      : 'action';

// Tipos de datos (para funciones simplificadas)
INTEGER_T   : 'integer';
DECIMAL_T   : 'decimal';
BOOLEAN_T   : 'boolean';
TEXT_T      : 'text';
LIST_T      : 'list';
OBJECT_T    : 'object';
VOID_T      : 'void';

// Literales
TRUE        : 'true';
FALSE       : 'false';
NULL        : 'null';

// Operadores lógicos como palabras
AND         : 'and';
OR          : 'or';
NOT         : 'not';

// Símbolos y operadores
ARROW       : '->';
EQ          : '=';
PLUS        : '+';
MINUS       : '-';
STAR        : '*';
DIV         : '/';
MOD         : '%';
LE          : '<=';
GE          : '>=';
LT          : '<';
GT          : '>';
EQ2         : '==';
NEQ         : '!=';
DOT         : '.';
LBRACK      : '[';
RBRACK      : ']';
LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
COMMA       : ',';
COLON       : ':';

// Cadenas de texto con escapes básicos
STRING
    : '"' ( '\\' . | ~["\\] )* '"'
    ;

// Números (enteros y decimales simples)
NUMBER
    : DIGITS ('.' DIGITS)?
    ;

fragment DIGITS
    : [0-9]+
    ;

// Identificadores
IDENTIFIER
    : [a-zA-Z_] [a-zA-Z0-9_]*
    ;

// Comentarios y espacios en blanco
LINE_COMMENT
    : '#' ~[\r\n]* -> skip
    ;

BLOCK_COMMENT
    : '/*' .*? '*/' -> skip
    ;

WS
    : [ \t\r\n]+ -> skip
    ;


// ============================
// PARSER RULES
// ============================

// ============================
// ESTRUCTURA DEL PROGRAMA
// ============================

program
    : (importStmt
      | importJarStmt
      | globalVarDecl
      | functionDecl
      | processDecl
      )* EOF
    ;

// Imports
importStmt
    : IMPORT STRING (AS IDENTIFIER)?
    ;

importJarStmt
    : IMPORT_JAR STRING AS IDENTIFIER
    ;

// Variables globales (deben tener asignación)
globalVarDecl
    : IDENTIFIER EQ expression
    ;

// ============================
// FUNCIONES (SIMPLIFICADAS)
// ============================

functionDecl
    : FUNCTION IDENTIFIER LPAREN parameterList? RPAREN returnType? LBRACE functionBody RBRACE
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : IDENTIFIER COLON type
    ;

type
    : INTEGER_T
    | DECIMAL_T
    | BOOLEAN_T
    | TEXT_T
    | LIST_T
    | OBJECT_T
    | VOID_T
    ;

returnType
    : ARROW type
    ;

functionBody
    : functionStatement*
    ;

functionStatement
    : returnStmt
    | exprStmt
    ;

returnStmt
    : RETURN expression?
    ;

exprStmt
    : expression
    ;

// ============================
// PROCESOS
// ============================

processDecl
    : PROCESS IDENTIFIER LBRACE startDecl processBody endDecl+ RBRACE
    ;

startDecl
    : START ARROW IDENTIFIER
    ;

processBody
    : processElement*
    ;

processElement
    : taskDecl
    | gatewayDecl
    ;

endDecl
    : END IDENTIFIER
    ;

// Tareas
taskDecl
    : TASK IDENTIFIER LBRACE ACTION COLON statementList RBRACE
    ;

statementList
    : statement*
    ;

statement
    : assignment
    | ifStatement
    | tryCatch
    | forEach
    | gatewayStmt
    | goToStmt
    | exprStmt
    ;

assignment
    : lvalue EQ expression
    ;

lvalue
    : IDENTIFIER (memberAccess | indexing)*
    ;

ifStatement
    : IF expression block (ELSE_IF expression block)* (ELSE block)?
    ;

block
    : LBRACE statementList RBRACE
    ;

tryCatch
    : TRY block CATCH LPAREN IDENTIFIER RPAREN block
    ;

forEach
    : FOR EACH IDENTIFIER IN expression block
    ;

goToStmt
    : GO_TO IDENTIFIER
    ;

// Gateways dentro de tareas
gatewayStmt
    : GATEWAY IDENTIFIER LBRACE whenClause+ elseClause RBRACE                #exclusiveGatewayStmt
    | GATEWAY IDENTIFIER PARALLEL LBRACE parallelBranch+ parallelJoin RBRACE #parallelGatewayStmt
    ;

// Gateways como nodos del proceso
gatewayDecl
    : GATEWAY IDENTIFIER LBRACE whenClause+ elseClause RBRACE                #exclusiveGatewayDecl
    | GATEWAY IDENTIFIER PARALLEL LBRACE parallelBranch+ parallelJoin RBRACE #parallelGatewayDecl
    ;

whenClause
    : WHEN expression ARROW IDENTIFIER
    ;

elseClause
    : ELSE ARROW IDENTIFIER
    ;

parallelBranch
    : BRANCH ARROW IDENTIFIER
    ;

parallelJoin
    : JOIN ARROW IDENTIFIER
    ;

// ============================
// EXPRESIONES
// ============================

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
    : relationalExpr ((EQ2 | NEQ) relationalExpr)*
    ;

relationalExpr
    : additiveExpr ((LT | GT | LE | GE) additiveExpr)*
    ;

additiveExpr
    : multiplicativeExpr ((PLUS | MINUS) multiplicativeExpr)*
    ;

multiplicativeExpr
    : unaryExpr ((STAR | DIV | MOD) unaryExpr)*
    ;

unaryExpr
    : (NOT | MINUS) unaryExpr
    | postfixExpr
    ;

postfixExpr
    : primary (postfix)*
    ;

postfix
    : functionCall
    | memberAccess
    | indexing
    ;

functionCall
    : LPAREN argumentList? RPAREN
    ;

argumentList
    : expression (COMMA expression)*
    ;

memberAccess
    : DOT IDENTIFIER
    ;

indexing
    : LBRACK expression RBRACK
    ;

primary
    : literal
    | IDENTIFIER
    | LPAREN expression RPAREN
    ;

literal
    : NUMBER
    | STRING
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
    : LBRACE (objectPair (COMMA objectPair)*)? RBRACE
    ;

objectPair
    : IDENTIFIER COLON expression
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
