package com.alkemy.ong.util.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class MethodsTime {

    @AfterReturning("execution(* com.alkemy.ong.service.impl.*.*(..))")
    public void afterSuccessfullyReturning (JoinPoint joinPoint){
        System.out.println("Method: " +
                joinPoint.getTarget().getClass().getSimpleName() + "." +
                joinPoint.getSignature().getName());
        System.out.println("Response Time: " + LocalDateTime.now());
    }



}