package com.amertkara.campsitemanager.controller.dto;

import com.amertkara.campsitemanager.controller.dto.validation.CheckArrivalDate;
import com.amertkara.campsitemanager.controller.dto.validation.CheckDate;
import com.amertkara.campsitemanager.controller.dto.validation.CheckReservationDuration;
import com.amertkara.campsitemanager.controller.dto.validation.ValidateableReservation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = {"id"}, allowGetters = true)
@CheckReservationDuration
public class ReservationDTO implements ValidateableReservation {
	@JsonProperty("id")
	private String uuid;
	@NotBlank(message = "Email cannot be empty")
	private String email;
	@NotBlank(message = "Fullname cannot be empty")
	private String fullName;
	@NotBlank
	@CheckDate
	@CheckArrivalDate
	private String arrivalDate;
	@NotBlank
	@CheckDate
	private String departureDate;
}
