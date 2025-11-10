package com.flowscript.sintactic;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ProgramNode;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.FunctionDeclarationNode;
import com.flowscript.sintactic.ast.process.estructura_principal.ProcessDeclarationNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive integration test covering the FULL FlowScript language spectrum.
 *
 * This test validates:
 * 1. All data types and literals
 * 2. All operators and expressions
 * 3. Variable declarations and assignments
 * 4. All control flow constructs (if, while, for-each, for-range, break, continue)
 * 5. Function declarations with parameters and return types
 * 6. Process declarations with BPMN elements
 * 7. Exception handling (try/catch/throw)
 * 8. Import declarations
 * 9. Complex nested structures
 * 10. Edge cases and advanced features
 */
public class FlowScriptComprehensiveIntegrationTest {

    private Parser parser = new Parser();

    /**
     * Test 1: ALL DATA TYPES AND LITERALS
     */
    @Test
    public void test01_AllDataTypesAndLiterals() throws Exception {
        String code = """
            x = 42
            negative = -100
            zero = 0
            pi = 3.14159
            negative_decimal = -2.5
            is_true = true
            is_false = false
            name = "FlowScript"
            empty = ""
            multiword = "Hello World"
            nothing = null
            empty_list = []
            numbers = [1, 2, 3, 4, 5]
            mixed = [1, "text", true, null]
            nested = [[1, 2], [3, 4]]
            empty_obj = {}
            person = { nombre: "Juan", edad: 25 }
            complex = { id: 1, data: { x: 10, y: 20 }, tags: ["tag1", "tag2"] }
            """;

        List<Token> tokens = new Lexer(code).tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(18, program.getDeclarations().size());
    }

    /**
     * Test 2: ALL OPERATORS AND EXPRESSIONS
     */
    @Test
    public void test02_AllOperatorsAndExpressions() throws Exception {
        String code = """
            // Arithmetic operators
            sum = 10 + 5
            diff = 10 - 5
            prod = 10 * 5
            quot = 10 / 5
            mod = 10 % 3

            // Comparison operators
            lt = 5 < 10
            gt = 10 > 5
            le = 5 <= 10
            ge = 10 >= 5
            eq = 10 == 10
            ne = 10 != 5

            // Logical operators
            and_result = true and false
            or_result = true or false
            not_result = not false

            // Unary operators
            negation = -x
            logical_not = not is_valid

            // Ternary operator
            max = a > b ? a : b

            // Property access
            user_name = user.name
            nested_prop = obj.data.value

            // Index access
            first = list[0]
            matrix_val = matrix[i][j]

            // Function calls
            result = calculate(10, 20)
            chained = obj.method().getValue()

            // Complex expressions
            complex = (a + b) * (c - d) / (e + f)
            logic = (x > 0 and y < 100) or (z == 0)
            mixed = func(a + b, c * d).prop[0]
            """;

        List<Token> tokens = new Lexer(code).tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertTrue(program.getDeclarations().size() > 20);
    }

    /**
     * Test 3: IF STATEMENTS WITH ALL VARIANTS
     */
    @Test
    public void test03_IfStatementsAllVariants() throws Exception {
        String code = """
            function test_if_statements(x: integer) {
                // Simple if
                if x > 0 {
                    result = "positive"
                }

                // If-else
                if x > 0 {
                    result = "positive"
                } else {
                    result = "non-positive"
                }

                // If-else if-else
                if x > 0 {
                    result = "positive"
                } else_if x < 0 {
                    result = "negative"
                } else {
                    result = "zero"
                }

                // Nested if
                if x > 0 {
                    if x > 100 {
                        result = "large"
                    } else {
                        result = "small"
                    }
                }

                // Complex conditions
                if (x > 0 and x < 100) or x == 1000 {
                    result = "in range"
                }
            }
            """;

        List<Token> tokens = new Lexer(code).tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
        assertTrue(program.getDeclarations().get(0) instanceof FunctionDeclarationNode);
    }

    /**
     * Test 4: WHILE LOOPS - NEW FEATURE
     */
    @Test
    public void test04_WhileLoops() throws Exception {
        String code = """
            function test_while_loops() {
                // Simple while loop
                i = 0
                while i < 10 {
                    i = i + 1
                }

                // While with complex condition
                while (x > 0 and y < 100) {
                    x = x - 1
                    y = y + 2
                }

                // Nested while loops
                i = 0
                while i < 10 {
                    j = 0
                    while j < 10 {
                        matrix[i][j] = i * j
                        j = j + 1
                    }
                    i = i + 1
                }

                // While with break
                counter = 0
                while true {
                    if counter >= 100 {
                        break
                    }
                    counter = counter + 1
                }

                // While with continue
                sum = 0
                i = 0
                while i < 100 {
                    i = i + 1
                    if i % 2 == 0 {
                        continue
                    }
                    sum = sum + i
                }
            }
            """;

        List<Token> tokens = new Lexer(code).tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    /**
     * Test 5: FOR-EACH LOOPS
     */
    @Test
    public void test05_ForEachLoops() throws Exception {
        String code = """
            function test_for_each_loops() {
                // Simple for-each
                numbers = [1, 2, 3, 4, 5]
                for each num in numbers {
                    imprimir(num)
                }

                // For-each with objects
                users = [
                    { nombre: "Juan", edad: 25 },
                    { nombre: "Ana", edad: 30 }
                ]
                for each user in users {
                    imprimir(user.nombre)
                }

                // Nested for-each
                matrix = [[1, 2], [3, 4], [5, 6]]
                for each row in matrix {
                    for each val in row {
                        sum = sum + val
                    }
                }

                // For-each with break
                for each item in items {
                    if item == target {
                        break
                    }
                }

                // For-each with continue
                for each num in numbers {
                    if num % 2 == 0 {
                        continue
                    }
                    odd_sum = odd_sum + num
                }
            }
            """;

        List<Token> tokens = new Lexer(code).tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    /**
     * Test 6: FOR-RANGE LOOPS - NEW FEATURE
     */
    @Test
    public void test06_ForRangeLoops() throws Exception {
        String code = """
            function test_for_range_loops() {
                // Basic range
                for i from 1 to 10 {
                    imprimir(i)
                }

                // Range with step
                for i from 0 to 100 step 5 {
                    imprimir(i)
                }

                // Descending range
                for i from 10 to 1 step -1 {
                    imprimir(i)
                }

                // Range with expressions
                for i from start_value to end_value step increment {
                    process(i)
                }

                // Nested range loops
                for i from 0 to rows {
                    for j from 0 to cols {
                        matrix[i][j] = i + j
                    }
                }

                // Range with break
                for i from 1 to 1000 {
                    if i * i > target {
                        break
                    }
                    result = i
                }

                // Range with continue
                for i from 1 to 100 {
                    if i % 3 != 0 {
                        continue
                    }
                    sum_of_multiples = sum_of_multiples + i
                }
            }
            """;

        List<Token> tokens = new Lexer(code).tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    /**
     * Test 7: BREAK AND CONTINUE - NEW FEATURES
     */
    @Test
    public void test07_BreakAndContinue() throws Exception {
        String code = """
            function test_break_continue() {
                // Break in while
                while true {
                    if should_exit {
                        break
                    }
                    work()
                }

                // Continue in while
                i = 0
                while i < 100 {
                    i = i + 1
                    if i % 2 == 0 {
                        continue
                    }
                    process_odd(i)
                }

                // Break in for-each
                for each item in collection {
                    if item.is_target {
                        found = item
                        break
                    }
                }

                // Continue in for-each
                for each user in users {
                    if not user.active {
                        continue
                    }
                    send_notification(user)
                }

                // Break in for-range
                for i from 1 to 1000000 {
                    if is_prime(i) and i > 1000 {
                        first_large_prime = i
                        break
                    }
                }

                // Continue in for-range
                for i from 1 to 100 {
                    if i % 5 != 0 and i % 7 != 0 {
                        continue
                    }
                    special_numbers = special_numbers + [i]
                }

                // Break in nested loops (breaks inner only)
                for each row in matrix {
                    found_in_row = false
                    for each val in row {
                        if val == target {
                            found_in_row = true
                            break
                        }
                    }
                }
            }
            """;

        List<Token> tokens = new Lexer(code).tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    /**
     * Test 8: FUNCTION DECLARATIONS - ALL VARIANTS
     */
    @Test
    public void test08_FunctionDeclarations() throws Exception {
        String code = """
            // Function with no parameters, no return
            function greet() {
                imprimir("Hello")
            }

            // Function with parameters, with return
            function add(a: integer, b: integer) -> integer {
                return a + b
            }

            // Function with multiple parameter types
            function process(id: integer, name: text, active: boolean) -> object {
                return { id: id, name: name, active: active }
            }

            // Function with list parameter
            function sum_list(numbers: list) -> integer {
                total = 0
                for each num in numbers {
                    total = total + num
                }
                return total
            }

            // Function with object parameter
            function get_user_info(user: object) -> text {
                return user.name + " (" + user.email + ")"
            }

            // Recursive function
            function factorial(n: integer) -> integer {
                if n <= 1 {
                    return 1
                }
                return n * factorial(n - 1)
            }

            // Function with complex logic
            function binary_search(arr: list, target: integer) -> integer {
                left = 0
                right = arr.length() - 1

                while left <= right {
                    mid = (left + right) / 2

                    if arr[mid] == target {
                        return mid
                    } else_if arr[mid] < target {
                        left = mid + 1
                    } else {
                        right = mid - 1
                    }
                }

                return -1
            }
            """;

        List<Token> tokens = new Lexer(code).tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(7, program.getDeclarations().size());

        // Verify all are functions
        for (var decl : program.getDeclarations()) {
            assertTrue(decl instanceof FunctionDeclarationNode);
        }
    }

    /**
     * Test 9: TRY-CATCH-THROW EXCEPTION HANDLING
     */
    @Test
    public void test09_ExceptionHandling() throws Exception {
        String code = """
            function test_exceptions() {
                // Basic try-catch
                try {
                    risky_operation()
                } catch (e) {
                    imprimir("Error: " + e.message)
                }

                // Try-catch with processing
                try {
                    data = parse_json(input)
                    result = data.value
                } catch (error) {
                    result = default_value
                    log_error(error)
                }

                // Nested try-catch
                try {
                    outer_operation()
                    try {
                        inner_operation()
                    } catch (inner_err) {
                        handle_inner(inner_err)
                    }
                } catch (outer_err) {
                    handle_outer(outer_err)
                }

                // Throw statement
                if not valid {
                    throw { type: "ValidationError", message: "Invalid input" }
                }

                // Try-catch in loop
                for each item in items {
                    try {
                        process_item(item)
                    } catch (e) {
                        failed_items = failed_items + [item]
                    }
                }
            }
            """;

        List<Token> tokens = new Lexer(code).tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    /**
     * Test 10: PROCESS DEFINITIONS - BPMN ELEMENTS
     */
    @Test
    public void test10_ProcessDefinitions() throws Exception {
        String code = """
            process SimpleProcess {
                start -> Task1

                task Task1 {
                    action:
                        result = entrada.value * 2
                        go_to End
                }

                end End
            }

            process ProcessWithGateway {
                start -> Check

                task Check {
                    action:
                        go_to Decision
                }

                gateway Decision {
                    when entrada.value > 0 -> Positive
                    when entrada.value < 0 -> Negative
                    else -> Zero
                }

                task Positive {
                    action:
                        result = "positive"
                        go_to End
                }

                task Negative {
                    action:
                        result = "negative"
                        go_to End
                }

                task Zero {
                    action:
                        result = "zero"
                        go_to End
                }

                end End
            }

            process ParallelProcess {
                start -> Init

                task Init {
                    action:
                        go_to Split
                }

                gateway Split parallel {
                    branch -> Task1
                    branch -> Task2
                    branch -> Task3
                    join -> Merge
                }

                task Task1 {
                    action:
                        result1 = process_part1()
                        go_to Merge
                }

                task Task2 {
                    action:
                        result2 = process_part2()
                        go_to Merge
                }

                task Task3 {
                    action:
                        result3 = process_part3()
                        go_to Merge
                }

                task Merge {
                    action:
                        final_result = combine(result1, result2, result3)
                        go_to End
                }

                end End
            }
            """;

        List<Token> tokens = new Lexer(code).tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(3, program.getDeclarations().size());

        // Verify all are processes
        for (var decl : program.getDeclarations()) {
            assertTrue(decl instanceof ProcessDeclarationNode);
        }
    }

    /**
     * Test 11: IMPORT DECLARATIONS
     */
    @Test
    public void test11_ImportDeclarations() throws Exception {
        String code = """
            // Simple import
            import "std/math"

            // Import with alias
            import "std/http" as http

            // Multiple imports
            import "std/json" as json
            import "std/string" as str
            import "std/list" as lst

            // Import JAR
            import_jar "lib/utils.jar"
            """;

        List<Token> tokens = new Lexer(code).tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(6, program.getDeclarations().size());
    }

    /**
     * Test 12: COMPLEX NESTED STRUCTURES
     */
    @Test
    public void test12_ComplexNestedStructures() throws Exception {
        String code = """
            function complex_algorithm(data: list, config: object) -> object {
                // Multiple nesting levels
                results = []

                for each group in data {
                    group_result = { items: [], count: 0 }

                    for each item in group.items {
                        try {
                            if item.active {
                                processed = null

                                while processed == null {
                                    candidate = process_item(item)

                                    if validate(candidate) {
                                        processed = candidate
                                    } else {
                                        if retries > config.max_retries {
                                            throw { type: "MaxRetries", item: item }
                                        }
                                        retries = retries + 1
                                        continue
                                    }
                                }

                                group_result.items = group_result.items + [processed]
                                group_result.count = group_result.count + 1
                            }
                        } catch (err) {
                            if err.type == "MaxRetries" {
                                log_failure(err.item)
                            } else {
                                throw err
                            }
                        }
                    }

                    if group_result.count > 0 {
                        results = results + [group_result]
                    }
                }

                return { results: results, total: results.length() }
            }
            """;

        List<Token> tokens = new Lexer(code).tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    /**
     * Test 13: REAL-WORLD SCENARIO - E-COMMERCE ORDER PROCESSING
     */
    @Test
    public void test13_RealWorldECommerceScenario() throws Exception {
        String code = """
            import "std/http" as http
            import "std/json" as json

            function validate_order(order: object) -> boolean {
                if order.items.length() == 0 {
                    return false
                }

                for each item in order.items {
                    if item.quantity <= 0 or item.price <= 0 {
                        return false
                    }
                }

                return true
            }

            function calculate_total(order: object) -> decimal {
                total = 0.0

                for each item in order.items {
                    subtotal = item.quantity * item.price
                    total = total + subtotal
                }

                if order.discount_code != null {
                    discount = apply_discount(order.discount_code, total)
                    total = total - discount
                }

                tax = total * 0.15
                total = total + tax

                return total
            }

            function process_payment(order: object, amount: decimal) -> object {
                try {
                    payment_data = {
                        order_id: order.id,
                        amount: amount,
                        method: order.payment_method
                    }

                    response = http.post("/api/payments", payment_data)

                    if response.status == 200 {
                        return { success: true, transaction_id: response.body.id }
                    } else {
                        return { success: false, error: response.body.message }
                    }
                } catch (err) {
                    return { success: false, error: "Payment service unavailable" }
                }
            }

            process OrderFulfillment {
                start -> ValidateOrder

                task ValidateOrder {
                    action:
                        is_valid = validate_order(entrada.order)
                        if is_valid {
                            go_to CalculateTotal
                        } else {
                            go_to Reject
                        }
                }

                task CalculateTotal {
                    action:
                        total = calculate_total(entrada.order)
                        go_to ProcessPayment
                }

                task ProcessPayment {
                    action:
                        payment_result = process_payment(entrada.order, total)
                        if payment_result.success {
                            go_to CheckInventory
                        } else {
                            go_to PaymentFailed
                        }
                }

                task CheckInventory {
                    action:
                        all_available = true
                        for each item in entrada.order.items {
                            stock = check_stock(item.product_id)
                            if stock < item.quantity {
                                all_available = false
                                break
                            }
                        }

                        if all_available {
                            go_to ReserveItems
                        } else {
                            go_to InsufficientStock
                        }
                }

                task ReserveItems {
                    action:
                        for each item in entrada.order.items {
                            reserve_stock(item.product_id, item.quantity)
                        }
                        go_to ShipOrder
                }

                task ShipOrder {
                    action:
                        shipping_label = create_shipping_label(entrada.order)
                        send_to_warehouse(entrada.order, shipping_label)
                        go_to Complete
                }

                task Reject {
                    action:
                        send_notification(entrada.order.customer_email, "Order rejected")
                        go_to End
                }

                task PaymentFailed {
                    action:
                        send_notification(entrada.order.customer_email, "Payment failed")
                        go_to End
                }

                task InsufficientStock {
                    action:
                        refund_payment(entrada.order.id)
                        send_notification(entrada.order.customer_email, "Out of stock")
                        go_to End
                }

                task Complete {
                    action:
                        send_confirmation(entrada.order.customer_email, entrada.order.id)
                        go_to End
                }

                end End
            }
            """;

        List<Token> tokens = new Lexer(code).tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(5, program.getDeclarations().size()); // 2 imports + 3 functions + 1 process
    }

    /**
     * Test 14: MATRIX OPERATIONS AND ALGORITHMS
     */
    @Test
    public void test14_MatrixOperationsAndAlgorithms() throws Exception {
        String code = """
            function create_matrix(rows: integer, cols: integer, default_value: integer) -> list {
                matrix = []

                for i from 0 to rows - 1 {
                    row = []
                    for j from 0 to cols - 1 {
                        row = row + [default_value]
                    }
                    matrix = matrix + [row]
                }

                return matrix
            }

            function matrix_multiply(a: list, b: list) -> list {
                rows_a = a.length()
                cols_a = a[0].length()
                cols_b = b[0].length()

                result = create_matrix(rows_a, cols_b, 0)

                for i from 0 to rows_a - 1 {
                    for j from 0 to cols_b - 1 {
                        sum = 0
                        for k from 0 to cols_a - 1 {
                            sum = sum + (a[i][k] * b[k][j])
                        }
                        result[i][j] = sum
                    }
                }

                return result
            }

            function find_path_dfs(graph: list, start: integer, end: integer) -> list {
                visited = create_matrix(1, graph.length(), false)[0]
                path = []

                found = dfs_recursive(graph, start, end, visited, path)

                if found {
                    return path
                } else {
                    return []
                }
            }

            function dfs_recursive(graph: list, current: integer, target: integer,
                                   visited: list, path: list) -> boolean {
                visited[current] = true
                path = path + [current]

                if current == target {
                    return true
                }

                neighbors = graph[current]
                for each neighbor in neighbors {
                    if not visited[neighbor] {
                        if dfs_recursive(graph, neighbor, target, visited, path) {
                            return true
                        }
                    }
                }

                path = path[0:path.length() - 1]
                return false
            }
            """;

        List<Token> tokens = new Lexer(code).tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(4, program.getDeclarations().size());
    }

    /**
     * Test 15: ALL LANGUAGE FEATURES COMBINED - ULTIMATE STRESS TEST
     */
    @Test
    public void test15_UltimateStressTestAllFeatures() throws Exception {
        String code = """
            import "std/http" as http
            import "std/json" as json
            import "std/math" as math

            // Global variables
            API_URL = "https://api.example.com"
            MAX_RETRIES = 3
            TIMEOUT = 5000

            // Complex data processing function
            function analyze_data(dataset: list, config: object) -> object {
                // Initialize result structure
                result = {
                    processed: [],
                    errors: [],
                    statistics: {
                        total: 0,
                        valid: 0,
                        invalid: 0,
                        avg: 0.0
                    }
                }

                // Process each data point
                for each point in dataset {
                    try {
                        // Validate
                        if not validate_point(point) {
                            result.errors = result.errors + [{ point: point, reason: "invalid" }]
                            result.statistics.invalid = result.statistics.invalid + 1
                            continue
                        }

                        // Transform
                        transformed = transform_point(point, config)

                        // Filter
                        if config.filter_enabled {
                            if not passes_filter(transformed, config.filter_criteria) {
                                continue
                            }
                        }

                        // Enrich
                        enriched = null
                        retries = 0

                        while enriched == null and retries < MAX_RETRIES {
                            try {
                                enriched = enrich_from_api(transformed)
                            } catch (api_error) {
                                retries = retries + 1
                                if retries >= MAX_RETRIES {
                                    throw api_error
                                }
                            }
                        }

                        // Aggregate
                        result.processed = result.processed + [enriched]
                        result.statistics.valid = result.statistics.valid + 1

                    } catch (error) {
                        result.errors = result.errors + [{ point: point, error: error.message }]
                    }
                }

                // Calculate statistics
                result.statistics.total = dataset.length()

                if result.statistics.valid > 0 {
                    sum = 0.0
                    for each item in result.processed {
                        sum = sum + item.value
                    }
                    result.statistics.avg = sum / result.statistics.valid
                }

                return result
            }

            // Advanced search algorithm
            function smart_search(collection: list, criteria: object) -> list {
                matches = []

                // Pre-process criteria
                normalized_criteria = normalize_criteria(criteria)

                // Search with multiple strategies
                for i from 0 to collection.length() - 1 {
                    item = collection[i]
                    score = 0

                    // Exact match check
                    if criteria.exact_match {
                        is_exact = true
                        for each key in criteria.fields {
                            if item[key] != criteria.values[key] {
                                is_exact = false
                                break
                            }
                        }
                        if is_exact {
                            matches = matches + [{ item: item, score: 100, index: i }]
                            continue
                        }
                    }

                    // Fuzzy match scoring
                    for each field in normalized_criteria.fields {
                        field_score = calculate_field_score(item[field], normalized_criteria[field])
                        score = score + field_score
                    }

                    // Add if above threshold
                    if score >= criteria.min_score {
                        matches = matches + [{ item: item, score: score, index: i }]
                    }
                }

                // Sort by score (descending)
                sorted_matches = sort_by_score(matches)

                // Return top N
                max_results = criteria.max_results != null ? criteria.max_results : sorted_matches.length()
                result = []

                for i from 0 to max_results - 1 {
                    if i < sorted_matches.length() {
                        result = result + [sorted_matches[i]]
                    } else {
                        break
                    }
                }

                return result
            }

            // Process with complex workflow
            process DataPipeline {
                start -> Initialize

                task Initialize {
                    action:
                        config = load_config()
                        data = []
                        errors = []
                        go_to FetchData
                }

                task FetchData {
                    action:
                        for i from 1 to 10 {
                            try {
                                batch = http.get(API_URL + "/data?page=" + i)
                                data = data + batch.items
                            } catch (e) {
                                errors = errors + [{ page: i, error: e.message }]
                            }
                        }
                        go_to ValidateData
                }

                task ValidateData {
                    action:
                        valid_count = 0
                        invalid_count = 0

                        for each item in data {
                            if validate_item(item) {
                                valid_count = valid_count + 1
                            } else {
                                invalid_count = invalid_count + 1
                            }
                        }

                        if valid_count > 0 {
                            go_to ProcessingGateway
                        } else {
                            go_to AllInvalid
                        }
                }

                gateway ProcessingGateway {
                    when entrada.mode == "parallel" -> ParallelProcessing
                    when entrada.mode == "sequential" -> SequentialProcessing
                    else -> SequentialProcessing
                }

                task ParallelProcessing {
                    action:
                        go_to SplitWork
                }

                gateway SplitWork parallel {
                    branch -> Worker1
                    branch -> Worker2
                    branch -> Worker3
                    join -> MergeResults
                }

                task Worker1 {
                    action:
                        result1 = process_chunk(data, 0, data.length() / 3)
                        go_to MergeResults
                }

                task Worker2 {
                    action:
                        result2 = process_chunk(data, data.length() / 3, data.length() * 2 / 3)
                        go_to MergeResults
                }

                task Worker3 {
                    action:
                        result3 = process_chunk(data, data.length() * 2 / 3, data.length())
                        go_to MergeResults
                }

                task MergeResults {
                    action:
                        final_result = merge(result1, result2, result3)
                        go_to SaveResults
                }

                task SequentialProcessing {
                    action:
                        results = []
                        for each item in data {
                            processed = process_single(item)
                            results = results + [processed]
                        }
                        go_to SaveResults
                }

                task SaveResults {
                    action:
                        try {
                            save_to_database(results)
                            go_to Complete
                        } catch (e) {
                            go_to SaveFailed
                        }
                }

                task AllInvalid {
                    action:
                        notify_admin("All data invalid")
                        go_to End
                }

                task SaveFailed {
                    action:
                        retry_count = 0
                        saved = false

                        while not saved and retry_count < 3 {
                            try {
                                save_to_database(results)
                                saved = true
                            } catch (e) {
                                retry_count = retry_count + 1
                            }
                        }

                        if saved {
                            go_to Complete
                        } else {
                            go_to End
                        }
                }

                task Complete {
                    action:
                        notify_admin("Pipeline completed successfully")
                        go_to End
                }

                end End
            }
            """;

        List<Token> tokens = new Lexer(code).tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertTrue(program.getDeclarations().size() >= 6); // imports + globals + functions + process

        // Verify we have a mix of different declaration types
        boolean hasImport = false;
        boolean hasFunction = false;
        boolean hasProcess = false;

        for (var decl : program.getDeclarations()) {
            if (decl.getNodeType().contains("Import")) hasImport = true;
            if (decl.getNodeType().contains("Function")) hasFunction = true;
            if (decl.getNodeType().contains("Process")) hasProcess = true;
        }

        assertTrue(hasImport, "Should have import declarations");
        assertTrue(hasFunction, "Should have function declarations");
        assertTrue(hasProcess, "Should have process declarations");
    }
}
