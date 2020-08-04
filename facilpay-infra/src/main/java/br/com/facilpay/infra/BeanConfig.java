/**
 * 
 */
package br.com.facilpay.infra;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
