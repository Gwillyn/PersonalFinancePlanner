package com.personalBudgetPlanner.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}