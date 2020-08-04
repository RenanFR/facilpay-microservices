/**
 * 
 */
package br.com.facilpay.payment.domain.adapter;

import org.springframework.stereotype.Service;

import br.com.facilpay.payment.domain.Transacao;
import br.com.facilpay.payment.domain.port.TransacaoCartaoCreditoUseCase;

/**
 * @author rnfr
 *
 */

@Service
public class TransacaoCartaoCreditoServiceAdapter implements TransacaoCartaoCreditoUseCase {

	@Override
	public Transacao buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transacao efetuarTransacao(Transacao transacao) {
		// TODO Auto-generated method stub
		return null;
	}

}
