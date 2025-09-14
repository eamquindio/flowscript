package com.flowscript.ide;

import com.flowscript.ide.components.CodeEditorPane;
import com.flowscript.ide.components.ConsolePane;
import com.flowscript.ide.components.ProjectExplorer;
import com.flowscript.ide.components.StatusBar;
import com.flowscript.ide.services.ProjectService;
import com.flowscript.ide.services.ThemeService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;

public class FlowScriptIDE extends Application {

    private ThemeService themeService;
    private ProjectService projectService;
    private CodeEditorPane codeEditorPane;
    private ConsolePane consolePane;
    private StatusBar statusBar;

    @Override
    public void start(Stage stage) {
        this.themeService = new ThemeService();
        this.projectService = new ProjectService();

        BorderPane root = new BorderPane();

        // Components
        consolePane = new ConsolePane();
        codeEditorPane = new CodeEditorPane(consolePane);
        ProjectExplorer projectExplorer = new ProjectExplorer(codeEditorPane::openFile);
        projectService.attachExplorer(projectExplorer);
        statusBar = new StatusBar(codeEditorPane);

        // Layout
        root.setLeft(projectExplorer.getView());
        root.setCenter(codeEditorPane.getView());
        root.setBottom(new VBox(consolePane.getView(), statusBar.getView()));
        VBox.setVgrow(consolePane.getView(), Priority.NEVER);

        // Menu + shortcuts
        MenuBar menuBar = buildMenuBar(stage);
        VBox top = new VBox(menuBar);
        top.setPadding(new Insets(0));
        root.setTop(top);

        Scene scene = new Scene(root, 1200, 800);
        themeService.applyTheme(scene, ThemeService.Theme.DARK);
        try {
            String syn = getClass().getResource("/css/syntax-highlighting.css").toExternalForm();
            if (!scene.getStylesheets().contains(syn)) scene.getStylesheets().add(syn);
        } catch (Exception ignored) { }

        // Global accelerators that act on active editor
        addAccelerators(scene, stage);

        stage.setTitle("FlowScript IDE");
        stage.setScene(scene);
        consolePane.info("Stylesheets: " + scene.getStylesheets());
        stage.show();
    }

    private MenuBar buildMenuBar(Stage stage) {
        Menu file = new Menu("File");
        MenuItem miNew = new MenuItem("New (Ctrl+N)");
        miNew.setOnAction(e -> codeEditorPane.newFile());

        MenuItem miOpenFolder = new MenuItem("Open Folder... (Ctrl+O)");
        miOpenFolder.setOnAction(e -> {
            DirectoryChooser chooser = new DirectoryChooser();
            File dir = chooser.showDialog(stage);
            if (dir != null) {
                projectService.setRoot(dir.toPath());
                consolePane.info("Project opened: " + dir.getAbsolutePath());
            }
        });

        MenuItem miOpenFile = new MenuItem("Open File... (Ctrl+Shift+O)");
        miOpenFile.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("FlowScript Files", "*.tlf", "*.flowscript", "*.fs", "*.txt"));
            File f = chooser.showOpenDialog(stage);
            if (f != null) codeEditorPane.openFile(f.toPath());
        });

        MenuItem miSave = new MenuItem("Save (Ctrl+S)");
        miSave.setOnAction(e -> codeEditorPane.saveCurrent(false, stage));
        MenuItem miSaveAs = new MenuItem("Save As...");
        miSaveAs.setOnAction(e -> codeEditorPane.saveCurrent(true, stage));

        MenuItem miExit = new MenuItem("Exit");
        miExit.setOnAction(e -> stage.close());

        file.getItems().addAll(miNew, miOpenFolder, miOpenFile, new SeparatorMenuItem(), miSave, miSaveAs, new SeparatorMenuItem(), miExit);

        Menu edit = new Menu("Edit");
        MenuItem miFind = new MenuItem("Find (Ctrl+F)");
        miFind.setOnAction(e -> codeEditorPane.findInCurrent(stage));
        MenuItem miReplace = new MenuItem("Replace (Ctrl+H)");
        miReplace.setOnAction(e -> codeEditorPane.replaceInCurrent(stage));
        MenuItem miFormat = new MenuItem("Format (Ctrl+Shift+F)");
        miFormat.setOnAction(e -> codeEditorPane.formatCurrent());
        edit.getItems().addAll(miFind, miReplace, new SeparatorMenuItem(), miFormat);

        Menu view = new Menu("View");
        Menu themes = new Menu("Theme");
        ToggleGroup tg = new ToggleGroup();
        for (ThemeService.Theme t : ThemeService.Theme.values()) {
            RadioMenuItem rmi = new RadioMenuItem(t.displayName);
            rmi.setToggleGroup(tg);
            if (t == ThemeService.Theme.DARK) rmi.setSelected(true);
            rmi.setOnAction(e -> themeService.applyTheme(stage.getScene(), t));
            themes.getItems().add(rmi);
        }
        MenuItem miZoomIn = new MenuItem("Zoom In (Ctrl++ )");
        miZoomIn.setOnAction(e -> codeEditorPane.zoomIn());
        MenuItem miZoomOut = new MenuItem("Zoom Out (Ctrl+-)");
        miZoomOut.setOnAction(e -> codeEditorPane.zoomOut());
        MenuItem miZoomReset = new MenuItem("Reset Zoom (Ctrl+0)");
        miZoomReset.setOnAction(e -> codeEditorPane.zoomReset());
        view.getItems().addAll(themes, new SeparatorMenuItem(), miZoomIn, miZoomOut, miZoomReset);

        Menu run = new Menu("Run");
        MenuItem miRun = new MenuItem("Run (F5)");
        miRun.setOnAction(e -> consolePane.info("Run not implemented. Use F8 to validate."));
        MenuItem miDebug = new MenuItem("Debug (F6)");
        miDebug.setOnAction(e -> consolePane.info("Debug not implemented."));
        MenuItem miStop = new MenuItem("Stop (F7)");
        miStop.setOnAction(e -> consolePane.info("Stop requested."));
        MenuItem miValidate = new MenuItem("Validate (F8)");
        miValidate.setOnAction(e -> codeEditorPane.validateCurrent());
        run.getItems().addAll(miRun, miDebug, miStop, new SeparatorMenuItem(), miValidate);

        return new MenuBar(file, edit, view, run);
    }

    private void addAccelerators(Scene scene, Stage stage) {
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN), () -> codeEditorPane.newFile());
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN), () -> {
            DirectoryChooser chooser = new DirectoryChooser();
            File dir = chooser.showDialog(stage);
            if (dir != null) {
                projectService.setRoot(dir.toPath());
                consolePane.info("Project opened: " + dir.getAbsolutePath());
            }
        });
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN), () -> {
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("FlowScript Files", "*.tlf", "*.flowscript", "*.fs", "*.txt"));
            File f = chooser.showOpenDialog(stage);
            if (f != null) codeEditorPane.openFile(f.toPath());
        });
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN), () -> codeEditorPane.saveCurrent(false, stage));
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN), () -> codeEditorPane.findInCurrent(stage));
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN), () -> codeEditorPane.replaceInCurrent(stage));
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.SPACE, KeyCombination.CONTROL_DOWN), () -> consolePane.info("Auto-complete placeholder"));
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN), () -> codeEditorPane.formatCurrent());
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.DIGIT0, KeyCombination.CONTROL_DOWN), () -> codeEditorPane.zoomReset());
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.CONTROL_DOWN), () -> codeEditorPane.zoomIn());
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_DOWN), () -> codeEditorPane.zoomOut());
        // Function keys
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F5), () -> consolePane.info("Run not implemented"));
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F6), () -> consolePane.info("Debug not implemented"));
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F7), () -> consolePane.info("Stop requested"));
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F8), () -> codeEditorPane.validateCurrent());
    }

    public static void main(String[] args) {
        launch();
    }
}
