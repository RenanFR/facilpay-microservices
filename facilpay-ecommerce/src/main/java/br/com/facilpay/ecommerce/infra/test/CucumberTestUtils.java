package br.com.facilpay.ecommerce.infra.test;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.facilpay.ecommerce.entrypoint.rest.EstabelecimentoComercialFilter;
import br.com.facilpay.ecommerce.entrypoint.rest.FacilPayResponse;
import br.com.facilpay.ecommerce.exception.ApiError;
import br.com.facilpay.shared.domain.EstabelecimentoComercial;
import br.com.facilpay.shared.models.Contato;
import br.com.facilpay.shared.models.Endereco;

@Service
public class CucumberTestUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(CucumberTestUtils.class);
	
	
	@Autowired
	private RestTemplate restTemplate;
	
    public EstabelecimentoComercial ecFromCucumberDataTable(Map<String, String> dataTable) {
		EstabelecimentoComercial estabelecimentoComercial = new EstabelecimentoComercial();
		estabelecimentoComercial.setCnpj(dataTable.get("cnpj"));
		estabelecimentoComercial.setInscricaoEstadual(dataTable.get("inscricaoEstadual"));
		estabelecimentoComercial.setRazaoSocial(dataTable.get("razaoSocial"));
		estabelecimentoComercial.setNomeFantasia(dataTable.get("nomeFantasia"));
		Contato contato = new Contato(dataTable.get("telefone"), dataTable.get("celular"), dataTable.get("email"), dataTable.get("pessoa"));
		estabelecimentoComercial.setContato(contato);
		Endereco endereco = new Endereco(dataTable.get("cep"), dataTable.get("logradouro"), dataTable.get("numero"), dataTable.get("bairro"), dataTable.get("cidade"), dataTable.get("estado"));
		estabelecimentoComercial.setEndereco(endereco);
		estabelecimentoComercial.setAtividadeEconomica(dataTable.get("atividadeEconomica"));
		estabelecimentoComercial.setNaturezaJuridica(dataTable.get("naturezaJuridica"));
		estabelecimentoComercial.setPorte(dataTable.get("porte"));
		estabelecimentoComercial.setSite(dataTable.get("site"));
		estabelecimentoComercial.setAtivo(Boolean.valueOf(dataTable.get("ativo")));
		estabelecimentoComercial.setEstadoIE(dataTable.get("estadoIE"));
        return estabelecimentoComercial;
    }	
    
	public ResponseEntity<EstabelecimentoComercial> salvarEstabelecimento(String endPointURL, EstabelecimentoComercial estabelecimentoComercial) {
		int status;
		try {
			ResponseEntity<EstabelecimentoComercial> postResponse = restTemplate.postForEntity(endPointURL, estabelecimentoComercial, EstabelecimentoComercial.class);
			return postResponse;
		} catch (HttpClientErrorException exception) {
			ApiError apiError;
			ObjectMapper objectMapper = new ObjectMapper();
			status = exception.getRawStatusCode();
			try {
				apiError = objectMapper.readValue(exception.getResponseBodyAsString(), ApiError.class);
				LOG.error(apiError.getMessage());
				LOG.error(apiError.getError());
				apiError
				.getFieldsErrors()
				.forEach(err -> {
					LOG.error(err.getField() + " : " + err.getError());
				});
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}
		return ResponseEntity.status(status).build();
	}    
	
	public ResponseEntity<FacilPayResponse<EstabelecimentoComercial>> pesquisarEstabelecimentos(String endPointURL, EstabelecimentoComercialFilter filtroPesquisa) {
		ResponseEntity<FacilPayResponse<EstabelecimentoComercial>> response = restTemplate.exchange(UriComponentsBuilder.fromUriString(endPointURL)
				.queryParam("page", 0)
				.queryParam("size", 10)
				.queryParam("cnpj", filtroPesquisa.getCnpj())
				.queryParam("cpf", filtroPesquisa.getCpf())
				.queryParam("inscricaoEstadual", filtroPesquisa.getInscricaoEstadual())
				.queryParam("razaoSocial", filtroPesquisa.getRazaoSocial())
				.queryParam("nomeFantasia", filtroPesquisa.getNomeFantasia())
				.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<FacilPayResponse<EstabelecimentoComercial>>() {});
		return response;		
	}
	
	public ResponseEntity<EstabelecimentoComercial> deleteEstabelecimento(String endPointURL, EstabelecimentoComercial ec) {
		ResponseEntity<EstabelecimentoComercial> response = restTemplate.exchange(endPointURL + "/" + ec.getId(), HttpMethod.DELETE, null, EstabelecimentoComercial.class);
		return response;		
	}
	
}
