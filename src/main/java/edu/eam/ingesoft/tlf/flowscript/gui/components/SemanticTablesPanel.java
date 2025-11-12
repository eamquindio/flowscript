package edu.eam.ingesoft.tlf.flowscript.gui.components;

import edu.eam.ingesoft.tlf.flowscript.semantic.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

/**
 * Panel que muestra las tablas semánticas (SymbolTable, FunctionTable, ProcessTable).
 */
public class SemanticTablesPanel extends JPanel {
    private final JTabbedPane tabbedPane;
    private final JTable symbolTable;
    private final DefaultTableModel symbolTableModel;
    private final JTable functionTable;
    private final DefaultTableModel functionTableModel;
    private final JTable processTable;
    private final DefaultTableModel processTableModel;
    private final JTable taskTable;
    private final DefaultTableModel taskTableModel;
    private final JLabel statusLabel;

    public SemanticTablesPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));

        // Crear pestañas internas
        tabbedPane = new JTabbedPane();

        // ========== Tabla de Símbolos (Variables) ==========
        String[] symbolColumns = {"Nombre", "Tipo", "Scope", "Inicializada", "Usada", "Línea"};
        symbolTableModel = new DefaultTableModel(symbolColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        symbolTable = createStyledTable(symbolTableModel);
        tabbedPane.addTab("Variables", new JScrollPane(symbolTable));

        // ========== Tabla de Funciones ==========
        String[] functionColumns = {"Nombre", "Tipo Retorno", "Parámetros", "Línea"};
        functionTableModel = new DefaultTableModel(functionColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        functionTable = createStyledTable(functionTableModel);
        tabbedPane.addTab("Funciones", new JScrollPane(functionTable));

        // ========== Tabla de Procesos ==========
        String[] processColumns = {"Nombre", "Tareas", "Tiene Inicio", "Tiene Fin", "Línea"};
        processTableModel = new DefaultTableModel(processColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        processTable = createStyledTable(processTableModel);
        tabbedPane.addTab("Procesos", new JScrollPane(processTable));

        // ========== Tabla de Tareas ==========
        String[] taskColumns = {"Proceso", "Nombre", "Tipo", "Referenciada", "Línea"};
        taskTableModel = new DefaultTableModel(taskColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        taskTable = createStyledTable(taskTableModel);
        tabbedPane.addTab("Tareas", new JScrollPane(taskTable));

        add(tabbedPane, BorderLayout.CENTER);

        // Panel de estado
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("No hay información semántica para mostrar");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
    }

    /**
     * Crea una tabla con estilo consistente.
     */
    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Monospaced", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        return table;
    }

    /**
     * Actualiza todas las tablas semánticas.
     */
    public void updateSemanticTables(SymbolTable symbolTable, FunctionTable functionTable, ProcessTable processTable) {
        clearAll();

        if (symbolTable == null && functionTable == null && processTable == null) {
            statusLabel.setText("No hay información semántica para mostrar");
            return;
        }

        int totalSymbols = 0;
        int totalFunctions = 0;
        int totalProcesses = 0;
        int totalTasks = 0;

        // Actualizar tabla de símbolos
        if (symbolTable != null) {
            totalSymbols = updateSymbolTable(symbolTable);
        }

        // Actualizar tabla de funciones
        if (functionTable != null) {
            totalFunctions = updateFunctionTable(functionTable);
        }

        // Actualizar tabla de procesos y tareas
        if (processTable != null) {
            totalProcesses = updateProcessTable(processTable);
            totalTasks = updateTaskTable(processTable);
        }

        // Actualizar estado
        statusLabel.setText(String.format(
            "Símbolos: %d | Funciones: %d | Procesos: %d | Tareas: %d",
            totalSymbols, totalFunctions, totalProcesses, totalTasks
        ));
    }

    /**
     * Actualiza la tabla de símbolos (variables).
     */
    private int updateSymbolTable(SymbolTable table) {
        int count = 0;

        // Obtener todos los símbolos recursivamente
        for (Symbol symbol : table.getAllSymbols()) {
            Object[] row = {
                symbol.getName(),
                symbol.getType(),
                symbol.getScope(),
                symbol.isInitialized() ? "Sí" : "No",
                symbol.isUsed() ? "Sí" : "No",
                symbol.getLine()
            };
            symbolTableModel.addRow(row);
            count++;
        }

        return count;
    }

    /**
     * Actualiza la tabla de funciones.
     */
    private int updateFunctionTable(FunctionTable table) {
        int count = 0;

        for (FunctionSymbol function : table.getFunctions()) {
            // Construir string de parámetros
            StringBuilder params = new StringBuilder();
            java.util.List<FunctionSymbol.Parameter> parameters = function.getParameters();
            for (int i = 0; i < parameters.size(); i++) {
                FunctionSymbol.Parameter param = parameters.get(i);
                if (i > 0) params.append(", ");
                params.append(param.getName()).append(": ").append(param.getType());
            }
            if (params.length() == 0) params.append("sin parámetros");

            Object[] row = {
                function.getName(),
                function.getReturnType() != null ? function.getReturnType() : "void",
                params.toString(),
                function.getLine()
            };
            functionTableModel.addRow(row);
            count++;
        }

        return count;
    }

    /**
     * Actualiza la tabla de procesos.
     */
    private int updateProcessTable(ProcessTable table) {
        int count = 0;

        for (ProcessSymbol process : table.getProcesses()) {
            TaskTable taskTable = process.getTaskTable();
            int taskCount = taskTable != null ? taskTable.getTasks().size() : 0;

            Object[] row = {
                process.getName(),
                taskCount,
                process.hasStart() ? "Sí" : "No",
                process.hasEnd() ? "Sí" : "No",
                process.getLine()
            };
            processTableModel.addRow(row);
            count++;
        }

        return count;
    }

    /**
     * Actualiza la tabla de tareas.
     */
    private int updateTaskTable(ProcessTable processTable) {
        int count = 0;

        for (ProcessSymbol process : processTable.getProcesses()) {
            TaskTable taskTable = process.getTaskTable();
            if (taskTable != null) {
                for (TaskSymbol task : taskTable.getTasks()) {
                    Object[] row = {
                        process.getName(),
                        task.getName(),
                        task.getTaskType().toString(),
                        task.isReferenced() ? "Sí" : "No",
                        task.getLine()
                    };
                    taskTableModel.addRow(row);
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Limpia todas las tablas.
     */
    public void clearAll() {
        symbolTableModel.setRowCount(0);
        functionTableModel.setRowCount(0);
        processTableModel.setRowCount(0);
        taskTableModel.setRowCount(0);
    }

    /**
     * Limpia y resetea el panel.
     */
    public void clear() {
        clearAll();
        statusLabel.setText("No hay información semántica para mostrar");
    }
}
