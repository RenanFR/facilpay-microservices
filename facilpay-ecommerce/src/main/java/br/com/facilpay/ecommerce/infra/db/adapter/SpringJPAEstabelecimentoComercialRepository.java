/**
 * 
 */
package br.com.facilpay.ecommerce.infra.db.adapter;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.facilpay.ecommerce.infra.entities.EstabelecimentoComercialEntity;

/**
 * @author Renan F Rodrigues
 *
 */
public interface SpringJPAEstabelecimentoComercialRepository extends JpaRepository<EstabelecimentoComercialEntity, Long> {

	/**
	 * @param razaoSocial
	 * @return
	 */
	EstabelecimentoComercialEntity findByRazaoSocial(String razaoSocial);

}
