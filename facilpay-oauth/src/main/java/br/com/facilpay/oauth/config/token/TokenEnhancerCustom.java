package br.com.facilpay.oauth.config.token;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import br.com.facilpay.oauth.entities.UsuarioEntity;

public class TokenEnhancerCustom implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		UsuarioEntity usuario = (UsuarioEntity) authentication.getPrincipal();
		Map<String, Object> infoMap = new HashMap<>();
		infoMap.put("usuario", usuario.getLogin());
		DefaultOAuth2AccessToken oauth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
		oauth2AccessToken.setAdditionalInformation(infoMap);
		return oauth2AccessToken;
	}

}
