package br.com.facilpay.audit.output.db.adapter;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.facilpay.audit.infra.entities.HistoricoTabelasEntity;
import br.com.facilpay.audit.infra.entities.HistoricoTabelasId;

public interface SpringJPAHistoricoTabelasRepository extends PagingAndSortingRepository<HistoricoTabelasEntity, HistoricoTabelasId> {
	
	Page<HistoricoTabelasEntity> findByIdNomeTabelaAndIdIdRegistroAlteradoAndIdDataHoraManutencaoBetween(String idNomeTabela, 
			Long idIdRegistroAlterado, LocalDateTime dataInicial, LocalDateTime dataFinal, Pageable pageable);
	
	Long countByIdNomeTabelaAndIdIdRegistroAlteradoAndIdDataHoraManutencaoBetween(String idNomeTabela, Long idIdRegistroAlterado, 
			LocalDateTime dataInicial, LocalDateTime dataFinal);

}
