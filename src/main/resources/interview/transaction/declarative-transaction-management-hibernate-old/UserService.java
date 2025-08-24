public interface UserService {
    void save(User user);
    User findById(Long id);
    List<User> findAll();
    User findByEmail(String email);
    List<User> findByGenderAndName(String gender, String firstName, String lastName);
}
