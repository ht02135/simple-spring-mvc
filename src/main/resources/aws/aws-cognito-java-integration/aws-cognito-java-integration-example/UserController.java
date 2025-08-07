@Autowired
    private UserServiceImpl userService;

    @PostMapping(value = "/register", consumes = {"application/json"})
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        System.out.println(userRegistrationRequest);
        // Perform user registration
        User registeredUser = userService.registerUser(userRegistrationRequest);
        if (registeredUser != null) {
            return ResponseEntity.ok(registeredUser);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        // Perform user login
        User loggedInUser = userService.loginUser(userLoginRequest.getUsername(), userLoginRequest.getPassword());

        if (loggedInUser != null) {
            // Successful login
            return ResponseEntity.ok(loggedInUser);
        } else {
            // Invalid login, return an appropriate response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
