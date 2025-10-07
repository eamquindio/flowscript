package com.flowscript.lexer;

import java.util.List;

/**
 * Demo class to showcase the FlowScript lexer functionality.
 */
public class LexerDemo {

    public static void main(String[] args) {
        System.out.println("=== FlowScript Lexer Demo ===\n");

        // Demo 1: Function with parameters and return type
        String functionCode = """
            function calculate_total(items: list, tax_rate: decimal) -> decimal {
                subtotal = 0.0
                for each item in items {
                    subtotal = subtotal + item.price * item.quantity
                }
                tax = subtotal * tax_rate
                return subtotal + tax
            }
            """;

        System.out.println("Demo 1: Function Declaration");
        System.out.println("Input Code:");
        System.out.println(functionCode);
        System.out.println("Tokens:");
        tokenizeAndPrint(functionCode);

        // Demo 2: Process with tasks and gateways
        String processCode = """
            process OrderValidation {
                start -> ValidateInput

                task ValidateInput {
                    action:
                        if input.customer_id == null {
                            error_message = "Customer ID required"
                            goto ValidationError
                        }
                        goto CheckInventory
                }

                gateway InventoryDecision {
                    when all_available -> ProcessOrder
                    else -> InsufficientStock
                }

                end Success
                end Error
            }
            """;

        System.out.println("\n\nDemo 2: Process Declaration");
        System.out.println("Input Code:");
        System.out.println(processCode);
        System.out.println("Tokens:");
        tokenizeAndPrint(processCode);

        // Demo 3: Complex expressions and literals
        String expressionCode = """
            # Calculate discount
            price = 100.50
            discount_rate = 0.15
            items = [1, 2, 3]
            customer = { name: "John", age: 30, active: true }

            /* Multi-line comment
               explaining the calculation */
            final_price = price * (1 - discount_rate)

            if final_price > 50.0 and customer.active {
                apply_discount = true
            } else_if final_price <= 50.0 or not customer.active {
                apply_discount = false
            }
            """;

        System.out.println("\n\nDemo 3: Expressions and Literals");
        System.out.println("Input Code:");
        System.out.println(expressionCode);
        System.out.println("Tokens (with comments):");
        tokenizeAndPrint(expressionCode, true);

        // Demo 4: Error handling
        System.out.println("\n\nDemo 4: Error Handling");
        String errorCode = "valid_identifier @invalid_char";
        System.out.println("Input Code: " + errorCode);
        System.out.println("Result:");
        try {
            tokenizeAndPrint(errorCode);
        } catch (Exception e) {
            System.out.println("✗ Lexical Error: " + e.getMessage());
        }

        System.out.println("\n=== End of Demo ===");
    }

    private static void tokenizeAndPrint(String code) {
        tokenizeAndPrint(code, false);
    }

    private static void tokenizeAndPrint(String code, boolean includeComments) {
        try {
            Lexer lexer = new Lexer(code, includeComments);
            List<Token> tokens = lexer.tokenize();

            for (Token token : tokens) {
                if (token.getType() != TokenType.EOF) {
                    System.out.printf("  %3d:%-3d %-20s %s%n",
                        token.getLine(),
                        token.getColumn(),
                        token.getType(),
                        formatValue(token.getValue()));
                }
            }
        } catch (Lexer.LexicalException e) {
            System.err.println("Lexical error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static String formatValue(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }

        // Truncate long values for display
        if (value.length() > 40) {
            value = value.substring(0, 37) + "...";
        }

        // Escape special characters for display
        value = value.replace("\n", "\\n")
                     .replace("\t", "\\t")
                     .replace("\r", "\\r");

        return "'" + value + "'";
    }
}