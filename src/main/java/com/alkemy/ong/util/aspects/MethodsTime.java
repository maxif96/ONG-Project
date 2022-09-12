package com.alkemy.ong.util.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MethodsTime {

    @Around("execution(* com.alkemy.ong.*.*(..))")
    public Object calculateTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long t1 = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long t2 = System.currentTimeMillis();

        if( t2-t1>100) {

            System.out.println("Metodo lento:"+ joinPoint.getTarget().getClass()+"."+joinPoint.getSignature().getName() +":"+ (t2-t1));
        }

        return result;

    }

}