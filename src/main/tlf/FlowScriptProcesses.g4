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
PROCESS: 'process';
FUNCTION: 'function';
IMPORT: 'import';
IMPORT_JAR: 'import_jar';
AS: 'as';

// Palabras clave de procesos
START: 'start';
END: 'end';
TASK: 'task';
GATEWAY: 'gateway';
GO_TO: 'go_to';
WHEN: 'when';
BRANCH: 'branch';
JOIN: 'join';
ACTION: 'action';
PARALLEL: 'parallel';
INPUT: 'input';

// Palabras clave de funciones (para declaraciones)
RETURN: 'return';
VOID: 'void';

// Tipos de datos
INTEGER_TYPE: 'integer';
DECIMAL_TYPE: 'decimal';
BOOLEAN_TYPE: 'boolean';
TEXT_TYPE: 'text';
LIST_TYPE: 'list';
OBJECT_TYPE: 'object';

// Control de flujo
IF: 'if';
ELSE_IF: 'else_if';
ELSE: 'else';
WHILE: 'while';
FOR: 'for';
EACH: 'each';
IN: 'in';
FROM: 'from';
TO: 'to';
STEP: 'step';
BREAK: 'break';
CONTINUE: 'continue';
TRY: 'try';
CATCH: 'catch';
THROW: 'throw';

// Operadores lógicos
AND: 'and';
OR: 'or';
NOT: 'not';

// Literales booleanos y null
TRUE: 'true';
FALSE: 'false';
NULL: 'null';

// Operadores aritméticos
PLUS: '+';
MINUS: '-';
MULTIPLY: '*';
DIVIDE: '/';
MODULO: '%';

// Operadores de comparación
LESS_THAN: '<';
GREATER_THAN: '>';
LESS_EQUAL: '<=';
GREATER_EQUAL: '>=';
EQUAL: '==';
NOT_EQUAL: '!=';

// Operadores de asignación y acceso
ASSIGN: '=';
DOT: '.';
ARROW: '->';

// Delimitadores
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
LBRACKET: '[';
RBRACKET: ']';
COMMA: ',';
SEMICOLON: ';';
COLON: ':';

// Literales numéricos
INTEGER_LITERAL
    : DIGIT+ ('_' DIGIT+)*
    | '0'
    ;

DECIMAL_LITERAL
    : DIGIT+ '.' DIGIT+ EXPONENT?
    | DIGIT+ EXPONENT
    | '.' DIGIT+ EXPONENT?
    ;

// Literal de texto
STRING_LITERAL
    : '"' STRING_CONTENT* '"'
    ;

// Identificadores
IDENTIFIER
    : LETTER (LETTER | DIGIT | '_')*
    | '_' (LETTER | DIGIT | '_')+
    ;

// Comentarios
SINGLE_LINE_COMMENT: '#' ~[\r\n]* -> skip;
MULTI_LINE_COMMENT: '/*' .*? '*/' -> skip;

// Espacios en blanco
WS: [ \t\r\n]+ -> skip;

// Fragmentos
fragment DIGIT: [0-9];
fragment LETTER: [a-zA-Z];
fragment EXPONENT: [eE] [+-]? DIGIT+;
fragment STRING_CONTENT: ~["\\\r\n] | '\\' ESCAPE_SEQUENCE;
fragment ESCAPE_SEQUENCE: [btnfr"\\] | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT;
fragment HEX_DIGIT: [0-9a-fA-F];

// ============================
// PARSER RULES
// ============================

// ============================
// ESTRUCTURA DEL PROGRAMA
// ============================

program
    : importStatement* topLevelDeclaration* EOF
    ;

importStatement
    : importFlowScript
    | importJar
    ;

importFlowScript
    : IMPORT STRING_LITERAL (AS IDENTIFIER)? SEMICOLON?
    ;

importJar
    : IMPORT_JAR STRING_LITERAL AS IDENTIFIER SEMICOLON?
    ;

topLevelDeclaration
    : functionDeclaration
    | processDeclaration
    | globalVariable
    ;

globalVariable
    : IDENTIFIER ASSIGN expression SEMICOLON?
    ;

// ============================
// DECLARACIÓN DE FUNCIONES (simplificada)
// ============================

functionDeclaration
    : FUNCTION IDENTIFIER LPAREN parameterList? RPAREN returnType? functionBody
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : IDENTIFIER COLON dataType
    ;

returnType
    : ARROW dataType
    ;

dataType
    : INTEGER_TYPE | DECIMAL_TYPE | BOOLEAN_TYPE
    | TEXT_TYPE | LIST_TYPE | OBJECT_TYPE | VOID
    ;

functionBody
    : LBRACE statement* RBRACE
    ;

// ============================
// DECLARACIÓN DE PROCESOS
// ============================

processDeclaration
    : PROCESS processName LBRACE startNode processBody+ endNode+ RBRACE
    ;

processName
    : IDENTIFIER
    ;

processBody
    : taskNode
    | gatewayNode
    ;

processElement
    : startNode
    | taskNode
    | endNode
    | gatewayNode
    ;

// ============================
// NODOS DEL PROCESO
// ============================

startNode
    : START ARROW nodeName SEMICOLON?
    ;

taskNode
    : TASK nodeName LBRACE taskContent RBRACE
    ;

taskContent
    : ACTION COLON taskStatements
    ;

taskStatements
    : taskStatement+
    ;

taskStatement
    : statement
    ;

gotoStatement
    : GO_TO IDENTIFIER SEMICOLON?
    ;

gatewayStatement
    : GATEWAY IDENTIFIER PARALLEL LBRACE parallelBranches RBRACE
    | GATEWAY IDENTIFIER LBRACE exclusiveBranches RBRACE
    ;

endNode
    : END nodeName SEMICOLON?
    ;

gatewayNode
    : GATEWAY nodeName PARALLEL LBRACE parallelBranches RBRACE
    | GATEWAY nodeName LBRACE exclusiveBranches RBRACE
    ;

exclusiveBranches
    : whenBranch+ elseBranch?
    ;

whenBranch
    : WHEN condition ARROW nodeName SEMICOLON?
    ;

elseBranch
    : ELSE ARROW nodeName SEMICOLON?
    ;

parallelBranches
    : branchStatement+ joinStatement
    ;

branchStatement
    : BRANCH ARROW nodeName SEMICOLON?
    ;

joinStatement
    : JOIN ARROW nodeName SEMICOLON?
    ;

nodeName
    : IDENTIFIER
    ;

// ============================
// STATEMENTS
// ============================

statement
    : assignmentStatement
    | ifStatement
    | whileStatement
    | forStatement
    | tryStatement
    | throwStatement
    | returnStatement
    | breakStatement
    | continueStatement
    | gotoStatement
    | gatewayStatement
    | expressionStatement
    | block
    | SEMICOLON
    ;

assignmentStatement
    : leftHandSide ASSIGN expression SEMICOLON?
    ;

leftHandSide
    : IDENTIFIER
    | postfixExpression
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
    : TRY block catchClause+
    ;

catchClause
    : CATCH LPAREN IDENTIFIER RPAREN block
    ;

throwStatement
    : THROW expression SEMICOLON?
    ;

returnStatement
    : RETURN expression? SEMICOLON?
    ;

breakStatement
    : BREAK SEMICOLON?
    ;

continueStatement
    : CONTINUE SEMICOLON?
    ;

expressionStatement
    : expression SEMICOLON?
    ;

block
    : LBRACE statement* RBRACE
    ;

condition
    : expression
    | LPAREN expression RPAREN
    ;

// ============================
// EXPRESIONES
// ============================

expression
    : assignmentExpression
    ;

assignmentExpression
    : logicalOrExpression (ASSIGN assignmentExpression)?
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
    : primaryExpression (
        DOT IDENTIFIER                     // Member access
        | LBRACKET expression RBRACKET     // Array indexing
        | LPAREN argumentList? RPAREN      // Function call
      )*
    ;

argumentList
    : expression (COMMA expression)*
    ;

primaryExpression
    : literal
    | IDENTIFIER
    | inputReference
    | LPAREN expression RPAREN
    | listLiteral
    | objectLiteral
    ;

inputReference
    : INPUT (DOT IDENTIFIER)*
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
    : LBRACKET (expression (COMMA expression)*)? RBRACKET
    ;

objectLiteral
    : LBRACE (objectProperty (COMMA objectProperty)*)? RBRACE
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