@Service
public class InvoiceService {
    @Autowired
    private InvoiceDao invoiceDao;

    @Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.READ_COMMITTED
    )
    public Invoice createPdf(Long userId) {
        String pdfPath = "/tmp/invoice_" + userId + ".pdf";
        Invoice invoice = new Invoice(pdfPath);
        invoiceDao.save(invoice);
        System.out.println("Invoice created at: " + pdfPath);
        return invoice;
    }

    // ================= NEW DAO METHODS =================

    @Transactional(readOnly = true)
    public List<Invoice> getAllInvoices() {
        return invoiceDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Invoice> getInvoicesByUserId(Long userId) {
        return invoiceDao.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Long countInvoices() {
        return invoiceDao.countInvoices();
    }
}
