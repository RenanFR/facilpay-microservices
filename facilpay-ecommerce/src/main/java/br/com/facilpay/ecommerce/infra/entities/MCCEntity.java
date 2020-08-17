/**
 * 
 */
package br.com.facilpay.ecommerce.infra.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "tbl_mcc")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MCCEntity {
	
	@Id
	@Column(name = "id_mcc")
	private Long mccCode;	
	
	@Column(name = "ds_mcc")
	private String descricao;		
	
	@Column(name = "nm_mcc")
	private String nome;
	
	@OneToMany(mappedBy = "mcc")
	private List<EstabelecimentoComercialEntity> estabelecimentos;

}
