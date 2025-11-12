# Guía de Usuario - FlowScript IDE

## 🎨 Descripción General

FlowScript IDE es un entorno de desarrollo integrado completo para el lenguaje FlowScript, que incluye:

- **Editor de código** con números de línea
- **Compilador completo** (análisis léxico y sintáctico)
- **Tabla de lexemas** (tokens)
- **Visualización del árbol AST** (Árbol de Sintaxis Abstracta)
- **Gestión de archivos** (abrir, guardar, nuevo)

## 🚀 Inicio Rápido

### Ejecutar la GUI

Hay dos formas de ejecutar la GUI:

#### Opción 1: Script de shell
```bash
./run-gui.sh
```

#### Opción 2: Maven directamente
```bash
mvn exec:java -Dexec.mainClass="edu.eam.ingesoft.tlf.flowscript.gui.MainWindow"
```

#### Opción 3: Desde el código compilado
```bash
mvn clean compile
java -cp target/classes edu.eam.ingesoft.tlf.flowscript.gui.MainWindow
```

## 📋 Interfaz de Usuario

La ventana principal está dividida en dos secciones:

### Panel Izquierdo: Editor de Código
- Área de texto con números de línea
- Soporte para código FlowScript
- Resaltado manual mediante tabulaciones

### Panel Derecho: Resultados
Contiene dos pestañas:

#### 1. **Tabla de Lexemas**
Muestra todos los tokens encontrados durante el análisis léxico:
- **#**: Número de token
- **Tipo**: Tipo de token (KEYWORD, IDENTIFIER, etc.)
- **Lexema**: Texto exacto del token
- **Línea**: Línea donde aparece
- **Columna**: Columna donde comienza

#### 2. **Árbol AST**
Visualización jerárquica del árbol de sintaxis abstracta:
- Declaraciones (funciones, procesos)
- Statements (si, para, retornar, etc.)
- Expresiones (operadores, llamadas a funciones)
- Literales (números, textos, listas, objetos)

## 🎯 Funcionalidades

### Menú Archivo

- **Nuevo** (`Ctrl+N`): Crea un nuevo archivo
- **Abrir** (`Ctrl+O`): Abre un archivo `.fls` existente
- **Guardar** (`Ctrl+S`): Guarda el archivo actual
- **Guardar como** (`Ctrl+Shift+S`): Guarda con un nuevo nombre
- **Salir**: Cierra la aplicación

### Menú Compilar

- **Compilar** (`F5`): Ejecuta análisis léxico y sintáctico completo
  - Muestra tokens en la tabla
  - Muestra AST en el árbol
  - Reporta errores si los hay

- **Solo Análisis Léxico** (`F6`): Ejecuta solo el lexer
  - Útil para verificar tokens sin parsear
  - Muestra solo la tabla de lexemas

- **Limpiar resultados**: Limpia todas las visualizaciones

### Menú Ayuda

- **Acerca de**: Información sobre la aplicación

## 💡 Ejemplos de Uso

### 1. Compilar un Programa Simple

1. Escribe o pega código FlowScript:
```flowscript
funcion suma(a: entero, b: entero) -> entero {
    resultado = a + b
    retornar resultado
}
```

2. Presiona `F5` o selecciona **Compilar → Compilar**

3. Observa:
   - **Tabla de Lexemas**: Tokens identificados (FUNCION, IDENTIFIER, etc.)
   - **Árbol AST**: Estructura del programa con nodos de función, parámetros, statements

### 2. Compilar un Proceso

```flowscript
proceso ProcesarPedido {
    inicio -> ValidarDatos

    tarea ValidarDatos {
        accion:
            verificar_cliente()
            verificar_stock()
    }

    tarea ProcesarPago {
        accion:
            si (monto > 0) {
                cobrar()
            }
    }

    fin Completado
}
```

El árbol AST mostrará:
- Proceso: ProcesarPedido
  - Inicio → ValidarDatos
  - Tarea: ValidarDatos
    - Acciones (llamadas a funciones)
  - Tarea: ProcesarPago
    - Acciones (if statement)
  - Fin: Completado

### 3. Analizar Solo Tokens

Si solo quieres ver los tokens sin validar sintaxis:

1. Escribe código (puede tener errores sintácticos)
2. Presiona `F6` o selecciona **Compilar → Solo Análisis Léxico**
3. La tabla mostrará todos los tokens reconocidos

## 🔍 Interpretación de Resultados

### Tabla de Lexemas

- **Tokens válidos**: Se muestran en la tabla
- **Tokens ignorados**: Whitespace y comentarios se filtran del conteo
- **Estado**: Muestra total de tokens y tokens válidos

Ejemplo:
```
Total: 15 tokens (12 tokens válidos mostrados, 3 ignorados)
```

### Árbol AST

El árbol muestra la estructura jerárquica del programa:

```
Programa
├── Función: suma
│   ├── Parámetros
│   │   ├── a: ENTERO
│   │   └── b: ENTERO
│   ├── Retorna: ENTERO
│   └── Cuerpo
│       ├── Variable: resultado
│       │   └── Valor
│       │       └── Operador: ADD
│       │           ├── Izquierda
│       │           │   └── Identificador: a
│       │           └── Derecha
│       │               └── Identificador: b
│       └── Retornar
│           └── Valor
│               └── Identificador: resultado
```

## ❌ Manejo de Errores

### Errores Léxicos
Si el lexer encuentra un token inválido:
- Se muestra un mensaje de error
- Se indica la línea y columna del error
- La tabla de lexemas puede estar vacía o parcial

### Errores Sintácticos
Si el parser encuentra un error de sintaxis:
- Se muestra un mensaje de error detallado
- La tabla de lexemas se muestra (el análisis léxico fue exitoso)
- El árbol AST está vacío (no se pudo construir)

### Ejemplo de Error
```
Error sintáctico: Unexpected token. Expected: RIGHT_PAREN at line 2, column 15.
Got token: IDENTIFIER 'x'
```

## 💾 Gestión de Archivos

### Extensión de Archivos
- Extensión recomendada: `.fls`
- Al guardar, se añade automáticamente si no está presente

### Estado del Archivo
- `*` en el título indica cambios no guardados
- El título muestra el nombre del archivo actual

## ⌨️ Atajos de Teclado

| Acción | Atajo |
|--------|-------|
| Nuevo archivo | `Ctrl+N` |
| Abrir archivo | `Ctrl+O` |
| Guardar | `Ctrl+S` |
| Guardar como | `Ctrl+Shift+S` |
| Compilar | `F5` |
| Análisis léxico | `F6` |

## 🎨 Características Visuales

- **Fuente monoespaciada** para el editor y tabla
- **Números de línea** automáticos
- **Scroll sincronizado** entre editor y números
- **Barra de estado** con mensajes informativos
- **Look & Feel nativo** del sistema operativo

## 🔧 Requisitos Técnicos

- **Java**: 17 o superior
- **Maven**: 3.6 o superior
- **Sistema operativo**: Windows, macOS, Linux

## 📚 Referencias

Para más información sobre el lenguaje FlowScript:
- Ver `docs/FLOWSCRIPT_SIMPLIFIED_SPEC.md` para la especificación completa
- Ver `docs/LEXER_SPECIFICATION.md` para detalles del análisis léxico
- Ver `docs/PARSER_ARCHITECTURE.md` para detalles del análisis sintáctico

## 🐛 Solución de Problemas

### La GUI no inicia
```bash
# Verificar compilación
mvn clean compile

# Verificar Java
java -version
```

### Errores de visualización
- Asegurarse de tener Java con soporte Swing
- Verificar que no hay problemas con el display en sistemas headless

### Performance lenta
- Para archivos grandes, el análisis puede tomar más tiempo
- El árbol AST puede ser grande para programas complejos

## 📝 Notas Adicionales

- Los cambios no guardados se marcan con `*` en el título
- La aplicación pregunta antes de cerrar con cambios sin guardar
- Los archivos se guardan en UTF-8
- No hay límite de tamaño de archivo, pero archivos muy grandes pueden ser lentos
