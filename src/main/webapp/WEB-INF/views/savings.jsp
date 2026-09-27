<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Savings Goals</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/navbar.css">

</head>

<body>

<header>

    <%@ include file="includes/header.jsp" %>

    <h1 class="title">Savings Goals</h1>

</header>


<div class="container">

    <h2>Manage Savings Goal</h2>


    <% if (request.getAttribute("successMessage") != null) { %>

        <div style="color: green;">
            <%= request.getAttribute("successMessage") %>
        </div>

    <% } %>


    <% if (request.getAttribute("errorMessage") != null) { %>

        <div style="color: red;">
            <%= request.getAttribute("errorMessage") %>
        </div>

    <% } %>


    <!-- ADD SAVINGS GOAL FORM -->

    <form action="${pageContext.request.contextPath}/savings"
          method="post">

        <div class="entries">

            <div>

                <label for="goalName">
                    Goal Name:
                </label>

                <input type="text"
                       id="goalName"
                       name="goalName"
                       required>

            </div>


            <br>


            <div>

                <label for="targetAmount">
                    Target Amount:
                </label>

                <input type="number"
                       id="targetAmount"
                       name="targetAmount"
                       min="0.01"
                       step="0.01"
                       required>

            </div>


            <br>


            <div>

                <label for="currentAmount">
                    Current Amount:
                </label>

                <input type="number"
                       id="currentAmount"
                       name="currentAmount"
                       min="0"
                       step="0.01"
                       required>

            </div>


            <br>


            <div>

                <label for="monthlyContribution">
                    Monthly Contribution:
                </label>

                <input type="number"
                       id="monthlyContribution"
                       name="monthlyContribution"
                       min="0"
                       step="0.01"
                       required>

            </div>


            <br>


            <div>

                <label for="targetDate">
                    Target Date:
                </label>

                <input type="date"
                       id="targetDate"
                       name="targetDate"
                       required>

            </div>


            <br>


            <button type="submit">
                Add Savings Goal
            </button>

        </div>

    </form>


    <br>
    <br>


    <h2>Saved Savings Goals</h2>


    <%
        List<Map<String, Object>> savingsGoals =
                (List<Map<String, Object>>)
                request.getAttribute("savingsGoals");

        if (savingsGoals != null && !savingsGoals.isEmpty()) {

            for (Map<String, Object> goal : savingsGoals) {

                int goalId =
                        ((Number) goal.get("goalId")).intValue();
    %>


        <div style="
                border: 1px solid #ccc;
                border-radius: 8px;
                padding: 15px;
                margin-bottom: 15px;
             ">

            <h3>
                <%= goal.get("goalName") %>
            </h3>


            <p>
                <strong>Target Amount:</strong>
                $<%= String.format(
                        "%.2f",
                        ((Number) goal.get("targetAmount")).doubleValue()
                ) %>
            </p>


            <p>
                <strong>Current Amount:</strong>
                $<%= String.format(
                        "%.2f",
                        ((Number) goal.get("currentAmount")).doubleValue()
                ) %>
            </p>


            <p>
                <strong>Monthly Contribution:</strong>
                $<%= String.format(
                        "%.2f",
                        ((Number) goal.get("monthlyContribution")).doubleValue()
                ) %>
            </p>


            <p>
                <strong>Target Date:</strong>
                <%= goal.get("targetDate") %>
            </p>


            <p>
                <strong>Status:</strong>
                <%= goal.get("goalStatus") %>
            </p>


            <!-- EDIT SAVINGS GOAL -->

            <details>

                <summary style="cursor: pointer;">
                    Edit
                </summary>

                <br>

                <form action="${pageContext.request.contextPath}/savings"
                      method="post">

                    <input type="hidden"
                           name="action"
                           value="update">

                    <input type="hidden"
                           name="goalId"
                           value="<%= goalId %>">


                    <div>

                        <label>
                            Goal Name:
                        </label>

                        <input type="text"
                               name="goalName"
                               value="<%= goal.get("goalName") %>"
                               required>

                    </div>


                    <br>


                    <div>

                        <label>
                            Target Amount:
                        </label>

                        <input type="number"
                               name="targetAmount"
                               value="<%= goal.get("targetAmount") %>"
                               min="0.01"
                               step="0.01"
                               required>

                    </div>


                    <br>


                    <div>

                        <label>
                            Current Amount:
                        </label>

                        <input type="number"
                               name="currentAmount"
                               value="<%= goal.get("currentAmount") %>"
                               min="0"
                               step="0.01"
                               required>

                    </div>


                    <br>


                    <div>

                        <label>
                            Monthly Contribution:
                        </label>

                        <input type="number"
                               name="monthlyContribution"
                               value="<%= goal.get("monthlyContribution") %>"
                               min="0"
                               step="0.01"
                               required>

                    </div>


                    <br>


                    <div>

                        <label>
                            Target Date:
                        </label>

                        <input type="date"
                               name="targetDate"
                               value="<%= goal.get("targetDate") %>"
                               required>

                    </div>


                    <br>


                    <button type="submit">
                        Save Changes
                    </button>

                </form>

            </details>


            <br>


            <!-- DELETE SAVINGS GOAL -->

            <form action="${pageContext.request.contextPath}/savings"
                  method="post"
                  onsubmit="return confirm('Are you sure you want to delete this savings goal?');">

                <input type="hidden"
                       name="action"
                       value="delete">

                <input type="hidden"
                       name="goalId"
                       value="<%= goalId %>">

                <button type="submit">
                    Delete
                </button>

            </form>

        </div>


    <%
            }

        } else {
    %>


        <p>
            No savings goals have been added yet.
        </p>


    <%
        }
    %>


</div>


</body>

</html>