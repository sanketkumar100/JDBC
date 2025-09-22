package org.example;
import java.sql.*;
import java.util.Scanner;

public class BatchProcessing {

    public static void main(String[] args) {
        String serverName = "localhost\\SQLEXPRESS";
        int port = 1433;
        String databaseName = "SalesDB";
        String username = "sa";
        String password = "Ks120@120";

        // Build connection URL

        String connectionUrl = "jdbc:sqlserver://localhost:" + port +
                ";databaseName=" + databaseName + ";encrypt=false";

        try {
            // Load JDBC Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Connect to the database
            Connection conn = DriverManager.getConnection(connectionUrl, username, password);
            System.out.println("Connected to the database!");

            // Create Statement object
            Statement statement = conn.createStatement();

            Scanner scanner = new Scanner(System.in);

            // Input loop for batch insert
            while (true) {
                System.out.print("Enter First Name: ");
                String firstName = scanner.next();

                // Build INSERT SQL string
                String sql = "INSERT INTO [Sales].[Customers](FirstName) VALUES ('" + firstName + "')";

                // Add to batch
                statement.addBatch(sql);

                System.out.print("Do you want to add more records? (Y/N): ");
                String choice = scanner.next();
                if (choice.equalsIgnoreCase("N")) {
                    break;
                }
            }

            // Execute the batch insert
            int[] results = statement.executeBatch();

            // Display results
            int successCount = 0;
            for (int i = 0; i < results.length; i++) {
                if (results[i] >= 0 || results[i] == Statement.SUCCESS_NO_INFO) {
                    System.out.println("Insert " + (i + 1) + " executed successfully.");
                    successCount++;
                } else if (results[i] == Statement.EXECUTE_FAILED) {
                    System.out.println("Insert " + (i + 1) + " failed.");
                }
            }

            // Summary
            if (successCount > 0) {
                System.out.println("Batch insert completed successfully.");
            } else {
                System.out.println("No rows inserted.");
            }

            // Clean up
            statement.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

