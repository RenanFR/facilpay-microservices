package br.com.facilpay.oauth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties("oauth-token-api")
public class OAuthTokenProperties {
	
	SecurityProperties securityProp = new SecurityProperties();

}
