// Class that needs the dependency
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // Spring bean
public class NotificationService {
    private final MessageService messageService;

    // Constructor Injection
    @Autowired // Marks the constructor for autowiring by Spring
    public NotificationService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void sendNotification(String message) {
        messageService.sendMessage(message);
    }
}