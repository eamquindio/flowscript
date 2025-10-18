package com.flowscript.ide.components;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatusBar extends HBox {
    private Label messageLabel;
    private Label fileInfoLabel;
    private Label cursorPositionLabel;
    private Label encodingLabel;
    private Label languageLabel;
    private Label timeLabel;
    private ProgressIndicator progressIndicator;
    private boolean isModified = false;
    
    public StatusBar() {
        initializeComponents();
        setupLayout();
        startTimeUpdater();
    }
    
    private void initializeComponents() {
        // Message area (left)
        messageLabel = new Label("Ready");
        messageLabel.setStyle("-fx-font-size: 11px;");
        
        // Progress indicator (initially hidden)
        progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxSize(16, 16);
        progressIndicator.setVisible(false);
        
        // File info
        fileInfoLabel = new Label("No file");
        fileInfoLabel.setStyle("-fx-font-size: 11px;");
        
        // Cursor position
        cursorPositionLabel = new Label("Ln 1, Col 1");
        cursorPositionLabel.setStyle("-fx-font-size: 11px;");
        
        // File encoding
        encodingLabel = new Label("UTF-8");
        encodingLabel.setStyle("-fx-font-size: 11px;");
        
        // Language mode
        languageLabel = new Label("FlowScript");
        languageLabel.setStyle("-fx-font-size: 11px;");
        
        // Current time
        timeLabel = new Label();
        timeLabel.setStyle("-fx-font-size: 11px;");
        updateTime();
    }
    
    private void setupLayout() {
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(5);
        setPadding(new Insets(2, 8, 2, 8));
        setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #d0d0d0; -fx-border-width: 1 0 0 0;");
        
        // Left side - message and progress
        HBox leftSide = new HBox(5);
        leftSide.setAlignment(Pos.CENTER_LEFT);
        leftSide.getChildren().addAll(messageLabel, progressIndicator);
        
        // Spacer to push right elements to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // Right side - file info, cursor, encoding, language, time
        HBox rightSide = new HBox(10);
        rightSide.setAlignment(Pos.CENTER_RIGHT);
        rightSide.getChildren().addAll(
            fileInfoLabel,
            new Separator(),
            cursorPositionLabel,
            new Separator(),
            encodingLabel,
            new Separator(),
            languageLabel,
            new Separator(),
            timeLabel
        );
        
        getChildren().addAll(leftSide, spacer, rightSide);
    }
    
    private void startTimeUpdater() {
        Thread timeThread = new Thread(() -> {
            while (true) {
                Platform.runLater(this::updateTime);
                try {
                    Thread.sleep(1000); // Update every second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        timeThread.setDaemon(true);
        timeThread.start();
    }
    
    private void updateTime() {
        String currentTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        timeLabel.setText(currentTime);
    }
    
    // Public API methods
    public void setMessage(String message) {
        Platform.runLater(() -> messageLabel.setText(message));
    }
    
    public void setFileInfo(String fileName) {
        Platform.runLater(() -> {
            String displayName = fileName;
            if (isModified) {
                displayName += " •";
            }
            fileInfoLabel.setText(displayName);
        });
    }
    
    public void setModified(boolean modified) {
        this.isModified = modified;
        // Update file info to show modified indicator
        String currentText = fileInfoLabel.getText();
        if (modified && !currentText.endsWith(" •")) {
            fileInfoLabel.setText(currentText + " •");
        } else if (!modified && currentText.endsWith(" •")) {
            fileInfoLabel.setText(currentText.substring(0, currentText.length() - 2));
        }
    }
    
    public void setCursorPosition(int line, int column) {
        Platform.runLater(() -> 
            cursorPositionLabel.setText(String.format("Ln %d, Col %d", line, column))
        );
    }
    
    public void setEncoding(String encoding) {
        Platform.runLater(() -> encodingLabel.setText(encoding));
    }
    
    public void setLanguage(String language) {
        Platform.runLater(() -> languageLabel.setText(language));
    }
    
    public void showProgress() {
        Platform.runLater(() -> progressIndicator.setVisible(true));
    }
    
    public void hideProgress() {
        Platform.runLater(() -> progressIndicator.setVisible(false));
    }
    
    public void setProgress(double progress) {
        Platform.runLater(() -> {
            progressIndicator.setProgress(progress);
            progressIndicator.setVisible(true);
        });
    }
    
    // Convenience methods for common operations
    public void showBusyMessage(String message) {
        setMessage(message);
        showProgress();
    }
    
    public void clearBusy() {
        hideProgress();
        setMessage("Ready");
    }
    
    public void showSuccess(String message) {
        setMessage("✓ " + message);
        
        // Clear success message after 3 seconds
        Thread clearThread = new Thread(() -> {
            try {
                Thread.sleep(3000);
                Platform.runLater(() -> setMessage("Ready"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        clearThread.setDaemon(true);
        clearThread.start();
    }
    
    public void showError(String message) {
        setMessage("✗ " + message);
        
        // Clear error message after 5 seconds
        Thread clearThread = new Thread(() -> {
            try {
                Thread.sleep(5000);
                Platform.runLater(() -> setMessage("Ready"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        clearThread.setDaemon(true);
        clearThread.start();
    }
    
    public void showWarning(String message) {
        setMessage("⚠ " + message);
        
        // Clear warning message after 4 seconds
        Thread clearThread = new Thread(() -> {
            try {
                Thread.sleep(4000);
                Platform.runLater(() -> setMessage("Ready"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        clearThread.setDaemon(true);
        clearThread.start();
    }
}