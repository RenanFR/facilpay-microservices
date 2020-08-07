package br.com.facilpay.audit.output.db.port;

import br.com.facilpay.shared.domain.HistoricoTabelas;

public interface HistoricoTabelasRepository {
	
	void salvarHistorico(HistoricoTabelas historicoTabela);

}
