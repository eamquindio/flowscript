package edu.eam.ingesoft.tlf.flowscript.parser.ast;

/**
 * Clase base abstracta para todos los nodos del Árbol de Sintaxis Abstracta (AST).
 *
 * Proporciona información de posición en el código fuente para facilitar
 * el reporte de errores y el debugging.
 */
public abstract class ASTNode {
    protected int lineNumber;
    protected int columnNumber;

    public ASTNode() {
        this.lineNumber = 0;
        this.columnNumber = 0;
    }

    public ASTNode(int lineNumber, int columnNumber) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    /**
     * Acepta un visitor para el patrón Visitor.
     */
    public abstract void accept(ASTVisitor visitor);

    @Override
    public String toString() {
        return String.format("%s[line=%d, col=%d]",
                getClass().getSimpleName(), lineNumber, columnNumber);
    }
}
