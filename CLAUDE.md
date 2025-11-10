# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

FlowScript is a process-oriented programming language inspired by BPMN (Business Process Model and Notation). The project consists of:
- **Language Implementation**: Custom lexer and parser for FlowScript syntax
- **IDE**: JavaFX-based editor with syntax highlighting and project management
- **Dual Grammar**: Two ANTLR grammars (FlowScriptFunctions.g4 and FlowScriptProcesses.g4)

## Build Commands

### Essential Commands

```bash
# Generate ANTLR parsers from grammars (REQUIRED before first compile)
mvn antlr4:antlr4

# Clean and compile (includes ANTLR generation)
mvn clean compile

# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=FlowScriptFunctionsTest
mvn test -Dtest=FlowScriptProcessesTest

# Run the IDE
mvn javafx:run

# Create executable JAR with all dependencies
mvn clean package
```

### Running Single Tests

```bash
# Run a specific test method
mvn test -Dtest=LexerTest#testKeywordRecognition

# Run tests matching a pattern
mvn test -Dtest=*ParserTest
```

## Architecture

### Three-Layer Analysis Pipeline

The project implements a **custom compiler frontend** without using ANTLR for parsing (ANTLR grammars exist but for reference/testing):

1. **Lexical Analysis** (`com.flowscript.lexer`)
   - **AFD-based tokenization** using finite automata recognizers
   - Each token type has a dedicated recognizer (KeywordRecognizer, OperatorRecognizer, etc.)
   - Recognizers are prioritized to handle overlapping patterns correctly
   - Entry point: `Lexer.java` coordinates all recognizers

2. **Syntactic Analysis** (`com.flowscript.sintactic`)
   - **Recursive descent parser** with specialized parsers for each grammar rule
   - **Parser-per-rule pattern**: Each grammar production has its own parser class
   - Produces an **Abstract Syntax Tree (AST)** with typed nodes
   - Entry point: `Parser.java` → `ProgramParser.java`
   - Parser hierarchy follows grammar structure:
     - `programa_declaraciones`: Top-level declarations (imports, functions, processes)
     - `control_ejecucion`: Control flow (if, try/catch, blocks)
     - `expresiones`: Expression parsing (binary ops, unary ops, etc.)
     - `literales`: Literal values (strings, numbers, objects, lists)
     - `tipos_parametros`: Type annotations and parameters
     - `control_flujo`: Process flow control (tasks, gateways)

3. **IDE Layer** (`com.flowscript.ide`)
   - JavaFX-based visual editor
   - Components: CodeEditorPane, ProjectExplorer, ConsolePane, StatusBar
   - Syntax highlighting via `FlowScriptSyntaxHighlighter.java`
   - Multi-theme support (Dark/Light/Monokai)

### Key Architecture Patterns

- **ParserContext**: Shared state container for token stream and current position
- **IParser<T>**: Common interface for all parsers, returns AST nodes of type T
- **TokenRecognizer hierarchy**: Strategy pattern for different token types
- **AST nodes**: Strongly-typed tree structure mirroring grammar

### Directory Structure

```
src/main/
├── java/com/flowscript/
│   ├── lexer/              # Lexical analysis
│   │   ├── Lexer.java      # Main tokenizer
│   │   ├── Token.java      # Token representation
│   │   └── afd/            # AFD recognizers for each token type
│   ├── sintactic/          # Syntactic analysis
│   │   ├── Parser.java     # Main parser entry point
│   │   ├── ParserContext.java
│   │   ├── IParser.java
│   │   ├── ast/            # AST node classes (mirrors parser structure)
│   │   └── parsers/functions/  # Parser implementations
│   │       ├── programa_declaraciones/
│   │       ├── control_ejecucion/
│   │       ├── expresiones/
│   │       ├── literales/
│   │       └── control_flujo/
│   └── ide/                # JavaFX IDE
│       ├── FlowScriptIDE.java  # Main application
│       ├── components/     # UI components
│       ├── services/       # Business logic (theme, project mgmt)
│       └── syntax/         # Syntax highlighting
├── tlf/                    # ANTLR grammars (reference/testing only)
│   ├── FlowScriptFunctions.g4
│   └── FlowScriptProcesses.g4
└── resources/
    └── css/                # IDE themes

src/test/
├── java/
│   ├── edu/eam/ingesoft/tlf/tester/  # ANTLR-based grammar tests
│   └── com/flowscript/               # Unit tests for custom parser/lexer
└── resources/
    ├── flowscript-functions.yaml     # YAML test cases for functions
    └── flowscript-processes.yaml     # YAML test cases for processes
```

## Testing Strategy

### Two Testing Approaches

1. **ANTLR Grammar Tests** (`edu.eam.ingesoft.tlf.tester`)
   - Uses ANTLR-generated parsers for validation
   - Test cases defined in YAML files
   - Tests `FlowScriptFunctionsTest` and `FlowScriptProcessesTest`
   - Validates grammar correctness against specification

2. **Custom Parser Tests** (`com.flowscript.sintactic.parsers`)
   - Unit tests for each parser component
   - Tests the actual implementation used by the IDE
   - More granular, test individual parser rules

### YAML Test Format

Tests are defined in `src/test/resources/*.yaml`:

```yaml
ejemplos:
  - nombre: "Function with parameters"
    valido: true
    texto: |
      function add(a: integer, b: integer) -> integer {
          return a + b
      }
```

### Running Tests

Always compile before testing to ensure ANTLR parsers are generated:

```bash
mvn clean compile test
```

## FlowScript Language Syntax

### Key Language Constructs

FlowScript has TWO main syntactic domains:

1. **Functions** (traditional imperative code):
   - `funcion nombre(params) -> tipo { ... }`
   - Standard control flow: `si`, `sino`, `mientras`, `para`
   - Try/catch: `intentar { ... } capturar (e) { ... }`

2. **Processes** (BPMN-style workflows):
   - `proceso NombreProceso { ... }`
   - Elements: `inicio`, `tarea`, `gateway`, `fin`
   - Flow control: `go_to`, `cuando`, `rama`, `unir`

### Keywords by Category

- **Structure**: `proceso`, `funcion`, `importar`, `importar_jar`, `como`, `retornar`
- **Flow**: `inicio`, `fin`, `tarea`, `gateway`, `go_to`, `cuando`, `rama`, `unir`
- **Control**: `si`, `sino_si`, `sino`, `intentar`, `capturar`, `lanzar`
- **Types**: `entero`, `decimal`, `booleano`, `texto`, `lista`, `objeto`, `nulo`
- **Literals**: `verdadero`, `falso`
- **Operators**: `y`, `o`, `no`

### Language Specification

The complete FlowScript language specification is documented in the README.md. When implementing language features:
- All keywords are in Spanish
- Identifiers follow `[a-zA-Z_][a-zA-Z0-9_]*`
- The language is statically typed with type inference
- Processes maintain implicit context between tasks

## Common Development Workflows

### Adding a New Language Feature

1. Update the appropriate ANTLR grammar in `src/main/tlf/`
2. Regenerate ANTLR parsers: `mvn antlr4:antlr4`
3. Create/update parser class in `com.flowscript.sintactic.parsers.functions/`
4. Create/update corresponding AST node in `com.flowscript.sintactic.ast/`
5. Add test cases to YAML file in `src/test/resources/`
6. Add unit tests in `src/test/java/com/flowscript/sintactic/parsers/`
7. Update syntax highlighter if adding new keywords: `FlowScriptSyntaxHighlighter.java`
8. Run tests: `mvn test`

### Modifying the Lexer

The lexer uses custom AFD recognizers:
- Each token type has a recognizer in `com.flowscript.lexer.afd/`
- Recognizers implement `TokenRecognizer` interface
- Priority determines matching order (lower = higher priority)
- Main coordination in `Lexer.java`

### Working with the IDE

The IDE is a JavaFX application:
- Entry point: `com.flowscript.ide.FlowScriptIDE`
- Run with: `mvn javafx:run`
- Uses RichTextFX for code editor with syntax highlighting
- Theme switching via `ThemeService.java`
- Project management via `ProjectService.java`

### Debugging ANTLR Grammars

ANTLR grammars are in `src/main/tlf/`:
- Generate parsers to `target/generated-sources/antlr4`
- Check generated files for errors
- Use test cases in YAML to validate grammar changes

## Important Notes

### ANTLR vs Custom Parser

This project has **both** ANTLR grammars and a custom hand-written parser:
- **ANTLR grammars** (`src/main/tlf/*.g4`): Used for **validation and testing**
- **Custom parser** (`com.flowscript.sintactic`): Used by the **actual IDE**

When modifying language syntax:
1. Update BOTH the ANTLR grammar AND the custom parser
2. Keep them in sync
3. Use ANTLR tests to validate grammar correctness
4. Use custom parser tests to validate implementation

### JavaFX Dependencies

The IDE requires JavaFX modules:
- Automatically handled by Maven when running `mvn javafx:run`
- When running from IDE, may need VM options: `--add-modules javafx.controls,javafx.fxml`
- See `RUN_IDE.md` for detailed configuration

### Test Resources

Test cases are data-driven from YAML files:
- Easy to add new test cases without writing Java code
- Both positive (valid) and negative (invalid) tests
- Comprehensive coverage of language features

## Language vs IDE Development

When working on this codebase:

- **Language changes** affect: lexer, parser, AST, grammars, tests
- **IDE changes** affect: JavaFX components, services, themes, UI
- **Cross-cutting concerns**: syntax highlighting (needs both language and IDE updates)

Always run full test suite after language changes:
```bash
mvn clean compile test
```