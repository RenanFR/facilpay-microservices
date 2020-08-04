/**
 * 
 */
package br.com.facilpay.shared.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author rnfr
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(
	    value = "Contato",
	    description = "INFORMAÇÕES GERAIS DE CONTATO"
)
@ToString
public class Contato {

	@ApiModelProperty(
			value = "NÚMERO DE TELEFONE COMERCIAL OU RESIDENCIAL DO EMPREENDEDOR",
			example = "(11) 2967-5644")
	private String telefone;
	
	@ApiModelProperty(
			value = "NÚMERO DO CELULAR CORPORATIVO OU PESSOAL DO EMPREENDEDOR",
			example = "(11) 99236-9007")	
	private String celular;
	
	@ApiModelProperty(
			value = "EMAIL DE CONTATO OFICIAL DA EMPRESA OU PESSOAL DO EMPREENDEDOR",
			example = "manutencao@enzoebrenocomerciodebebidasme.com.br")		
	private String email;
	
	@ApiModelProperty(
			value = "NOME DO CONTATO PARA INTERAÇÃO COM O ESTABELECIMENTO",
			example = "Breno")		
	private String pessoa;		
	
}
