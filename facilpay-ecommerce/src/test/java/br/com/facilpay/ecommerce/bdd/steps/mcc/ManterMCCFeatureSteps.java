package br.com.facilpay.ecommerce.bdd.steps.mcc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.facilpay.shared.domain.MCC;
import br.com.facilpay.shared.models.FacilPayResponse;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ManterMCCFeatureSteps {
	
	private static final Logger LOG = LoggerFactory.getLogger(ManterMCCFeatureSteps.class);
	
	@LocalServerPort
	private Integer serverPort;
	
	private String endPointURL;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Before
	public void setEndPointURL() {
		endPointURL = "http://localhost:" + serverPort + "/mccs";
	}	
	
	private MCC segmentoEstabelecimento;
	
	private Long segmentoCriadoId;
	
	private Integer mccsCadastrados;
	
	private Integer mccsEncontrados;
	
	@DataTableType
    public MCC segmentoFromCucumberDataTable(Map<String, String> dataTable) {
		MCC segmentoEstabelecimento = new MCC();
		segmentoEstabelecimento.setMccCode(Long.valueOf(dataTable.get("mccCode")));
		segmentoEstabelecimento.setNome(dataTable.get("nome"));
		segmentoEstabelecimento.setDescricao(dataTable.get("descricao"));
        return segmentoEstabelecimento;		
    }	
	
	@Dado("o seguinte segmento")
	public void oSeguinteSegmento(MCC mcc) {
		LOG.info("DADO O SEGUINTE SEGMENTO {}", mcc.getNome());
	    segmentoEstabelecimento = mcc;
	}

	@Quando("realizo a chamada para inclusao")
	public void realizoAChamadaParaInclusao() {
		LOG.info("QUANDO REALIZO A CHAMADA PARA INCLUSAO");
		HttpEntity<MCC> request = new HttpEntity<MCC>(segmentoEstabelecimento);
		ResponseEntity<MCC> responseMCC = restTemplate.postForEntity(endPointURL, request, MCC.class);
		segmentoCriadoId = responseMCC.getBody().getMccCode();
	}

	@Então("o segmento estara disponivel para consulta na base")
	public void oSegmentoEstaraDisponivelParaConsultaNaBase() {
		LOG.info("ENTÃO O SEGMENTO ESTARA DISPONIVEL PARA CONSULTA NA BASE");
		assertNotNull(segmentoCriadoId);
	}

	@Dado("que tenho na base os seguintes segmentos de atuacao")
	public void queTenhoNaBaseOsSeguintesSegmentosDeAtuacao(List<MCC> mccs) {
		mccsCadastrados = mccs.size();
		LOG.info("DADO QUE TENHO NA BASE {} SEGMENTOS DE ATUACAO", mccsCadastrados);
		mccs.forEach(mcc -> {
			restTemplate.postForEntity(endPointURL, new HttpEntity<MCC>(mcc), MCC.class);
		});
	}

	@Quando("realizo uma busca")
	public void realizoUmaBusca() {
		LOG.info("QUANDO REALIZO UMA BUSCA");
		ResponseEntity<FacilPayResponse<MCC>> response = restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(endPointURL).toUriString(), 
				HttpMethod.GET, null, new ParameterizedTypeReference<FacilPayResponse<MCC>>() {});
		mccsEncontrados = response.getBody().getNumberOfElements();
	}

	@Então("serei capaz de visualizar os mesmos")
	public void sereiCapazDeVisualizarOsMesmos() {
		LOG.info("ENTÃO SEREI CAPAZ DE VISUALIZAR OS MESMOS");
	    assertTrue(mccsEncontrados >= mccsCadastrados);
	}
	

}
