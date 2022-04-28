package com.example.jobs.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("within(@org.springframework.stereotype.Repository *)")
    public void springBeanPointcut() {
    }

    @Around("springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) {
        LocalTime startTime = null;
        if (log.isDebugEnabled()) {
            startTime = LocalTime.now();
            log.info("Started time: " + startTime);
            log.debug("Enter: {}.{}({})", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            LocalTime endTime = null;

            if (log.isDebugEnabled()) {
                endTime = LocalTime.now();
                log.info("Stopped time: " + endTime);

                log.debug("Enter: {}.{}({})", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), result);
                log.debug("{} took {}ms time to execute{}", joinPoint.getSignature().getName(), endTime, joinPoint.getKind());

            }
            if (startTime!=null && endTime!=null){
                Duration.between(startTime,endTime).getSeconds();
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
