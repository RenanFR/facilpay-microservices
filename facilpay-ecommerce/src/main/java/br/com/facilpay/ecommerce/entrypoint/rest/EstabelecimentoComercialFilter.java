/**
 * 
 */
package br.com.facilpay.ecommerce.entrypoint.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Renan F Rodrigues
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EstabelecimentoComercialFilter {
	
	private String cnpj;
	
	private String cpf;
	
	private String inscricaoEstadual;
	
	private String razaoSocial;
	
	private String nomeFantasia;
	
	private String numeroContrato;

}
