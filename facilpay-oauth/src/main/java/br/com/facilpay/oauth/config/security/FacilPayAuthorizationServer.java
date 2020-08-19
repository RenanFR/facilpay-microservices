package br.com.facilpay.oauth.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import br.com.facilpay.oauth.service.UsuarioService;

@Configuration
public class FacilPayAuthorizationServer extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${spring.jwt.secret}")
	private String jwtSecret;

	@Override
	public void configure(ClientDetailsServiceConfigurer clientConfig) throws Exception {
		clientConfig
			.inMemory()
			.withClient("test‐client")
			.secret(passwordEncoder.encode("test‐secret"))
			.scopes("read", "write")
			.authorizedGrantTypes("password", "refresh_token")
			.accessTokenValiditySeconds(20)
			.refreshTokenValiditySeconds(86400);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpointsConfig) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));

		endpointsConfig
			.tokenStore(jwtTokenStore()).reuseRefreshTokens(false)
			.tokenEnhancer(tokenEnhancerChain)
			.userDetailsService(usuarioService)
			.authenticationManager(authenticationManager);
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtConverter = new JwtAccessTokenConverter();
		jwtConverter.setSigningKey(jwtSecret);
		return jwtConverter;
	}

	@Bean
	public JwtTokenStore jwtTokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new TokenEnhancerCustom();
	}	

}
