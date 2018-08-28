package com.amertkara.campsitemanager.controller.dto;

import com.amertkara.campsitemanager.controller.dto.validation.CheckArrivalDate;
import com.amertkara.campsitemanager.controller.dto.validation.CheckDate;
import com.amertkara.campsitemanager.controller.dto.validation.CheckReservationDuration;
import com.amertkara.campsitemanager.controller.dto.validation.ValidateableReservation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = {"id", "duration"}, allowGetters = true)
@CheckReservationDuration
public class ReservationDTO implements ValidateableReservation {
	@JsonIgnore
	private Long id;
	@JsonProperty("id")
	private String uuid;
	@NotBlank
	private String email;
	@NotBlank
	private String fullName;
	@NotBlank
	@CheckDate
	@CheckArrivalDate
	private String arrivalDate;
	@NotBlank
	@CheckDate
	private String departureDate;
	private Long duration;
}
