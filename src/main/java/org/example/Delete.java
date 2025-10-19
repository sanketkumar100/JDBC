package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Delete
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


            String sql = String.format("DELETE FROM [Sales].[Customers] WHERE CustomerID='%d'",1056); //sql query

            Statement stmt = conn.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);//it returns no. of rows
            if(rowsAffected>0)
            {
                System.out.println("Data deleted" );
            }
            else
                System.out.println("Data not deleted" );



            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
