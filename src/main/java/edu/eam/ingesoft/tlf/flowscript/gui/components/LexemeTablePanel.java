package edu.eam.ingesoft.tlf.flowscript.gui.components;

import edu.eam.ingesoft.tlf.flowscript.lexer.Token;
import edu.eam.ingesoft.tlf.flowscript.lexer.TokenType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel que muestra la tabla de lexemas (tokens).
 */
public class LexemeTablePanel extends JPanel {
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JLabel statusLabel;

    public LexemeTablePanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));

        // Modelo de tabla
        String[] columnNames = {"#", "Tipo", "Lexema", "Línea", "Columna"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Tabla
        table = new JTable(tableModel);
        table.setFont(new Font("Monospaced", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Ajustar anchos de columnas
        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // #
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Tipo
        table.getColumnModel().getColumn(2).setPreferredWidth(200); // Lexema
        table.getColumnModel().getColumn(3).setPreferredWidth(60);  // Línea
        table.getColumnModel().getColumn(4).setPreferredWidth(60);  // Columna

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de estado
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("No hay tokens para mostrar");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
    }

    /**
     * Actualiza la tabla con una lista de tokens.
     */
    public void updateTokens(List<Token> tokens) {
        // Limpiar tabla
        tableModel.setRowCount(0);

        if (tokens == null || tokens.isEmpty()) {
            statusLabel.setText("No hay tokens para mostrar");
            return;
        }

        // Agregar tokens a la tabla
        int index = 1;
        int validTokenCount = 0;
        for (Token token : tokens) {
            // Filtrar tokens ignorables para mostrar un resumen limpio
            if (!token.getType().isIgnorable() && token.getType() != TokenType.EOF) {
                Object[] row = {
                    index++,
                    token.getType().name(),
                    formatLexeme(token.getLexeme()),
                    token.getLine(),
                    token.getColumn()
                };
                tableModel.addRow(row);
                validTokenCount++;
            }
        }

        statusLabel.setText(String.format(
            "Total: %d tokens (%d tokens válidos mostrados, %d ignorados)",
            tokens.size(),
            validTokenCount,
            tokens.size() - validTokenCount
        ));
    }

    /**
     * Formatea el lexema para mostrarlo en la tabla.
     */
    private String formatLexeme(String lexeme) {
        if (lexeme == null) return "";

        // Limitar longitud y mostrar caracteres especiales
        String formatted = lexeme
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");

        if (formatted.length() > 50) {
            formatted = formatted.substring(0, 47) + "...";
        }

        return formatted;
    }

    /**
     * Limpia la tabla.
     */
    public void clear() {
        tableModel.setRowCount(0);
        statusLabel.setText("No hay tokens para mostrar");
    }
}
