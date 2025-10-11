package com.flowscript.ide.components;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

/**
 * Placeholder for an interactive console pane.
 */
public class ConsolePane extends BorderPane {
    private final TextArea console = new TextArea();

    public ConsolePane() {
        console.setEditable(false);
        console.setPromptText("Consola");
        setCenter(console);
        setPrefHeight(160);
    }
}

