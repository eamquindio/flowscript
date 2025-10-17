grammar FlowScriptProcesses;

@header {
    package edu.eam.ingesoft.tlf;
}

program
    : elementoPrograma* EOF
    ;

elementoPrograma
    : importeSimple
    | importeJar
    | variableGlobal
    | funcionCorta
    | procesoPrincipal
    ;

importeSimple
    : IMPORT STRING (AS IDENT)?
    ;

importeJar
    : IMPORT_JAR STRING AS IDENT
    ;

variableGlobal
    : IDENT '=' expresion
    ;

funcionCorta
    : FUNCTION IDENT '(' listaParametros? ')' (ARROW tipoDato)?
      bloqueCodigo
    ;

listaParametros
    : parametro (',' parametro)*
    ;

parametro
    : IDENT ':' tipoDato
    ;

tipoDato
    : INTEGER
    | DECIMAL
    | BOOLEAN
    | TEXT
    | LIST
    | OBJECT
    | VOID
    ;

procesoPrincipal
    : PROCESS IDENT '{'
      inicioProceso
      elementoProceso*
      finalProceso+
      '}'
    ;

inicioProceso
    : START ARROW IDENT
    ;

finalProceso
    : END IDENT
    ;

elementoProceso
    : tareaProceso
    | gatewayExclusivo
    | gatewayParalelo
    ;

tareaProceso
    : TASK IDENT '{' ACTION ':' cuerpoAccion '}'
    ;

cuerpoAccion
    : sentencia+
    ;

gatewayExclusivo
    : GATEWAY IDENT '{'
      ramaWhen+
      ramaElse?
      '}'
    ;

ramaWhen
    : WHEN expresion ARROW IDENT
    ;

ramaElse
    : ELSE ARROW IDENT
    ;

gatewayParalelo
    : GATEWAY IDENT PARALLEL '{'
      ramaBranch+
      ramaJoin
      '}'
    ;

ramaBranch
    : BRANCH ARROW IDENT
    ;

ramaJoin
    : JOIN ARROW IDENT
    ;

sentencia
    : sentenciaIf
    | sentenciaForEach
    | sentenciaTryCatch
    | sentenciaGoTo
    | sentenciaReturn
    | gatewayExclusivo
    | gatewayParalelo
    | expresion
    ;

sentenciaIf
    : IF expresion bloqueCodigo (ELSE sentenciaIf | ELSE bloqueCodigo)?
    ;

sentenciaForEach
    : FOR EACH IDENT IN expresion bloqueCodigo
    ;

sentenciaTryCatch
    : TRY bloqueCodigo CATCH '(' IDENT ')' bloqueCodigo
    ;

sentenciaGoTo
    : GO_TO IDENT
    ;

sentenciaReturn
    : RETURN expresion?
    ;

bloqueCodigo
    : '{' sentencia* '}'
    ;

expresion
    : asignacion
    ;

asignacion
    : accesoPostfijo '=' asignacion
    | logicoOr
    ;

logicoOr
    : logicoAnd (OR logicoAnd)*
    ;

logicoAnd
    : igualdad (AND igualdad)*
    ;

igualdad
    : comparacion ((IGUALDAD | DIFERENTE) comparacion)*
    ;

comparacion
    : suma ((MAYOR | MENOR | MAYORIGUAL | MENORIGUAL) suma)*
    ;

suma
    : producto ((MAS | MENOS) producto)*
    ;

producto
    : unario ((MULT | DIV | MOD) unario)*
    ;

unario
    : NOT unario
    | MENOS unario
    | accesoPostfijo
    ;

accesoPostfijo
    : primario (accesoExtra)*
    ;

accesoExtra
    : '.' IDENT
    | '[' expresion ']'
    | '(' argumentos? ')'
    ;

argumentos
    : expresion (',' expresion)*
    ;

primario
    : literal
    | IDENT
    | '(' expresion ')'
    | listaLiteral
    | objetoLiteral
    ;

listaLiteral
    : '[' (expresion (',' expresion)*)? ']'
    ;

objetoLiteral
    : '{' (parObjeto (',' parObjeto)*)? '}'
    ;

parObjeto
    : (IDENT | STRING) ':' expresion
    ;

literal
    : NUMBER
    | STRING
    | TRUE
    | FALSE
    | NULL
    ;

IMPORT: 'import';
IMPORT_JAR: 'import_jar';
AS: 'as';
PROCESS: 'process';
FUNCTION: 'function';
START: 'start';
END: 'end';
TASK: 'task';
ACTION: 'action';
GATEWAY: 'gateway';
PARALLEL: 'parallel';
BRANCH: 'branch';
JOIN: 'join';
WHEN: 'when';
ELSE: 'else';
GO_TO: 'go_to';
TRY: 'try';
CATCH: 'catch';
FOR: 'for';
EACH: 'each';
IN: 'in';
IF: 'if';
RETURN: 'return';
OR: 'or';
AND: 'and';
NOT: 'not';
TRUE: 'true';
FALSE: 'false';
NULL: 'null';
INTEGER: 'integer';
DECIMAL: 'decimal';
BOOLEAN: 'boolean';
TEXT: 'text';
LIST: 'list';
OBJECT: 'object';
VOID: 'void';

ARROW: '->';
MAS: '+';
MENOS: '-';
MULT: '*';
DIV: '/';
MOD: '%';
MENORIGUAL: '<=';
MAYORIGUAL: '>=';
IGUALDAD: '==';
DIFERENTE: '!=';
MENOR: '<';
MAYOR: '>';

NUMBER
    : [0-9]+ ('.' [0-9]+)?
    ;

STRING
    : '"' ( '\\' . | ~["\\\r\n] )* '"'
    ;

IDENT
    : [a-zA-Z_][a-zA-Z_0-9]*
    ;

LINE_COMMENT
    : '#' ~[\r\n]* -> skip
    ;

BLOCK_COMMENT
    : '/*' ( . | '\r' | '\n' )*? '*/' -> skip
    ;

WS
    : [ \t\r\n]+ -> skip
    ;
