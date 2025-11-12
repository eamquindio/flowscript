package edu.eam.ingesoft.tlf.flowscript.transpiler;

/**
 * Representa un error ocurrido durante alguna fase del transpiler.
 */
public class TranspilerError {
    private final TranspilerPhase phase;
    private final String message;
    private final int line;
    private final int column;
    private final String sourceSnippet;

    public TranspilerError(TranspilerPhase phase, String message, int line, int column, String sourceSnippet) {
        this.phase = phase;
        this.message = message;
        this.line = line;
        this.column = column;
        this.sourceSnippet = sourceSnippet;
    }

    public TranspilerError(TranspilerPhase phase, String message, int line, int column) {
        this(phase, message, line, column, null);
    }

    public TranspilerError(TranspilerPhase phase, String message) {
        this(phase, message, -1, -1, null);
    }

    public TranspilerPhase getPhase() {
        return phase;
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

        sb.append("[").append(phase.getName()).append("] ");
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
