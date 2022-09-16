package com.alkemy.ong.util.aspects;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class MethodsTime {

    private final Logger log = LoggerFactory.getLogger(MethodsTime.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Pointcut("execution(* com.alkemy.ong.service.impl.*.*(..))")
    private static void forServiceMethods(){}

    @Pointcut("execution(* com.alkemy.ong.controller.*.*(..)) || execution(* com.alkemy.ong.auth.controller.*.*(..))")
    private static void forControllerMethods(){}

    @Around("forControllerMethods() || forServiceMethods()")
    public Object takenTime (ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        if (joinPoint.getTarget().toString().contains("controller")) log.info("------ Controller Response Time ------");
        if (joinPoint.getTarget().toString().contains("service")) log.info("------ Service Execution Time ------");
        log.info("Method: " +
                joinPoint.getTarget().getClass().getSimpleName() + "." +
                joinPoint.getSignature().getName());
        log.info("Time taken to execution: " + (endTime-startTime) + " milliseconds.");
        return object;
    }
    @AfterReturning(value = "forControllerMethods()", returning = "resp")
    public void response (JoinPoint joinPoint, ResponseEntity<?> resp) throws JsonProcessingException {
        log.info("------ Controller Response Data ------");
        log.info("Method: " +
                joinPoint.getTarget().getClass().getSimpleName() + "." +
                joinPoint.getSignature().getName());

        log.info("Response: " + objectMapper.writeValueAsString(resp));
    }



}