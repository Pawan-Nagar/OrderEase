<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
        <%@include file="cdn.jsp" %>
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Login</h2>
        <form action="login" method="post">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <button type="submit" class="btn btn-primary">Login</button>
        </form>
        
        <p class="mt-3"><a href="login">as Guest</a></p>
        
        <p><a href="signup.jsp">Sign up</a></p>
        <c:if test="${not empty errorMessage}">
            <p class="text-danger">${errorMessage}</p>
        </c:if>
    </div>

   
</body>
</html>
