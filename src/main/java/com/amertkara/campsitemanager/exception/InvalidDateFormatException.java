package com.amertkara.campsitemanager.exception;

public class InvalidDateFormatException extends GenericCampsiteManagerException {
	private static final long serialVersionUID = -2022756858690730221L;

	public InvalidDateFormatException(ErrorPayload errorPayload) {
		super(errorPayload);
	}
}
