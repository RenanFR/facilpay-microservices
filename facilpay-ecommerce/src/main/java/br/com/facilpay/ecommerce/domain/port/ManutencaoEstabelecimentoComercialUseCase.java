/**
 * 
 */
package br.com.facilpay.ecommerce.domain.port;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.facilpay.ecommerce.entrypoint.rest.EstabelecimentoComercialFilter;
import br.com.facilpay.shared.domain.EstabelecimentoComercial;

/**
 * @author rnfr
 *
 */
public interface ManutencaoEstabelecimentoComercialUseCase {
	
	Page<EstabelecimentoComercial> buscarTodos(Pageable pageRequest);
	
	EstabelecimentoComercial buscarPorId(Long id);
	
	EstabelecimentoComercial salvarOuAtualizar(EstabelecimentoComercial estabelecimento);
	
	EstabelecimentoComercial removePorId(Long id);
	
	Page<EstabelecimentoComercial> pesquisar(EstabelecimentoComercialFilter filter, Pageable pageable);
	
}
