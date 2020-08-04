/**
 * 
 */
package br.com.facilpay.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Renan F Rodrigues
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiFieldError {
	
	private String field;
	
	private String error;

}
