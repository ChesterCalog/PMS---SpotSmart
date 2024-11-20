package Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Handles establishing a connection to a database.
//Demonstrates abstraction by hiding implementation details(?)
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/parkingmanagement";
    private static final String USER = "ketch";
    private static final String PASSWORD = "Chessy122804";

    //Returns a connection object interacting with the database.
    //Uses abstraction to shield the client code from connection details.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
