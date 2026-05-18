package co.uniquindio.edu.proyecto_final_jfx.model.evento;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.EstadoEvento;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer.IObservador;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer.ISubject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Evento implements ISubject {

    private int idEvento;
    private String nombre;
    private Categoria categoria;
    private String descripcion;
    private String ciudad;
    private LocalDateTime fechaHora;
    private EstadoEvento estado;
    private Recinto recinto;
    private List<IObservador> observadores;

    public Evento(int idEvento, String nombre, Categoria categoria, String descripcion,
                  String ciudad, LocalDateTime fechaHora, Recinto recinto) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.fechaHora = fechaHora;
        this.recinto = recinto;
        this.estado = EstadoEvento.BORRADOR;
        this.observadores = new ArrayList<>();
    }

    @Override
    public void suscribir(IObservador o) {
        observadores.add(o);
    }

    @Override
    public void desuscribir(IObservador o) {
        observadores.remove(o);
    }

    @Override
    public void notificar(String tipoEvento) {
        for (IObservador o : observadores) {
            o.actualizar(tipoEvento, this);
        }
    }

    public void publicar() {
        this.estado = EstadoEvento.PUBLICADO;
        notificar("EVENTO_PUBLICADO");
    }

    public void pausar() {
        this.estado = EstadoEvento.PAUSADO;
        notificar("EVENTO_PAUSADO");
    }

    public void cancelar() {
        this.estado = EstadoEvento.CANCELADO;
        notificar("EVENTO_CANCELADO");
    }

    public void finalizar() {
        this.estado = EstadoEvento.FINALIZADO;
        notificar("EVENTO_FINALIZADO");
    }

    public int getIdEvento() { return idEvento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public EstadoEvento getEstado() { return estado; }
    public void setEstado(EstadoEvento estado) { this.estado = estado; }

    public Recinto getRecinto() { return recinto; }
    public void setRecinto(Recinto recinto) { this.recinto = recinto; }

    public List<IObservador> getObservadores() { return observadores; }
}