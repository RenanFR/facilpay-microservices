package br.com.facilpay.ecommerce.bdd.steps.ec.inativacao;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
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

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class InativacaoEstabelecimentoFeatureSteps {
	
	private static final Logger LOG = LoggerFactory.getLogger(InativacaoEstabelecimentoFeatureSteps.class);
	
	private String endPointURL;
	
	private EstabelecimentoComercial ec;
	
	@Autowired
	private CucumberTestUtils cucumberTestUtils;
	
	@LocalServerPort
	private Integer serverPort;
	
	@Before
	public void setEndPointURL() {
		endPointURL = "http://localhost:" + serverPort + "/estabelecimentos";
	}
	
	@DataTableType
    public EstabelecimentoComercial ecFromCucumberDataTable(Map<String, String> dataTable) {
        return cucumberTestUtils.ecFromCucumberDataTable(dataTable);
    }	
	
	@Dado("o seguinte ec existente na base")
	public void oSeguinteEcExistenteNaBase(EstabelecimentoComercial estabelecimentoComercial) throws JsonMappingException, JsonProcessingException {
		LOG.info("DADO O SEGUINTE EC EXISTENTE NA BASE {}", estabelecimentoComercial.getRazaoSocial());
		estabelecimentoComercial.setDataInicio(LocalDateTime.now());
		estabelecimentoComercial.setDataFim(LocalDateTime.now().plusYears(1));
		LOG.info("STATUS ATUAL {}", estabelecimentoComercial.getAtivo());
		ec = cucumberTestUtils.salvarEstabelecimento(endPointURL, estabelecimentoComercial).getBody();
	}

	@Quando("realizo a chamada para delecao")
	public void realizoAChamadaParaDelecao() {
		LOG.info("QUANDO REALIZO A CHAMADA PARA DELECAO");
		EstabelecimentoComercial estabelecimentoDelete = cucumberTestUtils.deleteEstabelecimento(endPointURL, ec).getBody();
		ec = estabelecimentoDelete;
	}

	@Então("o status do ec passa a ser inativo")
	public void oStatusDoEcPassaASerInativo() {
		LOG.info("ENTÃO O STATUS DO EC PASSA A SER INATIVO");
		assertFalse(ec.getAtivo());
	}	
	
	@Então("sera criado o historico de auditoria correspondente")
	public void seraCriadoOHistoricoDeAuditoriaCorrespondente() {
		LOG.info("ENTÃO SERA CRIADO O HISTORICO DE AUDITORIA CORRESPONDENTE");
	    assertEquals(cucumberTestUtils.buscarHistoricoRecemCriado(ec.getId()).size(), 2);
	}	
	
}
