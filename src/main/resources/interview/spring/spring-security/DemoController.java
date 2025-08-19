import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/")
    public String home() {
        return "Welcome! Public page.";
    }

    @GetMapping("/user/hello")
    public String userPage() {
        return "Hello User!";
    }

    @GetMapping("/admin/dashboard")
    public String adminPage() {
        return "Admin Dashboard!";
    }
}