package co.uniquindio.edu.proyecto_final_jfx.model.patrones.creacionales.singleton;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlataformaEventos implements IColeccionEventos {

    //Contiene el singleton

    private List<Evento> eventos = new ArrayList<>();

    public void agregarEvento(Evento evento){
        eventos.add(evento);
    }

    @Override
    public IIteradorEventos crearIterator() {
        return new IteradorCategoria(eventos,null);
    }

    public IIteradorEventos crearIteradorPorCategoria(Categoria filtro) {
        return new IteradorCategoria(eventos, filtro);
    }

    public IIteradorEventos crearIteradorPorCiudad(String ciudad) {
        return new IteradorCiudad(eventos, ciudad);
    }

    public IIteradorEventos crearIteradorPorFecha(LocalDate desde, LocalDate hasta) {
        return new IteradorFecha(eventos, desde, hasta);
    }

    public List<Evento> getEventos() {
        return eventos;
    }

}
