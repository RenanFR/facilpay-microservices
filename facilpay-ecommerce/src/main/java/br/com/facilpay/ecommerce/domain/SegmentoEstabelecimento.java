/**
 * 
 */
package br.com.facilpay.ecommerce.domain;

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
	    value = "Segmento de atuação (EC)",
	    description = "CONTROLE INTERNO DE SEGMENTOS DE ATUAÇÃO DOS ECs"
)
public class SegmentoEstabelecimento {
	
	@ApiModelProperty(
			value = "CÓDIGO DO SEGMENTO NA BASE DA FÁCIL PAY",
			example = "1")
	private Long id;	
	
	@ApiModelProperty(
			value = "NOME DO SEGMENTO DE ATUAÇÃO",
			example = "Comércio de bebidas")		
	private String nome;	
	
	@ApiModelProperty(
			value = "DESCRIÇÃO TEXTUAL DO SEGMENTO DE ATUAÇÃO",
			example = "Comércio de vendas de bebidas do distribuidor para o consumidor ou outro negócio ")	
	private String descricao;		
	
}
