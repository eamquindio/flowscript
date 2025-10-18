# FlowScript Language Grammar (BNF)

This document defines the complete grammar for the FlowScript language using Backus-Naur Form (BNF) notation.

## Program Structure

```
program ::= declaration*

declaration ::= import-declaration
              | function-declaration
              | process-declaration
              | variable-declaration
```

## Import Declarations

```
import-declaration ::= "import" string-literal ("as" identifier)?
                    | "import_jar" string-literal "as" identifier
```

## Function Declarations

```
function-declaration ::= "function" identifier "(" parameter-list? ")" ("->" type)? block

parameter-list ::= parameter ("," parameter)*

parameter ::= identifier ":" type

type ::= "integer" | "decimal" | "boolean" | "text" | "list" | "object" | "void"
```

## Process Declarations

```
process-declaration ::= "process" identifier "{" process-body "}"

process-body ::= process-element*

process-element ::= start-element
                  | task-element
                  | gateway-element
                  | end-element

start-element ::= "start" "->" identifier

task-element ::= "task" identifier "{" task-body "}"

task-body ::= "action:" statement-list

gateway-element ::= exclusive-gateway
                  | parallel-gateway

exclusive-gateway ::= "gateway" identifier "{" when-clause* else-clause? "}"

when-clause ::= "when" expression "->" identifier

else-clause ::= "else" "->" identifier

parallel-gateway ::= "gateway" identifier "parallel" "{" parallel-branch* join-clause "}"

parallel-branch ::= "branch" "->" identifier

join-clause ::= "join" "->" identifier

end-element ::= "end" identifier
```

## Statements

```
statement-list ::= statement*

statement ::= expression-statement
            | if-statement
            | try-statement
            | throw-statement
            | return-statement
            | goto-statement
            | for-statement
            | variable-declaration
            | block

expression-statement ::= expression

if-statement ::= "if" expression statement ("else_if" expression statement)* ("else" statement)?

try-statement ::= "try" block "catch" "(" identifier ")" block

throw-statement ::= "throw" expression

return-statement ::= "return" expression?

goto-statement ::= "go_to" identifier

for-statement ::= "for" "each" identifier "in" expression statement

variable-declaration ::= identifier "=" expression

block ::= "{" statement-list "}"
```

## Expressions

```
expression ::= ternary-expression

ternary-expression ::= logical-or-expression ("?" expression ":" expression)?

logical-or-expression ::= logical-and-expression ("or" logical-and-expression)*

logical-and-expression ::= equality-expression ("and" equality-expression)*

equality-expression ::= relational-expression (("==" | "!=") relational-expression)*

relational-expression ::= additive-expression (("<" | ">" | "<=" | ">=") additive-expression)*

additive-expression ::= multiplicative-expression (("+" | "-") multiplicative-expression)*

multiplicative-expression ::= unary-expression (("*" | "/" | "%") unary-expression)*

unary-expression ::= ("not" | "-")? postfix-expression

postfix-expression ::= primary-expression postfix-operator*

postfix-operator ::= "." identifier
                   | "[" expression "]"
                   | "(" argument-list? ")"

primary-expression ::= identifier
                     | literal
                     | object-literal
                     | list-literal
                     | "(" expression ")"

argument-list ::= expression ("," expression)*
```

## Literals

```
literal ::= integer-literal
          | decimal-literal
          | boolean-literal
          | string-literal
          | null-literal

integer-literal ::= digit+ ("_" digit+)*

decimal-literal ::= digit+ "." digit+ (("e" | "E") ("+" | "-")? digit+)?
                  | digit+ ("e" | "E") ("+" | "-")? digit+

boolean-literal ::= "true" | "false"

string-literal ::= '"' string-character* '"'

string-character ::= any-character-except-quote-and-backslash
                   | escape-sequence

escape-sequence ::= "\" ('"' | "\" | "n" | "r" | "t" | "u" hex-digit hex-digit hex-digit hex-digit)

null-literal ::= "null"

object-literal ::= "{" object-member-list? "}"

object-member-list ::= object-member ("," object-member)*

object-member ::= identifier ":" expression
                | string-literal ":" expression

list-literal ::= "[" expression-list? "]"

expression-list ::= expression ("," expression)*
```

## Lexical Elements

```
identifier ::= letter (letter | digit | "_")*

letter ::= "a" | "b" | "c" | "d" | "e" | "f" | "g" | "h" | "i" | "j" | "k" | "l" | "m" |
          "n" | "o" | "p" | "q" | "r" | "s" | "t" | "u" | "v" | "w" | "x" | "y" | "z" |
          "A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" | "J" | "K" | "L" | "M" |
          "N" | "O" | "P" | "Q" | "R" | "S" | "T" | "U" | "V" | "W" | "X" | "Y" | "Z"

digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"

hex-digit ::= digit | "a" | "b" | "c" | "d" | "e" | "f" | "A" | "B" | "C" | "D" | "E" | "F"
```

## Keywords (Reserved Words)

### Structure Keywords
- `import`, `import_jar`, `as`, `function`, `return`, `process`

### Flow Control Keywords
- `start`, `end`, `task`, `gateway`, `go_to`, `when`, `branch`, `join`, `else`

### Control Flow Keywords
- `if`, `else_if`, `try`, `catch`, `throw`, `for`, `each`, `in`

### Type Keywords
- `integer`, `decimal`, `boolean`, `text`, `list`, `object`, `void`

### Literal Keywords
- `true`, `false`, `null`

### Operator Keywords
- `and`, `or`, `not`

## Operators and Delimiters

### Arithmetic Operators
- `+`, `-`, `*`, `/`, `%`

### Comparison Operators
- `<`, `>`, `<=`, `>=`, `==`, `!=`

### Logical Operators (keywords)
- `and`, `or`, `not`

### Assignment Operator
- `=`

### Ternary Operators
- `?`, `:`

### Access Operators
- `.`, `[`, `]`

### Delimiters
- `(`, `)`, `{`, `}`, `[`, `]`, `,`, `;`, `:`

### Arrow Operator
- `->`

## Comments

```
single-line-comment ::= "#" any-character-except-newline* newline

multi-line-comment ::= "/*" any-character* "*/"
```

## Whitespace

```
whitespace ::= space | tab | newline | carriage-return
```

## Operator Precedence (Highest to Lowest)

1. **15** - Access/Indexing: `.`, `[`
2. **14** - Function Call: `(`
3. **13** - Unary: `not`, `-`
4. **12** - Multiplicative: `*`, `/`, `%`
5. **11** - Additive: `+`, `-`
6. **10** - Relational: `<`, `>`, `<=`, `>=`
7. **9** - Equality: `==`, `!=`
8. **8** - Logical AND: `and`
9. **7** - Logical OR: `or`
10. **6** - Ternary: `?`, `:`
11. **1** - Assignment: `=`

## Language Features Summary

### 1. Modular Imports
- Standard library imports: `import "std/json" as Json`
- Java JAR integration: `import_jar "libs/security.jar" as Security`

### 2. Functions
- Type annotations: `function name(param: type) -> return_type`
- Local variables and recursion supported

### 3. Processes (BPMN-like)
- `start -> task` flow modeling
- `task` elements with action blocks
- `gateway` elements (exclusive/parallel)
- `end` states
- `go_to` navigation between tasks

### 4. Data Types
- **Primitive**: `integer`, `decimal`, `boolean`, `text`
- **Complex**: `list`, `object`
- **Special**: `null`, `void`

### 5. Control Structures
- **Conditionals**: `if`/`else_if`/`else`
- **Loops**: `for each...in`
- **Error handling**: `try`/`catch`/`throw`
- **Early returns** and `go_to` navigation

### 6. Expressions
- Full operator precedence
- Ternary conditional operator
- Object and list literals
- Property access and method calls

### 7. Comments
- **Single line**: `# comment`
- **Multi-line**: `/* comment */`