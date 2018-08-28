package com.amertkara.campsitemanager.exception;

public final class ReservationDoesNotExistException extends GenericCampsiteManagerException {
	private static final long serialVersionUID = -5503670725919298856L;

	public ReservationDoesNotExistException(ErrorPayload errorPayload) {
		super(errorPayload);
	}
}
