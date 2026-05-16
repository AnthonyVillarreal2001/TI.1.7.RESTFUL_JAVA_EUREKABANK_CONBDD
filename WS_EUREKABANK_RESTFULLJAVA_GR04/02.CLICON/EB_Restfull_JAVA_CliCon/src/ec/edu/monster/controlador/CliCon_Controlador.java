package ec.edu.monster.controlador;

import ec.edu.monster.modelo.CliCon_Modelo;
import org.json.JSONArray;
import org.json.JSONObject;

public class CliCon_Controlador {

    private final CliCon_Modelo modelo;

    public CliCon_Controlador() {
        this.modelo = new CliCon_Modelo();
    }

    // ========= LOGIN =========
    public boolean login(String usuario, String password) {
        try {
            return modelo.login(usuario, password);
        } catch (Exception e) {
            System.out.println("Error al validar usuario: " + e.getMessage());
            return false;
        }
    }

    public String registrarDeposito(String cuenta, double importe) {
        try {
            return modelo.registrarDeposito(cuenta, importe);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String registrarRetiro(String cuenta, double importe) {
        try {
            return modelo.registrarRetiro(cuenta, importe);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String registrarTransferencia(String cuentaOrigen, String cuentaDestino, double importe) {
        try {
            return modelo.registrarTransferencia(cuentaOrigen, cuentaDestino, importe);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String obtenerMovimientos(String cuenta) {
        try {
            JSONArray movimientos = modelo.obtenerMovimientos(cuenta);
            StringBuilder sb = new StringBuilder();

            sb.append("===========================================================================================================================\n");
            sb.append(String.format("%-20s %-20s %-25s %-15s %-20s %-20s\n",
                    "Cuenta", "Nro Movimiento", "Fecha", "Tipo", "           Acción", "     Importe"));
            sb.append("===========================================================================================================================\n");

            for (int i = 0; i < movimientos.length(); i++) {
                JSONObject mov = movimientos.getJSONObject(i);
                sb.append(String.format("%-20s %-20s %-25s %-25s %-15s %-10.2f%n",
                        mov.getString("cuenta"),
                        mov.getInt("nromov"),
                        mov.getString("fecha"),
                        mov.getString("tipo"),
                        mov.getString("accion"),
                        mov.getDouble("importe")));
            }
            return sb.toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
