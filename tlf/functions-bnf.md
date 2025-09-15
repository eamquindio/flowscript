# FlowScript Functions BNF (Team A)

Nota: Este archivo resume la BNF utilizada para la gramática de funciones. Úsalo como base en BottleCaps (rr) para generar railroad diagrams si lo deseas.

Inicio
  functionProgram ::= functionDeclaration* EOF

Funciones
  functionDeclaration ::= 'function' functionName '(' parameterList? ')' ('->' returnType)? functionBody
  functionName       ::= IDENTIFIER
  parameterList      ::= parameter (',' parameter)*
  parameter          ::= parameterName ':' parameterType
  parameterName      ::= IDENTIFIER
  parameterType      ::= dataType
  returnType         ::= dataType | 'void'
  dataType           ::= primitiveType | compositeType
  primitiveType      ::= 'integer' | 'decimal' | 'boolean' | 'text'
  compositeType      ::= 'list' | 'object'
  functionBody       ::= '{' statement* '}'

Statements
  statement ::= assignmentStatement
              | ifStatement
              | whileStatement
              | forEachStatement
              | forRangeStatement
              | tryStatement
              | returnStatement
              | throwStatement
              | breakStatement
              | continueStatement
              | expressionStatement
              | block

  block                ::= '{' statement* '}'
  assignmentStatement  ::= leftHandSide '=' assignmentExpression
  leftHandSide         ::= IDENTIFIER ('.' IDENTIFIER | '[' expression ']')*
  ifStatement          ::= 'if' condition block elseIfClause* elseClause?
  elseIfClause         ::= 'else_if' condition block
  elseClause           ::= 'else' block
  whileStatement       ::= 'while' condition block
  forEachStatement     ::= 'for' 'each' IDENTIFIER 'in' expression block
  forRangeStatement    ::= 'for' IDENTIFIER 'from' expression 'to' expression ('step' expression)? block
  tryStatement         ::= 'try' block catchClause+
  catchClause          ::= 'catch' '(' IDENTIFIER ')' block
  returnStatement      ::= 'return' expression?
  throwStatement       ::= 'throw' expression
  breakStatement       ::= 'break'
  continueStatement    ::= 'continue'
  expressionStatement  ::= expression
  condition            ::= expression

Expresiones (precedencia)
  expression               ::= assignmentExpression
  assignmentExpression     ::= leftHandSide '=' assignmentExpression
                             | logicalOrExpression
  logicalOrExpression      ::= logicalAndExpression ('or' logicalAndExpression)*
  logicalAndExpression     ::= equalityExpression ('and' equalityExpression)*
  equalityExpression       ::= relationalExpression (('==' | '!=') relationalExpression)*
  relationalExpression     ::= additiveExpression (('<' | '>' | '<=' | '>=') additiveExpression)*
  additiveExpression       ::= multiplicativeExpression (('+' | '-') multiplicativeExpression)*
  multiplicativeExpression ::= unaryExpression (('*' | '/' | '%') unaryExpression)*
  unaryExpression          ::= 'not' unaryExpression
                             | '-' unaryExpression
                             | postfixExpression
  postfixExpression        ::= primaryExpression postfixOp*
  postfixOp                ::= '(' argumentList? ')' | '.' IDENTIFIER | '[' expression ']'
  argumentList             ::= expression (',' expression)*
  primaryExpression        ::= literal | IDENTIFIER | '(' expression ')' | listLiteral | objectLiteral

Literales
  literal        ::= INTEGER_LITERAL | DECIMAL_LITERAL | STRING_LITERAL | 'true' | 'false' | 'null'
  listLiteral    ::= '[' (expression (',' expression)*)? ']'
  objectLiteral  ::= '{' (objectProperty (',' objectProperty)*)? '}'
  objectProperty ::= propertyKey ':' expression
  propertyKey    ::= IDENTIFIER | STRING_LITERAL

Léxico (resumen)
  IDENTIFIER       ::= [a-zA-Z_] [a-zA-Z0-9_]*
  INTEGER_LITERAL  ::= [0-9]+ ('_'? [0-9]+)*
  DECIMAL_LITERAL  ::= INTEGER_LITERAL '.' INTEGER_LITERAL EXPONENT? | INTEGER_LITERAL EXPONENT
  EXPONENT         ::= [eE] [+-]? [0-9]+
  STRING_LITERAL   ::= '"' (escape | ~['"' '\\' '\r' '\n'])* '"'
  Comentarios      ::= '#' hasta fin de línea | /* ... */ multilinea
  WS               ::= espacios/tabs/nuevas líneas (ignorado)

