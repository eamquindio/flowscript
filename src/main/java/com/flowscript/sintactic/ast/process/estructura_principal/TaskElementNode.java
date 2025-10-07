package com.flowscript.sintactic.ast.process.estructura_principal;

import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.sintactic.ast.functions.control_ejecucion.StatementNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Nodo AST que representa una tarea dentro de un proceso.
 *
 * <p>Ejemplo:</p>
 * <pre>
 * task ValidarDatos {
 *     action:
 *         resultado = validar(entrada)
 *         go_to SiguienteTarea
 * }
 * </pre>
 */
public class TaskElementNode extends ASTNode {
    private final String taskName;
    private final List<StatementNode> actionStatements;

    public TaskElementNode(Token taskToken, String taskName, List<StatementNode> actionStatements) {
        super(taskToken.getLine(), taskToken.getColumn(), taskToken.getPosition());
        this.taskName = taskName;
        this.actionStatements = new ArrayList<>(actionStatements);
    }

    public String getTaskName() {
        return taskName;
    }

    public List<StatementNode> getActionStatements() {
        return new ArrayList<>(actionStatements);
    }

    @Override
    public String getNodeType() {
        return "TaskElement";
    }

    @Override
    public String toString() {
        return "TaskElement(" + taskName + " with " + actionStatements.size() + " statements)";
    }
}
