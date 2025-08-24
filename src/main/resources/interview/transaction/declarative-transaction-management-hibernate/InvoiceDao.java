@Repository
public class InvoiceDao {
    @PersistenceContext
    private EntityManager em;

    public Invoice save(Invoice invoice) {
        em.persist(invoice);
        return invoice;
    }

    public Invoice findById(Long id) {
        return em.find(Invoice.class, id);
    }

    // ================= NEW METHODS =================

    // Get all invoices
    public List<Invoice> findAll() {
        return em.createQuery("SELECT i FROM Invoice i", Invoice.class)
                 .getResultList();
    }

    // Find invoices by userId (assuming invoice table has user_id FK)
    public List<Invoice> findByUserId(Long userId) {
        return em.createQuery("SELECT i FROM Invoice i WHERE i.user.id = :userId", Invoice.class)
                 .setParameter("userId", userId)
                 .getResultList();
    }

    // Count invoices
    public Long countInvoices() {
        return em.createQuery("SELECT COUNT(i) FROM Invoice i", Long.class)
                 .getSingleResult();
    }
}
