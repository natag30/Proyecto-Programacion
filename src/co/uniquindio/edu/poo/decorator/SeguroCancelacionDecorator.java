package co.uniquindio.edu.poo.decorator;

public class SeguroCancelacionDecorator extends ServicioDecorator{
    public SeguroCancelacionDecorator(IEntrada entrada) {
        super(entrada);
    }

    @Override
    public String getDescription() {
        return entrada.getDescription() + ", con Seguro de Cancelación";
    }

    @Override
    public double getPrecio() {
        return entrada.getPrecio() + 15.0;
    }

}
