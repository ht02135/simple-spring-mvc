@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CognitoService cognitoService;

    @Override
    public User registerUser(UserRegistrationRequest userRegistrationRequest) {
        String username = userRegistrationRequest.getUsername();
        String password = userRegistrationRequest.getPassword();
        String email = userRegistrationRequest.getEmail();
        // Register the user with Amazon Cognito
        return cognitoService.registerUser(username, email, password);
    }

    @Override
    public User loginUser(String username, String password) {
        return cognitoService.loginUser(username, password);
    }
}