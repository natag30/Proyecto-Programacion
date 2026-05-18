package co.uniquindio.edu.proyecto_final_jfx.model.patrones.creacionales.singleton;

import co.uniquindio.edu.proyecto_final_jfx.model.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.Usuario;

import java.util.ArrayList;
import java.util.List;

    public class PlataformaEventos {

        private static PlataformaEventos instancia;

        private List<Usuario> listaUsuarios;
        private List<Evento> listaEventos;

        private PlataformaEventos() {

            listaUsuarios = new ArrayList<>();
            listaEventos = new ArrayList<>();
        }

        public static PlataformaEventos getInstancia() {

            if (instancia == null) {
                instancia = new PlataformaEventos();
            }

            return instancia;
        }

        public void agregarUsuario(Usuario usuario) {
            listaUsuarios.add(usuario);
        }

        public List<Usuario> getListaUsuarios() {
            return listaUsuarios;
        }

        public void agregarEvento(Evento evento) {
            listaEventos.add(evento);
        }

        public List<Evento> getListaEventos() {
            return listaEventos;
        }

    }
