package com.flowscript.ide.components;

import javafx.scene.Node;
import javafx.scene.shape.SVGPath;

/**
 * Minimal vector icon factory stub.
 */
public class IconFactory {
    public static Node folderIcon() {
        SVGPath svg = new SVGPath();
        svg.setContent("M3 6h5l2 2h7a2 2 0 0 1 2 2v6a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2z");
        return svg;
    }
}

