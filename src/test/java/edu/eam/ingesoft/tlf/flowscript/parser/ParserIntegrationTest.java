package edu.eam.ingesoft.tlf.flowscript.parser;

import edu.eam.ingesoft.tlf.flowscript.lexer.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.declarations.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.statements.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.expressions.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.literals.*;
import edu.eam.ingesoft.tlf.flowscript.parser.ast.types.*;
import edu.eam.ingesoft.tlf.flowscript.parser.parsers.ProgramParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de integración para el Parser completo de FlowScript.
 */
class ParserIntegrationTest {

    @Test
    void testSimpleFunctionDeclaration() {
        String code = "funcion suma(a: entero, b: entero) -> entero { retornar a + b }";

        ProgramNode program = parseCode(code);

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());

        FunctionDeclarationNode func = (FunctionDeclarationNode) program.getDeclarations().get(0);
        assertEquals("suma", func.getName());
        assertEquals(2, func.getParameters().size());
        assertEquals("a", func.getParameters().get(0).getName());
        assertEquals("b", func.getParameters().get(1).getName());
        assertEquals(TypeNode.TypeKind.ENTERO, func.getReturnType().getKind());

        BlockNode body = func.getBody();
        assertEquals(1, body.getStatements().size());

        ReturnStatementNode returnStmt = (ReturnStatementNode) body.getStatements().get(0);
        assertTrue(returnStmt.hasValue());

        BinaryExpressionNode addExpr = (BinaryExpressionNode) returnStmt.getValue();
        assertEquals(BinaryExpressionNode.Operator.ADD, addExpr.getOperator());
    }

    @Test
    void testFunctionWithVariableDeclaration() {
        String code = """
                funcion calcular(x: decimal) -> decimal {
                    resultado = x * 2.5
                    retornar resultado
                }
                """;

        ProgramNode program = parseCode(code);

        FunctionDeclarationNode func = (FunctionDeclarationNode) program.getDeclarations().get(0);
        assertEquals("calcular", func.getName());

        BlockNode body = func.getBody();
        assertEquals(2, body.getStatements().size());

        VariableDeclarationStatementNode varDecl = (VariableDeclarationStatementNode) body.getStatements().get(0);
        assertEquals("resultado", varDecl.getName());

        BinaryExpressionNode mulExpr = (BinaryExpressionNode) varDecl.getValue();
        assertEquals(BinaryExpressionNode.Operator.MULTIPLY, mulExpr.getOperator());
    }

    @Test
    void testExpressionPrecedence() {
        String code = "funcion test() -> entero { retornar 1 + 2 * 3 }";

        ProgramNode program = parseCode(code);
        FunctionDeclarationNode func = (FunctionDeclarationNode) program.getDeclarations().get(0);
        ReturnStatementNode returnStmt = (ReturnStatementNode) func.getBody().getStatements().get(0);

        // Should parse as: 1 + (2 * 3)
        BinaryExpressionNode addExpr = (BinaryExpressionNode) returnStmt.getValue();
        assertEquals(BinaryExpressionNode.Operator.ADD, addExpr.getOperator());

        // Right side should be multiplication
        BinaryExpressionNode mulExpr = (BinaryExpressionNode) addExpr.getRight();
        assertEquals(BinaryExpressionNode.Operator.MULTIPLY, mulExpr.getOperator());
    }

    @Test
    void testLogicalOperators() {
        String code = "funcion test(a: booleano, b: booleano) -> booleano { retornar a y b o no a }";

        ProgramNode program = parseCode(code);
        FunctionDeclarationNode func = (FunctionDeclarationNode) program.getDeclarations().get(0);
        ReturnStatementNode returnStmt = (ReturnStatementNode) func.getBody().getStatements().get(0);

        // Should parse as: (a y b) o (no a)
        BinaryExpressionNode orExpr = (BinaryExpressionNode) returnStmt.getValue();
        assertEquals(BinaryExpressionNode.Operator.OR, orExpr.getOperator());

        BinaryExpressionNode andExpr = (BinaryExpressionNode) orExpr.getLeft();
        assertEquals(BinaryExpressionNode.Operator.AND, andExpr.getOperator());

        UnaryExpressionNode notExpr = (UnaryExpressionNode) orExpr.getRight();
        assertEquals(UnaryExpressionNode.Operator.LOGICAL_NOT, notExpr.getOperator());
    }

    @Test
    void testRelationalOperators() {
        String code = "funcion comparar(x: entero, z: entero) -> booleano { retornar x < z }";

        ProgramNode program = parseCode(code);
        FunctionDeclarationNode func = (FunctionDeclarationNode) program.getDeclarations().get(0);
        ReturnStatementNode returnStmt = (ReturnStatementNode) func.getBody().getStatements().get(0);

        BinaryExpressionNode cmpExpr = (BinaryExpressionNode) returnStmt.getValue();
        assertEquals(BinaryExpressionNode.Operator.LESS_THAN, cmpExpr.getOperator());
    }

    @Test
    void testMemberAccess() {
        String code = "funcion test(obj: objeto) -> entero { retornar obj.propiedad }";

        ProgramNode program = parseCode(code);
        FunctionDeclarationNode func = (FunctionDeclarationNode) program.getDeclarations().get(0);
        ReturnStatementNode returnStmt = (ReturnStatementNode) func.getBody().getStatements().get(0);

        MemberAccessNode memberAccess = (MemberAccessNode) returnStmt.getValue();
        assertEquals("propiedad", memberAccess.getMemberName());

        IdentifierNode obj = (IdentifierNode) memberAccess.getObject();
        assertEquals("obj", obj.getName());
    }

    @Test
    void testIndexAccess() {
        String code = "funcion getElement(miLista: lista) -> entero { retornar miLista[0] }";

        ProgramNode program = parseCode(code);
        FunctionDeclarationNode func = (FunctionDeclarationNode) program.getDeclarations().get(0);
        ReturnStatementNode returnStmt = (ReturnStatementNode) func.getBody().getStatements().get(0);

        IndexAccessNode indexAccess = (IndexAccessNode) returnStmt.getValue();
        IntegerLiteralNode index = (IntegerLiteralNode) indexAccess.getIndex();
        assertEquals(0L, index.getValue());
    }

    @Test
    void testFunctionCall() {
        String code = "funcion main() -> entero { retornar suma(1, 2) }";

        ProgramNode program = parseCode(code);
        FunctionDeclarationNode func = (FunctionDeclarationNode) program.getDeclarations().get(0);
        ReturnStatementNode returnStmt = (ReturnStatementNode) func.getBody().getStatements().get(0);

        FunctionCallNode call = (FunctionCallNode) returnStmt.getValue();
        assertEquals(2, call.getArguments().size());

        IdentifierNode funcName = (IdentifierNode) call.getFunction();
        assertEquals("suma", funcName.getName());
    }

    @Test
    void testChainedMemberAccess() {
        String code = "funcion test() -> entero { retornar obj.prop1.prop2 }";

        ProgramNode program = parseCode(code);
        FunctionDeclarationNode func = (FunctionDeclarationNode) program.getDeclarations().get(0);
        ReturnStatementNode returnStmt = (ReturnStatementNode) func.getBody().getStatements().get(0);

        MemberAccessNode outer = (MemberAccessNode) returnStmt.getValue();
        assertEquals("prop2", outer.getMemberName());

        MemberAccessNode inner = (MemberAccessNode) outer.getObject();
        assertEquals("prop1", inner.getMemberName());
    }

    @Test
    void testMethodCall() {
        String code = "funcion test(obj: objeto) -> entero { retornar obj.metodo(1, 2) }";

        ProgramNode program = parseCode(code);
        FunctionDeclarationNode func = (FunctionDeclarationNode) program.getDeclarations().get(0);
        ReturnStatementNode returnStmt = (ReturnStatementNode) func.getBody().getStatements().get(0);

        FunctionCallNode call = (FunctionCallNode) returnStmt.getValue();
        assertEquals(2, call.getArguments().size());

        MemberAccessNode memberAccess = (MemberAccessNode) call.getFunction();
        assertEquals("metodo", memberAccess.getMemberName());
    }

    @Test
    void testListLiteral() {
        String code = "funcion getList() -> lista { retornar [1, 2, 3] }";

        ProgramNode program = parseCode(code);
        FunctionDeclarationNode func = (FunctionDeclarationNode) program.getDeclarations().get(0);
        ReturnStatementNode returnStmt = (ReturnStatementNode) func.getBody().getStatements().get(0);

        ListLiteralNode list = (ListLiteralNode) returnStmt.getValue();
        assertEquals(3, list.getElements().size());

        IntegerLiteralNode first = (IntegerLiteralNode) list.getElements().get(0);
        assertEquals(1L, first.getValue());
    }

    @Test
    void testObjectLiteral() {
        String code = "funcion getObject() -> objeto { retornar {nombre: \"Juan\", edad: 25} }";

        ProgramNode program = parseCode(code);
        FunctionDeclarationNode func = (FunctionDeclarationNode) program.getDeclarations().get(0);
        ReturnStatementNode returnStmt = (ReturnStatementNode) func.getBody().getStatements().get(0);

        ObjectLiteralNode obj = (ObjectLiteralNode) returnStmt.getValue();
        assertEquals(2, obj.getMembers().size());

        ObjectLiteralNode.ObjectMember firstMember = obj.getMembers().get(0);
        assertEquals("nombre", firstMember.getKey());

        StringLiteralNode nameValue = (StringLiteralNode) firstMember.getValue();
        assertEquals("Juan", nameValue.getValue());
    }

    @Test
    void testMultipleFunctions() {
        String code = """
                funcion suma(a: entero, b: entero) -> entero {
                    retornar a + b
                }

                funcion resta(a: entero, b: entero) -> entero {
                    retornar a - b
                }
                """;

        ProgramNode program = parseCode(code);

        assertEquals(2, program.getDeclarations().size());

        FunctionDeclarationNode func1 = (FunctionDeclarationNode) program.getDeclarations().get(0);
        assertEquals("suma", func1.getName());

        FunctionDeclarationNode func2 = (FunctionDeclarationNode) program.getDeclarations().get(1);
        assertEquals("resta", func2.getName());
    }

    @Test
    void testComplexExpression() {
        String code = "funcion calc(a: entero, b: entero) -> booleano { retornar (a + b) * 2 > 10 y a != 0 }";

        ProgramNode program = parseCode(code);
        FunctionDeclarationNode func = (FunctionDeclarationNode) program.getDeclarations().get(0);
        ReturnStatementNode returnStmt = (ReturnStatementNode) func.getBody().getStatements().get(0);

        // Should be: ((a + b) * 2 > 10) y (a != 0)
        BinaryExpressionNode andExpr = (BinaryExpressionNode) returnStmt.getValue();
        assertEquals(BinaryExpressionNode.Operator.AND, andExpr.getOperator());

        BinaryExpressionNode gtExpr = (BinaryExpressionNode) andExpr.getLeft();
        assertEquals(BinaryExpressionNode.Operator.GREATER_THAN, gtExpr.getOperator());

        BinaryExpressionNode neExpr = (BinaryExpressionNode) andExpr.getRight();
        assertEquals(BinaryExpressionNode.Operator.NOT_EQUAL, neExpr.getOperator());
    }

    @Test
    void testParserError() {
        String invalidCode = "funcion test( -> entero { }"; // Missing parameter

        assertThrows(ParserException.class, () -> {
            parseCode(invalidCode);
        });
    }

    @Test
    void testIfStatement() {
        String code = """
                funcion test(x: entero) -> entero {
                    si (x > 0) {
                        retornar 1
                    } sino_si (x < 0) {
                        retornar -1
                    } sino {
                        retornar 0
                    }
                }
                """;

        ProgramNode program = parseCode(code);
        FunctionDeclarationNode func = (FunctionDeclarationNode) program.getDeclarations().get(0);
        BlockNode body = func.getBody();

        assertEquals(1, body.getStatements().size());
        IfStatementNode ifStmt = (IfStatementNode) body.getStatements().get(0);

        // Should have 3 branches: si, sino_si, sino
        assertEquals(3, ifStmt.getBranches().size());

        // First branch (si) should have a condition
        assertFalse(ifStmt.getBranches().get(0).isElseBranch());
        assertNotNull(ifStmt.getBranches().get(0).getCondition());

        // Second branch (sino_si) should have a condition
        assertFalse(ifStmt.getBranches().get(1).isElseBranch());
        assertNotNull(ifStmt.getBranches().get(1).getCondition());

        // Third branch (sino) should not have a condition
        assertTrue(ifStmt.getBranches().get(2).isElseBranch());
        assertNull(ifStmt.getBranches().get(2).getCondition());
    }

    @Test
    void testForStatement() {
        String code = """
                funcion sumar(numeros: lista) -> entero {
                    suma = 0
                    para num en numeros {
                        suma = suma + num
                    }
                    retornar suma
                }
                """;

        ProgramNode program = parseCode(code);
        FunctionDeclarationNode func = (FunctionDeclarationNode) program.getDeclarations().get(0);
        BlockNode body = func.getBody();

        assertEquals(3, body.getStatements().size());
        ForStatementNode forStmt = (ForStatementNode) body.getStatements().get(1);

        assertEquals("num", forStmt.getIteratorVariable());
        assertNotNull(forStmt.getIterable());
        assertNotNull(forStmt.getBody());

        IdentifierNode iterableId = (IdentifierNode) forStmt.getIterable();
        assertEquals("numeros", iterableId.getName());

        // Body should have assignment statement
        assertEquals(1, forStmt.getBody().getStatements().size());
    }

    @Test
    void testProcessDeclarationWithStartAndEnd() {
        String code = """
                proceso SimpleProcess {
                    inicio -> PrimeraTarea
                    tarea PrimeraTarea {
                        accion:
                            ejecutar()
                    }
                    fin Completado
                }
                """;

        ProgramNode program = parseCode(code);

        assertEquals(1, program.getDeclarations().size());
        ProcessDeclarationNode process = (ProcessDeclarationNode) program.getDeclarations().get(0);

        assertEquals("SimpleProcess", process.getName());
        assertEquals(3, process.getElements().size());

        // Verify elements
        assertTrue(process.getElements().get(0) instanceof edu.eam.ingesoft.tlf.flowscript.parser.ast.process.StartElementNode);
        assertTrue(process.getElements().get(1) instanceof edu.eam.ingesoft.tlf.flowscript.parser.ast.process.TaskElementNode);
        assertTrue(process.getElements().get(2) instanceof edu.eam.ingesoft.tlf.flowscript.parser.ast.process.EndElementNode);
    }

    @Test
    void testProcessWithExclusiveGateway() {
        String code = """
                proceso ProcesoConGateway {
                    inicio -> DecisionTarea
                    tarea DecisionTarea {
                        accion:
                            si (x > 0) { ir_a TareaA }
                            sino { ir_a TareaB }
                    }
                    tarea TareaA {
                        accion:
                            hacer_algo_a()
                    }
                    tarea TareaB {
                        accion:
                            hacer_algo_b()
                    }
                    fin Fin
                }
                """;

        ProgramNode program = parseCode(code);
        ProcessDeclarationNode process = (ProcessDeclarationNode) program.getDeclarations().get(0);

        assertEquals("ProcesoConGateway", process.getName());
        assertEquals(5, process.getElements().size());

        // Second element should be an ExclusiveGateway
        assertTrue(process.getElements().get(1) instanceof edu.eam.ingesoft.tlf.flowscript.parser.ast.process.ExclusiveGatewayNode);

        edu.eam.ingesoft.tlf.flowscript.parser.ast.process.ExclusiveGatewayNode gateway =
                (edu.eam.ingesoft.tlf.flowscript.parser.ast.process.ExclusiveGatewayNode) process.getElements().get(1);

        // Should have 2 branches
        assertEquals(2, gateway.getBranches().size());
        assertEquals("TareaA", gateway.getBranches().get(0).getTargetTaskName());
        assertEquals("TareaB", gateway.getBranches().get(1).getTargetTaskName());
    }

    @Test
    void testProcessWithParallelGateway() {
        String code = """
                proceso ProcesoParalelo {
                    inicio -> Inicio
                    tarea Inicio {
                        accion:
                            preparar()
                    }
                    gateway GatewayParalelo paralelo {
                        rama -> TareaA
                        rama -> TareaB
                        unir -> Fin
                    }
                    tarea TareaA {
                        accion:
                            ejecutar_a()
                    }
                    tarea TareaB {
                        accion:
                            ejecutar_b()
                    }
                    fin Fin
                }
                """;

        ProgramNode program = parseCode(code);
        ProcessDeclarationNode process = (ProcessDeclarationNode) program.getDeclarations().get(0);

        assertEquals("ProcesoParalelo", process.getName());

        // Find the parallel gateway
        edu.eam.ingesoft.tlf.flowscript.parser.ast.process.ParallelGatewayNode gateway =
                (edu.eam.ingesoft.tlf.flowscript.parser.ast.process.ParallelGatewayNode) process.getElements().get(2);

        assertEquals("GatewayParalelo", gateway.getName());
        assertEquals(2, gateway.getBranches().size());
        assertEquals("TareaA", gateway.getBranches().get(0).getTargetTaskName());
        assertEquals("TareaB", gateway.getBranches().get(1).getTargetTaskName());
        assertEquals("Fin", gateway.getMergeTargetName());
    }

    /**
     * Helper method to parse code and return the AST.
     */
    private ProgramNode parseCode(String code) {
        // Lex the code
        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenizeFiltered();

        // Parse the tokens
        ParserContext context = new ParserContext(tokens);
        ProgramParser parser = new ProgramParser();
        return parser.parse(context);
    }
}
