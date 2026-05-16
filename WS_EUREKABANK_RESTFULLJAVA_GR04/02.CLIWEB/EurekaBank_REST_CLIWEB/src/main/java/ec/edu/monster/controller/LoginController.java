package ec.edu.monster.controller;

import ec.edu.monster.service.EurekaWebClient;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");

        String resultado = EurekaWebClient.validarIngreso(usuario, password);

        if ("Exitoso".equalsIgnoreCase(resultado)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("usuario", usuario);
            response.sendRedirect(request.getContextPath() + "/menu");
        } else {
            request.setAttribute("error", "Usuario o contraseña incorrectos.");
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }
    }
}
