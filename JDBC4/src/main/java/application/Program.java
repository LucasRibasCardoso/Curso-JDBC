package application;

import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Program {

  public static void main(String[] args) {

    Connection connection;
    PreparedStatement preparedStatement;

    try {
      connection = DB.getConnection();
      preparedStatement = connection.prepareStatement(
          "UPDATE seller SET BaseSalary = BaseSalary + ? WHERE (DepartmentId = ?)"
      );
      preparedStatement.setDouble(1, 199.0);
      preparedStatement.setInt(2, 2);

      int rowsAffected = preparedStatement.executeUpdate();

      System.out.println("Users update: " + rowsAffected);
    }
    catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

}


