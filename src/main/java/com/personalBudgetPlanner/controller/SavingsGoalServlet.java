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

        loadGoalsAndForward(request, response, session);
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

        if ("delete".equals(action)) {
            deleteGoal(request, response, session, userId);
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

            loadGoalsAndForward(request, response, session);
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

                Date date = Date.valueOf(targetDate);

                SavingsGoalDAO savingsGoalDAO =
                        new SavingsGoalDAO();

                if ("update".equals(action)) {

                    String goalIdValue =
                            request.getParameter("goalId");

                    if (goalIdValue == null
                            || goalIdValue.trim().isEmpty()) {

                        request.setAttribute(
                                "errorMessage",
                                "Savings goal could not be updated."
                        );

                    } else {

                        int goalId =
                                Integer.parseInt(goalIdValue);

                        boolean updated =
                                savingsGoalDAO.updateSavingsGoal(
                                        goalId,
                                        userId,
                                        goalName.trim(),
                                        target,
                                        current,
                                        monthly,
                                        date
                                );

                        if (updated) {

                            request.setAttribute(
                                    "successMessage",
                                    "Savings goal was updated successfully."
                            );

                        } else {

                            request.setAttribute(
                                    "errorMessage",
                                    "Savings goal could not be updated."
                            );
                        }
                    }

                } else {

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
            }

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "errorMessage",
                    "Please enter valid numeric values."
            );

        } catch (IllegalArgumentException e) {

            request.setAttribute(
                    "errorMessage",
                    "Please enter a valid target date."
            );
        }

        loadGoalsAndForward(request, response, session);
    }

    private void deleteGoal(HttpServletRequest request,
                            HttpServletResponse response,
                            HttpSession session,
                            int userId)
            throws ServletException, IOException {

        try {

            String goalIdValue =
                    request.getParameter("goalId");

            if (goalIdValue == null
                    || goalIdValue.trim().isEmpty()) {

                request.setAttribute(
                        "errorMessage",
                        "Savings goal could not be deleted."
                );

            } else {

                int goalId =
                        Integer.parseInt(goalIdValue);

                SavingsGoalDAO savingsGoalDAO =
                        new SavingsGoalDAO();

                boolean deleted =
                        savingsGoalDAO.deleteSavingsGoal(
                                goalId,
                                userId
                        );

                if (deleted) {

                    request.setAttribute(
                            "successMessage",
                            "Savings goal was deleted successfully."
                    );

                } else {

                    request.setAttribute(
                            "errorMessage",
                            "Savings goal could not be deleted."
                    );
                }
            }

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "errorMessage",
                    "Invalid savings goal."
            );
        }

        loadGoalsAndForward(request, response, session);
    }

    private void loadGoalsAndForward(HttpServletRequest request,
                                     HttpServletResponse response,
                                     HttpSession session)
            throws ServletException, IOException {

        int userId =
                (Integer) session.getAttribute("userId");

        SavingsGoalDAO savingsGoalDAO =
                new SavingsGoalDAO();

        request.setAttribute(
                "savingsGoals",
                savingsGoalDAO.getSavingsGoalsByUserId(userId)
        );

        request.getRequestDispatcher("/WEB-INF/views/savings.jsp")
               .forward(request, response);
    }
}