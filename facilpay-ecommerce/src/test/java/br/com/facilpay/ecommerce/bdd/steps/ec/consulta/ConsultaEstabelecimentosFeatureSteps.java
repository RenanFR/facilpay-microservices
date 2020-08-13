package br.com.facilpay.ecommerce.bdd.steps.ec.consulta;

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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import br.com.facilpay.ecommerce.entrypoint.rest.EstabelecimentoComercialFilter;
import br.com.facilpay.ecommerce.infra.test.CucumberTestUtils;
import br.com.facilpay.shared.domain.EstabelecimentoComercial;
import br.com.facilpay.shared.models.FacilPayResponse;
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
	
	private String endPointURL;
	
	private int resultados;
	
	private EstabelecimentoComercialFilter filtroPesquisa;	
	
	@Autowired
	private CucumberTestUtils cucumberTestUtils;	
	
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
			cucumberTestUtils.salvarEstabelecimento(endPointURL, ec);
		});
	}

	@Quando("realizo a chamada para pesquisa")
	public void realizoAChamadaParaPesquisa() {
		LOG.info("QUANDO REALIZO A CHAMADA PARA PESQUISA");
		ResponseEntity<FacilPayResponse<EstabelecimentoComercial>> response = cucumberTestUtils.pesquisarEstabelecimentos(endPointURL, filtroPesquisa);
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
	
}
