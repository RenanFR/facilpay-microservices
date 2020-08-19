package br.com.facilpay.oauth.config.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import br.com.facilpay.oauth.properties.OAuthTokenProperties;

public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {
	
	@Autowired
	private OAuthTokenProperties oauthTokenProperty;	

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		HttpServletRequest httpRequest = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse httpResponse = ((ServletServerHttpResponse) response).getServletResponse();
		addRefreshTokenInCookie(body, httpRequest, httpResponse);
		removeRefreshTokenInResponse(body);
		return body;
	}

	private void removeRefreshTokenInResponse(OAuth2AccessToken body) {
		DefaultOAuth2AccessToken accessToken = (DefaultOAuth2AccessToken) body;
		accessToken.setRefreshToken(null);
	}
	
	private void addRefreshTokenInCookie(OAuth2AccessToken body, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		Cookie cookie = new Cookie("refresh_token", body.getRefreshToken().getValue());
		cookie.setPath(httpServletRequest.getContextPath() + "/oauth/token");
		cookie.setHttpOnly(true);
		cookie.setSecure(oauthTokenProperty.getSecurityProp().isEnableHttps());
		cookie.setMaxAge(3600 * 24);
		httpServletResponse.addCookie(cookie);
	}

}
