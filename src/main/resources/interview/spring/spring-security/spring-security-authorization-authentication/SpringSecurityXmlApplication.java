/*
I’ll give you a minimal but complete example you can run in a 
Spring Boot project, with authentication + authorization using XML configuration and annotations (@PreAuthorize).
////////////////////
With this setup:
Start the app
Login with admin/admin123 → can access /api/v1/resources/admin & /public
Login with user/user123 → can access only /public
@PreAuthorize annotations also apply, in addition to <intercept-url> from XML.
*/
@SpringBootApplication
@ImportResource("classpath:applicationContext-security.xml")
public class SpringSecurityXmlApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityXmlApplication.class, args);
    }
}