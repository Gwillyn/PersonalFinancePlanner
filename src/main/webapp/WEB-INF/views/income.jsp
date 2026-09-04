<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

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

                <label for="incomeName">
                    Income Name:
                </label>

                <input type="text"
                       id="incomeName"
                       name="incomeName"
                       required>

            </div>

            <br>

            <div>

                <label for="amount">
                    Amount:
                </label>

                <input type="number"
                       id="amount"
                       name="amount"
                       step="0.01"
                       min="0.01"
                       required>

            </div>

            <br>

            <div>

                <label for="frequency">
                    Payment Frequency:
                </label>

                <select id="frequency"
                        name="frequency"
                        required>

                    <option value="">
                        Select frequency
                    </option>

                    <option value="Weekly">
                        Weekly
                    </option>

                    <option value="Biweekly">
                        Biweekly
                    </option>

                    <option value="Monthly">
                        Monthly
                    </option>

                    <option value="Yearly">
                        Yearly
                    </option>

                </select>

            </div>

            <br>

            <button type="submit">
                Add Income
            </button>

        </form>

    </div>

</div>

<div class="entry_list">

<%
    List<Map<String, Object>> incomeList =
            (List<Map<String, Object>>)
            request.getAttribute("incomeList");

    if (incomeList != null && !incomeList.isEmpty()) {

        for (Map<String, Object> income : incomeList) {
%>

    <div class="card entry_card">

        <h3>
            <%= income.get("incomeName") %>
        </h3>

        <p>
            $<%= String.format("%.2f", income.get("amount")) %>
        </p>

        <p>
            <%= income.get("frequency") %>
        </p>

        <div class="entry_actions">

            <button type="button"
                    onclick="showEditForm(
                        '<%= income.get("incomeId") %>',
                        '<%= income.get("incomeName") %>',
                        '<%= income.get("amount") %>',
                        '<%= income.get("frequency") %>'
                    )">
                Edit
            </button>

            <form action="${pageContext.request.contextPath}/income"
                  method="post"
                  style="display:inline;">

                <input type="hidden"
                       name="action"
                       value="delete">

                <input type="hidden"
                       name="incomeId"
                       value="<%= income.get("incomeId") %>">

                <button type="submit"
                        onclick="return confirm(
                            'Are you sure you want to delete this income?'
                        );">
                    Delete
                </button>

            </form>

        </div>

    </div>

<%
        }

    } else {
%>

    <p>No income records found.</p>

<%
    }
%>

</div>

<div class="container"
     id="edit_container"
     hidden>

    <h2 class="sub_title">
        Edit Income
    </h2>

    <form action="${pageContext.request.contextPath}/income"
          method="post">

        <input type="hidden"
               name="action"
               value="edit">

        <input type="hidden"
               id="editIncomeId"
               name="incomeId">

        <div>

            <label for="editIncomeName">
                Income Name:
            </label>

            <input type="text"
                   id="editIncomeName"
                   name="incomeName"
                   required>

        </div>

        <br>

        <div>

            <label for="editAmount">
                Amount:
            </label>

            <input type="number"
                   id="editAmount"
                   name="amount"
                   step="0.01"
                   min="0.01"
                   required>

        </div>

        <br>

        <div>

            <label for="editFrequency">
                Payment Frequency:
            </label>

            <select id="editFrequency"
                    name="frequency"
                    required>

                <option value="Weekly">
                    Weekly
                </option>

                <option value="Biweekly">
                    Biweekly
                </option>

                <option value="Monthly">
                    Monthly
                </option>

                <option value="Yearly">
                    Yearly
                </option>

            </select>

        </div>

        <br>

        <button type="submit">
            Save Changes
        </button>

        <button type="button"
                onclick="hideEditForm()">
            Cancel
        </button>

    </form>

</div>

<script>

    const toggleButton =
            document.getElementById("toggleEntry");

    const entryForm =
            document.getElementById("entry_form");

    toggleButton.addEventListener(
        "click",
        function() {

            const isHidden =
                    entryForm.hidden;

            entryForm.hidden =
                    !isHidden;

            toggleButton.textContent =
                    isHidden
                    ? "- Hide Income"
                    : "+ Add Income";
        }
    );

    function showEditForm(
        incomeId,
        incomeName,
        amount,
        frequency
    ) {

        document.getElementById(
            "editIncomeId"
        ).value = incomeId;

        document.getElementById(
            "editIncomeName"
        ).value = incomeName;

        document.getElementById(
            "editAmount"
        ).value = amount;

        document.getElementById(
            "editFrequency"
        ).value = frequency;

        document.getElementById(
            "edit_container"
        ).hidden = false;

        document.getElementById(
            "edit_container"
        ).scrollIntoView({
            behavior: "smooth"
        });
    }

    function hideEditForm() {

        document.getElementById(
            "edit_container"
        ).hidden = true;
    }

</script>

</body>
</html>