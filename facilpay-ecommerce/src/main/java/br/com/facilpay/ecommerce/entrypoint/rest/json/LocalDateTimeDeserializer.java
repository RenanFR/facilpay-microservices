/**
 * 
 */
package br.com.facilpay.ecommerce.entrypoint.rest.json;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author Renan F Rodrigues
 *
 */

@Component
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

	@Override
	public LocalDateTime deserialize(JsonParser json, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		try {
			return LocalDate.parse(json.getValueAsString(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
		} catch (DateTimeParseException e) {
			return LocalDateTime.parse(json.getValueAsString());
		}
	}

}
