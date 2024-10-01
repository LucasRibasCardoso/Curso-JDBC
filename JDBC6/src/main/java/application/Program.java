package application;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {

  public static void main(String[] args) {

    Connection connection = null;
    Statement statement = null;

    try{
      connection = DB.getConnection();
      connection.setAutoCommit(false);

      statement = connection.createStatement();

      int rowsAffectedDepartment1 = statement.executeUpdate(
          "UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1"
      );

      /**
       * Simulação de erro para fins de testes
       *
       * int x = 1;
       * if (x < 2){
       *   throw new SQLException("Fake error");
       * }
        */

      int rowsAffectedDepartment2 = statement.executeUpdate(
          "UPDATE seller SET BaseSalary = 2099 WHERE DepartmentId = 2"
      );

      connection.commit();

      System.out.println("Rows affected in department 1: " + rowsAffectedDepartment1);
      System.out.println("Rows affected in department 2: " + rowsAffectedDepartment2);
    }
    catch(SQLException e){
      try {
        connection.rollback();
        throw new DbException("Transition rolled back! Caused by: + " + e.getMessage());
      }
      catch (SQLException ex) {
        throw new DbException("Error trying to rollback! Caused by: + " + ex.getMessage());
      }
    }
    finally {
      DB.closeStatement(statement);
      DB.closeConnection();
    }
  }
}


