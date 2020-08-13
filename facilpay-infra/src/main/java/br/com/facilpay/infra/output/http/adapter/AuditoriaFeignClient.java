package br.com.facilpay.infra.output.http.adapter;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.facilpay.shared.domain.HistoricoTabelas;
import br.com.facilpay.shared.models.FacilPayResponse;

@FeignClient("facilpay-commons-audit")
public interface AuditoriaFeignClient {
	
	@RequestMapping(method = RequestMethod.POST, value = "/audit")
	ResponseEntity<Void> auditarAlteracao(@RequestBody List<HistoricoTabelas> historicosTabela);
	
	@RequestMapping(method = RequestMethod.GET, value = "/audit/{tabela}/{idRegistro}")
	public ResponseEntity<FacilPayResponse<HistoricoTabelas>> getHistoricoAuditoriaRegistro(
			@PathVariable("tabela") String tabela, 
			@PathVariable("idRegistro") Long idRegistro, 
			@RequestParam LocalDateTime dataInicial, 
			@RequestParam LocalDateTime dataFinal, 
			@RequestParam int page,
			@RequestParam int size);

}
