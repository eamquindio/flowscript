package com.flowscript.ide.components;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConsolePane {
    private final TextArea console = new TextArea();
    private final VBox container = new VBox();
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ConsolePane() {
        console.setEditable(false);
        console.getStyleClass().add("console-pane");
        console.setPrefRowCount(6);
        container.getChildren().add(console);
        VBox.setVgrow(console, Priority.NEVER);
    }

    public Node getView() { return container; }

    public void info(String msg) { append("INFO", msg); }
    public void warn(String msg) { append("WARN", msg); }
    public void error(String msg) { append("ERROR", msg); }

    private void append(String level, String msg) {
        String ts = LocalTime.now().format(fmt);
        console.appendText("[" + ts + "] " + level + ": " + msg + System.lineSeparator());
    }
}

