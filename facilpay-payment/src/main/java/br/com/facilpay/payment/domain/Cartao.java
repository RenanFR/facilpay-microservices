/**
 * 
 */
package br.com.facilpay.payment.domain;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author rnfr
 *
 */

@Getter
@Setter
public class Cartao {
	
	private Long id;	
	
	private TipoCartao tipoCartao;
	
	private BandeiraCartao bandeira;
	
	private String numero;
	
	private String nomeDoTitular;
	
	private String bin;
	
	private String iin;
	
	private LocalDate dataDeVencimento;
	
	private int cvv;
	
	private Portador portadorTitular;
	
	private List<CartaoParcela> parcelamentosCartao;	

}
