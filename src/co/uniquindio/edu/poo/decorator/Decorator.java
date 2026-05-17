package co.uniquindio.edu.poo.decorator;

public class Decorator {
    // esto es para el MAIN DECORATOR
    IEntrada entrada = new EntradaBase("Entrada Base", 100000);
    entrada = new AccesoVIPDecorator(entrada);
    entrada = new SeguroCancelacionDecorator(entrada);
    entrada = new MerchandisingDecorator(entrada);
    System.out.println(entrada.getDescription());
    System.out.println(entrada.getPrecio());


}
