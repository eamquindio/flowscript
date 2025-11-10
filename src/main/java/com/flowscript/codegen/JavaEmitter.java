package com.flowscript.codegen;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Utility class for generating Java code.
 * Provides methods to build Java source code programmatically.
 */
public class JavaEmitter {
    private final StringBuilder code;
    private int indentLevel;
    private final Set<String> imports;
    private String packageName;

    public JavaEmitter() {
        this.code = new StringBuilder();
        this.imports = new LinkedHashSet<>();
        this.indentLevel = 0;
    }

    /**
     * Resets the emitter to initial state.
     */
    public void reset() {
        code.setLength(0);
        imports.clear();
        indentLevel = 0;
        packageName = null;
    }

    /**
     * Sets the package name for the generated code.
     */
    public void setPackage(String pkg) {
        this.packageName = pkg;
    }

    /**
     * Adds an import statement.
     * Duplicates are automatically ignored.
     */
    public void addImport(String importStatement) {
        imports.add(importStatement);
    }

    /**
     * Adds multiple imports.
     */
    public void addImports(String... importStatements) {
        for (String imp : importStatements) {
            imports.add(imp);
        }
    }

    /**
     * Emits a line of code with proper indentation.
     */
    public void emit(String line) {
        if (line.isEmpty()) {
            code.append("\n");
        } else {
            code.append(getIndent()).append(line).append("\n");
        }
    }

    /**
     * Emits multiple lines of code.
     */
    public void emitLines(String... lines) {
        for (String line : lines) {
            emit(line);
        }
    }

    /**
     * Emits code without a newline.
     */
    public void emitInline(String text) {
        code.append(text);
    }

    /**
     * Emits a newline.
     */
    public void newLine() {
        code.append("\n");
    }

    /**
     * Increases indentation level.
     */
    public void indent() {
        indentLevel++;
    }

    /**
     * Decreases indentation level.
     */
    public void dedent() {
        if (indentLevel > 0) {
            indentLevel--;
        }
    }

    /**
     * Emits an opening brace and increases indentation.
     */
    public void openBlock() {
        emit("{");
        indent();
    }

    /**
     * Emits a closing brace and decreases indentation.
     */
    public void closeBlock() {
        dedent();
        emit("}");
    }

    /**
     * Emits a closing brace with semicolon (for static blocks, etc.)
     */
    public void closeBlockWithSemicolon() {
        dedent();
        emit("};");
    }

    /**
     * Gets the current indentation string.
     */
    private String getIndent() {
        return "    ".repeat(indentLevel);
    }

    /**
     * Builds and returns the complete Java source code.
     */
    public String getCode() {
        StringBuilder result = new StringBuilder();

        // Package declaration
        if (packageName != null && !packageName.isEmpty()) {
            result.append("package ").append(packageName).append(";\n\n");
        }

        // Import statements
        if (!imports.isEmpty()) {
            for (String imp : imports) {
                result.append("import ").append(imp).append(";\n");
            }
            result.append("\n");
        }

        // Generated code
        result.append(code);

        return result.toString();
    }

    /**
     * Returns the code without package and imports.
     */
    public String getCodeOnly() {
        return code.toString();
    }

    /**
     * Returns current indentation level.
     */
    public int getIndentLevel() {
        return indentLevel;
    }

    /**
     * Sets indentation level (useful for nested contexts).
     */
    public void setIndentLevel(int level) {
        this.indentLevel = Math.max(0, level);
    }

    /**
     * Helper: Emits a method signature.
     */
    public void emitMethodSignature(String visibility, boolean isStatic, String returnType,
                                     String methodName, String parameters) {
        StringBuilder sig = new StringBuilder();
        sig.append(visibility).append(" ");
        if (isStatic) {
            sig.append("static ");
        }
        sig.append(returnType).append(" ").append(methodName);
        sig.append("(").append(parameters).append(")");
        emit(sig.toString());
    }

    /**
     * Helper: Emits a field declaration.
     */
    public void emitField(String visibility, boolean isStatic, boolean isFinal,
                          String type, String name, String initialValue) {
        StringBuilder field = new StringBuilder();
        field.append(visibility).append(" ");
        if (isStatic) {
            field.append("static ");
        }
        if (isFinal) {
            field.append("final ");
        }
        field.append(type).append(" ").append(name);
        if (initialValue != null) {
            field.append(" = ").append(initialValue);
        }
        field.append(";");
        emit(field.toString());
    }

    /**
     * Helper: Begins a class declaration.
     */
    public void beginClass(String visibility, String className, String extendsClass,
                           String... implementsInterfaces) {
        StringBuilder classDef = new StringBuilder();
        classDef.append(visibility).append(" class ").append(className);

        if (extendsClass != null && !extendsClass.isEmpty()) {
            classDef.append(" extends ").append(extendsClass);
        }

        if (implementsInterfaces != null && implementsInterfaces.length > 0) {
            classDef.append(" implements ");
            classDef.append(String.join(", ", implementsInterfaces));
        }

        emit(classDef.toString());
        openBlock();
    }

    /**
     * Helper: Ends a class declaration.
     */
    public void endClass() {
        closeBlock();
    }

    /**
     * Helper: Emits a comment.
     */
    public void emitComment(String comment) {
        emit("// " + comment);
    }

    /**
     * Helper: Emits a block comment.
     */
    public void emitBlockComment(String... lines) {
        emit("/*");
        for (String line : lines) {
            emit(" * " + line);
        }
        emit(" */");
    }

    /**
     * Helper: Emits a JavaDoc comment.
     */
    public void emitJavaDoc(String... lines) {
        emit("/**");
        for (String line : lines) {
            emit(" * " + line);
        }
        emit(" */");
    }
}
