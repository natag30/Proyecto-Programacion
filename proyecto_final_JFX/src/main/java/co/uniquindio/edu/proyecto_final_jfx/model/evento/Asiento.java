package co.uniquindio.edu.proyecto_final_jfx.model.evento;

public class Asiento {

    private String numero;
    private boolean disponible;

    public Asiento(String numero){
        this.numero = numero;
        this.disponible = true;
    }

    public String getNumero() {
        return numero;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void ocupar() {
        disponible = false;
    }
}


