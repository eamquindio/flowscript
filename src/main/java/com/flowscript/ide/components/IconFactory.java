package com.flowscript.ide.components;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;

/**
 * Factory class for creating vector-based icons using JavaFX shapes and Unicode symbols
 */
public class IconFactory {
    
    private static final Color DEFAULT_COLOR = Color.web("#CCCCCC");
    private static final Color ACCENT_COLOR = Color.web("#007ACC");
    
    public static Node createIcon(String iconName) {
        return createIcon(iconName, 16, DEFAULT_COLOR);
    }
    
    public static Node createIcon(String iconName, double size) {
        return createIcon(iconName, size, DEFAULT_COLOR);
    }
    
    public static Node createIcon(String iconName, double size, Color color) {
        switch (iconName.toLowerCase()) {
            // File operations
            case "new":
            case "file-plus":
                return createNewFileIcon(size, color);
            case "open":
            case "folder-open":
                return createOpenFolderIcon(size, color);
            case "save":
            case "save-file":
                return createSaveIcon(size, color);
            
            // Edit operations
            case "undo":
                return createUndoIcon(size, color);
            case "redo":
                return createRedoIcon(size, color);
            case "cut":
                return createCutIcon(size, color);
            case "copy":
                return createCopyIcon(size, color);
            case "paste":
                return createPasteIcon(size, color);
            
            // Run operations
            case "run":
            case "play":
                return createPlayIcon(size, color);
            case "debug":
            case "bug":
                return createDebugIcon(size, color);
            case "stop":
                return createStopIcon(size, color);
            
            // Search and navigation
            case "search":
            case "find":
                return createSearchIcon(size, color);
            case "replace":
                return createReplaceIcon(size, color);
            
            // Format and tools
            case "format":
            case "code":
                return createFormatIcon(size, color);
            case "settings":
            case "preferences":
                return createSettingsIcon(size, color);
            
            // File types
            case "flowscript":
            case "flow-file":
                return createFlowScriptIcon(size, color);
            case "folder":
                return createFolderIcon(size, color);
            case "file":
                return createFileIcon(size, color);
            
            // UI elements
            case "close":
                return createCloseIcon(size, color);
            case "menu":
                return createMenuIcon(size, color);
            case "arrow-right":
                return createArrowRightIcon(size, color);
            case "arrow-down":
                return createArrowDownIcon(size, color);
            
            default:
                return createDefaultIcon(size, color);
        }
    }
    
    private static Node createNewFileIcon(double size, Color color) {
        // Rectangle with plus sign
        Rectangle rect = new Rectangle(size * 0.7, size * 0.9);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(color);
        rect.setStrokeWidth(1.5);
        
        // Plus sign
        Line horizontal = new Line(-size * 0.15, 0, size * 0.15, 0);
        horizontal.setStroke(color);
        horizontal.setStrokeWidth(2);
        
        Line vertical = new Line(0, -size * 0.15, 0, size * 0.15);
        vertical.setStroke(color);
        vertical.setStrokeWidth(2);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(rect, horizontal, vertical);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createOpenFolderIcon(double size, Color color) {
        // Folder shape
        Polygon folder = new Polygon();
        folder.getPoints().addAll(new Double[]{
            0.0, size * 0.3,
            size * 0.3, size * 0.3,
            size * 0.4, size * 0.1,
            size * 0.9, size * 0.1,
            size * 0.9, size * 0.8,
            0.0, size * 0.8
        });
        folder.setFill(Color.TRANSPARENT);
        folder.setStroke(color);
        folder.setStrokeWidth(1.5);
        
        StackPane stack = new StackPane();
        stack.getChildren().add(folder);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createSaveIcon(double size, Color color) {
        // Floppy disk
        Rectangle outer = new Rectangle(size * 0.8, size * 0.8);
        outer.setFill(Color.TRANSPARENT);
        outer.setStroke(color);
        outer.setStrokeWidth(1.5);
        
        Rectangle inner = new Rectangle(size * 0.5, size * 0.3);
        inner.setFill(Color.TRANSPARENT);
        inner.setStroke(color);
        inner.setStrokeWidth(1);
        inner.setTranslateY(-size * 0.15);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(outer, inner);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createPlayIcon(double size, Color color) {
        // Triangle pointing right
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(new Double[]{
            0.0, 0.0,
            size * 0.7, size * 0.4,
            0.0, size * 0.8
        });
        triangle.setFill(color);
        
        StackPane stack = new StackPane();
        stack.getChildren().add(triangle);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createStopIcon(double size, Color color) {
        // Square
        Rectangle square = new Rectangle(size * 0.6, size * 0.6);
        square.setFill(color);
        
        StackPane stack = new StackPane();
        stack.getChildren().add(square);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createDebugIcon(double size, Color color) {
        // Bug shape using ellipse and lines
        Ellipse body = new Ellipse(size * 0.25, size * 0.35);
        body.setFill(Color.TRANSPARENT);
        body.setStroke(color);
        body.setStrokeWidth(1.5);
        
        // Antennae
        Line antenna1 = new Line(-size * 0.1, -size * 0.3, -size * 0.2, -size * 0.4);
        Line antenna2 = new Line(size * 0.1, -size * 0.3, size * 0.2, -size * 0.4);
        antenna1.setStroke(color);
        antenna2.setStroke(color);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(body, antenna1, antenna2);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createSearchIcon(double size, Color color) {
        // Magnifying glass
        Circle circle = new Circle(size * 0.3);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(color);
        circle.setStrokeWidth(1.5);
        circle.setTranslateX(-size * 0.1);
        circle.setTranslateY(-size * 0.1);
        
        Line handle = new Line(size * 0.1, size * 0.1, size * 0.3, size * 0.3);
        handle.setStroke(color);
        handle.setStrokeWidth(2);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(circle, handle);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createUndoIcon(double size, Color color) {
        // Curved arrow pointing left
        Arc arc = new Arc(0, 0, size * 0.3, size * 0.3, 30, 300);
        arc.setFill(Color.TRANSPARENT);
        arc.setStroke(color);
        arc.setStrokeWidth(2);
        
        // Arrow head
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(new Double[]{
            -size * 0.4, -size * 0.1,
            -size * 0.25, -size * 0.25,
            -size * 0.25, 0.0
        });
        arrow.setFill(color);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(arc, arrow);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createRedoIcon(double size, Color color) {
        // Curved arrow pointing right
        Arc arc = new Arc(0, 0, size * 0.3, size * 0.3, 150, 300);
        arc.setFill(Color.TRANSPARENT);
        arc.setStroke(color);
        arc.setStrokeWidth(2);
        
        // Arrow head
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(new Double[]{
            size * 0.4, -size * 0.1,
            size * 0.25, -size * 0.25,
            size * 0.25, 0.0
        });
        arrow.setFill(color);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(arc, arrow);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createCutIcon(double size, Color color) {
        // Scissors
        Circle handle1 = new Circle(size * 0.1);
        Circle handle2 = new Circle(size * 0.1);
        handle1.setFill(Color.TRANSPARENT);
        handle2.setFill(Color.TRANSPARENT);
        handle1.setStroke(color);
        handle2.setStroke(color);
        handle1.setTranslateX(-size * 0.2);
        handle1.setTranslateY(size * 0.2);
        handle2.setTranslateX(-size * 0.2);
        handle2.setTranslateY(-size * 0.2);
        
        Line blade1 = new Line(-size * 0.1, size * 0.1, size * 0.3, -size * 0.1);
        Line blade2 = new Line(-size * 0.1, -size * 0.1, size * 0.3, size * 0.1);
        blade1.setStroke(color);
        blade2.setStroke(color);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(handle1, handle2, blade1, blade2);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createCopyIcon(double size, Color color) {
        // Two overlapping rectangles
        Rectangle back = new Rectangle(size * 0.6, size * 0.6);
        Rectangle front = new Rectangle(size * 0.6, size * 0.6);
        
        back.setFill(Color.TRANSPARENT);
        front.setFill(Color.TRANSPARENT);
        back.setStroke(color);
        front.setStroke(color);
        back.setStrokeWidth(1.5);
        front.setStrokeWidth(1.5);
        
        back.setTranslateX(size * 0.1);
        back.setTranslateY(size * 0.1);
        front.setTranslateX(-size * 0.1);
        front.setTranslateY(-size * 0.1);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(back, front);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createPasteIcon(double size, Color color) {
        // Clipboard
        Rectangle clipboard = new Rectangle(size * 0.6, size * 0.8);
        clipboard.setFill(Color.TRANSPARENT);
        clipboard.setStroke(color);
        clipboard.setStrokeWidth(1.5);
        
        Rectangle clip = new Rectangle(size * 0.3, size * 0.15);
        clip.setFill(Color.TRANSPARENT);
        clip.setStroke(color);
        clip.setStrokeWidth(1);
        clip.setTranslateY(-size * 0.35);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(clipboard, clip);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createFormatIcon(double size, Color color) {
        // Curly braces
        Text leftBrace = new Text("{");
        Text rightBrace = new Text("}");
        leftBrace.setFill(color);
        rightBrace.setFill(color);
        leftBrace.setFont(Font.font(size));
        rightBrace.setFont(Font.font(size));
        leftBrace.setTranslateX(-size * 0.2);
        rightBrace.setTranslateX(size * 0.2);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(leftBrace, rightBrace);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createSettingsIcon(double size, Color color) {
        // Gear
        Circle center = new Circle(size * 0.2);
        center.setFill(Color.TRANSPARENT);
        center.setStroke(color);
        center.setStrokeWidth(1.5);
        
        // Teeth around the gear
        Rectangle tooth1 = new Rectangle(size * 0.1, size * 0.05);
        Rectangle tooth2 = new Rectangle(size * 0.05, size * 0.1);
        Rectangle tooth3 = new Rectangle(size * 0.1, size * 0.05);
        Rectangle tooth4 = new Rectangle(size * 0.05, size * 0.1);
        
        tooth1.setFill(color);
        tooth2.setFill(color);
        tooth3.setFill(color);
        tooth4.setFill(color);
        
        tooth1.setTranslateY(-size * 0.35);
        tooth2.setTranslateX(size * 0.35);
        tooth3.setTranslateY(size * 0.35);
        tooth4.setTranslateX(-size * 0.35);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(center, tooth1, tooth2, tooth3, tooth4);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createFlowScriptIcon(double size, Color color) {
        // Diamond shape representing a flowchart node
        Polygon diamond = new Polygon();
        diamond.getPoints().addAll(new Double[]{
            0.0, -size * 0.4,
            size * 0.4, 0.0,
            0.0, size * 0.4,
            -size * 0.4, 0.0
        });
        diamond.setFill(Color.TRANSPARENT);
        diamond.setStroke(color);
        diamond.setStrokeWidth(1.5);
        
        // "F" for FlowScript
        Text f = new Text("F");
        f.setFill(color);
        f.setFont(Font.font(size * 0.4));
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(diamond, f);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createFolderIcon(double size, Color color) {
        return createOpenFolderIcon(size, color);
    }
    
    private static Node createFileIcon(double size, Color color) {
        Rectangle file = new Rectangle(size * 0.6, size * 0.8);
        file.setFill(Color.TRANSPARENT);
        file.setStroke(color);
        file.setStrokeWidth(1.5);
        
        // Corner fold
        Polygon corner = new Polygon();
        corner.getPoints().addAll(new Double[]{
            size * 0.15, -size * 0.4,
            size * 0.3, -size * 0.4,
            size * 0.3, -size * 0.25
        });
        corner.setFill(Color.TRANSPARENT);
        corner.setStroke(color);
        corner.setStrokeWidth(1);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(file, corner);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createCloseIcon(double size, Color color) {
        // X mark
        Line line1 = new Line(-size * 0.25, -size * 0.25, size * 0.25, size * 0.25);
        Line line2 = new Line(-size * 0.25, size * 0.25, size * 0.25, -size * 0.25);
        line1.setStroke(color);
        line2.setStroke(color);
        line1.setStrokeWidth(2);
        line2.setStrokeWidth(2);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(line1, line2);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createMenuIcon(double size, Color color) {
        // Hamburger menu
        Rectangle line1 = new Rectangle(size * 0.6, size * 0.1);
        Rectangle line2 = new Rectangle(size * 0.6, size * 0.1);
        Rectangle line3 = new Rectangle(size * 0.6, size * 0.1);
        
        line1.setFill(color);
        line2.setFill(color);
        line3.setFill(color);
        
        line1.setTranslateY(-size * 0.2);
        line3.setTranslateY(size * 0.2);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(line1, line2, line3);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createArrowRightIcon(double size, Color color) {
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(new Double[]{
            -size * 0.2, -size * 0.3,
            size * 0.2, 0.0,
            -size * 0.2, size * 0.3
        });
        arrow.setFill(color);
        
        StackPane stack = new StackPane();
        stack.getChildren().add(arrow);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createArrowDownIcon(double size, Color color) {
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(new Double[]{
            -size * 0.3, -size * 0.2,
            0.0, size * 0.2,
            size * 0.3, -size * 0.2
        });
        arrow.setFill(color);
        
        StackPane stack = new StackPane();
        stack.getChildren().add(arrow);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createReplaceIcon(double size, Color color) {
        // Two arrows forming a cycle
        Arc arc1 = new Arc(0, -size * 0.1, size * 0.25, size * 0.25, 0, 180);
        Arc arc2 = new Arc(0, size * 0.1, size * 0.25, size * 0.25, 180, 180);
        
        arc1.setFill(Color.TRANSPARENT);
        arc2.setFill(Color.TRANSPARENT);
        arc1.setStroke(color);
        arc2.setStroke(color);
        arc1.setStrokeWidth(1.5);
        arc2.setStrokeWidth(1.5);
        
        // Arrow heads
        Polygon arrow1 = new Polygon();
        arrow1.getPoints().addAll(new Double[]{
            size * 0.2, -size * 0.1,
            size * 0.3, -size * 0.2,
            size * 0.3, 0.0
        });
        arrow1.setFill(color);
        
        Polygon arrow2 = new Polygon();
        arrow2.getPoints().addAll(new Double[]{
            -size * 0.2, size * 0.1,
            -size * 0.3, size * 0.2,
            -size * 0.3, 0.0
        });
        arrow2.setFill(color);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(arc1, arc2, arrow1, arrow2);
        stack.setPrefSize(size, size);
        return stack;
    }
    
    private static Node createDefaultIcon(double size, Color color) {
        // Question mark
        Text question = new Text("?");
        question.setFill(color);
        question.setFont(Font.font(size));
        
        StackPane stack = new StackPane();
        stack.getChildren().add(question);
        stack.setPrefSize(size, size);
        return stack;
    }
}