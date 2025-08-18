/*
MyBatis Mapper Interfaces
*/
package x.y.mapper;

import x.y.model.Course;
import org.apache.ibatis.annotations.*;

public interface CourseMapper {

    @Insert("INSERT INTO courses(title) VALUES(#{title})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Course course);

    @Select("SELECT * FROM courses WHERE id = #{id}")
    Course findById(Long id);
}