package edu.eam.ingesoft.tlf.flowscript.semantic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa una función en la tabla de funciones.
 */
public class FunctionSymbol {
    private final String name;
    private final String returnType;
    private final List<Parameter> parameters;
    private final int line;
    private final int column;
    private boolean defined; // true si tiene cuerpo, false si solo declaración

    public static class Parameter {
        private final String name;
        private final String type;

        public Parameter(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return name + ": " + type;
        }
    }

    public FunctionSymbol(String name, String returnType, List<Parameter> parameters, int line, int column) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = new ArrayList<>(parameters);
        this.line = line;
        this.column = column;
        this.defined = true;
    }

    public String getName() {
        return name;
    }

    public String getReturnType() {
        return returnType;
    }

    public List<Parameter> getParameters() {
        return Collections.unmodifiableList(parameters);
    }

    public int getParameterCount() {
        return parameters.size();
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public boolean isDefined() {
        return defined;
    }

    public void setDefined(boolean defined) {
        this.defined = defined;
    }

    /**
     * Verifica si la firma de otra función coincide con esta.
     */
    public boolean matchesSignature(FunctionSymbol other) {
        if (!this.name.equals(other.name)) {
            return false;
        }

        if (this.parameters.size() != other.parameters.size()) {
            return false;
        }

        for (int i = 0; i < parameters.size(); i++) {
            if (!parameters.get(i).getType().equals(other.parameters.get(i).getType())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Obtiene la firma de la función como string.
     */
    public String getSignature() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("(");
        for (int i = 0; i < parameters.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(parameters.get(i));
        }
        sb.append(") -> ").append(returnType);
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("Function{%s at line %d, col %d}", getSignature(), line, column);
    }
}
