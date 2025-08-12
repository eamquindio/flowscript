package com.flowscript.ide.components;

import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flowscript.ide.services.ProjectService;

import java.io.File;
import java.util.function.Consumer;

public class ProjectExplorer extends VBox {
    private static final Logger logger = LoggerFactory.getLogger(ProjectExplorer.class);
    
    private TreeView<File> treeView;
    private ProjectService projectService;
    private Consumer<File> onFileSelected;
    
    public ProjectExplorer(ProjectService projectService) {
        this.projectService = projectService;
        initializeComponents();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        // Title bar
        Label titleLabel = new Label("Project Explorer");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-padding: 5px;");
        
        // Toolbar
        ToolBar toolBar = new ToolBar();
        
        Button openProjectBtn = new Button("Open");
        openProjectBtn.setTooltip(new Tooltip("Open Project"));
        openProjectBtn.setOnAction(e -> openProject());
        
        Button refreshBtn = new Button("↻");
        refreshBtn.setTooltip(new Tooltip("Refresh"));
        refreshBtn.setOnAction(e -> refreshTree());
        
        Button newFolderBtn = new Button("+");
        newFolderBtn.setTooltip(new Tooltip("New Folder"));
        newFolderBtn.setOnAction(e -> createNewFolder());
        
        toolBar.getItems().addAll(openProjectBtn, refreshBtn, newFolderBtn);
        
        // Tree view
        treeView = new TreeView<>();
        treeView.setShowRoot(false);
        treeView.setCellFactory(tv -> new FileTreeCell());
        treeView.setEditable(true);
        
        // Context menu
        ContextMenu contextMenu = createContextMenu();
        treeView.setContextMenu(contextMenu);
        
        getChildren().addAll(titleLabel, toolBar, treeView);
        
        // Load empty state
        loadEmptyState();
    }
    
    private void setupEventHandlers() {
        // Double-click to open files
        treeView.setOnMouseClicked(this::handleMouseClick);
        
        // Selection change
        treeView.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null && newItem.getValue().isFile()) {
                if (onFileSelected != null) {
                    onFileSelected.accept(newItem.getValue());
                }
            }
        });
    }
    
    private void handleMouseClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            TreeItem<File> selectedItem = treeView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                File file = selectedItem.getValue();
                if (file.isFile()) {
                    if (onFileSelected != null) {
                        onFileSelected.accept(file);
                    }
                } else if (file.isDirectory()) {
                    selectedItem.setExpanded(!selectedItem.isExpanded());
                }
            }
        }
    }
    
    private ContextMenu createContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        
        MenuItem newFileItem = new MenuItem("New FlowScript File");
        newFileItem.setOnAction(e -> createNewFile());
        
        MenuItem newFolderItem = new MenuItem("New Folder");
        newFolderItem.setOnAction(e -> createNewFolder());
        
        SeparatorMenuItem separator1 = new SeparatorMenuItem();
        
        MenuItem cutItem = new MenuItem("Cut");
        MenuItem copyItem = new MenuItem("Copy");
        MenuItem pasteItem = new MenuItem("Paste");
        
        SeparatorMenuItem separator2 = new SeparatorMenuItem();
        
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> deleteSelectedItem());
        
        MenuItem renameItem = new MenuItem("Rename");
        renameItem.setOnAction(e -> renameSelectedItem());
        
        SeparatorMenuItem separator3 = new SeparatorMenuItem();
        
        MenuItem propertiesItem = new MenuItem("Properties");
        propertiesItem.setOnAction(e -> showProperties());
        
        contextMenu.getItems().addAll(
            newFileItem, newFolderItem, separator1,
            cutItem, copyItem, pasteItem, separator2,
            deleteItem, renameItem, separator3,
            propertiesItem
        );
        
        return contextMenu;
    }
    
    private void openProject() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Project Directory");
        
        File selectedDirectory = directoryChooser.showDialog(getScene().getWindow());
        if (selectedDirectory != null) {
            loadProject(selectedDirectory);
        }
    }
    
    private void loadProject(File projectDir) {
        TreeItem<File> root = new TreeItem<>(projectDir);
        root.setExpanded(true);
        
        populateTree(root, projectDir);
        
        treeView.setRoot(root);
        treeView.setShowRoot(true);
        
        projectService.setCurrentProject(projectDir.getAbsolutePath());
        logger.info("Loaded project: " + projectDir.getAbsolutePath());
    }
    
    private void populateTree(TreeItem<File> parent, File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    // Skip hidden files and common build directories
                    if (file.isHidden() || isIgnoredDirectory(file)) {
                        continue;
                    }
                    
                    TreeItem<File> item = new TreeItem<>(file);
                    parent.getChildren().add(item);
                    
                    if (file.isDirectory()) {
                        populateTree(item, file);
                    }
                }
            }
        }
    }
    
    private boolean isIgnoredDirectory(File file) {
        if (!file.isDirectory()) return false;
        String name = file.getName();
        return name.equals(".git") || name.equals(".gradle") || 
               name.equals("build") || name.equals("target") ||
               name.equals("node_modules") || name.equals(".idea") ||
               name.equals(".vscode");
    }
    
    private void refreshTree() {
        TreeItem<File> root = treeView.getRoot();
        if (root != null) {
            root.getChildren().clear();
            populateTree(root, root.getValue());
        }
    }
    
    private void createNewFile() {
        TreeItem<File> selectedItem = treeView.getSelectionModel().getSelectedItem();
        File parentDir = getParentDirectory(selectedItem);
        
        if (parentDir != null) {
            TextInputDialog dialog = new TextInputDialog("Untitled.flow");
            dialog.setTitle("New FlowScript File");
            dialog.setHeaderText("Create new FlowScript file");
            dialog.setContentText("File name:");
            
            dialog.showAndWait().ifPresent(fileName -> {
                try {
                    File newFile = new File(parentDir, fileName);
                    if (!newFile.exists()) {
                        newFile.createNewFile();
                        refreshTree();
                        
                        // Select the new file
                        TreeItem<File> newItem = findTreeItem(treeView.getRoot(), newFile);
                        if (newItem != null) {
                            treeView.getSelectionModel().select(newItem);
                        }
                    } else {
                        showError("File already exists: " + fileName);
                    }
                } catch (Exception ex) {
                    logger.error("Failed to create file", ex);
                    showError("Failed to create file: " + ex.getMessage());
                }
            });
        }
    }
    
    private void createNewFolder() {
        TreeItem<File> selectedItem = treeView.getSelectionModel().getSelectedItem();
        File parentDir = getParentDirectory(selectedItem);
        
        if (parentDir != null) {
            TextInputDialog dialog = new TextInputDialog("New Folder");
            dialog.setTitle("New Folder");
            dialog.setHeaderText("Create new folder");
            dialog.setContentText("Folder name:");
            
            dialog.showAndWait().ifPresent(folderName -> {
                File newFolder = new File(parentDir, folderName);
                if (!newFolder.exists()) {
                    newFolder.mkdirs();
                    refreshTree();
                } else {
                    showError("Folder already exists: " + folderName);
                }
            });
        }
    }
    
    private void deleteSelectedItem() {
        TreeItem<File> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            File file = selectedItem.getValue();
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Delete " + (file.isDirectory() ? "folder" : "file"));
            alert.setContentText("Are you sure you want to delete '" + file.getName() + "'?");
            
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        if (file.isDirectory()) {
                            deleteDirectory(file);
                        } else {
                            file.delete();
                        }
                        refreshTree();
                    } catch (Exception ex) {
                        logger.error("Failed to delete file", ex);
                        showError("Failed to delete: " + ex.getMessage());
                    }
                }
            });
        }
    }
    
    private void renameSelectedItem() {
        TreeItem<File> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            File file = selectedItem.getValue();
            
            TextInputDialog dialog = new TextInputDialog(file.getName());
            dialog.setTitle("Rename");
            dialog.setHeaderText("Rename " + (file.isDirectory() ? "folder" : "file"));
            dialog.setContentText("New name:");
            
            dialog.showAndWait().ifPresent(newName -> {
                try {
                    File newFile = new File(file.getParent(), newName);
                    if (file.renameTo(newFile)) {
                        refreshTree();
                    } else {
                        showError("Failed to rename file");
                    }
                } catch (Exception ex) {
                    logger.error("Failed to rename file", ex);
                    showError("Failed to rename: " + ex.getMessage());
                }
            });
        }
    }
    
    private void showProperties() {
        TreeItem<File> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            File file = selectedItem.getValue();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Properties");
            alert.setHeaderText(file.getName());
            
            StringBuilder content = new StringBuilder();
            content.append("Path: ").append(file.getAbsolutePath()).append("\n");
            content.append("Type: ").append(file.isDirectory() ? "Folder" : "File").append("\n");
            content.append("Size: ").append(formatFileSize(file.length())).append("\n");
            content.append("Last Modified: ").append(new java.util.Date(file.lastModified())).append("\n");
            content.append("Readable: ").append(file.canRead()).append("\n");
            content.append("Writable: ").append(file.canWrite()).append("\n");
            
            alert.setContentText(content.toString());
            alert.showAndWait();
        }
    }
    
    private File getParentDirectory(TreeItem<File> item) {
        if (item == null) {
            return treeView.getRoot() != null ? treeView.getRoot().getValue() : null;
        }
        
        File file = item.getValue();
        return file.isDirectory() ? file : file.getParentFile();
    }
    
    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }
    
    private TreeItem<File> findTreeItem(TreeItem<File> parent, File targetFile) {
        if (parent.getValue().equals(targetFile)) {
            return parent;
        }
        
        for (TreeItem<File> child : parent.getChildren()) {
            TreeItem<File> result = findTreeItem(child, targetFile);
            if (result != null) {
                return result;
            }
        }
        
        return null;
    }
    
    private String formatFileSize(long size) {
        if (size < 1024) return size + " B";
        if (size < 1024 * 1024) return (size / 1024) + " KB";
        if (size < 1024 * 1024 * 1024) return (size / (1024 * 1024)) + " MB";
        return (size / (1024 * 1024 * 1024)) + " GB";
    }
    
    private void loadEmptyState() {
        TreeItem<File> emptyRoot = new TreeItem<>(new File("No Project Loaded"));
        treeView.setRoot(emptyRoot);
        treeView.setShowRoot(true);
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void setOnFileSelected(Consumer<File> onFileSelected) {
        this.onFileSelected = onFileSelected;
    }
    
    // Custom tree cell for file icons
    private static class FileTreeCell extends TextFieldTreeCell<File> {
        @Override
        public void updateItem(File file, boolean empty) {
            super.updateItem(file, empty);
            
            if (empty || file == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(file.getName());
                
                // Set icon based on file type
                ImageView icon = new ImageView();
                icon.setFitWidth(16);
                icon.setFitHeight(16);
                
                try {
                    String iconPath;
                    if (file.isDirectory()) {
                        iconPath = "/icons/folder.png";
                    } else if (file.getName().endsWith(".flow")) {
                        iconPath = "/icons/flowscript-file.png";
                    } else {
                        iconPath = "/icons/file.png";
                    }
                    
                    Image iconImage = new Image(getClass().getResourceAsStream(iconPath));
                    icon.setImage(iconImage);
                    setGraphic(icon);
                } catch (Exception e) {
                    // Use default text if icon not found
                    setGraphic(null);
                }
            }
        }
    }
}