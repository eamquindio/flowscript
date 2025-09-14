package com.flowscript.ide.components;

import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

public class ProjectExplorer {
    private final TreeView<File> tree = new TreeView<>();
    private File rootDir;
    private final java.util.function.Consumer<Path> openFileHandler;

    public ProjectExplorer(java.util.function.Consumer<Path> openFileHandler) {
        this.openFileHandler = openFileHandler;
        this.rootDir = new File(System.getProperty("user.dir"));
        setRootDir(this.rootDir);
        tree.setShowRoot(true);
        tree.setCellFactory(tv -> new TreeCell<>() {
            @Override
            protected void updateItem(File item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null); setGraphic(null);
                } else {
                    setText(item.getName().isEmpty() ? item.getAbsolutePath() : item.getName());
                }
            }
        });
        tree.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                TreeItem<File> selected = tree.getSelectionModel().getSelectedItem();
                if (selected != null && selected.getValue().isFile()) {
                    openFileHandler.accept(selected.getValue().toPath());
                }
            }
        });
        tree.getStyleClass().add("project-explorer");
    }

    public void setRootDir(File dir) {
        if (dir == null || !dir.isDirectory()) return;
        this.rootDir = dir;
        TreeItem<File> rootItem = buildTree(rootDir);
        rootItem.setExpanded(true);
        tree.setRoot(rootItem);
    }

    private TreeItem<File> buildTree(File dir) {
        TreeItem<File> item = new TreeItem<>(dir);
        File[] files = dir.listFiles();
        if (files != null) {
            Arrays.sort(files, (a,b) -> {
                if (a.isDirectory() && !b.isDirectory()) return -1;
                if (!a.isDirectory() && b.isDirectory()) return 1;
                return a.getName().compareToIgnoreCase(b.getName());
            });
            for (File f : files) {
                if (f.isDirectory()) {
                    if (f.getName().equals("target") || f.getName().equals(".git") || f.getName().equals(".idea")) continue;
                    item.getChildren().add(buildTree(f));
                } else {
                    item.getChildren().add(new TreeItem<>(f));
                }
            }
        }
        return item;
    }

    public Node getView() { return tree; }
}
