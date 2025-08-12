package com.flowscript.ide;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flowscript.ide.components.*;
import com.flowscript.ide.services.ProjectService;
import com.flowscript.ide.services.ThemeService;

public class FlowScriptIDE extends Application {
    private static final Logger logger = LoggerFactory.getLogger(FlowScriptIDE.class);
    private static final String APP_TITLE = "FlowScript IDE";
    private static final double DEFAULT_WIDTH = 1400;
    private static final double DEFAULT_HEIGHT = 900;
    
    private ProjectExplorer projectExplorer;
    private CodeEditorPane codeEditorPane;
    private ConsolePane consolePane;
    private StatusBar statusBar;
    private ProjectService projectService;
    private ThemeService themeService;
    private Scene scene;
    
    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting FlowScript IDE");
        
        try {
            initializeServices();
            BorderPane root = createMainLayout();
            scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            
            // Apply theme
            themeService.applyTheme(scene);
            
            // Configure stage
            primaryStage.setTitle(APP_TITLE);
            primaryStage.setScene(scene);
            primaryStage.setMaximized(false);
            
            // Set application icon (if available)
            // TODO: Add application icon
            // primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/flowscript-icon.png")));
            
            primaryStage.show();
            
            // Load last project if exists
            projectService.loadLastProject();
            
        } catch (Exception e) {
            logger.error("Failed to start FlowScript IDE", e);
            showErrorDialog("Startup Error", "Failed to start FlowScript IDE", e.getMessage());
        }
    }
    
    private void initializeServices() {
        projectService = new ProjectService();
        themeService = new ThemeService();
    }
    
    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        
        // Create menu bar
        MenuBar menuBar = createMenuBar();
        
        // Create toolbar
        ToolBar toolBar = createToolBar();
        
        // Top section (menu + toolbar)
        VBox topSection = new VBox(menuBar, toolBar);
        root.setTop(topSection);
        
        // Create main components
        projectExplorer = new ProjectExplorer(projectService);
        codeEditorPane = new CodeEditorPane();
        consolePane = new ConsolePane();
        statusBar = new StatusBar();
        
        // Create horizontal split between project explorer and editor
        SplitPane horizontalSplit = new SplitPane();
        horizontalSplit.setOrientation(Orientation.HORIZONTAL);
        horizontalSplit.getItems().addAll(projectExplorer, codeEditorPane);
        horizontalSplit.setDividerPositions(0.2);
        
        // Create vertical split between editor area and console
        SplitPane verticalSplit = new SplitPane();
        verticalSplit.setOrientation(Orientation.VERTICAL);
        verticalSplit.getItems().addAll(horizontalSplit, consolePane);
        verticalSplit.setDividerPositions(0.75);
        
        // Set as center
        root.setCenter(verticalSplit);
        
        // Set status bar at bottom
        root.setBottom(statusBar);
        
        // Setup event handlers
        setupEventHandlers();
        
        return root;
    }
    
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        
        // File Menu
        Menu fileMenu = new Menu("File");
        MenuItem newFile = new MenuItem("New FlowScript File");
        newFile.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        newFile.setGraphic(IconFactory.createIcon("new", 14));
        MenuItem openFile = new MenuItem("Open File...");
        openFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        openFile.setGraphic(IconFactory.createIcon("open", 14));
        MenuItem saveFile = new MenuItem("Save");
        saveFile.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        saveFile.setGraphic(IconFactory.createIcon("save", 14));
        MenuItem saveAsFile = new MenuItem("Save As...");
        MenuItem openProject = new MenuItem("Open Project...");
        MenuItem closeProject = new MenuItem("Close Project");
        MenuItem exit = new MenuItem("Exit");
        exit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        
        fileMenu.getItems().addAll(
            newFile, openFile, saveFile, saveAsFile,
            new SeparatorMenuItem(),
            openProject, closeProject,
            new SeparatorMenuItem(),
            exit
        );
        
        // Edit Menu
        Menu editMenu = new Menu("Edit");
        MenuItem undo = new MenuItem("Undo");
        undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        undo.setGraphic(IconFactory.createIcon("undo", 14));
        MenuItem redo = new MenuItem("Redo");
        redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        redo.setGraphic(IconFactory.createIcon("redo", 14));
        MenuItem cut = new MenuItem("Cut");
        cut.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        cut.setGraphic(IconFactory.createIcon("cut", 14));
        MenuItem copy = new MenuItem("Copy");
        copy.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        copy.setGraphic(IconFactory.createIcon("copy", 14));
        MenuItem paste = new MenuItem("Paste");
        paste.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        paste.setGraphic(IconFactory.createIcon("paste", 14));
        MenuItem find = new MenuItem("Find...");
        find.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));
        find.setGraphic(IconFactory.createIcon("search", 14));
        MenuItem replace = new MenuItem("Replace...");
        replace.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN));
        replace.setGraphic(IconFactory.createIcon("replace", 14));
        
        editMenu.getItems().addAll(
            undo, redo,
            new SeparatorMenuItem(),
            cut, copy, paste,
            new SeparatorMenuItem(),
            find, replace
        );
        
        // View Menu
        Menu viewMenu = new Menu("View");
        CheckMenuItem showProjectExplorer = new CheckMenuItem("Project Explorer");
        showProjectExplorer.setSelected(true);
        CheckMenuItem showConsole = new CheckMenuItem("Console");
        showConsole.setSelected(true);
        CheckMenuItem showStatusBar = new CheckMenuItem("Status Bar");
        showStatusBar.setSelected(true);
        MenuItem zoomIn = new MenuItem("Zoom In");
        zoomIn.setAccelerator(new KeyCodeCombination(KeyCode.PLUS, KeyCombination.CONTROL_DOWN));
        MenuItem zoomOut = new MenuItem("Zoom Out");
        zoomOut.setAccelerator(new KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_DOWN));
        MenuItem resetZoom = new MenuItem("Reset Zoom");
        resetZoom.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT0, KeyCombination.CONTROL_DOWN));
        
        Menu themeMenu = new Menu("Theme");
        RadioMenuItem lightTheme = new RadioMenuItem("Light");
        RadioMenuItem darkTheme = new RadioMenuItem("Dark");
        RadioMenuItem monokaiTheme = new RadioMenuItem("Monokai");
        ToggleGroup themeGroup = new ToggleGroup();
        lightTheme.setToggleGroup(themeGroup);
        darkTheme.setToggleGroup(themeGroup);
        monokaiTheme.setToggleGroup(themeGroup);
        
        // Set the initial selection based on current theme
        ThemeService.Theme currentTheme = themeService.getCurrentTheme();
        switch (currentTheme) {
            case LIGHT:
                lightTheme.setSelected(true);
                break;
            case DARK:
                darkTheme.setSelected(true);
                break;
            case MONOKAI:
                monokaiTheme.setSelected(true);
                break;
        }
        
        // Add theme change handlers
        lightTheme.setOnAction(e -> changeTheme(ThemeService.Theme.LIGHT));
        darkTheme.setOnAction(e -> changeTheme(ThemeService.Theme.DARK));
        monokaiTheme.setOnAction(e -> changeTheme(ThemeService.Theme.MONOKAI));
        
        themeMenu.getItems().addAll(lightTheme, darkTheme, monokaiTheme);
        
        viewMenu.getItems().addAll(
            showProjectExplorer, showConsole, showStatusBar,
            new SeparatorMenuItem(),
            zoomIn, zoomOut, resetZoom,
            new SeparatorMenuItem(),
            themeMenu
        );
        
        // Run Menu
        Menu runMenu = new Menu("Run");
        MenuItem runFile = new MenuItem("Run FlowScript");
        runFile.setAccelerator(new KeyCodeCombination(KeyCode.F5));
        runFile.setGraphic(IconFactory.createIcon("run", 14));
        MenuItem debugFile = new MenuItem("Debug FlowScript");
        debugFile.setAccelerator(new KeyCodeCombination(KeyCode.F6));
        debugFile.setGraphic(IconFactory.createIcon("debug", 14));
        MenuItem stopExecution = new MenuItem("Stop");
        stopExecution.setAccelerator(new KeyCodeCombination(KeyCode.F7));
        stopExecution.setGraphic(IconFactory.createIcon("stop", 14));
        MenuItem validateFile = new MenuItem("Validate Syntax");
        validateFile.setAccelerator(new KeyCodeCombination(KeyCode.F8));
        
        runMenu.getItems().addAll(
            runFile, debugFile, stopExecution,
            new SeparatorMenuItem(),
            validateFile
        );
        
        // Tools Menu
        Menu toolsMenu = new Menu("Tools");
        MenuItem flowToBpmn = new MenuItem("Export to BPMN...");
        MenuItem bpmnToFlow = new MenuItem("Import from BPMN...");
        MenuItem formatCode = new MenuItem("Format Code");
        formatCode.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        formatCode.setGraphic(IconFactory.createIcon("format", 14));
        MenuItem preferences = new MenuItem("Preferences...");
        preferences.setGraphic(IconFactory.createIcon("settings", 14));
        
        toolsMenu.getItems().addAll(
            flowToBpmn, bpmnToFlow,
            new SeparatorMenuItem(),
            formatCode,
            new SeparatorMenuItem(),
            preferences
        );
        
        // Help Menu
        Menu helpMenu = new Menu("Help");
        MenuItem documentation = new MenuItem("FlowScript Documentation");
        documentation.setAccelerator(new KeyCodeCombination(KeyCode.F1));
        MenuItem examples = new MenuItem("Example Projects...");
        MenuItem checkUpdates = new MenuItem("Check for Updates...");
        MenuItem about = new MenuItem("About FlowScript IDE");
        
        helpMenu.getItems().addAll(
            documentation, examples,
            new SeparatorMenuItem(),
            checkUpdates,
            new SeparatorMenuItem(),
            about
        );
        
        // Setup event handlers
        setupMenuEventHandlers(fileMenu, editMenu, viewMenu, runMenu, toolsMenu, helpMenu);
        
        menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu, runMenu, toolsMenu, helpMenu);
        return menuBar;
    }
    
    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar();
        
        // File operations
        Button newBtn = createToolButton("New");
        Button openBtn = createToolButton("Open");
        Button saveBtn = createToolButton("Save");
        
        // Edit operations
        Button undoBtn = createToolButton("Undo");
        Button redoBtn = createToolButton("Redo");
        
        // Run operations
        Button runBtn = createToolButton("Run");
        Button debugBtn = createToolButton("Debug");
        Button stopBtn = createToolButton("Stop");
        
        // Search
        Button searchBtn = createToolButton("Search");
        
        // Format
        Button formatBtn = createToolButton("Format");
        
        toolBar.getItems().addAll(
            newBtn, openBtn, saveBtn,
            new Separator(),
            undoBtn, redoBtn,
            new Separator(),
            runBtn, debugBtn, stopBtn,
            new Separator(),
            searchBtn, formatBtn
        );
        
        return toolBar;
    }
    
    private Button createToolButton(String tooltip) {
        Button button = new Button();
        button.setTooltip(new Tooltip(tooltip));
        button.setPrefSize(30, 30);
        
        // Use IconFactory to create vector icons
        String iconName = tooltip.toLowerCase();
        javafx.scene.Node icon = IconFactory.createIcon(iconName, 16);
        button.setGraphic(icon);
        
        return button;
    }
    
    private void setupEventHandlers() {
        // Project explorer file selection
        projectExplorer.setOnFileSelected(file -> {
            codeEditorPane.openFile(file);
            statusBar.setMessage("Opened: " + file.getName());
        });
        
        // Code editor changes
        codeEditorPane.setOnTextChanged(() -> {
            statusBar.setModified(true);
        });
        
        // Console commands
        consolePane.setOnCommand(command -> {
            executeCommand(command);
        });
    }
    
    private void setupMenuEventHandlers(Menu... menus) {
        // Implementation of menu event handlers would go here
        // For brevity, showing just a few examples
        
        for (Menu menu : menus) {
            for (MenuItem item : menu.getItems()) {
                if (item instanceof MenuItem && !(item instanceof Menu)) {
                    item.setOnAction(e -> {
                        logger.info("Menu action: " + item.getText());
                        handleMenuAction(item.getText());
                    });
                }
            }
        }
    }
    
    private void handleMenuAction(String action) {
        switch (action) {
            case "New FlowScript File":
                codeEditorPane.newFile();
                break;
            case "Open File...":
                codeEditorPane.openFile();
                break;
            case "Save":
                codeEditorPane.saveFile();
                break;
            case "Exit":
                System.exit(0);
                break;
            case "Run FlowScript":
                runCurrentFile();
                break;
            case "Validate Syntax":
                validateCurrentFile();
                break;
            default:
                statusBar.setMessage("Action not implemented: " + action);
        }
    }
    
    private void runCurrentFile() {
        consolePane.clear();
        consolePane.println("Running FlowScript file...");
        // Implementation would execute the FlowScript interpreter
        consolePane.println("FlowScript execution completed.");
    }
    
    private void validateCurrentFile() {
        consolePane.clear();
        consolePane.println("Validating FlowScript syntax...");
        // Implementation would validate the syntax
        consolePane.println("Syntax validation completed.");
    }
    
    private void executeCommand(String command) {
        consolePane.println("> " + command);
        // Process the command
        if (command.equalsIgnoreCase("clear")) {
            consolePane.clear();
        } else if (command.startsWith("run ")) {
            String file = command.substring(4);
            consolePane.println("Running " + file + "...");
        } else {
            consolePane.println("Unknown command: " + command);
        }
    }
    
    private void changeTheme(ThemeService.Theme theme) {
        themeService.setTheme(theme);
        themeService.applyTheme(scene);
        statusBar.setMessage("Theme changed to: " + theme.getDisplayName());
        logger.info("Theme changed to: {}", theme.getDisplayName());
    }
    
    private void showErrorDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}