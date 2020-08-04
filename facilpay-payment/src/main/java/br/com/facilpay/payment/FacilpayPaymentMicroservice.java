package br.com.facilpay.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { 
		"br.com.facilpay.infra", 
		"br.com.facilpay.payment.*" })
public class FacilpayPaymentMicroservice {

	public static void main(String[] args) {
		SpringApplication.run(FacilpayPaymentMicroservice.class, args);
	}

}
