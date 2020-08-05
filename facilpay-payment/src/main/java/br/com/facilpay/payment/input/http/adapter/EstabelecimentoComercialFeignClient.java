package br.com.facilpay.payment.input.http.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.facilpay.shared.domain.EstabelecimentoComercial;

@FeignClient("facilpay-backoffice-ec")
public interface EstabelecimentoComercialFeignClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/estabelecimentos/{id}")
	ResponseEntity<EstabelecimentoComercial> verificarPorId(@PathVariable("id") Long ecId);

}
