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
// PARSER RULES
// ============================

program
    : declaration* EOF
    ;

declaration
    : importDecl
    | importJarDecl
    | functionDecl
    | variableDecl
    | processDecl
    ;

// Imports
importDecl
    : IMPORT STRING (AS IDENTIFIER)?
    ;

importJarDecl
    : IMPORT_JAR STRING (AS IDENTIFIER)?
    ;

// Variables globales
variableDecl
    : IDENTIFIER ASSIGN expression
    ;

// Funciones
functionDecl
    : FUNCTION IDENTIFIER LEFT_PAREN parameterList? RIGHT_PAREN (ARROW type)? block
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
    | VOID
    ;

block
    : LEFT_BRACE statement+ RIGHT_BRACE
    ;

// Procesos
processDecl
    : PROCESS IDENTIFIER LEFT_BRACE startElement processMiddleElement* endElement+ RIGHT_BRACE
    ;

processMiddleElement
    : taskElement
    | gatewayElement
    ;

startElement
    : START ARROW IDENTIFIER
    ;

endElement
    : END IDENTIFIER
    ;

taskElement
    : TASK IDENTIFIER LEFT_BRACE ACTION COLON statement+ RIGHT_BRACE
    ;

gatewayElement
    : GATEWAY IDENTIFIER PARALLEL LEFT_BRACE parallelGatewayBody RIGHT_BRACE
    | GATEWAY IDENTIFIER LEFT_BRACE exclusiveGatewayBody RIGHT_BRACE
    ;

exclusiveGatewayBody
    : whenClause+ elseClause?
    ;

whenClause
    : WHEN expression ARROW IDENTIFIER
    ;

elseClause
    : ELSE ARROW IDENTIFIER
    ;

parallelGatewayBody
    : parallelBranch+ joinClause
    ;

parallelBranch
    : BRANCH ARROW IDENTIFIER
    ;

joinClause
    : JOIN ARROW IDENTIFIER
    ;

// Sentencias
statement
    : gotoStmt
    | returnStmt
    | ifStmt
    | tryCatchStmt
    | throwStmt
    | forEachStmt
    | assignment
    | gatewayStmt
    | expressionStmt
    | block
    ;

gatewayStmt
    : GATEWAY IDENTIFIER PARALLEL LEFT_BRACE parallelGatewayBody RIGHT_BRACE
    | GATEWAY IDENTIFIER LEFT_BRACE exclusiveGatewayBody RIGHT_BRACE
    ;

gotoStmt
    : (GOTO | GO_TO) IDENTIFIER
    ;

returnStmt
    : RETURN expression
    ;

ifStmt
    : IF expression block (ELSE block)?
    ;

tryCatchStmt
    : TRY block CATCH LEFT_PAREN IDENTIFIER? RIGHT_PAREN block
    ;

throwStmt
    : THROW expression
    ;

forEachStmt
    : FOR EACH IDENTIFIER IN expression block
    ;

assignment
    : lvalue ASSIGN expression
    ;

lvalue
    : IDENTIFIER (lvalueSuffix)*
    ;

lvalueSuffix
    : DOT IDENTIFIER
    | LEFT_BRACKET expression RIGHT_BRACKET
    ;

expressionStmt
    : expression
    ;

// Expresiones
expression
    : logicalOr
    ;

logicalOr
    : logicalAnd (OR logicalAnd)*
    ;

logicalAnd
    : equality (AND equality)*
    ;

equality
    : relational ((EQUAL | NOT_EQUAL) relational)*
    ;

relational
    : additive ((LESS_THAN | GREATER_THAN | LESS_EQUAL | GREATER_EQUAL) additive)*
    ;

additive
    : multiplicative ((PLUS | MINUS) multiplicative)*
    ;

multiplicative
    : unary ((MULTIPLY | DIVIDE | MODULO) unary)*
    ;

unary
    : (NOT | MINUS) unary
    | postfix
    ;

postfix
    : primary (postfixOp)*
    ;

postfixOp
    : LEFT_PAREN argumentList? RIGHT_PAREN
    | DOT IDENTIFIER
    | LEFT_BRACKET expression RIGHT_BRACKET
    ;

argumentList
    : expression (COMMA expression)*
    ;

primary
    : literal
    | IDENTIFIER
    | LEFT_PAREN expression RIGHT_PAREN
    | objectLiteral
    | listLiteral
    ;

literal
    : INTEGER
    | DECIMAL
    | STRING
    | TRUE
    | FALSE
    | NULL
    ;

objectLiteral
    : LEFT_BRACE (objectMember (COMMA objectMember)*)? RIGHT_BRACE
    ;

objectMember
    : IDENTIFIER COLON expression
    ;

listLiteral
    : LEFT_BRACKET (expression (COMMA expression)*)? RIGHT_BRACKET
    ;

// ============================
// LEXER RULES (TOKENS)
// ============================

PROCESS    : 'process';
FUNCTION   : 'function';
IMPORT     : 'import';
IMPORT_JAR : 'import_jar';
AS         : 'as';
RETURN     : 'return';

START    : 'start';
END      : 'end';
TASK     : 'task';
GATEWAY  : 'gateway';
GOTO     : 'goto';
GO_TO    : 'go_to';
WHEN     : 'when';
BRANCH   : 'branch';
JOIN     : 'join';
ELSE     : 'else';
PARALLEL : 'parallel';
ACTION   : 'action';

IF    : 'if';
CATCH : 'catch';
TRY   : 'try';
THROW : 'throw';
FOR   : 'for';
EACH  : 'each';
IN    : 'in';

INTEGER_TYPE : 'integer';
DECIMAL_TYPE : 'decimal';
BOOLEAN_TYPE : 'boolean';
TEXT_TYPE    : 'text';
LIST_TYPE    : 'list';
OBJECT_TYPE  : 'object';
VOID         : 'void';

TRUE  : 'true';
FALSE : 'false';
NULL  : 'null';

AND : 'and';
OR  : 'or';
NOT : 'not';

PLUS     : '+';
MINUS    : '-';
MULTIPLY : '*';
DIVIDE   : '/';
MODULO   : '%';

LESS_EQUAL    : '<=';
GREATER_EQUAL : '>=';
LESS_THAN     : '<';
GREATER_THAN  : '>';
EQUAL         : '==';
NOT_EQUAL     : '!=';

ASSIGN : '=';
DOT    : '.';

LEFT_PAREN    : '(';
RIGHT_PAREN   : ')';
LEFT_BRACE    : '{';
RIGHT_BRACE   : '}';
LEFT_BRACKET  : '[';
RIGHT_BRACKET : ']';
COMMA         : ',';
SEMICOLON     : ';';
COLON         : ':';
ARROW         : '->';

DECIMAL : DIGIT+ '.' DIGIT+ ;
INTEGER : DIGIT+ ;
STRING  : '"' (ESC | ~["\\\r\n])* '"' ;

IDENTIFIER : LETTER (LETTER | DIGIT | '_')* ;

fragment LETTER  : [a-zA-Z_];
fragment DIGIT   : [0-9];
fragment ESC     : '\\' ["ntbrf\\] | UNICODE ;
fragment UNICODE : 'u' HEX HEX HEX HEX ;
fragment HEX     : [0-9a-fA-F];

WS            : [ \t\r\n]+ -> skip ;
LINE_COMMENT  : '#' ~[\r\n]* -> skip ;
BLOCK_COMMENT : '/*' .*? '*/' -> skip ;


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