package br.com.facilpay.audit.domain.port;

import java.util.List;

import br.com.facilpay.shared.domain.HistoricoTabelas;

public interface ManterHistoricoTabelasUseCase {
	
	void auditarAlteracao(List<HistoricoTabelas> historicosTabela);
	
}
