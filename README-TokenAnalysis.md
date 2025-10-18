# FlowScript Token Analysis Feature

## Características Implementadas

### Panel de Análisis de Tokens
- **Ubicación**: Panel derecho de la GUI, junto al editor de código
- **Actualización**: Automática en tiempo real mientras escribes código
- **Control de visualización**: Puede mostrarse/ocultarse desde el menú View > Token Analysis

### Columnas de Información
1. **Line**: Número de línea donde aparece el token
2. **Col**: Número de columna donde comienza el token
3. **Token Type**: Tipo específico del token según la especificación de FlowScript
4. **Value**: Valor literal del token (truncado si es muy largo)
5. **Category**: Categoría general del token para mejor organización

### Categorías de Tokens con Codificación de Color
- **Keywords** (Violeta): Palabras reservadas como `process`, `function`, `if`, etc.
- **Operators** (Naranja): Operadores como `+`, `-`, `==`, `->`, etc.
- **Literals** (Verde): Valores literales como números, strings, `true`, `false`
- **Identifiers** (Azul claro): Nombres de variables, funciones, procesos
- **Comments** (Gris): Comentarios de línea simple (#) y multilínea (/* */)
- **Delimiters**: Paréntesis, llaves, corchetes, comas, etc.
- **Types**: Tipos de datos como `integer`, `decimal`, `text`, etc.

### Controles Interactivos
- **Auto Update**: Checkbox para activar/desactivar actualización automática
- **Include Comments**: Checkbox para mostrar/ocultar tokens de comentarios
- **Refresh**: Botón para actualizar manualmente la tabla de tokens

### Integración con Validación
- La función "Validate Syntax" (F8) usa el lexer para reportar errores léxicos
- Los errores se muestran en la consola con información de línea y columna
- La tabla de tokens se actualiza incluso con código que contiene errores (muestra tokens válidos hasta el error)

### Manejo de Errores
- **Errores léxicos**: Se muestran en el status de la tabla y en la consola
- **Tokens parciales**: Si hay un error, se muestran los tokens válidos encontrados antes del error
- **Código vacío**: La tabla se limpia y muestra un mensaje apropiado

### Ejemplos de Uso

#### 1. Función Simple
```flowscript
function add(a: integer, b: integer) -> integer {
    return a + b
}
```
**Tokens generados**: `FUNCTION`, `IDENTIFIER(add)`, `LEFT_PAREN`, `IDENTIFIER(a)`, `COLON`, `INTEGER_TYPE`, etc.

#### 2. Proceso con Gateway
```flowscript
process Validation {
    start -> CheckInput

    gateway Decision {
        when condition -> Success
        else -> Failure
    }

    end Success
}
```
**Tokens especiales**: `PROCESS`, `START`, `ARROW`, `GATEWAY`, `WHEN`, `ELSE`, `END`

#### 3. Expresiones Complejas
```flowscript
total = price * (1.0 - discount_rate)
```
**Operadores**: `ASSIGN`, `MULTIPLY`, `MINUS` con precedencia correcta

### Beneficios para el Desarrollo

1. **Aprendizaje**: Ayuda a entender cómo el lexer interpreta el código FlowScript
2. **Depuración**: Identifica rápidamente problemas de tokenización
3. **Verificación**: Confirma que el código se está parseando correctamente
4. **Desarrollo**: Útil para desarrolladores del lenguaje para verificar nuevas características

### Uso del Menú

- **View > Token Analysis**: Muestra/oculta el panel de análisis de tokens
- **Tools > Validate Syntax (F8)**: Ejecuta validación léxica completa
- **File > Open**: Carga archivo y actualiza automáticamente la tabla de tokens

La tabla de tokens se integra perfectamente con el flujo de trabajo del IDE, proporcionando retroalimentación instantánea sobre la estructura léxica del código FlowScript que estás escribiendo.