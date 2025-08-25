package com.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PerformanceAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);
    
    // Performance metrics storage
    private final ConcurrentHashMap<String, MethodStats> methodStats = new ConcurrentHashMap<>();
    
    // Performance thresholds (in milliseconds)
    private static final long SLOW_METHOD_THRESHOLD = 1000; // 1 second
    private static final long WARNING_THRESHOLD = 500; // 500ms
    
    /**
     * Around advice for performance monitoring
     */
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodSignature = joinPoint.getSignature().toShortString();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        long startTime = System.nanoTime();
        long startTimeMillis = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            
            long endTime = System.nanoTime();
            long executionTimeNanos = endTime - startTime;
            long executionTimeMillis = executionTimeNanos / 1_000_000; // Convert to milliseconds
            
            // Update method statistics
            updateMethodStats(methodSignature, executionTimeMillis);
            
            // Log performance based on thresholds
            logPerformance(className, methodName, executionTimeMillis);
            
            // Log detailed stats periodically (every 100th call)
            logDetailedStatsIfNeeded(methodSignature);
            
            return result;
            
        } catch (Throwable ex) {
            long endTime = System.nanoTime();
            long executionTimeNanos = endTime - startTime;
            long executionTimeMillis = executionTimeNanos / 1_000_000;
            
            // Update stats even for failed methods
            updateMethodStats(methodSignature + "_FAILED", executionTimeMillis);
            
            logger.warn("PERF: Method {}.{}() failed after {}ms", 
                       className, methodName, executionTimeMillis);
            
            throw ex;
        }
    }
    
    private void updateMethodStats(String methodSignature, long executionTime) {
        methodStats.computeIfAbsent(methodSignature, k -> new MethodStats())
                  .recordExecution(executionTime);
    }
    
    private void logPerformance(String className, String methodName, long executionTime) {
        if (executionTime > SLOW_METHOD_THRESHOLD) {
            logger.warn("PERF: SLOW METHOD - {}.{}() took {}ms", 
                       className, methodName, executionTime);
        } else if (executionTime > WARNING_THRESHOLD) {
            logger.info("PERF: {}.{}() took {}ms", 
                       className, methodName, executionTime);
        } else {
            logger.debug("PERF: {}.{}() took {}ms", 
                        className, methodName, executionTime);
        }
    }
    
    private void logDetailedStatsIfNeeded(String methodSignature) {
        MethodStats stats = methodStats.get(methodSignature);
        if (stats != null && stats.getCallCount() % 100 == 0) {
            logger.info("PERF STATS: {} - Calls: {}, Avg: {}ms, Min: {}ms, Max: {}ms, Total: {}ms",
                       methodSignature,
                       stats.getCallCount(),
                       stats.getAverageTime(),
                       stats.getMinTime(),
                       stats.getMaxTime(),
                       stats.getTotalTime());
        }
    }
    
    /**
     * Get performance statistics for a method
     */
    public MethodStats getMethodStats(String methodSignature) {
        return methodStats.get(methodSignature);
    }
    
    /**
     * Get all performance statistics
     */
    public ConcurrentHashMap<String, MethodStats> getAllMethodStats() {
        return new ConcurrentHashMap<>(methodStats);
    }
    
    /**
     * Clear all performance statistics
     */
    public void clearStats() {
        methodStats.clear();
        logger.info("PERF: Performance statistics cleared");
    }
    
    /**
     * Inner class to store method execution statistics
     */
    public static class MethodStats {
        private final AtomicInteger callCount = new AtomicInteger(0);
        private final AtomicLong totalTime = new AtomicLong(0);
        private volatile long minTime = Long.MAX_VALUE;
        private volatile long maxTime = Long.MIN_VALUE;
        
        public synchronized void recordExecution(long executionTime) {
            callCount.incrementAndGet();
            totalTime.addAndGet(executionTime);
            
            if (executionTime < minTime) {
                minTime = executionTime;
            }
            if (executionTime > maxTime) {
                maxTime = executionTime;
            }
        }
        
        public int getCallCount() {
            return callCount.get();
        }
        
        public long getTotalTime() {
            return totalTime.get();
        }
        
        public long getAverageTime() {
            int calls = callCount.get();
            return calls > 0 ? totalTime.get() / calls : 0;
        }
        
        public long getMinTime() {
            return minTime == Long.MAX_VALUE ? 0 : minTime;
        }
        
        public long getMaxTime() {
            return maxTime == Long.MIN_VALUE ? 0 : maxTime;
        }
        
        @Override
        public String toString() {
            return String.format("MethodStats{calls=%d, avg=%dms, min=%dms, max=%dms, total=%dms}",
                               getCallCount(), getAverageTime(), getMinTime(), getMaxTime(), getTotalTime());
        }
    }
}