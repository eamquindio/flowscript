package com.flowscript.ide.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

public class ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
    private static final String PREF_LAST_PROJECT = "last_project";
    private static final String PROJECT_CONFIG_FILE = ".flowscript-project";
    
    private final Preferences preferences;
    private final ObjectMapper objectMapper;
    private String currentProjectPath;
    private Map<String, Object> projectConfig;
    
    public ProjectService() {
        this.preferences = Preferences.userNodeForPackage(ProjectService.class);
        this.objectMapper = new ObjectMapper();
        this.projectConfig = new HashMap<>();
    }
    
    public void setCurrentProject(String projectPath) {
        this.currentProjectPath = projectPath;
        
        // Save to preferences
        preferences.put(PREF_LAST_PROJECT, projectPath);
        
        // Load project configuration
        loadProjectConfig();
        
        logger.info("Current project set to: {}", projectPath);
    }
    
    public String getCurrentProject() {
        return currentProjectPath;
    }
    
    public void loadLastProject() {
        String lastProject = preferences.get(PREF_LAST_PROJECT, null);
        if (lastProject != null && new File(lastProject).exists()) {
            setCurrentProject(lastProject);
        }
    }
    
    public boolean hasCurrentProject() {
        return currentProjectPath != null && !currentProjectPath.isEmpty();
    }
    
    public void createProject(String projectPath, String projectName) throws IOException {
        File projectDir = new File(projectPath);
        if (!projectDir.exists()) {
            projectDir.mkdirs();
        }
        
        // Create project structure
        createProjectStructure(projectDir);
        
        // Create project configuration
        Map<String, Object> config = new HashMap<>();
        config.put("name", projectName);
        config.put("version", "1.0.0");
        config.put("description", "FlowScript project");
        config.put("created", System.currentTimeMillis());
        config.put("flowscript_version", "1.0.0");
        
        saveProjectConfig(projectDir, config);
        setCurrentProject(projectPath);
        
        logger.info("Created new project: {} at {}", projectName, projectPath);
    }
    
    private void createProjectStructure(File projectDir) throws IOException {
        // Create standard directories
        new File(projectDir, "src").mkdirs();
        new File(projectDir, "src/main").mkdirs();
        new File(projectDir, "src/test").mkdirs();
        new File(projectDir, "docs").mkdirs();
        new File(projectDir, "examples").mkdirs();
        
        // Create example FlowScript file
        Path exampleFile = Paths.get(projectDir.getAbsolutePath(), "src", "main", "ejemplo.flow");
        String exampleContent = """
            # Ejemplo de proceso FlowScript
            
            proceso EjemploProceso {
                inicio -> PrimeraTarea
                
                tarea PrimeraTarea {
                    accion:
                        imprimir("¡Hola desde FlowScript!")
                        mensaje = "Proceso ejecutándose correctamente"
                        ir_a SegundaTarea
                }
                
                tarea SegundaTarea {
                    accion:
                        si mensaje != nulo {
                            imprimir("Mensaje: " + mensaje)
                            ir_a Fin
                        } sino {
                            ir_a FinError
                        }
                }
                
                fin Fin
                fin FinError
            }
            """;
        Files.writeString(exampleFile, exampleContent);
        
        // Create README
        Path readmeFile = Paths.get(projectDir.getAbsolutePath(), "README.md");
        String readmeContent = """
            # Proyecto FlowScript
            
            Este es un proyecto de FlowScript creado con el IDE.
            
            ## Estructura del Proyecto
            
            - `src/main/` - Archivos fuente principales
            - `src/test/` - Archivos de prueba
            - `docs/` - Documentación
            - `examples/` - Ejemplos
            
            ## Ejecutar
            
            Use el IDE de FlowScript para ejecutar los archivos .flow
            """;
        Files.writeString(readmeFile, readmeContent);
    }
    
    private void loadProjectConfig() {
        if (currentProjectPath == null) return;
        
        Path configPath = Paths.get(currentProjectPath, PROJECT_CONFIG_FILE);
        if (Files.exists(configPath)) {
            try {
                String content = Files.readString(configPath);
                projectConfig = objectMapper.readValue(content, Map.class);
                logger.info("Loaded project configuration");
            } catch (IOException e) {
                logger.warn("Failed to load project configuration", e);
                projectConfig = new HashMap<>();
            }
        } else {
            projectConfig = new HashMap<>();
        }
    }
    
    private void saveProjectConfig(File projectDir, Map<String, Object> config) throws IOException {
        Path configPath = Paths.get(projectDir.getAbsolutePath(), PROJECT_CONFIG_FILE);
        String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(config);
        Files.writeString(configPath, content);
    }
    
    public void saveCurrentProjectConfig() {
        if (currentProjectPath == null || projectConfig == null) return;
        
        try {
            saveProjectConfig(new File(currentProjectPath), projectConfig);
            logger.info("Saved project configuration");
        } catch (IOException e) {
            logger.error("Failed to save project configuration", e);
        }
    }
    
    public Map<String, Object> getProjectConfig() {
        return new HashMap<>(projectConfig);
    }
    
    public void updateProjectConfig(String key, Object value) {
        projectConfig.put(key, value);
        projectConfig.put("last_modified", System.currentTimeMillis());
    }
    
    public String getProjectName() {
        return (String) projectConfig.getOrDefault("name", "Unnamed Project");
    }
    
    public String getProjectVersion() {
        return (String) projectConfig.getOrDefault("version", "1.0.0");
    }
    
    public String getProjectDescription() {
        return (String) projectConfig.getOrDefault("description", "");
    }
    
    // Find FlowScript files in project
    public java.util.List<File> findFlowScriptFiles() {
        java.util.List<File> flowFiles = new java.util.ArrayList<>();
        
        if (currentProjectPath != null) {
            try {
                Files.walk(Paths.get(currentProjectPath))
                    .filter(path -> path.toString().endsWith(".flow"))
                    .forEach(path -> flowFiles.add(path.toFile()));
            } catch (IOException e) {
                logger.error("Error scanning for FlowScript files", e);
            }
        }
        
        return flowFiles;
    }
    
    // Get project statistics
    public Map<String, Integer> getProjectStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        
        if (currentProjectPath == null) {
            return stats;
        }
        
        try {
            java.util.concurrent.atomic.AtomicInteger flowFiles = new java.util.concurrent.atomic.AtomicInteger(0);
            java.util.concurrent.atomic.AtomicInteger totalLines = new java.util.concurrent.atomic.AtomicInteger(0);
            java.util.concurrent.atomic.AtomicInteger totalProcesses = new java.util.concurrent.atomic.AtomicInteger(0);
            java.util.concurrent.atomic.AtomicInteger totalFunctions = new java.util.concurrent.atomic.AtomicInteger(0);
            
            Files.walk(Paths.get(currentProjectPath))
                .filter(path -> path.toString().endsWith(".flow"))
                .forEach(path -> {
                    try {
                        flowFiles.incrementAndGet();
                        String content = Files.readString(path);
                        String[] lines = content.split("\n");
                        
                        // Count non-empty, non-comment lines
                        for (String line : lines) {
                            String trimmed = line.trim();
                            if (!trimmed.isEmpty() && !trimmed.startsWith("#")) {
                                totalLines.incrementAndGet();
                            }
                        }
                        
                        // Count processes and functions
                        long processes = content.lines()
                                .filter(line -> line.trim().startsWith("proceso "))
                                .count();
                        long functions = content.lines()
                                .filter(line -> line.trim().startsWith("funcion "))
                                .count();
                        
                        totalProcesses.addAndGet((int) processes);
                        totalFunctions.addAndGet((int) functions);
                        
                    } catch (IOException e) {
                        logger.warn("Failed to read file: {}", path, e);
                    }
                });
            
            stats.put("flowFiles", flowFiles.get());
            stats.put("totalLines", totalLines.get());
            stats.put("totalProcesses", totalProcesses.get());
            stats.put("totalFunctions", totalFunctions.get());
            
        } catch (IOException e) {
            logger.error("Error collecting project statistics", e);
        }
        
        return stats;
    }
    
    // Recent projects management
    public java.util.List<String> getRecentProjects() {
        java.util.List<String> recentProjects = new java.util.ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            String project = preferences.get("recent_project_" + i, null);
            if (project != null && new File(project).exists()) {
                recentProjects.add(project);
            }
        }
        
        return recentProjects;
    }
    
    public void addToRecentProjects(String projectPath) {
        java.util.List<String> recent = getRecentProjects();
        
        // Remove if already exists
        recent.remove(projectPath);
        
        // Add to front
        recent.add(0, projectPath);
        
        // Keep only 10 most recent
        if (recent.size() > 10) {
            recent = recent.subList(0, 10);
        }
        
        // Save back to preferences
        for (int i = 0; i < recent.size(); i++) {
            preferences.put("recent_project_" + i, recent.get(i));
        }
        
        // Clear remaining slots
        for (int i = recent.size(); i < 10; i++) {
            preferences.remove("recent_project_" + i);
        }
    }
    
    public void closeCurrentProject() {
        if (currentProjectPath != null) {
            saveCurrentProjectConfig();
            addToRecentProjects(currentProjectPath);
            currentProjectPath = null;
            projectConfig.clear();
            logger.info("Closed current project");
        }
    }
}