/**
 * 
 */
package br.com.facilpay.ecommerce;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.facilpay.ecommerce.infra.entities.EstabelecimentoComercialEntity;
import br.com.facilpay.ecommerce.output.db.adapter.SpringJPAEstabelecimentoComercialRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Renan F Rodrigues
 *
 */

@Slf4j
@ActiveProfiles("test")
@DataJpaTest
@RunWith(SpringRunner.class)
public class EstabelecimentoRepositoryIntegrationTest {
	
    @Autowired
    private TestEntityManager testEntityManager;
    
    @Autowired
    private SpringJPAEstabelecimentoComercialRepository repository;	
    
	@Test
	public void whenFindByRazaoSocial_thenReturnEstabelecimento() {
	    // Given
		EstabelecimentoComercialEntity estabelecimento = new EstabelecimentoComercialEntity();
		estabelecimento.setRazaoSocial("Fácil Pay");
		log.info("SALVANDO EC COM RAZÃO SOCIAL FÁCIL PAY");
		testEntityManager.persist(estabelecimento);
		testEntityManager.flush();
	 
	    // When
		EstabelecimentoComercialEntity estabelecimentoFound = repository.findByRazaoSocial(estabelecimento.getRazaoSocial());
		log.info("REALIZANDO A BUSCA NO REPOSITÓRIO COM BASE NA RAZÃO SOCIAL");
	 
	    // Then
		log.info("VERIFICANDO IGUALDADE DA RAZÃO SOCIAL DO EC ENCONTRADO");
	    assertThat(estabelecimentoFound.getRazaoSocial())
	      .isEqualTo(estabelecimento.getRazaoSocial());
	}	   

}
