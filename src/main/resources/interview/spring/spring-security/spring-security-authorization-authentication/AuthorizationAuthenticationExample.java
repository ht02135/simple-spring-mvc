/*
Authentication vs. Authorization
/////////////////////////
Authentication is the process of verifying who a user is. It's the act of 
validating a user's identity, usually by requiring them to provide 
credentials like a username and password. Think of it as a bouncer 
at a club checking your ID to confirm you are who you say you are.
/////////////////////////
Authorization is the process of determining what a user is allowed to do. 
Once a user's identity is authenticated, authorization checks their 
permissions to access specific resources or perform certain actions. 
This is like the bouncer letting you into the club, but only giving 
you access to the general admission area, not the VIP section.
//////////////////////////
Here's a simple analogy to summarize the difference:
Authentication asks: "Are you who you say you are?"
Authorization asks: "What are you allowed to do here?"
//////////////////////////  
A common way to see these two concepts in action in a Java application 
is through a web application using a framework like Spring Security.
Authentication
In Spring Security, authentication is often handled by a UserDetailsService 
and an AuthenticationManager. The UserDetailsService is responsible for 
loading user-specific data, and the AuthenticationManager is responsible 
for validating the credentials.
/////////////////////////
When a user tries to log in, Spring Security's Authentication process 
uses this UserDetailsService to find the user and then compares the 
provided password with the one in the database. If they match, the user 
is authenticated.
///////////////////////////
Authorization
After a user is authenticated, authorization determines what they can 
access. This is typically managed using roles or permissions. In Spring 
Security, this is often done using @PreAuthorize annotations or configuration 
in a security filter chain.
////////////////////////////
In this example, a user who has successfully authenticated can only access 
the /admin endpoint if their assigned role includes ADMIN. If they only 
have the USER role, they will be denied authorization to access that 
specific resource, even though they are authenticated.
*/
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}

//Controller with authorization rules
@RestController
@RequestMapping("/api/v1/resources")
public class ResourceController {

 // Only users with the 'ADMIN' role can access this endpoint
 @PreAuthorize("hasRole('ADMIN')")
 @GetMapping("/admin")
 public String getAdminResource() {
     return "This is a secret resource for admins only!";
 }

 // Both 'ADMIN' and 'USER' roles can access this endpoint
 @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
 @GetMapping("/public")
 public String getPublicResource() {
     return "This is a public resource for all logged-in users.";
 }
}