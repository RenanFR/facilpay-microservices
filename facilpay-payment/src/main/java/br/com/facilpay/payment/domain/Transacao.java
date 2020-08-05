/**
 * 
 */
package br.com.facilpay.payment.domain;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author rnfr
 *
 */

@Getter
@Setter
@ToString
public class Transacao {
	
	private Long id;
	
	private Long ecId;	
	
	private String codigo;
	
	private String token;
	
	private int quantidadeCartoes;
	
	private BigDecimal valorTotal;
	
	private List<CartaoParcela> parcelas;	

}
