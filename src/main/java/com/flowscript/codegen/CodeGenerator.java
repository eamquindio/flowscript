package com.flowscript.codegen;

import com.flowscript.codegen.generators.*;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.DeclarationNode;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.FunctionDeclarationNode;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ImportDeclarationNode;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ProgramNode;
import com.flowscript.sintactic.ast.process.estructura_principal.ProcessDeclarationNode;

import java.util.List;

/**
 * Main code generator orchestrator.
 * Coordinates all sub-generators to produce complete Java code.
 */
public class CodeGenerator {

    private final TypeMapper typeMapper;
    private final JavaEmitter emitter;
    private final ExpressionGenerator expressionGenerator;
    private final StatementGenerator statementGenerator;
    private final FunctionGenerator functionGenerator;
    private final ProcessGenerator processGenerator;

    public CodeGenerator() {
        this.typeMapper = new TypeMapper();
        this.emitter = new JavaEmitter();
        this.expressionGenerator = new ExpressionGenerator(typeMapper);
        this.statementGenerator = new StatementGenerator(expressionGenerator, typeMapper, emitter);
        this.functionGenerator = new FunctionGenerator(statementGenerator, typeMapper, emitter);
        this.processGenerator = new ProcessGenerator(statementGenerator, expressionGenerator, typeMapper, emitter);
    }

    /**
     * Generates complete Java code from a FlowScript program AST.
     *
     * @param program The program AST
     * @return Complete Java source code as a string
     */
    public String generate(ProgramNode program) {
        return generate(program, "com.flowscript.generated", "GeneratedCode");
    }

    /**
     * Generates complete Java code with custom package and class name.
     *
     * @param program The program AST
     * @param packageName Package name for generated code
     * @param className Class name for generated code
     * @return Complete Java source code as a string
     */
    public String generate(ProgramNode program, String packageName, String className) {
        emitter.reset();

        // Set package
        emitter.setPackage(packageName);

        // Add standard imports
        emitter.addImports(
            "java.util.*",
            "java.util.concurrent.*"
        );

        // Process declarations
        List<DeclarationNode> declarations = program.getDeclarations();

        // Process custom imports first
        if (declarations != null) {
            for (DeclarationNode declaration : declarations) {
                if (declaration instanceof ImportDeclarationNode) {
                    processImport((ImportDeclarationNode) declaration);
                }
            }
        }

        // Begin class
        emitter.beginClass("public", className, null);

        // Generate functions and processes
        if (declarations != null) {
            for (DeclarationNode declaration : declarations) {
                if (declaration instanceof FunctionDeclarationNode) {
                    functionGenerator.generate((FunctionDeclarationNode) declaration);
                } else if (declaration instanceof ProcessDeclarationNode) {
                    processGenerator.generate((ProcessDeclarationNode) declaration);
                }
            }
        }

        // End class
        emitter.endClass();

        return emitter.getCode();
    }

    /**
     * Generates Java code for a single function.
     */
    public String generateFunction(FunctionDeclarationNode function) {
        emitter.reset();
        functionGenerator.generate(function);
        return emitter.getCodeOnly();
    }

    /**
     * Generates Java code for a single process.
     */
    public String generateProcess(ProcessDeclarationNode process) {
        emitter.reset();
        processGenerator.generate(process);
        return emitter.getCodeOnly();
    }

    /**
     * Processes an import declaration.
     */
    private void processImport(ImportDeclarationNode importNode) {
        // Map FlowScript imports to Java imports
        // For now, just add them as-is
        String modulePath = importNode.getModulePath();
        if (modulePath != null && !modulePath.isEmpty()) {
            emitter.addImport(modulePath);
        }
    }

    /**
     * Gets the emitter (useful for testing).
     */
    public JavaEmitter getEmitter() {
        return emitter;
    }

    /**
     * Gets the type mapper (useful for testing).
     */
    public TypeMapper getTypeMapper() {
        return typeMapper;
    }
}
