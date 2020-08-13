package br.com.facilpay.infra.output.http.adapter;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.facilpay.infra.output.http.port.AuditoriaClient;
import br.com.facilpay.shared.domain.HistoricoTabelas;
import br.com.facilpay.shared.models.FacilPayResponse;

@Service
public class AuditoriaClientAdapter implements AuditoriaClient {
	
	@Autowired
	private AuditoriaFeignClient auditoriaClient;

	@Override
	public void auditarAlteracao(List<HistoricoTabelas> historicosTabela) {
		auditoriaClient.auditarAlteracao(historicosTabela);
	}

	@Override
	public FacilPayResponse<HistoricoTabelas> getHistoricoAuditoriaRegistro(String tabela, Long idRegistro, 
			LocalDateTime dataInicial, LocalDateTime dataFinal, int page, int size) {
		return auditoriaClient.getHistoricoAuditoriaRegistro(tabela, idRegistro, dataInicial, dataFinal, page, size).getBody();
	}	

}
