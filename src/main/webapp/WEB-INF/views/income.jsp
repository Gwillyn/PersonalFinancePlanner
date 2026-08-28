<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">

    <title>Income</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/navbar.css">
</head>

<body>

<header>
    <%@ include file="includes/header.jsp" %>
    <h1 class="title">Income</h1>
</header>

<div class="container">

    <h2>Manage Income</h2>

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

    <form action="${pageContext.request.contextPath}/income"
          method="post">

      <div class="entries">
        <div>
            <label for="incomeName">Income Name:</label>

            <input type="text"
                   id="incomeName"
                   name="incomeName"
                   required>
        </div>

        <br>

        <div>
            <label for="amount">Amount:</label>

            <input type="number"
                   id="amount"
                   name="amount"
                   step="0.01"
                   min="0.01"
                   required>
        </div>

        <br>

        <div>
            <label for="frequency">Payment Frequency:</label>

            <select id="frequency"
                    name="frequency"
                    required>

                <option value="">Select frequency</option>
                <option value="Weekly">Weekly</option>
                <option value="Biweekly">Biweekly</option>
                <option value="Monthly">Monthly</option>
                <option value="Yearly">Yearly</option>

            </select>
        </div>

        <br>

        <button type="submit">Add Income</button>

      </div>
    </form>

</div>

</body>

</html>
