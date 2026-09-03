package com.personalBudgetPlanner.controller;

import java.io.IOException;

import com.personalBudgetPlanner.database.IncomeDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/income")
public class IncomeServlet extends HttpServlet {

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

        request.getRequestDispatcher("/WEB-INF/views/income.jsp")
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

        IncomeDAO incomeDAO = new IncomeDAO();

        if ("delete".equalsIgnoreCase(action)) {
            handleDelete(request, response, userId, incomeDAO);
            return;
        }

        if ("edit".equalsIgnoreCase(action)) {
            handleEdit(request, response, userId, incomeDAO);
            return;
        }

        handleAdd(request, response, userId, incomeDAO);
    }

    private void handleAdd(HttpServletRequest request,
                           HttpServletResponse response,
                           int userId,
                           IncomeDAO incomeDAO)
            throws ServletException, IOException {

        String incomeName = request.getParameter("incomeName");
        String amount = request.getParameter("amount");
        String frequency = request.getParameter("frequency");

        if (incomeName == null || incomeName.trim().isEmpty()
                || amount == null || amount.trim().isEmpty()
                || frequency == null || frequency.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "All income fields are required."
            );

            request.getRequestDispatcher("/WEB-INF/views/income.jsp")
                   .forward(request, response);

            return;
        }

        try {

            double incomeAmount = Double.parseDouble(amount);

            if (incomeAmount <= 0) {

                request.setAttribute(
                        "errorMessage",
                        "Income amount must be greater than zero."
                );

            } else {

                boolean saved = incomeDAO.addIncome(
                        userId,
                        incomeName.trim(),
                        incomeAmount,
                        frequency
                );

                if (saved) {

                    response.sendRedirect(
                            request.getContextPath() + "/dashboard"
                    );

                    return;

                } else {

                    request.setAttribute(
                            "errorMessage",
                            "Income could not be saved."
                    );
                }
            }

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "errorMessage",
                    "Please enter a valid income amount."
            );
        }

        request.getRequestDispatcher("/WEB-INF/views/income.jsp")
               .forward(request, response);
    }

    private void handleEdit(HttpServletRequest request,
                            HttpServletResponse response,
                            int userId,
                            IncomeDAO incomeDAO)
            throws ServletException, IOException {

        String incomeIdValue = request.getParameter("incomeId");
        String incomeName = request.getParameter("incomeName");
        String amount = request.getParameter("amount");
        String frequency = request.getParameter("frequency");

        if (incomeIdValue == null || incomeIdValue.trim().isEmpty()
                || incomeName == null || incomeName.trim().isEmpty()
                || amount == null || amount.trim().isEmpty()
                || frequency == null || frequency.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "All income fields are required."
            );

            request.getRequestDispatcher("/WEB-INF/views/income.jsp")
                   .forward(request, response);

            return;
        }

        try {

            int incomeId = Integer.parseInt(incomeIdValue);
            double incomeAmount = Double.parseDouble(amount);

            if (incomeAmount <= 0) {

                request.setAttribute(
                        "errorMessage",
                        "Income amount must be greater than zero."
                );

                request.getRequestDispatcher("/WEB-INF/views/income.jsp")
                       .forward(request, response);

                return;
            }

            boolean updated = incomeDAO.updateIncome(
                    incomeId,
                    userId,
                    incomeName.trim(),
                    incomeAmount,
                    frequency
            );

            if (updated) {

                response.sendRedirect(
                        request.getContextPath() + "/income"
                );

                return;
            }

            request.setAttribute(
                    "errorMessage",
                    "Income could not be updated."
            );

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "errorMessage",
                    "Invalid income information."
            );
        }

        request.getRequestDispatcher("/WEB-INF/views/income.jsp")
               .forward(request, response);
    }

    private void handleDelete(HttpServletRequest request,
                              HttpServletResponse response,
                              int userId,
                              IncomeDAO incomeDAO)
            throws ServletException, IOException {

        String incomeIdValue = request.getParameter("incomeId");

        if (incomeIdValue == null || incomeIdValue.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "Income record could not be identified."
            );

            request.getRequestDispatcher("/WEB-INF/views/income.jsp")
                   .forward(request, response);

            return;
        }

        try {

            int incomeId = Integer.parseInt(incomeIdValue);

            boolean deleted = incomeDAO.deleteIncome(
                    incomeId,
                    userId
            );

            if (deleted) {

                response.sendRedirect(
                        request.getContextPath() + "/income"
                );

                return;
            }

            request.setAttribute(
                    "errorMessage",
                    "Income could not be deleted."
            );

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "errorMessage",
                    "Invalid income record."
            );
        }

        request.getRequestDispatcher("/WEB-INF/views/income.jsp")
               .forward(request, response);
    }
}