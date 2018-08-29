package com.amertkara.campsitemanager.controller;

import static com.amertkara.campsitemanager.controller.CampsiteController.CAMPSITE_PATH;
import static com.amertkara.campsitemanager.controller.dto.util.DateParser.toLocalDate;
import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.amertkara.campsitemanager.controller.dto.CampsiteDTO;
import com.amertkara.campsitemanager.controller.dto.ReservationDTO;
import com.amertkara.campsitemanager.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = CAMPSITE_PATH, produces = APPLICATION_JSON_VALUE)
public class CampsiteController {
	public static final String CAMPSITE_PATH = "/campsite";

	private final ReservationService reservationService;

	@GetMapping
	public ResponseEntity<CampsiteDTO> get(@RequestParam(value = "startDateParam", required = false) String startDateParam, @RequestParam(value = "endDateParam", required = false) String endDateParam) {
		log.debug("Received a get campsite request for startDate={} endDate={}", startDateParam, endDateParam);
		Date startDate = parseStartDate(startDateParam);
		Date endDate = parseEndDate(startDate, endDateParam);
		List<ReservationDTO> existingReservations = reservationService.getReservations(startDate, endDate);
		CampsiteDTO campsiteDTO = new CampsiteDTO();
		campsiteDTO.setUnavailableDates(existingReservations);
		campsiteDTO.setAvailability(startDate, endDate, existingReservations);
		return ResponseEntity.ok(campsiteDTO);
	}

	private Date parseStartDate(String startDateParam) {
		if (isBlank(startDateParam)) {
			return Date.from(Instant.now());
		} else {
			return Date.from(toLocalDate(startDateParam).atStartOfDay(ZoneId.systemDefault()).toInstant());
		}
	}

	private Date parseEndDate(Date startDate, String endDateParam) {
		if (isBlank(endDateParam)) {
			return DateUtils.addDays(startDate, 30);
		} else {
			Date endDate = Date.from(toLocalDate(endDateParam).atStartOfDay(ZoneId.systemDefault()).toInstant());
			if (endDate.before(startDate)) {
				return DateUtils.addDays(startDate, 30);
			} else {
				return endDate;
			}
		}
	}
}
