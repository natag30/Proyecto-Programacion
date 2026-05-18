package co.uniquindio.edu.proyecto_final_jfx.model.evento;

import java.util.ArrayList;
import java.util.List;

public class Recinto {

    private String nombre;
    private String direccion;
    private String ciudad;
    private List<Zona> zonas;

    /**
     * Crea un recinto con nombre, dirección y ciudad. La lista de zonas empieza vacía.
     *
     * @param nombre    nombre del recinto
     * @param direccion dirección del recinto
     * @param ciudad    ciudad donde está el recinto
     */
    public Recinto(String nombre, String direccion, String ciudad) {
        this.nombre    = nombre;
        this.direccion = direccion;
        this.ciudad    = ciudad;
        this.zonas     = new ArrayList<>();
    }

    /**
     * Agrega una zona al recinto.
     *
     * @param z la zona que se quiere agregar
     */
    public void agregarZona(Zona z) {
        zonas.add(z);
    }

    /**
     * Calcula y retorna la capacidad total sumando los aforos de todas las zonas.
     *
     * @return total de lugares disponibles en el recinto
     */
    public int getCapacidadTotal() {
        return zonas.stream().mapToInt(Zona::getCapacidad).sum();
    }

    /** @return nombre del recinto */
    public String getNombre()      { return nombre; }

    /** @return dirección del recinto */
    public String getDireccion()   { return direccion; }

    /** @return ciudad donde está el recinto */
    public String getCiudad()      { return ciudad; }

    /** @return lista de zonas del recinto */
    public List<Zona> getZonas()   { return zonas; }

    /**
     * Cambia el nombre del recinto.
     *
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre)       { this.nombre = nombre; }

    /**
     * Cambia la dirección del recinto.
     *
     * @param direccion nueva dirección
     */
    public void setDireccion(String direccion) { this.direccion = direccion; }

    /**
     * Cambia la ciudad del recinto.
     *
     * @param ciudad nueva ciudad
     */
    public void setCiudad(String ciudad)       { this.ciudad = ciudad; }

    @Override
    public String toString() {
        return "Recinto{nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", zonas=" + zonas.size() + '}';
    }
}