package br.com.facilpay.ecommerce.bdd.steps.mcc;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "classpath:features/segmento/manter_segmento.feature",
		monochrome = true,	
		plugin = { "pretty", "html:target/report-html", "json:target/report.json" },
		snippets = SnippetType.CAMELCASE,
		dryRun = false
)
public class ManterMCCCucumberTest {

}
