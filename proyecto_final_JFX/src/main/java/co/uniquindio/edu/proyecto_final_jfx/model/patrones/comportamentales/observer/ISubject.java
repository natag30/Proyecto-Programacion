package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer;

public interface ISubject {
    void suscribir(IObservador o);
    void desuscribir(IObservador o);
    void notificar(String tipoEvento);
}
