package com.flowscript.codegen;

import com.flowscript.sintactic.ast.functions.tipos_parametros.TypeNode;

/**
 * Maps FlowScript types to Java types.
 * Simple, direct mapping with no complex logic.
 */
public class TypeMapper {

    /**
     * Maps a FlowScript type to a Java type string.
     *
     * @param typeNode The FlowScript type node
     * @return Java type as string (e.g., "int", "String", "List<Integer>")
     */
    public String mapType(TypeNode typeNode) {
        if (typeNode == null) {
            return "void";
        }

        String baseType = typeNode.getTypeName();

        // Primitive types
        switch (baseType) {
            case "entero":
                return "int";
            case "decimal":
                return "double";
            case "booleano":
                return "boolean";
            case "texto":
                return "String";
            case "nulo":
            case "vacio":
                return "void";
            default:
                break;
        }

        // Generic types
        if (baseType.equals("lista")) {
            TypeNode elementType = typeNode.getGenericType();
            if (elementType != null) {
                return "List<" + mapTypeBoxed(elementType) + ">";
            }
            return "List<Object>";
        }

        if (baseType.equals("objeto")) {
            return "Map<String, Object>";
        }

        // Unknown type - return as-is (might be a custom class)
        return baseType;
    }

    /**
     * Maps a FlowScript type to a boxed Java type (for generics).
     *
     * @param typeNode The FlowScript type node
     * @return Boxed Java type (e.g., "Integer", "Double", "Boolean")
     */
    public String mapTypeBoxed(TypeNode typeNode) {
        if (typeNode == null) {
            return "Void";
        }

        String baseType = typeNode.getTypeName();

        switch (baseType) {
            case "entero":
                return "Integer";
            case "decimal":
                return "Double";
            case "booleano":
                return "Boolean";
            case "texto":
                return "String";
            case "nulo":
            case "vacio":
                return "Void";
            default:
                break;
        }

        if (baseType.equals("lista")) {
            TypeNode elementType = typeNode.getGenericType();
            if (elementType != null) {
                return "List<" + mapTypeBoxed(elementType) + ">";
            }
            return "List<Object>";
        }

        if (baseType.equals("objeto")) {
            return "Map<String, Object>";
        }

        return baseType;
    }

    /**
     * Maps FlowScript type name (as string) to Java type.
     * Used when we only have the type name.
     */
    public String mapTypeName(String typeName) {
        switch (typeName) {
            case "entero":
                return "int";
            case "decimal":
                return "double";
            case "booleano":
                return "boolean";
            case "texto":
                return "String";
            case "lista":
                return "List<Object>";
            case "objeto":
                return "Map<String, Object>";
            case "nulo":
            case "vacio":
                return "void";
            default:
                return typeName;
        }
    }

    /**
     * Gets the default value for a type.
     */
    public String getDefaultValue(String javaType) {
        switch (javaType) {
            case "int":
                return "0";
            case "double":
                return "0.0";
            case "boolean":
                return "false";
            default:
                return "null";
        }
    }
}
