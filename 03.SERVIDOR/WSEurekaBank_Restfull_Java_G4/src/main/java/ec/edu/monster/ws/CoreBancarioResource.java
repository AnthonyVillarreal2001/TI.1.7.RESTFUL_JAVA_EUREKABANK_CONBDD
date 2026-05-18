package ec.edu.monster.ws;

import ec.edu.monster.modelo.Movimiento;
import ec.edu.monster.modelo.OperacionCuentaResponse;
import ec.edu.monster.servicio.EurekaService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("corebancario")
@Produces(MediaType.APPLICATION_JSON)
public class CoreBancarioResource {

    private final EurekaService service = new EurekaService();

    // ========= LOGIN =========
    @POST
    @Path("login")
    public Response login(@QueryParam("usuario") String usuario,
            @QueryParam("password") String password) {
        boolean ok = service.validarIngreso(usuario, password);
        String resultado = ok ? "Exitoso" : "Denegado";
        return Response.ok("{\"resultado\":\"" + resultado + "\"}").build();
        // Si prefieres, puedes devolver un objeto con booleano
    }

    // ========= DEPÓSITO =========
    @POST
    @Path("deposito")
    public Response registrarDeposito(@QueryParam("cuenta") String cuenta,
            @QueryParam("importe") double importe) {

        String codEmp = "0001";
        OperacionCuentaResponse resp = new OperacionCuentaResponse();

        try {
            double saldo = service.registrarDeposito(cuenta, importe, codEmp);
            resp.setEstado(1);
            resp.setSaldo(saldo);
            return Response.ok(resp).build();
        } catch (Exception e) {
            resp.setEstado(-1);
            resp.setSaldo(-1);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(resp)
                    .build();
        }
    }

    // ========= RETIRO =========
    @POST
    @Path("retiro")
    public Response registrarRetiro(@QueryParam("cuenta") String cuenta,
            @QueryParam("importe") double importe) {

        String codEmp = "0004";
        OperacionCuentaResponse resp = new OperacionCuentaResponse();

        try {
            double saldo = service.registrarRetiro(cuenta, importe, codEmp);
            resp.setEstado(1);
            resp.setSaldo(saldo);
            return Response.ok(resp).build();
        } catch (Exception e) {
            resp.setEstado(-1);
            resp.setSaldo(-1);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(resp)
                    .build();
        }
    }

    // ========= TRANSFERENCIA =========
    @POST
    @Path("transferencia")
    public Response registrarTransferencia(@QueryParam("cuentaOrigen") String cuentaOrigen,
            @QueryParam("cuentaDestino") String cuentaDestino,
            @QueryParam("importe") double importe) {

        String codEmp = "0004";
        OperacionCuentaResponse resp = new OperacionCuentaResponse();

        try {
            double saldoOrigen = service.registrarTransferencia(cuentaOrigen, cuentaDestino, importe, codEmp);
            resp.setEstado(1);
            resp.setSaldo(saldoOrigen);
            return Response.ok(resp).build();
        } catch (Exception e) {
            resp.setEstado(-1);
            resp.setSaldo(-1);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(resp)
                    .build();
        }
    }

    // ========= MOVIMIENTOS =========
    @GET
    @Path("movimientos/{cuenta}")
    public Response obtenerMovimientos(@PathParam("cuenta") String cuenta) {
        try {
            List<Movimiento> movimientos = service.leerMovimientos(cuenta);
            return Response.ok(movimientos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("ping")
    public Response ping() {
        return Response.ok("{\"msg\":\"ok\"}").build();
    }

}
