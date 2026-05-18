package co.uniquindio.edu.proyecto_final_jfx.model.patrones.creacionales.factory;

public class ConciertoFactory extends EventoFactory {

        public Evento crearEvento(String nombre,
                                  String ciudad,
                                  double precioBase) {

            Evento concierto = new Evento();

            concierto.setNombre(nombre);
            concierto.setCiudad(ciudad);
            concierto.setPrecioBase(precioBase);
            concierto.setCategoria("Concierto");

            return concierto;
        }
    }


