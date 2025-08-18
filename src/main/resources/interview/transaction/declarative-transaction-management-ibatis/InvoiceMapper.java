/*
MyBatis Mapper Interfaces
*/
package x.y.mapper;

import x.y.model.Invoice;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;

public interface InvoiceMapper {

    @Insert("INSERT INTO invoices(pdf_path, created_at) VALUES(#{pdfPath}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Invoice invoice);

    @Select("SELECT * FROM invoices WHERE id = #{id}")
    Invoice findById(Long id);
}