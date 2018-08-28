package com.amertkara.campsitemanager.controller;

import static com.amertkara.campsitemanager.controller.ReservationController.RESERVATIONS_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

import com.amertkara.campsitemanager.controller.dto.ReservationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = RESERVATIONS_PATH, produces = APPLICATION_JSON_VALUE)
public class ReservationController {
	static final String RESERVATIONS_PATH = "/reservations";

	@PutMapping(consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@Valid @RequestBody ReservationDTO reservationDTO) {
		log.debug("Received a create request for a reservation, payload={}", reservationDTO);
		// ReservationDTO reservation = reservationService.saveOrUpdate(reservationDTO);
		log.debug("Created the reservation reservationUuid={}", reservationDTO.getUuid());
		// return ResponseEntity.created(fromPath(RESERVATIONS_PATH).pathSegment(reservation.getUuid()).build().toUri()).build();
		return ResponseEntity.created(fromPath(RESERVATIONS_PATH).pathSegment(reservationDTO.getUuid()).build().toUri()).build();
	}

	@GetMapping(path = "/{reservationUuid}")
	public ResponseEntity<ReservationDTO> get(@PathVariable String reservationUuid) {
		log.debug("Received a get request for the reservation reservationUuid={}", reservationUuid);
		// return ResponseEntity.ok(reservationService.get(reservationUuid));
		return ResponseEntity.ok(null);
	}

	@PostMapping(path = "/{reservationUuid}", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(@PathVariable String reservationUuid, @Valid @RequestBody ReservationDTO reservationDTO) {
		log.debug("Received an update request for the reservation reservationUuid={}, payload={}", reservationUuid, reservationDTO);
		// reservationDTO.setUuid(reservationUuid);
		// reservationService.saveOrUpdate(reservationDTO);
		log.debug("Updated the reservation reservationUuid={}", reservationUuid);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(path = "/{reservationUuid}")
	public ResponseEntity<Void> delete(@PathVariable String reservationUuid) {
		log.debug("Received a delete request for the reservation reservationUuid={}", reservationUuid);
		// reservationService.delete(reservationUuid);
		log.debug("Deleted the reservation reservationUuid={}", reservationUuid);
		return ResponseEntity.noContent().build();
	}
}