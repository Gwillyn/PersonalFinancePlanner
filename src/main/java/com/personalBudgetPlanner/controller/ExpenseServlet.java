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

        int userId = (Integer) session.getAttribute("userId");

        ExpenseDAO expenseDAO = new ExpenseDAO();

        loadExpenseList(request, userId, expenseDAO);

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

        int userId = (Integer) session.getAttribute("userId");

        String action = request.getParameter("action");

        ExpenseDAO expenseDAO = new ExpenseDAO();

        if ("delete".equalsIgnoreCase(action)) {
            handleDelete(request, response, userId, expenseDAO);
            return;
        }

        if ("edit".equalsIgnoreCase(action)) {
            handleEdit(request, response, userId, expenseDAO);
            return;
        }

        handleAdd(request, response, userId, expenseDAO);
    }

    private void handleAdd(HttpServletRequest request,
                           HttpServletResponse response,
                           int userId,
                           ExpenseDAO expenseDAO)
            throws ServletException, IOException {

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

            loadExpenseList(request, userId, expenseDAO);

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

                    boolean saved =
                            expenseDAO.addExpense(
                                    userId,
                                    categoryId,
                                    expenseName.trim(),
                                    expenseAmount,
                                    frequency
                            );

                    if (saved) {

                        response.sendRedirect(
                                request.getContextPath() + "/expenses"
                        );

                        return;

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

        loadExpenseList(request, userId, expenseDAO);

        request.getRequestDispatcher("/WEB-INF/views/expenses.jsp")
               .forward(request, response);
    }

    private void handleEdit(HttpServletRequest request,
                            HttpServletResponse response,
                            int userId,
                            ExpenseDAO expenseDAO)
            throws ServletException, IOException {

        String expenseIdValue = request.getParameter("expenseId");
        String expenseName = request.getParameter("expenseName");
        String amount = request.getParameter("amount");
        String frequency = request.getParameter("frequency");

        if (expenseIdValue == null || expenseIdValue.trim().isEmpty()
                || expenseName == null || expenseName.trim().isEmpty()
                || amount == null || amount.trim().isEmpty()
                || frequency == null || frequency.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "All expense fields are required."
            );

            loadExpenseList(request, userId, expenseDAO);

            request.getRequestDispatcher("/WEB-INF/views/expenses.jsp")
                   .forward(request, response);

            return;
        }

        try {

            int expenseId = Integer.parseInt(expenseIdValue);
            double expenseAmount = Double.parseDouble(amount);

            if (expenseAmount <= 0) {

                request.setAttribute(
                        "errorMessage",
                        "Expense amount must be greater than zero."
                );

                loadExpenseList(request, userId, expenseDAO);

                request.getRequestDispatcher("/WEB-INF/views/expenses.jsp")
                       .forward(request, response);

                return;
            }

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
                        "Expense category could not be found."
                );

                loadExpenseList(request, userId, expenseDAO);

                request.getRequestDispatcher("/WEB-INF/views/expenses.jsp")
                       .forward(request, response);

                return;
            }

            boolean updated =
                    expenseDAO.updateExpense(
                            expenseId,
                            userId,
                            categoryId,
                            expenseName.trim(),
                            expenseAmount,
                            frequency
                    );

            if (updated) {

                response.sendRedirect(
                        request.getContextPath() + "/expenses"
                );

                return;
            }

            request.setAttribute(
                    "errorMessage",
                    "Expense could not be updated."
            );

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "errorMessage",
                    "Invalid expense information."
            );
        }

        loadExpenseList(request, userId, expenseDAO);

        request.getRequestDispatcher("/WEB-INF/views/expenses.jsp")
               .forward(request, response);
    }

    private void handleDelete(HttpServletRequest request,
                              HttpServletResponse response,
                              int userId,
                              ExpenseDAO expenseDAO)
            throws ServletException, IOException {

        String expenseIdValue = request.getParameter("expenseId");

        if (expenseIdValue == null || expenseIdValue.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "Expense record could not be identified."
            );

            loadExpenseList(request, userId, expenseDAO);

            request.getRequestDispatcher("/WEB-INF/views/expenses.jsp")
                   .forward(request, response);

            return;
        }

        try {

            int expenseId = Integer.parseInt(expenseIdValue);

            boolean deleted =
                    expenseDAO.deleteExpense(
                            expenseId,
                            userId
                    );

            if (deleted) {

                response.sendRedirect(
                        request.getContextPath() + "/expenses"
                );

                return;
            }

            request.setAttribute(
                    "errorMessage",
                    "Expense could not be deleted."
            );

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "errorMessage",
                    "Invalid expense record."
            );
        }

        loadExpenseList(request, userId, expenseDAO);

        request.getRequestDispatcher("/WEB-INF/views/expenses.jsp")
               .forward(request, response);
    }

    private void loadExpenseList(HttpServletRequest request,
                                 int userId,
                                 ExpenseDAO expenseDAO) {

        request.setAttribute(
                "expenseList",
                expenseDAO.getActiveExpenses(userId)
        );
    }
}