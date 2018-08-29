package com.amertkara.campsitemanager.exception;

public final class OverlappingDatesException extends GenericCampsiteManagerException {
	private static final long serialVersionUID = 2762476493180975692L;

	public OverlappingDatesException(ErrorPayload errorPayload) {
		super(errorPayload);
	}
}
