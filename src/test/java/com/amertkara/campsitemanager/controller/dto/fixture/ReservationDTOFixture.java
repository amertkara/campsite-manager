package com.amertkara.campsitemanager.controller.dto.fixture;

import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import com.amertkara.campsitemanager.controller.dto.ReservationDTO;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class ReservationDTOFixture {
	public static ReservationDTO complete() {
		Date arrivalDate = Date.from(Instant.now());
		ReservationDTO reservationDTO = new ReservationDTO();
		reservationDTO.setFullName(randomAlphabetic(8));
		reservationDTO.setEmail(randomAlphabetic(8));
		reservationDTO.setUuid(randomUUID().toString());
		reservationDTO.setArrivalDate(new SimpleDateFormat("yyyy-MM-dd").format(arrivalDate));
		reservationDTO.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addDays(arrivalDate, 3)));
		reservationDTO.setDuration(3L);
		return reservationDTO;
	}

	public static ReservationDTO basic() {
		Date arrivalDate = DateUtils.addDays(Date.from(Instant.now()), 3);
		ReservationDTO reservationDTO = new ReservationDTO();
		reservationDTO.setFullName(randomAlphabetic(8));
		reservationDTO.setEmail(randomAlphabetic(8));
		reservationDTO.setArrivalDate(new SimpleDateFormat("yyyy-MM-dd").format(arrivalDate));
		reservationDTO.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addDays(arrivalDate, 3)));
		return reservationDTO;
	}
}
