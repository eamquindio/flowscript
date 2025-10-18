grammar FlowScriptProcesses;

@header {
    package edu.eam.ingesoft.tlf;
}

// LEXER RULES
IMPORT: 'import';
IMPORT_JAR: 'import_jar';
AS: 'as';
PROCESS: 'process';
FUNCTION: 'function';
START: 'start';
TASK: 'task';
END: 'end';
GATEWAY: 'gateway';
ACTION: 'action';
GO_TO: 'go_to';
WHEN: 'when';
ELSE: 'else';
BRANCH: 'branch';
JOIN: 'join';
PARALLEL: 'parallel';
IF: 'if';
ELSE_IF: 'else_if';
WHILE: 'while';
FOR: 'for';
EACH: 'each';
IN: 'in';
FROM: 'from';
TO: 'to';
STEP: 'step';
TRY: 'try';
CATCH: 'catch';
THROW: 'throw';
RETURN: 'return';
BREAK: 'break';
CONTINUE: 'continue';
TRUE: 'true';
FALSE: 'false';
NULL: 'null';
AND: 'and';
OR: 'or';
NOT: 'not';
INTEGER_TYPE: 'integer';
DECIMAL_TYPE: 'decimal';
BOOLEAN_TYPE: 'boolean';
TEXT_TYPE: 'text';
LIST_TYPE: 'list';
OBJECT_TYPE: 'object';
VOID_TYPE: 'void';
INPUT: 'input';

ARROW: '->';
EQUALS: '=';
PLUS: '+';
MINUS: '-';
MULTIPLY: '*';
DIVIDE: '/';
MODULO: '%';
EQUAL: '==';
NOT_EQUAL: '!=';
LESS_THAN: '<';
GREATER_THAN: '>';
LESS_EQUAL: '<=';
GREATER_EQUAL: '>=';
LEFT_PAREN: '(';
RIGHT_PAREN: ')';
LEFT_BRACE: '{';
RIGHT_BRACE: '}';
LEFT_BRACKET: '[';
RIGHT_BRACKET: ']';
SEMICOLON: ';';
COLON: ':';
COMMA: ',';
DOT: '.';

INTEGER_LITERAL: [0-9]+;
DECIMAL_LITERAL: [0-9]+ '.' [0-9]+;
STRING_LITERAL: '"' (~["\r\n] | '\\' .)* '"';
IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;

SINGLE_LINE_COMMENT: '#' ~[\r\n]* -> skip;
MULTI_LINE_COMMENT: '/*' .*? '*/' -> skip;
WHITESPACE: [ \t\r\n]+ -> skip;

// PARSER RULES
program
    : (importStatement | topLevelDeclaration)* EOF
    ;

importStatement
    : importFlowScript
    | importJar
    ;

importFlowScript
    : IMPORT STRING_LITERAL (AS IDENTIFIER)?
    ;

importJar
    : IMPORT_JAR STRING_LITERAL AS IDENTIFIER
    ;

topLevelDeclaration
    : globalVariable
    | functionDeclaration
    | processDeclaration
    ;

globalVariable
    : IDENTIFIER EQUALS expression
    ;

functionDeclaration
    : FUNCTION IDENTIFIER LEFT_PAREN parameterList? RIGHT_PAREN (ARROW returnType)? functionBody
    ;

processDeclaration
    : PROCESS processName processBody
    ;

processName
    : IDENTIFIER
    ;

processBody
    : LEFT_BRACE startNode (taskNode | gatewayNode)* endNode+ RIGHT_BRACE
    ;

processElement
    : taskNode
    | gatewayNode
    ;

startNode
    : START ARROW nodeName
    ;

taskNode
    : TASK IDENTIFIER taskContent
    ;

taskContent
    : LEFT_BRACE ACTION COLON taskStatements RIGHT_BRACE
    ;

taskStatements
    : statement*
    ;

endNode
    : END IDENTIFIER
    ;

gatewayNode
    : GATEWAY IDENTIFIER exclusiveBranches
    | GATEWAY IDENTIFIER PARALLEL parallelBranches
    ;

exclusiveBranches
    : LEFT_BRACE whenBranch+ elseBranch? RIGHT_BRACE
    ;

parallelBranches
    : LEFT_BRACE branchStatement+ joinStatement RIGHT_BRACE
    ;

whenBranch
    : WHEN condition ARROW nodeName
    ;

elseBranch
    : ELSE ARROW nodeName
    ;

branchStatement
    : BRANCH ARROW nodeName
    ;

joinStatement
    : JOIN ARROW nodeName
    ;

gatewayStatement
    : GATEWAY IDENTIFIER exclusiveBranches
    ;

gotoStatement
    : GO_TO nodeName
    ;

nodeName
    : IDENTIFIER
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : IDENTIFIER COLON dataType
    ;

returnType
    : dataType
    ;

dataType
    : INTEGER_TYPE
    | DECIMAL_TYPE
    | BOOLEAN_TYPE
    | TEXT_TYPE
    | LIST_TYPE
    | OBJECT_TYPE
    | VOID_TYPE
    ;

functionBody
    : block
    ;

block
    : LEFT_BRACE statement* RIGHT_BRACE
    ;

statement
    : assignmentStatement
    | expressionStatement
    | ifStatement
    | whileStatement
    | forStatement
    | tryStatement
    | returnStatement
    | throwStatement
    | breakStatement
    | continueStatement
    | gotoStatement
    | gatewayStatement
    ;

assignmentStatement
    : leftHandSide EQUALS expression
    ;

expressionStatement
    : expression
    ;

ifStatement
    : IF condition block (ELSE_IF condition block)* (ELSE block)?
    ;

whileStatement
    : WHILE condition block
    ;

forStatement
    : forEachStatement
    | forRangeStatement
    ;

forEachStatement
    : FOR EACH IDENTIFIER IN expression block
    ;

forRangeStatement
    : FOR IDENTIFIER FROM expression TO expression (STEP expression)? block
    ;

tryStatement
    : TRY block catchClause
    ;

catchClause
    : CATCH LEFT_PAREN IDENTIFIER RIGHT_PAREN block
    ;

returnStatement
    : RETURN expression?
    ;

throwStatement
    : THROW expression
    ;

breakStatement
    : BREAK
    ;

continueStatement
    : CONTINUE
    ;

condition
    : expression
    ;

expression
    : assignmentExpression
    ;

assignmentExpression
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
    : additiveExpression ((LESS_THAN | GREATER_THAN | LESS_EQUAL | GREATER_EQUAL) additiveExpression)*
    ;

additiveExpression
    : multiplicativeExpression ((PLUS | MINUS) multiplicativeExpression)*
    ;

multiplicativeExpression
    : unaryExpression ((MULTIPLY | DIVIDE | MODULO) unaryExpression)*
    ;

unaryExpression
    : (NOT | MINUS) unaryExpression
    | postfixExpression
    ;

postfixExpression
    : primaryExpression (DOT IDENTIFIER | LEFT_BRACKET expression RIGHT_BRACKET | LEFT_PAREN argumentList? RIGHT_PAREN)*
    ;

primaryExpression
    : literal
    | IDENTIFIER
    | inputReference
    | LEFT_PAREN expression RIGHT_PAREN
    | listLiteral
    | objectLiteral
    ;

inputReference
    : INPUT
    ;

leftHandSide
    : IDENTIFIER (DOT IDENTIFIER | LEFT_BRACKET expression RIGHT_BRACKET)*
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

listLiteral
    : LEFT_BRACKET (expression (COMMA expression)*)? RIGHT_BRACKET
    ;

objectLiteral
    : LEFT_BRACE (objectProperty (COMMA objectProperty)*)? RIGHT_BRACE
    ;

objectProperty
    : propertyKey COLON expression
    ;

propertyKey
    : IDENTIFIER
    | STRING_LITERAL
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