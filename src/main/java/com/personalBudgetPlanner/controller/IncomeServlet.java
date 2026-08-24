package com.personalBudgetPlanner.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.personalBudgetPlanner.database.DBConnection;
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

        if (session == null || session.getAttribute("userEmail") == null) {
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

        if (session == null || session.getAttribute("userEmail") == null) {
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

                String userEmail =
                        (String) session.getAttribute("userEmail");

                Integer userId = findUserIdByEmail(userEmail);

                if (userId == null) {

                    request.setAttribute(
                            "errorMessage",
                            "No database user was found for this login email."
                    );

                } else {

                    IncomeDAO incomeDAO = new IncomeDAO();

                    boolean saved = incomeDAO.addIncome(
                            userId,
                            incomeName.trim(),
                            incomeAmount,
                            frequency
                    );

                    if (saved) {

                        request.setAttribute(
                                "successMessage",
                                "Income was saved successfully."
                        );

                    } else {

                        request.setAttribute(
                                "errorMessage",
                                "Income could not be saved."
                        );
                    }
                }
            }

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "errorMessage",
                    "Please enter a valid income amount."
            );

        } catch (SQLException e) {

            e.printStackTrace();

            request.setAttribute(
                    "errorMessage",
                    "A database error occurred while saving the income."
            );
        }

        request.getRequestDispatcher("/WEB-INF/views/income.jsp")
               .forward(request, response);
    }

    private Integer findUserIdByEmail(String email) throws SQLException {

        String sql =
                "SELECT user_id FROM users WHERE email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt("user_id");
                }
            }
        }

        return null;
    }
}