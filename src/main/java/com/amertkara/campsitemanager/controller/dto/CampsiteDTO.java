package com.amertkara.campsitemanager.controller.dto;

import static java.lang.String.format;

import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class CampsiteDTO {
	private boolean isAvailable;
	private Long availableDays;
	private List<String> unavailableDates;

	public void setUnavailableDates(List<ReservationDTO> existingReservations) {
		unavailableDates = existingReservations.stream()
				.map(reservationDTO -> format("(%s,%s)", reservationDTO.getArrivalDate(), reservationDTO.getDepartureDate()))
				.collect(Collectors.toList());
	}

	public void setAvailability(Date startDate, Date endDate, List<ReservationDTO> existingReservations) {
		LocalDate durationStart = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate durationEnd = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		long differenceInDays = Duration.between(durationStart.atStartOfDay(), durationEnd.atStartOfDay()).toDays();
		long reservedDays = existingReservations.stream().mapToLong(ReservationDTO::getDuration).sum();
		long availableDays = differenceInDays - reservedDays;
		if (availableDays > 0) {
			isAvailable = true;
			this.availableDays = availableDays;
		} else {
			this.availableDays = 0L;
		}
	}
}
