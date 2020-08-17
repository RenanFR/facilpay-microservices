package br.com.facilpay.ecommerce.bdd.steps.servico;

import java.util.List;
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
import org.springframework.web.client.RestTemplate;

import br.com.facilpay.shared.domain.ServicoFacilPay;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ManterServicoFeatureSteps {
	
	private static final Logger LOG = LoggerFactory.getLogger(ManterServicoFeatureSteps.class);
	
	@LocalServerPort
	private Integer serverPort;
	
	private String endPointURL;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Before
	public void setEndPointURL() {
		endPointURL = "http://localhost:" + serverPort + "/servicos";
	}	
	
	@DataTableType
    public ServicoFacilPay servicoFromCucumberDataTable(Map<String, String> dataTable) {
		ServicoFacilPay servicoFacilPay = new ServicoFacilPay();
		servicoFacilPay.setNome(dataTable.get("nome"));
		servicoFacilPay.setDescricao(dataTable.get("descricao"));
        return servicoFacilPay;		
    }		
	
	@Dado("o seguinte servico")
	public void oSeguinteServico(ServicoFacilPay servico) {
		LOG.info("DADO O SEGUINTE SERVICO {}", servico.getNome());
	    throw new io.cucumber.java.PendingException();
	}

	@Quando("realizo a chamada para inclusao")
	public void realizoAChamadaParaInclusao() {
		LOG.info("QUANDO REALIZO A CHAMADA PARA INCLUSAO");
	    throw new io.cucumber.java.PendingException();
	}

	@Então("o servico estara disponivel para consulta na base")
	public void oServicoEstaraDisponivelParaConsultaNaBase() {
		LOG.info("ENTÃO O SERVICO ESTARA DISPONIVEL PARA CONSULTA NA BASE");
	    throw new io.cucumber.java.PendingException();
	}		
	
	@Dado("que tenho na base os seguintes servicos")
	public void queTenhoNaBaseOsSeguintesServicos(List<ServicoFacilPay> servicos) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
	    throw new io.cucumber.java.PendingException();
	}

	@Quando("realizo uma busca")
	public void realizoUmaBusca() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Então("serei capaz de visualizar os mesmos")
	public void sereiCapazDeVisualizarOsMesmos() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}	

}
