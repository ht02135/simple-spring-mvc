package x.y.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Isolation;
import x.y.dao.InvoiceDao;
import x.y.model.Invoice;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceDao invoiceDao;

    @Transactional(
        propagation = Propagation.REQUIRED, 
        isolation = Isolation.READ_COMMITTED
    )
    public Invoice createPdf(Long userId) {
        // simulate creating invoice file
        String pdfPath = "/tmp/invoice_" + userId + ".pdf";
        Invoice invoice = new Invoice(pdfPath);
        invoiceDao.save(invoice);
        System.out.println("Invoice created at: " + pdfPath);
        return invoice;
    }
}