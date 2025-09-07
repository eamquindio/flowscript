package com.flowscript.ide.components;

import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flowscript.ide.syntax.FlowScriptSyntaxHighlighter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeEditorPane extends VBox {
    private static final Logger logger = LoggerFactory.getLogger(CodeEditorPane.class);
    
    private TabPane tabPane;
    private FlowScriptSyntaxHighlighter syntaxHighlighter;
    private Runnable onTextChanged;
    
    public CodeEditorPane() {
        initializeComponents();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        syntaxHighlighter = new FlowScriptSyntaxHighlighter();
        
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        
        getChildren().add(tabPane);
        VBox.setVgrow(tabPane, Priority.ALWAYS);
        
        // Create welcome tab
        createWelcomeTab();
    }
    
    private void createWelcomeTab() {
        Tab welcomeTab = new Tab("Welcome");
        welcomeTab.setClosable(false);
        
        VBox welcomeContent = new VBox();
        welcomeContent.setSpacing(20);
        welcomeContent.setStyle("-fx-padding: 40px; -fx-alignment: center;");
        
        Label titleLabel = new Label("FlowScript IDE");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        Label subtitleLabel = new Label("Integrated Development Environment for FlowScript Language");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
        
        Button newFileBtn = new Button("New FlowScript File");
        newFileBtn.setStyle("-fx-pref-width: 200px; -fx-pref-height: 40px;");
        newFileBtn.setOnAction(e -> newFile());
        
        Button openFileBtn = new Button("Open File");
        openFileBtn.setStyle("-fx-pref-width: 200px; -fx-pref-height: 40px;");
        openFileBtn.setOnAction(e -> openFile());
        
        welcomeContent.getChildren().addAll(titleLabel, subtitleLabel, newFileBtn, openFileBtn);
        welcomeTab.setContent(welcomeContent);
        
        tabPane.getTabs().add(welcomeTab);
    }
    
    private void setupEventHandlers() {
        // Tab selection handling
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != null && newTab.getContent() instanceof CodeArea) {
                CodeArea codeArea = (CodeArea) newTab.getContent();
                codeArea.requestFocus();
            }
        });
    }
    
    public void newFile() {
        createNewTab("Untitled.flow", getFlowScriptTemplate());
    }
    
    public void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open FlowScript File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("FlowScript Files", "*.flow"),
            new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        
        File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());
        if (selectedFile != null) {
            openFile(selectedFile);
        }
    }
    
    public void openFile(File file) {
        try {
            String content = Files.readString(file.toPath());
            createNewTab(file.getName(), content, file.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Failed to open file: " + file.getAbsolutePath(), e);
            showError("File Error", "Could not open file: " + file.getName());
        }
    }
    
    public void saveFile() {
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        if (currentTab != null && currentTab.getContent() instanceof CodeArea) {
            CodeArea codeArea = (CodeArea) currentTab.getContent();
            String filePath = (String) currentTab.getUserData();
            
            if (filePath != null) {
                saveToFile(codeArea.getText(), filePath);
                currentTab.setText(currentTab.getText().replace("*", ""));
            } else {
                saveAsFile();
            }
        }
    }
    
    public void saveAsFile() {
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        if (currentTab != null && currentTab.getContent() instanceof CodeArea) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save FlowScript File");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("FlowScript Files", "*.flow")
            );
            
            File file = fileChooser.showSaveDialog(getScene().getWindow());
            if (file != null) {
                CodeArea codeArea = (CodeArea) currentTab.getContent();
                saveToFile(codeArea.getText(), file.getAbsolutePath());
                currentTab.setText(file.getName());
                currentTab.setUserData(file.getAbsolutePath());
            }
        }
    }
    
    private void saveToFile(String content, String filePath) {
        try {
            Files.writeString(Paths.get(filePath), content);
            logger.info("File saved: " + filePath);
        } catch (IOException e) {
            logger.error("Failed to save file: " + filePath, e);
            showError("Save Error", "Could not save file: " + filePath);
        }
    }
    
    private void createNewTab(String title, String content) {
        createNewTab(title, content, null);
    }
    
    private void createNewTab(String title, String content, String filePath) {
        CodeArea codeArea = new CodeArea();
        
        // Configure code area
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.getStyleClass().add("code-area");
        codeArea.setStyle("-fx-font-family: 'Consolas', 'Monaco', 'Lucida Console', monospace; -fx-font-size: 14px;");
        
        // Set initial content
        codeArea.replaceText(0, 0, content);
        
        // Setup syntax highlighting
        codeArea.multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .subscribe(ignore -> {
                    codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
                    if (onTextChanged != null) {
                        onTextChanged.run();
                    }
                });
        
        // Apply initial highlighting
        codeArea.setStyleSpans(0, computeHighlighting(content));
        
        // Auto-completion and smart features
        setupAutoCompletion(codeArea);
        setupSmartIndentation(codeArea);
        
        // Create tab
        Tab tab = new Tab(title);
        tab.setContent(codeArea);
        tab.setUserData(filePath);
        
        // Handle tab closing
        tab.setOnCloseRequest(e -> {
            if (tab.getText().contains("*")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Unsaved Changes");
                alert.setHeaderText("File has unsaved changes");
                alert.setContentText("Do you want to save changes before closing?");
                alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        saveFile();
                    } else if (response == ButtonType.CANCEL) {
                        e.consume();
                    }
                });
            }
        });
        
        // Track changes for unsaved indicator
        codeArea.textProperty().addListener((obs, oldText, newText) -> {
            if (!tab.getText().endsWith("*")) {
                tab.setText(tab.getText() + "*");
            }
        });
        
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
        
        // Remove welcome tab if it exists and this is not the welcome tab
        if (!title.equals("Welcome") && tabPane.getTabs().size() > 1) {
            Tab welcomeTab = tabPane.getTabs().stream()
                    .filter(t -> "Welcome".equals(t.getText()))
                    .findFirst()
                    .orElse(null);
            if (welcomeTab != null) {
                tabPane.getTabs().remove(welcomeTab);
            }
        }
    }
    
    private StyleSpans<Collection<String>> computeHighlighting(String text) {
        return syntaxHighlighter.computeHighlighting(text);
    }
    
    private void setupAutoCompletion(CodeArea codeArea) {
        codeArea.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.SPACE && e.isControlDown()) {
                showAutoCompletion(codeArea);
                e.consume();
            }
        });
    }
    
    private void setupSmartIndentation(CodeArea codeArea) {
        codeArea.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                int caretPos = codeArea.getCaretPosition();
                String text = codeArea.getText();
                String beforeCaret = text.substring(0, caretPos);
                String currentLine = getCurrentLine(beforeCaret);
                String indent = getIndentation(currentLine);
                
                // Add extra indent if line ends with {
                if (currentLine.trim().endsWith("{")) {
                    indent += "    ";
                }
                
                codeArea.insertText(caretPos, "\n" + indent);
                e.consume();
            }
        });
    }
    
    private void showAutoCompletion(CodeArea codeArea) {
        ContextMenu contextMenu = new ContextMenu();
        
        // FlowScript keywords
        String[] keywords = {
            "proceso", "funcion", "inicio", "fin", "tarea", "gateway", "go_to",
            "cuando", "rama", "unir", "sino", "si", "sino_si", "intentar",
            "capturar", "lanzar", "retornar", "importar", "importar_jar"
        };
        
        for (String keyword : keywords) {
            MenuItem item = new MenuItem(keyword);
            item.setOnAction(e -> {
                int caretPos = codeArea.getCaretPosition();
                codeArea.insertText(caretPos, keyword);
                contextMenu.hide();
            });
            contextMenu.getItems().add(item);
        }
        
        contextMenu.show(codeArea, codeArea.getCaretBounds().get().getMaxX(), 
                        codeArea.getCaretBounds().get().getMaxY());
    }
    
    private String getCurrentLine(String textBeforeCaret) {
        String[] lines = textBeforeCaret.split("\n");
        return lines.length > 0 ? lines[lines.length - 1] : "";
    }
    
    private String getIndentation(String line) {
        int indent = 0;
        for (char c : line.toCharArray()) {
            if (c == ' ') indent++;
            else if (c == '\t') indent += 4;
            else break;
        }
        return " ".repeat(indent);
    }
    
    private String getFlowScriptTemplate() {
        return """
            # FlowScript Template
            
            proceso EjemploProceso {
                inicio -> PrimeraTarea
                
                tarea PrimeraTarea {
                    accion:
                        imprimir("¡Hola FlowScript!")
                        go_to Fin
                }
                
                fin Fin
            }
            """;
    }
    
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void setOnTextChanged(Runnable onTextChanged) {
        this.onTextChanged = onTextChanged;
    }
    
    public String getCurrentText() {
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        if (currentTab != null && currentTab.getContent() instanceof CodeArea) {
            return ((CodeArea) currentTab.getContent()).getText();
        }
        return "";
    }
    
    public void insertText(String text) {
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        if (currentTab != null && currentTab.getContent() instanceof CodeArea) {
            CodeArea codeArea = (CodeArea) currentTab.getContent();
            codeArea.insertText(codeArea.getCaretPosition(), text);
        }
    }
}