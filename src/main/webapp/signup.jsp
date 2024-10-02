<!DOCTYPE html>
<html>
<head>
    <title>Sign Up</title>
    <%@include file="cdn.jsp" %>
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Sign Up</h2>
        <form action="signup" method="post">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="role">Role:</label>
                <select class="form-control" name="role" id="role">
                    <option value="customer">Customer</option>
                    <option value="admin">Admin</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Sign Up</button>
        </form>
        <p class="mt-3"><a href="login.jsp">Login</a></p>
    </div>

   
</body>
</html>
