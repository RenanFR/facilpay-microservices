package br.com.facilpay.audit.output.db.port;

import java.util.List;

import br.com.facilpay.shared.domain.HistoricoTabelas;

public interface HistoricoTabelasRepository {
	
	void salvarHistorico(List<HistoricoTabelas> historicosTabela);

}
