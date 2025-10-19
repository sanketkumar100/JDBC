
    package org.example;

import java.sql.*;
import java.util.Scanner;

    public class TransactionHandling {

        public static void main(String[] args) {
            String serverName = "localhost\\SQLEXPRESS";
            int port = 1433;
            String databaseName = "SalesDB";
            String username = "sa";
            String password = "Ks120@120";

            // Build connection URL
            String connectionUrl = "jdbc:sqlserver://localhost:" + port +
                    ";databaseName=" + databaseName + ";encrypt=false";

            Connection connection = null;

            try {
                // Load SQL Server JDBC driver
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                // Establish the connection
                connection = DriverManager.getConnection(connectionUrl, username, password);
                System.out.println("Connected to the database!");

                // Disable auto-commit for transaction management
                connection.setAutoCommit(false);

                Scanner scanner = new Scanner(System.in);

                System.out.print("Enter Sender Account Number: ");
                int senderAccount = scanner.nextInt();

                System.out.print("Enter Amount to Transfer: ");
                double amount = scanner.nextDouble();

                int receiverAccount = 102; // Hardcoded destination account

                // Check for sufficient funds
                if (!isSufficient(connection, senderAccount, amount)) {
                    System.out.println("Transaction Failed: Insufficient balance.");
                    connection.rollback();
                    return;
                }

                // Prepare SQL queries
                String debitQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
                String creditQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

                PreparedStatement debitStatement = connection.prepareStatement(debitQuery);
                PreparedStatement creditStatement = connection.prepareStatement(creditQuery);

                // Set parameters for debit
                debitStatement.setDouble(1, amount);
                debitStatement.setInt(2, senderAccount);

                // Set parameters for credit
                creditStatement.setDouble(1, amount);
                creditStatement.setInt(2, receiverAccount);

                // Execute update queries
                debitStatement.executeUpdate();
                creditStatement.executeUpdate();

                // Commit transaction
                connection.commit();
                System.out.println("Transaction Successful!");

            } catch (Exception e) {
                try {
                    if (connection != null) {
                        connection.rollback(); // Rollback in case of error
                    }
                } catch (SQLException ex) {
                    System.out.println("Rollback failed: " + ex.getMessage());
                }
                System.out.println("Error: " + e.getMessage());
            } finally {
                try {
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    System.out.println("Failed to close connection: " + e.getMessage());
                }
            }
        }

        // Helper method to check balance
        static boolean isSufficient(Connection connection, int accountNumber, double amount) {
            String query = "SELECT balance FROM accounts WHERE account_number = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, accountNumber);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    double currentBalance = resultSet.getDouble("balance");
                    return currentBalance >= amount;
                }
            } catch (SQLException e) {
                System.out.println("Error checking balance: " + e.getMessage());
            }
            return false;
        }
    }


