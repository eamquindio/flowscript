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

PROCESS  : 'process';
FUNCTION : 'function';
IMPORT   : 'import';
IMPORT_JAR : 'import_jar';
AS       : 'as';
RETURN   : 'return';

START    : 'start';
END      : 'end';
TASK     : 'task';
GATEWAY  : 'gateway';
GOTO     : 'go_to'; 
WHEN     : 'when';
BRANCH   : 'branch';
JOIN     : 'join';
ELSE     : 'else';
PARALLEL : 'parallel';
ACTION   : 'action';

IF       : 'if';
ELSE_IF  : 'else_if';
TRY      : 'try';
CATCH    : 'catch';
THROW    : 'throw';
WHILE    : 'while';
FOR      : 'for';
EACH     : 'each';
IN       : 'in';
FROM     : 'from';
TO       : 'to';
STEP     : 'step';
BREAK    : 'break';
CONTINUE : 'continue';

INTEGER_TYPE : 'integer';
DECIMAL_TYPE : 'decimal';
BOOLEAN_TYPE : 'boolean';
TEXT_TYPE    : 'text';
LIST_TYPE    : 'list';
OBJECT_TYPE  : 'object';
VOID         : 'void';

NULL     : 'null';
TRUE     : 'true';
FALSE    : 'false';

AND      : 'and';
OR       : 'or';
NOT      : 'not';

PLUS         : '+';
MINUS        : '-';
MULTIPLY     : '*';
DIVIDE       : '/';
MODULO       : '%';

LESS_EQUAL     : '<=';
GREATER_EQUAL  : '>=';
EQUAL          : '==';
NOT_EQUAL      : '!=';
LESS_THAN      : '<';
GREATER_THAN   : '>';

ASSIGN     : '=';
QUESTION   : '?';
DOT        : '.';

LEFT_PAREN   : '(';
RIGHT_PAREN  : ')';
LBRACE       : '{';
RBRACE       : '}';
LEFT_BRACKET : '[';
RIGHT_BRACKET: ']';
COMMA        : ',';
SEMICOLON    : ';';
COLON        : ':';
ARROW        : '->';

INTEGER_LITERAL : [0-9]+;
DECIMAL_LITERAL : [0-9]+ '.' [0-9]+;
STRING_LITERAL  : '"' ( '\\' . | ~["\\] )* '"';

IDENTIFIER : [a-zA-Z_][a-zA-Z0-9_]*;
INPUT      : 'input';

LINE_COMMENT  : '//' ~[\r\n]* -> skip ;
BLOCK_COMMENT : '/*' .*? '*/'  -> skip ;
HASH_COMMENT  : '#'  ~[\r\n]*  -> skip ; // Para ejemplos con '# ...'
WS            : [ \t\r\n]+     -> skip ;


// ============================
// PARSER RULES
// ============================

// ============================
// ESTRUCTURA DEL PROGRAMA
// ============================

program
    : ( importDecl
      | importJarDecl
      | globalVar
      | functionDecl
      | processDecl
      )* EOF
    ;

importDecl
    : IMPORT STRING_LITERAL (AS IDENTIFIER)? SEMICOLON?
    ;

importJarDecl
    : IMPORT_JAR STRING_LITERAL AS IDENTIFIER SEMICOLON?
    ;

globalVar
    : IDENTIFIER ASSIGN globalValue SEMICOLON?
    ;

globalValue
    : INTEGER_LITERAL
    | DECIMAL_LITERAL
    | STRING_LITERAL
    | TRUE | FALSE | NULL
    | IDENTIFIER
    ;

functionDecl
    : FUNCTION IDENTIFIER LEFT_PAREN paramList? RIGHT_PAREN ARROW type
      LBRACE functionBody RBRACE
    ;

paramList
    : param (COMMA param)*
    ;

param
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

functionBody
    : ( ~RBRACE )*      
    ;

processDecl
    : PROCESS IDENTIFIER LBRACE processBody RBRACE
    ;
    
processBody
    : startElement (taskElement | gatewayExclusive | gatewayParallel)* endElement+
    ;

processElement
    : startElement
    | taskElement
    | gatewayExclusive
    | gatewayParallel
    | endElement
    ;

startElement
    : START ARROW IDENTIFIER SEMICOLON?          #StartSimple
    ;

endElement
    : END IDENTIFIER SEMICOLON?                  #EndSimple
    ;

taskElement
    : TASK IDENTIFIER LBRACE ACTION COLON taskActionItems RBRACE
    ;

taskActionItems
    : statement*
    ;

statement
    : gotoStmt
    | ifElseStmt
    | tryCatchStmt
    | forEachStmt    
    | gatewayExclusive
    | gatewayParallel
    | assignment
    | expressionStmt
    ;

gotoStmt
    : GOTO IDENTIFIER SEMICOLON?                    
    ;

assignment
    : lvalue ASSIGN expression SEMICOLON?
    ;

lvalue
    : IDENTIFIER (DOT IDENTIFIER | LEFT_BRACKET expression RIGHT_BRACKET)*
    ;

expressionStmt
    : expression SEMICOLON?
    ;

ifElseStmt
    : IF expression block (ELSE_IF expression block)* (ELSE block)?
    ;

tryCatchStmt
    : TRY  block
      CATCH LEFT_PAREN IDENTIFIER RIGHT_PAREN block
    ;

forEachStmt
    : FOR EACH IDENTIFIER IN expression block
    ;

block
    : LBRACE statement* RBRACE
    ;

gatewayExclusive
    : GATEWAY IDENTIFIER LBRACE whenClause+ elseClause? RBRACE
    ;

whenClause
    : WHEN expression ARROW IDENTIFIER SEMICOLON?
    ;

elseClause
    : ELSE ARROW IDENTIFIER SEMICOLON?
    ;

gatewayParallel
    : GATEWAY IDENTIFIER PARALLEL LBRACE parallelBranch+ joinClause RBRACE
    ;

parallelBranch
    : BRANCH ARROW IDENTIFIER SEMICOLON?
    ;

joinClause
    : JOIN ARROW IDENTIFIER SEMICOLON?
    ;

expression
    : expression QUESTION expression COLON expression                        
    | expression OR expression                                                
    | expression AND expression                                               
    | expression (EQUAL | NOT_EQUAL) expression                               
    | expression (LESS_THAN | LESS_EQUAL | GREATER_THAN | GREATER_EQUAL) expression  
    | expression (PLUS | MINUS) expression                                    
    | expression (MULTIPLY | DIVIDE | MODULO) expression                      
    | NOT expression                                                          
    | MINUS expression                                                        
    | postfixExpr                                                             
    ;

postfixExpr
    : primary ( DOT IDENTIFIER
              | LEFT_BRACKET expression RIGHT_BRACKET
              | LEFT_PAREN argumentList? RIGHT_PAREN
              )*
    ;

primary
    : literal
    | INPUT
    | IDENTIFIER
    | LEFT_PAREN expression RIGHT_PAREN
    | objectLiteral
    | listLiteral
    ;

argumentList
    : expression (COMMA expression)*
    ;

literal
    : INTEGER_LITERAL
    | DECIMAL_LITERAL
    | STRING_LITERAL
    | TRUE
    | FALSE
    | NULL
    ;

objectLiteral
    : LBRACE (objectField (COMMA objectField)*)? RBRACE
    ;

objectField
    : (IDENTIFIER | STRING_LITERAL) COLON expression
    ;

listLiteral
    : LEFT_BRACKET (expression (COMMA expression)*)? RIGHT_BRACKET
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