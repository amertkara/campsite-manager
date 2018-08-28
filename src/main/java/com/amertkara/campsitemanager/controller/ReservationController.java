package com.amertkara.campsitemanager.controller;

import static com.amertkara.campsitemanager.controller.ReservationController.RESERVATIONS_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

import com.amertkara.campsitemanager.controller.dto.ReservationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = RESERVATIONS_PATH, produces = APPLICATION_JSON_VALUE)
public class ReservationController {
	static final String RESERVATIONS_PATH = "/reservations";

	@PutMapping(consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@Valid @RequestBody ReservationDTO reservationDTO) {
		log.debug("Received a create request for a network node payload={}", reservationDTO);

		log.debug("Created the network node networkNodeUuid={}", reservationDTO.getUuid());
		return ResponseEntity.created(fromPath(RESERVATIONS_PATH).pathSegment(reservationDTO.getUuid()).build().toUri()).build();
	}
}