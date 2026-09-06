package com.personalBudgetPlanner.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  public List<Map<String, Object>> getActiveIncomes(int userId) {

    List<Map<String, Object>> incomes = new ArrayList<>();

    String sql = "SELECT income_id, income_name, amount, payment_frequency "
        + "FROM income_sources "
        + "WHERE user_id = ? AND is_active = TRUE "
        + "ORDER BY income_id DESC";

    try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setInt(1, userId);

      try (ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {

          Map<String, Object> income = new HashMap<>();

          income.put(
              "incomeId",
              resultSet.getInt("income_id"));

          income.put(
              "incomeName",
              resultSet.getString("income_name"));

          income.put(
              "amount",
              resultSet.getDouble("amount"));

          income.put(
              "frequency",
              resultSet.getString("payment_frequency"));

          incomes.add(income);
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return incomes;
  }

  public double getMonthlyIncome(int userId) {
    double monthlyIncome = 0.0;
    List<Map<String, Object>> incomes = getActiveIncomes(userId);

    for (int i = 0; i < incomes.size(); i++) {
      Map<String, Object> income = incomes.get(i);
      double amount = (Double) income.get("amount");
      String frequency = (String) income.get("frequency");

      switch (frequency) {
        case "Weekly":
          monthlyIncome += amount * 52 / 12;
          break;
        case "Biweekly":
          monthlyIncome += amount * 26 / 12;
          break;
        case "Monthly":
          monthlyIncome += amount;
          break;
        case "Yearly":
          monthlyIncome += amount / 12;
          break;
      }
    }
    return monthlyIncome;
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
        + "WHERE income_id = ? "
        + "AND user_id = ? "
        + "AND is_active = TRUE";

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
