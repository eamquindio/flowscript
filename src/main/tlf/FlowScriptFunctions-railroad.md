# FlowScript Functions Grammar – BNF Overview

This document summarizes the FlowScript Functions grammar in BNF-style and serves as the basis for generating the railroad diagram. When internet is available, paste this BNF into https://www.bottlecaps.de/rr/ui to export the embedded railroad Markdown.

## BNF

```
<functionProgram>      ::= { <functionDeclaration> } EOF

<functionDeclaration>  ::= "function" <Identifier> "(" [ <parameterList> ] ")"
                           [ "->" <dataType> ] <functionBody>

<parameterList>        ::= <parameter> { "," <parameter> }
<parameter>            ::= <Identifier> ":" <dataType>

<dataType>             ::= "integer" | "decimal" | "boolean" | "text"
                           | "list" | "object" | "void"

<functionBody>         ::= "{" { <statement> } "}"

<statement>            ::= <ifStatement>
                           | <whileStatement>
                           | <forEachStatement>
                           | <forRangeStatement>
                           | <tryStatement>
                           | <throwStatement>
                           | <returnStatement>
                           | <breakStatement>
                           | <continueStatement>
                           | <assignmentStatement>
                           | <expressionStatement>

<block>                ::= "{" { <statement> } "}"

<ifStatement>          ::= "if" <expression> <block>
                           { "else_if" <expression> <block> }
                           [ "else" <block> ]

<whileStatement>       ::= "while" <expression> <block>

<forEachStatement>     ::= "for" "each" <Identifier> "in" <expression> <block>

<forRangeStatement>    ::= "for" <Identifier> "from" <expression> "to" <expression>
                           [ "step" <expression> ] <block>

<tryStatement>         ::= "try" <block> { <catchClause> }
<catchClause>          ::= "catch" "(" <Identifier> ")" <block>

<throwStatement>       ::= "throw" <expression>
<returnStatement>      ::= "return" [ <expression> ]
<breakStatement>       ::= "break"
<continueStatement>    ::= "continue"

<assignmentStatement>  ::= <leftHandSide> "=" <expression>
<leftHandSide>         ::= <primaryExpression> { <postfixPart> }

<expressionStatement>  ::= <expression>

<expression>           ::= <logicalOrExpression>
<logicalOrExpression>  ::= <logicalAndExpression> { "or" <logicalAndExpression> }
<logicalAndExpression> ::= <equalityExpression>   { "and" <equalityExpression> }
<equalityExpression>   ::= <relationalExpression> { ("==" | "!=") <relationalExpression> }
<relationalExpression> ::= <additiveExpression>   { ("<" | "<=" | ">" | ">=") <additiveExpression> }
<additiveExpression>   ::= <multiplicativeExpression> { ("+" | "-") <multiplicativeExpression> }
<multiplicativeExpression> ::= <unaryExpression> { ("*" | "/" | "%") <unaryExpression> }
<unaryExpression>      ::= ("not" | "+" | "-") <unaryExpression>
                           | <postfixExpression>

<postfixExpression>    ::= <primaryExpression> { <postfixPart> }
<postfixPart>          ::= "." <Identifier>
                           | "[" <expression> "]"
                           | "(" [ <argumentList> ] ")"
                           | "." <Identifier> "(" [ <argumentList> ] ")"

<primaryExpression>    ::= <literal>
                           | "(" <expression> ")"
                           | <Identifier>

<argumentList>         ::= <expression> { "," <expression> }

<literal>              ::= <IntegerLiteral>
                           | <DecimalLiteral>
                           | <StringLiteral>
                           | "true" | "false" | "null"
                           | <listLiteral>
                           | <objectLiteral>

<listLiteral>          ::= "[" [ <expression> { "," <expression> } ] "]"
<objectLiteral>        ::= "{" [ <objectProperty> { "," <objectProperty> } ] "}"
<objectProperty>       ::= <propertyKey> ":" <expression>
<propertyKey>          ::= <Identifier> | <StringLiteral>
```

## Notes
- Operator precedence matches the project spec (highest: member/index/call → lowest: assignment).
- Assignment is right-associative.
- Comments and whitespace are ignored at the lexer level.

## How to Export Railroad Markdown
- Open https://www.bottlecaps.de/rr/ui
- Paste the BNF above into “Edit Grammar”.
- Click “View Diagram” → “Download as Embedded Markdown”.
- Save as this file or replace its contents with the exported markdown.

