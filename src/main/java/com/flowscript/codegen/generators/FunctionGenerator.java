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
        // Clear variable scope for new function
        statementGenerator.clearScope();

        // Set current function name for logging in return statements
        statementGenerator.setCurrentFunctionName(function.getName());

        // Generate method signature
        String returnType = function.getReturnType() != null ?
            typeMapper.mapType(function.getReturnType()) : "void";

        String parameters = generateParameters(function.getParameters());
        String paramNames = generateParameterNames(function.getParameters());

        emitter.emitMethodSignature("public", true, returnType,
                                   function.getName(), parameters);

        // Generate method body with logs
        emitter.openBlock();

        // Log entrada
        String entryLog = String.format("System.out.println(\"[ENTRADA] %s(%s)\");",
                                       function.getName(),
                                       paramNames);
        emitter.emit(entryLog);

        // Generate body statements
        if (function.getBody() != null) {
            // Get statements from BlockNode without generating open/close block
            var body = (com.flowscript.sintactic.ast.functions.control_ejecucion.BlockNode) function.getBody();
            if (body.getStatements() != null) {
                statementGenerator.generateStatements(body.getStatements());
            }
        }

        // Log salida (solo si es void, de lo contrario sería unreachable después de returns)
        if ("void".equals(returnType)) {
            String exitLog = String.format("System.out.println(\"[SALIDA] %s\");",
                                          function.getName());
            emitter.emit(exitLog);
        }

        emitter.closeBlock();

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
     * Generates parameter names only (for logging).
     */
    private String generateParameterNames(List<ParameterNode> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return "";
        }

        return parameters.stream()
                .map(ParameterNode::getName)
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
