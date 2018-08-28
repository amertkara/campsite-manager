package com.amertkara.campsitemanager.controller.dto.validation;

import static com.amertkara.campsitemanager.exception.ErrorPayload.buildInvalidReservationDurationPayload;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.apache.commons.lang3.StringUtils.isAnyBlank;

import com.amertkara.campsitemanager.exception.InvalidReservationDurationException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;
import java.time.LocalDate;

@Slf4j
public class ReservationDurationValidator implements ConstraintValidator<CheckReservationDuration, ValidateableReservation> {
	@Override
	public boolean isValid(ValidateableReservation value, ConstraintValidatorContext context) {
		if (isAnyBlank(value.getArrivalDate(), value.getDepartureDate())) {
			return true;
		}

		LocalDate arrivalDate = parse(value.getArrivalDate(), ISO_LOCAL_DATE);
		LocalDate departureDate = parse(value.getDepartureDate(), ISO_LOCAL_DATE);

		if (Duration.between(departureDate.atStartOfDay(), arrivalDate.atStartOfDay()).toDays() > 3) {
			log.debug("Reservation duration is invalid arrivalDate={} departureDate={}", value.getArrivalDate(), value.getDepartureDate());
			throw new InvalidReservationDurationException(buildInvalidReservationDurationPayload());
		}

		return true;
	}
}
