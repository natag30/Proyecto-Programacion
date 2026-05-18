package co.uniquindio.edu.proyecto_final_jfx.model.patrones.creacionales.factory;

public abstract class EventoFactory {
    public abstract Evento crearEvento(
            String nombre,
            String ciudad,
            double precioBase
    );

}

