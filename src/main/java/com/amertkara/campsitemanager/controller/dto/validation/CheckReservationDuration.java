package com.amertkara.campsitemanager.controller.dto.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

@Retention(RUNTIME)
@Constraint(validatedBy = ReservationDurationValidator.class)
@Documented
public @interface CheckReservationDuration {
	String message() default "Duration is not valid";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
