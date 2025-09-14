package com.flowscript.ide.services;

import com.flowscript.ide.components.ProjectExplorer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ProjectService {
    private Path root;
    private ProjectExplorer explorer;

    public void attachExplorer(ProjectExplorer explorer) {
        this.explorer = explorer;
    }

    public void setRoot(Path rootDir) {
        this.root = rootDir;
        if (explorer != null) explorer.setRootDir(rootDir.toFile());
    }

    public Path getRoot() { return root; }

    public String readFile(Path path) throws IOException { return Files.readString(path); }
    public void writeFile(Path path, String content) throws IOException {
        Files.createDirectories(path.getParent());
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }
}
