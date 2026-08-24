package com.personalBudgetPlanner.controller;

import java.io.IOException;

import com.personalBudgetPlanner.database.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/login.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "Email and password are required."
            );

            request.getRequestDispatcher("/WEB-INF/views/login.jsp")
                   .forward(request, response);

            return;
        }

        UserDAO userDAO = new UserDAO();

        boolean validLogin =
                userDAO.validateLogin(email.trim(), password);

        if (!validLogin) {

            request.setAttribute(
                    "errorMessage",
                    "Invalid email or password."
            );

            request.getRequestDispatcher("/WEB-INF/views/login.jsp")
                   .forward(request, response);

            return;
        }

        Integer userId =
                userDAO.findUserIdByEmail(email.trim());

        if (userId == null) {

            request.setAttribute(
                    "errorMessage",
                    "Unable to load user account."
            );

            request.getRequestDispatcher("/WEB-INF/views/login.jsp")
                   .forward(request, response);

            return;
        }

        HttpSession session = request.getSession();

        session.setAttribute("userId", userId);
        session.setAttribute("userEmail", email.trim());

        response.sendRedirect(
                request.getContextPath() + "/dashboard"
        );
    }
}