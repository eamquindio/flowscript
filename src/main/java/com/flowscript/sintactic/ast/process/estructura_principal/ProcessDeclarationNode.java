package com.flowscript.sintactic.ast.process.estructura_principal;

import com.flowscript.sintactic.ast.functions.programa_declaraciones.DeclarationNode;
import com.flowscript.lexer.Token;

/**
 * Represents a process declaration in FlowScript.
 * Syntax: process ProcessName { inicio -> Node ... }
 * Contains the process structure and workflow definition.
 */
public class ProcessDeclarationNode extends DeclarationNode {
    private final String name;

    public ProcessDeclarationNode(Token processToken, String name) {
        super(processToken);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDeclaredName() {
        return name;
    }

    @Override
    public String getNodeType() {
        return "ProcessDeclaration";
    }

    @Override
    public String toString() {
        return "ProcessDeclaration(" + name + ")";
    }
}