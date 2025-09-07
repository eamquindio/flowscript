# FlowScript Language Summary for Grammar Design

## Table of Contents
1. [Language Philosophy](#1-language-philosophy)
2. [Lexical Elements](#2-lexical-elements)
3. [Data Types and Literals](#3-data-types-and-literals)
4. [Operators and Precedence](#4-operators-and-precedence)
5. [Language Constructs](#5-language-constructs)
6. [Functions](#6-functions)
7. [Processes](#7-processes)
8. [Control Flow](#8-control-flow)
9. [Error Handling](#9-error-handling)
10. [Modules and Imports](#10-modules-and-imports)
11. [Syntax Examples](#11-syntax-examples)
12. [Grammar Design Requirements](#12-grammar-design-requirements)

---

## 1. Language Philosophy

FlowScript is a domain-specific language that unifies:
- **Business Process Modeling** (BPMN-style workflows)
- **Executable Implementation** (traditional programming)

**Key Principle**: One `.flow` file contains both process structure and implementation logic.

---

## 2. Lexical Elements

### 2.1 Character Set
- UTF-8 encoding
- Unicode support in string literals and comments

### 2.2 Comments
```flowscript
# Single-line comment
/* Multi-line
   comment */
```

### 2.3 Identifiers
**Pattern**: `[a-zA-Z_][a-zA-Z0-9_]*`
- Case-sensitive
- Must start with letter or underscore
- Can contain letters, digits, underscores

**Valid**: `customer_id`, `_temp`, `ProcessStep3`  
**Invalid**: `3steps`, `total-amount`, `my variable`

### 2.4 Reserved Keywords

**Structure Keywords:**
```
process, function, import, import_jar, as, return
```

**Flow Control Keywords:**
```
start, end, task, gateway, goto, when, branch, join, else
```

**Control Flow Keywords:**
```
if, else_if, try, catch, throw, while, for, each, in, from, to, step, break, continue
```

**Data Type Keywords:**
```
integer, decimal, boolean, text, list, object, null, void, true, false
```

**Logical Operators:**
```
and, or, not
```

**Future Reserved:**
```
async, await, event, class
```

### 2.5 Operators and Punctuation

**Arithmetic Operators:**
```
+ - * / %
```

**Comparison Operators:**
```
< > <= >= == !=
```

**Logical Operators:**
```
and or not
```

**Assignment Operator:**
```
=
```

**Access Operators:**
```
. []
```

**Delimiters:**
```
( ) { } [ ] , ; : ->
```

### 2.6 Whitespace
- Spaces, tabs, newlines
- Not syntactically significant (except for line termination)
- Used only for token separation

---

## 3. Data Types and Literals

### 3.1 Primitive Types

#### Integer
- 64-bit signed integer
- **Literals**: `42`, `1_000_000`, `-17`

#### Decimal
- IEEE 754 double-precision floating-point
- **Literals**: `3.14`, `1.23e-5`, `-0.5`

#### Boolean
- **Literals**: `true`, `false`

#### Text (String)
- Unicode string sequence
- **Literals**: `"Hello World"`, `"Line 1\nLine 2"`, `""`

#### Null
- Represents absence of value
- **Literal**: `null`

### 3.2 Composite Types

#### List
- Ordered collection (dynamic array)
- **Syntax**: `[element1, element2, ..., elementN]`
- **Examples**: `[1, 2, 3]`, `["a", "b"]`, `[]`

#### Object
- Key-value pairs (dictionary/map)
- **Syntax**: `{ key1: value1, key2: value2, ..., keyN: valueN }`
- **Examples**: `{ name: "John", age: 30 }`, `{}`

### 3.3 Escape Sequences in Strings
```
\n  - newline
\t  - tab
\r  - carriage return
\"  - double quote
\\  - backslash
\uXXXX - Unicode character (4 hex digits)
```

---

## 4. Operators and Precedence

### 4.1 Operator Precedence (Highest to Lowest)

| Level | Operators | Associativity | Description |
|-------|-----------|---------------|-------------|
| 15 | `.` `[]` | Left | Member access, indexing |
| 14 | `()` | Left | Function call |
| 13 | `not` `-` (unary) | Right | Logical negation, unary minus |
| 12 | `*` `/` `%` | Left | Multiplication, division, modulo |
| 11 | `+` `-` | Left | Addition, subtraction |
| 10 | `<` `>` `<=` `>=` | Left | Relational comparison |
| 9 | `==` `!=` | Left | Equality comparison |
| 8 | `and` | Left | Logical AND |
| 7 | `or` | Left | Logical OR |
| 1 | `=` | Right | Assignment |

### 4.2 Operator Behavior

**Short-circuit evaluation**:
- `and`: If left operand is false, right operand is not evaluated
- `or`: If left operand is true, right operand is not evaluated

**Type conversion**:
- Integer to decimal: Implicit when mixed in arithmetic
- String concatenation: `+` operator with text operands
- Boolean context: `null`, `0`, `0.0`, `""`, `false` → false; everything else → true

---

## 5. Language Constructs

### 5.1 Top-Level Structure
A FlowScript program consists of:
- Import statements (optional)
- Function declarations
- Process declarations

### 5.2 Variable Declaration
- Implicit declaration through assignment
- No explicit type declarations for variables
- Variables are dynamically typed

**Examples**:
```flowscript
count = 42
name = "Alice"
data = { id: 1, active: true }
```

### 5.3 Scope Rules

#### Function Scope
- Variables declared in functions are local to that function
- Block-scoped variables (if, while, for blocks) are function-scoped

#### Process Context Scope
- Variables in process tasks are stored in shared process context
- Available across all tasks in the same process instance

---

## 6. Functions

### 6.1 Function Declaration Syntax
```
function function_name(parameter_list) -> return_type {
    function_body
}
```

### 6.2 Components

#### Parameters
- **Syntax**: `parameter_name: type`
- **Example**: `name: text, age: integer`

#### Return Types
```
integer, decimal, boolean, text, list, object, void
```

#### Function Body
- Sequence of statements
- Must return value for non-void functions

### 6.3 Function Calls
- **Syntax**: `function_name(argument_list)`
- **Arguments**: Comma-separated expressions

### 6.4 Recursion
- Functions can call themselves
- Both direct and mutual recursion supported

---

## 7. Processes

### 7.1 Process Declaration Syntax
```
process ProcessName {
    process_elements
}
```

### 7.2 Process Elements

#### Start Node
- **Syntax**: `start -> FirstTaskName`
- **Rule**: Exactly one per process
- **Purpose**: Entry point of the process

#### Task Node
```
task TaskName {
    action:
        statements
        goto NextNode
}
```
- Contains executable statements
- Must end with `goto` to next node

#### End Node
- **Syntax**: `end EndName`
- **Rule**: One or more per process
- **Purpose**: Exit points representing different outcomes

#### Gateway Node
Two types: Exclusive (XOR) and Parallel (AND)

**Exclusive Gateway:**
```
gateway GatewayName {
    when condition1 -> Task1
    when condition2 -> Task2
    else -> DefaultTask
}
```

**Parallel Gateway:**
```
gateway GatewayName parallel {
    branch -> Task1
    branch -> Task2
    branch -> Task3
    join -> ConsolidationTask
}
```

### 7.3 Process Context

#### Input Object
- Special object `input` contains initial process data
- Available in all tasks

#### Shared Variables
- Variables created in one task are available in subsequent tasks
- Stored in process execution context

#### Goto Statement
- **Syntax**: `goto NodeName`
- **Rule**: Only valid within task action blocks
- **Purpose**: Transfer control to another process node

---

## 8. Control Flow

### 8.1 Conditional Statements

#### If Statement
```
if condition {
    statements
} else_if condition2 {
    statements
} else {
    statements
}
```

### 8.2 Loop Statements

#### While Loop
```
while condition {
    statements
}
```

#### For-Each Loop
```
for each element in collection {
    statements
}
```

#### Range-Based For Loop
```
for variable from start_expr to end_expr step step_expr {
    statements
}
```

### 8.3 Control Transfer Statements
- `break`: Exit loop
- `continue`: Skip to next iteration
- `return expression?`: Return from function
- `goto identifier`: Transfer control (only in processes)

---

## 9. Error Handling

### 9.1 Try-Catch Structure
```
try {
    statements
} catch (error_variable) {
    error_handling_statements
}
```

### 9.2 Throw Statement
```
throw expression
```

### 9.3 Error Objects
- Thrown errors are objects
- Common properties: `type`, `message`
- Can include additional custom properties

---

## 10. Modules and Imports

### 10.1 FlowScript Module Import
```
import "module_path"
import "module_path" as Alias
```

### 10.2 JAR Integration
```
import_jar "jar_path" as Alias
```

### 10.3 Standard Library
Common modules:
- `std/io`: Input/output operations
- `std/http`: HTTP client operations
- `std/json`: JSON processing
- `std/db`: Database operations
- `std/dates`: Date and time operations

---

## 11. Syntax Examples

### 11.1 Complete Function Example
```flowscript
function calculate_total(items: list, tax_rate: decimal) -> decimal {
    subtotal = 0.0
    
    for each item in items {
        subtotal = subtotal + item.price * item.quantity
    }
    
    tax = subtotal * tax_rate
    return subtotal + tax
}
```

### 11.2 Complete Process Example
```flowscript
process OrderValidation {
    start -> ValidateInput
    
    task ValidateInput {
        action:
            if input.customer_id == null {
                error_message = "Customer ID required"
                goto ValidationError
            }
            
            if input.items.length() == 0 {
                error_message = "No items in order"
                goto ValidationError
            }
            
            goto CheckInventory
    }
    
    task CheckInventory {
        action:
            all_available = true
            
            for each item in input.items {
                if get_stock(item.id) < item.quantity {
                    all_available = false
                }
            }
            
            gateway InventoryDecision {
                when all_available -> ProcessOrder
                else -> InsufficientStock
            }
    }
    
    task ProcessOrder {
        action:
            order_id = create_order(input)
            goto Success
    }
    
    task ValidationError {
        action:
            log_error(error_message)
            goto Error
    }
    
    task InsufficientStock {
        action:
            notify_customer("Insufficient stock")
            goto Error
    }
    
    end Success
    end Error
}
```

### 11.3 Complete Program Example
```flowscript
import "std/db" as DB
import "validators" as V

function validate_email(email: text) -> boolean {
    return email.contains("@") and email.length() > 5
}

process UserRegistration {
    start -> ValidateData
    
    task ValidateData {
        action:
            if not validate_email(input.email) {
                goto InvalidEmail
            }
            
            if V.check_password_strength(input.password) < 3 {
                goto WeakPassword
            }
            
            goto SaveUser
    }
    
    task SaveUser {
        action:
            try {
                user_id = DB.insert("users", {
                    email: input.email,
                    password: hash_password(input.password)
                })
                goto Success
            } catch (e) {
                error_details = e.message
                goto DatabaseError
            }
    }
    
    end Success
    end InvalidEmail
    end WeakPassword
    end DatabaseError
}
```

---

## 12. Grammar Design Requirements

### 12.1 Key Considerations for Students

#### Lexical Analysis
- Define token types for all keywords, operators, literals, and identifiers
- Handle comments (single-line and multi-line)
- Manage whitespace and line termination
- Implement escape sequence processing for strings

#### Syntactic Analysis
- Handle operator precedence correctly
- Eliminate left recursion for top-down parsers
- Resolve ambiguities (dangling else, expression vs statement)
- Support proper nesting of constructs

#### Semantic Considerations
- Variable scope rules (function vs process context)
- Type compatibility for operations
- Control flow validation (goto targets must exist)
- Function signature matching

### 12.2 Grammar Structure Requirements

#### Must Support
1. **Program structure**: Multiple top-level declarations
2. **Function definitions**: With parameters, return types, and bodies
3. **Process definitions**: With all node types and flow control
4. **Expression hierarchy**: Respecting operator precedence
5. **Statement types**: All control flow and assignment statements
6. **Composite literals**: Lists and objects with proper nesting
7. **Import statements**: Both FlowScript modules and JAR files
8. **Error handling**: Try-catch blocks with proper scoping

#### Expression Grammar Complexity
- Must handle all operator precedence levels
- Support function calls with arbitrary argument lists
- Allow member access and indexing operations
- Handle unary and binary operators correctly
- Support parenthetical grouping

#### Statement Grammar Complexity
- Nested control structures (if-else, loops)
- Try-catch error handling
- Various statement types within blocks
- Process-specific statements (goto)

#### Process Grammar Complexity
- Different node types with specific syntax
- Gateway alternatives (exclusive vs parallel)
- Proper flow connections between nodes
- Action blocks containing regular statements

### 12.3 Ambiguity Resolution Strategies

Students must address these potential ambiguities:

1. **Dangling else**: Associate else with nearest if
2. **Function calls vs indexing**: `func()` vs `array[index]`
3. **Object literals vs blocks**: `{ }` as object vs statement block
4. **Expression statements**: When expressions can stand alone
5. **Gateway types**: Default exclusive vs explicit parallel

### 12.4 Error Recovery Considerations

Good grammars should facilitate:
- Meaningful error messages
- Error recovery at statement boundaries
- Synchronization tokens for parser recovery
- Detection of missing delimiters

---

## Course Work Assignment

### Work Division

The course will be divided into **TWO TEAMS**:

#### **TEAM A: FlowScript Functions** (First half of the class)
- **Responsibility**: Create the complete grammar for the functions system
- **Target file**: `FlowScriptFunctions.g4`
- **Program structure**: Functions only and related declarations

#### **TEAM B: FlowScript Processes** (Second half of the class)
- **Responsibility**: Create the complete grammar for the processes system
- **Target file**: `FlowScriptProcesses.g4` 
- **Program structure**: Complete processes including imports and global variables

---

## Instructions for TEAM A: FlowScript Functions

### 🎯 **Main Objective**
Create an ANTLR v4 grammar that can parse **exclusively FlowScript functions** with all their syntactic complexity.

### 📋 **Specific Responsibilities**

#### 1. **Program Structure**
- The program must start with `functionProgram` as the initial rule
- Must support multiple function declarations in sequence
- Each function is an independent and complete unit

#### 2. **Function Declarations**
- Syntax: `function name(parameters) -> return_type { body }`
- Typed parameters with syntax: `name: type`
- Optional return types (use `-> void` or omit completely)
- Support for functions without parameters and functions with multiple parameters

#### 3. **Data Types**
Implement **ALL** primitive types:
- `integer`, `decimal`, `boolean`, `text`, `list`, `object`, `void`

#### 4. **Complex Expressions**
Implement **ALL** the operator precedence hierarchy:
- Level 15: `.` `[]` (member access, indexing)
- Level 14: `()` (function calls)  
- Level 13: `not` `-` (negation, unary minus)
- Level 12: `*` `/` `%` (multiplication, division, modulo)
- Level 11: `+` `-` (addition, subtraction)
- Level 10: `<` `>` `<=` `>=` (relational comparison)
- Level 9: `==` `!=` (equality)
- Level 8: `and` (logical AND)
- Level 7: `or` (logical OR) 
- Level 1: `=` (assignment)

#### 5. **Control Structures**
- **If-else**: `if condition { } else_if condition { } else { }`
- **Loops**: `while`, `for each element in collection`, `for variable from start to end step increment`
- **Control**: `break`, `continue`, `return expression?`

#### 6. **Error Handling**
- **Try-catch**: `try { } catch (variable) { }`
- **Throw**: `throw expression`

#### 7. **Composite Literals**
- **Lists**: `[element1, element2, elementN]` (no trailing comma)
- **Objects**: `{ key1: value1, key2: value2 }` (no trailing comma)
- Support for arbitrary nesting

#### 8. **Recursion**
- Functions must be able to call themselves
- Support for mutual recursion between functions

### ⚠️ **Important Restrictions**
- **DO NOT implement**: `import`, `import_jar`, `process`, `goto`, `input`, `start`, `end`, `task`, `gateway`
- **DO NOT include**: Any process-related functionality
- **Focus**: Traditional function logic only

### 🧪 **Evaluation Criteria**
1. **Syntactic completeness**: All function elements implemented
2. **Correct precedence**: Operators with correct precedence and associativity
3. **Recursion**: Functional recursive functions
4. **Complex structures**: Nested if statements, complex loops, try-catch
5. **Composite literals**: Lists and objects with deep nesting
6. **Validation**: No parsing errors for valid code, appropriate errors for invalid code

---

## Instructions for TEAM B: FlowScript Processes

### 🎯 **Main Objective**  
Create an ANTLR v4 grammar that can parse **complete FlowScript programs** with emphasis on processes and program structure.

### 📋 **Specific Responsibilities**

#### 1. **Complete Program Structure**
- The program must start with `program` as the initial rule
- Sequence: `imports* declarations*`
- Support for empty files and valid combinations

#### 2. **Import System**
- **FlowScript modules**: `import "path"`, `import "path" as Alias`
- **JAR integration**: `import_jar "path.jar" as Alias`
- Path and alias syntax validation

#### 3. **Global Variables**
- Syntax: `identifier = expression`
- Available at program level
- Mandatory initialization

#### 4. **Process Declarations**
- **Mandatory structure**: exactly one `start`, at least one `end`, intermediate tasks
- **Syntax**: `process ProcessName { process_elements }`
- **Structural validation**: Every process must be well-formed

#### 5. **Process Nodes**
- **Start**: `start -> NextNode`
- **Task**: `task Name { action: statements }`
- **End**: `end Name` (multiple allowed)
- **Gateway**: Exclusive and parallel

#### 6. **Complex Gateways**
- **Exclusive (XOR)**: 
  ```
  gateway Name {
      when condition1 -> Node1
      when condition2 -> Node2  
      else -> DefaultNode
  }
  ```
- **Parallel (AND)**:
  ```
  gateway Name parallel {
      branch -> Node1
      branch -> Node2
      join -> ConsolidationNode  
  }
  ```

#### 7. **Process Flow Control**
- **Goto**: `goto NodeName` (only valid within task actions)
- **Input object**: `input.property` to access process data
- **Shared variables**: Between tasks in the same process

#### 8. **Simplified Functions**
- Basic declaration: `function name(params) -> type { body }`
- **Only what's necessary** for the program to compile
- Don't implement full complexity (that's Team A's responsibility)

#### 9. **Process Context Expressions**
- All expressions necessary for gateway conditions
- Access to `input` object with dot notation
- Comparisons and boolean logic for decision making

### ⚠️ **Important Restrictions**
- **Functions**: Implement only the basic structure needed
- **Main focus**: Processes, gateways, flow control
- **Complexity**: In orchestration, not in internal logic

### 🧪 **Evaluation Criteria**
1. **Complete structure**: Programs with imports, global variables, processes
2. **Process validation**: Mandatory structure (start, tasks, ends)
3. **Functional gateways**: Both types with correct syntax
4. **Flow control**: Goto and navigation between nodes
5. **Input object**: Correct access to process data  
6. **Integration**: The program must be syntactically complete

---

## Resources and Methodology

### 🛠️ **Required Tools**
- **ANTLR v4**: For lexer and parser generation
- **Maven**: Configured build system
- **JUnit 5**: For automated testing (already configured)

### 🚀 **Maven Commands for Testing Grammar**

To generate ANTLR classes and run tests, use these Maven commands:

```bash
# Generate ANTLR lexer and parser classes from .g4 files
mvn clean generate-sources

# Compile the project including generated sources
mvn compile

# Run all tests to validate your grammar
mvn test

# Run tests for a specific team (Functions or Processes)
mvn test -Dtest="*Functions*"  # For Team A
mvn test -Dtest="*Processes*"  # For Team B

# Complete build with tests
mvn clean compile test
```

**Important**: Always run `mvn clean generate-sources` after modifying your `.g4` grammar file to regenerate the parser classes.

### 📚 **Work Methodology**
1. **Analysis**: Study the corresponding specification
2. **BNF Design**: Create the grammatical structure in BNF notation using https://www.bottlecaps.de/rr/ui
   - Use the "Edit Grammar" section to write your BNF
   - Use the "View Grammar" section to download the railroad diagram in embedded markdown
   - Save the markdown file in the `tlf/` folder alongside your `.g4` files
3. **Implementation**: Convert BNF to ANTLR `.g4` file
4. **Testing**: Use automated tests for validation
5. **Refinement**: Iterate until all tests pass

### 🔀 **Git Workflow**

Each team must work on separate branches and submit their work via Pull Request:

#### **Team A (Functions)**
- Create and work on branch: `antlrfunctions`
- Submit Pull Request from `antlrfunctions` → `main`

#### **Team B (Processes)**  
- Create and work on branch: `antlrprocess`
- Submit Pull Request from `antlrprocess` → `main`

**Important**: All development work must be done on the designated team branch. The Pull Request will be reviewed before merging to main.

### ✅ **Deliverables**
- **Team A**:  
  - Complete and functional `FlowScriptFunctions.g4` on `feature/antlrfunctions` branch
  - BNF railroad diagram in markdown format (saved in `tlf/` folder)
- **Team B**: 
  - Complete and functional `FlowScriptProcesses.g4` on `feature/antlrprocess` branch
  - BNF railroad diagram in markdown format (saved in `tlf/` folder)
- **Both teams**: 100% passing automated tests
- **Both teams**: Pull Request from team branch to `main`

### 🎯 **Final Goal**
At the end of the course, both grammars should integrate to form a complete parser for the FlowScript language, covering both traditional programming functionality and business process orchestration.

---

## Summary

FlowScript combines traditional programming constructs with workflow modeling in a single language. The grammar must handle:

- **Dual nature**: Functions (traditional) + Processes (workflow)
- **Rich expression system**: With 9 precedence levels
- **Complex control flow**: Including process-specific goto statements
- **Composite data types**: Lists and objects with arbitrary nesting
- **Module system**: For code organization and reuse
- **Error handling**: Try-catch with flexible error objects

Students should design their grammar to be **unambiguous**, **complete**, and **parseable** while maintaining the language's expressiveness and readability.