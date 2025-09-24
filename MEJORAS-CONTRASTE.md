# Mejoras de Contraste en la Tabla de Tokens

## 🎨 Mejoras Visuales Implementadas

### 1. **Texto con Alto Contraste**
- **Fuente blanca (#FFFFFF)** sobre fondos oscuros
- **Font-weight: bold** para elementos importantes (encabezados, keywords, tipos)
- **Tamaños de fuente optimizados** para mejor legibilidad

### 2. **Colores de Categorías Mejorados**
- **Keywords**: Fondo violeta con mayor opacidad (rgba(138, 43, 226, 0.3))
- **Operators**: Fondo naranja con mayor opacidad (rgba(255, 140, 0, 0.3))
- **Literals**: Fondo verde más brillante (rgba(0, 150, 0, 0.3))
- **Identifiers**: Fondo azul claro (rgba(100, 149, 237, 0.3))
- **Comments**: Fondo gris con texto #E0E0E0 e itálica
- **Types**: Fondo magenta (rgba(255, 20, 147, 0.3))
- **Delimiters**: Fondo naranja rojizo (rgba(255, 69, 0, 0.2))

### 3. **Encabezados y Controles**
- **Título**: Texto blanco (#FFFFFF) con font-weight bold
- **Checkboxes**: Texto blanco con font-weight bold
- **Botón Refresh**:
  - Fondo verde (#4CAF50)
  - Texto blanco
  - Hover effect (#45A049)
  - Border radius 3px

### 4. **Status Bar Mejorada**
- **Estados exitosos**: Color verde brillante (#4CAF50)
- **Estados de error**: Color rojo claro (#FF6B6B)
- **Texto en negrita** para mejor legibilidad

### 5. **Tabla Principal**
- **Encabezados**: Fondo gris oscuro (#2D2D30) con texto blanco
- **Filas alternadas**: Diferentes tonos de gris para mejor separación
- **Selección**: Azul oscuro (#094771) con texto blanco
- **Bordes**: Color gris medio (#404040) para definir celdas

### 6. **Archivo CSS Complementario**
- **token-table.css**: Estilos CSS adicionales para refinamiento
- **Clases CSS específicas** para cada categoría de token
- **Estilo de scrollbars** para mantener consistencia visual

## 📋 Características de Accesibilidad

### Contraste WCAG
- **Ratio de contraste mejorado** entre texto y fondo
- **Colores vibrantes** pero no excesivamente brillantes
- **Diferenciación clara** entre categorías

### Legibilidad
- **Fuentes en negrita** para elementos importantes
- **Espaciado óptimo** en celdas (padding: 4px 6px)
- **Tamaños de fuente apropiados** (11px-14px)

### Navegación Visual
- **Colores categoriales consistentes** para rápida identificación
- **Estados hover y selección** claramente diferenciados
- **Indicadores visuales** para estados de la aplicación

## 🛠️ Implementación Técnica

### Estilos Inline
```java
// Ejemplo de estilo mejorado
setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: #4CAF50;");
```

### CSS Externo
```css
.token-table-button:hover {
    -fx-background-color: #45A049;
    -fx-cursor: hand;
}
```

### Row Factory Personalizada
```java
tokenTable.setRowFactory(tv -> {
    TableRow<TokenRow> row = new TableRow<>() {
        // Aplicar colores por categoría con mejor contraste
    };
});
```

## 📊 Antes vs Después

### Antes
- Texto gris sobre fondo oscuro (bajo contraste)
- Colores de categoría muy sutiles
- Difícil distinción entre elementos

### Después
- Texto blanco brillante sobre fondos contrastantes
- Colores de categoría vibrantes pero balanceados
- Clara diferenciación visual entre todos los elementos

## 🎯 Beneficios para el Usuario

1. **Mayor legibilidad** especialmente en sesiones largas de desarrollo
2. **Identificación rápida** de tipos de tokens por color
3. **Menos fatiga visual** gracias al alto contraste
4. **Mejor accesibilidad** para usuarios con diferentes necesidades visuales
5. **Experiencia profesional** con colores consistentes con el tema Monokai

La tabla de tokens ahora ofrece una experiencia visual **profesional y accesible** que facilita el análisis léxico del código FlowScript.