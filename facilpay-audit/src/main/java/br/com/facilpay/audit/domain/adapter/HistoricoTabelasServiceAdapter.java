package br.com.facilpay.audit.domain.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.facilpay.audit.domain.port.ManterHistoricoTabelasUseCase;
import br.com.facilpay.audit.output.db.port.HistoricoTabelasRepository;
import br.com.facilpay.shared.domain.HistoricoTabelas;

@Service
public class HistoricoTabelasServiceAdapter implements ManterHistoricoTabelasUseCase {
	
	@Autowired
	private HistoricoTabelasRepository repository;

	@Override
	public void auditarAlteracao(HistoricoTabelas historicoTabela) {
		repository.salvarHistorico(historicoTabela);
	}

}
