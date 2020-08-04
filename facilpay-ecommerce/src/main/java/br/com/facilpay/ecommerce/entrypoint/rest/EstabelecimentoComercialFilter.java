/**
 * 
 */
package br.com.facilpay.ecommerce.entrypoint.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Renan F Rodrigues
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstabelecimentoComercialFilter {
	
	private String cnpj;
	
	private String cpf;
	
	private String inscricaoEstadual;
	
	private String razaoSocial;
	
	private String nomeFantasia;
	
	private String numeroContrato;

}
