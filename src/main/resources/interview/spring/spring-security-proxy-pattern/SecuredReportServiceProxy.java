// 3. The Security Proxy (the aspect)
public class SecuredReportServiceProxy implements ReportService {
    private ReportService target;
    private String userRole;

    public SecuredReportServiceProxy(ReportService target, String userRole) {
        this.target = target;
        this.userRole = userRole;
    }

    @Override
    public void generateReport() {
        // This is the cross-cutting security logic
        if ("ADMIN".equals(userRole)) {
            target.generateReport();
        } else {
            System.out.println("Access Denied! You do not have permission to generate this report.");
        }
    }
}