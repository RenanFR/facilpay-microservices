package br.com.facilpay.infra.output.http.port;

import java.time.LocalDateTime;
import java.util.List;

import br.com.facilpay.shared.domain.HistoricoTabelas;
import br.com.facilpay.shared.models.FacilPayResponse;

public interface AuditoriaClient {
	
	void auditarAlteracao(List<HistoricoTabelas> historicosTabela);
	
	public FacilPayResponse<HistoricoTabelas> getHistoricoAuditoriaRegistro(String tabela, Long idRegistro, 
			LocalDateTime dataInicial, LocalDateTime dataFinal, int page, int size);

}
