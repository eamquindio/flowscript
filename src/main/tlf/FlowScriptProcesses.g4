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

// Palabras clave de estructura


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

// ----------------------------
// IMPORTS
// ----------------------------
importStmt
    : IMPORT STRING_LITERAL (AS IDENTIFIER)?
    ;

importJarStmt
    : IMPORT_JAR STRING_LITERAL AS IDENTIFIER
    ;

// ----------------------------
// VARIABLES GLOBALES
// ----------------------------
globalVarDecl
    : IDENTIFIER ASSIGN expression
    ;

// ----------------------------
// FUNCIONES (versión simplificada)
// ----------------------------
functionDecl
    : FUNCTION IDENTIFIER LPAREN parameterList? RPAREN (ARROW typeName)? block
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : IDENTIFIER COLON typeName
    ;

typeName
    : INTEGER_T
    | DECIMAL_T
    | BOOLEAN_T
    | TEXT_T
    | LIST_T
    | OBJECT_T
    | VOID_T
    ;

// ----------------------------
// PROCESOS
// ----------------------------
processDecl
    : PROCESS IDENTIFIER LBRACE startDecl processBody RBRACE
    ;

processBody
    : (taskDecl | gatewayDecl)* endDecl (taskDecl | gatewayDecl | endDecl)*
    ;

startDecl
    : START ARROW IDENTIFIER
    ;

endDecl
    : END IDENTIFIER
    ;

taskDecl
    : TASK IDENTIFIER LBRACE ACTION COLON statementList RBRACE
    ;

gatewayDecl
    : GATEWAY IDENTIFIER gatewayBody
    ;

gatewayBody
    : LBRACE exclusiveGatewayBody RBRACE            #exclusiveGateway
    | PARALLEL LBRACE parallelGatewayBody RBRACE    #parallelGateway
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
    : branchClause+ JOIN ARROW IDENTIFIER
    ;

branchClause
    : BRANCH ARROW IDENTIFIER
    ;

// ----------------------------
// SENTENCIAS (acciones dentro de task)
// ----------------------------
statementList
    : (statement)*
    ;

statement
    : gotoStmt
    | ifStmt
    | tryCatchStmt
    | whileStmt
    | forEachStmt
    | forRangeStmt
    | returnStmt
    | assignment
    | exprStmt
    | gatewayStmt // gateway exclusivo dentro de action
    ;

gotoStmt
    : GO_TO IDENTIFIER
    ;

assignment
    : assignable ASSIGN expression
    ;

assignable
    : IDENTIFIER (memberAccess | indexer)*
    ;

memberAccess
    : DOT IDENTIFIER (indexer)*
    ;

indexer
    : LBRACK expression RBRACK
    ;

exprStmt
    : expression
    ;

ifStmt
    : IF expression block (ELSE_IF expression block)* (ELSE block)?
    ;

tryCatchStmt
    : TRY block CATCH LPAREN IDENTIFIER RPAREN block
    ;

whileStmt
    : WHILE expression block
    ;

forEachStmt
    : FOR EACH IDENTIFIER IN expression block
    ;

forRangeStmt
    : FOR IDENTIFIER FROM expression TO expression (STEP expression)? block
    ;

returnStmt
    : RETURN expression?
    ;

gatewayStmt
    : GATEWAY IDENTIFIER LBRACE exclusiveGatewayBody RBRACE
    ;

block
    : LBRACE statementList RBRACE
    ;

// ----------------------------
// EXPRESIONES
// ----------------------------

expression
    : logicOrExpr
    ;

logicOrExpr
    : logicAndExpr (OR logicAndExpr)*
    ;

logicAndExpr
    : equalityExpr (AND equalityExpr)*
    ;

equalityExpr
    : relationalExpr ((EQ | NEQ) relationalExpr)*
    ;

relationalExpr
    : additiveExpr ((LT | LTE | GT | GTE) additiveExpr)*
    ;

additiveExpr
    : multiplicativeExpr ((PLUS | MINUS) multiplicativeExpr)*
    ;

multiplicativeExpr
    : unaryExpr ((MUL | DIV | MOD) unaryExpr)*
    ;

unaryExpr
    : (NOT | MINUS) unaryExpr
    | callMemberIndexExpr
    ;

callMemberIndexExpr
    : primaryExpr (callSuffix | memberSuffix | indexSuffix)*
    ;

callSuffix
    : LPAREN argumentList? RPAREN
    ;

memberSuffix
    : DOT IDENTIFIER
    ;

indexSuffix
    : LBRACK expression RBRACK
    ;

argumentList
    : expression (COMMA expression)*
    ;

primaryExpr
    : literal
    | INPUT
    | IDENTIFIER
    | LPAREN expression RPAREN
    | listLiteral
    | objectLiteral
    ;

literal
    : TRUE
    | FALSE
    | NULL
    | DECIMAL
    | INTEGER
    | STRING_LITERAL
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
// LEXER RULES (TOKENS)
// ============================

// Palabras clave de estructura y control
IMPORT      : 'import';
IMPORT_JAR  : 'import_jar';
AS          : 'as';
PROCESS     : 'process';
START       : 'start';
TASK        : 'task';
ACTION      : 'action';
END         : 'end';
GATEWAY     : 'gateway';
PARALLEL    : 'parallel';
WHEN        : 'when';
ELSE        : 'else';
BRANCH      : 'branch';
JOIN        : 'join';
GO_TO       : 'go_to';

// Control de flujo y funciones básicas (simplificado)
FUNCTION    : 'function';
RETURN      : 'return';
TRY         : 'try';
CATCH       : 'catch';
THROW       : 'throw';
IF          : 'if';
ELSE_IF     : 'else_if';
WHILE       : 'while';
FOR         : 'for';
EACH        : 'each';
IN          : 'in';
FROM        : 'from';
TO          : 'to';
STEP        : 'step';
BREAK       : 'break';
CONTINUE    : 'continue';
INPUT       : 'input';

// Tipos
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

// Operadores
ARROW       : '->';
ASSIGN      : '=';
EQ          : '==';
NEQ         : '!=';
LT          : '<';
LTE         : '<=';
GT          : '>';
GTE         : '>=';
AND         : 'and';
OR          : 'or';
NOT         : 'not';
PLUS        : '+';
MINUS       : '-';
MUL         : '*';
DIV         : '/';
MOD         : '%';
DOT         : '.';
COMMA       : ',';
COLON       : ':';
LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
LBRACK      : '[';
RBRACK      : ']';

// Literales básicos
DECIMAL     : [0-9]+ '.' [0-9]+ ( [eE] [+-]? [0-9]+ )?;
INTEGER     : [0-9]+;
STRING_LITERAL
    : '"' ( '\\' [btnr"\\] | ~["\\\r\n] )* '"'
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
 *          action:
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
