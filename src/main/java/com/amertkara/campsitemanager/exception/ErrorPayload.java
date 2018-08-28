package com.amertkara.campsitemanager.exception;

import static com.amertkara.campsitemanager.exception.ErrorCode.*;
import static java.lang.String.format;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Builder
@RequiredArgsConstructor
@Getter
@ToString
public final class ErrorPayload {
	@JsonProperty("errorCode")
	private final String code;
	private final String description;

	public static ErrorPayload buildReservationNotFoundPayload(String reservationUuid) {
		return builder().description(format("Reservation with id=%s does not exist", reservationUuid)).code(RESERVATION_NOT_FOUND.name()).build();
	}

	public static ErrorPayload buildPayloadNotValidPayload() {
		return builder().description("Payload is not valid").code(PAYLOAD_NOT_VALID.name()).build();
	}

	public static ErrorPayload buildInvalidDateFormatPayload(String date, String format) {
		return builder().description(format("Given date=%s has to have the format %s", date, format)).code(INVALID_DATE_FORMAT.name()).build();
	}

	public static ErrorPayload buildInvalidArrivalDatePayload() {
		return builder().description("The campsite can be reserved minimum 1 day(s) ahead of arrival and up to 1 month in advance").code(INVALID_ARRIVAL_DATE.name()).build();
	}

	public static ErrorPayload buildInvalidReservationDurationPayload() {
		return builder().description("The campsite can be reserved for max 3 days.").code(INVALID_RESERVATION_DURATION.name()).build();
	}
}
