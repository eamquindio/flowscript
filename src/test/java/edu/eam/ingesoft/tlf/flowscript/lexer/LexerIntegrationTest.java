package edu.eam.ingesoft.tlf.flowscript.lexer;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de integración para el Lexer completo de FlowScript.
 * Estos tests verifican que el lexer puede analizar programas FlowScript completos
 * y producir la secuencia correcta de tokens.
 */
class LexerIntegrationTest {

    @Test
    void testSimpleFunctionDeclaration() {
        String input = "funcion suma(a: entero, b: entero) -> entero { retornar a + b }";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenizeFiltered();

        // Verificar la secuencia de tokens
        assertTokenSequence(tokens,
                TokenType.FUNCION, TokenType.IDENTIFIER,
                TokenType.LEFT_PAREN, TokenType.IDENTIFIER, TokenType.COLON, TokenType.ENTERO,
                TokenType.COMMA, TokenType.IDENTIFIER, TokenType.COLON, TokenType.ENTERO,
                TokenType.RIGHT_PAREN, TokenType.ARROW, TokenType.ENTERO,
                TokenType.LEFT_BRACE,
                TokenType.RETORNAR, TokenType.IDENTIFIER, TokenType.PLUS, TokenType.IDENTIFIER,
                TokenType.RIGHT_BRACE,
                TokenType.EOF
        );
    }

    @Test
    void testFunctionWithComments() {
        String input = """
                # Funcion que suma dos numeros
                funcion suma(a: entero, b: entero) -> entero {
                    /* Retorna la suma */
                    retornar a + b
                }
                """;
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenizeFiltered();

        // Los comentarios deben ser filtrados
        assertFalse(tokens.stream().anyMatch(t -> t.getType() == TokenType.LINE_COMMENT));
        assertFalse(tokens.stream().anyMatch(t -> t.getType() == TokenType.BLOCK_COMMENT));

        // Debe contener la función
        assertTrue(tokens.stream().anyMatch(t -> t.getType() == TokenType.FUNCION));
        assertTrue(tokens.stream().anyMatch(t -> t.getType() == TokenType.RETORNAR));
    }

    @Test
    void testArithmeticExpression() {
        String input = "resultado = (a + b) * c - d / e % f";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenizeFiltered();

        assertTokenSequence(tokens,
                TokenType.IDENTIFIER, TokenType.ASSIGN,
                TokenType.LEFT_PAREN, TokenType.IDENTIFIER, TokenType.PLUS, TokenType.IDENTIFIER,
                TokenType.RIGHT_PAREN, TokenType.MULTIPLY, TokenType.IDENTIFIER,
                TokenType.MINUS, TokenType.IDENTIFIER, TokenType.DIVIDE, TokenType.IDENTIFIER,
                TokenType.MODULO, TokenType.IDENTIFIER,
                TokenType.EOF
        );
    }

    @Test
    void testConditionalStatement() {
        String input = """
                si (x > 0) {
                    resultado = verdadero
                } sino_si (x == 0) {
                    resultado = nulo
                } sino {
                    resultado = falso
                }
                """;
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenizeFiltered();

        // Verificar keywords de control
        assertTrue(hasToken(tokens, TokenType.SI));
        assertTrue(hasToken(tokens, TokenType.SINO_SI));
        assertTrue(hasToken(tokens, TokenType.SINO));
        assertTrue(hasToken(tokens, TokenType.VERDADERO));
        assertTrue(hasToken(tokens, TokenType.FALSO));
        assertTrue(hasToken(tokens, TokenType.NULO));
    }

    @Test
    void testLogicalOperators() {
        String input = "condicion = no (a > b y c < d o e == f)";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenizeFiltered();

        assertTrue(hasToken(tokens, TokenType.NO));
        assertTrue(hasToken(tokens, TokenType.Y));
        assertTrue(hasToken(tokens, TokenType.O));
        assertTrue(hasToken(tokens, TokenType.GREATER_THAN));
        assertTrue(hasToken(tokens, TokenType.LESS_THAN));
        assertTrue(hasToken(tokens, TokenType.EQUAL));
    }

    @Test
    void testForLoop() {
        String input = """
                para elemento en lista {
                    resultado = elemento + 1
                }
                """;
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenizeFiltered();

        assertTrue(hasToken(tokens, TokenType.PARA));
        assertTrue(hasToken(tokens, TokenType.EN));
    }

    @Test
    void testListAndObjectLiterals() {
        String input = """
                lista = [1, 2, 3]
                objeto = {nombre: "Juan", edad: 25}
                """;
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenizeFiltered();

        assertTrue(hasToken(tokens, TokenType.LEFT_BRACKET));
        assertTrue(hasToken(tokens, TokenType.RIGHT_BRACKET));
        assertTrue(hasToken(tokens, TokenType.LEFT_BRACE));
        assertTrue(hasToken(tokens, TokenType.RIGHT_BRACE));
        assertTrue(hasToken(tokens, TokenType.INTEGER_LITERAL));
        assertTrue(hasToken(tokens, TokenType.STRING_LITERAL));
        assertTrue(hasToken(tokens, TokenType.COLON));
        assertTrue(hasToken(tokens, TokenType.COMMA));
    }

    @Test
    void testMemberAccess() {
        String input = "valor = miObjeto.propiedad[0].metodo()";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenizeFiltered();

        assertTokenSequence(tokens,
                TokenType.IDENTIFIER, TokenType.ASSIGN,
                TokenType.IDENTIFIER, TokenType.DOT, TokenType.IDENTIFIER,
                TokenType.LEFT_BRACKET, TokenType.INTEGER_LITERAL, TokenType.RIGHT_BRACKET,
                TokenType.DOT, TokenType.IDENTIFIER,
                TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN,
                TokenType.EOF
        );
    }

    @Test
    void testProcessDeclaration() {
        String input = """
                proceso MiProceso {
                    inicio -> tarea1

                    tarea tarea1 {
                        accion:
                            x = 5
                            ir_a tarea2
                    }

                    fin tarea2
                }
                """;
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenizeFiltered();

        assertTrue(hasToken(tokens, TokenType.PROCESO));
        assertTrue(hasToken(tokens, TokenType.INICIO));
        assertTrue(hasToken(tokens, TokenType.TAREA));
        assertTrue(hasToken(tokens, TokenType.ACCION));
        assertTrue(hasToken(tokens, TokenType.IR_A));
        assertTrue(hasToken(tokens, TokenType.FIN));
        assertTrue(hasToken(tokens, TokenType.ARROW));
    }

    @Test
    void testDatabaseOperations() {
        String input = """
                resultado = db.consultar("SELECT * FROM usuarios", [])
                filas = db.ejecutar("UPDATE usuarios SET edad = ?", [25])
                """;
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenizeFiltered();

        assertTrue(hasToken(tokens, TokenType.DB));
        assertTrue(hasToken(tokens, TokenType.DOT));
        assertTrue(hasToken(tokens, TokenType.IDENTIFIER)); // consultar, ejecutar
        assertTrue(hasToken(tokens, TokenType.STRING_LITERAL));
    }

    @Test
    void testHttpOperations() {
        String input = """
                respuesta = http.get("https://api.ejemplo.com/datos", {})
                resultado = http.post("https://api.ejemplo.com/crear", {nombre: "Juan"}, {})
                """;
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenizeFiltered();

        assertTrue(hasToken(tokens, TokenType.HTTP));
        assertTrue(hasToken(tokens, TokenType.DOT));
        assertTrue(hasToken(tokens, TokenType.STRING_LITERAL));
    }

    @Test
    void testNumericLiterals() {
        String input = "a = 123 b = 45.67 c = 0 d = 0.0";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenizeFiltered();

        long integerCount = tokens.stream()
                .filter(t -> t.getType() == TokenType.INTEGER_LITERAL)
                .count();
        long decimalCount = tokens.stream()
                .filter(t -> t.getType() == TokenType.DECIMAL_LITERAL)
                .count();

        assertEquals(2, integerCount); // 123 y 0
        assertEquals(2, decimalCount); // 45.67 y 0.0
    }

    @Test
    void testAllOperators() {
        String input = "+ - * / % < > <= >= == != = ->";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenizeFiltered();

        assertTrue(hasToken(tokens, TokenType.PLUS));
        assertTrue(hasToken(tokens, TokenType.MINUS));
        assertTrue(hasToken(tokens, TokenType.MULTIPLY));
        assertTrue(hasToken(tokens, TokenType.DIVIDE));
        assertTrue(hasToken(tokens, TokenType.MODULO));
        assertTrue(hasToken(tokens, TokenType.LESS_THAN));
        assertTrue(hasToken(tokens, TokenType.GREATER_THAN));
        assertTrue(hasToken(tokens, TokenType.LESS_EQUAL));
        assertTrue(hasToken(tokens, TokenType.GREATER_EQUAL));
        assertTrue(hasToken(tokens, TokenType.EQUAL));
        assertTrue(hasToken(tokens, TokenType.NOT_EQUAL));
        assertTrue(hasToken(tokens, TokenType.ASSIGN));
        assertTrue(hasToken(tokens, TokenType.ARROW));
    }

    @Test
    void testAllDelimiters() {
        String input = "( ) { } [ ] , : .";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenizeFiltered();

        assertTrue(hasToken(tokens, TokenType.LEFT_PAREN));
        assertTrue(hasToken(tokens, TokenType.RIGHT_PAREN));
        assertTrue(hasToken(tokens, TokenType.LEFT_BRACE));
        assertTrue(hasToken(tokens, TokenType.RIGHT_BRACE));
        assertTrue(hasToken(tokens, TokenType.LEFT_BRACKET));
        assertTrue(hasToken(tokens, TokenType.RIGHT_BRACKET));
        assertTrue(hasToken(tokens, TokenType.COMMA));
        assertTrue(hasToken(tokens, TokenType.COLON));
        assertTrue(hasToken(tokens, TokenType.DOT));
    }

    @Test
    void testCompleteProgram() {
        String input = """
                # Programa completo de ejemplo

                funcion validar_edad(edad: entero) -> booleano {
                    si (edad >= 18) {
                        retornar verdadero
                    } sino {
                        retornar falso
                    }
                }

                proceso ProcesarUsuario {
                    inicio -> verificar

                    tarea verificar {
                        accion:
                            usuario = db.consultar("SELECT * FROM usuarios WHERE id = ?", [1])
                            es_valido = validar_edad(usuario[0].edad)

                            si (es_valido) {
                                ir_a aprobar
                            } sino {
                                ir_a rechazar
                            }
                    }

                    tarea aprobar {
                        accion:
                            resultado = http.post("https://api.com/aprobar", {id: 1}, {})
                            ir_a FinExito
                    }

                    tarea rechazar {
                        accion:
                            ir_a FinError
                    }

                    fin FinExito
                    fin FinError
                }
                """;

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenizeFiltered();

        // Verificar que el programa se tokeniza sin errores
        assertNotNull(tokens);
        assertTrue(tokens.size() > 50); // El programa debe tener muchos tokens

        // Verificar que termina con EOF
        assertEquals(TokenType.EOF, tokens.get(tokens.size() - 1).getType());

        // Verificar que contiene los elementos principales
        assertTrue(hasToken(tokens, TokenType.FUNCION));
        assertTrue(hasToken(tokens, TokenType.PROCESO));
        assertTrue(hasToken(tokens, TokenType.DB));
        assertTrue(hasToken(tokens, TokenType.HTTP));
        assertTrue(hasToken(tokens, TokenType.SI));
        assertTrue(hasToken(tokens, TokenType.SINO));
        assertTrue(hasToken(tokens, TokenType.RETORNAR));
    }

    @Test
    void testLineAndColumnTracking() {
        String input = """
                funcion test() -> vacio {
                    x = 5
                }
                """;
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        // Verificar que las líneas y columnas se rastrean correctamente
        Token firstToken = tokens.get(0);
        assertEquals(1, firstToken.getLine());
        assertEquals(1, firstToken.getColumn());

        // Buscar el token 'x' que debe estar en la línea 2
        Token xToken = tokens.stream()
                .filter(t -> t.getLexeme().equals("x"))
                .findFirst()
                .orElse(null);
        assertNotNull(xToken);
        assertEquals(2, xToken.getLine());
    }

    @Test
    void testInvalidCharacter() {
        String input = "variable = 5 @ otraVariable";
        Lexer lexer = new Lexer(input);

        // El carácter '@' debe causar un error léxico
        assertThrows(LexerException.class, () -> {
            lexer.tokenize();
        });
    }

    /**
     * Método auxiliar para verificar que una secuencia de tokens coincide con los tipos esperados.
     */
    private void assertTokenSequence(List<Token> tokens, TokenType... expectedTypes) {
        assertEquals(expectedTypes.length, tokens.size(),
                "El número de tokens no coincide");

        for (int i = 0; i < expectedTypes.length; i++) {
            assertEquals(expectedTypes[i], tokens.get(i).getType(),
                    String.format("Token en posición %d no coincide. Esperado: %s, Obtenido: %s",
                            i, expectedTypes[i], tokens.get(i).getType()));
        }
    }

    /**
     * Método auxiliar para verificar si existe un token de un tipo específico.
     */
    private boolean hasToken(List<Token> tokens, TokenType type) {
        return tokens.stream().anyMatch(t -> t.getType() == type);
    }
}
