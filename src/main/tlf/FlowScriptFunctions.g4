grammar FlowScriptFunctions;

@header {
    package edu.eam.ingesoft.tlf;
}

// ============================
// PARSER RULES
// ============================

functionProgram
    : importDeclaration* functionDeclaration* EOF
    ;

importDeclaration
    : IMPORT stringLiteral (AS IDENTIFIER)? SEMICOLON?
    | IMPORT_JAR stringLiteral (AS IDENTIFIER)? SEMICOLON?
    ;

functionDeclaration
    : FUNCTION funcName=IDENTIFIER LPAREN paramList? RPAREN returnType? funcBody
    ;

paramList
    : param (COMMA param)*
    ;

param
    : paramName=IDENTIFIER COLON paramType=typeSpec
    ;

returnType
    : ARROW retType=typeSpec
    ;

typeSpec
    : primitiveType
    | collectionType
    ;

primitiveType
    : INTEGER_TYPE
    | DECIMAL_TYPE
    | BOOLEAN_TYPE
    | TEXT_TYPE
    | OBJECT_TYPE
    | VOID_TYPE
    | NULL_VALUE
    ;

collectionType
    : LIST_TYPE (LBRACKET typeSpec RBRACKET)?
    ;

funcBody
    : LBRACE blockContent? RBRACE
    ;

blockContent
    : statement+
    ;
 

statement
    : assignmentStmt
    | ifStatement
    | whileStatement
    | forStatement
    | returnStatement
    | throwStatement
    | tryStatement
    | breakStatement
    | continueStatement
    | expressionStatement
    | declareStatement
    ;

declareStatement
    : varName=IDENTIFIER ASSIGN expr SEMICOLON?
    ;

assignmentStmt
    : target=postfixExpr ASSIGN value=expr SEMICOLON?
    ;

ifStatement
    : IF condition=expr LBRACE ifBody=blockContent? RBRACE 
      (ELSE_IF elifCond=expr LBRACE elifBody=blockContent? RBRACE)* 
      (ELSE LBRACE elseBody=blockContent? RBRACE)?
    ;

whileStatement
    : WHILE condition=expr LBRACE loopBody=blockContent? RBRACE
    ;

forStatement
    : FOR EACH loopVar=IDENTIFIER IN collexpr=expr LBRACE loopBody=blockContent? RBRACE
    | FOR loopVar=IDENTIFIER FROM startVal=expr TO endVal=expr (STEP stepVal=expr)? LBRACE loopBody=blockContent? RBRACE
    ;

returnStatement
    : RETURN returnValue=expr? SEMICOLON?
    ;

throwStatement
    : THROW throwExpr=expr SEMICOLON?
    ;

breakStatement
    : BREAK SEMICOLON?
    ;

continueStatement
    : CONTINUE SEMICOLON?
    ;

tryStatement
    : TRY LBRACE tryBody=blockContent? RBRACE 
      (CATCH LPAREN catchVar=IDENTIFIER RPAREN LBRACE catchBody=blockContent? RBRACE)+
    ;

expressionStatement
    : expr SEMICOLON?
    ;

// ============================
// EXPRESSION RULES (Precedence)
// ============================

expr
    : ternaryExpr
    ;

ternaryExpr
    : logicalOrExpr (QUESTION thenExpr=expr COLON elseExpr=expr)?
    ;

logicalOrExpr
    : logicalAndExpr (OR logicalAndExpr)*
    ;

logicalAndExpr
    : equalityExpr (AND equalityExpr)*
    ;

equalityExpr
    : relationalExpr ((EQ | NEQ) relationalExpr)*
    ;

relationalExpr
    : additiveExpr ((LT | GT | LE | GE) additiveExpr)*
    ;

additiveExpr
    : multiplicativeExpr ((PLUS | MINUS) multiplicativeExpr)*
    ;

multiplicativeExpr
    : unaryExpr ((MULT | DIV | MOD) unaryExpr)*
    ;

unaryExpr
    : (NOT | MINUS | PLUS) unaryExpr
    | postfixExpr
    ;

postfixExpr
    : primaryExpr 
      (
        LPAREN argList? RPAREN
        | LBRACKET indexExpr=expr RBRACKET
        | DOT propName=IDENTIFIER
      )*
    ;

primaryExpr
    : IDENTIFIER
    | numberLiteral
    | stringLiteral
    | booleanLiteral
    | NULL_VALUE
    | listLiteral
    | objectLiteral
    | LPAREN expr RPAREN
    | functionCallExpr
    | lambdaExpr
    ;

numberLiteral
    : INTEGER
    | DECIMAL
    ;

stringLiteral
    : STRING
    ;

booleanLiteral
    : TRUE
    | FALSE
    ;

listLiteral
    : LBRACKET (expr (COMMA expr)*)? RBRACKET
    ;

objectLiteral
    : LBRACE (objMember (COMMA objMember)*)? RBRACE
    ;

objMember
    : memberKey=IDENTIFIER COLON memberValue=expr
    | memberKey=STRING COLON memberValue=expr
    ;

functionCallExpr
    : funcName=IDENTIFIER LPAREN argList? RPAREN
    ;

argList
    : expr (COMMA expr)*
    ;

lambdaExpr
    : LPAREN paramList? RPAREN DOUBLE_ARROW expr
    | LPAREN paramList? RPAREN DOUBLE_ARROW LBRACE blockContent? RBRACE
    ;

// ============================
// LEXER RULES (TOKENS)
// ============================

FUNCTION : 'function' ;
RETURN : 'return' ;
IF : 'if' ;
ELSE_IF : 'else_if' ;
ELSE : 'else' ;
WHILE : 'while' ;
FOR : 'for' ;
EACH : 'each' ;
IN : 'in' ;
FROM : 'from' ;
TO : 'to' ;
STEP : 'step' ;
THROW : 'throw' ;
TRY : 'try' ;
CATCH : 'catch' ;
IMPORT : 'import' ;
IMPORT_JAR : 'import_jar' ;
AS : 'as' ;
BREAK : 'break' ;
CONTINUE : 'continue' ;

INTEGER_TYPE : 'integer' ;
DECIMAL_TYPE : 'decimal' ;
BOOLEAN_TYPE : 'boolean' ;
TEXT_TYPE : 'text' ;
LIST_TYPE : 'list' ;
OBJECT_TYPE : 'object' ;
VOID_TYPE : 'void' ;

TRUE : 'true' ;
FALSE : 'false' ;
NULL_VALUE : 'null' ;

AND : 'and' ;
OR : 'or' ;
NOT : 'not' ;

ARROW : '->' ;
DOUBLE_ARROW : '=>' ;
SEMICOLON : ';' ;
COMMA : ',' ;
DOT : '.' ;
COLON : ':' ;
QUESTION : '?' ;
LPAREN : '(' ;
RPAREN : ')' ;
LBRACE : '{' ;
RBRACE : '}' ;
LBRACKET : '[' ;
RBRACKET : ']' ;

ASSIGN : '=' ;
EQ : '==' ;
NEQ : '!=' ;
LT : '<' ;
GT : '>' ;
LE : '<=' ;
GE : '>=' ;

PLUS : '+' ;
MINUS : '-' ;
MULT : '*' ;
DIV : '/' ;
MOD : '%' ;

INTEGER
    : DIGIT+
    ;
    

DECIMAL
    : DIGIT+ DOT DIGIT+
    | DIGIT+ DOT DIGIT+ [eE] [+\-]? DIGIT+
    | DIGIT+ [eE] [+\-]? DIGIT+
    ;

STRING
    : '"' (~["\\\r\n] | '\\' .)* '"'
    | '\'' (~['\\\r\n] | '\\' .)* '\''
    ;

IDENTIFIER
    : [a-zA-Z_] [a-zA-Z0-9_]*
    ;

fragment DIGIT
    : [0-9]
    ;

WS
    : [ \t\r\n]+ -> skip
    ;

LINE_COMMENT
    : '#' ~[\r\n]* -> skip
    ;

BLOCK_COMMENT
    : '/*' .*? '*/' -> skip
    ;


