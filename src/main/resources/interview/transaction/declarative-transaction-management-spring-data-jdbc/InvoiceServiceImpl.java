import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Transactional
    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Transactional(readOnly = true)
    public Invoice findById(Long id) {
        return invoiceRepository.findByIdExplicit(id);
    }

    @Transactional(readOnly = true)
    public List<Invoice> findAll() {
        return invoiceRepository.findAllExplicit();
    }

    @Transactional(readOnly = true)
    public List<Invoice> findByUserId(Long userId) {
        return invoiceRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Long countInvoices() {
        return invoiceRepository.countInvoices();
    }
}
