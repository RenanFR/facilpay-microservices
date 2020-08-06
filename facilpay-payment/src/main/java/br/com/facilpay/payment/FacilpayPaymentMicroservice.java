package br.com.facilpay.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = { 
		"br.com.facilpay.infra", 
		"br.com.facilpay.payment.*" })
@EnableFeignClients(basePackages = { "br.com.facilpay.payment.input.http.adapter" })
public class FacilpayPaymentMicroservice {

	public static void main(String[] args) {
		SpringApplication.run(FacilpayPaymentMicroservice.class, args);
	}

}
