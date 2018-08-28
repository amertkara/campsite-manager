package com.amertkara.campsitemanager.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.amertkara.campsitemanager.controller.dto.CampsiteDTO;
import com.amertkara.campsitemanager.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/campsite", produces = APPLICATION_JSON_VALUE)
public class CampsiteController {
	private final ReservationService reservationService;

	@GetMapping
	public ResponseEntity<CampsiteDTO> get() {
		log.debug("Received a get campsite request");
		return ResponseEntity.ok(CampsiteDTO.builder().build());
	}
}
