package com.flowscript.ide.components;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class StatusBar {
    private final HBox container = new HBox(12);
    private final Label caret = new Label("Ln 1, Col 1");
    private final Label file = new Label("Untitled");
    private final Label zoom = new Label("100% ");

    public StatusBar(CodeEditorPane editorPane) {
        container.getStyleClass().add("status-bar");
        container.setPadding(new Insets(4, 8, 4, 8));
        container.getChildren().addAll(file, caret, zoom);

        editorPane.onActiveCaretChange((line, column) -> caret.setText("Ln " + line + ", Col " + column));
        editorPane.onActiveFileChange(name -> file.setText(name == null ? "Untitled" : name));
        editorPane.onZoomChange(z -> zoom.setText((int)(z * 100) + "% "));
    }

    public Node getView() { return container; }
}

