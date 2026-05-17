package co.uniquindio.edu.proyecto_final_jfx;

import javafx.application.Application;
import main.java.co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.PSEAdapter;
import main.java.co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.TarjetaCredito;

public class Launcher {
    public static void main(String[] args) {








        //ADAPTER MAIN
        PSE pse = new PSE();

        IMetodoPago pagoPSE = new PSEAdapter(pse);

        pagoPSE.realizarPago(100000);

        TarjetaCredito tarjeta = new TarjetaCredito();

        IMetodoPago pagoTarjeta = new TarjetaAdapter(tarjeta);

        pagoTarjeta.realizarPago(100000);



    }
}
