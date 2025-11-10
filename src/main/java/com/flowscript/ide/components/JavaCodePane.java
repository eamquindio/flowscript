package com.flowscript.ide.components;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

/**
 * Panel para mostrar el código Java generado por el transpilador.
 */
public class JavaCodePane extends VBox {

    private final CodeArea codeArea;
    private final Label statusLabel;

    public JavaCodePane() {
        setPadding(new Insets(10));
        setSpacing(10);

        // Status label
        statusLabel = new Label("Esperando código transpilado...");
        statusLabel.setStyle("-fx-font-weight: bold; -fx-padding: 5;");

        // Code area with line numbers
        codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.setEditable(false);
        codeArea.setStyle(
            "-fx-font-family: 'Consolas', 'Monaco', 'Lucida Console', monospace; " +
            "-fx-font-size: 13px; " +
            "-fx-background-color: #1e1e1e; " +
            "-fx-text-fill: #d4d4d4;"
        );

        // Wrap code area in scroll pane
        VBox.setVgrow(codeArea, Priority.ALWAYS);

        // Toolbar with copy button
        ToolBar toolbar = new ToolBar();
        Button copyButton = new Button("Copiar Código");
        copyButton.setOnAction(e -> copyToClipboard());

        Button clearButton = new Button("Limpiar");
        clearButton.setOnAction(e -> clear());

        toolbar.getItems().addAll(copyButton, clearButton);

        getChildren().addAll(statusLabel, toolbar, codeArea);
    }

    /**
     * Muestra el código Java generado.
     */
    public void displayCode(String javaCode) {
        if (javaCode != null && !javaCode.isEmpty()) {
            codeArea.replaceText(javaCode);
            statusLabel.setText("✓ Código Java generado (" + countLines(javaCode) + " líneas)");
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-padding: 5;");
        } else {
            codeArea.replaceText("// No se generó código Java");
            statusLabel.setText("⚠ Sin código generado");
            statusLabel.setStyle("-fx-text-fill: orange; -fx-font-weight: bold; -fx-padding: 5;");
        }
    }

    /**
     * Muestra un mensaje de error.
     */
    public void displayError(String message) {
        codeArea.replaceText("// Error al generar código\n// " + message);
        statusLabel.setText("✗ Error en generación de código");
        statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-padding: 5;");
    }

    /**
     * Limpia el panel.
     */
    public void clear() {
        codeArea.clear();
        statusLabel.setText("Esperando código transpilado...");
        statusLabel.setStyle("-fx-font-weight: bold; -fx-padding: 5;");
    }

    /**
     * Copia el código al portapapeles.
     */
    private void copyToClipboard() {
        String code = codeArea.getText();
        if (code != null && !code.isEmpty()) {
            javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
            javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
            content.putString(code);
            clipboard.setContent(content);

            statusLabel.setText("✓ Código copiado al portapapeles");
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-padding: 5;");
        }
    }

    /**
     * Cuenta las líneas de código.
     */
    private int countLines(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        return text.split("\n").length;
    }
}
