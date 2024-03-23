package gdsc.insangjinsolutionchallenge.global.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

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

    //before은 로그가 안찍히는데 왜지?
//    @Before("controller() || service()")
//    public void beforeLogic(JoinPoint joinPoint) throws Throwable{
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        Method method = methodSignature.getMethod();
//        log.info("method = {}", method.getName());
//
//        Object[] args = joinPoint.getArgs();
//        for (Object arg : args ){
//            if(arg != null){
//                log.info("type = {}", arg.getClass().getSimpleName());
//                log.info("value = {}", arg);
//            }
//
//        }
//
//    }
//
//    @After("controller() || service()")
//    public void afterLogic(JoinPoint joinPoint) throws Throwable {
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        Method method = methodSignature.getMethod();
//        log.info("method = {}", method.getName());
//
//        Object[] args = joinPoint.getArgs();
//        for (Object arg : args){
//            if(arg != null){
//                log.info("type = {}", arg.getClass().getSimpleName());
//                log.info("value = {}", arg);
//            }
//        }
//
//    }

}
