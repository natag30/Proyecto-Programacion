package co.uniquindio.edu.proyecto_final_jfx.controller;

import co.uniquindio.edu.proyecto_final_jfx.model.compra.Compra;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator.EntradaBase;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;

import java.io.*;
import java.util.List;

public class PersistenciaCompras {

    private static final String ARCHIVO = "compras.txt";

    public static void guardarCompras(List<Compra> compras) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Compra c : compras) {
                bw.write(
                    c.getIdCompra() + "|" +
                    c.getFechaCreacion() + "|" +
                    c.getEstado() + "|" +
                    c.getUsuario().getIdUsuario() + "|" +
                    c.getEvento().getNombre() + "|" +
                    c.getTotal() + "|" +
                    c.getEntradas().size()
                );
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cargarCompras(List<Compra> compras,
                                     List<Evento> eventos,
                                     List<Usuario> usuarios) {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split("\\|");
                if (p.length < 7) continue;

                int idUsuario = Integer.parseInt(p[3].trim());
                String nombreEvento = p[4].trim();
                double total = Double.parseDouble(p[5].trim());
                int numEntradas = Integer.parseInt(p[6].trim());

                Usuario usuario = null;
                for (Usuario u : usuarios) {
                    if (u.getIdUsuario() == idUsuario) { usuario = u; break; }
                }
                Evento evento = null;
                for (Evento e : eventos) {
                    if (e.getNombre().equals(nombreEvento)) { evento = e; break; }
                }
                if (usuario == null || evento == null) continue;

                int cantidad = Math.max(1, numEntradas);
                double precioCada = total / cantidad;

                Compra.Builder builder = new Compra.Builder()
                    .conUsuario(usuario)
                    .conEvento(evento);
                for (int i = 0; i < cantidad; i++) {
                    builder.conEntrada(new EntradaBase("Entrada " + (i + 1), precioCada));
                }
                compras.add(builder.build());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
