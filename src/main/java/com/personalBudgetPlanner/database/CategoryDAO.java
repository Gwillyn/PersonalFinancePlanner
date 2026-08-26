package com.personalBudgetPlanner.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAO {

    private static final String FIND_CATEGORY_SQL =
            "SELECT category_id FROM categories "
            + "WHERE user_id = ? AND category_name = ?";

    private static final String INSERT_CATEGORY_SQL =
            "INSERT INTO categories "
            + "(user_id, category_name, category_type) "
            + "VALUES (?, ?, ?)";

    public Integer findCategoryId(int userId, String categoryName) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_CATEGORY_SQL)) {

            statement.setInt(1, userId);
            statement.setString(2, categoryName);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt("category_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Integer getOrCreateCategory(int userId,
                                       String categoryName,
                                       String categoryType) {

        Integer categoryId = findCategoryId(userId, categoryName);

        if (categoryId != null) {
            return categoryId;
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             INSERT_CATEGORY_SQL,
                             PreparedStatement.RETURN_GENERATED_KEYS
                     )) {

            statement.setInt(1, userId);
            statement.setString(2, categoryName);
            statement.setString(3, categoryType);

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
}