package com.amertkara.campsitemanager.exception;

import static com.amertkara.campsitemanager.config.ServiceConstants.DATE_FORMAT;
import static com.amertkara.campsitemanager.exception.ErrorCode.*;
import static com.amertkara.campsitemanager.exception.ErrorPayload.*;
import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

import com.amertkara.campsitemanager.controller.dto.fixture.ReservationDTOFixture;
import com.google.common.collect.ImmutableList;
import org.testng.annotations.Test;

public class ErrorPayloadTest {

	@Test
	public void testBuildReservationNotFoundPayload_givenReservationUuid_shouldBuildErrorPayload() {
		assertThat(buildReservationNotFoundPayload(randomUUID().toString()).getCode()).isEqualTo(RESERVATION_NOT_FOUND.name());
	}

	@Test
	public void testBuildPayloadNotValidPayload_shouldBuildErrorPayload() {
		assertThat(buildPayloadNotValidPayload().getCode()).isEqualTo(PAYLOAD_NOT_VALID.name());
	}

	@Test
	public void testBuildInvalidDateFormatPayload_givenDateStringAndFormat_shouldBuildErrorPayload() {
		assertThat(ErrorPayload.buildInvalidDateFormatPayload("2018-10-111", DATE_FORMAT).getCode()).isEqualTo(INVALID_DATE_FORMAT.name());
	}

	@Test
	public void testBuildInvalidArrivalDatePayload_shouldBuildErrorPayload() {
		assertThat(buildInvalidArrivalDatePayload().getCode()).isEqualTo(INVALID_ARRIVAL_DATE.name());
	}

	@Test
	public void testBuildInvalidReservationDurationPayload_shouldBuildErrorPayload() {
		assertThat(buildInvalidReservationDurationPayload().getCode()).isEqualTo(INVALID_RESERVATION_DURATION.name());
	}

	@Test
	public void testBuildOverlappingDatesPayload_givenEmptyList__shouldBuildErrorPayload() {
		assertThat(buildOverlappingDatesPayload(emptyList()).getCode()).isEqualTo(OVERLAPPING_DATES.name());
	}

	@Test
	public void testBuildOverlappingDatesPayload_givenOverlappingReservations__shouldBuildErrorPayload() {
		// TODO: This test can be improved with a Condition to match the ErrorPayload.description
		assertThat(buildOverlappingDatesPayload(ImmutableList.of(ReservationDTOFixture.complete(), ReservationDTOFixture.complete())).getCode()).isEqualTo(OVERLAPPING_DATES.name());
	}
}