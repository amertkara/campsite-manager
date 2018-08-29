package com.amertkara.campsitemanager.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import com.amertkara.campsitemanager.service.ReservationService;
import org.apache.commons.lang3.time.DateUtils;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class CampsiteControllerTest {
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

	@Mock
	private ReservationService reservationService;
	private CampsiteController campsiteController;

	@BeforeMethod
	public void beforeMethod() {
		initMocks(this);
		campsiteController = new CampsiteController(reservationService);
	}

	@Test
	public void testGet_givenNoDates_shouldSetOneMonthRange() {
		Date today = Date.from(Instant.now());
		Date oneMonthLater = DateUtils.addDays(today, 30);
		ArgumentCaptor<Date> startDateArgumentCaptor = ArgumentCaptor.forClass(Date.class);
		ArgumentCaptor<Date> endDateArgumentCaptor = ArgumentCaptor.forClass(Date.class);

		campsiteController.get("", "");

		verify(reservationService).getReservations(startDateArgumentCaptor.capture(), endDateArgumentCaptor.capture());
		assertThat(DATE_FORMATTER.format(startDateArgumentCaptor.getValue())).isEqualTo(DATE_FORMATTER.format(today));
		assertThat(DATE_FORMATTER.format(endDateArgumentCaptor.getValue())).isEqualTo(DATE_FORMATTER.format(oneMonthLater));
	}

	@Test
	public void testGet_givenStartDate_shouldSetOneMonthRange() {
		Date today = DateUtils.addDays(Date.from(Instant.now()), 2);
		Date oneMonthLater = DateUtils.addDays(today, 30);
		ArgumentCaptor<Date> startDateArgumentCaptor = ArgumentCaptor.forClass(Date.class);
		ArgumentCaptor<Date> endDateArgumentCaptor = ArgumentCaptor.forClass(Date.class);

		campsiteController.get(DATE_FORMATTER.format(today), "");

		verify(reservationService).getReservations(startDateArgumentCaptor.capture(), endDateArgumentCaptor.capture());
		assertThat(DATE_FORMATTER.format(startDateArgumentCaptor.getValue())).isEqualTo(DATE_FORMATTER.format(today));
		assertThat(DATE_FORMATTER.format(endDateArgumentCaptor.getValue())).isEqualTo(DATE_FORMATTER.format(oneMonthLater));
	}

	@Test
	public void testGet_givenStartDateAndEndDate_shouldSetOne() {
		Date today = DateUtils.addDays(Date.from(Instant.now()), 2);
		Date fifteenDaysLater = DateUtils.addDays(today, 15);
		ArgumentCaptor<Date> startDateArgumentCaptor = ArgumentCaptor.forClass(Date.class);
		ArgumentCaptor<Date> endDateArgumentCaptor = ArgumentCaptor.forClass(Date.class);

		campsiteController.get(DATE_FORMATTER.format(today), DATE_FORMATTER.format(fifteenDaysLater));

		verify(reservationService).getReservations(startDateArgumentCaptor.capture(), endDateArgumentCaptor.capture());
		assertThat(DATE_FORMATTER.format(startDateArgumentCaptor.getValue())).isEqualTo(DATE_FORMATTER.format(today));
		assertThat(DATE_FORMATTER.format(endDateArgumentCaptor.getValue())).isEqualTo(DATE_FORMATTER.format(fifteenDaysLater));
	}
}