/**
 * 
 */
package br.com.facilpay.audit;

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
		"br.com.facilpay.audit.*" })
public class FacilpayAuditMicroservice {
	
    public static void main(String[] args) {
        SpringApplication.run(FacilpayAuditMicroservice.class, args);
    }	
    
}
