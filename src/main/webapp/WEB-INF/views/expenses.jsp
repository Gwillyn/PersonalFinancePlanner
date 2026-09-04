<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

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

<div class="container" id="entry_container">

    <h2 class="sub_title">Manage Expenses</h2>

    <div id="add_button">

        <button type="button" id="toggleEntry">
            + Add Expense
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

        <form action="${pageContext.request.contextPath}/expenses"
              method="post">

            <div>

                <label for="expenseName">
                    Expense Name:
                </label>

                <input type="text"
                       id="expenseName"
                       name="expenseName"
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
                Add Expense
            </button>

        </form>

    </div>

</div>

<div class="entry_list">

<%
    List<Map<String, Object>> expenseList =
            (List<Map<String, Object>>)
            request.getAttribute("expenseList");

    if (expenseList != null && !expenseList.isEmpty()) {

        for (Map<String, Object> expense : expenseList) {
%>

    <div class="card entry_card">

        <h3>
            <%= expense.get("expenseName") %>
        </h3>

        <p>
            $<%= String.format("%.2f", expense.get("amount")) %>
        </p>

        <p>
            <%= expense.get("frequency") %>
        </p>

        <div class="entry_actions">

            <button type="button"
                    onclick="showEditForm(
                        '<%= expense.get("expenseId") %>',
                        '<%= expense.get("expenseName") %>',
                        '<%= expense.get("amount") %>',
                        '<%= expense.get("frequency") %>'
                    )">
                Edit
            </button>

            <form action="${pageContext.request.contextPath}/expenses"
                  method="post"
                  style="display:inline;">

                <input type="hidden"
                       name="action"
                       value="delete">

                <input type="hidden"
                       name="expenseId"
                       value="<%= expense.get("expenseId") %>">

                <button type="submit"
                        onclick="return confirm(
                            'Are you sure you want to delete this expense?'
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

    <p>No expense records found.</p>

<%
    }
%>

</div>

<div class="container"
     id="edit_container"
     hidden>

    <h2 class="sub_title">
        Edit Expense
    </h2>

    <form action="${pageContext.request.contextPath}/expenses"
          method="post">

        <input type="hidden"
               name="action"
               value="edit">

        <input type="hidden"
               id="editExpenseId"
               name="expenseId">

        <div>

            <label for="editExpenseName">
                Expense Name:
            </label>

            <input type="text"
                   id="editExpenseName"
                   name="expenseName"
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
                    ? "- Hide Expense"
                    : "+ Add Expense";
        }
    );

    function showEditForm(
        expenseId,
        expenseName,
        amount,
        frequency
    ) {

        document.getElementById(
            "editExpenseId"
        ).value = expenseId;

        document.getElementById(
            "editExpenseName"
        ).value = expenseName;

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