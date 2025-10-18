package com.flowscript.lexer;

/**
 * Enumeration of all token types in the FlowScript language.
 */
public enum TokenType {
    // Structure Keywords
    PROCESS("process"),
    FUNCTION("function"),
    IMPORT("import"),
    IMPORT_JAR("import_jar"),
    AS("as"),
    RETURN("return"),

    // Flow Control Keywords
    START("start"),
    END("end"),
    TASK("task"),
    GATEWAY("gateway"),
    GOTO("goto"),
    WHEN("when"),
    BRANCH("branch"),
    JOIN("join"),
    ELSE("else"),
    PARALLEL("parallel"),
    ACTION("action"),

    // Control Flow Keywords
    IF("if"),
    ELSE_IF("else_if"),
    TRY("try"),
    CATCH("catch"),
    THROW("throw"),
    WHILE("while"),
    FOR("for"),
    EACH("each"),
    IN("in"),
    FROM("from"),
    TO("to"),
    STEP("step"),
    BREAK("break"),
    CONTINUE("continue"),

    // Data Type Keywords
    INTEGER_TYPE("integer"),
    DECIMAL_TYPE("decimal"),
    BOOLEAN_TYPE("boolean"),
    TEXT_TYPE("text"),
    LIST_TYPE("list"),
    OBJECT_TYPE("object"),
    VOID("void"),

    // Literal Values
    NULL("null"),
    TRUE("true"),
    FALSE("false"),

    // Logical Operators (as keywords)
    AND("and"),
    OR("or"),
    NOT("not"),
    NO("no"),


    // Arithmetic Operators
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    MODULO("%"),

    // Comparison Operators
    LESS_THAN("<"),
    GREATER_THAN(">"),
    LESS_EQUAL("<="),
    GREATER_EQUAL(">="),
    EQUAL("=="),
    NOT_EQUAL("!="),

    // Assignment Operator
    ASSIGN("="),

    // Ternary Operators
    QUESTION("?"),

    // Access Operators
    DOT("."),

    // Delimiters
    LEFT_PAREN("("),
    RIGHT_PAREN(")"),
    LEFT_BRACE("{"),
    RIGHT_BRACE("}"),
    LEFT_BRACKET("["),
    RIGHT_BRACKET("]"),
    COMMA(","),
    SEMICOLON(";"),
    COLON(":"),
    ARROW("->"),

    // Literals
    INTEGER_LITERAL(null),
    DECIMAL_LITERAL(null),
    STRING_LITERAL(null),

    // Identifier
    IDENTIFIER(null),

    // Special
    INPUT("input"),  // Special context object in processes

    // Comments (typically filtered out but tracked for completeness)
    SINGLE_LINE_COMMENT(null),
    MULTI_LINE_COMMENT(null),

    // End of file
    EOF(null),

    // Error token
    ERROR(null);

    private final String defaultValue;

    TokenType(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Check if this token type is a keyword.
     */
    public boolean isKeyword() {
        return defaultValue != null &&
               this != PLUS && this != MINUS && this != MULTIPLY &&
               this != DIVIDE && this != MODULO && this != LESS_THAN &&
               this != GREATER_THAN && this != LESS_EQUAL && this != GREATER_EQUAL &&
               this != EQUAL && this != NOT_EQUAL && this != ASSIGN &&
               this != QUESTION && this != DOT && this != LEFT_PAREN && this != RIGHT_PAREN &&
               this != LEFT_BRACE && this != RIGHT_BRACE && this != LEFT_BRACKET &&
               this != RIGHT_BRACKET && this != COMMA && this != SEMICOLON &&
               this != COLON && this != ARROW;
    }

    /**
     * Check if this token type is an operator.
     */
    public boolean isOperator() {
        return this == PLUS || this == MINUS || this == MULTIPLY ||
               this == DIVIDE || this == MODULO || this == LESS_THAN ||
               this == GREATER_THAN || this == LESS_EQUAL || this == GREATER_EQUAL ||
               this == EQUAL || this == NOT_EQUAL || this == ASSIGN ||
               this == QUESTION || this == DOT || this == AND || this == OR || this == NOT;
    }

    /**
     * Check if this token type is a delimiter.
     */
    public boolean isDelimiter() {
        return this == LEFT_PAREN || this == RIGHT_PAREN ||
               this == LEFT_BRACE || this == RIGHT_BRACE ||
               this == LEFT_BRACKET || this == RIGHT_BRACKET ||
               this == COMMA || this == SEMICOLON || this == COLON ||
               this == ARROW;
    }

    /**
     * Check if this token type is a literal.
     */
    public boolean isLiteral() {
        return this == INTEGER_LITERAL || this == DECIMAL_LITERAL ||
               this == STRING_LITERAL || this == TRUE || this == FALSE ||
               this == NULL;
    }

    /**
     * Check if this token type is a data type keyword.
     */
    public boolean isDataType() {
        return this == INTEGER_TYPE || this == DECIMAL_TYPE ||
               this == BOOLEAN_TYPE || this == TEXT_TYPE ||
               this == LIST_TYPE || this == OBJECT_TYPE || this == VOID;
    }
}