<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Personal Budget Tracker</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css">
</head>
<body>

<header>
    <%@ include file="includes/header.jsp" %>
    <h1 class="title">Dashboard</h1>
    <p class="description">Here is your financial overview for this month!</p>
</header>

<div class="summary-grid">

    <div class="card dash_card entry_card"
          onclick="window.location.href='${pageContext.request.contextPath}/income'">
        <h2>Monthly Income</h2>
        <div class="card-body">
          <span class="card-icon income-icon">↑</span>
          <p class="card-value">
              $<%= String.format("%.2f",
                  (Double) request.getAttribute("totalIncome")) %>
          </p>
        </div>
    </div>

    <div class="card dash_card entry_card"
          onclick="window.location.href='${pageContext.request.contextPath}/expenses'">
        <h2>Monthly Expenses</h2>
        <div class="card-body">
          <span class="card-icon expense-icon">↓</span>
          <p class="card-value">
              $<%= String.format("%.2f",
                  (Double) request.getAttribute("totalExpenses")) %>
          </p>
        </div>
    </div>

    <div class="card dash_card entry_card"
          onclick="window.location.href='${pageContext.request.contextPath}/budget'">
        <h2>Budget</h2>
        <p>
            $<%= String.format("%.2f",
                (Double) request.getAttribute("totalBudget")) %>
        </p>
    </div>

    <div class="card dash_card entry_card">
        <h2>Remaining Balance</h2>
        <p>
            $<%= String.format("%.2f",
                (Double) request.getAttribute("remainingBalance")) %>
        </p>
    </div>

</div>


<div class="budget-grid">
    <div class="card dash_card entry_card"
         onclick="window.location.href='${pageContext.request.contextPath}/savings'">
        <h2>Savings Goals</h2>
        <p>View Goals</p>
    </div>

    <div class="card dash_card entry_card"
         onclick="window.location.href='${pageContext.request.contextPath}/profile'">
        <h2>Profile</h2>
        <p>View Profile</p>
    </div>
</div>

</body>
</html>
