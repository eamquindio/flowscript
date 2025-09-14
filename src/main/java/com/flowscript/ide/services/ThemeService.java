package com.flowscript.ide.services;

import javafx.scene.Scene;

public class ThemeService {
    public enum Theme {
        DARK("Dark", "/css/dark-theme.css", "/css/syntax-highlighting.css"),
        LIGHT("Light", "/css/light-theme.css", "/css/syntax-highlighting.css"),
        MONOKAI("Monokai", "/css/monokai-theme.css", "/css/syntax-highlighting.css");
        public final String displayName;
        public final String[] stylesheets;
        Theme(String displayName, String... stylesheets) {
            this.displayName = displayName;
            this.stylesheets = stylesheets;
        }
    }

    public void applyTheme(Scene scene, Theme theme) {
        scene.getStylesheets().clear();
        for (String s : theme.stylesheets) {
            String url = getClass().getResource(s).toExternalForm();
            scene.getStylesheets().add(url);
        }
    }
}

