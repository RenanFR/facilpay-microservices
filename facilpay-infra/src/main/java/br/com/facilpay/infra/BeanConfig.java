/**
 * 
 */
package br.com.facilpay.infra;

import org.modelmapper.ModelMapper;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

/**
 * @author rnfr
 *
 */

@Configuration
public class BeanConfig {
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	@Bean
	@LoadBalanced
	@Profile("!test")
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	@Profile("test")
	public RestTemplate restTemplateTest() {
		return new RestTemplate();
	}	

}
