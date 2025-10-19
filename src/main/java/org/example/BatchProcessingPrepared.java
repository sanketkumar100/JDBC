package org.example;

import java.sql.*;
import java.util.Scanner;

public class BatchProcessingPrepared {

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

            // Prepare the SQL update query
            String sql = "UPDATE [Sales].[Customers] SET FirstName = ? WHERE CustomerID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            Scanner scanner = new Scanner(System.in);

            // User input loop
            while (true) {
                System.out.print("Enter Customer ID: ");
                int customerId = scanner.nextInt();

                System.out.print("Enter new First Name: ");
                String firstName = scanner.next();

                // Set parameters
                preparedStatement.setString(1, firstName);
                preparedStatement.setInt(2, customerId);

                // Add to batch
                preparedStatement.addBatch();

                System.out.print("Do you want to update more records? (Y/N): ");
                String choice = scanner.next();
                if (choice.equalsIgnoreCase("N")) {
                    break;
                }
            }

            // Execute the batch update
            int[] results = preparedStatement.executeBatch();

            // Check how many records were updated
            int successCount = 0;
            for (int i = 0; i < results.length; i++) {
                if (results[i] >= 0 || results[i] == Statement.SUCCESS_NO_INFO) {
                    System.out.println("Update " + (i + 1) + " executed successfully.");
                    successCount++;
                } else if (results[i] == Statement.EXECUTE_FAILED) {
                    System.out.println("Update " + (i + 1) + " failed.");
                }
            }

            // Summary
            if (successCount > 0) {
                System.out.println("Batch update completed successfully.");
            } else {
                System.out.println("No updates performed.");
            }

            // Clean up
            preparedStatement.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
