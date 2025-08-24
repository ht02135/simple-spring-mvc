public interface InvoiceService {
    void save(Invoice invoice);
    Invoice findById(Long id);
    List<Invoice> findAll();
    List<Invoice> findByUserId(Long userId);
    Long countInvoices();
}
