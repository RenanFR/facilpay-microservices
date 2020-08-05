/**
 * 
 */
package br.com.facilpay.payment.output.db.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.facilpay.payment.domain.Transacao;
import br.com.facilpay.payment.infra.mapper.TransacaoMapper;
import br.com.facilpay.payment.output.db.port.TransacaoRepository;
import br.com.facilpay.shared.exception.EntidadeNaoEncontradaException;
import br.com.facilpay.shared.exception.EntidadeNaoEncontradaException.TipoEntidade;

/**
 * @author rnfr
 *
 */

@Repository
public class TransacaoRepositoryAdapter implements TransacaoRepository {
	
	@Autowired
	private SpringJPATransacaoRepository repository;
	
	@Autowired
	private TransacaoMapper mapper;

	@Override
	public Transacao buscarPorId(Long id) {
		return mapper.convertToDto(repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(TipoEntidade.TRANSACAO, id.toString())));
	}

	@Override
	public Transacao salvarOuAtualizar(Transacao transacao) {
		return mapper.convertToDto(repository.save(mapper.convertToEntity(transacao)));
	}	
	
}
