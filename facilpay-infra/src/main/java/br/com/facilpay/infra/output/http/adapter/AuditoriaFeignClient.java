package br.com.facilpay.infra.output.http.adapter;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.facilpay.shared.domain.HistoricoTabelas;

@FeignClient("facilpay-commons-audit")
public interface AuditoriaFeignClient {
	
	@RequestMapping(method = RequestMethod.POST, value = "/audit")
	ResponseEntity<Void> auditarAlteracao(@RequestBody List<HistoricoTabelas> historicosTabela);	

}
