package com.flowscript.sintactic.ast.process.estructura_principal;

import com.flowscript.sintactic.ast.ASTNode;

/**
 * Clase base abstracta para todos los elementos de un proceso.
 *
 * <p>Los elementos de proceso incluyen:</p>
 * <ul>
 *   <li>StartElement - punto de inicio</li>
 *   <li>TaskElement - tareas</li>
 *   <li>ExclusiveGateway - gateways exclusivos (XOR)</li>
 *   <li>ParallelGateway - gateways paralelos (AND)</li>
 *   <li>EndElement - puntos de fin</li>
 * </ul>
 */
public abstract class ProcessElementNode extends ASTNode {

    public ProcessElementNode(int line, int column, int position) {
        super(line, column, position);
    }
}
