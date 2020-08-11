package br.com.facilpay.infra.output.http.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.facilpay.infra.output.http.port.AuditoriaClient;
import br.com.facilpay.shared.domain.HistoricoTabelas;

@Service
public class AuditoriaClientAdapter implements AuditoriaClient {
	
	@Autowired
	private AuditoriaFeignClient auditoriaClient;

	@Override
	public void auditarAlteracao(List<HistoricoTabelas> historicosTabela) {
		auditoriaClient.auditarAlteracao(historicosTabela);
	}	

}
