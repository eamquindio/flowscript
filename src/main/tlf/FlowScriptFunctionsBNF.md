**functionProgram:**

![functionProgram](diagram/functionProgram.svg)

```
functionProgram
         ::= functionDeclaration* EOF
```

**functionDeclaration:**

![functionDeclaration](diagram/functionDeclaration.svg)

```
functionDeclaration
         ::= 'function' functionName '(' parameterList? ')' ( '->' dataType )? block
```

referenced by:

* functionProgram

**functionName:**

![functionName](diagram/functionName.svg)

```
functionName
         ::= IDENTIFIER
```

referenced by:

* functionDeclaration

**parameterList:**

![parameterList](diagram/parameterList.svg)

```
parameterList
         ::= parameter ( ',' parameter )*
```

referenced by:

* functionDeclaration

**parameter:**

![parameter](diagram/parameter.svg)

```
parameter
         ::= IDENTIFIER ':' dataType
```

referenced by:

* parameterList

**dataType:**

![dataType](diagram/dataType.svg)

```
dataType ::= 'integer'
           | 'decimal'
           | 'boolean'
           | 'text'
           | 'list'
           | 'object'
           | 'void'
```

referenced by:

* functionDeclaration
* parameter

**block:**

![block](diagram/block.svg)

```
block    ::= '{' statement* '}'
```

referenced by:

* forEachStatement
* forRangeStatement
* functionDeclaration
* ifStatement
* tryCatchStatement
* whileStatement

**statement:**

![statement](diagram/statement.svg)

```
statement
         ::= ifStatement
           | whileStatement
           | forEachStatement
           | forRangeStatement
           | tryCatchStatement
           | throwStatement
           | 'break'
           | 'continue'
           | returnStatement
           | expressionStatement
```

referenced by:

* block

**ifStatement:**

![ifStatement](diagram/ifStatement.svg)

```
ifStatement
         ::= 'if' expression block ( 'else_if' expression block )* ( 'else' block )?
```

referenced by:

* statement

**whileStatement:**

![whileStatement](diagram/whileStatement.svg)

```
whileStatement
         ::= 'while' expression block
```

referenced by:

* statement

**forEachStatement:**

![forEachStatement](diagram/forEachStatement.svg)

```
forEachStatement
         ::= 'for' 'each' IDENTIFIER 'in' expression block
```

referenced by:

* statement

**forRangeStatement:**

![forRangeStatement](diagram/forRangeStatement.svg)

```
forRangeStatement
         ::= 'for' IDENTIFIER 'from' expression 'to' expression ( 'step' expression )? block
```

referenced by:

* statement

**tryCatchStatement:**

![tryCatchStatement](diagram/tryCatchStatement.svg)

```
tryCatchStatement
         ::= 'try' block ( 'catch' '(' IDENTIFIER ')' block )+
```

referenced by:

* statement

**throwStatement:**

![throwStatement](diagram/throwStatement.svg)

```
throwStatement
         ::= 'throw' expression
```

referenced by:

* statement

**returnStatement:**

![returnStatement](diagram/returnStatement.svg)

```
returnStatement
         ::= 'return' expression?
```

referenced by:

* statement

**expressionStatement:**

![expressionStatement](diagram/expressionStatement.svg)

```
expressionStatement
         ::= expression
```

referenced by:

* statement

**expression:**

![expression](diagram/expression.svg)

```
expression
         ::= ( leftHandSide '=' )* logicalOrExpression
```

referenced by:

* argumentList
* expressionStatement
* forEachStatement
* forRangeStatement
* ifStatement
* leftHandSide
* listLiteral
* objectPair
* postfixPart
* primaryExpression
* returnStatement
* throwStatement
* whileStatement

**logicalOrExpression:**

![logicalOrExpression](diagram/logicalOrExpression.svg)

```
logicalOrExpression
         ::= logicalAndExpression ( 'or' logicalAndExpression )*
```

referenced by:

* expression

**logicalAndExpression:**

![logicalAndExpression](diagram/logicalAndExpression.svg)

```
logicalAndExpression
         ::= equalityExpression ( 'and' equalityExpression )*
```

referenced by:

* logicalOrExpression

**equalityExpression:**

![equalityExpression](diagram/equalityExpression.svg)

```
equalityExpression
         ::= relationalExpression ( ( '==' | '!=' ) relationalExpression )*
```

referenced by:

* logicalAndExpression

**relationalExpression:**

![relationalExpression](diagram/relationalExpression.svg)

```
relationalExpression
         ::= additiveExpression ( ( '<' | '<=' | '>' | '>=' ) additiveExpression )*
```

referenced by:

* equalityExpression

**additiveExpression:**

![additiveExpression](diagram/additiveExpression.svg)

```
additiveExpression
         ::= multiplicativeExpression ( ( '+' | '-' ) multiplicativeExpression )*
```

referenced by:

* relationalExpression

**multiplicativeExpression:**

![multiplicativeExpression](diagram/multiplicativeExpression.svg)

```
multiplicativeExpression
         ::= unaryExpression ( ( '*' | '/' | '%' ) unaryExpression )*
```

referenced by:

* additiveExpression

**unaryExpression:**

![unaryExpression](diagram/unaryExpression.svg)

```
unaryExpression
         ::= ( 'not' | '-' )* postfixExpression
```

referenced by:

* multiplicativeExpression

**postfixExpression:**

![postfixExpression](diagram/postfixExpression.svg)

```
postfixExpression
         ::= primaryExpression postfixPart*
```

referenced by:

* unaryExpression

**postfixPart:**

![postfixPart](diagram/postfixPart.svg)

```
postfixPart
         ::= '.' IDENTIFIER
           | '[' expression ']'
           | '(' argumentList? ')'
```

referenced by:

* postfixExpression

**leftHandSide:**

![leftHandSide](diagram/leftHandSide.svg)

```
leftHandSide
         ::= IDENTIFIER ( '.' IDENTIFIER | '[' expression ']' )*
```

referenced by:

* expression

**argumentList:**

![argumentList](diagram/argumentList.svg)

```
argumentList
         ::= expression ( ',' expression )*
```

referenced by:

* postfixPart

**primaryExpression:**

![primaryExpression](diagram/primaryExpression.svg)

```
primaryExpression
         ::= literal
           | IDENTIFIER
           | '(' expression ')'
           | listLiteral
           | objectLiteral
```

referenced by:

* postfixExpression

**literal:**

![literal](diagram/literal.svg)

```
literal  ::= 'null'
           | 'true'
           | 'false'
           | DECIMAL_LITERAL
           | INTEGER_LITERAL
           | STRING_LITERAL
```

referenced by:

* primaryExpression

**listLiteral:**

![listLiteral](diagram/listLiteral.svg)

```
listLiteral
         ::= '[' ( expression ( ',' expression )* )? ']'
```

referenced by:

* primaryExpression

**objectLiteral:**

![objectLiteral](diagram/objectLiteral.svg)

```
objectLiteral
         ::= '{' ( objectPair ( ',' objectPair )* )? '}'
```

referenced by:

* primaryExpression

**objectPair:**

![objectPair](diagram/objectPair.svg)

```
objectPair
         ::= IDENTIFIER ':' expression
```

referenced by:

* objectLiteral

**IDENTIFIER:**

![IDENTIFIER](diagram/IDENTIFIER.svg)

```
IDENTIFIER
         ::= [a-zA-Z_] [a-zA-Z0-9_]*
```

referenced by:

* forEachStatement
* forRangeStatement
* functionName
* leftHandSide
* objectPair
* parameter
* postfixPart
* primaryExpression
* tryCatchStatement

**INTEGER_LITERAL:**

![INTEGER_LITERAL](diagram/INTEGER_LITERAL.svg)

```
INTEGER_LITERAL
         ::= DIGITS
```

referenced by:

* literal

**DECIMAL_LITERAL:**

![DECIMAL_LITERAL](diagram/DECIMAL_LITERAL.svg)

```
DECIMAL_LITERAL
         ::= DIGITS ( '.' ( DIGITS [eE] [+#x2D]? )? | [eE] [+#x2D]? ) DIGITS
```

referenced by:

* literal

**STRING_LITERAL:**

![STRING_LITERAL](diagram/STRING_LITERAL.svg)

```
STRING_LITERAL
         ::= '"' ( ESC | [^"\] )* '"'
```

referenced by:

* literal

**ESC:**

![ESC](diagram/ESC.svg)

```
ESC      ::= '\\' [\/"bfnrt]
           | '\\u' HEX HEX HEX HEX
```

referenced by:

* STRING_LITERAL

**DIGITS:**

![DIGITS](diagram/DIGITS.svg)

```
DIGITS   ::= [0-9] ( [0-9_]* [0-9] )?
```

referenced by:

* DECIMAL_LITERAL
* INTEGER_LITERAL

**HEX:**

![HEX](diagram/HEX.svg)

```
HEX      ::= [0-9a-fA-F]
```

referenced by:

* ESC

## 
![rr-2.5](diagram/rr-2.5.svg) <sup>generated by [RR - Railroad Diagram Generator][RR]</sup>

[RR]: https://www.bottlecaps.de/rr/ui