package com.flowscript.ide.components;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.lexer.TokenType;

import java.util.List;

/**
 * Component that displays tokens in a table format.
 */
public class TokenTablePane extends BorderPane {
    private TableView<TokenRow> tokenTable;
    private ObservableList<TokenRow> tokenData;
    private Label statusLabel;
    private CheckBox autoUpdateCheckBox;
    private CheckBox includeCommentsCheckBox;
    private Button refreshButton;
    private boolean autoUpdate = true;

    public TokenTablePane() {
        initializeUI();
        loadStyles();
    }

    private void initializeUI() {
        // Header
        Label titleLabel = new Label("Token Analysis");
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Controls
        autoUpdateCheckBox = new CheckBox("Auto Update");
        autoUpdateCheckBox.setSelected(true);
        autoUpdateCheckBox.setOnAction(e -> autoUpdate = autoUpdateCheckBox.isSelected());
        autoUpdateCheckBox.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        includeCommentsCheckBox = new CheckBox("Include Comments");
        includeCommentsCheckBox.setSelected(false);
        includeCommentsCheckBox.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        refreshButton = new Button("Refresh");
        refreshButton.setStyle("-fx-font-size: 11px; -fx-text-fill: white; -fx-font-weight: bold; " +
            "-fx-background-color: #4CAF50; -fx-border-radius: 3px; -fx-background-radius: 3px;");

        HBox controls = new HBox(10);
        controls.getChildren().addAll(autoUpdateCheckBox, includeCommentsCheckBox, refreshButton);
        controls.setPadding(new Insets(5));

        VBox header = new VBox(5);
        header.getChildren().addAll(titleLabel, controls);
        header.setPadding(new Insets(10));

        // Create table
        tokenTable = new TableView<>();
        tokenData = FXCollections.observableArrayList();
        tokenTable.setItems(tokenData);

        // Define columns
        TableColumn<TokenRow, Integer> lineCol = new TableColumn<>("Line");
        lineCol.setCellValueFactory(cellData ->
            new SimpleIntegerProperty(cellData.getValue().line).asObject());
        lineCol.setPrefWidth(50);
        lineCol.setStyle("-fx-alignment: CENTER; -fx-text-fill: white; -fx-font-weight: bold;");

        TableColumn<TokenRow, Integer> columnCol = new TableColumn<>("Col");
        columnCol.setCellValueFactory(cellData ->
            new SimpleIntegerProperty(cellData.getValue().column).asObject());
        columnCol.setPrefWidth(40);
        columnCol.setStyle("-fx-alignment: CENTER; -fx-text-fill: white; -fx-font-weight: bold;");

        TableColumn<TokenRow, String> typeCol = new TableColumn<>("Token Type");
        typeCol.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().type));
        typeCol.setPrefWidth(150);
        typeCol.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        TableColumn<TokenRow, String> valueCol = new TableColumn<>("Value");
        valueCol.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().value));
        valueCol.setPrefWidth(200);
        valueCol.setStyle("-fx-text-fill: white;");

        TableColumn<TokenRow, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(cellData ->
            new SimpleStringProperty(cellData.getValue().category));
        categoryCol.setPrefWidth(100);
        categoryCol.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        tokenTable.getColumns().addAll(lineCol, columnCol, typeCol, valueCol, categoryCol);

        // Make table resizable
        tokenTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Style the table
        tokenTable.setRowFactory(tv -> {
            TableRow<TokenRow> row = new TableRow<>() {
                @Override
                protected void updateItem(TokenRow item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        // Apply color coding based on category with better contrast
                        switch (item.category) {
                            case "Keyword":
                                setStyle("-fx-background-color: rgba(138, 43, 226, 0.3); -fx-text-fill: white; -fx-font-weight: bold;");
                                break;
                            case "Operator":
                                setStyle("-fx-background-color: rgba(255, 140, 0, 0.3); -fx-text-fill: white; -fx-font-weight: bold;");
                                break;
                            case "Literal":
                                setStyle("-fx-background-color: rgba(0, 150, 0, 0.3); -fx-text-fill: white; -fx-font-weight: bold;");
                                break;
                            case "Identifier":
                                setStyle("-fx-background-color: rgba(100, 149, 237, 0.3); -fx-text-fill: white;");
                                break;
                            case "Comment":
                                setStyle("-fx-background-color: rgba(128, 128, 128, 0.3); -fx-text-fill: #E0E0E0; -fx-font-style: italic;");
                                break;
                            case "Delimiter":
                                setStyle("-fx-background-color: rgba(255, 69, 0, 0.2); -fx-text-fill: white;");
                                break;
                            case "Type":
                                setStyle("-fx-background-color: rgba(255, 20, 147, 0.3); -fx-text-fill: white; -fx-font-weight: bold;");
                                break;
                            default:
                                setStyle("-fx-text-fill: white;");
                        }
                    } else {
                        setStyle("");
                    }
                }
            };
            return row;
        });

        // Status label
        statusLabel = new Label("Ready");
        statusLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: white; -fx-font-weight: bold;");
        statusLabel.setPadding(new Insets(5));

        // Style the table headers
        tokenTable.setStyle("-fx-table-header-background-color: #2D2D30; " +
            "-fx-table-header-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-background-color: #1E1E1E;");

        // Layout
        setTop(header);
        setCenter(tokenTable);
        setBottom(statusLabel);

        // Set minimum width
        setMinWidth(300);
        setPrefWidth(500);
    }

    /**
     * Updates the token table with the provided code.
     */
    public void updateTokens(String code) {
        if (!autoUpdate && !refreshButton.isArmed()) {
            return;
        }

        tokenData.clear();

        if (code == null || code.trim().isEmpty()) {
            statusLabel.setText("No code to analyze");
            return;
        }

        try {
            Lexer lexer = new Lexer(code, includeCommentsCheckBox.isSelected());
            List<Token> tokens = lexer.tokenize();

            int tokenCount = 0;
            for (Token token : tokens) {
                if (token.getType() != TokenType.EOF) {
                    TokenRow row = new TokenRow(
                        token.getLine(),
                        token.getColumn(),
                        token.getType().toString(),
                        formatValue(token.getValue()),
                        getCategoryForToken(token.getType())
                    );
                    tokenData.add(row);
                    tokenCount++;
                }
            }

            statusLabel.setText(String.format("Tokens: %d | Lines: %d",
                tokenCount,
                tokens.isEmpty() ? 0 : tokens.get(tokens.size() - 1).getLine()));
            statusLabel.setStyle("-fx-text-fill: #4CAF50; -fx-font-size: 11px; -fx-font-weight: bold;");

        } catch (Lexer.LexicalException e) {
            statusLabel.setText("Error: " + e.getMessage());
            statusLabel.setStyle("-fx-text-fill: #FF6B6B; -fx-font-size: 11px; -fx-font-weight: bold;");

            // Show partial tokens up to the error point
            String partialCode = code.substring(0, Math.min(code.length(),
                getErrorPosition(e.getMessage())));
            if (!partialCode.isEmpty()) {
                try {
                    Lexer partialLexer = new Lexer(partialCode, includeCommentsCheckBox.isSelected());
                    List<Token> partialTokens = partialLexer.tokenize();
                    for (Token token : partialTokens) {
                        if (token.getType() != TokenType.EOF) {
                            TokenRow row = new TokenRow(
                                token.getLine(),
                                token.getColumn(),
                                token.getType().toString(),
                                formatValue(token.getValue()),
                                getCategoryForToken(token.getType())
                            );
                            tokenData.add(row);
                        }
                    }
                } catch (Exception ignored) {
                    // Ignore errors in partial lexing
                }
            }
        } catch (Exception e) {
            statusLabel.setText("Unexpected error: " + e.getMessage());
            statusLabel.setStyle("-fx-text-fill: #FF6B6B; -fx-font-size: 11px; -fx-font-weight: bold;");
        }
    }

    private String formatValue(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }

        // Truncate long values
        if (value.length() > 50) {
            value = value.substring(0, 47) + "...";
        }

        // Escape special characters for display
        value = value.replace("\n", "\\n")
                     .replace("\t", "\\t")
                     .replace("\r", "\\r");

        return value;
    }

    private String getCategoryForToken(TokenType type) {
        if (type.isKeyword()) {
            return "Keyword";
        } else if (type.isOperator()) {
            return "Operator";
        } else if (type.isDelimiter()) {
            return "Delimiter";
        } else if (type.isLiteral()) {
            return "Literal";
        } else if (type.isDataType()) {
            return "Type";
        } else if (type == TokenType.IDENTIFIER) {
            return "Identifier";
        } else if (type == TokenType.SINGLE_LINE_COMMENT ||
                   type == TokenType.MULTI_LINE_COMMENT) {
            return "Comment";
        } else {
            return "Other";
        }
    }

    private int getErrorPosition(String errorMessage) {
        // Try to extract position from error message
        // Format: "... at line X, column Y"
        try {
            int columnIndex = errorMessage.lastIndexOf("column ");
            if (columnIndex != -1) {
                String afterColumn = errorMessage.substring(columnIndex + 7);
                String number = afterColumn.split("\\D")[0];
                return Integer.parseInt(number);
            }
        } catch (Exception e) {
            // Fallback to a reasonable default
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Set the refresh button action handler.
     */
    public void setOnRefresh(Runnable action) {
        refreshButton.setOnAction(e -> action.run());
    }

    /**
     * Get whether auto-update is enabled.
     */
    public boolean isAutoUpdate() {
        return autoUpdate;
    }

    /**
     * Load CSS styles for the token table.
     */
    private void loadStyles() {
        try {
            String cssFile = getClass().getResource("/styles/token-table.css").toExternalForm();
            getStylesheets().add(cssFile);

            // Apply CSS classes
            getStyleClass().add("token-table-panel");
            tokenTable.getStyleClass().add("token-table");

        } catch (Exception e) {
            // CSS file not found, use inline styles
            System.err.println("Token table CSS file not found, using inline styles");
        }
    }

    /**
     * Inner class representing a row in the token table.
     */
    public static class TokenRow {
        public final int line;
        public final int column;
        public final String type;
        public final String value;
        public final String category;

        public TokenRow(int line, int column, String type, String value, String category) {
            this.line = line;
            this.column = column;
            this.type = type;
            this.value = value;
            this.category = category;
        }
    }
}