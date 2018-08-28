package com.amertkara.campsitemanager.exception;

import static com.amertkara.campsitemanager.exception.ErrorPayload.buildPayloadNotValidPayload;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public final class CampsiteManagerExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler({ReservationDoesNotExistException.class})
	public ResponseEntity<Object> handleNotFoundException(GenericCampsiteManagerException e, WebRequest webRequest) {
		log.debug("Not found exception is thrown exceptionMessage={} errorPayload={}", e.getMessage(), e.getErrorPayload());
		return handleExceptionInternal(e, e.getErrorPayload(), new HttpHeaders(), NOT_FOUND, webRequest);
	}

	@ExceptionHandler({InvalidDateFormatException.class, InvalidArrivalDateException.class, InvalidReservationDurationException.class})
	public ResponseEntity<Object> handleBadRequestException(GenericCampsiteManagerException e, WebRequest webRequest) {
		log.debug("Bad request exception is thrown exceptionMessage={} errorPayload={}", e.getMessage(), e.getErrorPayload());
		return handleExceptionInternal(e, e.getErrorPayload(), new HttpHeaders(), BAD_REQUEST, webRequest);
	}

	/**
	 * Handles validation exceptions thrown for @{@link javax.validation.Valid} annotated payloads in the controller level
	 *
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, buildPayloadNotValidPayload(), headers, status, request);
	}
}