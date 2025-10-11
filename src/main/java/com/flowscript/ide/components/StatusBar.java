package com.flowscript.ide.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Placeholder for a status bar.
 */
public class StatusBar extends HBox {
    private final Label status = new Label("Listo");

    public StatusBar() {
        setPadding(new Insets(4, 8, 4, 8));
        getChildren().add(status);
    }

    public void setMessage(String message) {
        status.setText(message);
    }
}

