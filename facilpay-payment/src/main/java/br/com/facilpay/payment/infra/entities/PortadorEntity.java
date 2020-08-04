/**
 * 
 */
package br.com.facilpay.payment.infra.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.facilpay.shared.entities.ContatoEntity;
import br.com.facilpay.shared.entities.EnderecoEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Renan F Rodrigues
 *
 */

@Entity
@Table(name = "tbl_portador")
@Getter
@Setter
public class PortadorEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_comprador")
	private Long id;	
	
	@Column(name = "nm_portador")
	private String nome;
	
	@Column(name = "cd_cpf")
	private String cpf;
	
	@Embedded
	private ContatoEntity contato;
	
	@Column(name = "dt_nascimento")
	private String dataNascimento;
	
	@Embedded
	private EnderecoEntity endereco;
	
	@OneToMany(mappedBy = "portadorTitular")
	private List<CartaoEntity> cartoes;

}
