package co.uniquindio.edu.controller;

import co.uniquindio.edu.proyecto_final_jfx.model.compra.Compra;
import co.uniquindio.edu.proyecto_final_jfx.model.compra.Pago;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.IMetodoPago;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator.IEntrada;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.creacionales.singleton.PlataformaEventos;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;

import java.util.List;

public class CompraController {

    private final PlataformaEventos plataforma = PlataformaEventos.getInstancia();

    /**
     * Procesa una compra: ejecuta el pago y la registra en la plataforma si es exitosa.
     *
     * @param usuario   usuario que realiza la compra
     * @param evento    evento al que pertenece la compra
     * @param entrada   entrada decorada con los servicios elegidos
     * @param metodo    metodo de pago seleccionado
     * @return la compra creada, o null si el pago fallo
     */
    public Compra realizarCompra(Usuario usuario, Evento evento,
                                 IEntrada entrada, IMetodoPago metodo) {
        Pago pago = new Pago(entrada.getPrecio(), metodo);
        if (!pago.procesar()) return null;

        Compra compra = new Compra.Builder()
                .conUsuario(usuario)
                .conEvento(evento)
                .conEntrada(entrada)
                .conPago(pago)
                .build();

        evento.reservarLugar();
        plataforma.agregarCompra(compra);
        return compra;
    }

    /**
     * Retorna todas las compras registradas en la plataforma.
     *
     * @return lista de todas las compras
     */
    public List<Compra> getCompras() {
        return plataforma.getCompras();
    }

    /**
     * Retorna las compras que pertenecen a un usuario específico.
     *
     * @param usuario usuario del que se quieren ver las compras
     * @return lista de compras del usuario
     */
    public List<Compra> getComprasPorUsuario(Usuario usuario) {
        // stream() convierte la lista en un flujo de elementos
        // filter() retiene solo las compras cuyo idUsuario coincide
        // toList() recoge los resultados en una nueva lista
        return plataforma.getCompras().stream()
                .filter(c -> c.getUsuario().getIdUsuario() == usuario.getIdUsuario())
                .toList();
    }
}
