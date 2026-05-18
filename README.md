# Plataforma de Gestión de Eventos y Venta de Entradas

**Proyecto Final — Programación II**  
Universidad del Quindío · Facultad de Ingeniería · Ingeniería de Sistemas y Computación  
Docente: Carolina Londoño Idárraga

---

## Integrantes

| # | Nombre | ID |
|---|--------|----|
| 1 | Diana Gaona Valencia | 1092853450 |
| 2 | Natalia Garcia Hidalgo | 1094975918 |
| 3 | Diego Rincón Alvarez | 1115189532 |

---

## 1. Descripción del proyecto

Plataforma digital de gestión de eventos y venta de entradas donde usuarios finales pueden explorar eventos (conciertos, teatro, conferencias), seleccionar zonas y asientos, comprar entradas, agregar servicios adicionales (acceso VIP, seguro de cancelación, merchandising, parqueadero), realizar pagos y recibir notificaciones sobre cambios de estado.

Existen dos perfiles principales: **Usuario** y **Administrador (Operaciones)**.

---

## 2. Pensamiento Computacional (RF-043)

### 2.1 ¿Qué se solicita finalmente? (problema)

Una plataforma de gestión de eventos y venta de entradas que permita a los usuarios explorar, reservar y pagar entradas para eventos, agregar servicios adicionales y recibir notificaciones. Los administradores gestionan el catálogo completo de eventos, recintos, zonas, asientos, compras e incidencias, y acceden a un panel de métricas con visualización gráfica (JavaFX Charts).

---

### 2.2 ¿Qué información es relevante?

| Entidad | Atributos principales |
|---|---|
| **Usuario** | idUsuario, nombre, correo, teléfono, métodos de pago simulados |
| **Administrador** | idAdmin, usuario, contraseña |
| **Evento** | idEvento, nombre, categoría, descripción, ciudad, fechaHora, estadoEvento, políticas, recinto |
| **Recinto** | idRecinto, nombre, dirección, ciudad, zonas |
| **Zona** | idZona, nombre, capacidad, precioBase, asientos |
| **Asiento** | idAsiento, fila, número, estadoAsiento |
| **Compra** | idCompra, fechaCreacion, total, estadoCompra, entradas, serviciosAdicionales |
| **Entrada** | idEntrada, zona, asiento, precioFinal, estadoEntrada |
| **Pago** | idPago, monto, metodoPago, fecha, estadoPago |
| **Tarifa** | precioBase, factorZona, factorAjuste, descuentos |
| **ServicioAdicional** | idServicio, nombre, precio, tipo |
| **Incidencia** | idIncidencia, tipo, descripción, fecha, entidadAfectada |

---

### 2.3 ¿Cómo se agrupa la información relevante?

```
Usuario              [idUsuario, nombre, correo, teléfono]
Administrador        [idAdmin, usuario, contraseña]
Evento               [idEvento, nombre, categoría, ciudad, fechaHora]
  EstadoEvento       [BORRADOR, PUBLICADO, PAUSADO, CANCELADO, FINALIZADO]
Recinto              [idRecinto, nombre, dirección, ciudad]
Zona                 [idZona, nombre, capacidad, precioBase]
Asiento              [idAsiento, fila, número]
  EstadoAsiento      [DISPONIBLE, RESERVADO, VENDIDO, BLOQUEADO]
Compra               [idCompra, fechaCreacion, total]
  EstadoCompra       [CREADA, PAGADA, CONFIRMADA, CANCELADA, REEMBOLSADA, INCIDENCIA]
Entrada              [idEntrada, precioFinal]
  EstadoEntrada      [ACTIVA, USADA, ANULADA]
Pago                 [idPago, monto, fecha]
Tarifa               [precioBase, factorAjuste]
ServicioAdicional    [idServicio, nombre, precio]
  TipoServicio       [VIP, SEGURO_CANCELACION, MERCHANDISING, PARQUEADERO, ACCESO_PREFERENCIAL]
Incidencia           [idIncidencia, tipo, descripción, fecha]
```

---

### 2.4 ¿Qué funcionalidades se requieren?

**Usuario:**
- Registrarse / iniciar sesión (RF-001, RF-020)
- Gestionar perfil y métodos de pago (RF-002, RF-021)
- Explorar y filtrar eventos por fecha, ciudad, categoría y precio (RF-003)
- Consultar detalle de un evento (RF-004)
- Seleccionar entradas por zona y/o asientos (RF-005)
- Crear, modificar y cancelar compras antes del pago (RF-006)
- Pagar y consultar comprobantes (RF-007)
- Agregar servicios adicionales a la compra (RF-009)
- Consultar historial de compras con filtros (RF-010)
- Descargar reportes en CSV o PDF (RF-011)

**Administrador:**
- Gestionar usuarios CRUD (RF-012)
- Gestionar eventos: crear, publicar, pausar, cancelar (RF-013)
- Gestionar recintos y zonas (RF-014)
- Gestionar asientos y disponibilidad (RF-015)
- Gestionar compras e incidencias (RF-016, RF-017)
- Panel de métricas con JavaFX Charts (RF-018, RF-019)

---

### 2.5 ¿Cómo se distribuyen las funcionalidades?

## Diagrama UML de clases (RF-044)

> El diagrama completo se encuentra en [`/docs/Diagrama_UML.pdf`](./docs/Diagrama_UML.pdf)
---

### 2.6 ¿Qué debo hacer para probar las funcionalidades?

---

### 2.7 ¿Qué puedo reutilizar?




---

## Patrones de diseño

Resumen de los 9 patrones implementados:

| # | Patrón | Tipo | Obligatorio | RF clave |
|---|---|---|---|---|
| 1 | Singleton | Creacional | Si| RF-043, RF-012, RF-013 |
| 2 | Factory Method | Creacional | libre | RF-023, RF-024 |
| 3 | Builder | Creacional | libre | RF-034, RF-009, RF-006 |
| 4 | Decorator | Estructural | Si | RF-009, RF-038, RF-007 |
| 5 | Adapter | Estructural | libre| RF-007, RF-021 |
| 6 | Facade | Estructural | libre | RF-005, RF-006, RF-007 |
| 7 | Strategy | Comportamiento | Si | RF-029, RF-005, RF-007 |
| 8 | Observer | Comportamiento | libre | RF-008, RF-017, RF-024 |
| 9 | Iterator | Comportamiento | libre | RF-003, RF-010, RF-033 |

---

### Patrones Creacionales (RF-049)

---

#### 1. Singleton — `PlataformaEventos`

**RF que resuelve:** RF-043, RF-012, RF-013, RF-018

**Problema:** La plataforma necesita un único punto de acceso global al gestor central del sistema (`PlataformaEventos`), que centraliza la lista de eventos, usuarios, recintos y compras activas. Si existieran múltiples instancias, habría inconsistencias en la disponibilidad de asientos.

**Por qué este patrón:** Garantiza que solo exista una instancia del gestor durante toda la ejecución. Es la opción natural cuando el sistema tiene un punto de entrada único y estado global que no puede duplicarse.

---

#### 2. Factory Method — `EventoFactory`

**RF que resuelve:** RF-023, RF-024, RF-013

**Problema:** Los eventos pueden ser de distintos tipos (Concierto, Teatro, Conferencia), cada uno con reglas distintas de aforo, asientos numerados o no, y políticas de cancelación propias. Instanciarlos directamente con `new` acopla el código al tipo concreto.

**Por qué este patrón:** Permite delegar la creación del tipo correcto de `Evento` a una fábrica, haciendo el sistema extensible sin modificar código existente (aplica OCP de SOLID). Es preferible a un Simple Factory porque cada subtipo puede tener su propia lógica de inicialización.

---

#### 3. Builder — `CompraBuilder`

**RF que resuelve:** RF-034, RF-035, RF-006, RF-009

**Problema:** Una `Compra` se construye paso a paso: primero el usuario selecciona entradas, luego agrega servicios adicionales opcionales (VIP, seguro, parqueadero), y finalmente confirma el pago. Construirla en un solo constructor con todos los parámetros opcionales sería imposible de mantener.

**Por qué este patrón:** Permite construir objetos complejos con configuraciones variables de forma clara y legible, evitando constructores telescópicos. Es preferible al Prototype porque cada compra es única y no se clona de una existente.

---

### Patrones Estructurales (RF-050)

---

#### 4. Decorator — `ServicioDecorator`

**RF que resuelve:** RF-009, RF-038, RF-007

**Problema:** Una entrada base tiene un precio según su zona. Al agregar servicios adicionales (acceso VIP, seguro de cancelación, merchandising, parqueadero) el precio cambia dinámicamente. Usar herencia para cada combinación generaría explosión de subclases (2ⁿ combinaciones posibles).

**Por qué este patrón:** Permite envolver la `EntradaBase` con capas de servicios en tiempo de ejecución, sumando precio y comportamiento sin modificar la clase base. Es preferible a la herencia múltiple porque las combinaciones son potencialmente infinitas y se deciden en tiempo de ejecución.

---

#### 5. Adapter — `PSEAdapter` / `TarjetaAdapter`

**RF que resuelve:** RF-007, RF-021

**Problema:** Los métodos de pago simulados (tarjeta de crédito, PSE, efectivo, billetera digital) tienen interfaces distintas. El sistema de compra necesita invocarlos de manera uniforme sin conocer los detalles de cada implementación.

**Por qué este patrón:** Convierte la interfaz incompatible de cada método de pago en la interfaz estándar `IMetodoPago` que espera el sistema, permitiendo integrar nuevos métodos sin cambiar la lógica de `Compra`. Es preferible a modificar las clases originales ya que eso violaría OCP.

---

#### 6. Facade — `ServicioCompra`

**RF que resuelve:** RF-005, RF-006, RF-007, RF-008

**Problema:** El proceso de compra involucra múltiples subsistemas: verificar disponibilidad de asientos, calcular tarifas, procesar pago, generar entrada, enviar notificación y registrar incidencias. El controlador JavaFX no debería conocer todos esos subsistemas directamente.

**Por qué este patrón:** Proporciona una interfaz simplificada que orquesta todos los pasos internos. Es preferible al Mediator porque la complejidad es de dependencias hacia subsistemas, no de comunicación entre pares.

---

### Patrones de Comportamiento (RF-051)

---

#### 7. Strategy — `IEstrategiaTarifa`

**RF que resuelve:** RF-029, RF-005, RF-007

**Problema:** El cálculo del precio final de una entrada varía según la zona (VIP, Preferencial, General), el tipo de usuario (estudiante, trabajador, externo) y la anticipación de compra. Codificar todo en `if-else` dentro de `Compra` viola SRP y OCP.

**Por qué este patrón:** Encapsula cada algoritmo de cálculo en una clase independiente intercambiable. Se puede cambiar la estrategia en tiempo de ejecución sin modificar `Compra`. Es preferible al Template Method porque los algoritmos son completamente independientes entre sí.

---

#### 8. Observer — `Observador`

**RF que resuelve:** RF-008, RF-017, RF-024

**Problema:** Cuando el estado de un `Evento` o `Compra` cambia (evento cancelado, compra confirmada, asiento liberado), múltiples actores deben ser notificados: el usuario vía correo, el sistema de reportes, el panel de métricas. Notificarlos directamente acopla el modelo a la UI.

**Por qué este patrón:** Define una relación uno-a-muchos donde el sujeto notifica automáticamente a todos sus observadores cuando cambia de estado, sin conocerlos directamente. Es preferible al Mediator porque la comunicación es unidireccional: el sujeto no necesita respuesta.

---

#### 9. Iterator — `IIteradorEventos`

**RF que resuelve:** RF-003, RF-010, RF-033

**Problema:** El sistema necesita recorrer colecciones de eventos aplicando distintos filtros (por fecha, por ciudad, por categoría, por precio) sin exponer la estructura interna de la lista ni duplicar la lógica de recorrido en cada pantalla. Lo mismo aplica para recorrer asientos de una zona al consultar el mapa de disponibilidad.

**Por qué este patrón:** Encapsula el algoritmo de recorrido y filtrado en objetos independientes, permitiendo cambiar el criterio de búsqueda sin modificar la colección ni el código cliente. Es preferible al Strategy para este caso porque el objetivo es **atravesar** una colección, no calcular un valor.



