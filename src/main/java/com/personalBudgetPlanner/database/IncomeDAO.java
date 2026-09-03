package com.personalBudgetPlanner.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IncomeDAO {

    private static final String INSERT_INCOME_SQL =
            "INSERT INTO income_sources "
            + "(user_id, income_name, amount, payment_frequency) "
            + "VALUES (?, ?, ?, ?)";

    public boolean addIncome(int userId,
                             String incomeName,
                             double amount,
                             String paymentFrequency) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_INCOME_SQL)) {

            statement.setInt(1, userId);
            statement.setString(2, incomeName);
            statement.setDouble(3, amount);
            statement.setString(4, paymentFrequency);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getTotalIncome(int userId) {

        String sql = "SELECT COALESCE(SUM(amount), 0) AS total_income "
                + "FROM income_sources "
                + "WHERE user_id = ? AND is_active = TRUE";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getDouble("total_income");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public boolean updateIncome(int incomeId,
                                int userId,
                                String incomeName,
                                double amount,
                                String paymentFrequency) {

        String sql = "UPDATE income_sources "
                + "SET income_name = ?, amount = ?, payment_frequency = ? "
                + "WHERE income_id = ? AND user_id = ? AND is_active = TRUE";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, incomeName);
            statement.setDouble(2, amount);
            statement.setString(3, paymentFrequency);
            statement.setInt(4, incomeId);
            statement.setInt(5, userId);

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteIncome(int incomeId, int userId) {

        String sql = "UPDATE income_sources "
                + "SET is_active = FALSE "
                + "WHERE income_id = ? AND user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, incomeId);
            statement.setInt(2, userId);

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}