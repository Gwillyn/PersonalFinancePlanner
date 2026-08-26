package com.personalBudgetPlanner.controller;

import java.io.IOException;

import com.personalBudgetPlanner.database.DashboardDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

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

        DashboardDAO dashboardDAO = new DashboardDAO();

        double totalIncome =
                dashboardDAO.getTotalIncome(userId);

        double totalExpenses =
                dashboardDAO.getTotalExpenses(userId);

        double totalBudget =
                dashboardDAO.getTotalBudget(userId);

        double remainingBalance =
                totalIncome - totalExpenses;

        request.setAttribute(
                "totalIncome",
                totalIncome
        );

        request.setAttribute(
                "totalExpenses",
                totalExpenses
        );

        request.setAttribute(
                "totalBudget",
                totalBudget
        );

        request.setAttribute(
                "remainingBalance",
                remainingBalance
        );

        request.getRequestDispatcher("/WEB-INF/views/index.jsp")
               .forward(request, response);
    }
}