/**
 * 
 */
package br.com.facilpay.payment.domain.port;

import br.com.facilpay.payment.domain.Transacao;

/**
 * @author rnfr
 *
 */
public interface TransacaoCartaoCreditoUseCase {
	
	
	Transacao buscarPorId(Long id);
	
	Transacao efetuarTransacao(Transacao transacao);		
	
}
