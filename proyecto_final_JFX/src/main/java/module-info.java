module co.uniquindio.edu.proyecto_final_jfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.uniquindio.edu.proyecto_final_jfx to javafx.fxml;
    exports co.uniquindio.edu.proyecto_final_jfx;
}