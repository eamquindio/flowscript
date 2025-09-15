# PR: Team A – FlowScript Functions Grammar (<amcode>)

## Resumen
- Regla inicial: `functionProgram`
- Alcance:
  - Declaración de funciones con parámetros tipados y retorno opcional (`void` o ausente)
  - Statements: `if/else_if/else`, `while`, `for each`, `for from/to/step`, `try/catch+`, `return`, `throw`, `break`, `continue`, bloques y expresiones sueltas
  - Expresiones con precedencia: acceso `.`, indexación `[]`, llamadas `()`, unarios (`not`, `-`), multiplicativos, aditivos, relacionales, igualdad, `and`, `or`, asignación derecha-asociativa
  - Literales: enteros (con `_`), decimales (con exponente), strings con escapes, `true/false/null`, listas y objetos sin coma final

## Archivos Cambiados
- `src/main/tlf/FlowScriptFunctions.g4`
- `src/test/resources/flowscript-functions.yaml` (agregados casos corregidos)
- `tlf/functions-bnf.md` (BNF de referencia)

## Cómo Validar
```
mvn -q clean generate-sources && mvn -q -Dtest="*Functions*" test
```

## Evidencia
- Output de consola (resumen):
  - “Cargados 66 casos … 0 fallos (49 válidos, 17 inválidos)”
- Reporte Surefire:
  - `target/surefire-reports/TEST-edu.eam.ingesoft.tlf.tester.FlowScriptFunctionsTest.xml`

## Notas
- La suite de Processes puede fallar si el Team B aún no genera sus clases (`FlowScriptProcessesLexer/Parser`). Para validar exclusivamente Functions usar el comando de arriba.

## Checklist
- [ ] Grammar compila y genera clases ANTLR
- [ ] 100% tests Functions en verde
- [ ] BNF/diagrama RR adjunto en `tlf/`
- [ ] Descripción del alcance y evidencia de pruebas
