package com.personalBudgetPlanner.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavingsGoalDAO {

    private static final String INSERT_GOAL_SQL =
            "INSERT INTO savings_goals "
            + "(user_id, goal_name, target_amount, current_amount, "
            + "monthly_contribution, target_date, goal_status) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_GOALS_BY_USER_SQL =
            "SELECT goal_id, goal_name, target_amount, current_amount, " +
            "monthly_contribution, target_date, goal_status " +
            "FROM savings_goals WHERE user_id = ? ORDER BY goal_id DESC";
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
    
    public List<Map<String, Object>> getSavingsGoalsByUserId(int userId) {

        List<Map<String, Object>> goals = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SELECT_GOALS_BY_USER_SQL)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Map<String, Object> goal = new HashMap<>();

                    goal.put(
                            "goalId",
                            resultSet.getInt("goal_id")
                    );

                    goal.put(
                            "goalName",
                            resultSet.getString("goal_name")
                    );

                    goal.put(
                            "targetAmount",
                            resultSet.getDouble("target_amount")
                    );

                    goal.put(
                            "currentAmount",
                            resultSet.getDouble("current_amount")
                    );

                    goal.put(
                            "monthlyContribution",
                            resultSet.getDouble("monthly_contribution")
                    );

                    goal.put(
                            "targetDate",
                            resultSet.getDate("target_date")
                    );

                    goal.put(
                            "goalStatus",
                            resultSet.getString("goal_status")
                    );

                    goals.add(goal);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return goals;
    }
}