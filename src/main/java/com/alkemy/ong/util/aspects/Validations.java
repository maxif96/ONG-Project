package com.alkemy.ong.util.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;

@Aspect
@Component
public class Validations {

    @Pointcut("execution(* pagination*(..))")
    public void forPaginationMethods(){}

    @Before("forPaginationMethods()")
    public void pageValidation (JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();

        Integer pageNumber = (Integer) args[0];
        if (pageNumber < 1) throw new EntityNotFoundException("Page must be greater than 0");
    }

}
