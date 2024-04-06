package gdsc.insangjinsolutionchallenge.global.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect //이 클래스가 Aspect클래스임을 명시
@Slf4j
@Component //스프링 빈으로 등록
public class LogAspect {

    @Pointcut("execution(* gdsc.insangjinsolutionchallenge.domain..*(..))")
    public void all(){
    }

    @Pointcut("execution(* gdsc.insangjinsolutionchallenge..*Controller.*(..))")
    public void controller(){
    }

    @Pointcut("execution(* gdsc.insangjinsolutionchallenge..*Service.*(..))")
    public void service(){
    }

    @Pointcut("execution(* gdsc.insangjinsolutionchallenge.global..*(..))")
    public void global(){

    }

    @Around("all()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log.info("log = {}" , joinPoint.getSignature());
            log.info("timeMs = {}", timeMs);
        }
    }

    //시큐리티 로직 확인용
    @After("global()")
    public void afterLogic(JoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info("method = {}", method.getName());

        Object[] args = joinPoint.getArgs();
        for (Object arg : args){
            if(arg != null){
                log.info("type = {}", arg.getClass().getSimpleName());
                log.info("value = {}", arg);
            }
        }
    }
}
