@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRequest) {
        try {
            userService.registerUser(userRequest); // Register user with Cognito
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error registering user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest userRequest) {
        if (userService.loginUser(userRequest)) { // Login user with Cognito
            return ResponseEntity.ok("User logged in successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid username or password.");
        }
    }
    
    @RestController
    @RequestMapping("/api")
    public class MyController {

        @GetMapping("/secured")
        @PreAuthorize("hasRole('ROLE_USER')")
        public String securedEndpoint() {
            return "This is a secured endpoint!";
        }
    }
}