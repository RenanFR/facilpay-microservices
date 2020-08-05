package br.com.facilpay.payment.input.http.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.facilpay.payment.input.http.port.EstabelecimentoComercialClient;
import br.com.facilpay.shared.domain.EstabelecimentoComercial;

@Service
public class EstabelecimentoComercialClientAdapter implements EstabelecimentoComercialClient {
	
	@Autowired
	private EstabelecimentoComercialFeignClient client;

	@Override
	public EstabelecimentoComercial verificarPorId(Long id) {
		return client.verificarPorId(id).getBody();
	}

}
