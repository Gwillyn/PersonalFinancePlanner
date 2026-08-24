package com.personalBudgetPlanner.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAO {

    public double getTotalIncome(int userId) {

        String sql =
                "SELECT COALESCE(SUM(amount), 0) AS total_income "
                + "FROM income_sources "
                + "WHERE user_id = ? AND is_active = TRUE";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

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

    public double getTotalExpenses(int userId) {

        String sql =
                "SELECT COALESCE(SUM(amount), 0) AS total_expenses "
                + "FROM recurring_expenses "
                + "WHERE user_id = ? AND is_active = TRUE";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getDouble("total_expenses");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public double getTotalBudget(int userId) {

        String sql =
                "SELECT COALESCE(SUM(ba.allocated_amount), 0) AS total_budget "
                + "FROM budget_allocations ba "
                + "JOIN budget_plans bp ON ba.plan_id = bp.plan_id "
                + "WHERE bp.user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getDouble("total_budget");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }
}