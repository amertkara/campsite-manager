package com.amertkara.campsitemanager.controller.dto.validation;

import static com.amertkara.campsitemanager.controller.dto.util.DateParser.toLocalDate;
import static com.amertkara.campsitemanager.exception.ErrorPayload.buildInvalidReservationDurationPayload;
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

		LocalDate arrivalDate = toLocalDate(value.getArrivalDate());
		LocalDate departureDate = toLocalDate(value.getDepartureDate());
		long differenceInDays = Duration.between(arrivalDate.atStartOfDay(), departureDate.atStartOfDay()).toDays();

		if (differenceInDays > 3 || differenceInDays <= 0) {
			log.debug("Reservation duration is invalid arrivalDate={} departureDate={}", value.getArrivalDate(), value.getDepartureDate());
			throw new InvalidReservationDurationException(buildInvalidReservationDurationPayload());
		}
		value.setDuration(differenceInDays);
		log.debug("Setting the duration as {}", differenceInDays);
		return true;
	}
}
