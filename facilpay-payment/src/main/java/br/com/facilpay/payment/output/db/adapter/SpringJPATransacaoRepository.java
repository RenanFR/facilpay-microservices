/**
 * 
 */
package br.com.facilpay.payment.output.db.adapter;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.facilpay.payment.infra.entities.TransacaoEntity;

/**
 * @author rnfr
 *
 */
public interface SpringJPATransacaoRepository extends JpaRepository<TransacaoEntity, Long> {

}
