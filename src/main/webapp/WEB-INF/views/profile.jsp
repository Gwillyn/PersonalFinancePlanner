<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">

    <title>Profile</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/navbar.css">
</head>

<body>

<header>
    <%@ include file="includes/header.jsp" %>
    <h1 class="title">Profile</h1>
</header>

<div class="container">

    <h2>Manage Profile</h2>

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

    <form action="${pageContext.request.contextPath}/profile"
          method="post">

        <div>
            <label for="firstName">First Name:</label>

            <input type="text"
                   id="firstName"
                   name="firstName"
                   value="<%= session.getAttribute("firstName") != null
                           ? session.getAttribute("firstName")
                           : "" %>"
                   required>
        </div>

        <br>

        <div>
            <label for="lastName">Last Name:</label>

            <input type="text"
                   id="lastName"
                   name="lastName"
                   value="<%= session.getAttribute("lastName") != null
                           ? session.getAttribute("lastName")
                           : "" %>"
                   required>
        </div>

        <br>

        <div>
            <label for="email">Email:</label>

            <input type="email"
                   id="email"
                   value="<%= session.getAttribute("userEmail") != null
                           ? session.getAttribute("userEmail")
                           : "" %>"
                   readonly>
        </div>

        <br>

        <div>
            <label for="preferredCurrency">Preferred Currency:</label>

            <select id="preferredCurrency"
                    name="preferredCurrency"
                    required>

                <option value="">Select currency</option>

                <option value="CAD"
                    <%= "CAD".equals(session.getAttribute("preferredCurrency"))
                        ? "selected" : "" %>>
                    CAD
                </option>

                <option value="USD"
                    <%= "USD".equals(session.getAttribute("preferredCurrency"))
                        ? "selected" : "" %>>
                    USD
                </option>

                <option value="EUR"
                    <%= "EUR".equals(session.getAttribute("preferredCurrency"))
                        ? "selected" : "" %>>
                    EUR
                </option>

            </select>
        </div>

        <br>

        <button type="submit">Update Profile</button>

    </form>

</div>

</body>

</html>