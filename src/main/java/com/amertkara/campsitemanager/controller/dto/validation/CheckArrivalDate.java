package com.amertkara.campsitemanager.controller.dto.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

@Retention(RUNTIME)
@Constraint(validatedBy = ArrivalDateValidator.class)
@Documented
public @interface CheckArrivalDate {
	String message() default "Arrival Date is not valid";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
