package co.uniquindio.edu.proyecto_final_jfx.model.evento;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.EstadoEvento;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer.IObservador;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer.ISubject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un evento del sistema y avisa a los observadores cuando su estado cambia.
 * Es el Subject del patrón Observer: cuando el evento pasa a publicado, pausado, cancelado
 * o finalizado, notifica a todos los que se registraron para escuchar esos cambios.
 */
public class Evento implements ISubject {

    // contador global para asignar un id único a cada evento creado
    private static int contadorId = 0;

    // identificador único del evento
    private int idEvento;

    // nombre del evento
    private String nombre;

    // tipo de evento (concierto, teatro, conferencia, etc.)
    private Categoria categoria;

    // texto que describe el evento
    private String descripcion;

    // ciudad donde se realiza el evento
    private String ciudad;

    // fecha y hora en que ocurre el evento
    private LocalDateTime fechaHora;

    // estado actual del evento (borrador, publicado, pausado, etc.)
    private EstadoEvento estado;

    // lugar físico donde se realiza el evento
    private Recinto recinto;

    // lista de objetos que escuchan los cambios de estado del evento
    private List<IObservador> observadores;

    // cantidad total de lugares en el recinto
    private int aforoTotal;

    // cantidad de lugares que aún están disponibles
    private int aforoDisponible;

    /**
     * Crea un evento con los datos básicos y calcula el aforo a partir del recinto.
     *
     * @param nombre    nombre del evento
     * @param categoria tipo de evento
     * @param ciudad    ciudad donde ocurre
     * @param fechaHora fecha y hora del evento
     * @param recinto   lugar donde se realiza
     */
    public Evento(String nombre, Categoria categoria,
                  String ciudad, LocalDateTime fechaHora, Recinto recinto) {
        this.idEvento        = ++contadorId;
        this.nombre          = nombre;
        this.categoria       = categoria;
        this.ciudad          = ciudad;
        this.fechaHora       = fechaHora;
        this.recinto         = recinto;
        this.estado          = EstadoEvento.BORRADOR;
        this.observadores    = new ArrayList<>();
        this.aforoTotal      = recinto.getCapacidadTotal();
        this.aforoDisponible = recinto.getCapacidadTotal();
    }

    // ─── ISubject ────────────────────────────────────────────────

    /**
     * Agrega un observador a la lista para que reciba notificaciones de cambios.
     *
     * @param o el observador que se quiere registrar
     */
    @Override
    public void suscribir(IObservador o) {
        observadores.add(o);
    }

    /**
     * Elimina un observador de la lista para que deje de recibir notificaciones.
     *
     * @param o el observador que se quiere quitar
     */
    @Override
    public void desuscribir(IObservador o) {
        observadores.remove(o);
    }

    /**
     * Avisa a todos los observadores registrados que ocurrió un cambio en el evento.
     *
     * @param tipoEvento texto que describe qué cambio ocurrió
     */
    @Override
    public void notificar(String tipoEvento) {
        for (IObservador o : observadores) {
            o.actualizar(tipoEvento, this);
        }
    }

    // ─── Cambios de estado ───────────────────────────────────────

    /**
     * Cambia el estado del evento a PUBLICADO si está en borrador o pausado,
     * y avisa a los observadores.
     *
     * @return true si el cambio se hizo, false si el estado actual no lo permite
     */
    public boolean publicar() {
        if (estado != EstadoEvento.BORRADOR && estado != EstadoEvento.PAUSADO) {
            return false;
        }
        this.estado = EstadoEvento.PUBLICADO;
        notificar("EVENTO_PUBLICADO");
        return true;
    }

    /**
     * Cambia el estado del evento a PAUSADO si está publicado,
     * y avisa a los observadores.
     *
     * @return true si el cambio se hizo, false si el estado actual no lo permite
     */
    public boolean pausar() {
        if (estado != EstadoEvento.PUBLICADO) {
            return false;
        }
        this.estado = EstadoEvento.PAUSADO;
        notificar("EVENTO_PAUSADO");
        return true;
    }

    /**
     * Cambia el estado del evento a CANCELADO si no está ya finalizado ni cancelado,
     * y avisa a los observadores.
     *
     * @return true si el cambio se hizo, false si el estado actual no lo permite
     */
    public boolean cancelar() {
        if (estado == EstadoEvento.FINALIZADO || estado == EstadoEvento.CANCELADO) {
            return false;
        }
        this.estado = EstadoEvento.CANCELADO;
        notificar("EVENTO_CANCELADO");
        return true;
    }

    /**
     * Cambia el estado del evento a FINALIZADO si está publicado,
     * y avisa a los observadores.
     *
     * @return true si el cambio se hizo, false si el estado actual no lo permite
     */
    public boolean finalizar() {
        if (estado != EstadoEvento.PUBLICADO) {
            return false;
        }
        this.estado = EstadoEvento.FINALIZADO;
        notificar("EVENTO_FINALIZADO");
        return true;
    }

    /**
     * Verifica si todavía hay lugares disponibles en el evento.
     *
     * @return true si hay al menos un lugar disponible
     */
    public boolean hayDisponibilidad() {
        return aforoDisponible > 0;
    }

    /**
     * Reduce en uno el aforo disponible para registrar una reserva.
     *
     * @return true si se pudo reservar, false si ya no hay lugares
     */
    public boolean reservarLugar() {
        if (!hayDisponibilidad()) return false;
        aforoDisponible--;
        return true;
    }

    /**
     * Aumenta en uno el aforo disponible cuando se libera un lugar reservado.
     */
    public void liberarLugar() {
        if (aforoDisponible < aforoTotal) {
            aforoDisponible++;
        }
    }

    // ─── Getters ─────────────────────────────────────────────────

    /** @return id único del evento */
    public int getIdEvento()                  { return idEvento; }

    /** @return nombre del evento */
    public String getNombre()                 { return nombre; }

    /** @return categoría del evento */
    public Categoria getCategoria()           { return categoria; }

    /** @return descripción del evento */
    public String getDescripcion()            { return descripcion; }

    /** @return ciudad donde ocurre el evento */
    public String getCiudad()                 { return ciudad; }

    /** @return fecha y hora del evento */
    public LocalDateTime getFechaHora()       { return fechaHora; }

    /** @return estado actual del evento */
    public EstadoEvento getEstado()           { return estado; }

    /** @return recinto donde se realiza el evento */
    public Recinto getRecinto()               { return recinto; }

    /** @return aforo total del recinto */
    public int getAforoTotal()                { return aforoTotal; }

    /** @return cantidad de lugares disponibles */
    public int getAforoDisponible()           { return aforoDisponible; }

    /** @return lista de observadores registrados */
    public List<IObservador> getObservadores() { return observadores; }

    // ─── Setters ─────────────────────────────────────────────────

    /**
     * Cambia la descripción del evento.
     *
     * @param descripcion nuevo texto descriptivo
     */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /**
     * Cambia la ciudad del evento.
     *
     * @param ciudad nueva ciudad
     */
    public void setCiudad(String ciudad)           { this.ciudad = ciudad; }

    /**
     * Cambia el nombre del evento.
     *
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre)           { this.nombre = nombre; }

    /**
     * Cambia el recinto del evento.
     *
     * @param recinto nuevo recinto
     */
    public void setRecinto(Recinto recinto)        { this.recinto = recinto; }

    /**
     * Cambia la fecha y hora del evento.
     *
     * @param fecha nueva fecha y hora
     */
    public void setFechaHora(LocalDateTime fecha)  { this.fechaHora = fecha; }

    @Override
    public String toString() {
        return "Evento{" +
                "idEvento=" + idEvento +
                ", nombre='" + nombre + '\'' +
                ", categoria=" + categoria +
                ", ciudad='" + ciudad + '\'' +
                ", fechaHora=" + fechaHora +
                ", estado=" + estado +
                ", aforoDisponible=" + aforoDisponible +
                '}';
    }
}