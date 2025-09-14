package com.flowscript.ide.components;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class IconFactory {
    public static Node fileIcon() {
        return svg("M6 2h6l4 4v12a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2z M12 2v4h4", 16, Color.GRAY);
    }
    public static Node folderIcon() {
        return svg("M3 6h5l2 2h9a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2z", 16, Color.GOLDENROD);
    }
    public static Node runIcon() {
        return svg("M3 2l18 10L3 22z", 16, Color.LIMEGREEN);
    }
    public static Node debugIcon() {
        return svg("M12 2a10 10 0 1 0 0 20 10 10 0 0 0 0-20zm1 5h-2v6h2V7z", 16, Color.ORANGE);
    }
    public static Node stopIcon() {
        return svg("M6 6h12v12H6z", 16, Color.CRIMSON);
    }

    private static Node svg(String path, double size, Color color) {
        SVGPath p = new SVGPath();
        p.setContent(path);
        p.setFill(color);
        p.setScaleX(size/24.0);
        p.setScaleY(size/24.0);
        return p;
    }
}

