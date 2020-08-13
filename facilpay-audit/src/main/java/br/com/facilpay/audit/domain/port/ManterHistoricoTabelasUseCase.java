package br.com.facilpay.audit.domain.port;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.facilpay.shared.domain.HistoricoTabelas;

public interface ManterHistoricoTabelasUseCase {
	
	void auditarAlteracao(List<HistoricoTabelas> historicosTabela);
	
	Page<HistoricoTabelas> recuperarHistoricoRegistro(String tabela, Long idRegistro, LocalDateTime dataInicial, 
			LocalDateTime dataFinal, Pageable pageable);
	
}
