/*
UserController.java
*/
// Dependent bean
public class UserController {
    private UserService userService;

    // For constructor autowiring
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // For setter autowiring
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void handleRequest() {
        userService.process();
    }
}