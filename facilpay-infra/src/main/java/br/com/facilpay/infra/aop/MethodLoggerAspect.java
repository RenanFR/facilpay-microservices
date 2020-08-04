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
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class MethodLoggerAspect {
	
	@Pointcut("execution(* br.com.facilpay.ecommerce.entrypoint.rest..*.*(..))")
	public void everyMethod() {}

	@Around("everyMethod()")
	public Object logAndCount(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Object[] methodArgs = joinPoint.getArgs();
		String args = Stream.of(methodArgs)
				.filter(arg -> arg != null && !arg.toString().isEmpty())
				.map(Object::toString)
				.collect(Collectors.joining(","));
		String returnType = methodSignature.getReturnType().getSimpleName();
		log.info(String.format(("MÉTODO %s INVOCADO EM %s " + (methodArgs.length != 0? "COM OS PARÂMETROS " +  args : "SEM PARÂMETROS") + ", O TIPO DE RETORNO É %s"), 
				methodSignature.getName(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), returnType));
		long start = System.nanoTime();
		Object returningObj = joinPoint.proceed();
		long end = System.nanoTime();
		Double timeToComplete = ((end - start) / 1_000_000_000.0);
		log.info(String.format("MÉTODO LEVOU %f SEGUNDOS PARA COMPLETAR A EXECUÇÃO, RETORNANDO %s", timeToComplete, returningObj));
		return returningObj;
	}

}
