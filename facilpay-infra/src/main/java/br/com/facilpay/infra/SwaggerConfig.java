package br.com.facilpay.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Value("${apiPackage}")
	private String apiPackage;	
	
	public static final String TAG_ESTABELECIMENTO = "Manutenção de ECs";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(apiPackage))
				.paths(PathSelectors.ant("/**")).build()
				.apiInfo(apiInfo())
				.tags(new Tag(TAG_ESTABELECIMENTO, "DISPONIBILIZA AS OPERAÇÕES DE MANIPULAÇÃO E CONSULTA DOS ESTABELECIMENTOS GERIDOS PELA FÁCIL PAY"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("API - Fácil Pay")
				.description("Documentação dos endpoints da API da Fácil Pay")
				.contact(new Contact("Fácil Pay", "www.facilpay.com.br", "evandro.marchezini@facilpay.com.br"))
				.license("Apache License")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.version("2.0")
				.build();
	}
	
}
