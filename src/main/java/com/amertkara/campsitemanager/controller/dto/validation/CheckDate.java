package com.amertkara.campsitemanager.controller.dto.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

@Retention(RUNTIME)
@Constraint(validatedBy = DateValidator.class)
@Documented
public @interface CheckDate {
	String message() default "Date is not valid";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
