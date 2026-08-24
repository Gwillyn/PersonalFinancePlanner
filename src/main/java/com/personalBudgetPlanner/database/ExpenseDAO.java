package com.personalBudgetPlanner.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}