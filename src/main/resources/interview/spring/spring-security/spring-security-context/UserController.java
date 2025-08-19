@RestController
@RequestMapping("/api")
public class UserController {

    // Method 1: Using SecurityContextHolder
    @GetMapping("/current-user")
    public ResponseEntity<String> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            
            return ResponseEntity.ok("Current user: " + username + 
                                   ", Roles: " + authorities.toString());
        }
        
        return ResponseEntity.ok("No authenticated user");
    }

    // Method 2: Using Principal parameter injection
    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getProfile(Principal principal) {
        String username = principal.getName();
        // Fetch user profile logic
        return ResponseEntity.ok(userService.getUserProfile(username));
    }

    // Method 3: Using Authentication parameter injection
    @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> getUserInfo(Authentication authentication) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", authentication.getName());
        userInfo.put("authorities", authentication.getAuthorities());
        userInfo.put("principal", authentication.getPrincipal());
        
        return ResponseEntity.ok(userInfo);
    }

    // Method 4: Using @AuthenticationPrincipal
    @GetMapping("/user-details")
    public ResponseEntity<String> getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok("User: " + userDetails.getUsername() + 
                               ", Authorities: " + userDetails.getAuthorities());
    }
}