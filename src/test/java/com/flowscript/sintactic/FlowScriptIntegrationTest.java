package com.flowscript.sintactic;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ProgramNode;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.FunctionDeclarationNode;
import com.flowscript.sintactic.ast.process.estructura_principal.ProcessDeclarationNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for complete FlowScript programs.
 * Tests that the entire parsing system works together correctly.
 */
public class FlowScriptIntegrationTest {

    private Parser parser = new Parser();

    @Test
    public void testCompleteFunctionProgram() throws Exception {
        String input = """
            funcion calcular_area(radio: decimal) -> decimal {
                PI = 3.14159
                area = PI * radio * radio
                retornar area
            }

            funcion main() {
                resultado = calcular_area(5.0)
                imprimir(resultado)
            }
            """;

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(2, program.getDeclarations().size());
        assertTrue(program.getDeclarations().get(0) instanceof FunctionDeclarationNode);
        assertTrue(program.getDeclarations().get(1) instanceof FunctionDeclarationNode);
    }

    @Test
    public void testProcessWithGatewaysAndTasks() throws Exception {
        String input = """
            proceso AprobacionPedido {
                inicio -> ValidarMonto

                tarea ValidarMonto {
                    accion:
                        monto = 1500
                        si monto > 1000 {
                            ir_a RequiereAprobacion
                        } sino {
                            ir_a ProcesarDirecto
                        }
                }

                tarea RequiereAprobacion {
                    accion:
                        pedido_id = 123
                        notificar_gerente(pedido_id)
                        ir_a FinPendiente
                }

                tarea ProcesarDirecto {
                    accion:
                        pedido_id = 456
                        procesar_pedido(pedido_id)
                        ir_a FinAprobado
                }

                fin FinPendiente
                fin FinAprobado
            }
            """;

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
        assertTrue(program.getDeclarations().get(0) instanceof ProcessDeclarationNode);
    }

    @Test
    public void testMixedProgramWithImportsAndDeclarations() throws Exception {
        String input = """
            importar "std/http" como http
            importar "std/json" como json

            API_URL = "https://api.ejemplo.com"

            funcion obtener_datos(id: entero) {
                url = API_URL + "/datos/" + id
                respuesta = http.get(url)
                retornar json.parse(respuesta.body)
            }

            proceso ProcesarDatos {
                inicio -> ObtenerDatos

                tarea ObtenerDatos {
                    accion:
                        datos = obtener_datos(42)
                        ir_a Finalizar
                }

                fin Finalizar
            }
            """;

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(5, program.getDeclarations().size());
    }

    @Test
    public void testComplexExpressions() throws Exception {
        String input = """
            funcion calcular_complejo(a: entero, b: entero, c: entero) -> entero {
                resultado = (a + b) * c - (a / b)
                retornar resultado
            }

            funcion con_ternario(x: entero) -> texto {
                mensaje = x > 0 ? "positivo" : (x < 0 ? "negativo" : "cero")
                retornar mensaje
            }

            funcion con_logica(activo: booleano, verificado: booleano) -> booleano {
                retornar activo y verificado o falso
            }
            """;

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(3, program.getDeclarations().size());
    }

    @Test
    public void testErrorHandling() throws Exception {
        String input = """
            funcion operacion_segura(x: decimal, y: decimal) -> decimal {
                intentar {
                    si y == 0 {
                        lanzar { tipo: "ErrorDivision", mensaje: "División por cero" }
                    }
                    resultado = x / y
                    retornar resultado
                }
                capturar (error) {
                    imprimir("Error: " + error.mensaje)
                    retornar 0.0
                }
            }
            """;

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void testForLoops() throws Exception {
        String input = """
            funcion procesar_lista() {
                items = [1, 2, 3, 4, 5]
                contador = 0
                para cada item en items {
                    imprimir(item)
                    contador = contador + 1
                }
                retornar contador
            }
            """;

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void testNestedStructures() throws Exception {
        String input = """
            funcion clasificar_numeros() {
                numeros = [1, -2, 3, 0, -5]
                positivos = []
                negativos = []
                ceros = []

                para cada num en numeros {
                    si num > 0 {
                        positivos = agregar(positivos, num)
                    } sino_si num < 0 {
                        negativos = agregar(negativos, num)
                    } sino {
                        ceros = agregar(ceros, num)
                    }
                }

                retornar {
                    positivos: positivos,
                    negativos: negativos,
                    ceros: ceros
                }
            }
            """;

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void testObjectAndListLiterals() throws Exception {
        String input = """
            funcion crear_usuario(nombre: texto, edad: entero) {
                usuario = {
                    nombre: nombre,
                    edad: edad,
                    activo: verdadero,
                    roles: ["usuario", "lector"],
                    configuracion: {
                        notificaciones: verdadero,
                        idioma: "es"
                    }
                }
                retornar usuario
            }
            """;

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void testPropertyAccessAndMethodCalls() throws Exception {
        String input = """
            funcion procesar_pedido() {
                pedido = obtener_pedido()
                cliente = pedido.cliente
                nombre = cliente.nombre
                email = cliente.contacto.email

                validar = validador.verificar(email)
                si validar {
                    db.pedidos.insertar(pedido)
                    email.enviar(email, "Confirmación")
                }
            }
            """;

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void testECommerceScenario() throws Exception {
        String input = """
            importar "std/http" como http
            importar "std/db" como db

            proceso ProcesarOrden {
                inicio -> ValidarStock

                tarea ValidarStock {
                    accion:
                        items = obtener_items()
                        stock_ok = verdadero

                        para cada item en items {
                            producto = db.get("productos", item.id)
                            si producto.stock < item.cantidad {
                                stock_ok = falso
                            }
                        }

                        si stock_ok {
                            ir_a ProcesarPago
                        } sino {
                            ir_a FinRechazado
                        }
                }

                tarea ProcesarPago {
                    accion:
                        intentar {
                            pago = http.post("https://api.stripe.com/v1/charges", {
                                amount: 100,
                                token: "tok_visa"
                            })

                            si pago.status == "succeeded" {
                                ir_a ActualizarInventario
                            } sino {
                                ir_a FinPagoFallido
                            }
                        }
                        capturar (error) {
                            imprimir("Error procesando pago: " + error.mensaje)
                            ir_a FinPagoFallido
                        }
                }

                tarea ActualizarInventario {
                    accion:
                        items = obtener_items()
                        para cada item en items {
                            db.execute(
                                "UPDATE productos SET stock = stock - ? WHERE id = ?",
                                [item.cantidad, item.id]
                            )
                        }
                        ir_a FinExitoso
                }

                fin FinExitoso
                fin FinRechazado
                fin FinPagoFallido
            }
            """;

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        ProgramNode program = parser.parse(tokens);

        assertNotNull(program);
        assertEquals(3, program.getDeclarations().size());
    }
}
