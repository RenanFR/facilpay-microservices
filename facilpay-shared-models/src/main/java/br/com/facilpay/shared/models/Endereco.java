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
	    value = "Endereço",
	    description = "INFORMAÇÕES GERAIS DE ENDEREÇO"
)
@ToString
public class Endereco {
	
	@ApiModelProperty(
			value = "CEP",
			example = "02652-090")	
	private String cep;
	
	@ApiModelProperty(
			value = "LOGRADOURO",
			example = "Rua Caravela Princesa")	
	private String logradouro;
	
	@ApiModelProperty(
			value = "NÚMERO",
			example = "985")	
	private String numero;
	
	@ApiModelProperty(
			value = "BAIRRO",
			example = "Jardim Peri")	
	private String bairro;
	
	@ApiModelProperty(
			value = "CIDADE",
			example = "São Paulo")	
	private String cidade;
	
	@ApiModelProperty(
			value = "ESTADO",
			example = "SP")	
	private String estado;	

}
