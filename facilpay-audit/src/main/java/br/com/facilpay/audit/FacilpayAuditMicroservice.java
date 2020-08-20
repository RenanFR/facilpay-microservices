/**
 * 
 */
package br.com.facilpay.audit;

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
		"br.com.facilpay.audit.*" })
@EnableFeignClients
@EnableResourceServer
public class FacilpayAuditMicroservice {
	
    public static void main(String[] args) {
        SpringApplication.run(FacilpayAuditMicroservice.class, args);
    }	
    
}
