package co.uniquindio.edu.proyecto_final_jfx;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Recinto;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator.IIteradorEventos;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.creacionales.singleton.PlataformaEventos;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Administrador;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PlataformaEventosTest {

    @BeforeEach
    void resetSingleton() throws Exception {
        Field f = PlataformaEventos.class.getDeclaredField("instancia");
        f.setAccessible(true);
        f.set(null, null);
    }

    private Evento crearEvento(String nombre, Categoria cat, String ciudad, int diasDesdeHoy) {
        Recinto r = new Recinto("R", "Dir", ciudad);
        Zona z = new Zona("General", 5, 50000);
        for (int i = 1; i <= 5; i++) z.agregarAsiento(new Asiento("A", i, z));
        r.agregarZona(z);
        return new Evento(nombre, cat, ciudad, LocalDateTime.now().plusDays(diasDesdeHoy), r);
    }

    @Test
    void getInstancia_retornaMismaInstancia() {
        PlataformaEventos p1 = PlataformaEventos.getInstancia();
        PlataformaEventos p2 = PlataformaEventos.getInstancia();
        assertSame(p1, p2);
    }

    @Test
    void agregarEvento_incrementaLista() {
        PlataformaEventos plataforma = PlataformaEventos.getInstancia();
        int antes = plataforma.getEventos().size();
        plataforma.agregarEvento(crearEvento("Test", Categoria.CONCIERTO, "Armenia", 10));
        assertEquals(antes + 1, plataforma.getEventos().size());
    }

    @Test
    void buscarUsuarioPorCorreo_encuentraUsuario() {
        PlataformaEventos plataforma = PlataformaEventos.getInstancia();
        Usuario u = new Usuario("Laura", 1, "laura@test.com", 123L, "laura", "1234");
        plataforma.agregarUsuario(u);
        Usuario encontrado = plataforma.buscarUsuarioPorCorreo("laura@test.com");
        assertNotNull(encontrado);
        assertEquals("Laura", encontrado.getNombre());
    }

    @Test
    void buscarUsuarioPorCorreo_noEncuentra() {
        PlataformaEventos plataforma = PlataformaEventos.getInstancia();
        assertNull(plataforma.buscarUsuarioPorCorreo("noexiste@test.com"));
    }

    @Test
    void buscarAdministrador_credencialesCorrectas() {
        PlataformaEventos plataforma = PlataformaEventos.getInstancia();
        Administrador admin = new Administrador("Admin", "a@a.com", 0, 1, "admin", "pass");
        plataforma.agregarAdministrador(admin);
        Administrador encontrado = plataforma.buscarAdministrador("admin", "pass");
        assertNotNull(encontrado);
    }

    @Test
    void iteradorPorCategoria_filtraCorrectamente() {
        PlataformaEventos plataforma = PlataformaEventos.getInstancia();
        plataforma.agregarEvento(crearEvento("C1", Categoria.CONCIERTO, "Armenia", 5));
        plataforma.agregarEvento(crearEvento("C2", Categoria.CONCIERTO, "Pereira", 10));
        plataforma.agregarEvento(crearEvento("T1", Categoria.TEATRO, "Manizales", 15));

        IIteradorEventos it = plataforma.crearIteradorPorCategoria(Categoria.CONCIERTO);
        int count = 0;
        while (it.hasNext()) { it.next(); count++; }
        assertEquals(2, count);
    }

    @Test
    void iteradorPorCiudad_filtraCorrectamente() {
        PlataformaEventos plataforma = PlataformaEventos.getInstancia();
        plataforma.agregarEvento(crearEvento("E1", Categoria.CONCIERTO, "Armenia", 5));
        plataforma.agregarEvento(crearEvento("E2", Categoria.TEATRO, "Armenia", 10));
        plataforma.agregarEvento(crearEvento("E3", Categoria.CONFERENCIA, "Pereira", 15));

        IIteradorEventos it = plataforma.crearIteradorPorCiudad("Armenia");
        int count = 0;
        while (it.hasNext()) { it.next(); count++; }
        assertEquals(2, count);
    }

    @Test
    void iteradorPorFecha_filtraCorrectamente() {
        PlataformaEventos plataforma = PlataformaEventos.getInstancia();
        plataforma.agregarEvento(crearEvento("Dentro", Categoria.CONCIERTO, "Armenia", 5));
        plataforma.agregarEvento(crearEvento("Fuera",  Categoria.TEATRO,    "Pereira", 60));

        LocalDate desde = LocalDate.now();
        LocalDate hasta = LocalDate.now().plusDays(10);
        IIteradorEventos it = plataforma.crearIteradorPorFecha(desde, hasta);
        int count = 0;
        while (it.hasNext()) { it.next(); count++; }
        assertEquals(1, count);
    }
}
