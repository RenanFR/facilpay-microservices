package br.com.facilpay.audit.output.db.adapter;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.facilpay.audit.infra.entities.HistoricoTabelasEntity;
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

	@Override
	public Page<HistoricoTabelas> recuperarHistoricoRegistro(String tabela, Long idRegistro, LocalDateTime dataInicial, 
			LocalDateTime dataFinal, Pageable pageable) {
		Page<HistoricoTabelasEntity> historicoRegistro = repository
				.findByIdNomeTabelaAndIdIdRegistroAlteradoAndIdDataHoraManutencaoBetween(tabela, idRegistro, dataInicial, dataFinal, pageable);
		return new PageImpl<HistoricoTabelas>(mapper.convertAllToDto(historicoRegistro.getContent()), pageable, 
				repository.countByIdNomeTabelaAndIdIdRegistroAlteradoAndIdDataHoraManutencaoBetween(tabela, idRegistro, 
						dataInicial, dataFinal));
	}

}
