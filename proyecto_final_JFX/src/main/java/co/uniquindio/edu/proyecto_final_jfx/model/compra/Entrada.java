package co.uniquindio.edu.proyecto_final_jfx.model.compra;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.EstadoEntrada;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;

public class Entrada {

    private static int contador = 0;

    private int idEntrada;
    private Zona zona;
    private Asiento asiento;
    private double precioFinal;
    private EstadoEntrada estado;

    /**
     * Crea una entrada con la zona, el asiento y el precio final, y la deja activa.
     *
     * @param zona        zona del recinto a la que corresponde la entrada
     * @param asiento     asiento asignado a la entrada
     * @param precioFinal precio que se cobra por la entrada
     */
    public Entrada(Zona zona, Asiento asiento, double precioFinal) {
        this.idEntrada   = ++contador;
        this.zona        = zona;
        this.asiento     = asiento;
        this.precioFinal = precioFinal;
        this.estado      = EstadoEntrada.ACTIVA;
    }

    /**
     * Cambia el estado de la entrada a USADA si estaba activa.
     *
     * @return true si se marcó como usada, false si ya no estaba activa
     */
    public boolean usar() {
        if (estado != EstadoEntrada.ACTIVA) return false;
        estado = EstadoEntrada.USADA;
        return true;
    }

    /**
     * Cambia el estado de la entrada a ANULADA si estaba activa.
     *
     * @return true si se anuló, false si ya no estaba activa
     */
    public boolean anular() {
        if (estado != EstadoEntrada.ACTIVA) return false;
        estado = EstadoEntrada.ANULADA;
        return true;
    }

    /**
     * Retorna el identificador único de la entrada.
     *
     * @return id de la entrada
     */
    public int getIdEntrada()         { return idEntrada; }

    /**
     * Retorna la zona a la que corresponde la entrada.
     *
     * @return zona de la entrada
     */
    public Zona getZona()             { return zona; }

    /**
     * Retorna el asiento asignado a la entrada.
     *
     * @return asiento de la entrada
     */
    public Asiento getAsiento()       { return asiento; }

    /**
     * Retorna el precio final de la entrada.
     *
     * @return precio final
     */
    public double getPrecioFinal()    { return precioFinal; }

    /**
     * Retorna el estado actual de la entrada.
     *
     * @return estado de la entrada
     */
    public EstadoEntrada getEstado()  { return estado; }

    @Override
    public String toString() {
        return "Entrada{idEntrada=" + idEntrada +
                ", zona=" + zona.getNombre() +
                ", asiento=" + asiento +
                ", precioFinal=" + precioFinal +
                ", estado=" + estado + '}';
    }
}