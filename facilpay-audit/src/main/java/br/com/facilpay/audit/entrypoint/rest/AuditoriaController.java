package br.com.facilpay.audit.entrypoint.rest;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.facilpay.audit.domain.port.ManterHistoricoTabelasUseCase;
import br.com.facilpay.shared.domain.HistoricoTabelas;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = { "audit" })
public class AuditoriaController {
	
	@Autowired
	private ManterHistoricoTabelasUseCase manterHistoricoTabelasUseCase;
	
	private static final Logger LOG = LoggerFactory.getLogger(AuditoriaController.class);
	
	@ApiOperation(value = "PERSISTE OS DADOS DE UM ESTABELECIMENTO", notes = "PERMITE CADASTRAR UM NOVO EC NA FÁCIL PAY")
	@PostMapping
	public ResponseEntity<Void> auditarAlteracao(
			@RequestBody @Valid List<HistoricoTabelas> historicosTabela) {
		LOG.info("AUDITANDO ALTERAÇÃO DE BANCO DE DADOS NA TABELA {}, REGISTROS ALTERADOS: {}", historicosTabela.get(0).getNomeTabela(), historicosTabela.size());
		manterHistoricoTabelasUseCase.auditarAlteracao(historicosTabela);
		return ResponseEntity.ok().build();
	}
	
}
