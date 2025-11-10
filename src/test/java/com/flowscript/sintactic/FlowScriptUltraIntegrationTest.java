package com.flowscript.sintactic;

import com.flowscript.lexer.Lexer;
import com.flowscript.lexer.Token;
import com.flowscript.sintactic.ast.functions.programa_declaraciones.ProgramNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ultra-comprehensive integration test for FlowScript.
 * Tests the ENTIRE language spectrum in realistic scenarios.
 */
public class FlowScriptUltraIntegrationTest {

    private Parser parser = new Parser();

    @Test
    public void test01_DataTypesAndLiterals() throws Exception {
        String code = """
            x = 42
            pi = 3.14159
            is_true = true
            name = "FlowScript"
            nothing = null
            numbers = [1, 2, 3, 4, 5]
            person = { nombre: "Juan", edad: 25 }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(7, program.getDeclarations().size());
    }

    @Test
    public void test02_ArithmeticAndLogicalOperators() throws Exception {
        String code = """
            sum = 10 + 5
            diff = 10 - 5
            prod = 10 * 5
            quot = 10 / 5
            mod = 10 % 3
            lt = 5 < 10
            gt = 10 > 5
            le = 5 <= 10
            ge = 10 >= 5
            eq = 10 == 10
            ne = 10 != 5
            and_result = true and false
            or_result = true or false
            not_result = not false
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(14, program.getDeclarations().size());
    }

    @Test
    public void test03_WhileLoop() throws Exception {
        String code = """
            funcion contar_hasta_diez() {
                i = 0
                mientras i < 10 {
                    i = i + 1
                }
                retornar i
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void test04_ForEachLoop() throws Exception {
        String code = """
            funcion sumar_lista(numeros: lista) -> entero {
                total = 0
                para cada num en numeros {
                    total = total + num
                }
                retornar total
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void test05_ForRangeLoop() throws Exception {
        String code = """
            funcion sumar_rango() -> entero {
                total = 0
                para i from 1 to 10 {
                    total = total + i
                }
                retornar total
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void test06_ForRangeWithStep() throws Exception {
        String code = """
            funcion sumar_pares() -> entero {
                total = 0
                para i from 0 to 100 step 2 {
                    total = total + i
                }
                retornar total
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void test07_BreakStatement() throws Exception {
        String code = """
            funcion encontrar_primero() -> entero {
                para i from 1 to 1000 {
                    si i * i > 100 {
                        break
                    }
                }
                retornar i
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void test08_ContinueStatement() throws Exception {
        String code = """
            funcion sumar_impares() -> entero {
                total = 0
                para i from 1 to 100 {
                    si i % 2 == 0 {
                        continue
                    }
                    total = total + i
                }
                retornar total
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void test09_IfElseIfElse() throws Exception {
        String code = """
            funcion clasificar(x: entero) -> texto {
                si x > 0 {
                    retornar "positivo"
                } sino_si x < 0 {
                    retornar "negativo"
                } sino {
                    retornar "cero"
                }
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void test10_TryCatchThrow() throws Exception {
        String code = """
            funcion dividir_seguro(a: entero, b: entero) -> decimal {
                intentar {
                    si b == 0 {
                        lanzar { tipo: "ErrorDivision", mensaje: "No se puede dividir por cero" }
                    }
                    retornar a / b
                } capturar (e) {
                    retornar 0.0
                }
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void test11_NestedLoops() throws Exception {
        String code = """
            funcion suma_matriz() -> entero {
                total = 0
                para i from 0 to 9 {
                    para j from 0 to 9 {
                        total = total + (i * j)
                    }
                }
                retornar total
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void test12_ComplexFunction() throws Exception {
        String code = """
            funcion busqueda_binaria(arr: lista, objetivo: entero) -> entero {
                izq = 0
                der = 100
                mientras izq <= der {
                    medio = (izq + der) / 2
                    si arr[medio] == objetivo {
                        retornar medio
                    } sino_si arr[medio] < objetivo {
                        izq = medio + 1
                    } sino {
                        der = medio - 1
                    }
                }
                retornar -1
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void test13_FunctionWithAllLoopTypes() throws Exception {
        String code = """
            funcion procesar_datos(datos: lista) -> objeto {
                contador = 0
                suma = 0

                i = 0
                mientras i < 100 {
                    i = i + 1
                    si i % 10 != 0 {
                        continuar
                    }
                    contador = contador + 1
                }

                para cada item en datos {
                    si item < 0 {
                        break
                    }
                    suma = suma + item
                }

                validos = []
                para n from 1 to 50 step 5 {
                    validos = agregar(validos, n)
                }

                retornar { contador: contador, suma: suma, validos: validos }
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void test14_SimpleProcess() throws Exception {
        String code = """
            proceso ProcesoSimple {
                inicio -> Tarea1

                tarea Tarea1 {
                    accion:
                        resultado = 42
                        ir_a Fin
                }

                fin Fin
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void test15_ProcessWithGateway() throws Exception {
        String code = """
            proceso ProcesoDecision {
                inicio -> Verificar

                tarea Verificar {
                    accion:
                        valor_entrada = 10
                        ir_a Decision
                }

                gateway Decision {
                    cuando valor_entrada > 0 -> Positivo
                    sino -> Negativo
                }

                tarea Positivo {
                    accion:
                        resultado = "pos"
                        ir_a Fin
                }

                tarea Negativo {
                    accion:
                        resultado = "neg"
                        ir_a Fin
                }

                fin Fin
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void test16_ProcessWithParallelGateway() throws Exception {
        String code = """
            proceso ProcesoParalelo {
                inicio -> Iniciar

                tarea Iniciar {
                    accion:
                        ir_a Division
                }

                gateway Division paralelo {
                    rama -> Tarea1
                    rama -> Tarea2
                    unir -> Combinar
                }

                tarea Tarea1 {
                    accion:
                        r1 = 1
                        ir_a Combinar
                }

                tarea Tarea2 {
                    accion:
                        r2 = 2
                        ir_a Combinar
                }

                tarea Combinar {
                    accion:
                        ir_a Fin
                }

                fin Fin
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void test17_ImportsAndMultipleFunctions() throws Exception {
        String code = """
            importar "std/math" como math

            funcion sumar(a: entero, b: entero) -> entero {
                retornar a + b
            }

            funcion multiplicar(a: entero, b: entero) -> entero {
                retornar a * b
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(3, program.getDeclarations().size());
    }

    @Test
    public void test18_RecursiveFunction() throws Exception {
        String code = """
            funcion factorial(n: entero) -> entero {
                si n <= 1 {
                    retornar 1
                }
                retornar n * factorial(n - 1)
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void test19_ComplexExpressionsAndPropertyAccess() throws Exception {
        String code = """
            funcion calcular(obj: objeto) -> entero {
                resultado = obj.x + obj.y
                resultado = resultado * obj.data.value
                resultado = resultado / obj.items[0]
                retornar resultado
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertEquals(1, program.getDeclarations().size());
    }

    @Test
    public void test20_UltimateIntegration() throws Exception {
        String code = """
            importar "std/http" como http

            funcion procesar_ordenes(ordenes: lista) -> objeto {
                procesadas = 0
                fallidas = 0

                para cada orden en ordenes {
                    intentar {
                        monto = orden.amount
                        si monto <= 0 {
                            continue
                        }

                        validada = falso
                        reintentos = 0

                        mientras no validada y reintentos < 3 {
                            estado = orden.status
                            si estado == "valido" {
                                validada = verdadero
                            } sino {
                                reintentos = reintentos + 1
                            }
                        }

                        si validada {
                            procesadas = procesadas + 1
                        } sino {
                            fallidas = fallidas + 1
                        }

                    } capturar (e) {
                        fallidas = fallidas + 1
                    }
                }

                para i from 0 to procesadas step 10 {
                    si i > 100 {
                        break
                    }
                }

                retornar { procesadas: procesadas, fallidas: fallidas }
            }

            proceso PipelineOrdenes {
                inicio -> Validar

                tarea Validar {
                    accion:
                        ordenes_entrada = obtener_ordenes()
                        resultado = procesar_ordenes(ordenes_entrada)
                        ir_a Decision
                }

                gateway Decision {
                    cuando resultado.procesadas > 0 -> Exito
                    sino -> Fallo
                }

                tarea Exito {
                    accion:
                        estado = "completado"
                        ir_a Fin
                }

                tarea Fallo {
                    accion:
                        estado = "fallido"
                        ir_a Fin
                }

                fin Fin
            }
            """;

        ProgramNode program = parser.parse(new Lexer(code).tokenize());
        assertNotNull(program);
        assertTrue(program.getDeclarations().size() >= 2);
    }
}
