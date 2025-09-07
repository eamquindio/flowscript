# FlowScript

FlowScript es un lenguaje de programación orientado a procesos que unifica la modelación y ejecución de flujos de trabajo. Está inspirado en BPMN (Business Process Model and Notation) y proporciona una sintaxis textual isomórfica a los diagramas BPMN.

## Características Principales

- **Lenguaje Dual**: Combina funciones tradicionales con procesos de flujo de trabajo
- **Sintaxis Textual BPMN**: Representación textual de diagramas BPMN
- **IDE Integrado**: Editor visual con syntax highlighting
- **Tipos de Datos Ricos**: Soporte para listas, objetos y tipos primitivos
- **Gateways**: Soporte para flujos exclusivos (XOR) y paralelos (AND)
- **Manejo de Errores**: Sistema robusto de try-catch
- **Modularidad**: Sistema de imports para reutilización de código

## Estructura del Proyecto

```
flowscript/
├── src/
│   ├── main/
│   │   ├── java/                    # Código Java del IDE
│   │   └── tlf/                     # Gramáticas ANTLR
│   │       ├── FlowScriptFunctions.g4
│   │       └── FlowScriptProcesses.g4
│   └── test/
│       ├── java/                    # Tests unitarios
│       └── resources/               # Casos de prueba YAML
├── pom.xml                          # Configuración Maven
└── README.md
```

## Requisitos

- **Java 17+**
- **Maven 3.8+**
- **JavaFX 21+** (incluido en las dependencias)

## 🌟 Ejemplo de FlowScript

```flowscript
# Ejemplo de proceso FlowScript con resaltado completo
proceso EjemploProceso {
    inicio -> PrimeraTarea
    
    tarea PrimeraTarea {
        accion:
            imprimir("¡Hola FlowScript!")
            mensaje = "Proceso ejecutándose correctamente"
            go_to SegundaTarea
    }
    
    tarea SegundaTarea {
        accion:
            si mensaje != nulo {
                imprimir("Mensaje: " + mensaje)
                go_to Fin
            } sino {
                go_to FinError
            }
    }
    
    fin Fin
    fin FinError
}
```

## 🛠️ Tecnologías Utilizadas

## Instalación y Ejecución

### 1. Clonar el repositorio

```bash
git clone <repository-url>
cd flowscript
```

### 2. Generar las clases ANTLR

```bash
# Generar parsers desde las gramáticas
mvn antlr4:antlr4
```

### 3. Compilar el proyecto

```bash
mvn clean compile
```

### 4. Ejecutar tests

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar solo tests de funciones
mvn test -Dtest=FlowScriptFunctionsTest

# Ejecutar solo tests de procesos
mvn test -Dtest=FlowScriptProcessesTest
```

### 5. Ejecutar el IDE

```bash
# Ejecutar la aplicación JavaFX
mvn javafx:run

# O usar exec plugin
mvn exec:java -Dexec.mainClass="com.flowscript.ide.FlowScriptIDE"
```

### 6. Crear JAR ejecutable

```bash
# Crear JAR con todas las dependencias
mvn clean package

# El JAR se generará en target/flowscript-1.0.0-jar-with-dependencies.jar
java -jar target/flowscript-1.0.0-jar-with-dependencies.jar
```

## Comandos Maven Útiles

### Desarrollo de Gramáticas

```bash
# Solo generar parsers ANTLR
mvn antlr4:antlr4

# Limpiar clases generadas
mvn clean

# Generar y compilar
mvn clean compile
```

### Testing

```bash
# Ejecutar tests con más verbosidad
mvn test -X

# Ejecutar tests de una clase específica
mvn test -Dtest=FlowScriptFunctionsTest

# Ejecutar un test específico
mvn test -Dtest=FlowScriptFunctionsTest#testFlowScriptFunctions
```

### Ejecución

```bash
# Ejecutar IDE con debug
mvn exec:java -Dexec.mainClass="com.flowscript.ide.FlowScriptIDE" -X

# Ejecutar con JavaFX plugin
mvn clean javafx:compile javafx:run
```

## 📁 Estructura del Proyecto

```
flowscript/
├── src/main/java/com/flowscript/ide/
│   ├── FlowScriptIDE.java           # Aplicación principal
│   ├── components/                   # Componentes de UI
│   │   ├── CodeEditorPane.java      # Editor de código multi-pestaña
│   │   ├── ProjectExplorer.java     # Explorador de proyectos
│   │   ├── ConsolePane.java         # Panel de consola interactiva
│   │   ├── StatusBar.java           # Barra de estado informativa
│   │   └── IconFactory.java         # Fábrica de iconos vectoriales
│   ├── services/                     # Servicios del sistema
│   │   ├── ThemeService.java        # Gestión de temas visuales
│   │   └── ProjectService.java      # Gestión de proyectos
│   └── syntax/                       # Procesamiento de sintaxis
│       └── FlowScriptSyntaxHighlighter.java
├── src/main/resources/
│   └── css/                         # Hojas de estilo
│       ├── syntax-highlighting.css  # Colores de sintaxis
│       ├── dark-theme.css          # Tema oscuro
│       ├── light-theme.css         # Tema claro
│       └── monokai-theme.css       # Tema Monokai
└── pom.xml                          # Configuración Maven
```

## ⌨️ Atajos de Teclado

| Función | Atajo |
|---------|-------|
| Nuevo archivo FlowScript | `Ctrl+N` |
| Abrir archivo | `Ctrl+O` |
| Guardar archivo | `Ctrl+S` |
| Deshacer/Rehacer | `Ctrl+Z` / `Ctrl+Y` |
| Cortar/Copiar/Pegar | `Ctrl+X` / `Ctrl+C` / `Ctrl+V` |
| Buscar | `Ctrl+F` |
| Reemplazar | `Ctrl+H` |
| Auto-completado | `Ctrl+Space` |
| Ejecutar FlowScript | `F5` |
| Debug FlowScript | `F6` |
| Parar ejecución | `F7` |
| Validar sintaxis | `F8` |
| Formatear código | `Ctrl+Shift+F` |
| Zoom In/Out/Reset | `Ctrl++` / `Ctrl+-` / `Ctrl+0` |

## 🎨 Temas Disponibles

### Tema Dark (Predeterminado)
- Fondo oscuro para mejor experiencia nocturna
- Colores de sintaxis optimizados para contraste

### Tema Light  
- Fondo claro para trabajo diurno
- Colores suaves y profesionales

### Tema Monokai
- Inspirado en el popular tema de Sublime Text
- Colores vibrantes y alta legibilidad

**Cambio de tema**: `View > Theme > [Seleccionar]`

## 🔤 Sintaxis FlowScript Soportada

### Palabras Clave por Categoría
- **🏗️ Estructura**: `proceso`, `funcion`, `importar`, `importar_jar`, `como`, `retornar`
- **🔄 Flujo**: `inicio`, `fin`, `tarea`, `gateway`, `go_to`, `cuando`, `rama`, `unir`, `sino`
- **🎛️ Control**: `si`, `sino_si`, `intentar`, `capturar`, `lanzar`
- **🏷️ Tipos**: `entero`, `decimal`, `booleano`, `texto`, `lista`, `objeto`, `nulo`
- **✅ Valores**: `verdadero`, `falso`
- **➕ Operadores**: `y`, `o`, `no`

### Colores de Resaltado
- **Azul**: Palabras clave de estructura (`proceso`, `funcion`)
- **Púrpura**: Palabras clave de flujo (`inicio`, `tarea`, `fin`)
- **Amarillo**: Palabras clave de control (`si`, `sino`)
- **Verde azulado**: Tipos de datos (`entero`, `booleano`)
- **Verde claro**: Números (`123`, `45.67`)
- **Naranja/Café**: Cadenas de texto (`"texto"`)
- **Verde**: Comentarios (`# comentario`)
- **Dorado**: Llaves `{}`
- **Orquídea**: Paréntesis `()`
- **Azul cielo**: Corchetes `[]`

## 🚀 Funcionalidades Implementadas

### ✅ Completadas
- [x] Interfaz gráfica completa con JavaFX
- [x] Editor de código con resaltado de sintaxis a color
- [x] Sistema de temas (Dark/Light/Monokai) con cambio dinámico
- [x] Iconos vectoriales personalizados en toolbar y menús
- [x] Gestión completa de proyectos FlowScript
- [x] Auto-completado de palabras clave
- [x] Consola interactiva con historial
- [x] Indentación automática inteligente
- [x] Sistema de logging robusto
- [x] Persistencia de preferencias del usuario

### 🔮 Próximas Funcionalidades
- [ ] Implementación del intérprete de FlowScript
- [ ] Depurador visual con breakpoints
- [ ] Exportación/importación BPMN 2.0
- [ ] Análisis estático de código y linting
- [ ] Sistema de extensiones y plugins
- [ ] Integración con Git
- [ ] Búsqueda y reemplazo con regex
- [ ] Refactoring automático

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu funcionalidad (`git checkout -b feature/NuevaFuncionalidad`)
3. Commit tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/NuevaFuncionalidad`)
5. Crea un Pull Request

## 🐛 Solución de Problemas

### El IDE no inicia
- Verificar Java 17+ instalado: `java -version`
- Limpiar y reconstruir: `./gradlew clean build`
- Revisar logs: `logs/flowscript-ide.log`

### Problemas de resaltado de sintaxis
- Verificar que el archivo tenga extensión `.flow`
- Reiniciar la aplicación
- Cambiar de tema y volver al original

### Problemas de rendimiento
- Cerrar pestañas innecesarias
- Limpiar caché: eliminar carpeta `logs/`
- Verificar memoria disponible de Java

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 👨‍💻 Autor

**Carlos Ferrer** - [caferrerbeam](https://github.com/caferrerbeam)

## 🌟 Estado del Proyecto

🚧 **En desarrollo activo** - El IDE está completamente funcional y en constante mejora.

### Versión Actual: 1.0.0
- ✅ IDE completamente funcional
- ✅ Resaltado de sintaxis a color
- ✅ Cambio de temas dinámico
- ✅ Gestión completa de proyectos
- ✅ Iconos vectoriales integrados

---

**FlowScript IDE** - Desarrolla flujos de trabajo con poder y elegancia 🚀