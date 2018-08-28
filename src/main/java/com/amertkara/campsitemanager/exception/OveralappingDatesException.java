package com.amertkara.campsitemanager.exception;

public final class OveralappingDatesException extends GenericCampsiteManagerException {
	private static final long serialVersionUID = -5503670725919298856L;

	public OveralappingDatesException(ErrorPayload errorPayload) {
		super(errorPayload);
	}
}
