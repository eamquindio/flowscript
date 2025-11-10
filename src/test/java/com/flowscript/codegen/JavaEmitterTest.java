package com.flowscript.codegen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JavaEmitterTest {

    private JavaEmitter emitter;

    @BeforeEach
    void setUp() {
        emitter = new JavaEmitter();
    }

    // ========== Basic Emission ==========

    @Test
    void testEmit_SingleLine() {
        emitter.emit("System.out.println(\"Hello\");");
        assertEquals("System.out.println(\"Hello\");\n", emitter.getCodeOnly());
    }

    @Test
    void testEmit_MultipleLines() {
        emitter.emit("int x = 5;");
        emitter.emit("int y = 10;");
        assertEquals("int x = 5;\nint y = 10;\n", emitter.getCodeOnly());
    }

    @Test
    void testEmit_EmptyLine() {
        emitter.emit("");
        assertEquals("\n", emitter.getCodeOnly());
    }

    @Test
    void testEmitLines() {
        emitter.emitLines("line1", "line2", "line3");
        assertEquals("line1\nline2\nline3\n", emitter.getCodeOnly());
    }

    @Test
    void testEmitInline() {
        emitter.emitInline("System.out.print(");
        emitter.emitInline("\"Hello\"");
        emitter.emitInline(");");
        emitter.newLine();
        assertEquals("System.out.print(\"Hello\");\n", emitter.getCodeOnly());
    }

    @Test
    void testNewLine() {
        emitter.emitInline("test");
        emitter.newLine();
        emitter.emitInline("test2");
        assertEquals("test\ntest2", emitter.getCodeOnly());
    }

    // ========== Indentation ==========

    @Test
    void testIndent_SingleLevel() {
        emitter.indent();
        emitter.emit("indented");
        assertEquals("    indented\n", emitter.getCodeOnly());
    }

    @Test
    void testIndent_MultiLevel() {
        emitter.indent();
        emitter.indent();
        emitter.emit("double indented");
        assertEquals("        double indented\n", emitter.getCodeOnly());
    }

    @Test
    void testDedent() {
        emitter.indent();
        emitter.indent();
        emitter.dedent();
        emitter.emit("one level");
        assertEquals("    one level\n", emitter.getCodeOnly());
    }

    @Test
    void testDedent_BelowZero() {
        emitter.dedent();
        emitter.dedent();
        emitter.emit("no indent");
        assertEquals("no indent\n", emitter.getCodeOnly());
    }

    @Test
    void testGetIndentLevel() {
        assertEquals(0, emitter.getIndentLevel());
        emitter.indent();
        assertEquals(1, emitter.getIndentLevel());
        emitter.indent();
        assertEquals(2, emitter.getIndentLevel());
        emitter.dedent();
        assertEquals(1, emitter.getIndentLevel());
    }

    @Test
    void testSetIndentLevel() {
        emitter.setIndentLevel(3);
        assertEquals(3, emitter.getIndentLevel());
        emitter.emit("test");
        assertEquals("            test\n", emitter.getCodeOnly());
    }

    @Test
    void testSetIndentLevel_Negative() {
        emitter.setIndentLevel(-5);
        assertEquals(0, emitter.getIndentLevel());
    }

    // ========== Blocks ==========

    @Test
    void testOpenBlock() {
        emitter.openBlock();
        emitter.emit("inside");
        assertEquals("{\n    inside\n", emitter.getCodeOnly());
    }

    @Test
    void testCloseBlock() {
        emitter.openBlock();
        emitter.emit("inside");
        emitter.closeBlock();
        assertEquals("{\n    inside\n}\n", emitter.getCodeOnly());
    }

    @Test
    void testCloseBlockWithSemicolon() {
        emitter.openBlock();
        emitter.emit("inside");
        emitter.closeBlockWithSemicolon();
        assertEquals("{\n    inside\n};\n", emitter.getCodeOnly());
    }

    @Test
    void testNestedBlocks() {
        emitter.openBlock();
        emitter.emit("outer");
        emitter.openBlock();
        emitter.emit("inner");
        emitter.closeBlock();
        emitter.closeBlock();
        assertEquals("{\n    outer\n    {\n        inner\n    }\n}\n", emitter.getCodeOnly());
    }

    // ========== Package and Imports ==========

    @Test
    void testSetPackage() {
        emitter.setPackage("com.example.test");
        emitter.emit("class Test {}");
        String code = emitter.getCode();
        assertTrue(code.startsWith("package com.example.test;\n\n"));
    }

    @Test
    void testSetPackage_Empty() {
        emitter.setPackage("");
        emitter.emit("class Test {}");
        String code = emitter.getCode();
        assertFalse(code.startsWith("package"));
    }

    @Test
    void testAddImport_Single() {
        emitter.addImport("java.util.List");
        emitter.emit("class Test {}");
        String code = emitter.getCode();
        assertTrue(code.contains("import java.util.List;\n"));
    }

    @Test
    void testAddImport_Multiple() {
        emitter.addImport("java.util.List");
        emitter.addImport("java.util.Map");
        emitter.addImport("java.util.Set");
        emitter.emit("class Test {}");
        String code = emitter.getCode();
        assertTrue(code.contains("import java.util.List;\n"));
        assertTrue(code.contains("import java.util.Map;\n"));
        assertTrue(code.contains("import java.util.Set;\n"));
    }

    @Test
    void testAddImport_Duplicates() {
        emitter.addImport("java.util.List");
        emitter.addImport("java.util.List");
        emitter.addImport("java.util.List");
        emitter.emit("class Test {}");
        String code = emitter.getCode();
        // Should only appear once
        int count = code.split("import java.util.List;", -1).length - 1;
        assertEquals(1, count);
    }

    @Test
    void testAddImports_Array() {
        emitter.addImports("java.util.List", "java.util.Map", "java.util.Set");
        emitter.emit("class Test {}");
        String code = emitter.getCode();
        assertTrue(code.contains("import java.util.List;\n"));
        assertTrue(code.contains("import java.util.Map;\n"));
        assertTrue(code.contains("import java.util.Set;\n"));
    }

    @Test
    void testPackageAndImports() {
        emitter.setPackage("com.example");
        emitter.addImport("java.util.List");
        emitter.addImport("java.util.Map");
        emitter.emit("class Test {}");
        String code = emitter.getCode();

        assertTrue(code.startsWith("package com.example;\n\n"));
        assertTrue(code.contains("import java.util.List;\n"));
        assertTrue(code.contains("import java.util.Map;\n"));
        assertTrue(code.contains("\nclass Test {}"));
    }

    // ========== Helper Methods ==========

    @Test
    void testEmitMethodSignature_PublicStatic() {
        emitter.emitMethodSignature("public", true, "void", "main", "String[] args");
        assertEquals("public static void main(String[] args)\n", emitter.getCodeOnly());
    }

    @Test
    void testEmitMethodSignature_PrivateNonStatic() {
        emitter.emitMethodSignature("private", false, "int", "calculate", "int a, int b");
        assertEquals("private int calculate(int a, int b)\n", emitter.getCodeOnly());
    }

    @Test
    void testEmitMethodSignature_NoParameters() {
        emitter.emitMethodSignature("public", true, "void", "run", "");
        assertEquals("public static void run()\n", emitter.getCodeOnly());
    }

    @Test
    void testEmitField_PublicStaticFinal() {
        emitter.emitField("public", true, true, "int", "MAX_VALUE", "100");
        assertEquals("public static final int MAX_VALUE = 100;\n", emitter.getCodeOnly());
    }

    @Test
    void testEmitField_PrivateNonStatic() {
        emitter.emitField("private", false, false, "String", "name", null);
        assertEquals("private String name;\n", emitter.getCodeOnly());
    }

    @Test
    void testEmitField_WithInitializer() {
        emitter.emitField("private", false, false, "List<String>", "items", "new ArrayList<>()");
        assertEquals("private List<String> items = new ArrayList<>();\n", emitter.getCodeOnly());
    }

    @Test
    void testBeginClass_Simple() {
        emitter.beginClass("public", "MyClass", null);
        assertEquals("public class MyClass\n{\n", emitter.getCodeOnly());
        assertEquals(1, emitter.getIndentLevel());
    }

    @Test
    void testBeginClass_WithExtends() {
        emitter.beginClass("public", "MyClass", "BaseClass");
        assertEquals("public class MyClass extends BaseClass\n{\n", emitter.getCodeOnly());
    }

    @Test
    void testBeginClass_WithImplements() {
        emitter.beginClass("public", "MyClass", null, "Interface1", "Interface2");
        assertEquals("public class MyClass implements Interface1, Interface2\n{\n",
                     emitter.getCodeOnly());
    }

    @Test
    void testBeginClass_WithExtendsAndImplements() {
        emitter.beginClass("public", "MyClass", "BaseClass", "Interface1", "Interface2");
        assertEquals("public class MyClass extends BaseClass implements Interface1, Interface2\n{\n",
                     emitter.getCodeOnly());
    }

    @Test
    void testEndClass() {
        emitter.beginClass("public", "MyClass", null);
        emitter.emit("// body");
        emitter.endClass();
        assertEquals("public class MyClass\n{\n    // body\n}\n", emitter.getCodeOnly());
    }

    @Test
    void testCompleteClass() {
        emitter.setPackage("com.example");
        emitter.addImport("java.util.List");
        emitter.beginClass("public", "Example", null);
        emitter.emitField("private", false, false, "String", "name", null);
        emitter.newLine();
        emitter.emitMethodSignature("public", false, "void", "setName", "String name");
        emitter.openBlock();
        emitter.emit("this.name = name;");
        emitter.closeBlock();
        emitter.endClass();

        String code = emitter.getCode();
        assertTrue(code.startsWith("package com.example;\n\n"));
        assertTrue(code.contains("import java.util.List;\n"));
        assertTrue(code.contains("public class Example\n"));
        assertTrue(code.contains("private String name;\n"));
        assertTrue(code.contains("public void setName(String name)\n"));
        assertTrue(code.contains("this.name = name;"));
    }

    // ========== Comments ==========

    @Test
    void testEmitComment() {
        emitter.emitComment("This is a comment");
        assertEquals("// This is a comment\n", emitter.getCodeOnly());
    }

    @Test
    void testEmitBlockComment() {
        emitter.emitBlockComment("Line 1", "Line 2", "Line 3");
        assertEquals("/*\n * Line 1\n * Line 2\n * Line 3\n */\n", emitter.getCodeOnly());
    }

    @Test
    void testEmitJavaDoc() {
        emitter.emitJavaDoc("Returns the name.", "@return the name");
        assertEquals("/**\n * Returns the name.\n * @return the name\n */\n", emitter.getCodeOnly());
    }

    // ========== Reset ==========

    @Test
    void testReset() {
        emitter.setPackage("com.example");
        emitter.addImport("java.util.List");
        emitter.indent();
        emitter.indent();
        emitter.emit("test");

        emitter.reset();

        assertEquals("", emitter.getCodeOnly());
        assertEquals(0, emitter.getIndentLevel());
        assertEquals("", emitter.getCode()); // Should not have package/imports
    }

    // ========== Edge Cases ==========

    @Test
    void testMultipleResets() {
        emitter.emit("test");
        emitter.reset();
        emitter.emit("test2");
        emitter.reset();
        assertEquals("", emitter.getCodeOnly());
    }

    @Test
    void testGetCodeOnly_NoPackageOrImports() {
        emitter.emit("class Test {}");
        assertEquals("class Test {}\n", emitter.getCodeOnly());
        assertEquals("class Test {}\n", emitter.getCode());
    }
}
