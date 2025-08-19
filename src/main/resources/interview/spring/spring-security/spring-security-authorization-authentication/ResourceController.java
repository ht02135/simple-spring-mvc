/*
ResourceController.java
Your authorization rules via annotations.
///////////////////////
///With this setup:
Start the app
Login with admin/admin123 → can access /api/v1/resources/admin & /public
Login with user/user123 → can access only /public
@PreAuthorize annotations also apply, in addition to <intercept-url> from XML.
*/
@RestController
@RequestMapping("/api/v1/resources")
public class ResourceController {

    // Only ADMIN can access
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String getAdminResource() {
        return "This is a secret resource for admins only!";
    }

    // Both USER and ADMIN can access
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/public")
    public String getPublicResource() {
        return "This is a public resource for all logged-in users.";
    }
}