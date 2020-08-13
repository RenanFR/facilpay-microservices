package br.com.facilpay.shared.models;

import java.util.List;

public class ResponseMapper<E> {
	
	public FacilPayResponse<E> map(List<E> content, Boolean isFirst, Boolean isLast, int numberOfElements, Long totalElements, 
			int pageNumber, int pageSize, int totalPages) {
		FacilPayResponse<E> response = new FacilPayResponse<>();
		response.setContent(content);
		response.setFirst(isFirst);
		response.setLast(isLast);
		response.setNumberOfElements(numberOfElements);
		response.setTotalElements(totalElements);
		response.setPageNumber(pageNumber);
		response.setPageSize(pageSize);
		response.setTotalPages(totalPages);
		return response;		
	}
	
}
