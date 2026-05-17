package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator;

public class EntradaBase implements IEntrada{
    private String description;
    private double precio;

    public EntradaBase(String descripcion, double precio) {
        this.description = descripcion;
        this.precio = precio;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrecio() {
        return precio;
    }



}
