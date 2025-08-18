package x.y.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Isolation;

@Service
public class InvoiceService {

    /**
     * Generates the invoice PDF (and might log something in DB).
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void createPdf() {
        // ... generate PDF, maybe persist invoice metadata in DB
        System.out.println("Invoice PDF created.");
    }
}