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
    : importStmt* globalVarDecl* declaration* EOF
    ;

// --------------------
// Imports y Globales
// --------------------
importStmt
    : IMPORT stringLiteral (AS identifier)?                #flowImport
    | IMPORT_JAR stringLiteral AS identifier               #jarImport
    ;

globalVarDecl
    : identifier ASSIGN expression
    ;

declaration
    : functionDecl
    | processDecl
    ;

// --------------------
// Funciones (simplificadas)
// --------------------
functionDecl
    : FUNCTION identifier LPAREN paramList? RPAREN returnType? LBRACE statementList RBRACE
    ;

paramList
    : param (COMMA param)*
    ;

param
    : identifier COLON type
    ;

returnType
    : ARROW type
    ;

type
    : INTEGER
    | DECIMAL
    | BOOLEAN
    | TEXT
    | LIST
    | OBJECT
    | VOID
    ;

// --------------------
// Procesos
// --------------------
processDecl
    : PROCESS identifier LBRACE startNode processBody endNode+ RBRACE
    ;

processBody
    : (taskNode | gatewayNode | endNode)*
    ;

processElement
    : startNode
    | taskNode
    | endNode
    | gatewayNode
    ;

startNode
    : START ARROW identifier
    ;

taskNode
    : TASK identifier LBRACE ACTION COLON (statementList | gatewayNode) RBRACE
    ;

endNode
    : END identifier
    ;

gatewayNode
    : GATEWAY identifier LBRACE whenClause+ (ELSE ARROW identifier)? RBRACE     #exclusiveGateway
    | GATEWAY identifier PARALLEL LBRACE branchClauses joinClause RBRACE        #parallelGateway
    ;

whenClause
    : WHEN expression ARROW identifier
    ;

branchClauses
    : (BRANCH ARROW identifier)+
    ;

joinClause
    : JOIN ARROW identifier
    ;

// --------------------
// Statements
// --------------------
statementList
    : statement*
    ;

statement
    : assignment
    | ifStmt
    | whileStmt
    | forEachStmt
    | forRangeStmt
    | tryCatchStmt
    | throwStmt
    | gotoStmt
    | returnStmt
    | expressionStmt
    ;

assignment
    : (identifier | memberAccess | indexAccess) ASSIGN expression
    ;

ifStmt
    : IF expression LBRACE statementList RBRACE elseIfClause* elseClause?
    ;

elseIfClause
    : ELSE_IF expression LBRACE statementList RBRACE
    ;

elseClause
    : ELSE LBRACE statementList RBRACE
    ;

whileStmt
    : WHILE expression LBRACE statementList RBRACE
    ;

forEachStmt
    : FOR EACH identifier IN expression LBRACE statementList RBRACE
    ;

forRangeStmt
    : FOR identifier FROM expression TO expression (STEP expression)? LBRACE statementList RBRACE
    ;

tryCatchStmt
    : TRY LBRACE statementList RBRACE CATCH LPAREN identifier RPAREN LBRACE statementList RBRACE
    ;

throwStmt
    : THROW expression
    ;

gotoStmt
    : GOTO identifier
    ;

returnStmt
    : RETURN expression?
    ;

expressionStmt
    : expression
    ;

// --------------------
// Expressions
// --------------------
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
    : relationalExpr ((EQ | NEQ) relationalExpr)*
    ;

relationalExpr
    : addExpr ((LT | GT | LE | GE) addExpr)*
    ;

addExpr
    : mulExpr ((PLUS | MINUS) mulExpr)*
    ;

mulExpr
    : unaryExpr ((MUL | DIV | MOD) unaryExpr)*
    ;

unaryExpr
    : (NOT | MINUS) unaryExpr
    | postfixExpr
    ;

primaryExpr
    : literal
    | identifier
    | functionCall
    | LPAREN expression RPAREN
    ;

postfixExpr
    : primaryExpr ( (DOT identifier) | (DOT functionCall) | (LBRACK expression RBRACK) )*
    ;

functionCall
    : identifier LPAREN argList? RPAREN
    ;

argList
    : expression (COMMA expression)*
    ;

memberAccess
    : postfixExpr DOT identifier
    ;

indexAccess
    : postfixExpr LBRACK expression RBRACK
    ;

// --------------------
// Literales
// --------------------
literal
    : integerLiteral
    | decimalLiteral
    | booleanLiteral
    | stringLiteral
    | listLiteral
    | objectLiteral
    | NULLLIT
    ;

integerLiteral
    : INT
    ;

decimalLiteral
    : DECIMAL_LIT
    ;

booleanLiteral
    : TRUE
    | FALSE
    ;

stringLiteral
    : STRING
    ;

listLiteral
    : LBRACK (expression (COMMA expression)*)? RBRACK
    ;

objectLiteral
    : LBRACE (objectField (COMMA objectField)*)? RBRACE
    ;

objectField
    : identifier COLON expression
    ;

// ============================
// LEXER RULES (TOKENS)
// ============================

// Palabras clave
IMPORT      : 'import';
IMPORT_JAR  : 'import_jar';
AS          : 'as';
PROCESS     : 'process';
FUNCTION    : 'function';
RETURN      : 'return';
START       : 'start';
END         : 'end';
TASK        : 'task';
ACTION      : 'action';
GATEWAY     : 'gateway';
WHEN        : 'when';
ELSE        : 'else';
ELSE_IF     : 'else_if';
PARALLEL    : 'parallel';
BRANCH      : 'branch';
JOIN        : 'join';
IF          : 'if';
TRY         : 'try';
CATCH       : 'catch';
THROW       : 'throw';
WHILE       : 'while';
FOR         : 'for';
EACH        : 'each';
IN          : 'in';
FROM        : 'from';
TO          : 'to';
STEP        : 'step';
BREAK       : 'break';
CONTINUE    : 'continue';
GOTO        : 'go_to';

// Tipos
INTEGER     : 'integer';
DECIMAL     : 'decimal';
BOOLEAN     : 'boolean';
TEXT        : 'text';
LIST        : 'list';
OBJECT      : 'object';
VOID        : 'void';
NULLLIT     : 'null';
TRUE        : 'true';
FALSE       : 'false';

// Operadores
PLUS        : '+';
MINUS       : '-';
MUL         : '*';
DIV         : '/';
MOD         : '%';
LT          : '<';
GT          : '>';
LE          : '<=';
GE          : '>=';
EQ          : '==';
NEQ         : '!=';
AND         : 'and';
OR          : 'or';
NOT         : 'not';
ASSIGN      : '=';
DOT         : '.';
LBRACK      : '[';
RBRACK      : ']';
LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
COMMA       : ',';
COLON       : ':';
ARROW       : '->';

// Literales
INT             : [0-9]+;
DECIMAL_LIT     : [0-9]+ '.' [0-9]+;
STRING          : '"' ( ~["\\] | '\\' . )* '"';

// Identificadores
identifier
    : IDENT
    ;

IDENT           : [a-zA-Z_][a-zA-Z0-9_]*;

// Comentarios y espacio en blanco
LINE_COMMENT    : '#' ~[\r\n]* -> skip;
BLOCK_COMMENT   : '/*' .*? '*/' -> skip;
WS              : [ \t\r\n]+ -> skip;




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