package com.personalBudgetPlanner.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class IncomeDAO {

  private static final String INSERT_INCOME_SQL = "INSERT INTO income_sources "
      + "(user_id, income_name, amount, payment_frequency) "
      + "VALUES (?, ?, ?, ?)";

  public boolean addIncome(int userId,
      String incomeName,
      double amount,
      String paymentFrequency) {

    try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT_INCOME_SQL)) {

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

}
