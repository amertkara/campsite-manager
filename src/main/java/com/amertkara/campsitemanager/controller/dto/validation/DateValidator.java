package com.amertkara.campsitemanager.controller.dto.validation;

import static com.amertkara.campsitemanager.exception.ErrorPayload.buildInvalidDateFormatPayload;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.apache.logging.log4j.util.Strings.isBlank;

import com.amertkara.campsitemanager.exception.InvalidArrivalDateException;
import com.amertkara.campsitemanager.exception.InvalidDateFormatException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Slf4j
public class DateValidator implements ConstraintValidator<CheckDate, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) throws InvalidArrivalDateException {
		if (isBlank(value)) {
			return true;
		}
		try {
			LocalDate.parse(value, ISO_LOCAL_DATE);
		} catch (DateTimeParseException e) {
			log.debug("Error while parsing a date string", e);
			throw new InvalidDateFormatException(buildInvalidDateFormatPayload(value, "yyyy-mm-dd"));
		}

		return true;
	}
}
