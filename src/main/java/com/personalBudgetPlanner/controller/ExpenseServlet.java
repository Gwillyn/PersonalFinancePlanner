package com.personalBudgetPlanner.controller;

import java.io.IOException;

import com.personalBudgetPlanner.database.CategoryDAO;
import com.personalBudgetPlanner.database.ExpenseDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/expenses")
public class ExpenseServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/expenses.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String expenseName = request.getParameter("expenseName");
        String amount = request.getParameter("amount");
        String frequency = request.getParameter("frequency");

        if (expenseName == null || expenseName.trim().isEmpty()
                || amount == null || amount.trim().isEmpty()
                || frequency == null || frequency.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "All expense fields are required."
            );

            request.getRequestDispatcher("/WEB-INF/views/expenses.jsp")
                   .forward(request, response);

            return;
        }

        try {

            double expenseAmount = Double.parseDouble(amount);

            if (expenseAmount <= 0) {

                request.setAttribute(
                        "errorMessage",
                        "Expense amount must be greater than zero."
                );

            } else {

                int userId = (Integer) session.getAttribute("userId");

                CategoryDAO categoryDAO = new CategoryDAO();

                Integer categoryId =
                        categoryDAO.getOrCreateCategory(
                                userId,
                                "General Expenses",
                                "EXPENSE"
                        );

                if (categoryId == null) {

                    request.setAttribute(
                            "errorMessage",
                            "Expense category could not be created."
                    );

                } else {

                    ExpenseDAO expenseDAO = new ExpenseDAO();

                    boolean saved =
                            expenseDAO.addExpense(
                                    userId,
                                    categoryId,
                                    expenseName.trim(),
                                    expenseAmount,
                                    frequency
                            );

                    if (saved) {

                        request.setAttribute(
                                "successMessage",
                                "Expense was saved successfully."
                        );

                    } else {

                        request.setAttribute(
                                "errorMessage",
                                "Expense could not be saved."
                        );
                    }
                }
            }

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "errorMessage",
                    "Please enter a valid expense amount."
            );
        }

        request.getRequestDispatcher("/WEB-INF/views/expenses.jsp")
               .forward(request, response);
    }
}