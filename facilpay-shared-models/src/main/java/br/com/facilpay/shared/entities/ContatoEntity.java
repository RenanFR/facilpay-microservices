/**
 * 
 */
package br.com.facilpay.shared.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Renan F Rodrigues
 *
 */

@Embeddable
@Getter
@Setter
public class ContatoEntity {
	
	@Column(name = "nu_telefone")
	private String telefone;
	
	@Column(name = "nu_celular")
	private String celular;
	
	@Column(name = "ds_email")
	private String email;
	
	@Column(name = "nm_pessoa")
	private String pessoa;	

}
