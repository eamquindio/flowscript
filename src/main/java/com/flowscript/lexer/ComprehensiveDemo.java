package com.flowscript.lexer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Comprehensive demo using the large FlowScript example.
 */
public class ComprehensiveDemo {

    public static void main(String[] args) {
        System.out.println("=== FlowScript Comprehensive Lexer Demo ===\n");

        try {
            // Read the comprehensive example file
            File exampleFile = new File("example-flowscript.flow");
            String code = Files.readString(exampleFile.toPath());

            System.out.println("📊 Analyzing Comprehensive FlowScript Example");
            System.out.println("📄 File: " + exampleFile.getName());
            System.out.println("📏 Size: " + code.length() + " characters");
            System.out.println("📋 Lines: " + code.split("\n").length);
            System.out.println("\n" + "=".repeat(70) + "\n");

            // Tokenize the code
            Lexer lexer = new Lexer(code, true); // Include comments
            List<Token> tokens = lexer.tokenize();

            // Analyze token statistics
            analyzeTokenStatistics(tokens);

            System.out.println("\n" + "=".repeat(70));
            System.out.println("📋 SAMPLE TOKENS (first 50 tokens)");
            System.out.println("=".repeat(70));

            // Display first 50 tokens in a formatted table
            printTokenTable(tokens.subList(0, Math.min(50, tokens.size() - 1)));

            System.out.println("\n" + "=".repeat(70));
            System.out.println("🎯 FUNCTION DECLARATIONS FOUND");
            System.out.println("=".repeat(70));

            // Find and display function declarations
            findFunctionDeclarations(tokens);

            System.out.println("\n" + "=".repeat(70));
            System.out.println("⚙️ PROCESS DECLARATIONS FOUND");
            System.out.println("=".repeat(70));

            // Find and display process declarations
            findProcessDeclarations(tokens);

            System.out.println("\n" + "=".repeat(70));
            System.out.println("🔍 STRING LITERALS ANALYSIS");
            System.out.println("=".repeat(70));

            // Analyze string literals
            analyzeStringLiterals(tokens);

            System.out.println("\n" + "=".repeat(70));
            System.out.println("💰 NUMERIC LITERALS ANALYSIS");
            System.out.println("=".repeat(70));

            // Analyze numeric literals
            analyzeNumericLiterals(tokens);

        } catch (IOException e) {
            System.err.println("❌ Error reading example file: " + e.getMessage());
        } catch (Lexer.LexicalException e) {
            System.err.println("❌ Lexical analysis error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void analyzeTokenStatistics(List<Token> tokens) {
        Map<TokenType, Integer> tokenCounts = new HashMap<>();
        Map<String, Integer> categoryCounts = new HashMap<>();

        for (Token token : tokens) {
            if (token.getType() != TokenType.EOF) {
                tokenCounts.put(token.getType(), tokenCounts.getOrDefault(token.getType(), 0) + 1);

                String category = getCategoryForToken(token.getType());
                categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
            }
        }

        System.out.println("📊 TOKEN STATISTICS");
        System.out.println("─".repeat(40));
        System.out.printf("Total tokens: %d%n", tokens.size() - 1); // Exclude EOF
        System.out.printf("Unique token types: %d%n", tokenCounts.size());
        System.out.println();

        System.out.println("📋 BY CATEGORY:");
        categoryCounts.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .forEach(entry -> System.out.printf("  %-12s: %4d tokens%n", entry.getKey(), entry.getValue()));

        System.out.println();
        System.out.println("🔝 TOP TOKEN TYPES:");
        tokenCounts.entrySet().stream()
            .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
            .limit(10)
            .forEach(entry -> System.out.printf("  %-20s: %4d%n", entry.getKey(), entry.getValue()));
    }

    private static void printTokenTable(List<Token> tokens) {
        System.out.printf("%-4s %-4s %-20s %-25s %-10s%n", "Line", "Col", "Type", "Value", "Category");
        System.out.println("─".repeat(70));

        for (Token token : tokens) {
            String value = formatValue(token.getValue());
            String category = getCategoryForToken(token.getType());

            System.out.printf("%-4d %-4d %-20s %-25s %-10s%n",
                token.getLine(),
                token.getColumn(),
                token.getType(),
                value,
                category);
        }
    }

    private static void findFunctionDeclarations(List<Token> tokens) {
        int functionCount = 0;

        for (int i = 0; i < tokens.size() - 2; i++) {
            if (tokens.get(i).getType() == TokenType.FUNCTION &&
                tokens.get(i + 1).getType() == TokenType.IDENTIFIER) {

                functionCount++;
                Token nameToken = tokens.get(i + 1);

                // Try to find return type
                String returnType = "void";
                for (int j = i + 2; j < Math.min(i + 20, tokens.size()); j++) {
                    if (tokens.get(j).getType() == TokenType.ARROW && j + 1 < tokens.size()) {
                        Token returnTypeToken = tokens.get(j + 1);
                        if (returnTypeToken.getType().isDataType()) {
                            returnType = returnTypeToken.getValue();
                        }
                        break;
                    }
                }

                System.out.printf("%2d. %s() -> %s (line %d)%n",
                    functionCount,
                    nameToken.getValue(),
                    returnType,
                    nameToken.getLine());
            }
        }

        if (functionCount == 0) {
            System.out.println("No function declarations found.");
        }
    }

    private static void findProcessDeclarations(List<Token> tokens) {
        int processCount = 0;

        for (int i = 0; i < tokens.size() - 1; i++) {
            if (tokens.get(i).getType() == TokenType.PROCESS &&
                tokens.get(i + 1).getType() == TokenType.IDENTIFIER) {

                processCount++;
                Token nameToken = tokens.get(i + 1);

                // Count tasks in this process
                int taskCount = 0;
                int gatewayCount = 0;
                int endCount = 0;

                // Look ahead for tasks, gateways, and ends (within reasonable range)
                for (int j = i + 2; j < Math.min(i + 1000, tokens.size()); j++) {
                    if (tokens.get(j).getType() == TokenType.PROCESS) {
                        break; // Next process found
                    }
                    if (tokens.get(j).getType() == TokenType.TASK) {
                        taskCount++;
                    } else if (tokens.get(j).getType() == TokenType.GATEWAY) {
                        gatewayCount++;
                    } else if (tokens.get(j).getType() == TokenType.END) {
                        endCount++;
                    }
                }

                System.out.printf("%d. %s (line %d)%n", processCount, nameToken.getValue(), nameToken.getLine());
                System.out.printf("   └─ %d tasks, %d gateways, %d end states%n", taskCount, gatewayCount, endCount);
            }
        }

        if (processCount == 0) {
            System.out.println("No process declarations found.");
        }
    }

    private static void analyzeStringLiterals(List<Token> tokens) {
        int stringCount = 0;
        int totalLength = 0;
        String longest = "";

        for (Token token : tokens) {
            if (token.getType() == TokenType.STRING_LITERAL) {
                stringCount++;
                String value = token.getValue();
                totalLength += value.length();

                if (value.length() > longest.length()) {
                    longest = value;
                }

                if (stringCount <= 10) { // Show first 10
                    System.out.printf("%2d. %s (line %d)%n", stringCount, formatValue(value), token.getLine());
                }
            }
        }

        if (stringCount > 10) {
            System.out.printf("... and %d more string literals%n", stringCount - 10);
        }

        if (stringCount > 0) {
            System.out.printf("\n📊 Summary: %d strings, avg length: %.1f chars%n",
                stringCount, (double) totalLength / stringCount);
            System.out.printf("🏆 Longest: %s%n", formatValue(longest));
        }
    }

    private static void analyzeNumericLiterals(List<Token> tokens) {
        int integerCount = 0;
        int decimalCount = 0;

        System.out.println("🔢 INTEGER LITERALS:");
        for (Token token : tokens) {
            if (token.getType() == TokenType.INTEGER_LITERAL) {
                integerCount++;
                if (integerCount <= 15) { // Show first 15
                    System.out.printf("  %s (line %d)%n", token.getValue(), token.getLine());
                }
            }
        }

        System.out.println("\n💰 DECIMAL LITERALS:");
        for (Token token : tokens) {
            if (token.getType() == TokenType.DECIMAL_LITERAL) {
                decimalCount++;
                if (decimalCount <= 15) { // Show first 15
                    System.out.printf("  %s (line %d)%n", token.getValue(), token.getLine());
                }
            }
        }

        System.out.printf("\n📊 Summary: %d integers, %d decimals%n", integerCount, decimalCount);
    }

    private static String formatValue(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }

        // Truncate long values
        if (value.length() > 22) {
            return value.substring(0, 19) + "...";
        }

        return value;
    }

    private static String getCategoryForToken(TokenType type) {
        if (type.isKeyword()) {
            return "Keyword";
        } else if (type.isOperator()) {
            return "Operator";
        } else if (type.isDelimiter()) {
            return "Delimiter";
        } else if (type.isLiteral()) {
            return "Literal";
        } else if (type.isDataType()) {
            return "Type";
        } else if (type == TokenType.IDENTIFIER) {
            return "Identifier";
        } else if (type == TokenType.SINGLE_LINE_COMMENT ||
                   type == TokenType.MULTI_LINE_COMMENT) {
            return "Comment";
        } else {
            return "Other";
        }
    }
}