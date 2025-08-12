package com.flowscript.ide.syntax;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlowScriptSyntaxHighlighter {
    
    // FlowScript keywords
    private static final String[] STRUCTURE_KEYWORDS = {
        "proceso", "funcion", "importar", "importar_jar", "como", "retornar"
    };
    
    private static final String[] FLOW_KEYWORDS = {
        "inicio", "fin", "tarea", "gateway", "ir_a", "cuando", "rama", "unir", "sino"
    };
    
    private static final String[] CONTROL_KEYWORDS = {
        "si", "sino_si", "intentar", "capturar", "lanzar"
    };
    
    private static final String[] TYPE_KEYWORDS = {
        "entero", "decimal", "booleano", "texto", "lista", "objeto", "nulo"
    };
    
    private static final String[] VALUE_KEYWORDS = {
        "verdadero", "falso"
    };
    
    private static final String[] OPERATOR_KEYWORDS = {
        "y", "o", "no"
    };
    
    private static final String[] FUTURE_KEYWORDS = {
        "asinc", "esperar", "evento", "clase"
    };
    
    // Combine all keywords
    private static final String STRUCTURE_PATTERN = "\\b(" + String.join("|", STRUCTURE_KEYWORDS) + ")\\b";
    private static final String FLOW_PATTERN = "\\b(" + String.join("|", FLOW_KEYWORDS) + ")\\b";
    private static final String CONTROL_PATTERN = "\\b(" + String.join("|", CONTROL_KEYWORDS) + ")\\b";
    private static final String TYPE_PATTERN = "\\b(" + String.join("|", TYPE_KEYWORDS) + ")\\b";
    private static final String VALUE_PATTERN = "\\b(" + String.join("|", VALUE_KEYWORDS) + ")\\b";
    private static final String OPERATOR_PATTERN = "\\b(" + String.join("|", OPERATOR_KEYWORDS) + ")\\b";
    private static final String FUTURE_PATTERN = "\\b(" + String.join("|", FUTURE_KEYWORDS) + ")\\b";
    
    // Other patterns
    private static final String IDENTIFIER_PATTERN = "\\b[a-zA-Z_][a-zA-Z0-9_]*\\b";
    private static final String NUMBER_PATTERN = "\\b\\d+(\\.\\d+)?([eE][+-]?\\d+)?\\b|\\b\\d+_\\d+\\b";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "#[^\r\n]*|/\\*.*?\\*/";
    private static final String PUNCTUATION_PATTERN = "[{}()\\[\\];,.]";
    private static final String OPERATOR_SYMBOL_PATTERN = "[+\\-*/%=<>!&|]|->|<=|>=|==|!=";
    private static final String BRACE_PATTERN = "[{}]";
    private static final String PAREN_PATTERN = "[()]";
    private static final String BRACKET_PATTERN = "[\\[\\]]";
    
    // Function and process names (after keywords)
    private static final String FUNCTION_NAME_PATTERN = "(?<=\\bfuncion\\s)\\w+";
    private static final String PROCESS_NAME_PATTERN = "(?<=\\bproceso\\s)\\w+";
    private static final String TASK_NAME_PATTERN = "(?<=\\btarea\\s)\\w+";
    private static final String GATEWAY_NAME_PATTERN = "(?<=\\bgateway\\s)\\w+";
    
    private static final Pattern PATTERN = Pattern.compile(
        "(?<STRUCTUREKW>" + STRUCTURE_PATTERN + ")"
        + "|(?<FLOWKW>" + FLOW_PATTERN + ")"
        + "|(?<CONTROLKW>" + CONTROL_PATTERN + ")"
        + "|(?<TYPEKW>" + TYPE_PATTERN + ")"
        + "|(?<VALUEKW>" + VALUE_PATTERN + ")"
        + "|(?<OPERATORKW>" + OPERATOR_PATTERN + ")"
        + "|(?<FUTUREKW>" + FUTURE_PATTERN + ")"
        + "|(?<FUNCTIONNAME>" + FUNCTION_NAME_PATTERN + ")"
        + "|(?<PROCESSNAME>" + PROCESS_NAME_PATTERN + ")"
        + "|(?<TASKNAME>" + TASK_NAME_PATTERN + ")"
        + "|(?<GATEWAYNAME>" + GATEWAY_NAME_PATTERN + ")"
        + "|(?<NUMBER>" + NUMBER_PATTERN + ")"
        + "|(?<STRING>" + STRING_PATTERN + ")"
        + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
        + "|(?<BRACE>" + BRACE_PATTERN + ")"
        + "|(?<PAREN>" + PAREN_PATTERN + ")"
        + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
        + "|(?<OPERATORSYM>" + OPERATOR_SYMBOL_PATTERN + ")"
        + "|(?<PUNCTUATION>" + PUNCTUATION_PATTERN + ")"
        + "|(?<IDENTIFIER>" + IDENTIFIER_PATTERN + ")",
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );
    
    public StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        
        while (matcher.find()) {
            String styleClass = null;
            
            if (matcher.group("STRUCTUREKW") != null) {
                styleClass = "flow-structure-keyword";
            } else if (matcher.group("FLOWKW") != null) {
                styleClass = "flow-flow-keyword";
            } else if (matcher.group("CONTROLKW") != null) {
                styleClass = "flow-control-keyword";
            } else if (matcher.group("TYPEKW") != null) {
                styleClass = "flow-type-keyword";
            } else if (matcher.group("VALUEKW") != null) {
                styleClass = "flow-value-keyword";
            } else if (matcher.group("OPERATORKW") != null) {
                styleClass = "flow-operator-keyword";
            } else if (matcher.group("FUTUREKW") != null) {
                styleClass = "flow-future-keyword";
            } else if (matcher.group("FUNCTIONNAME") != null) {
                styleClass = "flow-function-name";
            } else if (matcher.group("PROCESSNAME") != null) {
                styleClass = "flow-process-name";
            } else if (matcher.group("TASKNAME") != null) {
                styleClass = "flow-task-name";
            } else if (matcher.group("GATEWAYNAME") != null) {
                styleClass = "flow-gateway-name";
            } else if (matcher.group("NUMBER") != null) {
                styleClass = "flow-number";
            } else if (matcher.group("STRING") != null) {
                styleClass = "flow-string";
            } else if (matcher.group("COMMENT") != null) {
                styleClass = "flow-comment";
            } else if (matcher.group("BRACE") != null) {
                styleClass = "flow-brace";
            } else if (matcher.group("PAREN") != null) {
                styleClass = "flow-paren";
            } else if (matcher.group("BRACKET") != null) {
                styleClass = "flow-bracket";
            } else if (matcher.group("OPERATORSYM") != null) {
                styleClass = "flow-operator";
            } else if (matcher.group("PUNCTUATION") != null) {
                styleClass = "flow-punctuation";
            } else if (matcher.group("IDENTIFIER") != null) {
                styleClass = "flow-identifier";
            }
            
            // Add the gap before this match as plain text
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            
            // Add the matched text with its style
            if (styleClass != null) {
                spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            } else {
                spansBuilder.add(Collections.emptyList(), matcher.end() - matcher.start());
            }
            
            lastKwEnd = matcher.end();
        }
        
        // Add any remaining plain text
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        
        return spansBuilder.create();
    }
    
    // Helper method to check if a word is a FlowScript keyword
    public boolean isKeyword(String word) {
        return isStructureKeyword(word) || isFlowKeyword(word) || isControlKeyword(word) ||
               isTypeKeyword(word) || isValueKeyword(word) || isOperatorKeyword(word) ||
               isFutureKeyword(word);
    }
    
    public boolean isStructureKeyword(String word) {
        for (String keyword : STRUCTURE_KEYWORDS) {
            if (keyword.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isFlowKeyword(String word) {
        for (String keyword : FLOW_KEYWORDS) {
            if (keyword.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isControlKeyword(String word) {
        for (String keyword : CONTROL_KEYWORDS) {
            if (keyword.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isTypeKeyword(String word) {
        for (String keyword : TYPE_KEYWORDS) {
            if (keyword.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isValueKeyword(String word) {
        for (String keyword : VALUE_KEYWORDS) {
            if (keyword.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isOperatorKeyword(String word) {
        for (String keyword : OPERATOR_KEYWORDS) {
            if (keyword.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isFutureKeyword(String word) {
        for (String keyword : FUTURE_KEYWORDS) {
            if (keyword.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }
    
    // Get all keywords for auto-completion
    public String[] getAllKeywords() {
        return java.util.stream.Stream.of(
            STRUCTURE_KEYWORDS, FLOW_KEYWORDS, CONTROL_KEYWORDS,
            TYPE_KEYWORDS, VALUE_KEYWORDS, OPERATOR_KEYWORDS, FUTURE_KEYWORDS
        ).flatMap(java.util.Arrays::stream).toArray(String[]::new);
    }
    
    // Get keywords by category
    public String[] getStructureKeywords() { return STRUCTURE_KEYWORDS.clone(); }
    public String[] getFlowKeywords() { return FLOW_KEYWORDS.clone(); }
    public String[] getControlKeywords() { return CONTROL_KEYWORDS.clone(); }
    public String[] getTypeKeywords() { return TYPE_KEYWORDS.clone(); }
    public String[] getValueKeywords() { return VALUE_KEYWORDS.clone(); }
    public String[] getOperatorKeywords() { return OPERATOR_KEYWORDS.clone(); }
    public String[] getFutureKeywords() { return FUTURE_KEYWORDS.clone(); }
}