/**
 * 
 */
package br.com.facilpay.payment.infra.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Renan F Rodrigues
 *
 */

@Entity
@Table(name = "tbl_parcela_cartao")
@Getter
@Setter
public class CartaoParcelaEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_parcela")
	private Long id;		
	
	@Column(name = "vl_parcela")
	private BigDecimal valorParcela;
	
	@Column(name = "qt_parcela")
	private int quantidadeParcelas;
	
	@ManyToOne
	@JoinColumn(name  = "id_cartao", foreignKey = @ForeignKey(name = "fk_parcela_cartao"))
	private CartaoEntity cartaoUtilizado;
	
	@ManyToOne
	@JoinColumn(name  = "id_transacao", foreignKey = @ForeignKey(name = "fk_parcela_transacao"))
	private TransacaoEntity transacaoVinculada;

}
