package com.amertkara.campsitemanager.controller.dto.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.MockitoAnnotations.initMocks;

import com.amertkara.campsitemanager.exception.InvalidArrivalDateException;
import org.apache.commons.lang3.time.DateUtils;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class ArrivalDateValidatorTest {
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	private ArrivalDateValidator arrivalDateValidator;
	@Mock
	private ConstraintValidatorContext constraintValidatorContext;
	private Date today;

	@BeforeMethod
	public void beforeMethod() {
		initMocks(this);
		arrivalDateValidator = new ArrivalDateValidator();
		today = Date.from(Instant.now());
	}

	@Test
	public void testIsValid_givenEmptyString_shouldReturnTrue() {
		assertThat(arrivalDateValidator.isValid("", constraintValidatorContext)).isTrue();
	}

	@Test
	public void testIsValid_givenDateBeforeToday_shouldThrowException() {
		Date yesterday = DateUtils.addDays(today, -1);

		assertThatExceptionOfType(InvalidArrivalDateException.class)
				.isThrownBy(() -> arrivalDateValidator.isValid(DATE_FORMATTER.format(yesterday), constraintValidatorContext));
	}

	@Test
	public void testIsValid_givenDateMoreThan30DaysLater_shouldThrowException() {
		Date dateMoreThan30DaysLater = DateUtils.addDays(today, 35);

		assertThatExceptionOfType(InvalidArrivalDateException.class)
				.isThrownBy(() -> arrivalDateValidator.isValid(DATE_FORMATTER.format(dateMoreThan30DaysLater), constraintValidatorContext));
	}

	@Test
	public void testIsValid_givenValid_shouldReturnTrue() {
		Date validDate = DateUtils.addDays(today, 30);

		assertThat(arrivalDateValidator.isValid(DATE_FORMATTER.format(validDate), constraintValidatorContext)).isTrue();
	}

	@Test
	public void testIsValid_givenTomorrow_shouldThrowException() {
		Date tomorrow = DateUtils.addDays(today, 1);

		assertThatExceptionOfType(InvalidArrivalDateException.class)
				.isThrownBy(() -> arrivalDateValidator.isValid(DATE_FORMATTER.format(tomorrow), constraintValidatorContext));
	}

	@Test
	public void testIsValid_givenMoreThanOneDayLater_shouldReturnTrue() {
		Date validDate = DateUtils.addDays(today, 2);

		assertThat(arrivalDateValidator.isValid(DATE_FORMATTER.format(validDate), constraintValidatorContext)).isTrue();
	}
}