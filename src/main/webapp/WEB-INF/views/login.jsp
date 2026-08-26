<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Personal Budget Planner</title>
</head>

<body>

<div class="container">

    <h2>Login</h2>

    <% if (request.getAttribute("errorMessage") != null) { %>
        <p style="color:red;">
            <%= request.getAttribute("errorMessage") %>
        </p>
    <% } %>

    <form action="<%= request.getContextPath() %>/login" method="post">

        <div>
            <label for="email">Email:</label>
            <input type="email"
                   id="email"
                   name="email"
                   required>
        </div>

        <br>

        <div>
            <label for="password">Password:</label>
            <input type="password"
                   id="password"
                   name="password"
                   required>
        </div>

        <br>

        <button type="submit">Login</button>

    </form>

</div>

</body>
</html>