package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator;

public class MerchandisingDecorator extends ServicioDecorator{
    public MerchandisingDecorator(IEntrada entrada) {
        super(entrada);
    }

    @Override
    public String getDescription() {
        return entrada.getDescription() + ", con Merchandising";
    }

    @Override
    public double getPrecio() {
        return entrada.getPrecio() + 20.0;
    }

}
