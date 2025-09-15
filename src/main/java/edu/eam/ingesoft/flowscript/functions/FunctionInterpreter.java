package edu.eam.ingesoft.flowscript.functions;

import java.util.List;

public class FunctionInterpreter {
    public Object execute(FlowScriptFunction function, List<Object> args) {
        // Contexto de variables locales
        java.util.Map<String, Object> context = new java.util.HashMap<>();
        List<Parameter> params = function.getParameters();
        for (int i = 0; i < params.size(); i++) {
            context.put(params.get(i).getName(), args.get(i));
        }

        return executeStatements(function.getBody(), context, function);
    }

    private Object executeStatements(List<Statement> statements, java.util.Map<String, Object> context, FlowScriptFunction function) {
        for (Statement stmt : statements) {
            if (stmt instanceof ReturnStatement) {
                ReturnStatement ret = (ReturnStatement) stmt;
                return evalExpression(ret.getExpression(), context, function);
            } else if (stmt instanceof VariableDeclarationStatement) {
                VariableDeclarationStatement varStmt = (VariableDeclarationStatement) stmt;
                context.put(varStmt.getVariableName(), evalExpression(varStmt.getExpression(), context, function));
            } else if (stmt instanceof IfStatement) {
                IfStatement ifStmt = (IfStatement) stmt;
                Object cond = evalExpression(ifStmt.getCondition(), context, function);
                boolean condBool = cond instanceof Boolean ? (Boolean) cond : ((Number) cond).intValue() != 0;
                if (condBool) {
                    Object result = executeStatements(ifStmt.getThenStatements(), context, function);
                    if (result != null) return result;
                } else {
                    Object result = executeStatements(ifStmt.getElseStatements(), context, function);
                    if (result != null) return result;
                }
            }
            // Se pueden agregar más tipos de statements aquí según la gramática
        }
        return null;
    }

    private Object evalExpression(Expression expr, java.util.Map<String, Object> context, FlowScriptFunction function) {
        if (expr instanceof LiteralExpression) {
            return ((LiteralExpression) expr).getValue();
        } else if (expr instanceof VariableReferenceExpression) {
            return context.get(((VariableReferenceExpression) expr).getVariableName());
        } else if (expr instanceof BinaryExpression) {
            BinaryExpression bin = (BinaryExpression) expr;
            Object left = evalExpression(bin.getLeft(), context, function);
            Object right = evalExpression(bin.getRight(), context, function);
            switch (bin.getOperator()) {
                case "+": return toNumber(left) + toNumber(right);
                case "-": return toNumber(left) - toNumber(right);
                case "*": return toNumber(left) * toNumber(right);
                case "/": return toNumber(left) / toNumber(right);
                case "<=": return toNumber(left) <= toNumber(right);
                case "==": return toNumber(left) == toNumber(right);
                case ">": return toNumber(left) > toNumber(right);
                case "<": return toNumber(left) < toNumber(right);
                default: throw new RuntimeException("Operador no soportado: " + bin.getOperator());
            }
        } else if (expr instanceof FunctionCallExpression) {
            FunctionCallExpression call = (FunctionCallExpression) expr;
            // Solo soporta recursividad directa para factorial
            if (call.getFunctionName().equals(function.getName())) {
                Object arg = evalExpression(call.getArguments().get(0), context, function);
                return execute(function, java.util.Arrays.asList(arg));
            }
        }
        return null;
    }

    private int toNumber(Object obj) {
        if (obj instanceof Integer) return (Integer) obj;
        if (obj instanceof Double) return ((Double) obj).intValue();
        if (obj instanceof Boolean) return (Boolean) obj ? 1 : 0;
        if (obj instanceof Number) return ((Number) obj).intValue();
        throw new RuntimeException("No se puede convertir a número: " + obj);
    }
    }
