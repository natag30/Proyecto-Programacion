package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.facade;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;

import java.util.List;

public class ServicioAsiento {

    /**
     * Verifica que todos los asientos estén disponibles y los ocupa.
     * Si alguno no está disponible, cancela toda la operación.
     */
    public boolean reservarAsientos(List<Asiento> asientos) {
        for (Asiento asiento : asientos) {
            if (!asiento.isDisponible()) {
                System.out.println("El Asiento " + asiento.getNumero() + " No esta Disponible");
                return false;
            }
        }
        for (Asiento asiento : asientos) {
            asiento.ocupar();
            System.out.println("Asiento reservado" + asiento.getNumero());
        }
        return true;
    }

    /**
     * Libera una lista de asientos, por ejemplo al cancelar una compra.
     * Solo libera los que estén en estado VENDIDO o RESERVADO.
     */
    public boolean liberarAsientos(List<Asiento> asientos) {
        for (Asiento asiento : asientos) {
            boolean liberado = asiento.liberar();
            if (!liberado) {
                System.out.println("El asiento " + asiento.getNumero() + " no se pudo liberar");
            }
        }
        return true;
    }

    /**
     * Verifica si un asiento específico está disponible.
     * Útil para consultar desde la vista antes de mostrar el mapa de asientos.
     */
    public boolean verificarDisponibilidad(Asiento asiento) {
        return asiento.isDisponible();
    }

    /**
     * Cuenta cuántos asientos disponibles hay en una lista.
     * Útil para mostrar disponibilidad en la vista de compra.
     */
    public int contarDisponibles(List<Asiento> asientos) {
        int contador = 0;
        for (Asiento asiento : asientos) {
            if (asiento.isDisponible()) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Bloquea una lista de asientos para que no puedan comprarse.
     * Solo bloquea los que estén disponibles.
     */
    public boolean bloquearAsientos(List<Asiento> asientos) {
        for (Asiento asiento : asientos) {
            if (!asiento.isDisponible()) {
                System.out.println("El asiento " + asiento.getNumero() + " no se puede bloquear");
                return false;
            }
        }
        for (Asiento asiento : asientos) {
            asiento.bloquear();
            System.out.println("Asiento bloqueado: " + asiento.getNumero());
        }
        return true;
    }
}