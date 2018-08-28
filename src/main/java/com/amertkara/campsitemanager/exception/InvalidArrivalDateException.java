package com.amertkara.campsitemanager.exception;

public class InvalidArrivalDateException extends GenericCampsiteManagerException {
	private static final long serialVersionUID = -2022756858690730221L;

	public InvalidArrivalDateException(ErrorPayload errorPayload) {
		super(errorPayload);
	}
}
