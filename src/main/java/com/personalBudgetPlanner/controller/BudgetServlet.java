package com.personalBudgetPlanner.controller;

import java.io.IOException;
import java.time.LocalDate;

import com.personalBudgetPlanner.database.BudgetDAO;
import com.personalBudgetPlanner.database.CategoryDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.personalBudgetPlanner.database.IncomeDAO;

@WebServlet("/budget")
public class BudgetServlet extends HttpServlet {

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
    double monthlyIncome = incomeDAO.getMonthlyIncome(userId);

    request.setAttribute("monthlyIncome", monthlyIncome);

    request.getRequestDispatcher("/WEB-INF/views/budget.jsp")
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

    IncomeDAO incomeDAO = new IncomeDAO();
    double monthlyIncome = incomeDAO.getMonthlyIncome(userId);
    request.setAttribute("monthlyIncome", monthlyIncome);

    String budgetAmount = request.getParameter("budgetAmount");

    if (budgetAmount == null || budgetAmount.trim().isEmpty()) {

      request.setAttribute(
          "errorMessage",
          "Budget amount is required");

      request.getRequestDispatcher("/WEB-INF/views/budget.jsp")
          .forward(request, response);

      return;
    }

    try {

      double budget = Double.parseDouble(budgetAmount);

      if (monthlyIncome < 0 || budget < 0) {

        request.setAttribute(
            "errorMessage",
            "Amounts cannot be negative.");

      } else if (budget > monthlyIncome) {

        request.setAttribute(
            "errorMessage",
            "Budget amount cannot be greater than monthly income.");

      } else {

        LocalDate currentDate = LocalDate.now();

        int month = currentDate.getMonthValue();
        int year = currentDate.getYear();

        CategoryDAO categoryDAO = new CategoryDAO();

        Integer categoryId = categoryDAO.getOrCreateCategory(
            userId,
            "General Budget",
            "BUDGET");

        if (categoryId == null) {

          request.setAttribute(
              "errorMessage",
              "Budget category could not be created.");

        } else {

          BudgetDAO budgetDAO = new BudgetDAO();

          Integer planId = budgetDAO.getOrCreateBudgetPlan(
              userId,
              month,
              year);

          if (planId == null) {

            request.setAttribute(
                "errorMessage",
                "Budget plan could not be created.");

          } else {

            boolean saved = budgetDAO.addBudgetAllocation(
                planId,
                categoryId,
                budget);

            if (saved) {

              double remainingAmount = monthlyIncome - budget;

              request.setAttribute(
                  "successMessage",
                  "Budget was saved successfully.");

              request.setAttribute(
                  "remainingAmount",
                  remainingAmount);

            } else {

              request.setAttribute(
                  "errorMessage",
                  "Budget could not be saved.");
            }
          }
        }
      }

    } catch (NumberFormatException e) {

      request.setAttribute(
          "errorMessage",
          "Please enter valid numeric amounts.");
    }

    request.getRequestDispatcher("/WEB-INF/views/budget.jsp")
        .forward(request, response);
  }
}
