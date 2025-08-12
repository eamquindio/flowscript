# FlowScript - Especificación del Lenguaje

## Descripción General
FlowScript es un lenguaje de programación orientado a procesos que unifica la modelación y ejecución de flujos de trabajo. Está inspirado en BPMN (Business Process Model and Notation) y proporciona una sintaxis textual isomórfica a los diagramas BPMN.

## Filosofía y Principios de Diseño

### Principios Clave
- **Legibilidad unificada**: Un único archivo `.flow` guarda estructura y lógica
- **Separación de incumbencias**: La orquestación reside en procesos; la lógica en funciones
- **Seguridad y previsibilidad**: No hay `goto` arbitrarios; `ir_a` solo enlaza nodos definidos

## Estructura Léxica y Sintaxis

### Comentarios
```flowscript
# Comentario de una línea
/* Comentario
   multilínea */
```

### Identificadores
- Regla: `[a-zA-Z_][a-zA-Z0-9_]*`
- Sensibles a mayúsculas/minúsculas
- Válidos: `cliente_id`, `_temp`, `Paso_3_Validacion`
- Inválidos: `3pasos`, `total-ventas`, `mi variable`

### Palabras Reservadas

| Categoría | Palabras |
|-----------|----------|
| Estructura | `proceso`, `funcion`, `importar`, `importar_jar`, `como`, `retornar` |
| Flujo | `inicio`, `fin`, `tarea`, `gateway`, `ir_a`, `cuando`, `rama`, `unir`, `sino` |
| Control | `si`, `sino_si`, `intentar`, `capturar`, `lanzar` |
| Tipos/Valores | `entero`, `decimal`, `booleano`, `texto`, `lista`, `objeto`, `nulo`, `verdadero`, `falso` |
| Operadores | `y`, `o`, `no` |
| Futuras | `asinc`, `esperar`, `evento`, `clase` |

### Tipos de Datos Fundamentales

| Tipo | Descripción | Ejemplo |
|------|-------------|---------|
| `entero` | 64 bits sin fracción | `42`, `1_000_000` |
| `decimal` | Doble precisión | `3.14`, `1.23e-5` |
| `booleano` | verdadero/falso | `verdadero`, `falso` |
| `texto` | Cadena Unicode | `"Hola\n"` |
| `lista` | Colección ordenada | `[1, "manzana"]` |
| `objeto` | Pares clave-valor | `{ nombre: "Ana" }` |
| `nulo` | Ausencia intencional | `nulo` |

### Operadores (por precedencia)

| Precedencia | Operador | Significado | Asociatividad |
|-------------|----------|-------------|---------------|
| 15 | `. []` | Acceso/indexación | Izq. |
| 14 | `()` | Llamada a función | Izq. |
| 13 | `no` | Negación lógica | Der. |
| 12 | `* / %` | Aritmética | Izq. |
| 11 | `+ -` | Aritmética | Izq. |
| 10 | `< > <= >=` | Comparación | Izq. |
| 9 | `== !=` | Igualdad | Izq. |
| 8 | `y` | AND lógico | Izq. |
| 7 | `o` | OR lógico | Izq. |
| 1 | `=` | Asignación | Der. |

## Funciones

### Definición y Llamada
```flowscript
funcion calcular_impuesto(monto: decimal, tasa: decimal) -> decimal {
    si monto <= 0 { retornar 0.0 }
    retornar monto * tasa
}

total = calcular_impuesto(500.0, 0.19)
```

- `funcion` define, `retornar` devuelve
- `-> tipo` se omite o usa `-> vacio` si no retorna

### Recursión
```flowscript
funcion factorial(n: entero) -> entero {
    si n <= 1 { retornar 1 }
    retornar n * factorial(n - 1)
}
```

## Procesos: Orquestación del Flujo

### Estructura de un Proceso

#### Elementos Principales
- **`proceso <NombreProceso>`**: Define el contenedor principal
- **`entrada`**: Objeto con los datos iniciales del proceso
- **`inicio`**: Punto de entrada único, debe apuntar a un primer nodo
- **`tarea <NombreTarea>`**: Unidad de trabajo atómica con `accion:`
- **`fin <NombreFin>`**: Punto de terminación (puede haber múltiples)
- **`ir_a <NodoDestino>`**: Transferir control a otro nodo

### Estructura Básica
```flowscript
proceso GestionCliente {
    inicio -> CargarCliente
    
    tarea CargarCliente {
        accion: cliente = db_get("clientes", entrada.id)
        ir_a ValidarActivo
    }
    
    tarea ValidarActivo {
        accion:
            si cliente.activo {
                ir_a EnviarCorreo
            } sino {
                ir_a FinInactivo
            }
    }
    
    tarea EnviarCorreo { /* ... */ }
    
    fin FinInactivo
}
```

### Objeto Contexto
Cada instancia de proceso tiene un contexto implícito que almacena el estado. Las variables declaradas en `accion` de una tarea se guardan en este contexto y están disponibles para tareas posteriores.

## Gateways: Decisión y Paralelismo

### Gateway Exclusivo (XOR)
Solo un camino de salida es elegido. Las condiciones se evalúan en orden:

```flowscript
proceso AprobacionFactura {
    inicio -> ClasificarMonto
    
    tarea ClasificarMonto {
        accion: gateway DecisionMonto {
            cuando entrada.monto > 10000 -> RequiereAprobacionGerente
            cuando entrada.monto > 1000 -> RequiereAprobacionSupervisor
            sino -> AprobacionAutomatica  # camino por defecto
        }
    }
    
    tarea AprobacionAutomatica { 
        accion: imprimir("Aprobada automáticamente")
        ir_a FinOK 
    }
    
    fin FinOK
}
```

### Gateway Paralelo (AND)
Divide el flujo en múltiples ramas concurrentes. Requiere `unir` para sincronizar:

```flowscript
proceso VerificacionAntecedentes {
    inicio -> Iniciar
    
    gateway Iniciar paralelo {
        rama -> VerificarCredito
        rama -> VerificarPenal
        unir -> Consolidar
    }
    
    tarea VerificarCredito {
        accion: reporte_credito = http.get("api/credito/" + entrada.cedula)
        ir_a FinCredito
    }
    
    fin FinCredito
    
    tarea VerificarPenal {
        accion: reporte_penal = http.get("api/penal/" + entrada.cedula)
        ir_a FinPenal
    }
    
    fin FinPenal
    
    tarea Consolidar {
        accion:
            si reporte_credito.aprobado y reporte_penal.limpio {
                imprimir("Todo en orden")
                ir_a Exito
            } sino { 
                ir_a Falla 
            }
    }
    
    fin Exito
    fin Falla
}
```

## Manejo de Errores

### Bloques intentar...capturar
```flowscript
funcion dividir_seguro(a, b) -> decimal {
    intentar {
        si b == 0 {
            lanzar { tipo: "ErrorMatematico", mensaje: "División por cero" }
        }
        retornar a / b
    }
    capturar (e) {
        imprimir("Error: " + e.mensaje)
        retornar 0.0
    }
}

tarea ProcesarPago {
    accion: intento = http.post("api/pagos", entrada.pago)
    
    intentar {
        confirmacion = http.post("api/confirmar", { id: intento.id })
        ir_a FinOK
    }
    capturar (ex) {
        imprimir("Falló confirmación: " + ex.mensaje)
        ir_a ReversarPago
    }
}
```

## Modularidad e Interoperabilidad

### Módulos .flow
**utilidades.flow:**
```flowscript
funcion es_par(n: entero) -> booleano { retornar n % 2 == 0 }
PI = 3.14159
```

**proceso_principal.flow:**
```flowscript
importar "utilidades"
importar "std/json" como Json

proceso Principal {
    inicio -> Tarea
    
    tarea Tarea {
        accion:
            numero = 6
            si utilidades.es_par(numero) { imprimir("Es par") }
            texto = Json.stringify({ valor: utilidades.PI })
            imprimir(texto)
            ir_a Fin
    }
    
    fin Fin
}
```

### Integración con JAR (Java)
```flowscript
importar_jar "libs/bouncycastle.jar" as crypto
importar_jar "libs/com.google.guava.jar" as guava

funcion generar_hash(data: texto) -> texto {
    hasher = crypto.MessageDigest.getInstance("SHA-256")
    bytes = hasher.digest(data.getBytes("UTF-8"))
    retornar guava.io.BaseEncoding.base64().encode(bytes)
}
```

## Biblioteca Estándar (std)

### Módulos Disponibles

| Módulo | Funciones principales |
|--------|----------------------|
| `std/io` | `imprimir(...)`, `leer_linea(prompt)` |
| `std/http` | `get(url, headers)`, `post(url, body, headers)`, `put`, `delete` |
| `std/json` | `parse(texto) -> objeto`, `stringify(valor, indentacion) -> texto` |
| `std/db` | `query(sql, params)`, `execute(sql, params)`, `get(tabla, id)`, `insert(tabla, registro)` |
| `std/fechas` | `ahora() -> entero`, `formatear(timestamp, formato) -> texto` |

## Ejemplo Completo: Proceso E-commerce

```flowscript
# --- Importaciones ---
importar "std/http" as http
importar "std/db" as db
importar "std/json" as json
importar_jar "libs/notificaciones.jar" as email

# --- Funciones de negocio ---
funcion validar_stock(items: lista) -> booleano {
    para cada item en items {
        prod = db.get("inventario", item.id_producto)
        si prod.stock < item.cantidad {
            lanzar { tipo: "ErrorStock", mensaje: "Sin stock de " + prod.nombre }
        }
    }
    retornar verdadero
}

# --- Proceso principal ---
proceso ProcesarOrden {
    inicio -> Validar
    
    tarea Validar {
        accion:
            intentar {
                validar_stock(entrada.items)
                ir_a ProcesarPago
            }
            capturar (err) {
                imprimir("Stock insuficiente: " + err.mensaje)
                ir_a FinRechazado
            }
    }
    
    tarea ProcesarPago {
        accion:
            resp = http.post("https://api.stripe.com/v1/charges", {
                monto: entrada.total,
                fuente: entrada.token_pago
            })
            
            si resp.status == "succeeded" {
                id_pago_confirmado = resp.id
                ir_a PrepararEnvioYNotificar
            } sino {
                ir_a FinPagoFallido
            }
    }
    
    # Gateway paralelo
    gateway PrepararEnvioYNotificar paralelo {
        rama -> ActualizarInventarioYLogistica
        rama -> NotificarCliente
        unir -> CompletarOrden
    }
    
    tarea ActualizarInventarioYLogistica {
        accion:
            db.execute("START TRANSACTION")
            para cada item en entrada.items {
                db.execute(
                    "UPDATE inventario SET stock = stock - ? WHERE id = ?",
                    [item.cantidad, item.id_producto]
                )
            }
            http.post("https://api.logistica.com/envios", { orden_id: entrada.id })
            db.execute("COMMIT")
            ir_a FinLogistica
    }
    
    fin FinLogistica
    
    tarea NotificarCliente {
        accion:
            email.ClienteEmail.enviar(
                entrada.cliente.email,
                "Confirmación de tu orden #" + entrada.id,
                "Tu pago fue exitoso. Estamos preparando tu envío."
            )
            ir_a FinNotificacion
    }
    
    fin FinNotificacion
    
    tarea CompletarOrden {
        accion:
            db.execute(
                "UPDATE ordenes SET estado = 'COMPLETADO' WHERE id = ?",
                [entrada.id]
            )
            imprimir("Orden " + entrada.id + " completada.")
            ir_a FinExitoso
    }
    
    fin FinExitoso
    fin FinRechazado
    fin FinPagoFallido
}
```

## Consideraciones de Implementación

### Ejecución
- Los procesos se ejecutan de forma secuencial siguiendo el flujo definido
- Las tareas dentro de un gateway paralelo se ejecutan concurrentemente
- El contexto del proceso mantiene el estado entre tareas

### Mejores Prácticas
1. Usar nombres descriptivos para tareas y procesos
2. Manejar errores con `intentar...capturar`
3. Modularizar la lógica en funciones reutilizables
4. Documentar con comentarios los flujos complejos
5. Validar datos de entrada al inicio del proceso

### Limitaciones Actuales
- Las palabras reservadas marcadas como "Futuras" aún no están implementadas
- La integración con JAR requiere configuración específica del entorno
- El paralelismo real depende de la implementación del runtime

## Referencias
- Videos de BPMN de referencia:
  - https://www.youtube.com/watch?v=E4yHqTh7NMA
  - https://www.youtube.com/watch?v=BbT0IN3y2V4
  - https://www.youtube.com/watch?v=RtxViAl1VPE&t=30s