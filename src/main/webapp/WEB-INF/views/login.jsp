<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">

</head>

<body>
<header>
	<%@ include file="includes/header.jsp" %>
</header>

<div class="container">

    <h2>Login</h2>

    <% if (request.getAttribute("errorMessage") != null) { %>
        <p style="color:red;">
            <%= request.getAttribute("errorMessage") %>
        </p>
    <% } %>

    <form action="<%= request.getContextPath() %>/login" method="post">

      <div class="entries">
        <div>
            <label for="email">Email:</label>
            <input type="email"
                   id="email"
                   name="email"
                   placeholder="name@example.com"
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
      </div>

        <br>

        <button type="submit">Login</button>
        <div>
        	<p>Don't have an account? </p>
        	<a href="register" id="register_text">Sign Up</a>
        </div>

    </form>

</div>

</body>
</html>
