package org.example;
import java.lang.*;
import java.sql.*;
public class Retrieve
{
    public static void main(String[] args)
    {
        String serverName = "localhost\\SQLEXPRESS";
        int port = 1433;
        String databaseName = "SalesDB";
        String username = "sa";
        String password = "Ks120@120";

        // Build connection URL using variables correctly
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=SalesDB;encrypt=false";


        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Connection conn = DriverManager.getConnection(connectionUrl, username, password);
            System.out.println("Connected to the database!");


            String sql = "SELECT * FROM [Sales].[Customers]"; //sql query

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);//the sql query is executed and it return the result
                                                  //of type "Resultset" as it is performingn read operation

            while (rs.next()) {
                System.out.println("First Name: " + rs.getString("FirstName"));
                System.out.println("Last Name: " + rs.getString("LastName"));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
