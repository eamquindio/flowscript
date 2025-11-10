package com.flowscript.codegen.generators;

import com.flowscript.codegen.JavaEmitter;
import com.flowscript.codegen.TypeMapper;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.FunctionDeclarationNode;
import com.flowscript.sintactic.ast.functions.tipos_parametros.ParameterNode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates Java code for FlowScript function declarations.
 * Converts functions to static methods.
 */
public class FunctionGenerator {

    private final StatementGenerator statementGenerator;
    private final TypeMapper typeMapper;
    private final JavaEmitter emitter;

    public FunctionGenerator(StatementGenerator statementGenerator,
                            TypeMapper typeMapper,
                            JavaEmitter emitter) {
        this.statementGenerator = statementGenerator;
        this.typeMapper = typeMapper;
        this.emitter = emitter;
    }

    /**
     * Generates a complete function as a Java static method.
     */
    public void generate(FunctionDeclarationNode function) {
        // Generate method signature
        String returnType = function.getReturnType() != null ?
            typeMapper.mapType(function.getReturnType()) : "void";

        String parameters = generateParameters(function.getParameters());

        emitter.emitMethodSignature("public", true, returnType,
                                   function.getName(), parameters);

        // Generate method body
        if (function.getBody() != null) {
            statementGenerator.generate(function.getBody());
        } else {
            // Empty body
            emitter.openBlock();
            emitter.closeBlock();
        }

        emitter.newLine(); // Add blank line after function
    }

    /**
     * Generates parameter list for a function.
     */
    private String generateParameters(List<ParameterNode> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return "";
        }

        return parameters.stream()
                .map(this::generateParameter)
                .collect(Collectors.joining(", "));
    }

    /**
     * Generates a single parameter.
     */
    private String generateParameter(ParameterNode parameter) {
        String type = parameter.getType() != null ?
            typeMapper.mapType(parameter.getType()) : "Object";
        String name = parameter.getName();

        return type + " " + name;
    }
}
