package co.uniquindio.edu.poo.model;

import java.time.LocalDateTime;

public class Evento {

    private int idEvento;
    private String nombre;
    private String ciudad;
    LocalDateTime fechaHora;

    private Categoria categoria;
    private EstadoEvento estado;


    public Evento(int idEvento, String nombre, String ciudad, LocalDateTime fechaHora,
                  Categoria categoria, EstadoEvento estado ) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.fechaHora = fechaHora;
        this.estado = EstadoEvento.PUBLICADO;
        this.categoria = Categoria.CONCIERTO;

    }

    public void publicar(){}

    public int getIdEvento() {return idEvento;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public void setEstado(EstadoEvento estado) {
        this.estado = estado;
    }
}
