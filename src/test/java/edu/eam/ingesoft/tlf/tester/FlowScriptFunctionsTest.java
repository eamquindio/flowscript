package edu.eam.ingesoft.tlf.tester;

import org.antlr.v4.runtime.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * Clase de prueba para la gramática FlowScriptFunctions.
 * Prueba el sistema completo de funciones de FlowScript usando reflection
 * y casos de prueba definidos en YAML.
 */
public class FlowScriptFunctionsTest {
    
    // Configuración específica para FlowScript Functions
    private static final String GRAMMAR_NAME = "FlowScriptFunctions";
    private static final String PACKAGE_NAME = "edu.eam.ingesoft.tlf";
    private static final String YAML_FILE = "flowscript-functions.yaml";
    private static final String START_RULE = "functionProgram"; // Regla inicial para testing de funciones
    
    // Contadores para estadísticas
    private static int totalCases = 0;
    private static int validCases = 0;
    private static int invalidCases = 0;
    
    @BeforeAll
    static void initTest() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("INICIANDO PRUEBAS DE GRAMÁTICA: FlowScript Functions");
        System.out.println("=".repeat(80));
        System.out.println("Probando sistema completo de funciones FlowScript");
        System.out.println("Incluye: declaraciones, parámetros, tipos, control de flujo, recursión");
    }
    
    @TestFactory
    Stream<DynamicTest> testFlowScriptFunctions() {
        return createTestsFromYaml();
    }
    
    @AfterAll
    static void printSummary() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RESUMEN DE PRUEBAS - FlowScript Functions");
        System.out.println("=".repeat(80));
        System.out.println("Archivo YAML: " + YAML_FILE);
        System.out.println("Regla inicial: " + START_RULE);
        System.out.println("Gramática: " + GRAMMAR_NAME);
        System.out.println("-".repeat(80));
        System.out.println("Casos de prueba procesados:");
        System.out.println("  • Total: " + totalCases);
        System.out.println("  • Casos que deben ser válidos: " + validCases);
        System.out.println("  • Casos que deben ser inválidos: " + invalidCases);
        System.out.println("-".repeat(80));
        System.out.println("Cobertura de funcionalidades probadas:");
        System.out.println("  ✓ Declaración de funciones");
        System.out.println("  ✓ Parámetros tipados");
        System.out.println("  ✓ Tipos de retorno");
        System.out.println("  ✓ Control de flujo (if, while, for, try-catch)");
        System.out.println("  ✓ Expresiones aritméticas y lógicas");
        System.out.println("  ✓ Estructuras de datos (listas, objetos)");
        System.out.println("  ✓ Recursión");
        System.out.println("  ✓ Manejo de errores");
        System.out.println("  ✓ Casos de error sintáctico");
        System.out.println("=".repeat(80));
    }
    
    /**
     * Crea tests dinámicos desde el archivo YAML de FlowScript Functions.
     */
    protected Stream<DynamicTest> createTestsFromYaml() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream(YAML_FILE);
        
        if (inputStream == null) {
            fail("No se pudo cargar el archivo " + YAML_FILE + 
                 ". Asegúrate de que esté en src/test/resources/");
        }
        
        Map<String, Object> data = yaml.load(inputStream);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> ejemplos = (List<Map<String, Object>>) data.get("ejemplos");
        
        if (ejemplos == null) {
            fail("El archivo YAML debe contener una lista 'ejemplos'");
        }
        
        // Contar casos
        totalCases = ejemplos.size();
        validCases = (int) ejemplos.stream().filter(e -> (boolean) e.get("valido")).count();
        invalidCases = totalCases - validCases;
        
        System.out.println("\nCargados " + totalCases + " casos de prueba desde " + YAML_FILE);
        System.out.println("  • Casos válidos esperados: " + validCases);
        System.out.println("  • Casos inválidos esperados: " + invalidCases);
        System.out.println();
        
        return ejemplos.stream().map(ejemplo -> {
            String nombre = (String) ejemplo.get("nombre");
            boolean valido = (boolean) ejemplo.get("valido");
            String texto = (String) ejemplo.get("texto");
            
            return dynamicTest(nombre, () -> {
                ParseResult result = parseWithReflection(texto);
                
                if (valido) {
                    assertTrue(result.isValid(), 
                        String.format("ESPERADO VÁLIDO: '%s'\n" +
                                     "Texto: %s\n" +
                                     "Errores encontrados: %s", 
                            nombre, 
                            texto.length() > 100 ? texto.substring(0, 100) + "..." : texto,
                            result.getErrors()));
                } else {
                    assertFalse(result.isValid(), 
                        String.format("ESPERADO INVÁLIDO: '%s'\n" +
                                     "Texto: %s\n" +
                                     "El parser debería haber rechazado este código", 
                            nombre,
                            texto.length() > 100 ? texto.substring(0, 100) + "..." : texto));
                }
            });
        });
    }
    
    /**
     * Parsea una entrada usando reflection para cargar dinámicamente el lexer y parser
     * de FlowScript Functions.
     * 
     * @param input Texto a parsear (código FlowScript con funciones)
     * @return Resultado del parsing
     */
    private ParseResult parseWithReflection(String input) {
        ParseResult result = new ParseResult();
        List<String> errors = new ArrayList<>();
        
        try {
            // Cargar clases del lexer y parser
            String lexerClassName = PACKAGE_NAME + "." + GRAMMAR_NAME + "Lexer";
            String parserClassName = PACKAGE_NAME + "." + GRAMMAR_NAME + "Parser";
            
            Class<?> lexerClass = Class.forName(lexerClassName);
            Class<?> parserClass = Class.forName(parserClassName);
            
            // Crear CharStream
            CharStream charStream = CharStreams.fromString(input);
            
            // Crear instancia del lexer
            Constructor<?> lexerConstructor = lexerClass.getConstructor(CharStream.class);
            Lexer lexer = (Lexer) lexerConstructor.newInstance(charStream);
            
            // Configurar error listener para el lexer
            lexer.removeErrorListeners();
            lexer.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer,
                                       Object offendingSymbol,
                                       int line, int charPositionInLine,
                                       String msg,
                                       RecognitionException e) {
                    errors.add(String.format("LEXER ERROR - Línea %d:%d - %s", 
                        line, charPositionInLine, msg));
                }
            });
            
            // Crear CommonTokenStream
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            
            // Crear instancia del parser
            Constructor<?> parserConstructor = parserClass.getConstructor(TokenStream.class);
            Parser parser = (Parser) parserConstructor.newInstance(tokens);
            
            // Configurar error listener para el parser
            parser.removeErrorListeners();
            parser.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer,
                                       Object offendingSymbol,
                                       int line, int charPositionInLine,
                                       String msg,
                                       RecognitionException e) {
                    errors.add(String.format("PARSER ERROR - Línea %d:%d - %s", 
                        line, charPositionInLine, msg));
                }
            });
            
            // Buscar y ejecutar el método de la regla inicial
            Method startRuleMethod = parserClass.getMethod(START_RULE);
            Object parseTree = startRuleMethod.invoke(parser);
            
            // Configurar resultado
            result.setValid(errors.isEmpty());
            result.setErrors(errors);
            result.setParseTree(parseTree);
            result.setGrammarName(GRAMMAR_NAME);
            
        } catch (ClassNotFoundException e) {
            fail(String.format(
                "No se pudieron encontrar las clases del lexer o parser.\n" +
                "Clases esperadas:\n" +
                "  - %s\n" +
                "  - %s\n" +
                "Asegúrate de que:\n" +
                "1. La gramática %s.g4 esté en src/main/tlf/\n" +
                "2. ANTLR haya generado las clases en el paquete %s\n" +
                "3. Las clases estén compiladas",
                GRAMMAR_NAME + "Lexer", 
                GRAMMAR_NAME + "Parser", 
                GRAMMAR_NAME, 
                PACKAGE_NAME));
        } catch (NoSuchMethodException e) {
            fail(String.format(
                "No se encontró el método '%s' en el parser.\n" +
                "Verifica que:\n" +
                "1. La gramática tenga una regla llamada '%s'\n" +
                "2. La regla sea pública (no privada)\n" +
                "3. ANTLR haya generado correctamente la clase del parser",
                START_RULE, START_RULE));
        } catch (Exception e) {
            fail("Error inesperado al parsear con reflection: " + e.getMessage() + 
                 "\nCausa: " + (e.getCause() != null ? e.getCause().getMessage() : "No disponible"));
        }
        
        return result;
    }
    
    /**
     * Clase para almacenar el resultado del parsing de FlowScript Functions
     */
    public static class ParseResult {
        private boolean valid;
        private List<String> errors = new ArrayList<>();
        private Object parseTree;
        private String grammarName;
        
        public boolean isValid() {
            return valid;
        }
        
        public void setValid(boolean valid) {
            this.valid = valid;
        }
        
        public List<String> getErrors() {
            return errors;
        }
        
        public void setErrors(List<String> errors) {
            this.errors = errors;
        }
        
        public Object getParseTree() {
            return parseTree;
        }
        
        public void setParseTree(Object parseTree) {
            this.parseTree = parseTree;
        }
        
        public String getGrammarName() {
            return grammarName;
        }
        
        public void setGrammarName(String grammarName) {
            this.grammarName = grammarName;
        }
        
        @Override
        public String toString() {
            return String.format("ParseResult[grammar=%s, valid=%s, errors=%d]",
                grammarName, valid, errors.size());
        }
    }
}