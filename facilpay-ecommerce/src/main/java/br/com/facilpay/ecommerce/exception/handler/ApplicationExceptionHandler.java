package br.com.facilpay.ecommerce.exception.handler;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.facilpay.ecommerce.exception.ApiError;
import br.com.facilpay.ecommerce.exception.ApiFieldError;
import br.com.facilpay.shared.exception.EntidadeNaoEncontradaException;

@RestControllerAdvice
public class ApplicationExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(ApplicationExceptionHandler.class);
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler({ EntidadeNaoEncontradaException.class })
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException exception, HttpServletRequest httpRequest) {

		String userMessage = "NÃO FOI POSSÍVEL ENCONTRAR REGISTROS NA BASE DE DADOS COM OS CRITÉRIOS INFORMADOS";
		String devMessage = exception.getMessage();
		
		List<ApiError> errors = List.of(ApiError
				.builder()
				.dataHora(LocalDateTime.now())
				.error(devMessage)
				.message(userMessage)
				.requestedPath(httpRequest.getRequestURI())
				.build());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<?> handleBeanValidation(MethodArgumentNotValidException exception, HttpServletRequest httpRequest) {
		
		ApiError apiError = ApiError
				.builder()
				.dataHora(LocalDateTime.now())
				.error(exception.getMessage())
				.message("FORAM ENCONTRADOS ERROS DE VALIDAÇÃO NOS CAMPOS DO OBJETO ENVIADO")
				.requestedPath(httpRequest.getRequestURI())
				.build();
		
		exception.getBindingResult().getFieldErrors().forEach(error -> {
			apiError.getFieldsErrors().add(new ApiFieldError(error.getField(), error.getDefaultMessage()));
		});
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<?> handleGeneric(RuntimeException exception, HttpServletRequest httpRequest) {
		
		String userMessage = "ERRO NO PROCESSAMENTO DA REQUISIÇÃO (BACKEND)";
		LOG.error("ERRO: {}", exception.getClass().getCanonicalName());
		exception.printStackTrace();
		String devMessage = exception.toString();
		
		List<ApiError> errors = List.of(ApiError
				.builder()
				.dataHora(LocalDateTime.now())
				.error(devMessage)
				.message(userMessage)
				.requestedPath(httpRequest.getRequestURI())
				.build());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
	}	
	
}
