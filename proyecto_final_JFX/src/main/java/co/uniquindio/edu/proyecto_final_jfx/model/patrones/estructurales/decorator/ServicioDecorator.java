package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator;

public abstract class ServicioDecorator implements IEntrada{
    protected IEntrada entrada;

    public ServicioDecorator(IEntrada entrada) {
        this.entrada = entrada;
    }

    @Override
    public String getDescription() {
        return entrada.getDescription();
    }

    @Override
    public double getPrecio() {
        return entrada.getPrecio();
    }

}
