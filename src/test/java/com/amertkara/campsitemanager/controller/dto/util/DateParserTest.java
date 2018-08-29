package com.amertkara.campsitemanager.controller.dto.util;

import static com.amertkara.campsitemanager.controller.dto.util.DateParser.toLocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.amertkara.campsitemanager.exception.InvalidDateFormatException;
import org.junit.Test;

import java.time.LocalDate;

public class DateParserTest {

	@Test
	public void testToLocalDate_givenValidDateString_shouldReturnLocalDate() {
		String date = "2018-09-10";

		LocalDate localDate = toLocalDate(date);

		assertThat(localDate.getDayOfMonth()).isEqualTo(10);
		assertThat(localDate.getMonthValue()).isEqualTo(9);
		assertThat(localDate.getYear()).isEqualTo(2018);
	}

	@Test
	public void testToLocalDate_givenInvalidDateString_shouldThrowException() {
		String date = "09-2018-10";

		assertThatExceptionOfType(InvalidDateFormatException.class)
				.isThrownBy(() -> toLocalDate(date));
	}
}