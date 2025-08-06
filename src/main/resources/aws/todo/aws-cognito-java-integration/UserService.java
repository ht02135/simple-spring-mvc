public interface UserService {
    User registerUser(UserRegistrationRequest userRegistrationRequest);
    User loginUser(String username, String password);
}