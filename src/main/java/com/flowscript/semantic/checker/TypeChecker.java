package com.flowscript.semantic.checker;

import com.flowscript.semantic.types.Type;
import com.flowscript.semantic.symbols.SymbolTable;
import com.flowscript.semantic.functions.FunctionTable;
import com.flowscript.semantic.errors.*;

public class TypeChecker {
    private final SymbolTable symbolTable;
    private final FunctionTable functionTable;

    public TypeChecker(SymbolTable symbolTable, FunctionTable functionTable) {
        this.symbolTable = symbolTable;
        this.functionTable = functionTable;
    }

    /**
     * Check if actual type is compatible with expected type.
     */
    public boolean isCompatible(Type expected, Type actual) {
        // Exact match
        if (expected.equals(actual)) {
            return true;
        }

        // ANY type is compatible with anything
        if (expected.equals(Type.ANY) || actual.equals(Type.ANY)) {
            return true;
        }

        // Numeric promotion: entero -> decimal
        if (expected.equals(Type.DECIMAL) && actual.equals(Type.INTEGER)) {
            return true;
        }

        // Any type converts to texto in concatenation context
        if (expected.equals(Type.TEXT)) {
            return true;
        }

        // null is compatible with object/list types
        if (actual.equals(Type.NULL) &&
            (expected.equals(Type.OBJECT) || expected.equals(Type.LIST))) {
            return true;
        }

        return false;
    }

    /**
     * Get result type of binary operation.
     */
    public Type getOperatorResultType(Operator op, Type left, Type right) {
        switch (op) {
            case PLUS:
                return getPlusResultType(left, right);

            case MINUS:
            case MULTIPLY:
            case DIVIDE:
                return getArithmeticResultType(op, left, right);

            case MODULO:
                return getModuloResultType(left, right);

            case AND:
            case OR:
                return getLogicalResultType(op, left, right);

            case LESS_THAN:
            case GREATER_THAN:
            case LESS_EQUAL:
            case GREATER_EQUAL:
                return getRelationalResultType(op, left, right);

            case EQUAL:
            case NOT_EQUAL:
                return Type.BOOLEAN;  // Can compare any types

            default:
                throw new IllegalArgumentException("Unknown operator: " + op);
        }
    }

    private Type getPlusResultType(Type left, Type right) {
        // texto + any -> texto
        if (left.equals(Type.TEXT) || right.equals(Type.TEXT)) {
            return Type.TEXT;
        }

        // decimal + numeric -> decimal
        if (left.equals(Type.DECIMAL) || right.equals(Type.DECIMAL)) {
            if (isNumeric(left) && isNumeric(right)) {
                return Type.DECIMAL;
            }
        }

        // entero + entero -> entero
        if (left.equals(Type.INTEGER) && right.equals(Type.INTEGER)) {
            return Type.INTEGER;
        }

        // ANY involved
        if (left.equals(Type.ANY) || right.equals(Type.ANY)) {
            return Type.ANY;
        }

        throw new IllegalArgumentException(
            "Cannot apply operator + to types " + left + " and " + right
        );
    }

    private Type getArithmeticResultType(Operator op, Type left, Type right) {
        if (!isNumeric(left) || !isNumeric(right)) {
            if (left.equals(Type.ANY) || right.equals(Type.ANY)) {
                return Type.ANY;
            }
            throw new IllegalArgumentException(
                "Operator " + op + " requires numeric types, got " + left + " and " + right
            );
        }

        // If either is decimal, result is decimal
        if (left.equals(Type.DECIMAL) || right.equals(Type.DECIMAL)) {
            return Type.DECIMAL;
        }

        return Type.INTEGER;
    }

    private Type getModuloResultType(Type left, Type right) {
        if (!left.equals(Type.INTEGER) || !right.equals(Type.INTEGER)) {
            if (left.equals(Type.ANY) || right.equals(Type.ANY)) {
                return Type.ANY;
            }
            throw new IllegalArgumentException(
                "Modulo operator requires integer types, got " + left + " and " + right
            );
        }
        return Type.INTEGER;
    }

    private Type getLogicalResultType(Operator op, Type left, Type right) {
        if (!left.equals(Type.BOOLEAN) || !right.equals(Type.BOOLEAN)) {
            if (left.equals(Type.ANY) || right.equals(Type.ANY)) {
                return Type.ANY;
            }
            throw new IllegalArgumentException(
                "Logical operator " + op + " requires boolean types, got " + left + " and " + right
            );
        }
        return Type.BOOLEAN;
    }

    private Type getRelationalResultType(Operator op, Type left, Type right) {
        if (!isComparable(left, right)) {
            if (left.equals(Type.ANY) || right.equals(Type.ANY)) {
                return Type.ANY;
            }
            throw new IllegalArgumentException(
                "Cannot compare types " + left + " and " + right
            );
        }
        return Type.BOOLEAN;
    }

    public boolean isNumeric(Type type) {
        return type.equals(Type.INTEGER) || type.equals(Type.DECIMAL) || type.equals(Type.ANY);
    }

    public boolean isComparable(Type left, Type right) {
        // Numbers are comparable with numbers
        if (isNumeric(left) && isNumeric(right)) {
            return true;
        }
        // Texts are comparable with texts
        if (left.equals(Type.TEXT) && right.equals(Type.TEXT)) {
            return true;
        }
        // ANY is comparable
        if (left.equals(Type.ANY) || right.equals(Type.ANY)) {
            return true;
        }
        return false;
    }

    /**
     * Get unary operator result type.
     */
    public Type getUnaryOperatorResultType(Operator op, Type operandType) {
        switch (op) {
            case NOT:
                if (!operandType.equals(Type.BOOLEAN) && !operandType.equals(Type.ANY)) {
                    throw new IllegalArgumentException(
                        "Logical NOT requires boolean type, got " + operandType
                    );
                }
                return Type.BOOLEAN;

            case MINUS:
                if (!isNumeric(operandType)) {
                    throw new IllegalArgumentException(
                        "Unary minus requires numeric type, got " + operandType
                    );
                }
                return operandType;

            default:
                throw new IllegalArgumentException("Unknown unary operator: " + op);
        }
    }
}
