package com.flowscript.sintactic.base;

import com.flowscript.sintactic.ProgramParser;
import com.flowscript.sintactic.TestHelper;
import com.flowscript.sintactic.ast.ProgramNode;
import com.flowscript.sintactic.ast.DeclarationNode;
import com.flowscript.sintactic.ast.declarations.ImportDeclarationNode;
import com.flowscript.sintactic.ast.functions.FunctionDeclarationNode;
import com.flowscript.sintactic.ast.process.ProcessDeclarationNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ProgramParser.
 */
public class ProgramParserTest {

    @Test
    public void testEmptyProgram() throws Exception {
        ProgramParser parser = TestHelper.createParser(ProgramParser.class, "");
        ProgramNode program = parser.parse();

        assertNotNull(program);
        assertEquals(0, program.getDeclarations().size());
        assertEquals("Program", program.getNodeType());
    }

    @Test
    public void testProgramWithImport() throws Exception {
        String code = "import \"std/json\" as Json";
        ProgramParser parser = TestHelper.createParser(ProgramParser.class, code);
        ProgramNode program = parser.parse();

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());

        DeclarationNode decl = program.getDeclarations().get(0);
        assertTrue(decl instanceof ImportDeclarationNode);
        ImportDeclarationNode importDecl = (ImportDeclarationNode) decl;
        assertEquals("std/json", importDecl.getModulePath());
        assertEquals("Json", importDecl.getAlias());
        assertFalse(importDecl.isJarImport());
    }

    @Test
    public void testProgramWithFunction() throws Exception {
        String code = "function test() -> void { }";
        ProgramParser parser = TestHelper.createParser(ProgramParser.class, code);
        ProgramNode program = parser.parse();

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());

        DeclarationNode decl = program.getDeclarations().get(0);
        assertTrue(decl instanceof FunctionDeclarationNode);
        FunctionDeclarationNode funcDecl = (FunctionDeclarationNode) decl;
        assertEquals("test", funcDecl.getName());
        assertEquals("void", funcDecl.getReturnTypeName());
        assertEquals(0, funcDecl.getParameters().size());
    }

    @Test
    public void testProgramWithProcess() throws Exception {
        String code = """
            process TestProcess {
                start -> Task1
                end Task1
            }
            """;
        ProgramParser parser = TestHelper.createParser(ProgramParser.class, code);
        ProgramNode program = parser.parse();

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());

        DeclarationNode decl = program.getDeclarations().get(0);
        assertTrue(decl instanceof ProcessDeclarationNode);
        ProcessDeclarationNode processDecl = (ProcessDeclarationNode) decl;
        assertEquals("TestProcess", processDecl.getName());
        assertEquals(2, processDecl.getElements().size()); // start + end
    }

    @Test
    public void testComplexProgram() throws Exception {
        String code = """
            import \"std/json\" as Json

            function calculate(x: integer) -> integer {
                return x * 2
            }

            process MainProcess {
                start -> Calculate
                task Calculate {
                    action: result = calculate(5)
                }
                end Done
            }
            """;

        ProgramParser parser = TestHelper.createParser(ProgramParser.class, code);
        ProgramNode program = parser.parse();

        assertNotNull(program);
        assertEquals(3, program.getDeclarations().size());

        // Import
        assertTrue(program.getDeclarations().get(0) instanceof ImportDeclarationNode);

        // Function
        assertTrue(program.getDeclarations().get(1) instanceof FunctionDeclarationNode);
        FunctionDeclarationNode func = (FunctionDeclarationNode) program.getDeclarations().get(1);
        assertEquals("calculate", func.getName());

        // Process
        assertTrue(program.getDeclarations().get(2) instanceof ProcessDeclarationNode);
        ProcessDeclarationNode proc = (ProcessDeclarationNode) program.getDeclarations().get(2);
        assertEquals("MainProcess", proc.getName());
    }

    @Test
    public void testErrorRecovery() throws Exception {
        // Program with syntax error should attempt recovery
        String code = """
            import "valid"
            invalid syntax here
            function valid() -> void { }
            """;

        ProgramParser parser = TestHelper.createParser(ProgramParser.class, code);
        // Should not crash, may parse some valid parts
        assertDoesNotThrow(() -> parser.parse());
    }
}