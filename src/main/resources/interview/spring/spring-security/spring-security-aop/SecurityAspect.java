/*
You want to see how to rewrite your proxy-based security logic 
into a proper Spring AOP (Aspect-Oriented Programming) example 
using Java + XML configuration.
////////////////////////
Security Aspect (cross-cutting concern)
////////////////////////
Core AOP Concepts in Your Example
1. Interception
Definition: Mechanism of intercepting method calls or executions 
so that cross-cutting logic can run before/after/around the core logic.
In our code:
The method checkAccess() in SecurityAspect intercepts the call to 
generateReport() before it executes.
///////////////////////////
2. Aspect
Definition: A modularization of a cross-cutting concern (like security, 
logging, transactions).
In our code:
SecurityAspect is the Aspect — it holds the cross-cutting security logic.
///////////////////////////
3. Weaving
Definition: The process of linking aspects with target objects to create 
an advised (proxy) object.
Types: Compile-time, Load-time, or Runtime weaving.
In our code:
Spring AOP does runtime weaving using proxies. When you get reportService 
from the Spring container, it’s actually a proxy object woven with SecurityAspect.
///////////////////////////
///////////////////////////
1. Advice
Definition: The actual action to take at a join point (before, after, around).
In our XML example:
<aop:before method="checkAccess"
            pointcut="execution(* com.example.service.ReportService.generateReport(..))"/>
Here, checkAccess() is the advice method.
///////////////////////
2. Pointcut
Definition: An expression that selects join points (specific places in code 
where advice applies).
In our XML example:
pointcut="execution(* com.example.service.ReportService.generateReport(..))"
This pointcut means: apply advice to any generateReport() method in ReportService.
////////////////////////
3. Advisor
Definition: Combines a Pointcut + Advice into a single unit.
In Spring XML:
An <aop:aspect> is essentially a collection of advisors.
<aop:aspect id="security" ref="securityAspect">
    <aop:before method="checkAccess"
                pointcut="execution(* com.example.service.ReportService.generateReport(..))"/>
</aop:aspect>
This <aop:before> declaration = an advisor (it links the checkAccess advice with the pointcut).
*/
package com.example.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class SecurityAspect {

    private String userRole;

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    // Advice that runs before report generation
    @Before("execution(* com.example.service.ReportService.generateReport(..))")
    public void checkAccess() {
        if (!"ADMIN".equals(userRole)) {
            throw new SecurityException("Access Denied! You do not have permission to generate this report.");
        }
        System.out.println("Access Granted! Proceeding with report generation...");
    }
}