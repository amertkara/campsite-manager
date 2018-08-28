package com.amertkara.campsitemanager.controller.dto.validation;

import static com.amertkara.campsitemanager.exception.ErrorPayload.buildInvalidArrivalDatePayload;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.apache.logging.log4j.util.Strings.isBlank;

import com.amertkara.campsitemanager.exception.InvalidArrivalDateException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
public class ArrivalDateValidator implements ConstraintValidator<CheckArrivalDate, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) throws InvalidArrivalDateException {
		if (isBlank(value)) {
			return true;
		}

		long diffFromToday = Duration.between(LocalDate.now().atTime(LocalTime.now()), LocalDate.parse(value, ISO_LOCAL_DATE).atStartOfDay()).toDays();
		if (diffFromToday < 1L || diffFromToday > 30L) {
			log.debug("Invalid Arrival date is provided arrivalDate={}", value);
			throw new InvalidArrivalDateException(buildInvalidArrivalDatePayload());
		}

		return true;
	}
}
