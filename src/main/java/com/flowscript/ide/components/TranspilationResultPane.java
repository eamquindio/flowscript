package com.flowscript.ide.components;

import com.flowscript.FlowScriptTranspiler;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Panel para mostrar resultados detallados de la transpilación.
 */
public class TranspilationResultPane extends VBox {

    private final TextArea generatedCodeArea;
    private final TextArea messagesArea;
    private final Label statusLabel;
    private final ProgressBar progressBar;
    private final TabPane tabPane;

    public TranspilationResultPane() {
        setPadding(new Insets(10));
        setSpacing(10);

        // Status section
        HBox statusSection = new HBox(10);
        statusSection.setPadding(new Insets(5));
        statusSection.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5;");

        statusLabel = new Label("Listo");
        statusLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(200);
        progressBar.setVisible(false);

        statusSection.getChildren().addAll(statusLabel, progressBar);

        // Tab pane for results
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Messages tab
        Tab messagesTab = new Tab("Mensajes");
        messagesArea = new TextArea();
        messagesArea.setEditable(false);
        messagesArea.setFont(Font.font("Monospaced", 12));
        messagesArea.setStyle("-fx-control-inner-background: #1e1e1e; -fx-text-fill: #d4d4d4;");
        messagesTab.setContent(messagesArea);

        // Generated code tab
        Tab codeTab = new Tab("Código Java Generado");
        generatedCodeArea = new TextArea();
        generatedCodeArea.setEditable(false);
        generatedCodeArea.setFont(Font.font("Monospaced", 12));
        generatedCodeArea.setStyle("-fx-control-inner-background: #1e1e1e; -fx-text-fill: #d4d4d4;");
        codeTab.setContent(generatedCodeArea);

        tabPane.getTabs().addAll(messagesTab, codeTab);

        VBox.setVgrow(tabPane, Priority.ALWAYS);

        getChildren().addAll(statusSection, tabPane);
    }

    /**
     * Muestra los resultados de la transpilación.
     */
    public void displayResult(FlowScriptTranspiler.TranspilationResult result) {
        // Update status
        if (result.success) {
            statusLabel.setText("✓ Transpilación exitosa - " + result.phase.getDisplayName());
            statusLabel.setTextFill(Color.GREEN);
        } else {
            statusLabel.setText("✗ Error en " + result.phase.getDisplayName());
            statusLabel.setTextFill(Color.RED);
        }

        // Update messages
        StringBuilder messages = new StringBuilder();
        messages.append("=".repeat(60)).append("\n");
        messages.append("  RESULTADO DE TRANSPILACIÓN\n");
        messages.append("=".repeat(60)).append("\n\n");

        messages.append("Estado: ").append(result.success ? "✓ ÉXITO" : "✗ ERROR").append("\n");
        messages.append("Fase: ").append(result.phase.getDisplayName()).append("\n");
        messages.append("Tiempo total: ").append(result.totalTime).append("ms\n\n");

        // Phase timings
        messages.append("Tiempos por fase:\n");
        messages.append("  Léxico: ").append(result.lexerTime).append("ms\n");
        messages.append("  Sintáctico: ").append(result.parserTime).append("ms\n");
        if (result.semanticTime > 0) {
            messages.append("  Semántico: ").append(result.semanticTime).append("ms\n");
        }
        if (result.codeGenTime > 0) {
            messages.append("  Generación: ").append(result.codeGenTime).append("ms\n");
        }
        if (result.compilationTime > 0) {
            messages.append("  Compilación: ").append(result.compilationTime).append("ms\n");
        }
        if (result.executionTime > 0) {
            messages.append("  Ejecución: ").append(result.executionTime).append("ms\n");
        }

        messages.append("\n").append("-".repeat(60)).append("\n");
        messages.append("MENSAJES:\n");
        messages.append("-".repeat(60)).append("\n");
        for (String message : result.getMessages()) {
            messages.append(message).append("\n");
        }

        messagesArea.setText(messages.toString());

        // Update generated code
        if (result.generatedCode != null) {
            generatedCodeArea.setText(result.generatedCode);
        } else {
            generatedCodeArea.setText("// No se generó código Java");
        }
    }

    /**
     * Muestra un error de transpilación.
     */
    public void displayError(String message) {
        statusLabel.setText("✗ Error");
        statusLabel.setTextFill(Color.RED);

        StringBuilder errorMsg = new StringBuilder();
        errorMsg.append("=".repeat(60)).append("\n");
        errorMsg.append("  ERROR\n");
        errorMsg.append("=".repeat(60)).append("\n\n");
        errorMsg.append(message);

        messagesArea.setText(errorMsg.toString());
        generatedCodeArea.setText("// Error: No se generó código");
    }

    /**
     * Limpia el panel.
     */
    public void clear() {
        statusLabel.setText("Listo");
        statusLabel.setTextFill(Color.BLACK);
        messagesArea.clear();
        generatedCodeArea.clear();
    }

    /**
     * Muestra/oculta la barra de progreso.
     */
    public void setProgress(boolean visible) {
        progressBar.setVisible(visible);
    }

    /**
     * Actualiza el progreso.
     */
    public void updateProgress(double progress) {
        progressBar.setProgress(progress);
    }
}
