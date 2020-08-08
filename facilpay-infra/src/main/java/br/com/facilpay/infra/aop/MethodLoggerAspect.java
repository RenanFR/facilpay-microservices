package br.com.facilpay.infra.aop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodLoggerAspect {
	
	private static final Logger LOG = LoggerFactory.getLogger(MethodLoggerAspect.class);
	
	@Pointcut("execution(* br.com.facilpay.*.entrypoint.rest..*.*(..))")
	public void everyRestRequest() {}

	@Around("everyRestRequest()")
	public Object logAndCount(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Object[] methodArgs = joinPoint.getArgs();
		String args = Stream.of(methodArgs)
				.filter(arg -> arg != null && !arg.toString().isEmpty())
				.map(Object::toString)
				.collect(Collectors.joining(","));
		String returnType = methodSignature.getReturnType().getSimpleName();
		LOG.info(String.format(("MÉTODO %s INVOCADO EM %s " + (methodArgs.length != 0? "COM OS PARÂMETROS " +  args : "SEM PARÂMETROS") + ", O TIPO DE RETORNO É %s"), 
				methodSignature.getName(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), returnType));
		long start = System.nanoTime();
		Object returningObj = joinPoint.proceed();
		long end = System.nanoTime();
		Double timeToComplete = ((end - start) / 1_000_000_000.0);
		LOG.info(String.format("MÉTODO LEVOU %f SEGUNDOS PARA COMPLETAR A EXECUÇÃO, RETORNANDO %s", timeToComplete, returningObj));
		return returningObj;
	}

}
