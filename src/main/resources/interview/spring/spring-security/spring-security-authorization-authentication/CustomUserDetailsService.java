/*
CustomUserDetailsService.java
If you want DB-backed authentication instead of XML-defined users:
*/
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Assume JPA repo

    /*
    By default, Spring Security calls loadUserByUsername(username) automatically 
    when a user tries to authenticate (via form login or HTTP basic). But this 
    only works if your CustomUserDetailsService is properly registered in the 
    AuthenticationManager
    //////////////////
    What triggers loadUserByUsername?
	1>When a client posts credentials to /login (form login) or sends an Authorization 
	header (HTTP Basic), Spring Security’s DaoAuthenticationProvider kicks in.
	2>That provider uses your registered UserDetailsService (in this case, 
	CustomUserDetailsService) to look up the user by username.
	3>If the user exists and the password matches, authentication succeeds.
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Map DB roles to Spring Security GrantedAuthorities
        List<GrantedAuthority> authorities =
                user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .toList();

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}