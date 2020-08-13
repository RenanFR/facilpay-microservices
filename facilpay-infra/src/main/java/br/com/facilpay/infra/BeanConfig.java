/**
 * 
 */
package br.com.facilpay.infra;

import org.modelmapper.ModelMapper;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author rnfr
 *
 */

@Configuration
@EnableFeignClients(basePackages = { "br.com.facilpay.infra.output.http.adapter" })
public class BeanConfig {
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	@Bean
	@Primary
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public ObjectMapper objectMapper() {
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	    objectMapper.registerModule(new JavaTimeModule());
	    return objectMapper;
	}	

}
