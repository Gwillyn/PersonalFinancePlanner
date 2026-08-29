<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">

    <title>Expenses</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/navbar.css">
</head>

<body>

<header>
    <%@ include file="includes/header.jsp" %>
    <h1 class="title">Expenses</h1>
</header>

<div class="container">

    <h2>Manage Expenses</h2>

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

    <form action="${pageContext.request.contextPath}/expenses" method="post">

      <div class="entries">
        <div>
            <label for="expenseName">Expense Name:</label>

            <input type="text"
                   id="expenseName"
                   name="expenseName"
                   required>
        </div>

        <br>

        <div>
            <label for="amount">Amount:</label>

            <input type="number"
                   id="amount"
                   name="amount"
                   step="0.01"
                   min="0"
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

        <button type="submit">Add Expense</button>

      </div>
    </form>
  

</div>

</body>

</html>
