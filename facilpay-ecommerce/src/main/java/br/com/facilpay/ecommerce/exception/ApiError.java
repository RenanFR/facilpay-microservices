package br.com.facilpay.ecommerce.exception;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.facilpay.shared.json.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime dataHora;
	
	private String error;
	
	private String message;
	
	private String requestedPath;
	
	@Builder.Default
	private List<ApiFieldError> fieldsErrors = new ArrayList<>();

}
