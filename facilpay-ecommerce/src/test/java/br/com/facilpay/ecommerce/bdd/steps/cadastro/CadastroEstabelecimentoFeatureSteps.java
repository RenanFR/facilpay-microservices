/**
 * 
 */
package br.com.facilpay.ecommerce.bdd.steps.cadastro;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.facilpay.ecommerce.exception.ApiError;
import br.com.facilpay.shared.domain.EstabelecimentoComercial;
import br.com.facilpay.shared.models.Contato;
import br.com.facilpay.shared.models.Endereco;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

/**
 * @author Renan F Rodrigues
 *
 */


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CadastroEstabelecimentoFeatureSteps {
	
	private static final Logger LOG = LoggerFactory.getLogger(CadastroEstabelecimentoFeatureSteps.class);
	
	@LocalServerPort
	private Integer serverPort;	
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String endPointURL;
	
	private EstabelecimentoComercial ec;
	
	private HttpStatus codigoRetorno;
	
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
		Endereco endereco = new Endereco(dataTable.get("cep"), dataTable.get("LOGradouro"), dataTable.get("numero"), dataTable.get("bairro"), dataTable.get("cidade"), dataTable.get("estado"));
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
		LOG.info("DADO UM NOVO EC {}", estabelecimentoComercial.getRazaoSocial());
		ec = estabelecimentoComercial;
	}

	@Quando("adiciono um ec com o cnpj {string}")
	public void adicionoUmEcComOCnpj(String cnpj) throws JsonMappingException, JsonProcessingException {
		LOG.info("QUANDO ADICIONO UM EC COM O CNPJ {}", cnpj);
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
	
	@Então("meu retorno sera {int}")
	public void meuRetornoSera(Integer retorno) {
		LOG.info("ENTÃO MEU RETORNO SERA {}", retorno);
		assertEquals(Integer.valueOf(codigoRetorno.value()), retorno);
	}	
	
}

