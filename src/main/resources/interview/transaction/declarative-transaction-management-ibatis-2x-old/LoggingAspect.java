package com.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class LoggingAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    
    /**
     * Before advice - logs method entry
     */
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        
        logger.info("==> Entering method: {}.{}() with arguments: {}", 
                   className, methodName, Arrays.toString(args));
    }
    
    /**
     * After returning advice - logs successful method completion
     */
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        if (result != null) {
            logger.info("<== Exiting method: {}.{}() with result: {}", 
                       className, methodName, result.toString());
        } else {
            logger.info("<== Exiting method: {}.{}() with null result", 
                       className, methodName);
        }
    }
    
    /**
     * After throwing advice - logs exceptions
     */
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        logger.error("<!> Exception in method: {}.{}() - Exception: {} - Message: {}", 
                    className, methodName, error.getClass().getSimpleName(), error.getMessage());
    }
    
    /**
     * Around advice - comprehensive logging for DAO layer
     */
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
        Object[] args = proceedingJoinPoint.getArgs();
        
        long startTime = System.currentTimeMillis();
        
        logger.debug("---> Starting DAO method: {}.{}() with args: {}", 
                    className, methodName, Arrays.toString(args));
        
        try {
            Object result = proceedingJoinPoint.proceed();
            
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            logger.debug("<--- Completed DAO method: {}.{}() in {}ms", 
                        className, methodName, executionTime);
            
            return result;
            
        } catch (Throwable ex) {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            logger.error("<!-> Failed DAO method: {}.{}() after {}ms - Error: {}", 
                        className, methodName, executionTime, ex.getMessage());
            throw ex;
        }
    }
}