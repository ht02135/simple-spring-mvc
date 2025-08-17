
/*
TransactionIsolationExample.java
//////////////////////
Here's a complete Java example demonstrating how to use:
Connection.TRANSACTION_READ_UNCOMMITTED for isolation level.
Savepoint for simulating NESTED transaction-like behavior.
This is often used outside of Spring, where you control transactions manually using JDBC.
//////////////////////////
Isolation Level: READ_UNCOMMITTED allows reading uncommitted changes from other 
transactions (dirty reads).
Nested Transactions: JDBC doesn't support real nested transactions, but Savepoint 
allows you to simulate rollback within a larger transaction.
Auto-commit must be disabled to use transactions and savepoints manually.
///////////////////////////
*/

import java.sql.*;

public class TransactionExample {
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/your_database";
        String username = "your_username";
        String password = "your_password";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {

            // Set auto-commit to false for manual transaction control
            connection.setAutoCommit(false);

            // (1) Set isolation level to READ_UNCOMMITTED
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

            try (Statement stmt = connection.createStatement()) {
                // Perform some operation
                stmt.executeUpdate("INSERT INTO accounts (name, balance) VALUES ('Alice', 100)");

                // (2) Create a savepoint for nested transaction simulation
                Savepoint savePoint = connection.setSavepoint("Savepoint1");

                try {
                    // Simulate inner operation that might fail
                    stmt.executeUpdate("INSERT INTO accounts (name, balance) VALUES ('Bob', 200)");

                    // Simulate failure
                    if (true) {
                        throw new SQLException("Simulated exception in nested transaction");
                    }

                    // If all goes well, release savepoint
                    connection.releaseSavepoint(savePoint);
                } catch (SQLException e) {
                    System.out.println("Rolling back to savepoint due to error: " + e.getMessage());
                    connection.rollback(savePoint);  // Rollback only nested part
                }

                // Commit outer transaction
                connection.commit();
                System.out.println("Transaction committed.");
            } catch (SQLException e) {
                System.out.println("Rolling back entire transaction due to error: " + e.getMessage());
                connection.rollback();  // Rollback entire transaction
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// ///////////////////////////

/*
 * ✅ You want to use @Transactional annotation (in Spring)
✅ And specify Isolation.READ_UNCOMMITTED using that annotation
✅ No need for raw JDBC or manual setTransactionIsolation(...)
*/

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

@Service
public class AccountService {

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void transferMoney(Long fromAccountId, Long toAccountId, double amount) {
        // Your business logic goes here
        // E.g., fetch accounts, update balances, save entities
        System.out.println("Transferring money with READ_UNCOMMITTED isolation level.");
    }
}