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

<div class="container" id="entry_container">

    <h2 class="sub_title">Manage Income</h2>

  <div id="add_button">
    <button type="button" id="toggleEntry">
      + Add Income
    </button>
  </div>

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
    
    <div class="entries" id="entry_form" hidden>
    <form action="${pageContext.request.contextPath}/income"
          method="post">

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

    </form>
</div>
</div>

<div class="entry_list">
  <div class="card entry_card">
    <h3>Income</h3>
    <p>number</p>
    <p>Time type</p>

    <div class="entry_actions">
      <button type="button">Edit</button>
      <button type="button">Delete</button>
    </div>

  </div>

  <div class="card entry_card">
    <h3>Income</h3>
    <p>number</p>
    <p>Time type</p>

    <div class="entry_actions">
      <button type="button">Edit</button>
      <button type="button">Delete</button>
    </div>

  </div>
  <div class="card entry_card">
    <h3>Income</h3>
    <p>number</p>
    <p>Time type</p>

    <div class="entry_actions">
      <button type="button">Edit</button>
      <button type="button">Delete</button>
    </div>

  </div>

</div>

<script>
              const toggleButton = document.getElementById("toggleEntry");
              const entryForm= document.getElementById("entry_form");

              toggleButton.addEventListener("click", function() {
              const isHidden = entryForm.hidden;

              entryForm.hidden = !isHidden;
              toggleButton.textContent = isHidden
                ? "- Hide Income"
                : "+ Add Income";
              });
            </script>

</body>

</html>
