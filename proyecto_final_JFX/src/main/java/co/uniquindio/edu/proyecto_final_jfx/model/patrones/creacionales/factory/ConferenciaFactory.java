package co.uniquindio.edu.proyecto_final_jfx.model.patrones.creacionales.factory;

public class ConferenciaFactory extends EventoFactory{

        public Evento crearEvento(String nombre,
                                  String ciudad,
                                  double precioBase) {

            Evento conferencia = new Evento();

            conferencia.setNombre(nombre);
            conferencia.setCiudad(ciudad);
            conferencia.setPrecioBase(precioBase);
            conferencia.setCategoria("Conferencia");

            return conferencia;
        }
    }
