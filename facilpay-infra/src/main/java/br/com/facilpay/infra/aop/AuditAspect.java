package br.com.facilpay.infra.aop;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.facilpay.infra.output.http.port.AuditoriaClient;
import br.com.facilpay.shared.domain.HistoricoTabelas;

@Aspect
@Component
public class AuditAspect {
	
	private static final Logger LOG = LoggerFactory.getLogger(AuditAspect.class);
	
	@Autowired
	private AuditoriaClient auditoriaClient;
	
	@Pointcut("execution(* br.com.facilpay.*.output.db.adapter..*.*(..)) && @annotation(br.com.facilpay.infra.aop.DeveAuditar)")
	public void everyDBCall() {}
	
	@Around("everyDBCall()")
	public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
		Object returningObj = joinPoint.proceed();
		@SuppressWarnings("unchecked")
		List<HistoricoTabelas> historicosTabela = (List<HistoricoTabelas>) joinPoint
				.getTarget()
				.getClass()
				.getDeclaredField("historicosTabela")
				.get(joinPoint.getTarget());
		LOG.info("ENVIANDO {} ALTERAÇÃO(ÕES) PARA O MICROSERVICE DE AUDITORIA, MÉTODO DE ORIGEM: {}", historicosTabela.size(), joinPoint.getSignature().getName());
		if (!historicosTabela.isEmpty()) {
			auditoriaClient.auditarAlteracao(historicosTabela);
		}
		return returningObj;
	}	

}
