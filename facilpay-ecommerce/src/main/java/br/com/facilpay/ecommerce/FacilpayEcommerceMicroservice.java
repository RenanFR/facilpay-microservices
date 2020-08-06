/**
 * 
 */
package br.com.facilpay.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Renan F Rodrigues
 *
 */

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = { 
		"br.com.facilpay.infra", 
		"br.com.facilpay.ecommerce.*" })
public class FacilpayEcommerceMicroservice {
	
    public static void main(String[] args) {
        SpringApplication.run(FacilpayEcommerceMicroservice.class, args);
    }	
    
}
