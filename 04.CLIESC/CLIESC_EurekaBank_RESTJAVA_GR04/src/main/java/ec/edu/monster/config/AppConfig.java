package ec.edu.monster.config;

public class AppConfig {
  /**
   * URL base del servicio REST Java (CoreBancarioResource).
   * Ajusta si el servidor corre en otro host/puerto/contexto.
   */
  public static final String BASE_URL =
      System.getenv().getOrDefault(
          "EB_API_BASE",
          "http://localhost:8080/WSEurekaBank_Restfull_Java_G4/resources/corebancario");

  /** Activa trazas en consola cuando es true. */
  public static final boolean DEBUG = true;

}
