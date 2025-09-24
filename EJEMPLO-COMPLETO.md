# 🚀 FlowScript Ejemplo Completo - Sistema E-Commerce

## 📊 Estadísticas del Ejemplo

- **📄 Líneas de código**: 648 líneas
- **🎯 Funciones**: 8 funciones complejas
- **⚙️ Procesos**: 2 procesos (1 principal + 1 utilitario)
- **📋 Tasks**: 23+ tareas con lógica compleja
- **🔀 Gateways**: 3 gateways (exclusivos y paralelos)
- **🎭 Tokens estimados**: ~2,500+ tokens únicos

## 🏗️ Arquitectura del Sistema

### **Sistema Principal: E-Commerce Order Processing**

#### **📱 Funciones Utilitarias (8 funciones)**
1. `validate_email()` - Validación de formato de email
2. `calculate_tax()` - Cálculo de impuestos con validación
3. `apply_discount()` - Aplicación de descuentos (porcentual/fijo)
4. `calculate_shipping_cost()` - Cálculo de envío basado en peso/distancia
5. `validate_inventory()` - Validación completa de inventario
6. `generate_order_id()` - Generación de IDs únicos de orden
7. `encrypt_payment_data()` - Encriptación de datos de pago
8. `send_notification()` - Sistema de notificaciones por email

#### **🔄 Proceso Principal: ComprehensiveOrderProcessing**

##### **📋 Flujo de Tareas (15+ tareas principales)**
1. **InitializeOrder** - Inicialización y generación de orden
2. **ValidateCustomer** - Validación completa del cliente
3. **ValidateOrderItems** - Validación de items y cálculo de subtotales
4. **CalculatePricing** - Cálculos complejos de precios y descuentos
5. **ProcessPaymentDecision** (Gateway) - Decisión de método de pago
6. **RequireManagerApproval** - Aprobación para órdenes de alto valor
7. **ProcessCreditPayment** - Procesamiento de tarjetas de crédito
8. **ProcessDebitPayment** - Procesamiento de tarjetas débito
9. **ProcessPayPalPayment** - Integración con PayPal
10. **ProcessStandardPayment** - Procesamiento estándar
11. **FinalizeOrder** - Finalización de la orden
12. **PostProcessingTasks** (Gateway Paralelo) - Tareas post-proceso
13. **UpdateCustomerStats** - Actualización de estadísticas del cliente
14. **GenerateReports** - Generación de reportes
15. **TriggerRecommendations** - Activación del motor de recomendaciones

##### **⚠️ Manejo de Errores (4 tareas de error)**
- **CustomerValidationFailed** - Fallos en validación de cliente
- **CreditCheckFailed** - Fallos en verificación de crédito
- **InventoryValidationFailed** - Fallos en validación de inventario
- **PaymentFailed** - Fallos en procesamiento de pagos

##### **🏁 Estados Finales (7 end states)**
- `OrderSuccess` - Orden exitosa
- `OrderCompleteSuccess` - Orden completa con post-procesamiento
- `PendingApproval` - Pendiente de aprobación gerencial
- `OrderFailed` - Orden fallida
- `CustomerStatsUpdated` - Estadísticas actualizadas
- `ReportsGenerated` - Reportes generados
- `RecommendationsTriggered` - Recomendaciones activadas

#### **🔄 Proceso Utilitario: InventoryMonitoring**
- Monitoreo continuo de inventario
- Alertas automáticas de stock bajo
- Notificaciones al equipo de compras

## 🎨 Características del Lenguaje Demostradas

### **📚 Imports y Librerías**
```flowscript
import "std/json" as Json
import "std/db" as Database
import "std/http" as Http
import "std/dates" as Dates
import "std/io" as IO
import_jar "libs/security.jar" as Security
```

### **🧮 Tipos de Datos Complejos**
- **Decimales**: `5.00`, `2.50`, `0.10`, `100.0`
- **Booleanos**: `true`, `false`, expresiones complejas
- **Objetos**: Objetos anidados con múltiples propiedades
- **Listas**: Arrays dinámicos con elementos complejos
- **Strings**: Cadenas de texto con concatenación y interpolación

### **🔧 Operadores Avanzados**
- **Aritméticos**: `+`, `-`, `*`, `/`, `%`, operador ternario `?:`
- **Lógicos**: `and`, `or`, `not`, comparaciones complejas
- **Acceso**: `.`, `[]`, acceso a propiedades anidadas
- **Asignación**: `=` con cálculos complejos

### **🔀 Control de Flujo**
- **Condicionales**: `if`, `else_if`, `else` con múltiples condiciones
- **Loops**: `for each` con objetos complejos
- **Try-Catch**: Manejo robusto de errores con objetos de error
- **Throw**: Lanzamiento de excepciones personalizadas

### **⚙️ Elementos de Proceso**
- **Start/End**: Múltiples puntos de finalización
- **Tasks**: Lógica compleja con múltiples operaciones
- **Gateways**:
  - **Exclusivos**: Decisiones basadas en condiciones múltiples
  - **Paralelos**: Procesamiento concurrente con join
- **Goto**: Navegación entre tareas con lógica condicional

### **🎯 Integración Externa**
- **Base de Datos**: `Database.query()`, `Database.execute()`, `Database.insert()`
- **HTTP**: `Http.post()`, `Http.get()` con headers y payloads
- **JSON**: `Json.stringify()`, parseo de respuestas
- **Criptografía**: Uso de JARs externos para encriptación
- **Fechas**: `Dates.now()`, `Dates.format()`, `Dates.addDays()`

## 📈 Análisis de Tokens Esperados

### **Categorías de Tokens Representadas**

#### **🔑 Keywords (~150+ ocurrencias)**
- `function`, `process`, `task`, `gateway`, `start`, `end`
- `if`, `else_if`, `else`, `try`, `catch`, `throw`
- `for`, `each`, `in`, `while`, `return`, `goto`
- `import`, `import_jar`, `as`

#### **⚙️ Operators (~300+ ocurrencias)**
- `+`, `-`, `*`, `/`, `%`, `=`, `==`, `!=`
- `<`, `>`, `<=`, `>=`, `and`, `or`, `not`
- `->`, `.`, `?`, `:`

#### **📊 Literals (~200+ ocurrencias)**
- **Números**: `5.00`, `2.50`, `0.10`, `1000.0`, `10000`
- **Strings**: URLs, mensajes de error, SQL queries, emails
- **Booleanos**: `true`, `false`, `null`

#### **🏷️ Identifiers (~800+ ocurrencias)**
- Variables complejas: `order_tracking`, `customer_data`, `payment_info`
- Funciones: `validate_email`, `calculate_tax`, `encrypt_payment_data`
- Propiedades: `customer_id`, `total_amount`, `payment_status`

#### **📝 Comments (~50+ líneas)**
- Comentarios explicativos de secciones
- Documentación de lógica compleja
- Headers de sección

#### **🔗 Delimiters (~500+ ocurrencias)**
- `()`, `{}`, `[]`, `,`, `;`, `:`
- Estructuras anidadas complejas

## 🎯 Beneficios para la Demostración

### **Para Desarrolladores**
1. **Sintaxis Completa**: Muestra todas las características del lenguaje
2. **Casos Reales**: Escenarios empresariales auténticos
3. **Best Practices**: Estructura y organización profesional
4. **Error Handling**: Manejo robusto de errores y excepciones

### **Para la Tabla de Tokens**
1. **Variedad Máxima**: Todos los tipos de tokens representados
2. **Volumen Significativo**: ~2,500+ tokens para análisis
3. **Complejidad Real**: Estructuras anidadas y expresiones complejas
4. **Colores Distintivos**: Cada categoría claramente visible

### **Para Testing del Lexer**
1. **Prueba Exhaustiva**: Cobertura completa de características
2. **Edge Cases**: Casos límite y situaciones complejas
3. **Performance**: Testeo con código de tamaño significativo
4. **Precisión**: Validación de tokenización correcta

## 🚀 Experiencia de Usuario

Al abrir este ejemplo en FlowScript IDE, los usuarios verán:

- **📊 Panel izquierdo**: Explorador de archivos con el ejemplo
- **💻 Panel central**: Editor con syntax highlighting completo
- **📋 Panel derecho**: Tabla de tokens con ~2,500+ entradas clasificadas por colores
- **🔍 Estado en tiempo real**: Análisis léxico instantáneo mientras navegan

Este ejemplo convierte la tabla de tokens en una **herramienta de aprendizaje interactiva** y demuestra la potencia completa del analizador léxico de FlowScript.

<function_calls>
<invoke name="BashOutput">
<parameter name="bash_id">8daec2