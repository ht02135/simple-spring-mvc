package com.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SecurityAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityAspect.class);
    
    // Simulated current user context (in real app, get from SecurityContext)
    private static final ThreadLocal<String> currentUser = new ThreadLocal<>();
    private static final ThreadLocal<String[]> currentUserRoles = new ThreadLocal<>();
    
    static {
        // Initialize with default user for demo purposes
        currentUser.set("admin");
        currentUserRoles.set(new String[]{"ADMIN", "USER"});
    }
    
    /**
     * Security check for create operations
     */
    public void checkCreatePermission(JoinPoint joinPoint) throws SecurityException {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        
        logger.info("SECURITY: Checking CREATE permission for {}.{}()", className, methodName);
        
        if (!hasPermission("CREATE")) {
            String message = String.format("Access denied: User '%s' does not have CREATE permission for %s.%s()", 
                                         getCurrentUser(), className, methodName);
            logger.error("SECURITY: {}", message);
            throw new SecurityException(message);
        }
        
        // Log successful authorization
        logger.info("SECURITY: CREATE permission granted for user '{}' on {}.{}() with args: {}", 
                   getCurrentUser(), className, methodName, Arrays.toString(args));
        
        // Audit log for create operations
        auditLog("CREATE", className, methodName, args);
    }
    
    /**
     * Security check for update operations
     */
    public void checkUpdatePermission(JoinPoint joinPoint) throws SecurityException {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        
        logger.info("SECURITY: Checking UPDATE permission for {}.{}()", className, methodName);
        
        if (!hasPermission("UPDATE")) {
            String message = String.format("Access denied: User '%s' does not have UPDATE permission for %s.%s()", 
                                         getCurrentUser(), className, methodName);
            logger.error("SECURITY: {}", message);
            throw new SecurityException(message);
        }
        
        // Additional check for sensitive operations
        if (methodName.contains("Salary") && !hasRole("ADMIN")) {
            String message = String.format("Access denied: User '%s' does not have ADMIN role for salary operations", 
                                         getCurrentUser());
            logger.error("SECURITY: {}", message);
            throw new SecurityException(message);
        }
        
        // Log successful authorization
        logger.info("SECURITY: UPDATE permission granted for user '{}' on {}.{}() with args: {}", 
                   getCurrentUser(), className, methodName, Arrays.toString(args));
        
        // Audit log for update operations
        auditLog("UPDATE", className, methodName, args);
    }
    
    /**
     * Security check for delete operations
     */
    public void checkDeletePermission(JoinPoint joinPoint) throws SecurityException {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        
        logger.info("SECURITY: Checking DELETE permission for {}.{}()", className, methodName);
        
        if (!hasPermission("DELETE")) {
            String message = String.format("Access denied: User '%s' does not have DELETE permission for %s.%s()", 
                                         getCurrentUser(), className, methodName);
            logger.error("SECURITY: {}", message);
            throw new SecurityException(message);
        }
        
        // Delete operations require ADMIN role
        if (!hasRole("ADMIN")) {
            String message = String.format("Access denied: User '%s' does not have ADMIN role for delete operations", 
                                         getCurrentUser());
            logger.error("SECURITY: {}", message);
            throw new SecurityException(message);
        }
        
        // Log successful authorization
        logger.info("SECURITY: DELETE permission granted for user '{}' on {}.{}() with args: {}", 
                   getCurrentUser(), className, methodName, Arrays.toString(args));
        
        // Audit log for delete operations
        auditLog("DELETE", className, methodName, args);
    }
    
    /**
     * Check if current user has specific permission
     */
    private boolean hasPermission(String permission) {
        String user = getCurrentUser();
        String[] roles = getCurrentUserRoles();
        
        // Simple permission logic (in real app, integrate with proper security framework)
        if (hasRole("ADMIN")) {
            return true; // Admins have all permissions
        }
        
        switch (permission) {
            case "CREATE":
            case "UPDATE":
                return hasRole("USER") || hasRole("MANAGER");
            case "DELETE":
                return hasRole("ADMIN") || hasRole("MANAGER");
            default:
                return false;
        }
    }
    
    /**
     * Check if current user has specific role
     */
    private boolean hasRole(String role) {
        String[] roles = getCurrentUserRoles();
        return roles != null && Arrays.asList(roles).contains(role);
    }
    
    /**
     * Get current user (in real app, get from SecurityContextHolder)
     */
    private String getCurrentUser() {
        String user = currentUser.get();
        return user != null ? user : "anonymous";
    }
    
    /**
     * Get current user roles (in real app, get from SecurityContextHolder)
     */
    private String[] getCurrentUserRoles() {
        String[] roles = currentUserRoles.get();
        return roles != null ? roles : new String[0];
    }
    
    /**
     * Audit logging for security events
     */
    private void auditLog(String operation, String className, String methodName, Object[] args) {
        logger.info("AUDIT: User '{}' performed {} operation on {}.{}() with parameters: {} at {}", 
                   getCurrentUser(), 
                   operation, 
                   className, 
                   methodName, 
                   Arrays.toString(args),
                   new java.util.Date());
    }
    
    /**
     * Set current user (for testing/demo purposes)
     */
    public static void setCurrentUser(String username, String... roles) {
        currentUser.set(username);
        currentUserRoles.set(roles);
    }
    
    /**
     * Clear current user context
     */
    public static void clearCurrentUser() {
        currentUser.remove();
        currentUserRoles.remove();
    }
}