package com.flowscript.sintactic.ast.process.estructura_principal;

import com.flowscript.sintactic.ast.functions.programa_declaraciones.DeclarationNode;
import com.flowscript.sintactic.ast.ASTNode;
import com.flowscript.lexer.Token;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a process declaration in FlowScript.
 * Syntax: process ProcessName { inicio -> Node ... }
 * Contains the process structure and workflow definition.
 */
public class ProcessDeclarationNode extends DeclarationNode {
    private final String name;
    private final List<ASTNode> processElements;

    public ProcessDeclarationNode(Token processToken, String name) {
        super(processToken);
        this.name = name;
        this.processElements = new ArrayList<>();
    }

    public ProcessDeclarationNode(Token processToken, String name, List<ASTNode> processElements) {
        super(processToken);
        this.name = name;
        this.processElements = new ArrayList<>(processElements);
    }

    public String getName() {
        return name;
    }

    public List<ASTNode> getProcessElements() {
        return new ArrayList<>(processElements);
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