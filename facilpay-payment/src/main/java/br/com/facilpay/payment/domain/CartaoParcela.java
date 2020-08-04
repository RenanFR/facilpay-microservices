/**
 * 
 */
package br.com.facilpay.payment.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * @author rnfr
 *
 */

@Getter
@Setter
public class CartaoParcela {
	
	private Long id;		
	
	private BigDecimal valorParcela;
	
	private int quantidadeParcelas;
	
	private Cartao cartaoUtilizado;
	
	private Transacao transacaoVinculada;	

}
