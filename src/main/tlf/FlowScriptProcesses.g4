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

main
    : ((imports)+ | (process)+ | (variable)+ | (function)+ | (COMMENT)+ | (SINGLE_LINE_COMMENT)+)+
    | EOF
    ;

array
    : '[' ']'
    | IDENTIFIER '['(IDENTIFIER | NUMBER | fieldAccess)']'
    ;

atributte
    : IDENTIFIER ':' (IDENTIFIER | fieldAccess | operation | expression)
    ;

object
    : '{' (atributte ((',' atributte)+)?)? '}'
    ;

variable
    : (IDENTIFIER | array) '=' ((IDENTIFIER | NUMBER | BOOLEAN | fieldAccess | STRING | call_funcition | array) | operation | object | expression)
    ;

operation
    : mathExpr
    ;

mathExpr
    : mathTerm (('+'|'-') mathTerm)*        // suma y resta
    ;

mathTerm
    : mathFactor (('*'|'/'|'%') mathFactor)* // multiplicación, división, módulo
    ;

mathFactor
    : '(' mathExpr ')'                      // subexpresión entre paréntesis
    | IDENTIFIER
    | NUMBER
    | BOOLEAN
    | fieldAccess
    ;
variable_type
    : 'integer'
    | 'void'
    | 'string'
    | 'boolean'
    | 'object'
    ;

function_param
    : (IDENTIFIER':'variable_type)
    ;

function_body
    : ((action)+)? ('return' (IDENTIFIER | operation | expression))?
    ;

function
    : 'function' IDENTIFIER '(' (function_param ((',' function_param)+)?)?')' '->' variable_type '{' function_body '}'
    ;

call_funcition
    : IDENTIFIER '('(term ((',' term)+)?)?')'
    ;

imports
    : import_type library ('as' IDENTIFIER)?
    ;

library
    : STRING
    ;

import_type
    : 'import'
    | 'import_jar'
    ;

process
    : 'process' IDENTIFIER '{' start ( task | gateway )+ (end)+ '}'
    ;

start
    : 'start' '->' IDENTIFIER
    ;

end
    : 'end' IDENTIFIER
    ;

task
    : 'task' IDENTIFIER '{' task_body '}'
    ;

task_body
    : 'action'':' (action)+ (goto)?
    ;

goto
    : ('go_to' | 'goto') IDENTIFIER
    ;

action
    : gateway
    | goto
    | variable
    | if
    | for_each
    | call_funcition
    | fieldAccess
    | try_catch
    | array
    | COMMENT
    | SINGLE_LINE_COMMENT
    ;

if
    : 'if' expression '{' (action)+ '}' ('else' '{' (action)+ '}')?
    ;

try_catch
    : 'try' '{' (action)+ '}' 'catch' '(' IDENTIFIER ')' '{' (action)+ '}'
    ;

for_each
    : 'for' 'each' IDENTIFIER 'in' (IDENTIFIER | fieldAccess) '{' action '}'
    ;

gateway
    : 'gateway' IDENTIFIER (exclusive_gateway | parallel_gateway)
    ;

exclusive_gateway
    : '{' ('when' expression '->' IDENTIFIER)+ 'else' '->' IDENTIFIER '}'
    ;

parallel_gateway
    : 'parallel' '{' ('branch' '->' IDENTIFIER)+ 'join' '->' IDENTIFIER '}'
    ;

expression
    : expression 'or' orExpression
    | orExpression
    ;

orExpression
    : orExpression 'and' andExpression
    | andExpression
    ;

andExpression
    : ('not')+ notExpression
    | notExpression
    ;

notExpression
    : '(' expression ')'
    | comparison
    | fieldAccess
    | call_funcition
    | BOOLEAN
    | IDENTIFIER
    ;

comparison
    : term comparisonOp term
    ;

comparisonOp
    : '=='
    | '!='
    | '<'
    | '>'
    | '<='
    | '>='
    ;

term
    : NUMBER
    | call_funcition
    | fieldAccess
    | IDENTIFIER
    | BOOLEAN
    | STRING
    ;

fieldAccess
    : IDENTIFIER ('.' (IDENTIFIER | call_funcition | array))+    // Support nested field access like user.profile.name
    ;

// ============================

// Palabras clave de estructura

IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;
NUMBER: ('+' | '-')? [0-9]+ ('.' [0-9]+)?;
BOOLEAN     : 'true' | 'false' ;
STRING: '"' (~["\r\n])* '"' | '\'' (~['\r\n])* '\'';
COMMENT     : '/*' .*? '*/' -> skip ;
SINGLE_LINE_COMMENT
    : '#' ~[\r\n]* -> skip
    ;
// ============================
// PARSER RULES
// ============================
WS: [ \t\r\n]+ -> skip;
// ============================
// ESTRUCTURA DEL PROGRAMA
// ============================

program
    : main
    | EOF
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