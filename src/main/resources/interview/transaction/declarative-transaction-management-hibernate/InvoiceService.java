/*
1. Why Hibernate InvoiceService has:
@Transactional(
    propagation = Propagation.REQUIRED,
    isolation = Isolation.READ_COMMITTED
)
--------------------
1>When using Hibernate/JPA (EntityManager), Spring manages transactions 
through a JpaTransactionManager.
2>By default, it uses:
2a>Propagation = REQUIRED (join existing transaction or create a new 
one if none exists).
2b>Isolation = default of DB (often READ_COMMITTED in MySQL/Postgres).
2c>In your Hibernate service, someone explicitly wrote propagation 
and isolation — but it’s actually redundant, because those are already defaults.
3>i.e. Even if you wrote just @Transactional, Spring would treat it 
the same way.
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
