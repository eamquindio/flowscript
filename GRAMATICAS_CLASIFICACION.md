# Clasificación de Gramáticas FlowScript

## Descripción General

FlowScript es un lenguaje dual que combina **modelado de procesos de negocio** (estilo BPMN) con **implementación de funciones** (lógica de negocio). Esta clasificación organiza las 56 gramáticas BNF según su propósito principal dentro de la filosofía del lenguaje.

---

## 🔄 GRAMÁTICAS DE ORQUESTACIÓN DE PROCESOS (BPMN-Style)

**Propósito**: Modelar y ejecutar flujos de trabajo de negocio usando conceptos BPMN.

### Nivel 1: Estructura Principal del Proceso
1. **ProcessDeclaration** - Define un proceso completo con su nombre y cuerpo
2. **ProcessBody** - Contenedor principal de todos los elementos del proceso

### Nivel 2: Elementos Core BPMN
3. **ProcessElement** - Elemento genérico del proceso (start, task, gateway, end)
4. **StartElement** - Punto de inicio único del proceso (`start -> TaskName`)
5. **EndElement** - Punto de terminación del proceso (`end FinName`)

### Nivel 3: Elementos de Trabajo
6. **TaskElement** - Unidad atómica de trabajo (`task TaskName { ... }`)
7. **TaskBody** - Define la acción concreta de una tarea (`action: ...`)

### Nivel 4: Elementos de Control de Flujo
8. **GatewayElement** - Decisión o paralelización (exclusivo o paralelo)
9. **ExclusiveGateway** - Gateway XOR para decisiones (`when ... -> ...`)
10. **ParallelGateway** - Gateway AND para paralelización (`branch -> ...`)

### Nivel 5: Cláusulas de Control de Flujo
11. **WhenClause** - Condición de decisión (`when Expression -> Target`)
12. **ElseClause** - Rama por defecto (`else -> Target`)
13. **ParallelBranch** - Rama paralela (`branch -> Target`)
14. **JoinClause** - Punto de sincronización (`join -> Target`)

### Nivel 6: Navegación entre Elementos
15. **GotoStatement** - Transferencia de control (`go_to TaskName`)

**Total Gramáticas de Orquestación: 15**

---

## 🔧 GRAMÁTICAS DE IMPLEMENTACIÓN DE FUNCIONES

**Propósito**: Implementar la lógica de negocio usando paradigmas de programación tradicional.

### Nivel 1: Estructura de Programa y Declaraciones
1. **Program** - Programa completo (punto de entrada)
2. **Declaration** - Declaración genérica (import, function, process, variable)
3. **ImportDeclaration** - Importación de módulos y JARs
4. **FunctionDeclaration** - Definición de funciones

### Nivel 2: Definición de Tipos y Parámetros
5. **Type** - Sistema de tipos (integer, decimal, boolean, text, list, object, void)
6. **ParameterList** - Lista de parámetros de función
7. **Parameter** - Parámetro individual con tipo

### Nivel 3: Estructura de Control de Ejecución
8. **StatementList** - Lista de statements (base de bloques de código)
9. **Statement** - Statement genérico (if, try, return, etc.)
10. **Block** - Bloque de código delimitado por llaves

### Nivel 4: Statements de Control de Flujo
11. **IfStatement** - Condicional if/else_if/else
12. **TryStatement** - Manejo de excepciones try/catch
13. **ForStatement** - Bucle for-each
14. **ThrowStatement** - Lanzamiento de excepciones
15. **ReturnStatement** - Retorno de funciones

### Nivel 5: Statements Básicos
16. **ExpressionStatement** - Statement que consiste en una expresión
17. **VariableDeclaration** - Declaración y asignación de variables

### Nivel 6: Sistema de Expresiones (Jerarquía de Precedencia)
18. **Expression** - Expresión genérica (punto de entrada)
19. **TernaryExpression** - Operador ternario (?:) - Precedencia 6
20. **LogicalOrExpression** - OR lógico (or) - Precedencia 7
21. **LogicalAndExpression** - AND lógico (and) - Precedencia 8
22. **EqualityExpression** - Igualdad (==, !=) - Precedencia 9
23. **RelationalExpression** - Comparación (<, >, <=, >=) - Precedencia 10
24. **AdditiveExpression** - Suma/Resta (+, -) - Precedencia 11
25. **MultiplicativeExpression** - Multiplicación/División (*, /, %) - Precedencia 12
26. **UnaryExpression** - Operadores unarios (not, -) - Precedencia 13
27. **PostfixExpression** - Acceso/Indexación/Llamadas - Precedencia 14-15
28. **PostfixOperator** - Operadores postfijos (., [], ())
29. **PrimaryExpression** - Expresiones primarias (literales, identificadores)

### Nivel 7: Listas y Argumentos
30. **ArgumentList** - Lista de argumentos para llamadas a función
31. **ExpressionList** - Lista de expresiones para arrays/listas

### Nivel 8: Literales y Valores
32. **Literal** - Literal genérico (números, texto, boolean, null)
33. **IntegerLiteral** - Literales enteros (42, 1_000_000)
34. **DecimalLiteral** - Literales decimales (3.14, 1.23e-5)
35. **BooleanLiteral** - Literales booleanos (true, false)
36. **StringLiteral** - Literales de texto ("Hola\n")
37. **NullLiteral** - Literal null
38. **ObjectLiteral** - Objetos literales ({ clave: valor })
39. **ListLiteral** - Arrays literales ([1, 2, 3])
40. **ObjectMemberList** - Lista de miembros de objeto
41. **ObjectMember** - Miembro individual de objeto

**Total Gramáticas de Funciones: 41**

---

## 📊 RESUMEN DE CLASIFICACIÓN

| Categoría | Cantidad | Propósito Principal |
|-----------|----------|---------------------|
| **Orquestación de Procesos** | 15 (27%) | Modelado de flujos BPMN, coordinación entre tareas |
| **Implementación de Funciones** | 41 (73%) | Lógica de negocio, estructuras de datos, control de flujo |
| **Total** | **56** | **100%** |

---

## 🏗️ ARQUITECTURA DEL PARSER

### Organización en Paquetes

```
com.flowscript.sintactic/
├── process/                    # Gramáticas de Orquestación (15)
│   ├── ProcessDeclarationParser
│   ├── ProcessElementParser
│   ├── GatewayParser
│   └── TaskElementParser
└── functions/                  # Gramáticas de Funciones (41)
    ├── expressions/            # Sistema de Expresiones (18)
    │   ├── ExpressionCoordinator
    │   ├── TernaryExpressionParser
    │   ├── LogicalOrExpressionParser
    │   └── ...
    ├── statements/             # Control de Flujo (10)
    │   ├── parsers/
    │   │   ├── IfStatementParser
    │   │   ├── TryStatementParser
    │   │   └── ...
    │   └── StatementParser
    └── literals/               # Literales y Valores (13)
        ├── ObjectLiteralParser
        ├── ListLiteralParser
        └── ...
```

### Principios de Separación

1. **Separación de Incumbencias**: La orquestación reside en `process`; la lógica en `functions`
2. **Una Clase por Gramática**: Cada regla BNF tiene su propia clase parser y AST node
3. **Delegación Coordinada**: Parsers coordinadores delegan a parsers específicos
4. **Jerarquía de Precedencia**: Expresiones siguen la jerarquía de precedencia estricta

---

## 🎯 FILOSOFÍA DEL LENGUAJE

### Dualidad Fundamental

FlowScript implementa la filosofía de **"Separación de Orquestación e Implementación"**:

- **Procesos** (`process { ... }`) definen **QUÉ** hacer y **CUÁNDO** hacerlo
- **Funciones** (`function(...) { ... }`) definen **CÓMO** hacer cada tarea específica

### Isomorfismo BPMN-Textual

El lenguaje permite representar diagramas BPMN como código textual legible:

```flowscript
process AprobacionFactura {
    start -> ValidarMonto

    task ValidarMonto {
        action: gateway DecisionMonto {
            when entrada.monto > 10000 -> RequiereGerente
            when entrada.monto > 1000 -> RequiereSupervisor
            else -> AprobacionAutomatica
        }
    }

    task AprobacionAutomatica { /* ... */ }
    end FinOK
}
```

Esta dualidad arquitectural se refleja directamente en la organización de las gramáticas y parsers del lenguaje.