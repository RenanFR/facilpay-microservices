package br.com.facilpay.oauth.config.token;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;

public class RefreshTokenRequestUpdate extends HttpServletRequestWrapper {
	
	String refreshToken;
	
	public RefreshTokenRequestUpdate(HttpServletRequest httpRequest, String refreshToken) {
		super(httpRequest);
		this.refreshToken = refreshToken;
	}
	
	@Override
	public Map<String, String[]> getParameterMap() {
		ParameterMap<String, String[]> paramMap = new ParameterMap<>(getRequest().getParameterMap());
		paramMap.put("refresh_token", new String[] { refreshToken });
		paramMap.setLocked(true);
		return paramMap;
	}	

}
