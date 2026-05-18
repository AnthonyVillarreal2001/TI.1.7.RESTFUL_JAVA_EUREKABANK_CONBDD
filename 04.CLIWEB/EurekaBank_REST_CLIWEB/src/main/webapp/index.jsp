<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>EurekaBank</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/bank-theme.css">
    </head>
    <body>
        <div class="header">
            <div class="header-left">
                <img src="<%= request.getContextPath()%>/resources/img/logo-ldu.png" alt="Logo" class="header-logo">
                <h1>EurekaBank</h1>
            </div>
        </div>

        <div class="login">
            <div class="login-icon">
                <img src="data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23c00000' viewBox='0 0 24 24'%3E%3Cpath d='M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z'/%3E%3C/svg%3E" alt="Login">
            </div>
            <h2>Ingreso al sistema</h2>
            <form action="login" method="post">
                <div class="form-group">
                    <input type="text" name="usuario" placeholder="Usuario" required />
                </div>
                <div class="form-group">
                    <input type="password" name="password" placeholder="Contraseña" required />
                </div>
                <button type="submit" class="btn">Ingresar</button>
            </form>
            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <div class="error">
                <span><%= error%></span>
            </div>
            <% }%>
        </div>
        <div class="footer">Hechos por Ariel R. y Anthony V. | EurekaBank &copy; 2025</div>
</body>
</html>