package com.flowscript.sintactic.ast.functions.programa_declaraciones;

import com.flowscript.sintactic.ast.functions.programa_declaraciones.DeclarationNode;
import com.flowscript.lexer.Token;

/**
 * Represents import declarations (both standard imports and JAR imports).
 * Examples:
 * - import "std/json" as Json
 * - import_jar "libs/security.jar" as Security
 */
public class ImportDeclarationNode extends DeclarationNode {
    private final boolean isJarImport;
    private final String modulePath;
    private final String alias;

    public ImportDeclarationNode(Token importToken, boolean isJarImport, String modulePath, String alias) {
        super(importToken);
        this.isJarImport = isJarImport;
        this.modulePath = modulePath;
        this.alias = alias;
    }

    public boolean isJarImport() {
        return isJarImport;
    }

    public String getModulePath() {
        return modulePath;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String getDeclaredName() {
        return alias != null ? alias : extractModuleName(modulePath);
    }

    private String extractModuleName(String path) {
        // Extract module name from path like "std/json" -> "json"
        int lastSlash = path.lastIndexOf('/');
        return lastSlash >= 0 ? path.substring(lastSlash + 1) : path;
    }

    @Override
    public String getNodeType() {
        return isJarImport ? "JarImportDeclaration" : "ImportDeclaration";
    }

    @Override
    public String toString() {
        return getNodeType() + "(" + modulePath +
               (alias != null ? " as " + alias : "") + ")";
    }
}