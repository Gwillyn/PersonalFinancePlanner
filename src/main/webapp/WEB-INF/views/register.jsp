<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Register - Personal Budget Tracker</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

    <h1>Create Account</h1>

    <% if (request.getAttribute("errorMessage") != null) { %>
        <p style="color: red;">
            <%= request.getAttribute("errorMessage") %>
        </p>
    <% } %>

    <% if (request.getAttribute("successMessage") != null) { %>
        <p style="color: green;">
            <%= request.getAttribute("successMessage") %>
        </p>
    <% } %>

    <form action="${pageContext.request.contextPath}/register"
          method="post">

        <div>
            <label for="firstName">First Name:</label>
            <input type="text"
                   id="firstName"
                   name="firstName"
                   required>
        </div>

        <br>

        <div>
            <label for="lastName">Last Name:</label>
            <input type="text"
                   id="lastName"
                   name="lastName"
                   required>
        </div>

        <br>

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

        <div>
            <label for="preferredCurrency">
                Preferred Currency:
            </label>

            <select id="preferredCurrency"
                    name="preferredCurrency"
                    required>

                <option value="">Select currency</option>
                <option value="CAD">CAD</option>
                <option value="USD">USD</option>
                <option value="EUR">EUR</option>

            </select>
        </div>

        <br>

        <button type="submit">Register</button>

    </form>

    <p>
        Already have an account?
        <a href="${pageContext.request.contextPath}/login">
            Login
        </a>
    </p>

</body>
</html>