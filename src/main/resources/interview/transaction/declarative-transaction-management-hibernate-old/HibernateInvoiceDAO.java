// --- Hibernate Implementation ---
@Repository("invoiceDAO")
public class HibernateInvoiceDAO extends HibernateDaoSupport implements InvoiceDAO {

    private static Logger log = Logger.getLogger(HibernateInvoiceDAO.class);

    @Autowired
    public HibernateInvoiceDAO(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super();
        setSessionFactory(sessionFactory);
    }

    @Override
    public void save(Invoice invoice) {
        log.info("Saving invoice: " + invoice);
        getHibernateTemplate().save(invoice);
    }

    @Override
    public Invoice findById(Long id) {
        return (Invoice) getHibernateTemplate().execute((HibernateCallback) session -> {
            Query query = session.createQuery("from Invoice i where i.id = :id");
            query.setParameter("id", id);
            return query.uniqueResult();
        });
    }

    @Override
    public List<Invoice> findAll() {
        return (List<Invoice>) getHibernateTemplate().execute((HibernateCallback) session -> {
            Query query = session.createQuery("from Invoice i");
            return query.list();
        });
    }

    @Override
    public List<Invoice> findByUserId(Long userId) {
        return (List<Invoice>) getHibernateTemplate().execute((HibernateCallback) session -> {
            Query query = session.createQuery("from Invoice i where i.user.id = :userId");
            query.setParameter("userId", userId);
            return query.list();
        });
    }

    @Override
    public Long countInvoices() {
        return (Long) getHibernateTemplate().execute((HibernateCallback) session -> {
            Query query = session.createQuery("select count(i) from Invoice i");
            return query.uniqueResult();
        });
    }
}
