// Concrete dependency implementation
import org.springframework.stereotype.Component;

@Component("emailService") // Spring bean named "emailService"
public class EmailService implements MessageService {
    @Override
    public void sendMessage(String message) {
        System.out.println("Email message sent: " + message);
    }
}