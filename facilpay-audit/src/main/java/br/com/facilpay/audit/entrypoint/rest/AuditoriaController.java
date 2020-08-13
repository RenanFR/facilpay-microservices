package br.com.facilpay.audit.entrypoint.rest;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.facilpay.audit.domain.port.ManterHistoricoTabelasUseCase;
import br.com.facilpay.shared.domain.HistoricoTabelas;
import br.com.facilpay.shared.models.FacilPayResponse;
import br.com.facilpay.shared.models.ResponseMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = { "audit" })
public class AuditoriaController {
	
	@Autowired
	private ManterHistoricoTabelasUseCase manterHistoricoTabelasUseCase;
	
	private static final Logger LOG = LoggerFactory.getLogger(AuditoriaController.class);
	
	@ApiOperation(value = "AUDITA ALTERAÇÕES EM DETERMINADA TABELA CORRESPONDENTE A UMA ENTIDADE DO DOMÍNIO DA FÁCIL PAY", 
			notes = "PERSISTE NA TABELA DE HISTÓRICO DE ALTERAÇÕES AS MUDANÇAS FEITAS EM CADA CAMPO DA ENTIDADE")
	@PostMapping
	public ResponseEntity<Void> postAuditarAlteracao(
			@RequestBody @Valid List<HistoricoTabelas> historicosTabela) {
		LOG.info("AUDITANDO ALTERAÇÃO DE BANCO DE DADOS NA TABELA {}, REGISTROS ALTERADOS: {}", historicosTabela.get(0).getNomeTabela(), historicosTabela.size());
		manterHistoricoTabelasUseCase.auditarAlteracao(historicosTabela);
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "RECUPERA O HISTÓRICO DE ALTERAÇÃO PARA DETERMINADO TIPO DE ENTIDADE E REGISTRO ESPECÍFICO", 
			notes = "OBTÉM PARA TABELA E REGISTRO DESEJADO O HISTÓRICO DE MUDANÇAS NOS CAMPOS")
	@GetMapping("/{tabela}/{idRegistro}")
	public ResponseEntity<FacilPayResponse<HistoricoTabelas>> getHistoricoAuditoriaRegistro(
			@PathVariable("tabela") String tabela, 
			@PathVariable("idRegistro") Long idRegistro, 
			@RequestParam LocalDateTime dataInicial, 
			@RequestParam LocalDateTime dataFinal, 			
			@ApiParam(value = "NÚMERO DA PÁGINA DESEJADA", example = "1")
			@RequestParam int page,
			@ApiParam(value = "NÚMERO DE REGISTROS POR PÁGINA", example = "10")
			@RequestParam int size) {
		LOG.info("RECUPERANDO O HISTÓRICO DE ALTERAÇÕES NA TABELA {} PARA O REGISTRO {}", tabela, idRegistro);
		Page<HistoricoTabelas> historicoRegistro = manterHistoricoTabelasUseCase.recuperarHistoricoRegistro(tabela, idRegistro, dataInicial, 
				dataFinal, PageRequest.of(page, size));
		FacilPayResponse<HistoricoTabelas> response = new ResponseMapper<HistoricoTabelas>().map(historicoRegistro.getContent(), historicoRegistro.isFirst(), historicoRegistro.isLast(), 
				historicoRegistro.getNumberOfElements(), historicoRegistro.getTotalElements(), historicoRegistro.getNumber(), 
				historicoRegistro.getSize(), historicoRegistro.getTotalPages());
		return ResponseEntity.ok(response);
	}	
	
}
