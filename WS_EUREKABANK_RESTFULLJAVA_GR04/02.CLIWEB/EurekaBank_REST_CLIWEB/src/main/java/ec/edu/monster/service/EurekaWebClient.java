package ec.edu.monster.service;

import ec.edu.monster.model.Movimiento;
import ec.edu.monster.model.OperacionCuentaResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class EurekaWebClient {

    private static final String BASE_URL = "http://localhost:8080/WSEurekaBank_Restfull_Java_G4/resources/corebancario";
    private static final Client client = ClientBuilder.newClient();

    // ========= LOGIN =========
    public static String validarIngreso(String usuario, String password) {
        String url = BASE_URL + "/login?usuario=" + usuario + "&password=" + password;
        Response response = client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .post(null);

        if (response.getStatus() == 200) {
            String json = response.readEntity(String.class);
            return json.contains("Exitoso") ? "Exitoso" : "Denegado";
        } else {
            return "Error";
        }
    }

    // ========= MOVIMIENTOS =========
    public static List<Movimiento> traerMovimientos(String cuenta) {
        String url = BASE_URL + "/movimientos/" + cuenta;
        Response response = client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<List<Movimiento>>() {});
        }
        return null;
    }

    // ========= DEPÓSITO =========
    public static OperacionCuentaResponse regDeposito(String cuenta, double importe) {
        String url = BASE_URL + "/deposito?cuenta=" + cuenta + "&importe=" + importe;
        Response response = client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(""));

        return response.readEntity(OperacionCuentaResponse.class);
    }

    // ========= RETIRO =========
    public static OperacionCuentaResponse regRetiro(String cuenta, double importe) {
        String url = BASE_URL + "/retiro?cuenta=" + cuenta + "&importe=" + importe;
        Response response = client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(""));

        return response.readEntity(OperacionCuentaResponse.class);
    }

    // ========= TRANSFERENCIA =========
    public static OperacionCuentaResponse regTransferencia(String cuentaOrigen, String cuentaDestino, double importe) {
        String url = BASE_URL + "/transferencia?cuentaOrigen=" + cuentaOrigen +
                "&cuentaDestino=" + cuentaDestino + "&importe=" + importe;
        Response response = client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(""));

        return response.readEntity(OperacionCuentaResponse.class);
    }
}
