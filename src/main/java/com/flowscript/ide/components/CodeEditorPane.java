package com.flowscript.ide.components;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

/**
 * Placeholder for a code editor component.
 */
public class CodeEditorPane extends BorderPane {
    private final TextArea editor = new TextArea();

    public CodeEditorPane() {
        setPrefSize(800, 600);
        editor.setText(
            "# Bienvenido a FlowScript\n" +
            "process Sample {\n" +
            "    start -> Task1\n\n" +
            "    task Task1 {\n" +
            "        action:\n" +
            "            message = \"Hola FlowScript!\"\n" +
            "            go_to End1\n" +
            "    }\n\n" +
            "    end End1\n" +
            "}\n");
        setCenter(editor);
    }
}
