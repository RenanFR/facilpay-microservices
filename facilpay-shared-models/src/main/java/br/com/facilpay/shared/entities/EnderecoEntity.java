/**
 * 
 */
package br.com.facilpay.shared.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Renan F Rodrigues
 *
 */

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoEntity {
	
	@Column(name = "cd_cep")
	private String cep;
	
	@Column(name = "ds_logradouro")
	private String logradouro;
	
	@Column(name = "nu_endereco")
	private String numero;
	
	@Column(name = "nm_bairro")
	private String bairro;
	
	@Column(name = "nm_cidade")
	private String cidade;
	
	@Column(name = "nm_uf")
	private String estado;	

}
