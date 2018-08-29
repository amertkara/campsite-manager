package com.amertkara.campsitemanager.controller.dto.util;

import static com.amertkara.campsitemanager.exception.ErrorPayload.buildInvalidDateFormatPayload;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

import com.amertkara.campsitemanager.exception.InvalidDateFormatException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Slf4j
public class DateParser {
	public static LocalDate toLocalDate(String date) {
		try {
			return LocalDate.parse(date, ISO_LOCAL_DATE);
		} catch (DateTimeParseException e) {
			log.debug("Error while parsing a date string", e);
			throw new InvalidDateFormatException(buildInvalidDateFormatPayload(date, "yyyy-mm-dd"));
		}
	}
}
