@Entity
@Table(name = "comp_users")
public class CompUser implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "authority")
	private String authority;

	@Column(name = "enabled")
	private int enabled;
        
        //Setters and Getters
} 