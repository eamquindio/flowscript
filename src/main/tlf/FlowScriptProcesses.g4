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
 * - Control de flujo con go_to
 * - Variables globales y contexto del proceso
 */

// ============================
// LEXER RULES (TOKENS)
// ============================

// Palabras clave de estructura y procesos
IMPORT:        'import';
IMPORT_JAR:    'import_jar';
AS:            'as';
PROCESS:       'process';
START:         'start';
END:           'end';
TASK:          'task';
GATEWAY:       'gateway';
PARALLEL:      'parallel';
BRANCH:        'branch';
JOIN:          'join';
WHEN:          'when';
ELSE:          'else';
GOTO:          'go_to';
ACTION:        'action';

// Palabras clave de funciones (simplificadas)
FUNCTION:      'function';
RETURN:        'return';

// Palabras clave de control y bucles
IF:            'if';
ELSE_IF:       'else_if';
WHILE:         'while';
FOR:           'for';
EACH:          'each';
IN:            'in';
FROM:          'from';
TO:            'to';
STEP:          'step';
BREAK:         'break';
CONTINUE:      'continue';
TRY:           'try';
CATCH:         'catch';
THROW:         'throw';

// Tipos y literales booleanos/null
INTEGER_T:     'integer';
DECIMAL_T:     'decimal';
BOOLEAN_T:     'boolean';
TEXT_T:        'text';
LIST_T:        'list';
OBJECT_T:      'object';
VOID_T:        'void';
NULL:          'null';
TRUE:          'true';
FALSE:         'false';

// Operadores lógicos como palabras
AND:           'and';
OR:            'or';
NOT:           'not';

// Delimitadores y operadores
ARROW:         '->';
LPAREN:        '(';
RPAREN:        ')';
LBRACE:        '{';
RBRACE:        '}';
LBRACK:        '[';
RBRACK:        ']';
COMMA:         ',';
COLON:         ':';
DOT:           '.';
ASSIGN:        '=';
LE:            '<=';
GE:            '>=';
EQ:            '==';
NEQ:           '!=';
LT:            '<';
GT:            '>';
PLUS:          '+';
MINUS:         '-';
MUL:           '*';
DIV:           '/';
MOD:           '%';

// Literales
DECIMAL
    : DIGIT+ '.' DIGIT+ ( [eE] [+-]? DIGIT+ )?
    | DIGIT+ [eE] [+-]? DIGIT+
    ;

INTEGER
    : DIGIT+ ('_' DIGIT+)*
    ;

STRING
    : '"' ( ESC_SEQ | ~["\\\r\n] )* '"'
    ;

fragment ESC_SEQ
    : '\\' ["\\/bfnrt]
    | '\\' 'u' HEX HEX HEX HEX
    ;

fragment HEX: [0-9a-fA-F];
fragment DIGIT: [0-9];

IDENTIFIER
    : [a-zA-Z_] [a-zA-Z0-9_]*
    ;

// Separadores
NEWLINE: ('\r'? '\n')+;
WS: [\t \f]+ -> skip;
LINE_COMMENT: '#' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;

// ============================
// PARSER RULES
// ============================

// Regla para consumir nuevas líneas opcionales
nl
    : NEWLINE+
    ;

// ============================
// ESTRUCTURA DEL PROGRAMA
// ============================

program
    : ( nl
      | importStmt nl?
      | globalVarDecl nl?
      | functionDecl nl?
      | processDecl nl?
      )* EOF
    ;

// Imports
importStmt
    : IMPORT STRING (AS IDENTIFIER)?
    | IMPORT_JAR STRING AS IDENTIFIER
    ;

// Variables globales
globalVarDecl
    : IDENTIFIER ASSIGN expression
    ;

// ============================
// DECLARACIÓN BÁSICA DE FUNCIONES (simplificado para Team B)
// ============================

functionDecl
    : FUNCTION IDENTIFIER LPAREN paramList? RPAREN (ARROW type)? block
    ;

paramList
    : param (COMMA param)*
    ;

param
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

// ============================
// PROCESOS
// ============================

processDecl
    : PROCESS IDENTIFIER LBRACE nl?
        startNode
        ( processMidElement )*
        endNode+
      RBRACE
    ;

processMidElement
    : taskNode
    | gatewayParallelNode
    | nl
    ;

startNode
    : START ARROW IDENTIFIER nl
    ;

endNode
    : END IDENTIFIER nl
    ;

taskNode
    : TASK IDENTIFIER LBRACE nl? ACTION COLON nl? statementList? RBRACE nl?
    ;

gatewayParallelNode
    : GATEWAY IDENTIFIER PARALLEL LBRACE nl? parallelBranch+ joinClause RBRACE nl?
    ;

whenClause
    : WHEN expression ARROW IDENTIFIER nl
    ;

elseClause
    : ELSE ARROW IDENTIFIER nl
    ;

parallelBranch
    : BRANCH ARROW IDENTIFIER nl
    ;

joinClause
    : JOIN ARROW IDENTIFIER nl
    ;

// ============================
// BLOQUES Y SENTENCIAS
// ============================

block
    : LBRACE nl? statementList? RBRACE
    ;

statementList
    : statement (nl+ statement)* nl*
    ;

statement
    : assignment
    | ifStatement
    | whileStatement
    | forEachStatement
    | forRangeStatement
    | breakStatement
    | continueStatement
    | returnStatement
    | tryCatchStatement
    | throwStatement
    | gotoStatement
    | gatewayStatement
    | exprStatement
    ;

assignment
    : lvalue ASSIGN expression
    ;

lvalue
    : IDENTIFIER ( DOT IDENTIFIER | LBRACK expression RBRACK )*
    ;

ifStatement
    : IF expression block (ELSE_IF expression block)* (ELSE block)?
    ;

whileStatement
    : WHILE expression block
    ;

forEachStatement
    : FOR EACH IDENTIFIER IN expression block
    ;

forRangeStatement
    : FOR IDENTIFIER FROM expression TO expression (STEP expression)? block
    ;

breakStatement
    : BREAK
    ;

continueStatement
    : CONTINUE
    ;

returnStatement
    : RETURN expression?
    ;

tryCatchStatement
    : TRY block CATCH LPAREN IDENTIFIER RPAREN block
    ;

throwStatement
    : THROW expression
    ;

gotoStatement
    : GOTO IDENTIFIER
    ;

exprStatement
    : expression
    ;

// Gateway exclusivo como sentencia dentro de acciones de tareas
gatewayStatement
    : GATEWAY IDENTIFIER LBRACE nl? whenClause+ elseClause? RBRACE
    ;

// ============================
// EXPRESIONES
// ============================

expression
    : logicOr
    ;

logicOr
    : logicAnd (OR logicAnd)*
    ;

logicAnd
    : equality (AND equality)*
    ;

equality
    : comparison ((EQ | NEQ) comparison)*
    ;

comparison
    : addition ((LT | GT | LE | GE) addition)*
    ;

addition
    : multiplication ((PLUS | MINUS) multiplication)*
    ;

multiplication
    : unary ((MUL | DIV | MOD) unary)*
    ;

unary
    : (NOT | MINUS) unary
    | postfix
    ;

postfix
    : primary ( callSuffix | memberSuffix | indexSuffix )*
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

primary
    : LPAREN expression RPAREN
    | listLiteral
    | objectLiteral
    | literal
    | IDENTIFIER
    ;

literal
    : DECIMAL
    | INTEGER
    | STRING
    | TRUE
    | FALSE
    | NULL
    ;

listLiteral
    : LBRACK (nl? expression (nl? COMMA nl? expression)* nl?)? RBRACK
    ;

objectLiteral
    : LBRACE (nl? objectPair (nl? COMMA nl? objectPair)* nl?)? RBRACE
    ;

objectPair
    : IDENTIFIER COLON nl? expression
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
 */
