/**
 * 
 */
package br.com.facilpay.ecommerce.bdd.steps.ec.cadastro;

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
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.facilpay.ecommerce.infra.test.CucumberTestUtils;
import br.com.facilpay.shared.domain.EstabelecimentoComercial;
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
	
	@Autowired
	private CucumberTestUtils cucumberTestUtils;
	
	@LocalServerPort
	private Integer serverPort;	
	
	private String endPointURL;
	
	private EstabelecimentoComercial ec;
	
	private HttpStatus codigoRetorno;
	
	@Before
	public void setEndPointURL() {
		endPointURL = "http://localhost:" + serverPort + "/estabelecimentos";
	}
	
	@DataTableType
    public EstabelecimentoComercial ecFromCucumberDataTable(Map<String, String> dataTable) {
        return cucumberTestUtils.ecFromCucumberDataTable(dataTable);
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
		codigoRetorno = cucumberTestUtils.salvarEstabelecimento(endPointURL, ec).getStatusCode();
	}

	@Então("meu retorno sera {int}")
	public void meuRetornoSera(Integer retorno) {
		LOG.info("ENTÃO MEU RETORNO SERA {}", retorno);
		assertEquals(Integer.valueOf(codigoRetorno.value()), retorno);
	}	
	
}

