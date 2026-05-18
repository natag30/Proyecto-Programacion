package co.uniquindio.edu.proyecto_final_jfx.model.incidencia;

import java.time.LocalDate;

public class Incidencia {

    private int idIncidencia;
    private String tipo;
    private String descripcion;
    private LocalDate fecha;

    /**
     * Crea una incidencia con su id, tipo, descripción y fecha.
     *
     * @param idIncidencia identificador único de la incidencia
     * @param tipo         tipo de incidencia registrada
     * @param descripcion  texto que describe lo que ocurrió
     * @param fecha        fecha en que se registró la incidencia
     */
    public Incidencia(int idIncidencia, String tipo, String descripcion, LocalDate fecha) {
        this.idIncidencia = idIncidencia;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    /**
     * Retorna el identificador único de la incidencia.
     *
     * @return id de la incidencia
     */
    public int getIdIncidencia() { return idIncidencia; }

    /**
     * Retorna el tipo de la incidencia.
     *
     * @return tipo de incidencia
     */
    public String getTipo() { return tipo; }

    /**
     * Retorna la descripción de la incidencia.
     *
     * @return texto descriptivo de la incidencia
     */
    public String getDescripcion() { return descripcion; }

    /**
     * Retorna la fecha en que se registró la incidencia.
     *
     * @return fecha de la incidencia
     */
    public LocalDate getFecha() { return fecha; }
}
