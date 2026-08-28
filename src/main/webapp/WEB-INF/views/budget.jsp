<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">

    <title>Budget</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/navbar.css">
</head>

<body>

<header>
    <%@ include file="includes/header.jsp" %>
    <h1 class="title">Budget</h1>
</header>

<div class="container">

    <h2>Manage Budget</h2>

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

    <% if (request.getAttribute("remainingAmount") != null) { %>
        <p>
            Remaining Amount:
            $<%= String.format("%.2f",
                (Double) request.getAttribute("remainingAmount")) %>
        </p>
    <% } %>

    <form action="${pageContext.request.contextPath}/budget"
          method="post">

      <div class="entries">
        <div>
            <label for="monthlyIncome">Monthly Income:</label>

            <input type="number"
                   id="monthlyIncome"
                   name="monthlyIncome"
                   step="0.01"
                   min="0"
                   required>
        </div>

        <br>

        <div>
            <label for="budgetAmount">Budget Amount:</label>

            <input type="number"
                   id="budgetAmount"
                   name="budgetAmount"
                   step="0.01"
                   min="0"
                   required>
        </div>

        <br>

        <button type="submit">Calculate Budget</button>
      </div>

    </form>

</div>

</body>

</html>
