/*
ProductService.java
//////////////////////
Spring's TransactionTemplate provides a simple way to manage 
transactions programmatically, which is useful when you need 
to handle transactions that don't fit into the typical declarative 
model (using annotations like @Transactional). This approach gives 
you granular control over when transactions begin, commit, or roll back.
/////////////////////
Key Components
1>TransactionTemplate: This is the core class that encapsulates the 
logic for managing a transaction. It handles the low-level details of 
obtaining, committing, and rolling back a transaction.
2>PlatformTransactionManager: The TransactionTemplate requires a 
PlatformTransactionManager to do its work. The PlatformTransactionManager 
is responsible for the actual interaction with the underlying transaction 
infrastructure (e.g., JDBC, JPA, JMS).
3>TransactionCallback: This is the functional interface you implement 
to contain the transactional code. The TransactionTemplate's execute() 
method takes a TransactionCallback as an argument.
*/

/*
The following is a simple Java example of how to use TransactionTemplate 
within a service class. In this setup, the TransactionTemplate is injected 
via the constructor.
*/
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.UUID;

@Service
public class ProductService {

    private final TransactionTemplate transactionTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductService(TransactionTemplate transactionTemplate, JdbcTemplate jdbcTemplate) {
        this.transactionTemplate = transactionTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createProductAndLog(String productName) {
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {
                    // Step 1: Insert the new product
                    String productId = UUID.randomUUID().toString();
                    String sqlInsertProduct = "INSERT INTO products (id, name) VALUES (?, ?)";
                    jdbcTemplate.update(sqlInsertProduct, productId, productName);

                    // Step 2: Insert a log entry for the new product
                    String sqlInsertLog = "INSERT INTO product_logs (product_id, log_message) VALUES (?, ?)";
                    jdbcTemplate.update(sqlInsertLog, productId, "Product created: " + productName);
                    
                    System.out.println("Product and log successfully created in a single transaction.");
                    
                } catch (Exception e) {
                    System.err.println("Transaction failed, rolling back.");
                    status.setRollbackOnly(); // Marks the transaction for rollback
                    throw e; // Re-throw the exception to allow the transaction to be rolled back
                }
                return null;
            }
        });
    }
}