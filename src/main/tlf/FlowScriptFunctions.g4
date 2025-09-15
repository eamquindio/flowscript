grammar FlowScriptFunctions;

@header {
    package edu.eam.ingesoft.tlf;
}

// ---------------------- REGLA INICIAL ----------------------
functionProgram: functionDecl* EOF;

// ---------------------- DECLARACIÓN DE FUNCIONES ----------------------
functionDecl:
	FUNCTION ID '(' listaParametro? ')' ('->' tipo)? bloque;

listaParametro: parametro (',' parametro)*;

parametro: ID ':' tipo;

tipo:
	'integer'
	| 'decimal'
	| 'boolean'
	| 'text'
	| 'list'
	| 'object'
	| 'void';

// ---------------------- BLOQUES Y SENTENCIAS ----------------------
bloque: '{' instrucciones* '}';

instrucciones:
	instruccionIf
	| instruccionWhile
	| instruccionFor
	| tryCatchStmt
	| instruccionBreak
	| instruccionContinue
	| instruccionReturn
	| instruccionThrow
	| instruccionExpr;

instruccionIf:
	IF '('? expresion ')'? bloque (
		ELSE IF '('? expresion ')'? bloque
	)* (ELSE bloque)?;

instruccionWhile: WHILE '('? expresion ')'? bloque;

instruccionFor: instruccionForEach | instruccionForRange;

instruccionForEach: FOR EACH ID IN expresion bloque;

instruccionForRange:
	FOR ID FROM expresion TO expresion (STEP expresion)? bloque;

tryCatchStmt: TRY bloque (CATCH '(' ID (':' tipo)? ')' bloque)+;

instruccionBreak: BREAK;

instruccionContinue: CONTINUE;

instruccionReturn: RETURN expresion?;

instruccionThrow: THROW expresion;

instruccionExpr:
	expresion ';'?; // Permite que las expresiones terminen con punto y coma (o no)

// ---------------------- EXPRESIONES Y PRECEDENCIA ----------------------
expresion: expr;

expr: exprOr (('=' | '+=' | '-=' | '*=' | '/=') expr)?;

exprOr: exprAnd (OR exprAnd)*;

exprAnd: exprEquality (AND exprEquality)*;

exprEquality: exprRelational (('==' | '!=') exprRelational)*;

exprRelational: exprAdd (('<' | '<=' | '>' | '>=') exprAdd)*;

exprAdd: exprMul (('+' | '-') exprMul)*;

exprMul: exprUnary (('*' | '/' | '%') exprUnary)*;

exprUnary: (NOT | '-' | '+') exprUnary | exprPostFix;

exprPostFix: exprPrimary (postfixOp)*;

postfixOp: '(' argList? ')' | '.' ID | '[' expr ']';

argList: expr (',' expr)*;

exprPrimary: literal | ID | '(' expr ')';

// ---------------------- LITERALES ----------------------
literal:
	INT
	| DECIMAL
	| STRING
	| 'true'
	| 'false'
	| 'null'
	| listLiteral
	| objectLiteral;

listLiteral: '[' (expr (',' expr)*)? ']';

objectLiteral: '{' (pair (',' pair)*)? '}';

pair: (STRING | ID) ':' expr;

// ---------------------- TOKENS ----------------------
IF: 'if';
ELSE: 'else';
WHILE: 'while';
FOR: 'for';
IN: 'in';
TRY: 'try';
CATCH: 'catch';
RETURN: 'return';
THROW: 'throw';
BREAK: 'break';
CONTINUE: 'continue';
FUNCTION: 'function';

// Nuevos tokens para el léxico
EACH: 'each';
FROM: 'from';
TO: 'to';
STEP: 'step';
AND: 'and';
OR: 'or';
NOT: 'not';

ID: [a-zA-Z_][a-zA-Z0-9_]*;

INT: [0-9][0-9_]*;

DECIMAL:
	[0-9]+ '.' [0-9]* ([eE] [+-]? [0-9]+)?
	| '.' [0-9]+ ([eE] [+-]? [0-9]+)?;

STRING: '"' (ESC | ~["\\])* '"';

fragment ESC: '\\' ([btnfr"'\\] | 'u' HEX HEX HEX HEX);

fragment HEX: [0-9a-fA-F];

WS: [ \t\r\n]+ -> skip;

COMMENT_LINE: '//' ~[\r\n]* -> skip;

COMMENT_BLOCK: '/*' .*? '*/' -> skip;

COMMENT_POUND: '#' ~[\r\n]* -> skip;