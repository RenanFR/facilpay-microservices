/**
 * 
 */
package br.com.facilpay.ecommerce.output.db.adapter;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.facilpay.ecommerce.infra.entities.SegmentoEstabelecimentoEntity;

/**
 * @author Renan F Rodrigues
 *
 */
public interface SpringJPASegmentoEstabelecimentoRepository extends JpaRepository<SegmentoEstabelecimentoEntity, Long> {

}
