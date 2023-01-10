package com.project.library.aop;

import com.project.library.exception.CustomValidationException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class ParamsAop {

    @Pointcut("@annotation(com.project.library.aop.annotation.ParamsAspect)")
    private void pointCut(){}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();

        for (Object arg : args) {
            log.info("{}", arg);
        }

        return proceedingJoinPoint.proceed();
    }
}
