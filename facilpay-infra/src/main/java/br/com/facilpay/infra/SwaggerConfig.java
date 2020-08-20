package br.com.facilpay.infra;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Value("${apiPackage}")
	private String apiPackage;	
	
	public static final String TAG_ESTABELECIMENTO = "Manutenção de ECs";
	public static final String TAG_TRANSACOES = "Transações financeiras";
	public static final String TAG_USUARIOS = "Autenticação e autorização";
	
    @Value("${app.client.id}")
    private String clientId;
    
    @Value("${app.client.secret}")
    private String clientSecret;
	
    @Value("${host.full.dns.auth.link}")
    private String authLink;
    
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(apiPackage))
				.paths(PathSelectors.ant("/**")).build()
				.securitySchemes(List.of(securitySchema()))
                .securityContexts(List.of(securityContext()))
				.apiInfo(apiInfo())
				.tags(new Tag(TAG_ESTABELECIMENTO, "DISPONIBILIZA AS OPERAÇÕES DE MANIPULAÇÃO E CONSULTA DOS ESTABELECIMENTOS GERIDOS PELA FÁCIL PAY"), 
						new Tag(TAG_TRANSACOES, "OPERAÇÕES RELACIONADAS A TRANSAÇÕES FINANCEIRAS"), 
						new Tag(TAG_USUARIOS, "OPERAÇÕES DE GESTÃO DE USUÁRIO"));
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
	
	private List<AuthorizationScope> getAuthorizationScopeList() {
		return List.of(
				new AuthorizationScope("read", "Read all"), 
				new AuthorizationScope("web", "Web scope"), 
				new AuthorizationScope("write", "Access all"));
	}
	
    private OAuth securitySchema() {
        List<GrantType> grantTypes = new ArrayList<>();
        GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(authLink + "/oauth/token");
        grantTypes.add(creGrant);
        return new OAuth("oauth2schema", getAuthorizationScopeList(), grantTypes);
    }
    
    private List<SecurityReference> defaultAuthentication() {
        return List.of(new SecurityReference("oauth2schema", 
        		getAuthorizationScopeList().toArray(new AuthorizationScope[3])));
    }    
    
    private SecurityContext securityContext() {
        return SecurityContext
    			.builder()
    			.securityReferences(defaultAuthentication())
    			.forPaths(PathSelectors.ant("/usuarios/**"))
                .build();
    }    
    
	@SuppressWarnings("deprecation")
	@Bean
    public SecurityConfiguration securityConfig() {
        return new SecurityConfiguration(clientId, clientSecret, "", "", "", ApiKeyVehicle.HEADER, "", "");
    }    
    
}
