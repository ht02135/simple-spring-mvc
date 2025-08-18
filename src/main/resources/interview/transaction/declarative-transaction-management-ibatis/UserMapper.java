/*
MyBatis Mapper Interfaces
*/
package x.y.mapper;

import x.y.model.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

    @Insert("INSERT INTO users(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);
}