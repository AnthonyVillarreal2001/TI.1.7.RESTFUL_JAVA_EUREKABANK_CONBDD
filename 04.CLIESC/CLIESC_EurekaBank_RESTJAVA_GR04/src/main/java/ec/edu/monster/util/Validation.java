package ec.edu.monster.util;

public final class Validation {

  private Validation(){}

  /** chr_cuencodigo del ejemplo: 8 dígitos. Ajusta si tu BD usa otro formato. */
  public static boolean isCuentaValida(String cta){
    if (cta == null) return false;
    String s = cta.trim();
    return s.matches("\\d{8}");
  }

  /** Convierte "10,50" o "10.50" a double y valida > 0. */
  public static double parseImportePositivo(String txt) {
    if (txt == null) throw new IllegalArgumentException("Ingrese un importe.");
    String s = txt.trim().replace(',', '.');
    if (s.isEmpty()) throw new IllegalArgumentException("Ingrese un importe.");
    double v;
    try { v = Double.parseDouble(s); }
    catch (NumberFormatException ex){ throw new IllegalArgumentException("Importe no válido."); }
    if (v <= 0) throw new IllegalArgumentException("El importe debe ser mayor a 0.");
    if (!Double.isFinite(v)) throw new IllegalArgumentException("Importe fuera de rango.");
    return v;
  }
}
