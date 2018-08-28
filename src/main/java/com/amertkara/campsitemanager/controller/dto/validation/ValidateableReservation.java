package com.amertkara.campsitemanager.controller.dto.validation;

public interface ValidateableReservation {
	String getArrivalDate();

	String getDepartureDate();

	void setDuration(Long duration);
}
