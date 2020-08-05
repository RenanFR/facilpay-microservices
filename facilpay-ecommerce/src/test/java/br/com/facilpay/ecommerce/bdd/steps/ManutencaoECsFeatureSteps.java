/**
 * 
 */
package br.com.facilpay.ecommerce.bdd.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.facilpay.ecommerce.entrypoint.rest.EstabelecimentoComercialFilter;
import br.com.facilpay.ecommerce.entrypoint.rest.FacilPayResponse;
import br.com.facilpay.ecommerce.exception.ApiError;
import br.com.facilpay.shared.domain.EstabelecimentoComercial;
import br.com.facilpay.shared.models.Contato;
import br.com.facilpay.shared.models.Endereco;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Renan F Rodrigues
 *
 */

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ManutencaoECsFeatureSteps {
	
	@LocalServerPort
	private Integer serverPort;	
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String endPointURL;
	
	private EstabelecimentoComercial ec;
	
	private HttpStatus codigoRetorno;
	
	private int resultados;
	
	private EstabelecimentoComercialFilter filtroPesquisa;
	
	@Before
	public void setEndPointURL() {
		endPointURL = "http://localhost:" + serverPort + "/estabelecimentos";
	}
	
	@DataTableType
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
	
	@Dado("um novo ec")
	public void umNovoEc(EstabelecimentoComercial estabelecimentoComercial) {
		log.info("DADO UM NOVO EC {}", estabelecimentoComercial.getRazaoSocial());
		ec = estabelecimentoComercial;
	}

	@Quando("adiciono um ec com o cnpj {string}")
	public void adicionoUmEcComOCnpj(String cnpj) throws JsonMappingException, JsonProcessingException {
		log.info("QUANDO ADICIONO UM EC COM O CNPJ {}", cnpj);
		ec.setCnpj(cnpj);
		salvarEstabelecimento(ec);
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
				log.error(apiError.getMessage());
				log.error(apiError.getError());
				apiError
				.getFieldsErrors()
				.forEach(err -> {
					log.error(err.getField() + " : " + err.getError());
				});
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return null;
	}
	
	@Então("meu retorno sera {int}")
	public void meuRetornoSera(Integer retorno) {
		log.info("ENTÃO MEU RETORNO SERA {}", retorno);
		assertEquals(Integer.valueOf(codigoRetorno.value()), retorno);
	}	
	
	@Dado("o seguinte ec existente na base")
	public void oSeguinteEcExistenteNaBase(EstabelecimentoComercial estabelecimentoComercial) throws JsonMappingException, JsonProcessingException {
		log.info("DADO O SEGUINTE EC EXISTENTE NA BASE {}", estabelecimentoComercial.getRazaoSocial());
		ec = salvarEstabelecimento(estabelecimentoComercial);
	}

	@Quando("realizo a chamada para delecao")
	public void realizoAChamadaParaDelecao() {
		log.info("QUANDO REALIZO A CHAMADA PARA DELECAO");
		EstabelecimentoComercial estabelecimentoDelete = restTemplate.exchange(endPointURL + "/" + ec.getId(), HttpMethod.DELETE, null, EstabelecimentoComercial.class).getBody();
		ec = estabelecimentoDelete;
	}

	@Então("o status do ec passa a ser inativo")
	public void oStatusDoEcPassaASerInativo() {
		log.info("ENTÃO O STATUS DO EC PASSA A SER INATIVO");
		assertFalse(ec.getAtivo());
		
	}	
	
	@Dado("que desejo encontrar um ou mais ecs por meio dos atributos {string}, {string}, {string}, {string} e ou {string}")
	public void queDesejoEncontrarUmOuMaisEcsPorMeioDosAtributosEOu(String cnpj, String cpf, String inscricaoEstadual, String razaoSocial, String nomeFantasia) {
		log.info("DADO QUE DESEJO ENCONTRAR UM OU MAIS ECS POR MEIO DOS ATRIBUTOS {}, {}, {}, {} E OU {}", cnpj, cpf, inscricaoEstadual, razaoSocial, nomeFantasia);
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
		log.info("DADO QUE TENHO {} REGISTROS DE TESTE", estabelecimentosTeste.size());
		estabelecimentosTeste.forEach(ec -> {
			salvarEstabelecimento(ec);
		});
	}

	@Quando("realizo a chamada para pesquisa")
	public void realizoAChamadaParaPesquisa() {
		log.info("QUANDO REALIZO A CHAMADA PARA PESQUISA");
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
	    log.info(estabelecimentos.toString());
		resultados = estabelecimentos.size();
	    estabelecimentos.forEach(estabelecimento -> {
	    	log.info("FOI ENCONTRADO O ESTABELECIMENTO {}", estabelecimento.getRazaoSocial());
	    	
	    });
	    log.info("O NÚMERO DE RESULTADOS FOI {}", resultados);
	}

	@Então("o numero de resultados devera ser {int}")
	public void oNumeroDeResultadosDeveraSerNumResultados(Integer numResultados) {
		log.info("ENTÃO O NUMERO DE RESULTADOS DEVERA SER {}", numResultados);
	    assertTrue(numResultados == resultados);
	}

}

