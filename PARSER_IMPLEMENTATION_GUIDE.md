# Complete Parser Implementation Guide for FlowScript

## Overview
This document provides comprehensive implementation patterns for ALL 51 stub parsers in the FlowScript project.

## Implementation Status

### ✅ COMPLETED (7/51)
1. TypeParser
2. ParameterParser
3. IntegerLiteralParser
4. DecimalLiteralParser
5. BooleanLiteralParser
6. StringLiteralParser
7. NullLiteralParser

### 🚧 IN PROGRESS - Critical Path Parsers (44/51)

## Parser Implementation Patterns

### Pattern 1: Simple Terminal Parsers
For parsers that consume a single token and create a node.

**Template:**
```java
@Override
public XxxNode parse(ParserContext context) throws Parser.ParseException {
    Token token = context.getCurrentToken();

    if (token == null) {
        throw new Parser.ParseException("Expected XXX but reached end of input");
    }

    if (token.getType() != TokenType.XXX) {
        throw new Parser.ParseException(
            "Expected XXX but found '" + token.getValue() +
            "' at line " + token.getLine()
        );
    }

    context.consume();
    return new XxxNode(token);
}
```

**Examples:** IntegerLiteralParser, StringLiteralParser, BooleanLiteralParser

### Pattern 2: Composite Parsers (with sub-parsers)
For parsers that delegate to other parsers.

**Template:**
```java
private final SubParser1 subParser1;
private final SubParser2 subParser2;

public XxxParser() {
    this.subParser1 = new SubParser1();
    this.subParser2 = new SubParser2();
}

@Override
public XxxNode parse(ParserContext context) throws Parser.ParseException {
    // Parse first component
    ComponentNode comp1 = subParser1.parse(context);

    // Consume expected delimiter/keyword
    context.consume(TokenType.DELIMITER);

    // Parse second component
    ComponentNode comp2 = subParser2.parse(context);

    return new XxxNode(comp1, comp2);
}
```

**Examples:** ParameterParser, FunctionDeclarationParser

### Pattern 3: Optional Component Parsers
For parsers with optional elements.

**Template:**
```java
@Override
public XxxNode parse(ParserContext context) throws Parser.ParseException {
    // Required component
    RequiredNode required = requiredParser.parse(context);

    // Optional component
    OptionalNode optional = null;
    Token next = context.getCurrentToken();
    if (next != null && next.getType() == TokenType.OPTIONAL_MARKER) {
        context.consume();
        optional = optionalParser.parse(context);
    }

    return new XxxNode(required, optional);
}
```

**Examples:** FunctionDeclarationParser (optional return type), ImportDeclarationParser (optional alias)

### Pattern 4: List/Repetition Parsers
For parsers that handle comma-separated lists.

**Template:**
```java
@Override
public List<XxxNode> parse(ParserContext context) throws Parser.ParseException {
    List<XxxNode> items = new ArrayList<>();

    // Parse first item
    items.add(itemParser.parse(context));

    // Parse remaining items
    while (context.getCurrentToken() != null &&
           context.getCurrentToken().getType() == TokenType.COMMA) {
        context.consume(); // consume comma
        items.add(itemParser.parse(context));
    }

    return items;
}
```

**Examples:** ParameterListParser, ExpressionListParser, ObjectMemberListParser

### Pattern 5: Choice/Alternative Parsers
For parsers with multiple alternatives.

**Template:**
```java
@Override
public XxxNode parse(ParserContext context) throws Parser.ParseException {
    Token current = context.getCurrentToken();

    if (current == null) {
        throw new Parser.ParseException("Unexpected end of input");
    }

    // Try each alternative
    switch (current.getType()) {
        case TYPE_A:
            return parserA.parse(context);
        case TYPE_B:
            return parserB.parse(context);
        case TYPE_C:
            return parserC.parse(context);
        default:
            throw new Parser.ParseException(
                "Expected A, B, or C but found '" + current.getValue() +
                "' at line " + current.getLine()
            );
    }
}
```

**Examples:** LiteralParser, StatementParser, DeclarationParser

### Pattern 6: Binary Expression Parsers
For left-associative binary operators.

**Template:**
```java
@Override
public ExpressionNode parse(ParserContext context) throws Parser.ParseException {
    // Parse left operand
    ExpressionNode left = higherPrecedenceParser.parse(context);

    // Parse operator and right operands (left-associative)
    while (context.getCurrentToken() != null &&
           isOperator(context.getCurrentToken())) {
        Token operator = context.consume();
        ExpressionNode right = higherPrecedenceParser.parse(context);
        left = new BinaryExpressionNode(left, operator, right);
    }

    return left;
}

private boolean isOperator(Token token) {
    return token.getType() == TokenType.OP1 || token.getType() == TokenType.OP2;
}
```

**Examples:** AdditiveExpressionParser, MultiplicativeExpressionParser, LogicalAndExpressionParser

## Complete Implementation List

### 1. Literal Parsers (7 total)
- [x] IntegerLiteralParser
- [x] DecimalLiteralParser
- [x] BooleanLiteralParser
- [x] StringLiteralParser
- [x] NullLiteralParser
- [ ] ObjectLiteralParser - Pattern 4 (List parser) + Pattern 2 (Composite)
- [ ] ListLiteralParser - Pattern 4 (List parser) + Pattern 2 (Composite)
- [ ] LiteralParser - Pattern 5 (Choice parser)

### 2. Expression List Parsers (4 total)
- [ ] ObjectMemberParser - Pattern 2 (Composite: IDENTIFIER : Expression)
- [ ] ObjectMemberListParser - Pattern 4 (List: ObjectMember, ObjectMember, ...)
- [ ] ExpressionListParser - Pattern 4 (List: Expression, Expression, ...)
- [ ] ArgumentListParser - Pattern 4 (List: Expression, Expression, ...)

### 3. Primary Expression Parsers (4 total)
- [ ] PrimaryExpressionParser - Pattern 5 (Choice: Literal | IDENTIFIER | (Expression))
- [ ] PostfixOperatorParser - Pattern 5 (Choice: . IDENTIFIER | [ Expression ] | ( ArgumentList ))
- [ ] PostfixExpressionParser - Pattern 6 adapted (PrimaryExpression PostfixOperator*)
- [ ] UnaryExpressionParser - Pattern 2 (Optional unary operator + PostfixExpression)

### 4. Binary Expression Parsers (6 total)
- [ ] MultiplicativeExpressionParser - Pattern 6 (UnaryExpression (* | / | %) UnaryExpression)*
- [ ] AdditiveExpressionParser - Pattern 6 (MultiplicativeExpression (+ | -) MultiplicativeExpression)*
- [ ] RelationalExpressionParser - Pattern 6 (AdditiveExpression (< | > | <= | >=) AdditiveExpression)*
- [ ] EqualityExpressionParser - Pattern 6 (RelationalExpression (== | !=) RelationalExpression)*
- [ ] LogicalAndExpressionParser - Pattern 6 (EqualityExpression and EqualityExpression)*
- [ ] LogicalOrExpressionParser - Pattern 6 (LogicalAndExpression or LogicalAndExpression)*

### 5. Ternary and Top-Level Expression Parsers (2 total)
- [ ] TernaryExpressionParser - Pattern 2 (LogicalOrExpression ? Expression : Expression)
- [ ] ExpressionParser - Simple delegation to TernaryExpressionParser

### 6. Statement Parsers (3 total)
- [ ] VariableDeclarationParser - Pattern 2 (IDENTIFIER : Type = Expression)
- [ ] ExpressionStatementParser - Pattern 2 (Expression)
- [ ] StatementParser - Pattern 5 (Choice: VariableDeclaration | ExpressionStatement | IfStatement | etc.)

### 7. Control Flow Statement Parsers (5 total)
- [ ] IfStatementParser - Pattern 3 (if ( Expression ) Block (else_if ( Expression ) Block)* (else Block)?)
- [ ] ForStatementParser - Pattern 2 (for IDENTIFIER in/from... Block)
- [ ] TryStatementParser - Pattern 2 (try Block catch ( IDENTIFIER ) Block)
- [ ] ThrowStatementParser - Pattern 2 (throw Expression)
- [ ] ReturnStatementParser - Pattern 3 (return Expression?)

### 8. Control Execution Parsers (3 total)
- [ ] BlockParser - Pattern 2 ({ StatementList })
- [ ] StatementListParser - Pattern 4 (Statement Statement Statement ...)
- [ ] VariableDeclarationStatementParser - Pattern 2 (VariableDeclaration)

### 9. Program Declaration Parsers (3 total)
- [ ] ImportDeclarationParser - Pattern 3 (import STRING_LITERAL (as IDENTIFIER)?)
- [ ] FunctionDeclarationParser - Pattern 2 + 3 (function IDENTIFIER ( ParameterList? ) (-> Type)? Block)
- [ ] ProgramParser - Pattern 4 (Declaration Declaration ...)
- [ ] DeclarationParser - Pattern 5 (Choice: Import | Function | Process | Variable)

### 10. Process Core Parsers (2 total)
- [ ] StartElementParser - Pattern 2 (start -> IDENTIFIER)
- [ ] EndElementParser - Pattern 2 (end IDENTIFIER)

### 11. Process Task Parsers (1 total)
- [ ] TaskElementParser - Pattern 2 (task IDENTIFIER { action: StatementList })

### 12. Process Gateway Parsers (2 total)
- [ ] ExclusiveGatewayParser - Pattern 2 (gateway IDENTIFIER { when clauses... else clause })
- [ ] ParallelGatewayParser - Pattern 2 (gateway IDENTIFIER parallel { branch -> ... join -> ... })

### 13. Process Clause Parsers (4 total)
- [ ] WhenClauseParser - Pattern 2 (when Expression -> IDENTIFIER)
- [ ] ElseClauseParser - Pattern 2 (else -> IDENTIFIER)
- [ ] ParallelBranchParser - Pattern 2 (branch -> IDENTIFIER)
- [ ] JoinClauseParser - Pattern 2 (join -> IDENTIFIER)

### 14. Process Navigation Parsers (1 total)
- [ ] GotoStatementParser - Pattern 2 (go_to IDENTIFIER)

### 15. Process Structure Parsers (2 total)
- [ ] ProcessBodyParser - Pattern 4 (ProcessElement ProcessElement ...)
- [ ] ProcessDeclarationParser - Pattern 2 (process IDENTIFIER { ProcessBody })

## Dependency Order for Implementation

To avoid circular dependencies, implement in this order:

**Phase 1: Foundation** (No dependencies)
1. All simple literal parsers (IntegerLiteral, DecimalLiteral, BooleanLiteral, StringLiteral, NullLiteral) ✅

**Phase 2: Lists and Members** (Depends on Phase 1)
2. ExpressionParser (stub that throws exception initially)
3. ObjectMemberParser (needs ExpressionParser)
4. ObjectMemberListParser (needs ObjectMemberParser)
5. ExpressionListParser (needs ExpressionParser)
6. ArgumentListParser (needs ExpressionParser)

**Phase 3: Complex Literals** (Depends on Phase 2)
7. ObjectLiteralParser (needs ObjectMemberListParser)
8. ListLiteralParser (needs ExpressionListParser)
9. LiteralParser (needs all literal parsers)

**Phase 4: Expressions** (Depends on Phase 3)
10. PrimaryExpressionParser (needs LiteralParser)
11. PostfixOperatorParser
12. PostfixExpressionParser (needs PrimaryExpressionParser, PostfixOperatorParser)
13. UnaryExpressionParser (needs PostfixExpressionParser)
14. MultiplicativeExpressionParser (needs UnaryExpressionParser)
15. AdditiveExpressionParser (needs MultiplicativeExpressionParser)
16. RelationalExpressionParser (needs AdditiveExpressionParser)
17. EqualityExpressionParser (needs RelationalExpressionParser)
18. LogicalAndExpressionParser (needs EqualityExpressionParser)
19. LogicalOrExpressionParser (needs LogicalAndExpressionParser)
20. TernaryExpressionParser (needs LogicalOrExpressionParser)
21. ExpressionParser (update to delegate to TernaryExpressionParser)

**Phase 5: Statements** (Depends on Phase 4)
22. ExpressionStatementParser
23. VariableDeclarationParser
24. BlockParser + StatementListParser + StatementParser (circular, implement together)

**Phase 6: Control Flow** (Depends on Phase 5)
25. ReturnStatementParser
26. ThrowStatementParser
27. IfStatementParser
28. ForStatementParser
29. TryStatementParser

**Phase 7: Functions** (Depends on Phase 6)
30. ParameterListParser
31. FunctionDeclarationParser

**Phase 8: Processes** (Depends on Phase 5)
32. GotoStatementParser
33. StartElementParser
34. EndElementParser
35. WhenClauseParser
36. ElseClauseParser
37. ParallelBranchParser
38. JoinClauseParser
39. ExclusiveGatewayParser
40. ParallelGatewayParser
41. TaskElementParser
42. ProcessBodyParser
43. ProcessDeclarationParser

**Phase 9: Top Level** (Depends on all previous)
44. ImportDeclarationParser
45. DeclarationParser
46. ProgramParser

## Quick Reference: TokenType Values

```java
// Keywords
FUNCTION, PROCESS, IMPORT, IMPORT_JAR, AS, RETURN
START, END, TASK, GATEWAY, GOTO, WHEN, BRANCH, JOIN, ELSE, PARALLEL, ACTION
IF, ELSE_IF, TRY, CATCH, THROW, WHILE, FOR, EACH, IN, FROM, TO, STEP
INTEGER_TYPE, DECIMAL_TYPE, BOOLEAN_TYPE, TEXT_TYPE, LIST_TYPE, OBJECT_TYPE, VOID
NULL, TRUE, FALSE
AND, OR, NOT

// Operators
PLUS, MINUS, MULTIPLY, DIVIDE, MODULO
LESS_THAN, GREATER_THAN, LESS_EQUAL, GREATER_EQUAL, EQUAL, NOT_EQUAL
ASSIGN, QUESTION, DOT

// Delimiters
LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, LEFT_BRACKET, RIGHT_BRACKET
COMMA, SEMICOLON, COLON, ARROW

// Literals
INTEGER_LITERAL, DECIMAL_LITERAL, STRING_LITERAL, IDENTIFIER
```

## Testing Strategy

After implementing each parser:
1. Write unit tests with valid input
2. Write unit tests with invalid input (error handling)
3. Test edge cases (empty lists, optional components)
4. Integration test with full FlowScript programs

## Common Pitfalls

1. **Forgetting to consume tokens**: Always call `context.consume()` after checking a token
2. **Wrong token type checks**: Use `==` for TokenType enum comparison
3. **Null pointer exceptions**: Always check if `context.getCurrentToken()` is null
4. **Infinite loops in repetition**: Ensure progress by consuming at least one token per iteration
5. **Left recursion**: FlowScript grammar is designed to avoid this, but be careful
6. **Missing error messages**: Always provide helpful error messages with line numbers

## Next Steps

1. Implement all parsers following the patterns above
2. Run `mvn clean compile` to verify compilation
3. Run `mvn test` to verify all tests pass
4. Test with comprehensive FlowScript programs
