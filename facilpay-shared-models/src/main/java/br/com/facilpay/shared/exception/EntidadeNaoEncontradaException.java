/**
 * 
 */
package br.com.facilpay.shared.exception;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author rnfr
 *
 */
public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaException(TipoEntidade tipoEntidade, String... argumentos) {
		super("Não foi possível encontrar o(a) " + tipoEntidade + " na base através dos parâmetros " + Stream.of(argumentos).collect(Collectors.joining(",")));
	}
	
	public enum TipoEntidade {
		ESTABELECIMENTO("estabelecimento comercial"), 
		TRANSACAO("transação financeira");
		
		String tipo;
		
		private TipoEntidade(String tipo) {
			this.tipo = tipo;
		}
		
		@Override
		public String toString() {
			return tipo;
		}
	}

}
