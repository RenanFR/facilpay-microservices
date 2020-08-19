package br.com.facilpay.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = { 
		"br.com.facilpay.infra", 
		"br.com.facilpay.oauth.*" })
@EnableResourceServer
@EnableAuthorizationServer
public class FacilPayOAuthMicroservice {
	
    public static void main(String[] args) {
        SpringApplication.run(FacilPayOAuthMicroservice.class, args);
    }		

}
