package co.uniquindio.edu.proyecto_final_jfx.model.incidencia;

import java.time.LocalDate;

public class Incidencia {

    private int idIncidencia;
    private String tipo;
    private String descripcion;
    private LocalDate fecha;

    public Incidencia(int idIncidencia, String tipo, String descripcion, LocalDate fecha) {
        this.idIncidencia = idIncidencia;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public int getIdIncidencia() { return idIncidencia; }
    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFecha() { return fecha; }
}