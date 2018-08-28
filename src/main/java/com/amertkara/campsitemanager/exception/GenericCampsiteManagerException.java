package com.amertkara.campsitemanager.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GenericCampsiteManagerException extends RuntimeException {
	private static final long serialVersionUID = -6615349998770278464L;

	private final ErrorPayload errorPayload;
}
