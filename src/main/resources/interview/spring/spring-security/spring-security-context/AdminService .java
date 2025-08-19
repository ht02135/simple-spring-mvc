@Service
public class AdminService {

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long userId) {
        // Admin only operation
    }

    @PreAuthorize("hasRole('USER') and #userId == authentication.principal.id")
    public void updateProfile(Long userId, UserProfile profile) {
        // User can only update their own profile
    }

    @PostFilter("hasRole('ADMIN') or filterObject.owner == authentication.name")
    public List<Document> getAllDocuments() {
        // Filter results based on ownership
        return documentRepository.findAll();
    }
}