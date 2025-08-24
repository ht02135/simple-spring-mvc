/*
2. Why MyBatis (UserMapper, InvoiceMapper) services only have:
@Transactional
-----------------
1>With MyBatis/iBatis, Spring usually wires a DataSourceTransactionManager 
instead of a JpaTransactionManager.
2>Defaults of @Transactional are Propagation.REQUIRED and 
Isolation.DEFAULT (which means “use database’s default isolation level” → MySQL = READ_COMMITTED).
3>So writing only @Transactional is equivalent to what you see in 
the Hibernate example, unless you need to override.
/////////////////////
3. Why UserService (both iBatis & Hibernate) only uses @Transactional
-----------------
1>UserService methods don’t need special isolation — they’re mostly 
just CRUD inserts/selects.
2>The default Spring settings (REQUIRED + DB default isolation) are enough.
3>That’s why you only see @Transactional.
/////////////////
4. Key takeaway
---------------
@Transactional without attributes =
propagation = REQUIRED, isolation = DEFAULT (→ DB’s isolation, e.g. READ_COMMITTED).
1>Hibernate example just made these explicit.
2>iBatis example left them implicit.
3>Both behave the same in practice unless you deliberately change them.
*/
public class InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Transactional
    public Invoice createPdf(Long userId) {
        Invoice invoice = new Invoice();
        invoice.setPdfPath("/tmp/invoice_" + userId + ".pdf");
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setUserId(userId);
        invoiceMapper.insert(invoice);
        System.out.println("Invoice created at " + invoice.getPdfPath());
        return invoice;
    }

    // ================= NEW METHODS =================

    @Transactional(readOnly = true)
    public List<Invoice> getAllInvoices() {
        return invoiceMapper.findAll();
    }

    @Transactional(readOnly = true)
    public List<Invoice> getInvoicesByUserId(Long userId) {
        return invoiceMapper.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Long countInvoices() {
        return invoiceMapper.countInvoices();
    }
}
