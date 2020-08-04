/**
 * 
 */
package br.com.facilpay.payment.entrypoint.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.facilpay.payment.domain.Transacao;
import br.com.facilpay.payment.domain.port.TransacaoCartaoCreditoUseCase;

/**
 * @author rnfr
 *
 */

@RestController
@RequestMapping(path = { "pagamentos" })
public class PagamentosController {
	
	@Autowired
	private TransacaoCartaoCreditoUseCase transacaoCCUseCase;
	
	@GetMapping("/{id}")
	public ResponseEntity<Transacao> getPorId(
			@PathVariable("id") Long id) {
		return ResponseEntity.ok(transacaoCCUseCase.buscarPorId(id));
	}

	@PostMapping
	public ResponseEntity<Transacao> postTransacao(
			@RequestBody Transacao transacao) {
		return ResponseEntity.ok(transacaoCCUseCase.efetuarTransacao(transacao));
	}	
	
}
