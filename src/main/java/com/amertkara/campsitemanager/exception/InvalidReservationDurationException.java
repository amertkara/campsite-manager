package com.amertkara.campsitemanager.exception;

public class InvalidReservationDurationException extends GenericCampsiteManagerException {
	private static final long serialVersionUID = -2022756858690730221L;

	public InvalidReservationDurationException(ErrorPayload errorPayload) {
		super(errorPayload);
	}
}
