package com.personalBudgetPlanner.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SavingsGoalDAO {

    private static final String INSERT_GOAL_SQL =
            "INSERT INTO savings_goals "
            + "(user_id, goal_name, target_amount, current_amount, "
            + "monthly_contribution, target_date, goal_status) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    public boolean addSavingsGoal(int userId,
                                  String goalName,
                                  double targetAmount,
                                  double currentAmount,
                                  double monthlyContribution,
                                  java.sql.Date targetDate) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_GOAL_SQL)) {

            statement.setInt(1, userId);
            statement.setString(2, goalName);
            statement.setDouble(3, targetAmount);
            statement.setDouble(4, currentAmount);
            statement.setDouble(5, monthlyContribution);
            statement.setDate(6, targetDate);
            statement.setString(7, "ACTIVE");

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}