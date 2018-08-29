package com.amertkara.campsitemanager.exception;

public class InvalidArrivalDateException extends GenericCampsiteManagerException {
	private static final long serialVersionUID = -3792445422701703242L;

	public InvalidArrivalDateException(ErrorPayload errorPayload) {
		super(errorPayload);
	}
}
