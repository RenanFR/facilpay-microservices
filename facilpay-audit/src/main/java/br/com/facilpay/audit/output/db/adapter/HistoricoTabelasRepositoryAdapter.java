package br.com.facilpay.audit.output.db.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.facilpay.audit.infra.mapper.HistoricoTabelasMapper;
import br.com.facilpay.audit.output.db.port.HistoricoTabelasRepository;
import br.com.facilpay.shared.domain.HistoricoTabelas;

@Repository
public class HistoricoTabelasRepositoryAdapter implements HistoricoTabelasRepository {
	
	@Autowired
	private SpringJPAHistoricoTabelasRepository repository;
	
	@Autowired
	private HistoricoTabelasMapper mapper;
	
	@Override
	public void salvarHistorico(List<HistoricoTabelas> historicosTabela) {
		repository.saveAll(mapper.convertAllToEntity(historicosTabela));
	}

}
