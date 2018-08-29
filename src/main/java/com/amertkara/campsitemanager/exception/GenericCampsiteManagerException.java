package com.amertkara.campsitemanager.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GenericCampsiteManagerException extends RuntimeException {
	private static final long serialVersionUID = -1512750686502317882L;

	private final ErrorPayload errorPayload;
}
