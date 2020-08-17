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
	    value = "Segmento de atuação (MCC/Merchant Category Code)",
	    description = "CONTROLE INTERNO DE SEGMENTOS DE ATUAÇÃO DOS ECs"
)
public class MCC {
	
	@ApiModelProperty(
			value = "CÓDIGO DO MCC",
			example = "1799")
	private Long mccCode;	
	
	@ApiModelProperty(
			value = "NOME DO SEGMENTO DE ATUAÇÃO",
			example = "DEPARTMENT STORES")		
	private String nome;	
	
	@ApiModelProperty(
			value = "DESCRIÇÃO TEXTUAL DO SEGMENTO DE ATUAÇÃO",
			example = "DEPARTMENT STORES")	
	private String descricao;		
	
}
