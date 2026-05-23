package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator;

import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;

public class EntradaBase implements IEntrada {
    private String  description;
    private double  precio;
    private Asiento asiento;

    public EntradaBase(String descripcion, double precio) {
        this.description = descripcion;
        this.precio      = precio;
        this.asiento     = null;
    }

    public EntradaBase(String descripcion, double precio, Asiento asiento) {
        this.description = descripcion;
        this.precio      = precio;
        this.asiento     = asiento;
    }

    @Override
    public String getDescription() { return description; }

    @Override
    public double getPrecio() { return precio; }

    public Asiento getAsiento() { return asiento; }
}
