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
@Table(name = "tbl_segmento_estabelecimento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SegmentoEstabelecimentoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_segmento")
	private Long id;	
	
	@Column(name = "ds_segmento")
	private String descricao;		
	
	@Column(name = "nm_segmento")
	private String nome;
	
	@OneToMany(mappedBy = "segmento")
	private List<EstabelecimentoComercialEntity> estabelecimentos;

}
