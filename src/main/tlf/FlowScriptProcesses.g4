grammar FlowScriptProcesses;

@header {
    package edu.eam.ingesoft.tlf;
}

// ============================
// PARSER RULES PRINCIPALES
// ============================

program
    : imports globals declarations EOF
    ;

imports
    : importStmt*
    ;

globals
    : globalVar*
    ;

declarations
    : declaration*
    ;

importStmt
    : IMPORT STRING (importAlias)? ';'?
    | 'import_jar' STRING 'as' IDENTIFIER ';'?
    ;

importAlias
    : 'as' IDENTIFIER
    ;

globalVar
    : IDENTIFIER '=' expression ';'?
    ;

declaration
    : process
    | functionSimple
    ;

// ============================
// PROCESOS
// ============================

process
    : 'process' IDENTIFIER '{'
        start
        processElements
        end+
      '}'
    ;

processElements
    : (processElementWithoutStartOrEnd | COMMENT)*
    ;

processElementWithoutStartOrEnd
    : task
    | gateway
    | functionSimple
    ;

start
    : 'start' '->' IDENTIFIER ';'?
    ;

end
    : 'end' IDENTIFIER ';'?
    ;

gateway
    : 'gateway' IDENTIFIER '{' exclusiveBranches '}'
    | 'gateway' IDENTIFIER 'parallel' '{' parallelBranches '}'
    ;

exclusiveBranches
    : whenBranch+ elseBranch
    ;

whenBranch
    : 'when' expression '->' IDENTIFIER ';'?
    ;

elseBranch
    : 'else' '->' IDENTIFIER ';'?
    ;

parallelBranches
    : branch+ join
    ;

branch
    : 'branch' '->' IDENTIFIER ';'?
    ;

join
    : 'join' '->' IDENTIFIER ';'?
    ;

// ============================
// TASKS & FUNCIONES
// ============================

task
    : 'task' IDENTIFIER '{' 'action:' taskStatement+ '}'
    ;

functionSimple
    : 'function' IDENTIFIER '(' parameterList? ')' '->' type blockFunc
    ;

parameterList
    : parameter (',' parameter)*
    ;

parameter
    : IDENTIFIER ':' type
    ;

type
    : 'integer' | 'decimal' | 'boolean' | 'text'
    | 'list' | 'object' | 'void'
    ;

// ============================
// BLOQUES
// ============================

blockTask
    : '{' taskStatement* '}'
    ;

blockFunc
    : '{' blockFuncBody* '}'
    ;

blockFuncBody
    : statement
    | COMMENT
    ;

// ============================
// STATEMENTS
// ============================

taskStatement
    : ifStmtTask
    | forEachTask
    | tryCatchTask
    | gateway
    | assign ';'?
    | gotoStmt ';'?
    | returnStmt ';'?
    | functionCall ';'?
    | COMMENT
    ;

statement
    : assign ';'?
    | returnStmt ';'?
    | ifStmtFunc
    | forEachFunc
    | tryCatchFunc
    | functionCall ';'?
    ;

// ============================
// CONTROL STRUCTURES
// ============================

ifStmtTask
    : 'if'  expression  blockTask (elseBlockTask)?
    ;

elseBlockTask
    : 'else' blockTask
    ;

ifStmtFunc
    : 'if'  expression  blockFunc (elseBlockFunc)?
    ;

elseBlockFunc
    : 'else' blockFunc
    ;

forEachTask
    : 'for' 'each' IDENTIFIER 'in' expression blockTask
    ;

forEachFunc
    : 'for' 'each' IDENTIFIER 'in' expression blockFunc
    ;

tryCatchTask
    : 'try' blockTask 'catch' '(' IDENTIFIER ')' blockTask
    ;

tryCatchFunc
    : 'try' blockFunc 'catch' '(' IDENTIFIER ')' blockFunc
    ;

// ============================
// EXPRESSIONS
// ============================

assign
    : primaryExpr ( '[' expression ']' | '.' IDENTIFIER arguments? )* '=' expression
    ;

functionCall
    : IDENTIFIER '(' argumentList? ')'
    ;

gotoStmt
    : ('goto' | 'go_to') IDENTIFIER
    ;

returnStmt
    : 'return' expression?
    ;

expression
    : orExpr
    ;

orExpr
    : orExpr 'or' andExpr
    | andExpr
    ;

andExpr
    : andExpr 'and' relationalExpr
    | relationalExpr
    ;

relationalExpr
    : relationalExpr ( '==' | '!=' | '<' | '>' | '<=' | '>=') addExpr
    | addExpr
    ;

addExpr
    : addExpr ( '+' | '-' ) mulExpr
    | mulExpr
    ;

mulExpr
    : mulExpr ( '*' | '/' | '%' ) unaryExpr
    | unaryExpr
    ;

unaryExpr
    : ( 'not' | '-' ) unaryExpr
    | primaryExpr
    ;

// ============================
// PRIMARY EXPRESSIONS
// ============================


primaryExpr
    : '(' expression ')'
    | functionCall
    | ('input' '.')? IDENTIFIER arguments? memberAccess*
    | literal
    ;

memberAccess
    : '.' IDENTIFIER arguments?
    | '[' expression ']'
    ;

arguments
    : '(' argumentList? ')'
    ;

argumentList
    : expression (',' expression)*
    ;

literal
    : INTEGER
    | DECIMAL
    | BOOLEAN
    | STRING
    | 'null'
    | list
    | object
    ;

list
    : '[' expressionList? ']'
    ;

expressionList
    : expression (',' expression)*
    ;

object
    : '{' objectFieldList? '}'
    ;

objectFieldList
    : objectField (',' objectField)*
    ;

objectField
    : IDENTIFIER ':' expression
    ;

// ============================
// LEXER RULES
// ============================

COMMENT: '//' ~[\r\n]* -> skip;
MULTILINE_COMMENT: '/*' .*? '*/' -> skip;
HASH_COMMENT: '#' ~[\r\n]* -> skip;
IMPORT: 'import';
IDENTIFIER : [a-zA-Z_] [a-zA-Z0-9_]*;
STRING: '"' (~["\r\n])* '"';
INTEGER: [0-9]+;
DECIMAL: [0-9]+ '.' [0-9]+;
BOOLEAN: 'true' | 'false';
WS: [ \t\r\n]+ -> skip;
