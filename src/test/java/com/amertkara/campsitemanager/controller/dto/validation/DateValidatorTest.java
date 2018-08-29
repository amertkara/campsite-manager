package com.amertkara.campsitemanager.controller.dto.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ConstraintValidatorContext;

/**
 * Only testing the empty string case, the remaining logic is already tested in @{@link com.amertkara.campsitemanager.controller.dto.util.DateParser}
 */
public class DateValidatorTest {
	private DateValidator dateValidator;
	@Mock
	private ConstraintValidatorContext constraintValidatorContext;

	@BeforeMethod
	public void beforeMethod() {
		initMocks(this);
		dateValidator = new DateValidator();
	}

	@Test
	public void testIsValid_givenEmptyString_shouldReturnTrue() {
		assertThat(dateValidator.isValid("", constraintValidatorContext)).isTrue();
	}
}