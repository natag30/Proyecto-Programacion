package co.uniquindio.edu.proyecto_final_jfx;

import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.*;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator.*;
import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {

        IEntrada entrada = new EntradaBase("Entrada Base", 100000);
        entrada = new AccesoVIPDecorator(entrada);
        entrada = new SeguroCancelacionDecorator(entrada);
        entrada = new MerchandisingDecorator(entrada);
        System.out.println(entrada.getDescription());
        System.out.println(entrada.getPrecio());

        PSE pse = new PSE();

        IMetodoPago pagoPSE = new PSEAdapter(pse);

        pagoPSE.realizarPago(100000);

        TarjetaCredito tarjeta = new TarjetaCredito();

        IMetodoPago pagoTarjeta = new TarjetaAdapter(tarjeta);

        pagoTarjeta.realizarPago(100000);


    }
}
