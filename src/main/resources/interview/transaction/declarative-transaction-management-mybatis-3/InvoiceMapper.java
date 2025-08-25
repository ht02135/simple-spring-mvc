/*
MyBatis Mapper Interfaces
*/
public interface InvoiceMapper {

    @Insert("INSERT INTO invoices(pdf_path, created_at) VALUES(#{pdfPath}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Invoice invoice);

    @Select("SELECT * FROM invoices WHERE id = #{id}")
    Invoice findById(Long id);

    // ================= NEW METHODS =================

    @Select("SELECT * FROM invoices")
    List<Invoice> findAll();

    @Select("SELECT * FROM invoices WHERE user_id = #{userId}")
    List<Invoice> findByUserId(Long userId);

    @Select("SELECT COUNT(*) FROM invoices")
    Long countInvoices();
}
