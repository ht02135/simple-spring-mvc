import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table("invoices")
public class Invoice {
    @Id
    private Long id;
    private Long userId;
    private String pdfPath;
    private LocalDateTime createdAt;

    // getters and setters
}
