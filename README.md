# FlowScript IDE

Un IDE (Entorno de Desarrollo Integrado) moderno para el lenguaje FlowScript, construido con JavaFX.

## 🚀 Características Principales

### 🎨 Interfaz Moderna
- **Interfaz JavaFX 21**: Interfaz gráfica moderna y responsiva
- **Temas múltiples**: Dark, Light y Monokai con cambio dinámico
- **Iconos vectoriales**: Iconos escalables creados con formas JavaFX
- **Layout flexible**: Paneles redimensionables y pestañas

### 📝 Editor de Código Avanzado
- **Resaltado de sintaxis a color**: Colores específicos para elementos de FlowScript
- **Números de línea**: Numeración automática de líneas
- **Auto-completado**: Sugerencias de palabras clave de FlowScript (`Ctrl+Space`)
- **Indentación inteligente**: Indentación automática para bloques de código
- **Múltiples pestañas**: Edición de múltiples archivos simultáneamente

### 🗂️ Gestión de Proyectos
- **Explorador de proyectos**: Navegación de archivos en árbol
- **Creación de proyectos**: Estructura de proyecto estándar para FlowScript
- **Archivos de configuración**: Gestión de metadatos de proyecto (.flowscript-project)
- **Proyectos recientes**: Acceso rápido a proyectos utilizados recientemente

### 🔧 Herramientas Integradas
- **Consola interactiva**: Ejecución de comandos y visualización de salida
- **Validación de sintaxis**: Verificación de código FlowScript
- **Sistema de logging**: Registro detallado de actividades del IDE
- **Barra de estado**: Información del estado actual del editor

## 🌟 Ejemplo de FlowScript

```flowscript
# Ejemplo de proceso FlowScript con resaltado completo
proceso EjemploProceso {
    inicio -> PrimeraTarea
    
    tarea PrimeraTarea {
        accion:
            imprimir("¡Hola FlowScript!")
            mensaje = "Proceso ejecutándose correctamente"
            ir_a SegundaTarea
    }
    
    tarea SegundaTarea {
        accion:
            si mensaje != nulo {
                imprimir("Mensaje: " + mensaje)
                ir_a Fin
            } sino {
                ir_a FinError
            }
    }
    
    fin Fin
    fin FinError
}
```

## 🛠️ Tecnologías Utilizadas

- **Java 17+**: Plataforma de desarrollo
- **JavaFX 21**: Framework de interfaz gráfica
- **RichTextFX 0.11.2**: Editor de texto avanzado con resaltado de sintaxis
- **ControlsFX 11.2.0**: Controles adicionales para JavaFX
- **Jackson 2.16.0**: Procesamiento JSON para configuración
- **Logback 1.4.14**: Sistema de logging
- **Gradle 8.12**: Sistema de construcción y gestión de dependencias

## 📦 Instalación y Uso

### Prerrequisitos
- Java 17 o superior
- Gradle (wrapper incluido)

### Compilación
```bash
./gradlew build
```

### Ejecución
```bash
./gradlew run
```

### Crear Distribución
```bash
./gradlew distZip
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
└── build.gradle                     # Configuración de construcción
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
- **🔄 Flujo**: `inicio`, `fin`, `tarea`, `gateway`, `ir_a`, `cuando`, `rama`, `unir`, `sino`
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