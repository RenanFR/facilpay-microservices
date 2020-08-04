/**
 * 
 */
package br.com.facilpay.ecommerce.infra.db.adapter;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.facilpay.ecommerce.infra.entities.ServicoFacilPayEntity;

/**
 * @author Renan F Rodrigues
 *
 */
public interface SpringJPAServicoFacilPayRepository extends JpaRepository<ServicoFacilPayEntity, Long> {

}
