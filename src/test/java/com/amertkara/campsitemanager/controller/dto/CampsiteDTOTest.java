package com.amertkara.campsitemanager.controller.dto;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

import com.amertkara.campsitemanager.controller.dto.fixture.ReservationDTOFixture;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.time.DateUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class CampsiteDTOTest {
	private CampsiteDTO campsiteDTO;
	private List<ReservationDTO> existingReservations;

	@BeforeMethod
	public void beforeMethod() {
		campsiteDTO = new CampsiteDTO();
		existingReservations = ImmutableList.of(ReservationDTOFixture.complete(), ReservationDTOFixture.complete());
	}

	@Test
	public void testSetUnavailableDates_givenReservationsList_shouldPopulateUnavailableList() {
		campsiteDTO.setUnavailableDates(existingReservations);

		assertThat(campsiteDTO.getUnavailableDates()).hasSize(existingReservations.size());
	}

	@Test
	public void testSetUnavailableDates_givenEmptyList_shouldNotPopulateUnavailableList() {
		campsiteDTO.setUnavailableDates(emptyList());

		assertThat(campsiteDTO.getUnavailableDates()).isEmpty();
	}

	@Test
	public void testSetAvailability_givenDateRangeAndReservations_shouldSetToAvailable() {
		Date startDate = Date.from(Instant.now());
		Date endDate = DateUtils.addDays(startDate, 15);
		Long reservedDays = existingReservations.stream().mapToLong(ReservationDTO::getDuration).sum();

		campsiteDTO.setAvailability(startDate, endDate, existingReservations);

		assertThat(campsiteDTO.isAvailable()).isTrue();
		assertThat(campsiteDTO.getAvailableDays()).isEqualTo(15 - reservedDays);
	}

	@Test
	public void testSetAvailability_givenDateRangeAndReservations_shouldSetToNotAvailable() {
		Long reservedDays = existingReservations.stream().mapToLong(ReservationDTO::getDuration).sum();
		Date startDate = Date.from(Instant.now());
		Date endDate = DateUtils.addDays(startDate, reservedDays.intValue());

		campsiteDTO.setAvailability(startDate, endDate, existingReservations);

		assertThat(campsiteDTO.isAvailable()).isFalse();
		assertThat(campsiteDTO.getAvailableDays()).isEqualTo(0);
	}

	@Test
	public void testSetAvailability_givenNoReservations_shouldSetToAvailable() {
		Date startDate = Date.from(Instant.now());
		Date endDate = DateUtils.addDays(startDate, 15);

		campsiteDTO.setAvailability(startDate, endDate, emptyList());

		assertThat(campsiteDTO.isAvailable()).isTrue();
		assertThat(campsiteDTO.getAvailableDays()).isEqualTo(15);
	}
}