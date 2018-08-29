package com.amertkara.campsitemanager.controller.dto.validation;

import static com.amertkara.campsitemanager.controller.dto.util.DateParser.toLocalDate;
import static org.apache.logging.log4j.util.Strings.isBlank;

import com.amertkara.campsitemanager.exception.InvalidArrivalDateException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class DateValidator implements ConstraintValidator<CheckDate, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) throws InvalidArrivalDateException {
		if (isBlank(value)) {
			return true;
		}
		toLocalDate(value);
		return true;
	}
}
