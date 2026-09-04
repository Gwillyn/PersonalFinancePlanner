package com.personalBudgetPlanner.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseDAO {

    private static final String INSERT_EXPENSE_SQL =
            "INSERT INTO recurring_expenses "
            + "(user_id, category_id, expense_name, amount, payment_frequency) "
            + "VALUES (?, ?, ?, ?, ?)";

    public boolean addExpense(int userId,
                              int categoryId,
                              String expenseName,
                              double amount,
                              String paymentFrequency) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_EXPENSE_SQL)) {

            statement.setInt(1, userId);
            statement.setInt(2, categoryId);
            statement.setString(3, expenseName);
            statement.setDouble(4, amount);
            statement.setString(5, paymentFrequency);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Map<String, Object>> getActiveExpenses(int userId) {

        List<Map<String, Object>> expenses = new ArrayList<>();

        String sql =
                "SELECT expense_id, category_id, expense_name, amount, payment_frequency "
                + "FROM recurring_expenses "
                + "WHERE user_id = ? AND is_active = TRUE "
                + "ORDER BY expense_id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Map<String, Object> expense = new HashMap<>();

                    expense.put(
                            "expenseId",
                            resultSet.getInt("expense_id")
                    );

                    expense.put(
                            "categoryId",
                            resultSet.getInt("category_id")
                    );

                    expense.put(
                            "expenseName",
                            resultSet.getString("expense_name")
                    );

                    expense.put(
                            "amount",
                            resultSet.getDouble("amount")
                    );

                    expense.put(
                            "frequency",
                            resultSet.getString("payment_frequency")
                    );

                    expenses.add(expense);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses;
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

    public boolean updateExpense(int expenseId,
                                 int userId,
                                 int categoryId,
                                 String expenseName,
                                 double amount,
                                 String paymentFrequency) {

        String sql =
                "UPDATE recurring_expenses "
                + "SET category_id = ?, expense_name = ?, amount = ?, "
                + "payment_frequency = ? "
                + "WHERE expense_id = ? "
                + "AND user_id = ? "
                + "AND is_active = TRUE";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, categoryId);
            statement.setString(2, expenseName);
            statement.setDouble(3, amount);
            statement.setString(4, paymentFrequency);
            statement.setInt(5, expenseId);
            statement.setInt(6, userId);

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteExpense(int expenseId, int userId) {

        String sql =
                "UPDATE recurring_expenses "
                + "SET is_active = FALSE "
                + "WHERE expense_id = ? AND user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, expenseId);
            statement.setInt(2, userId);

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}