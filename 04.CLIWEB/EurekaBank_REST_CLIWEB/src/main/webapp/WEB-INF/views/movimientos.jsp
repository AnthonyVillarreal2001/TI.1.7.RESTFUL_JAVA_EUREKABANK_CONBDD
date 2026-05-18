<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="ec.edu.monster.model.Movimiento"%>
<%
    String usuario = (String) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    String cuenta = (String) request.getAttribute("cuenta");
    List<Movimiento> movimientos = (List<Movimiento>) request.getAttribute("movimientos");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Movimientos - EurekaBank LDU</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/bank-theme.css">
</head>
    <body>
        <div class="header">
            <h1>EurekaBank | Liga de Quito</h1>
            <a href="<%= request.getContextPath()%>/menu" class="btn-back">
                <img src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='white' viewBox='0 0 24 24'%3E%3Cpath d='M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z'/%3E%3C/svg%3E" alt="Home">
                Volver al Menú
            </a>
        </div>

        <div class="modal-overlay">
            <div class="modal">
                <button class="btn-close" onclick="window.location.href = '<%= request.getContextPath()%>/menu'">
                    <img src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23666' viewBox='0 0 24 24'%3E%3Cpath d='M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z'/%3E%3C/svg%3E" alt="Cerrar">
                </button>

                <div class="modal-header">
                    <div class="modal-header-icon">
                        <img src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23c00000' viewBox='0 0 24 24'%3E%3Cpath d='M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zM9 17H7v-7h2v7zm4 0h-2V7h2v10zm4 0h-2v-4h2v4z'/%3E%3C/svg%3E" alt="Movimientos">
                    </div>
                    <h2>Movimientos de Cuenta</h2>
                    <div class="account-number"><%= cuenta%></div>
                </div>

                <%
                    if (movimientos == null || movimientos.isEmpty()) {
                %>
                <div class="empty-state">
                    <div class="empty-icon">
                        <img src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%237f8c8d' viewBox='0 0 24 24'%3E%3Cpath d='M20 6h-2.18c.11-.31.18-.65.18-1 0-1.66-1.34-3-3-3-1.05 0-1.96.54-2.5 1.35l-.5.67-.5-.68C10.96 2.54 10.05 2 9 2 7.34 2 6 3.34 6 5c0 .35.07.69.18 1H4c-1.11 0-1.99.89-1.99 2L2 19c0 1.11.89 2 2 2h16c1.11 0 2-.89 2-2V8c0-1.11-.89-2-2-2zm-5-2c.55 0 1 .45 1 1s-.45 1-1 1-1-.45-1-1 .45-1 1-1zM9 4c.55 0 1 .45 1 1s-.45 1-1 1-1-.45-1-1 .45-1 1-1zm11 15H4v-2h16v2zm0-5H4V8h5.08L7 10.83 8.62 12 11 8.76l1-1.36 1 1.36L15.38 12 17 10.83 14.92 8H20v6z'/%3E%3C/svg%3E" alt="Sin movimientos">
                    </div>
                    <p>No hay movimientos registrados para esta cuenta.</p>
                    <div class="help-message">
                        <img src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23f39c12' viewBox='0 0 24 24'%3E%3Cpath d='M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-2h2v2zm0-4h-2V7h2v6z'/%3E%3C/svg%3E" alt="Ayuda">
                        <span>Verifique que el número de cuenta sea correcto o que la cuenta tenga movimientos registrados.</span>
                    </div>
                </div>
                <%
                } else {
                %>
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>Nro</th>
                                <th>Fecha</th>
                                <th>Tipo</th>
                                <th>Acción</th>
                                <th>Importe</th>
                            </tr>
                        </thead>
                        <tbody>
<%
    for (Movimiento m : movimientos) {

        String accion = m.getAccion() != null ? m.getAccion() : "";
        String tipo   = m.getTipo()   != null ? m.getTipo()   : "";

        // Clase para la fila (verde ingreso / roja salida)
        String rowClass = "";
        if ("INGRESO".equalsIgnoreCase(accion)) {
            rowClass = "tr-ingreso";
        } else if ("SALIDA".equalsIgnoreCase(accion)) {
            rowClass = "tr-salida";
        }

        // Badge según tipo + acción
        String badgeClass = "badge";
        String tipoLower = tipo.toLowerCase();

        if (tipoLower.contains("deposit")) {
            // Depósito normal
            badgeClass += " badge-deposito";

        } else if (tipoLower.contains("retiro")) {
            // Retiro normal
            badgeClass += " badge-retiro";

        } else if (tipoLower.contains("transfer")) {
            // Transferencia: pinta como ingreso o salida
            if ("INGRESO".equalsIgnoreCase(accion)) {
                badgeClass += " badge-deposito"; // verde
            } else if ("SALIDA".equalsIgnoreCase(accion)) {
                badgeClass += " badge-retiro";   // rojo
            } else {
                badgeClass += " badge-transferencia"; // fallback celeste
            }

        } else {
            // Otros tipos (INTERES, MANTENIMIENTO, etc.) → celeste
            badgeClass += " badge-transferencia";
        }

        // Clase para importe (solo color en la columna Importe)
        String importeClass = "INGRESO".equalsIgnoreCase(accion)
                              ? "importe-ingreso"
                              : "importe-salida";
%>
    <tr class="<%= rowClass %>">
        <td><strong><%= m.getNromov() %></strong></td>
        <td><%= m.getFecha() %></td>
        <td><span class="<%= badgeClass %>"><%= tipo %></span></td>
        <td><%= accion %></td>
        <td class="<%= importeClass %>">
            <strong>$<%= String.format("%.2f", m.getImporte()) %></strong>
        </td>
    </tr>
<%
    } // fin for
%>
</tbody>


                    </table>
                </div>
                <%
                    }
                %>
            </div>
        </div>
        <div class="footer">Hechos por Ariel R. y Anthony V. | EurekaBank &copy; 2025</div>
</body>
</html>