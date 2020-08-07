package br.com.facilpay.audit.domain.port;

import br.com.facilpay.shared.domain.HistoricoTabelas;

public interface ManterHistoricoTabelasUseCase {
	
	void auditarAlteracao(HistoricoTabelas historicoTabela);
	
}
