package com.amertkara.campsitemanager.controller.dto.fixture;

import static com.amertkara.campsitemanager.config.ServiceConstants.DATE_FORMAT;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import com.amertkara.campsitemanager.controller.dto.ReservationDTO;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class ReservationDTOFixture {
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

	public static ReservationDTO complete() {
		Date arrivalDate = Date.from(Instant.now());
		ReservationDTO reservationDTO = new ReservationDTO();
		reservationDTO.setFullName(randomAlphabetic(8));
		reservationDTO.setEmail(randomAlphabetic(8));
		reservationDTO.setUuid(randomUUID().toString());
		reservationDTO.setArrivalDate(DATE_FORMATTER.format(arrivalDate));
		reservationDTO.setDepartureDate(DATE_FORMATTER.format(DateUtils.addDays(arrivalDate, 3)));
		reservationDTO.setDuration(3L);
		return reservationDTO;
	}

	public static ReservationDTO basic() {
		Date arrivalDate = DateUtils.addDays(Date.from(Instant.now()), 3);
		ReservationDTO reservationDTO = new ReservationDTO();
		reservationDTO.setFullName(randomAlphabetic(8));
		reservationDTO.setEmail(randomAlphabetic(8));
		reservationDTO.setArrivalDate(DATE_FORMATTER.format(arrivalDate));
		reservationDTO.setDepartureDate(DATE_FORMATTER.format(DateUtils.addDays(arrivalDate, 3)));
		return reservationDTO;
	}
}
