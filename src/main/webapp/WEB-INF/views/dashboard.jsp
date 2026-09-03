<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Personal Budget Tracker</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/index.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/navbar.css">

</head>

<body>

<header>

    <%@ include file="includes/header.jsp" %>

    <h1 class="title">Personal Budget Tracker</h1>

</header>

<div class="dash_container">

    <div class="card dash_card"
         onclick="window.location.href='${pageContext.request.contextPath}/income'"
         style="cursor: pointer;">

        <h2>Income</h2>

        <p>
            $<%= String.format("%.2f",
                (Double) request.getAttribute("totalIncome")) %>
        </p>

    </div>

    <div class="card dash_card"
         onclick="window.location.href='${pageContext.request.contextPath}/expenses'"
         style="cursor: pointer;">

        <h2>Expenses</h2>

        <p>
            $<%= String.format("%.2f",
                (Double) request.getAttribute("totalExpenses")) %>
        </p>

    </div>

    <div class="card dash_card"
         onclick="window.location.href='${pageContext.request.contextPath}/budget'"
         style="cursor: pointer;">

        <h2>Budget</h2>

        <p>
            $<%= String.format("%.2f",
                (Double) request.getAttribute("totalBudget")) %>
        </p>

    </div>

    <div class="card dash_card">

        <h2>Remaining Balance</h2>

        <p>
            $<%= String.format("%.2f",
                (Double) request.getAttribute("remainingBalance")) %>
        </p>

    </div>

    <div class="card dash_card"
         onclick="window.location.href='${pageContext.request.contextPath}/profile'"
         style="cursor: pointer;">

        <h2>Profile</h2>

        <p>View Profile</p>

    </div>

</div>

</body>

</html>
