/**
 * 
 */
package br.com.facilpay.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author Renan F Rodrigues
 *
 */

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = { 
		"br.com.facilpay.infra", 
		"br.com.facilpay.ecommerce.*" })
@EnableFeignClients
@EnableResourceServer
public class FacilpayEcommerceMicroservice {
	
    public static void main(String[] args) {
        SpringApplication.run(FacilpayEcommerceMicroservice.class, args);
    }	
    
}
