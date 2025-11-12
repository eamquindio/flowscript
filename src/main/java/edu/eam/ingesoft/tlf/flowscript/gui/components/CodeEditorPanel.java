package edu.eam.ingesoft.tlf.flowscript.gui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Panel que contiene el editor de código con números de línea.
 */
public class CodeEditorPanel extends JPanel {
    private final JTextArea textArea;
    private final JTextArea lineNumberArea;
    private final JScrollPane scrollPane;

    public CodeEditorPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));

        // Área de texto principal
        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setTabSize(4);
        textArea.setLineWrap(false);

        // Área de números de línea
        lineNumberArea = new JTextArea("1");
        lineNumberArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        lineNumberArea.setBackground(new Color(240, 240, 240));
        lineNumberArea.setEditable(false);
        lineNumberArea.setFocusable(false);
        lineNumberArea.setBorder(new EmptyBorder(0, 5, 0, 5));

        // Actualizar números de línea cuando cambia el texto
        textArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateLineNumbers();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateLineNumbers();
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateLineNumbers();
            }
        });

        // Scroll pane
        scrollPane = new JScrollPane(textArea);
        scrollPane.setRowHeaderView(lineNumberArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void updateLineNumbers() {
        String text = textArea.getText();
        int lineCount = text.isEmpty() ? 1 : text.split("\n", -1).length;

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= lineCount; i++) {
            sb.append(i).append("\n");
        }

        lineNumberArea.setText(sb.toString());
    }

    public String getText() {
        return textArea.getText();
    }

    public void setText(String text) {
        textArea.setText(text);
        updateLineNumbers();
    }

    public void clear() {
        textArea.setText("");
        updateLineNumbers();
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
