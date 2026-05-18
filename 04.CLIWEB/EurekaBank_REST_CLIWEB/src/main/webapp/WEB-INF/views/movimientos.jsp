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
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: linear-gradient(135deg, #1a1a2e 0%, #0f3460 50%, #16213e 100%);
                min-height: 100vh;
                position: relative;
                overflow-x: hidden;
            }

            body::before {
                content: '';
                position: fixed;
                width: 100%;
                height: 100%;
                background-image: radial-gradient(circle, rgba(255,255,255,0.05) 1px, transparent 1px);
                background-size: 30px 30px;
                animation: movePattern 20s linear infinite;
                z-index: 0;
            }

            @keyframes movePattern {
                0% {
                    transform: translateY(0);
                }
                100% {
                    transform: translateY(30px);
                }
            }

            .header {
                background: linear-gradient(135deg, rgba(255,255,255,0.95) 0%, rgba(255,255,255,0.98) 100%);
                backdrop-filter: blur(10px);
                border-bottom: 5px solid #c00000;
                padding: 20px 40px;
                box-shadow: 0 4px 20px rgba(0,0,0,0.3);
                position: relative;
                z-index: 100;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .header h1 {
                margin: 0;
                background: linear-gradient(135deg, #c00000 0%, #0052a3 100%);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                background-clip: text;
                font-size: 28px;
                font-weight: 900;
            }

            .btn-back {
                background: linear-gradient(135deg, #c00000 0%, #900000 100%);
                color: #fff;
                border: none;
                padding: 12px 25px;
                border-radius: 25px;
                cursor: pointer;
                font-weight: bold;
                font-size: 14px;
                text-decoration: none;
                display: flex;
                align-items: center;
                gap: 8px;
                transition: all 0.3s ease;
                box-shadow: 0 3px 10px rgba(192,0,0,0.3);
            }

            .btn-back:hover {
                background: linear-gradient(135deg, #900000 0%, #600000 100%);
                transform: translateY(-2px);
                box-shadow: 0 5px 15px rgba(192,0,0,0.5);
            }

            .btn-back img {
                width: 20px;
                height: 20px;
            }

            .btn-close {
                position: absolute;
                top: 20px;
                right: 20px;
                background: rgba(0,0,0,0.1);
                border: none;
                width: 40px;
                height: 40px;
                border-radius: 50%;
                cursor: pointer;
                display: flex;
                align-items: center;
                justify-content: center;
                transition: all 0.3s ease;
                z-index: 10;
            }

            .btn-close:hover {
                background: rgba(0,0,0,0.2);
                transform: rotate(90deg);
            }

            .btn-close img {
                width: 20px;
                height: 20px;
            }

            /* Modal overlay */
            .modal-overlay {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0,0,0,0.75);
                backdrop-filter: blur(8px);
                display: flex;
                justify-content: center;
                align-items: center;
                z-index: 1000;
                padding: 20px;
                animation: fadeIn 0.3s ease;
                overflow-y: auto;
            }

            @keyframes fadeIn {
                from {
                    opacity: 0;
                }
                to {
                    opacity: 1;
                }
            }

            /* Modal contenedor */
            .modal {
                background: rgba(255,255,255,0.98);
                border-radius: 30px;
                padding: 40px;
                max-width: 1000px;
                width: 100%;
                box-shadow: 0 25px 80px rgba(0,0,0,0.5);
                position: relative;
                animation: modalSlideIn 0.5s cubic-bezier(0.175, 0.885, 0.32, 1.275);
                border: 3px solid rgba(192,0,0,0.3);
                max-height: 90vh;
                overflow-y: auto;
            }

            @keyframes modalSlideIn {
                from {
                    transform: translateY(-100px) scale(0.9);
                    opacity: 0;
                }
                to {
                    transform: translateY(0) scale(1);
                    opacity: 1;
                }
            }

            /* Decoración superior */
            .modal-header {
                text-align: center;
                margin-bottom: 30px;
                position: relative;
            }

            .modal-header-icon {
                width: 80px;
                height: 80px;
                margin: 0 auto 20px;
                animation: iconFloat 3s ease-in-out infinite;
            }

            .modal-header-icon img {
                width: 100%;
                height: 100%;
                filter: drop-shadow(0 5px 15px rgba(192,0,0,0.3));
            }

            @keyframes iconFloat {
                0%, 100% {
                    transform: translateY(0);
                }
                50% {
                    transform: translateY(-10px);
                }
            }

            .modal h2 {
                color: #c00000;
                margin-bottom: 10px;
                font-size: 28px;
                font-weight: 800;
            }

            .account-number {
                display: inline-block;
                background: linear-gradient(135deg, #c00000, #0052a3);
                color: white;
                padding: 10px 25px;
                border-radius: 20px;
                font-weight: 700;
                font-size: 18px;
                margin-top: 10px;
                box-shadow: 0 5px 15px rgba(192,0,0,0.3);
            }

            /* Tabla de movimientos */
            .table-container {
                overflow-x: auto;
                margin: 30px 0;
                border-radius: 15px;
                box-shadow: 0 5px 20px rgba(0,0,0,0.1);
            }

            table {
                width: 100%;
                border-collapse: separate;
                border-spacing: 0;
                background: white;
            }

            thead {
                background: linear-gradient(135deg, #c00000 0%, #0052a3 100%);
                color: white;
            }

            th {
                padding: 18px 15px;
                text-align: center;
                font-weight: 700;
                font-size: 14px;
                text-transform: uppercase;
                letter-spacing: 0.5px;
                border: none;
            }

            th:first-child {
                border-top-left-radius: 15px;
            }

            th:last-child {
                border-top-right-radius: 15px;
            }

            td {
                padding: 15px;
                text-align: center;
                border-bottom: 1px solid #e0e0e0;
                font-size: 14px;
                color: #2c3e50;
            }

            tbody tr {
                transition: all 0.3s ease;
                background: white;
            }

            tbody tr:nth-child(even) {
                background: rgba(192,0,0,0.03);
            }

            tbody tr:hover {
                background: linear-gradient(to right, rgba(192,0,0,0.1), rgba(0,82,163,0.1));
                transform: scale(1.01);
                box-shadow: 0 3px 10px rgba(0,0,0,0.1);
            }

            tbody tr:last-child td:first-child {
                border-bottom-left-radius: 15px;
            }

            tbody tr:last-child td:last-child {
                border-bottom-right-radius: 15px;
            }

            /* Badges para tipo */
            .badge {
                display: inline-block;
                padding: 5px 12px;
                border-radius: 12px;
                font-weight: 600;
                font-size: 12px;
                text-transform: uppercase;
            }

            .badge-deposito {
                background: linear-gradient(135deg, #2ecc71, #27ae60);
                color: white;
            }

            .badge-retiro {
                background: linear-gradient(135deg, #e74c3c, #c0392b);
                color: white;
            }

            .badge-transferencia {
                background: linear-gradient(135deg, #3498db, #2980b9);
                color: white;
            }

            /* Mensaje vacío */
            .empty-state {
                text-align: center;
                padding: 60px 40px;
            }

            .empty-icon {
                width: 100px;
                height: 100px;
                margin: 0 auto 20px;
                opacity: 0.5;
            }

            .empty-icon img {
                width: 100%;
                height: 100%;
            }

            .empty-state p {
                color: #7f8c8d;
                font-size: 18px;
                font-weight: 500;
            }

            .help-message {
                background: linear-gradient(135deg, rgba(241,196,15,0.1), rgba(243,156,18,0.05));
                padding: 15px 20px;
                border-radius: 15px;
                margin-top: 20px;
                border-left: 4px solid #f39c12;
                font-size: 14px;
                color: #7f6200;
                display: flex;
                align-items: center;
                gap: 10px;
                text-align: left;
            }

            .help-message img {
                width: 20px;
                height: 20px;
                flex-shrink: 0;
            }

            /* Scrollbar personalizada */
            .modal::-webkit-scrollbar {
                width: 10px;
            }

            .modal::-webkit-scrollbar-track {
                background: rgba(0,0,0,0.1);
                border-radius: 10px;
            }

            .modal::-webkit-scrollbar-thumb {
                background: linear-gradient(135deg, #c00000, #0052a3);
                border-radius: 10px;
            }

            /* Responsive */
            @media (max-width: 768px) {
                .header {
                    flex-direction: column;
                    gap: 15px;
                    text-align: center;
                }

                .modal {
                    padding: 25px;
                }

                table {
                    font-size: 12px;
                }

                th, td {
                    padding: 10px 8px;
                }
            }

            /* Colores según acción */
            .tr-ingreso td {
                color: #1e8449; /* verde oscuro */
            }

            .tr-salida td {
                color: #c0392b; /* rojo */
            }

            /* Si prefieres sólo el importe: */
            .importe-ingreso {
                color: #27ae60;
                font-weight: 700;
            }

            .importe-salida {
                color: #e74c3c;
                font-weight: 700;
            }

        </style>
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
    </body>
</html>