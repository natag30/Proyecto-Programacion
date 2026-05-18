package co.uniquindio.edu.proyecto_final_jfx.model.patrones.creacionales.singleton;

import co.uniquindio.edu.proyecto_final_jfx.model.compra.Compra;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator.*;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer.PanelMetricas;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer.RegistroIncidencias;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Administrador;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Guarda todos los eventos del sistema y permite recorrerlos con distintos filtros.
 * Se usa Singleton porque la lista de eventos debe ser la misma para toda la aplicación,
 * y se usa Iterator para poder buscar eventos por categoría, ciudad o fecha sin exponer
 * la lista interna directamente.
 */
public class PlataformaEventos implements IColeccionEventos {

    private static PlataformaEventos instancia;

    private List<Evento> eventos                 = new ArrayList<>();
    private List<Usuario> usuarios               = new ArrayList<>();
    private List<Administrador> administradores  = new ArrayList<>();
    private List<Compra> compras                 = new ArrayList<>();
    private RegistroIncidencias registroIncidencias = new RegistroIncidencias();
    private PanelMetricas panelMetricas             = new PanelMetricas();

    /**
     * Retorna la única instancia de la plataforma. Si no existe, la crea.
     *
     * @return la instancia compartida de PlataformaEventos
     */
    public static PlataformaEventos getInstancia() {
        if (instancia == null) {
            instancia = new PlataformaEventos();
        }
        return instancia;
    }

    /**
     * Agrega un evento nuevo a la lista de la plataforma y lo suscribe a los observadores.
     *
     * @param evento el evento que se quiere registrar
     */
    public void agregarEvento(Evento evento) {
        evento.suscribir(panelMetricas);
        evento.suscribir(registroIncidencias);
        eventos.add(evento);
    }

    /**
     * Agrega un usuario al sistema.
     *
     * @param usuario usuario a registrar
     */
    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    /**
     * Agrega un administrador al sistema.
     *
     * @param administrador administrador a registrar
     */
    public void agregarAdministrador(Administrador administrador) {
        administradores.add(administrador);
    }

    /**
     * Agrega una compra al historial de la plataforma.
     *
     * @param compra compra a registrar
     */
    public void agregarCompra(Compra compra) {
        compras.add(compra);
    }

    /**
     * Busca un administrador por nombre de usuario y contraseña.
     *
     * @param usuario   nombre de usuario del administrador
     * @param contrasena contraseña de acceso
     * @return el administrador si las credenciales coinciden, null si no se encuentra
     */
    public Administrador buscarAdministrador(String usuario, String contrasena) {
        for (Administrador a : administradores) {
            if (a.getUsuario().equals(usuario) && a.getContraseña().equals(contrasena)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param correo correo del usuario a buscar
     * @return el usuario si existe, null si no se encuentra
     */
    public Usuario buscarUsuarioPorCorreo(String correo) {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Busca un usuario por correo o nombre de usuario, validando también la contraseña.
     *
     * @param identificador correo o nombre de usuario
     * @param contrasena contraseña ingresada
     * @return el usuario si las credenciales coinciden, null si no se encuentra
     */
    public Usuario buscarUsuarioPorCredenciales(String identificador, String contrasena) {
        for (Usuario u : usuarios) {
            boolean coincideCorreo = u.getCorreo().equalsIgnoreCase(identificador);
            boolean coincideUsuario = u.getUsuario() != null
                    && u.getUsuario().equalsIgnoreCase(identificador);

            if ((coincideCorreo || coincideUsuario) && u.getContraseña().equals(contrasena)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Crea un iterador que recorre todos los eventos sin filtrar por categoría.
     *
     * @return un iterador sobre todos los eventos
     */
    @Override
    public IIteradorEventos crearIterator() {
        return new IteradorCategoria(eventos, null);
    }

    /**
     * Crea un iterador que solo muestra los eventos de una categoría específica.
     *
     * @param filtro la categoría por la que se quiere filtrar
     * @return un iterador que recorre solo los eventos de esa categoría
     */
    public IIteradorEventos crearIteradorPorCategoria(Categoria filtro) {
        return new IteradorCategoria(eventos, filtro);
    }

    /**
     * Crea un iterador que solo muestra los eventos de una ciudad específica.
     *
     * @param ciudad el nombre de la ciudad por la que se quiere filtrar
     * @return un iterador que recorre solo los eventos de esa ciudad
     */
    public IIteradorEventos crearIteradorPorCiudad(String ciudad) {
        return new IteradorCiudad(eventos, ciudad);
    }

    /**
     * Crea un iterador que solo muestra los eventos dentro de un rango de fechas.
     *
     * @param desde fecha de inicio del rango (inclusive)
     * @param hasta fecha de fin del rango (inclusive)
     * @return un iterador que recorre los eventos en ese rango de fechas
     */
    public IIteradorEventos crearIteradorPorFecha(LocalDate desde, LocalDate hasta) {
        return new IteradorFecha(eventos, desde, hasta);
    }

    /**
     * Retorna la lista completa de eventos registrados en la plataforma.
     *
     * @return lista de todos los eventos
     */
    public List<Evento> getEventos() { return eventos; }

    /**
     * Retorna la lista de usuarios registrados.
     *
     * @return lista de usuarios
     */
    public List<Usuario> getUsuarios() { return usuarios; }

    /**
     * Retorna la lista de administradores registrados.
     *
     * @return lista de administradores
     */
    public List<Administrador> getAdministradores() { return administradores; }

    /**
     * Retorna el historial de compras de la plataforma.
     *
     * @return lista de compras
     */
    public List<Compra> getCompras() { return compras; }

    /**
     * Retorna el registro de incidencias del sistema.
     *
     * @return registro de incidencias
     */
    public RegistroIncidencias getRegistroIncidencias() { return registroIncidencias; }

    /**
     * Retorna el panel de métricas del sistema.
     *
     * @return panel de métricas
     */
    public PanelMetricas getPanelMetricas() { return panelMetricas; }
}