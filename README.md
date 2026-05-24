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

## Tabla de contenidos

1. [Descripción del proyecto](#1-descripción-del-proyecto)
2. [Pensamiento computacional](#2-pensamiento-computacional-rf-043)
3. [Diagrama UML de clases](#3-diagrama-uml-de-clases-rf-044)
4. [Estructura del proyecto](#4-estructura-del-proyecto)
5. [Tecnologías utilizadas](#5-tecnologías-utilizadas)
6. [Cómo ejecutar el proyecto](#6-cómo-ejecutar-el-proyecto)
7. [Datos de prueba inicializados](#7-datos-de-prueba-inicializados-rf-045)
8. [Patrones de diseño](#8-patrones-de-diseño)
9. [Principios SOLID](#9-principios-solid-rf-047)
10. [Generador de reportes](#10-generador-de-reportes-rf-046)
11. [Tests unitarios](#11-tests-unitarios)
12. [Control de versiones](#12-control-de-versiones-rf-048)

---

## 1. Descripción del proyecto

Plataforma digital de gestión de eventos y venta de entradas donde usuarios finales pueden explorar eventos (conciertos, teatro, conferencias), seleccionar zonas y asientos, comprar entradas, agregar servicios adicionales (acceso VIP, seguro de cancelación, merchandising, parqueadero), realizar pagos y recibir notificaciones sobre cambios de estado.

Existen dos perfiles principales: **Usuario** y **Administrador (Operaciones)**.

### Funcionalidades por perfil

**Usuario:**
- Registrarse e iniciar sesión (RF-001, RF-020)
- Gestionar perfil: nombre, correo, teléfono (RF-002, RF-021)
- Explorar eventos con filtros por fecha, ciudad, categoría y precio (RF-003)
- Consultar detalle de un evento: descripción, lugar, fechas, aforo, zonas, precios (RF-004)
- Seleccionar entradas por zona y/o asientos según disponibilidad (RF-005)
- Crear, modificar y cancelar compras antes del pago (RF-006)
- Pagar y consultar comprobantes (RF-007)
- Visualizar estado de la compra (RF-008)
- Agregar servicios adicionales: VIP, seguro, merchandising, parqueadero (RF-009)
- Consultar historial de compras con filtros (RF-010)
- Descargar reportes en CSV o PDF (RF-011)

**Administrador:**
- Gestionar usuarios: crear, actualizar, eliminar, listar (RF-012)
- Gestionar eventos: crear, publicar, pausar, cancelar (RF-013)
- Gestionar recintos y zonas (RF-014)
- Gestionar asientos y disponibilidad (RF-015)
- Gestionar compras e incidencias (RF-016, RF-017)
- Panel de métricas con JavaFX Charts — BarChart y PieChart (RF-018, RF-019)
- Exportar reportes CSV y PDF (RF-046)

---

## 2. Pensamiento computacional (RF-043)

### 2.1 Problema

Una plataforma de gestión de eventos y venta de entradas que permita a los usuarios explorar, reservar y pagar entradas para eventos, agregar servicios adicionales y recibir notificaciones. Los administradores gestionan el catálogo completo de eventos, recintos, zonas, asientos, compras e incidencias, y acceden a un panel de métricas con visualización gráfica.

### 2.2 Información relevante

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

### 2.3 Agrupación de la información

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

### 2.4 Funcionalidades requeridas

Las funcionalidades se dividen en dos perfiles tal como se describe en la sección 1. Cada funcionalidad está respaldada por un requisito funcional (RF-001 a RF-051) detallado en el enunciado del proyecto.

### 2.5 Distribución de responsabilidades

La solución aplica el principio de separación de responsabilidades en tres capas:

- **Modelo** — entidades, lógica de negocio y patrones de diseño
- **Vista** — interfaces JavaFX construidas dinámicamente desde los controladores
- **Controlador** — coordinación entre modelo y vista, gestión de datos de sesión

### 2.6 Cómo probar las funcionalidades

Ver la sección [Cómo ejecutar el proyecto](#6-cómo-ejecutar-el-proyecto) y [Datos de prueba inicializados](#7-datos-de-prueba-inicializados-rf-045).

### 2.7 Reutilización

- Los iteradores (`IteradorCategoria`, `IteradorCiudad`, `IteradorFecha`) son reutilizados tanto en la vista de exploración del usuario como en búsquedas administrativas.
- El `Decorator` de entradas es reutilizable para cualquier combinación de servicios adicionales sin modificar `EntradaBase`.
- El `GeneradorReportes` es independiente de la UI y puede ser invocado desde cualquier parte del sistema.
- La `PlataformaEventos` (Singleton) centraliza el acceso a todas las listas del sistema.

---

## 3. Diagrama UML de clases (RF-044)

El diagrama completo se encuentra en [`/docs/Diagrama_UML.pdf`](./docs/Diagrama_UML.pdf).

Cubre las siguientes entidades y sus relaciones (Asociación, Composición, Herencia) con multiplicidad y roles:

`Usuario` · `Administrador` · `Evento` · `Recinto` · `Zona` · `Asiento` · `Compra` · `Entrada` · `Tarifa` · `Pago` · `Incidencia` · clases de soporte (estrategias, decoradores, adaptadores, fábricas, iteradores, observadores)

---

## 4. Estructura del proyecto

```
Proyecto-Programacion/
├── docs/
│   └── Diagrama_UML.pdf
└── proyecto_final_JFX/
    ├── pom.xml
    └── src/
        ├── main/
        │   └── java/co/uniquindio/edu/proyecto_final_jfx/
        │       ├── Launcher.java                     ← Punto de entrada + datos de prueba
        │       ├── controller/
        │       │   ├── GeneradorReportes.java         ← Exportación CSV y PDF
        │       │   └── PersistenciaCompras.java       ← Persistencia en archivo
        │       ├── model/
        │       │   ├── compra/
        │       │   │   ├── Compra.java
        │       │   │   ├── Entrada.java
        │       │   │   ├── Pago.java
        │       │   │   ├── ServicioAdicional.java
        │       │   │   └── Tarifa.java
        │       │   ├── enums/
        │       │   │   ├── Categoria.java
        │       │   │   ├── EstadoAsiento.java
        │       │   │   ├── EstadoCompra.java
        │       │   │   ├── EstadoEntrada.java
        │       │   │   ├── EstadoEvento.java
        │       │   │   ├── Rol.java
        │       │   │   └── TipoServicio.java
        │       │   ├── evento/
        │       │   │   ├── Asiento.java
        │       │   │   ├── Evento.java
        │       │   │   ├── Recinto.java
        │       │   │   └── Zona.java
        │       │   ├── incidencia/
        │       │   │   └── Incidencia.java
        │       │   ├── usuario/
        │       │   │   ├── Administrador.java
        │       │   │   ├── SesionActual.java
        │       │   │   └── Usuario.java
        │       │   └── patrones/
        │       │       ├── creacionales/
        │       │       │   ├── factory/
        │       │       │   │   ├── EventoFactory.java
        │       │       │   │   ├── ConciertoFactory.java
        │       │       │   │   ├── TeatroFactory.java
        │       │       │   │   └── ConferenciaFactory.java
        │       │       │   └── singleton/
        │       │       │       └── PlataformaEventos.java
        │       │       ├── estructurales/
        │       │       │   ├── adapter/
        │       │       │   │   ├── IMetodoPago.java
        │       │       │   │   ├── PSE.java
        │       │       │   │   ├── PSEAdapter.java
        │       │       │   │   ├── TarjetaCredito.java
        │       │       │   │   └── TarjetaAdapter.java
        │       │       │   ├── decorator/
        │       │       │   │   ├── IEntrada.java
        │       │       │   │   ├── EntradaBase.java
        │       │       │   │   ├── ServicioDecorator.java
        │       │       │   │   ├── AccesoVIPDecorator.java
        │       │       │   │   ├── MerchandisingDecorator.java
        │       │       │   │   └── SeguroCancelacionDecorator.java
        │       │       │   ├── facade/
        │       │       │   │   ├── ServicioCompra.java
        │       │       │   │   ├── ServicioAsiento.java
        │       │       │   │   ├── ServicioEntrada.java
        │       │       │   │   └── ServicioPago.java
        │       │       │   └── proxy/
        │       │       │       ├── IPrincipal.java
        │       │       │       ├── PrincipalReal.java
        │       │       │       └── ProxyAcceso.java
        │       │       └── comportamentales/
        │       │           ├── iterator/
        │       │           │   ├── IColeccionEventos.java
        │       │           │   ├── IIteradorEventos.java
        │       │           │   ├── IteradorCategoria.java
        │       │           │   ├── IteradorCiudad.java
        │       │           │   └── IteradorFecha.java
        │       │           ├── observer/
        │       │           │   ├── IObservador.java
        │       │           │   ├── ISubject.java
        │       │           │   ├── NotificacionCorreo.java
        │       │           │   ├── PanelMetricas.java
        │       │           │   └── RegistroIncidencias.java
        │       │           └── strategy/
        │       │               ├── IEstrategiaTarifa.java
        │       │               ├── TarifaEstandar.java
        │       │               ├── TarifaAnticipada.java
        │       │               └── TarifaEstudiante.java
        │       └── viewController/
        │           ├── Estilos.java
        │           ├── LoginViewController.java
        │           ├── PrincipalAdminViewController.java
        │           └── PrincipalUsuarioViewController.java
        └── test/
            └── java/co/uniquindio/edu/proyecto_final_jfx/
                ├── AsientoTest.java
                ├── CompraTest.java
                ├── EventoTest.java
                ├── GeneradorReportesTest.java
                ├── IteradorTest.java
                └── PlataformaEventosTest.java
```

---

## 5. Tecnologías utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 21 | Lenguaje principal |
| JavaFX | 21 | Interfaz gráfica (FXML + controladores) |
| Maven | 3.x | Gestión de dependencias y build |
| JUnit Jupiter | 5.12.1 | Tests unitarios |
| Apache PDFBox | 3.0.3 | Exportación de reportes en PDF |
| Apache POI | 5.3.0 | Soporte para exportación CSV/Excel |
| Git | — | Control de versiones con ramas por integrante |

---

## 6. Cómo ejecutar el proyecto

### Prerrequisitos

- Java 21 o superior
- Maven 3.8 o superior
- Git

### Pasos

```bash
# 1. Clonar el repositorio
git clone https://github.com/natag30/Proyecto-Programacion.git
cd Proyecto-Programacion/proyecto_final_JFX

# 2. Compilar el proyecto
mvn compile

# 3. Ejecutar la aplicación
mvn javafx:run

# 4. Ejecutar los tests
mvn test
```

### Credenciales de prueba

| Rol | Usuario | Contraseña |
|---|---|---|
| Administrador | admin | 1234 |
| Usuario | laura | 1234 |
| Usuario | carlos | 1234 |

---

## 7. Datos de prueba inicializados (RF-045)

El archivo `Launcher.java` inicializa automáticamente al arrancar:

**Eventos (3):**

| Nombre | Categoría | Ciudad | Fecha | Estado |
|---|---|---|---|---|
| Rock en la Montaña | CONCIERTO | Armenia | 2026-08-15 | PUBLICADO |
| Hamlet en el Risaralda | TEATRO | Pereira | 2026-09-20 | PUBLICADO |
| Innovación Tech 2026 | CONFERENCIA | Manizales | 2026-10-05 | PUBLICADO |

Cada evento tiene un recinto con zonas VIP, Preferencial y General, cada zona con asientos numerados por fila y número.

**Usuarios (2):** Laura Gomez, Carlos Perez — con métodos de pago Tarjeta y PSE disponibles.

**Administrador (1):** admin / 1234

---

## 8. Patrones de diseño

El proyecto implementa **9 patrones de diseño** distribuidos en las tres categorías requeridas.

| # | Patrón | Categoría | Obligatorio | RF clave |
|---|---|---|---|---|
| 1 | Singleton | Creacional | Sí | RF-043, RF-012, RF-013 |
| 2 | Factory Method | Creacional | Libre | RF-023, RF-024 |
| 3 | Builder | Creacional | Libre | RF-034, RF-006, RF-009 |
| 4 | Decorator | Estructural | Sí | RF-009, RF-038, RF-007 |
| 5 | Adapter | Estructural | Libre | RF-007, RF-021 |
| 6 | Facade | Estructural | Libre | RF-005, RF-006, RF-007 |
| 7 | Strategy | Comportamiento | Sí | RF-029, RF-005, RF-007 |
| 8 | Observer | Comportamiento | Libre | RF-008, RF-017, RF-024 |
| 9 | Iterator | Comportamiento | Libre | RF-003, RF-010, RF-033 |

---

### Patrones Creacionales (RF-049)

#### 1. Singleton — `PlataformaEventos`

**RF que resuelve:** RF-043, RF-012, RF-013, RF-018

**Problema:** La plataforma necesita un único punto de acceso global al gestor central del sistema, que centraliza la lista de eventos, usuarios, recintos y compras activas. Si existieran múltiples instancias, habría inconsistencias en la disponibilidad de asientos y en los datos del panel de métricas.

**Propósito:** Garantizar que solo exista una instancia del gestor durante toda la ejecución, con un punto de acceso global.

**Solución:**

```java
public class PlataformaEventos {
    private static PlataformaEventos instancia;
    private List<Evento> eventos = new ArrayList<>();

    private PlataformaEventos() {}

    public static PlataformaEventos getInstancia() {
        if (instancia == null) {
            instancia = new PlataformaEventos();
        }
        return instancia;
    }
}
```

---

#### 2. Factory Method — `EventoFactory`

**RF que resuelve:** RF-023, RF-024, RF-013

**Problema:** Los eventos pueden ser de distintos tipos (Concierto, Teatro, Conferencia), cada uno con reglas distintas de aforo y políticas de cancelación. Instanciarlos directamente con `new` acopla el código al tipo concreto y viola OCP.

**Propósito:** Delegar la creación del tipo correcto de `Evento` a una fábrica, haciendo el sistema extensible sin modificar código existente.

**Solución:**

```java
public abstract class EventoFactory {
    public abstract Evento crearEvento(String nombre, String ciudad,
                                       LocalDateTime fechaHora, Recinto recinto);
}

public class ConciertoFactory extends EventoFactory {
    @Override
    public Evento crearEvento(String nombre, String ciudad,
                               LocalDateTime fechaHora, Recinto recinto) {
        return new Evento(nombre, Categoria.CONCIERTO, ciudad, fechaHora, recinto);
    }
}
// TeatroFactory y ConferenciaFactory siguen el mismo patrón
```

---

#### 3. Builder — `Compra.Builder`

**RF que resuelve:** RF-034, RF-035, RF-006, RF-009

**Problema:** Una `Compra` se construye paso a paso: el usuario selecciona entradas, agrega servicios adicionales opcionales y confirma el pago. Un constructor con todos los parámetros opcionales sería imposible de mantener.

**Propósito:** Construir objetos complejos con configuraciones variables de forma clara, evitando constructores telescópicos.

**Solución:**

```java
Compra compra = new Compra.Builder()
    .conUsuario(usuario)
    .conEvento(evento)
    .conEntrada(new AccesoVIPDecorator(new EntradaBase(asiento, precio)))
    .conPago(new Pago(total, new PSEAdapter(new PSE())))
    .build();
```

---

### Patrones Estructurales (RF-050)

#### 4. Decorator — `ServicioDecorator`

**RF que resuelve:** RF-009, RF-038, RF-007

**Problema:** Al agregar servicios adicionales (VIP, seguro, merchandising) el precio de una entrada cambia dinámicamente. Usar herencia para cada combinación generaría una explosión de subclases (2ⁿ combinaciones posibles).

**Propósito:** Envolver la `EntradaBase` con capas de servicios en tiempo de ejecución, sumando precio y comportamiento sin modificar la clase base.

**Solución:**

```java
public interface IEntrada {
    double getPrecio();
    String getDescripcion();
}

public class EntradaBase implements IEntrada { ... }

public abstract class ServicioDecorator implements IEntrada {
    protected IEntrada entradaDecorada;
}

public class AccesoVIPDecorator extends ServicioDecorator {
    @Override
    public double getPrecio() { return entradaDecorada.getPrecio() + 50000; }
}
// SeguroCancelacionDecorator y MerchandisingDecorator siguen el mismo patrón
```

---

#### 5. Adapter — `PSEAdapter` / `TarjetaAdapter`

**RF que resuelve:** RF-007, RF-021

**Problema:** Los métodos de pago simulados (tarjeta, PSE) tienen interfaces distintas. El sistema necesita invocarlos de manera uniforme sin conocer los detalles de cada implementación.

**Propósito:** Convertir la interfaz incompatible de cada método de pago en la interfaz estándar `IMetodoPago` que espera el sistema.

**Solución:**

```java
public interface IMetodoPago {
    void realizarPago(double monto);
}

public class PSEAdapter implements IMetodoPago {
    private PSE pse;
    public PSEAdapter(PSE pse) { this.pse = pse; }

    @Override
    public void realizarPago(double monto) {
        pse.transferir(monto);
    }
}
```

---

#### 6. Facade — `ServicioCompra`

**RF que resuelve:** RF-005, RF-006, RF-007, RF-008

**Problema:** El proceso de compra involucra múltiples subsistemas: `ServicioAsiento`, `ServicioPago` y `ServicioEntrada`. El controlador JavaFX no debería conocer todos esos subsistemas directamente.

**Propósito:** Proporcionar una interfaz simplificada que orquesta todos los pasos internos del proceso de compra.

**Solución:**

```java
public class ServicioCompra {
    private ServicioPago servicioPago;
    private ServicioAsiento servicioAsiento;
    private ServicioEntrada servicioEntrada;

    public boolean procesarCompra(Compra compra, List<Asiento> asientos) {
        if (!servicioAsiento.reservarAsientos(asientos)) return false;
        if (!servicioPago.procesarPago(compra.getPago())) {
            servicioAsiento.liberarAsientos(asientos);
            return false;
        }
        servicioEntrada.generarEntrada(compra.getUsuario(), asientos);
        return true;
    }

    public boolean cancelarCompra(Compra compra, List<Asiento> asientos) {
        compra.cancelar();
        servicioAsiento.liberarAsientos(asientos);
        return true;
    }
}
```

---

### Patrones de Comportamiento (RF-051)

#### 7. Strategy — `IEstrategiaTarifa`

**RF que resuelve:** RF-029, RF-005, RF-007

**Problema:** El cálculo del precio final varía según el tipo de usuario y la anticipación de compra. Codificar todo en `if-else` dentro de `Compra` viola SRP y OCP.

**Propósito:** Encapsular cada algoritmo de cálculo en una clase independiente intercambiable en tiempo de ejecución.

**Solución:**

```java
public interface IEstrategiaTarifa {
    double calcularPrecio(double precioBase);
}

public class TarifaEstandar implements IEstrategiaTarifa {
    @Override
    public double calcularPrecio(double precioBase) { return precioBase; }
}

public class TarifaEstudiante implements IEstrategiaTarifa {
    @Override
    public double calcularPrecio(double precioBase) { return precioBase * 0.7; }
}

public class TarifaAnticipada implements IEstrategiaTarifa {
    @Override
    public double calcularPrecio(double precioBase) { return precioBase * 0.85; }
}
```

---

#### 8. Observer — `IObservador` / `ISubject`

**RF que resuelve:** RF-008, RF-017, RF-024

**Problema:** Cuando el estado de un `Evento` cambia, múltiples actores deben ser notificados: correo, panel de métricas, registro de incidencias. Notificarlos directamente acopla el modelo a la UI.

**Propósito:** Definir una relación uno-a-muchos donde el sujeto notifica automáticamente a sus observadores al cambiar de estado.

**Solución:**

```java
public interface IObservador {
    void actualizar(String evento, String mensaje);
}

public interface ISubject {
    void suscribir(IObservador observador);
    void notificar(String evento, String mensaje);
}

// Observadores concretos:
// - NotificacionCorreo  → simula envío de correo
// - PanelMetricas       → actualiza contadores en la vista admin
// - RegistroIncidencias → registra cambios anómalos como Incidencia
```

---

#### 9. Iterator — `IIteradorEventos`

**RF que resuelve:** RF-003, RF-010, RF-033

**Problema:** El sistema necesita recorrer eventos con distintos filtros (fecha, ciudad, categoría) sin exponer la estructura interna de la lista ni duplicar la lógica de recorrido en cada pantalla.

**Propósito:** Encapsular el algoritmo de recorrido y filtrado en objetos independientes, permitiendo cambiar el criterio sin modificar la colección ni el código cliente.

**Solución:**

```java
public interface IIteradorEventos {
    boolean hasNext();
    Evento next();
}

// Tres implementaciones:
// IteradorCategoria → filtra por Categoria enum
// IteradorCiudad    → filtra por nombre de ciudad
// IteradorFecha     → filtra por rango LocalDateTime desde–hasta

// Uso desde la vista:
IIteradorEventos it = plataforma.crearIteradorPorCategoria(Categoria.CONCIERTO);
while (it.hasNext()) {
    Evento e = it.next();
}
```

---

## 9. Principios SOLID (RF-047)

| Principio | Cómo se aplica en el proyecto |
|---|---|
| **SRP** — Single Responsibility | Cada clase tiene una única razón para cambiar: `Compra` gestiona su ciclo de vida, `ServicioPago` procesa pagos, `GeneradorReportes` exporta archivos, `RegistroIncidencias` registra anomalías. |
| **OCP** — Open/Closed | `IEstrategiaTarifa` y `ServicioDecorator` están abiertos para extensión sin modificar código existente. Nuevas fábricas de eventos se añaden sin tocar `EventoFactory`. |
| **LSP** — Liskov Substitution | `ConciertoFactory`, `TeatroFactory` y `ConferenciaFactory` son intercambiables donde se espere `EventoFactory`. `PSEAdapter` y `TarjetaAdapter` son intercambiables donde se espere `IMetodoPago`. |
| **ISP** — Interface Segregation | Interfaces pequeñas y específicas: `IMetodoPago` solo tiene `realizarPago()`, `IIteradorEventos` solo `hasNext()` y `next()`, `IObservador` solo `actualizar()`. |
| **DIP** — Dependency Inversion | `Compra` depende de `IMetodoPago` (interfaz), no de `PSEAdapter` o `TarjetaAdapter` (concretos). `ServicioCompra` depende de los servicios como abstracciones. Las estrategias se inyectan como `IEstrategiaTarifa`. |

---

## 10. Generador de reportes (RF-046)

La clase `GeneradorReportes` (en `controller/`) exporta reportes en tres formatos:

| Método | Formato | Contenido |
|---|---|---|
| `exportarComprasCSV` | CSV | ID, Fecha, Estado, Usuario, Evento, Total, Num. Entradas |
| `exportarComprasPDF` | PDF | Título, fecha de generación, lista de compras paginada |
| `exportarOcupacionCSV` | CSV | Evento, Zona, Capacidad, Vendidos, Disponibles, % Ocupación |

Los reportes se generan desde el panel de **Métricas** del administrador con los botones **"Exportar CSV"** y **"Exportar PDF"**. Los archivos se guardan en el directorio raíz del proyecto.

**Dependencias:**
- `Apache PDFBox 3.0.3` — generación de PDF con paginación automática
- `Apache POI 5.3.0` — soporte de formato CSV/Excel

---

## 11. Tests unitarios

El proyecto incluye **45 tests unitarios** con JUnit Jupiter 5.12.1, organizados en 6 clases:

| Clase | Qué prueba | Tests |
|---|---|---|
| `AsientoTest` | Estados del asiento: reservar, vender, liberar, bloquear, ocupar | 9 |
| `CompraTest` | Builder, cancelación, validación de estados, cálculo de total | 7 |
| `EventoTest` | Ciclo de vida: publicar, pausar, cancelar, finalizar, notificación observers | 10 |
| `PlataformaEventosTest` | Singleton, búsqueda de usuarios/admins, iteradores integrados | 8 |
| `IteradorTest` | IteradorCategoria, IteradorCiudad, IteradorFecha con distintos rangos | 7 |
| `GeneradorReportesTest` | Creación de archivos CSV y PDF, verificación de contenido y tamaño | 4 |

```bash
mvn test
```

Los tests del modelo puro no dependen de JavaFX y corren en entorno headless sin problemas.

---

## 12. Control de versiones (RF-048)

El proyecto se gestiona con **Git** usando una estrategia de ramas por integrante con integración a `main` mediante Pull Requests.

### Estrategia de ramas

| Rama | Integrante | Responsabilidad principal |
|---|---|---|
| `main` | — | Rama de integración estable |
| `Natalia` | Natalia Garcia | Modelo, patrones de comportamiento (Iterator, Observer, Strategy), tests |
| `Diana` | Diana Gaona | Patrones creacionales (Singleton, Factory, Builder), vistas admin |
| `Diego` | Diego Rincón | Patrones estructurales (Decorator, Adapter, Facade, Proxy), vistas usuario |

### Flujo de trabajo

1. Cada integrante trabaja en su rama propia
2. Los cambios se integran a `main` mediante Pull Requests
3. Los commits son frecuentes y descriptivos, asociados a funcionalidades concretas
4. Los merges quedan registrados en el historial del repositorio

### Repositorio

[https://github.com/natag30/Proyecto-Programacion](https://github.com/natag30/Proyecto-Programacion)