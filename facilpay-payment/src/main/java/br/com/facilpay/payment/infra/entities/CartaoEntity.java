/**
 * 
 */
package br.com.facilpay.payment.infra.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import br.com.facilpay.payment.domain.BandeiraCartao;
import br.com.facilpay.payment.domain.TipoCartao;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Renan F Rodrigues
 *
 */

@Entity
@Table(name = "tbl_cartao", uniqueConstraints = @UniqueConstraint(columnNames = { "id_cartao" }, name = "pk_cartao"))
@Getter
@Setter
public class CartaoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cartao")
	private Long id;	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tp_cartao")
	private TipoCartao tipoCartao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tp_bandeira")
	private BandeiraCartao bandeira;
	
	@Column(name = "nu_cartao")
	private String numero;
	
	@Column(name = "nm_titular")
	private String nomeDoTitular;
	
	@Column(name = "nu_bin")
	private String bin;
	
	@Column(name = "nu_iin")
	private String iin;
	
	@Column(name = "dt_vencimento")
	private LocalDate dataDeVencimento;
	
	@Transient
	private int cvv;
	
	@ManyToOne
	@JoinColumn(name  = "id_portador", foreignKey = @ForeignKey(name = "fk_cartao_portador"))
	private PortadorEntity portadorTitular;
	
	@OneToMany(mappedBy = "cartaoUtilizado")
	private List<CartaoParcelaEntity> parcelamentosCartao;
	
}
