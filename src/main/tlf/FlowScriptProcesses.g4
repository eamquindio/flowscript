grammar FlowScriptProcesses;

@header {
      package edu.eam.ingesoft.tlf;
}

/*
   =========================================================
   PARSER
   ---------------------------------------------------------
 */

program: importsPr declarationsPr EOF;

importsPr: imports*;
declarationsPr: declaration*;


imports: importModulos | importJar;

importModulos: IMPORT nombreModulo (AS alias)? PUNTCO?;
importJar: IMPORT_JAR nombreModulo (AS alias)? PUNTCO?;

nombreModulo: STRING;
alias: ID;

declarations: declaration*;

declaration: variablesGlDc | funcionDc | processDecl;

variablesGlDc: ref ASSIGN expresion PUNTCO?;
funcionDc: FUNCTION ID LPAREN paramListaOpt? RPAREN returnTipoOpt block;

paramListaOpt: param (COMMA param)*;
param: ID (PUNTOS tipoRef)?;

returnTipoOpt: (ARROW tipoRef)?;

tipoRef: INTEGER_T | DECIMAL_T | BOOLEAN_T | TEXT_T | LIST_T | OBJECT_T | VOID_T;

processDecl: PROCESS ID LBRACE
            startNodo
            processBody*
            endNodo+
        RBRACE;

processBody: taskNodo | gatewayNodo | statement;

startNodo: START ARROW ID PUNTCO?;
endNodo: END ID PUNTCO?;
taskNodo: TASK ID LBRACE ACTION PUNTOS statement* RBRACE;

gatewayNodo: getawayExcNodo | gatewayParNodo;

getawayExcNodo: GATEWAY ID LBRACE gatewayWhen+ gatewayElse? RBRACE;
gatewayParNodo: GATEWAY ID PARALLEL LBRACE gatewayBranch+ gatewayJoin RBRACE;

gatewayWhen: WHEN expresion ARROW ID PUNTCO?;
gatewayElse: ELSE ARROW ID PUNTCO?;
gatewayBranch: BRANCH ARROW ID PUNTCO?;
gatewayJoin: JOIN ARROW ID PUNTCO?;
block: LBRACE statement* RBRACE;


statement: ref ASSIGN expresion PUNTCO? | goToSent | ifSent | whileSent | foreachSent
        | forRangeSent | tryCatchSent | throwSent | returnSent |
        gatewaySent | block | expresion PUNTCO?;


ref: ID (DOT ID | LBRACK expresion RBRACK)*;

goToSent: GO_TO ID PUNTCO?;
ifSent: IF expresion block (ELSE_IF expresion block)* (ELSE block)?;

whileSent: WHILE expresion block;
foreachSent: FOR EACH ID IN expresion block;
forRangeSent: FOR ID FROM expresion TO expresion (STEP expresion)? block;

tryCatchSent: TRY block CATCH LPAREN ID RPAREN block;

throwSent: THROW expresion PUNTCO?;
returnSent: RETURN expresion? PUNTCO?;
gatewaySent: gatewayExclusivo | gatewayParalelo;
gatewayExclusivo: GATEWAY ID LBRACE gatewayWhen+ gatewayElse? RBRACE;
gatewayParalelo: GATEWAY ID PARALLEL LBRACE gatewayBranch+ gatewayJoin RBRACE;


expresion: expresion OR expresion | expresion AND expresion
        | expresion (EQ | NEQ) expresion | expresion (LT | GT | LE | GE) expresion
        | expresion (ADD | SUB) expresion | expresion (MUL | DIV | MOD) expresion
        | NOT expresion | SUB expresion | expresion DOT ID
        | expresion LBRACK expresion RBRACK | expresion LPAREN listaArgumentos? RPAREN
        | primary;

listaArgumentos: expresion (COMMA expresion)*;
primary: LPAREN expresion RPAREN | listaLiteral | objetoLiteral | literal | INPUT | ID;
listaLiteral: LBRACK (expresion (COMMA expresion)*)? RBRACK;
objetoLiteral: LBRACE (objectPair (COMMA objectPair)*)? RBRACE;
objectPair: ID PUNTOS expresion;
literal: INT | DECIMAL | STRING | BOOLEAN | NULL;

PROCESS     : [Pp][Rr][Oo][Cc][Ee][Ss][Ss];
FUNCTION    : [Ff][Uu][Nn][Cc][Tt][Ii][Oo][Nn];
IMPORT      : [Ii][Mm][Pp][Oo][Rr][Tt];
IMPORT_JAR  : [Ii][Mm][Pp][Oo][Rr][Tt] '_' [Jj][Aa][Rr];
AS          : [Aa][Ss];

START       : [Ss][Tt][Aa][Rr][Tt];
END         : [Ee][Nn][Dd];
TASK        : [Tt][Aa][Ss][Kk];
GATEWAY     : [Gg][Aa][Tt][Ee][Ww][Aa][Yy];
PARALLEL    : [Pp][Aa][Rr][Aa][Ll][Ll][Ee][Ll];
WHEN        : [Ww][Hh][Ee][Nn];
ELSE        : [Ee][Ll][Ss][Ee];
BRANCH      : [Bb][Rr][Aa][Nn][Cc][Hh];
JOIN        : [Jj][Oo][Ii][Nn];

ACTION      : [Aa][Cc][Tt][Ii][Oo][Nn];

GO_TO       : [Gg][Oo] '_' [Tt][Oo];
ARROW       : '-' '>';

IF          : [Ii][Ff];
ELSE_IF     : [Ee][Ll][Ss][Ee] '_' [Ii][Ff];
WHILE       : [Ww][Hh][Ii][Ll][Ee];
FOR         : [Ff][Oo][Rr];
EACH        : [Ee][Aa][Cc][Hh];
IN          : [Ii][Nn];
FROM        : [Ff][Rr][Oo][Mm];
TO          : [Tt][Oo];
STEP        : [Ss][Tt][Ee][Pp];
TRY         : [Tt][Rr][Yy];
CATCH       : [Cc][Aa][Tt][Cc][Hh];
THROW       : [Tt][Hh][Rr][Oo][Ww];
RETURN      : [Rr][Ee][Tt][Uu][Rr][Nn];

INTEGER_T   : [Ii][Nn][Tt][Ee][Gg][Ee][Rr];
DECIMAL_T   : [Dd][Ee][Cc][Ii][Mm][Aa][Ll];
BOOLEAN_T   : [Bb][Oo][Oo][Ll][Ee][Aa][Nn];
TEXT_T      : [Tt][Ee][Xx][Tt];
LIST_T      : [Ll][Ii][Ss][Tt];
OBJECT_T    : [Oo][Bb][Jj][Ee][Cc][Tt];
VOID_T      : [Vv][Oo][Ii][Dd];

AND         : [Aa][Nn][Dd];
OR          : [Oo][Rr];
NOT         : [Nn][Oo][Tt];

INPUT       : [Ii][Nn][Pp][Uu][Tt];

ADD         : '+';
SUB         : '-';
MUL         : '*';
DIV         : '/';
MOD         : '%';
LT          : '<';
GT          : '>';
LE          : '<=';
GE          : '>=';
EQ          : '==';
NEQ         : '!=';
ASSIGN      : '=';
LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
LBRACK      : '[';
RBRACK      : ']';
DOT         : '.';
COMMA       : ',';
PUNTOS       : ':';
PUNTCO      : ';';

BOOLEAN: 'true' | 'false';
NULL: 'null';
DECIMAL: [0-9]+ '.' [0-9]+ ([eE] [+-]? [0-9]+)?
        | '.' [0-9]+ ([eE] [+-]? [0-9]+)?
        | [0-9]+ '.' ([eE] [+-]? [0-9]+)?;
INT: [0-9]+ ;
STRING: '"' ( '\\' [btnr"\\/] | '\\u' HEX HEX HEX HEX | ~["\\] )* '"';

fragment HEX: [0-9a-fA-F] ;
ID : [a-zA-Z_][a-zA-Z0-9_]*;

WS: [ \t\r\n\f]+ -> skip ;
BLOCK_COMMENT: '/*' .*? '*/' -> skip ;
LINE_COMMENT_SLASH: '//' ~[\r\n]* -> skip ;
LINE_COMMENT_HASH: '#' ~[\r\n]*  -> skip ;

