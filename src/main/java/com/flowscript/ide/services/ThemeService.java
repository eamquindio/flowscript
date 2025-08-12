package com.flowscript.ide.services;

import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.prefs.Preferences;

public class ThemeService {
    private static final Logger logger = LoggerFactory.getLogger(ThemeService.class);
    private static final String PREF_THEME = "selected_theme";
    
    public enum Theme {
        LIGHT("Light", "/css/light-theme.css"),
        DARK("Dark", "/css/dark-theme.css"),
        MONOKAI("Monokai", "/css/monokai-theme.css");
        
        private final String displayName;
        private final String cssFile;
        
        Theme(String displayName, String cssFile) {
            this.displayName = displayName;
            this.cssFile = cssFile;
        }
        
        public String getDisplayName() { return displayName; }
        public String getCssFile() { return cssFile; }
    }
    
    private final Preferences preferences;
    private Theme currentTheme;
    
    public ThemeService() {
        this.preferences = Preferences.userNodeForPackage(ThemeService.class);
        loadThemePreference();
    }
    
    private void loadThemePreference() {
        String themeName = preferences.get(PREF_THEME, Theme.DARK.name());
        try {
            currentTheme = Theme.valueOf(themeName);
        } catch (IllegalArgumentException e) {
            logger.warn("Unknown theme preference: {}, defaulting to DARK", themeName);
            currentTheme = Theme.DARK;
        }
    }
    
    public void setTheme(Theme theme) {
        this.currentTheme = theme;
        preferences.put(PREF_THEME, theme.name());
        logger.info("Theme changed to: {}", theme.getDisplayName());
    }
    
    public Theme getCurrentTheme() {
        return currentTheme;
    }
    
    public void applyTheme(Scene scene) {
        // Clear existing stylesheets
        scene.getStylesheets().clear();
        
        // Add the base styles
        addStylesheet(scene, "/css/base.css");
        
        // Add syntax highlighting styles
        addStylesheet(scene, "/css/syntax-highlighting.css");
        
        // Add theme-specific styles
        addStylesheet(scene, currentTheme.getCssFile());
        
        logger.info("Applied theme: {}", currentTheme.getDisplayName());
    }
    
    private void addStylesheet(Scene scene, String cssPath) {
        try {
            String css = getClass().getResource(cssPath).toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
            logger.warn("Could not load stylesheet: {}", cssPath, e);
        }
    }
    
    public Theme[] getAvailableThemes() {
        return Theme.values();
    }
}