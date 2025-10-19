package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UpdatePrepared
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


            String sql ="UPDATE [Sales].[Customers] SET FirstName= ? WHERE CustomerID= ?"; //sql query

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,"prepareUpdated"); //new name
            ps.setInt(2,1056); //customer id to update first name

            int rowsAffected = ps.executeUpdate();//it returns no. of rows
            if(rowsAffected>0)
            {
                System.out.println("Data inserted" );
            }
            else
                System.out.println("Data not inserted" );



            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
