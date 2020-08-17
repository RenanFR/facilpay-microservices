/**
 * 
 */
package br.com.facilpay.ecommerce.bdd.steps.ec.cadastro;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

/**
 * @author Renan F Rodrigues
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(
		features = "classpath:features/ec/cadastro_ec.feature",
		monochrome = true,	
		plugin = { "pretty", "html:target/report-html", "json:target/report.json" },
		snippets = SnippetType.CAMELCASE,
		dryRun = false
)
public class CadastroEstabelecimentoCucumberTest {
	
}
