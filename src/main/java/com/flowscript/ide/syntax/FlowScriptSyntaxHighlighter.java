package com.flowscript.ide.syntax;

import javafx.application.Platform;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlowScriptSyntaxHighlighter {

    // Support English and Spanish keywords
    private static final String[] STRUCTURE = {
            "process", "function", "import", "import_jar", "as", "return",
            "proceso", "funcion", "importar", "importar_jar", "como", "retornar"
    };
    private static final String[] FLOW = {
            "start", "end", "task", "gateway", "go_to", "when", "branch", "join", "else",
            "inicio", "fin", "tarea", "gateway", "go_to", "cuando", "rama", "unir", "sino"
    };
    private static final String[] CONTROL = {
            "if", "else_if", "try", "catch", "throw",
            "si", "sino_si", "intentar", "capturar", "lanzar"
    };
    private static final String[] TYPES = {
            "integer", "decimal", "boolean", "text", "list", "object", "null",
            "entero", "decimal", "booleano", "texto", "lista", "objeto", "nulo"
    };
    private static final String[] BOOLEAN_LITS = {"true", "false", "verdadero", "falso"};

    private static final Pattern STRING = Pattern.compile("\"([^\\\"\\\\]|\\\\.)*\"");
    private static final Pattern NUMBER = Pattern.compile("(?<![\\w.])(?:\\d+\\.\\d+|\\d+)(?![\\w.])");
    private static final Pattern COMMENT_HASH = Pattern.compile("#[^\\n]*");
    private static final Pattern COMMENT_BLOCK = Pattern.compile("/\\*(?s).*?\\*/");

    private final Pattern PATTERN;

    public FlowScriptSyntaxHighlighter(CodeArea area) {
        String structure = kw(STRUCTURE);
        String flow = kw(FLOW);
        String control = kw(CONTROL);
        String types = kw(TYPES);
        String bools = kw(BOOLEAN_LITS);

        PATTERN = Pattern.compile(
                "(?<COMMENTBLOCK>/\\*(?s).*?\\*/)|" +
                "(?<COMMENT>#[^\\n]*$)|" +
                "(?<STRING>\\\"([^\\\\\"\\\\]|\\\\\\\\.)*\\\")|" +
                "(?<NUMBER>(?<![\\\\w.])(?:\\\\d+\\\\.\\\\d+|\\\\d+)(?![\\\\w.]))|" +
                "(?<STRUCT>(?<![A-Za-z_])(?:" + structure + ")(?![A-Za-z_]))|" +
                "(?<FLOW>(?<![A-Za-z_])(?:" + flow + ")(?![A-Za-z_]))|" +
                "(?<CONTROL>(?<![A-Za-z_])(?:" + control + ")(?![A-Za-z_]))|" +
                "(?<TYPE>(?<![A-Za-z_])(?:" + types + ")(?![A-Za-z_]))|" +
                "(?<BOOL>(?<![A-Za-z_])(?:" + bools + ")(?![A-Za-z_]))|" +
                "(?<BRACE>[{}])|(?<PAREN>[()])|(?<BRACKET>[\\\\[\\\\]])",
                Pattern.MULTILINE
        );

        area.setParagraphGraphicFactory(LineNumberFactory.get(area));
        area.multiPlainChanges()
                .successionEnds(Duration.ofMillis(150))
                .subscribe(ignore -> applyHighlighting(area));

        // Initial run
        applyHighlighting(area);
    }

    private static String kw(String[] arr) {
        return String.join("|", Arrays.stream(arr).map(Pattern::quote).toArray(String[]::new));
    }

    private void applyHighlighting(CodeArea area) {
        String text = area.getText();
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass =
                    matcher.group("COMMENTBLOCK") != null ? "comment" :
                    matcher.group("COMMENT") != null ? "comment" :
                    matcher.group("STRING") != null ? "string" :
                    matcher.group("NUMBER") != null ? "number" :
                    matcher.group("STRUCT") != null ? "keyword-structure" :
                    matcher.group("FLOW") != null ? "keyword-flow" :
                    matcher.group("CONTROL") != null ? "keyword-control" :
                    matcher.group("TYPE") != null ? "type" :
                    matcher.group("BOOL") != null ? "bool" :
                    matcher.group("BRACE") != null ? "brace" :
                    matcher.group("PAREN") != null ? "paren" :
                    matcher.group("BRACKET") != null ? "bracket" : null;
            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        StyleSpans<Collection<String>> spans = spansBuilder.create();
        Platform.runLater(() -> area.setStyleSpans(0, spans));
    }
}
