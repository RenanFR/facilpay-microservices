package br.com.facilpay.infra.output.http.port;

import java.util.List;

import br.com.facilpay.shared.domain.HistoricoTabelas;

public interface AuditoriaClient {
	
	void auditarAlteracao(List<HistoricoTabelas> historicosTabela);

}
