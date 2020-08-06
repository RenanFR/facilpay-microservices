/**
 * 
 */
package br.com.facilpay.infra;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	public RestTemplate restTemplateTest() {
		return new RestTemplate();
	}	

}
