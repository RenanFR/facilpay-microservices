/**
 * 
 */
package br.com.facilpay.shared.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author rnfr
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(
	    value = "Serviço da Fácil Pay",
	    description = "SERVIÇOS QUE A FÁCIL PAY OFERECE JUNTO AOS ECs"
)
public class ServicoFacilPay {

	@ApiModelProperty(
			value = "CÓDIGO DO SERVIÇO PRESTADO PELA FÁCIL PAY",
			example = "1")	
	private Long id;
	
	@ApiModelProperty(
			value = "NOME DO SERVIÇO PRESTADO PELA FÁCIL PAY",
			example = "ANÁLISE DE RISCO")		
	private String nome;	
	
	@ApiModelProperty(
			value = "DESCRIÇÃO TEXTUAL DO SERVIÇO PRESTADO PELA FÁCIL PAY",
			example = "ANÁLISE DE RISCO")		
	private String descricao;		
	
}
