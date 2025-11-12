package edu.eam.ingesoft.tlf.flowscript.semantic;

/**
 * Representa un error semántico encontrado durante el análisis.
 */
public class SemanticError {
    private final String message;
    private final int line;
    private final int column;
    private final ErrorType errorType;
    private final String sourceSnippet;

    public enum ErrorType {
        DUPLICATE_DECLARATION("Declaración duplicada"),
        UNDECLARED_VARIABLE("Variable no declarada"),
        UNDECLARED_FUNCTION("Función no declarada"),
        UNDECLARED_TASK("Tarea no declarada"),
        UNDECLARED_PROCESS("Proceso no declarado"),
        TYPE_MISMATCH("Incompatibilidad de tipos"),
        INVALID_ARGUMENT_COUNT("Número incorrecto de argumentos"),
        INVALID_RETURN("Retorno inválido"),
        MISSING_RETURN("Falta declaración de retorno"),
        UNREACHABLE_CODE("Código inalcanzable"),
        CIRCULAR_DEPENDENCY("Dependencia circular"),
        INVALID_BREAK("break fuera de ciclo"),
        INVALID_CONTINUE("continue fuera de ciclo"),
        PROCESS_NO_START("Proceso sin elemento inicio"),
        PROCESS_NO_END("Proceso sin elemento fin"),
        TASK_UNREACHABLE("Tarea inalcanzable"),
        INVALID_GOTO_TARGET("Destino de ir_a inválido"),
        GOTO_OUTSIDE_PROCESS("ir_a usado fuera de proceso"),
        INVALID_DB_OPERATION("Operación de base de datos inválida"),
        INVALID_HTTP_OPERATION("Operación HTTP inválida");

        private final String description;

        ErrorType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public SemanticError(ErrorType errorType, String message, int line, int column, String sourceSnippet) {
        this.errorType = errorType;
        this.message = message;
        this.line = line;
        this.column = column;
        this.sourceSnippet = sourceSnippet;
    }

    public SemanticError(ErrorType errorType, String message, int line, int column) {
        this(errorType, message, line, column, null);
    }

    public String getMessage() {
        return message;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public String getSourceSnippet() {
        return sourceSnippet;
    }

    public boolean hasLocation() {
        return line >= 0 && column >= 0;
    }

    public boolean hasSourceSnippet() {
        return sourceSnippet != null && !sourceSnippet.isEmpty();
    }

    /**
     * Formatea el error para mostrar al usuario.
     */
    public String format() {
        StringBuilder sb = new StringBuilder();

        sb.append("[Error Semántico: ").append(errorType.getDescription()).append("] ");
        sb.append(message);

        if (hasLocation()) {
            sb.append(" (línea ").append(line).append(", columna ").append(column).append(")");
        }

        if (hasSourceSnippet()) {
            sb.append("\n\n");
            sb.append("  ").append(sourceSnippet).append("\n");
            if (hasLocation() && column > 0) {
                sb.append("  ");
                for (int i = 1; i < column; i++) {
                    sb.append(" ");
                }
                sb.append("^");
            }
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return format();
    }
}
