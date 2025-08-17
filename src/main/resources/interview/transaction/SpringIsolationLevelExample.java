
/*
SpringIsolationLevelExample.java
//////////////////////////
In Spring, isolation levels for database transactions are typically set using the 
@Transactional annotation. Spring delegates this setting to the underlying database 
via the JDBC Connection object.
Here’s a simple example to demonstrate how Spring sets isolation levels on a 
database connection using @Transactional.
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // This method will run with the specified isolation level
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void transferMoney(Long fromAccountId, Long toAccountId, double amount) {
        Account from = accountRepository.findById(fromAccountId).orElseThrow();
        Account to = accountRepository.findById(toAccountId).orElseThrow();

        from.withdraw(amount);
        to.deposit(amount);

        accountRepository.save(from);
        accountRepository.save(to);
    }
}