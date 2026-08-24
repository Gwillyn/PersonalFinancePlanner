package com.personalBudgetPlanner.controller;

import java.io.IOException;

import com.personalBudgetPlanner.database.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

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

        UserDAO userDAO = new UserDAO();
        String[] profile = userDAO.getUserProfile(userId);

        if (profile != null) {

            session.setAttribute("firstName", profile[0]);
            session.setAttribute("lastName", profile[1]);
            session.setAttribute("userEmail", profile[2]);
            session.setAttribute("preferredCurrency", profile[3]);

        } else {

            request.setAttribute(
                    "errorMessage",
                    "Profile information could not be loaded."
            );
        }

        request.getRequestDispatcher("/WEB-INF/views/profile.jsp")
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

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String preferredCurrency =
                request.getParameter("preferredCurrency");

        if (firstName == null || firstName.trim().isEmpty()
                || lastName == null || lastName.trim().isEmpty()
                || preferredCurrency == null
                || preferredCurrency.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "First name, last name, and preferred currency are required."
            );

            request.getRequestDispatcher("/WEB-INF/views/profile.jsp")
                   .forward(request, response);

            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        UserDAO userDAO = new UserDAO();

        boolean updated = userDAO.updateProfile(
                userId,
                firstName.trim(),
                lastName.trim(),
                preferredCurrency
        );

        if (updated) {

            session.setAttribute("firstName", firstName.trim());
            session.setAttribute("lastName", lastName.trim());
            session.setAttribute(
                    "preferredCurrency",
                    preferredCurrency
            );

            request.setAttribute(
                    "successMessage",
                    "Profile was updated successfully."
            );

        } else {

            request.setAttribute(
                    "errorMessage",
                    "Profile could not be updated."
            );
        }

        request.getRequestDispatcher("/WEB-INF/views/profile.jsp")
               .forward(request, response);
    }
}