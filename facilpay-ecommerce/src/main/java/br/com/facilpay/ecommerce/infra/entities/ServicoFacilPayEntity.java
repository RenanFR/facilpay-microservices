/**
 * 
 */
package br.com.facilpay.ecommerce.infra.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Renan F Rodrigues
 *
 */

@Entity
@Table(name = "tbl_servico_facil_pay")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServicoFacilPayEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_servico")
	private Long id;
	
	@Column(name = "ds_servico")
	private String descricao;
	
	@Column(name = "nm_servico")
	private String nome;
	
	@ManyToMany
	@JoinTable(
	  name = "tbl_servico_estabelecimento", 
	  joinColumns = @JoinColumn(name = "id_servico"), 
	  inverseJoinColumns = @JoinColumn(name = "id_loja")
	)	
	private List<EstabelecimentoComercialEntity> estabelecimentosContratantes;

}
