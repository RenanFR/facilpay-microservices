package br.com.facilpay.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

@Configuration
public class FacilPayResourceServer extends ResourceServerConfigurerAdapter {
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers(
					"/v2/api-docs",
					"/swagger-resources",
					"/swagger-resources/configuration/ui",
					"/swagger-resources/configuration/security",
					"/swagger-ui.html",
					"/actuator/**",
					"/webjars/**",
					"/swagger.json",
					"/oauth/token",
					"/oauth/check_token")
	        .permitAll()
	        .anyRequest()
	        	.authenticated()
        	.and()
        		.csrf()
				.disable()
		 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resourceConfig) throws Exception {
		resourceConfig.stateless(true);
	}
	
	@Bean
	public MethodSecurityExpressionHandler createExpressionHandler() {
		return new OAuth2MethodSecurityExpressionHandler();
	}	

}
