/**
 * 
 */
package br.com.facilpay.ecommerce.output.db.adapter;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.facilpay.ecommerce.infra.entities.MCCEntity;

/**
 * @author Renan F Rodrigues
 *
 */
public interface SpringJPASegmentoEstabelecimentoRepository extends JpaRepository<MCCEntity, Long> {

}
