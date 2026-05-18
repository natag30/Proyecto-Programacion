package co.uniquindio.edu.proyecto_final_jfx.model.compra;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.TipoServicio;

public class ServicioAdicional {

    private String nombre;
    private double precio;
    private TipoServicio tipo;

    /**
     * Crea un servicio adicional con su nombre, precio y tipo.
     *
     * @param nombre nombre del servicio adicional
     * @param precio costo del servicio
     * @param tipo   tipo de servicio adicional
     */
    public ServicioAdicional(String nombre, double precio, TipoServicio tipo) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo   = tipo;
    }

    /**
     * Retorna el nombre del servicio adicional.
     *
     * @return nombre del servicio
     */
    public String getNombre()     { return nombre; }

    /**
     * Retorna el precio del servicio adicional.
     *
     * @return precio del servicio
     */
    public double getPrecio()     { return precio; }

    /**
     * Retorna el tipo de servicio adicional.
     *
     * @return tipo del servicio
     */
    public TipoServicio getTipo() { return tipo; }

    @Override
    public String toString() {
        return "ServicioAdicional{nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", tipo=" + tipo + '}';
    }
}
