package com.flowscript.ide.components;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.fxmisc.richtext.StyleClassedTextArea;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class ConsolePane extends VBox {
    private StyleClassedTextArea outputArea;
    private TextField inputField;
    private Consumer<String> onCommand;
    private StringBuilder inputHistory;
    private int historyIndex = -1;
    
    public ConsolePane() {
        initializeComponents();
        setupEventHandlers();
        setupStyling();
    }
    
    private void initializeComponents() {
        inputHistory = new StringBuilder();
        
        // Title bar
        Label titleLabel = new Label("Console");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-padding: 5px;");
        
        // Toolbar
        ToolBar toolBar = new ToolBar();
        
        Button clearBtn = new Button("Clear");
        clearBtn.setTooltip(new Tooltip("Clear Console"));
        clearBtn.setOnAction(e -> clear());
        
        Button saveOutputBtn = new Button("Save");
        saveOutputBtn.setTooltip(new Tooltip("Save Output"));
        saveOutputBtn.setOnAction(e -> saveOutput());
        
        Separator separator = new Separator();
        
        Label promptLabel = new Label("FlowScript Console - Ready");
        promptLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #666;");
        
        toolBar.getItems().addAll(clearBtn, saveOutputBtn, separator, promptLabel);
        
        // Output area
        outputArea = new StyleClassedTextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setStyle("-fx-font-family: 'Consolas', 'Monaco', monospace; -fx-font-size: 12px;");
        
        // Input field
        inputField = new TextField();
        inputField.setPromptText("Enter FlowScript commands...");
        inputField.setStyle("-fx-font-family: 'Consolas', 'Monaco', monospace;");
        
        getChildren().addAll(titleLabel, toolBar, outputArea, inputField);
        
        // Set growth priorities
        VBox.setVgrow(outputArea, Priority.ALWAYS);
        
        // Welcome message
        printWelcomeMessage();
    }
    
    private void setupEventHandlers() {
        // Handle Enter key in input field
        inputField.setOnKeyPressed(this::handleKeyPress);
        
        // Auto-scroll to bottom when new text is added
        outputArea.textProperty().addListener((obs, oldText, newText) -> {
            Platform.runLater(() -> {
                outputArea.moveTo(outputArea.getLength());
                outputArea.requestFollowCaret();
            });
        });
    }
    
    private void setupStyling() {
        outputArea.getStylesheets().add(getClass().getResource("/css/console.css").toExternalForm());
    }
    
    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER -> executeCommand();
            case UP -> navigateHistory(-1);
            case DOWN -> navigateHistory(1);
            case TAB -> autoComplete();
        }
    }
    
    private void executeCommand() {
        String command = inputField.getText().trim();
        if (!command.isEmpty()) {
            // Add to history
            addToHistory(command);
            
            // Display command in output
            printCommand(command);
            
            // Execute command
            if (onCommand != null) {
                onCommand.accept(command);
            } else {
                processBuiltInCommand(command);
            }
            
            // Clear input field
            inputField.clear();
            historyIndex = -1;
        }
    }
    
    private void processBuiltInCommand(String command) {
        String[] parts = command.split("\\s+");
        String cmd = parts[0].toLowerCase();
        
        switch (cmd) {
            case "clear" -> clear();
            case "help" -> showHelp();
            case "version" -> showVersion();
            case "time" -> showTime();
            case "echo" -> {
                if (parts.length > 1) {
                    String message = String.join(" ", java.util.Arrays.copyOfRange(parts, 1, parts.length));
                    println(message, "output");
                } else {
                    println("Usage: echo <message>", "warning");
                }
            }
            case "history" -> showHistory();
            default -> println("Unknown command: " + cmd + " (type 'help' for available commands)", "error");
        }
    }
    
    private void addToHistory(String command) {
        inputHistory.append(command).append("\n");
    }
    
    private void navigateHistory(int direction) {
        String[] commands = inputHistory.toString().split("\n");
        if (commands.length == 0) return;
        
        if (direction == -1) { // UP
            if (historyIndex == -1) historyIndex = commands.length - 1;
            else if (historyIndex > 0) historyIndex--;
        } else { // DOWN
            if (historyIndex == -1) return;
            historyIndex++;
            if (historyIndex >= commands.length) {
                historyIndex = -1;
                inputField.clear();
                return;
            }
        }
        
        if (historyIndex >= 0 && historyIndex < commands.length) {
            inputField.setText(commands[historyIndex]);
            inputField.positionCaret(inputField.getText().length());
        }
    }
    
    private void autoComplete() {
        String currentText = inputField.getText();
        String[] suggestions = {
            "clear", "help", "version", "time", "echo", "history",
            "run", "debug", "validate", "compile", "execute"
        };
        
        for (String suggestion : suggestions) {
            if (suggestion.startsWith(currentText.toLowerCase())) {
                inputField.setText(suggestion);
                inputField.positionCaret(suggestion.length());
                break;
            }
        }
    }
    
    private void printWelcomeMessage() {
        println("FlowScript IDE Console", "header");
        println("=====================", "header");
        println("Welcome to the FlowScript development environment.", "info");
        println("Type 'help' for available commands.", "info");
        println("");
    }
    
    private void printCommand(String command) {
        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        println("[" + timestamp + "] > " + command, "command");
    }
    
    public void println(String text) {
        println(text, "output");
    }
    
    public void println(String text, String styleClass) {
        Platform.runLater(() -> {
            int startPos = outputArea.getLength();
            outputArea.appendText(text + "\n");
            outputArea.setStyleClass(startPos, startPos + text.length(), styleClass);
        });
    }
    
    public void print(String text) {
        print(text, "output");
    }
    
    public void print(String text, String styleClass) {
        Platform.runLater(() -> {
            int startPos = outputArea.getLength();
            outputArea.appendText(text);
            outputArea.setStyleClass(startPos, startPos + text.length(), styleClass);
        });
    }
    
    public void printError(String text) {
        println("ERROR: " + text, "error");
    }
    
    public void printWarning(String text) {
        println("WARNING: " + text, "warning");
    }
    
    public void printSuccess(String text) {
        println("SUCCESS: " + text, "success");
    }
    
    public void clear() {
        outputArea.clear();
        printWelcomeMessage();
    }
    
    private void showHelp() {
        println("Available Commands:", "header");
        println("==================", "header");
        println("clear          - Clear the console", "info");
        println("help           - Show this help message", "info");
        println("version        - Show version information", "info");
        println("time           - Show current time", "info");
        println("echo <text>    - Echo text to console", "info");
        println("history        - Show command history", "info");
        println("");
        println("FlowScript Commands:", "header");
        println("===================", "header");
        println("run <file>     - Run a FlowScript file", "info");
        println("debug <file>   - Debug a FlowScript file", "info");
        println("validate <file>- Validate FlowScript syntax", "info");
        println("compile <file> - Compile FlowScript to bytecode", "info");
        println("");
    }
    
    private void showVersion() {
        println("FlowScript IDE Console v1.0.0", "info");
        println("Built with JavaFX and RichTextFX", "info");
        println("Copyright (c) 2024 FlowScript Team", "info");
    }
    
    private void showTime() {
        String currentTime = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        println("Current time: " + currentTime, "info");
    }
    
    private void showHistory() {
        String[] commands = inputHistory.toString().split("\n");
        if (commands.length == 0 || (commands.length == 1 && commands[0].isEmpty())) {
            println("No command history available.", "info");
            return;
        }
        
        println("Command History:", "header");
        println("================", "header");
        for (int i = 0; i < commands.length; i++) {
            if (!commands[i].isEmpty()) {
                println((i + 1) + ". " + commands[i], "info");
            }
        }
        println("");
    }
    
    private void saveOutput() {
        // Implementation for saving console output to file
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Save Console Output");
        fileChooser.getExtensionFilters().add(
                new javafx.stage.FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        
        java.io.File file = fileChooser.showSaveDialog(getScene().getWindow());
        if (file != null) {
            try {
                java.nio.file.Files.writeString(file.toPath(), outputArea.getText());
                printSuccess("Console output saved to: " + file.getName());
            } catch (Exception e) {
                printError("Failed to save console output: " + e.getMessage());
            }
        }
    }
    
    // Public API methods
    public void setOnCommand(Consumer<String> onCommand) {
        this.onCommand = onCommand;
    }
    
    public void focusInput() {
        Platform.runLater(() -> inputField.requestFocus());
    }
    
    public String getOutputText() {
        return outputArea.getText();
    }
    
    public void executeCommand(String command) {
        inputField.setText(command);
        executeCommand();
    }
    
    // Convenience methods for different message types
    public void logInfo(String message) {
        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        println("[" + timestamp + "] INFO: " + message, "info");
    }
    
    public void logError(String message) {
        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        println("[" + timestamp + "] ERROR: " + message, "error");
    }
    
    public void logWarning(String message) {
        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        println("[" + timestamp + "] WARNING: " + message, "warning");
    }
    
    public void logSuccess(String message) {
        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        println("[" + timestamp + "] SUCCESS: " + message, "success");
    }
}