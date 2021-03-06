package Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect {
    @Pointcut("execution(* Service.*.*(..))")
    public void service() {}

    @Before("service()")
    public void beforeService() {System.out.println("Before Service");}

    @After("service()")
    public void afterService() {System.out.println("After Service");}

    @Around("service()")
    public Object aroundService(ProceedingJoinPoint jp) {
        Object object = null;
        try {
            System.out.println("Before Around Service");
            object = jp.proceed();
            System.out.println("After Around Service");
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return object;
    }
}
