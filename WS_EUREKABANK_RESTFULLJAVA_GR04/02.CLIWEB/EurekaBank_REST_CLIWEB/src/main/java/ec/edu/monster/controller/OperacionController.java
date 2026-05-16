package ec.edu.monster.controller;

import ec.edu.monster.service.EurekaWebClient;
import ec.edu.monster.model.OperacionCuentaResponse;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "OperacionController", urlPatterns = {"/operacion"})
public class OperacionController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String action = request.getParameter("action");
        String mensaje = null;
        Double saldo = null;
        String cuentaAfectada = null;

        try {
            switch (action) {
                case "deposito": {
                    String cuenta = request.getParameter("cuenta");
                    double importe = Double.parseDouble(request.getParameter("importe"));

                    OperacionCuentaResponse res = EurekaWebClient.regDeposito(cuenta, importe);
                    if (res.getEstado() == 1) {
                        mensaje = "Depósito realizado correctamente por $" + String.format("%.2f", importe) + ".";
                        saldo = res.getSaldo();
                        cuentaAfectada = cuenta;
                    } else {
                        mensaje = "Error al realizar el depósito.";
                    }
                    break;
                }
                case "retiro": {
                    String cuenta = request.getParameter("cuenta");
                    double importe = Double.parseDouble(request.getParameter("importe"));

                    OperacionCuentaResponse res = EurekaWebClient.regRetiro(cuenta, importe);
                    if (res.getEstado() == 1) {
                        mensaje = "Retiro realizado correctamente por $" + String.format("%.2f", importe) + ".";
                        saldo = res.getSaldo();
                        cuentaAfectada = cuenta;
                    } else {
                        mensaje = "Error al realizar el retiro.";
                    }
                    break;
                }
                case "transferencia": {
                    String cuentaOrigen = request.getParameter("cuentaOrigen");
                    String cuentaDestino = request.getParameter("cuentaDestino");
                    double importe = Double.parseDouble(request.getParameter("importe"));

                    OperacionCuentaResponse res = EurekaWebClient.regTransferencia(cuentaOrigen, cuentaDestino, importe);
                    if (res.getEstado() == 1) {
                        mensaje = "Transferencia de $" + String.format("%.2f", importe)
                                + " desde la cuenta " + cuentaOrigen
                                + " hacia la cuenta " + cuentaDestino + " realizada correctamente.";
                        saldo = res.getSaldo();       // saldo de la cuenta origen
                        cuentaAfectada = cuentaOrigen;
                    } else {
                        mensaje = "Error al realizar la transferencia.";
                    }
                    break;
                }
                default:
                    mensaje = "Operación no reconocida.";
            }
        } catch (Exception ex) {
            mensaje = "Error en la operación: " + ex.getMessage();
        }

        request.setAttribute("mensaje", mensaje);
        if (saldo != null) {
            request.setAttribute("saldo", saldo);
        }
        if (cuentaAfectada != null) {
            request.setAttribute("cuenta", cuentaAfectada);
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/mensaje.jsp");
        rd.forward(request, response);
    }
}
