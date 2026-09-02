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

                int userId = (Integer) session.getAttribute("userId");

                IncomeDAO incomeDAO = new IncomeDAO();

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
}