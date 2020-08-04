/**
 * 
 */
package br.com.facilpay.payment.infra.db.port;

import br.com.facilpay.payment.domain.Transacao;

/**
 * @author rnfr
 *
 */
public interface TransacaoRepository {
	
	Transacao buscarPorId(Long id);
	
	Transacao salvarOuAtualizar(Transacao estabelecimento);	

}
