package br.com.facilpay.infra.aop;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {
	
	private static final Logger LOG = LoggerFactory.getLogger(AuditAspect.class);

}
