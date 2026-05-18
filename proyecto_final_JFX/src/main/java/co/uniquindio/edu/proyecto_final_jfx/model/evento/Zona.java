package co.uniquindio.edu.proyecto_final_jfx.model.evento;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.EstadoAsiento;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una sección dentro de un recinto (por ejemplo "Piso", "Palco" o "Tribuna").
 */
public class Zona {

    // nombre de la zona
    private String nombre;

    // cantidad total de asientos en esta zona
    private int capacidad;

    // precio base de las entradas para esta zona
    private double precioBase;

    // lista de asientos que tiene la zona
    private List<Asiento> asientos;

    /**
     * Crea una zona con nombre, capacidad y precio base. La lista de asientos empieza vacía.
     *
     * @param nombre     nombre de la zona
     * @param capacidad  número total de asientos
     * @param precioBase precio inicial de las entradas de esta zona
     */
    public Zona(String nombre, int capacidad, double precioBase) {
        this.nombre     = nombre;
        this.capacidad  = capacidad;
        this.precioBase = precioBase;
        this.asientos   = new ArrayList<>();
    }

    /**
     * Agrega un asiento a la zona.
     *
     * @param a el asiento que se quiere agregar
     */
    public void agregarAsiento(Asiento a) {
        asientos.add(a);
    }

    /**
     * Retorna la lista de asientos que están disponibles para comprar.
     *
     * @return lista de asientos con estado DISPONIBLE
     */
    public List<Asiento> getAsientosDisponibles() {
        return asientos.stream()
                .filter(a -> a.getEstado() == EstadoAsiento.DISPONIBLE)
                .toList();
    }

    /**
     * Retorna cuántos asientos de esta zona ya fueron vendidos.
     *
     * @return número de asientos con estado VENDIDO
     */
    public int getOcupacion() {
        return (int) asientos.stream()
                .filter(a -> a.getEstado() == EstadoAsiento.VENDIDO)
                .count();
    }

    /** @return nombre de la zona */
    public String getNombre()          { return nombre; }

    /** @return capacidad total de la zona */
    public int getCapacidad()          { return capacidad; }

    /** @return precio base de las entradas de esta zona */
    public double getPrecioBase()      { return precioBase; }

    /** @return lista de todos los asientos de la zona */
    public List<Asiento> getAsientos() { return asientos; }

    /**
     * Cambia el nombre de la zona.
     *
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre)        { this.nombre = nombre; }

    /**
     * Cambia la capacidad de la zona.
     *
     * @param capacidad nueva capacidad
     */
    public void setCapacidad(int capacidad)     { this.capacidad = capacidad; }

    /**
     * Cambia el precio base de la zona.
     *
     * @param precioBase nuevo precio base
     */
    public void setPrecioBase(double precioBase){ this.precioBase = precioBase; }

    @Override
    public String toString() {
        return "Zona{nombre='" + nombre + '\'' +
                ", capacidad=" + capacidad +
                ", precioBase=" + precioBase + '}';
    }
}