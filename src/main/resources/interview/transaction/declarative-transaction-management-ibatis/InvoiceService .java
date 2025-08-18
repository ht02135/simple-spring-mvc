package x.y.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import x.y.mapper.InvoiceMapper;
import x.y.model.Invoice;

import java.time.LocalDateTime;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Transactional
    public Invoice createPdf(Long userId) {
        Invoice invoice = new Invoice();
        invoice.setPdfPath("/tmp/invoice_" + userId + ".pdf");
        invoice.setCreatedAt(LocalDateTime.now());
        invoiceMapper.insert(invoice);
        System.out.println("Invoice created at " + invoice.getPdfPath());
        return invoice;
    }
}