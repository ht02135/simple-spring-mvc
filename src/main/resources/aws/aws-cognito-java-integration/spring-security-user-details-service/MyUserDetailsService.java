
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository; 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<CompUser> compUserOpt = userRepository.findById(username);
		if (compUserOpt.isEmpty()) {
			throw new UsernameNotFoundException("Username not found");
		}
		CompUser compUser = compUserOpt.get();
		GrantedAuthority authority = new SimpleGrantedAuthority(compUser.getAuthority());
		UserDetails userDetails = (UserDetails)new User(compUser.getUsername(), 
				compUser.getPassword(), Arrays.asList(authority));
		return userDetails;
	}
} 

