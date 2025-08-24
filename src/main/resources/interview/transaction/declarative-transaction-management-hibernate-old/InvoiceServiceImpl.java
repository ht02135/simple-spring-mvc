@Service("invoiceService")
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    @Qualifier("invoiceDAO")
    private InvoiceDAO invoiceDAO;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Invoice invoice) {
        invoiceDAO.save(invoice);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Invoice findById(Long id) {
        return invoiceDAO.findById(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Invoice> findAll() {
        return invoiceDAO.findAll();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Invoice> findByUserId(Long userId) {
        return invoiceDAO.findByUserId(userId);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Long countInvoices() {
        return invoiceDAO.countInvoices();
    }

    // setter injection (optional)
    public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }
}
