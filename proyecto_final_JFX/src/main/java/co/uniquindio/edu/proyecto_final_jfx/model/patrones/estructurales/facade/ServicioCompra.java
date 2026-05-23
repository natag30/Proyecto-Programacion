package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.facade;

import co.uniquindio.edu.proyecto_final_jfx.model.compra.Compra;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;

import java.util.List;

public class ServicioCompra {

    private ServicioPago servicioPago;
    private ServicioAsiento servicioAsiento;
    private ServicioEntrada servicioEntrada;

    public ServicioCompra() {
        this.servicioPago   = new ServicioPago();
        this.servicioAsiento = new ServicioAsiento();
        this.servicioEntrada = new ServicioEntrada();
    }

    public boolean procesarCompra(Compra compra, List<Asiento> asientos) {
        if (!servicioAsiento.reservarAsientos(asientos)) return false;
        if (!servicioPago.procesarPago(compra.getPago())) {
            servicioAsiento.liberarAsientos(asientos);
            return false;
        }
        servicioEntrada.generarEntrada(compra.getUsuario(), asientos);
        return true;
    }

    public boolean cancelarCompra(Compra compra, List<Asiento> asientos) {
        compra.cancelar();
        servicioAsiento.liberarAsientos(asientos);
        return true;
    }
}
