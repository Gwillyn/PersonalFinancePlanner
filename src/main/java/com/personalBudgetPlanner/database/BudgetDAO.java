package com.personalBudgetPlanner.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BudgetDAO {

    private static final String FIND_PLAN_SQL =
            "SELECT plan_id FROM budget_plans "
            + "WHERE user_id = ? AND plan_month = ? AND plan_year = ?";

    private static final String INSERT_PLAN_SQL =
            "INSERT INTO budget_plans "
            + "(user_id, plan_month, plan_year) "
            + "VALUES (?, ?, ?)";

    private static final String INSERT_ALLOCATION_SQL =
            "INSERT INTO budget_allocations "
            + "(plan_id, category_id, allocated_amount) "
            + "VALUES (?, ?, ?)";

    public Integer getOrCreateBudgetPlan(int userId,
                                         int month,
                                         int year) {

        Integer existingPlanId =
                findBudgetPlanId(userId, month, year);

        if (existingPlanId != null) {
            return existingPlanId;
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             INSERT_PLAN_SQL,
                             PreparedStatement.RETURN_GENERATED_KEYS
                     )) {

            statement.setInt(1, userId);
            statement.setInt(2, month);
            statement.setInt(3, year);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {

                try (ResultSet generatedKeys =
                             statement.getGeneratedKeys()) {

                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Integer findBudgetPlanId(int userId,
                                    int month,
                                    int year) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_PLAN_SQL)) {

            statement.setInt(1, userId);
            statement.setInt(2, month);
            statement.setInt(3, year);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt("plan_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean addBudgetAllocation(int planId,
                                       int categoryId,
                                       double allocatedAmount) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             INSERT_ALLOCATION_SQL
                     )) {

            statement.setInt(1, planId);
            statement.setInt(2, categoryId);
            statement.setDouble(3, allocatedAmount);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}