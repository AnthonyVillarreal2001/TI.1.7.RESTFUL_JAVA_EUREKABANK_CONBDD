package ec.edu.monster.ws;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Registra la raíz de los servicios REST:
 * /WSEurekaBank_Restfull_Java_G4/resources/...
 */
@ApplicationPath("resources")
public class ApplicationConfig extends Application {
    // vacío está bien
}
