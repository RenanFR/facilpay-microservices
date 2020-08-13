/**
 * 
 */
package br.com.facilpay.shared.models;

import java.util.List;

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
public class FacilPayResponse <T> {
	
	private List<T> content;
	
	private int pageNumber;
	
	private int pageSize;
	
	private int totalPages;
	
	private Long totalElements;
	
	private int numberOfElements;
	
	private Boolean first;
	
	private Boolean last;

}
