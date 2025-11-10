package com.flowscript.codegen.generators;

import com.flowscript.codegen.CodeGenerator;
import com.flowscript.codegen.TypeMapper;
import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.Parser;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.FunctionDeclarationNode;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ProgramNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ExpressionGenerator.
 * Uses full parse pipeline to create nodes rather than manual construction.
 */
public class ExpressionGeneratorTest {

    private ExpressionGenerator generator;
    private TypeMapper typeMapper;

    @BeforeEach
    public void setUp() {
        typeMapper = new TypeMapper();
        generator = new ExpressionGenerator(typeMapper);
    }

    @Test
    public void testIntegerLiteralGeneration() throws Exception {
        String code = "funcion test() -> entero { retornar 42 }";
        String generated = generateExpression(code);

        assertTrue(generated.contains("return 42"));
    }

    @Test
    public void testStringLiteralGeneration() throws Exception {
        String code = "funcion test() -> texto { retornar \"Hola\" }";
        String generated = generateExpression(code);

        assertTrue(generated.contains("return \"Hola\""));
    }

    @Test
    public void testBooleanLiteralGeneration() throws Exception {
        String code = "funcion test() -> booleano { retornar verdadero }";
        String generated = generateExpression(code);

        assertTrue(generated.contains("return true"));
    }

    @Test
    public void testAdditiveExpression() throws Exception {
        String code = "funcion test() -> entero { retornar 10 + 20 }";
        String generated = generateExpression(code);

        assertTrue(generated.contains("+"));
    }

    @Test
    public void testMultiplicativeExpression() throws Exception {
        String code = "funcion test() -> entero { retornar 5 * 3 }";
        String generated = generateExpression(code);

        assertTrue(generated.contains("*"));
    }

    @Test
    public void testUnaryExpression() throws Exception {
        String code = "funcion test() -> entero { retornar -5 }";
        String generated = generateExpression(code);

        assertTrue(generated.contains("-"));
    }

    @Test
    public void testLogicalOrExpression() throws Exception {
        String code = "funcion test() -> booleano { retornar verdadero o falso }";
        String generated = generateExpression(code);

        System.out.println("Generated code for 'verdadero o falso':");
        System.out.println(generated);
        System.out.println("Contains ||: " + generated.contains("||"));

        assertTrue(generated.contains("||"));
    }

    @Test
    public void testRelationalExpression() throws Exception {
        String code = "funcion test() -> booleano { retornar 10 > 5 }";
        String generated = generateExpression(code);

        assertTrue(generated.contains(">"));
    }

    @Test
    public void testEqualityExpression() throws Exception {
        String code = "funcion test(a: entero, b: entero) -> booleano { retornar a == b }";
        String generated = generateExpression(code);

        assertTrue(generated.contains("=="));
    }

    @Test
    public void testNullLiteral() throws Exception {
        String code = "funcion test() -> objeto { retornar nulo }";
        String generated = generateExpression(code);

        assertTrue(generated.contains("return null"));
    }

    @Test
    public void testDecimalLiteral() throws Exception {
        String code = "funcion test() -> decimal { retornar 3.14 }";
        String generated = generateExpression(code);

        assertTrue(generated.contains("3.14"));
    }

    @Test
    public void testComplexExpression() throws Exception {
        String code = "funcion test() -> entero { retornar (10 + 20) * 3 }";
        String generated = generateExpression(code);

        assertTrue(generated.contains("+"));
        assertTrue(generated.contains("*"));
    }

    /**
     * Helper method to generate code for an expression.
     */
    private String generateExpression(String flowScriptCode) throws Exception {
        Lexer lexer = new Lexer(flowScriptCode);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser();
        ProgramNode program = parser.parse(tokens);

        CodeGenerator codeGen = new CodeGenerator();
        return codeGen.generate(program);
    }
}
