package com.flowscript.ide.components;

import com.flowscript.ide.syntax.FlowScriptSyntaxHighlighter;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class CodeEditorPane {
    private final TabPane tabs = new TabPane();
    private final BorderPane container = new BorderPane();
    private final ConsolePane console;
    private double zoom = 1.0;

    // Callbacks for StatusBar
    private Consumer<String> fileChangeListener = s -> {};
    private BiCaretListener caretListener = (l, c) -> {};
    private Consumer<Double> zoomListener = z -> {};

    private final Map<Tab, EditorFile> opened = new HashMap<>();

    public CodeEditorPane(ConsolePane console) {
        this.console = console;
        container.setCenter(tabs);
        container.setPadding(new Insets(4));

        tabs.getTabs().addListener((javafx.collections.ListChangeListener<Tab>) c -> updateFileStatus());
        tabs.getSelectionModel().selectedItemProperty().addListener((obs, old, val) -> updateFileStatus());
        newFile();
    }

    public Node getView() { return container; }

    public void newFile() {
        CodeArea area = new CodeArea();
        area.setWrapText(true);
        if (!area.getStyleClass().contains("code-area")) area.getStyleClass().add("code-area");
        new FlowScriptSyntaxHighlighter(area);
        area.caretPositionProperty().addListener((obs, o, n) -> notifyCaret(area));
        Tab tab = new Tab("Untitled");
        tab.setContent(wrap(area));
        tab.setOnClosed(e -> opened.remove(tab));
        tabs.getTabs().add(tab);
        tabs.getSelectionModel().select(tab);
        opened.put(tab, new EditorFile(area, null));
        notifyZoom();
        notifyCaret(area);
        updateFileStatus();
    }

    public void openFile(Path path) {
        try {
            String content = Files.readString(path);
            CodeArea area = new CodeArea();
            area.replaceText(content);
            area.setWrapText(true);
            if (!area.getStyleClass().contains("code-area")) area.getStyleClass().add("code-area");
            new FlowScriptSyntaxHighlighter(area);
            area.caretPositionProperty().addListener((obs, o, n) -> notifyCaret(area));
            Tab tab = new Tab(path.getFileName().toString());
            tab.setContent(wrap(area));
            tab.setOnClosed(e -> opened.remove(tab));
            tabs.getTabs().add(tab);
            tabs.getSelectionModel().select(tab);
            opened.put(tab, new EditorFile(area, path));
            notifyZoom();
            notifyCaret(area);
            updateFileStatus();
            console.info("Opened: " + path);
        } catch (IOException e) {
            console.error("Failed to open file: " + e.getMessage());
        }
    }

    public void saveCurrent(boolean saveAs, Stage stage) {
        EditorFile ef = getActive();
        if (ef == null) return;
        try {
            Path path = ef.path;
            if (saveAs || path == null) {
                FileChooser chooser = new FileChooser();
                chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("FlowScript Files", "*.tlf", "*.flowscript", "*.fs", "*.txt"));
                java.io.File f = chooser.showSaveDialog(stage);
                if (f == null) return;
                path = f.toPath();
                ef.path = path;
                getActiveTab().setText(path.getFileName().toString());
            }
            Files.writeString(path, ef.area.getText(), StandardCharsets.UTF_8);
            console.info("Saved: " + path);
            updateFileStatus();
        } catch (IOException e) {
            console.error("Failed to save file: " + e.getMessage());
        }
    }

    public void findInCurrent(Stage stage) {
        EditorFile ef = getActive();
        if (ef == null) return;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Find");
        dialog.setHeaderText(null);
        dialog.setContentText("Find:");
        Optional<String> res = dialog.showAndWait();
        res.ifPresent(q -> {
            int start = Math.max(ef.area.getCaretPosition(), 0);
            String text = ef.area.getText();
            int idx = text.indexOf(q, start);
            if (idx < 0 && start > 0) idx = text.indexOf(q);
            if (idx >= 0) {
                ef.area.selectRange(idx, idx + q.length());
            } else {
                console.warn("Not found: " + q);
            }
        });
    }

    public void replaceInCurrent(Stage stage) {
        EditorFile ef = getActive();
        if (ef == null) return;
        TextInputDialog find = new TextInputDialog();
        find.setTitle("Replace");
        find.setHeaderText(null);
        find.setContentText("Find:");
        Optional<String> fq = find.showAndWait();
        if (fq.isEmpty()) return;
        TextInputDialog repl = new TextInputDialog();
        repl.setTitle("Replace");
        repl.setHeaderText(null);
        repl.setContentText("Replace with:");
        Optional<String> rq = repl.showAndWait();
        rq.ifPresent(r -> {
            String txt = ef.area.getText();
            String res = txt.replace(fq.get(), r);
            ef.area.replaceText(res);
        });
    }

    public void formatCurrent() {
        EditorFile ef = getActive();
        if (ef == null) return;
        String src = ef.area.getText();
        String formatted = simpleFormatter(src);
        ef.area.replaceText(formatted);
        console.info("Formatted current document");
    }

    public void validateCurrent() {
        EditorFile ef = getActive();
        if (ef == null) return;
        String text = ef.area.getText();
        // Very basic structural validation placeholder
        int braces = (int) text.chars().filter(c -> c == '{').count() - (int) text.chars().filter(c -> c == '}').count();
        if (braces == 0) {
            console.info("Validation OK (placeholder). Use grammar later.");
        } else {
            console.error("Unbalanced braces: " + braces);
        }
    }

    public void zoomIn() { setZoom(zoom + 0.1); }
    public void zoomOut() { setZoom(Math.max(0.2, zoom - 0.1)); }
    public void zoomReset() { setZoom(1.0); }

    private void setZoom(double z) {
        this.zoom = z;
        EditorFile ef = getActive();
        if (ef != null) {
            ef.area.setStyle("-fx-font-size: " + (int)(14 * z) + "px;");
        }
        notifyZoom();
    }

    private BorderPane wrap(CodeArea area) {
        BorderPane p = new BorderPane();
        p.setCenter(area);
        VBox.setVgrow(area, Priority.ALWAYS);
        area.setStyle("-fx-font-size: 14px;");
        return p;
    }

    private void updateFileStatus() {
        EditorFile ef = getActive();
        String name = ef == null || ef.path == null ? "Untitled" : ef.path.getFileName().toString();
        fileChangeListener.accept(name);
    }

    private void notifyCaret(CodeArea area) {
        int pos = area.getCaretPosition();
        int line = area.getCurrentParagraph() + 1;
        int col = pos - area.getAbsolutePosition(line - 1, 0) + 1;
        caretListener.onChange(line, col);
    }

    private void notifyZoom() { zoomListener.accept(zoom); }

    private EditorFile getActive() {
        Tab t = getActiveTab();
        return t == null ? null : opened.get(t);
    }

    private Tab getActiveTab() { return tabs.getSelectionModel().getSelectedItem(); }

    // StatusBar listeners
    public void onActiveCaretChange(BiCaretListener l) { this.caretListener = l; }
    public void onActiveFileChange(java.util.function.Consumer<String> c) { this.fileChangeListener = c; }
    public void onZoomChange(java.util.function.Consumer<Double> c) { this.zoomListener = c; }

    // Clipboard helpers (optional future use)
    private void copyToClipboard(String s) {
        ClipboardContent content = new ClipboardContent();
        content.putString(s);
        Clipboard.getSystemClipboard().setContent(content);
    }

    private static String simpleFormatter(String src) {
        StringBuilder out = new StringBuilder();
        int indent = 0;
        for (String line : src.split("\n", -1)) {
            String trimmed = line.trim();
            if (trimmed.endsWith("}")) indent = Math.max(0, indent - 1);
            out.append("    ".repeat(indent)).append(trimmed).append('\n');
            if (trimmed.endsWith("{")) indent++;
        }
        return out.toString();
    }

    private static class EditorFile {
        CodeArea area; Path path;
        EditorFile(CodeArea area, Path path) { this.area = area; this.path = path; }
    }

    @FunctionalInterface
    public interface BiCaretListener { void onChange(int line, int column); }
}
