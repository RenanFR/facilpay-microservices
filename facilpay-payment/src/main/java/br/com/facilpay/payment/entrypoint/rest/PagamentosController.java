/**
 * 
 */
package br.com.facilpay.payment.entrypoint.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.facilpay.infra.SwaggerConfig;
import br.com.facilpay.payment.domain.Transacao;
import br.com.facilpay.payment.domain.port.TransacaoCartaoCreditoUseCase;
import br.com.facilpay.payment.input.http.port.EstabelecimentoComercialClient;
import br.com.facilpay.shared.domain.EstabelecimentoComercial;
import io.swagger.annotations.Api;

/**
 * @author rnfr
 *
 */

@RestController
@RequestMapping(path = { "pagamentos" })
@Api(tags = { SwaggerConfig.TAG_TRANSACOES })
public class PagamentosController {
	
	private static final Logger LOG = LoggerFactory.getLogger(PagamentosController.class);
	
	@Autowired
	private TransacaoCartaoCreditoUseCase transacaoCCUseCase;
	
	@Autowired
	private EstabelecimentoComercialClient ecClient;
	
	@GetMapping("/{id}")
	public ResponseEntity<Transacao> getPorId(
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(transacaoCCUseCase.buscarPorId(id));
	}

	@PostMapping
	public ResponseEntity<Transacao> postTransacao(
			@RequestBody Transacao transacao) {
		LOG.info("EFETIVANDO UMA TRANSAÇÃO COM OS DADOS: {}", transacao);
		EstabelecimentoComercial ec = ecClient.verificarPorId(transacao.getEcId());
		LOG.info("ESTABELECIMENTO VINCULADO: {}", ec);
		return ResponseEntity.ok(transacaoCCUseCase.efetuarTransacao(transacao));
	}	
	
}
