/**
 * 
 */
package br.com.facilpay.payment.infra.entities;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Renan F Rodrigues
 *
 */

@Entity
@Table(name = "tbl_transacao")
@Getter
@Setter
public class TransacaoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_transacao")
	private Long id;	
	
	@Column(name = "cd_transacao")
	private String codigo;
	
	@Column(name = "cd_token")
	private String token;
	
	@Column(name = "qt_cartoes")
	private int quantidadeCartoes;
	
	@Column(name = "vl_total")
	private BigDecimal valorTotal;
	
	@OneToMany(mappedBy = "transacaoVinculada")
	private List<CartaoParcelaEntity> parcelas;
	
}
