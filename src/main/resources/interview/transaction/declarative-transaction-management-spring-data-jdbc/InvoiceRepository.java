import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

    @Query("SELECT * FROM invoices WHERE id = :id")
    Invoice findByIdExplicit(@Param("id") Long id);

    @Query("SELECT * FROM invoices")
    List<Invoice> findAllExplicit();

    @Query("SELECT * FROM invoices WHERE user_id = :userId")
    List<Invoice> findByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(*) FROM invoices")
    Long countInvoices();
}
