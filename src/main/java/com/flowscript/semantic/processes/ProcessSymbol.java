package com.flowscript.semantic.processes;

import com.flowscript.semantic.symbols.Symbol;
import com.flowscript.semantic.types.Type;
import com.flowscript.sintactic.ast.process.estructura_principal.ProcessDeclarationNode;
import java.util.*;

public class ProcessSymbol extends Symbol {
    private final ProcessDeclarationNode node;
    private final Set<String> declaredNodes;
    private final Set<String> reachableNodes;

    public ProcessSymbol(String name, ProcessDeclarationNode node) {
        super(name, Type.VOID, node);
        this.node = node;
        this.declaredNodes = new LinkedHashSet<>();
        this.reachableNodes = new LinkedHashSet<>();
    }

    public ProcessDeclarationNode getNode() {
        return node;
    }

    public void addDeclaredNode(String nodeName) {
        declaredNodes.add(nodeName);
    }

    public boolean hasNode(String nodeName) {
        return declaredNodes.contains(nodeName);
    }

    public Set<String> getDeclaredNodes() {
        return new LinkedHashSet<>(declaredNodes);
    }

    public void addReachableNode(String nodeName) {
        reachableNodes.add(nodeName);
    }

    public boolean isReachable(String nodeName) {
        return reachableNodes.contains(nodeName);
    }

    public Set<String> getUnreachableNodes() {
        Set<String> unreachable = new LinkedHashSet<>(declaredNodes);
        unreachable.removeAll(reachableNodes);
        return unreachable;
    }

    @Override
    public String getKind() {
        return "process";
    }
}
