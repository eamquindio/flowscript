**Program:**

![Program](diagram/Program.svg)

```
Program  ::= ImportList GlobalList EOF
```

**ImportList:**

![ImportList](diagram/ImportList.svg)

```
ImportList
         ::= ImportDecl [ImportLisRe]
```

referenced by:

* Program

**ImportListRest:**

![ImportListRest](diagram/ImportListRest.svg)

```
ImportListRest
         ::= ( ImportDecl | ImportJarDecl ) [ImportLisRe]
```

**ImportDecl:**

![ImportDecl](diagram/ImportDecl.svg)

```
ImportDecl
         ::= 'import' STRING ['as ID] ';'?
```

referenced by:

* ImportList
* ImportListRest

**ImportJarDecl:**

![ImportJarDecl](diagram/ImportJarDecl.svg)

```
ImportJarDecl
         ::= 'import_jar' STRING ['as ID] ';'?
```

referenced by:

* ImportListRest

**GlobalList:**

![GlobalList](diagram/GlobalList.svg)

```
GlobalList
         ::= GlobalVarDecl
           | FunctionDecl
           | ProcessDecl [GlobaListRe]
```

referenced by:

* Program

**GlobalListRest:**

![GlobalListRest](diagram/GlobalListRest.svg)

```
GlobalListRest
         ::= GlobalVarDecl
           | FunctionDecl
           | ProcessDecl [GlobaListRe]
```

**GlobalVarDecl:**

![GlobalVarDecl](diagram/GlobalVarDecl.svg)

```
GlobalVarDecl
         ::= LValue '=' Expression ';'?
```

referenced by:

* GlobalList
* GlobalListRest

**FunctionDecl:**

![FunctionDecl](diagram/FunctionDecl.svg)

```
FunctionDecl
         ::= 'function' ID '(' [ParmListOp]? ')' [ReturnTypO]? Block
```

referenced by:

* GlobalList
* GlobalListRest

**ParamListOpt:**

![ParamListOpt](diagram/ParamListOpt.svg)

```
ParamListOpt
         ::= Param [ParmListRe]
```

**ParamListRest:**

![ParamListRest](diagram/ParamListRest.svg)

```
ParamListRest
         ::= ',' Param [ParmListRe]
```

**Param:**

![Param](diagram/Param.svg)

```
Param    ::= ID ':' TypeRef
```

referenced by:

* ParamListOpt
* ParamListRest

**ReturnTypeOpt:**

![ReturnTypeOpt](diagram/ReturnTypeOpt.svg)

```
ReturnTypeOpt
         ::= '->' TypeRef
```

**TypeRef:**

![TypeRef](diagram/TypeRef.svg)

```
TypeRef  ::= 'integer'
           | 'decimal'
           | 'boolean'
           | 'text'
           | 'list'
           | 'object'
           | 'void'
```

referenced by:

* Param
* ReturnTypeOpt

**ProcessDecl:**

![ProcessDecl](diagram/ProcessDecl.svg)

```
ProcessDecl
         ::= 'process' ID '{' StartNode [ProcesInLit] EndList '}'
```

referenced by:

* GlobalList
* GlobalListRest

**ProcessInnerList:**

![ProcessInnerList](diagram/ProcessInnerList.svg)

```
ProcessInnerList
         ::= ProcessInner [ProcesInLit]
```

**ProcessInner:**

![ProcessInner](diagram/ProcessInner.svg)

```
ProcessInner
         ::= TaskNode
           | GatewayNode
```

referenced by:

* ProcessInnerList

**EndList:**

![EndList](diagram/EndList.svg)

```
EndList  ::= EndNode [EndList]
```

referenced by:

* ProcessDecl

**StartNode:**

![StartNode](diagram/StartNode.svg)

```
StartNode
         ::= 'start' '->' ID ';'?
```

referenced by:

* ProcessDecl

**EndNode:**

![EndNode](diagram/EndNode.svg)

```
EndNode  ::= 'end' ID ';'?
```

referenced by:

* EndList

**TaskNode:**

![TaskNode](diagram/TaskNode.svg)

```
TaskNode ::= 'task' ID '{' 'action' ':' [StaemnLis] '}'
```

referenced by:

* ProcessInner

**StatementList:**

![StatementList](diagram/StatementList.svg)

```
StatementList
         ::= Statement [StaemnLis]
```

**GatewayNode:**

![GatewayNode](diagram/GatewayNode.svg)

```
GatewayNode
         ::= ExclusiveGatewayNode
           | ParallelGatewayNode
```

referenced by:

* ProcessInner

**ExclusiveGatewayNode:**

![ExclusiveGatewayNode](diagram/ExclusiveGatewayNode.svg)

```
ExclusiveGatewayNode
         ::= 'gateway' ID '{' GatewayWhenList [GatewyElsOp] '}'
```

referenced by:

* GatewayNode

**GatewayWhenList:**

![GatewayWhenList](diagram/GatewayWhenList.svg)

```
GatewayWhenList
         ::= GatewayWhen [GatewyWhnLis]
```

referenced by:

* ExclusiveGatewayNode
* GatewayStmtExclusive

**GatewayWhen:**

![GatewayWhen](diagram/GatewayWhen.svg)

```
GatewayWhen
         ::= 'when' Expression '->' ID ';'?
```

referenced by:

* GatewayWhenList

**GatewayElseOpt:**

![GatewayElseOpt](diagram/GatewayElseOpt.svg)

```
GatewayElseOpt
         ::= GatewayElse?
```

**GatewayElse:**

![GatewayElse](diagram/GatewayElse.svg)

```
GatewayElse
         ::= 'else' '->' ID ';'?
```

referenced by:

* GatewayElseOpt

**ParallelGatewayNode:**

![ParallelGatewayNode](diagram/ParallelGatewayNode.svg)

```
ParallelGatewayNode
         ::= 'gateway' ID 'parallel' '{' GatewayBranchList GatewayJoin '}'
```

referenced by:

* GatewayNode

**GatewayBranchList:**

![GatewayBranchList](diagram/GatewayBranchList.svg)

```
GatewayBranchList
         ::= GatewayBranch [GatewyBrnchLis]
```

referenced by:

* GatewayStmtParallel
* ParallelGatewayNode

**GatewayBranch:**

![GatewayBranch](diagram/GatewayBranch.svg)

```
GatewayBranch
         ::= 'branch' '->' ID ';'?
```

referenced by:

* GatewayBranchList

**GatewayJoin:**

![GatewayJoin](diagram/GatewayJoin.svg)

```
GatewayJoin
         ::= 'join' '->' ID ';'?
```

referenced by:

* GatewayStmtParallel
* ParallelGatewayNode

**Block:**

![Block](diagram/Block.svg)

```
Block    ::= '{' [StaemnLis] '}'
```

referenced by:

* ElseIfList
* ElseOpt
* ForEachStmt
* ForRangeStmt
* FunctionDecl
* IfStmt
* TryCatchStmt
* WhileStmt

**Statement:**

![Statement](diagram/Statement.svg)

```
Statement
         ::= AssignStmt
           | GotoStmt
           | IfStmt
           | WhileStmt
           | ForEachStmt
           | ForRangeStmt
           | TryCatchStmt
           | ThrowStmt
           | ReturnStmt
           | GatewayStmtInAction
           | BlockStmt
           | ExprStmt
```

referenced by:

* StatementList

**AssignStmt:**

![AssignStmt](diagram/AssignStmt.svg)

```
AssignStmt
         ::= LValue '=' Expression ';'?
```

referenced by:

* Statement

**GotoStmt:**

![GotoStmt](diagram/GotoStmt.svg)

```
GotoStmt ::= 'go_to' ID ';'?
```

referenced by:

* Statement

**IfStmt:**

![IfStmt](diagram/IfStmt.svg)

```
IfStmt   ::= 'if' Expression Block [ElseIfLit] [ElseOpt]
```

referenced by:

* Statement

**ElseIfList:**

![ElseIfList](diagram/ElseIfList.svg)

```
ElseIfList
         ::= 'else_if' Expression Block [ElseIfLit]
```

**ElseOpt:**

![ElseOpt](diagram/ElseOpt.svg)

```
ElseOpt  ::= ( 'else' Block )?
```

**WhileStmt:**

![WhileStmt](diagram/WhileStmt.svg)

```
WhileStmt
         ::= 'while' Expression Block
```

referenced by:

* Statement

**ForEachStmt:**

![ForEachStmt](diagram/ForEachStmt.svg)

```
ForEachStmt
         ::= 'for each' ID 'in' Expression Block
```

referenced by:

* Statement

**ForRangeStmt:**

![ForRangeStmt](diagram/ForRangeStmt.svg)

```
ForRangeStmt
         ::= 'for' ID 'from' Expression 'to' Expression ['step Exrion]? Block
```

referenced by:

* Statement

**TryCatchStmt:**

![TryCatchStmt](diagram/TryCatchStmt.svg)

```
TryCatchStmt
         ::= 'try' Block 'catch' '(' ID ')' Block
```

referenced by:

* Statement

**ThrowStmt:**

![ThrowStmt](diagram/ThrowStmt.svg)

```
ThrowStmt
         ::= 'throw' Expression ';'?
```

referenced by:

* Statement

**ReturnStmt:**

![ReturnStmt](diagram/ReturnStmt.svg)

```
ReturnStmt
         ::= 'return' [Expresion] ';'?
```

referenced by:

* Statement

**GatewayStmtInAction:**

![GatewayStmtInAction](diagram/GatewayStmtInAction.svg)

```
GatewayStmtInAction
         ::= GatewayStmtExclusive
           | GatewayStmtParallel
```

referenced by:

* Statement

**GatewayStmtExclusive:**

![GatewayStmtExclusive](diagram/GatewayStmtExclusive.svg)

```
GatewayStmtExclusive
         ::= 'gateway' ID '{' GatewayWhenList [GatewyElsOp] '}'
```

referenced by:

* GatewayStmtInAction

**GatewayStmtParallel:**

![GatewayStmtParallel](diagram/GatewayStmtParallel.svg)

```
GatewayStmtParallel
         ::= 'gateway' ID 'parallel' '{' GatewayBranchList GatewayJoin '}'
```

referenced by:

* GatewayStmtInAction

**Expression:**

![Expression](diagram/Expression.svg)

```
Expression
         ::= OrExpr
           | AndExpr
           | EqExpr
           | RelExpr
           | AddExpr
           | MulExpr
           | UnaryNotExpr
           | UnaryMinusExpr
           | MemberAccessExpr
           | IndexAccessExpr
           | CallExpr
           | PrimaryExpr
```

referenced by:

* AssignStmt
* ElseIfList
* ForEachStmt
* ForRangeStmt
* GatewayWhen
* GlobalVarDecl
* IfStmt
* LValueRest
* ListExpression
* ListExpressionRest
* ObjectPair
* PrimaryExpr
* ThrowStmt
* WhileStmt

**PrimaryExpr:**

![PrimaryExpr](diagram/PrimaryExpr.svg)

```
PrimaryExpr
         ::= '(' Expression ')'
           | ListLiteral
           | ObjectLiteral
           | Literal
           | 'input'
           | ID
```

referenced by:

* Expression

**ListLiteral:**

![ListLiteral](diagram/ListLiteral.svg)

```
ListLiteral
         ::= '[' [ListExpreon] ']'
```

referenced by:

* PrimaryExpr

**ListExpression:**

![ListExpression](diagram/ListExpression.svg)

```
ListExpression
         ::= Expression [ListExpreonR]
```

**ListExpressionRest:**

![ListExpressionRest](diagram/ListExpressionRest.svg)

```
ListExpressionRest
         ::= ',' Expression [ListExpreonR]
```

**ObjectLiteral:**

![ObjectLiteral](diagram/ObjectLiteral.svg)

```
ObjectLiteral
         ::= '{' [ObjectPairLs] '}'
```

referenced by:

* PrimaryExpr

**ObjectPairList:**

![ObjectPairList](diagram/ObjectPairList.svg)

```
ObjectPairList
         ::= ObjectPair [ObjectPairLsR]
```

**ObjectPairListRest:**

![ObjectPairListRest](diagram/ObjectPairListRest.svg)

```
ObjectPairListRest
         ::= ',' ObjectPair [ObjectPairLsR]
```

**ObjectPair:**

![ObjectPair](diagram/ObjectPair.svg)

```
ObjectPair
         ::= ID ':' Expression
```

referenced by:

* ObjectPairList
* ObjectPairListRest

**Literal:**

![Literal](diagram/Literal.svg)

```
Literal  ::= INT
           | DECIMAL
           | STRING
           | BOOL
           | 'null'
```

referenced by:

* PrimaryExpr

**LValue:**

![LValue](diagram/LValue.svg)

```
LValue   ::= ID [LValueRst]
```

referenced by:

* AssignStmt
* GlobalVarDecl

**LValueRest:**

![LValueRest](diagram/LValueRest.svg)

```
LValueRest
         ::= ( '.' ID | '[' Expression ']' ) [LValueRst]
```

## 
![Railroad-Diagram-Generator](diagram/Railroad-Diagram-Generator.svg) <sup>generated by [RR - Railroad Diagram Generator][RR]</sup>

[RR]: https://www.bottlecaps.de/rr/ui