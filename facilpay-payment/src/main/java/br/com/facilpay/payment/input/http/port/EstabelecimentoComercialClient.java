package br.com.facilpay.payment.input.http.port;

import br.com.facilpay.shared.domain.EstabelecimentoComercial;

public interface EstabelecimentoComercialClient {
	
	EstabelecimentoComercial verificarPorId(Long id);

}
