package com.flowscript.ide.services;

import javafx.scene.Scene;

/**
 * Minimal theme service to switch between CSS styles.
 */
public class ThemeService {
    public enum Theme { DARK, LIGHT, MONOKAI }

    public void apply(Scene scene, Theme theme) {
        scene.getStylesheets().clear();
        switch (theme) {
            case LIGHT -> scene.getStylesheets().add(resource("/css/light-theme.css"));
            case MONOKAI -> scene.getStylesheets().add(resource("/css/monokai-theme.css"));
            default -> scene.getStylesheets().add(resource("/css/dark-theme.css"));
        }
    }

    private String resource(String path) {
        return ThemeService.class.getResource(path).toExternalForm();
    }
}

