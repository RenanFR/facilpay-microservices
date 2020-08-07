/**
 * 
 */
package br.com.facilpay.ecommerce.domain.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.facilpay.ecommerce.domain.port.ManutencaoEstabelecimentoComercialUseCase;
import br.com.facilpay.ecommerce.entrypoint.rest.EstabelecimentoComercialFilter;
import br.com.facilpay.ecommerce.output.db.port.EstabelecimentoComercialRepository;
import br.com.facilpay.shared.domain.EstabelecimentoComercial;

/**
 * @author Renan F Rodrigues
 *
 */

@Service
public class EstabelecimentoComercialServiceAdapter implements ManutencaoEstabelecimentoComercialUseCase {
	
	@Autowired
	private EstabelecimentoComercialRepository repository;
	
	public Page<EstabelecimentoComercial> buscarTodos(Pageable pageRequest) {
		return repository.buscarTodos(pageRequest);
		
	}	

	public EstabelecimentoComercial buscarPorId(Long id) {
		return repository.buscarPorId(id);
	}

	public EstabelecimentoComercial salvarOuAtualizar(EstabelecimentoComercial estabelecimento) {
		return repository.salvarOuAtualizar(estabelecimento);
	}

	public EstabelecimentoComercial removePorId(Long id) {
		return repository.removePorId(id);
	}

	@Override
	public Page<EstabelecimentoComercial> pesquisar(EstabelecimentoComercialFilter filter, Pageable pageable) {
		return repository.pesquisar(filter, pageable);
	}
}
