package com.personalBudgetPlanner.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.personalBudgetPlanner.database.BudgetDAO;
import com.personalBudgetPlanner.database.IncomeDAO;
import com.personalBudgetPlanner.database.ExpenseDAO;

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

    IncomeDAO incomeDAO = new IncomeDAO();
    ExpenseDAO expenseDAO = new ExpenseDAO();
    BudgetDAO budgetDAO = new BudgetDAO();

    double totalIncome = incomeDAO.getTotalIncome(userId);

    double totalExpenses = expenseDAO.getTotalExpenses(userId);

    double totalBudget = budgetDAO.getTotalBudget(userId);

    double remainingBalance = totalIncome - totalExpenses;

    request.setAttribute(
        "totalIncome",
        totalIncome);

    request.setAttribute(
        "totalExpenses",
        totalExpenses);

    request.setAttribute(
        "totalBudget",
        totalBudget);

    request.setAttribute(
        "remainingBalance",
        remainingBalance);

    request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp")
        .forward(request, response);
  }
}
