package edu.eam.ingesoft.tlf.flowscript.gui;

import edu.eam.ingesoft.tlf.flowscript.gui.components.ASTTreePanel;
import edu.eam.ingesoft.tlf.flowscript.gui.components.CodeEditorPanel;
import edu.eam.ingesoft.tlf.flowscript.gui.components.LexemeTablePanel;
import edu.eam.ingesoft.tlf.flowscript.gui.components.SemanticTablesPanel;
import edu.eam.ingesoft.tlf.flowscript.gui.services.CompilerService;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Ventana principal del IDE de FlowScript.
 */
public class MainWindow extends JFrame {
    private final CodeEditorPanel editorPanel;
    private final LexemeTablePanel lexemePanel;
    private final ASTTreePanel astPanel;
    private final SemanticTablesPanel semanticPanel;
    private final CompilerService compilerService;
    private final JLabel statusBar;

    private File currentFile = null;
    private boolean isModified = false;

    public MainWindow() {
        super("FlowScript IDE");

        // Inicializar servicios
        compilerService = new CompilerService();

        // Configurar ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Crear componentes
        editorPanel = new CodeEditorPanel();
        lexemePanel = new LexemeTablePanel();
        astPanel = new ASTTreePanel();
        semanticPanel = new SemanticTablesPanel();
        statusBar = new JLabel(" Listo");
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));

        // Listener para detectar cambios
        editorPanel.getTextArea().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { markModified(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { markModified(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { markModified(); }
        });

        // Crear layout
        createLayout();

        // Crear menú
        createMenuBar();

        // Mostrar ejemplo inicial
        showWelcomeExample();
    }

    private void createLayout() {
        // Panel derecho con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Tabla de Lexemas", lexemePanel);
        tabbedPane.addTab("Árbol AST", astPanel);
        tabbedPane.addTab("Tablas Semánticas", semanticPanel);

        // Split pane principal
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, editorPanel, tabbedPane);
        splitPane.setDividerLocation(600);
        splitPane.setResizeWeight(0.5);

        // Layout principal
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(splitPane, BorderLayout.CENTER);
        getContentPane().add(statusBar, BorderLayout.SOUTH);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menú Archivo
        JMenu fileMenu = new JMenu("Archivo");
        fileMenu.setMnemonic(KeyEvent.VK_A);

        JMenuItem newItem = new JMenuItem("Nuevo");
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        newItem.addActionListener(e -> newFile());
        fileMenu.add(newItem);

        JMenuItem openItem = new JMenuItem("Abrir...");
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        openItem.addActionListener(e -> openFile());
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Guardar");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        saveItem.addActionListener(e -> saveFile());
        fileMenu.add(saveItem);

        JMenuItem saveAsItem = new JMenuItem("Guardar como...");
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
        saveAsItem.addActionListener(e -> saveFileAs());
        fileMenu.add(saveAsItem);

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Salir");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        // Menú Compilar
        JMenu compileMenu = new JMenu("Compilar");
        compileMenu.setMnemonic(KeyEvent.VK_C);

        JMenuItem compileItem = new JMenuItem("Compilar");
        compileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        compileItem.addActionListener(e -> compile());
        compileMenu.add(compileItem);

        JMenuItem tokenizeItem = new JMenuItem("Solo Análisis Léxico");
        tokenizeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
        tokenizeItem.addActionListener(e -> tokenize());
        compileMenu.add(tokenizeItem);

        compileMenu.addSeparator();

        JMenuItem clearItem = new JMenuItem("Limpiar resultados");
        clearItem.addActionListener(e -> clearResults());
        compileMenu.add(clearItem);

        menuBar.add(compileMenu);

        // Menú Ayuda
        JMenu helpMenu = new JMenu("Ayuda");
        helpMenu.setMnemonic(KeyEvent.VK_Y);

        JMenuItem aboutItem = new JMenuItem("Acerca de");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.add(aboutItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void newFile() {
        if (isModified) {
            int result = JOptionPane.showConfirmDialog(
                this,
                "¿Desea guardar los cambios antes de crear un nuevo archivo?",
                "Guardar cambios",
                JOptionPane.YES_NO_CANCEL_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (result == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }

        editorPanel.clear();
        currentFile = null;
        isModified = false;
        updateTitle();
        clearResults();
        setStatus("Nuevo archivo creado");
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("FlowScript Files (*.fls)", "fls"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                String content = Files.readString(file.toPath());
                editorPanel.setText(content);
                currentFile = file;
                isModified = false;
                updateTitle();
                clearResults();
                setStatus("Archivo abierto: " + file.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error al abrir el archivo: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void saveFile() {
        if (currentFile == null) {
            saveFileAs();
        } else {
            try {
                Files.writeString(currentFile.toPath(), editorPanel.getText());
                isModified = false;
                updateTitle();
                setStatus("Archivo guardado: " + currentFile.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar el archivo: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void saveFileAs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("FlowScript Files (*.fls)", "fls"));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            // Agregar extensión si no la tiene
            if (!file.getName().endsWith(".fls")) {
                file = new File(file.getAbsolutePath() + ".fls");
            }

            try {
                Files.writeString(file.toPath(), editorPanel.getText());
                currentFile = file;
                isModified = false;
                updateTitle();
                setStatus("Archivo guardado como: " + file.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar el archivo: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void compile() {
        String sourceCode = editorPanel.getText();

        if (sourceCode.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "No hay código para compilar",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        setStatus("Compilando...");

        boolean success = compilerService.compile(sourceCode);

        if (success) {
            // Actualizar vistas
            lexemePanel.updateTokens(compilerService.getTokens());
            astPanel.updateAST(compilerService.getAST());
            semanticPanel.updateSemanticTables(
                compilerService.getSymbolTable(),
                compilerService.getFunctionTable(),
                compilerService.getProcessTable()
            );

            // Mostrar resumen y métricas
            String summary = compilerService.getSummary();
            String metrics = compilerService.getTimeMetrics();
            String message = summary + "\n\n" + metrics;

            setStatus("Compilación exitosa ✓ - " + compilerService.getLastResult().getTotalTime() + "ms");
            JOptionPane.showMessageDialog(
                this,
                message,
                "Compilación Exitosa",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            // Mostrar error y métricas
            String error = compilerService.getLastError();
            String metrics = compilerService.getTimeMetrics();
            String message = error + "\n" + metrics;

            setStatus("Error de compilación ✗");
            JOptionPane.showMessageDialog(
                this,
                message,
                "Error de Compilación",
                JOptionPane.ERROR_MESSAGE
            );

            // Mostrar tokens si los hay
            if (compilerService.hasTokens()) {
                lexemePanel.updateTokens(compilerService.getTokens());
            }
        }
    }

    private void tokenize() {
        String sourceCode = editorPanel.getText();

        if (sourceCode.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "No hay código para analizar",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        setStatus("Analizando léxicamente...");

        boolean success = compilerService.tokenize(sourceCode);

        if (success) {
            lexemePanel.updateTokens(compilerService.getTokens());

            // Mostrar resumen con métricas
            String summary = compilerService.getSummary();
            String metrics = compilerService.getTimeMetrics();
            String message = summary + "\n\n" + metrics;

            setStatus("Análisis léxico exitoso ✓ - " + compilerService.getLastResult().getLexicalAnalysisTime() + "ms");
            JOptionPane.showMessageDialog(
                this,
                message,
                "Análisis Léxico Exitoso",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            String error = compilerService.getLastError();
            String metrics = compilerService.getTimeMetrics();
            String message = error + "\n" + metrics;

            setStatus("Error léxico ✗");
            JOptionPane.showMessageDialog(
                this,
                message,
                "Error Léxico",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearResults() {
        compilerService.clear();
        lexemePanel.clear();
        astPanel.clear();
        semanticPanel.clear();
        setStatus("Resultados limpiados");
    }

    private void showAbout() {
        String message = """
            FlowScript IDE v1.0

            Compilador completo con:
            • Análisis Léxico (AFD)
            • Análisis Sintáctico (Recursive Descent)
            • Análisis Semántico (Visitor Pattern)
            • Visualización de tokens
            • Visualización de AST
            • Visualización de tablas semánticas

            Desarrollado con Java Swing
            """;

        JOptionPane.showMessageDialog(
            this,
            message,
            "Acerca de FlowScript IDE",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showWelcomeExample() {
        String example = """
            # Ejemplo de FlowScript

            funcion calcular(x: entero, y: entero) -> entero {
                resultado = x + y * 2
                retornar resultado
            }

            proceso ProcesarDatos {
                inicio -> Validar

                tarea Validar {
                    accion:
                        verificar_datos()
                }

                fin Completo
            }
            """;

        editorPanel.setText(example);
        isModified = false;
    }

    private void markModified() {
        if (!isModified) {
            isModified = true;
            updateTitle();
        }
    }

    private void updateTitle() {
        String title = "FlowScript IDE";
        if (currentFile != null) {
            title += " - " + currentFile.getName();
        } else {
            title += " - Sin título";
        }
        if (isModified) {
            title += " *";
        }
        setTitle(title);
    }

    private void setStatus(String message) {
        statusBar.setText(" " + message);
    }

    public static void main(String[] args) {
        // Configurar Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear y mostrar ventana
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}
