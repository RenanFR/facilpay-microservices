/**
 * 
 */
package br.com.facilpay.payment.domain;

import java.util.List;

import br.com.facilpay.shared.models.Contato;
import br.com.facilpay.shared.models.Endereco;
import lombok.Getter;
import lombok.Setter;

/**
 * @author rnfr
 *
 */

@Getter
@Setter
public class Portador {
	
	private Long id;	
	
	private String nome;
	
	private String cpf;
	
	private Contato contato;
	
	private String dataNascimento;
	
	private Endereco endereco;
	
	private List<Cartao> cartoes;	

}
