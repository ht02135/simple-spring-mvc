/*
JDBCTransactionExample.java
////////////////////
DriverManager.getConnection(...) is used here, but in real-world apps you’d 
often use a DataSource (like from HikariCP, Tomcat, etc.).
setAutoCommit(false) means you control when the transaction ends.
If everything succeeds → commit().
If an exception occurs → rollback().
try-with-resources ensures the Connection and PreparedStatements are closed automatically.
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb"; // your DB URL
        String user = "root";  // your DB username
        String password = "password"; // your DB password

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            connection.setAutoCommit(false); // (2)

            try (PreparedStatement stmt1 = connection.prepareStatement(
                        "INSERT INTO accounts (id, balance) VALUES (?, ?)");
                 PreparedStatement stmt2 = connection.prepareStatement(
                        "UPDATE accounts SET balance = balance - ? WHERE id = ?")) {

                // Insert new account
                stmt1.setInt(1, 1001);
                stmt1.setDouble(2, 500.00);
                stmt1.executeUpdate();

                // Deduct from another account
                stmt2.setDouble(1, 200.00);
                stmt2.setInt(2, 1002);
                stmt2.executeUpdate();

                connection.commit(); // (3)
                System.out.println("Transaction committed successfully.");
            } catch (SQLException e) {
                connection.rollback(); // (4)
                System.err.println("Transaction rolled back: " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

