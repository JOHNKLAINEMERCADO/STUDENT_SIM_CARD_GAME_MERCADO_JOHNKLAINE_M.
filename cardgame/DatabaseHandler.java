package cardgame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {
    private Connection connection;
    // Method to set up a connection

    public Connection getConnection() {
        return connection;
    }

    public void setupConnection(String url, String user, String password) {
        try {
            // Load MySQL driver (optional for modern versions of Java)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    // Method to execute a query and return the result set
    public ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            if (connection == null || connection.isClosed()) {
                throw new IllegalStateException("Connection is not established or is closed.");
            }

            // Prepare the statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Execute the query
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL Error during query execution: " + e.getMessage());
        }

        return resultSet;
    }

// Method to execute update, insert, or delete queries
    public int executeUpdate(String query) {
        int rowsAffected = 0;
        try {
            if (connection == null || connection.isClosed()) {
                throw new IllegalStateException("Connection is not established or is closed.");
            }

            // Prepare the statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Execute the update
            rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Query executed successfully. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("SQL Error during update execution: " + e.getMessage());
        }

        return rowsAffected;
    }
    // Close the connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error while closing connection: " + e.getMessage());
        }
    }
}