<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String usuario = (String) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    String mensaje = (String) request.getAttribute("mensaje");
    boolean esExitoso = mensaje != null && !mensaje.toLowerCase().contains("error");

    Double saldoObj = (Double) request.getAttribute("saldo");
    String cuenta = (String) request.getAttribute("cuenta");
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Resultado - EurekaBank LDU</title>
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

                <div class="result-icon">
                    <% if (esExitoso) { %>
                    <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <circle cx="12" cy="12" r="10" fill="#27ae60"/>
                    <path d="M8 12.5l2.5 2.5 5.5-5.5" stroke="white" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    <% } else { %>
                    <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <circle cx="12" cy="12" r="10" fill="#c0392b"/>
                    <path d="M8 8l8 8M16 8l-8 8" stroke="white" stroke-width="2.5" stroke-linecap="round"/>
                    </svg>
                    <% }%>
                </div>

                <h2><%= esExitoso ? "¡Operación Exitosa!" : "Error en la Operación"%></h2>

                <div class="modal-message">
                    <%= mensaje%>
                </div>

                <% if (esExitoso && saldoObj != null && cuenta != null) {%>
                <div style="
                     margin-top:20px;
                     padding:18px 20px;
                     border-radius:18px;
                     background:linear-gradient(135deg, rgba(39,174,96,0.1), rgba(46,204,113,0.05));
                     border-left:5px solid #27ae60;
                     font-size:15px;
                     color:#1e3c2f;
                     text-align:left;
                     ">
                    <strong>Nuevo saldo disponible</strong><br/>
                    Cuenta <strong><%= cuenta%></strong>: 
                    <span style="font-size:17px;">$ <%= String.format("%.2f", saldoObj)%></span>
                </div>
                <% } %>

            </div>
        </div>

        <% if (esExitoso) { %>
        <script>
            function createConfetti() {
                for (let i = 0; i < 30; i++) {
                    let confetti = document.createElement('div');
                    confetti.className = 'confetti';
                    confetti.style.left = Math.random() * 100 + '%';
                    confetti.style.animationDelay = Math.random() * 3 + 's';
                    confetti.style.animationDuration = (Math.random() * 2 + 2) + 's';
                    document.body.appendChild(confetti);
                }
            }

            window.onload = createConfetti;
        </script>
        <% }%>
        <div class="footer">Hechos por Ariel R. y Anthony V. | EurekaBank &copy; 2025</div>
</body>
</html>