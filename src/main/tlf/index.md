**Program:**

![Program](diagram/Program.png)

```
Program  ::= ( ImportStatement | GlobalVar | FunctionDecl | ProcessDecl )*
```

**ImportStatement:**

![ImportStatement](diagram/ImportStatement.png)

```
ImportStatement
         ::= ( 'import' | 'import_jar' ) StringLiteral ( 'as' NCName )? ';'?
```

referenced by:

* Program

**GlobalVar:**

![GlobalVar](diagram/GlobalVar.png)

```
GlobalVar
         ::= NCName '=' Expression ';'?
```

referenced by:

* Program

**FunctionDecl:**

![FunctionDecl](diagram/FunctionDecl.png)

```
FunctionDecl
         ::= 'function' NCName '(' ParamList? ')' ( '->' Type )? '{' StatementList '}' ';'?
```

referenced by:

* Program

**ParamList:**

![ParamList](diagram/ParamList.png)

```
ParamList
         ::= Param ( ',' Param )*
```

referenced by:

* FunctionDecl

**Param:**

![Param](diagram/Param.png)

```
Param    ::= NCName ':' Type
```

referenced by:

* ParamList

**Type:**

![Type](diagram/Type.png)

```
Type     ::= 'boolean'
           | 'text'
           | 'integer'
           | 'void'
           | 'object'
```

referenced by:

* FunctionDecl
* Param

**ProcessDecl:**

![ProcessDecl](diagram/ProcessDecl.png)

```
ProcessDecl
         ::= 'process' NCName '{' ProcessBody '}' ';'?
```

referenced by:

* Program

**ProcessBody:**

![ProcessBody](diagram/ProcessBody.png)

```
ProcessBody
         ::= StartNode ProcessElement* EndNode+
```

referenced by:

* ProcessDecl

**ProcessElement:**

![ProcessElement](diagram/ProcessElement.png)

```
ProcessElement
         ::= TaskNode
           | GatewayNode
```

referenced by:

* ProcessBody

**StartNode:**

![StartNode](diagram/StartNode.png)

```
StartNode
         ::= 'start' '->' NCName ';'?
```

referenced by:

* ProcessBody

**TaskNode:**

![TaskNode](diagram/TaskNode.png)

```
TaskNode ::= 'task' NCName '{' ActionBlock '}' ';'?
```

referenced by:

* ProcessElement

**ActionBlock:**

![ActionBlock](diagram/ActionBlock.png)

```
ActionBlock
         ::= 'action' ':' StatementList
```

referenced by:

* TaskNode

**GatewayNode:**

![GatewayNode](diagram/GatewayNode.png)

```
GatewayNode
         ::= 'gateway' NCName 'parallel'? '{' GatewayBody '}' ';'?
```

referenced by:

* ProcessElement

**GatewayBody:**

![GatewayBody](diagram/GatewayBody.png)

```
GatewayBody
         ::= ParallelGateway
           | ExclusiveGateway
```

referenced by:

* GatewayNode

**ParallelGateway:**

![ParallelGateway](diagram/ParallelGateway.png)

```
ParallelGateway
         ::= 'parallel'? BranchStmt+ JoinStmt
```

referenced by:

* GatewayBody

**ExclusiveGateway:**

![ExclusiveGateway](diagram/ExclusiveGateway.png)

```
ExclusiveGateway
         ::= WhenStmt+ ( 'else' '->' NCName ';'? )?
```

referenced by:

* GatewayBody

**BranchStmt:**

![BranchStmt](diagram/BranchStmt.png)

```
BranchStmt
         ::= 'branch' '->' NCName ';'?
```

referenced by:

* ParallelGateway

**WhenStmt:**

![WhenStmt](diagram/WhenStmt.png)

```
WhenStmt ::= 'when' Condition '->' NCName ';'?
```

referenced by:

* ExclusiveGateway
* GatewayStmt

**JoinStmt:**

![JoinStmt](diagram/JoinStmt.png)

```
JoinStmt ::= 'join' '->' NCName ';'?
```

referenced by:

* ParallelGateway

**EndNode:**

![EndNode](diagram/EndNode.png)

```
EndNode  ::= 'end' NCName ';'?
```

referenced by:

* ProcessBody

**StatementList:**

![StatementList](diagram/StatementList.png)

```
StatementList
         ::= Statement*
```

referenced by:

* ActionBlock
* ForStatement
* FunctionDecl
* IfStatement
* TryCatch

**Statement:**

![Statement](diagram/Statement.png)

```
Statement
         ::= Assignment
           | ArrayAssignment
           | IfStatement
           | ForStatement
           | TryCatch
           | GotoStatement
           | GatewayStmt
           | ReturnStatement
           | ExpressionStatement
```

referenced by:

* StatementList

**Assignment:**

![Assignment](diagram/Assignment.png)

```
Assignment
         ::= NCName '=' Expression ';'?
```

referenced by:

* Statement

**ArrayAssignment:**

![ArrayAssignment](diagram/ArrayAssignment.png)

```
ArrayAssignment
         ::= Expression '[' Expression ']' '=' Expression ';'?
```

referenced by:

* Statement

**IfStatement:**

![IfStatement](diagram/IfStatement.png)

```
IfStatement
         ::= 'if' Condition '{' StatementList '}' ( 'else' '{' StatementList '}' )? ';'?
```

referenced by:

* Statement

**ForStatement:**

![ForStatement](diagram/ForStatement.png)

```
ForStatement
         ::= 'for' 'each' NCName 'in' Expression '{' StatementList '}' ';'?
```

referenced by:

* Statement

**TryCatch:**

![TryCatch](diagram/TryCatch.png)

```
TryCatch ::= 'try' '{' StatementList '}' 'catch' '(' NCName ')' '{' StatementList '}' ';'?
```

referenced by:

* Statement

**GotoStatement:**

![GotoStatement](diagram/GotoStatement.png)

```
GotoStatement
         ::= ( 'goto' | 'go_to' ) NCName ';'?
```

referenced by:

* Statement

**GatewayStmt:**

![GatewayStmt](diagram/GatewayStmt.png)

```
GatewayStmt
         ::= 'gateway' NCName '{' ( WhenStmt+ ( 'else' '->' NCName ';'? )? )? '}' ';'?
```

referenced by:

* Statement

**ExpressionStatement:**

![ExpressionStatement](diagram/ExpressionStatement.png)

```
ExpressionStatement
         ::= Expression ';'?
```

referenced by:

* Statement

**ReturnStatement:**

![ReturnStatement](diagram/ReturnStatement.png)

```
ReturnStatement
         ::= 'return' Expression ';'?
```

referenced by:

* Statement

**Expression:**

![Expression](diagram/Expression.png)

```
Expression
         ::= LogicalOrExpression
```

referenced by:

* ArrayAccess
* ArrayAssignment
* Assignment
* Condition
* ExpressionList
* ExpressionStatement
* ForStatement
* GlobalVar
* ObjectField
* PrimaryExpression
* ReturnStatement

**LogicalOrExpression:**

![LogicalOrExpression](diagram/LogicalOrExpression.png)

```
LogicalOrExpression
         ::= LogicalAndExpression ( 'or' LogicalAndExpression )*
```

referenced by:

* Expression

**LogicalAndExpression:**

![LogicalAndExpression](diagram/LogicalAndExpression.png)

```
LogicalAndExpression
         ::= EqualityExpression ( 'and' EqualityExpression )*
```

referenced by:

* LogicalOrExpression

**EqualityExpression:**

![EqualityExpression](diagram/EqualityExpression.png)

```
EqualityExpression
         ::= RelationalExpression ( ( '==' | '!=' ) RelationalExpression )*
```

referenced by:

* LogicalAndExpression

**RelationalExpression:**

![RelationalExpression](diagram/RelationalExpression.png)

```
RelationalExpression
         ::= AdditiveExpression ( ( '>' | '<' | '>=' | '<=' ) AdditiveExpression )*
```

referenced by:

* EqualityExpression

**AdditiveExpression:**

![AdditiveExpression](diagram/AdditiveExpression.png)

```
AdditiveExpression
         ::= MultiplicativeExpression ( ( '+' | '-' ) MultiplicativeExpression )*
```

referenced by:

* RelationalExpression

**MultiplicativeExpression:**

![MultiplicativeExpression](diagram/MultiplicativeExpression.png)

```
MultiplicativeExpression
         ::= UnaryExpression ( ( '*' | '/' ) UnaryExpression )*
```

referenced by:

* AdditiveExpression

**UnaryExpression:**

![UnaryExpression](diagram/UnaryExpression.png)

```
UnaryExpression
         ::= 'not'* PostfixExpression
```

referenced by:

* MultiplicativeExpression

**PostfixExpression:**

![PostfixExpression](diagram/PostfixExpression.png)

```
PostfixExpression
         ::= PrimaryExpression ( PropertyAccess | FunctionCall | ArrayAccess )*
```

referenced by:

* UnaryExpression

**PropertyAccess:**

![PropertyAccess](diagram/PropertyAccess.png)

```
PropertyAccess
         ::= '.' NCName
```

referenced by:

* PostfixExpression

**FunctionCall:**

![FunctionCall](diagram/FunctionCall.png)

```
FunctionCall
         ::= '(' ExpressionList? ')'
```

referenced by:

* PostfixExpression

**ArrayAccess:**

![ArrayAccess](diagram/ArrayAccess.png)

```
ArrayAccess
         ::= '[' Expression ']'
```

referenced by:

* PostfixExpression

**PrimaryExpression:**

![PrimaryExpression](diagram/PrimaryExpression.png)

```
PrimaryExpression
         ::= Literal
           | NCName
           | '(' Expression ')'
           | ObjectLiteral
           | ArrayLiteral
```

referenced by:

* PostfixExpression

**ExpressionList:**

![ExpressionList](diagram/ExpressionList.png)

```
ExpressionList
         ::= Expression ( ',' Expression )*
```

referenced by:

* ArrayLiteral
* FunctionCall

**Condition:**

![Condition](diagram/Condition.png)

```
Condition
         ::= Expression
```

referenced by:

* IfStatement
* WhenStmt

**Literal:**

![Literal](diagram/Literal.png)

```
Literal  ::= StringLiteral
           | NumberLiteral
           | 'true'
           | 'false'
           | 'null'
```

referenced by:

* PrimaryExpression

**ObjectLiteral:**

![ObjectLiteral](diagram/ObjectLiteral.png)

```
ObjectLiteral
         ::= '{' ( ObjectField ( ',' ObjectField )* )? '}'
```

referenced by:

* PrimaryExpression

**ObjectField:**

![ObjectField](diagram/ObjectField.png)

```
ObjectField
         ::= ( NCName | StringLiteral ) ':' Expression
```

referenced by:

* ObjectLiteral

**ArrayLiteral:**

![ArrayLiteral](diagram/ArrayLiteral.png)

```
ArrayLiteral
         ::= '[' ExpressionList? ']'
```

referenced by:

* PrimaryExpression

**StringLiteral:**

![StringLiteral](diagram/StringLiteral.png)

```
StringLiteral
         ::= '"' [^"]* '"'
```

referenced by:

* ImportStatement
* Literal
* ObjectField

**NumberLiteral:**

![NumberLiteral](diagram/NumberLiteral.png)

```
NumberLiteral
         ::= [0-9]+ ( '.' [0-9]+ )?
```

referenced by:

* Literal

**NCName:**

![NCName](diagram/NCName.png)

```
NCName   ::= [a-zA-Z_] [a-zA-Z0-9_]*
```

referenced by:

* Assignment
* BranchStmt
* EndNode
* ExclusiveGateway
* ForStatement
* FunctionDecl
* GatewayNode
* GatewayStmt
* GlobalVar
* GotoStatement
* ImportStatement
* JoinStmt
* ObjectField
* Param
* PrimaryExpression
* ProcessDecl
* PropertyAccess
* StartNode
* TaskNode
* TryCatch
* WhenStmt

**Comment:**

![Comment](diagram/Comment.png)

```
Comment  ::= '#' [^#xA#xD]*
           | '/*' ( [^*] | '*'+ [^*/] )* '*'* '*/'
```

## 
![Railroad-Diagram-Generator](diagram/Railroad-Diagram-Generator.png) <sup>generated by [RR - Railroad Diagram Generator][RR]</sup>

[RR]: https://www.bottlecaps.de/rr/ui