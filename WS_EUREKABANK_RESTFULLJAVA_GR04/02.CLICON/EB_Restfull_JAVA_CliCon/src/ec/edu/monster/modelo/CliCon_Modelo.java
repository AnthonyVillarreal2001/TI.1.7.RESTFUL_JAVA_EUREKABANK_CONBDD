package ec.edu.monster.modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONObject;

public class CliCon_Modelo {

    private static final String BASE_URL = "http://localhost:8080/WSEurekaBank_Restfull_Java_G4/resources/corebancario";

    // ========= LOGIN =========
    public boolean login(String usuario, String password) throws Exception {
        String endpoint = BASE_URL 
                + "/login?usuario=" + URLEncoder.encode(usuario, "UTF-8")
                + "&password=" + URLEncoder.encode(password, "UTF-8");

        URL url = new URL(endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            String response = leerRespuesta(con);
            JSONObject jsonResponse = new JSONObject(response);
            String resultado = jsonResponse.getString("resultado");
            return "Exitoso".equalsIgnoreCase(resultado);
        } else {
            throw new RuntimeException("Error al consumir el servicio de login. Código: " + responseCode);
        }
    }

    // ========= DEPÓSITO =========
    public String registrarDeposito(String cuenta, double importe) throws Exception {
        String endpoint = BASE_URL + "/deposito?cuenta=" + cuenta + "&importe=" + importe;
        URL url = new URL(endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            String response = leerRespuesta(con);
            JSONObject jsonResponse = new JSONObject(response);
            int estado = jsonResponse.getInt("estado");
            double saldo = jsonResponse.getDouble("saldo");

            if (estado == 1) {
                return "\033[32mDepósito registrado exitosamente. Saldo actual: " 
                        + String.format("%.2f", saldo) + "\033[0m";
            } else {
                return "\033[31mError al registrar depósito.\033[0m";
            }
        } else {
            throw new RuntimeException("Error al consumir el servicio. Código: " + responseCode);
        }
    }

    // ========= RETIRO =========
    public String registrarRetiro(String cuenta, double importe) throws Exception {
        String endpoint = BASE_URL + "/retiro?cuenta=" + cuenta + "&importe=" + importe;
        URL url = new URL(endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            String response = leerRespuesta(con);
            JSONObject jsonResponse = new JSONObject(response);
            int estado = jsonResponse.getInt("estado");
            double saldo = jsonResponse.getDouble("saldo");

            if (estado == 1) {
                return "\033[32mRetiro registrado exitosamente. Saldo actual: "
                        + String.format("%.2f", saldo) + "\033[0m";
            } else {
                return "\033[31mError al registrar retiro.\033[0m";
            }
        } else {
            throw new RuntimeException("Error al consumir el servicio. Código: " + responseCode);
        }
    }

    // ========= TRANSFERENCIA =========
    public String registrarTransferencia(String cuentaOrigen, String cuentaDestino, double importe) throws Exception {
        String endpoint = BASE_URL 
                + "/transferencia?cuentaOrigen=" + cuentaOrigen 
                + "&cuentaDestino=" + cuentaDestino 
                + "&importe=" + importe;

        URL url = new URL(endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            String response = leerRespuesta(con);
            JSONObject jsonResponse = new JSONObject(response);
            int estado = jsonResponse.getInt("estado");
            double saldoOrigen = jsonResponse.getDouble("saldo");

            if (estado == 1) {
                return "\033[32mTransferencia registrada exitosamente.\n"
                        + "Saldo actual en cuenta origen: " 
                        + String.format("%.2f", saldoOrigen) + "\033[0m";
            } else {
                return "\033[31mError al registrar transferencia.\033[0m";
            }
        } else {
            throw new RuntimeException("Error al consumir el servicio. Código: " + responseCode);
        }
    }

    // ========= MOVIMIENTOS =========
    public JSONArray obtenerMovimientos(String cuenta) throws Exception {
        String endpoint = BASE_URL + "/movimientos/" + cuenta;
        URL url = new URL(endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            String response = leerRespuesta(con);
            return new JSONArray(response);
        } else {
            throw new RuntimeException("Error al consumir el servicio. Código: " + responseCode);
        }
    }

    // ========= MÉTODO AUXILIAR =========
    private String leerRespuesta(HttpURLConnection con) throws Exception {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
}
