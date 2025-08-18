package x.y.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import x.y.model.Invoice;

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
}