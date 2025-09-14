# FlowScript Processes – BNF Overview (Team B)

This BNF corresponds to the implemented ANTLR grammar in `src/main/tlf/FlowScriptProcesses.g4` and captures the complete program structure with emphasis on processes.

## Program
- program := (importStmt | importJarStmt | globalVarDecl | functionDecl | processDecl)* EOF

## Imports & Globals
- importStmt := 'import' STRING ('as' IDENTIFIER)?
- importJarStmt := 'import_jar' STRING 'as' IDENTIFIER
- globalVarDecl := IDENTIFIER '=' expression

## Functions (Simplified)
- functionDecl := 'function' IDENTIFIER '(' parameterList? ')' returnType? '{' functionBody '}'
- parameterList := parameter (',' parameter)*
- parameter := IDENTIFIER ':' type
- returnType := '->' type
- type := 'integer' | 'decimal' | 'boolean' | 'text' | 'list' | 'object' | 'void'
- functionBody := functionStatement*
- functionStatement := returnStmt | exprStmt
- returnStmt := 'return' expression?
- exprStmt := expression

## Processes
- processDecl := 'process' IDENTIFIER '{' startDecl processBody endDecl+ '}'
- startDecl := 'start' '->' IDENTIFIER
- processBody := processElement*
- processElement := taskDecl | gatewayDecl
- endDecl := 'end' IDENTIFIER

### Task
- taskDecl := 'task' IDENTIFIER '{' 'action' ':' statementList '}'
- statementList := statement*
- statement := assignment | ifStatement | tryCatch | forEach | gatewayStmt | goToStmt | exprStmt
- assignment := lvalue '=' expression
- lvalue := IDENTIFIER (memberAccess | indexing)*
- goToStmt := 'go_to' IDENTIFIER

### Control Flow
- ifStatement := 'if' expression block ('else_if' expression block)* ('else' block)?
- tryCatch := 'try' block 'catch' '(' IDENTIFIER ')' block
- forEach := 'for' 'each' IDENTIFIER 'in' expression block
- block := '{' statementList '}'

### Gateways
- gatewayStmt := exclusiveGatewayStmt | parallelGatewayStmt
- gatewayDecl := exclusiveGatewayDecl | parallelGatewayDecl

- exclusiveGatewayStmt := 'gateway' IDENTIFIER '{' whenClause+ elseClause '}'
- exclusiveGatewayDecl := 'gateway' IDENTIFIER '{' whenClause+ elseClause '}'
- whenClause := 'when' expression '->' IDENTIFIER
- elseClause := 'else' '->' IDENTIFIER

- parallelGatewayStmt := 'gateway' IDENTIFIER 'parallel' '{' parallelBranch+ parallelJoin '}'
- parallelGatewayDecl := 'gateway' IDENTIFIER 'parallel' '{' parallelBranch+ parallelJoin '}'
- parallelBranch := 'branch' '->' IDENTIFIER
- parallelJoin := 'join' '->' IDENTIFIER

## Expressions
- expression := orExpr
- orExpr := andExpr ('or' andExpr)*
- andExpr := equalityExpr ('and' equalityExpr)*
- equalityExpr := relationalExpr (('==' | '!=') relationalExpr)*
- relationalExpr := additiveExpr (('<' | '>' | '<=' | '>=') additiveExpr)*
- additiveExpr := multiplicativeExpr (('+' | '-') multiplicativeExpr)*
- multiplicativeExpr := unaryExpr (('*' | '/' | '%') unaryExpr)*
- unaryExpr := ('not' | '-') unaryExpr | postfixExpr
- postfixExpr := primary postfix*
- postfix := functionCall | memberAccess | indexing
- functionCall := '(' argumentList? ')'
- argumentList := expression (',' expression)*
- memberAccess := '.' IDENTIFIER
- indexing := '[' expression ']'
- primary := literal | IDENTIFIER | '(' expression ')'
- literal := NUMBER | STRING | 'true' | 'false' | 'null' | listLiteral | objectLiteral
- listLiteral := '[' (expression (',' expression)*)? ']'
- objectLiteral := '{' (objectPair (',' objectPair)*)? '}'
- objectPair := IDENTIFIER ':' expression

