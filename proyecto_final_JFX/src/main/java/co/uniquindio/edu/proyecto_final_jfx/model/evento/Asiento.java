package co.uniquindio.edu.proyecto_final_jfx.model.evento;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.EstadoAsiento;

public class Asiento {

    private static int contador = 0;

    private int idAsiento;
    private String fila;
    private int numero;
    private EstadoAsiento estado;
    private Zona zona;

    /**
     * Crea un asiento con su fila, número y zona, y lo deja disponible.
     *
     * @param fila   letra o nombre de la fila donde está el asiento
     * @param numero número del asiento dentro de la fila
     * @param zona   zona del recinto a la que pertenece el asiento
     */
    public Asiento(String fila, int numero, Zona zona) {
        this.idAsiento = ++contador;
        this.fila      = fila;
        this.numero    = numero;
        this.zona      = zona;
        this.estado    = EstadoAsiento.DISPONIBLE;
    }

    /**
     * Indica si el asiento está disponible para ser ocupado.
     *
     * @return true si el estado es DISPONIBLE
     */
    public boolean isDisponible() {
        return estado == EstadoAsiento.DISPONIBLE;
    }

    /**
     * Ocupa el asiento directamente cambiando su estado a VENDIDO.
     * Usado por el facade ServicioAsiento para compras inmediatas.
     *
     * @return true si se ocupó, false si no estaba disponible
     */
    public boolean ocupar() {
        if (estado != EstadoAsiento.DISPONIBLE) return false;
        estado = EstadoAsiento.VENDIDO;
        return true;
    }

    /**
     * Cambia el estado del asiento a RESERVADO si está disponible.
     *
     * @return true si se reservó, false si ya estaba en otro estado
     */
    public boolean reservar() {
        if (estado != EstadoAsiento.DISPONIBLE) return false;
        estado = EstadoAsiento.RESERVADO;
        return true;
    }

    /**
     * Cambia el estado del asiento a VENDIDO si estaba reservado.
     *
     * @return true si se vendió, false si no estaba reservado
     */
    public boolean vender() {
        if (estado != EstadoAsiento.RESERVADO) return false;
        estado = EstadoAsiento.VENDIDO;
        return true;
    }

    /**
     * Cambia el estado del asiento a DISPONIBLE si estaba reservado o vendido.
     *
     * @return true si se liberó, false si el estado no lo permite
     */
    public boolean liberar() {
        if (estado != EstadoAsiento.RESERVADO && estado != EstadoAsiento.VENDIDO) return false;
        estado = EstadoAsiento.DISPONIBLE;
        return true;
    }

    /**
     * Cambia el estado del asiento a BLOQUEADO si está disponible.
     *
     * @return true si se bloqueó, false si no estaba disponible
     */
    public boolean bloquear() {
        if (estado != EstadoAsiento.DISPONIBLE) return false;
        estado = EstadoAsiento.BLOQUEADO;
        return true;
    }

    /**
     * Retorna el identificador único del asiento.
     *
     * @return id del asiento
     */
    public int getIdAsiento()       { return idAsiento; }

    /**
     * Retorna la fila en la que se encuentra el asiento.
     *
     * @return nombre o letra de la fila
     */
    public String getFila()         { return fila; }

    /**
     * Retorna el número del asiento dentro de su fila.
     *
     * @return número del asiento
     */
    public int getNumero()          { return numero; }

    /**
     * Retorna el estado actual del asiento.
     *
     * @return estado del asiento
     */
    public EstadoAsiento getEstado(){ return estado; }

    /**
     * Retorna la zona del recinto a la que pertenece el asiento.
     *
     * @return zona del asiento
     */
    public Zona getZona()           { return zona; }

    @Override
    public String toString() {
        return "Asiento{idAsiento=" + idAsiento +
                ", fila='" + fila + '\'' +
                ", numero=" + numero +
                ", estado=" + estado + '}';
    }
}