grammar FlowScriptProcesses;

// Package declaration for generated code
@header {
    package edu.eam.ingesoft.tlf;
}

/*
 * GRAMÁTICA DE PROCESOS PARA FLOWSCRIPT

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
PROCESS: 'process';
START: 'start';
END: 'end';
TASK: 'task';
GATEWAY: 'gateway';
GO_TO: 'go_to';
WHEN: 'when';
BRANCH: 'branch';
JOIN: 'join';
PARALLEL: 'parallel';
ELSE: 'else';

// Palabras clave de funciones
FUNCTION: 'function';
RETURN: 'return';

// Palabras clave de control
IF: 'if';
ELSE_IF: 'else_if';
FOR: 'for';
EACH: 'each';
IN: 'in';
TRY: 'try';
CATCH: 'catch';
THROW: 'throw';

// Tipos de datos
INTEGER_TYPE: 'integer';
DECIMAL_TYPE: 'decimal';
BOOLEAN_TYPE: 'boolean';
TEXT_TYPE: 'text';
LIST_TYPE: 'list';
OBJECT_TYPE: 'object';
VOID_TYPE: 'void';

// Valores
TRUE: 'true';
FALSE: 'false';
NULL: 'null';

// Importaciones
IMPORT: 'import';
IMPORT_JAR: 'import_jar';
AS: 'as';

// Operadores lógicos
AND: 'and';
OR: 'or';
NOT: 'not';

// Operadores
ASSIGN: '=';
ARROW: '->';
PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';
MOD: '%';
LT: '<';
GT: '>';
LE: '<=';
GE: '>=';
EQ: '==';
NE: '!=';

// Delimitadores
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
LBRACKET: '[';
RBRACKET: ']';
COMMA: ',';
COLON: ':';
SEMICOLON: ';';
DOT: '.';

// Literales
INTEGER_LITERAL: [0-9]+ ('_' [0-9]+)*;
DECIMAL_LITERAL: [0-9]+ '.' [0-9]+ ([eE] [+-]? [0-9]+)?;
STRING_LITERAL: '"' (~["\\\r\n] | '\\' ["\\/bfnrt] | '\\u' [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F])* '"';

// Identificadores
IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;

// Comentarios y espacios
LINE_COMMENT: '#' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
WS: [ \t\r\n]+ -> skip;


// ============================
// PARSER RULES
// ============================

// iimportaciones y globlaes

importStatement
    : IMPORT STRING_LITERAL (AS IDENTIFIER)? SEMICOLON?
    | IMPORT_JAR STRING_LITERAL AS IDENTIFIER SEMICOLON?
    ;

globalVariable
    : IDENTIFIER ASSIGN expression SEMICOLON?
    ;


// Funcciones

functionDeclaration
    : FUNCTION IDENTIFIER LPAREN parameterList? RPAREN (ARROW type)? block
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : IDENTIFIER COLON type
    ;

type
    : INTEGER_TYPE | DECIMAL_TYPE | BOOLEAN_TYPE | TEXT_TYPE
    | LIST_TYPE | OBJECT_TYPE | VOID_TYPE
    ;

block
    : LBRACE statement* RBRACE
    ;



// Procesos

processDeclaration
    : PROCESS IDENTIFIER LBRACE processElement+ RBRACE
    ;

processElement
    : startNode
    | taskNode
    | endNode
    | gatewayNode
    ;

startNode
    : START ARROW IDENTIFIER
    ;

taskNode
    : TASK IDENTIFIER LBRACE actionBlock RBRACE
    ;

endNode
    : END IDENTIFIER
    ;



// los Gtaeways y los flujos

gatewayNode
    : GATEWAY IDENTIFIER (PARALLEL)? LBRACE gatewayBody RBRACE
    ;

gatewayBody
    : (whenBranch | elseBranch | branchStatement | joinStatement)+
    ;

whenBranch
    : WHEN expression ARROW IDENTIFIER
    ;

elseBranch
    : ELSE ARROW IDENTIFIER
    ;

branchStatement
    : BRANCH ARROW IDENTIFIER
    ;

joinStatement
    : JOIN ARROW IDENTIFIER
    ;

actionBlock
    : 'action:' (statement | gatewayNode)+
    ;

gotoStatement
    : GO_TO IDENTIFIER SEMICOLON?
    ;


// Elas expresiiones y los staments


statement
    : variableDeclaration
    | assignment
    | ifStatement
    | forEachStatement
    | tryCatchStatement
    | gotoStatement
    | expression SEMICOLON?
    | returnStatement
    | block
    ;

variableDeclaration
    : IDENTIFIER ASSIGN expression SEMICOLON?
    ;

assignment
    : postfixExpression ASSIGN expression SEMICOLON?
    ;


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
    : relationalExpression ((EQ | NE) relationalExpression)*
    ;

relationalExpression
    : additiveExpression ((LT | GT | LE | GE) additiveExpression)*
    ;

additiveExpression
    : multiplicativeExpression ((PLUS | MINUS) multiplicativeExpression)*
    ;

multiplicativeExpression
    : unaryExpression ((MULT | DIV | MOD) unaryExpression)*
    ;

unaryExpression
    : (NOT | MINUS)? postfixExpression
    ;

postfixExpression
    : primaryExpression (postfixOp)*
    ;
postfixOp
    : memberAccess
    | functionCall
    | indexAccess
    ;

memberAccess
    : DOT IDENTIFIER
    ;

functionCall
    : LPAREN (expression (COMMA expression)*)? RPAREN
    ;

indexAccess
    : LBRACKET expression RBRACKET
    ;

primaryExpression
    : literal
    | IDENTIFIER
    | LPAREN expression RPAREN
    | inputAccess
    ;

literal
    : INTEGER_LITERAL
    | DECIMAL_LITERAL
    | STRING_LITERAL
    | TRUE
    | FALSE
    | NULL
    | listLiteral
    | objectLiteral
    ;

listLiteral
    : LBRACKET (expression (COMMA expression)*)? RBRACKET
    ;

objectLiteral
    : LBRACE (objectProperty (COMMA objectProperty)*)? RBRACE
    ;

objectProperty
    : IDENTIFIER COLON expression
    ;


inputAccess
    : 'input' (DOT IDENTIFIER | LBRACKET expression RBRACKET)*
    ;

ifStatement
    : IF expression block (ELSE_IF expression block)* (ELSE block)?
    ;

forEachStatement
    : FOR EACH IDENTIFIER IN expression block
    ;

tryCatchStatement
    : TRY block CATCH LPAREN IDENTIFIER RPAREN block
    ;

returnStatement
    : RETURN expression? SEMICOLON?
    ;

// ============================
// ESTRUCTURA DEL PROGRAMA
// ============================

program
    : (importStatement | globalVariable | functionDeclaration | processDeclaration)* EOF
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