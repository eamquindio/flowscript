package com.flowscript.ide.services;

import java.nio.file.Path;

/**
 * Minimal project service placeholder.
 */
public class ProjectService {
    private Path projectRoot;

    public void open(Path root) {
        this.projectRoot = root;
    }

    public Path getProjectRoot() {
        return projectRoot;
    }
}

