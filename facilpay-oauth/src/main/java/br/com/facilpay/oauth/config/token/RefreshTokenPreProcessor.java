package br.com.facilpay.oauth.config.token;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class RefreshTokenPreProcessor implements Filter {
	
	private static final String GRANT_TYPE = "grant_type";
	private static final String REFRESH_TOKEN = "refresh_token";
	private static final String OAUTH_TOKEN = "oauth/token";
	
	private HttpServletRequest httpRequest;	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		httpRequest = (HttpServletRequest) request;
		if(REFRESH_TOKEN.equals(httpRequest.getParameter(GRANT_TYPE))
				&& OAUTH_TOKEN.equalsIgnoreCase(httpRequest.getRequestURI())
					&& httpRequest.getCookies() != null) {
			Optional<String> refreshTokenCookie = Stream.of(httpRequest.getCookies())
				  .filter(cookie -> REFRESH_TOKEN.equals(cookie.getName()))
				  .findFirst()
				  .map(Cookie::getValue);
			refreshTokenCookie
				.ifPresent(refreshToken -> { httpRequest = new RefreshTokenRequestUpdate(httpRequest, refreshToken); });
		}
		chain.doFilter(httpRequest, response);
		
	}

}
