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
IMPORT          : 'import';
IMPORT_JAR      : 'import_jar';
AS              : 'as';
PROCESS         : 'process' | 'proceso';
FUNCTION        : 'function' | 'funcion';

// Palabras clave de nodos de proceso
START           : 'start' | 'inicio';
TASK            : 'task' | 'tarea';
END             : 'end' | 'fin';
GATEWAY         : 'gateway' | 'compuerta';
ACTION          : 'action' | 'accion';

// Palabras clave de gateway
WHEN            : 'when' | 'cuando';
ELSE            : 'else' | 'sino';
PARALLEL        : 'parallel' | 'paralelo';
BRANCH          : 'branch' | 'rama';
JOIN            : 'join' | 'unir';

// Palabras clave de control de flujo
GO_TO           : 'go_to' | 'ir_a' | 'goto';
IF              : 'if' | 'si';
FOR             : 'for' | 'para';
EACH            : 'each' | 'cada';
IN              : 'in' | 'en';
FROM            : 'from' | 'desde';
TO              : 'to' | 'hasta';
WHILE           : 'while' | 'mientras';
RETURN          : 'return' | 'retornar';
TRY             : 'try' | 'intentar';
CATCH           : 'catch' | 'capturar';
THROW           : 'throw' | 'lanzar';

// Palabras clave de tipos
VOID            : 'void' | 'vacio';
INTEGER         : 'integer' | 'entero';
DECIMAL         : 'decimal';
TEXT            : 'text' | 'texto';
BOOLEAN         : 'boolean' | 'booleano';
LIST            : 'list' | 'lista';
OBJECT          : 'object' | 'objeto';

// Palabras clave especiales
INPUT           : 'input' | 'entrada';
NULL            : 'null' | 'nulo';
TRUE            : 'true' | 'verdadero';
FALSE           : 'false' | 'falso';
AND             : 'and' | 'y';
OR              : 'or' | 'o';
NOT             : 'not' | 'no';

// Operadores
ARROW           : '->';
PLUS            : '+';
MINUS           : '-';
MULTIPLY        : '*';
DIVIDE          : '/';
MODULO          : '%';
ASSIGN          : '=';
EQ              : '==';
NEQ             : '!=';
LT              : '<';
GT              : '>';
LTE             : '<=';
GTE             : '>=';
DOT             : '.';
COMMA           : ',';
COLON           : ':';
SEMICOLON       : ';';

// Delimitadores
LPAREN          : '(';
RPAREN          : ')';
LBRACE          : '{';
RBRACE          : '}';
LBRACKET        : '[';
RBRACKET        : ']';

// Literales
INTEGER_LITERAL : [0-9]+;
DECIMAL_LITERAL : [0-9]+ '.' [0-9]+;
STRING_LITERAL  : '"' (~["\r\n] | '\\' .)* '"';
IDENTIFIER      : [a-zA-Z_][a-zA-Z0-9_]*;

// Comentarios y espacios en blanco
SINGLE_LINE_COMMENT : '#' ~[\r\n]* -> skip;
MULTI_LINE_COMMENT  : '/*' .*? '*/' -> skip;
WHITESPACE          : [ \t\r\n]+ -> skip;

// ============================
// PARSER RULES
// ============================

// ============================
// ESTRUCTURA DEL PROGRAMA
// ============================

program
    : programElement* EOF
    ;

programElement
    : importStatement
    | globalVariableDeclaration
    | functionDeclaration
    | processDeclaration
    ;

// ============================
// IMPORTS
// ============================

importStatement
    : IMPORT STRING_LITERAL (AS IDENTIFIER)?
    | IMPORT_JAR STRING_LITERAL (AS IDENTIFIER)?
    ;

// ============================
// VARIABLES GLOBALES
// ============================

globalVariableDeclaration
    : IDENTIFIER ASSIGN expression
    ;

// ============================
// DECLARACIÓN DE FUNCIONES
// ============================

functionDeclaration
    : FUNCTION IDENTIFIER LPAREN parameterList? RPAREN ARROW returnType functionBody
    ;

parameterList
    : parameter (COMMA parameter)*
    ;

parameter
    : IDENTIFIER COLON type
    ;

returnType
    : type
    | VOID
    ;

type
    : INTEGER
    | DECIMAL
    | TEXT
    | BOOLEAN
    | LIST
    | OBJECT
    ;

functionBody
    : LBRACE statement* RBRACE
    ;

// ============================
// DECLARACIÓN DE PROCESOS
// ============================

processDeclaration
    : PROCESS IDENTIFIER LBRACE startElement processElement+ endElement+ RBRACE
    ;

processElement
    : taskElement
    | gatewayElement
    | endElement
    ;

// ============================
// NODOS DEL PROCESO
// ============================

startElement
    : START ARROW IDENTIFIER
    ;

taskElement
    : TASK IDENTIFIER LBRACE ACTION COLON statement* RBRACE
    ;

gatewayElement
    : GATEWAY IDENTIFIER PARALLEL LBRACE parallelGatewayBody RBRACE
    | GATEWAY IDENTIFIER LBRACE exclusiveGatewayBody RBRACE
    ;

gatewayBody
    : exclusiveGatewayBody
    | parallelGatewayBody
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

endElement
    : END IDENTIFIER
    ;

// ============================
// STATEMENTS
// ============================

statement
    : variableAssignment
    | gotoStatement
    | ifStatement
    | forEachStatement
    | forRangeStatement
    | whileStatement
    | returnStatement
    | tryStatement
    | throwStatement
    | expressionStatement
    | inlineGateway
    ;

variableAssignment
    : assignableExpression ASSIGN expression
    ;

assignableExpression
    : IDENTIFIER
    | memberAccess
    | IDENTIFIER LBRACKET expression RBRACKET
    | memberAccess LBRACKET expression RBRACKET
    ;

gotoStatement
    : GO_TO IDENTIFIER
    ;

ifStatement
    : IF expression LBRACE statement* RBRACE (ELSE LBRACE statement* RBRACE)?
    ;

forEachStatement
    : FOR EACH IDENTIFIER IN expression LBRACE statement* RBRACE
    ;

forRangeStatement
    : FOR IDENTIFIER FROM expression TO expression LBRACE statement* RBRACE
    ;

whileStatement
    : WHILE expression LBRACE statement* RBRACE
    ;

returnStatement
    : RETURN expression?
    ;

tryStatement
    : TRY LBRACE statement* RBRACE CATCH LPAREN IDENTIFIER RPAREN LBRACE statement* RBRACE
    ;

throwStatement
    : THROW expression
    ;

expressionStatement
    : expression
    ;

inlineGateway
    : GATEWAY IDENTIFIER PARALLEL LBRACE parallelGatewayBody RBRACE
    | GATEWAY IDENTIFIER LBRACE exclusiveGatewayBody RBRACE
    ;

// ============================
// EXPRESIONES
// ============================

expression
    : primary                                                   # PrimaryExpression
    | expression DOT IDENTIFIER                                 # MemberAccessExpression
    | expression LBRACKET expression RBRACKET                   # IndexAccessExpression
    | expression LPAREN argumentList? RPAREN                    # FunctionCallExpression
    | unaryOp nonUnaryPrimary                                   # UnaryExpression
    | expression (MULTIPLY | DIVIDE | MODULO) expression        # MultiplicativeExpression
    | expression (PLUS | MINUS) expression                      # AdditiveExpression
    | expression (LT | GT | LTE | GTE) expression               # RelationalExpression
    | expression (EQ | NEQ) expression                          # EqualityExpression
    | expression AND expression                                 # LogicalAndExpression
    | expression OR expression                                  # LogicalOrExpression
    ;

unaryOp
    : NOT
    | PLUS
    | MINUS
    ;

nonUnaryPrimary
    : INTEGER_LITERAL
    | DECIMAL_LITERAL
    | STRING_LITERAL
    | TRUE
    | FALSE
    | NULL
    | IDENTIFIER
    | INPUT
    | listLiteral
    | objectLiteral
    ;

atomicExpression
    : INTEGER_LITERAL
    | DECIMAL_LITERAL
    | STRING_LITERAL
    | TRUE
    | FALSE
    | NULL
    | IDENTIFIER
    | INPUT
    | listLiteral
    | objectLiteral
    | IDENTIFIER LBRACKET expression RBRACKET
    | memberAccess LBRACKET expression RBRACKET
    | IDENTIFIER LPAREN argumentList? RPAREN
    | memberAccess LPAREN argumentList? RPAREN
    ;

primary
    : INTEGER_LITERAL
    | DECIMAL_LITERAL
    | STRING_LITERAL
    | TRUE
    | FALSE
    | NULL
    | IDENTIFIER
    | INPUT
    | listLiteral
    | objectLiteral
    | LPAREN expression RPAREN
    ;

memberAccess
    : (IDENTIFIER | INPUT) (DOT IDENTIFIER)+
    ;

argumentList
    : expression (COMMA expression)*
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
 *                 go_to ValidationError
 *             }
 *             
 *             if not validate_email(input.email) {
 *                 error_message = "Invalid email"
 *                 go_to ValidationError
 *             }
 *             
 *             go_to CheckInventory
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
 *                     go_to PrepareShipping
 *                 } else {
 *                     go_to PaymentFailed
 *                 }
 *             } catch (error) {
 *                 log_error(error)
 *                 go_to PaymentFailed
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
 *             go_to InventoryUpdated
 *     }
 *     
 *     task NotifyCustomer {
 *         action:
 *             utils.EmailService.send(
 *                 input.email,
 *                 "Order Confirmation",
 *                 "Your order has been confirmed and is being prepared."
 *             )
 *             go_to CustomerNotified
 *     }
 *     
 *     task CreateShipment {
 *         action:
 *             shipment = http.post("https://api.shipping.com/create", {
 *                 address: input.shipping_address,
 *                 items: input.items
 *             })
 *             tracking_number = shipment.tracking_number
 *             go_to ShipmentCreated
 *     }
 *     
 *     task FinalizeOrder {
 *         action:
 *             db.execute(
 *                 "UPDATE orders SET tracking_number = ?, status = ? WHERE id = ?",
 *                 [tracking_number, "shipped", order_id]
 *             )
 *             go_to Success
 *     }
 *     
 *     task ValidationError {
 *         action:
 *             response = { success: false, message: error_message }
 *             go_to Error
 *     }
 *     
 *     task InsufficientStock {
 *         action:
 *             response = { success: false, message: "Insufficient stock" }
 *             go_to Error
 *     }
 *     
 *     task PaymentFailed {
 *         action:
 *             response = { success: false, message: "Payment failed" }
 *             go_to Error
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
 *             go_to WaitingApproval
 *     }
 *     
 *     task RequireManagerApproval {
 *         action:
 *             send_approval_request("manager@company.com", input)
 *             go_to WaitingApproval
 *     }
 *     
 *     task RequireSupervisorApproval {
 *         action:
 *             send_approval_request("supervisor@company.com", input)
 *             go_to WaitingApproval
 *     }
 *     
 *     task AutoApprove {
 *         action:
 *             update_status(input.id, "approved")
 *             go_to Approved
 *     }
 *     
 *     task WaitingApproval {
 *         action:
 *             # En un caso real, esto esperaría una respuesta asíncrona
 *             approval_result = wait_for_approval(input.id)
 *             if approval_result.approved {
 *                 go_to Approved
 *             } else {
 *                 go_to Rejected
 *             }
 *     }
 *     
 *     end Approved
 *     end Rejected
 * }
 */
