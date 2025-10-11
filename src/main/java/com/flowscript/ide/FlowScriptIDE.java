package com.flowscript.ide;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.flowscript.ide.components.CodeEditorPane;
import com.flowscript.ide.components.ConsolePane;
import com.flowscript.ide.components.ProjectExplorer;
import com.flowscript.ide.components.StatusBar;
import com.flowscript.ide.services.ThemeService;
import com.flowscript.ide.services.ThemeService.Theme;

/**
 * Minimal JavaFX application scaffold to satisfy README structure.
 */
public class FlowScriptIDE extends Application {

    @Override
    public void start(Stage stage) {
        // Root layout
        BorderPane root = new BorderPane();

        // Scene (created early to allow theme switching)
        Scene scene = new Scene(root, 1100, 720);

        // Default theme: DARK
        ThemeService themeService = new ThemeService();
        themeService.apply(scene, Theme.DARK);

        // Top: Menu bar (with theme options)
        MenuBar menuBar = buildMenuBar(scene, themeService);
        root.setTop(menuBar);

        // Left: Project explorer
        ProjectExplorer explorer = new ProjectExplorer();
        root.setLeft(explorer);

        // Center: Editor
        CodeEditorPane editor = new CodeEditorPane();
        root.setCenter(editor);

        // Bottom: Status + Console
        StatusBar statusBar = new StatusBar();
        ConsolePane console = new ConsolePane();
        VBox bottom = new VBox(statusBar, console);
        VBox.setVgrow(console, Priority.SOMETIMES);
        root.setBottom(bottom);

        // Stage
        stage.setTitle("FlowScript IDE");
        stage.setScene(scene);
        stage.show();
    }

    private MenuBar buildMenuBar(Scene scene, ThemeService themeService) {
        Menu file = new Menu("File");
        file.getItems().addAll(new MenuItem("New"), new MenuItem("Open"), new MenuItem("Save"));

        Menu edit = new Menu("Edit");
        edit.getItems().addAll(new MenuItem("Undo"), new MenuItem("Redo"));

        Menu view = new Menu("View");
        Menu theme = new Menu("Theme");
        MenuItem dark = new MenuItem("Dark");
        MenuItem light = new MenuItem("Light");
        MenuItem monokai = new MenuItem("Monokai");
        dark.setOnAction(e -> themeService.apply(scene, Theme.DARK));
        light.setOnAction(e -> themeService.apply(scene, Theme.LIGHT));
        monokai.setOnAction(e -> themeService.apply(scene, Theme.MONOKAI));
        theme.getItems().addAll(dark, light, monokai);
        view.getItems().add(theme);

        Menu run = new Menu("Run");
        run.getItems().addAll(new MenuItem("Validate Syntax"), new MenuItem("Run"));

        return new MenuBar(file, edit, view, run);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
