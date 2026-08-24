package com.personalBudgetPlanner.controller;

import java.io.IOException;
import java.sql.Date;

import com.personalBudgetPlanner.database.SavingsGoalDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/savings")
public class SavingsGoalServlet extends HttpServlet {

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

        request.getRequestDispatcher("/WEB-INF/views/savings.jsp")
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

        String goalName = request.getParameter("goalName");
        String targetAmount = request.getParameter("targetAmount");
        String currentAmount = request.getParameter("currentAmount");
        String monthlyContribution =
                request.getParameter("monthlyContribution");
        String targetDate = request.getParameter("targetDate");

        if (goalName == null || goalName.trim().isEmpty()
                || targetAmount == null || targetAmount.trim().isEmpty()
                || currentAmount == null || currentAmount.trim().isEmpty()
                || monthlyContribution == null
                || monthlyContribution.trim().isEmpty()
                || targetDate == null || targetDate.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "All savings goal fields are required."
            );

            request.getRequestDispatcher("/WEB-INF/views/savings.jsp")
                   .forward(request, response);

            return;
        }

        try {

            double target = Double.parseDouble(targetAmount);
            double current = Double.parseDouble(currentAmount);
            double monthly = Double.parseDouble(monthlyContribution);

            if (target <= 0 || current < 0 || monthly < 0) {

                request.setAttribute(
                        "errorMessage",
                        "Please enter valid positive amounts."
                );

            } else if (current > target) {

                request.setAttribute(
                        "errorMessage",
                        "Current amount cannot be greater than target amount."
                );

            } else {

                int userId =
                        (Integer) session.getAttribute("userId");

                Date date = Date.valueOf(targetDate);

                SavingsGoalDAO savingsGoalDAO =
                        new SavingsGoalDAO();

                boolean saved =
                        savingsGoalDAO.addSavingsGoal(
                                userId,
                                goalName.trim(),
                                target,
                                current,
                                monthly,
                                date
                        );

                if (saved) {

                    request.setAttribute(
                            "successMessage",
                            "Savings goal was saved successfully."
                    );

                } else {

                    request.setAttribute(
                            "errorMessage",
                            "Savings goal could not be saved."
                    );
                }
            }

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "errorMessage",
                    "Please enter valid numeric amounts."
            );

        } catch (IllegalArgumentException e) {

            request.setAttribute(
                    "errorMessage",
                    "Please enter a valid target date."
            );
        }

        request.getRequestDispatcher("/WEB-INF/views/savings.jsp")
               .forward(request, response);
    }
}