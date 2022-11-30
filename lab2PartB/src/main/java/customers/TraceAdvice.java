package customers;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
@Configuration
public class TraceAdvice {
    @Autowired
    private ILogger logger;

    @After("execution(* customers.EmailSender.*(..)) && args(email,message)")
    public void traceAfterMethod(JoinPoint joinpoint, String email,String message) {
        Object emailOutGoing = joinpoint.getTarget();
        EmailSender emailSender = (EmailSender) emailOutGoing;
        logger.log("method = " + joinpoint.getSignature().getName() +
                " address = "+ email + " message = "+ message +
                " outgoing mail server = " + emailSender.getOutgoingMailServer());
    }

    @Around("execution(* customers.CustomerDAO.*(..))")
    public Object invoke(ProceedingJoinPoint call) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        //long startTime = System.currentTimeMillis();
        stopWatch.start(call.getSignature().getName());
        Object retVal = call.proceed();
        stopWatch.stop();
        //long endTime = System.currentTimeMillis();
        long totalTime = stopWatch.getLastTaskTimeMillis();
        //long totalTimeRequired = endTime - startTime;

        logger.log("ClassName: " + call.getSignature().getDeclaringTypeName()+" " +
                " MethodName: " +call.getSignature().getName() +
                " time taken for Execution is : ===> " + totalTime + " milliseconds.");
        return retVal;
    }
}
