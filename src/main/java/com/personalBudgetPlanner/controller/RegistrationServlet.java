package com.personalBudgetPlanner.controller;

import java.io.IOException;

import com.personalBudgetPlanner.database.UserDAO;

import org.mindrot.jbcrypt.BCrypt;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    request.getRequestDispatcher("/WEB-INF/views/register.jsp")
        .forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String preferredCurrency = request.getParameter("preferredCurrency");

    if (firstName == null || firstName.trim().isEmpty()
        || lastName == null || lastName.trim().isEmpty()
        || email == null || email.trim().isEmpty()
        || password == null || password.trim().isEmpty()
        || preferredCurrency == null
        || preferredCurrency.trim().isEmpty()) {

      request.setAttribute(
          "errorMessage",
          "All registration fields are required.");

      request.getRequestDispatcher("/WEB-INF/views/register.jsp")
          .forward(request, response);

      return;
    }

    firstName = firstName.trim();
    lastName = lastName.trim();
    email = email.trim().toLowerCase();
    preferredCurrency = preferredCurrency.trim();

    UserDAO userDAO = new UserDAO();

    if (userDAO.emailExists(email)) {

      request.setAttribute(
          "errorMessage",
          "An account with this email already exists.");

      request.getRequestDispatcher("/WEB-INF/views/register.jsp")
          .forward(request, response);

      return;
    }

    // Implementation of password hashing for security
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

    boolean registered = userDAO.registerUser(
        firstName,
        lastName,
        email,
        hashedPassword,
        preferredCurrency);

    if (registered) {

      request.setAttribute(
          "successMessage",
          "Registration successful. You can now log in.");

    } else {

      request.setAttribute(
          "errorMessage",
          "Registration could not be completed.");
    }

    request.getRequestDispatcher("/WEB-INF/views/register.jsp")
        .forward(request, response);
  }
}
