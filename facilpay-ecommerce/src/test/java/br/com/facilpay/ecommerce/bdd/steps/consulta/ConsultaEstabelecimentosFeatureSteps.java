package br.com.facilpay.ecommerce.bdd.steps.consulta;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.facilpay.ecommerce.entrypoint.rest.EstabelecimentoComercialFilter;
import br.com.facilpay.ecommerce.entrypoint.rest.FacilPayResponse;
import br.com.facilpay.ecommerce.exception.ApiError;
import br.com.facilpay.shared.domain.EstabelecimentoComercial;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ConsultaEstabelecimentosFeatureSteps {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConsultaEstabelecimentosFeatureSteps.class);
	
	@LocalServerPort
	private Integer serverPort;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String endPointURL;
	
	private int resultados;
	
	private EstabelecimentoComercialFilter filtroPesquisa;	
	
	private HttpStatus codigoRetorno;	
	
	@Before
	public void setEndPointURL() {
		endPointURL = "http://localhost:" + serverPort + "/estabelecimentos";
	}	

	@Dado("que desejo encontrar um ou mais ecs por meio dos atributos {string}, {string}, {string}, {string} e ou {string}")
	public void queDesejoEncontrarUmOuMaisEcsPorMeioDosAtributosEOu(String cnpj, String cpf, String inscricaoEstadual, String razaoSocial, String nomeFantasia) {
		LOG.info("DADO QUE DESEJO ENCONTRAR UM OU MAIS ECS POR MEIO DOS ATRIBUTOS {}, {}, {}, {} E OU {}", cnpj, cpf, inscricaoEstadual, razaoSocial, nomeFantasia);
		filtroPesquisa = EstabelecimentoComercialFilter
				.builder()
				.cnpj(cnpj)
				.cpf(cpf)
				.inscricaoEstadual(inscricaoEstadual)
				.razaoSocial(razaoSocial)
				.nomeFantasia(nomeFantasia)
				.build();
	}

	@Dado("que tenho alguns registro de teste")
	public void queTenhoAlgunsRegistroDeTeste() {
		List<EstabelecimentoComercial> estabelecimentosTeste = EstabelecimentoComercial.getEstabelecimentosTeste();
		LOG.info("DADO QUE TENHO {} REGISTROS DE TESTE", estabelecimentosTeste.size());
		estabelecimentosTeste.forEach(ec -> {
			salvarEstabelecimento(ec);
		});
	}

	@Quando("realizo a chamada para pesquisa")
	public void realizoAChamadaParaPesquisa() {
		LOG.info("QUANDO REALIZO A CHAMADA PARA PESQUISA");
		ResponseEntity<FacilPayResponse<EstabelecimentoComercial>> response = restTemplate.exchange(UriComponentsBuilder.fromUriString(endPointURL)
				.queryParam("page", 0)
				.queryParam("size", 10)
				.queryParam("cnpj", filtroPesquisa.getCnpj())
				.queryParam("cpf", filtroPesquisa.getCpf())
				.queryParam("inscricaoEstadual", filtroPesquisa.getInscricaoEstadual())
				.queryParam("razaoSocial", filtroPesquisa.getRazaoSocial())
				.queryParam("nomeFantasia", filtroPesquisa.getNomeFantasia())
				.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<FacilPayResponse<EstabelecimentoComercial>>() {});
	    List<EstabelecimentoComercial> estabelecimentos = response.getBody().getContent();
	    LOG.info(estabelecimentos.toString());
		resultados = estabelecimentos.size();
	    estabelecimentos.forEach(estabelecimento -> {
	    	LOG.info("FOI ENCONTRADO O ESTABELECIMENTO {}", estabelecimento.getRazaoSocial());
	    	
	    });
	    LOG.info("O NÚMERO DE RESULTADOS FOI {}", resultados);
	}

	@Então("o numero de resultados devera ser {int}")
	public void oNumeroDeResultadosDeveraSerNumResultados(Integer numResultados) {
		LOG.info("ENTÃO O NUMERO DE RESULTADOS DEVERA SER {}", numResultados);
	    assertTrue(numResultados == resultados);
	}	
	
	private EstabelecimentoComercial salvarEstabelecimento(EstabelecimentoComercial estabelecimentoComercial) {
		try {
			ResponseEntity<EstabelecimentoComercial> postResponse = restTemplate.postForEntity(endPointURL, estabelecimentoComercial, EstabelecimentoComercial.class);
			HttpStatus status = postResponse.getStatusCode();
			codigoRetorno = status;
			return postResponse.getBody();
		} catch (HttpClientErrorException exception) {
			codigoRetorno = exception.getStatusCode();
			ObjectMapper objectMapper = new ObjectMapper();
			ApiError apiError;
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
		return null;
	}	

}
