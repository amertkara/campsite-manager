package com.amertkara.campsitemanager.controller.dto.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.MockitoAnnotations.initMocks;

import com.amertkara.campsitemanager.controller.dto.ReservationDTO;
import com.amertkara.campsitemanager.controller.dto.fixture.ReservationDTOFixture;
import com.amertkara.campsitemanager.exception.InvalidReservationDurationException;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ConstraintValidatorContext;

public class ReservationDurationValidatorTest {
	private ReservationDurationValidator reservationDurationValidator;
	@Mock
	private ConstraintValidatorContext constraintValidatorContext;
	private ReservationDTO validateableReservation;

	@BeforeMethod
	public void beforeMethod() {
		initMocks(this);
		reservationDurationValidator = new ReservationDurationValidator();
		validateableReservation = ReservationDTOFixture.complete();
	}

	@Test
	public void testIsValid_givenArrivalDate_shouldReturnTrue() {
		validateableReservation.setArrivalDate("");
		assertThat(reservationDurationValidator.isValid(validateableReservation, constraintValidatorContext)).isTrue();
	}

	@Test
	public void testIsValid_givenDepartureDate_shouldReturnTrue() {
		validateableReservation.setDepartureDate("");
		assertThat(reservationDurationValidator.isValid(validateableReservation, constraintValidatorContext)).isTrue();
	}

	@Test
	public void testIsValid_givenBothDates_shouldReturnTrue() {
		validateableReservation.setArrivalDate("");
		validateableReservation.setDepartureDate("");
		assertThat(reservationDurationValidator.isValid(validateableReservation, constraintValidatorContext)).isTrue();
	}

	@Test
	public void testIsValid_givenProperDates_shouldReturnTrue() {
		validateableReservation.setArrivalDate("2018-09-10");
		validateableReservation.setDepartureDate("2018-09-11");

		assertThat(reservationDurationValidator.isValid(validateableReservation, constraintValidatorContext)).isTrue();
		assertThat(validateableReservation.getDuration()).isEqualTo(1);
	}

	@Test
	public void testIsValid_givenMoreThanThreeDaysDuration_shouldThrowException() {
		validateableReservation.setArrivalDate("2018-09-10");
		validateableReservation.setDepartureDate("2018-09-14");

		assertThatExceptionOfType(InvalidReservationDurationException.class)
				.isThrownBy(() -> reservationDurationValidator.isValid(validateableReservation, constraintValidatorContext));
	}

	@Test
	public void testIsValid_givenZeroDaysDuration_shouldThrowException() {
		validateableReservation.setArrivalDate("2018-09-10");
		validateableReservation.setDepartureDate("2018-09-10");

		assertThatExceptionOfType(InvalidReservationDurationException.class)
				.isThrownBy(() -> reservationDurationValidator.isValid(validateableReservation, constraintValidatorContext));
	}
}