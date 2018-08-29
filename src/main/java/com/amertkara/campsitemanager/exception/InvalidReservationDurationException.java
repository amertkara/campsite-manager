package com.amertkara.campsitemanager.exception;

public class InvalidReservationDurationException extends GenericCampsiteManagerException {
	private static final long serialVersionUID = -1359165061755835179L;

	public InvalidReservationDurationException(ErrorPayload errorPayload) {
		super(errorPayload);
	}
}
