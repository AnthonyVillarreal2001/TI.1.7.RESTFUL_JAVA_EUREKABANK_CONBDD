package ec.edu.monster.service;

import com.google.gson.*;
import ec.edu.monster.config.AppConfig;
import ec.edu.monster.model.Movimiento;
import ec.edu.monster.model.OperacionCuentaResponse;
import ec.edu.monster.util.Debug;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Cliente REST que consume el recurso CoreBancarioResource del servidor Java.
 */
public class RestEurekaApi implements EurekaApi {

  private final HttpClient http = HttpClient.newBuilder()
      .connectTimeout(Duration.ofSeconds(5))
      .build();

  private final Gson gson = new GsonBuilder()
      .setLenient()
      .create();

  private static String qs(Map<String, String> params) {
    if (params == null || params.isEmpty()) return "";
    StringJoiner sj = new StringJoiner("&");
    params.forEach((k, v) -> sj.add(URLEncoder.encode(k, StandardCharsets.UTF_8)
        + "=" + URLEncoder.encode(v, StandardCharsets.UTF_8)));
    return sj.toString();
  }

  private HttpResponse<String> send(HttpRequest req) throws IOException, InterruptedException {
    HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
    if (res.statusCode() >= 200 && res.statusCode() < 300) return res;
    throw new IOException(req.uri() + " -> " + res.statusCode() + " | " + res.body());
  }

  @Override
  public boolean validarIngreso(String usuario, String password) throws Exception {
    String uri = AppConfig.BASE_URL + "/login?" + qs(Map.of(
        "usuario", usuario,
        "password", password));

    Debug.log("POST " + uri);
    HttpRequest req = HttpRequest.newBuilder(URI.create(uri))
        .timeout(Duration.ofSeconds(10))
        .header("Accept", "application/json")
        .POST(HttpRequest.BodyPublishers.noBody())
        .build();
    JsonObject json = JsonParser.parseString(send(req).body()).getAsJsonObject();
    String res = json.has("resultado") ? json.get("resultado").getAsString() : "";
    Debug.log("Respuesta login=" + res);
    return "Exitoso".equalsIgnoreCase(res);
  }

  @Override
  public List<Movimiento> traerMovimientos(String cuenta) throws Exception {
    String uri = AppConfig.BASE_URL + "/movimientos/" +
        URLEncoder.encode(cuenta, StandardCharsets.UTF_8);

    Debug.log("GET " + uri);
    HttpRequest req = HttpRequest.newBuilder(URI.create(uri))
        .timeout(Duration.ofSeconds(10))
        .header("Accept", "application/json")
        .GET()
        .build();

    String body = send(req).body();
    JsonArray arr = JsonParser.parseString(body).getAsJsonArray();

    List<Movimiento> list = new ArrayList<>();
    for (JsonElement el : arr) {
      JsonObject o = el.getAsJsonObject();
      Movimiento m = new Movimiento();
      m.setCuenta(optStr(o, "cuenta", "cuentaCodigo"));
      m.setNromov(optInt(o, "nromov", "numero"));
      m.setFecha(optStr(o, "fechaAsString", "fecha"));
      m.setTipo(optStr(o, "tipo", "tipoCodigo"));
      m.setAccion(optStr(o, "accion", "accionTexto"));
      m.setImporte(optDouble(o, "importe"));
      list.add(m);
    }
    Debug.log("Movimientos recibidos: " + list.size());
    return list;
  }

  @Override
  public OperacionCuentaResponse regDeposito(String cuenta, double importe) throws Exception {
    return doOperacion("/deposito", Map.of(
        "cuenta", cuenta,
        "importe", String.valueOf(importe)));
  }

  @Override
  public OperacionCuentaResponse regRetiro(String cuenta, double importe) throws Exception {
    return doOperacion("/retiro", Map.of(
        "cuenta", cuenta,
        "importe", String.valueOf(importe)));
  }

  @Override
  public OperacionCuentaResponse regTransferencia(String origen, String destino, double importe) throws Exception {
    return doOperacion("/transferencia", Map.of(
        "cuentaOrigen", origen,
        "cuentaDestino", destino,
        "importe", String.valueOf(importe)));
  }

  private OperacionCuentaResponse doOperacion(String path, Map<String, String> params) throws Exception {
    String uri = AppConfig.BASE_URL + path + "?" + qs(params);
    Debug.log("POST " + uri);
    HttpRequest req = HttpRequest.newBuilder(URI.create(uri))
        .timeout(Duration.ofSeconds(10))
        .header("Accept", "application/json")
        .POST(HttpRequest.BodyPublishers.noBody())
        .build();

    String body = send(req).body();
    JsonObject o = JsonParser.parseString(body).getAsJsonObject();
    OperacionCuentaResponse r = new OperacionCuentaResponse();
    r.setEstado(o.has("estado") ? o.get("estado").getAsInt() : -1);
    r.setSaldo(o.has("saldo") ? o.get("saldo").getAsDouble() : -1);
    Debug.log("Respuesta op estado=" + r.getEstado() + " saldo=" + r.getSaldo());
    return r;
  }

  private static String optStr(JsonObject o, String... keys) {
    for (String k : keys) if (o.has(k) && !o.get(k).isJsonNull()) return o.get(k).getAsString();
    return "";
  }

  private static int optInt(JsonObject o, String... keys) {
    for (String k : keys) if (o.has(k) && !o.get(k).isJsonNull()) return o.get(k).getAsInt();
    return 0;
  }

  private static double optDouble(JsonObject o, String... keys) {
    for (String k : keys) if (o.has(k) && !o.get(k).isJsonNull()) return o.get(k).getAsDouble();
    return 0;
  }
}
