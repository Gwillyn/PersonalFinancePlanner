package com.personalBudgetPlanner.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static final String FIND_USER_BY_EMAIL_SQL =
            "SELECT user_id, first_name, last_name, email, password_hash, preferred_currency "
            + "FROM users WHERE email = ?";

    private static final String FIND_USER_BY_ID_SQL =
            "SELECT user_id, first_name, last_name, email, password_hash, preferred_currency "
            + "FROM users WHERE user_id = ?";

    private static final String INSERT_USER_SQL =
            "INSERT INTO users "
            + "(first_name, last_name, email, password_hash, preferred_currency) "
            + "VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_PROFILE_SQL =
            "UPDATE users "
            + "SET first_name = ?, last_name = ?, preferred_currency = ? "
            + "WHERE user_id = ?";

    public Integer findUserIdByEmail(String email) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_USER_BY_EMAIL_SQL)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt("user_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean emailExists(String email) {
        return findUserIdByEmail(email) != null;
    }

    public boolean registerUser(String firstName,
                                String lastName,
                                String email,
                                String passwordHash,
                                String preferredCurrency) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_USER_SQL)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, passwordHash);
            statement.setString(5, preferredCurrency);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateLogin(String email, String password) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_USER_BY_EMAIL_SQL)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    String storedPassword =
                            resultSet.getString("password_hash");

                    return storedPassword.equals(password);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateProfile(int userId,
                                 String firstName,
                                 String lastName,
                                 String preferredCurrency) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_PROFILE_SQL)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, preferredCurrency);
            statement.setInt(4, userId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String[] getUserProfile(int userId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_USER_BY_ID_SQL)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new String[] {
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("email"),
                            resultSet.getString("preferred_currency")
                    };
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}