module co.uniquindio.edu.proyecto_final_jfx {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.uniquindio.edu.proyecto_final_jfx to javafx.fxml;
    opens co.uniquindio.edu.viewController to javafx.fxml;
    opens co.uniquindio.edu.controller to javafx.fxml;

    exports co.uniquindio.edu.proyecto_final_jfx;
    exports co.uniquindio.edu.viewController;
    exports co.uniquindio.edu.controller;
    exports co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.proxy;

    opens co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.proxy to javafx.fxml;
}