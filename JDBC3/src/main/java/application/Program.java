package application;

import db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {

  public static void main(String[] args) {

    Connection connection;
    PreparedStatement preparedStatement = null;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    try {
      connection = DB.getConnection();

      preparedStatement = connection.prepareStatement(
          "INSERT INTO seller " + "(Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES "
              + "(?, ?, ?, ?, ?)",
          Statement.RETURN_GENERATED_KEYS);

      preparedStatement.setString(1, "Lucas");
      preparedStatement.setString(2, "Lucas@gmail.com");
      preparedStatement.setDate(3, new java.sql.Date(
          simpleDateFormat.parse("05/09/2005").getTime())
      );
      preparedStatement.setDouble(4, 3000.0);
      preparedStatement.setInt(5, 4);

      /**
       * Outra maneira de inserir dados na tabela de forma mais prática
       * preparedStatement = connection.prepareStatement(
       *           "INSERT INTO department (Name) VALUES ('D1'), ('D2')",
       *           Statement.RETURN_GENERATED_KEYS
       *       );
       */

      int rowsAffected = preparedStatement.executeUpdate();

      if (rowsAffected > 0) {
        ResultSet rowsAffectedID = preparedStatement.getGeneratedKeys();

        while (rowsAffectedID.next()) {
          int id = rowsAffectedID.getInt(1);
          System.out.println("Done! Id = " + id);
        }
      }
      else {
        System.out.println("Not rows affected");
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    catch (ParseException e) {
      e.printStackTrace();
    }

    finally {
      DB.closeStatement(preparedStatement);
      DB.closeConnection();
    }
  }

}

