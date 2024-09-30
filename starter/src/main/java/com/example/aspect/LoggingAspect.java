package com.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(com.example.annotations.LogExecution)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        logger.info("Method {} called with arguments: {}", methodName, Arrays.toString(methodArgs));

        Object result;

        try {
            result = joinPoint.proceed();
            logger.info("Method {} returned: {}", methodName, result);
            } catch (Throwable ex) {
            logger.error("Method {} threw an exception: {}", methodName, ex.getMessage());
            throw ex;
        }
        return result;
    }

}
