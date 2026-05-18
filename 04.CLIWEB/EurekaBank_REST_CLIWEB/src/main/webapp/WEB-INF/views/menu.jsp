<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String usuario = (String) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Menú - EurekaBank LDU</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/bank-theme.css">
</head>
    <body>
        <div class="header">
            <div class="header-left">
                <img src="<%= request.getContextPath()%>/resources/img/logo-ldu.png" 
                     alt="Logo EurekaBank LDU" 
                     class="logo-img">
                <h1>EurekaBank</h1>
            </div>

            <div class="header-right">
                <div class="user-chip">
                    <div class="avatar-container">
                        <img src="<%= request.getContextPath()%>/resources/img/monster.jpg" alt="Monster Avatar">
                    </div>

                    <span><%= usuario%></span>
                </div>
                <a href="<%= request.getContextPath()%>/index.jsp" class="logout-btn">
                    <img src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='white' viewBox='0 0 24 24'%3E%3Cpath d='M17 7l-1.41 1.41L18.17 11H8v2h10.17l-2.58 2.58L17 17l5-5zM4 5h8V3H4c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h8v-2H4V5z'/%3E%3C/svg%3E" alt="Salir">
                    Cerrar Sesión
                </a>
            </div>
        </div>

        <div class="main">
            <div class="grid-container">
                <!-- Consultar Movimientos -->
                <div class="operation-card">
                    <div class="card-icon">
                        <img src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='white' viewBox='0 0 24 24'%3E%3Cpath d='M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zM9 17H7v-7h2v7zm4 0h-2V7h2v10zm4 0h-2v-4h2v4z'/%3E%3C/svg%3E" alt="Movimientos">
                    </div>
                    <div class="card-title">Consultar Movimientos</div>
                    <div class="card-form">
                        <form action="movimientos" method="get">
                            <div class="form-group">
                                <label>Número de cuenta:</label>
                                <input type="text" name="cuenta" placeholder="Ingresa el número de cuenta" required />
                            </div>
                            <button type="submit" class="btn">Ver Movimientos</button>
                        </form>
                    </div>
                </div>

                <!-- Depósito -->
                <div class="operation-card">
                    <div class="card-icon">
                        <img src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='white' viewBox='0 0 24 24'%3E%3Cpath d='M11 17h2v-1h1c.55 0 1-.45 1-1v-3c0-.55-.45-1-1-1h-3v-1h4V8h-2V7h-2v1h-1c-.55 0-1 .45-1 1v3c0 .55.45 1 1 1h3v1H9v2h2v1zm9-13H4c-1.11 0-1.99.89-1.99 2L2 18c0 1.11.89 2 2 2h16c1.11 0 2-.89 2-2V6c0-1.11-.89-2-2-2zm0 14H4V6h16v12z'/%3E%3C/svg%3E" alt="Depósito">
                    </div>
                    <div class="card-title">Depósito</div>
                    <div class="card-form">
                        <form action="operacion" method="post">
                            <input type="hidden" name="action" value="deposito" />
                            <div class="form-group">
                                <label>Cuenta:</label>
                                <input type="text" name="cuenta" placeholder="Número de cuenta" required />
                            </div>
                            <div class="form-group">
                                <label>Importe:</label>
                                <input type="number" step="0.01" name="importe" placeholder="0.00" required />
                            </div>
                            <button type="submit" class="btn">Depositar</button>
                        </form>
                    </div>
                </div>

                <!-- Retiro -->
                <div class="operation-card">
                    <div class="card-icon">
                        <img src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='white' viewBox='0 0 24 24'%3E%3Cpath d='M21 7.28V5c0-1.1-.9-2-2-2H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2v-2.28c.59-.35 1-.98 1-1.72V9c0-.74-.41-1.37-1-1.72zM20 9v6h-7V9h7zM5 19V5h14v2h-6c-1.1 0-2 .9-2 2v6c0 1.1.9 2 2 2h6v2H5z'/%3E%3Ccircle cx='16' cy='12' r='1.5'/%3E%3C/svg%3E" alt="Retiro">
                    </div>
                    <div class="card-title">Retiro</div>
                    <div class="card-form">
                        <form action="operacion" method="post">
                            <input type="hidden" name="action" value="retiro" />
                            <div class="form-group">
                                <label>Cuenta:</label>
                                <input type="text" name="cuenta" placeholder="Número de cuenta" required />
                            </div>
                            <div class="form-group">
                                <label>Importe:</label>
                                <input type="number" step="0.01" name="importe" placeholder="0.00" required />
                            </div>
                            <button type="submit" class="btn">Retirar</button>
                        </form>
                    </div>
                </div>

                <!-- Transferencia -->
                <div class="operation-card">
                    <div class="card-icon">
                        <img src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='white' viewBox='0 0 24 24'%3E%3Cpath d='M20 4H4c-1.11 0-1.99.89-1.99 2L2 18c0 1.11.89 2 2 2h16c1.11 0 2-.89 2-2V6c0-1.11-.89-2-2-2zm0 14H4v-6h16v6zm0-10H4V6h16v2z'/%3E%3Cpath d='M6 10h2v2H6zm0 4h8v2H6z'/%3E%3C/svg%3E" alt="Transferencia">
                    </div>
                    <div class="card-title">Transferencia</div>
                    <div class="card-form">
                        <form action="operacion" method="post">
                            <input type="hidden" name="action" value="transferencia" />
                            <div class="form-group">
                                <label>Cuenta origen:</label>
                                <input type="text" name="cuentaOrigen" placeholder="Cuenta origen" required />
                            </div>
                            <div class="form-group">
                                <label>Cuenta destino:</label>
                                <input type="text" name="cuentaDestino" placeholder="Cuenta destino" required />
                            </div>
                            <div class="form-group">
                                <label>Importe:</label>
                                <input type="number" step="0.01" name="importe" placeholder="0.00" required />
                            </div>
                            <button type="submit" class="btn">Transferir</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', () => {
                const cards = document.querySelectorAll('.operation-card');

                // Recorre todas las tarjetas
                cards.forEach(card => {
                    const header = card.querySelector('.card-icon, .card-title');
                    const form = card.querySelector('.card-form');

                    // Cuando haces clic en el encabezado (icono o título)
                    header.addEventListener('click', e => {
                        e.stopPropagation();

                        // Si ya está activa, la cerramos
                        if (card.classList.contains('active')) {
                            card.classList.remove('active');
                            return;
                        }

                        // Cerrar todas las demás
                        cards.forEach(c => c.classList.remove('active'));

                        // Activar solo la actual
                        card.classList.add('active');
                    });

                    // Evita que clics dentro del formulario cierren o abran otros
                    form.addEventListener('click', e => {
                        e.stopPropagation();
                    });
                });

                // Cerrar todo si haces clic fuera de las tarjetas
                document.body.addEventListener('click', e => {
                    if (!e.target.closest('.operation-card')) {
                        cards.forEach(c => c.classList.remove('active'));
                    }
                });
            });
        </script>


        <div class="footer">Hechos por Ariel R. y Anthony V. | EurekaBank &copy; 2025</div>
</body>
</html>