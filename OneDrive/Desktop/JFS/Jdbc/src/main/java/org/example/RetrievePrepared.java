package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class RetrievePrepared
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


            String sql ="INSERT INTO [Sales].[Customers](CustomerID,FirstName,LastName) VALUES(?,?,?)"; //sql query

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,1056);
            ps.setString(2,"Golu");
            ps.setString(3,"yadav");

            ps.setInt(1,1059);
            ps.setString(2,"Golaaa");
            ps.setString(3,"yadav");
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
