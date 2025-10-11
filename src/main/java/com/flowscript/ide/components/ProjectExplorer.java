package com.flowscript.ide.components;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

/**
 * Placeholder for a project explorer tree.
 */
public class ProjectExplorer extends BorderPane {
    public ProjectExplorer() {
        TreeItem<String> root = new TreeItem<>("Proyecto");
        TreeView<String> tree = new TreeView<>(root);
        setCenter(tree);
        setPrefWidth(240);
    }
}

