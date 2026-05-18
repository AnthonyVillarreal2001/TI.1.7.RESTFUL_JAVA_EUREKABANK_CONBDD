package ec.edu.monster.controller;

import ec.edu.monster.service.EurekaWebClient;
import ec.edu.monster.model.Movimiento;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "MovimientosController", urlPatterns = {"/movimientos"})
public class MovimientosController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        String cuenta = request.getParameter("cuenta");
        List<Movimiento> lista = EurekaWebClient.traerMovimientos(cuenta);

        request.setAttribute("cuenta", cuenta);
        request.setAttribute("movimientos", lista);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/movimientos.jsp");
        rd.forward(request, response);
    }
}
