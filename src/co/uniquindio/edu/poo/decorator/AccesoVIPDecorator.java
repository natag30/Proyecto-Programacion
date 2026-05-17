package co.uniquindio.edu.poo.decorator;

public class AccesoVIPDecorator extends ServicioDecorator{
    public AccesoVIPDecorator(IEntrada entrada) {
        super(entrada);
    }

    @Override
    public String getDescription() {
        return entrada.getDescription()+", con Acceso VIP";
    }


    @Override
    public double getPrecio() {
        return entrada.getPrecio() + 50.0;
    }

}
