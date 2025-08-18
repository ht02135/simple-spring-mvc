
/*
now we’re getting into the real subtleties of Spring’s @Transactional when 
one service calls another service.

Let’s extend our previous UserService/CourseService example with an 
InvoiceService and an invoice() method on UserService that calls it.
//////////////////////////
What Happens in Spring
1>When userService.invoice() is called:
Spring starts a transaction (because of @Transactional).
Inside, it calls invoiceService.createPdf().
Because both use Propagation.REQUIRED, Spring does not start a new 
transaction; instead it reuses the existing one.
From the DB’s perspective, there is only one physical transaction (exactly what you want).
2>If invoiceService.createPdf() throws a RuntimeException, the whole 
transaction rolls back, including work done in UserService.invoice().
///////////////////////////
Propagation Levels — What to Use
1>Propagation.REQUIRED (✅ recommended default)
Joins the existing transaction if one exists, otherwise creates a new one.
Ensures UserService.invoice() and InvoiceService.createPdf() run in the same transaction.
2>Other propagation options:
REQUIRES_NEW: would suspend the outer transaction and start a new one (not desirable 
here, because you’d end up with two separate transactions).
SUPPORTS or NOT_SUPPORTED: usually not what you want if persistence is involved.
3>👉 So: use Propagation.REQUIRED for both.
/////////////////////////////
Isolation Levels — What to Use
1>Isolation.READ_COMMITTED (✅ recommended default in most RDBMS like MySQL, PostgreSQL, Oracle):
Prevents dirty reads (can’t see uncommitted changes from other transactions).
Good balance between safety and performance.
2>Other options (rarely needed unless you have strong consistency requirements):
Isolation.REPEATABLE_READ: prevents non-repeatable reads (used in MySQL InnoDB by default).
Isolation.SERIALIZABLE: strictest, prevents phantom reads, but expensive.
3>👉 So: use Isolation.READ_COMMITTED unless you have a specific consistency issue 
requiring stricter isolation.
//////////////////////////////
For your invoice example:
@Transactional(
    propagation = Propagation.REQUIRED, 
    isolation = Isolation.READ_COMMITTED
)
Both UserService and InvoiceService should use REQUIRED → so they share the same 
physical transaction.
Isolation level: use READ_COMMITTED (or REPEATABLE_READ if you’re on MySQL default).
Result: from Spring’s view there are “two logical transactions” (because two 
methods are annotated), but Spring is smart enough to collapse them into one actual DB transaction.
*/
package x.y.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private InvoiceService invoiceService;

    /**
     * Creates an invoice for a user.
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void invoice() {
        // transactional boundary starts here
        invoiceService.createPdf();
        // possibly send invoice via email, update db, etc.
    }
}