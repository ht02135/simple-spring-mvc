/*
MyBatis Mapper Interfaces
*/
public interface UserMapper {

    @Insert("INSERT INTO users(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);

    // ================= NEW METHODS =================

    @Insert("INSERT INTO users(name, email, gender, first_name, last_name) " +
            "VALUES(#{name}, #{email}, #{gender}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertFull(User user);

    @Select("SELECT * FROM users")
    List<User> findAll();

    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(String email);

    @Select("SELECT * FROM users WHERE gender = #{gender} AND first_name = #{firstName} AND last_name = #{lastName}")
    List<User> findByGenderAndName(@Param("gender") String gender,
                                   @Param("firstName") String firstName,
                                   @Param("lastName") String lastName);

    @Select("SELECT gender, COUNT(*) AS cnt FROM users GROUP BY gender ORDER BY cnt DESC")
    List<Map<String, Object>> countUsersByGender();
}
