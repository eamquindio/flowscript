package com.flowscript.codegen.generators;

import com.flowscript.codegen.TypeMapper;
import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.functions.expresiones.*;
import com.flowscript.sintactic.ast.functions.literales.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates Java code for FlowScript expressions.
 * Handles all expression types including literals, operators, function calls, property access, etc.
 */
public class ExpressionGenerator {

    private final TypeMapper typeMapper;

    public ExpressionGenerator(TypeMapper typeMapper) {
        this.typeMapper = typeMapper;
    }

    /**
     * Generates Java code for any expression node.
     */
    public String generate(ASTNode node) {
        if (node == null) {
            return "null";
        }

        // Literals
        if (node instanceof IntegerLiteralNode) {
            return generateIntegerLiteral((IntegerLiteralNode) node);
        }
        if (node instanceof DecimalLiteralNode) {
            return generateDecimalLiteral((DecimalLiteralNode) node);
        }
        if (node instanceof StringLiteralNode) {
            return generateStringLiteral((StringLiteralNode) node);
        }
        if (node instanceof BooleanLiteralNode) {
            return generateBooleanLiteral((BooleanLiteralNode) node);
        }
        if (node instanceof NullLiteralNode) {
            return "null";
        }
        if (node instanceof ListLiteralNode) {
            return generateListLiteral((ListLiteralNode) node);
        }
        if (node instanceof ObjectLiteralNode) {
            return generateObjectLiteral((ObjectLiteralNode) node);
        }

        // Identifier
        if (node instanceof IdentifierNode) {
            return generateIdentifier((IdentifierNode) node);
        }

        // N-ary operators (support multiple operands: a + b + c + d)
        if (node instanceof AdditiveExpressionNode) {
            return generateNaryExpression((AdditiveExpressionNode) node);
        }
        if (node instanceof MultiplicativeExpressionNode) {
            return generateNaryExpression((MultiplicativeExpressionNode) node);
        }
        if (node instanceof RelationalExpressionNode) {
            return generateNaryExpression((RelationalExpressionNode) node);
        }
        if (node instanceof EqualityExpressionNode) {
            return generateNaryExpression((EqualityExpressionNode) node);
        }
        if (node instanceof LogicalAndExpressionNode) {
            return generateLogicalAnd((LogicalAndExpressionNode) node);
        }
        if (node instanceof LogicalOrExpressionNode) {
            return generateLogicalOr((LogicalOrExpressionNode) node);
        }

        // Binary expression (base class, if used directly)
        if (node instanceof BinaryExpressionNode) {
            return generateBinaryExpression((BinaryExpressionNode) node);
        }

        // Unary operators
        if (node instanceof UnaryExpressionNode) {
            return generateUnaryExpression((UnaryExpressionNode) node);
        }

        // Ternary operator
        if (node instanceof TernaryExpressionNode) {
            return generateTernaryExpression((TernaryExpressionNode) node);
        }

        // Function call
        if (node instanceof FunctionCallNode) {
            return generateFunctionCall((FunctionCallNode) node);
        }

        // Property access
        if (node instanceof PropertyAccessNode) {
            return generatePropertyAccess((PropertyAccessNode) node);
        }

        // Index access
        if (node instanceof IndexAccessNode) {
            return generateIndexAccess((IndexAccessNode) node);
        }

        // Postfix expression
        if (node instanceof PostfixExpressionNode) {
            return generatePostfixExpression((PostfixExpressionNode) node);
        }

        // Primary expression (parenthesized)
        if (node instanceof PrimaryExpressionNode.ParenthesizedExpressionNode) {
            PrimaryExpressionNode.ParenthesizedExpressionNode paren =
                (PrimaryExpressionNode.ParenthesizedExpressionNode) node;
            return generate(paren.getInnerExpression());
        }

        // Special operations
        if (node instanceof DbExecuteNode) {
            return generateDbExecute((DbExecuteNode) node);
        }
        if (node instanceof DbQueryNode) {
            return generateDbQuery((DbQueryNode) node);
        }
        if (node instanceof HttpGetNode) {
            return generateHttpGet((HttpGetNode) node);
        }
        if (node instanceof HttpPostNode) {
            return generateHttpPost((HttpPostNode) node);
        }
        if (node instanceof HttpDeleteNode) {
            return generateHttpDelete((HttpDeleteNode) node);
        }

        throw new IllegalArgumentException("Unsupported expression type: " + node.getClass().getSimpleName());
    }

    // ========== Literals ==========

    private String generateIntegerLiteral(IntegerLiteralNode node) {
        return node.getValue().toString();
    }

    private String generateDecimalLiteral(DecimalLiteralNode node) {
        return node.getValue().toString();
    }

    private String generateStringLiteral(StringLiteralNode node) {
        String value = node.getValue();
        // Escape special characters
        value = value.replace("\\", "\\\\");
        value = value.replace("\"", "\\\"");
        value = value.replace("\n", "\\n");
        value = value.replace("\r", "\\r");
        value = value.replace("\t", "\\t");
        return "\"" + value + "\"";
    }

    private String generateBooleanLiteral(BooleanLiteralNode node) {
        return String.valueOf(node.getValue());
    }

    private String generateListLiteral(ListLiteralNode node) {
        if (node.getElements() == null || node.getElements().isEmpty()) {
            return "List.of()";
        }

        String elements = node.getElements().stream()
                .map(this::generate)
                .collect(Collectors.joining(", "));

        return "List.of(" + elements + ")";
    }

    private String generateObjectLiteral(ObjectLiteralNode node) {
        if (node.getMembers() == null || node.getMembers().isEmpty()) {
            return "new HashMap<>()";
        }

        var members = node.getMembers();

        // Use HashMap to avoid type inference issues with Map.of()
        // Map.of() creates immutable maps with complex intersection types
        // that don't match Map<String, Object> return types
        StringBuilder sb = new StringBuilder();
        sb.append("new HashMap<String, Object>() {{ ");

        for (var member : members) {
            sb.append("put(\"").append(member.getKey()).append("\", ");
            sb.append(generate(member.getValue())).append("); ");
        }

        sb.append("}}");
        return sb.toString();
    }

    // ========== Identifier ==========

    private String generateIdentifier(IdentifierNode node) {
        return node.getName();
    }

    // ========== N-ary Operators ==========

    /**
     * Generates code for N-ary expressions (expressions with multiple operands).
     * Examples: a + b + c, x * y * z
     *
     * These nodes have:
     * - getOperands(): List<ExpressionNode>
     * - getOperators(): List<Token>
     * - getSimplified(): ExpressionNode (returns single operand if only one)
     */
    private String generateNaryExpression(ExpressionNode node) {
        // All N-ary expression nodes have these methods
        List<ExpressionNode> operands = null;
        List<com.flowscript.lexer.Token> operators = null;

        if (node instanceof AdditiveExpressionNode) {
            AdditiveExpressionNode n = (AdditiveExpressionNode) node;
            operands = n.getOperands();
            operators = n.getOperators();
        } else if (node instanceof MultiplicativeExpressionNode) {
            MultiplicativeExpressionNode n = (MultiplicativeExpressionNode) node;
            operands = n.getOperands();
            operators = n.getOperators();
        } else if (node instanceof RelationalExpressionNode) {
            RelationalExpressionNode n = (RelationalExpressionNode) node;
            operands = n.getOperands();
            operators = n.getOperators();
        } else if (node instanceof EqualityExpressionNode) {
            EqualityExpressionNode n = (EqualityExpressionNode) node;
            operands = n.getOperands();
            operators = n.getOperators();
        }

        if (operands == null || operands.isEmpty()) {
            return "null";
        }

        // Single operand - no operator needed
        if (operands.size() == 1) {
            return generate(operands.get(0));
        }

        // Multiple operands - build expression
        StringBuilder result = new StringBuilder("(");
        result.append(generate(operands.get(0)));

        for (int i = 0; i < operators.size(); i++) {
            String operator = mapOperator(operators.get(i).getValue());
            result.append(" ").append(operator).append(" ");
            result.append(generate(operands.get(i + 1)));
        }

        result.append(")");
        return result.toString();
    }

    private String generateLogicalAnd(LogicalAndExpressionNode node) {
        List<ExpressionNode> operands = node.getOperands();

        if (operands.isEmpty()) {
            return "true";
        }

        if (operands.size() == 1) {
            return generate(operands.get(0));
        }

        // Multiple operands: (a && b && c)
        StringBuilder result = new StringBuilder("(");
        result.append(generate(operands.get(0)));

        for (int i = 1; i < operands.size(); i++) {
            result.append(" && ");
            result.append(generate(operands.get(i)));
        }

        result.append(")");
        return result.toString();
    }

    private String generateLogicalOr(LogicalOrExpressionNode node) {
        List<ExpressionNode> operands = node.getOperands();

        if (operands.isEmpty()) {
            return "false";
        }

        if (operands.size() == 1) {
            return generate(operands.get(0));
        }

        // Multiple operands: (a || b || c)
        StringBuilder result = new StringBuilder("(");
        result.append(generate(operands.get(0)));

        for (int i = 1; i < operands.size(); i++) {
            result.append(" || ");
            result.append(generate(operands.get(i)));
        }

        result.append(")");
        return result.toString();
    }

    // ========== Binary Expression (fallback) ==========

    private String generateBinaryExpression(BinaryExpressionNode node) {
        String left = generate(node.getLeft());
        String right = generate(node.getRight());
        String operator = mapOperator(node.getOperator());

        return "(" + left + " " + operator + " " + right + ")";
    }

    /**
     * Maps FlowScript operators to Java operators.
     */
    private String mapOperator(String operator) {
        switch (operator) {
            // Arithmetic
            case "+": return "+";
            case "-": return "-";
            case "*": return "*";
            case "/": return "/";
            case "%": return "%";

            // Relational
            case "<": return "<";
            case ">": return ">";
            case "<=": return "<=";
            case ">=": return ">=";

            // Equality
            case "==": return "==";
            case "!=": return "!=";

            // Logical (these are handled separately in their own methods)
            case "y": return "&&";
            case "o": return "||";

            default:
                return operator; // Pass through
        }
    }

    // ========== Unary Operators ==========

    private String generateUnaryExpression(UnaryExpressionNode node) {
        String operand = generate(node.getOperand());
        String operator = mapUnaryOperator(node.getOperator());

        return operator + operand;
    }

    private String mapUnaryOperator(String operator) {
        switch (operator) {
            case "-": return "-";
            case "+": return "+";
            case "no": return "!";
            case "!": return "!";
            default:
                return operator;
        }
    }

    // ========== Ternary Operator ==========

    private String generateTernaryExpression(TernaryExpressionNode node) {
        String condition = generate(node.getCondition());
        String trueExpr = generate(node.getTrueExpression());
        String falseExpr = generate(node.getFalseExpression());

        return "(" + condition + " ? " + trueExpr + " : " + falseExpr + ")";
    }

    // ========== Function Call ==========

    private String generateFunctionCall(FunctionCallNode node) {
        // Get function name from the function expression (usually an IdentifierNode)
        ExpressionNode functionExpr = node.getFunction();
        String functionName;

        if (functionExpr instanceof IdentifierNode) {
            functionName = ((IdentifierNode) functionExpr).getName();
        } else {
            // For more complex expressions (like method chains), generate the full expression
            functionName = generate(functionExpr);
        }

        // Map built-in functions
        String mappedName = mapBuiltinFunction(functionName);

        // Generate arguments
        List<ExpressionNode> args = node.getArguments();
        String argsList = "";

        if (args != null && !args.isEmpty()) {
            argsList = args.stream()
                    .map(this::generate)
                    .collect(Collectors.joining(", "));
        }

        return mappedName + "(" + argsList + ")";
    }

    /**
     * Maps FlowScript built-in functions to Java runtime equivalents.
     */
    private String mapBuiltinFunction(String functionName) {
        switch (functionName) {
            // I/O
            case "imprimir":
                return "FlowScriptBuiltins.imprimir";
            case "leer":
                return "FlowScriptBuiltins.leer";

            // HTTP
            case "httpGet":
                return "FlowScriptBuiltins.httpGet";
            case "httpPost":
                return "FlowScriptBuiltins.httpPost";

            // Database
            case "consultar":
                return "FlowScriptBuiltins.consultar";
            case "ejecutar":
                return "FlowScriptBuiltins.ejecutar";

            // JSON
            case "parseJson":
                return "FlowScriptBuiltins.parseJson";
            case "toJson":
                return "FlowScriptBuiltins.toJson";

            // String operations
            case "longitud":
                return "FlowScriptBuiltins.longitud";
            case "subcadena":
                return "FlowScriptBuiltins.subcadena";
            case "contiene":
                return "FlowScriptBuiltins.contiene";

            // List operations
            case "agregar":
                return "FlowScriptBuiltins.agregar";
            case "remover":
                return "FlowScriptBuiltins.remover";
            case "tamano":
                return "FlowScriptBuiltins.tamano";

            default:
                // User-defined function
                return functionName;
        }
    }

    // ========== Property Access ==========

    private String generatePropertyAccess(PropertyAccessNode node) {
        String object = generate(node.getObject());
        String property = node.getPropertyName();

        // For maps, use get() method
        // Assume Map<String, Object> for objects
        return "((Map<String, Object>) " + object + ").get(\"" + property + "\")";
    }

    // ========== Index Access ==========

    private String generateIndexAccess(IndexAccessNode node) {
        String object = generate(node.getObject());
        String index = generate(node.getIndex());

        // For lists, use get() method
        return "(" + object + ").get(" + index + ")";
    }

    // ========== Postfix Expression ==========

    private String generatePostfixExpression(PostfixExpressionNode node) {
        String base = generate(node.getPrimary());

        List<PostfixOperatorNode> operators = node.getOperators();

        if (operators == null || operators.isEmpty()) {
            return base;
        }

        // Apply each postfix operator in sequence
        StringBuilder result = new StringBuilder(base);

        for (PostfixOperatorNode op : operators) {
            if (op instanceof PostfixOperatorNode.PropertyAccessOperatorNode) {
                PostfixOperatorNode.PropertyAccessOperatorNode propOp =
                    (PostfixOperatorNode.PropertyAccessOperatorNode) op;
                result.append(".get(\"").append(propOp.getPropertyName()).append("\")");
            }
            else if (op instanceof PostfixOperatorNode.IndexAccessOperatorNode) {
                PostfixOperatorNode.IndexAccessOperatorNode indexOp =
                    (PostfixOperatorNode.IndexAccessOperatorNode) op;
                result.append(".get(").append(generate(indexOp.getIndex())).append(")");
            }
            else if (op instanceof PostfixOperatorNode.FunctionCallOperatorNode) {
                PostfixOperatorNode.FunctionCallOperatorNode callOp =
                    (PostfixOperatorNode.FunctionCallOperatorNode) op;
                String args = callOp.getArguments().stream()
                        .map(this::generate)
                        .collect(Collectors.joining(", "));
                result.append("(").append(args).append(")");
            }
        }

        return result.toString();
    }

    // ========== Special Operations ==========

    /**
     * Generates code for db.ejecutar(query, params)
     * Returns inline JDBC code for executing SQL
     */
    private String generateDbExecute(DbExecuteNode node) {
        String query = generate(node.getQuery());
        String params = generate(node.getParameters());

        return "((java.util.function.Supplier<Integer>)(() -> {\n" +
               "        try (java.sql.Connection __conn = java.sql.DriverManager.getConnection(\"jdbc:h2:mem:flowscript;DB_CLOSE_DELAY=-1\", \"sa\", \"\");\n" +
               "             java.sql.PreparedStatement __stmt = __conn.prepareStatement(" + query + ")) {\n" +
               "            java.util.List<?> __params = " + params + ";\n" +
               "            for (int i = 0; i < __params.size(); i++) {\n" +
               "                __stmt.setObject(i + 1, __params.get(i));\n" +
               "            }\n" +
               "            int __result = __stmt.executeUpdate();\n" +
               "            System.out.println(\"[DB EXECUTE] \" + " + query + " + \" | Affected rows: \" + __result);\n" +
               "            return __result;\n" +
               "        } catch (java.sql.SQLException __e) {\n" +
               "            throw new RuntimeException(\"Database execution error: \" + __e.getMessage(), __e);\n" +
               "        }\n" +
               "    })).get()";
    }

    /**
     * Generates code for db.consultar(query, params)
     * Returns inline JDBC code for querying SQL
     */
    private String generateDbQuery(DbQueryNode node) {
        String query = generate(node.getQuery());
        String params = generate(node.getParameters());

        return "((java.util.function.Supplier<java.util.List<java.util.Map<String, Object>>>)(() -> {\n" +
               "        java.util.List<java.util.Map<String, Object>> __results = new java.util.ArrayList<>();\n" +
               "        try (java.sql.Connection __conn = java.sql.DriverManager.getConnection(\"jdbc:h2:mem:flowscript;DB_CLOSE_DELAY=-1\", \"sa\", \"\");\n" +
               "             java.sql.PreparedStatement __stmt = __conn.prepareStatement(" + query + ")) {\n" +
               "            java.util.List<?> __params = " + params + ";\n" +
               "            for (int i = 0; i < __params.size(); i++) {\n" +
               "                __stmt.setObject(i + 1, __params.get(i));\n" +
               "            }\n" +
               "            try (java.sql.ResultSet __rs = __stmt.executeQuery()) {\n" +
               "                java.sql.ResultSetMetaData __meta = __rs.getMetaData();\n" +
               "                int __colCount = __meta.getColumnCount();\n" +
               "                while (__rs.next()) {\n" +
               "                    java.util.Map<String, Object> __row = new java.util.LinkedHashMap<>();\n" +
               "                    for (int i = 1; i <= __colCount; i++) {\n" +
               "                        __row.put(__meta.getColumnLabel(i), __rs.getObject(i));\n" +
               "                    }\n" +
               "                    __results.add(__row);\n" +
               "                }\n" +
               "            }\n" +
               "            System.out.println(\"[DB QUERY] \" + " + query + " + \" | Results: \" + __results.size() + \" rows\");\n" +
               "            return __results;\n" +
               "        } catch (java.sql.SQLException __e) {\n" +
               "            throw new RuntimeException(\"Database query error: \" + __e.getMessage(), __e);\n" +
               "        }\n" +
               "    })).get()";
    }

    /**
     * Generates code for http.get(url) or http.get(url, headers)
     * Returns inline HTTP code for GET request
     */
    private String generateHttpGet(HttpGetNode node) {
        String url = generate(node.getUrl());
        String headers = node.hasHeaders() ? generate(node.getHeaders()) : "null";

        return "((java.util.function.Supplier<java.util.Map<String, Object>>)(() -> {\n" +
               "        try {\n" +
               "            java.net.http.HttpClient __client = java.net.http.HttpClient.newBuilder()\n" +
               "                .connectTimeout(java.time.Duration.ofSeconds(30))\n" +
               "                .build();\n" +
               "            java.net.http.HttpRequest.Builder __builder = java.net.http.HttpRequest.newBuilder()\n" +
               "                .uri(java.net.URI.create(" + url + "))\n" +
               "                .GET()\n" +
               "                .timeout(java.time.Duration.ofSeconds(30));\n" +
               "            java.util.Map<String, Object> __headers = " + headers + ";\n" +
               "            if (__headers != null) {\n" +
               "                for (java.util.Map.Entry<String, Object> __h : __headers.entrySet()) {\n" +
               "                    __builder.header(__h.getKey(), String.valueOf(__h.getValue()));\n" +
               "                }\n" +
               "            }\n" +
               "            java.net.http.HttpRequest __request = __builder.build();\n" +
               "            java.net.http.HttpResponse<String> __response = __client.send(__request, java.net.http.HttpResponse.BodyHandlers.ofString());\n" +
               "            java.util.Map<String, Object> __result = new java.util.LinkedHashMap<>();\n" +
               "            __result.put(\"status\", __response.statusCode());\n" +
               "            __result.put(\"body\", __response.body());\n" +
               "            System.out.println(\"[HTTP GET] \" + " + url + " + \" | Status: \" + __response.statusCode());\n" +
               "            return __result;\n" +
               "        } catch (Exception __e) {\n" +
               "            java.util.Map<String, Object> __error = new java.util.LinkedHashMap<>();\n" +
               "            __error.put(\"status\", 0);\n" +
               "            __error.put(\"error\", __e.getMessage());\n" +
               "            return __error;\n" +
               "        }\n" +
               "    })).get()";
    }

    /**
     * Generates code for http.post(url, body) or http.post(url, body, headers)
     * Returns inline HTTP code for POST request
     */
    private String generateHttpPost(HttpPostNode node) {
        String url = generate(node.getUrl());
        String body = generate(node.getBody());
        String headers = node.hasHeaders() ? generate(node.getHeaders()) : "null";

        return "((java.util.function.Supplier<java.util.Map<String, Object>>)(() -> {\n" +
               "        try {\n" +
               "            com.google.gson.Gson __gson = new com.google.gson.Gson();\n" +
               "            String __jsonBody = __gson.toJson(" + body + ");\n" +
               "            java.net.http.HttpClient __client = java.net.http.HttpClient.newBuilder()\n" +
               "                .connectTimeout(java.time.Duration.ofSeconds(30))\n" +
               "                .build();\n" +
               "            java.net.http.HttpRequest.Builder __builder = java.net.http.HttpRequest.newBuilder()\n" +
               "                .uri(java.net.URI.create(" + url + "))\n" +
               "                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(__jsonBody))\n" +
               "                .header(\"Content-Type\", \"application/json\")\n" +
               "                .timeout(java.time.Duration.ofSeconds(30));\n" +
               "            java.util.Map<String, Object> __headers = " + headers + ";\n" +
               "            if (__headers != null) {\n" +
               "                for (java.util.Map.Entry<String, Object> __h : __headers.entrySet()) {\n" +
               "                    __builder.header(__h.getKey(), String.valueOf(__h.getValue()));\n" +
               "                }\n" +
               "            }\n" +
               "            java.net.http.HttpRequest __request = __builder.build();\n" +
               "            java.net.http.HttpResponse<String> __response = __client.send(__request, java.net.http.HttpResponse.BodyHandlers.ofString());\n" +
               "            java.util.Map<String, Object> __result = new java.util.LinkedHashMap<>();\n" +
               "            __result.put(\"status\", __response.statusCode());\n" +
               "            __result.put(\"body\", __response.body());\n" +
               "            System.out.println(\"[HTTP POST] \" + " + url + " + \" | Status: \" + __response.statusCode());\n" +
               "            return __result;\n" +
               "        } catch (Exception __e) {\n" +
               "            java.util.Map<String, Object> __error = new java.util.LinkedHashMap<>();\n" +
               "            __error.put(\"status\", 0);\n" +
               "            __error.put(\"error\", __e.getMessage());\n" +
               "            return __error;\n" +
               "        }\n" +
               "    })).get()";
    }

    /**
     * Generates code for http.delete(url) or http.delete(url, headers)
     * Returns inline HTTP code for DELETE request
     */
    private String generateHttpDelete(HttpDeleteNode node) {
        String url = generate(node.getUrl());
        String headers = node.hasHeaders() ? generate(node.getHeaders()) : "null";

        return "((java.util.function.Supplier<java.util.Map<String, Object>>)(() -> {\n" +
               "        try {\n" +
               "            java.net.http.HttpClient __client = java.net.http.HttpClient.newBuilder()\n" +
               "                .connectTimeout(java.time.Duration.ofSeconds(30))\n" +
               "                .build();\n" +
               "            java.net.http.HttpRequest.Builder __builder = java.net.http.HttpRequest.newBuilder()\n" +
               "                .uri(java.net.URI.create(" + url + "))\n" +
               "                .DELETE()\n" +
               "                .timeout(java.time.Duration.ofSeconds(30));\n" +
               "            java.util.Map<String, Object> __headers = " + headers + ";\n" +
               "            if (__headers != null) {\n" +
               "                for (java.util.Map.Entry<String, Object> __h : __headers.entrySet()) {\n" +
               "                    __builder.header(__h.getKey(), String.valueOf(__h.getValue()));\n" +
               "                }\n" +
               "            }\n" +
               "            java.net.http.HttpRequest __request = __builder.build();\n" +
               "            java.net.http.HttpResponse<String> __response = __client.send(__request, java.net.http.HttpResponse.BodyHandlers.ofString());\n" +
               "            java.util.Map<String, Object> __result = new java.util.LinkedHashMap<>();\n" +
               "            __result.put(\"status\", __response.statusCode());\n" +
               "            __result.put(\"body\", __response.body());\n" +
               "            System.out.println(\"[HTTP DELETE] \" + " + url + " + \" | Status: \" + __response.statusCode());\n" +
               "            return __result;\n" +
               "        } catch (Exception __e) {\n" +
               "            java.util.Map<String, Object> __error = new java.util.LinkedHashMap<>();\n" +
               "            __error.put(\"status\", 0);\n" +
               "            __error.put(\"error\", __e.getMessage());\n" +
               "            return __error;\n" +
               "        }\n" +
               "    })).get()";
    }
}
