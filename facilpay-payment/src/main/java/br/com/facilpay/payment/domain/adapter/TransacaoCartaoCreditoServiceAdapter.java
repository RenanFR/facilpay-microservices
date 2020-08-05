/**
 * 
 */
package br.com.facilpay.payment.domain.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.facilpay.payment.domain.Transacao;
import br.com.facilpay.payment.domain.port.TransacaoCartaoCreditoUseCase;

/**
 * @author rnfr
 *
 */

@Service
public class TransacaoCartaoCreditoServiceAdapter implements TransacaoCartaoCreditoUseCase {
	
	private static final Logger LOG = LoggerFactory.getLogger(TransacaoCartaoCreditoServiceAdapter.class);

	@Override
	public Transacao buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transacao efetuarTransacao(Transacao transacao) {
		LOG.info("FINALIZANDO TRANSAÇÃO: {}", transacao);
		return null;
	}

}
